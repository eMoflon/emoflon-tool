package org.moflon.ide.ui.admin.views.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

public class ModelDropListener extends ViewerDropAdapter {
	private static final Logger logger = Logger.getLogger(ModelDropListener.class);

	private final Viewer viewer;

	/**
	 * Variable currentInput is a {@link LinkedHashSet} to ensure that each
	 * presented Object in the viewer is unique.
	 */
	private LinkedHashSet<Object> currentInput;

	public ModelDropListener(Viewer viewer) {
		super(viewer);
		this.viewer = viewer;
		currentInput = new LinkedHashSet<Object>();
	}

	@Override
	protected Object determineTarget(DropTargetEvent event) {
		Object obj = super.determineTarget(event);
		if (obj == null) {
			obj = LocalSelectionTransfer.getTransfer().getSelection();
		}
		return obj;
	}

	/**
	 * @see ExpressionDropAdapter#dragEnter(DropTargetEvent)
	 * @see org.eclipse.jface.viewers.ViewerDropAdapter#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragEnter(DropTargetEvent event) {
		super.dragEnter(event);
	}

	@Override
	public boolean performDrop(Object data) {
		if (data instanceof TreeSelection) {
			TreeSelection treeSel = (TreeSelection) data;

			Iterator<?> elements = treeSel.iterator();
			while (elements.hasNext()) {
				Object element = elements.next();
				if (element instanceof EObject) {
					performDrop(element);
				} else if (element instanceof IAdaptable) {
					IAdaptable adapter = (IAdaptable) element;
					Object o = adapter.getAdapter(EObject.class);
					return performDrop(o);
				}
			}
		} else if (data instanceof EObject) {
			EObject root = (EObject) data;
			// TODO Improve for performance reasons
			if (viewer.getInput() != null) {
				currentInput.clear();
				currentInput.addAll(Arrays.asList((Object[]) viewer.getInput()));
			}
			currentInput.add(root);
			updateView();
		} else {
			logger.error("Unable to handle dropped content: " + (data == null ? data : data.toString()));
			throw new RuntimeException("Type not supported: " + (data == null ? data : data.getClass().toString()));
		}
		return true;
	}

	/**
	 * Validates if a drop that is a {@link TreeSelection} that contains
	 * {@link IAdaptable}s or is an {@link IAdaptable} that returns an non null
	 * adapter for {@link EObject}.
	 * 
	 * @see org.eclipse.jface.viewers.ViewerDropAdapter#validateDrop(java.lang.Object,
	 *      int, org.eclipse.swt.dnd.TransferData)
	 */
	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		// System.out.println("validate " + target);
		// System.out.println("val:" +
		// LocalSelectionTransfer.getTransfer().getSelection());
		if (target instanceof TreeSelection) {
			TreeSelection ts = (TreeSelection) target;
			Iterator<?> iter = ts.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (!validateDrop(obj, operation, transferType)) {
					return false;
				}
			}
			return true;
		} else if (target instanceof IAdaptable) {
			IAdaptable adapter = (IAdaptable) target;
			// TODO : Refactor as most implemented adapters of the debugger copy objects
			// thus this might be expensive
			return adapter.getAdapter(EObject.class) instanceof EObject;
		}
		return true;
	}

	public void clear() {
		currentInput.clear();
		updateView();
	}

	private void updateView() {
		viewer.setInput(currentInput.toArray());
	}
}
