package org.moflon.gt.mosl.codeadapter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.moflon.gt.mosl.codeadapter.transformplanrules.TransformPlanRule;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternObject;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.Scope;

public class PatternBuilder
{
   private static PatternBuilder instance;

   private final PatternKind[] searchOrder = { PatternKind.BLACK, PatternKind.GREEN, PatternKind.RED };

   private Map<String, Map<PatternKind, List<PatternObject>>> transformPlan;

   private Map<PatternKind, TransformPlanRule> transformPlanRuleCache;

   private Map<String, List<PatternInvocation>> patternInvocationCache;

   private PatternBuilder()
   {
      transformPlan = new HashMap<>();
      patternInvocationCache = new HashMap<>();
      transformPlanRuleCache = new HashMap<>();
   }

   public static PatternBuilder getInstance()
   {
      if (instance == null)
         instance = new PatternBuilder();
      return instance;
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
   }

   public void createPattern(PatternDef patternDef, Map<String, Boolean> bindings, Map<String, CFVariable> env, Function<String, String> patternNameGenerator)
   {
      String patternName = patternDef.getName();

      // creating a search plans
      createTransformPlan(patternName, patternDef, bindings, env);

      // get the found search plans
      Map<PatternKind, List<PatternObject>> patternPlan = transformPlan.get(patternName);

      // transform using the search plan
      patternInvocationCache.put(patternName, patternPlan.keySet().stream()
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
      
      // handle ObjectVariables
      patternObjectIndex.stream().filter(po -> po instanceof ObjectVariableDefinition).map(po -> ObjectVariableDefinition.class.cast(po))
            .forEach(ov -> VariableTransformator.getInstance().transformObjectVariable(pattern, ov, env, invocation));
      
      // handle LinkVariables
      VariableTransformator.getInstance().transformPatternObjects(patternObjectIndex.stream().filter(po -> po instanceof LinkVariablePattern)
            .map(po -> LinkVariablePattern.class.cast(po)).collect(Collectors.toList()), bindings, patternBody, pk);

      //register Pattern
      pattern.setName(patternNameGenerator.apply(pk.getSuffix()));
      EClass eClass = EClassDef.class.cast(StatementBuilder.getInstance().getCurrentMethod().eContainer()).getName();
      CodeadapterTrafo.getInstance().loadResourceSet(eClass.eResource().getResourceSet());
      Resource patternResource = (Resource) EcoreUtil.getRegisteredAdapter(eClass, pk.getSuffix());
      if (patternResource != null)
      {
         patternResource.getContents().add(pattern);
         try
         {
            pattern.eResource().save(Collections.EMPTY_MAP);
         } catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      return invocation;
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

}
