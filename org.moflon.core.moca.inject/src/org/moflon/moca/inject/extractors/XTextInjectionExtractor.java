package org.moflon.moca.inject.extractors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.moca.inject.CodeInjectionPlugin;
import org.moflon.moca.inject.util.ClassNameToPathConverter;

public class XTextInjectionExtractor implements InjectionExtractor
{

   private final HashMap<EOperation, String> modelCode;

   private final HashMap<String, String> membersCode;

   private final HashMap<String, List<String>> imports;

   private final HashMap<String, GenClass> fqnToGenClassMap;

   private final ClassNameToPathConverter classNameToPathConverter;

   private final IFolder injectionRootFolder;

   private GenModel genModel;

   public XTextInjectionExtractor(final IFolder injectionFolder, final GenModel genModel)
   {
      this.modelCode = new HashMap<EOperation, String>();
      this.membersCode = new HashMap<String, String>();
      this.imports = new HashMap<String, List<String>>();
      this.fqnToGenClassMap = new HashMap<String, GenClass>();

      this.injectionRootFolder = injectionFolder;
      this.classNameToPathConverter = new ClassNameToPathConverter(WorkspaceHelper.GEN_FOLDER);
      this.genModel = genModel;
   }

   @Override
   public IStatus extractInjections()
   {
      /*
       * Traverse injectionRootFolder recursively, collecting the relative path on the way
       */
      final MultiStatus resultStatus = new MultiStatus(WorkspaceHelper.getPluginId(getClass()), 0, "Problems during injection extraction", null);
      try
      {
         processGenModel();

         this.injectionRootFolder.accept(new IResourceVisitor() {

            @Override
            public boolean visit(final IResource resource) throws CoreException
            {
               //TODO@rkluge: Continue here
               final IFile injectionFile = resource.getAdapter(IFile.class);
               if (injectionFile != null && WorkspaceHelper.INJECTION_FILE_EXTENSION.equals(resource.getFileExtension()))
               {
                  System.out.println(injectionFile);
               }
               return true;
            }
         });
      } catch (final CoreException e)
      {
         resultStatus.add(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Exception during injection extraction", e));
      }
      return resultStatus.matches(IStatus.WARNING) ? resultStatus : Status.OK_STATUS;
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

   /**
    * Extracts all relevant information from the {@link GenModel}
    */
   private void processGenModel()
   {
      for (final GenPackage genPackage : genModel.getGenPackages())
      {
         processGenPackageContents(genPackage);
      }
   }

   /**
    * Extracts the fully-qualified name of each {@link GenClass} in the given {@link GenPackage} and adds it to {@link #fqnToGenClassMap}
    * @param genPackage the {@link GenPackage} to process
    */
   private final void processGenPackageContents(final GenPackage genPackage)
   {
      for (final GenClass genClass : genPackage.getGenClasses())
      {
         fqnToGenClassMap.put(CodeInjectionPlugin.getInterfaceName(genClass), genClass);
         fqnToGenClassMap.put(CodeInjectionPlugin.getClassName(genClass), genClass);
      }
      for (final GenPackage subPackage : genPackage.getSubGenPackages())
      {
         processGenPackageContents(subPackage);
      }
   }
}
