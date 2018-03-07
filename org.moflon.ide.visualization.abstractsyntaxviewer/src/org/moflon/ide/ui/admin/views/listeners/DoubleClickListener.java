package org.moflon.ide.ui.admin.views.listeners;

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;

public class DoubleClickListener implements IDoubleClickListener {

	@SuppressWarnings("unchecked")
	@Override
	public void doubleClick(DoubleClickEvent event) {
		ISelection selection = event.getSelection();
		Viewer viewer = event.getViewer();
		if (selection instanceof IStructuredSelection && viewer instanceof GraphViewer) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			// TODO Refactor and unify with MenuListener coding (currently simply code copy)
			LinkedHashSet<Object> firstChildren = new LinkedHashSet<Object>();
			LinkedHashSet<Object> currentInput = new LinkedHashSet<Object>(Arrays.asList((Object[]) viewer.getInput()));

			structuredSelection.toList().forEach(s -> {
				MenuListener.calculateFirstChilden((Object) s, firstChildren);
			});
			currentInput.addAll(firstChildren);
			viewer.setInput(currentInput.toArray());
		}
	}

}
