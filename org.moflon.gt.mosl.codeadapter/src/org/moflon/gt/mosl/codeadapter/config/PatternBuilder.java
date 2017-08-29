package org.moflon.gt.mosl.codeadapter.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.PatternInvocationConstraint;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.transformplanrules.TransformPlanRule;
import org.moflon.gt.mosl.codeadapter.utils.MOSLUtil;
import org.moflon.gt.mosl.codeadapter.utils.OperatorUtils;
import org.moflon.gt.mosl.codeadapter.utils.PatternInvocationType;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.codeadapter.utils.VariableVisibility;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.NACGroup;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.Operator;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternObject;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.NodeDeletion;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.VariableReference;

public class PatternBuilder
{
   private final List<PatternKind> patternKindProcessingOrder = Arrays.asList(PatternKind.BLACK, PatternKind.RED, PatternKind.GREEN);

   private Map<String, Map<PatternKind, List<PatternObject>>> patternNameToPatternObjectsByPatternKind;

   private Map<PatternKind, TransformPlanRule> transformationPlanRuleCache;

   private Map<String, SortedMap<PatternKind, PatternInvocation>> patternInvocationCache;

   private Map<String, List<String>> nodeDeletionNameCache;

   private Map<PatternInvocation, PatternKind> patternTypes;

   private PatternNameGenerator patternNameGenerator;
   
   private Map<String, List<List<PatternObject>>> nacObjects;

   private final TransformationConfiguration transformationConfiguration;
   
   private Map<Pattern,Set<Pattern>> patternNacConnection;

   public PatternBuilder(TransformationConfiguration trafoConfig)
   {
      this.transformationConfiguration = trafoConfig;
      this.patternNameToPatternObjectsByPatternKind = new HashMap<>();
      this.patternInvocationCache = new HashMap<>();
      this.transformationPlanRuleCache = new HashMap<>();
      this.patternTypes = new HashMap<>();
      this.nodeDeletionNameCache = new HashMap<>();
      this.patternNameGenerator = new PatternNameGenerator();
      this.nacObjects = new HashMap<>();
      this.patternNacConnection = new HashMap<>();
   }

   public PatternNameGenerator getPatternNameGenerator()
   {
      return this.patternNameGenerator;
   }

   public void addTransformPlanRule(PatternKind patternKind, TransformPlanRule transformPlanRule)
   {
      transformationPlanRuleCache.put(patternKind, transformPlanRule);
   }

   public void createAllPatterns(PatternDef patternDef, Map<String, CFVariable> env, Map<String, VariableVisibility> visibilty, PatternNameGenerator patternNameGenerator,
         EClass eClass)
   {
      final String patternName = patternDef.getName();

      validateTransformationRules();

      createTransformPlan(patternDef, env);

      final Map<PatternKind, List<PatternObject>> patternObjectsByKind = patternNameToPatternObjectsByPatternKind.getOrDefault(patternName, new HashMap<>());

      final List<PatternKind> patternKindsInTransformatinoPlan = extractUsedPatternKinds(patternObjectsByKind.keySet());

      final SortedMap<PatternKind, PatternInvocation> patternKindToInvocation = new TreeMap<>(new PatternKindProcessingOrderComparator());
      patternInvocationCache.put(patternName, patternKindToInvocation);
      patternKindsInTransformatinoPlan.stream().forEach(patternKind -> patternKindToInvocation.put(patternKind, createPatternInvocation(patternKind,
            patternObjectsByKind.get(patternKind), patternName, env, visibilty, patternNameGenerator, eClass, PatternInvocationType.REGULAR)));
   }

   public SortedMap<PatternKind, PatternInvocation> getPatternInvocations(final String patternName)
   {
      return patternInvocationCache.get(patternName);
   }

