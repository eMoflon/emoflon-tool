/**
 */
package org.moflon.core.ecore2mocaxmi;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiFactory
 * @model kind="package"
 * @generated
 */
public interface Ecore2mocaxmiPackage extends EPackage
{
   /**
    * The package name.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   String eNAME = "ecore2mocaxmi";

   /**
    * The package namespace URI.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   String eNS_URI = "platform:/plugin/org.moflon.core.ecore2mocaxmi/model/Ecore2mocaxmi.ecore";

   /**
    * The package namespace name.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   String eNS_PREFIX = "org.moflon.core.ecore2mocaxmi";

   /**
    * The singleton instance of the package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   Ecore2mocaxmiPackage eINSTANCE = org.moflon.core.ecore2mocaxmi.impl.Ecore2mocaxmiPackageImpl.init();

   /**
    * The meta object id for the '{@link org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterAdapterImpl <em>Ecore2 Moca XMI Converter Adapter</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterAdapterImpl
    * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2mocaxmiPackageImpl#getEcore2MocaXMIConverterAdapter()
    * @generated
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER = 0;

   /**
    * The number of structural features of the '<em>Ecore2 Moca XMI Converter Adapter</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER_FEATURE_COUNT = 0;

   /**
    * The operation id for the '<em>Get Guid</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_GUID__ENAMEDELEMENT_STRING = 0;

   /**
    * The operation id for the '<em>Add Search Element</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_SEARCH_ELEMENT__ENAMEDELEMENT_ATTRIBUTE = 1;

   /**
    * The operation id for the '<em>Resolve</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___RESOLVE = 2;

   /**
    * The operation id for the '<em>Init</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INIT__STRING_NODE = 3;

   /**
    * The operation id for the '<em>Get Children Size</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_CHILDREN_SIZE__NODE = 4;

   /**
    * The operation id for the '<em>Boolean To String</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___BOOLEAN_TO_STRING__BOOLEAN = 5;

   /**
    * The operation id for the '<em>Int To String</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INT_TO_STRING__INT = 6;

   /**
    * The operation id for the '<em>Instanceof EEnum</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INSTANCEOF_EENUM__EDATATYPE = 7;

   /**
    * The operation id for the '<em>Cast EEnum</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___CAST_EENUM__EDATATYPE = 8;

   /**
    * The operation id for the '<em>Add To Path</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_TO_PATH__STRING_ENAMEDELEMENT = 9;

   /**
    * The number of operations of the '<em>Ecore2 Moca XMI Converter Adapter</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_ADAPTER_OPERATION_COUNT = 10;

   /**
    * The meta object id for the '{@link org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterHelperImpl <em>Ecore2 Moca XMI Converter Helper</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterHelperImpl
    * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2mocaxmiPackageImpl#getEcore2MocaXMIConverterHelper()
    * @generated
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER = 1;

   /**
    * The number of structural features of the '<em>Ecore2 Moca XMI Converter Helper</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER_FEATURE_COUNT = ECORE2_MOCA_XMI_CONVERTER_ADAPTER_FEATURE_COUNT + 0;

   /**
    * The operation id for the '<em>Get Guid</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_GUID__ENAMEDELEMENT_STRING = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_GUID__ENAMEDELEMENT_STRING;

   /**
    * The operation id for the '<em>Add Search Element</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___ADD_SEARCH_ELEMENT__ENAMEDELEMENT_ATTRIBUTE = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_SEARCH_ELEMENT__ENAMEDELEMENT_ATTRIBUTE;

   /**
    * The operation id for the '<em>Resolve</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___RESOLVE = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___RESOLVE;

   /**
    * The operation id for the '<em>Init</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___INIT__STRING_NODE = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INIT__STRING_NODE;

   /**
    * The operation id for the '<em>Get Children Size</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_CHILDREN_SIZE__NODE = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_CHILDREN_SIZE__NODE;

   /**
    * The operation id for the '<em>Boolean To String</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___BOOLEAN_TO_STRING__BOOLEAN = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___BOOLEAN_TO_STRING__BOOLEAN;

   /**
    * The operation id for the '<em>Int To String</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___INT_TO_STRING__INT = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INT_TO_STRING__INT;

   /**
    * The operation id for the '<em>Instanceof EEnum</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___INSTANCEOF_EENUM__EDATATYPE = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INSTANCEOF_EENUM__EDATATYPE;

   /**
    * The operation id for the '<em>Cast EEnum</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___CAST_EENUM__EDATATYPE = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___CAST_EENUM__EDATATYPE;

   /**
    * The operation id for the '<em>Add To Path</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___ADD_TO_PATH__STRING_ENAMEDELEMENT = ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_TO_PATH__STRING_ENAMEDELEMENT;

   /**
    * The operation id for the '<em>Create Guid Attribute</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_GUID_ATTRIBUTE__ENAMEDELEMENT_NODE_INT_STRING = ECORE2_MOCA_XMI_CONVERTER_ADAPTER_OPERATION_COUNT + 0;

   /**
    * The operation id for the '<em>Create Type Guid Attribute</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_TYPE_GUID_ATTRIBUTE__ECLASSIFIER_NODE_INT = ECORE2_MOCA_XMI_CONVERTER_ADAPTER_OPERATION_COUNT + 1;

   /**
    * The operation id for the '<em>Create Name Attribute</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_NAME_ATTRIBUTE__ENAMEDELEMENT_NODE_INT = ECORE2_MOCA_XMI_CONVERTER_ADAPTER_OPERATION_COUNT + 2;

   /**
    * The operation id for the '<em>Get Type</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_TYPE__ETYPEDELEMENT = ECORE2_MOCA_XMI_CONVERTER_ADAPTER_OPERATION_COUNT + 3;

   /**
    * The number of operations of the '<em>Ecore2 Moca XMI Converter Helper</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT = ECORE2_MOCA_XMI_CONVERTER_ADAPTER_OPERATION_COUNT + 4;

   /**
    * The meta object id for the '{@link org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterImpl <em>Ecore2 Moca XMI Converter</em>}' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterImpl
    * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2mocaxmiPackageImpl#getEcore2MocaXMIConverter()
    * @generated
    */
   int ECORE2_MOCA_XMI_CONVERTER = 2;

