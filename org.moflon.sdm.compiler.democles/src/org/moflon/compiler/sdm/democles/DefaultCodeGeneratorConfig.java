package org.moflon.compiler.sdm.democles;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.preferences.EMoflonPreferencesStorage;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;

public class DefaultCodeGeneratorConfig extends DefaultValidatorConfig {
	// Start of pattern types
	public static final String BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR = "BindingAndBlackPatternMatcherGenerator";
	public static final String BINDING_PATTERN_MATCHER_GENERATOR = "BindingPatternMatcherGenerator";
	public static final String BLACK_PATTERN_MATCHER_GENERATOR = "BlackPatternMatcherGenerator";
	public static final String RED_PATTERN_MATCHER_GENERATOR = "RedPatternMatcherGenerator";
	public static final String GREEN_PATTERN_MATCHER_GENERATOR = "GreenPatternMatcherGenerator";
	public static final String EXPRESSION_PATTERN_MATCHER_GENERATOR = "ExpressionPatternMatcherGenerator";
	// End of pattern types

	public DefaultCodeGeneratorConfig(final ResourceSet resourceSet,
			final EMoflonPreferencesStorage preferencesStorage) {
		super(resourceSet, preferencesStorage);
	}

	@Override
	protected PatternMatcher configureBindingAndBlackPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler bindingAndBlackPatternMatcherCompiler = configureBindingAndBlackPatternMatcherCompiler(
				resource);
		final RegularPatternMatcherGenerator bindingAndBlackPatternMatcherGenerator = new RegularPatternMatcherGenerator(
				bindingAndBlackPatternMatcherCompiler, BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR,
				getPreferencesStorage());
		resource.getContents().add(bindingAndBlackPatternMatcherGenerator);
		return bindingAndBlackPatternMatcherGenerator;
	}

	@Override
	protected PatternMatcher configureBindingPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler bindingPatternMatcherCompiler = configureBindingPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator bindingPatternMatcherGenerator = new RegularPatternMatcherGenerator(
				bindingPatternMatcherCompiler, BINDING_PATTERN_MATCHER_GENERATOR, getPreferencesStorage());
		resource.getContents().add(bindingPatternMatcherGenerator);
		return bindingPatternMatcherGenerator;
	}

	@Override
	protected PatternMatcher configureBlackPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler blackPatternMatcherCompiler = configureBlackPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator blackPatternMatcherGenerator = new RegularPatternMatcherGenerator(
				blackPatternMatcherCompiler, BLACK_PATTERN_MATCHER_GENERATOR, getPreferencesStorage());
		resource.getContents().add(blackPatternMatcherGenerator);
		return blackPatternMatcherGenerator;
	}

	@Override
	protected PatternMatcher configureRedPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler redPatternMatcherCompiler = configureRedPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator redPatternMatcherGenerator = new RegularPatternMatcherGenerator(
				redPatternMatcherCompiler, RED_PATTERN_MATCHER_GENERATOR, getPreferencesStorage());
		resource.getContents().add(redPatternMatcherGenerator);
		return redPatternMatcherGenerator;
	}

	@Override
	protected PatternMatcher configureGreenPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler greenPatternMatcherCompiler = configureGreenPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator greenPatternMatcherGenerator = new RegularPatternMatcherGenerator(
				greenPatternMatcherCompiler, GREEN_PATTERN_MATCHER_GENERATOR, getPreferencesStorage());
		resource.getContents().add(greenPatternMatcherGenerator);
		return greenPatternMatcherGenerator;
	}

	@Override
	protected PatternMatcher configureExpressionPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler expressionPatternMatcherCompiler = configureExpressionPatternMatcherCompiler(
				resource);
		final ExpressionPatternMatcherGenerator expressionPatternMatcherGenerator = new ExpressionPatternMatcherGenerator(
				expressionPatternMatcherCompiler, EXPRESSION_PATTERN_MATCHER_GENERATOR, getPreferencesStorage());
		resource.getContents().add(expressionPatternMatcherGenerator);
		return expressionPatternMatcherGenerator;
	}
}
