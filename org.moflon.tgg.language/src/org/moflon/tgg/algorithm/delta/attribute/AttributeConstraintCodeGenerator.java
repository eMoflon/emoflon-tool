package org.moflon.tgg.algorithm.delta.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.csp.codegenerator.MyBasicFormatRenderer;
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

   public AttributeConstraintCodeGenerator(final TGGRule rule, final String name, final TGGCompiler compiler)
   {
      this.separator = "\n\n";
      this.stg = new STGroupFile(this.getClass().getClassLoader().getResource("/templates/Csp.stg"), "UTF-8", '<', '>');
      this.stg.registerRenderer(String.class, new MyBasicFormatRenderer());
      this.code = new StringBuilder();

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

   private String getOperand(final Expression expression)
   {
      if (expression instanceof LiteralExpression)
         return ((LiteralExpression) expression).getValue();

      AttributeValueExpression attrValueExp;
      if (expression instanceof AttributeValueExpression)
      {
         attrValueExp = (AttributeValueExpression) expression;
         return attrValueExp.getAttribute().getName();
      }

      return "null";
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
      StringBuilder locateObjects = new StringBuilder();
      StringBuilder attrConsResult = new StringBuilder();

      EList<ObjectVariable> ovs = rule.getObjectVariable();

      // create Mapping for AttributeConstraints
      for (ObjectVariable ov : ovs)
      {
         if (ov.getBindingSemantics().equals(BindingSemantics.NEGATIVE))
            continue;

         // handle AttributeAssignments
         for (AttributeAssignment a : ov.getAttributeAssignment())
         {
        	String ovName = a.getObjectVariable().getName();
        	String attrName = a.getAttribute().getName();
            String expectedValue = getOperand(a.getValueExpression());
            attrConsResult.append(buildAssignmentsAndConstraints(ovName, attrName, expectedValue, ComparingOperator.EQUAL));
            attrConsResult.append(separator);
         }

         // handle Constraints
         for (Constraint c : ov.getConstraint())
         {
            Expression exp = c.getConstraintExpression();
            if (exp instanceof ComparisonExpression)
            {
               String attrName = getOperand(((ComparisonExpression) exp).getLeftExpression());
               String expectedValue = getOperand(((ComparisonExpression) exp).getRightExpression());
               ComparingOperator comp = ((ComparisonExpression) exp).getOperator();
               attrConsResult.append(buildAssignmentsAndConstraints(ov.getName(), attrName, expectedValue, comp));
               attrConsResult.append(separator);
            }
         }
      }

      code.append(locateObjects.toString() + attrConsResult + separator);
   }

   private String buildAssignmentsAndConstraints(final String ovName, final String attrName, final String expectedValue, final ComparingOperator comp)
   {
	  ST assignmentsAndConstraints = stg.getInstanceOf("check_constraints");
      assignmentsAndConstraints.add("ovName", ovName);
      assignmentsAndConstraints.add("attrName", attrName); 
      assignmentsAndConstraints.add("expectedValue", expectedValue);
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

            ST bV = stg.getInstanceOf("BoundAttributeVariableGeneric");
            bV.add("var_name", label);
            bV.add("name", aV.getObjectVariable());
            bV.add("value", aV.getAttribute());
            bV.add("type", aV.getType());
            code.append(bV.render() + separator);

            varToLabel.put(v, label);
         } else if (v instanceof Literal)
         {
            String label = "var_literal" + j;

            ST bV = stg.getInstanceOf("BoundLiteralGeneric");
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
}
