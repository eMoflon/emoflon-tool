package org.moflon.tgg.algorithm.delta.attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.csp.codegenerator.MyBasicFormatRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import SDMLanguage.expressions.ComparingOperator;
import SDMLanguage.expressions.ComparisonExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.expressions.LiteralExpression;
import SDMLanguage.patterns.AttributeAssignment;
import SDMLanguage.patterns.BindingOperator;
import SDMLanguage.patterns.BindingSemantics;
import SDMLanguage.patterns.Constraint;
import SDMLanguage.patterns.ObjectVariable;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;
import org.moflon.tgg.language.DomainType;
import org.moflon.tgg.language.TGGObjectVariable;
import org.moflon.tgg.language.TGGRule;
import org.moflon.tgg.language.compiler.TGGCompiler;
import org.moflon.tgg.language.compiler.compilerfacade.CSPSearchPlanAdapter;
import org.moflon.tgg.language.compiler.compilerfacade.CompilerfacadeFactory;
import org.moflon.tgg.language.csp.AttributeVariable;
import org.moflon.tgg.language.csp.CSP;
import org.moflon.tgg.language.csp.CspFactory;
import org.moflon.tgg.language.csp.Literal;
import org.moflon.tgg.language.csp.LocalVariable;
import org.moflon.tgg.language.csp.OperationalCSP;
import org.moflon.tgg.language.csp.TGGConstraint;
import org.moflon.tgg.language.csp.Variable;

public class AttributeConstraintCodeGenerator
{
   private TGGRule rule;

   private String name;

   private String separator;

   private STGroupFile stg;

   ArrayList<String> source;

   ArrayList<String> target;

   private StringBuilder code;

   private ArrayList<AttributConstraintContainer> csp_solver;

   private OperationalCSP ocsp;

   private TGGCompiler compiler;

   public AttributeConstraintCodeGenerator(final TGGRule rule, final String name, final TGGCompiler compiler)
   {
      this.separator = "\n\n";
      this.stg = new STGroupFile(this.getClass().getClassLoader().getResource("/templates/Csp.stg"), "UTF-8", '<', '>');
      this.stg.registerRenderer(String.class, new MyBasicFormatRenderer());
      this.code = new StringBuilder();
      this.compiler = compiler;

      this.rule = rule;
      this.name = name;

      locateObjectVariables();
      initOCSP();
   }

   public String getInjection()
   {
      createAttributeConstraintRuleResult();
      handleAttributeAssignmentsAndConstraints();
      handleCSPSolving();
      return code.toString();
   }

   private void locateObjectVariables()
   {
      source = new ArrayList<String>();
      target = new ArrayList<String>();

      for (ObjectVariable ov : rule.getObjectVariable())
      {
         if (ov instanceof TGGObjectVariable)
         {
            TGGObjectVariable tggOV = (TGGObjectVariable) ov;
            if (tggOV.getDomain().getType() == DomainType.SOURCE)
            {
               source.add(ov.getName());
            }
            if (tggOV.getDomain().getType() == DomainType.TARGET)
            {
               target.add(ov.getName());
            }
         }
      }
   }

   private void initOCSP()
   {
      CSP csp = EcoreUtil.copy(rule.getCsp());
      ocsp = CspFactory.eINSTANCE.createOperationalCSP();
      ocsp.getConstraints().addAll(csp.getConstraints());
      ocsp.getVariables().addAll(csp.getVariables());

      // First of all everything bound apart from local variables
      ocsp.getVariables().forEach(v -> {
         if (!(v instanceof LocalVariable))
            v.setBound(true);
      });

      // If OV is green in the rule then it may be changed to free (for the right domain)
      Collection<String> destVarNames = isOutputDomainTarget() ? target : source;
      Collection<Variable> destVars = getDestVariables(ocsp.getVariables(), destVarNames);
      destVars.forEach(v -> {
         if (v instanceof AttributeVariable)
         {
            AttributeVariable av = AttributeVariable.class.cast(v);
            rule.getObjectVariable().forEach(ov -> {
               if (ov.getName().equals(av.getObjectVariable()) && ov.getBindingOperator().equals(BindingOperator.CREATE))
                  v.setBound(false);
            });
         }
      });

      // Solve the CSP to ensure that this bounding state is possible
      CSPSearchPlanAdapter plan = CompilerfacadeFactory.eINSTANCE.createCSPSearchPlanAdapter();
      plan.computeConstraintOrder(ocsp);
   }

   private void createAttributeConstraintRuleResult()
   {
      ST rR = stg.getInstanceOf("createRuleResult");
      rR.add("ruleName", rule.getName());
      code.append(rR.render() + separator);
   }

   private String buildMethodCall(final String attribute, final String type, final String ov)
   {
      String prefix = ".get" + StringUtils.capitalize(attribute);
      if ("boolean".equals(type) || "Boolean".equals(type) || "EBoolean".equals(type))
      {
         prefix = MoflonUtil.handlePrefixForBooleanAttributes("", attribute);
      }
      return ov + prefix + "()";
   }

