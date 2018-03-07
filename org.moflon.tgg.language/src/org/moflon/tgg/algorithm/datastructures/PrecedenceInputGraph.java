package org.moflon.tgg.algorithm.datastructures;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.RuntimeFactory;
import org.moflon.tgg.runtime.TripleMatchNodeMapping;

/**
 * An on-the-fly collection of source/target matches and their precedence
 * dependencies created and used during synchronization.
 * 
 * @author anjorin
 *
 */
public class PrecedenceInputGraph extends PrecedenceStructureWithSiblingsSupport<Match> {

	public void collectAllPrecedences(Collection<Match> inputMatches) {
		for (Match match : inputMatches)
			calculateTables(match);

		calculateSiblings();
	}

	// --------

	@Override
	public Collection<EObject> getContextElements(Match m) {
		return m.getContextHashSet();
	}

	@Override
	public Collection<EObject> getCreatedElements(Match m) {
		return m.getCreatedHashSet();
	}

	// ---------

	@Override
	protected org.moflon.tgg.runtime.TripleMatch toEMF(Match m) {
		org.moflon.tgg.runtime.TripleMatch tripleMatch = RuntimeFactory.eINSTANCE.createTripleMatch();

		tripleMatch.getSourceElements().addAll(m.getContextNodes());
		tripleMatch.getSourceElements().addAll(m.getContextEdges());
		tripleMatch.getSourceElements().addAll(m.getToBeTranslatedNodes());
		tripleMatch.getSourceElements().addAll(m.getToBeTranslatedEdges());

		tripleMatch.getContextElements().addAll(m.getContextNodes());
		tripleMatch.getContextElements().addAll(m.getContextEdges());

		tripleMatch.getCreatedElements().addAll(m.getToBeTranslatedNodes());
		tripleMatch.getCreatedElements().addAll(m.getToBeTranslatedEdges());

		addEdges(tripleMatch);

		tripleMatch.setRuleName(m.getRuleName());

		m.getNodeMappings().keySet().stream().forEach(nodeName -> {
			TripleMatchNodeMapping nodeMapping = RuntimeFactory.eINSTANCE.createTripleMatchNodeMapping();
			nodeMapping.setNodeName(nodeName);
			nodeMapping.setNode(m.getNodeMappings().get(nodeName));
			tripleMatch.getNodeMappings().add(nodeMapping);
		});

		return tripleMatch;
	}

	@Override
	protected Match fromEMF(org.moflon.tgg.runtime.TripleMatch tripleMatch) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();

		tripleMatch.getContextElements().forEach(elt -> {
			if (elt instanceof EMoflonEdge)
				match.getContextEdges().add((EMoflonEdge) elt);
			else
				match.getContextNodes().add(elt);
		});

		tripleMatch.getCreatedElements().forEach(elt -> {
			if (elt instanceof EMoflonEdge)
				match.getToBeTranslatedEdges().add((EMoflonEdge) elt);
			else
				match.getToBeTranslatedNodes().add(elt);
		});

		return match;
	}

}
