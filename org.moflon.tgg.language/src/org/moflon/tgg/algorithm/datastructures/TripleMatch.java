package org.moflon.tgg.algorithm.datastructures;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * Represents a complete match over all domains.
 * 
 * @author anjorin
 *
 */
public class TripleMatch {
	private static int counter = 0;

	private String ruleName;

	private int id;

	private Graph source;

	private Graph corr;

	private Graph target;

	private Graph created;

	private Graph context;

	private Graph createdSrc;

	private Graph createdTrg;

	private Graph createdCorrAndTrg;

	private Graph createdCorrAndSrc;

	private Graph createdCorr;

	private Map<String, EObject> nodeMappings;

	public TripleMatch(String ruleName, Graph source, Graph target, Graph corr, Graph created, Graph context,
			Map<String, EObject> nodeMapping) {
		this.ruleName = ruleName;
		id = counter++;

		this.nodeMappings = nodeMapping;
		this.created = created;
		this.context = context;
		this.source = source;
		this.target = target;
		this.corr = corr;
		this.createdSrc = source.remove(context);
		this.createdTrg = target.remove(context);
		this.createdCorr = corr.remove(context);
		this.createdCorrAndSrc = corr.add(source).removeDestructive(context);
		this.createdCorrAndTrg = corr.add(target).removeDestructive(context);
	}

	public Graph getSourceElements() {
		return source;
	}

	public Graph getTargetElements() {
		return target;
	}

	public Graph getCorrespondenceElements() {
		return corr;
	}

	public Graph getCreatedElements() {
		return created;
	}

	public Graph getContextElements() {
		return context;
	}

	public String getRuleName() {
		return ruleName;
	}

	public int getID() {
		return id;
	}

	public Graph getCreatedSrcElts() {
		return createdSrc;
	}

	public Graph getCreatedTrgElts() {
		return createdTrg;
	}

	public Graph getCreatedCorrElts() {
		return createdCorr;
	}

	public Graph getCreatedCorrAndTrgElts() {
		return createdCorrAndTrg;
	}

	public Graph getCreatedCorrAndSrcElts() {
		return createdCorrAndSrc;
	}

	public EObject getNode(String nodeName) {
		return nodeMappings.get(nodeName);
	}

	public Map<String, EObject> getNodeMappings() {
		return nodeMappings;
	}

	@Override
	public String toString() {
		return "TripleMatch: " + ruleName + "(" + counter + "," + id + ")";
	}
}
