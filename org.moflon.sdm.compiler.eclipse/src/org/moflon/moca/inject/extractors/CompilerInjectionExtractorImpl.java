package org.moflon.moca.inject.extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.moca.inject.validation.InjectionValidationMessage;

import SDMLanguage.sdmUtil.CompilerInjection;
import SDMLanguage.sdmUtil.ImportInjectionEntry;
import SDMLanguage.sdmUtil.SdmUtilFactory;

public class CompilerInjectionExtractorImpl implements InjectionExtractor
{
   final private IProject project;

   final private ResourceSet resourceSet;


   private CompilerInjection compilerInjection;

   private Map<String, List<String>> imports;

   private Map<EClass, String> genClassNameToEClass;

   private GenModel genModel;

   public CompilerInjectionExtractorImpl(final IProject project, final GenModel genModel)
   {
      this.project = project;
      this.resourceSet = genModel.eResource().getResourceSet();
      this.imports = new HashMap<String, List<String>>();
      this.genClassNameToEClass = new HashMap<EClass, String>();
      this.genModel = genModel;
   }

   @Override
   public void extractInjections()
   {
      IFile compilerInjectionFile = project.getFolder("model").getFile(MoflonUtil.lastCapitalizedSegmentOf(project.getName()) + ".injection.xmi");
      if (compilerInjectionFile.exists())
         compilerInjection = (CompilerInjection) eMoflonEMFUtil.loadModelWithDependencies(compilerInjectionFile.getLocation().toString(), resourceSet);
      else
         compilerInjection = SdmUtilFactory.eINSTANCE.createCompilerInjection();

      for (GenPackage genPackage : genModel.getGenPackages())
      {
         handleGenPackage(genPackage);
      }

      for (ImportInjectionEntry injectionEntry : compilerInjection.getImportInjectionEntries())
      {
         String genClassName = genClassNameToEClass.get(injectionEntry.getEClass());
         if (!imports.containsKey(genClassName))
         {
            imports.put(genClassName, new ArrayList<String>());
         }
         imports.get(genClassName).add(injectionEntry.getContent());
      }

   }

   private void handleGenPackage(final GenPackage genPackage)
   {
      extractGenModelClasses(genPackage);
      for (GenPackage subPackage : genPackage.getSubGenPackages())
      {
         handleGenPackage(subPackage);
      }
      
   }

   private void extractGenModelClasses(final GenPackage genPackage)
   {
      for (GenClass genClass : genPackage.getGenClasses())
      {
         EClass eClass = genClass.getEcoreClass();
         genClassNameToEClass.put(eClass, CodeGeneratorPlugin.getClassName(genClass));
      }
   }

   @Override
   public boolean hasModelCode(final EOperation eOperation)
   {
      return compilerInjection.getCompilerInjectionEntries().stream().anyMatch(entry -> entry.getEOperation().equals(eOperation));
   }

   @Override
   public String getModelCode(final EOperation eOperation)
   {
      return compilerInjection.getCompilerInjectionEntries().stream().filter(entry -> entry.getEOperation().equals(eOperation)).findAny().get().getContent();
   }

   @Override
   public List<InjectionValidationMessage> getErrors()
   {
      return Arrays.asList();
   }

   @Override
   public Set<String> getMembersPaths()
   {
      throw new UnsupportedOperationException("Compiler injections currently do not support member injections!");
   }

   @Override
   public String getMembersCode(final String fullyQualifiedName)
   {
      throw new UnsupportedOperationException("Compiler injections currently do not support member injections!");
   }

   @Override
   public String getMembersCodeByClassName(final String className)
   {
      throw new UnsupportedOperationException("Compiler injections currently do not support member injections!");
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
}
