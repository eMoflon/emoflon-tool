/**
 */
package org.moflon.core.ecore2mocaxmi.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.moflon.core.ecore2mocaxmi.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage
 * @generated
 */
public class Ecore2mocaxmiAdapterFactory extends AdapterFactoryImpl
{
   /**
    * The cached model package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected static Ecore2mocaxmiPackage modelPackage;

   /**
    * Creates an instance of the adapter factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Ecore2mocaxmiAdapterFactory()
   {
      if (modelPackage == null)
      {
         modelPackage = Ecore2mocaxmiPackage.eINSTANCE;
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
   public boolean isFactoryForType(Object object)
   {
      if (object == modelPackage)
      {
         return true;
      }
      if (object instanceof EObject)
      {
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
   protected Ecore2mocaxmiSwitch<Adapter> modelSwitch = new Ecore2mocaxmiSwitch<Adapter>() {
      @Override
      public Adapter caseEcore2MocaXMIConverterAdapter(Ecore2MocaXMIConverterAdapter object)
      {
         return createEcore2MocaXMIConverterAdapterAdapter();
      }

      @Override
      public Adapter caseEcore2MocaXMIConverterHelper(Ecore2MocaXMIConverterHelper object)
      {
         return createEcore2MocaXMIConverterHelperAdapter();
      }

      @Override
      public Adapter caseEcore2MocaXMIConverter(Ecore2MocaXMIConverter object)
      {
         return createEcore2MocaXMIConverterAdapter();
      }

      @Override
      public Adapter defaultCase(EObject object)
      {
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
   public Adapter createAdapter(Notifier target)
   {
      return modelSwitch.doSwitch((EObject) target);
   }

   /**
    * Creates a new adapter for an object of class '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter <em>Ecore2 Moca XMI Converter Adapter</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter
    * @generated
    */
   public Adapter createEcore2MocaXMIConverterAdapterAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper <em>Ecore2 Moca XMI Converter Helper</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper
    * @generated
    */
   public Adapter createEcore2MocaXMIConverterHelperAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter <em>Ecore2 Moca XMI Converter</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter
    * @generated
    */
   public Adapter createEcore2MocaXMIConverterAdapter()
   {
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
   public Adapter createEObjectAdapter()
   {
      return null;
   }

} //Ecore2mocaxmiAdapterFactory
