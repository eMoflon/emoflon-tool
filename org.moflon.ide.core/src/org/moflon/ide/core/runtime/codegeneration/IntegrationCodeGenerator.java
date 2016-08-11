package org.moflon.ide.core.runtime.codegeneration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.MonitoredMetamodelLoader;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.csp.CSPNotSolvableException;
import org.moflon.dependency.PackageRemappingDependency;
import org.moflon.eclipse.resource.SDMEnhancedEcoreResource;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.builders.IntegrationBuilder;
import org.moflon.moca.MocaUtil;
import org.moflon.moca.tggUserDefinedConstraint.unparser.TGGUserDefinedConstraintUnparserAdapter;
import org.moflon.moca.tie.RunIntegrationGeneratorBatch;
import org.moflon.moca.tie.RunIntegrationGeneratorCC;
import org.moflon.moca.tie.RunIntegrationGeneratorSync;
import org.moflon.moca.tie.RunModelGenerationGenerator;
import org.moflon.properties.MoflonPropertiesContainerHelper;
import org.moflon.tgg.cspcodeadapter.CspcodeadapterFactory;
import org.moflon.tgg.cspcodeadapter.VariableTypeManager;
import org.moflon.tgg.language.LanguagePackage;
import org.moflon.tgg.language.TGGRule;
import org.moflon.tgg.language.TripleGraphGrammar;
import org.moflon.tgg.language.algorithm.ApplicationTypes;
import org.moflon.tgg.language.analysis.StaticAnalysis;
import org.moflon.tgg.language.compiler.CompilerFactory;
import org.moflon.tgg.language.compiler.TGGCompiler;
import org.moflon.tgg.language.compiler.compilerfacade.CompilerfacadeFactory;
import org.moflon.tgg.language.compiler.compilerfacade.InjectionHelper;
import org.moflon.tgg.language.csp.CSP;
import org.moflon.tgg.language.csp.TGGConstraint;
import org.moflon.tgg.language.modelgenerator.RuleAnalyzerResult;
import org.moflon.tgg.language.precompiler.CyclicContextMessage;
import org.moflon.tgg.language.precompiler.PrecompileLog;
import org.moflon.tgg.language.precompiler.PrecompileMessage;
import org.moflon.tgg.language.precompiler.PrecompilerFactory;
import org.moflon.tgg.language.precompiler.RuleProcessingMessage;
import org.moflon.tgg.language.precompiler.TGGPrecompiler;
import org.moflon.tgg.runtime.RuntimePackage;

import MocaTree.MocaTreePackage;
import MoflonPropertyContainer.MoflonPropertiesContainer;
import SDMLanguage.SDMLanguagePackage;
import SDMLanguage.sdmUtil.CompilerInjection;
import SDMLanguage.sdmUtil.SdmUtilFactory;

//TODO@rkluge
/**
 * @deprecated Should be eliminated by the new automatic build process (rkluge, 2016-08-11)
 */
@Deprecated
public class IntegrationCodeGenerator extends RepositoryCodeGenerator
{
   private static final Logger logger = Logger.getLogger(IntegrationCodeGenerator.class);
   
   List<TGGConstraint> userDefinedConstraints = new ArrayList<TGGConstraint>();

   public static final String SUFFIX_SMA = ".sma.xmi";

   private TripleGraphGrammar tgg;

   public IntegrationCodeGenerator(final IProject project)
   {
      super(project);
   }

   @Override
   public boolean generateCode(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Generating code", 500);
         processTGG(monitor);

         ResourceSet set = CodeGeneratorPlugin.createDefaultResourceSet();
         eMoflonEMFUtil.installCrossReferencers(set);
         final MoflonPropertiesContainer moflonProperties = MoflonPropertiesContainerHelper.load(project, WorkspaceHelper.createSubMonitor(monitor, 100));

         final MonitoredMetamodelLoader metamodelLoader = new MonitoredMetamodelLoader(set, RepositoryCodeGenerator.getEcoreFile(project), moflonProperties);
         metamodelLoader.run(monitor);
         WorkspaceHelper.createSubMonitor(monitor, 100);

         Resource ecoreResource = metamodelLoader.getEcoreResource();
         HashMap<String, Object> saveOptions = new HashMap<String, Object>();
         saveOptions.put(SDMEnhancedEcoreResource.SAVE_GENERATED_PACKAGE_CROSSREF_URIS, Boolean.valueOf(true));
         try
         {
            ecoreResource.save(saveOptions);
         } catch (IOException e)
         {
            e.printStackTrace();
         }
         WorkspaceHelper.createSubMonitor(monitor, 100);

         boolean success = super.generateCode(WorkspaceHelper.createSubMonitor(monitor, 100));

         CoreActivator.getDefault().setDirty(project, false);
         removeObsoleteErrorMarkers();
         
         return success;
      } finally
      {
         monitor.done();
      }

   }

