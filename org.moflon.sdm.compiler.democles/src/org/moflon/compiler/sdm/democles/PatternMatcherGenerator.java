package org.moflon.compiler.sdm.democles;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.compiler.CompilerPattern;
import org.gervarro.democles.compiler.CompilerPatternBody;
import org.gervarro.democles.specification.emf.Pattern;
import org.moflon.compiler.sdm.democles.eclipse.AdapterResource;
import org.moflon.democles.reachability.javabdd.LegacyBDDReachabilityAnalyzer;
import org.moflon.democles.reachability.javabdd.ReachabilityAnalyzer;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.compiler.democles.validation.scope.impl.PatternMatcherImpl;

/**
 * This class is responsible for generating a search plan for patterns
 * 
 * @author Gergely Varró - Initial implementation
 * @author Roland Kluge - Integration of reachability analysis
 *
 */
public abstract class PatternMatcherGenerator extends PatternMatcherImpl
{
   protected PatternMatcherCompiler patternMatcher;

   protected String patternType;

   /**
    * Configures which serach plan generator to use ('patternMatcher') and which pattern type is supported 
    * @param patternMatcher the search plan generator to use
    * @param patternType the pattern type to use (cf. e.g., {@link DefaultCodeGeneratorConfig#BLACK_PATTERN_MATCHER_GENERATOR})
    */
   public PatternMatcherGenerator(final PatternMatcherCompiler patternMatcher, final String patternType)
   {
      this.patternMatcher = patternMatcher;
      this.patternType = patternType;
   }

   /**
    * This method generates a search plan for the given pattern invocations (consisting of a {@link Pattern} and an input {@link Adornment})
    * @param pattern the pattern of the pattern invocation
    * @param adornment the adornment of the pattern invocation
    * @param isMultipleMatch if true, the search plan shall return all matches, if false the search plan shall return any match
    * @return a validation report that may contain errors occurred during the search plan generation 
    */
   @Override
   public ValidationReport generateSearchPlan(final Pattern pattern, final Adornment adornment, final boolean isMultipleMatch)
   {
      final ValidationReport report = ResultFactory.eINSTANCE.createValidationReport();
      try
      {
         final EClass eClass = (EClass) ((AdapterResource) pattern.eResource()).getTarget();
         final CompilerPattern compilerPattern = patternMatcher.compilePattern(pattern, adornment);
         final CompilerPatternBody body = compilerPattern.getBodies().get(0);
         final List<GeneratorOperation> operations = body.getOperations();
         // final ReachabilityAnalyzer reachabilityAnalyzer = new BDDReachabilityAnalyzer<>(operations, adornment);
         //         final ReachabilityAnalyzer reachabilityAnalyzer = new NullReachabilityAnalyzer();
         final ReachabilityAnalyzer reachabilityAnalyzer = new LegacyBDDReachabilityAnalyzer<>(operations, adornment);
         reachabilityAnalyzer.analyzeReachability();
         final boolean isReachable = reachabilityAnalyzer.isReachable(adornment);
         if (isReachable)
         {
            final Chain<GeneratorOperation> searchPlan = PatternMatcherCompiler.generateSearchPlan(body, adornment);
            final SearchPlanAdapter adapter = createSearchPlanAdapter(body, adornment, searchPlan, isMultipleMatch);
            eClass.eAdapters().add(adapter);
         } else
         {
            createAndAddErrorMessage(pattern, report);
         }
      } catch (final RuntimeException e)
      {
         createAndAddErrorMessage(pattern, report);
      }
      return report;
   }

   /**
    * Search plan adapter generation strategy to be implemented by subclasses.
    * @param body the body of the processed {@link Pattern}
    * @param adornment the input {@link Adornment} 
    * @param searchPlan the search plan generated from the {@link CompilerPatternBody} and the {@link Adornment}
    * @param multipleMatches (see isMultipleMatch in {@link #generateSearchPlan(Pattern, Adornment, boolean)})
    * @return
    */
   abstract public SearchPlanAdapter createSearchPlanAdapter(final CompilerPatternBody body, final Adornment adornment, final Chain<GeneratorOperation> searchPlan,
         final boolean multipleMatches);
   
   /**
    * Creates a 'no search plan found' error for the given {@link Pattern} and attaches it to the {@link ValidationReport}.
    * @param pattern the pattern
    * @param report the report
    */
   private void createAndAddErrorMessage(final Pattern pattern, final ValidationReport report)
   {
      final ErrorMessage error = ResultFactory.eINSTANCE.createErrorMessage();
      report.getErrorMessages().add(error);
      error.setId(String.format("No search plan found for pattern '%s'", pattern.getName()));
      error.setSeverity(Severity.ERROR);
   }

}