   /**
    * The number of structural features of the '<em>Ecore2 Moca XMI Converter</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_FEATURE_COUNT = ECORE2_MOCA_XMI_CONVERTER_HELPER_FEATURE_COUNT + 0;

   /**
    * The operation id for the '<em>Get Guid</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___GET_GUID__ENAMEDELEMENT_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_GUID__ENAMEDELEMENT_STRING;

   /**
    * The operation id for the '<em>Add Search Element</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___ADD_SEARCH_ELEMENT__ENAMEDELEMENT_ATTRIBUTE = ECORE2_MOCA_XMI_CONVERTER_HELPER___ADD_SEARCH_ELEMENT__ENAMEDELEMENT_ATTRIBUTE;

   /**
    * The operation id for the '<em>Resolve</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___RESOLVE = ECORE2_MOCA_XMI_CONVERTER_HELPER___RESOLVE;

   /**
    * The operation id for the '<em>Init</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___INIT__STRING_NODE = ECORE2_MOCA_XMI_CONVERTER_HELPER___INIT__STRING_NODE;

   /**
    * The operation id for the '<em>Get Children Size</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___GET_CHILDREN_SIZE__NODE = ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_CHILDREN_SIZE__NODE;

   /**
    * The operation id for the '<em>Boolean To String</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___BOOLEAN_TO_STRING__BOOLEAN = ECORE2_MOCA_XMI_CONVERTER_HELPER___BOOLEAN_TO_STRING__BOOLEAN;

   /**
    * The operation id for the '<em>Int To String</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___INT_TO_STRING__INT = ECORE2_MOCA_XMI_CONVERTER_HELPER___INT_TO_STRING__INT;

   /**
    * The operation id for the '<em>Instanceof EEnum</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___INSTANCEOF_EENUM__EDATATYPE = ECORE2_MOCA_XMI_CONVERTER_HELPER___INSTANCEOF_EENUM__EDATATYPE;

   /**
    * The operation id for the '<em>Cast EEnum</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___CAST_EENUM__EDATATYPE = ECORE2_MOCA_XMI_CONVERTER_HELPER___CAST_EENUM__EDATATYPE;

   /**
    * The operation id for the '<em>Add To Path</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___ADD_TO_PATH__STRING_ENAMEDELEMENT = ECORE2_MOCA_XMI_CONVERTER_HELPER___ADD_TO_PATH__STRING_ENAMEDELEMENT;

   /**
    * The operation id for the '<em>Create Guid Attribute</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___CREATE_GUID_ATTRIBUTE__ENAMEDELEMENT_NODE_INT_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_GUID_ATTRIBUTE__ENAMEDELEMENT_NODE_INT_STRING;

   /**
    * The operation id for the '<em>Create Type Guid Attribute</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___CREATE_TYPE_GUID_ATTRIBUTE__ECLASSIFIER_NODE_INT = ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_TYPE_GUID_ATTRIBUTE__ECLASSIFIER_NODE_INT;

   /**
    * The operation id for the '<em>Create Name Attribute</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___CREATE_NAME_ATTRIBUTE__ENAMEDELEMENT_NODE_INT = ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_NAME_ATTRIBUTE__ENAMEDELEMENT_NODE_INT;

   /**
    * The operation id for the '<em>Get Type</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___GET_TYPE__ETYPEDELEMENT = ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_TYPE__ETYPEDELEMENT;

   /**
    * The operation id for the '<em>Convert</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_STRING_BOOLEAN = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 0;

   /**
    * The operation id for the '<em>Transform Sub Package</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_SUB_PACKAGE__EPACKAGE_INT_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 1;

   /**
    * The operation id for the '<em>Transform Outermost Package</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_OUTERMOST_PACKAGE__EPACKAGE_STRING_NODE_NODE_BOOLEAN = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 2;

   /**
    * The operation id for the '<em>Transform EClassifiers</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_ECLASSIFIERS__EPACKAGE_NODE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 3;

   /**
    * The operation id for the '<em>Transform EClass</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_ECLASS__ECLASS_NODE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 4;

   /**
    * The operation id for the '<em>Convert</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 5;

   /**
    * The operation id for the '<em>Transform EAttribute</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EATTRIBUTE__EATTRIBUTE_NODE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 6;

   /**
    * The operation id for the '<em>Transform EReference</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EREFERENCE__EREFERENCE_NODE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 7;

   /**
    * The operation id for the '<em>Transform Operation</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_OPERATION__EOPERATION_NODE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 8;

   /**
    * The operation id for the '<em>Transform EParameter</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EPARAMETER__EPARAMETER_NODE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 9;

   /**
    * The operation id for the '<em>Transform EEnum</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EENUM__EENUM_NODE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 10;

   /**
    * The operation id for the '<em>Transform EData Type</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EDATA_TYPE__EDATATYPE_NODE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 11;

   /**
    * The operation id for the '<em>Transform EEnum Literal</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EENUM_LITERAL__EENUMLITERAL_NODE = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 12;

   /**
    * The operation id for the '<em>Convert</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_STRING = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 13;

   /**
    * The operation id for the '<em>Convert</em>' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_BOOLEAN = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 14;

   /**
    * The number of operations of the '<em>Ecore2 Moca XMI Converter</em>' class.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    * @ordered
    */
   int ECORE2_MOCA_XMI_CONVERTER_OPERATION_COUNT = ECORE2_MOCA_XMI_CONVERTER_HELPER_OPERATION_COUNT + 15;