   public boolean isConstructorPattern(CFNode cfNode, CFVariable cfVar, PatternInvocation currentInvocation, List<MethodParameter> methodParameters,
         String patternName, Map<String, VariableVisibility> visiblity)
   {
      if ("this".equals(cfVar.getName()))
         return false;

      if (isMethodParameter(cfVar, methodParameters))
         return false;
      
      if (visiblity.getOrDefault(cfVar.getName(), VariableVisibility.LOCAL) == VariableVisibility.GLOBAL)
         return false;

      final PatternInvocation blackInvocation = getInvocationIfVarExists(PatternKind.BLACK, cfNode, cfVar);
      if (blackInvocation != null && isCFVarCorrectKind(PatternKind.BLACK, cfVar, patternName))
         return blackInvocation.equals(currentInvocation);

      final PatternInvocation greenInvocation = getInvocationIfVarExists(PatternKind.GREEN, cfNode, cfVar);
      if (greenInvocation != null && isCFVarCorrectKind(PatternKind.GREEN, cfVar, patternName))
         return greenInvocation.equals(currentInvocation);

      return false;
   }

   private boolean isMethodParameter(final CFVariable cfVariable, final List<MethodParameter> methodParameters)
   {
      return methodParameters.stream().anyMatch(mp -> mp.getName().equals(cfVariable.getName()));
   }

   public NodeDeletion getNodeDeletion(String patternName, List<CFVariable> cfVars)
   {
      final List<String> nameList = nodeDeletionNameCache.get(patternName);
      if (nameList == null)
      {
         return null;
      } else
      {
         final NodeDeletion nodeDeletion = DemoclesFactory.eINSTANCE.createNodeDeletion();
         nodeDeletion.getDestructedVariables().addAll(cfVars.stream().filter(cfVar -> nameList.contains(cfVar.getName())).collect(Collectors.toList()));
         return nodeDeletion;
      }
   }

   /**
    * A view of {@link #patternKindProcessingOrder} that contains only {@link PatternKind}s contained in
    * 'patternKindsToKeep'
    */
   private List<PatternKind> extractUsedPatternKinds(final Set<PatternKind> patternKindsToKeep)
   {
      return patternKindProcessingOrder.stream().filter(pk -> patternKindsToKeep.contains(pk)).collect(Collectors.toList());
   }

   /**
    * Throws an {@link IllegalStateException} iff the {@link #transformationPlanRuleCache} does not contain a
    * transformation rule for each of the pattern kinds returned by {@link #getPatternKindProcessingOrder}
    */
   private void validateTransformationRules()
   {
      List<PatternKind> patternKindsWithMissingRule = this.getPatternKindProcessingOrder().stream()
            .filter(patternKind -> !this.transformationPlanRuleCache.containsKey(patternKind)).collect(Collectors.toList());
      if (!patternKindsWithMissingRule.isEmpty())
      {
         final String formattedPatternKindList = patternKindsWithMissingRule.stream().map(Object::toString).collect(Collectors.joining(", "));
         throw new IllegalStateException(
               String.format("No transformation rules have been registered for the following pattern kinds: [%s]", formattedPatternKindList));
      }
   }

   /**
    * Creates a search plan
    * 
    * @param patternDef
    *           the pattern definition from XText
    * @param bindings
    *           the information which ControlFlowVariable is bound
    * @param env
    *           the environment where the ControlVariables are available
    */
   private void createTransformPlan(PatternDef patternDef, Map<String, CFVariable> env)
   {
      final String patternName = patternDef.getName();
      for (final PatternKind patternKind : this.getPatternKindProcessingOrder())
      {
         final TransformPlanRule transformationRule = transformationPlanRuleCache.get(patternKind);
         if (transformationRule.isTransformable(patternKind, patternDef, env))
         {
            final List<PatternObject> patternObjectIndex = transformationRule.getPatterObjectIndex();
            final Map<PatternKind, List<PatternObject>> patternKindIndex = patternNameToPatternObjectsByPatternKind.getOrDefault(patternName, new HashMap<>());
            patternKindIndex.put(patternKind, patternObjectIndex);
            patternNameToPatternObjectsByPatternKind.put(patternName, patternKindIndex);
         }
      }
      createNodeDeletion(patternName, patternDef);
      collectNACs(patternName, patternDef);
   }

