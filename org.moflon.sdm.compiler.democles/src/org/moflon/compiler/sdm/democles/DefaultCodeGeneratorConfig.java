package org.moflon.compiler.sdm.democles;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;


public class DefaultCodeGeneratorConfig extends DefaultValidatorConfig {
	public static final String BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR = "BindingAndBlackPatternMatcherGenerator";
	public static final String BINDING_PATTERN_MATCHER_GENERATOR = "BindingPatternMatcherGenerator";
	public static final String BLACK_PATTERN_MATCHER_GENERATOR = "BlackPatternMatcherGenerator";
	public static final String RED_PATTERN_MATCHER_GENERATOR = "RedPatternMatcherGenerator";
	public static final String GREEN_PATTERN_MATCHER_GENERATOR = "GreenPatternMatcherGenerator";
	public static final String EXPRESSION_PATTERN_MATCHER_GENERATOR = "ExpressionPatternMatcherGenerator";

	public DefaultCodeGeneratorConfig(ResourceSet resourceSet) {
		super(resourceSet);
	}

	protected PatternMatcher configureBindingAndBlackPatternMatcher(Resource resource) throws IOException {
		final PatternMatcherCompiler bindingAndBlackPatternMatcherCompiler =
				configureBindingAndBlackPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator bindingAndBlackPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(bindingAndBlackPatternMatcherCompiler, BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR);
		resource.getContents().add(bindingAndBlackPatternMatcherGenerator);
		return bindingAndBlackPatternMatcherGenerator;
	}
	
	protected PatternMatcher configureBindingPatternMatcher(Resource resource) throws IOException {
		final PatternMatcherCompiler bindingPatternMatcherCompiler =
				configureBindingPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator bindingPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(bindingPatternMatcherCompiler, BINDING_PATTERN_MATCHER_GENERATOR);
		resource.getContents().add(bindingPatternMatcherGenerator);
		return bindingPatternMatcherGenerator;
	}

	protected PatternMatcher configureBlackPatternMatcher(Resource resource) throws IOException {
		final PatternMatcherCompiler blackPatternMatcherCompiler =
				configureBlackPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator blackPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(blackPatternMatcherCompiler, BLACK_PATTERN_MATCHER_GENERATOR);
		resource.getContents().add(blackPatternMatcherGenerator);
		return blackPatternMatcherGenerator;
	}

	protected PatternMatcher configureRedPatternMatcher(Resource resource) throws IOException {
		final PatternMatcherCompiler redPatternMatcherCompiler =
				configureRedPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator redPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(redPatternMatcherCompiler, RED_PATTERN_MATCHER_GENERATOR);
		resource.getContents().add(redPatternMatcherGenerator);
		return redPatternMatcherGenerator;
	}

	protected PatternMatcher configureGreenPatternMatcher(Resource resource) throws IOException {
		final PatternMatcherCompiler greenPatternMatcherCompiler =
				configureGreenPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator greenPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(greenPatternMatcherCompiler, GREEN_PATTERN_MATCHER_GENERATOR);
		resource.getContents().add(greenPatternMatcherGenerator);
		return greenPatternMatcherGenerator;
	}

	protected PatternMatcher configureExpressionPatternMatcher(Resource resource) throws IOException {
		final PatternMatcherCompiler expressionPatternMatcherCompiler =
				configureExpressionPatternMatcherCompiler(resource);
		final ExpressionPatternMatcherGenerator expressionPatternMatcherGenerator =
				new ExpressionPatternMatcherGenerator(expressionPatternMatcherCompiler, EXPRESSION_PATTERN_MATCHER_GENERATOR);
		resource.getContents().add(expressionPatternMatcherGenerator);
		return expressionPatternMatcherGenerator;
	}
}