   /**
    * Returns the meta object for class '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter <em>Ecore2 Moca XMI Converter Adapter</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for class '<em>Ecore2 Moca XMI Converter Adapter</em>'.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter
    * @generated
    */
   EClass getEcore2MocaXMIConverterAdapter();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#getGuid(org.eclipse.emf.ecore.ENamedElement, java.lang.String) <em>Get Guid</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Get Guid</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#getGuid(org.eclipse.emf.ecore.ENamedElement, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__GetGuid__ENamedElement_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#addSearchElement(org.eclipse.emf.ecore.ENamedElement, MocaTree.Attribute) <em>Add Search Element</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Add Search Element</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#addSearchElement(org.eclipse.emf.ecore.ENamedElement, MocaTree.Attribute)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__AddSearchElement__ENamedElement_Attribute();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#resolve() <em>Resolve</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Resolve</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#resolve()
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__Resolve();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#init(java.lang.String, MocaTree.Node) <em>Init</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Init</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#init(java.lang.String, MocaTree.Node)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__Init__String_Node();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#getChildrenSize(MocaTree.Node) <em>Get Children Size</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Get Children Size</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#getChildrenSize(MocaTree.Node)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__GetChildrenSize__Node();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#booleanToString(boolean) <em>Boolean To String</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Boolean To String</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#booleanToString(boolean)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__BooleanToString__boolean();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#intToString(int) <em>Int To String</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Int To String</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#intToString(int)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__IntToString__int();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#instanceofEEnum(org.eclipse.emf.ecore.EDataType) <em>Instanceof EEnum</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Instanceof EEnum</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#instanceofEEnum(org.eclipse.emf.ecore.EDataType)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__InstanceofEEnum__EDataType();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#castEEnum(org.eclipse.emf.ecore.EDataType) <em>Cast EEnum</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Cast EEnum</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#castEEnum(org.eclipse.emf.ecore.EDataType)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__CastEEnum__EDataType();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#addToPath(java.lang.String, org.eclipse.emf.ecore.ENamedElement) <em>Add To Path</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Add To Path</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter#addToPath(java.lang.String, org.eclipse.emf.ecore.ENamedElement)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterAdapter__AddToPath__String_ENamedElement();

