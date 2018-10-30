package org.moflon.compiler.sdm.democles.attributes;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.SimpleCombiner;
import org.gervarro.democles.compiler.CompilerPatternBuilder;
import org.gervarro.democles.plan.WeightedOperationBuilder;
import org.gervarro.democles.plan.common.DefaultAlgorithm;
import org.moflon.compiler.sdm.democles.DefaultCodeGeneratorConfig;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.compiler.sdm.democles.PatternMatcherCompiler;
import org.moflon.compiler.sdm.democles.TemplateConfigurationProvider;
import org.moflon.core.preferences.EMoflonPreferencesStorage;
import org.moflon.sdm.compiler.democles.pattern.BindingPatternTransformer;
import org.moflon.sdm.compiler.democles.pattern.DefaultExpressionTransformer;
import org.moflon.sdm.compiler.democles.pattern.GreenPatternTransformer;
import org.moflon.sdm.compiler.democles.pattern.LiteralExpressionTransformer;
import org.moflon.sdm.compiler.democles.pattern.NacPatternTransformer;
import org.moflon.sdm.compiler.democles.pattern.PatternFactory;
import org.moflon.sdm.compiler.democles.pattern.PatternTransformer;
import org.moflon.sdm.compiler.democles.pattern.RedPatternTransformer;
import org.moflon.sdm.compiler.democles.validation.scope.BindingAndBlackPatternBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.BindingExpressionBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.BlackPatternBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.ExpressionExplorer;
import org.moflon.sdm.compiler.democles.validation.scope.NacPatternBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.RedNodeDeletionBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.RedPatternBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.RegularPatternInvocationBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.ScopeFactory;
import org.moflon.sdm.compiler.democles.validation.scope.ScopeValidator;
import org.moflon.sdm.compiler.democles.validation.scope.SingleResultPatternInvocationBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.StoryNodeActionBuilder;
import org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer;
import org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesFactory;
import org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintLibUtilImpl;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.constraint.AttributeVariableConstraintsTypeModule;
import org.moflon.sdm.constraints.operationspecification.constraint.util.AttributeVariableConstraintsModule;
import org.moflon.sdm.constraints.scopevalidation.ScopevalidationFactory;
import org.moflon.sdm.democles.literalexpressionsolver.ConstantTransformer;
import org.moflon.sdm.democles.literalexpressionsolver.LiteralexpressionsolverFactory;

public class AttributeConstraintCodeGeneratorConfig extends DefaultCodeGeneratorConfig {
	protected final WeightedOperationBuilder<GeneratorOperation> builder = new AttributeEnabledWeightedOperationBuilder<GeneratorOperation>();

	protected final DefaultAlgorithm<SimpleCombiner, GeneratorOperation> algorithm = new DefaultAlgorithm<SimpleCombiner, GeneratorOperation>(
			builder);

	// Constraint modules
	// private final AttributeVariableConstraintLibrary
	// attributeVariableConstraintLibrary;
	final AttributeVariableConstraintsModule attributeVariableConstraintsModule;

	// Operation modules (constraint to operation (constraint + adornment) mappings)
	private final AttributeConstraintsOperationBuilder attributeConstraintsOperationBuilder = new AttributeConstraintsOperationBuilder();

	// Constraint libraies
	private List<AttributeConstraintLibrary> attributeVariableConstraintLibraries = new LinkedList<AttributeConstraintLibrary>();

	// Constraint type module
	private final AttributeVariableConstraintsTypeModule attributeVariableConstraintsTypeModule;

	protected AttributeConstraintLibUtilImpl attributeConstraintLibUtil = (AttributeConstraintLibUtilImpl) ConstraintstodemoclesFactory.eINSTANCE
			.createAttributeConstraintLibUtil();

