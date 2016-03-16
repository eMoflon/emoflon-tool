package org.moflon.ide.ui.admin.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.preferences.EMoflonPreferencesStorage;
import org.moflon.ide.core.util.BuilderHelper;
import org.moflon.ide.ui.UIActivator;
import org.moflon.ide.ui.preferences.EMoflonPreferenceInitializer;

public class EMoflonBuildJob extends WorkspaceJob
{
   private final Logger logger;

   private final List<IProject> projects;

   public EMoflonBuildJob(final String name, final List<IProject> projects)
   {
      super(name);
      this.logger = Logger.getLogger(this.getClass());
      this.projects = projects;
   }

   @Override
   public IStatus runInWorkspace(final IProgressMonitor monitor)
   {
      try
      {
         final MultiStatus resultStatus = new MultiStatus(UIActivator.getModuleID(), 0, "eMoflon Build Job failed", null);
         final List<IProject> projectsToBeBuilt = this.projects.stream().filter(project -> shallBuildProject(project)).collect(Collectors.toList());
         
         monitor.beginTask("eMoflon Build Job", 2 * projectsToBeBuilt.size());

         updateUserSelectedTimeoutForValidation();

         for (final IProject project : projectsToBeBuilt)
         {

            final IStatus projectBuildStatus = cleanAndBuild(project, WorkspaceHelper.createSubMonitor(monitor, 2));
            if (!projectBuildStatus.isOK())
            {
               resultStatus.add(projectBuildStatus);
            }
         }

         final IStatus codeGenerationStatus = BuilderHelper.generateCodeInOrder(WorkspaceHelper.createSubMonitor(monitor, projectsToBeBuilt.size()), projectsToBeBuilt);
         if (!codeGenerationStatus.isOK())
         {
            resultStatus.add(codeGenerationStatus);
         }
         
         return resultStatus.matches(Status.ERROR) ? resultStatus : Status.OK_STATUS;
      } finally
      {
         monitor.done();
      }

   }

   private void updateUserSelectedTimeoutForValidation()
   {
      EMoflonPreferencesStorage.getInstance().setValidationTimeout(EMoflonPreferenceInitializer.getValidationTimeoutMillis());
   }

   private IStatus cleanAndBuild(final IProject project, final IProgressMonitor monitor)
   {
      IStatus status = Status.OK_STATUS;
      try
      {
         monitor.beginTask("Clean and build of " + project.getName(), 2);

         if (project != null && WorkspaceHelper.isMoflonOrMetamodelProject(project))
         {

            logger.info("Cleaning project " + project.getName() + " - triggered manually!");

            project.build(IncrementalProjectBuilder.CLEAN_BUILD, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            project.build(IncrementalProjectBuilder.FULL_BUILD, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

            logger.debug("Cleaning project " + project.getName() + " done.");

         }
      } catch (final OperationCanceledException e)
      {
         status = new Status(IStatus.CANCEL, UIActivator.getModuleID(), "", null);
      } catch (final CoreException e)
      {
         status = new Status(IStatus.ERROR, UIActivator.getModuleID(), "Problem during clean and build: " + e.getMessage(), e);
      } finally
      {
         monitor.done();
      }
      return status;
   }

   /**
    * Returns true if the given project should be rebuilt
    */
   protected boolean shallBuildProject(final IProject project)
   {
      return true;
   }
}