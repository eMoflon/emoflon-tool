package org.moflon.compiler.sdm.democles;

import java.util.Map;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;
import org.moflon.sdm.compiler.democles.validation.scope.ScopeValidator;

public interface ScopeValidationConfigurator {
	ScopeValidator createScopeValidator();

	/**
	 * Returns the configured operation-to-template mapping
	 * @param genModel the {@link GenModel} to consult
	 * @return the operation-to-template mapping
	 */
	TemplateConfigurationProvider createTemplateConfiguration(GenModel genModel);

	/**
	 * Provides a mapping from pattern type (e.g.,
	 * {@link DemoclesMethodBodyHandler#BLACK_FILE_EXTENSION} to an appropriate
	 * {@link PatternMatcher}
	 */
	Map<String, PatternMatcher> getSearchPlanGenerators();
}
