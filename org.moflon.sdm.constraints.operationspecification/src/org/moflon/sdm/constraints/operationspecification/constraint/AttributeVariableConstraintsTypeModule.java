package org.moflon.sdm.constraints.operationspecification.constraint;

import java.util.List;

import org.gervarro.democles.common.TypeModule;
import org.gervarro.democles.specification.ConstraintType;
import org.moflon.sdm.constraints.democles.AttributeVariableConstraint;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;

public class AttributeVariableConstraintsTypeModule implements TypeModule {

	public static AttributeVariableConstraintsTypeModule INSTANCE;

	private final List<AttributeConstraintLibrary> libraries;

	public AttributeVariableConstraintsTypeModule(final List<AttributeConstraintLibrary> libraries) {
		this.libraries = libraries;
		INSTANCE = this;
	}

	@Override
	public final String getName() {
		return "AttributeValueConstraintsTypeModule";
	}

	public final ConstraintType getConstraintType(final AttributeVariableConstraint constraint) {
		//
		ConstraintSpecification cSpecification = null;
		for (AttributeConstraintLibrary attributeConstraintLibrary : libraries) {
			cSpecification = attributeConstraintLibrary.lookupConstraintType(constraint);
			if (cSpecification != null) {
				break;
			}
		}
		return cSpecification;
	}

}
