package org.moflon.compiler.sdm.democles;

import org.eclipse.emf.ecore.EClass;
import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.compiler.CompilerPattern;
import org.gervarro.democles.compiler.CompilerPatternBody;
import org.gervarro.democles.specification.emf.Pattern;
import org.moflon.compiler.sdm.democles.eclipse.AdapterResource;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.compiler.democles.validation.scope.impl.PatternMatcherImpl;

abstract public class PatternMatcherGenerator extends PatternMatcherImpl {
	PatternMatcherCompiler patternMatcher;
	String patternType;
	
	public PatternMatcherGenerator(PatternMatcherCompiler delegate,
			final String patternType) {
		this.patternMatcher = delegate;
		this.patternType = patternType;
	}
	
	public ValidationReport generateSearchPlan(Pattern pattern, Adornment adornment, boolean isMultipleMatch) {
		ValidationReport report = ResultFactory.eINSTANCE.createValidationReport();
		try {
			EClass eClass = (EClass) ((AdapterResource) pattern.eResource()).getTarget();

			CompilerPattern compilerPattern = patternMatcher.compilePattern(pattern, adornment);
			CompilerPatternBody body = compilerPattern.getBodies().get(0);
			Chain<GeneratorOperation> searchPlan = PatternMatcherCompiler.generateSearchPlan(body, adornment);
			SearchPlanAdapter adapter = createSearchPlanAdapter(body, adornment, searchPlan, isMultipleMatch);
			eClass.eAdapters().add(adapter);
		} catch (RuntimeException e) {
			ErrorMessage error = ResultFactory.eINSTANCE.createErrorMessage();
			report.getErrorMessages().add(error);
			error.setId("No search plan found for pattern");
			error.setSeverity(Severity.ERROR);
		}
		return report;
	}
	
	abstract public SearchPlanAdapter createSearchPlanAdapter(CompilerPatternBody body, Adornment adornment,
			Chain<GeneratorOperation> searchPlan, boolean multipleMatches);
}