	public AttributeConstraintCodeGeneratorConfig(final ResourceSet resourceSet, final IProject project,
			final EMoflonPreferencesStorage preferencesStorage) {
		super(resourceSet, preferencesStorage);
		if (project == null) {
			throw new RuntimeException(
					"Parameter ecoreResource must be defined for AttributeConstraintCodeGeneratorConfig");
		}

		// load attribute constraint libraries first loaded has higher priority
		attributeConstraintLibUtil.init(resourceSet, project);
		if (attributeConstraintLibUtil.getUserDefinedAttributeConstraintLibrary() != null) {
			attributeVariableConstraintLibraries
					.add(attributeConstraintLibUtil.getUserDefinedAttributeConstraintLibrary());
		}
		attributeVariableConstraintLibraries.add(attributeConstraintLibUtil.getBuildInAttributeConstraintLibrary());

		// create attribute variable constraints type module using constraint libraries
		attributeVariableConstraintsTypeModule = new AttributeVariableConstraintsTypeModule(
				attributeVariableConstraintLibraries);

		this.attributeVariableConstraintsModule = new AttributeVariableConstraintsModule(
				attributeVariableConstraintsTypeModule);
		this.bindingAndBlackPatternBuilder
				.addConstraintTypeSwitch(attributeVariableConstraintsModule.getConstraintTypeSwitch());
	}

