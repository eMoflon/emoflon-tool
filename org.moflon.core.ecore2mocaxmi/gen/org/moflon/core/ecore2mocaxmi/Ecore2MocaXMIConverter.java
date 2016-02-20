/**
 */
package org.moflon.core.ecore2mocaxmi;

import MocaTree.Node;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ecore2 Moca XMI Converter</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage#getEcore2MocaXMIConverter()
 * @model
 * @generated
 */
public interface Ecore2MocaXMIConverter extends Ecore2MocaXMIConverterHelper
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   Node convert(EPackage rootPackage, String workingSetName, boolean export);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   Node transformSubPackage(EPackage ePackage, int index, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformOutermostPackage(EPackage rootEPackage, String workingSetName, Node preConvertedNode, Node target, boolean export);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformEClassifiers(EPackage ePackage, Node targetNode, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformEClass(EClass eClass, Node targetNode, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   Node convert(EPackage rootPackage);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformEAttribute(EAttribute eAttribute, Node targetNode, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformEReference(EReference eReference, Node targetNode, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformOperation(EOperation eOperation, Node targetNode, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformEParameter(EParameter eParameter, Node targetNode, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformEEnum(EEnum eEnum, Node targetNode, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformEDataType(EDataType eDataType, Node targetNode, String path);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   void transformEEnumLiteral(EEnumLiteral eEnumLiteral, Node targetNode);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   Node convert(EPackage rootPackage, String workingSetName);

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @model
    * @generated
    */
   Node convert(EPackage rootPackage, boolean export);
   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} // Ecore2MocaXMIConverter
