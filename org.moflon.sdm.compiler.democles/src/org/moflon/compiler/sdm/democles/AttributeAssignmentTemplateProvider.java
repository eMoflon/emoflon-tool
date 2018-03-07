package org.moflon.compiler.sdm.democles;

import java.util.List;

import org.eclipse.emf.ecore.EcorePackage;
import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.CodeGeneratorProvider;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.codegen.TemplateController;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.constraint.CoreConstraintModule;
import org.gervarro.democles.constraint.CoreConstraintType;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.impl.Variable;

public class AttributeAssignmentTemplateProvider implements CodeGeneratorProvider<Chain<TemplateController>> {

	@Override
	public final Chain<TemplateController> getTemplateController(final GeneratorOperation operation,
			final Chain<TemplateController> tail) {
		Adornment adornment = operation.getPrecondition();
		if (adornment.get(0) == Adornment.FREE && adornment.get(1) == Adornment.BOUND) {
			ConstraintType type = (ConstraintType) operation.getType();
			if (type == CoreConstraintModule.EQUAL) {
				if (operation.getPostcondition().get(0) == Adornment.NOT_TYPECHECKED) {
					return new Chain<TemplateController>(
							new TemplateController("/assignment/AssignWithTypeCheck", operation), tail);
				} else if (operation.isAlwaysSuccessful()) {
					if (forceCasting(operation)) {
						return new Chain<TemplateController>(
								new TemplateController("/assignment/AssignWithClassCastException", operation), tail);
					} else {
						return new Chain<TemplateController>(new TemplateController("/assignment/Assign", operation),
								tail);
					}
				} else {
					return new Chain<TemplateController>(
							new TemplateController("/assignment/AssignWithNullCheck", operation), tail);
				}
			}
		}

		throw new RuntimeException("Invalid operation");
	}

	@Override
	public final boolean isResponsibleFor(final GeneratorOperation operation) {
		return operation != null && operation.getType() instanceof CoreConstraintType;
	}

	private final boolean forceCasting(final GeneratorOperation operation) {
		List<GeneratorVariable> parameters = operation.getParameters();
		if (EcorePackage.eINSTANCE.getEDataType()
				.isInstance(AssignmentOperationBuilder.lookupEClassifier(parameters.get(0)))) {
			GeneratorVariable variable = parameters.get(1);
			if (variable.getSpecification() instanceof Variable && EcorePackage.eINSTANCE.getEJavaObject()
					.equals(AssignmentOperationBuilder.lookupEClassifier(variable))) {
				return true;
			}
		}
		return false;
	}
}
