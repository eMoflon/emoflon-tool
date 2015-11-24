/**
 */
package org.moflon.sdm.constraints.operationspecification.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;
import org.moflon.sdm.constraints.operationspecification.ParameterType;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.ConstraintSpecificationImpl#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.ConstraintSpecificationImpl#getParameterTypes <em>Parameter Types</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.ConstraintSpecificationImpl#getOperationSpecificationGroup <em>Operation Specification Group</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.ConstraintSpecificationImpl#getSymbol <em>Symbol</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConstraintSpecificationImpl extends EObjectImpl implements ConstraintSpecification {
	/**
	 * The cached value of the '{@link #getParameterTypes() <em>Parameter Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterType> parameterTypes;

	/**
	 * The cached value of the '{@link #getOperationSpecificationGroup() <em>Operation Specification Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 * @ordered
	 */
	protected OperationSpecificationGroup operationSpecificationGroup;

	/**
	 * The default value of the '{@link #getSymbol() <em>Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbol()
	 * @generated
	 * @ordered
	 */
	protected static final String SYMBOL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSymbol() <em>Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbol()
	 * @generated
	 * @ordered
	 */
	protected String symbol = SYMBOL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationspecificationPackage.Literals.CONSTRAINT_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintLibrary getAttributeConstraintLibrary() {
		if (eContainerFeatureID() != OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY)
			return null;
		return (AttributeConstraintLibrary) eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConstraintLibrary(
			AttributeConstraintLibrary newAttributeConstraintLibrary, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newAttributeConstraintLibrary,
				OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeConstraintLibrary(AttributeConstraintLibrary newAttributeConstraintLibrary) {
		if (newAttributeConstraintLibrary != eInternalContainer()
				|| (eContainerFeatureID() != OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY
						&& newAttributeConstraintLibrary != null)) {
			if (EcoreUtil.isAncestor(this, newAttributeConstraintLibrary))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAttributeConstraintLibrary != null)
				msgs = ((InternalEObject) newAttributeConstraintLibrary).eInverseAdd(this,
						OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS,
						AttributeConstraintLibrary.class, msgs);
			msgs = basicSetAttributeConstraintLibrary(newAttributeConstraintLibrary, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY,
					newAttributeConstraintLibrary, newAttributeConstraintLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterType> getParameterTypes() {
		if (parameterTypes == null) {
			parameterTypes = new EObjectContainmentEList<ParameterType>(ParameterType.class, this,
					OperationspecificationPackage.CONSTRAINT_SPECIFICATION__PARAMETER_TYPES);
		}
		return parameterTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationSpecificationGroup getOperationSpecificationGroup() {
		if (operationSpecificationGroup != null && operationSpecificationGroup.eIsProxy()) {
			InternalEObject oldOperationSpecificationGroup = (InternalEObject) operationSpecificationGroup;
			operationSpecificationGroup = (OperationSpecificationGroup) eResolveProxy(oldOperationSpecificationGroup);
			if (operationSpecificationGroup != oldOperationSpecificationGroup) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP,
							oldOperationSpecificationGroup, operationSpecificationGroup));
			}
		}
		return operationSpecificationGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationSpecificationGroup basicGetOperationSpecificationGroup() {
		return operationSpecificationGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOperationSpecificationGroup(
			OperationSpecificationGroup newOperationSpecificationGroup, NotificationChain msgs) {
		OperationSpecificationGroup oldOperationSpecificationGroup = operationSpecificationGroup;
		operationSpecificationGroup = newOperationSpecificationGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP,
					oldOperationSpecificationGroup, newOperationSpecificationGroup);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperationSpecificationGroup(OperationSpecificationGroup newOperationSpecificationGroup) {
		if (newOperationSpecificationGroup != operationSpecificationGroup) {
			NotificationChain msgs = null;
			if (operationSpecificationGroup != null)
				msgs = ((InternalEObject) operationSpecificationGroup).eInverseRemove(this,
						OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS,
						OperationSpecificationGroup.class, msgs);
			if (newOperationSpecificationGroup != null)
				msgs = ((InternalEObject) newOperationSpecificationGroup).eInverseAdd(this,
						OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS,
						OperationSpecificationGroup.class, msgs);
			msgs = basicSetOperationSpecificationGroup(newOperationSpecificationGroup, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP,
					newOperationSpecificationGroup, newOperationSpecificationGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbol(String newSymbol) {
		String oldSymbol = symbol;
		symbol = newSymbol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.CONSTRAINT_SPECIFICATION__SYMBOL, oldSymbol, symbol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetAttributeConstraintLibrary((AttributeConstraintLibrary) otherEnd, msgs);
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP:
			if (operationSpecificationGroup != null)
				msgs = ((InternalEObject) operationSpecificationGroup).eInverseRemove(this,
						OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS,
						OperationSpecificationGroup.class, msgs);
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
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY:
			return basicSetAttributeConstraintLibrary(null, msgs);
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__PARAMETER_TYPES:
			return ((InternalEList<?>) getParameterTypes()).basicRemove(otherEnd, msgs);
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP:
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
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY:
			return eInternalContainer().eInverseRemove(this,
					OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS,
					AttributeConstraintLibrary.class, msgs);
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
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY:
			return getAttributeConstraintLibrary();
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__PARAMETER_TYPES:
			return getParameterTypes();
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP:
			if (resolve)
				return getOperationSpecificationGroup();
			return basicGetOperationSpecificationGroup();
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__SYMBOL:
			return getSymbol();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY:
			setAttributeConstraintLibrary((AttributeConstraintLibrary) newValue);
			return;
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__PARAMETER_TYPES:
			getParameterTypes().clear();
			getParameterTypes().addAll((Collection<? extends ParameterType>) newValue);
			return;
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP:
			setOperationSpecificationGroup((OperationSpecificationGroup) newValue);
			return;
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__SYMBOL:
			setSymbol((String) newValue);
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
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY:
			setAttributeConstraintLibrary((AttributeConstraintLibrary) null);
			return;
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__PARAMETER_TYPES:
			getParameterTypes().clear();
			return;
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP:
			setOperationSpecificationGroup((OperationSpecificationGroup) null);
			return;
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__SYMBOL:
			setSymbol(SYMBOL_EDEFAULT);
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
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY:
			return getAttributeConstraintLibrary() != null;
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__PARAMETER_TYPES:
			return parameterTypes != null && !parameterTypes.isEmpty();
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP:
			return operationSpecificationGroup != null;
		case OperationspecificationPackage.CONSTRAINT_SPECIFICATION__SYMBOL:
			return SYMBOL_EDEFAULT == null ? symbol != null : !SYMBOL_EDEFAULT.equals(symbol);
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
		result.append(" (symbol: ");
		result.append(symbol);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //ConstraintSpecificationImpl
