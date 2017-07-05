package org.moflon.gt.mosl.codeadapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.moflon.gt.mosl.codeadapter.transformplanrules.TransformPlanRule;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.Operator;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternObject;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.NodeDeletion;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.Scope;
import org.moflon.sdm.runtime.democles.VariableReference;

public class PatternBuilder
{
   private static PatternBuilder instance;

   private final PatternKind[] searchOrder = { PatternKind.BLACK, PatternKind.RED, PatternKind.GREEN };

   private Map<String, Map<PatternKind, List<PatternObject>>> transformPlan;

   private Map<PatternKind, TransformPlanRule> transformPlanRuleCache;

   private Map<String, List<PatternInvocation>> patternInvocationCache;
   
   private Map<String, List<String>> nodeDeletionNameCache;
   
   private Map<PatternInvocation, PatternKind> patternTypes;
   

   private PatternBuilder()
   {
      transformPlan = new HashMap<>();
      patternInvocationCache = new HashMap<>();
      transformPlanRuleCache = new HashMap<>();
      patternTypes = new HashMap<>();
      nodeDeletionNameCache = new HashMap<>();
   }

   public static PatternBuilder getInstance()
   {
      if (instance == null)
         instance = new PatternBuilder();
      return instance;
   }

   public PatternKind getPK(PatternInvocation invocation){
      return patternTypes.get(invocation);
   }
   
   public void addTransformPlanRule(PatternKind patternKind, TransformPlanRule transformPlanRule)
   {
      transformPlanRuleCache.put(patternKind, transformPlanRule);
   }

   /**
    * Creates a search plan
    * @param patternName which the patternName without the suffix
    * @param patternDef the pattern definition from XText
    * @param bindings the information which ControlFlowVariable is bound
    * @param env the environment where the ControlVariables are available
    */
   private void createTransformPlan(String patternName, PatternDef patternDef, Map<String, Boolean> bindings, Map<String, CFVariable> env)
   {
      for (PatternKind patternKind : searchOrder)
      {
         if (transformPlanRuleCache.get(patternKind) != null && transformPlanRuleCache.get(patternKind).isTransformable(patternKind, patternDef, bindings, env))
         {
            List<PatternObject> patternObjectIndex = transformPlanRuleCache.get(patternKind).getPatterObjectIndex();
            Map<PatternKind, List<PatternObject>> patternKindIndex = transformPlan.get(patternName);
            if (patternKindIndex == null)
               patternKindIndex = new HashMap<>();
            patternKindIndex.put(patternKind, patternObjectIndex);
            transformPlan.put(patternName, patternKindIndex);
         }
      }
      createNodeDeletion(patternName, patternDef);
   }
   
   private void createNodeDeletion(String patternName, PatternDef patternDef){
      List<String> deletions = new ArrayList<>();
      List<PatternObject> redObjects = transformPlan.get(patternName).get(PatternKind.RED);
      if(redObjects != null){
         deletions = redObjects.stream().filter(po -> po instanceof ObjectVariableDefinition && ObjectVariableDefinition.class.cast(po).getOp() != null && ObjectVariableDefinition.class.cast(po).getOp().getValue().equals("--"))
             .map(po -> ObjectVariableDefinition.class.cast(po).getName()).collect(Collectors.toList());
         if(deletions.size() > 0)
            nodeDeletionNameCache.put(patternName, deletions);
      }      
   }
   
   public void createPattern(PatternDef patternDef, Map<String, Boolean> bindings, Map<String, CFVariable> env, Function<String, String> patternNameGenerator)
   {
      String patternName = patternDef.getName();

      // creating a search plans
      createTransformPlan(patternName, patternDef, bindings, env);

      // get the found search plans
      Map<PatternKind, List<PatternObject>> patternPlan = transformPlan.get(patternName);

      List<PatternKind> patternKinds = Arrays.asList(searchOrder).stream().filter(pk -> patternPlan.keySet().contains(pk)).collect(Collectors.toList());
      
      // transform using the search plan
      patternInvocationCache.put(patternName, patternKinds.stream()
            .map(pk -> createPatternInvocation(pk, patternPlan.get(pk), patternName, bindings, env, patternNameGenerator)).collect(Collectors.toList()));
   }

