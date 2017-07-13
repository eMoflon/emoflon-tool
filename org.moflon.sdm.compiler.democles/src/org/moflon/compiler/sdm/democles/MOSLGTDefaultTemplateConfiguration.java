package org.moflon.compiler.sdm.democles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.gervarro.democles.codegen.ImportManager;
import org.gervarro.democles.codegen.OperationSequenceCompiler;
import org.gervarro.democles.codegen.PatternInvocationConstraintTemplateProvider;
import org.gervarro.democles.codegen.emf.EMFTemplateProvider;
import org.gervarro.democles.codegen.emf.EcoreToGenModelConverter;
import org.gervarro.democles.codegen.stringtemplate.AdornmentHandler;
import org.gervarro.democles.codegen.stringtemplate.FullyQualifiedName;
import org.gervarro.democles.codegen.stringtemplate.ImportHandler;
import org.gervarro.democles.codegen.stringtemplate.StringRenderer;
import org.gervarro.democles.codegen.stringtemplate.emf.EcoreModelAdaptor;
import org.gervarro.democles.codegen.stringtemplate.emf.GenModelAdaptor;
import org.gervarro.democles.common.runtime.VariableRuntime;
import org.gervarro.democles.constraint.emf.EMFVariable;
import org.gervarro.democles.relational.RelationalConstraintTemplateProvider;
import org.gervarro.democles.specification.ConstraintVariable;
import org.moflon.compiler.sdm.democles.stringtemplate.ControlFlowModelAdaptor;
import org.moflon.compiler.sdm.democles.stringtemplate.LoggingSTErrorListener;
import org.moflon.compiler.sdm.democles.stringtemplate.PatternMatcherModelAdaptor;
import org.moflon.sdm.compiler.democles.DemoclesSdmCompilerPlugin;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.VariableReference;
import org.osgi.framework.FrameworkUtil;
import org.stringtemplate.v4.ModelAdaptor;
import org.stringtemplate.v4.STGroup;

public class MOSLGTDefaultTemplateConfiguration implements TemplateConfigurationProvider
{
   private static final Logger logger = Logger.getLogger(MOSLGTDefaultTemplateConfiguration.class);

   private static final String CONTROL_FLOW_TEMPLATE_PATH = "templates/stringtemplate/ControlFlow.stg";

   private static final String REGULAR_PATTERN_TEMPLATE_PATH = "templates/stringtemplate/MOSLGTRegularPatternMatcher.stg";

   private static final String ASSIGNMENT_TEMPLATE_PATH = "templates/stringtemplate/Assignment.stg";

   private static final String DEMOCLES_COMMON_TEMPLATE_PATH = "templates/stringtemplate/DemoclesCommon.stg";

   private static final String NUMBER_TEMPLATE_PATH = "templates/stringtemplate/Number.stg";

   private static final String EMF_CONSTANT_TEMPLATE_PATH = "templates/stringtemplate/EMFConstant.stg";

   private static final String EMF_OPERATION_TEMPLATE_PATH = "templates/stringtemplate/EMFOperation.stg";
   
   private static final String PRIORITIZED_PATTERN_CALL_TEMPLATE_PATH = "templates/stringtemplate/PrioritizedPatternCall.stg";
   
   private static final String RELATIONAL_OPERATION_TEMPLATE_PATH = "templates/stringtemplate/RelationalOperation.stg";
   
   private static final String PATTERN_CALL_OPERATION_TEMPLATE_PATH = "templates/stringtemplate/PatternCallOperation.stg";
   
   private static final String EMF_DELETE_OPERATION_TEMPLATE_PATH = "templates/stringtemplate/EMFDeleteOperation.stg";
   
   private static final String EMF_CREATE_OPERATION_TEMPLATE_PATH = "templates/stringtemplate/EMFCreateOperation.stg";
   
   private static final String EXPRESSION_PATTERN_MATCHER_TEMPLATE_PATH = "templates/stringtemplate/ExpressionPatternMatcher.stg";

   public static final String CONTROL_FLOW_GENERATOR = "ControlFlowGenerator";

   protected final HashMap<String, STGroup> templates = new HashMap<String, STGroup>();

   protected final HashMap<String, OperationSequenceCompiler> operationSequenceCompilers = new HashMap<String, OperationSequenceCompiler>();

