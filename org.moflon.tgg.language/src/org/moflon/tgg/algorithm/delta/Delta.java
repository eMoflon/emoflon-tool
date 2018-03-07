package org.moflon.tgg.algorithm.delta;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.runtime.EMoflonEdge;

/**
 * Represents a set of changes applied to a model that are to be propagated in
 * the same synchronization step.
 * 
 * @author anjorin
 *
 */
public class Delta {
	private Collection<EObject> addedNodes = new HashSet<>();

	private Collection<EObject> deletedNodes = new HashSet<>();

	private Collection<EMoflonEdge> addedEdges = new HashSet<>();

	private Collection<EMoflonEdge> deletedEdges = new HashSet<>();

	private Collection<AttributeDelta> attributeChanges = new HashSet<>();

	public void addNode(EObject node) {
		addedNodes.add(node);
	}

	public void addEdge(EMoflonEdge edge) {
		addedEdges.add(edge);
	}

	public void changeAttribute(EAttribute affectedAttribute, Object oldValue, Object newValue, EObject obj) {
		attributeChanges.add(new AttributeDelta(affectedAttribute, oldValue, newValue, obj));
	}

	public void deleteNode(EObject node) {
		deletedNodes.add(node);
	}

	public void deleteEdge(EMoflonEdge edge) {
		deletedEdges.add(edge);
	}

	public Collection<EObject> getAddedNodes() {
		return addedNodes;
	}

	public Collection<EObject> getDeletedNodes() {
		return deletedNodes;
	}

	public Collection<EMoflonEdge> getAddedEdges() {
		return addedEdges;
	}

	public Collection<EMoflonEdge> getDeletedEdges() {
		return deletedEdges;
	}

	public Collection<AttributeDelta> getAttributeChanges() {
		return attributeChanges;
	}

	public Collection<EObject> getAllAddedElements() {
		return Stream.concat(addedNodes.stream(), addedEdges.stream()).collect(Collectors.toSet());
	}

	public Collection<EObject> getAllDeletedElements() {
		return Stream.concat(deletedNodes.stream(), deletedEdges.stream()).collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return "Added Nodes       (" + addedNodes.size() + "):" + Graph.displayNodes(addedNodes) + "\n"
				+ "Added Edges       (" + addedEdges.size() + "):" + Graph.displayEdges(addedEdges) + "\n"
				+ "Deleted Nodes     (" + deletedNodes.size() + "):" + Graph.displayNodes(deletedNodes) + "\n"
				+ "Deleted Edges     (" + deletedEdges.size() + "):" + Graph.displayEdges(deletedEdges) + "\n"
				+ "Attribute Changes (" + attributeChanges.size() + "):" + displayAttrChanges(attributeChanges);
	}

	private String displayAttrChanges(Collection<AttributeDelta> changes) {
		return changes.stream()
				.map(chg -> chg.getAffectedAttribute().getEContainingClass().getName() + "."
						+ chg.getAffectedAttribute().getName() + ": " + "[" + chg.getOldValue() + "]" + " -> " + "["
						+ chg.getNewValue() + "]")
				.reduce("", Graph.combineElementReps("", ""));
	}

	public boolean isChangeDetected() {
		return !(addedNodes.isEmpty() && addedEdges.isEmpty() && deletedNodes.isEmpty() && deletedEdges.isEmpty()
				&& attributeChanges.isEmpty());
	}

	public void clear() {
		addedEdges.clear();
		addedNodes.clear();
		attributeChanges.clear();
		deletedEdges.clear();
		deletedNodes.clear();
	}
}