   private void createNodeDeletion(String patternName, PatternDef patternDef)
   {
      Map<PatternKind, List<PatternObject>> patternObjectsByPatternKind = patternNameToPatternObjectsByPatternKind.get(patternName);
      if (patternObjectsByPatternKind != null && patternObjectsByPatternKind.containsKey(PatternKind.RED))
      {
         final List<String> deletions = MOSLUtil.mapToSubtype(patternObjectsByPatternKind.get(PatternKind.RED), ObjectVariableDefinition.class).stream()
               .filter(objectVariable ->  OperatorUtils.isDestroyed(objectVariable.getOp()))
               .map(objectVariable -> objectVariable.getName()).collect(Collectors.toList());

         if (!deletions.isEmpty())
            nodeDeletionNameCache.put(patternName, deletions);
      }
   }
   
   private void collectNACs(String patternName, PatternDef patternDef){
      patternNacConnection.clear();
      List<List<PatternObject>> nacs = new ArrayList<>();
      List<NACGroup> nacGroups = MOSLUtil.mapToSubtype(patternDef.getVariables(), NACGroup.class);
      nacs.addAll(nacGroups.stream().map(nacGroup -> 
      PatternUtil.collectObjects(new ArrayList<>(), nacGroup.getObjects()).stream().collect(Collectors.toList())).collect(Collectors.toList()));
      nacObjects.put(patternName, nacs);      
   }

   /**
    * Transform a Pattern using a found search plan
    * 
    * @param patternKind
    *           the PatternKind which will be registered
    * @param patternObjectIndex
    *           Plan the search plan
    * @param patternName
    *           which the patternName without the suffix
    * @param bindings
    *           the information which ControlFlowVariable is bound
    * @param env
    *           the environment where the ControlVariables are available
    * @param patternNameGenerator
    * @param patternNameGenerator
    *           generates a pattern name to save using a suffix
    * @param transformationConfiguration
    * @return A patternInvocation where the belonged pattern is registered
    */
   private PatternInvocation createPatternInvocation(PatternKind patternKind, List<PatternObject> patternObjectIndex, String patternName,
         Map<String, CFVariable> env, Map<String, VariableVisibility> visibilty, PatternNameGenerator patternNameGenerator, EClass eClass,
         PatternInvocationType patternInvocationType)
   {
      // creating Pattern hull
      PatternInvocation invocation = patternInvocationType.createPatternInvocation();
      patternTypes.put(invocation, patternKind);
      List <Variable> variables = createPatternAndGetItsVariables(patternObjectIndex, patternKind, visibilty, eClass, invocation::setPattern);
      Pattern pattern = invocation.getPattern();
      
      // set connection to global variables
      variables.stream().filter(variable -> isGlobal(variable, visibilty)).forEach(variable -> this.transformationConfiguration.getVariableTransformer().addVariableReferencesToInvocation(invocation, env, variable));

      transformationConfiguration.getBindingHandler().setDemoclesBindings(patternName, invocation);
      
      if(patternKind == PatternKind.BLACK)
         createNACStructure(pattern, patternName, patternObjectIndex, patternKind, variables, eClass);
      
      // return value
      return invocation;
   }
   
