package org.moflon.tgg.mosl.ui.highlighting.rules;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.ui.highlighting.exceptions.IDAlreadyExistException;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLHighlightProviderHelper;

@SuppressWarnings("deprecation")
public abstract class AbstractHighlightingRule {

	protected Logger logger;
	
	protected String id;
	
	protected String description;
	
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
	
	protected void setHighlighting(IHighlightedPositionAcceptor acceptor, INode node, String id){
		acceptor.addPosition(node.getOffset(), node.getLength() , id);
	}
	
	protected abstract TextStyle getTextStyle();
	
	public void setHighlightingConfiguration(IHighlightingConfigurationAcceptor acceptor){
		acceptor.acceptDefaultHighlighting(id, description, getTextStyle());
	}
	
	public abstract void provideHighlightingFor(EObject rootObject, IHighlightedPositionAcceptor acceptor);
	
	public String getID(){
		return id;
	}
}
