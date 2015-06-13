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

import AttributeConstraintScopeValidation.AttributeConstraintScopeValidationFactory;
import AttributeConstraintToDemocles.AttributeConstraintToDemoclesFactory;
import DemoclesAttributeConstraintSpecification.AttributeConstraintLibrary;
import DemoclesAttributeConstraintSpecification.constraint.AttributeVariableConstraintsTypeModule;
import DemoclesAttributeConstraintSpecification.constraint.util.AttributeConstraintLibUtil;
import DemoclesAttributeConstraintSpecification.constraint.util.AttributeVariableConstraintsModule;
import LiteralExprResolver.ConstantTransformer;
import LiteralExprResolver.LiteralExprResolverFactory;
import SDMLanguageToDemocles.BindingPatternTransformer;
import SDMLanguageToDemocles.BlackAndNacPatternTransformer;
import SDMLanguageToDemocles.DefaultExpressionTransformer;
import SDMLanguageToDemocles.GreenPatternTransformer;
import SDMLanguageToDemocles.LiteralExpressionTransformer;
import SDMLanguageToDemocles.NacPatternTransformer;
import SDMLanguageToDemocles.PatternTransformer;
import SDMLanguageToDemocles.RedPatternTransformer;
import SDMLanguageToDemocles.SDMLanguageToDemoclesFactory;
import ScopeValidation.BindingAndBlackPatternBuilder;
import ScopeValidation.BindingExpressionBuilder;
import ScopeValidation.BlackPatternBuilder;
import ScopeValidation.ExpressionExplorer;
import ScopeValidation.NacPatternBuilder;
import ScopeValidation.PatternMatcher;
import ScopeValidation.RedNodeDeletionBuilder;
import ScopeValidation.RedPatternBuilder;
import ScopeValidation.RegularPatternInvocationBuilder;
import ScopeValidation.ScopeValidationFactory;
import ScopeValidation.ScopeValidator;
import ScopeValidation.SingleResultPatternInvocationBuilder;
import ScopeValidation.StoryNodeActionBuilder;

public class AttributeConstraintCodeGeneratorConfig extends DefaultCodeGeneratorConfig {
	protected final WeightedOperationBuilder<GeneratorOperation> builder =
			new AttributeEnabledWeightedOperationBuilder<GeneratorOperation>();
	protected final DefaultAlgorithm<SimpleCombiner, GeneratorOperation> algorithm =
			new DefaultAlgorithm<SimpleCombiner, GeneratorOperation>(builder);
		// ***************************************************
		// Pattern matcher configuration
		// ***************************************************
		

		// Constraint modules
		//private final AttributeVariableConstraintLibrary attributeVariableConstraintLibrary;
		final AttributeVariableConstraintsModule attributeVariableConstraintsModule;

		// Operation modules (constraint to operation (constraint + adornment) mappings)
		private final AttributeConstraintsOperationBuilder attributeConstraintsOperationBuilder=
				new AttributeConstraintsOperationBuilder();
		
		
		//Constraint libraies
		private List<AttributeConstraintLibrary> attributeVariableConstraintLibraries=new LinkedList<AttributeConstraintLibrary>();
		//Constraint type module
		private final AttributeVariableConstraintsTypeModule attributeVariableConstraintsTypeModule;
		
		protected static final URI BUILDIN_ATTRIBUTE_CONSTRAINT_LIB=URI.createPlatformPluginURI("/DemoclesAttributeConstraintSpecification/lib/buildInConstraintsLibrary/BuildInAttributeVariableConstraintLibrary.xmi",true); 
		