   private void createNACStructure(Pattern srcPattern, String patternName, List<PatternObject> srcPatternObjectIndex, PatternKind patternKind, List<Variable> variables, EClass eClass){
      PatternBody srcPatternBody = srcPattern.getBodies().get(0);
      Set<String> srcOVNames = MOSLUtil.mapToSubtype(srcPatternObjectIndex, ObjectVariableDefinition.class).stream().map(ov -> ov.getName()).collect(Collectors.toSet());
      List<List<PatternObject>> allNacsPatternObjects = nacObjects.getOrDefault(patternName, new ArrayList<>());
      List<Map<String, VariableVisibility>> allNacVisibilities = allNacsPatternObjects.stream().map(nacPatternObjectIndex ->
      this.transformationConfiguration.getVariableTransformer().getNacVisibility(srcOVNames, nacPatternObjectIndex)).collect(Collectors.toList());
      for(int index =0; index < allNacsPatternObjects.size(); index++){
         Map<String, VariableVisibility> nacVisibility = allNacVisibilities.get(index);
         List<PatternObject> nacPatternObjectIndex = allNacsPatternObjects.get(index);
         PatternInvocationConstraint patternInvocationConstraint = this.transformationConfiguration.getVariableTransformer().createPatternInvocationConstraint(nacVisibility, variables);
         srcPatternBody.getConstraints().add(patternInvocationConstraint);
         createPatternAndGetItsVariables(nacPatternObjectIndex, patternKind, nacVisibility, eClass, trgPattern -> setPattern(srcPattern, trgPattern, patternInvocationConstraint), "_"+index);
      }
      
   }   

   private void setPattern(Pattern srcPattern, Pattern trgPattern, PatternInvocationConstraint patternInvocationConstraint){
      Set<Pattern> connections = patternNacConnection.getOrDefault(srcPattern, new HashSet<>());
      connections.add(trgPattern);
      patternInvocationConstraint.setInvokedPattern(trgPattern);
      patternNacConnection.put(srcPattern, connections);
   }
   
   public Optional<Pattern> getSourcePattern(Pattern trgPattern){
      return patternNacConnection.entrySet().stream().filter(entry -> entry.getValue().contains(trgPattern)).map(entry -> entry.getKey()).findFirst();
   }
   
   private boolean isGlobal(Variable variable, Map<String, VariableVisibility> visibilty){
      VariableVisibility variableVisibility = visibilty.getOrDefault(variable.getName(), VariableVisibility.LOCAL);
      return variableVisibility == VariableVisibility.GLOBAL;
   }
   


   private List<Variable> createPatternAndGetItsVariables(List<PatternObject> patternObjectIndex, PatternKind patternKind,  Map<String, VariableVisibility> visibilty, EClass eClass, Consumer<Pattern> addTo){
      return createPatternAndGetItsVariables(patternObjectIndex, patternKind, visibilty, eClass, addTo, "");
   }
   
   private List<Variable> createPatternAndGetItsVariables(List<PatternObject> patternObjectIndex, PatternKind patternKind,  Map<String, VariableVisibility> visibilty, EClass eClass, Consumer<Pattern> addTo, String nacExtension)
   {
      Pattern pattern = SpecificationFactory.eINSTANCE.createPattern();
      PatternBody patternBody = SpecificationFactory.eINSTANCE.createPatternBody();
      patternBody.setHeader(pattern);
      addTo.accept(pattern);
      
      // special case for black pattern
      if (patternKind == PatternKind.BLACK)
         transformationConfiguration.getVariableTransformer().addUnequals(pattern, patternBody);
      
      List<ObjectVariableDefinition> objectVariables = MOSLUtil.mapToSubtype(patternObjectIndex, ObjectVariableDefinition.class);
      
      // handle ObjectVariables
      List<Variable> resolvingVariables = objectVariables.stream()
            .map(ov -> transformationConfiguration.getVariableTransformer().transformObjectVariable(pattern, ov, visibilty.getOrDefault(ov.getName(), VariableVisibility.LOCAL))).collect(Collectors.toList());

      // handle LinkVariables must be executed after handle ObjectVariables
      transformationConfiguration.getVariableTransformer().transformPatternObjects(MOSLUtil.mapToSubtype(patternObjectIndex, LinkVariablePattern.class),
            patternBody, patternKind);
      
      // register Pattern
      registerPattern(patternKind, pattern, eClass, nacExtension);
      
      
      
      return resolvingVariables;
   }

