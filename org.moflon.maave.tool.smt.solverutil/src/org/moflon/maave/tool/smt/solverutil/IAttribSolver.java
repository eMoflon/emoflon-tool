package org.moflon.maave.tool.smt.solverutil;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;


public interface IAttribSolver {
	
	

	public boolean checkImplication(SymbolicGraphMorphism morphism);
	
	public boolean hasNonEmptySemantic(SymbolicGraph symbGraph);
	
	public boolean hasEquivalentFormulas(SymbolicGraphMorphism morphism);
	


}