   private String getOperand(final Expression expression)
   {
      if (expression instanceof LiteralExpression)
         return ((LiteralExpression) expression).getValue();

      AttributeValueExpression attrValueExp;
      if (expression instanceof AttributeValueExpression)
      {
         attrValueExp = (AttributeValueExpression) expression;
         return buildMethodCall(attrValueExp.getAttribute().getName(), attrValueExp.getAttribute().getEAttributeType().getName(), attrValueExp.getObject()
               .getName());

      }

      return "null";
   }

   private String handleType(final EClassifier type)
   {
      // Some Java dependent adjustments
      String typeName = type.getName();
      if ("boolean".equals(typeName))
         return "Boolean";

      if (CORE_ECORE_CLASS_NAMES.contains(typeName))
         return "org.eclipse.emf.ecore." + typeName;

      String fqn = MoflonUtil.getFQN(type);
      
      // Return immediately if the type is only contained in the default package
      if(fqn.lastIndexOf('.') == -1){
         return fqn;
      }
      
      String fullyQualifiedPackageName = fqn.substring(0, fqn.lastIndexOf('.'));

      String remappedPackage = this.compiler.correctPathWithMappings(fullyQualifiedPackageName);
      String remappedType = remappedPackage + "." + typeName;
      return remappedType;
   }

   private String getComparisonOp(final ComparingOperator comparingOperator)
   {
      String result = "*";
      switch (comparingOperator)
      {
      case LESS:
         result = "<";
         break;
      case LESS_OR_EQUAL:
         result = "<=";
         break;
      case EQUAL:
         result = "==";
         break;
      case GREATER_OR_EQUAL:
         result = ">=";
         break;
      case GREATER:
         result = ">";
         break;
      case UNEQUAL:
         result = "!=";
         break;
      default:
         break;
      }
      return result;
   }

   private Collection<Variable> getDestVariables(final Collection<Variable> variables, final Collection<String> relevantOVs)
   {
      return variables.stream().filter(v -> {
         if (v instanceof AttributeVariable)
         {
            AttributeVariable av = (AttributeVariable) v;
            if (relevantOVs.contains(av.getObjectVariable()))
               return true;
         }

         return false;
      }).collect(Collectors.toList());
   }

   private void handleAttributeAssignmentsAndConstraints()
   {
      HashMap<String, String> variables = new HashMap<String, String>();

      StringBuilder locateObjects = new StringBuilder();
      StringBuilder attrConsResult = new StringBuilder();

      EList<ObjectVariable> ovs = rule.getObjectVariable();

      // create Mapping for AttributeConstraints
      for (ObjectVariable ov : ovs)
      {
         if (ov.getBindingSemantics().equals(BindingSemantics.NEGATIVE))
            continue;

         ST oVT = stg.getInstanceOf("locate_node");
         oVT.add("var_name", ov.getName());
         oVT.add("type", handleType(ov.getType()));
         variables.put(ov.getName(), ov.getType().getName());
         locateObjects.append(oVT.render());
         locateObjects.append(separator);

         // handle AttributeAssignments
         for (AttributeAssignment a : ov.getAttributeAssignment())
         {
            String op1 = buildMethodCall(a.getAttribute().getName(), a.getAttribute().getEAttributeType().getName(), a.getObjectVariable().getName());
            String op2 = getOperand(a.getValueExpression());
            attrConsResult.append(buildAssignmentsAndConstraints(op1, op2, "==", a.getAttribute().getEType().getName()));
            attrConsResult.append(separator);
         }

         // handle Constraints
         for (Constraint c : ov.getConstraint())
         {
            Expression exp = c.getConstraintExpression();
            if (exp instanceof ComparisonExpression)
            {
               String op1 = getOperand(((ComparisonExpression) exp).getLeftExpression());
               String op2 = getOperand(((ComparisonExpression) exp).getRightExpression());
               String comp = getComparisonOp(((ComparisonExpression) exp).getOperator());
               attrConsResult.append(buildAssignmentsAndConstraints(op1, op2, comp, isOperandOfTypeString(((ComparisonExpression) exp).getLeftExpression())));
               attrConsResult.append(separator);
            }
         }
      }

      code.append(locateObjects.toString() + attrConsResult + separator);
   }

   private String isOperandOfTypeString(final Expression exp)
   {
      if (exp instanceof AttributeValueExpression)
      {
         AttributeValueExpression attrValueExp = (AttributeValueExpression) exp;
         return attrValueExp.getAttribute().getEType().getName();
      }

      return "notString";
   }

   private String buildAssignmentsAndConstraints(final String op1, final String op2, final String comp, final String type)
   {

      if (type.equals("EString"))
      {
         if (comp.equals("=="))
         {
            ST equalConstraint = stg.getInstanceOf("check_constraints_eq");
            equalConstraint.add("op1", op1);
            equalConstraint.add("op2", op2);
            equalConstraint.add("not", "!");
            return equalConstraint.render();
         }

         if (comp.equals("!="))
         {
            ST equalConstraint = stg.getInstanceOf("check_constraints_eq");
            equalConstraint.add("op1", op1);
            equalConstraint.add("op2", op2);
            equalConstraint.add("not", "");
            return equalConstraint.render();
         }
      }
      ST assignmentsAndConstraints = stg.getInstanceOf("check_constraints");
      assignmentsAndConstraints.add("op1", op1);
      assignmentsAndConstraints.add("op2", op2);
      assignmentsAndConstraints.add("comp", comp);

      return assignmentsAndConstraints.render();
   }

