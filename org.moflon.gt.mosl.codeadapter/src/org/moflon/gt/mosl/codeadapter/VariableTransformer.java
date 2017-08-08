package org.moflon.gt.mosl.codeadapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference;
import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraintFactory;
import org.gervarro.democles.specification.emf.constraint.relational.Unequal;
import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.codeadapter.utils.MOSLUtil;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.codeadapter.utils.VariableVisibility;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.VariableReference;

public class VariableTransformer
{
   private final TransformationConfiguration transformationConfiguration;
   private Function<String, Optional<Variable>> getVariableMonadFun;
   
   public VariableTransformer(TransformationConfiguration trafoConfig){
      transformationConfiguration=trafoConfig;
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
      String targetName = PatternUtil.getNormalizedVariableName(linkVariable.getTarget().getName());
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
      EClass contextEclass = transformationConfiguration.getContextController().getTypeContext(EClassDef.class.cast(ov.eContainer().eContainer()).getName());
      reference.setEModelElement(transformationConfiguration.getContextController().getEReferenceContext(linkVariable.getType(), contextEclass)); //TODO create an EReference to the contextEPackage
      patternBody.getConstraints().add(reference);

      ConstraintParameter source = SpecificationFactory.eINSTANCE.createConstraintParameter();
      reference.getParameters().add(source);
      source.setReference(this.getVariableMonad(PatternUtil.getNormalizedVariableName(ov.getName())).get());

      this.handleTarget(reference, linkVariable);

   }
   
   public void addUnequals(Pattern pattern, PatternBody patternBody){
      List<Variable> variables = pattern.getSymbolicParameters();
      for(int i = 0; i < variables.size(); i++){
         for(int k = i+1; k < variables.size(); k++){
            Unequal uneaqual = RelationalConstraintFactory.eINSTANCE.createUnequal();
            patternBody.getConstraints().add(uneaqual);
            ConstraintParameter from = SpecificationFactory.eINSTANCE.createConstraintParameter();
            from.setReference(variables.get(i));
            ConstraintParameter to = SpecificationFactory.eINSTANCE.createConstraintParameter();
            to.setReference(variables.get(k));
            
            uneaqual.getParameters().add(from);
            uneaqual.getParameters().add(to);
         }
      }
   }
   
   public Variable transformObjectVariable(Pattern pattern, ObjectVariableDefinition ov, VariableVisibility variableVisibility)
   {
      String name = PatternUtil.getNormalizedVariableName(ov.getName());
      PatternBody patternBody = pattern.getBodies().get(0);
      
      Variable variable = null; 
      Predicate<Variable> condition= var -> var.getName().equals(name);
      Optional<Variable> existingVariable =null;
      if(VariableVisibility.GLOBAL == variableVisibility)
         existingVariable = pattern.getSymbolicParameters().stream().filter(condition).findFirst();
      else
         existingVariable = patternBody.getLocalVariables().stream().filter(condition).findFirst();
      
      if (!existingVariable.isPresent())
      {
         final EMFVariable patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
         variableVisibility.addObjectVariableToToPattern(pattern, patternVariable);

         patternVariable.setName(name);
         patternVariable.setEClassifier(transformationConfiguration.getContextController().getTypeContext(ov.getType()));
         
         variable = patternVariable;

      }else{
         variable = existingVariable.get();
      }
      
      return variable;
   }
   
}
