package org.moflon.maave.tool.smt.solverutil;

import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;

public interface IFormulaTransformer
{

   public static final String VAR_SUFFIX="!";
   public static final String QANT_VAR_SUFFIX="!!"; 
   
   String transformBiImplication(Disjunction term1, Disjunction term2, Mapping<LabelNode> term2VariableSubstMap);

   String transformImplication(Disjunction premise, Disjunction conclusion, Mapping<LabelNode> conclusionVariableSubstMap);

   String transformDisjunctionSat(Disjunction disjunction);

   String transformDisjunctionUnsat(Disjunction disjunction);

}