   /**
    * Returns the meta object for class '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper <em>Ecore2 Moca XMI Converter Helper</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for class '<em>Ecore2 Moca XMI Converter Helper</em>'.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper
    * @generated
    */
   EClass getEcore2MocaXMIConverterHelper();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper#createGuidAttribute(org.eclipse.emf.ecore.ENamedElement, MocaTree.Node, int, java.lang.String) <em>Create Guid Attribute</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Create Guid Attribute</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper#createGuidAttribute(org.eclipse.emf.ecore.ENamedElement, MocaTree.Node, int, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterHelper__CreateGuidAttribute__ENamedElement_Node_int_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper#createTypeGuidAttribute(org.eclipse.emf.ecore.EClassifier, MocaTree.Node, int) <em>Create Type Guid Attribute</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Create Type Guid Attribute</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper#createTypeGuidAttribute(org.eclipse.emf.ecore.EClassifier, MocaTree.Node, int)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterHelper__CreateTypeGuidAttribute__EClassifier_Node_int();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper#createNameAttribute(org.eclipse.emf.ecore.ENamedElement, MocaTree.Node, int) <em>Create Name Attribute</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Create Name Attribute</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper#createNameAttribute(org.eclipse.emf.ecore.ENamedElement, MocaTree.Node, int)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterHelper__CreateNameAttribute__ENamedElement_Node_int();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper#getType(org.eclipse.emf.ecore.ETypedElement) <em>Get Type</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Get Type</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper#getType(org.eclipse.emf.ecore.ETypedElement)
    * @generated
    */
   EOperation getEcore2MocaXMIConverterHelper__GetType__ETypedElement();

