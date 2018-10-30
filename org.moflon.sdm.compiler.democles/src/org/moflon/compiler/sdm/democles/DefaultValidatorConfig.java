package org.moflon.compiler.sdm.democles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.SimpleCombiner;
import org.gervarro.democles.codegen.emf.BasicEMFOperationBuilder;
import org.gervarro.democles.codegen.emf.EMFOperationBuilder;
import org.gervarro.democles.compiler.CompilerPatternBuilder;
import org.gervarro.democles.constraint.CoreConstraintModule;
import org.gervarro.democles.constraint.emf.EMFConstraintModule;
import org.gervarro.democles.emf.EMFWeightedOperationBuilder;
import org.gervarro.democles.plan.WeightedOperationBuilder;
import org.gervarro.democles.plan.common.DefaultAlgorithm;
import org.gervarro.democles.relational.RelationalOperationBuilder;
import org.gervarro.democles.specification.emf.EMFPatternBuilder;
import org.gervarro.democles.specification.emf.constraint.EMFTypeModule;
import org.gervarro.democles.specification.emf.constraint.PatternInvocationTypeModule;
import org.gervarro.democles.specification.emf.constraint.RelationalTypeModule;
import org.gervarro.democles.specification.impl.DefaultPattern;
import org.gervarro.democles.specification.impl.DefaultPatternBody;
import org.gervarro.democles.specification.impl.DefaultPatternFactory;
import org.gervarro.democles.specification.impl.PatternInvocationConstraintModule;
import org.moflon.core.preferences.EMoflonPreferencesStorage;
import org.moflon.sdm.compiler.democles.pattern.BindingPatternTransformer;
import org.moflon.sdm.compiler.democles.pattern.BlackAndNacPatternTransformer;
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
import org.moflon.sdm.compiler.democles.validation.scope.GreenPatternBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.NacPatternBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;
import org.moflon.sdm.compiler.democles.validation.scope.RedNodeDeletionBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.RedPatternBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.ScopeFactory;
import org.moflon.sdm.compiler.democles.validation.scope.ScopeValidator;
import org.moflon.sdm.compiler.democles.validation.scope.SingleResultPatternInvocationBuilder;
import org.moflon.sdm.compiler.democles.validation.scope.StoryNodeActionBuilder;
import org.moflon.sdm.democles.literalexpressionsolver.ConstantTransformer;
import org.moflon.sdm.democles.literalexpressionsolver.LiteralexpressionsolverFactory;

public class DefaultValidatorConfig implements ScopeValidationConfigurator {
	protected final ResourceSet resourceSet;

	private final WeightedOperationBuilder<GeneratorOperation> builder = new EMFWeightedOperationBuilder<GeneratorOperation>();
	private final DefaultAlgorithm<SimpleCombiner, GeneratorOperation> algorithm = new DefaultAlgorithm<SimpleCombiner, GeneratorOperation>(
			builder);

	// Constraint modules
	final EMFConstraintModule emfTypeModule;
	final EMFTypeModule internalEMFTypeModule;
	final RelationalTypeModule internalRelationalTypeModule = new RelationalTypeModule(CoreConstraintModule.INSTANCE);
	protected final EMFPatternBuilder<DefaultPattern, DefaultPatternBody> bindingAndBlackPatternBuilder = new EMFPatternBuilder<DefaultPattern, DefaultPatternBody>(
			new DefaultPatternFactory());
	final PatternInvocationConstraintModule<DefaultPattern, DefaultPatternBody> bindingAndBlackPatternInvocationTypeModule = new PatternInvocationConstraintModule<DefaultPattern, DefaultPatternBody>(
			bindingAndBlackPatternBuilder);
	final PatternInvocationTypeModule<DefaultPattern, DefaultPatternBody> internalPatternInvocationTypeModule = new PatternInvocationTypeModule<DefaultPattern, DefaultPatternBody>(
			bindingAndBlackPatternInvocationTypeModule);

	// Operation modules (constraint to operation (constraint + adornment) mappings)
	protected final RelationalOperationBuilder relationalOperationBuilder = new RelationalOperationBuilder();
	private final AssignmentOperationBuilder assignmentOperationBuilder = new AssignmentOperationBuilder();
	private final BindingAssignmentOperationBuilder bindingAssignmentOperationBuilder = new BindingAssignmentOperationBuilder();
	private final BasicEMFOperationBuilder basicOperationBuilder = new BasicEMFOperationBuilder();
	protected final EMFOperationBuilder emfBlackOperationBuilder = new EMFOperationBuilder();
	private final EMFRedOperationBuilder emfRedOperationBuilder = new EMFRedOperationBuilder();
	private final EMFGreenOperationBuilder emfGreenOperationBuilder = new EMFGreenOperationBuilder();

