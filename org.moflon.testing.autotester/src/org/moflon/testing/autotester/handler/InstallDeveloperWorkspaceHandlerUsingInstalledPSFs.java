package org.moflon.testing.autotester.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;

public class InstallDeveloperWorkspaceHandlerUsingInstalledPSFs extends AbstractInstallCommandHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		this.getWorkspaceController().installWorkspaceByName(EMoflonStandardWorkspaces.OLD_DEVELOPER_WORKSPACE_NAME);
		return null;
	}

}