		public AttributeConstraintCodeGeneratorConfig(final ResourceSet resourceSet, final IProject project){
			super(resourceSet);
			if(project==null){
				throw new RuntimeException("Parameter ecoreResource must be defined for AttributeConstraintCodeGeneratorConfig");
			}
			
			//load attribute constraint libraries first loaded has higher priority 
			loadUserDefinedAttributeConstraintLibrary(project);
			loadBuildInAttributeConstraintLibrary();
			
			
			//create attribute variable constraints type module using constraint libraries
			attributeVariableConstraintsTypeModule=
					new AttributeVariableConstraintsTypeModule(attributeVariableConstraintLibraries);
			
			this.attributeVariableConstraintsModule=new AttributeVariableConstraintsModule(attributeVariableConstraintsTypeModule);
			this.bindingAndBlackPatternBuilder.addConstraintTypeSwitch(attributeVariableConstraintsModule.getConstraintTypeSwitch());
		}
		
		public AttributeConstraintCodeGeneratorConfig(final ResourceSet resourceSet) {
			this(resourceSet, null);
			
		}
		
		@Override
      public ScopeValidator createScopeValidator() {
			final Resource resource = new ResourceImpl(URI.createURI("ScopeValidator"));
			resourceSet.getResources().add(resource);
			final ScopeValidator scopeValidator = ScopeValidationFactory.eINSTANCE.createScopeValidator();
			resource.getContents().add(scopeValidator);

			try {
				final Resource expressionTransformerResource = new ResourceImpl(URI.createURI("ExpressionHandler"));
				resourceSet.getResources().add(expressionTransformerResource);
				final ConstantTransformer constantTransformer = LiteralExprResolverFactory.eINSTANCE.createConstantTransformer();
				expressionTransformerResource.getContents().add(constantTransformer);
				final LiteralExpressionTransformer literalExpressionTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createLiteralExpressionTransformer();
				expressionTransformerResource.getContents().add(literalExpressionTransformer);
				literalExpressionTransformer.setConstantTransformer(constantTransformer);
				final DefaultExpressionTransformer expressionTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createDefaultExpressionTransformer();
				expressionTransformerResource.getContents().add(expressionTransformer);
				expressionTransformer.setDelegate(literalExpressionTransformer);
				final ExpressionExplorer expressionExplorer = ScopeValidationFactory.eINSTANCE.createExpressionExplorer();
				expressionTransformerResource.getContents().add(expressionExplorer);
				expressionExplorer.setExpressionTransformer(expressionTransformer);

				final PatternMatcher bindingAndBlackPatternMatcher = configureBindingAndBlackPatternMatcher(resource);
				final PatternMatcher bindingPatternMatcher = configureBindingPatternMatcher(resource);
				final PatternMatcher blackPatternMatcher = configureBlackPatternMatcher(resource);
				final PatternMatcher redPatternMatcher = configureRedPatternMatcher(resource);
				final PatternMatcher greenPatternMatcher = configureGreenPatternMatcher(resource);
				
				// (1) Handler for regular story nodes
				final StoryNodeActionBuilder regularStoryNodeActionBuilder = ScopeValidationFactory.eINSTANCE.createStoryNodeActionBuilder();
				scopeValidator.getActionBuilders().add(regularStoryNodeActionBuilder);
				regularStoryNodeActionBuilder.setRequiresForEach(false);

				final BindingAndBlackPatternBuilder regularBindingAndBlackInvocationBuilder = ScopeValidationFactory.eINSTANCE.createBindingAndBlackPatternBuilder();
				regularStoryNodeActionBuilder.getChildren().add(regularBindingAndBlackInvocationBuilder);
				regularBindingAndBlackInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION);
				regularBindingAndBlackInvocationBuilder.setMainActionBuilder(true);
				regularBindingAndBlackInvocationBuilder.setPatternMatcher(bindingAndBlackPatternMatcher);

				final BindingExpressionBuilder regularBindingExpressionBuilder = ScopeValidationFactory.eINSTANCE.createBindingExpressionBuilder();
				regularBindingAndBlackInvocationBuilder.getChildBuilders().add(regularBindingExpressionBuilder);
				final BindingPatternTransformer regularBindingPatternTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createBindingPatternTransformer();
				regularBindingExpressionBuilder.setPatternTransformer(regularBindingPatternTransformer);
				regularBindingExpressionBuilder.setExpressionExplorer(expressionExplorer);
				regularBindingExpressionBuilder.setSuffix(DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION);
				regularBindingExpressionBuilder.setMainActionBuilder(false);
				regularBindingExpressionBuilder.setPatternMatcher(bindingPatternMatcher);
				regularBindingPatternTransformer.setExpressionTransformer(expressionTransformer);

				final BlackPatternBuilder regularBlackInvocationBuilder = AttributeConstraintScopeValidationFactory.eINSTANCE.createAttributeConstraintBlackPatternInvocationBuilder();
				regularBindingAndBlackInvocationBuilder.getChildBuilders().add(regularBlackInvocationBuilder);
				regularBindingAndBlackInvocationBuilder.setBlackPatternBuilder(regularBlackInvocationBuilder);
				final BlackAndNacPatternTransformer regularBlackPatternTransformer = AttributeConstraintToDemoclesFactory.eINSTANCE.createAttributeConstraintBlackAndNacPatternTransformer();
				regularBlackInvocationBuilder.setPatternTransformer(regularBlackPatternTransformer);
				regularBlackInvocationBuilder.setExpressionExplorer(expressionExplorer);
				regularBlackInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION);
				regularBlackInvocationBuilder.setMainActionBuilder(true);
				regularBlackInvocationBuilder.setPatternMatcher(blackPatternMatcher);
				regularBlackPatternTransformer.setExpressionTransformer(expressionTransformer);

