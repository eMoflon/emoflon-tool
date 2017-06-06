package org.moflon.compiler.sdm.democles;

import java.io.IOException;
import java.util.Arrays;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.preferences.EMoflonPreferencesStorage;
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
   private final EMoflonPreferencesStorage preferencesStorage;
   private final boolean isMoslGT;

	public DefaultCodeGeneratorConfig(ResourceSet resourceSet, IResource resourceContext) {
		super(resourceSet);
		boolean tmpMoslGT;
		try
      {
		   IProject project = resourceContext.getProject();
         IProjectDescription ipd =project.getDescription();
         tmpMoslGT = Arrays.asList(ipd.getNatureIds()).stream().filter(ids -> ids.compareTo(WorkspaceHelper.MOSL_GT_NATURE_ID) == 0).findFirst().isPresent();
         
      } catch (CoreException | NullPointerException e)
      {
         tmpMoslGT = false;
      }
		isMoslGT = tmpMoslGT;
		this.preferencesStorage = EMoflonPreferencesStorage.getInstance();
	}

	@Override
	protected PatternMatcher configureBindingAndBlackPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler bindingAndBlackPatternMatcherCompiler =
				configureBindingAndBlackPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator bindingAndBlackPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(bindingAndBlackPatternMatcherCompiler, BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR, preferencesStorage);
		resource.getContents().add(bindingAndBlackPatternMatcherGenerator);
		return bindingAndBlackPatternMatcherGenerator;
	}

   @Override
	protected PatternMatcher configureBindingPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler bindingPatternMatcherCompiler =
				configureBindingPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator bindingPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(bindingPatternMatcherCompiler, BINDING_PATTERN_MATCHER_GENERATOR, preferencesStorage);
		resource.getContents().add(bindingPatternMatcherGenerator);
		return bindingPatternMatcherGenerator;
	}

   @Override
	protected PatternMatcher configureBlackPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler blackPatternMatcherCompiler =
				configureBlackPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator blackPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(blackPatternMatcherCompiler, BLACK_PATTERN_MATCHER_GENERATOR, preferencesStorage);
		resource.getContents().add(blackPatternMatcherGenerator);
		return blackPatternMatcherGenerator;
	}

   @Override
	protected PatternMatcher configureRedPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler redPatternMatcherCompiler =
				configureRedPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator redPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(redPatternMatcherCompiler, RED_PATTERN_MATCHER_GENERATOR, preferencesStorage);
		resource.getContents().add(redPatternMatcherGenerator);
		return redPatternMatcherGenerator;
	}

   @Override
	protected PatternMatcher configureGreenPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler greenPatternMatcherCompiler =
				configureGreenPatternMatcherCompiler(resource);
		final RegularPatternMatcherGenerator greenPatternMatcherGenerator =
				new RegularPatternMatcherGenerator(greenPatternMatcherCompiler, GREEN_PATTERN_MATCHER_GENERATOR, preferencesStorage);
		resource.getContents().add(greenPatternMatcherGenerator);
		return greenPatternMatcherGenerator;
	}

   @Override
	protected PatternMatcher configureExpressionPatternMatcher(final Resource resource) throws IOException {
		final PatternMatcherCompiler expressionPatternMatcherCompiler =
				configureExpressionPatternMatcherCompiler(resource);
		final ExpressionPatternMatcherGenerator expressionPatternMatcherGenerator =
				new ExpressionPatternMatcherGenerator(expressionPatternMatcherCompiler, EXPRESSION_PATTERN_MATCHER_GENERATOR, preferencesStorage);
		resource.getContents().add(expressionPatternMatcherGenerator);
		return expressionPatternMatcherGenerator;
	}
}
