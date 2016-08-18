package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.ui.highlighting.MOSLTokenMapper;
import org.moflon.tgg.mosl.ui.highlighting.exceptions.IDAlreadyExistException;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLHighlightProviderHelper;


public abstract class AbstractHighlightingRule{

	protected Logger logger;
	
	protected String id;
	
	protected String description;
	
	private int prio=50;
	
	private IHighlightedPositionAcceptor acceptor;
	
	public AbstractHighlightingRule(String id, String description){
		init(id, description);
	}
	
	public AbstractHighlightingRule(String id, String description, int prio){
		this.prio = prio;
		init(id, description);
	}
	
	private void init(String id, String description){
		logger = Logger.getLogger(this.getClass());
		this.id = id;
		this.description = description;
		try {
			MOSLHighlightProviderHelper.addHighlightRule(this);
		} catch (IDAlreadyExistException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}
	
	protected void setHighlighting(INode node){
		acceptor.addPosition(node.getOffset(), node.getLength() , id);
	}
	
	protected abstract TextStyle getTextStyle();
	
	public void setHighlightingConfiguration(IHighlightingConfigurationAcceptor acceptor){
		acceptor.
		acceptDefaultHighlighting(id, description, getTextStyle());
	}
	
//	public void provideHighlightingFor(INode rootNode, IHighlightedPositionAcceptor acceptor) {
//		this.acceptor = acceptor;
//		for(INode node : rootNode.getLeafNodes()){
//			EObject moslObject = NodeModelUtils.findActualSemanticObjectFor(node);
//			//String test = MOSLTokenMapper.mapper.getId(node.getOffset());
//			provideHighlightingFor(moslObject, node);
//			
//			
//			//INode node = NodeModelUtils.findActualNodeFor(moslObject);
//				//provideHighlightingFor(moslObject, node);				
//			
//		}
//
//	}
	
	public boolean canProvideHighlighting(EObject moslObject, INode node, IHighlightedPositionAcceptor acceptor){
		boolean provide = getHighlightingConditions(moslObject, node);
		if(provide){
			this.acceptor = acceptor;
			setHighlighting(node);
		}
		return provide;
	}
	
	public int getPriority(){
		return prio;
	}
	
//	public void provideHighlightingFor(EObject rootObject, IHighlightedPositionAcceptor acceptor) {
//		this.acceptor = acceptor;
//		for(M moslObject: EcoreUtil2.getAllContentsOfType(rootObject, getNodeClass())){
//			INode node = NodeModelUtils.findActualNodeFor(moslObject);
//				provideHighlightingFor(moslObject, node);				
//			
//		}
//
//	}
	
	//protected abstract EStructuralFeature getLiteral();
	
	//protected abstract Class<M> getNodeClass();
	
//	protected abstract void provideHighlightingFor(EObject moslObject, INode node);
	
	protected abstract boolean getHighlightingConditions(EObject moslObject, INode node);
	
	public String getID(){
		return id;
	}
}
