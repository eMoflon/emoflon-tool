package org.moflon.gt.mosl.codeadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.moflon.gt.mosl.codeadapter.transformplanrules.TransformPlanRule;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternObject;
import org.moflon.gt.mosl.moslgt.PatternParameter;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.Scope;
import org.moflon.sdm.runtime.democles.VariableReference;

public class PatternBuilder
{
   private static PatternBuilder instance;

   private Map<String, List<Consumer<Variable>>> unfinishedLinkVariables;

   private final PatternKind[] searchOrder = { PatternKind.BLACK, PatternKind.GREEN, PatternKind.RED };

   private Map<String, Map<PatternKind, List<PatternObject>>> transformPlan;

   private Map<PatternKind, TransformPlanRule> transformPlanRuleCache;

   private Map<String, List<PatternInvocation>> patternInvocationCache;

   private PatternBuilder()
   {
      unfinishedLinkVariables = new HashMap<>();
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
      List<PatternInvocation> invocations = patternInvocationCache.get(patternName);
      invocations = new ArrayList<>();

      createTransformPlan(patternName, patternDef, bindings, env);
      Map<PatternKind, List<PatternObject>> patternPlan = transformPlan.get(patternName);

      for (PatternKind patternKind : patternPlan.keySet())
      {
         List<PatternObject> patternObjectIndex = patternPlan.get(patternKind);
         Pattern pattern = SpecificationFactory.eINSTANCE.createPattern();
         PatternBody patternBody = SpecificationFactory.eINSTANCE.createPatternBody();
         patternBody.setHeader(pattern);

         List<LinkVariablePattern> lvIndex = patternObjectIndex.stream().filter(po -> po instanceof LinkVariablePattern).map(po -> {
            return LinkVariablePattern.class.cast(po);
         }).collect(Collectors.toList());

         PatternInvocation invocation = createPatternInvocation(patternName, pattern);
         invocations.add(invocation);
         List<ObjectVariableDefinition> ovLst = patternObjectIndex.stream().filter(po -> po instanceof ObjectVariableDefinition).map(po -> {
            return ObjectVariableDefinition.class.cast(po);
         }).collect(Collectors.toList());
         for (ObjectVariableDefinition ov : ovLst)
         {

            EMFVariable patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
            pattern.getSymbolicParameters().add(patternVariable);

            patternVariable.setName(ov.getName());
            patternVariable.setEClassifier(ov.getType());

            finishLinkVariables(patternVariable);

            CFVariable cfVar = env.get(ov.getName());

            // ObjectVariableBuilder.getInstance().transformObjectVariable(PatternUtil.getCorrespondingOV(pp,
            // patternDef), patternVariable, bindings,
            // patternBody);

            VariableReference vr = DemoclesFactory.eINSTANCE.createVariableReference();
            vr.setInvocation(invocation);
            vr.setFrom(cfVar);
            vr.setTo(patternVariable);

         }
         
         VariableTransformator.getInstance().transformPatternObjects(lvIndex, bindings, patternBody, patternKind);

         pattern.setName(patternNameGenerator.apply(patternKind.getSuffix()));

         EClass eClass = EClassDef.class.cast(StatementBuilder.getInstance().getCurrentMethod().eContainer()).getName();
         CodeadapterTrafo.getInstance().loadResourceSet(eClass.eResource().getResourceSet());
         Resource patternResource = (Resource) EcoreUtil.getRegisteredAdapter(eClass, patternKind.getSuffix());
         if (patternResource != null)
         {
            patternResource.getContents().add(pattern);
            try
            {
               pattern.eResource().save(Collections.EMPTY_MAP);
            } catch (IOException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
      patternInvocationCache.put(patternName, invocations);
   }

   private void finishLinkVariables(Variable var)
   {
      List<Consumer<Variable>> toFinishLst = unfinishedLinkVariables.get(var.getName());
      if (toFinishLst != null)
      {
         toFinishLst.stream().forEach(finisher -> {
            finisher.accept(var);
         });
      }
      unfinishedLinkVariables.remove(var.getName());
   }

   public void addUnfinishedLinkVaraible(String targetName, Consumer<Variable> lvFinisher)
   {
      List<Consumer<Variable>> toFinishLst = unfinishedLinkVariables.get(targetName);
      if (toFinishLst == null)
      {
         toFinishLst = new ArrayList<>();
         unfinishedLinkVariables.put(targetName, toFinishLst);
      }
      toFinishLst.add(lvFinisher);
   }

   private PatternInvocation createPatternInvocation(String readAblePatternName, Pattern pattern)
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
