package org.moflon.tgg.algorithm.ccutils;

import java.util.HashMap;
import java.util.Map;

import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.runtime.CCMatch;


public interface UserDefinedILPObjectiveProvider {
	
	
	default public UserDefinedILPObjective getUserDefinedObjective(ConsistencyCheckPrecedenceGraph protocol) {
		Map<Integer, Double> idsToCoefficients = new HashMap<>();
		for (int matchId : protocol.getMatchIDs().toArray()) {
			CCMatch match = protocol.intToMatch(matchId);
			Double weight = (double) match.getSourceMatch().getCreatedHashSet().size();
			weight += match.getTargetMatch().getCreatedHashSet().size();
			idsToCoefficients.put(matchId, weight);
		}
		return new UserDefinedILPObjective(idsToCoefficients, UserDefinedILPObjective.OptGoal.MAX);
	}


}
