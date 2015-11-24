/**
 */
package org.moflon.sdm.constraints.scopevalidation;

import ControlFlow.VariableReference;

import SDMLanguage.patterns.StoryPattern;

import ScopeValidation.BlackPatternBuilder;
import ScopeValidation.PatternInvocationBuilder;

import org.eclipse.emf.ecore.EClassifier;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Constraint Black Pattern Invocation Builder</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.moflon.sdm.constraints.scopevalidation.ScopevalidationPackage#getAttributeConstraintBlackPatternInvocationBuilder()
 * @model
 * @generated
 */
public interface AttributeConstraintBlackPatternInvocationBuilder extends BlackPatternBuilder {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void analyzeDependencies(PatternInvocationBuilder patternInvocationBuilder, StoryPattern storyPattern,
			Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void callSuperAnalyzeDependencies(PatternInvocationBuilder patternInvocationBuilder, StoryPattern storyPattern,
			Pattern pattern);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String concat(String param1, int param2);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String calcLocalVariableName(Variable variable);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	VariableReference handleAttrVariableReference(Variable variable, EClassifier variableType);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // AttributeConstraintBlackPatternInvocationBuilder
