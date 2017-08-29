package org.moflon.gt.mosl.codeadapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.gervarro.democles.specification.emf.Constraint;
import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.PatternInvocationConstraint;
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
import org.moflon.gt.mosl.moslgt.PatternObject;
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
   
   public void transformPatternObjects(List<LinkVariablePattern> lvs, PatternBody patternBody, PatternKind patternKind){
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
      Optional<Variable> variableMonad = this.getVariableMonad(targetName);
      target.setReference(variableMonad.get());
   }
   
   public void transforming(LinkVariablePattern linkVariable, PatternBody patternBody, PatternKind patternKind)
   {
         getVariableMonadFun = variableMonadFun(patternBody);
         transformLinkVariable(linkVariable, patternBody, patternKind);
   }
   
   private Function<String, Optional<Variable>> variableMonadFun(PatternBody patternBody){
      Set<Variable> variables = initSet(patternBody);
      variables.addAll(collectAllVariables(patternBody));
      return varName -> variables.stream().filter(var -> findVariable(var, varName)).findFirst();
   }
   
   private Set<Variable> collectAllVariables(PatternBody patternBody){
      Set<Variable> variables = patternBody.getLocalVariables().stream().collect(Collectors.toSet());
      variables.addAll(patternBody.getHeader().getSymbolicParameters());
      return variables;
   }
   
   private Set<Variable> initSet(PatternBody patternBody){
      Optional<Pattern> srcPatternMonad = transformationConfiguration.getPatternCreationController().getSourcePattern(patternBody.getHeader());
      if(srcPatternMonad.isPresent()){
        Pattern pattern = srcPatternMonad.get();
        return collectAllVariables(pattern.getBodies().get(0));
      }else {
         return new HashSet<>();
      }
   }
   
   private boolean findVariable(Variable variable, String varName){
      return variable.getName().compareTo(varName)==0;
   }
   
   private void transformLinkVariable(LinkVariablePattern linkVariable, PatternBody patternBody, PatternKind patternKind)
   {
      ObjectVariableDefinition ov = ObjectVariableDefinition.class.cast(linkVariable.eContainer());
      Reference reference = EMFTypeFactory.eINSTANCE.createReference();
      EClass contextEclass = transformationConfiguration.getContextController().getTypeContext(getTypeContextFromOV(ov));
      reference.setEModelElement(transformationConfiguration.getContextController().getEReferenceContext(linkVariable.getType(), contextEclass)); //TODO create an EReference to the contextEPackage
      patternBody.getConstraints().add(reference);

      ConstraintParameter source = SpecificationFactory.eINSTANCE.createConstraintParameter();
      reference.getParameters().add(source);
      source.setReference(this.getVariableMonad(PatternUtil.getNormalizedVariableName(ov.getName())).get());

      this.handleTarget(reference, linkVariable);

   }
   
   private EClass getTypeContextFromOV(EObject eObject){
      if(eObject instanceof EClassDef)
         return EClassDef.class.cast(eObject).getName();
      else if(eObject == null)
         throw new RuntimeException("Unknown Type or Container");
      else
         return getTypeContextFromOV(eObject.eContainer());
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
   
   public void addVariableReferencesToInvocation(PatternInvocation invocation, Map<String, CFVariable> env, Variable variable){
      CFVariable cfVar = env.get(PatternUtil.getNormalizedVariableName(variable.getName()));

      VariableReference vr = DemoclesFactory.eINSTANCE.createVariableReference();
      vr.setInvocation(invocation);
      vr.setFrom(cfVar);
      vr.setTo(variable);
   }
   
   public PatternInvocationConstraint createPatternInvocationConstraint (Map<String, VariableVisibility> nacVisibility, List <Variable> variables){
      Set<String> globalVariableVisibilitiyNames = nacVisibility.entrySet().stream().filter(entry -> entry.getValue()== VariableVisibility.GLOBAL).map(entry -> entry.getKey()).collect(Collectors.toSet());
      PatternInvocationConstraint patternInvocationConstraint = SpecificationFactory.eINSTANCE.createPatternInvocationConstraint();
      List<Variable> globalVariables = variables.stream().filter(var -> globalVariableVisibilitiyNames.contains(var.getName())).collect(Collectors.toList());
      globalVariables.forEach(var -> addConstraintParameter(patternInvocationConstraint, var));
      return patternInvocationConstraint;
   }
   
   private void addConstraintParameter(Constraint constraint, Variable variable){
      ConstraintParameter parameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
      parameter.setReference(variable);
      constraint.getParameters().add(parameter);
   }
   
   public Map<String, VariableVisibility> getNacVisibility (Set<String> srcOVNames, List<PatternObject> nacPatternObjectIndex){
      Map<String, VariableVisibility> nacVisibility = new HashMap<>();
      MOSLUtil.mapToSubtype(nacPatternObjectIndex, ObjectVariableDefinition.class).forEach(ov -> addVisibility(ov, srcOVNames, nacVisibility));
      return nacVisibility;
   }
   
   private void addVisibility(ObjectVariableDefinition ov, Set<String> srcOVNames, Map<String, VariableVisibility> nacVisibility){
      if(srcOVNames.contains(ov.getName()))
         nacVisibility.put(ov.getName(), VariableVisibility.GLOBAL);
      else
         nacVisibility.put(ov.getName(), VariableVisibility.LOCAL);
   }
}
