package org.moflon.tgg.algorithm.delta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.RuntimeFactory;

import MocaTree.Attribute;
import MocaTree.File;
import MocaTree.Folder;
import MocaTree.MocaTreePackage;
import MocaTree.Node;
import MocaTree.TreeElement;
import convenience.RTED;

public class OfflineTreeChangeDetector {
	private double costCreate = 1;
	private double costDelete = 1;
	private double costRename = 1;
	private TreeElement before;
	private TreeElement after;
	private Delta delta;
	private Map<Integer, TreeElement> beforePostOrderIdToTreeElt;
	private Map<Integer, TreeElement> afterPostOrderIdToTreeElt;
	private static final EAttribute nameAttr = (EAttribute) MocaTreePackage.eINSTANCE.getTreeElement()
			.getEStructuralFeature("name");
	private static final EAttribute valueAttr = (EAttribute) MocaTreePackage.eINSTANCE.getAttribute()
			.getEStructuralFeature("value");

	public OfflineTreeChangeDetector(final TreeElement before, final TreeElement after, final double costIns,
			final double costDel, final double costRen) {
		this(before, after);
		this.costCreate = costIns;
		this.costDelete = costDel;
		this.costRename = costRen;
	}

	public OfflineTreeChangeDetector(final TreeElement before, final TreeElement after) {
		this.before = before;
		this.after = after;

		this.beforePostOrderIdToTreeElt = new HashMap<>();
		this.afterPostOrderIdToTreeElt = new HashMap<>();
	}

	public Delta computeForwardDelta() {
		Pair<String, Integer> beforeRep = computePostOrderIdsAndTreeRep(before, beforePostOrderIdToTreeElt,
				Pair.of("", 1));
		Pair<String, Integer> afterRep = computePostOrderIdsAndTreeRep(after, afterPostOrderIdToTreeElt,
				Pair.of("", 1));

		List<int[]> mappings = RTED.computeMapping(escape(beforeRep.getLeft()), escape(afterRep.getLeft()), costCreate,
				costDelete, costRename);

		return computeDelta(mappings);
	}

	private String escape(final String rep) {
		return rep.replace(":", "__COLON__");
	}

	private Pair<String, Integer> computePostOrderIdsAndTreeRep(final TreeElement root,
			final Map<Integer, TreeElement> map, Pair<String, Integer> current) {
		String childrenRep = "";
		for (TreeElement child : computeChildren(root)) {
			Pair<String, Integer> childResult = computePostOrderIdsAndTreeRep(child, map, current);
			current = Pair.of(current.getLeft(), childResult.getRight());
			childrenRep = childrenRep + childResult.getLeft();
		}

		map.put(current.getRight(), root);

		return Pair.of("{" + computeLabel(root) + " " + childrenRep + "}", current.getRight() + 1);
	}

	private Delta computeDelta(final List<int[]> mappings) {
		delta = new Delta();

		mappings.forEach(mapping -> {
			if (mapping[0] == 0)
				handleCreation(mapping[1]);
			else if (mapping[1] == 0)
				handleDeletion(mapping[0]);
			else
				handleAttributeChange(mapping[0], mapping[1]);
		});

		return delta;
	}

	private void handleAttributeChange(final int before, final int after) {
		TreeElement oldElt = beforePostOrderIdToTreeElt.get(before);
		TreeElement newElt = afterPostOrderIdToTreeElt.get(after);

		if (!oldElt.getName().equals(newElt.getName()))
			delta.changeAttribute(nameAttr, oldElt.getName(), newElt.getName(), newElt);

		if (oldElt instanceof Attribute && newElt instanceof Attribute) {
			Attribute oldAttr = (Attribute) oldElt;
			Attribute newAttr = (Attribute) newElt;
			if (!oldAttr.getValue().equals(newAttr.getValue()))
				delta.changeAttribute(valueAttr, oldAttr.getValue(), newAttr.getValue(), newAttr);
		}
	}

	private void handleDeletion(final int toBeDeleted) {
		handle(toBeDeleted, beforePostOrderIdToTreeElt, delta::deleteNode, delta::deleteEdge);
	}

	private void handleCreation(final int toBeCreated) {
		handle(toBeCreated, afterPostOrderIdToTreeElt, delta::addNode, delta::addEdge);
	}

	private void handle(final int id, final Map<Integer, TreeElement> idToEltMap,
			final Consumer<TreeElement> nodeAction, final Consumer<EMoflonEdge> edgeAction) {
		TreeElement toBeHandledElt = idToEltMap.get(id);

		nodeAction.accept(toBeHandledElt);

		if (toBeHandledElt.eContainer() != null) {
			edgeAction.accept(createContainmentEdge(toBeHandledElt));
			edgeAction.accept(createContainingEdge(toBeHandledElt));
		}

		toBeHandledElt.eContents().forEach(child -> {
			edgeAction.accept(createContainmentEdge(child));
			edgeAction.accept(createContainingEdge(child));
		});
	}

	private EMoflonEdge createContainingEdge(final EObject elt) {
		EMoflonEdge edge = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		edge.setName(((EReference) (elt.eContainingFeature())).getEOpposite().getName());
		edge.setSrc(elt);
		edge.setTrg(elt.eContainer());

		return edge;
	}

	private EMoflonEdge createContainmentEdge(final EObject elt) {
		EMoflonEdge edge = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		edge.setName(elt.eContainingFeature().getName());
		edge.setTrg(elt);
		edge.setSrc(elt.eContainer());

		return edge;
	}

	private Collection<TreeElement> computeChildren(final TreeElement root) {
		ArrayList<TreeElement> children = new ArrayList<>();

		if (root instanceof Folder) {
			Folder folder = (Folder) root;
			children.addAll(folder.getFile());
			children.addAll(folder.getSubFolder());
		}

		if (root instanceof File) {
			children.add(((File) root).getRootNode());
		}

		if (root instanceof Node) {
			Node node = (Node) root;
			children.addAll(node.getAttribute());
			children.addAll(node.getChildren());
		}

		return children;
	}

	private String computeLabel(final TreeElement root) {
		String label = root.getName();

		if (root instanceof Attribute)
			return label + "__" + ((Attribute) root).getValue();

		return label;
	}
}