   /**
    * Returns the meta object for class '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter <em>Ecore2 Moca XMI Converter</em>}'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for class '<em>Ecore2 Moca XMI Converter</em>'.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter
    * @generated
    */
   EClass getEcore2MocaXMIConverter();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#convert(org.eclipse.emf.ecore.EPackage, java.lang.String, boolean) <em>Convert</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Convert</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#convert(org.eclipse.emf.ecore.EPackage, java.lang.String, boolean)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__Convert__EPackage_String_boolean();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformSubPackage(org.eclipse.emf.ecore.EPackage, int, java.lang.String) <em>Transform Sub Package</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform Sub Package</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformSubPackage(org.eclipse.emf.ecore.EPackage, int, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformSubPackage__EPackage_int_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformOutermostPackage(org.eclipse.emf.ecore.EPackage, java.lang.String, MocaTree.Node, MocaTree.Node, boolean) <em>Transform Outermost Package</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform Outermost Package</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformOutermostPackage(org.eclipse.emf.ecore.EPackage, java.lang.String, MocaTree.Node, MocaTree.Node, boolean)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformOutermostPackage__EPackage_String_Node_Node_boolean();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEClassifiers(org.eclipse.emf.ecore.EPackage, MocaTree.Node, java.lang.String) <em>Transform EClassifiers</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform EClassifiers</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEClassifiers(org.eclipse.emf.ecore.EPackage, MocaTree.Node, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformEClassifiers__EPackage_Node_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEClass(org.eclipse.emf.ecore.EClass, MocaTree.Node, java.lang.String) <em>Transform EClass</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform EClass</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEClass(org.eclipse.emf.ecore.EClass, MocaTree.Node, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformEClass__EClass_Node_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#convert(org.eclipse.emf.ecore.EPackage) <em>Convert</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Convert</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#convert(org.eclipse.emf.ecore.EPackage)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__Convert__EPackage();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEAttribute(org.eclipse.emf.ecore.EAttribute, MocaTree.Node, java.lang.String) <em>Transform EAttribute</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform EAttribute</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEAttribute(org.eclipse.emf.ecore.EAttribute, MocaTree.Node, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformEAttribute__EAttribute_Node_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEReference(org.eclipse.emf.ecore.EReference, MocaTree.Node, java.lang.String) <em>Transform EReference</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform EReference</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEReference(org.eclipse.emf.ecore.EReference, MocaTree.Node, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformEReference__EReference_Node_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformOperation(org.eclipse.emf.ecore.EOperation, MocaTree.Node, java.lang.String) <em>Transform Operation</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform Operation</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformOperation(org.eclipse.emf.ecore.EOperation, MocaTree.Node, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformOperation__EOperation_Node_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEParameter(org.eclipse.emf.ecore.EParameter, MocaTree.Node, java.lang.String) <em>Transform EParameter</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform EParameter</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEParameter(org.eclipse.emf.ecore.EParameter, MocaTree.Node, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformEParameter__EParameter_Node_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEEnum(org.eclipse.emf.ecore.EEnum, MocaTree.Node, java.lang.String) <em>Transform EEnum</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform EEnum</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEEnum(org.eclipse.emf.ecore.EEnum, MocaTree.Node, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformEEnum__EEnum_Node_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEDataType(org.eclipse.emf.ecore.EDataType, MocaTree.Node, java.lang.String) <em>Transform EData Type</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform EData Type</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEDataType(org.eclipse.emf.ecore.EDataType, MocaTree.Node, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformEDataType__EDataType_Node_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEEnumLiteral(org.eclipse.emf.ecore.EEnumLiteral, MocaTree.Node) <em>Transform EEnum Literal</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Transform EEnum Literal</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#transformEEnumLiteral(org.eclipse.emf.ecore.EEnumLiteral, MocaTree.Node)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__TransformEEnumLiteral__EEnumLiteral_Node();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#convert(org.eclipse.emf.ecore.EPackage, java.lang.String) <em>Convert</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Convert</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#convert(org.eclipse.emf.ecore.EPackage, java.lang.String)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__Convert__EPackage_String();

   /**
    * Returns the meta object for the '{@link org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#convert(org.eclipse.emf.ecore.EPackage, boolean) <em>Convert</em>}' operation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the meta object for the '<em>Convert</em>' operation.
    * @see org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter#convert(org.eclipse.emf.ecore.EPackage, boolean)
    * @generated
    */
   EOperation getEcore2MocaXMIConverter__Convert__EPackage_boolean();

   /**
    * Returns the factory that creates the instances of the model.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the factory that creates the instances of the model.
    * @generated
    */
   Ecore2mocaxmiFactory getEcore2mocaxmiFactory();

