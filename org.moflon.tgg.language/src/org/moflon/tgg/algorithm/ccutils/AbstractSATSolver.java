package org.moflon.tgg.algorithm.ccutils;

public abstract class AbstractSATSolver {

	protected abstract int[] solve(DimacFormat problem);
	
	public int[] solve(int[][] problem){
		return solve(new DimacFormat(problem));
	}

}