private void removeObsoleteErrorMarkers() throws CoreException {
	project.deleteMarkers( "org.eclipse.xtext.ui.check.fast", true, IResource.DEPTH_INFINITE);
}

   private void createFilesFromPreFiles()
   {
      try
      {
         if (RepositoryCodeGenerator.getEcoreFile(project).exists())
            RepositoryCodeGenerator.getEcoreFile(project).delete(true, new NullProgressMonitor());

         // Try another build to solve many problems due to checking out a workspace	
         project.build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
         project.build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
         
         IntegrationBuilder.getPreEcoreFile(project).copy(RepositoryCodeGenerator.getEcoreFile(project).getFullPath(), true, new NullProgressMonitor());

         if (getTGGFile().exists())
            getTGGFile().delete(true, new NullProgressMonitor());

         IntegrationBuilder.getPreTGGFile(project).copy(getTGGFile().getFullPath(), true, new NullProgressMonitor());
      } catch (Exception e)
      {
    	 logger.error("I'm having problems building " + project.getName() + ", please refresh all projects and retry.");
         e.printStackTrace();
      }
   }

   private void processTGG(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Processing TGGs", 66);

         createFilesFromPreFiles();

         ResourceSet set = CodeGeneratorPlugin.createDefaultResourceSet();
         eMoflonEMFUtil.installCrossReferencers(set);
         final MoflonPropertiesContainer moflonProperties = MoflonPropertiesContainerHelper.load(project, WorkspaceHelper.createSubMonitor(monitor, 1));

         handleBuiltInMetamodels(set);
         monitor.worked(5);

         final MonitoredMetamodelLoader metamodelLoader = new MonitoredMetamodelLoader(set, RepositoryCodeGenerator.getEcoreFile(project), moflonProperties);
         try {
        	 metamodelLoader.run(WorkspaceHelper.createSubMonitor(monitor, 10));
         } catch(Exception e){
        	 logger.error("Unable to load " + RepositoryCodeGenerator.getEcoreFile(project));
        	 return;
         }

         // Make sure all dependencies are really loaded in the resource set.
         // This might be necessary as the tgg might depend on more than the correspondence metamodel does!
         moflonProperties.getDependencies().forEach(dep -> {
            URI uri = URI.createURI(dep.getValue());
            new PackageRemappingDependency(uri).getResource(set, true);
         });
         monitor.worked(5);

         // Load TGG file
         IFile tggFile = project.getFile("/model/" + MoflonUtil.lastCapitalizedSegmentOf(project.getName()) + WorkspaceHelper.TGG_FILE_EXTENSION);
         URI tggFileURI = URI.createPlatformResourceURI(tggFile.getFullPath().toOSString(), true);
         monitor.worked(5);

         // Compile TGGs to SDMs
         monitor.subTask(project.getName() + ": Translating TGG model to SDMs...");
         PackageRemappingDependency tggDependency = new PackageRemappingDependency(tggFileURI);
         Resource tggResource = tggDependency.getResource(set, true);
         tgg = (TripleGraphGrammar) tggResource.getContents().get(0);
         monitor.worked(5);

         if(tggIsEmpty()){
        	 monitor.done();
        	 logger.warn("Your TGG does not contain any rules, aborting attempt to generate code...");
        	 return;        	 
         }
         
         // Create and add precompiler to resourceSet so reverse navigation of links works
         TGGPrecompiler precompiler = PrecompilerFactory.eINSTANCE.createTGGPrecompiler();
         eMoflonEMFUtil.createParentResourceAndInsertIntoResourceSet(precompiler, set);

         // Precompile rules
         precompiler.setUseNewImpl(true);
         precompiler.precompileTGG(tgg);

         // print refinement precompiling log
         printPrecompilerLog(precompiler.getRefinementPrecompiler().getPrecompilelog());
         monitor.worked(5);

         // this has to be done here because it requires "flat" tgg rules instead of refined ones
         enrichCspsWithTypeInformation();

         // Persist tgg model after precompilation
         HashMap<String, Object> saveOptions = new HashMap<String, Object>();
         saveOptions.put(SDMEnhancedEcoreResource.SAVE_GENERATED_PACKAGE_CROSSREF_URIS, Boolean.valueOf(true));

         Resource genTGGResource = tgg.eResource();
         try
         {
            genTGGResource.save(saveOptions);
         } catch (IOException e1)
         {
            e1.printStackTrace();
         }
         monitor.worked(5);

         // Create SDMs from TGG specification
         StaticAnalysis staticAnalysis = null;

         // Prepare injection resource compiler injections
         URI compilerInjectionFileURI = URI.createURI(genTGGResource.getURI().toString().replace(WorkspaceHelper.TGG_FILE_EXTENSION, ".injection.xmi"));
         PackageRemappingDependency compilerInjectionFile = new PackageRemappingDependency(compilerInjectionFileURI);
         Resource compilerInjectionResource = compilerInjectionFile.getResource(set, false);
         InjectionHelper injectionHelper = CompilerfacadeFactory.eINSTANCE.createInjectionHelper();
         CompilerInjection compilerInjection = SdmUtilFactory.eINSTANCE.createCompilerInjection();
         injectionHelper.setCompilerInjection(compilerInjection);
         monitor.worked(5);

         if (isSynchronizationMode(moflonProperties))
         {
            TGGCompiler compiler = CompilerFactory.eINSTANCE.createTGGCompiler();
            eMoflonEMFUtil.createParentResourceAndInsertIntoResourceSet(compiler, set);

            compiler.setProperties(moflonProperties);
            compiler.setInjectionHelper(injectionHelper);
            compiler.deriveOperationalRules(tgg, ApplicationTypes.get(moflonProperties.getTGGBuildMode().getBuildMode().getValue()));
            staticAnalysis = compiler.getStaticAnalysis();

            // Persist compiler injections
            saveInjectionFiles(saveOptions, compiler, compilerInjectionResource);
         }
         monitor.worked(5);

         if (isModelGenMode(moflonProperties))
         {
            try
            {
               org.moflon.tgg.language.modelgenerator.Compiler compiler = org.moflon.tgg.language.modelgenerator.ModelgeneratorFactory.eINSTANCE.createCompiler();
               eMoflonEMFUtil.createParentResourceAndInsertIntoResourceSet(compiler, set);
               compiler.setProperties(moflonProperties);
               compiler.setInjectionHelper(injectionHelper);
               compiler.compileModelgenerationSdms(tgg);
               for (RuleAnalyzerResult analyzerResult : compiler.getRuleAnalyzer().getRuleAnalyzerResult())
               {
                  logger.warn(analyzerResult.getMessage() + ": " + analyzerResult.getEObject());
               }
               
               // Persist compiler injections
               saveInjectionFiles(saveOptions, compiler, compilerInjectionResource);
            } catch (CSPNotSolvableException e)
            {
               logger.warn("CSPs could not be solved for modelgenerator: " + e.getMessage(), e);
            }

         }
         monitor.worked(5);

         try
         {
            // Persist results of static analysis
            URI smaFileURI = URI.createURI(genTGGResource.getURI().toString().replace(WorkspaceHelper.TGG_FILE_EXTENSION, SUFFIX_SMA));
            PackageRemappingDependency smaFile = new PackageRemappingDependency(smaFileURI);
            Resource smaResource = smaFile.getResource(set, false);
            if (staticAnalysis != null)
            {
               smaResource.getContents().add(staticAnalysis);
            }
            smaResource.save(saveOptions);

            // Persist ecore resource
            set.getResources().add(genTGGResource);
            Resource ecoreResource = metamodelLoader.getEcoreResource();
            ecoreResource.save(saveOptions);
         } catch (IOException e)
         {
            throw new CoreException(new Status(IStatus.ERROR, CoreActivator.getModuleID(), IStatus.ERROR, e.getMessage(), e));
         }
         monitor.worked(5);

         generateUserDefinedConstraints(userDefinedConstraints);

         new RunIntegrationGeneratorBatch(project).doFinish();
         new RunIntegrationGeneratorSync(project).doFinish();
         new RunIntegrationGeneratorCC(project).doFinish();
         new RunModelGenerationGenerator(project).doFinish();
         monitor.worked(5);
      } finally
      {
         monitor.done();
      }
   }

   private boolean tggIsEmpty() {
	   return tgg.getTggRule().isEmpty();
   }

   private void enrichCspsWithTypeInformation()
   {
      for (TGGRule rule : tgg.getTggRule())
      {
         VariableTypeManager varTypeManager = CspcodeadapterFactory.eINSTANCE.createVariableTypeManager();
         varTypeManager.setTggrule(rule);
         CSP csp = rule.getCsp();
         if (csp != null)
         {
            try
            {
               varTypeManager.deriveVariableTypes(csp);
            } catch (IllegalStateException e)
            {
               throw new IllegalStateException("Unable to infer types of attribute constraints used in TGG rule: " + rule.getName());
            }
            for (TGGConstraint constraint : csp.getConstraints())
            {
               if (constraint.isUserDefined())
               {
                  userDefinedConstraints.add(constraint);
               }
            }
         }
      }
   }

   private void handleBuiltInMetamodels(final ResourceSet set)
   {
      ArrayList<String> metamodels = new ArrayList<>();
      metamodels.add(MocaTreePackage.eNS_URI);
      metamodels.add(SDMLanguagePackage.eNS_URI);
      metamodels.add(RuntimePackage.eNS_URI);
      metamodels.add(LanguagePackage.eNS_URI);
      metamodels.add(EcorePackage.eNS_URI);

      metamodels.forEach(mm -> {
         Resource resource = set.getResource(URI.createURI(mm), false);
         set.getResources().add(resource);
      });
   }

   /**
    * Generates the files to implement user defined constraints and fills them with basic content. <br>
    * An existing file won't be changed even if the definition of the constraint has been changed.
    * 
    * @throws CoreException
    */
   private void generateUserDefinedConstraints(final List<TGGConstraint> userDefinedConstraints) throws CoreException
   {
      TGGUserDefinedConstraintUnparserAdapter unparser = new TGGUserDefinedConstraintUnparserAdapter();

      if (userDefinedConstraints.size() != 0)
      {
         // Create required folder structure
         WorkspaceHelper.addAllFolders(project, "src/csp/constraints", new NullProgressMonitor());

         for (TGGConstraint constraint : userDefinedConstraints)
         {
            String content = unparser.unparseCspConstraint(constraint);

            String nameInUpperCase = MocaUtil.firstToUpper(constraint.getName());
            String path = "src/csp/constraints/" + nameInUpperCase + ".java";

            // Ignore existing files
            if (!project.getFile(path).exists())
               WorkspaceHelper.addFile(project, path, content, new NullProgressMonitor());
         }

      }
   }

   private void saveInjectionFiles(final HashMap<String, Object> saveOptions, final TGGCompiler compiler, final Resource compilerInjectionResource)
   {
      if (compiler.getInjectionHelper().getCompilerInjection() != null)
         compilerInjectionResource.getContents().add(compiler.getInjectionHelper().getCompilerInjection());

      try
      {
         compilerInjectionResource.save(saveOptions);
      } catch (IOException e)
      {
         logger.error("Unable to persist compiler injection model for project: " + project.getName());
      }
   }

   private boolean isModelGenMode(final MoflonPropertiesContainer moflonProperties)
   {
      switch (moflonProperties.getTGGBuildMode().getBuildMode())
      {
      case ALL:
         return true;
      case SIMULTANEOUS:
         return true;
      default:
         return false;
      }
   }

   private boolean isSynchronizationMode(final MoflonPropertiesContainer moflonProperties)
   {
      switch (moflonProperties.getTGGBuildMode().getBuildMode())
      {
      case ALL:
      case FORWARD_AND_BACKWARD:
      case BACKWARD:
      case FORWARD:
         return true;
      default:
         return false;
      }
   }

   private IFile getTGGFile()
   {
      return project.getFile(MoflonUtil.getDefaultPathToFileInProject(project.getName(), WorkspaceHelper.TGG_FILE_EXTENSION));
   }

   private void printPrecompilerLog(final PrecompileLog log)
   {
      String errorMessage = "";
      for (PrecompileMessage error : log.getPrecompileerror())
      {
         if (error instanceof CyclicContextMessage)
         {
            CyclicContextMessage cycle = (CyclicContextMessage) error;
            errorMessage += cycle.getMessage() + ": " + error.getRefinementrule() + " cycle found: ";

            for (TGGRule cyclicRule : cycle.getRefineablerules().getTggrule())
               errorMessage += cyclicRule.getName() + " ";

            errorMessage += "\n";
         }

         if (error instanceof RuleProcessingMessage)
         {
            RuleProcessingMessage rule = (RuleProcessingMessage) error;
            errorMessage += rule.getMessage() + ": " + error.getRefinementrule() + " basis: " + rule.getBaserule() + " | Error at "
                  + rule.getTggobjectvariable() + "\n";
         }
      }
      if (!errorMessage.equals(""))
      {
         errorMessage = " ------------ PreCompiler Errorlog ------------ \n" + errorMessage + " --------------------------------------";
         logger.error("TGGLanguage Precompiler (Refinements) - " + errorMessage);
      }

      String warningMessage = "";
      for (PrecompileMessage warning : log.getPrecompilewarning())
      {
         if (warning instanceof RuleProcessingMessage)
         {
            RuleProcessingMessage rule = (RuleProcessingMessage) warning;
            warningMessage += rule.getMessage() + ": " + warning.getRefinementrule() + " basis: " + rule.getBaserule() + " | Refining: "
                  + rule.getTggobjectvariable() + "\n";
         }
      }
      if (!errorMessage.equals(""))
      {
         warningMessage = " ------------ PreCompiler Warninglog ------------ \n" + warningMessage + " --------------------------------------";
         logger.warn("TGGLanguage Precompiler (Refinements) - " + warningMessage);
      }
   }
}
