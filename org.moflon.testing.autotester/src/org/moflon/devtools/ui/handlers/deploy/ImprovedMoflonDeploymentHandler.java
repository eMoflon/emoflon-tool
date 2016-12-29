package org.moflon.devtools.ui.handlers.deploy;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetPlatformService;
import org.moflon.autotest.AutoTestActivator;
import org.moflon.autotest.core.DeploymentJob;

public class ImprovedMoflonDeploymentHandler extends DeploymentHandler
{

   @Override
   protected void performDeploy(final String destinationDirectory)
         throws ExecutionException
   {
      assert destinationDirectory != null;
      try
      {
         // Setup Moflon target definition
         final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("MoflonIdeUpdateSite");
         if (project.isAccessible())
         {
            final DeploymentJob deploymentController = new DeploymentJob(destinationDirectory);
            final IFile moflonTargetDefinitionFile = project.getFile("moflon.target");
            if (moflonTargetDefinitionFile.isAccessible())
            {
               final ITargetPlatformService service = AutoTestActivator.getDefault().getTargetPlatformService();
               final ITargetDefinition oldTarget = service.getWorkspaceTargetDefinition();
               final ITargetDefinition newTarget = AutoTestActivator.getDefault().getMoflonTargetDefinition();

               if (!oldTarget.getHandle().equals(newTarget.getHandle()))
               {
                  Job.getJobManager().cancel("LoadTargetDefinitionJob");
                  final Job loadNewTargetJob = (Job) Platform.getAdapterManager().loadAdapter(newTarget, "org.eclipse.pde.core.target.LoadTargetDefinitionJob");
                  final ProvisioningUI ui = AutoTestActivator.getDefault().getProvisioningUI();
                  final Job updateSiteReloadingJob = (Job) Platform.getAdapterManager().loadAdapter(ui, "org.moflon.eclipse.pde.fragment.UpdateSiteReloadJob");
                  loadNewTargetJob.addJobChangeListener(new SequencingJobAdapter(updateSiteReloadingJob));
                  updateSiteReloadingJob.addJobChangeListener(new SequencingJobAdapter(deploymentController));
                  loadNewTargetJob.schedule();
               } else
               {
                  deploymentController.schedule();
               }
            } else
            {
               deploymentController.schedule();
            }
         }
      } catch (CoreException e)
      {
         throw new ExecutionException(e.getMessage(), e);
      }
   }

   private static class SequencingJobAdapter extends JobChangeAdapter
   {
      private final Job nextJob;

      private SequencingJobAdapter(final Job nextJob)
      {
         this.nextJob = nextJob;
      }

      @Override
      public void done(final IJobChangeEvent event)
      {
         if (event.getResult().isOK())
         {
            nextJob.schedule();
         }
      }
   }

}
