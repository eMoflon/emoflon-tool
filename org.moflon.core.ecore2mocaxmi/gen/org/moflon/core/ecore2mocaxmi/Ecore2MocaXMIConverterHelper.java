/**
 */
package org.moflon.core.ecore2mocaxmi;

import MocaTree.Node;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.ETypedElement;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ecore2 Moca XMI Converter Helper</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage#getEcore2MocaXMIConverterHelper()
 * @model
 * @generated
 */
public interface Ecore2MocaXMIConverterHelper extends Ecore2MocaXMIConverterAdapter
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void createGuidAttribute(ENamedElement element, Node targetNode, int index, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void createTypeGuidAttribute(EClassifier type, Node targetNode, int index);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void createNameAttribute(ENamedElement element, Node targetNode, int index);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   EClassifier getType(ETypedElement element);
   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} // Ecore2MocaXMIConverterHelper
