package org.moflon.tgg.algorithm.datastructures;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.runtime.EMoflonEdge;

/**
 * An immutable collection of nodes and edges.
 * 
 * @author anjorin
 *
 */
public class Graph {

	private Collection<EObject> nodes;

	private Collection<EMoflonEdge> edges;

	public Graph(Collection<EObject> elements) {
		nodes = new HashSet<>();
		edges = new HashSet<>();

		addElts(elements);
	}

	public Graph(Collection<EObject> nodes, Collection<EMoflonEdge> edges) {
		this.nodes = new HashSet<>();
		this.edges = new HashSet<>();

		this.nodes.addAll(nodes);
		this.edges.addAll(edges);
	}

	private Graph() {
		nodes = new HashSet<>();
		edges = new HashSet<>();
	}

	public static Graph getEmptyGraph() {
		return new Graph();
	}

	public Collection<EObject> getElements() {
		Collection<EObject> elements = new HashSet<>();
		elements.addAll(nodes);
		elements.addAll(edges);
		return elements;
	}

	public Stream<EObject> stream() {
		return getElements().stream();
	}

	private void addElts(Collection<? extends EObject> addedElements) {
		addedElements.stream().forEach(elt -> {
			if (elt instanceof EMoflonEdge)
				edges.add((EMoflonEdge) elt);
			else
				nodes.add(elt);
		});
	}

	private void removeElts(Collection<? extends EObject> removedelements) {
		removedelements.stream().forEach(elt -> {
			if (elt instanceof EMoflonEdge)
				edges.remove((EMoflonEdge) elt);
			else
				nodes.remove(elt);
		});

	}

	public Graph add(Graph elts) {
		Collection<EObject> n = new HashSet<>();
		Collection<EMoflonEdge> e = new HashSet<>();

		n.addAll(nodes);
		n.addAll(elts.nodes);

		e.addAll(edges);
		e.addAll(elts.edges);

		return new Graph(n, e);
	}

	public Graph addConstructive(Graph elts) {
		nodes.addAll(elts.nodes);
		edges.addAll(elts.edges);
		return this;
	}

	public Graph addConstructive(Collection<? extends EObject> elts) {
		addElts(elts);
		return this;
	}

	public Graph remove(Graph elts) {
		Collection<EObject> n = new HashSet<>();
		Collection<EMoflonEdge> e = new HashSet<>();

		n.addAll(nodes);
		n.removeAll(elts.nodes);

		e.addAll(edges);
		e.removeAll(elts.edges);

		return new Graph(n, e);
	}

	public Graph removeDestructive(Graph elts) {
		nodes.removeAll(elts.nodes);
		edges.removeAll(elts.edges);
		return this;
	}

	public Graph removeDestructive(Collection<? extends EObject> elts) {
		removeElts(elts);
		return this;
	}

	public void delete() {
		edges.forEach(edge -> {
			if (edge.getSrc() != null && edge.getTrg() != null)
				eMoflonEMFUtil.removeEdge(edge.getSrc(), edge.getTrg(), edge.getName());
		});

		nodes.forEach(node -> eMoflonEMFUtil.remove(node));
	}

	@Override
	public String toString() {
		return "Nodes       (" + nodes.size() + "):" + displayNodes(nodes) + "\n" + "Edges       (" + edges.size()
				+ "):" + displayEdges(edges);
	}

	public static BinaryOperator<String> combineElementReps(String open, String close) {
		return (s1, s2) -> s1 + "\n\t " + open + s2 + close;
	}

	public static String displayNodes(Collection<EObject> nodes) {
		return nodes.stream().map(eMoflonEMFUtil::getName).reduce("", Graph.combineElementReps("[", "]"));
	}

	public static String displayEdges(Collection<EMoflonEdge> edges) {
		return edges.stream().map(e -> eMoflonEMFUtil.getName(e.getSrc()) + "] -" + e.getName() + "-> ["
				+ eMoflonEMFUtil.getName(e.getTrg())).reduce("", Graph.combineElementReps("[", "]"));
	}

	public Collection<EObject> getNodes() {
		return nodes;
	}

	public Collection<EMoflonEdge> getEdges() {
		return edges;
	}
}
