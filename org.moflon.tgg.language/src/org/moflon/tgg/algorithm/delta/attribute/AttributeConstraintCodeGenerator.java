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
import SDMLanguage.patterns.Constraint;
import SDMLanguage.patterns.ObjectVariable;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;
import TGGLanguage.DomainType;
import TGGLanguage.TGGObjectVariable;
import TGGLanguage.TGGRule;
import TGGLanguage.compiler.compilerfacade.impl.CSPSearchPlanAdapterImpl;
import TGGLanguage.csp.AttributeVariable;
import TGGLanguage.csp.CSP;
import TGGLanguage.csp.CspFactory;
import TGGLanguage.csp.Literal;
import TGGLanguage.csp.LocalVariable;
import TGGLanguage.csp.OperationalCSP;
import TGGLanguage.csp.TGGConstraint;
import TGGLanguage.csp.Variable;

public class AttributeConstraintCodeGenerator {

	private TGGRule rule;
	private String name;
	private String separator;
//	private String attributeAssignmentAndConstraints;
	private STGroupFile stg;
	ArrayList<String> source;
	ArrayList<String> target;
//	private StringBuilder csp_result;
	private StringBuilder code;
	private ArrayList<AttributConstraintContainer> csp_solver;

	public AttributeConstraintCodeGenerator() {
		this.separator = "\n\n";
		stg = new STGroupFile(this.getClass().getClassLoader()
				.getResource("/templates/Csp.stg"), "UTF-8", '<', '>');
		stg.registerRenderer(String.class, new MyBasicFormatRenderer());
//		csp_result = new StringBuilder();
		code = new StringBuilder();
	}
	
	public String getInjection() {

		locateObjectVariables();

		createAttributeConstraintRuleResult();

		handleAttributeAssignmentsAndConstraints();

		handleCSPSolving();


		return code.toString();
	}
	
	
	public void setTGGRule(TGGRule rule) {
		this.rule = rule;
	}

	public void setDirection(String name) {
		this.name = name;
	}





	private void createAttributeConstraintRuleResult() {

		ST rR = stg.getInstanceOf("createRuleResult");
		rR.add("ruleName", rule.getName());
		code.append(rR.render() + separator);

	}

	private String buildMethodCall(final String attribute, final String type,
			final String ov) {
		String prefix = ".get" + StringUtils.capitalize(attribute);
		if ("boolean".equals(type) || "Boolean".equals(type)
				|| "EBoolean".equals(type)) {
			prefix = MoflonUtil.handlePrefixForBooleanAttributes("", attribute);
		}
		return ov + prefix + "()";
	}

	private String getOperand(final Expression expression) {
		if (expression instanceof LiteralExpression) {
			return ((LiteralExpression) expression).getValue();
		}

		AttributeValueExpression attrValueExp;
		if (expression instanceof AttributeValueExpression) {
			attrValueExp = (AttributeValueExpression) expression;
			return buildMethodCall(attrValueExp.getAttribute().getName(),
					attrValueExp.getObject().getType().getName(), attrValueExp
							.getObject().getName());

		}
		return "null";
	}

	private Object handleType(final EClassifier type) {
		// Some Java dependent adjustments
		String typeName = type.getName();
		if ("boolean".equals(typeName))
			return "Boolean";

		if (CORE_ECORE_CLASS_NAMES.contains(typeName))
			return "org.eclipse.emf.ecore." + typeName;

		return MoflonUtil.getFQN(type);
	}