	private PatternMatcher bindingAndBlackPatternMatcher;

	private PatternMatcher bindingPatternMatcher;

	private PatternMatcher blackPatternMatcher;

	private PatternMatcher redPatternMatcher;

	private PatternMatcher greenPatternMatcher;

	private PatternMatcher expressionPatternMatcher;

	private final EMoflonPreferencesStorage preferencesStorage;

	public DefaultValidatorConfig(final ResourceSet resourceSet, EMoflonPreferencesStorage preferencesStorage) {
		this.resourceSet = resourceSet;
		this.preferencesStorage = preferencesStorage;

		this.emfTypeModule = new EMFConstraintModule(this.resourceSet);
		this.internalEMFTypeModule = new EMFTypeModule(emfTypeModule);
		this.bindingAndBlackPatternBuilder
				.addConstraintTypeSwitch(internalPatternInvocationTypeModule.getConstraintTypeSwitch());
		this.bindingAndBlackPatternBuilder
				.addConstraintTypeSwitch(internalRelationalTypeModule.getConstraintTypeSwitch());
		this.bindingAndBlackPatternBuilder.addConstraintTypeSwitch(internalEMFTypeModule.getConstraintTypeSwitch());
		this.bindingAndBlackPatternBuilder.addVariableTypeSwitch(internalEMFTypeModule.getVariableTypeSwitch());
	}

	@Override
	public Map<String, PatternMatcher> getSearchPlanGenerators() {
		final Map<String, PatternMatcher> searchPlanGenerators = new HashMap<>();
		searchPlanGenerators.put(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION, getGreenPatternSearchPlanGenerator());
		searchPlanGenerators.put(DemoclesMethodBodyHandler.RED_FILE_EXTENSION, getRedPatternSearchPlanGenerator());
		searchPlanGenerators.put(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION, getBlackPatternSearchPlanGenerator());
		searchPlanGenerators.put(DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION,
				getBindingPatternSearchPlanGenerator());
		searchPlanGenerators.put(DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION,
				getBindingAndBlackPatternSearchPlanGenerator());
		searchPlanGenerators.put(DemoclesMethodBodyHandler.EXPRESSION_FILE_EXTENSION,
				getExpressionPatternSearchPlanGenerator());
		return searchPlanGenerators;
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
			regularBindingAndBlackInvocationBuilder.setPatternMatcher(getBindingAndBlackPatternSearchPlanGenerator());

			final BindingExpressionBuilder regularBindingExpressionBuilder = ScopeFactory.eINSTANCE
					.createBindingExpressionBuilder();
			regularBindingAndBlackInvocationBuilder.getChildBuilders().add(regularBindingExpressionBuilder);
			final BindingPatternTransformer regularBindingPatternTransformer = PatternFactory.eINSTANCE
					.createBindingPatternTransformer();
			regularBindingExpressionBuilder.setPatternTransformer(regularBindingPatternTransformer);
			regularBindingExpressionBuilder.setExpressionExplorer(expressionExplorer);
			regularBindingExpressionBuilder.setSuffix(DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION);
			regularBindingExpressionBuilder.setMainActionBuilder(false);
			regularBindingExpressionBuilder.setPatternMatcher(getBindingPatternSearchPlanGenerator());
			regularBindingPatternTransformer.setExpressionTransformer(expressionTransformer);

			final BlackPatternBuilder regularBlackInvocationBuilder = ScopeFactory.eINSTANCE
					.createBlackPatternBuilder();
			regularBindingAndBlackInvocationBuilder.getChildBuilders().add(regularBlackInvocationBuilder);
			regularBindingAndBlackInvocationBuilder.setBlackPatternBuilder(regularBlackInvocationBuilder);
			final BlackAndNacPatternTransformer regularBlackPatternTransformer = PatternFactory.eINSTANCE
					.createBlackAndNacPatternTransformer();
			regularBlackInvocationBuilder.setPatternTransformer(regularBlackPatternTransformer);
			regularBlackInvocationBuilder.setExpressionExplorer(expressionExplorer);
			regularBlackInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION);
			regularBlackInvocationBuilder.setMainActionBuilder(true);
			regularBlackInvocationBuilder.setPatternMatcher(getBlackPatternSearchPlanGenerator());
			regularBlackPatternTransformer.setExpressionTransformer(expressionTransformer);

