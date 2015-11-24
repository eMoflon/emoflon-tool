/**
 */
package org.moflon.sdm.constraints.constraintstodemocles;

import SDMLanguage.patterns.AttributeConstraints.AttributeLookupConstraint;
import SDMLanguage.patterns.AttributeConstraints.CspConstraint;
import SDMLanguage.patterns.AttributeConstraints.PrimitiveVariable;

import SDMLanguage.patterns.StoryPattern;

import SDMLanguageToDemocles.BlackAndNacPatternTransformer;

import org.eclipse.emf.ecore.EObject;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;

import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;

import org.moflon.sdm.constraints.democles.AttributeVariableConstraint;

import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Constraint Black And Nac Pattern Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage#getAttributeConstraintBlackAndNacPatternTransformer()
 * @model
 * @generated
 */
public interface AttributeConstraintBlackAndNacPatternTransformer extends BlackAndNacPatternTransformer {
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
	void processPrimitiveVariable(PrimitiveVariable primitiveVariable, Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void processLookupConstraint(AttributeLookupConstraint lookupConstraint, Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void processCspConstraint(CspConstraint cspConstraint, Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Variable lookupVariableByName(String name, Pattern pattern);

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
	void validateCspConstraints(AttributeVariableConstraint attributeConstraint, CspConstraint cspConstraint);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void updateUserDefinedConstraintLib(AttributeVariableConstraint attributeConstraint,
			AttributeConstraintLibrary userDefinedLib);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void validateConstraintLibrary(AttributeConstraintLibrary attributeConstraintLibrary, String projectName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ErrorMessage createErrorMessage(EObject location);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // AttributeConstraintBlackAndNacPatternTransformer
