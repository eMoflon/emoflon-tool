package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.CodeadapterTrafo;
import org.moflon.gt.mosl.codeadapter.PatternBuilder;
import org.moflon.gt.mosl.exceptions.NoMatchingVariableFound;
import org.moflon.gt.mosl.exceptions.PatternParameterSizeIsNotMatching;
import org.moflon.gt.mosl.moslgt.CalledPatternParameter;
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
      List<Consumer<PatternInvocation>> setConstructors = new ArrayList<>();
      String patternName = patternDef.getName();

      List<PatternParameter> patternParameters = patternDef.getParameters();
      int size = patternParameters.size();
      if (size != cpps.size())
         throw new PatternParameterSizeIsNotMatching();

      // Binding Handling
      for (int index = 0; index < size; index++)
      {

         ObjectVariableDefinition ovRef = cpps.get(index).getDefiningOV();
         if (ovRef == null)
         {
            ovRef = cpps.get(index).getExistingOV();
         }

         CFVariable cfVar = getOrCreateVariable(scope, ovRef.getName(), ovRef.getType());
         Action constructor = cfVar.getConstructor();
         if (constructor == null)
         {
            cfVar.setConstructor(DemoclesFactory.eINSTANCE.createAction()); // DummyAction
            setConstructors.add(invocation -> {
               cfVar.setConstructor(invocation);
            });
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

      PatternInvocation invocation = PatternBuilder.getInstance().getPatternInvocation(patternName);
      int actionSize = cfNode.getActions().size();
      if (actionSize > 0)
      {
         cfNode.getActions().get(actionSize - 1).setNext(invocation);
      }
      cfNode.setMainAction(invocation);
      invocation.setCfNode(cfNode);

      Pattern pattern = invocation.getPattern();
      if (size != pattern.getSymbolicParameters().size())
         throw new PatternParameterSizeIsNotMatching();

      for (int index = 0; index < size; index++)
      {
         setConstructors.get(index).accept(invocation);
         ObjectVariableDefinition patternParOV = patternParameters.get(index).getOv();
         Variable var = pattern.getSymbolicParameters().get(index);
         if (var.getName().compareTo(patternParOV.getName()) != 0)
            throw new NoMatchingVariableFound();
      }
   }

}
