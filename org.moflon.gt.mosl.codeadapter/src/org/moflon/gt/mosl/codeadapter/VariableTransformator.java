package org.moflon.gt.mosl.codeadapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
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
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
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
      EClass contextEclass = CodeadapterTrafo.getInstance().getTypeContext(EClassDef.class.cast(PatternDef.class.cast(ov.eContainer()).eContainer()).getName());
      reference.setEModelElement(CodeadapterTrafo.getInstance().getEReferenceContext(linkVariable.getType(), contextEclass)); //TODO create an EReference to the contextEPackage
      patternBody.getConstraints().add(reference);

      ConstraintParameter source = SpecificationFactory.eINSTANCE.createConstraintParameter();
      reference.getParameters().add(source);
      source.setReference(this.getVariableMonad(PatternUtil.getSaveName(ov.getName())).get());

      this.handleTarget(reference, linkVariable);

   }
   
   public void transformObjectVariable(Pattern pattern, ObjectVariableDefinition ov, Map<String, CFVariable> env, PatternInvocation invocation)
   {
      EMFVariable patternVariable;
      String name = PatternUtil.getSaveName(ov.getName());
      Optional<Variable> patternVariableMonad = pattern.getSymbolicParameters().stream().filter(var -> var.getName().compareTo(name) == 0).findFirst();
      if (!patternVariableMonad.isPresent())
      {
         patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
         pattern.getSymbolicParameters().add(patternVariable);

         patternVariable.setName(name);
         patternVariable.setEClassifier(CodeadapterTrafo.getInstance().getTypeContext(ov.getType()));

         CFVariable cfVar = env.get(PatternUtil.getSaveName(ov.getName()));

         VariableReference vr = DemoclesFactory.eINSTANCE.createVariableReference();
         vr.setInvocation(invocation);
         vr.setFrom(cfVar);
         vr.setTo(patternVariable);
      }
   }
   
}
