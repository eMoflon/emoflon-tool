/**
 * 
 */
package org.moflon.ide.ui.admin.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.ecore2mocaxmi.utils.ConverterHelper;

import MocaTree.Node;

/**
 * @author ah70bafa
 *
 */
public class ExportToEAPHandler extends AbstractCommandHandler {

	final static private String ECORE_FILE_EXSTENSION = "ecore";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		return execute(HandlerUtil.getCurrentSelectionChecked(event), event);
	}

	private Object execute(final ISelection selection, final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String filePath = null;
		IFile ecoreFile = null;
		String ecoreFileName = null;
		Node eaTree = null;
		if (selection instanceof TreeSelection) {
			final TreeSelection treeSelection = (TreeSelection) selection;
			for (final Iterator<?> selectionIterator = treeSelection.iterator(); selectionIterator.hasNext();) {
				final Object element = selectionIterator.next();
				if (element != null && element instanceof IFile
						&& IFile.class.cast(element).getName().endsWith("." + ECORE_FILE_EXSTENSION)) {
					ecoreFile = IFile.class.cast(element);
					eaTree = ConverterHelper.getEATree(ecoreFile);
					ecoreFileName = ecoreFile.getName();
					break;
				}
			}
		}
		try {
			filePath = ConverterHelper.getEAPFilePath(window.getShell(), ecoreFile.getParent().getLocation().toString())
					.replace('\\', '/');
		} catch (NullPointerException npe) {
			return null;
		}
		if (filePath != null && eaTree != null) {
			final Job job = new ExportToEAPJob(filePath, ecoreFileName, eaTree);
			job.setUser(true);
			job.schedule();
		}
		return null;
	}

}
