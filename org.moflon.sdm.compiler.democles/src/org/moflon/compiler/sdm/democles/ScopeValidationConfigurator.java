package org.moflon.compiler.sdm.democles;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;
import org.moflon.sdm.compiler.democles.validation.scope.ScopeValidator;

public interface ScopeValidationConfigurator {
	public ScopeValidator createScopeValidator();
	public TemplateConfigurationProvider createTemplateConfiguration(GenModel genModel);
   PatternMatcher getGreenPatternMatcher();
   PatternMatcher getRedPatternMatcher();
   PatternMatcher getBlackPatternMatcher();
   PatternMatcher getBindingPatternMatcher();
   PatternMatcher getBindingAndBlackPatternMatcher();
   default Map<String, PatternMatcher> getSearchPlanGenerators(){
      Map<String,PatternMatcher> searchPlanGenerators = new HashMap<>();
      searchPlanGenerators.put(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION, getGreenPatternMatcher());     
      searchPlanGenerators.put(DemoclesMethodBodyHandler.RED_FILE_EXTENSION, getRedPatternMatcher());     
      searchPlanGenerators.put(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION, getBlackPatternMatcher());     
      searchPlanGenerators.put(DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION, getBindingPatternMatcher());     
      searchPlanGenerators.put(DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION, getBindingAndBlackPatternMatcher());     
      return searchPlanGenerators;
   }
}
