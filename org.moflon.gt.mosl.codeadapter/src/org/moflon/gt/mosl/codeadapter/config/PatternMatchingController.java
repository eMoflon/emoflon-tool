package org.moflon.gt.mosl.codeadapter.config;

import java.util.Map;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.specification.emf.Pattern;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;

public class PatternMatchingController
{
	private final TransformationConfiguration trafoConfig;
   private Map<String, PatternMatcher> searchPlanGenerators;

   public PatternMatchingController(TransformationConfiguration trafoConfig){
	   this.trafoConfig = trafoConfig;
   }
   
   public void setSearchplanGenerators(final Map<String, PatternMatcher> searchPlanGeneratorsByPatternKind)
   {
      this.searchPlanGenerators = searchPlanGeneratorsByPatternKind;
   }
   
   public ValidationReport generateSearchPlan(Pattern pattern, Adornment adornment, boolean isMultipleMatch, String type)
   {
      return searchPlanGenerators.get(type).generateSearchPlan(pattern, adornment, isMultipleMatch);
   }
}