   /**
    * <!-- begin-user-doc -->
    * Defines literals for the meta objects that represent
    * <ul>
    *   <li>each class,</li>
    *   <li>each feature of each class,</li>
    *   <li>each operation of each class,</li>
    *   <li>each enum,</li>
    *   <li>and each data type</li>
    * </ul>
    * <!-- end-user-doc -->
    * @generated
    */
   interface Literals
   {
      /**
       * The meta object literal for the '{@link org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterAdapterImpl <em>Ecore2 Moca XMI Converter Adapter</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterAdapterImpl
       * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2mocaxmiPackageImpl#getEcore2MocaXMIConverterAdapter()
       * @generated
       */
      EClass ECORE2_MOCA_XMI_CONVERTER_ADAPTER = eINSTANCE.getEcore2MocaXMIConverterAdapter();

      /**
       * The meta object literal for the '<em><b>Get Guid</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_GUID__ENAMEDELEMENT_STRING = eINSTANCE
            .getEcore2MocaXMIConverterAdapter__GetGuid__ENamedElement_String();

      /**
       * The meta object literal for the '<em><b>Add Search Element</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_SEARCH_ELEMENT__ENAMEDELEMENT_ATTRIBUTE = eINSTANCE
            .getEcore2MocaXMIConverterAdapter__AddSearchElement__ENamedElement_Attribute();

      /**
       * The meta object literal for the '<em><b>Resolve</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___RESOLVE = eINSTANCE.getEcore2MocaXMIConverterAdapter__Resolve();

      /**
       * The meta object literal for the '<em><b>Init</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INIT__STRING_NODE = eINSTANCE.getEcore2MocaXMIConverterAdapter__Init__String_Node();

      /**
       * The meta object literal for the '<em><b>Get Children Size</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_CHILDREN_SIZE__NODE = eINSTANCE.getEcore2MocaXMIConverterAdapter__GetChildrenSize__Node();

      /**
       * The meta object literal for the '<em><b>Boolean To String</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___BOOLEAN_TO_STRING__BOOLEAN = eINSTANCE.getEcore2MocaXMIConverterAdapter__BooleanToString__boolean();

      /**
       * The meta object literal for the '<em><b>Int To String</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INT_TO_STRING__INT = eINSTANCE.getEcore2MocaXMIConverterAdapter__IntToString__int();

      /**
       * The meta object literal for the '<em><b>Instanceof EEnum</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INSTANCEOF_EENUM__EDATATYPE = eINSTANCE.getEcore2MocaXMIConverterAdapter__InstanceofEEnum__EDataType();

      /**
       * The meta object literal for the '<em><b>Cast EEnum</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___CAST_EENUM__EDATATYPE = eINSTANCE.getEcore2MocaXMIConverterAdapter__CastEEnum__EDataType();

      /**
       * The meta object literal for the '<em><b>Add To Path</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_TO_PATH__STRING_ENAMEDELEMENT = eINSTANCE
            .getEcore2MocaXMIConverterAdapter__AddToPath__String_ENamedElement();

      /**
       * The meta object literal for the '{@link org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterHelperImpl <em>Ecore2 Moca XMI Converter Helper</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterHelperImpl
       * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2mocaxmiPackageImpl#getEcore2MocaXMIConverterHelper()
       * @generated
       */
      EClass ECORE2_MOCA_XMI_CONVERTER_HELPER = eINSTANCE.getEcore2MocaXMIConverterHelper();

      /**
       * The meta object literal for the '<em><b>Create Guid Attribute</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_GUID_ATTRIBUTE__ENAMEDELEMENT_NODE_INT_STRING = eINSTANCE
            .getEcore2MocaXMIConverterHelper__CreateGuidAttribute__ENamedElement_Node_int_String();

      /**
       * The meta object literal for the '<em><b>Create Type Guid Attribute</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_TYPE_GUID_ATTRIBUTE__ECLASSIFIER_NODE_INT = eINSTANCE
            .getEcore2MocaXMIConverterHelper__CreateTypeGuidAttribute__EClassifier_Node_int();

      /**
       * The meta object literal for the '<em><b>Create Name Attribute</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_NAME_ATTRIBUTE__ENAMEDELEMENT_NODE_INT = eINSTANCE
            .getEcore2MocaXMIConverterHelper__CreateNameAttribute__ENamedElement_Node_int();

      /**
       * The meta object literal for the '<em><b>Get Type</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_TYPE__ETYPEDELEMENT = eINSTANCE.getEcore2MocaXMIConverterHelper__GetType__ETypedElement();

      /**
       * The meta object literal for the '{@link org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterImpl <em>Ecore2 Moca XMI Converter</em>}' class.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2MocaXMIConverterImpl
       * @see org.moflon.core.ecore2mocaxmi.impl.Ecore2mocaxmiPackageImpl#getEcore2MocaXMIConverter()
       * @generated
       */
      EClass ECORE2_MOCA_XMI_CONVERTER = eINSTANCE.getEcore2MocaXMIConverter();

