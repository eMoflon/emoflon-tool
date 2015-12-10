package org.moflon.devtools.ui.handlers.install;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.autotest.core.WorkspaceInstaller;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.ui.preferences.EMoflonPreferenceInitializer;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;
import org.moflon.ide.workspaceinstaller.psf.PSFPlugin;

public class InstallDeveloperWorkspaceHandlerUsingCurrentPSFs extends AbstractInstallCommandHandler
{
   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(HandlerUtil.getActiveShell(event), "Install Dev. Workspace (up-to-date)",
            "The current version of the project set (PSF) file of the developer workspace is used for this operation. " //
                  + "The plugin project " + PSFPlugin.getDefault().getPluginId()
                  + " is removed from the workspace first and then downloaded freshly. The contained PSF files are then used to configure the dev. workspace. ",
            null, false, EMoflonPreferenceInitializer.getPreferencesStore(), EMoflonPreferenceInitializer.INSTALL_DEVELOPER_WORKSPACE_KEY);
      if (dialog.getReturnCode() == Window.OK)
      {
         WorkspaceInstaller.removeEmptyMoflonWorkingSets();
         IProject psfFileProject = WorkspaceHelper.getProjectByPluginId(PSFPlugin.getDefault().getPluginId());
         if (psfFileProject != null)
         {
            try
            {
               psfFileProject.delete(true, true, null);
            } catch (CoreException e)
            {
               throw new ExecutionException("Failed to delete project " + psfFileProject, e);
            }
         }
         this.getWorkspaceController(event).installWorkspaceByName(EMoflonStandardWorkspaces.PSFS_ONLY_NAME);
         this.getWorkspaceController(event).installWorkspaceByName(EMoflonStandardWorkspaces.DEVELOPER_WORKSPACE_NAME);
      }
      return null;
   }

}
