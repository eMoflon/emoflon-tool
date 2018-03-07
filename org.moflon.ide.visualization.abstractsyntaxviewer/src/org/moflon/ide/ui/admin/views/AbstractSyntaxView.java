package org.moflon.ide.ui.admin.views;

import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.gef4.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.gef4.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.gef4.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.gef4.zest.core.widgets.Graph;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.gef4.zest.layouts.LayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.moflon.ide.ui.admin.views.listeners.DoubleClickListener;
import org.moflon.ide.ui.admin.views.listeners.KeyListener;
import org.moflon.ide.ui.admin.views.listeners.MenuListener;
import org.moflon.ide.ui.admin.views.util.FixedSpringLayoutAlgorithm;
import org.moflon.ide.ui.admin.views.util.ModelContentProvider;
import org.moflon.ide.ui.admin.views.util.ModelDropListener;
import org.moflon.ide.ui.admin.views.util.ModelLabelProvider;

public class AbstractSyntaxView extends ViewPart implements IZoomableWorkbenchPart {

	public static final String ID = "org.moflon.ide.ui.admin.views.AbstractSyntaxView";

	private GraphViewer viewer;

	private ModelDropListener modelDropListener;

	public static AbstractSyntaxView INSTANCE;

	public AbstractSyntaxView() {
		super();
		INSTANCE = this;
	}

	public void createPartControl(Composite parent) {
		viewer = new GraphViewer(parent, SWT.BORDER);
		getSite().setSelectionProvider(viewer);
		viewer.setContentProvider(new ModelContentProvider());
		viewer.setLabelProvider(new ModelLabelProvider());
		LayoutAlgorithm layout = getLayout();
		viewer.setLayoutAlgorithm(layout, true);
		addDropSupport();
		viewer.applyLayout();
		fillToolBar();
		Graph graph = viewer.getGraphControl();
		graph.setAnimationEnabled(true);
		graph.addMenuDetectListener(new MenuListener(viewer));
		graph.addKeyListener(new KeyListener(viewer));
		viewer.addDoubleClickListener(new DoubleClickListener());
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
	}

	private void addDropSupport() {
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer(), LocalTransfer.getInstance(),
				FileTransfer.getInstance() };

		modelDropListener = new ModelDropListener(viewer);
		viewer.addDropSupport(operations, transferTypes, modelDropListener);
	}

	private LayoutAlgorithm getLayout() {
		LayoutAlgorithm layout;
		layout = new TreeLayoutAlgorithm();
		return layout;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */

	public void setFocus() {
	}

	private void fillToolBar() {
		ZoomContributionViewItem toolbarZoomContributionViewItem = new ZoomContributionViewItem(this);
		IActionBars bars = getViewSite().getActionBars();
		bars.getMenuManager().add(toolbarZoomContributionViewItem);
		addLayoutOptions(bars.getMenuManager());
	}

	private void addLayoutOptions(IMenuManager menu) {
		menu.add(new Action("Clear View", IAction.AS_PUSH_BUTTON) {
			public void run() {
				modelDropListener.clear();
			}
		});

		menu.add(new Action("Redraw Graph", IAction.AS_PUSH_BUTTON) {
			public void run() {
				updateViewer();
			}
		});

		menu.add(new Separator());

		Action action = new Action("Tree Layout", IAction.AS_RADIO_BUTTON) {
			public void run() {
				viewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(), true);
			}
		};
		action.setChecked(true);

		menu.add(action);

		menu.add(new Action("Spring Layout", IAction.AS_RADIO_BUTTON) {
			public void run() {
				FixedSpringLayoutAlgorithm layout = new FixedSpringLayoutAlgorithm();
				// A random number, could be random for each new loaded instance
				layout.setSeed(4711);
				viewer.setLayoutAlgorithm(layout, true);
			}
		});

		menu.add(new Action("Grid Layout", IAction.AS_RADIO_BUTTON) {
			public void run() {
				viewer.setLayoutAlgorithm(new GridLayoutAlgorithm(), true);
			}
		});

		menu.add(new Action("Radial Layout", IAction.AS_RADIO_BUTTON) {
			public void run() {
				viewer.setLayoutAlgorithm(new RadialLayoutAlgorithm(), true);
			}
		});
	}

	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

	public void updateViewer() {
		viewer.setInput(viewer.getInput());
	}

}
