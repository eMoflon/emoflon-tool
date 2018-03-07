package org.moflon.compiler.sdm.democles;

import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.CodeGeneratorProvider;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.TemplateController;
import org.gervarro.democles.constraint.PatternInvocationConstraintType;

public class BindingAndBlackTemplateProvider implements CodeGeneratorProvider<Chain<TemplateController>> {

	public final Chain<TemplateController> getTemplateController(GeneratorOperation operation,
			Chain<TemplateController> tail) {
		return new Chain<TemplateController>(new TemplateController("/priority/PatternCall", operation), tail);
	}

	@Override
	public final boolean isResponsibleFor(GeneratorOperation operation) {
		return operation != null && operation.getType() instanceof PatternInvocationConstraintType
				&& ((PatternInvocationConstraintType) operation.getType()).isPositive();
	}
}
