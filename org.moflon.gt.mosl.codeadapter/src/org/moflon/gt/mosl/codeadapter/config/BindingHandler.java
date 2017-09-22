package org.moflon.gt.mosl.codeadapter.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.moflon.gt.mosl.codeadapter.utils.GTBinding;
import org.moflon.gt.mosl.codeadapter.utils.MOSLUtil;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.ObjectVariablePattern;
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
   
   public GTBinding createBinding(PatternDef patternDef, ObjectVariablePattern objectVariable){
      GTBinding binding = createGTBinding(patternDef, objectVariable);
      addBinding(patternDef.getName(), binding, objectVariable);
      return binding;
   }
   
   private void addBinding(String patternName, GTBinding binding, ObjectVariablePattern objectVariable){
      Map<String, GTBinding> bindings = bindingCache.getOrDefault(patternName, new HashMap<>());
      bindings.put(objectVariable.getName(), binding);
      bindingCache.put(patternName, bindings);
   }
   
   public void addExpressionForBinding(ObjectVariablePattern objectVariable, EClass eClass){
      String name = eClass.getName() + "_Expressions";
      addBinding(name, GTBinding.BOUND, objectVariable);
   }
   
   private GTBinding createGTBinding(PatternDef patternDef, ObjectVariablePattern objectVariable){
      List<ObjectVariablePattern>paramOVs = patternDef.getParameters().stream().map(pp -> pp.getOv()).collect(Collectors.toList());
      if(MOSLUtil.exist(paramOVs, ov->ov.getName().compareTo(objectVariable.getName())==0))
         return GTBinding.BOUND;
      else
         return GTBinding.FREE;
   }
   
   public GTBinding getExpressionBinding(ObjectVariablePattern objectVariable, EClass eClass){
      String name= eClass.getName() + "_Expression";
      return getBinding(name, objectVariable);
   }
   
   public GTBinding getBinding(PatternDef patternDef, ObjectVariablePattern objectVariable){
      return getBinding(patternDef.getName(), objectVariable.getName());
   }
   
   public GTBinding getBinding(String patternName, ObjectVariablePattern objectVariable){
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
