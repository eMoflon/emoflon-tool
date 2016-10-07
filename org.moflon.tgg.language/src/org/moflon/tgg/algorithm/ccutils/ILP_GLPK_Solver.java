package org.moflon.tgg.algorithm.ccutils;

import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;

import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGLPK;

public class ILP_GLPK_Solver extends AbstractILPSolver{

	@Override
	protected SolverFactory getSolverFactory() {
		return new SolverFactoryGLPK();
	}

	






}
