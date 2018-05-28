package org.moflon.tgg.algorithm.ccutils;

import org.cardygan.ilp.api.model.Model;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;

public interface UserDefinedILPStrategy {
	public void modifyILPproblem(Model model, ConsistencyCheckPrecedenceGraph protocol);
}
