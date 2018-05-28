package org.moflon.tgg.algorithm.ccutils;

import org.cardygan.ilp.api.model.BinaryVar;
import org.cardygan.ilp.api.model.Model;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class BinaryVarProvider {

	
	private TIntObjectMap<BinaryVar> idToBinaryVars = new TIntObjectHashMap<>();
	private Model ilpProblem;
	private ConsistencyCheckPrecedenceGraph protocol;
	
	public BinaryVarProvider(Model ilpProblem, ConsistencyCheckPrecedenceGraph protocol) {
		this.ilpProblem = ilpProblem;
		this.protocol = protocol;
	}
	
	protected BinaryVar getBinaryVar(int v) {
		if(idToBinaryVars.containsKey(v))
			return idToBinaryVars.get(v);
		
		String varName = "v" + v;
		BinaryVar binaryVar = ilpProblem.newBinaryVar(varName);
		idToBinaryVars.put(v, binaryVar);
		protocol.putBinaryVar(protocol.intToMatch(v), binaryVar);
		return binaryVar;
	}
	
	protected BinaryVar[] getBinaryVars(int[] vs) {
		BinaryVar[] result = new BinaryVar[vs.length];
		for(int i = 0; i < vs.length; i++) {
			result[i] = getBinaryVar(vs[i]);
		}
		return result;
	}
}
