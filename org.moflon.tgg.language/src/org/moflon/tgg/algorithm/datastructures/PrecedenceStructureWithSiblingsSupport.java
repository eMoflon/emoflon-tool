package org.moflon.tgg.algorithm.datastructures;

import gnu.trove.TIntCollection;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.TIntHashSet;

public abstract class PrecedenceStructureWithSiblingsSupport<M> extends PrecedenceStructure<M> {

	protected TIntObjectHashMap<TIntHashSet> matchToSiblings = new TIntObjectHashMap<>();

	public void calculateSiblings() {
		for (M match : matches.valueCollection()) {
			TIntHashSet siblings = new TIntHashSet();
			getCreatedElements(match).forEach(elt -> siblings.addAll(getOrReturnEmpty(elt, createToMatch)));
			matchToSiblings.put(matchToInt(match), siblings);
		}

	}

	public TIntCollection siblings(int m) {
		return matchToSiblings.get(m);
	}

}
