package org.moflon.moca.inject.extractors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.moca.inject.util.ClassNameToPathConverter;
import org.moflon.moca.inject.validation.InjectionValidationMessage;

public class XTextInjectionExtractor implements InjectionExtractor
{

   private final HashMap<EOperation, String> modelCode;

   private final HashMap<String, String> membersCode;

   private final HashMap<String, List<String>> imports;

//   private final HashMap<String, GenClass> fqnToGenClassMap;

   private final ClassNameToPathConverter classNameToPathConverter;

   private final List<InjectionValidationMessage> errors;
   
//   private final GenModel genModel;

   private final IFolder injectionRootFolder;
   
   public XTextInjectionExtractor(final IFolder injectionFolder, final GenModel genModel)
   {
      this.modelCode = new HashMap<EOperation, String>();
      this.membersCode = new HashMap<String, String>();
      this.imports = new HashMap<String, List<String>>();
//      this.fqnToGenClassMap = new HashMap<String, GenClass>();
      this.errors = new ArrayList<>();

      this.injectionRootFolder = injectionFolder;
      this.classNameToPathConverter = new ClassNameToPathConverter(WorkspaceHelper.GEN_FOLDER);
//      this.genModel = genModel;
   }
   
   @Override
   public void extractInjections()
   {
      /*
       * Traverse injectionRootFolder recursively, collecting the relative path on the way
       */
      try
      {
         this.injectionRootFolder.accept(new IResourceVisitor() {
            
            @Override
            public boolean visit(final IResource resource) throws CoreException
            {
               //TODO@rkluge: Continue here
               return false;
            }
         });
      } catch (final CoreException e)
      {
         // TODO@rkluge: Wrap exception
      }
   }

   @Override
   public List<InjectionValidationMessage> getErrors()
   {
      return this.errors;
   }

   @Override
   public Collection<String> getImportsPaths()
   {
      return this.imports.keySet();
   }

   @Override
   public List<String> getImports(final String fullyQualifiedName)
   {
      final List<String> result = imports.get(fullyQualifiedName);
      return result != null ? result : new ArrayList<String>();
   }

   @Override
   public List<String> getAllImports()
   {
      final List<String> allRawImports = new ArrayList<>();
      for (final String file : this.imports.keySet())
      {
         allRawImports.addAll(this.imports.get(file));
      }
      return allRawImports;
   }

   @Override
   public boolean hasModelCode(final EOperation eOperation)
   {
      return modelCode.containsKey(eOperation);
   }

   @Override
   public String getModelCode(final EOperation eOperation)
   {
      return modelCode.get(eOperation);
   }

   @Override
   public Set<String> getMembersPaths()
   {
      return membersCode.keySet();
   }

   @Override
   public String getMembersCode(final String fullyQualifiedName)
   {
      return membersCode.get(fullyQualifiedName);
   }

   @Override
   public String getMembersCodeByClassName(final String className)
   {
      final String path = this.classNameToPathConverter.toPath(className);
      return this.getMembersCode(path);
   }

}
