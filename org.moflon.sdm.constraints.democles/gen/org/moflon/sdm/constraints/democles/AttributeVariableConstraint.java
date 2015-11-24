/**
 */
package org.moflon.sdm.constraints.democles;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.gervarro.democles.specification.emf.Constraint;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Variable Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.democles.AttributeVariableConstraint#getPredicateSymbol <em>Predicate Symbol</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#getAttributeVariableConstraint()
 * @model kind="class"
 * @generated
 */
public class AttributeVariableConstraint extends Constraint implements EObject {
	/**
	 * The default value of the '{@link #getPredicateSymbol() <em>Predicate Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredicateSymbol()
	 * @generated
	 * @ordered
	 */
	protected static final String PREDICATE_SYMBOL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPredicateSymbol() <em>Predicate Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredicateSymbol()
	 * @generated
	 * @ordered
	 */
	protected String predicateSymbol = PREDICATE_SYMBOL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeVariableConstraint() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DemoclesPackage.Literals.ATTRIBUTE_VARIABLE_CONSTRAINT;
	}

	/**
	 * Returns the value of the '<em><b>Predicate Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Predicate Symbol</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Predicate Symbol</em>' attribute.
	 * @see #setPredicateSymbol(String)
	 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#getAttributeVariableConstraint_PredicateSymbol()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	public String getPredicateSymbol() {
		return predicateSymbol;
	}

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.democles.AttributeVariableConstraint#getPredicateSymbol <em>Predicate Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Predicate Symbol</em>' attribute.
	 * @see #getPredicateSymbol()
	 * @generated
	 */
	public void setPredicateSymbol(String newPredicateSymbol) {
		String oldPredicateSymbol = predicateSymbol;
		predicateSymbol = newPredicateSymbol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DemoclesPackage.ATTRIBUTE_VARIABLE_CONSTRAINT__PREDICATE_SYMBOL, oldPredicateSymbol,
					predicateSymbol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DemoclesPackage.ATTRIBUTE_VARIABLE_CONSTRAINT__PREDICATE_SYMBOL:
			return getPredicateSymbol();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DemoclesPackage.ATTRIBUTE_VARIABLE_CONSTRAINT__PREDICATE_SYMBOL:
			setPredicateSymbol((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DemoclesPackage.ATTRIBUTE_VARIABLE_CONSTRAINT__PREDICATE_SYMBOL:
			setPredicateSymbol(PREDICATE_SYMBOL_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DemoclesPackage.ATTRIBUTE_VARIABLE_CONSTRAINT__PREDICATE_SYMBOL:
			return PREDICATE_SYMBOL_EDEFAULT == null ? predicateSymbol != null
					: !PREDICATE_SYMBOL_EDEFAULT.equals(predicateSymbol);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (predicateSymbol: ");
		result.append(predicateSymbol);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // AttributeVariableConstraint
