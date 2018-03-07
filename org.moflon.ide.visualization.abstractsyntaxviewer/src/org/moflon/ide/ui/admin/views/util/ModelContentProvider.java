package org.moflon.ide.ui.admin.views.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef4.zest.core.viewers.IGraphEntityRelationshipContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ModelContentProvider implements IGraphEntityRelationshipContentProvider {

	private List<EObject> content;

	@Override
	public Object[] getElements(Object inputElement) {
		return content.toArray();
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		content = new ArrayList<EObject>();
		if (newInput instanceof Object[]) {
			Object[] input = (Object[]) newInput;
			addInputToContent(input);
		} else if (newInput != null)
			throw new RuntimeException("Unsupported input type: " + newInput.getClass().toString());
	}

	private void addInputToContent(Object[] inputElements) {
		for (Object object : inputElements) {
			if (object instanceof EObject) {
				content.add((EObject) object);
			} else if (object != null) {
				throw new RuntimeException("Unsupported input element type: " + object.getClass().toString());
			}
		}
	}

	@Override
	public Object[] getRelationships(Object source, Object dest) {
		if (source instanceof EObject && dest instanceof EObject)
			return ModelConnection.getConnections((EObject) source, (EObject) dest);
		else
			throw new RuntimeException(
					"Types not supported (src, dest): " + source.getClass().toString() + dest.getClass().toString());
	}

}
