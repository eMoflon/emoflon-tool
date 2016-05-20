package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.nodemodel.INode;
import org.moflon.tgg.mosl.tgg.TggPackage;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.tgg.OperatorPattern;

public abstract class AbstractOperatorPatternHighlightRule extends AbstractHighlightingRule<OperatorPattern> {


	public AbstractOperatorPatternHighlightRule(String id, String description) {
		super(id, description);
	}


	@Override
	protected EStructuralFeature getLiteral() {
		return TggPackage.Literals.OPERATOR_PATTERN__OP;
	}

	@Override
	protected Class<OperatorPattern> getNodeClass() {
		return OperatorPattern.class;
	}
	
	protected abstract boolean getOperatorCondition(Operator op);


	@Override
	protected void provideHighlightingFor(OperatorPattern moslObject, INode node) {
		Operator operator = moslObject.getOp();
		if(getOperatorCondition(operator)){
			setHighlighting(moslObject);
		}
	}
	

}
