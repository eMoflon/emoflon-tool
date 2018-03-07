package org.moflon.ide.ui.admin.views.selection.adapters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class RedrawGraphSelectionAdapter extends SelectionAdapter {
	private Viewer viewer;

	public RedrawGraphSelectionAdapter(Viewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		viewer.setInput(viewer.getInput());
	}
}
