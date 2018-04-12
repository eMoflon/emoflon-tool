package org.moflon.tgg.algorithm.datastructures;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.runtime.TripleMatch;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.hash.TIntHashSet;

/**
 * 
 * @author leblebici
 *
 */

public class TGGRuleApplicationProtocol extends PrecedenceStructure<TGGRuleApplication> {

	private int name = 0;
	
	TIntHashSet allVariables = new TIntHashSet();

	@Override
	public Collection<EObject> getContextElements(TGGRuleApplication m) {

		return Stream.concat(Stream.concat(m.getContextSrc().stream(), m.getContextTrg().stream()),
				m.getContextCorr().stream()).collect(Collectors.toSet());
	}

	@Override
	public Collection<EObject> getCreatedElements(TGGRuleApplication m) {

		return Stream.concat(Stream.concat(m.getCreatedSrc().stream(), m.getCreatedTrg().stream()),
				m.getCreatedCorr().stream()).collect(Collectors.toSet());
	}

	@Override
	protected TripleMatch toEMF(TGGRuleApplication m) {

		return null;
	}

	@Override
	protected TGGRuleApplication fromEMF(TripleMatch m) {

		return null;
	}


	

}
