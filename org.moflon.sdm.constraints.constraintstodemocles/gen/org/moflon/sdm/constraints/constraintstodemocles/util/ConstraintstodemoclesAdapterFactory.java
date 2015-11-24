/**
 */
package org.moflon.sdm.constraints.constraintstodemocles.util;

import SDMLanguageToDemocles.BlackAndNacPatternTransformer;
import SDMLanguageToDemocles.GreenPatternTransformer;
import SDMLanguageToDemocles.PatternTransformer;
import SDMLanguageToDemocles.PatternVariableHandler;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.moflon.sdm.constraints.constraintstodemocles.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage
 * @generated
 */
public class ConstraintstodemoclesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ConstraintstodemoclesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintstodemoclesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ConstraintstodemoclesPackage.eINSTANCE;
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
	protected ConstraintstodemoclesSwitch<Adapter> modelSwitch = new ConstraintstodemoclesSwitch<Adapter>() {
		@Override
		public Adapter caseAttributeConstraintBlackAndNacPatternTransformer(
				AttributeConstraintBlackAndNacPatternTransformer object) {
			return createAttributeConstraintBlackAndNacPatternTransformerAdapter();
		}

		@Override
		public Adapter caseAttributeConstraintGreenPatternTransformer(
				AttributeConstraintGreenPatternTransformer object) {
			return createAttributeConstraintGreenPatternTransformerAdapter();
		}

		@Override
		public Adapter caseIdentifyerHelper(IdentifyerHelper object) {
			return createIdentifyerHelperAdapter();
		}

		@Override
		public Adapter casePatternVariableHandler(PatternVariableHandler object) {
			return createPatternVariableHandlerAdapter();
		}

		@Override
		public Adapter casePatternTransformer(PatternTransformer object) {
			return createPatternTransformerAdapter();
		}

		@Override
		public Adapter caseBlackAndNacPatternTransformer(BlackAndNacPatternTransformer object) {
			return createBlackAndNacPatternTransformerAdapter();
		}

		@Override
		public Adapter caseGreenPatternTransformer(GreenPatternTransformer object) {
			return createGreenPatternTransformerAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer <em>Attribute Constraint Black And Nac Pattern Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer
	 * @generated
	 */
	public Adapter createAttributeConstraintBlackAndNacPatternTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer <em>Attribute Constraint Green Pattern Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer
	 * @generated
	 */
	public Adapter createAttributeConstraintGreenPatternTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.moflon.sdm.constraints.constraintstodemocles.IdentifyerHelper <em>Identifyer Helper</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.IdentifyerHelper
	 * @generated
	 */
	public Adapter createIdentifyerHelperAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SDMLanguageToDemocles.PatternVariableHandler <em>Pattern Variable Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SDMLanguageToDemocles.PatternVariableHandler
	 * @generated
	 */
	public Adapter createPatternVariableHandlerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SDMLanguageToDemocles.PatternTransformer <em>Pattern Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SDMLanguageToDemocles.PatternTransformer
	 * @generated
	 */
	public Adapter createPatternTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SDMLanguageToDemocles.BlackAndNacPatternTransformer <em>Black And Nac Pattern Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SDMLanguageToDemocles.BlackAndNacPatternTransformer
	 * @generated
	 */
	public Adapter createBlackAndNacPatternTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SDMLanguageToDemocles.GreenPatternTransformer <em>Green Pattern Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SDMLanguageToDemocles.GreenPatternTransformer
	 * @generated
	 */
	public Adapter createGreenPatternTransformerAdapter() {
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

} //ConstraintstodemoclesAdapterFactory
