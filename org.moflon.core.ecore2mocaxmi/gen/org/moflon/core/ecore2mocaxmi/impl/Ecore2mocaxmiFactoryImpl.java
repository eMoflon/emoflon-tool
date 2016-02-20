/**
 */
package org.moflon.core.ecore2mocaxmi.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.moflon.core.ecore2mocaxmi.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Ecore2mocaxmiFactoryImpl extends EFactoryImpl implements Ecore2mocaxmiFactory
{
   /**
    * Creates the default factory implementation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public static Ecore2mocaxmiFactory init()
   {
      try
      {
         Ecore2mocaxmiFactory theEcore2mocaxmiFactory = (Ecore2mocaxmiFactory) EPackage.Registry.INSTANCE.getEFactory(Ecore2mocaxmiPackage.eNS_URI);
         if (theEcore2mocaxmiFactory != null)
         {
            return theEcore2mocaxmiFactory;
         }
      } catch (Exception exception)
      {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new Ecore2mocaxmiFactoryImpl();
   }

   /**
    * Creates an instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Ecore2mocaxmiFactoryImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EObject create(EClass eClass)
   {
      switch (eClass.getClassifierID())
      {
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER:
         return createEcore2MocaXMIConverterAdapter();
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_HELPER:
         return createEcore2MocaXMIConverterHelper();
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER:
         return createEcore2MocaXMIConverter();
      default:
         throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Ecore2MocaXMIConverterAdapter createEcore2MocaXMIConverterAdapter()
   {
      Ecore2MocaXMIConverterAdapterImpl ecore2MocaXMIConverterAdapter = new Ecore2MocaXMIConverterAdapterImpl();
      return ecore2MocaXMIConverterAdapter;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Ecore2MocaXMIConverterHelper createEcore2MocaXMIConverterHelper()
   {
      Ecore2MocaXMIConverterHelperImpl ecore2MocaXMIConverterHelper = new Ecore2MocaXMIConverterHelperImpl();
      return ecore2MocaXMIConverterHelper;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Ecore2MocaXMIConverter createEcore2MocaXMIConverter()
   {
      Ecore2MocaXMIConverterImpl ecore2MocaXMIConverter = new Ecore2MocaXMIConverterImpl();
      return ecore2MocaXMIConverter;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Ecore2mocaxmiPackage getEcore2mocaxmiPackage()
   {
      return (Ecore2mocaxmiPackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @deprecated
    * @generated
    */
   @Deprecated
   public static Ecore2mocaxmiPackage getPackage()
   {
      return Ecore2mocaxmiPackage.eINSTANCE;
   }

} //Ecore2mocaxmiFactoryImpl
