package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.tgg.VariablePattern;

@SuppressWarnings("deprecation")
public abstract class AbstractVariablePatternWithOperatorHighlightRule extends AbstractVariablePatternHighlightRule {

	public AbstractVariablePatternWithOperatorHighlightRule(String id, String description) {
		super(id, description);
	}

	@Override
	protected void provideHighlightingForVariablePattern(VariablePattern vp, INode variablePatternNode,
			IHighlightedPositionAcceptor acceptor) {
		if(vp.getOp() != null && vp.getOp().getValue() != null){
			INode operatorNode = NodeModelUtils.getNode(vp.getOp());
			if(getOperatorCondition(vp.getOp())){
				setHighlighting(acceptor, operatorNode, id);
				setHighlighting(acceptor, variablePatternNode, id);	
			}
		}

	}
	
	protected abstract boolean getOperatorCondition(Operator op);

}
