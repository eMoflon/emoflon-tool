package org.moflon.ide.ui.admin.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.ui.AbstractCommandHandler;

public class AbstractMoflonToolCommandHandler extends AbstractCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection<IProject> extractSelectedProjects(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		if (selection instanceof StructuredSelection) {
			return getProjectsFromSelection((IStructuredSelection)selection);
		} else {
			return new ArrayList<>();
		}
	}

	protected List<IFile> extractSelectedFiles(final ExecutionEvent event) throws ExecutionException {
		final List<IFile> files = new ArrayList<>();
		final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		final TreeSelection treeSelection = (TreeSelection) selection;
		for (final Iterator<?> selectionIterator = treeSelection.iterator(); selectionIterator.hasNext();) {
			final Object element = selectionIterator.next();
			if (element instanceof IFile) {
				files.add((IFile) element);
			}
		}
		return files;
	}

}
