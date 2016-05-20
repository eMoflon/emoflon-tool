package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.ui.highlighting.exceptions.IDAlreadyExistException;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLHighlightProviderHelper;

@SuppressWarnings("deprecation")
public abstract class AbstractHighlightingRule <M extends EObject>{

	protected Logger logger;
	
	protected String id;
	
	protected String description;
	
	private IHighlightedPositionAcceptor acceptor;
	
	public AbstractHighlightingRule(String id, String description){
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
	
	protected void setHighlighting(EObject eObject){
		INode node = NodeModelUtils.getNode(eObject);
		if(node != null)
			setHighlighting(node);
	}
	
	protected void setHighlighting(INode node){
		acceptor.addPosition(node.getOffset(), node.getLength() , id);
	}
	
	protected abstract TextStyle getTextStyle();
	
	public void setHighlightingConfiguration(IHighlightingConfigurationAcceptor acceptor){
		acceptor.acceptDefaultHighlighting(id, description, getTextStyle());
	}
	
	public void provideHighlightingFor(EObject rootObject, IHighlightedPositionAcceptor acceptor) {
		this.acceptor = acceptor;
		for(M moslObject: EcoreUtil2.getAllContentsOfType(rootObject, getNodeClass())){
			INode node = NodeModelUtils.findActualNodeFor(moslObject);
				provideHighlightingFor(moslObject, node);				
			
		}

	}
	
	protected abstract EStructuralFeature getLiteral();
	
	protected abstract Class<M> getNodeClass();
	
	protected abstract void provideHighlightingFor(M moslObject, INode node);
	
	public String getID(){
		return id;
	}
}