      /**
       * The meta object literal for the '<em><b>Convert</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_STRING_BOOLEAN = eINSTANCE.getEcore2MocaXMIConverter__Convert__EPackage_String_boolean();

      /**
       * The meta object literal for the '<em><b>Transform Sub Package</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_SUB_PACKAGE__EPACKAGE_INT_STRING = eINSTANCE
            .getEcore2MocaXMIConverter__TransformSubPackage__EPackage_int_String();

      /**
       * The meta object literal for the '<em><b>Transform Outermost Package</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_OUTERMOST_PACKAGE__EPACKAGE_STRING_NODE_NODE_BOOLEAN = eINSTANCE
            .getEcore2MocaXMIConverter__TransformOutermostPackage__EPackage_String_Node_Node_boolean();

      /**
       * The meta object literal for the '<em><b>Transform EClassifiers</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_ECLASSIFIERS__EPACKAGE_NODE_STRING = eINSTANCE
            .getEcore2MocaXMIConverter__TransformEClassifiers__EPackage_Node_String();

      /**
       * The meta object literal for the '<em><b>Transform EClass</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_ECLASS__ECLASS_NODE_STRING = eINSTANCE.getEcore2MocaXMIConverter__TransformEClass__EClass_Node_String();

      /**
       * The meta object literal for the '<em><b>Convert</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE = eINSTANCE.getEcore2MocaXMIConverter__Convert__EPackage();

      /**
       * The meta object literal for the '<em><b>Transform EAttribute</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EATTRIBUTE__EATTRIBUTE_NODE_STRING = eINSTANCE
            .getEcore2MocaXMIConverter__TransformEAttribute__EAttribute_Node_String();

      /**
       * The meta object literal for the '<em><b>Transform EReference</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EREFERENCE__EREFERENCE_NODE_STRING = eINSTANCE
            .getEcore2MocaXMIConverter__TransformEReference__EReference_Node_String();

      /**
       * The meta object literal for the '<em><b>Transform Operation</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_OPERATION__EOPERATION_NODE_STRING = eINSTANCE
            .getEcore2MocaXMIConverter__TransformOperation__EOperation_Node_String();

      /**
       * The meta object literal for the '<em><b>Transform EParameter</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EPARAMETER__EPARAMETER_NODE_STRING = eINSTANCE
            .getEcore2MocaXMIConverter__TransformEParameter__EParameter_Node_String();

      /**
       * The meta object literal for the '<em><b>Transform EEnum</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EENUM__EENUM_NODE_STRING = eINSTANCE.getEcore2MocaXMIConverter__TransformEEnum__EEnum_Node_String();

      /**
       * The meta object literal for the '<em><b>Transform EData Type</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EDATA_TYPE__EDATATYPE_NODE_STRING = eINSTANCE
            .getEcore2MocaXMIConverter__TransformEDataType__EDataType_Node_String();

      /**
       * The meta object literal for the '<em><b>Transform EEnum Literal</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EENUM_LITERAL__EENUMLITERAL_NODE = eINSTANCE
            .getEcore2MocaXMIConverter__TransformEEnumLiteral__EEnumLiteral_Node();

      /**
       * The meta object literal for the '<em><b>Convert</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_STRING = eINSTANCE.getEcore2MocaXMIConverter__Convert__EPackage_String();

      /**
       * The meta object literal for the '<em><b>Convert</b></em>' operation.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      EOperation ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_BOOLEAN = eINSTANCE.getEcore2MocaXMIConverter__Convert__EPackage_boolean();

   }

} //Ecore2mocaxmiPackage
