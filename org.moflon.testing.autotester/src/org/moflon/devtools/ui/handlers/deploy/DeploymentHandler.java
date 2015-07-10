package org.moflon.devtools.ui.handlers.deploy;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.autotest.AutoTestActivator;
import org.moflon.autotest.core.DeploymentJob;
import org.moflon.devtools.ui.handlers.AbstractCommandHandler;
import org.moflon.ide.deployment.ui.DeploymentDialog;

public class DeploymentHandler extends AbstractCommandHandler
{

   public static final String LOCAL_DEPLOY_PATH_PROPERTY = "org.moflon.ide.deployment.deploymentpath";

   private static final String DEFAULT_LOCAL_DEPLOY_PATH = ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString();

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

      DeploymentDialog dialog = new DeploymentDialog(window.getShell());
      dialog.setDeploymentDirectory(getDefaultDeploymentPath());
      int exitCode = dialog.open();

      switch (exitCode)
      {
      case Dialog.OK:
         String selectedDirectoryPath = dialog.getSelectedDirectory();
         File targetDirectory = new File(selectedDirectoryPath);
         if (!targetDirectory.exists())
         {
            final boolean successful = targetDirectory.mkdirs();
            if (!successful)
               throw new ExecutionException("Failed to create target directory. Try to create it manually");
         }

         setDefaultDeploymentPath(selectedDirectoryPath);

         performDeploy(selectedDirectoryPath);

         break;
      case Dialog.CANCEL:
         logger.info("Deployment aborted by user");
         break;
      default:
         break;
      }

      return null;
   }

   protected void performDeploy(final String destinationDirectory) throws ExecutionException
   {
      DeploymentJob deploymentJob = new DeploymentJob(destinationDirectory);
      deploymentJob.schedule();
   }

   /*
    * Returns the previously stored default path to which the deployed data is stored
    */
   private String getDefaultDeploymentPath()
   {
      return InstanceScope.INSTANCE.getNode(AutoTestActivator.getModuleID()).get(LOCAL_DEPLOY_PATH_PROPERTY, DEFAULT_LOCAL_DEPLOY_PATH);
   }

   private void setDefaultDeploymentPath(final String defaultPath)
   {
      InstanceScope.INSTANCE.getNode(AutoTestActivator.getModuleID()).put(LOCAL_DEPLOY_PATH_PROPERTY, defaultPath);
   }

}
