package org.moflon.ide.metamodelevolution.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.ui.SharedASTProvider;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.metamodelevolution.core.processing.MetamodelDeltaProcessor;

public class EMFCompareMetamodelDeltaProcessor implements MetamodelDeltaProcessor
{

   private static final String JAVA_NATURE = "org.eclipse.jdt.core.javanature";  //TODO@settl : Use JavaCore.NATURE_ID

   private String changedValue = null;

   private String newValue = null;

   public void processDelta(final IProject project, final Map<String, String> delta)
   { // TODO@settl: document the format of the delta as soon as possible Map<String,String> is relatively generic
     // (rkluge)
      try
      {
         if (project.isOpen() && project.hasNature(JAVA_NATURE))
         {
            // TODO@settl: adapt for multiple changes
            if (delta.containsKey("changedValue"))
            {
               changedValue = delta.get("changedValue");
               newValue = delta.get(changedValue);
            } else
               return;

            Set<ICompilationUnit> compilationUnits = new HashSet<ICompilationUnit>();
            try
            {
               // find all markers in project
               IMarker[] markers = project.findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
               if (markers.length <= 0) 
                  return;
               
               for (IMarker marker : markers)
               {
                  // find all CompilationUnits with markers
                  ICompilationUnit cu = getICompilationUnitFromMarker(marker);
                  if (cu != null)
                  {
                     compilationUnits.add(cu);
                  }
               }
            } catch (CoreException e)
            {
               e.printStackTrace();
               return;
            }
            // process errors for each CompilationUnit
            for (ICompilationUnit cu : compilationUnits)
            {
               processCompilationUnit(cu);
            }
         }

      } catch (CoreException e1)
      {
         e1.printStackTrace();
      }
   }

   private void processCompilationUnit(final ICompilationUnit cu) throws CoreException
   {

      boolean hasChanges = false;

      CompilationUnit compilationUnit = getAstRoot(cu);
      AST ast = compilationUnit.getAST();
      ASTRewrite rewriter = ASTRewrite.create(ast);

      // find markers for each CompilationUnit
      IMarker[] markers = findProblemMarkers(cu);

      for (IMarker marker : markers)
      {
         ASTNode node = getASTNodeFromMarkerForCompilationUnit(marker, compilationUnit);
         if (node == null)
            break;
         String nodeValue = node.toString();
         int nodeType = node.getNodeType();

         switch (nodeType)
         {
         case ASTNode.SIMPLE_NAME:
            if (nodeValue.equals(changedValue))
            {
               // System.out.println("Refactoring SimpleName of type: " +
               // ASTNode.nodeClassForType(node.getParent().getNodeType()).toString());
               SimpleName newName = ast.newSimpleName(newValue);
               rewriter.replace(node, newName, null);
               hasChanges = true;
            } else if (node.getParent().getNodeType() == ASTNode.METHOD_INVOCATION)
            {
               SimpleName simpleName = (SimpleName) node;
               if (simpleName.getIdentifier().contains(changedValue))
               {
                  String newString = simpleName.getIdentifier().replace(changedValue, newValue);
                  SimpleName newName = ast.newSimpleName(newString);
                  rewriter.replace(node, newName, null);
                  hasChanges = true;
               }
            }

            break;
         case ASTNode.QUALIFIED_NAME:
            ASTNode parentNode = node.getParent();
            // handle imports
            if (parentNode.getNodeType() == ASTNode.IMPORT_DECLARATION)
            {

               QualifiedName importName = (QualifiedName) node;
               String importClassName = importName.getName().getIdentifier();
               if (changedValue.equals(importClassName))
               {
                  // System.out.println("packages: " + importName.getQualifier().getFullyQualifiedName());
                  String[] identifiers = resolveImportStatement(importName.getQualifier(), newValue);
                  ImportDeclaration id = ast.newImportDeclaration();
                  id.setName(ast.newName(identifiers));
                  rewriter.replace(parentNode, id, null);

                  hasChanges = true;
               }

               break;
            }
            break;
         }
      }
      if (hasChanges)
      {
         Document document = new Document(cu.getSource());
         TextEdit edits = rewriter.rewriteAST(document, null);
         try
         {
            edits.apply(document);
         } catch (MalformedTreeException | BadLocationException e)
         {
            e.printStackTrace();
         }
         // update the compilation unit/code
         cu.getBuffer().setContents(document.get());
         cu.commitWorkingCopy(false, null);

         if (cu.getPath().toString().contains("/gen/"))
         {

            JavaFileInjectionExtractor injectionExtractor = new JavaFileInjectionExtractor();
            injectionExtractor.extractInjectionNonInteractively((IFile) cu.getUnderlyingResource());
         }
      }
   }

