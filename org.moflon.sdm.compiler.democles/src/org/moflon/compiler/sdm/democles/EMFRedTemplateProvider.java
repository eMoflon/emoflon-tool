package org.moflon.compiler.sdm.democles;

import org.eclipse.emf.ecore.EReference;
import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.CodeGeneratorProvider;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.TemplateController;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.constraint.emf.EMFConstraint;
import org.gervarro.democles.constraint.emf.Reference;

public class EMFRedTemplateProvider implements CodeGeneratorProvider<Chain<TemplateController>> {

	public final Chain<TemplateController> getTemplateController(GeneratorOperation operation,
			Chain<TemplateController> tail) {
		Object type = operation.getType();
		Adornment adornment = operation.getPrecondition();
		if (type instanceof Reference) {
			Reference ref = (Reference) type;
			if (adornment.get(0) == Adornment.BOUND && adornment.get(1) == Adornment.BOUND) {
				if (ref.getLinkedElement().isMany()) {
					return new Chain<TemplateController>(
							new TemplateController("/emf-delete/DeleteToManyLink", operation), tail);
				} else if (ref.getLinkedElement().isChangeable()) {
					return new Chain<TemplateController>(
							new TemplateController("/emf-delete/DeleteToOneLink", operation), tail);
				} else if (ref.getLinkedElement().getEOpposite() != null) {
					final EReference opposite = ref.getLinkedElement().getEOpposite();
					if (opposite.isMany()) {
						return new Chain<TemplateController>(
								new TemplateController("/emf-delete/ReverseDeleteToManyEReference", operation), tail);
					} else if (opposite.isChangeable()) {
						return new Chain<TemplateController>(
								new TemplateController("/emf-delete/ReverseDeleteToOneEReference", operation), tail);
					}
				}
			}
		}
		throw new RuntimeException("Invalid operation");
	}

	public final boolean isResponsibleFor(GeneratorOperation operation) {
		return operation.getType() instanceof EMFConstraint<?>;
	}
}