   public MOSLGTDefaultTemplateConfiguration(final GenModel genModel)
   {
      final EcoreToGenModelConverter ecoreToGenModelConverter = new EcoreToGenModelConverter(genModel);
      final EcoreModelAdaptor ecoreModelAdaptor = new EcoreModelAdaptor(ecoreToGenModelConverter);

      final STGroup controlFlowTemplateGroup = createControlFlowTemplates();
      controlFlowTemplateGroup.registerModelAdaptor(EClassifier.class, ecoreModelAdaptor);
      controlFlowTemplateGroup.registerRenderer(EClassifier.class, ecoreModelAdaptor);
      templates.put(CONTROL_FLOW_GENERATOR, controlFlowTemplateGroup);

      Class<?>[] ecoreModelClasses = { EModelElement.class, EMFVariable.class };
      final STGroup bindingAndBlackTemplateGroup = createBindingAndBlackTemplates();
      registerModelForClasses(bindingAndBlackTemplateGroup, ecoreModelAdaptor, ecoreModelClasses);
      bindingAndBlackTemplateGroup.registerRenderer(EMFVariable.class, ecoreModelAdaptor);
      bindingAndBlackTemplateGroup.registerRenderer(EClassifier.class, ecoreModelAdaptor);
      templates.put(DefaultCodeGeneratorConfig.BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR, bindingAndBlackTemplateGroup);

      final STGroup bindingTemplateGroup = createBindingTemplates();
      registerModelForClasses(bindingTemplateGroup, ecoreModelAdaptor, ecoreModelClasses);
      bindingTemplateGroup.registerRenderer(EMFVariable.class, ecoreModelAdaptor);
      bindingTemplateGroup.registerRenderer(EClassifier.class, ecoreModelAdaptor);
      templates.put(DefaultCodeGeneratorConfig.BINDING_PATTERN_MATCHER_GENERATOR, bindingTemplateGroup);

      final STGroup blackTemplateGroup = createBlackTemplates();
      registerModelForClasses(blackTemplateGroup, ecoreModelAdaptor, ecoreModelClasses);
      blackTemplateGroup.registerRenderer(EMFVariable.class, ecoreModelAdaptor);
      blackTemplateGroup.registerRenderer(EClassifier.class, ecoreModelAdaptor);
      templates.put(DefaultCodeGeneratorConfig.BLACK_PATTERN_MATCHER_GENERATOR, blackTemplateGroup);

      final STGroup redTemplateGroup = createRedTemplates();
      registerModelForClasses(redTemplateGroup, ecoreModelAdaptor, ecoreModelClasses);
      redTemplateGroup.registerRenderer(EMFVariable.class, ecoreModelAdaptor);
      redTemplateGroup.registerRenderer(EClassifier.class, ecoreModelAdaptor);
      templates.put(DefaultCodeGeneratorConfig.RED_PATTERN_MATCHER_GENERATOR, redTemplateGroup);

      final STGroup greenTemplateGroup = createGreenTemplates();
      registerModelForClasses(greenTemplateGroup, ecoreModelAdaptor, ecoreModelClasses);
      greenTemplateGroup.registerRenderer(EMFVariable.class, ecoreModelAdaptor);
      greenTemplateGroup.registerRenderer(EClassifier.class, ecoreModelAdaptor);
      templates.put(DefaultCodeGeneratorConfig.GREEN_PATTERN_MATCHER_GENERATOR, greenTemplateGroup);

      final STGroup expressionTemplateGroup = createExpressionTemplates();
      registerModelForClasses(expressionTemplateGroup, ecoreModelAdaptor, ecoreModelClasses);
      expressionTemplateGroup.registerRenderer(EMFVariable.class, ecoreModelAdaptor);
      expressionTemplateGroup.registerRenderer(EClassifier.class, ecoreModelAdaptor);
      templates.put(DefaultCodeGeneratorConfig.EXPRESSION_PATTERN_MATCHER_GENERATOR, expressionTemplateGroup);

      operationSequenceCompilers.put(DefaultCodeGeneratorConfig.BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR, createBindingAndBlackOperationSequenceCompiler());
      operationSequenceCompilers.put(DefaultCodeGeneratorConfig.BINDING_PATTERN_MATCHER_GENERATOR, createBindingOperationSequenceCompiler());
      operationSequenceCompilers.put(DefaultCodeGeneratorConfig.BLACK_PATTERN_MATCHER_GENERATOR, createBlackOperationSequenceCompiler());
      operationSequenceCompilers.put(DefaultCodeGeneratorConfig.RED_PATTERN_MATCHER_GENERATOR, createRedOperationSequenceCompiler());
      operationSequenceCompilers.put(DefaultCodeGeneratorConfig.GREEN_PATTERN_MATCHER_GENERATOR, createGreenOperationSequenceCompiler());
      operationSequenceCompilers.put(DefaultCodeGeneratorConfig.EXPRESSION_PATTERN_MATCHER_GENERATOR, createExpressionOperationSequenceCompiler());
   }