   /**
    * Transform a Pattern using a found search plan
    * @param pk the PatternKind which will be registered
    * @param patternObjectIndex Plan the search plan
    * @param patternName which the patternName without the suffix
    * @param bindings the information which ControlFlowVariable is bound
    * @param env the environment where the ControlVariables are available
    * @param patternNameGenerator generates a pattern name to save using a suffix
    * @return A patternInvocation where the belonged pattern is registered
    */
   private PatternInvocation createPatternInvocation(PatternKind pk, List<PatternObject> patternObjectIndex, String patternName, Map<String, Boolean> bindings, Map<String, CFVariable> env, Function<String, String> patternNameGenerator){
      // creating Pattern hull
      Pattern pattern = SpecificationFactory.eINSTANCE.createPattern();
      PatternBody patternBody = SpecificationFactory.eINSTANCE.createPatternBody();
      patternBody.setHeader(pattern);
      PatternInvocation invocation = createNewPatternInvocation(patternName, pattern);
      patternTypes.put(invocation, pk);
      
      // handle ObjectVariables
      patternObjectIndex.stream().filter(po -> po instanceof ObjectVariableDefinition).map(po -> ObjectVariableDefinition.class.cast(po))
            .forEach(ov -> VariableTransformator.getInstance().transformObjectVariable(pattern, ov, env, invocation));
      
      // handle LinkVariables
      VariableTransformator.getInstance().transformPatternObjects(patternObjectIndex.stream().filter(po -> po instanceof LinkVariablePattern)
            .map(po -> LinkVariablePattern.class.cast(po)).collect(Collectors.toList()), bindings, patternBody, pk);

      //special case for black pattern
      if(pk == PatternKind.BLACK)
         VariableTransformator.getInstance().addUnequals(pattern, patternBody);
      
      //register Pattern
      pattern.setName(patternNameGenerator.apply(pk.getSuffix()));
      EClass eClass = EClassDef.class.cast(StatementBuilder.getInstance().getCurrentMethod().eContainer()).getName();
      CodeadapterTrafo.getInstance().saveAsRegisteredAdapter(pattern, CodeadapterTrafo.getInstance().getTypeContext(eClass), pk.getSuffix());
//      CodeadapterTrafo.getInstance().generateSearchPlan(pk.getSuffix(), calculateAdornment(invocation));
     // CodeadapterTrafo.getInstance().generateSearchPlan(pattern, calculateAdornment(invocation, bindings), invocation.isMultipleMatch(), pk.getSuffix());
      
      //register name
      PatternUtil.addKind(pattern.getName(), pk);
      PatternUtil.add(invocation, eClass);

      //return value
      return invocation;
   }
   
 //TODO@rkluge: Possible code duplication
 public Adornment calculateAdornment(PatternInvocation invocation, Map<String, Boolean> bindings)
 {
    Adornment adornment = new Adornment(invocation.getParameters().size());
    int i = 0;
    for(VariableReference variableRef : invocation.getParameters())
    {
       final int value = bindings.getOrDefault(variableRef.getFrom().getName(), false) ? Adornment.BOUND : Adornment.FREE;
       adornment.set(i, value);
       i++;
    }
    return adornment;
 }
   
   /**
    * Creates a new PatternInvocation
    * @param readAblePatternName which the patternName without the suffix
    * @param pattern the generated Pattern
    * @return a new PatternInvocation
    */
   private PatternInvocation createNewPatternInvocation(String readAblePatternName, Pattern pattern)
   {
      PatternInvocation invocation = DemoclesFactory.eINSTANCE.createRegularPatternInvocation();
      invocation.setPattern(pattern);
      return invocation;
   }

   public List<PatternInvocation> getPatternInvocation(String patternName)
   {
      return patternInvocationCache.get(patternName);
   }

