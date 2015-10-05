package org.moflon.ide.ui.admin.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.eclipse.job.IMonitoredJob;
import org.moflon.eclipse.job.ProgressMonitoringJob;
import org.moflon.ide.core.util.BuilderHelper;
import org.moflon.ide.ui.UIActivator;

public class EMoflonBuildJob extends Job
{
   private final Logger logger;

   private final IStatus OK_STATUS = new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);

   private final List<IProject> projects;

   private final boolean validateAutomaticallyIfCodeGenerationFailed = true;

   public EMoflonBuildJob(final String name, final List<IProject> projects)
   {
      super(name);
      this.logger = Logger.getLogger(this.getClass());
      this.projects = projects;
   }

   @Override
   protected IStatus run(final IProgressMonitor monitor)
   {
      IStatus status = OK_STATUS;
      final List<IProject> projectsToBeBuilt = this.projects.stream().filter(project -> shallBuildProject(project)).collect(Collectors.toList());

      try
      {
         monitor.beginTask("eMoflon Cleaning", 4 * projectsToBeBuilt.size());

         for (final IProject project : projectsToBeBuilt)
         {
            final IStatus projectBuildStatus = cleanAndBuild(project, WorkspaceHelper.createSubMonitor(monitor, 2));
            if (projectBuildStatus != OK_STATUS)
            {
               status = projectBuildStatus;
            }
         }

         BuilderHelper.generateCodeInOrder(WorkspaceHelper.createSubMonitor(monitor, projectsToBeBuilt.size()), projectsToBeBuilt);
      } catch (final CoreException e)
      {
         logger.error("Unable to Clean and Build: " + MoflonUtil.displayExceptionAsString(e));
         status = new Status(IStatus.ERROR, UIActivator.getModuleID(), IStatus.ERROR, "Error during clean and build", e);

         if (this.validateAutomaticallyIfCodeGenerationFailed)
         {
            for (final IProject project : projectsToBeBuilt)
            {
               final IFile ecoreFile = WorkspaceHelper.getDefaultEcoreFile(project);

               final IMonitoredJob validationTask = (IMonitoredJob) Platform.getAdapterManager().loadAdapter(ecoreFile,
                     "org.moflon.compiler.sdm.democles.eclipse.MonitoredSDMValidator");

               if (validationTask != null)
               {
                  final ProgressMonitoringJob job = new ProgressMonitoringJob(CodeGeneratorPlugin.getModuleID(), validationTask);
                  job.run(WorkspaceHelper.createSubmonitorWith1Tick(monitor));
               }
            }

         } else
         {
            monitor.worked(projectsToBeBuilt.size());
         }

      } finally
      {
         monitor.done();
      }

      return status;
   }

   private IStatus cleanAndBuild(final IProject project, final IProgressMonitor monitor)
   {
      IStatus status = OK_STATUS;
      try
      {
         monitor.beginTask("Clean and build of " + project.getName(), 2);

         if (project != null && WorkspaceHelper.isMoflonOrMetamodelProject(project))
         {

            logger.info("Cleaning project " + project.getName() + " - triggered manually!");

            project.build(IncrementalProjectBuilder.CLEAN_BUILD, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            project.build(IncrementalProjectBuilder.FULL_BUILD, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

            // TODO@rkluge: Test this later
            // if (WorkspaceHelper.isRepositoryProject(project))
            // {
            // project.build(IncrementalProjectBuilder.CLEAN_BUILD, RepositoryBuilder.BUILDER_ID, new HashMap<String,
            // String>(),
            // WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            // project.build(IncrementalProjectBuilder.FULL_BUILD, RepositoryBuilder.BUILDER_ID, new HashMap<String,
            // String>(),
            // WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            // }
            //
            // if (WorkspaceHelper.isIntegrationProject(project))
            // {
            // project.build(IncrementalProjectBuilder.CLEAN_BUILD, IntegrationBuilder.BUILDER_ID, new HashMap<String,
            // String>(),
            // WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            // project.build(IncrementalProjectBuilder.FULL_BUILD, IntegrationBuilder.BUILDER_ID, new HashMap<String,
            // String>(),
            // WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            // }
            //
            // if (WorkspaceHelper.isMetamodelProject(project))
            // {
            // project.build(IncrementalProjectBuilder.CLEAN_BUILD, MetamodelBuilder.BUILDER_ID, new HashMap<String,
            // String>(),
            // WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            // project.build(IncrementalProjectBuilder.FULL_BUILD, MetamodelBuilder.BUILDER_ID, new HashMap<String,
            // String>(),
            // WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            // }

            logger.debug("Cleaning project " + project.getName() + " done.");

         }
      } catch (final OperationCanceledException e)
      {
         status = new Status(IStatus.CANCEL, UIActivator.getModuleID(), IStatus.OK, "", null);
      } catch (final CoreException e)
      {
         status = new Status(IStatus.ERROR, UIActivator.getModuleID(), IStatus.OK, "", e);
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