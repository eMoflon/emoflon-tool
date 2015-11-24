/**
 */
package org.moflon.sdm.constraints.scopevalidation;

import ControlFlow.Action;
import ControlFlow.CFVariable;

import SDMLanguage.patterns.ObjectVariable;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Constraint Green Pattern Invocation Builder</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.moflon.sdm.constraints.scopevalidation.ScopevalidationPackage#getAttributeConstraintGreenPatternInvocationBuilder()
 * @model
 * @generated
 */
public interface AttributeConstraintGreenPatternInvocationBuilder
		extends AttributeConstraintBlackPatternInvocationBuilder {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void validateVariable(Action action, CFVariable variable, ObjectVariable objectVariable);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // AttributeConstraintGreenPatternInvocationBuilder
