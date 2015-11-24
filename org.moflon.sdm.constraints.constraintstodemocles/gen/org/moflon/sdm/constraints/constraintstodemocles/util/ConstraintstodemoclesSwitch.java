/**
 */
package org.moflon.sdm.constraints.constraintstodemocles.util;

import SDMLanguageToDemocles.BlackAndNacPatternTransformer;
import SDMLanguageToDemocles.GreenPatternTransformer;
import SDMLanguageToDemocles.PatternTransformer;
import SDMLanguageToDemocles.PatternVariableHandler;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.moflon.sdm.constraints.constraintstodemocles.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage
 * @generated
 */
public class ConstraintstodemoclesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ConstraintstodemoclesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintstodemoclesSwitch() {
		if (modelPackage == null) {
			modelPackage = ConstraintstodemoclesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER: {
			AttributeConstraintBlackAndNacPatternTransformer attributeConstraintBlackAndNacPatternTransformer = (AttributeConstraintBlackAndNacPatternTransformer) theEObject;
			T result = caseAttributeConstraintBlackAndNacPatternTransformer(
					attributeConstraintBlackAndNacPatternTransformer);
			if (result == null)
				result = caseBlackAndNacPatternTransformer(attributeConstraintBlackAndNacPatternTransformer);
			if (result == null)
				result = casePatternTransformer(attributeConstraintBlackAndNacPatternTransformer);
			if (result == null)
				result = casePatternVariableHandler(attributeConstraintBlackAndNacPatternTransformer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER: {
			AttributeConstraintGreenPatternTransformer attributeConstraintGreenPatternTransformer = (AttributeConstraintGreenPatternTransformer) theEObject;
			T result = caseAttributeConstraintGreenPatternTransformer(attributeConstraintGreenPatternTransformer);
			if (result == null)
				result = caseGreenPatternTransformer(attributeConstraintGreenPatternTransformer);
			if (result == null)
				result = casePatternTransformer(attributeConstraintGreenPatternTransformer);
			if (result == null)
				result = casePatternVariableHandler(attributeConstraintGreenPatternTransformer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ConstraintstodemoclesPackage.IDENTIFYER_HELPER: {
			IdentifyerHelper identifyerHelper = (IdentifyerHelper) theEObject;
			T result = caseIdentifyerHelper(identifyerHelper);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Constraint Black And Nac Pattern Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Constraint Black And Nac Pattern Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeConstraintBlackAndNacPatternTransformer(
			AttributeConstraintBlackAndNacPatternTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Constraint Green Pattern Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Constraint Green Pattern Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeConstraintGreenPatternTransformer(AttributeConstraintGreenPatternTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifyer Helper</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifyer Helper</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifyerHelper(IdentifyerHelper object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pattern Variable Handler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pattern Variable Handler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatternVariableHandler(PatternVariableHandler object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pattern Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pattern Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatternTransformer(PatternTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Black And Nac Pattern Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Black And Nac Pattern Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlackAndNacPatternTransformer(BlackAndNacPatternTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Green Pattern Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Green Pattern Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGreenPatternTransformer(GreenPatternTransformer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ConstraintstodemoclesSwitch
