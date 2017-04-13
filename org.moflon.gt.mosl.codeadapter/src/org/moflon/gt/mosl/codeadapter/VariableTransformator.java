package org.moflon.gt.mosl.codeadapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.VariableReference;

public class VariableTransformator
{
   private static VariableTransformator instance;
   
   private VariableTransformator(){
      
   }
   
   private Function<String, Optional<Variable>> getVariableMonadFun;
   
   public static VariableTransformator getInstance(){
      if(instance == null)
         instance = new VariableTransformator();
      return instance;
   }
   
   public void transformPatternObjects(List<LinkVariablePattern> lvs, Map<String, Boolean> bindings, PatternBody patternBody, PatternKind patternKind){
      lvs.stream().forEach(lv -> {transforming(lv, patternBody, patternKind);});
   }

   private Optional<Variable> getVariableMonad(String varName)
   {
      return getVariableMonadFun.apply(varName);
   }
   
   private void handleTarget(Reference reference, LinkVariablePattern linkVariable)
   {
      ConstraintParameter target = SpecificationFactory.eINSTANCE.createConstraintParameter();
      reference.getParameters().add(target);
      String targetName = PatternUtil.getSaveName(linkVariable.getTarget().getName());
      target.setReference(this.getVariableMonad(targetName).get());
   }
   
   public void transforming(LinkVariablePattern linkVariable, PatternBody patternBody, PatternKind patternKind)
   {
         getVariableMonadFun = varName -> {
            return patternBody.getHeader().getSymbolicParameters().stream().filter(var -> var.getName().compareTo(varName) == 0).findFirst();
         };
         transformLinkVariable(linkVariable, patternBody, patternKind);
   }
   
   private void transformLinkVariable(LinkVariablePattern linkVariable, PatternBody patternBody, PatternKind patternKind)
   {
      ObjectVariableDefinition ov = ObjectVariableDefinition.class.cast(linkVariable.eContainer());
      Reference reference = EMFTypeFactory.eINSTANCE.createReference();
      reference.setEModelElement(linkVariable.getType());
      patternBody.getConstraints().add(reference);

      ConstraintParameter source = SpecificationFactory.eINSTANCE.createConstraintParameter();
      reference.getParameters().add(source);
      source.setReference(this.getVariableMonad(PatternUtil.getSaveName(ov.getName())).get());

      this.handleTarget(reference, linkVariable);

   }
   
   public void transformObjectVariable(Pattern pattern, ObjectVariableDefinition ov, Map<String, CFVariable> env, PatternInvocation invocation){
      EMFVariable patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
      pattern.getSymbolicParameters().add(patternVariable);

      patternVariable.setName(PatternUtil.getSaveName(ov.getName()));
      patternVariable.setEClassifier(ov.getType());

      CFVariable cfVar = env.get(PatternUtil.getSaveName(ov.getName()));

      VariableReference vr = DemoclesFactory.eINSTANCE.createVariableReference();
      vr.setInvocation(invocation);
      vr.setFrom(cfVar);
      vr.setTo(patternVariable);
   }
   
}