	private String getComparisonOp(final ComparingOperator comparingOperator) {
		String result = "*";
		switch (comparingOperator) {
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

	private Collection<Variable> getDestVariables(
			final EList<Variable> variables, final ArrayList<String> relevantOVs) {
		return variables.stream().filter(v -> {
			if (v instanceof AttributeVariable) {
				AttributeVariable av = (AttributeVariable) v;
				if (relevantOVs.contains(av.getObjectVariable()))
					return true;
			}

			return false;
		}).collect(Collectors.toList());
	}

	private void locateObjectVariables() {

		source = new ArrayList<String>();
		target = new ArrayList<String>();

		for (ObjectVariable ov : rule.getObjectVariable()) {
			if (ov instanceof TGGObjectVariable) {
				TGGObjectVariable tggOV = (TGGObjectVariable) ov;
				if (tggOV.getDomain().getType() == DomainType.SOURCE) {
					source.add(ov.getName());
				}
				if (tggOV.getDomain().getType() == DomainType.TARGET) {
					target.add(ov.getName());
				}
			}
		}
	}

	private void handleAttributeAssignmentsAndConstraints() {

		HashMap<String, String> variables = new HashMap<String, String>();

		StringBuilder locateObjects = new StringBuilder();
		StringBuilder attrConsResult = new StringBuilder();

		EList<ObjectVariable> ovs = rule.getObjectVariable();

		
		// create Mapping for AttributeConstraints
		for (ObjectVariable ov : ovs) {
			ST oVT = stg.getInstanceOf("locate_node");
			oVT.add("var_name", ov.getName());
			oVT.add("type", handleType(ov.getType()));
			variables.put(ov.getName(), ov.getType().getName());
			locateObjects.append(oVT.render());
			locateObjects.append(separator);

			// handle AttributeAssignments
			for (AttributeAssignment a : ov.getAttributeAssignment()) {
				String op1 = buildMethodCall(a.getAttribute().getName(), a
						.getAttribute().getEAttributeType().getName(), a
						.getObjectVariable().getName());
				String op2 = getOperand(a.getValueExpression());
				attrConsResult.append(buildAssignmentsAndConstraints(op1, op2, "=="));
				attrConsResult.append(separator);
			}

			// handle Constraints
			for (Constraint c : ov.getConstraint()) {
				Expression exp = c.getConstraintExpression();
				if (exp instanceof ComparisonExpression) {
					String op1 = getOperand(((ComparisonExpression) exp)
							.getLeftExpression());
					String op2 = getOperand(((ComparisonExpression) exp)
							.getRightExpression());
					String comp = getComparisonOp(((ComparisonExpression) exp)
							.getOperator());
					attrConsResult.append(buildAssignmentsAndConstraints(op1, op2, comp));
					attrConsResult.append(separator);
				}
			}
		}

		code.append(locateObjects.toString() + attrConsResult + separator);
	}

	private String buildAssignmentsAndConstraints(String op1, String op2,
			String comp) {

		ST assignmentsAndConstraints = stg.getInstanceOf("check_constraints");
		assignmentsAndConstraints.add("op1", op1);
		assignmentsAndConstraints.add("op2", op2);
		assignmentsAndConstraints.add("comp", comp);

		return assignmentsAndConstraints.render();
	}

	private Map<Variable, String> handleVariableValueExtraction(
			final OperationalCSP ocsp) {
		Map<Variable, String> varToLabel = new HashMap<>();
		// StringBuilder variableValueExtraction = new StringBuilder();
		int j = 0;
		int k = 0;
		for (Variable v : ocsp.getVariables()) {
			if (v instanceof AttributeVariable) {
				AttributeVariable aV = (AttributeVariable) v;
				String label = "var_" + aV.getObjectVariable() + "_"
						+ aV.getAttribute();

				ST bV = stg.getInstanceOf("BoundVariable");
				bV.add("var_name", label);
				bV.add("name", aV.getObjectVariable());
				bV.add("value",
						buildMethodCall(aV.getAttribute(), aV.getType(),
								aV.getObjectVariable()));
				bV.add("type", aV.getType());
				code.append(bV.render() + separator);

				varToLabel.put(v, label);
			} else if (v instanceof Literal) {
				String label = "var_literal" + j;

				ST bV = stg.getInstanceOf("BoundVariable");
				bV.add("var_name", label);
				bV.add("name", "literal");
				bV.add("value", v.getValue());
				bV.add("type", v.getType());
				code.append(bV.render() + separator);
				j++;

				varToLabel.put(v, label);
			} else if (v instanceof LocalVariable) {
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

	private void handleCSPSolving() {



		int i = 0;

		CSP csp = EcoreUtil.copy(rule.getCsp());
		OperationalCSP ocsp = CspFactory.eINSTANCE.createOperationalCSP();
		ocsp.getConstraints().addAll(csp.getConstraints());
		ocsp.getVariables().addAll(csp.getVariables());
		ocsp.getVariables().forEach(v -> {
			// Local variables are never bound
				if (!(v instanceof LocalVariable))
					v.setBound(true);
			});

		CSPSearchPlanAdapterImpl plan = new CSPSearchPlanAdapterImpl();
		plan.computeConstraintOrder(ocsp);

		Map<Variable, String> varToLabel = handleVariableValueExtraction(ocsp);

		csp_solver = new ArrayList<AttributConstraintContainer>();
		for (TGGConstraint c : ocsp.getConstraints()) {
//			EList<Variable> vars = c.getVariables();

			AttributConstraintContainer container = new AttributConstraintContainer(
					rule.getName());
			container.setInstanceName(c.getName() + i);

			if (c.getName().equals("eq")) {
				container.setEq(true);
			} else {
				container.setConstraint(c.getName());
			}

			Collection<String> solveVars = c.getVariables().stream()
					.map(v -> varToLabel.get(v)).collect(Collectors.toList());

			container.setSolveVars(solveVars);

			
			if (this.name.endsWith("FWD")) {
				container.setDestinationVars(getDestVariables(c.getVariables(),
						this.target));
			} else {
				container.setDestinationVars(getDestVariables(c.getVariables(),
						this.source));
			}

			csp_solver.add(container);
			i++;
		}

		 createAttributeConstraints();
		 createSolveConstraints();
		 createCheckCSP();
	}

	private void createAttributeConstraints() {

		for (AttributConstraintContainer con : csp_solver) {
			ST template;
			if (con.isEq()) {
				template = stg.getInstanceOf("Eq");
			} else {
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

	private void createSolveConstraints() {

		for (AttributConstraintContainer con : csp_solver) {
			ST template = stg.getInstanceOf("Solve");
			template.add("constraint", con.getInstanceName());
			template.add("variables", con.getSolveVars());
			template.add("ruleName", con.getRuleName());
			code.append(template.render() + separator);
		}

	}

	private void createCheckCSP(){

	//	ArrayList<String> constraints = new ArrayList<String>();
		ArrayList<Variable> destination = new ArrayList<Variable>();
		for (AttributConstraintContainer con : csp_solver) {
			destination.addAll(con.getDestinationVars());
		}
		ST template = stg.getInstanceOf("check_csp");
		template.add("target", destination);
		template.add("constraints", csp_solver);
		code.append(template.render() + separator + "return ruleResult;");
	}

	private static List<String> CORE_ECORE_CLASS_NAMES = Arrays
			.asList(EAnnotation.class, EAttribute.class, EClass.class,
					EClassifier.class, EDataType.class, EEnum.class,
					EEnumLiteral.class, EGenericType.class,
					EModelElement.class, ENamedElement.class, EObject.class,
					EOperation.class, EPackage.class, EParameter.class,
					EReference.class, EStructuralFeature.class,
					ETypedElement.class, ETypeParameter.class).stream()
			.map(clazz -> clazz.getSimpleName()).collect(Collectors.toList());

}