			final NacPatternBuilder regularNacPatternBuilder = ScopeFactory.eINSTANCE.createNacPatternBuilder();
			regularBlackInvocationBuilder.getChildBuilders().add(regularNacPatternBuilder);
			final NacPatternTransformer regularNacPatternTransformer = PatternFactory.eINSTANCE
					.createNacPatternTransformer();
			regularNacPatternBuilder.setPatternTransformer(regularNacPatternTransformer);
			regularNacPatternBuilder.setExpressionExplorer(expressionExplorer);
			regularNacPatternBuilder.setMainActionBuilder(false);
			regularNacPatternBuilder.setPatternMatcher(getBlackPatternSearchPlanGenerator());
			regularNacPatternTransformer.setExpressionTransformer(expressionTransformer);

			final RedPatternBuilder regularRedInvocationBuilder = ScopeFactory.eINSTANCE.createRedPatternBuilder();
			regularStoryNodeActionBuilder.getChildren().add(regularRedInvocationBuilder);
			final RedPatternTransformer regularRedPatternTransformer = PatternFactory.eINSTANCE
					.createRedPatternTransformer();
			regularRedInvocationBuilder.setPatternTransformer(regularRedPatternTransformer);
			regularRedInvocationBuilder.setExpressionExplorer(expressionExplorer);
			regularRedInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.RED_FILE_EXTENSION);
			regularRedInvocationBuilder.setMainActionBuilder(false);
			regularRedInvocationBuilder.setPatternMatcher(getRedPatternSearchPlanGenerator());
			regularRedPatternTransformer.setExpressionTransformer(expressionTransformer);

			final GreenPatternBuilder regularGreenInvocationBuilder = ScopeFactory.eINSTANCE
					.createGreenPatternBuilder();
			regularStoryNodeActionBuilder.getChildren().add(regularGreenInvocationBuilder);
			final GreenPatternTransformer regularGreenPatternTransformer = PatternFactory.eINSTANCE
					.createGreenPatternTransformer();
			regularGreenInvocationBuilder.setPatternTransformer(regularGreenPatternTransformer);
			regularGreenInvocationBuilder.setExpressionExplorer(expressionExplorer);
			regularGreenInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION);
			regularGreenInvocationBuilder.setMainActionBuilder(true);
			regularGreenInvocationBuilder.setPatternMatcher(getGreenPatternSearchPlanGenerator());
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
			forEachBindingExpressionBuilder.setPatternMatcher(getBindingPatternSearchPlanGenerator());
			forEachBindingPatternTransformer.setExpressionTransformer(expressionTransformer);

			final BlackPatternBuilder forEachBlackInvocationBuilder = ScopeFactory.eINSTANCE
					.createBlackPatternBuilder();
			forEachStoryNodeActionBuilder.getChildren().add(forEachBlackInvocationBuilder);
			final BlackAndNacPatternTransformer forEachBlackPatternTransformer = PatternFactory.eINSTANCE
					.createBlackAndNacPatternTransformer();
			forEachBlackInvocationBuilder.setPatternTransformer(forEachBlackPatternTransformer);
			forEachBlackInvocationBuilder.setExpressionExplorer(expressionExplorer);
			forEachBlackInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION);
			forEachBlackInvocationBuilder.setMainActionBuilder(true);
			forEachBlackInvocationBuilder.setPatternMatcher(getBlackPatternSearchPlanGenerator());
			forEachBlackPatternTransformer.setExpressionTransformer(expressionTransformer);

			final NacPatternBuilder forEachNacPatternBuilder = ScopeFactory.eINSTANCE.createNacPatternBuilder();
			forEachBlackInvocationBuilder.getChildBuilders().add(forEachNacPatternBuilder);
			final NacPatternTransformer forEachNacPatternTransformer = PatternFactory.eINSTANCE
					.createNacPatternTransformer();
			forEachNacPatternBuilder.setPatternTransformer(forEachNacPatternTransformer);
			forEachNacPatternBuilder.setExpressionExplorer(expressionExplorer);
			forEachNacPatternBuilder.setMainActionBuilder(false);
			forEachNacPatternBuilder.setPatternMatcher(getBlackPatternSearchPlanGenerator());
			forEachNacPatternTransformer.setExpressionTransformer(expressionTransformer);

			final RedPatternBuilder forEachRedInvocationBuilder = ScopeFactory.eINSTANCE.createRedPatternBuilder();
			forEachStoryNodeActionBuilder.getChildren().add(forEachRedInvocationBuilder);
			final RedPatternTransformer forEachRedPatternTransformer = PatternFactory.eINSTANCE
					.createRedPatternTransformer();
			forEachRedInvocationBuilder.setPatternTransformer(forEachRedPatternTransformer);
			forEachRedInvocationBuilder.setExpressionExplorer(expressionExplorer);
			forEachRedInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.RED_FILE_EXTENSION);
			forEachRedInvocationBuilder.setMainActionBuilder(false);
			forEachRedInvocationBuilder.setPatternMatcher(getRedPatternSearchPlanGenerator());
			forEachRedPatternTransformer.setExpressionTransformer(expressionTransformer);

			final GreenPatternBuilder forEachGreenInvocationBuilder = ScopeFactory.eINSTANCE
					.createGreenPatternBuilder();
			forEachStoryNodeActionBuilder.getChildren().add(forEachGreenInvocationBuilder);
			final GreenPatternTransformer forEachGreenPatternTransformer = PatternFactory.eINSTANCE
					.createGreenPatternTransformer();
			forEachGreenInvocationBuilder.setPatternTransformer(forEachGreenPatternTransformer);
			forEachGreenInvocationBuilder.setExpressionExplorer(expressionExplorer);
			forEachGreenInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION);
			forEachGreenInvocationBuilder.setMainActionBuilder(true);
			forEachGreenInvocationBuilder.setPatternMatcher(getGreenPatternSearchPlanGenerator());
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
			expressionActionBuilder.setPatternMatcher(expressionPatternMatcher);
			expressionPatternTransformer.setExpressionTransformer(expressionTransformer);
		} catch (final IOException e) {
			// Do nothing
		}
		return scopeValidator;
	}

	public EMoflonPreferencesStorage getPreferencesStorage() {
		return this.preferencesStorage;
	}

	protected PatternMatcher getBindingAndBlackPatternSearchPlanGenerator() {
		return bindingAndBlackPatternMatcher;
	}

	protected PatternMatcher getBindingPatternSearchPlanGenerator() {
		return bindingPatternMatcher;
	}

	protected PatternMatcher getBlackPatternSearchPlanGenerator() {
		return blackPatternMatcher;
	}

	protected PatternMatcher getRedPatternSearchPlanGenerator() {
		return redPatternMatcher;
	}

	protected PatternMatcher getGreenPatternSearchPlanGenerator() {
		return greenPatternMatcher;
	}

	protected PatternMatcher getExpressionPatternSearchPlanGenerator() {
		return expressionPatternMatcher;
	}

	protected void setBindingAndBlackPatternMatcher(PatternMatcher bindingAndBlackPatternMatcher) {
		this.bindingAndBlackPatternMatcher = bindingAndBlackPatternMatcher;
	}

	protected void setBindingPatternMatcher(PatternMatcher bindingPatternMatcher) {
		this.bindingPatternMatcher = bindingPatternMatcher;
	}

	protected void setBlackPatternMatcher(PatternMatcher blackPatternMatcher) {
		this.blackPatternMatcher = blackPatternMatcher;
	}

	protected void setRedPatternMatcher(PatternMatcher redPatternMatcher) {
		this.redPatternMatcher = redPatternMatcher;
	}

	protected void setGreenPatternMatcher(PatternMatcher greenPatternMatcher) {
		this.greenPatternMatcher = greenPatternMatcher;
	}

	protected void setExpressionPatternMatcher(PatternMatcher expressionPatternMatcher) {
		this.expressionPatternMatcher = expressionPatternMatcher;
	}

	protected PatternMatcher configureBindingAndBlackPatternMatcher(Resource resource) throws IOException {
		return configureBindingAndBlackPatternMatcherCompiler(resource);
	}

	protected PatternMatcherCompiler configureBindingAndBlackPatternMatcherCompiler(Resource resource) {
		// Configuring binding & black pattern matcher
		final CompilerPatternBuilder bindingAndBlackCompilerPatternBuilder = new CompilerPatternBuilder();
		bindingAndBlackCompilerPatternBuilder.addOperationBuilder(basicOperationBuilder);
		bindingAndBlackCompilerPatternBuilder.setAlgorithm(algorithm);

		final PatternMatcherCompiler bindingAndBlackPatternMatcherCompiler = new BindingAndBlackPatternMatcherCompiler(
				bindingAndBlackPatternBuilder, bindingAndBlackCompilerPatternBuilder);
		resource.getContents().add(bindingAndBlackPatternMatcherCompiler);
		return bindingAndBlackPatternMatcherCompiler;
	}

	protected PatternMatcher configureBindingPatternMatcher(Resource resource) throws IOException {
		return configureBindingPatternMatcherCompiler(resource);
	}

	protected PatternMatcherCompiler configureBindingPatternMatcherCompiler(Resource resource) {
		// Configuring binding pattern matcher
		// final EMFPatternBuilder<DefaultPattern, DefaultPatternBody>
		// bindingPatternBuilder =
		// new EMFPatternBuilder<DefaultPattern, DefaultPatternBody>(new
		// DefaultPatternFactory());
		// bindingPatternBuilder.addConstraintTypeSwitch(internalRelationalTypeModule.getConstraintTypeSwitch());
		// bindingPatternBuilder.addConstraintTypeSwitch(internalEMFTypeModule.getConstraintTypeSwitch());
		// bindingPatternBuilder.addVariableTypeSwitch(internalEMFTypeModule.getVariableTypeSwitch());

		final CompilerPatternBuilder bindingCompilerPatternBuilder = new CompilerPatternBuilder();
		bindingCompilerPatternBuilder.addOperationBuilder(basicOperationBuilder);
		bindingCompilerPatternBuilder.addOperationBuilder(bindingAssignmentOperationBuilder);
		bindingCompilerPatternBuilder.setAlgorithm(algorithm);

		final PatternMatcherCompiler bindingPatternMatcherCompiler = new PatternMatcherCompiler(
				bindingAndBlackPatternBuilder, bindingCompilerPatternBuilder);
		resource.getContents().add(bindingPatternMatcherCompiler);
		return bindingPatternMatcherCompiler;
	}

	protected PatternMatcher configureBlackPatternMatcher(Resource resource) throws IOException {
		return configureBlackPatternMatcherCompiler(resource);
	}

	protected PatternMatcherCompiler configureBlackPatternMatcherCompiler(Resource resource) {
		// Configuring black pattern matcher
		// final EMFPatternBuilder<DefaultPattern, DefaultPatternBody>
		// blackPatternBuilder =
		// new EMFPatternBuilder<DefaultPattern, DefaultPatternBody>(new
		// DefaultPatternFactory());
		// final PatternInvocationConstraintModule<DefaultPattern, DefaultPatternBody>
		// patternInvocationTypeModule =
		// new PatternInvocationConstraintModule<DefaultPattern,
		// DefaultPatternBody>(blackPatternBuilder);
		// final PatternInvocationTypeModule<DefaultPattern, DefaultPatternBody>
		// internalPatternInvocationTypeModule =
		// new PatternInvocationTypeModule<DefaultPattern,
		// DefaultPatternBody>(patternInvocationTypeModule);
		// blackPatternBuilder.addConstraintTypeSwitch(internalPatternInvocationTypeModule.getConstraintTypeSwitch());
		// blackPatternBuilder.addConstraintTypeSwitch(internalRelationalTypeModule.getConstraintTypeSwitch());
		// blackPatternBuilder.addConstraintTypeSwitch(internalEMFTypeModule.getConstraintTypeSwitch());
		// blackPatternBuilder.addVariableTypeSwitch(internalEMFTypeModule.getVariableTypeSwitch());

		final CompilerPatternBuilder blackCompilerPatternBuilder = new CompilerPatternBuilder();
		blackCompilerPatternBuilder.addOperationBuilder(emfBlackOperationBuilder);
		blackCompilerPatternBuilder.addOperationBuilder(relationalOperationBuilder);
		blackCompilerPatternBuilder.setAlgorithm(algorithm);

		final PatternMatcherCompiler blackPatternMatcherCompiler = new PatternMatcherCompiler(
				bindingAndBlackPatternBuilder, blackCompilerPatternBuilder);
		resource.getContents().add(blackPatternMatcherCompiler);
		return blackPatternMatcherCompiler;
	}

	protected PatternMatcher configureRedPatternMatcher(Resource resource) throws IOException {
		return configureRedPatternMatcherCompiler(resource);
	}

	protected PatternMatcherCompiler configureRedPatternMatcherCompiler(Resource resource) {
		// Configuring red pattern matcher
		final EMFPatternBuilder<DefaultPattern, DefaultPatternBody> redPatternBuilder = new EMFPatternBuilder<DefaultPattern, DefaultPatternBody>(
				new DefaultPatternFactory());
		redPatternBuilder.addConstraintTypeSwitch(internalEMFTypeModule.getConstraintTypeSwitch());
		redPatternBuilder.addVariableTypeSwitch(internalEMFTypeModule.getVariableTypeSwitch());

		final CompilerPatternBuilder redCompilerPatternBuilder = new CompilerPatternBuilder();
		redCompilerPatternBuilder.addOperationBuilder(emfRedOperationBuilder);
		redCompilerPatternBuilder.setAlgorithm(algorithm);

		final PatternMatcherCompiler redPatternMatcherCompiler = new PatternMatcherCompiler(redPatternBuilder,
				redCompilerPatternBuilder);
		resource.getContents().add(redPatternMatcherCompiler);
		return redPatternMatcherCompiler;
	}

	protected PatternMatcher configureGreenPatternMatcher(Resource resource) throws IOException {
		return configureGreenPatternMatcherCompiler(resource);
	}

	protected PatternMatcherCompiler configureGreenPatternMatcherCompiler(Resource resource) {
		// Configuring green pattern matcher
		final EMFPatternBuilder<DefaultPattern, DefaultPatternBody> greenPatternBuilder = new EMFPatternBuilder<DefaultPattern, DefaultPatternBody>(
				new DefaultPatternFactory());
		greenPatternBuilder.addConstraintTypeSwitch(internalRelationalTypeModule.getConstraintTypeSwitch());
		greenPatternBuilder.addConstraintTypeSwitch(internalEMFTypeModule.getConstraintTypeSwitch());
		greenPatternBuilder.addVariableTypeSwitch(internalEMFTypeModule.getVariableTypeSwitch());

		final CompilerPatternBuilder greenCompilerPatternBuilder = new CompilerPatternBuilder();
		greenCompilerPatternBuilder.addOperationBuilder(assignmentOperationBuilder);
		greenCompilerPatternBuilder.addOperationBuilder(emfGreenOperationBuilder);
		greenCompilerPatternBuilder.setAlgorithm(algorithm);

		final PatternMatcherCompiler greenPatternMatcherCompiler = new PatternMatcherCompiler(greenPatternBuilder,
				greenCompilerPatternBuilder);
		resource.getContents().add(greenPatternMatcherCompiler);
		return greenPatternMatcherCompiler;
	}

	protected PatternMatcher configureExpressionPatternMatcher(Resource resource) throws IOException {
		return configureExpressionPatternMatcherCompiler(resource);
	}

	protected PatternMatcherCompiler configureExpressionPatternMatcherCompiler(Resource resource) {
		// Configuring expression pattern matcher
		final EMFPatternBuilder<DefaultPattern, DefaultPatternBody> expressionPatternBuilder = new EMFPatternBuilder<DefaultPattern, DefaultPatternBody>(
				new DefaultPatternFactory());
		expressionPatternBuilder.addConstraintTypeSwitch(internalRelationalTypeModule.getConstraintTypeSwitch());
		expressionPatternBuilder.addVariableTypeSwitch(internalEMFTypeModule.getVariableTypeSwitch());
		expressionPatternBuilder.addConstraintTypeSwitch(internalEMFTypeModule.getConstraintTypeSwitch());

		final CompilerPatternBuilder expressionCompilerPatternBuilder = new CompilerPatternBuilder();
		expressionCompilerPatternBuilder.addOperationBuilder(assignmentOperationBuilder);
		expressionCompilerPatternBuilder.addOperationBuilder(basicOperationBuilder);
		expressionCompilerPatternBuilder.setAlgorithm(algorithm);

		final PatternMatcherCompiler expressionPatternMatcherCompiler = new PatternMatcherCompiler(
				expressionPatternBuilder, expressionCompilerPatternBuilder);
		resource.getContents().add(expressionPatternMatcherCompiler);
		return expressionPatternMatcherCompiler;
	}

	@Override
	public TemplateConfigurationProvider createTemplateConfiguration(GenModel genModel) {
		return new DefaultTemplateConfiguration(genModel);
	}
}
