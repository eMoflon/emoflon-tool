package org.moflon.gt.mosl.codeadapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

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
      String targetName = linkVariable.getTarget().getName();

      Optional<Variable> targetMonad = this.getVariableMonad(targetName);

      if (targetMonad.isPresent())
      {
         target.setReference(targetMonad.get());
      } else
      {
         PatternBuilder.getInstance().addUnfinishedLinkVaraible(targetName, var -> {
            target.setReference(var);
         });
      }

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
      source.setReference(this.getVariableMonad(ov.getName()).get());

      this.handleTarget(reference, linkVariable);

   }
}
