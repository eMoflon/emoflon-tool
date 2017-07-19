package org.moflon.gt.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.LogUtils;
import org.moflon.gt.ui.wizards.NewMOSLGTProjectWizard;
import org.moflon.ide.ui.UIActivator;
import org.moflon.ide.ui.admin.handlers.AbstractCommandHandler;

public class NewMOSLGTProjectHandler extends AbstractCommandHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		try {
			UIActivator.openWizard(NewMOSLGTProjectWizard.NEW_MOSLGT_PROJECT_WIZARD_ID, window);
		} catch (final Exception e) {
         LogUtils.error(logger, e, "Unable to open 'New MOSL-GT Project' wizard.");
		}

		return null;
	}

}