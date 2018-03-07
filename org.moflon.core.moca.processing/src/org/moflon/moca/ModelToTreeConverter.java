package org.moflon.moca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.moflon.core.utilities.eMoflonEMFUtil;

import MocaTree.Attribute;
import MocaTree.File;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;
import MocaTree.impl.MocaTreeFactoryImpl;

public class ModelToTreeConverter {
	private HashMap<EObject, Integer> hashToId = new HashMap<EObject, Integer>();

	private int index = 0;

	public File modelToTree(final EObject root, final boolean produceArbitraryButUniqueNames) {
		Node rootNode = MocaTreeFactory.eINSTANCE.createNode();
		rootNode.setName("root");

		// Traverse along the containment edges (sufficient enough due to the
		// transitive containment hierarchy each Ecore model adheres to)
		Iterator<EObject> contents = root.eAllContents();

		// Add root
		Collection<EObject> allContents = new ArrayList<EObject>();
		allContents.add(root);
		while (contents.hasNext())
			allContents.add(contents.next());

		// Handle nodes in graph
		hashToId.clear();
		index = 0;
		Iterator<EObject> verticies = allContents.iterator();
		while (verticies.hasNext()) {
			// Element in model
			EObject node = verticies.next();

			// Build tree
			Node treeNode = MocaTreeFactory.eINSTANCE.createNode();
			treeNode.setName(getNameForNode(node, produceArbitraryButUniqueNames));
			treeNode.setParentNode(rootNode);

			handleAttributes(node, treeNode);

			Node treeNodeRefs = MocaTreeFactory.eINSTANCE.createNode();
			treeNodeRefs.setName("REFS");
			treeNodeRefs.setParentNode(treeNode);
			treeNodeRefs.setIndex(1);

			// Retrieve all EReference instances
			Set<EStructuralFeature> references = eMoflonEMFUtil.getAllReferences(node);

			// Iterate through all references
			for (EStructuralFeature reference : references) {
				Node refName = MocaTreeFactory.eINSTANCE.createNode();
				refName.setName(reference.getName());
				refName.setParentNode(treeNodeRefs);

				// Check if the reference to be handled is a containment edge
				// (i.e., node contains s.th.)
				if (reference.getUpperBound() != 1)
					// Edge is n-ary: edge exists only once, but points to many
					// contained EObjects
					for (Object containedObject : (EList<?>) node.eGet(reference, true)) {
						EObject element = (EObject) containedObject;
						Node target = MocaTreeFactory.eINSTANCE.createNode();
						target.setName(getNameForNode(element, produceArbitraryButUniqueNames));
						target.setParentNode(refName);
					}
				// else a standard reference was found
				else {
					EObject target = (EObject) node.eGet(reference, true);
					Node targetNode = MocaTreeFactory.eINSTANCE.createNode();
					targetNode.setName(getNameForNode(target, produceArbitraryButUniqueNames));
					targetNode.setParentNode(refName);
				}

			}
		}

		File file = MocaTreeFactoryImpl.eINSTANCE.createFile();
		file.setName(root.eClass().getName());
		file.setRootNode(rootNode);

		return file;
	}

	private String getNameForNode(final EObject node, final boolean produceArbitraryButUniqueNames) {
		if (produceArbitraryButUniqueNames) {
			if (!hashToId.containsKey(node))
				hashToId.put(node, index++);
			return "n" + hashToId.get(node) + "_" + node.eClass().getName();
		} else {
			int attributesAdded = 0;
			for (EAttribute feature : node.eClass().getEAllAttributes()) {
				if (node.eGet(feature) != null)
					attributesAdded += node.eGet(feature).toString().hashCode();
			}
			return node.eClass().getName() + "_" + attributesAdded;
		}

	}

	private void handleAttributes(final EObject node, final Node treeNode) {
		Node properties = MocaTreeFactory.eINSTANCE.createNode();
		properties.setName("PROPERTIES");
		properties.setParentNode(treeNode);
		properties.setIndex(0);

		int i = 0;
		for (EAttribute feature : node.eClass().getEAllAttributes()) {
			Attribute featureAttr = MocaTreeFactory.eINSTANCE.createAttribute();
			featureAttr.setIndex(i++);
			featureAttr.setName(feature.getName());
			featureAttr.setValue(node.eGet(feature) == null ? "" : prepareStringForDOT(node.eGet(feature).toString()));

			featureAttr.setNode(properties);
		}
	}

	private String prepareStringForDOT(final String string) {
		String s = string.replace("{", "\\{");
		s = s.replace("}", "\\}");
		s = s.replace("\"", "\\\"");
		s = s.replace("\n", "\\n ");

		return s;
	}
}