	@Override
	public ScopeValidator createScopeValidator() {
		final Resource resource = new ResourceImpl(URI.createURI("ScopeValidator"));
		resourceSet.getResources().add(resource);
		final ScopeValidator scopeValidator = ScopeFactory.eINSTANCE.createScopeValidator();
		resource.getContents().add(scopeValidator);

		try {
			final Resource expressionTransformerResource = new ResourceImpl(URI.createURI("ExpressionHandler"));
			resourceSet.getResources().add(expressionTransformerResource);
			final ConstantTransformer constantTransformer = LiteralexpressionsolverFactory.eINSTANCE
					.createConstantTransformer();
			expressionTransformerResource.getContents().add(constantTransformer);
			final LiteralExpressionTransformer literalExpressionTransformer = PatternFactory.eINSTANCE
					.createLiteralExpressionTransformer();
			expressionTransformerResource.getContents().add(literalExpressionTransformer);
			literalExpressionTransformer.setConstantTransformer(constantTransformer);
			final DefaultExpressionTransformer expressionTransformer = PatternFactory.eINSTANCE
					.createDefaultExpressionTransformer();
			expressionTransformerResource.getContents().add(expressionTransformer);
			expressionTransformer.setDelegate(literalExpressionTransformer);
			final ExpressionExplorer expressionExplorer = ScopeFactory.eINSTANCE.createExpressionExplorer();
			expressionTransformerResource.getContents().add(expressionExplorer);
			expressionExplorer.setExpressionTransformer(expressionTransformer);

			this.setBindingAndBlackPatternMatcher(configureBindingAndBlackPatternMatcher(resource));
			this.setBindingPatternMatcher(configureBindingPatternMatcher(resource));
			this.setBlackPatternMatcher(configureBlackPatternMatcher(resource));
			this.setRedPatternMatcher(configureRedPatternMatcher(resource));
			this.setGreenPatternMatcher(configureGreenPatternMatcher(resource));
			this.setExpressionPatternMatcher(configureExpressionPatternMatcher(resource));

			// (1) Handler for regular story nodes
			final StoryNodeActionBuilder regularStoryNodeActionBuilder = ScopeFactory.eINSTANCE
					.createStoryNodeActionBuilder();
			scopeValidator.getActionBuilders().add(regularStoryNodeActionBuilder);
			regularStoryNodeActionBuilder.setRequiresForEach(false);

			final BindingAndBlackPatternBuilder regularBindingAndBlackInvocationBuilder = ScopeFactory.eINSTANCE
					.createBindingAndBlackPatternBuilder();
			regularStoryNodeActionBuilder.getChildren().add(regularBindingAndBlackInvocationBuilder);
			regularBindingAndBlackInvocationBuilder
					.setSuffix(DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION);
			regularBindingAndBlackInvocationBuilder.setMainActionBuilder(true);
			regularBindingAndBlackInvocationBuilder
					.setPatternMatcher(this.getBindingAndBlackPatternSearchPlanGenerator());

			final BindingExpressionBuilder regularBindingExpressionBuilder = ScopeFactory.eINSTANCE
					.createBindingExpressionBuilder();
			regularBindingAndBlackInvocationBuilder.getChildBuilders().add(regularBindingExpressionBuilder);
			final BindingPatternTransformer regularBindingPatternTransformer = PatternFactory.eINSTANCE
					.createBindingPatternTransformer();
			regularBindingExpressionBuilder.setPatternTransformer(regularBindingPatternTransformer);
			regularBindingExpressionBuilder.setExpressionExplorer(expressionExplorer);
			regularBindingExpressionBuilder.setSuffix(DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION);
			regularBindingExpressionBuilder.setMainActionBuilder(false);
			regularBindingExpressionBuilder.setPatternMatcher(this.getBindingPatternSearchPlanGenerator());
			regularBindingPatternTransformer.setExpressionTransformer(expressionTransformer);

			final BlackPatternBuilder regularBlackInvocationBuilder = ScopevalidationFactory.eINSTANCE
					.createAttributeConstraintBlackPatternInvocationBuilder();
			regularBindingAndBlackInvocationBuilder.getChildBuilders().add(regularBlackInvocationBuilder);
			regularBindingAndBlackInvocationBuilder.setBlackPatternBuilder(regularBlackInvocationBuilder);
			final AttributeConstraintBlackAndNacPatternTransformer regularBlackPatternTransformer = ConstraintstodemoclesFactory.eINSTANCE
					.createAttributeConstraintBlackAndNacPatternTransformer();
			regularBlackPatternTransformer.setAttributeConstraintLibUtil(attributeConstraintLibUtil);
			regularBlackInvocationBuilder.setPatternTransformer(regularBlackPatternTransformer);
			regularBlackInvocationBuilder.setExpressionExplorer(expressionExplorer);
			regularBlackInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION);
			regularBlackInvocationBuilder.setMainActionBuilder(true);
			regularBlackInvocationBuilder.setPatternMatcher(this.getBlackPatternSearchPlanGenerator());
			regularBlackPatternTransformer.setExpressionTransformer(expressionTransformer);

			final NacPatternBuilder regularNacPatternBuilder = ScopeFactory.eINSTANCE.createNacPatternBuilder();
			regularBlackInvocationBuilder.getChildBuilders().add(regularNacPatternBuilder);
			final NacPatternTransformer regularNacPatternTransformer = PatternFactory.eINSTANCE
					.createNacPatternTransformer();
			regularNacPatternBuilder.setPatternTransformer(regularNacPatternTransformer);
			regularNacPatternBuilder.setExpressionExplorer(expressionExplorer);
			regularNacPatternBuilder.setMainActionBuilder(false);
			regularNacPatternBuilder.setPatternMatcher(this.getBlackPatternSearchPlanGenerator());
			regularNacPatternTransformer.setExpressionTransformer(expressionTransformer);

			final RedPatternBuilder regularRedInvocationBuilder = ScopeFactory.eINSTANCE.createRedPatternBuilder();
			regularStoryNodeActionBuilder.getChildren().add(regularRedInvocationBuilder);
			final RedPatternTransformer regularRedPatternTransformer = PatternFactory.eINSTANCE
					.createRedPatternTransformer();
			regularRedInvocationBuilder.setPatternTransformer(regularRedPatternTransformer);
			regularRedInvocationBuilder.setExpressionExplorer(expressionExplorer);
			regularRedInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.RED_FILE_EXTENSION);
			regularRedInvocationBuilder.setMainActionBuilder(false);
			regularRedInvocationBuilder.setPatternMatcher(this.getRedPatternSearchPlanGenerator());
			regularRedPatternTransformer.setExpressionTransformer(expressionTransformer);

