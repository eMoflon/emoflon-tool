package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.moflon.tgg.mosl.tgg.TggPackage;
import org.moflon.tgg.mosl.tgg.VariablePattern;


@SuppressWarnings("deprecation")
public abstract class AbstractVariablePatternHighlightRule extends AbstractHighlightingRule {


	public AbstractVariablePatternHighlightRule(String id, String description) {
		super(id, description);
	}
	
	@Override
	public void provideHighlightingFor(EObject rootObject, IHighlightedPositionAcceptor acceptor) {
		for(VariablePattern vp: EcoreUtil2.getAllContentsOfType(rootObject, VariablePattern.class)){
			for(INode node : NodeModelUtils.findNodesForFeature(vp, TggPackage.Literals.VARIABLE_PATTERN__NAME)){	
				provideHighlightingForVariablePattern(vp, node, acceptor);				
			}
		}

	}
	
	protected abstract void provideHighlightingForVariablePattern(VariablePattern vp, INode variablePatternNode, IHighlightedPositionAcceptor acceptor);

}