   private String[] resolveImportStatement(final Name name, final String newClassName)
   {
      ArrayList<String> packages = new ArrayList<String>();
      // case import QualifiedName.SimpleName: parse QualifiedName recursively
      if (name instanceof QualifiedName)
      {
         packages = resolvePackages((QualifiedName) name, packages);
      }
      // case import SimpleName.SimpleName
      else
      {
         packages.add(((SimpleName) name).getIdentifier());
      }
      packages.add(newClassName);

      String[] identifiers = new String[packages.size()];
      identifiers = packages.toArray(identifiers);
      return identifiers;
   }

   private ArrayList<String> resolvePackages(final QualifiedName name, final ArrayList<String> identifiers)
   {
      if (name.getQualifier() instanceof SimpleName)
      {
         identifiers.add(0, name.getName().getIdentifier());
         identifiers.add(0, ((SimpleName) name.getQualifier()).getIdentifier());
         return identifiers;
      } else
      {
         identifiers.add(0, name.getName().getIdentifier());
         return resolvePackages((QualifiedName) name.getQualifier(), identifiers);
      }
   }

   private ICompilationUnit getICompilationUnitFromMarker(final IMarker marker)
   {
      IResource res = marker.getResource();
      if (res.getType() == IResource.FILE)
      {
         IFile f = (IFile) res;
         ICompilationUnit cu = JavaCore.createCompilationUnitFrom(f);
         return cu;
      }
      return null;
   }

   public IMarker[] findProblemMarkers(final ICompilationUnit cu) throws CoreException
   {
      IResource javaSourceFile = cu.getUnderlyingResource();
      IMarker[] markers = javaSourceFile.findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
      return markers;
   }

   public ASTNode getASTNodeFromMarkerForICompilationUnit(final IMarker marker, final ICompilationUnit cu)
   {
      CompilationUnit astRoot = getAstRoot(cu);
      int start = marker.getAttribute(IMarker.CHAR_START, 0);
      int end = marker.getAttribute(IMarker.CHAR_END, 0);
      if (!(end == 0))
      {
         NodeFinder nf = new NodeFinder(astRoot, start, end - start);
         // System.out.println("covering node: " + nf.getCoveringNode() + "; type: " +
         // nf.getCoveringNode().getNodeType());
         return nf.getCoveringNode();
      } else
      {
         return null;
      }
   }

   public ASTNode getASTNodeFromMarkerForCompilationUnit(final IMarker marker, final CompilationUnit cu)
   {

      int start = marker.getAttribute(IMarker.CHAR_START, 0);
      int end = marker.getAttribute(IMarker.CHAR_END, 0);
      if (!(end == 0))
      {
         NodeFinder nf = new NodeFinder(cu, start, end - start);
         // System.out.println("covering node: " + nf.getCoveringNode() + "; type: " +
         // nf.getCoveringNode().getNodeType());
         return nf.getCoveringNode();
      } else
      {
         return null;
      }
   }

   private CompilationUnit getAstRoot(final ITypeRoot typeRoot)
   {
      CompilationUnit root = SharedASTProvider.getAST(typeRoot, SharedASTProvider.WAIT_YES, null);
      if (root == null)
      {
         ASTParser astParser = ASTParser.newParser(AST.JLS8);
         astParser.setSource(typeRoot);
         astParser.setResolveBindings(true);
         astParser.setStatementsRecovery(true);
         astParser.setBindingsRecovery(true);
         root = (CompilationUnit) astParser.createAST(null);
      }
      return root;
   }

   @Override
   public void processDelta(final IProject project, final MetamodelDelta delta)
   {
      // TODO Auto-generated method stub

   }
}
