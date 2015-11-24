/**
 */
package org.moflon.sdm.constraints.operationspecification.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;
import org.moflon.sdm.constraints.operationspecification.ParamIdentifier;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Param Identifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.ParamIdentifierImpl#getOperationSpecificationGroup <em>Operation Specification Group</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.ParamIdentifierImpl#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParamIdentifierImpl extends EObjectImpl implements ParamIdentifier {
	/**
	 * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final String IDENTIFIER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected String identifier = IDENTIFIER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParamIdentifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationspecificationPackage.Literals.PARAM_IDENTIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationSpecificationGroup getOperationSpecificationGroup() {
		if (eContainerFeatureID() != OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP)
			return null;
		return (OperationSpecificationGroup) eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOperationSpecificationGroup(
			OperationSpecificationGroup newOperationSpecificationGroup, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newOperationSpecificationGroup,
				OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperationSpecificationGroup(OperationSpecificationGroup newOperationSpecificationGroup) {
		if (newOperationSpecificationGroup != eInternalContainer()
				|| (eContainerFeatureID() != OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP
						&& newOperationSpecificationGroup != null)) {
			if (EcoreUtil.isAncestor(this, newOperationSpecificationGroup))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOperationSpecificationGroup != null)
				msgs = ((InternalEObject) newOperationSpecificationGroup).eInverseAdd(this,
						OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS,
						OperationSpecificationGroup.class, msgs);
			msgs = basicSetOperationSpecificationGroup(newOperationSpecificationGroup, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP,
					newOperationSpecificationGroup, newOperationSpecificationGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdentifier(String newIdentifier) {
		String oldIdentifier = identifier;
		identifier = newIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.PARAM_IDENTIFIER__IDENTIFIER, oldIdentifier, identifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetOperationSpecificationGroup((OperationSpecificationGroup) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP:
			return basicSetOperationSpecificationGroup(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP:
			return eInternalContainer().eInverseRemove(this,
					OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS,
					OperationSpecificationGroup.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP:
			return getOperationSpecificationGroup();
		case OperationspecificationPackage.PARAM_IDENTIFIER__IDENTIFIER:
			return getIdentifier();
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
		case OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP:
			setOperationSpecificationGroup((OperationSpecificationGroup) newValue);
			return;
		case OperationspecificationPackage.PARAM_IDENTIFIER__IDENTIFIER:
			setIdentifier((String) newValue);
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
		case OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP:
			setOperationSpecificationGroup((OperationSpecificationGroup) null);
			return;
		case OperationspecificationPackage.PARAM_IDENTIFIER__IDENTIFIER:
			setIdentifier(IDENTIFIER_EDEFAULT);
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
		case OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP:
			return getOperationSpecificationGroup() != null;
		case OperationspecificationPackage.PARAM_IDENTIFIER__IDENTIFIER:
			return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
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
		result.append(" (identifier: ");
		result.append(identifier);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //ParamIdentifierImpl
