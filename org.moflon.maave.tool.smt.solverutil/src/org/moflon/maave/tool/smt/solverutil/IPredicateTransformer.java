package org.moflon.maave.tool.smt.solverutil;

import java.util.Collection;

import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;

public interface IPredicateTransformer
{
   public String transformPredicate(Predicate predicate,Mapping<LabelNode> labelNodeSubstMap);
   public Collection<String> getFunctionDefinitions();
   public Collection<String> getVariableDeclarations();
}