   private Map<Variable, String> handleVariableValueExtraction(final OperationalCSP ocsp)
   {
      Map<Variable, String> varToLabel = new HashMap<>();

      int j = 0;
      int k = 0;
      for (Variable v : ocsp.getVariables())
      {
         if (v instanceof AttributeVariable)
         {
            AttributeVariable aV = (AttributeVariable) v;
            String label = "var_" + aV.getObjectVariable() + "_" + aV.getAttribute();

            ST bV = stg.getInstanceOf("BoundVariable");
            bV.add("var_name", label);
            bV.add("name", aV.getObjectVariable());
            bV.add("value", buildMethodCall(aV.getAttribute(), aV.getType(), aV.getObjectVariable()));
            bV.add("type", aV.getType());
            code.append(bV.render() + separator);

            varToLabel.put(v, label);
         } else if (v instanceof Literal)
         {
            String label = "var_literal" + j;

            ST bV = stg.getInstanceOf("BoundVariable");
            bV.add("var_name", label);
            bV.add("name", "literal");
            bV.add("value", v.getValue());
            bV.add("type", v.getType());
            code.append(bV.render() + separator);
            j++;

            varToLabel.put(v, label);
         } else if (v instanceof LocalVariable)
         {
            String label = "var_local" + k;

            ST fV = stg.getInstanceOf("UnboundVariable");
            fV.add("var_name", label);
            fV.add("name", "local");
            fV.add("type", v.getType());
            code.append(fV.render() + separator);
            k++;

            varToLabel.put(v, label);
         }

      }

      return varToLabel;
   }

   private void handleCSPSolving()
   {
      Collection<Variable> allFreeVars = ocsp.getVariables().stream().filter(v -> !v.isBound()).collect(Collectors.toList());
      Map<Variable, String> varToLabel = handleVariableValueExtraction(ocsp);
      csp_solver = new ArrayList<AttributConstraintContainer>();
      int i = 0;
      for (TGGConstraint c : ocsp.getConstraints())
      {
         AttributConstraintContainer container = new AttributConstraintContainer(rule.getName());
         container.setInstanceName(c.getName() + i);

         if (c.getName().equals("eq"))
         {
            container.setEq(true);
         } else
         {
            container.setConstraint(c.getName());
         }

         Collection<String> solveVars = c.getVariables().stream().map(v -> varToLabel.get(v)).collect(Collectors.toList());
         container.setSolveVars(solveVars);

         Collection<Variable> freeVars = allFreeVars.stream().filter(v -> c.getVariables().contains(v) && v instanceof AttributeVariable)
               .collect(Collectors.toList());
         container.setDestinationVars(freeVars);

         csp_solver.add(container);
         i++;
      }

      createAttributeConstraints();
      createSolveConstraints();
      createCheckCSP();
   }

   private boolean isOutputDomainTarget()
   {
      return name.endsWith("FWD");
   }

   private void createAttributeConstraints()
   {
      for (AttributConstraintContainer con : csp_solver)
      {
         ST template;
         if (con.isEq())
         {
            template = stg.getInstanceOf("Eq");
         } else
         {
            template = stg.getInstanceOf("Constraint");
            template.add("constraint", con.getConstraint());
         }

         template.add("instanceName", con.getInstanceName());
         code.append(template.render());

         ST templateAdd = stg.getInstanceOf("AddConstraints");
         templateAdd.add("constraints", con.getInstanceName());
         code.append(templateAdd.render() + separator);
      }
   }

   private void createSolveConstraints()
   {
      for (AttributConstraintContainer con : csp_solver)
      {
         ST template = stg.getInstanceOf("Solve");
         template.add("constraint", con.getInstanceName());
         template.add("variables", con.getSolveVars());
         template.add("ruleName", con.getRuleName());
         code.append(template.render() + separator);
      }
   }

   private void createCheckCSP()
   {
      ArrayList<Variable> destination = new ArrayList<Variable>();
      for (AttributConstraintContainer con : csp_solver)
         destination.addAll(con.getDestinationVars());

      ST template = stg.getInstanceOf("check_csp");
      template.add("target", destination);
      template.add("constraints", csp_solver);
      code.append(template.render() + separator + "return ruleResult;");
   }

   private static List<String> CORE_ECORE_CLASS_NAMES = Arrays
         .asList(EAnnotation.class, EAttribute.class, EClass.class, EClassifier.class, EDataType.class, EEnum.class, EEnumLiteral.class, EGenericType.class,
               EModelElement.class, ENamedElement.class, EObject.class, EOperation.class, EPackage.class, EParameter.class, EReference.class,
               EStructuralFeature.class, ETypedElement.class, ETypeParameter.class).stream().map(clazz -> clazz.getSimpleName()).collect(Collectors.toList());
}
