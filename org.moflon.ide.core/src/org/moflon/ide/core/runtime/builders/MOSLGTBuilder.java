package org.moflon.ide.core.runtime.builders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.gervarro.eclipse.workspace.util.RelevantElementCollector;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.core.utilities.ErrorReporter;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.gt.mosl.MOSLGTStandaloneSetupGenerated;
import org.moflon.gt.mosl.codeadapter.tie.CodeadapterTrafo;
import org.moflon.gt.mosl.moslgt.GraphTransformationFile;
import org.moflon.ide.core.preferences.EMoflonPreferencesStorage;
import org.moflon.ide.core.runtime.CleanVisitor;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.util.plugins.manifest.ExportedPackagesInManifestUpdater;
import org.moflon.util.plugins.manifest.PluginXmlUpdater;

import com.google.inject.Injector;

/**
 * This builder triggers the build process for MOSL-GT projects
 * 
 * The main parts of such projects are 
 * * A plain Ecore file that describes the structure of a metamodel
 * * A set of MOSL-GT files (*.mgt), which specify the implementation of operations in a platform-independent way
 * * A set of injection files (*.inject), which store the  implementation of operations in Java
 *   
 * @author Roland Kluge - Initial implementation
 *
 */
public class MOSLGTBuilder extends AbstractVisitorBuilder
{

   private static final Logger logger = Logger.getLogger(MOSLGTBuilder.class);

   /**
    * Specification of files whose changes will trigger the invocation of this builder
    */
   private static final String[] PROJECT_INTERNAL_TRIGGERS = new String[] { "src/**/*." + WorkspaceHelper.MOSL_GT_EXTENSION, "model/*.ecore" };

   private static final String[] PROJECT_EXTERNAL_TRIGGERS = new String[] { "gen/**" };

   private XtextResourceSet resourceSet;

   public MOSLGTBuilder()
   {
      super(new AntPatternCondition(PROJECT_INTERNAL_TRIGGERS));
   }

