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
		return createAndRegisterRegularPatternMatcherGenerator(bindingAndBlackPatternMatcherCompiler,
				BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR, resource);
	}

	@Override
	protected PatternMatcher configureBindingPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler bindingPatternMatcherCompiler = configureBindingPatternMatcherCompiler(resource);
		return createAndRegisterRegularPatternMatcherGenerator(bindingPatternMatcherCompiler,
				BINDING_PATTERN_MATCHER_GENERATOR, resource);
	}

	@Override
	protected PatternMatcher configureBlackPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler blackPatternMatcherCompiler = configureBlackPatternMatcherCompiler(resource);
		return createAndRegisterRegularPatternMatcherGenerator(blackPatternMatcherCompiler,
				BLACK_PATTERN_MATCHER_GENERATOR, resource);
	}

	@Override
	protected PatternMatcher configureRedPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler redPatternMatcherCompiler = configureRedPatternMatcherCompiler(resource);
		return createAndRegisterRegularPatternMatcherGenerator(redPatternMatcherCompiler, RED_PATTERN_MATCHER_GENERATOR,
				resource);
	}

	@Override
	protected PatternMatcher configureGreenPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler greenPatternMatcherCompiler = configureGreenPatternMatcherCompiler(resource);
		return createAndRegisterRegularPatternMatcherGenerator(greenPatternMatcherCompiler,
				GREEN_PATTERN_MATCHER_GENERATOR, resource);
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

	/**
	 * Creates a {@link RegularPatternMatcherGenerator} from the given
	 * {@link PatternMatcherCompiler} and registers it a the {@link Resource}
	 *
	 * @param patternMatcherCompiler
	 * @param generatorName
	 * @param resource
	 * @return
	 */
	private PatternMatcher createAndRegisterRegularPatternMatcherGenerator(
			final PatternMatcherCompiler patternMatcherCompiler, String generatorName, final Resource resource) {
		final RegularPatternMatcherGenerator bindingAndBlackPatternMatcherGenerator = new RegularPatternMatcherGenerator(
				patternMatcherCompiler, generatorName, getPreferencesStorage());
		resource.getContents().add(bindingAndBlackPatternMatcherGenerator);
		return bindingAndBlackPatternMatcherGenerator;
	}
}