			final RegularPatternInvocationBuilder regularGreenInvocationBuilder = ScopevalidationFactory.eINSTANCE
					.createAttributeConstraintGreenPatternInvocationBuilder();
			regularStoryNodeActionBuilder.getChildren().add(regularGreenInvocationBuilder);
			final GreenPatternTransformer regularGreenPatternTransformer = ConstraintstodemoclesFactory.eINSTANCE
					.createAttributeConstraintGreenPatternTransformer();
			regularGreenInvocationBuilder.setPatternTransformer(regularGreenPatternTransformer);
			regularGreenInvocationBuilder.setExpressionExplorer(expressionExplorer);
			regularGreenInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION);
			regularGreenInvocationBuilder.setMainActionBuilder(true);
			regularGreenInvocationBuilder.setPatternMatcher(this.getGreenPatternSearchPlanGenerator());
			regularGreenPatternTransformer.setExpressionTransformer(expressionTransformer);

			final RedNodeDeletionBuilder regularRedNodeDeletionBuilder = ScopeFactory.eINSTANCE
					.createRedNodeDeletionBuilder();
			regularStoryNodeActionBuilder.getChildren().add(regularRedNodeDeletionBuilder);

			// (2) Handler for ForEach story nodes
			final StoryNodeActionBuilder forEachStoryNodeActionBuilder = ScopeFactory.eINSTANCE
					.createStoryNodeActionBuilder();
			scopeValidator.getActionBuilders().add(forEachStoryNodeActionBuilder);
			forEachStoryNodeActionBuilder.setRequiresForEach(true);

			final BindingExpressionBuilder forEachBindingExpressionBuilder = ScopeFactory.eINSTANCE
					.createBindingExpressionBuilder();
			forEachStoryNodeActionBuilder.getChildren().add(forEachBindingExpressionBuilder);
			final BindingPatternTransformer forEachBindingPatternTransformer = PatternFactory.eINSTANCE
					.createBindingPatternTransformer();
			forEachBindingExpressionBuilder.setPatternTransformer(forEachBindingPatternTransformer);
			forEachBindingExpressionBuilder.setExpressionExplorer(expressionExplorer);
			forEachBindingExpressionBuilder.setSuffix(DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION);
			forEachBindingExpressionBuilder.setMainActionBuilder(false);
			forEachBindingExpressionBuilder.setPatternMatcher(this.getBindingPatternSearchPlanGenerator());
			forEachBindingPatternTransformer.setExpressionTransformer(expressionTransformer);

			final BlackPatternBuilder forEachBlackInvocationBuilder = ScopevalidationFactory.eINSTANCE
					.createAttributeConstraintBlackPatternInvocationBuilder();
			forEachStoryNodeActionBuilder.getChildren().add(forEachBlackInvocationBuilder);
			final AttributeConstraintBlackAndNacPatternTransformer forEachBlackPatternTransformer = ConstraintstodemoclesFactory.eINSTANCE
					.createAttributeConstraintBlackAndNacPatternTransformer();
			forEachBlackPatternTransformer.setAttributeConstraintLibUtil(attributeConstraintLibUtil);
			forEachBlackInvocationBuilder.setPatternTransformer(forEachBlackPatternTransformer);
			forEachBlackInvocationBuilder.setExpressionExplorer(expressionExplorer);
			forEachBlackInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION);
			forEachBlackInvocationBuilder.setMainActionBuilder(true);
			forEachBlackInvocationBuilder.setPatternMatcher(this.getBlackPatternSearchPlanGenerator());
			forEachBlackPatternTransformer.setExpressionTransformer(expressionTransformer);

			final NacPatternBuilder forEachNacPatternBuilder = ScopeFactory.eINSTANCE.createNacPatternBuilder();
			forEachBlackInvocationBuilder.getChildBuilders().add(forEachNacPatternBuilder);
			final NacPatternTransformer forEachNacPatternTransformer = PatternFactory.eINSTANCE
					.createNacPatternTransformer();
			forEachNacPatternBuilder.setPatternTransformer(forEachNacPatternTransformer);
			forEachNacPatternBuilder.setExpressionExplorer(expressionExplorer);
			forEachNacPatternBuilder.setMainActionBuilder(false);
			forEachNacPatternBuilder.setPatternMatcher(this.getBlackPatternSearchPlanGenerator());
			forEachNacPatternTransformer.setExpressionTransformer(expressionTransformer);

			final RedPatternBuilder forEachRedInvocationBuilder = ScopeFactory.eINSTANCE.createRedPatternBuilder();
			forEachStoryNodeActionBuilder.getChildren().add(forEachRedInvocationBuilder);
			final RedPatternTransformer forEachRedPatternTransformer = PatternFactory.eINSTANCE
					.createRedPatternTransformer();
			forEachRedInvocationBuilder.setPatternTransformer(forEachRedPatternTransformer);
			forEachRedInvocationBuilder.setExpressionExplorer(expressionExplorer);
			forEachRedInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.RED_FILE_EXTENSION);
			forEachRedInvocationBuilder.setMainActionBuilder(false);
			forEachRedInvocationBuilder.setPatternMatcher(this.getRedPatternSearchPlanGenerator());
			forEachRedPatternTransformer.setExpressionTransformer(expressionTransformer);

			final RegularPatternInvocationBuilder forEachGreenInvocationBuilder = ScopevalidationFactory.eINSTANCE
					.createAttributeConstraintGreenPatternInvocationBuilder();
			forEachStoryNodeActionBuilder.getChildren().add(forEachGreenInvocationBuilder);
			final GreenPatternTransformer forEachGreenPatternTransformer = ConstraintstodemoclesFactory.eINSTANCE
					.createAttributeConstraintGreenPatternTransformer();
			forEachGreenInvocationBuilder.setPatternTransformer(forEachGreenPatternTransformer);
			forEachGreenInvocationBuilder.setExpressionExplorer(expressionExplorer);
			forEachGreenInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION);
			forEachGreenInvocationBuilder.setMainActionBuilder(true);
			forEachGreenInvocationBuilder.setPatternMatcher(this.getGreenPatternSearchPlanGenerator());
			forEachGreenPatternTransformer.setExpressionTransformer(expressionTransformer);

			final RedNodeDeletionBuilder forEachRedNodeDeletionBuilder = ScopeFactory.eINSTANCE
					.createRedNodeDeletionBuilder();
			forEachStoryNodeActionBuilder.getChildren().add(forEachRedNodeDeletionBuilder);

			// (3) Handler for statement and stop nodes
			final SingleResultPatternInvocationBuilder expressionActionBuilder = ScopeFactory.eINSTANCE
					.createSingleResultPatternInvocationBuilder();
			scopeValidator.getActionBuilders().add(expressionActionBuilder);
			final PatternTransformer expressionPatternTransformer = PatternFactory.eINSTANCE.createPatternTransformer();
			expressionActionBuilder.setPatternVariableHandler(expressionPatternTransformer);
			expressionActionBuilder.setExpressionExplorer(expressionExplorer);
			expressionActionBuilder.setSuffix(DemoclesMethodBodyHandler.EXPRESSION_FILE_EXTENSION);
			expressionActionBuilder.setPatternMatcher(this.getExpressionPatternSearchPlanGenerator());
			expressionPatternTransformer.setExpressionTransformer(expressionTransformer);
		} catch (final IOException e) {
			// Do nothing
		}
		return scopeValidator;

	}

	@Override
	protected PatternMatcherCompiler configureBlackPatternMatcherCompiler(final Resource resource) {
		final CompilerPatternBuilder blackCompilerPatternBuilder = new CompilerPatternBuilder();
		blackCompilerPatternBuilder.addOperationBuilder(emfBlackOperationBuilder);
		blackCompilerPatternBuilder.addOperationBuilder(relationalOperationBuilder);
		blackCompilerPatternBuilder.addOperationBuilder(attributeConstraintsOperationBuilder);
		blackCompilerPatternBuilder.setAlgorithm(algorithm);

		final PatternMatcherCompiler blackPatternMatcherCompiler = new PatternMatcherCompiler(
				bindingAndBlackPatternBuilder, blackCompilerPatternBuilder);
		resource.getContents().add(blackPatternMatcherCompiler);
		return blackPatternMatcherCompiler;
	}

	@Override
	public TemplateConfigurationProvider createTemplateConfiguration(final GenModel genModel) {
		return new AttributeConstraintTemplateConfig(genModel, attributeVariableConstraintLibraries);
	}
}
