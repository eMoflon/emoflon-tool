package org.moflon.tgg.algorithm.datastructures;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.runtime.CCMatch;
import org.moflon.tgg.runtime.TripleMatch;

import gnu.trove.map.hash.TIntIntHashMap;

public class ConsistencyCheckPrecedenceGraph extends PrecedenceStructure<CCMatch> {
	

	private TIntIntHashMap matchToInt = new TIntIntHashMap();
	
	private int counter = 1;
	
	@Override
	public Collection<EObject> getContextElements(CCMatch m) {
		return m.getContextHashSet();
	}

	@Override
	public Collection<EObject> getCreatedElements(CCMatch m) {
		return m.getCreatedHashSet();
	}
	
	public void collectPrecedences(CCMatch m){
		calculateTables(m);
	}


	@Override
	protected CCMatch fromEMF(TripleMatch m) {
		throw new UnsupportedOperationException("Consistency checks from loaded protocols are not implemented");
	}

	@Override
	protected TripleMatch toEMF(CCMatch m) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int matchToInt(CCMatch m){
		int hashCode = m.hashCode();
		if(!matchToInt.containsKey(hashCode)){
			matchToInt.put(hashCode, counter++);
		}
		return matchToInt.get(hashCode);
	}

}
