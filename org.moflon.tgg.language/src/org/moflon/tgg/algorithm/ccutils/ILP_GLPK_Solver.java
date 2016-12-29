package org.moflon.tgg.algorithm.ccutils;

import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGLPK;

public class ILP_GLPK_Solver extends AbstractILPSolver{

	@Override
	protected SolverFactory getSolverFactory() {
		return new SolverFactoryGLPK();
	}

	






}
