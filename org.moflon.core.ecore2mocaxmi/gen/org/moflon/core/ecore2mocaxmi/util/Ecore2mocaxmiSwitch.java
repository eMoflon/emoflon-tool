/**
 */
package org.moflon.core.ecore2mocaxmi.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.moflon.core.ecore2mocaxmi.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage
 * @generated
 */
public class Ecore2mocaxmiSwitch<T> extends Switch<T>
{
   /**
    * The cached model package
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected static Ecore2mocaxmiPackage modelPackage;

   /**
    * Creates an instance of the switch.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Ecore2mocaxmiSwitch()
   {
      if (modelPackage == null)
      {
         modelPackage = Ecore2mocaxmiPackage.eINSTANCE;
      }
   }

   /**
    * Checks whether this is a switch for the given package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param ePackage the package in question.
    * @return whether this is a switch for the given package.
    * @generated
    */
   @Override
   protected boolean isSwitchFor(EPackage ePackage)
   {
      return ePackage == modelPackage;
   }

   /**
    * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the first non-null result returned by a <code>caseXXX</code> call.
    * @generated
    */
   @Override
   protected T doSwitch(int classifierID, EObject theEObject)
   {
      switch (classifierID)
      {
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER:
      {
         Ecore2MocaXMIConverterAdapter ecore2MocaXMIConverterAdapter = (Ecore2MocaXMIConverterAdapter) theEObject;
         T result = caseEcore2MocaXMIConverterAdapter(ecore2MocaXMIConverterAdapter);
         if (result == null)
            result = defaultCase(theEObject);
         return result;
      }
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_HELPER:
      {
         Ecore2MocaXMIConverterHelper ecore2MocaXMIConverterHelper = (Ecore2MocaXMIConverterHelper) theEObject;
         T result = caseEcore2MocaXMIConverterHelper(ecore2MocaXMIConverterHelper);
         if (result == null)
            result = caseEcore2MocaXMIConverterAdapter(ecore2MocaXMIConverterHelper);
         if (result == null)
            result = defaultCase(theEObject);
         return result;
      }
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER:
      {
         Ecore2MocaXMIConverter ecore2MocaXMIConverter = (Ecore2MocaXMIConverter) theEObject;
         T result = caseEcore2MocaXMIConverter(ecore2MocaXMIConverter);
         if (result == null)
            result = caseEcore2MocaXMIConverterHelper(ecore2MocaXMIConverter);
         if (result == null)
            result = caseEcore2MocaXMIConverterAdapter(ecore2MocaXMIConverter);
         if (result == null)
            result = defaultCase(theEObject);
         return result;
      }
      default:
         return defaultCase(theEObject);
      }
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Ecore2 Moca XMI Converter Adapter</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Ecore2 Moca XMI Converter Adapter</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseEcore2MocaXMIConverterAdapter(Ecore2MocaXMIConverterAdapter object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Ecore2 Moca XMI Converter Helper</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Ecore2 Moca XMI Converter Helper</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseEcore2MocaXMIConverterHelper(Ecore2MocaXMIConverterHelper object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>Ecore2 Moca XMI Converter</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>Ecore2 Moca XMI Converter</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseEcore2MocaXMIConverter(Ecore2MocaXMIConverter object)
   {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch, but this is the last case anyway.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject)
    * @generated
    */
   @Override
   public T defaultCase(EObject object)
   {
      return null;
   }

} //Ecore2mocaxmiSwitch
