package org.moflon.ide.metamodelevolution.core.changes;

import org.moflon.ide.metamodelevolution.core.ChangeSequence;

import MocaTree.Node;

public interface MetamodelChangeCalculator {
	   public ChangeSequence parseTree(final Node tree);
}
