package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.moflon.gt.mosl.codeadapter.CodeadapterTrafo;
import org.moflon.gt.mosl.codeadapter.PatternBuilder;
import org.moflon.gt.mosl.codeadapter.StatementBuilder;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.exceptions.PatternParameterSizeIsNotMatching;
import org.moflon.gt.mosl.moslgt.CalledPatternParameter;
import org.moflon.gt.mosl.moslgt.MethodParameter;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameter;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.Scope;

public interface IHandlePatternsInStatement extends IHandleCFVariable
{
   default void handlePattern(List<CalledPatternParameter> cpps, PatternDef patternDef, CFNode cfNode, Scope scope)
   {
      Map<String, Boolean> bindings = new HashMap<>();
      Map<String, CFVariable> env = new HashMap<>();
      List<CFVariable> cfVars = new ArrayList<>();
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

         CFVariable cfVar = getOrCreateVariable(scope, PatternUtil.getSaveName(ovRef.getName()), ovRef.getType());
         Action constructor = cfVar.getConstructor();
         if (constructor == null)
         {
//            Optional<MethodParameter> parameterMonad = methodParameters.stream().filter(p -> p.getName().equals(cfVar.getName())).findFirst();
//            if(parameterMonad.isPresent())
//              cfVar.setConstructor(DemoclesFactory.eINSTANCE.createAction()); // DummyAction
            cfVars.add(cfVar);
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

      List<PatternInvocation> invocations = PatternBuilder.getInstance().getPatternInvocation(patternName);
      
      invocations.stream().forEach(inv -> inv.setCfNode(cfNode));
      for (int piIndex = invocations.size() - 1; piIndex >= 0; piIndex--)
      {
         PatternInvocation invocation = invocations.get(piIndex);
//         int actionSize = cfNode.getActions().size();
         if (piIndex > 0)
         {
            cfNode.getActions().get(piIndex - 1).setNext(invocation);
         }
         cfNode.setMainAction(invocation);
         
         cfVars.stream().filter(cfVar -> PatternBuilder.getInstance().isConstructorPattern(cfNode, cfVar, invocation, methodParameters, patternName)).forEach(cfVar -> cfVar.setConstructor(invocation));
  
      }
   }

}
