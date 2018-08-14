package org.moflon.compiler.sdm.democles.attributes;

import org.gervarro.democles.codegen.Chain;
import org.gervarro.democles.codegen.CodeGeneratorProvider;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.TemplateController;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;

/**
 * This class provides the operation-to-template mapping for operations that belong to {@link ConstraintSpecification}s
 *
 * @author Frederik Deckwerth - Initial implementation
 * @author Roland Kluge - Docu
 */
public class AttributeConstraintsTemplateProvider implements CodeGeneratorProvider<Chain<TemplateController>> {

	@Override
	public Chain<TemplateController> getTemplateController(final GeneratorOperation operation,
			final Chain<TemplateController> tail) {

		if (operation.getType() instanceof ConstraintSpecification) {
			final ConstraintSpecification cType = (ConstraintSpecification) operation.getType();

			final String fullyQualifiedTemplateName = createTemplateName(operation, cType);
			return new Chain<>(new TemplateController(fullyQualifiedTemplateName, operation), tail);
		}
		throw new RuntimeException("Invalid operation type: " + operation.getType());
	}

	private String createTemplateName(final GeneratorOperation operation, final ConstraintSpecification cType) {
		final String prefix = cType.getAttributeConstraintLibrary().getPrefix();
		final String operationIdentifier = cType.getOperationSpecificationGroup().getOperationIdentifier();
		final String adornmentString = operation.getPrecondition().toString();

		final String fullyQualifiedTemplateName = "/" + prefix + "/" + operationIdentifier + "/" + operationIdentifier
				+ adornmentString;
		return fullyQualifiedTemplateName;
	}

	/**
	 * This class is only responsible for {@link ConstraintSpecification} operations
	 */
	@Override
	public boolean isResponsibleFor(final GeneratorOperation operation) {
		return operation != null && operation.getType() instanceof ConstraintSpecification;
	}

}
