/**
 */
package org.moflon.sdm.constraints.operationspecification.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.gervarro.democles.specification.ConstraintType;

import org.moflon.sdm.constraints.operationspecification.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage
 * @generated
 */
public class OperationspecificationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OperationspecificationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationspecificationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = OperationspecificationPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationspecificationSwitch<Adapter> modelSwitch = new OperationspecificationSwitch<Adapter>() {
		@Override
		public Adapter caseOperationSpecification(OperationSpecification object) {
			return createOperationSpecificationAdapter();
		}

		@Override
		public Adapter caseConstraintType(ConstraintType object) {
			return createConstraintTypeAdapter();
		}

		@Override
		public Adapter caseAttributeConstraintLibrary(AttributeConstraintLibrary object) {
			return createAttributeConstraintLibraryAdapter();
		}

		@Override
		public Adapter caseOperationSpecificationGroup(OperationSpecificationGroup object) {
			return createOperationSpecificationGroupAdapter();
		}

		@Override
		public Adapter caseParamIdentifier(ParamIdentifier object) {
			return createParamIdentifierAdapter();
		}

		@Override
		public Adapter caseConstraintSpecification(ConstraintSpecification object) {
			return createConstraintSpecificationAdapter();
		}

		@Override
		public Adapter caseParameterType(ParameterType object) {
			return createParameterTypeAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification <em>Operation Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecification
	 * @generated
	 */
	public Adapter createOperationSpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gervarro.democles.specification.ConstraintType <em>Constraint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gervarro.democles.specification.ConstraintType
	 * @generated
	 */
	public Adapter createConstraintTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary <em>Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary
	 * @generated
	 */
	public Adapter createAttributeConstraintLibraryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup <em>Operation Specification Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup
	 * @generated
	 */
	public Adapter createOperationSpecificationGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier <em>Param Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.operationspecification.ParamIdentifier
	 * @generated
	 */
	public Adapter createParamIdentifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification <em>Constraint Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.operationspecification.ConstraintSpecification
	 * @generated
	 */
	public Adapter createConstraintSpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.operationspecification.ParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.operationspecification.ParameterType
	 * @generated
	 */
	public Adapter createParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //OperationspecificationAdapterFactory
