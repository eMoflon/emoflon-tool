package org.moflon.compiler.sdm.democles;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.moflon.sdm.compiler.democles.validation.scope.ScopeValidator;

public interface ScopeValidationConfigurator {
	public ScopeValidator createScopeValidator();
	public TemplateConfigurationProvider createTemplateConfiguration(GenModel genModel);
}
