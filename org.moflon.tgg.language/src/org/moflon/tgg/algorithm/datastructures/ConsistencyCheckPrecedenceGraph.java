package org.moflon.tgg.algorithm.datastructures;

import java.util.Collection;

import org.cardygan.ilp.api.model.BinaryVar;
import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.runtime.CCMatch;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.TripleMatch;
import org.moflon.tgg.runtime.TripleMatchNodeMapping;

import gnu.trove.map.hash.THashMap;

public class ConsistencyCheckPrecedenceGraph extends PrecedenceStructure<CCMatch> {
	
	private THashMap<CCMatch, BinaryVar> matchToBinaryVar = new THashMap<>();
	
	public void putBinaryVar(CCMatch m, BinaryVar bv) {
		matchToBinaryVar.put(m, bv);
	}
	
	public BinaryVar getBinaryVar(CCMatch m) {
		return matchToBinaryVar.get(m);
	}
	
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
		org.moflon.tgg.runtime.TripleMatch tripleMatch = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createTripleMatch();
		
		tripleMatch.setRuleName(m.getRuleName());
		
		Match srcMatch = m.getSourceMatch();
		Match trgMatch = m.getTargetMatch();
		
		tripleMatch.getSourceElements().addAll(srcMatch.getContextHashSet());
		tripleMatch.getSourceElements().addAll(srcMatch.getCreatedHashSet());
		
		tripleMatch.getTargetElements().addAll(trgMatch.getContextHashSet());
		tripleMatch.getTargetElements().addAll(trgMatch.getCreatedHashSet());
		
		tripleMatch.getCorrespondenceElements().addAll(m.getAllContextElements());
		tripleMatch.getCorrespondenceElements().addAll(m.getCreateCorr());
		
		tripleMatch.getContextElements().addAll(m.getContextHashSet());
		tripleMatch.getCreatedElements().addAll(m.getCreatedHashSet());
		
		addEdges(tripleMatch);
		
		transferNodeMappings(tripleMatch, srcMatch);
		transferNodeMappings(tripleMatch, trgMatch);
		//TODO: correspondence node mappings are missing here

		return tripleMatch;
	}

	private void transferNodeMappings(org.moflon.tgg.runtime.TripleMatch tripleMatch, Match domainMatch) {
		domainMatch.getNodeMappings().keySet().forEach(nodeName -> {
			TripleMatchNodeMapping nodeMapping = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
					.createTripleMatchNodeMapping();
			nodeMapping.setNodeName(nodeName);
			nodeMapping.setNode(domainMatch.getObject(nodeName));
			tripleMatch.getNodeMappings().add(nodeMapping);
		});
	}
	


}
