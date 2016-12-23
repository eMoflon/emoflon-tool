package org.moflon.ide.core.runtime.builders;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.gervarro.eclipse.workspace.util.RelevantElementCollector;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.core.utilities.ErrorReporter;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.preferences.EMoflonPreferencesStorage;
import org.moflon.ide.core.runtime.CleanVisitor;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.util.plugins.manifest.ExportedPackagesInManifestUpdater;
import org.moflon.util.plugins.manifest.PluginXmlUpdater;

public class MOSLGTBuilder extends AbstractVisitorBuilder
{
   public static final Logger logger = Logger.getLogger(MOSLGTBuilder.class);

   /**
    * Specification of files whose changes will trigger in invocation of this builder
    */
   private static final String[] PROJECT_INTERNAL_TRIGGERS = new String[] { "src/**/*.mgt", "model/*.ecore" };

   public MOSLGTBuilder()
   {
      super(new AntPatternCondition(PROJECT_INTERNAL_TRIGGERS));
   }

   @Override
   protected AntPatternCondition getTriggerCondition(final IProject project)
   {
      try
      {
         if (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) || project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))
         {
            return new AntPatternCondition(new String[] { "gen/**" });
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

   @Override
   protected void processResource(final IResource resource, final int kind, final Map<String, String> args, final IProgressMonitor monitor)
   {
      //TODO@rkluge: Steps of this builder (wrap them into something similar to the 
      /*
       * 1. Read input Ecore file
       * 2. Read CF model from .mgt files
       * 3. Connect .mgt files to EOperations
       * 4. Trigger code generation
       * 
       * Gather parts from 
       * * RepositoryBuilder
       * * MoslTGGBuilder
       */
      final IFile ecoreFile = Platform.getAdapterManager().getAdapter(resource, IFile.class);
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Generating code for project " + getProject().getName(), 10);

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
         subMon.worked(1);

         // Remove markers and delete generated code
         deleteProblemMarkers();
         final CleanVisitor cleanVisitor = new CleanVisitor(project, //
               new AntPatternCondition(new String[] { "gen/**" }), //
               new AntPatternCondition(new String[] { "gen/.keep*" }));
         project.accept(cleanVisitor, IResource.DEPTH_INFINITE, IResource.NONE);
         subMon.worked(1);

         // Build
         final ResourceSet resourceSet = CodeGeneratorPlugin.createDefaultResourceSet();
         eMoflonEMFUtil.installCrossReferencers(resourceSet);
         subMon.worked(1);

         final MoflonCodeGenerator codeGenerationTask = new MoflonCodeGenerator(ecoreFile, resourceSet);
         codeGenerationTask.setValidationTimeout(EMoflonPreferencesStorage.getInstance().getValidationTimeout());

         final IStatus status = codeGenerationTask.run(subMon.split(1));
         handleErrorsAndWarnings(status, ecoreFile);
         subMon.worked(3);

         postprocessAfterCodeGeneration(codeGenerationTask.getGenModel(), subMon.split(1));
      } catch (final CoreException e)
      {
         final IStatus status = new Status(e.getStatus().getSeverity(), CoreActivator.getModuleID(), e.getMessage(), e);
         handleErrorsInEclipse(status, ecoreFile);
      }
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

   // The following code is duplicated from RepositoryBuilder to keep dependencies minimal

   private boolean indicatesThatValidationCrashed(IStatus status)
   {
      return status.getException() != null;
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
         handleErrorsInEclipse(status, ecoreFile);
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

}
