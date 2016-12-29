package org.moflon.ide.core.runtime.builders;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.ErrorReporter;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.preferences.EMoflonPreferencesStorage;
import org.moflon.ide.core.runtime.CleanVisitor;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.util.plugins.manifest.ExportedPackagesInManifestUpdater;
import org.moflon.util.plugins.manifest.PluginXmlUpdater;

public class RepositoryBuilder extends AbstractVisitorBuilder
{
   public static final Logger logger = Logger.getLogger(RepositoryBuilder.class);

   protected boolean generateSDMs = true;

   public RepositoryBuilder()
   {
      super(new AntPatternCondition(new String[] { "model/*.ecore" }));
   }

   @Override
   public ISchedulingRule getRule(final int kind, final Map<String, String> args)
   {
      return getProject();
   }

   @Override
   protected void processResource(final IResource ecoreResource, final int kind, Map<String, String> args, final IProgressMonitor monitor)
   {
      if (isEcoreFile(ecoreResource))
      {
         final IFile ecoreFile = Platform.getAdapterManager().getAdapter(ecoreResource, IFile.class);
         try
         {
            final SubMonitor subMon = SubMonitor.convert(monitor, "Generating code for project " + getProject().getName(), 13);

            final IProject project = getProject();
            MoflonProjectCreator.createFoldersIfNecessary(project, subMon.split(1));
            MoflonProjectCreator.addGitignoreFileForRepositoryProject(project, subMon.split(1));
            MoflonProjectCreator.addGitKeepFiles(project, subMon.split(1));

            // Compute project dependencies
            final IBuildConfiguration[] referencedBuildConfigs = project.getReferencedBuildConfigs(project.getActiveBuildConfig().getName(), false);
            for (final IBuildConfiguration referencedConfig : referencedBuildConfigs)
            {
               addTriggerProject(referencedConfig.getProject());
            }

            // Remove markers and delete generated code
            deleteProblemMarkers();
            final CleanVisitor cleanVisitor = new CleanVisitor(project, //
                  new AntPatternCondition(new String[] { "gen/**" }), //
                  new AntPatternCondition(new String[] { "gen/.keep*" }));
            project.accept(cleanVisitor, IResource.DEPTH_INFINITE, IResource.NONE);

            // Build
            final ResourceSet resourceSet = CodeGeneratorPlugin.createDefaultResourceSet();
            eMoflonEMFUtil.installCrossReferencers(resourceSet);
            subMon.worked(1);

            final MoflonCodeGenerator codeGenerationTask = new MoflonCodeGenerator(ecoreFile, resourceSet);
            codeGenerationTask.setValidationTimeout(EMoflonPreferencesStorage.getInstance().getValidationTimeout());

            final IStatus status = codeGenerationTask.run(subMon.split(1));
            handleErrorsAndWarnings(status, ecoreFile);
            subMon.worked(3);

            final GenModel genModel = codeGenerationTask.getGenModel();
            if (genModel != null)
            {
               ExportedPackagesInManifestUpdater.updateExportedPackageInManifest(project, genModel);

               PluginXmlUpdater.updatePluginXml(project, genModel, subMon.split(1));
               ResourcesPlugin.getWorkspace().checkpoint(false);
            }

         } catch (final CoreException e)
         {
            final IStatus status = new Status(e.getStatus().getSeverity(), WorkspaceHelper.getPluginId(getClass()), e.getMessage(), e);
            handleErrorsInEclipse(status, ecoreFile);
         }
      }
   }

   protected boolean isEcoreFile(final IResource ecoreResource)
   {
      return ecoreResource.getType() == IResource.FILE && "ecore".equals(ecoreResource.getFileExtension());
   }

   @Override
   protected final AntPatternCondition getTriggerCondition(final IProject project)
   {
      try
      {
         if (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) || project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))
         {
            return new AntPatternCondition(new String[] { "gen/**" });
         } else if (project.hasNature(WorkspaceHelper.METAMODEL_NATURE_ID))
         {
            return new AntPatternCondition(new String[] { ".temp/*.moca.xmi" });
         }
      } catch (final CoreException e)
      {
         // Do nothing
      }
      return new AntPatternCondition(new String[0]);
   }

   public void clean(final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Cleaning " + getProject(), 4);

      final IProject project = getProject();

      // Remove all problem markers
      deleteProblemMarkers();
      subMon.worked(1);

      final CleanVisitor cleanVisitor = new CleanVisitor(getProject(), //
            new AntPatternCondition(new String[] { "gen/**", "debug/**" }), //
            new AntPatternCondition(new String[] { "gen/.keep*" }));
      // Remove generated code
      project.accept(cleanVisitor, IResource.DEPTH_INFINITE, IResource.NONE);

      // Remove generated model files
      cleanModels(WorkspaceHelper.getModelFolder(project), subMon.split(1));
   }

   // Delete generated models within model folder
   private void cleanModels(final IFolder folder, final IProgressMonitor monitor) throws CoreException
   {
      if (!folder.exists())
      {
         return;
      }

      final SubMonitor subMon = SubMonitor.convert(monitor, "Inspecting " + folder.getName(), folder.members().length);
      for (final IResource resource : folder.members())
      {
         // keep SVN data
         if (!resource.getName().startsWith("."))
         {
            // only delete generated models directly in folder 'model'
            if (!WorkspaceHelper.isFolder(resource))
            {
               MoflonPropertiesContainer properties = MoflonPropertiesContainerHelper.load(getProject(), subMon.split(1));
               if (properties.getReplaceGenModel().isBool() && resource.getName().endsWith(WorkspaceHelper.GEN_MODEL_EXT))
               {
                  resource.delete(true, subMon.split(1));
               } else
               {
                  monitor.worked(1);
               }

               if (WorkspaceHelper.isIntegrationProject(getProject()) && isAGeneratedFileInIntegrationProject(resource))
               {
                  resource.delete(true, subMon.split(1));
               } else
               {
                  monitor.worked(1);
               }
            }
         } else
         {
            monitor.worked(1);
         }
      }
   }

   private boolean isAGeneratedFileInIntegrationProject(final IResource resource)
   {
      return !(resource.getName().endsWith(WorkspaceHelper.PRE_ECORE_FILE_EXTENSION) || resource.getName().endsWith(WorkspaceHelper.PRE_TGG_FILE_EXTENSION));
   }

   /**
    * Handles errors and warning produced by the code generation task
    * 
    * @param status the {@link IStatus} that contains the errors and warnings
    */
   protected void handleErrorsAndWarnings(final IStatus status, final IFile ecoreFile) throws CoreException
   {
      if (indicatesThatValidationCrashed(status))
      {
         throw new CoreException(new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), status.getMessage(), status.getException().getCause()));
      }
      if (status.matches(IStatus.ERROR))
      {
         handleErrorsInEA(status);
         handleErrorsInEclipse(status, ecoreFile);
      }
      if (status.matches(IStatus.WARNING))
      {
         handleInjectionWarningsAndErrors(status);
      }
   }

   private boolean indicatesThatValidationCrashed(IStatus status)
   {
      return status.getException() != null;
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

   public void handleErrorsInEclipse(final IStatus status, final IFile ecoreFile)
   {
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

   public void handleErrorsInEA(final IStatus status)
   {
      final String reporterClass = "org.moflon.validation.EnterpriseArchitectValidationHelper";
      final ErrorReporter errorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(getProject(), reporterClass);
      if (errorReporter != null)
      {
         errorReporter.report(status);
      } else
      {
         logger.debug("Could not load error reporter '" + reporterClass + "'");
      }
   }
}