   private void registerPattern(PatternKind patternKind, Pattern pattern, EClass eClass, String nacExtension)
   {
      patternNameGenerator.setSuffix(patternKind.getSuffix());
      pattern.setName(patternNameGenerator.generateName());

      transformationConfiguration.getECoreAdapterController().saveAsRegisteredAdapter(pattern,
            transformationConfiguration.getContextController().getTypeContext(eClass), patternKind.getSuffix(),
            transformationConfiguration.getContextController().getResourceSet());

   }

   private boolean isCFVarCorrectKind(PatternKind patternKind, CFVariable cfVar, String patternName)
   {
      List<ObjectVariableDefinition> objectVariables = patternNameToPatternObjectsByPatternKind.get(patternName).get(patternKind).stream()
            .filter(po -> po instanceof ObjectVariableDefinition).map(po -> ObjectVariableDefinition.class.cast(po))
            .filter(ov -> ov.getName().equals(cfVar.getName())).collect(Collectors.toList());

      Optional<ObjectVariableDefinition> option = objectVariables.stream().filter(objectVariable -> isCorrectType(objectVariable.getOp(), patternKind))
            .findFirst();
      return option.isPresent();
   }

   private boolean isCorrectType(Operator op, PatternKind pk)
   {
      if (pk == PatternKind.GREEN && op != null && op.getValue() != null && op.getValue().equals("++"))
         return true;
      else if (pk == PatternKind.BLACK && (op == null || op.getValue() == null || op.getValue().equals("")))
         return true;
      else
         return false;
   }

   private PatternInvocation getInvocationIfVarExists(PatternKind patternKind, CFNode cfNode, CFVariable cfVariable)
   {
      final List<PatternInvocation> invocationsOfKind = MOSLUtil.mapToSubtype(cfNode.getActions(), PatternInvocation.class).stream()
            .filter(pi -> isCorrectPatternInvocation(pi, patternKind)).collect(Collectors.toList());

      if (!invocationsOfKind.isEmpty())
      {
         final PatternInvocation invocation = invocationsOfKind.get(0);
         final Optional<VariableReference> varRefMonad = invocation.getParameters().stream().filter(parameter -> parameter.getFrom().equals(cfVariable))
               .findFirst();
         if (varRefMonad.isPresent())
            return invocation;
         else
            return null;
      }

      return null;
   }

   private boolean isCorrectPatternInvocation(PatternInvocation invocation, PatternKind patternKind)
   {
      PatternKind otherPK = patternTypes.get(invocation);
      return otherPK != null && otherPK.equals(patternKind);
   }

   /**
    * The returned list specifies the order of creating Democles patterns
    * 
    * @return
    */
   private List<PatternKind> getPatternKindProcessingOrder()
   {
      return this.patternKindProcessingOrder;
   }

   /**
    * This comparator reflects the order of the outer class's {@link PatternBuilder#getPatternKindProcessingOrder()}
    * 
    * @author Roland Kluge - Initial implementation
    */
   public class PatternKindProcessingOrderComparator implements Comparator<PatternKind>
   {
      /**
       * compare(firstKind, secondKind) < 0 if firstKind appears earlier in the pattern kind order than secondKind
       */
      @Override
      public int compare(final PatternKind firstKind, final PatternKind secondKind)
      {
         final int indexOfFirstKind = getPatternKindProcessingOrder().indexOf(firstKind);
         final int indexOfSecondKind = getPatternKindProcessingOrder().indexOf(secondKind);
         return indexOfFirstKind - indexOfSecondKind;
      }
   }
   
   public PatternKind getPatternKind(PatternInvocation invocation){
      return patternTypes.get(invocation);
   }
}
