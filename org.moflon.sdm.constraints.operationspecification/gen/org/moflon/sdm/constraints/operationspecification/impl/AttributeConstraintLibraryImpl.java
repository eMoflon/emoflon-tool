/**
 */
package org.moflon.sdm.constraints.operationspecification.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.moflon.sdm.constraints.democles.AttributeVariableConstraint;

import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;
// <-- [user defined imports]
import org.eclipse.emf.ecore.EClassifier;
import org.gervarro.democles.specification.emf.ConstraintVariable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.moflon.sdm.constraints.democles.TypedConstant;
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Constraint Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.AttributeConstraintLibraryImpl#getOperationSpecifications <em>Operation Specifications</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.AttributeConstraintLibraryImpl#getConstraintSpecifications <em>Constraint Specifications</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.AttributeConstraintLibraryImpl#getPrefix <em>Prefix</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeConstraintLibraryImpl extends EObjectImpl implements AttributeConstraintLibrary {
	/**
	 * The cached value of the '{@link #getOperationSpecifications() <em>Operation Specifications</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationSpecifications()
	 * @generated
	 * @ordered
	 */
	protected EList<OperationSpecificationGroup> operationSpecifications;

	/**
	 * The cached value of the '{@link #getConstraintSpecifications() <em>Constraint Specifications</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintSpecifications()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstraintSpecification> constraintSpecifications;

	/**
	 * The default value of the '{@link #getPrefix() <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrefix() <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrefix()
	 * @generated
	 * @ordered
	 */
	protected String prefix = PREFIX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeConstraintLibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationspecificationPackage.Literals.ATTRIBUTE_CONSTRAINT_LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OperationSpecificationGroup> getOperationSpecifications() {
		if (operationSpecifications == null) {
			operationSpecifications = new EObjectContainmentWithInverseEList<OperationSpecificationGroup>(
					OperationSpecificationGroup.class, this,
					OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS,
					OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY);
		}
		return operationSpecifications;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConstraintSpecification> getConstraintSpecifications() {
		if (constraintSpecifications == null) {
			constraintSpecifications = new EObjectContainmentWithInverseEList<ConstraintSpecification>(
					ConstraintSpecification.class, this,
					OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS,
					OperationspecificationPackage.CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY);
		}
		return constraintSpecifications;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrefix(String newPrefix) {
		String oldPrefix = prefix;
		prefix = newPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__PREFIX, oldPrefix, prefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintSpecification lookupConstraintType(AttributeVariableConstraint constraint) {
		// [user code injected with eMoflon]
		for (ConstraintSpecification constSpec : this.getConstraintSpecifications()) {
			if (constSpec.getSymbol().equals(constraint.getPredicateSymbol())) {
				if (constraint.getParameters().size() == constSpec.getParameterTypes().size()) {
					boolean successful = true;
					for (int i = 0; i < constraint.getParameters().size(); i++) {
						ConstraintVariable cVariable = constraint.getParameters().get(i).getReference();
						EClassifier typeA = null;
						if (cVariable instanceof EMFVariable) {
							typeA = ((EMFVariable) cVariable).getEClassifier();
						}
						if (cVariable instanceof TypedConstant) {
							typeA = ((TypedConstant) cVariable).getEClassifier();
						}

						if (typeA != null) {

							EClassifier typeB = constSpec.getParameterTypes().get(i).getType();
							if (typeA != typeB) {
								successful = false;
								break;
							}
						} else {
							throw new RuntimeException("AttributeConstraintLibrary: unknown variable type: "
									+ cVariable.getClass().getName());
						}
					}
					if (successful) {
						return constSpec;
					}
				}

			}

		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOperationSpecifications()).basicAdd(otherEnd,
					msgs);
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getConstraintSpecifications())
					.basicAdd(otherEnd, msgs);
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
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS:
			return ((InternalEList<?>) getOperationSpecifications()).basicRemove(otherEnd, msgs);
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS:
			return ((InternalEList<?>) getConstraintSpecifications()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS:
			return getOperationSpecifications();
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS:
			return getConstraintSpecifications();
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__PREFIX:
			return getPrefix();
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
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS:
			getOperationSpecifications().clear();
			getOperationSpecifications().addAll((Collection<? extends OperationSpecificationGroup>) newValue);
			return;
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS:
			getConstraintSpecifications().clear();
			getConstraintSpecifications().addAll((Collection<? extends ConstraintSpecification>) newValue);
			return;
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__PREFIX:
			setPrefix((String) newValue);
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
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS:
			getOperationSpecifications().clear();
			return;
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS:
			getConstraintSpecifications().clear();
			return;
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__PREFIX:
			setPrefix(PREFIX_EDEFAULT);
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
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS:
			return operationSpecifications != null && !operationSpecifications.isEmpty();
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS:
			return constraintSpecifications != null && !constraintSpecifications.isEmpty();
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__PREFIX:
			return PREFIX_EDEFAULT == null ? prefix != null : !PREFIX_EDEFAULT.equals(prefix);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY___LOOKUP_CONSTRAINT_TYPE__ATTRIBUTEVARIABLECONSTRAINT:
			return lookupConstraintType((AttributeVariableConstraint) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (prefix: ");
		result.append(prefix);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //AttributeConstraintLibraryImpl