   @Override
   public final STGroup getTemplateGroup(final String id)
   {
      return templates.get(id);
   }

   @Override
   public final OperationSequenceCompiler getOperationSequenceCompiler(final String id)
   {
      return operationSequenceCompilers.get(id);
   }

   public static final STGroup createControlFlowTemplates()
   {
      // final URL groupFileURL = FileLocator.resolve(Platform.getBundle("org.moflon.compiler.sdm.democles").getEntry(
      // "templates/stringtemplate/ControlFlow.stg"));
      // final STGroup controlFlowGenerator = new STGroupFile(groupFileURL, "ascii", '<', '>');
      final STGroup group = createBasicSTGroup();
      group.loadGroupFile("/" + CONTROL_FLOW_GENERATOR + "/", getCompilerURI() + CONTROL_FLOW_TEMPLATE_PATH);

      Class<?>[] controlFlowClasses = { PatternInvocation.class, VariableReference.class, CFNode.class, CFVariable.class };
      registerModelForClasses(group, new ControlFlowModelAdaptor(), controlFlowClasses);
      registerImportManager(group);
      return group;
   }

   @SuppressWarnings("unchecked")
   public static final OperationSequenceCompiler createBindingAndBlackOperationSequenceCompiler()
   {
      return new OperationSequenceCompiler(new BindingAndBlackTemplateProvider());
   }

   public static final STGroup createBindingAndBlackTemplates()
   {
      final STGroup group = createRegularSTGroup();
      addDemoclesCommon(group);
      group.loadGroupFile("/priority/", getCompilerURI() + PRIORITIZED_PATTERN_CALL_TEMPLATE_PATH);

      postRegister(group);
      return group;
   }

   @SuppressWarnings("unchecked")
   public static final OperationSequenceCompiler createBindingOperationSequenceCompiler()
   {
      return new OperationSequenceCompiler(new AssignmentTemplateProvider(), new EMFTemplateProvider());
   }

   public static final STGroup createBindingTemplates()
   {
      final STGroup group = createBindingSTGroup();
      addDemoclesCommon(group);
      addBlackGreenBindingAndExpressionStuff(group);

      postRegister(group);
      return group;
   }

   @SuppressWarnings("unchecked")
   public static OperationSequenceCompiler createBlackOperationSequenceCompiler()
   {
      return new OperationSequenceCompiler(new PatternInvocationConstraintTemplateProvider(), new RelationalConstraintTemplateProvider(),
            new EMFTemplateProvider());
   }

   public static final STGroup createBlackTemplates()
   {
      final STGroup group = createRegularSTGroup();
      addDemoclesCommon(group);
      addBlackGreenBindingAndExpressionStuff(group);
      group.loadGroupFile("/core/", getDemoclesCoreURI() + RELATIONAL_OPERATION_TEMPLATE_PATH);
      group.loadGroupFile("/pattern/", getDemoclesCoreURI() + PATTERN_CALL_OPERATION_TEMPLATE_PATH);

      postRegister(group);
      return group;
   }

   @SuppressWarnings("unchecked")
   public static final OperationSequenceCompiler createRedOperationSequenceCompiler()
   {
      return new OperationSequenceCompiler(new EMFRedTemplateProvider());
   }

   public static final STGroup createRedTemplates()
   {
      final STGroup group = createRegularSTGroup();
      addDemoclesCommon(group);
      group.loadGroupFile("/emf-delete/", getCompilerURI() + EMF_DELETE_OPERATION_TEMPLATE_PATH);
      group.loadGroupFile("/democles/", getDemoclesEMFURI() + EMF_CONSTANT_TEMPLATE_PATH);

      postRegister(group);
      return group;
   }

