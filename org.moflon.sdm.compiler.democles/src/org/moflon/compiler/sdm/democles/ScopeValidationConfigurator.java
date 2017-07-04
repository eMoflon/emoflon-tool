package org.moflon.compiler.sdm.democles;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.specification.emf.Pattern;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
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
   default Map<String,Function<Pattern, Function<Adornment,Function<Boolean, ValidationReport>>>> getSearchPlanGenerators(){
      Map<String,Function<Pattern, Function<Adornment,Function<Boolean, ValidationReport>>>> searchPlanGenerators = new HashMap<>();
      searchPlanGenerators.put(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION, pattern->adornment->isMultipleMatch->getGreenPatternMatcher().generateSearchPlan(pattern, adornment, isMultipleMatch));     
      searchPlanGenerators.put(DemoclesMethodBodyHandler.RED_FILE_EXTENSION, pattern->adornment->isMultipleMatch->getRedPatternMatcher().generateSearchPlan(pattern, adornment, isMultipleMatch));     
      searchPlanGenerators.put(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION, pattern->adornment->isMultipleMatch->getBlackPatternMatcher().generateSearchPlan(pattern, adornment, isMultipleMatch));     
      searchPlanGenerators.put(DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION, pattern->adornment->isMultipleMatch->getBindingPatternMatcher().generateSearchPlan(pattern, adornment, isMultipleMatch));     
      searchPlanGenerators.put(DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION, pattern->adornment->isMultipleMatch->getBindingAndBlackPatternMatcher().generateSearchPlan(pattern, adornment, isMultipleMatch));     
      return searchPlanGenerators;
   }
}