   @Override
   protected void processResource(final IResource resource, final int kind, final Map<String, String> args, final IProgressMonitor monitor)
   {
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Generating code for project " + getProject().getName(), 20);

         updateProjectStructure(subMon.split(1));

         computeBuildDependencies(subMon.split(1));

         performClean(subMon.split(1));

         generateCode(subMon.split(17));
      } catch (final CoreException e)
      {
         final IStatus status = new Status(e.getStatus().getSeverity(), WorkspaceHelper.getPluginId(getClass()), e.getMessage(), e);
         handleErrorsInEclipse(status);
      }
   }

   @Override
   protected AntPatternCondition getTriggerCondition(final IProject project)
   {
      try
      {
         if (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) || project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))
         {
            return new AntPatternCondition(PROJECT_EXTERNAL_TRIGGERS);
         }
      } catch (final CoreException e)
      {
         // Do nothing
      }
      return new AntPatternCondition(new String[0]);
   }

   @Override
   protected void postprocess(RelevantElementCollector buildVisitor, int originalKind, Map<String, String> args, IProgressMonitor monitor)
   {
      final int kind = correctBuildTrigger(originalKind);

      if (getCommand().isBuilding(kind))
      {
         super.postprocess(buildVisitor, kind, args, monitor);
      }
   }

   public ResourceSet getResourceSet()
   {
      return resourceSet;
   }

   private void generateCode(final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Generate code", 10);
      subMon.worked(1);
      initializeResourceSet();

      loadMGTFiles();

      final MoflonCodeGenerator codeGenerationTask = new MoflonCodeGenerator(WorkspaceHelper.getDefaultEcoreFile(getProject()), resourceSet);
      codeGenerationTask.setValidationTimeout(EMoflonPreferencesStorage.getInstance().getValidationTimeout());

      final IStatus status = codeGenerationTask.run(subMon.split(7));

      handleErrorsAndWarnings(status);
      subMon.worked(2);

      postprocessAfterCodeGeneration(codeGenerationTask.getGenModel(), subMon.split(1));
   }

   private void initializeResourceSet()
   {
      // See also: https://wiki.eclipse.org/Xtext/FAQ#How_do_I_load_my_model_in_a_standalone_Java_application.C2.A0.3F
      Injector injector = new MOSLGTStandaloneSetupGenerated().createInjectorAndDoEMFRegistration();
      this.resourceSet = injector.getInstance(XtextResourceSet.class);
      //eMoflonEMFUtil.initializeDefault(this.resourceSet);
      this.resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
      eMoflonEMFUtil.installCrossReferencers(this.resourceSet);
   }

   /**
    * Adds all MOSL-GT files in this project to the resource set (see {@link #getResourceSet()}) 
    */
   private IStatus loadMGTFiles()
   {
      try
      {
         CodeadapterTrafo helper = new CodeadapterTrafo(
               URI.createPlatformPluginURI(WorkspaceHelper.getPluginId(CodeadapterTrafo.class) + "/model/Codeadapter.sma.xmi", true), getResourceSet());
         for (final IFile moslGTFile : collectMOSLGTFiles())
         {
            Resource schemaResource = (Resource) this.getResourceSet()
                  .createResource(URI.createPlatformResourceURI(moslGTFile.getFullPath().toString(), false));
            schemaResource.load(null);
            final GraphTransformationFile gtf = GraphTransformationFile.class.cast(schemaResource.getContents().get(0));
            helper.setSrc(gtf);
            helper.setVerbose(false);
            helper.integrateForward();
            
            //TODO@szander: Need to add postprocessing (as in MOSLTGGConversionHelper:120)
         }
         EcoreUtil.resolveAll(this.getResourceSet());
      } catch (IOException | CoreException e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problems while loading MOSL-GT specification", e);
      }
      return Status.OK_STATUS;
   }

   private Collection<IFile> collectMOSLGTFiles() throws CoreException
   {
      final Collection<IFile> moslGTFiles = new ArrayList<>();
      getProject().accept(new IResourceVisitor() {

         @Override
         public boolean visit(IResource resource) throws CoreException
         {
            if (isMOSLGTFile(resource))
               moslGTFiles.add(IFile.class.cast(resource));
            return true;
         }

         private boolean isMOSLGTFile(IResource resource)
         {
            return resource != null && resource.exists() && resource instanceof IFile
                  && Arrays.asList(resource.getFullPath().segments()).stream().anyMatch(s -> "src".equals(s))
                  && resource.getName().endsWith("." + WorkspaceHelper.MOSL_GT_EXTENSION);
         }
      });
      return moslGTFiles;
   }

   private IProject updateProjectStructure(final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Update project structure", 3);
      final IProject project = getProject();

      MoflonProjectCreator.createFoldersIfNecessary(project, subMon.split(1));
      MoflonProjectCreator.addGitignoreFileForRepositoryProject(project, subMon.split(1));
      MoflonProjectCreator.addGitKeepFiles(project, subMon.split(1));

      return project;
   }

   private void performClean(final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Perform clean", 2);
      final IProject project = getProject();

      deleteProblemMarkers();
      subMon.worked(1);

      final CleanVisitor cleanVisitor = new CleanVisitor(project, //
            new AntPatternCondition(new String[] { "gen/**" }), //
            new AntPatternCondition(new String[] { "gen/.keep*" }));

      project.accept(cleanVisitor, IResource.DEPTH_INFINITE, IResource.NONE);

      subMon.worked(1);
   }

   private void computeBuildDependencies(IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Registering build dependencies", 1);
      final IProject project = getProject();
      final IBuildConfiguration[] referencedBuildConfigs = project.getReferencedBuildConfigs(project.getActiveBuildConfig().getName(), false);
      for (final IBuildConfiguration referencedConfig : referencedBuildConfigs)
      {
         addTriggerProject(referencedConfig.getProject());
      }
      subMon.worked(1);
   }

   /**
    * Updates resources in the project after completing the code generation
    * @param genModel
    * @throws CoreException
    */
   private void postprocessAfterCodeGeneration(final GenModel genModel, final IProgressMonitor monitor) throws CoreException
   {
      if (genModel != null)
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Postprocessing after code generation", 2);
         IProject project = getProject();
         ExportedPackagesInManifestUpdater.updateExportedPackageInManifest(project, genModel);
         subMon.worked(1);

         PluginXmlUpdater.updatePluginXml(project, genModel, subMon.split(1));
         ResourcesPlugin.getWorkspace().checkpoint(false);
      }
   }

   private boolean indicatesThatValidationCrashed(IStatus status)
   {
      return status.getException() != null;
   }

   /**
    * Handles errors and warning produced by the code generation task
    * 
    * @param status the {@link IStatus} that contains the errors and warnings
    */
   private void handleErrorsAndWarnings(final IStatus status) throws CoreException
   {
      if (indicatesThatValidationCrashed(status))
      {
         throw new CoreException(new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), status.getMessage(), status.getException().getCause()));
      }

      if (status.matches(IStatus.ERROR))
      {
         handleErrorsInEclipse(status);
      }

      if (status.matches(IStatus.WARNING))
      {
         handleInjectionWarningsAndErrors(status);
      }
   }

   private void handleInjectionWarningsAndErrors(final IStatus status)
   {
      final String reporterClass = "org.moflon.moca.inject.validation.InjectionErrorReporter";
      final ErrorReporter errorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(getProject(), reporterClass);
      if (errorReporter != null)
      {
         errorReporter.report(status);
      } else
      {
         logger.debug("Could not load error reporter '" + reporterClass + "'");
      }
   }

   public void handleErrorsInEclipse(final IStatus status)
   {
      final IFile ecoreFile = WorkspaceHelper.getDefaultEcoreFile(getProject());
      final String reporterClass = "org.moflon.compiler.sdm.democles.eclipse.EclipseErrorReporter";
      final ErrorReporter eclipseErrorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(ecoreFile, reporterClass);
      if (eclipseErrorReporter != null)
      {
         eclipseErrorReporter.report(status);
      } else
      {
         logger.debug("Could not load error reporter '" + reporterClass + "'");
      }
   }

}
