package org.moflon.ide.ui.admin.views.util;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.moflon.core.utilities.eMoflonEMFUtil;

public class ModelConnection {

	private EObject source, destination;
	private String name;

	protected ModelConnection(EObject source, EObject destination, String name) {
		this.source = source;
		this.destination = destination;
		this.name = name;
	}

	public EObject getSource() {
		return source;
	}

	public EObject getDestination() {
		return destination;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ModelConnection))
			return false;

		ModelConnection other = (ModelConnection) object;
		if (this.name.equals(other.getName())
				&& ((this.destination.equals(other.getDestination()) && this.source.equals(other.getSource()))
						|| (this.source.equals(other.getDestination()) && this.destination.equals(other.getSource()))))
			return true;

		return false;
	}

	public static ModelConnection[] getConnections(EObject source, EObject destination) {
		Set<EStructuralFeature> references = eMoflonEMFUtil.getAllReferences(source);
		ArrayList<ModelConnection> result = new ArrayList<ModelConnection>();
		for (EStructuralFeature reference : references) {
			if (isConnectedTo(source, reference, destination)) {
				ModelConnection connection = new ModelConnection(source, destination, reference.getName());
				result.add(connection);
			}
		}

		return result.toArray(new ModelConnection[result.size()]);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isConnectedTo(EObject source, EStructuralFeature reference, EObject destination) {
		// Check if the reference to be handled is a containment edge
		// (i.e., node contains s.th.)
		if (reference.getUpperBound() != 1) {
			// Edge is n-ary: edge exists only once, but points to many
			// contained EObjects
			EList containedObjects = (EList) source.eGet(reference, true);
			try {
				return containedObjects.contains(destination);
			} catch (Exception e) {
				return false;
			}
		}
		// else a standard reference was found
		else {
			EObject target = (EObject) source.eGet(reference, true);
			return target.equals(destination);
		}
	}
}
