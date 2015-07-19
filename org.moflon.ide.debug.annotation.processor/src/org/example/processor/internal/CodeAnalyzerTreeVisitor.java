package org.example.processor.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;

import org.apache.log4j.Logger;
import org.moflon.tgg.algorithm.synchronization.DebugBreakpoint;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.util.Pair;

import demo.codeanalyzer.common.model.AnnotationInfo;
import demo.codeanalyzer.common.model.JavaClassInfo;
import demo.codeanalyzer.helper.ClassInfoDataSetter;
import demo.codeanalyzer.helper.FieldInfoDataSetter;

/**
 * Visitor class which visits different nodes of the input source file, extracts the required atribute of the visiting
 * class, its mehods, fields, annotations etc and set it in the java class model.
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class CodeAnalyzerTreeVisitor extends TreePathScanner<Object, Trees>
{
   public static final Logger log = Logger.getLogger(CodeAnalyzerTreeVisitor.class);

   // Model class stores the details of the visiting class
   private JavaClassInfo clazzInfo = new JavaClassInfo();

   private Map<DebugBreakpoint.Phase, Pair<String, Long>> phaseBreakpoints = new HashMap<DebugBreakpoint.Phase, Pair<String, Long>>();

   public Map<DebugBreakpoint.Phase, Pair<String, Long>> getPhaseBreakpoints()
   {
      return phaseBreakpoints;
   }

   private JCClassDecl computeCurrentClass()
   {
      // TODO Think about multiple classes in one file
      return (JCClassDecl) getCurrentPath().getCompilationUnit().getTypeDecls().get(0);
   }

   @Override
   public Object visitAnnotation(AnnotationTree annotationTree, Trees trees)
   {
      TreePath path = getCurrentPath();
      if (annotationTree instanceof JCAnnotation)
      {
         JCAnnotation jcannotation = (JCAnnotation) annotationTree;
         long linenumber = path.getCompilationUnit().getLineMap().getLineNumber(jcannotation.getStartPosition());
         Type.ClassType classType = (Type.ClassType) jcannotation.type;
         String annotationName;
         if (classType == null)
         {
            JCIdent ic = (JCIdent) jcannotation.getAnnotationType();
            annotationName = ic.name.toString();
         } else
         {
            annotationName = classType.tsym.getQualifiedName().toString();
         }
         if (annotationName.equals(DebugBreakpoint.class.getCanonicalName()) || annotationName.equals(DebugBreakpoint.class.getSimpleName()))
         {
            JCClassDecl classDeclaration = computeCurrentClass();
            List<JCExpression> arguments = jcannotation.getArguments();
            if (arguments.size() == 1)
            {
               JCAssign phaseAssign = (JCAssign) arguments.get(0);

               String name = null;
               if (phaseAssign.rhs instanceof MemberSelectTree)
               {
                  name = ((MemberSelectTree) phaseAssign.rhs).getIdentifier().toString();
               } else if (phaseAssign.rhs instanceof IdentifierTree)
               {
                  name = ((IdentifierTree) phaseAssign.rhs).getName().toString();
               } else
               {
                  throw new RuntimeException((phaseAssign.rhs != null ? phaseAssign.rhs.getClass().getName() : phaseAssign.rhs)
                        + " is not supported. Please Implement.");
               }

               if (!phaseBreakpoints.containsKey(DebugBreakpoint.Phase.valueOf(name)))
               {
                  String fullClassName = getCurrentPath().getCompilationUnit().getPackageName() + "." + classDeclaration.getSimpleName().toString();
                  phaseBreakpoints.put(DebugBreakpoint.Phase.valueOf(name), new Pair<String, Long>(fullClassName, linenumber));
               }
               log.info(classDeclaration.getSimpleName() + "->" + annotationName + "#" + name + " : " + linenumber);
            } else
            {
               log.info(classDeclaration.getSimpleName() + "->" + annotationName + " : " + linenumber);
            }
         }
      }
      return super.visitAnnotation(annotationTree, trees);
   }

   /**
    * Visits the class
    * 
    * @param classTree
    * @param trees
    * @return
    */
   @Override
   public Object visitClass(ClassTree classTree, Trees trees)
   {
      TreePath path = getCurrentPath();
      // populate required class information to model
      ClassInfoDataSetter.populateClassInfo(clazzInfo, classTree, path, trees);
      return super.visitClass(classTree, trees);
   }

   /**
    * Visits all methods of the input java source file
    * 
    * @param methodTree
    * @param trees
    * @return
    */
   @Override
   public Object visitMethod(MethodTree methodTree, Trees trees)
   {
      TreePath path = getCurrentPath();
      MethodSymbol e = (MethodSymbol) trees.getElement(path);
      DebugBreakpoint[] annotations = e.getAnnotationsByType(DebugBreakpoint.class);
      for (DebugBreakpoint annotationMirror : annotations)
      {
         String qualifiedName = annotationMirror.toString().substring(1);
         AnnotationInfo annotationInfo = new AnnotationInfo();
         annotationInfo.setName(qualifiedName);
         clazzInfo.addAnnotation(annotationInfo);

         // ToDo necessary if Linebreakpoints are not efficient enough
         // JCClassDecl classDeclaration = computeCurrentClass();
         //
         // LocationInfo locationInfo = DataSetterUtil.getLocationInfo(trees, path, methodTree);
         // long linenumber = path.getCompilationUnit().getLineMap().getLineNumber(locationInfo.getStartOffset());
         //
         // phaseBreakpoints.put(annotationMirror.phase(), new Pair<String,
         // Long>(classDeclaration.getSimpleName().toString(), linenumber));
      }

      // populate required method information to model
      // MethodInfoDataSetter.populateMethodInfo(clazzInfo, methodTree, path, trees);
      return super.visitMethod(methodTree, trees);
   }

   /**
    * Visits all variables of the java source file
    * 
    * @param variableTree
    * @param trees
    * @return
    */
   @Override
   public Object visitVariable(VariableTree variableTree, Trees trees)
   {
      // System.out.println(variableTree);
      TreePath path = getCurrentPath();
      Element e = trees.getElement(path);

      // populate required method information to model
      FieldInfoDataSetter.populateFieldInfo(clazzInfo, variableTree, e, path, trees);
      return super.visitVariable(variableTree, trees);
   }

   /**
    * Returns the Java class model which holds the details of java source
    * 
    * @return clazzInfo Java class model
    */
   public JavaClassInfo getClassInfo()
   {
      return clazzInfo;
   }
}
