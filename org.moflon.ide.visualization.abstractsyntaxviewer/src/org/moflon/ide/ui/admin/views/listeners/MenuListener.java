package org.moflon.ide.ui.admin.views.listeners;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.gef4.zest.core.widgets.Graph;
import org.eclipse.gef4.zest.core.widgets.GraphItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.moflon.ide.ui.admin.views.selection.adapters.RedrawGraphSelectionAdapter;

public class MenuListener implements MenuDetectListener {

	private Graph graph;

	private GraphViewer viewer;

	public MenuListener(GraphViewer viewer) {
		this.viewer = viewer;
		this.graph = viewer.getGraphControl();
	}

	private LinkedHashSet<Object> calculateAllChilden(Object root, LinkedHashSet<Object> result) {
		LinkedHashSet<Object> firstChildren = new LinkedHashSet<Object>();
		calculateFirstChilden(root, firstChildren);
		firstChildren.stream().filter(c -> !result.contains(c)).forEach(c -> {
			result.add(c);
			calculateAllChilden(c, result);
		});
		return result;
	}

	/**
	 * Calculates and adds all direct contents of an EObject to a result set.
	 * 
	 * @param root
	 * @param result
	 */
	static void addContents(EObject root, LinkedHashSet<Object> result) {
		result.addAll(root.eContents());
		result.addAll(root.eCrossReferences());
	}

	/**
	 * Calculates the children of a root element and adds it to <i>result</i>.
	 * 
	 * @param root
	 * @param result
	 * @return
	 */
	static LinkedHashSet<Object> calculateFirstChilden(Object root, LinkedHashSet<Object> result) {
		if (root instanceof EObject) {
			EObject eobject = (EObject) root;
			addContents(eobject, result);
		}

		return result;
	}

	@Override
	public void menuDetected(MenuDetectEvent e) {
		Point point = graph.toControl(e.x, e.y);
		IFigure fig = graph.getFigureAt(point.x, point.y);

		if (fig != null) {
			Menu menu = new Menu(new Shell(), SWT.POP_UP);
			MenuItem expandOne = new MenuItem(menu, SWT.NONE);
			expandOne.setText("-(1)->");
			expandOne.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					LinkedHashSet<Object> currentInput = new LinkedHashSet<Object>(
							Arrays.asList((Object[]) viewer.getInput()));
					LinkedHashSet<Object> firstChildren = new LinkedHashSet<Object>();
					for (Object selection : graph.getSelection()) {
						GraphItem gi = (GraphItem) selection;
						calculateFirstChilden(gi.getData(), firstChildren);
						gi.unhighlight();
					}
					currentInput.addAll(firstChildren);
					viewer.setInput(currentInput.toArray());
				}
			});
			MenuItem expandAll = new MenuItem(menu, SWT.NONE);
			expandAll.setText("-(*)->");
			expandAll.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					LinkedHashSet<Object> currentInput = new LinkedHashSet<Object>(
							Arrays.asList((Object[]) viewer.getInput()));
					LinkedHashSet<Object> allChildren = new LinkedHashSet<Object>();
					for (Object selection : graph.getSelection()) {
						GraphItem gi = (GraphItem) selection;
						calculateAllChilden(gi.getData(), allChildren);
						gi.unhighlight();
					}
					currentInput.addAll(allChildren);
					viewer.setInput(currentInput.toArray());
				}
			});
			MenuItem deleteItem = new MenuItem(menu, SWT.NONE);
			deleteItem.setText("Delete");
			deleteItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					// @SuppressWarnings("unchecked")
					// Stream<GraphNode> l = graph.getGraph().getNodes().stream();
					// GraphNode node = l.filter(n -> n.getFigure().equals(fig)).findFirst().get();
					// node.dispose();
					while (!graph.getSelection().isEmpty()) {
						GraphItem gi = (GraphItem) graph.getSelection().get(0);
						LinkedList<Object> currentInput = new LinkedList<Object>(
								Arrays.asList((Object[]) viewer.getInput()));
						currentInput.remove(gi.getData());

						viewer.setInput(currentInput.toArray());
						gi.dispose();
					}
				}
			});

			menu.setVisible(true);
		} else {
			Menu menu = new Menu(new Shell(), SWT.POP_UP);
			MenuItem exit = new MenuItem(menu, SWT.NONE);
			exit.setText("Redraw Graph");
			exit.addSelectionListener(new RedrawGraphSelectionAdapter(viewer));
			menu.setVisible(true);
		}
	}
}
