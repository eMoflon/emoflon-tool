package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.ui.AbstractCommandHandler;
import org.moflon.core.ui.UiUtilities;
import org.moflon.core.utilities.LogUtils;
import org.moflon.ide.ui.UIActivator;

public class AddParserAndUnparserHandler extends AbstractCommandHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		IStructuredSelection structuredSelection = null;
		if (selection instanceof IStructuredSelection) {
			structuredSelection = (IStructuredSelection) selection;
		} else if (selection instanceof ITextSelection) {
			final IFile file = getEditedFile(event);
			structuredSelection = new StructuredSelection(file);
		}

		if (structuredSelection != null) {
			try {
				UiUtilities.openWizard(UIActivator.ADD_PARSER_AND_UNPARSER_WIZARD_ID, window, structuredSelection);
			} catch (final CoreException e) {
				LogUtils.error(logger, e, "unable to open wizard");
			}
		}

		return null;
	}

}
