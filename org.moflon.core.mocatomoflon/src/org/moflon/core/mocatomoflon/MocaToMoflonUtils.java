package org.moflon.core.mocatomoflon;

import MocaTree.Node;

public class MocaToMoflonUtils {
	public static final String ECLASS_NODE_NAME = "EClass";

	public static final String EPACKAGE_NODE_NAME = "EPackage";

	public static final String ROOTPACKAGE_NODE_NAME = "RootPackage";

	public static boolean isEClassNode(final Node node) {
		return node.getName() != null && (node.getName().equals(ECLASS_NODE_NAME));
	}

	public static boolean isEPackageNode(final Node node) {
		return node.getName() != null && (node.getName().equals(EPACKAGE_NODE_NAME));
	}

	public static boolean isTLPackageNode(final Node node) {
		return node.getName() != null && (node.getName().equals(ROOTPACKAGE_NODE_NAME));
	}
}
