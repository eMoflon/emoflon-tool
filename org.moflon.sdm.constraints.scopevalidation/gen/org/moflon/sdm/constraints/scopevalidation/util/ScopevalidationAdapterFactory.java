/**
 */
package org.moflon.sdm.constraints.scopevalidation.util;

import ScopeValidation.ActionBuilder;
import ScopeValidation.BlackPatternBuilder;
import ScopeValidation.CompoundPatternInvocationBuilder;
import ScopeValidation.PatternInvocationBuilder;
import ScopeValidation.RegularPatternInvocationBuilder;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.moflon.sdm.constraints.scopevalidation.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.scopevalidation.ScopevalidationPackage
 * @generated
 */
public class ScopevalidationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ScopevalidationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopevalidationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ScopevalidationPackage.eINSTANCE;
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
	protected ScopevalidationSwitch<Adapter> modelSwitch = new ScopevalidationSwitch<Adapter>() {
		@Override
		public Adapter caseAttributeConstraintBlackPatternInvocationBuilder(
				AttributeConstraintBlackPatternInvocationBuilder object) {
			return createAttributeConstraintBlackPatternInvocationBuilderAdapter();
		}

		@Override
		public Adapter caseAttributeConstraintGreenPatternInvocationBuilder(
				AttributeConstraintGreenPatternInvocationBuilder object) {
			return createAttributeConstraintGreenPatternInvocationBuilderAdapter();
		}

		@Override
		public Adapter caseActionBuilder(ActionBuilder object) {
			return createActionBuilderAdapter();
		}

		@Override
		public Adapter casePatternInvocationBuilder(PatternInvocationBuilder object) {
			return createPatternInvocationBuilderAdapter();
		}

		@Override
		public Adapter caseRegularPatternInvocationBuilder(RegularPatternInvocationBuilder object) {
			return createRegularPatternInvocationBuilderAdapter();
		}

		@Override
		public Adapter caseCompoundPatternInvocationBuilder(CompoundPatternInvocationBuilder object) {
			return createCompoundPatternInvocationBuilderAdapter();
		}

		@Override
		public Adapter caseBlackPatternBuilder(BlackPatternBuilder object) {
			return createBlackPatternBuilderAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder <em>Attribute Constraint Black Pattern Invocation Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder
	 * @generated
	 */
	public Adapter createAttributeConstraintBlackPatternInvocationBuilderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintGreenPatternInvocationBuilder <em>Attribute Constraint Green Pattern Invocation Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintGreenPatternInvocationBuilder
	 * @generated
	 */
	public Adapter createAttributeConstraintGreenPatternInvocationBuilderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link ScopeValidation.ActionBuilder <em>Action Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see ScopeValidation.ActionBuilder
	 * @generated
	 */
	public Adapter createActionBuilderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link ScopeValidation.PatternInvocationBuilder <em>Pattern Invocation Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see ScopeValidation.PatternInvocationBuilder
	 * @generated
	 */
	public Adapter createPatternInvocationBuilderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link ScopeValidation.RegularPatternInvocationBuilder <em>Regular Pattern Invocation Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see ScopeValidation.RegularPatternInvocationBuilder
	 * @generated
	 */
	public Adapter createRegularPatternInvocationBuilderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link ScopeValidation.CompoundPatternInvocationBuilder <em>Compound Pattern Invocation Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see ScopeValidation.CompoundPatternInvocationBuilder
	 * @generated
	 */
	public Adapter createCompoundPatternInvocationBuilderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link ScopeValidation.BlackPatternBuilder <em>Black Pattern Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see ScopeValidation.BlackPatternBuilder
	 * @generated
	 */
	public Adapter createBlackPatternBuilderAdapter() {
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

} //ScopevalidationAdapterFactory
