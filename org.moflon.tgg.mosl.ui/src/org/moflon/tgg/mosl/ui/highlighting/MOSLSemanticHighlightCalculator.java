package org.moflon.tgg.mosl.ui.highlighting;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.moflon.tgg.mosl.services.TGGGrammarAccess;
import org.moflon.tgg.mosl.tgg.TggPackage;
import org.moflon.tgg.mosl.tgg.VariablePattern;

import com.google.inject.Inject;

@SuppressWarnings("deprecation")
public class MOSLSemanticHighlightCalculator extends DefaultSemanticHighlightingCalculator {

	@Inject
	TGGGrammarAccess ga;
	
	@Override
	protected void doProvideHighlightingFor(XtextResource resource,
			IHighlightedPositionAcceptor acceptor) {
		EObject rootObject = resource.getParseResult().getRootASTElement();
		
		provideHighlightingForVariablePatterns(rootObject, acceptor);

		super.doProvideHighlightingFor(resource, acceptor);
	}
	
	private void provideHighlightingForVariablePatterns(EObject rootObject, IHighlightedPositionAcceptor acceptor){
		for(VariablePattern vp: EcoreUtil2.getAllContentsOfType(rootObject, VariablePattern.class)){
			for(INode node : NodeModelUtils.findNodesForFeature(vp, TggPackage.Literals.VARIABLE_PATTERN__NAME)){
				if(vp.getOp() != null && vp.getOp().getValue() != null){
					INode operatorNode = NodeModelUtils.getNode(vp.getOp());
					if(vp.getOp().getValue().contains("++")){
						setHighlighting(acceptor, operatorNode, MOSLHighlightingConfiguration.CREATION_ID);
						setHighlighting(acceptor, node, MOSLHighlightingConfiguration.CREATION_ID);	
					}else if(vp.getOp().getValue().contains("--")){
						setHighlighting(acceptor, operatorNode, MOSLHighlightingConfiguration.DESTROY_ID);
						setHighlighting(acceptor, node, MOSLHighlightingConfiguration.DESTROY_ID);						
					}else if(vp.getOp().getValue().contains("!")){
						setHighlighting(acceptor, operatorNode, MOSLHighlightingConfiguration.NEGATE_ID);
						setHighlighting(acceptor, node, MOSLHighlightingConfiguration.NEGATE_ID);				
					}
				}
			}
		}
	}
	
	private void setHighlighting(IHighlightedPositionAcceptor acceptor, INode node, String id){
		acceptor.addPosition(node.getOffset(), node.getLength() , id);
	}
	
}