				final NacPatternBuilder regularNacPatternBuilder = ScopeValidationFactory.eINSTANCE.createNacPatternBuilder();
				regularBlackInvocationBuilder.getChildBuilders().add(regularNacPatternBuilder);
				final NacPatternTransformer regularNacPatternTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createNacPatternTransformer();
				regularNacPatternBuilder.setPatternTransformer(regularNacPatternTransformer);
				regularNacPatternBuilder.setExpressionExplorer(expressionExplorer);
				regularNacPatternBuilder.setMainActionBuilder(false);
				regularNacPatternBuilder.setPatternMatcher(blackPatternMatcher);
				regularNacPatternTransformer.setExpressionTransformer(expressionTransformer);

				final RedPatternBuilder regularRedInvocationBuilder = ScopeValidationFactory.eINSTANCE.createRedPatternBuilder();
				regularStoryNodeActionBuilder.getChildren().add(regularRedInvocationBuilder);
				final RedPatternTransformer regularRedPatternTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createRedPatternTransformer();
				regularRedInvocationBuilder.setPatternTransformer(regularRedPatternTransformer);
				regularRedInvocationBuilder.setExpressionExplorer(expressionExplorer);
				regularRedInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.RED_FILE_EXTENSION);
				regularRedInvocationBuilder.setMainActionBuilder(false);
				regularRedInvocationBuilder.setPatternMatcher(redPatternMatcher);
				regularRedPatternTransformer.setExpressionTransformer(expressionTransformer);

				final RegularPatternInvocationBuilder regularGreenInvocationBuilder = AttributeConstraintScopeValidationFactory.eINSTANCE.createAttributeConstraintGreenPatternInvocationBuilder();
				regularStoryNodeActionBuilder.getChildren().add(regularGreenInvocationBuilder);
				final GreenPatternTransformer regularGreenPatternTransformer = AttributeConstraintToDemoclesFactory.eINSTANCE.createAttributeConstraintGreenPatternTransformer();
				regularGreenInvocationBuilder.setPatternTransformer(regularGreenPatternTransformer);
				regularGreenInvocationBuilder.setExpressionExplorer(expressionExplorer);
				regularGreenInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION);
				regularGreenInvocationBuilder.setMainActionBuilder(true);
				regularGreenInvocationBuilder.setPatternMatcher(greenPatternMatcher);
				regularGreenPatternTransformer.setExpressionTransformer(expressionTransformer);

				final RedNodeDeletionBuilder regularRedNodeDeletionBuilder = ScopeValidationFactory.eINSTANCE.createRedNodeDeletionBuilder();
				regularStoryNodeActionBuilder.getChildren().add(regularRedNodeDeletionBuilder);

				// (2) Handler for ForEach story nodes
				final StoryNodeActionBuilder forEachStoryNodeActionBuilder = ScopeValidationFactory.eINSTANCE.createStoryNodeActionBuilder();
				scopeValidator.getActionBuilders().add(forEachStoryNodeActionBuilder);
				forEachStoryNodeActionBuilder.setRequiresForEach(true);

				final BindingExpressionBuilder forEachBindingExpressionBuilder = ScopeValidationFactory.eINSTANCE.createBindingExpressionBuilder();
				forEachStoryNodeActionBuilder.getChildren().add(forEachBindingExpressionBuilder);
				final BindingPatternTransformer forEachBindingPatternTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createBindingPatternTransformer();
				forEachBindingExpressionBuilder.setPatternTransformer(forEachBindingPatternTransformer);
				forEachBindingExpressionBuilder.setExpressionExplorer(expressionExplorer);
				forEachBindingExpressionBuilder.setSuffix(DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION);
				forEachBindingExpressionBuilder.setMainActionBuilder(false);
				forEachBindingExpressionBuilder.setPatternMatcher(bindingPatternMatcher);
				forEachBindingPatternTransformer.setExpressionTransformer(expressionTransformer);

				final BlackPatternBuilder forEachBlackInvocationBuilder = AttributeConstraintScopeValidationFactory.eINSTANCE.createAttributeConstraintBlackPatternInvocationBuilder();
				forEachStoryNodeActionBuilder.getChildren().add(forEachBlackInvocationBuilder);
				final BlackAndNacPatternTransformer forEachBlackPatternTransformer = AttributeConstraintToDemoclesFactory.eINSTANCE.createAttributeConstraintBlackAndNacPatternTransformer();
				forEachBlackInvocationBuilder.setPatternTransformer(forEachBlackPatternTransformer);
				forEachBlackInvocationBuilder.setExpressionExplorer(expressionExplorer);
				forEachBlackInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION);
				forEachBlackInvocationBuilder.setMainActionBuilder(true);
				forEachBlackInvocationBuilder.setPatternMatcher(blackPatternMatcher);
				forEachBlackPatternTransformer.setExpressionTransformer(expressionTransformer);

				final NacPatternBuilder forEachNacPatternBuilder = ScopeValidationFactory.eINSTANCE.createNacPatternBuilder();
				forEachBlackInvocationBuilder.getChildBuilders().add(forEachNacPatternBuilder);
				final NacPatternTransformer forEachNacPatternTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createNacPatternTransformer();
				forEachNacPatternBuilder.setPatternTransformer(forEachNacPatternTransformer);
				forEachNacPatternBuilder.setExpressionExplorer(expressionExplorer);
				forEachNacPatternBuilder.setMainActionBuilder(false);
				forEachNacPatternBuilder.setPatternMatcher(blackPatternMatcher);
				forEachNacPatternTransformer.setExpressionTransformer(expressionTransformer);

				final RedPatternBuilder forEachRedInvocationBuilder = ScopeValidationFactory.eINSTANCE.createRedPatternBuilder();
				forEachStoryNodeActionBuilder.getChildren().add(forEachRedInvocationBuilder);
				final RedPatternTransformer forEachRedPatternTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createRedPatternTransformer();
				forEachRedInvocationBuilder.setPatternTransformer(forEachRedPatternTransformer);
				forEachRedInvocationBuilder.setExpressionExplorer(expressionExplorer);
				forEachRedInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.RED_FILE_EXTENSION);
				forEachRedInvocationBuilder.setMainActionBuilder(false);
				forEachRedInvocationBuilder.setPatternMatcher(redPatternMatcher);
				forEachRedPatternTransformer.setExpressionTransformer(expressionTransformer);

				final RegularPatternInvocationBuilder forEachGreenInvocationBuilder = AttributeConstraintScopeValidationFactory.eINSTANCE.createAttributeConstraintGreenPatternInvocationBuilder();
				forEachStoryNodeActionBuilder.getChildren().add(forEachGreenInvocationBuilder);
				final GreenPatternTransformer forEachGreenPatternTransformer = AttributeConstraintToDemoclesFactory.eINSTANCE.createAttributeConstraintGreenPatternTransformer();
				forEachGreenInvocationBuilder.setPatternTransformer(forEachGreenPatternTransformer);
				forEachGreenInvocationBuilder.setExpressionExplorer(expressionExplorer);
				forEachGreenInvocationBuilder.setSuffix(DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION);
				forEachGreenInvocationBuilder.setMainActionBuilder(true);
				forEachGreenInvocationBuilder.setPatternMatcher(greenPatternMatcher);
				forEachGreenPatternTransformer.setExpressionTransformer(expressionTransformer);

				final RedNodeDeletionBuilder forEachRedNodeDeletionBuilder = ScopeValidationFactory.eINSTANCE.createRedNodeDeletionBuilder();
				forEachStoryNodeActionBuilder.getChildren().add(forEachRedNodeDeletionBuilder);

				// (3) Handler for statement and stop nodes
				final SingleResultPatternInvocationBuilder expressionActionBuilder = ScopeValidationFactory.eINSTANCE.createSingleResultPatternInvocationBuilder();
				scopeValidator.getActionBuilders().add(expressionActionBuilder);
				final PatternTransformer expressionPatternTransformer = SDMLanguageToDemoclesFactory.eINSTANCE.createPatternTransformer();
				expressionActionBuilder.setPatternVariableHandler(expressionPatternTransformer);
				expressionActionBuilder.setExpressionExplorer(expressionExplorer);
				expressionActionBuilder.setSuffix(DemoclesMethodBodyHandler.EXPRESSION_FILE_EXTENSION);
				expressionActionBuilder.setPatternMatcher(configureExpressionPatternMatcher(resource));
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

			final PatternMatcherCompiler blackPatternMatcherCompiler =
					new PatternMatcherCompiler(bindingAndBlackPatternBuilder, blackCompilerPatternBuilder);
			resource.getContents().add(blackPatternMatcherCompiler);
			return blackPatternMatcherCompiler;
		}

		protected void loadBuildInAttributeConstraintLibrary(){
			
				AttributeConstraintLibrary attributeVariableConstraintLibrary =AttributeConstraintLibUtil.getBuildInAttributeConstraintLibrary();
				if(attributeVariableConstraintLibrary==null){
					throw new NullPointerException("AttributeConstraintCodeGeneratorConfig: loading of build in attribute constraint library failed: "+BUILDIN_ATTRIBUTE_CONSTRAINT_LIB );
				}
				attributeVariableConstraintLibraries.add(attributeVariableConstraintLibrary);
			
			
		}
		protected void loadUserDefinedAttributeConstraintLibrary(final IProject project){
			
			
		   AttributeConstraintLibrary attributeVariableConstraintLibrary=AttributeConstraintLibUtil.loadUserDefinedAttributeConstraintLibrary(project);
		   
			if(attributeVariableConstraintLibrary!=null){
				attributeVariableConstraintLibraries.add(attributeVariableConstraintLibrary);
			}
					
		}
					
			
		@Override
		public TemplateConfigurationProvider createTemplateConfiguration(final GenModel genModel) {
			return new AttributeConstraintTemplateConfig(genModel, attributeVariableConstraintLibraries);
		}
}
