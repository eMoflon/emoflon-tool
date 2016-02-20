/**
 */
package org.moflon.core.ecore2mocaxmi;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage
 * @generated
 */
public interface Ecore2mocaxmiFactory extends EFactory
{
   /**
    * The singleton instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   Ecore2mocaxmiFactory eINSTANCE = org.moflon.core.ecore2mocaxmi.impl.Ecore2mocaxmiFactoryImpl.init();

   /**
    * Returns a new object of class '<em>Ecore2 Moca XMI Converter Adapter</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>Ecore2 Moca XMI Converter Adapter</em>'.
    * @generated
    */
   Ecore2MocaXMIConverterAdapter createEcore2MocaXMIConverterAdapter();

   /**
    * Returns a new object of class '<em>Ecore2 Moca XMI Converter Helper</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>Ecore2 Moca XMI Converter Helper</em>'.
    * @generated
    */
   Ecore2MocaXMIConverterHelper createEcore2MocaXMIConverterHelper();

   /**
    * Returns a new object of class '<em>Ecore2 Moca XMI Converter</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>Ecore2 Moca XMI Converter</em>'.
    * @generated
    */
   Ecore2MocaXMIConverter createEcore2MocaXMIConverter();

   /**
    * Returns the package supported by this factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the package supported by this factory.
    * @generated
    */
   Ecore2mocaxmiPackage getEcore2mocaxmiPackage();

} //Ecore2mocaxmiFactory