   @SuppressWarnings("unchecked")
   public static final OperationSequenceCompiler createGreenOperationSequenceCompiler()
   {
      return new OperationSequenceCompiler(new AttributeAssignmentTemplateProvider(), new EMFGreenTemplateProvider());
   }

   public static final STGroup createGreenTemplates()
   {
      final STGroup group = createBindingSTGroup();
      addDemoclesCommon(group);
      addBlackGreenBindingAndExpressionStuff(group);
      group.loadGroupFile("/emf-create/", getCompilerURI() + EMF_CREATE_OPERATION_TEMPLATE_PATH);

      postRegister(group);
      return group;
   }

   @SuppressWarnings("unchecked")
   public static final OperationSequenceCompiler createExpressionOperationSequenceCompiler()
   {
      return new OperationSequenceCompiler(new AssignmentTemplateProvider(), new EMFTemplateProvider());
   }

   public static final STGroup createExpressionTemplates()
   {
      final STGroup group = createBindingSTGroup();
      addDemoclesCommon(group);
      addBlackGreenBindingAndExpressionStuff(group);
      group.loadGroupFile("/expression/", getCompilerURI() + EXPRESSION_PATTERN_MATCHER_TEMPLATE_PATH);

      postRegister(group);
      return group;
   }

   private static final STGroup createBasicSTGroup()
   {
      final STGroup group = new STGroup();
      group.setListener(new LoggingSTErrorListener(logger));
      return group;
   }

   private static final STGroup createRegularSTGroup()
   {
      final STGroup group = createBasicSTGroup();
      group.loadGroupFile("/regular/", getCompilerURI() + REGULAR_PATTERN_TEMPLATE_PATH);
      return group;
   }

   private static final STGroup createBindingSTGroup()
   {
      final STGroup group = createRegularSTGroup();
      group.loadGroupFile("/assignment/", getCompilerURI() + ASSIGNMENT_TEMPLATE_PATH);
      return group;
   }

   private static void addDemoclesCommon(final STGroup group)
   {
      group.loadGroupFile("/democles/", getDemoclesCoreURI() + DEMOCLES_COMMON_TEMPLATE_PATH);
   }

   private static void addBlackGreenBindingAndExpressionStuff(final STGroup group)
   {
      group.loadGroupFile("/emf/", getDemoclesEMFURI() + EMF_OPERATION_TEMPLATE_PATH);
      group.loadGroupFile("/democles/", getDemoclesEMFURI() + EMF_CONSTANT_TEMPLATE_PATH);
      group.loadGroupFile("/democles/", getCompilerURI() + NUMBER_TEMPLATE_PATH);
   }

   private static void postRegister(final STGroup group)
   {
      registerImportManager(group);

      Class<?>[] parameterClasses = { ConstraintVariable.class, VariableRuntime.class };
      registerModelForClasses(group, new PatternMatcherModelAdaptor(), parameterClasses);

      group.registerModelAdaptor(Integer.class, new AdornmentHandler());
      group.registerRenderer(String.class, new StringRenderer());

      group.registerModelAdaptor(GenBase.class, new GenModelAdaptor());
   }

   private static void registerImportManager(final STGroup group)
   {
      Class<?>[] importClasses = { ImportManager.class, FullyQualifiedName.class };
      registerModelForClasses(group, new ImportHandler(), importClasses);
   }

   private static void registerModelForClasses(final STGroup group, final ModelAdaptor adaptor, Class<?>[] clazzes)
   {
      List<Class<?>> classes = Arrays.asList(clazzes);
      classes.stream().forEach(clazz -> group.registerModelAdaptor(clazz, adaptor));
   }

   private static String getCompilerURI()
   {
      return "platform:/plugin/" + FrameworkUtil.getBundle(DemoclesSdmCompilerPlugin.class).getSymbolicName() + "/";
   }

   private static String getDemoclesCoreURI()
   {
      return "platform:/plugin/org.gervarro.democles.codegen.stringtemplate/";
   }

   private static String getDemoclesEMFURI()
   {
      return "platform:/plugin/org.gervarro.democles.codegen.emf/";
   }
}
