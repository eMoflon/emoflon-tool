/**
 */
package org.moflon.core.ecore2mocaxmi.impl;

import MocaTree.MocaTreePackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter;
import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter;
import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper;
import org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiFactory;
import org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Ecore2mocaxmiPackageImpl extends EPackageImpl implements Ecore2mocaxmiPackage
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass ecore2MocaXMIConverterAdapterEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass ecore2MocaXMIConverterHelperEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass ecore2MocaXMIConverterEClass = null;

   /**
    * Creates an instance of the model <b>Package</b>, registered with
    * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
    * package URI value.
    * <p>Note: the correct way to create the package is via the static
    * factory method {@link #init init()}, which also performs
    * initialization of the package, or returns the registered package,
    * if one already exists.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see org.eclipse.emf.ecore.EPackage.Registry
    * @see org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage#eNS_URI
    * @see #init()
    * @generated
    */
   private Ecore2mocaxmiPackageImpl()
   {
      super(eNS_URI, Ecore2mocaxmiFactory.eINSTANCE);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private static boolean isInited = false;

   /**
    * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
    * 
    * <p>This method is used to initialize {@link Ecore2mocaxmiPackage#eINSTANCE} when that field is accessed.
    * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #eNS_URI
    * @see #createPackageContents()
    * @see #initializePackageContents()
    * @generated
    */
   public static Ecore2mocaxmiPackage init()
   {
      if (isInited)
         return (Ecore2mocaxmiPackage) EPackage.Registry.INSTANCE.getEPackage(Ecore2mocaxmiPackage.eNS_URI);

      // Obtain or create and register package
      Ecore2mocaxmiPackageImpl theEcore2mocaxmiPackage = (Ecore2mocaxmiPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Ecore2mocaxmiPackageImpl
            ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Ecore2mocaxmiPackageImpl());

      isInited = true;

      // Initialize simple dependencies
      MocaTreePackage.eINSTANCE.eClass();

      // Create package meta-data objects
      theEcore2mocaxmiPackage.createPackageContents();

      // Initialize created meta-data
      theEcore2mocaxmiPackage.initializePackageContents();

      // Mark meta-data to indicate it can't be changed
      theEcore2mocaxmiPackage.freeze();

      // Update the registry and return the package
      EPackage.Registry.INSTANCE.put(Ecore2mocaxmiPackage.eNS_URI, theEcore2mocaxmiPackage);
      return theEcore2mocaxmiPackage;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getEcore2MocaXMIConverterAdapter()
   {
      return ecore2MocaXMIConverterAdapterEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__GetGuid__ENamedElement_String()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__AddSearchElement__ENamedElement_Attribute()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__Resolve()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__Init__String_Node()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(3);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__GetChildrenSize__Node()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(4);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__BooleanToString__boolean()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(5);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__IntToString__int()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(6);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__InstanceofEEnum__EDataType()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(7);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__CastEEnum__EDataType()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(8);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterAdapter__AddToPath__String_ENamedElement()
   {
      return ecore2MocaXMIConverterAdapterEClass.getEOperations().get(9);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getEcore2MocaXMIConverterHelper()
   {
      return ecore2MocaXMIConverterHelperEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterHelper__CreateGuidAttribute__ENamedElement_Node_int_String()
   {
      return ecore2MocaXMIConverterHelperEClass.getEOperations().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterHelper__CreateTypeGuidAttribute__EClassifier_Node_int()
   {
      return ecore2MocaXMIConverterHelperEClass.getEOperations().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterHelper__CreateNameAttribute__ENamedElement_Node_int()
   {
      return ecore2MocaXMIConverterHelperEClass.getEOperations().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverterHelper__GetType__ETypedElement()
   {
      return ecore2MocaXMIConverterHelperEClass.getEOperations().get(3);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getEcore2MocaXMIConverter()
   {
      return ecore2MocaXMIConverterEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__Convert__EPackage_String_boolean()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformSubPackage__EPackage_int_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformOutermostPackage__EPackage_String_Node_Node_boolean()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformEClassifiers__EPackage_Node_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(3);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformEClass__EClass_Node_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(4);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__Convert__EPackage()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(5);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformEAttribute__EAttribute_Node_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(6);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformEReference__EReference_Node_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(7);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformOperation__EOperation_Node_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(8);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformEParameter__EParameter_Node_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(9);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformEEnum__EEnum_Node_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(10);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformEDataType__EDataType_Node_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(11);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__TransformEEnumLiteral__EEnumLiteral_Node()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(12);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__Convert__EPackage_String()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(13);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EOperation getEcore2MocaXMIConverter__Convert__EPackage_boolean()
   {
      return ecore2MocaXMIConverterEClass.getEOperations().get(14);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Ecore2mocaxmiFactory getEcore2mocaxmiFactory()
   {
      return (Ecore2mocaxmiFactory) getEFactoryInstance();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private boolean isCreated = false;

   /**
    * Creates the meta-model objects for the package.  This method is
    * guarded to have no affect on any invocation but its first.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void createPackageContents()
   {
      if (isCreated)
         return;
      isCreated = true;

      // Create classes and their features
      ecore2MocaXMIConverterAdapterEClass = createEClass(ECORE2_MOCA_XMI_CONVERTER_ADAPTER);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_GUID__ENAMEDELEMENT_STRING);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_SEARCH_ELEMENT__ENAMEDELEMENT_ATTRIBUTE);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___RESOLVE);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INIT__STRING_NODE);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_CHILDREN_SIZE__NODE);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___BOOLEAN_TO_STRING__BOOLEAN);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INT_TO_STRING__INT);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INSTANCEOF_EENUM__EDATATYPE);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___CAST_EENUM__EDATATYPE);
      createEOperation(ecore2MocaXMIConverterAdapterEClass, ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_TO_PATH__STRING_ENAMEDELEMENT);

      ecore2MocaXMIConverterHelperEClass = createEClass(ECORE2_MOCA_XMI_CONVERTER_HELPER);
      createEOperation(ecore2MocaXMIConverterHelperEClass, ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_GUID_ATTRIBUTE__ENAMEDELEMENT_NODE_INT_STRING);
      createEOperation(ecore2MocaXMIConverterHelperEClass, ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_TYPE_GUID_ATTRIBUTE__ECLASSIFIER_NODE_INT);
      createEOperation(ecore2MocaXMIConverterHelperEClass, ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_NAME_ATTRIBUTE__ENAMEDELEMENT_NODE_INT);
      createEOperation(ecore2MocaXMIConverterHelperEClass, ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_TYPE__ETYPEDELEMENT);

      ecore2MocaXMIConverterEClass = createEClass(ECORE2_MOCA_XMI_CONVERTER);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_STRING_BOOLEAN);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_SUB_PACKAGE__EPACKAGE_INT_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_OUTERMOST_PACKAGE__EPACKAGE_STRING_NODE_NODE_BOOLEAN);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_ECLASSIFIERS__EPACKAGE_NODE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_ECLASS__ECLASS_NODE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EATTRIBUTE__EATTRIBUTE_NODE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EREFERENCE__EREFERENCE_NODE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_OPERATION__EOPERATION_NODE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EPARAMETER__EPARAMETER_NODE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EENUM__EENUM_NODE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EDATA_TYPE__EDATATYPE_NODE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EENUM_LITERAL__EENUMLITERAL_NODE);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_STRING);
      createEOperation(ecore2MocaXMIConverterEClass, ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_BOOLEAN);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private boolean isInitialized = false;

   /**
    * Complete the initialization of the package and its meta-model.  This
    * method is guarded to have no affect on any invocation but its first.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void initializePackageContents()
   {
      if (isInitialized)
         return;
      isInitialized = true;

      // Initialize package
      setName(eNAME);
      setNsPrefix(eNS_PREFIX);
      setNsURI(eNS_URI);

      // Obtain other dependent packages
      MocaTreePackage theMocaTreePackage = (MocaTreePackage) EPackage.Registry.INSTANCE.getEPackage(MocaTreePackage.eNS_URI);

      // Create type parameters

      // Set bounds for type parameters

      // Add supertypes to classes
      ecore2MocaXMIConverterHelperEClass.getESuperTypes().add(this.getEcore2MocaXMIConverterAdapter());
      ecore2MocaXMIConverterEClass.getESuperTypes().add(this.getEcore2MocaXMIConverterHelper());

      // Initialize classes, features, and operations; add parameters
      initEClass(ecore2MocaXMIConverterAdapterEClass, Ecore2MocaXMIConverterAdapter.class, "Ecore2MocaXMIConverterAdapter", !IS_ABSTRACT, !IS_INTERFACE,
            IS_GENERATED_INSTANCE_CLASS);

      EOperation op = initEOperation(getEcore2MocaXMIConverterAdapter__GetGuid__ENamedElement_String(), ecorePackage.getEString(), "getGuid", 0, 1, IS_UNIQUE,
            IS_ORDERED);
      addEParameter(op, ecorePackage.getENamedElement(), "ecoreElement", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterAdapter__AddSearchElement__ENamedElement_Attribute(), null, "addSearchElement", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getENamedElement(), "ecoreElement", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getAttribute(), "searchAttribute", 0, 1, IS_UNIQUE, IS_ORDERED);

      initEOperation(getEcore2MocaXMIConverterAdapter__Resolve(), null, "resolve", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterAdapter__Init__String_Node(), null, "init", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "projectName", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterAdapter__GetChildrenSize__Node(), ecorePackage.getEInt(), "getChildrenSize", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "node", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterAdapter__BooleanToString__boolean(), ecorePackage.getEString(), "booleanToString", 0, 1, IS_UNIQUE,
            IS_ORDERED);
      addEParameter(op, ecorePackage.getEBoolean(), "eBoolean", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterAdapter__IntToString__int(), ecorePackage.getEString(), "intToString", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEInt(), "eInt", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterAdapter__InstanceofEEnum__EDataType(), ecorePackage.getEBoolean(), "instanceofEEnum", 0, 1, IS_UNIQUE,
            IS_ORDERED);
      addEParameter(op, ecorePackage.getEDataType(), "eDataType", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterAdapter__CastEEnum__EDataType(), ecorePackage.getEEnum(), "castEEnum", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEDataType(), "eDataType", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterAdapter__AddToPath__String_ENamedElement(), ecorePackage.getEString(), "addToPath", 0, 1, IS_UNIQUE,
            IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "oldPath", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getENamedElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(ecore2MocaXMIConverterHelperEClass, Ecore2MocaXMIConverterHelper.class, "Ecore2MocaXMIConverterHelper", !IS_ABSTRACT, !IS_INTERFACE,
            IS_GENERATED_INSTANCE_CLASS);

      op = initEOperation(getEcore2MocaXMIConverterHelper__CreateGuidAttribute__ENamedElement_Node_int_String(), null, "createGuidAttribute", 0, 1, IS_UNIQUE,
            IS_ORDERED);
      addEParameter(op, ecorePackage.getENamedElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEInt(), "index", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterHelper__CreateTypeGuidAttribute__EClassifier_Node_int(), null, "createTypeGuidAttribute", 0, 1, IS_UNIQUE,
            IS_ORDERED);
      addEParameter(op, ecorePackage.getEClassifier(), "type", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEInt(), "index", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterHelper__CreateNameAttribute__ENamedElement_Node_int(), null, "createNameAttribute", 0, 1, IS_UNIQUE,
            IS_ORDERED);
      addEParameter(op, ecorePackage.getENamedElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEInt(), "index", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverterHelper__GetType__ETypedElement(), ecorePackage.getEClassifier(), "getType", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getETypedElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

      initEClass(ecore2MocaXMIConverterEClass, Ecore2MocaXMIConverter.class, "Ecore2MocaXMIConverter", !IS_ABSTRACT, !IS_INTERFACE,
            IS_GENERATED_INSTANCE_CLASS);

      op = initEOperation(getEcore2MocaXMIConverter__Convert__EPackage_String_boolean(), theMocaTreePackage.getNode(), "convert", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEPackage(), "rootPackage", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "workingSetName", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEBoolean(), "export", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformSubPackage__EPackage_int_String(), theMocaTreePackage.getNode(), "transformSubPackage", 0, 1,
            IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEPackage(), "ePackage", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEInt(), "index", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformOutermostPackage__EPackage_String_Node_Node_boolean(), null, "transformOutermostPackage", 0, 1,
            IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEPackage(), "rootEPackage", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "workingSetName", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "preConvertedNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "target", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEBoolean(), "export", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformEClassifiers__EPackage_Node_String(), null, "transformEClassifiers", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEPackage(), "ePackage", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformEClass__EClass_Node_String(), null, "transformEClass", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEClass(), "eClass", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__Convert__EPackage(), theMocaTreePackage.getNode(), "convert", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEPackage(), "rootPackage", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformEAttribute__EAttribute_Node_String(), null, "transformEAttribute", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEAttribute(), "eAttribute", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformEReference__EReference_Node_String(), null, "transformEReference", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEReference(), "eReference", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformOperation__EOperation_Node_String(), null, "transformOperation", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEOperation(), "eOperation", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformEParameter__EParameter_Node_String(), null, "transformEParameter", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEParameter(), "eParameter", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformEEnum__EEnum_Node_String(), null, "transformEEnum", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEEnum(), "eEnum", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformEDataType__EDataType_Node_String(), null, "transformEDataType", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEDataType(), "eDataType", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__TransformEEnumLiteral__EEnumLiteral_Node(), null, "transformEEnumLiteral", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEEnumLiteral(), "eEnumLiteral", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, theMocaTreePackage.getNode(), "targetNode", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__Convert__EPackage_String(), theMocaTreePackage.getNode(), "convert", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEPackage(), "rootPackage", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEString(), "workingSetName", 0, 1, IS_UNIQUE, IS_ORDERED);

      op = initEOperation(getEcore2MocaXMIConverter__Convert__EPackage_boolean(), theMocaTreePackage.getNode(), "convert", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEPackage(), "rootPackage", 0, 1, IS_UNIQUE, IS_ORDERED);
      addEParameter(op, ecorePackage.getEBoolean(), "export", 0, 1, IS_UNIQUE, IS_ORDERED);

      // Create resource
      createResource(eNS_URI);
   }

} //Ecore2mocaxmiPackageImpl
