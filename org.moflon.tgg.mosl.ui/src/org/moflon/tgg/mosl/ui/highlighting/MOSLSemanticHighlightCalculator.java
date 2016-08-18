package org.moflon.tgg.mosl.ui.highlighting;

import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.moflon.tgg.mosl.services.TGGGrammarAccess;
import org.moflon.tgg.mosl.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLHighlightProviderHelper;

import com.google.inject.Inject;


public class MOSLSemanticHighlightCalculator extends DefaultSemanticHighlightingCalculator {

	@Inject
	TGGGrammarAccess ga;
	
	@Override
	protected void doProvideHighlightingFor(XtextResource resource,
			IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator ) {
		EObject rootObject = resource.getParseResult().getRootASTElement();
		for(AbstractHighlightingRule<?> rule : MOSLHighlightProviderHelper.getHighlightRules())
			rule.provideHighlightingFor(rootObject, acceptor);
		super.doProvideHighlightingFor(resource, acceptor, cancelIndicator);
		
	}
	
}
