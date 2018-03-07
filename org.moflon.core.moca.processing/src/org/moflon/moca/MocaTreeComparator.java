package org.moflon.moca;

import java.util.Comparator;

import MocaTree.TreeElement;
import MocaTree.impl.TreeElementImpl;

public class MocaTreeComparator implements Comparator<TreeElement> {

	@Override
	public int compare(TreeElement arg0, TreeElement arg1) {
		return ((TreeElementImpl) arg0).compareTo(arg1);
	}

}
