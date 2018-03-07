package org.moflon.compiler.sdm.democles;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.TemplateController;
import org.gervarro.democles.codegen.emf.EMFTemplateProvider;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.constraint.emf.EMFVariable;
import org.gervarro.democles.constraint.emf.StructuralFeature;

public class EMFGreenTemplateProvider extends EMFTemplateProvider {

	public final Chain<TemplateController> getTemplateController(GeneratorOperation operation,
			Chain<TemplateController> tail) {
		Object type = operation.getType();
		Adornment adornment = operation.getPrecondition();
		if (type instanceof StructuralFeature<?>) {
			StructuralFeature<?> sf = (StructuralFeature<?>) type;
			if (adornment.get(0) == Adornment.BOUND && adornment.get(1) == Adornment.BOUND) {
				if (sf.getLinkedElement().isMany()) {
					return new Chain<TemplateController>(
							new TemplateController("/emf-create/CreateToManyLink", operation), tail);
				} else if (sf.getLinkedElement().isChangeable()) {
					return new Chain<TemplateController>(
							new TemplateController("/emf-create/CreateToOneLink", operation), tail);
				} else if (EcorePackage.eINSTANCE.getEReference().isInstance(sf.getLinkedElement())
						&& ((EReference) sf.getLinkedElement()).getEOpposite() != null) {
					final EReference opposite = ((EReference) sf.getLinkedElement()).getEOpposite();
					if (opposite.isMany()) {
						return new Chain<TemplateController>(
								new TemplateController("/emf-create/ReverseCreateToManyEReference", operation), tail);
					} else if (opposite.isChangeable()) {
						return new Chain<TemplateController>(
								new TemplateController("/emf-create/ReverseCreateToOneEReference", operation), tail);
					}
				}
			}
		} else if (type instanceof EMFVariable) {
			if (adornment.get(0) == Adornment.FREE) {
				return new Chain<TemplateController>(new TemplateController("/emf-create/CreateObject", operation),
						tail);
			}
		}
		return super.getTemplateController(operation, tail);
	}
}
