/**
 */
package org.moflon.core.ecore2mocaxmi;

import MocaTree.Attribute;
import MocaTree.Node;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ecore2 Moca XMI Converter Adapter</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage#getEcore2MocaXMIConverterAdapter()
 * @model
 * @generated
 */
public interface Ecore2MocaXMIConverterAdapter extends EObject
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   String getGuid(ENamedElement ecoreElement, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void addSearchElement(ENamedElement ecoreElement, Attribute searchAttribute);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void resolve();

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void init(String projectName, Node targetNode);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   int getChildrenSize(Node node);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   String booleanToString(boolean eBoolean);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   String intToString(int eInt);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   boolean instanceofEEnum(EDataType eDataType);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   EEnum castEEnum(EDataType eDataType);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   String addToPath(String oldPath, ENamedElement element);
   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} // Ecore2MocaXMIConverterAdapter
