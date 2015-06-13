package org.moflon.compiler.sdm.democles.eclipse;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.ErrorReporter;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.eclipse.job.IMonitoredJob;

import MoflonPropertyContainer.MoflonPropertiesContainer;

public class MonitoredSDMValidator implements IMonitoredJob
{
   private static final Logger logger = Logger.getLogger(MonitoredSDMValidator.class);

   public static final String TASK_NAME = "SDM validation";

   private final IFile ecoreFile;

   public MonitoredSDMValidator(final IFile ecoreFile)
   {
      this.ecoreFile = ecoreFile;
   }

   @Override
   public IStatus run(final IProgressMonitor monitor)
   {
      try
      {
         monitor.beginTask(TASK_NAME + " task", 100);
         logger.info("Validating Ecore file '" + ecoreFile.getProjectRelativePath() + "'.");

         // Delete markers
         ecoreFile.deleteMarkers(WorkspaceHelper.MOSL_PROBLEM_MARKER_ID, true, IResource.DEPTH_INFINITE);
         final IProject project = ecoreFile.getProject();

         ResourceSet resourceSet = CodeGeneratorPlugin.createDefaultResourceSet();
         DemoclesMethodBodyHandler.initResourceSetForDemocles(resourceSet);

         monitor.subTask("Validating SDMs for project " + project.getName());
         DemoclesValidationProcess validationProcess = new DemoclesValidationProcess(ecoreFile, resourceSet);
         IStatus validationStatus = validationProcess.run(WorkspaceHelper.createSubMonitor(monitor, 50));

         if (!validationStatus.isOK())
         {
            handlePropertiesInEnterpriseArchitect(validationStatus, validationProcess.getMoflonProperties());
            monitor.worked(25);
            
            handleErrorsInEclipse(validationStatus);
            monitor.worked(25);
         }

         logger.info("Validation of Ecore file '" + ecoreFile.getProjectRelativePath() + "' complete.");

         return validationStatus.isOK() ? new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), TASK_NAME + " succeeded") : validationStatus;
      } catch (RuntimeException e)
      {
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR,
               "Internal exception occured (probably caused by a bug in the validation module): " + e.getMessage() + " Please report the bug on "
                     + WorkspaceHelper.ISSUE_TRACKER_URL, e);
      } catch (CoreException e)
      {
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR, e.getMessage(), e);
      } finally
      {
         monitor.done();
      }
   }

   public void handlePropertiesInEnterpriseArchitect(final IStatus validationStatus, final MoflonPropertiesContainer moflonProperties)
   {
      IProject metamodelProject = ResourcesPlugin.getWorkspace().getRoot().getProject(moflonProperties.getMetaModelProject().getMetaModelProjectName());
      ErrorReporter errorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(metamodelProject,
            "org.moflon.validation.EnterpriseArchitectValidationHelper");
      if (errorReporter != null)
      {
         errorReporter.report(validationStatus);
      }
   }

   public void handleErrorsInEclipse(final IStatus validationStatus)
   {
      ErrorReporter eclipseErrorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(ecoreFile,
            "org.moflon.compiler.sdm.democles.eclipse.EclipseErrorReporter");
      if (eclipseErrorReporter != null)
      {
         eclipseErrorReporter.report(validationStatus);
      }
   }

   @Override
   public final String getTaskName()
   {
      return TASK_NAME + " on project " + ecoreFile.getProject().getName();
   }

   

}
