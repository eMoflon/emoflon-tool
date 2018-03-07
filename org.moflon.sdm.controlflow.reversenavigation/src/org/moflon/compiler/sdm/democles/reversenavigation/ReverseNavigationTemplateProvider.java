package org.moflon.compiler.sdm.democles.reversenavigation;

import org.eclipse.emf.ecore.EReference;
import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.TemplateController;
import org.gervarro.democles.codegen.emf.EMFTemplateProvider;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.constraint.emf.StructuralFeature;

public class ReverseNavigationTemplateProvider extends EMFTemplateProvider {
	@Override
	public Chain<TemplateController> getTemplateController(GeneratorOperation operation,
			Chain<TemplateController> tail) {
		Object type = operation.getType();
		Adornment adornment = operation.getPrecondition();
		if (type instanceof StructuralFeature<?>) {
			StructuralFeature<?> sf = (StructuralFeature<?>) type;
			if (adornment.get(0) == Adornment.FREE && adornment.get(1) == Adornment.BOUND) {
				if (((EReference) sf.getLinkedElement()).getEOpposite() == null) {
					return new Chain<TemplateController>(
							new TemplateController("/reverseNavi/BackwardExtendToMany", operation), tail);
				}
			}
		}

		return super.getTemplateController(operation, tail);
	}
}
