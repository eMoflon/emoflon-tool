package org.moflon.tgg.mosl.ui.highlighting;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;
import org.moflon.tgg.mosl.services.TGGGrammarAccess;
import org.moflon.tgg.mosl.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLHighlightProviderHelper;

import com.google.inject.Inject;

@SuppressWarnings("deprecation")
public class MOSLSemanticHighlightCalculator extends DefaultSemanticHighlightingCalculator {

	@Inject
	TGGGrammarAccess ga;
	
	@Override
	protected void doProvideHighlightingFor(XtextResource resource,
			IHighlightedPositionAcceptor acceptor) {
		EObject rootObject = resource.getParseResult().getRootASTElement();
		for(AbstractHighlightingRule<?> rule : MOSLHighlightProviderHelper.getHighlightRules())
			rule.provideHighlightingFor(rootObject, acceptor);
		super.doProvideHighlightingFor(resource, acceptor);
	}
	
}
