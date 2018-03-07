package org.moflon.ide.ui.admin.views.listeners;

import java.util.Arrays;
import java.util.LinkedList;

import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.gef4.zest.core.widgets.Graph;
import org.eclipse.gef4.zest.core.widgets.GraphItem;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.moflon.ide.ui.admin.views.selection.adapters.RedrawGraphSelectionAdapter;

public class KeyListener implements org.eclipse.swt.events.KeyListener {

	private Graph graph;

	private Viewer viewer;

	public KeyListener(GraphViewer viewer) {
		this.graph = viewer.getGraphControl();
		this.viewer = viewer;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.keyCode) {
		case java.awt.event.KeyEvent.VK_DELETE:
			while (!graph.getSelection().isEmpty()) {
				// Just hide item
				// ((GraphItem) graph.getSelection().get(0)).dispose();

				// Fully delete item + relayout
				GraphItem gi = (GraphItem) graph.getSelection().get(0);
				LinkedList<Object> currentInput = new LinkedList<Object>(Arrays.asList((Object[]) viewer.getInput()));
				currentInput.remove(gi.getData());

				viewer.setInput(currentInput.toArray());
				gi.dispose();
			}
			break;
		case SWT.F5:
		case java.awt.event.KeyEvent.VK_F5:
			RedrawGraphSelectionAdapter rgsa = new RedrawGraphSelectionAdapter(viewer);
			// perform redraw
			rgsa.widgetSelected(null);
			break;

		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
