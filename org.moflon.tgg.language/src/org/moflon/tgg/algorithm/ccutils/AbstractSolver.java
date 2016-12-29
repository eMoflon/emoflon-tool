package org.moflon.tgg.algorithm.ccutils;

import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;

public abstract class AbstractSolver {

	public abstract int[] solve(Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol);

}
