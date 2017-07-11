package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.gervarro.democles.common.Adornment;
import org.moflon.gt.mosl.codeadapter.CodeadapterTrafo;
import org.moflon.gt.mosl.codeadapter.PatternBuilder;
import org.moflon.gt.mosl.codeadapter.StatementBuilder;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.exceptions.PatternParameterSizeIsNotMatching;
import org.moflon.gt.mosl.moslgt.CalledPatternParameter;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameter;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.NodeDeletion;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.Scope;
import org.moflon.sdm.runtime.democles.VariableReference;

public interface IHandlePatternsInStatement extends IHandleCFVariable
{
   default void handlePattern(List<CalledPatternParameter> cpps, PatternDef patternDef, CFNode cfNode, Scope scope)
   {
      Map<String, Boolean> bindings = new HashMap<>();
      Map<String, CFVariable> env = new HashMap<>();
      List<CFVariable> cfVariables = new ArrayList<>();
      String patternName = patternDef.getName();
      List<MethodParameter> methodParameters = StatementBuilder.getInstance().getCurrentMethod().getParameters();

      List<PatternParameter> patternParameters = patternDef.getParameters();
      int size = patternParameters.size();
      if (size != cpps.size())
         throw new PatternParameterSizeIsNotMatching();

      Set<ObjectVariableDefinition> ovs = new HashSet<>(patternDef.getObjectVariables());
      ovs.addAll(patternDef.getParameters().stream().map(pp -> PatternUtil.getCorrespondingOV(pp, patternDef)).collect(Collectors.toSet()));
      
      // Binding Handling
      for (ObjectVariableDefinition ovRef : ovs)
      {

         CFVariable cfVar = getOrCreateVariable(scope, PatternUtil.getSafeName(ovRef.getName()), ovRef.getType());
         Action constructor = cfVar.getConstructor();
         if (constructor == null)
         {
            cfVariables.add(cfVar);
            bindings.put(cfVar.getName(), false);
            env.put(cfVar.getName(), cfVar);
         } else
         {
            bindings.put(cfVar.getName(), true);
            env.put(cfVar.getName(), cfVar);
         }
      }

      // Pattern Handling
      Function<String, String> nameGenerator = suffix -> {
         return CodeadapterTrafo.getInstance().getPatternName(cfNode, patternDef, suffix);
      };
      PatternBuilder.getInstance().createPattern(patternDef, bindings, env, nameGenerator);

      final SortedMap<PatternKind, PatternInvocation> invocations = PatternBuilder.getInstance().getPatternInvocations(patternName);
      
      invocations.values().stream().forEach(invocation -> invocation.setCfNode(cfNode));
      final List<PatternKind> kindOrder = new ArrayList<PatternKind>(invocations.keySet());
      for (int invocationIndex = invocations.size() - 1; invocationIndex >= 0; invocationIndex--)
      {
         PatternKind patternKind = kindOrder.get(invocationIndex);
         final PatternInvocation invocation = invocations.get(patternKind);
         if (invocationIndex > 0)
         {
            cfNode.getActions().get(invocationIndex - 1).setNext(invocation);
         }
         cfNode.setMainAction(invocation);
         
         cfVariables.stream().filter(cfVar -> PatternBuilder.getInstance().isConstructorPattern(cfNode, cfVar, invocation, methodParameters, patternName)).forEach(cfVar -> cfVar.setConstructor(invocation));
  
         final Adornment adornment = calculateAdornment(invocation);
         //TODO@rkluge: Here, we escape from a stateless function to the very stateful CodeAdapterTrafo singelton
         final ValidationReport validationReport = CodeadapterTrafo.getInstance().generateSearchPlan(invocation.getPattern(), adornment, invocation.isMultipleMatch(), patternKind.getSuffix());
         //TODO@rkluge: For Debugging - should be returned!
         System.out.println(validationReport.getErrorMessages());
      }
      
      final NodeDeletion nodeDeletion = PatternBuilder.getInstance().getNodeDeletion(patternName, cfVariables);
      if(nodeDeletion != null){
         PatternInvocation lastInvocation = invocations.get(kindOrder.get(invocations.size() - 1));
         lastInvocation.setNext(nodeDeletion);
         nodeDeletion.setPrev(lastInvocation);
         nodeDeletion.setCfNode(cfNode);
      }
   }
   
   default Adornment calculateAdornment(PatternInvocation invocation)
   {
      final EList<VariableReference> parameters = invocation.getParameters();
      final Adornment adornment = new Adornment(parameters.size());
      int i = 0;
      for(final VariableReference variableRef : parameters)
      {
         final int value = variableRef.isFree() ? Adornment.FREE : Adornment.BOUND;
         adornment.set(i, value);
         i++;
      }
      return adornment;
   }

}
