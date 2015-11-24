/**
 */
package org.moflon.sdm.constraints.operationspecification.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.moflon.sdm.constraints.operationspecification.OperationSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationImpl#getSpecification <em>Specification</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationImpl#getAdornmentString <em>Adornment String</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationImpl#isAlwaysSuccessful <em>Always Successful</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperationSpecificationImpl extends EObjectImpl implements OperationSpecification {
	/**
	 * The default value of the '{@link #getSpecification() <em>Specification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecification()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFICATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecification() <em>Specification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecification()
	 * @generated
	 * @ordered
	 */
	protected String specification = SPECIFICATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getAdornmentString() <em>Adornment String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdornmentString()
	 * @generated
	 * @ordered
	 */
	protected static final String ADORNMENT_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAdornmentString() <em>Adornment String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdornmentString()
	 * @generated
	 * @ordered
	 */
	protected String adornmentString = ADORNMENT_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #isAlwaysSuccessful() <em>Always Successful</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlwaysSuccessful()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALWAYS_SUCCESSFUL_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isAlwaysSuccessful() <em>Always Successful</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlwaysSuccessful()
	 * @generated
	 * @ordered
	 */
	protected boolean alwaysSuccessful = ALWAYS_SUCCESSFUL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationspecificationPackage.Literals.OPERATION_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSpecification() {
		return specification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecification(String newSpecification) {
		String oldSpecification = specification;
		specification = newSpecification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.OPERATION_SPECIFICATION__SPECIFICATION, oldSpecification,
					specification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAdornmentString() {
		return adornmentString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdornmentString(String newAdornmentString) {
		String oldAdornmentString = adornmentString;
		adornmentString = newAdornmentString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.OPERATION_SPECIFICATION__ADORNMENT_STRING, oldAdornmentString,
					adornmentString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAlwaysSuccessful() {
		return alwaysSuccessful;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlwaysSuccessful(boolean newAlwaysSuccessful) {
		boolean oldAlwaysSuccessful = alwaysSuccessful;
		alwaysSuccessful = newAlwaysSuccessful;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.OPERATION_SPECIFICATION__ALWAYS_SUCCESSFUL, oldAlwaysSuccessful,
					alwaysSuccessful));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION__SPECIFICATION:
			return getSpecification();
		case OperationspecificationPackage.OPERATION_SPECIFICATION__ADORNMENT_STRING:
			return getAdornmentString();
		case OperationspecificationPackage.OPERATION_SPECIFICATION__ALWAYS_SUCCESSFUL:
			return isAlwaysSuccessful();
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
		case OperationspecificationPackage.OPERATION_SPECIFICATION__SPECIFICATION:
			setSpecification((String) newValue);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION__ADORNMENT_STRING:
			setAdornmentString((String) newValue);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION__ALWAYS_SUCCESSFUL:
			setAlwaysSuccessful((Boolean) newValue);
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
		case OperationspecificationPackage.OPERATION_SPECIFICATION__SPECIFICATION:
			setSpecification(SPECIFICATION_EDEFAULT);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION__ADORNMENT_STRING:
			setAdornmentString(ADORNMENT_STRING_EDEFAULT);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION__ALWAYS_SUCCESSFUL:
			setAlwaysSuccessful(ALWAYS_SUCCESSFUL_EDEFAULT);
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
		case OperationspecificationPackage.OPERATION_SPECIFICATION__SPECIFICATION:
			return SPECIFICATION_EDEFAULT == null ? specification != null
					: !SPECIFICATION_EDEFAULT.equals(specification);
		case OperationspecificationPackage.OPERATION_SPECIFICATION__ADORNMENT_STRING:
			return ADORNMENT_STRING_EDEFAULT == null ? adornmentString != null
					: !ADORNMENT_STRING_EDEFAULT.equals(adornmentString);
		case OperationspecificationPackage.OPERATION_SPECIFICATION__ALWAYS_SUCCESSFUL:
			return alwaysSuccessful != ALWAYS_SUCCESSFUL_EDEFAULT;
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
		result.append(" (specification: ");
		result.append(specification);
		result.append(", adornmentString: ");
		result.append(adornmentString);
		result.append(", alwaysSuccessful: ");
		result.append(alwaysSuccessful);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //OperationSpecificationImpl
