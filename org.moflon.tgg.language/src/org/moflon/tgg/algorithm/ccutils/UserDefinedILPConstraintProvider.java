package org.moflon.tgg.algorithm.ccutils;

import java.util.Collection;

import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;

public interface UserDefinedILPConstraintProvider {

	public Collection<UserDefinedILPConstraint> getUserDefinedConstraints(ConsistencyCheckPrecedenceGraph protocol);

}
