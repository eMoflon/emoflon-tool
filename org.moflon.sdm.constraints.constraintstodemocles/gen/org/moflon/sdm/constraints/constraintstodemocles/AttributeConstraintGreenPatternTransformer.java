/**
 */
package org.moflon.sdm.constraints.constraintstodemocles;

import SDMLanguage.patterns.AttributeConstraints.AssignmentConstraint;

import SDMLanguage.patterns.ObjectVariable;
import SDMLanguage.patterns.StoryPattern;

import SDMLanguageToDemocles.GreenPatternTransformer;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Constraint Green Pattern Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage#getAttributeConstraintGreenPatternTransformer()
 * @model
 * @generated
 */
public interface AttributeConstraintGreenPatternTransformer extends GreenPatternTransformer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void postprocess(StoryPattern storyPattern, Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void callSuperPostprocess(StoryPattern storyPattern, Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void processAssignmentConstraint(AssignmentConstraint assignmentConstraint, Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Variable lookupVariableByName(String variableName, Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean isVariableToAdd(ObjectVariable objectVariable);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean callSuperIsVariableToAdd(ObjectVariable objectVariable);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // AttributeConstraintGreenPatternTransformer
