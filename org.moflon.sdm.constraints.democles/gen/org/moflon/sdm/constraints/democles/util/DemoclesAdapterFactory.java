/**
 */
package org.moflon.sdm.constraints.democles.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.ETypedElement;

import org.gervarro.democles.specification.emf.Constant;
import org.gervarro.democles.specification.emf.Constraint;
import org.gervarro.democles.specification.emf.ConstraintVariable;

import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFConstraint;

import org.moflon.sdm.constraints.democles.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.democles.DemoclesPackage
 * @generated
 */
public class DemoclesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DemoclesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DemoclesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DemoclesPackage.eINSTANCE;
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
	protected DemoclesSwitch<Adapter> modelSwitch = new DemoclesSwitch<Adapter>() {
		@Override
		public Adapter caseAttributeVariableConstraint(AttributeVariableConstraint object) {
			return createAttributeVariableConstraintAdapter();
		}

		@Override
		public Adapter caseAttributeValueConstraint(AttributeValueConstraint object) {
			return createAttributeValueConstraintAdapter();
		}

		@Override
		public Adapter caseTypedConstant(TypedConstant object) {
			return createTypedConstantAdapter();
		}

		@Override
		public Adapter caseConstraint(Constraint object) {
			return createConstraintAdapter();
		}

		@Override
		public <E extends ETypedElement> Adapter caseEMFConstraint(EMFConstraint<E> object) {
			return createEMFConstraintAdapter();
		}

		@Override
		public Adapter caseConstraintVariable(ConstraintVariable object) {
			return createConstraintVariableAdapter();
		}

		@Override
		public Adapter caseConstant(Constant object) {
			return createConstantAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.democles.AttributeVariableConstraint <em>Attribute Variable Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.democles.AttributeVariableConstraint
	 * @generated
	 */
	public Adapter createAttributeVariableConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.democles.AttributeValueConstraint <em>Attribute Value Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.democles.AttributeValueConstraint
	 * @generated
	 */
	public Adapter createAttributeValueConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.democles.TypedConstant <em>Typed Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.democles.TypedConstant
	 * @generated
	 */
	public Adapter createTypedConstantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gervarro.democles.specification.emf.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gervarro.democles.specification.emf.Constraint
	 * @generated
	 */
	public Adapter createConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gervarro.democles.specification.emf.constraint.emf.emf.EMFConstraint <em>EMF Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gervarro.democles.specification.emf.constraint.emf.emf.EMFConstraint
	 * @generated
	 */
	public Adapter createEMFConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gervarro.democles.specification.emf.ConstraintVariable <em>Constraint Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gervarro.democles.specification.emf.ConstraintVariable
	 * @generated
	 */
	public Adapter createConstraintVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.gervarro.democles.specification.emf.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.gervarro.democles.specification.emf.Constant
	 * @generated
	 */
	public Adapter createConstantAdapter() {
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

} //DemoclesAdapterFactory
