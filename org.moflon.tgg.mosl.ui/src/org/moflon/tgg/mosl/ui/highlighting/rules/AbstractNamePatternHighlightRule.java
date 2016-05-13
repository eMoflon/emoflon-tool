package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.nodemodel.INode;
import org.moflon.tgg.mosl.tgg.NamePattern;
import org.moflon.tgg.mosl.tgg.TggPackage;

public abstract class AbstractNamePatternHighlightRule extends AbstractHighlightingRule<NamePattern> {

	public AbstractNamePatternHighlightRule(String id, String description) {
		super(id, description);
	}

	@Override
	protected EStructuralFeature getLiteral() {
		return TggPackage.Literals.NAMED_ELEMENTS__NAME;
	}

	@Override
	protected Class<NamePattern> getNodeClass() {
		return NamePattern.class;
	}

	@Override
	protected void provideHighlightingFor(NamePattern moslObject, INode node) {
		if(getNameCondition(moslObject))
			setHighlighting(node);
	}
	
	protected abstract boolean getNameCondition(NamePattern namePattern);

}
