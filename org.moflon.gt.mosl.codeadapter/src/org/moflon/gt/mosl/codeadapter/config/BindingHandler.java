package org.moflon.gt.mosl.codeadapter.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.moflon.gt.mosl.codeadapter.utils.GTBinding;
import org.moflon.gt.mosl.codeadapter.utils.MOSLUtil;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.VariableReference;

public class BindingHandler
{
   private Map<String, Map<String, GTBinding>> bindingCache;
   
   final private TransformationConfiguration trafoConfig;
   
   public BindingHandler(TransformationConfiguration transformationConfiguration){
      bindingCache = new HashMap<>();
      trafoConfig = transformationConfiguration;
   }
   
   public GTBinding createBinding(PatternDef patternDef, ObjectVariableDefinition objectVariable){
      Map<String, GTBinding> bindings = bindingCache.getOrDefault(patternDef.getName(), new HashMap<>());
      GTBinding binding = createGTBinding(patternDef, objectVariable);
      bindings.put(objectVariable.getName(), binding);
      bindingCache.put(patternDef.getName(), bindings);
      return binding;
   }
   
   private GTBinding createGTBinding(PatternDef patternDef, ObjectVariableDefinition objectVariable){
      List<ObjectVariableDefinition>paramOVs = patternDef.getParameters().stream().map(pp -> pp.getOv()).collect(Collectors.toList());
      if(MOSLUtil.exist(paramOVs, ov->ov.getName().compareTo(objectVariable.getName())==0))
         return GTBinding.BOUND;
      else
         return GTBinding.FREE;
   }
   
   public GTBinding getBinding(PatternDef patternDef, ObjectVariableDefinition objectVariable){
      return getBinding(patternDef.getName(), objectVariable.getName());
   }
   
   public GTBinding getBinding(String patternName, ObjectVariableDefinition objectVariable){
      return getBinding(patternName, objectVariable.getName());
   }
   
   public GTBinding getBinding(PatternDef patternDef, String ovName){
      return getBinding(patternDef.getName(), ovName);
   }
   
   public GTBinding getBinding(String patternName, String ovName){
      Map<String, GTBinding> bindings = bindingCache.getOrDefault(patternName, new HashMap<>());
      if(bindings.size() == 0){
         throw new RuntimeException("No Bindings are set");
      }
      GTBinding binding = bindings.get(ovName);
      if(binding == null)
         throw new RuntimeException("Unknown ObjectVariable");
      return binding;
   }
   
   public void setDemoclesBindings(String patternName, PatternInvocation invocation){
      invocation.getParameters().forEach(varRef -> setDemoclesBinding(getBinding(patternName, varRef.getFrom().getName()), varRef, invocation));
   }
   
   private void setDemoclesBinding(GTBinding binding, VariableReference varRef, PatternInvocation invocation){
      if(binding == GTBinding.FREE && trafoConfig.getPatternCreationController().getPatternKind(invocation)==PatternKind.BLACK){
         CFVariable cfVar = varRef.getFrom();
         cfVar.setConstructor(invocation);
      }
   }
   
}