   public void createResultPattern(ObjectVariableDefinition ov, Scope scope)
   {

   }
   
   public boolean isConstructorPattern(CFNode cfNode, CFVariable cfVar, PatternInvocation currentInvocation, List<MethodParameter> methodParameters, String patternName){
      // check if the name is "this"
      if(cfVar.getName().compareTo("this")==0)
         return false;
      
      // check if the variable is a parameter from the method
      Optional<?> parameterMonad = methodParameters.stream().filter(mp -> mp.getName().equals(cfVar.getName())).findFirst();
      if(parameterMonad.isPresent())
         return false;
      
      //check black pattern
      PatternInvocation blackInvocation = getInvocationIfVarExists(PatternKind.BLACK, cfNode, cfVar);
      if(blackInvocation != null && blackInvocation.equals(currentInvocation) && isCFVarCorrectKind(PatternKind.BLACK, cfVar, patternName))
         return true;
      else if(blackInvocation != null && !blackInvocation.equals(currentInvocation) && isCFVarCorrectKind(PatternKind.BLACK, cfVar, patternName))
         return false;
      
      //check green pattern
      PatternInvocation greenInvocation = getInvocationIfVarExists(PatternKind.GREEN, cfNode, cfVar);
      if(greenInvocation != null && greenInvocation.equals(currentInvocation) && isCFVarCorrectKind(PatternKind.GREEN, cfVar, patternName))
         return true;
      else if(greenInvocation != null && !greenInvocation.equals(currentInvocation) && isCFVarCorrectKind(PatternKind.GREEN, cfVar, patternName))
         return false;
      
      
      return false;
   }
   
   private boolean isCFVarCorrectKind(PatternKind pk, CFVariable cfVar, String patternName){
      List<ObjectVariableDefinition> ovs = transformPlan.get(patternName).get(pk).stream().filter(po -> po instanceof ObjectVariableDefinition)
            .map(po -> ObjectVariableDefinition.class.cast(po)).filter(ov -> ov.getName().equals(cfVar.getName())).collect(Collectors.toList());
      
      Optional<?> option = ovs.stream().filter(ov -> isCorrectType(ov.getOp(), pk)).findFirst();
      return option.isPresent();
   }
   
   private boolean isCorrectType(Operator op, PatternKind pk){
      if(pk == PatternKind.GREEN && op != null && op.getValue() != null && op.getValue().equals("++"))
         return true;
      else if(pk == PatternKind.BLACK && !(op!=null && op.getValue() != null && !op.getValue().equals("")))
         return true;
      else
         return false;         
   }
   
   private PatternInvocation getInvocationIfVarExists(PatternKind pk, CFNode cfNode, CFVariable cfVar){
      Optional<PatternInvocation> piMonad = cfNode.getActions().stream().filter(action -> action instanceof PatternInvocation).map(action -> PatternInvocation.class.cast(action)).filter(pi -> isCorrectPatternInvocation(pi, pk)).findFirst();
      if(piMonad.isPresent()){
         PatternInvocation invocation = piMonad.get();
         Optional<VariableReference> varRefMonad = invocation.getParameters().stream().filter(var -> var.getFrom().equals(cfVar)).findFirst();
         if(varRefMonad.isPresent())
            return invocation;
         else
            return null;
      }
      return null;
   }
   
   private boolean isCorrectPatternInvocation(PatternInvocation invocation, PatternKind pk){
      PatternKind otherPK = patternTypes.get(invocation);
      return otherPK != null && otherPK.equals(pk);
   }
   
   public NodeDeletion getNodeDeletion(String patternName, List<CFVariable> cfVars){
      NodeDeletion nodeDeletion = null; 
      List<String> nameList = nodeDeletionNameCache.get(patternName);
      if(nameList != null){
         nodeDeletion = DemoclesFactory.eINSTANCE.createNodeDeletion();
         nodeDeletion.getDestructedVariables().addAll(cfVars.stream().filter(cfVar -> nameList.contains(cfVar.getName())).collect(Collectors.toList()));
      }
      return nodeDeletion;
   }

}