/**
 */
package org.moflon.core.ecore2mocaxmi.impl;

import MocaTree.Attribute;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;

import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterHelper;
import org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ecore2 Moca XMI Converter Helper</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class Ecore2MocaXMIConverterHelperImpl extends Ecore2MocaXMIConverterAdapterImpl implements Ecore2MocaXMIConverterHelper
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected Ecore2MocaXMIConverterHelperImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass()
   {
      return Ecore2mocaxmiPackage.Literals.ECORE2_MOCA_XMI_CONVERTER_HELPER;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void createGuidAttribute(ENamedElement element, Node targetNode, int index, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_0_1_init_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ".");
      }

      // createGuidNode
      Object[] result2_black = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_0_2_createGuidNode_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [createGuidNode] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_0_2_createGuidNode_greenBFBBBB(targetNode, index, this, element, path);
      // Attribute guid = (Attribute) result2_green[1];

      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void createTypeGuidAttribute(EClassifier type, Node targetNode, int index)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_1_1_init_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ".");
      }

      // addTypeGuidAttribute
      Object[] result2_black = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_1_2_addTypeGuidAttribute_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [addTypeGuidAttribute] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result2_green = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_1_2_addTypeGuidAttribute_greenBFB(targetNode, index);
      Attribute typeGuid = (Attribute) result2_green[1];

      // 
      Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_1_3_expressionBBB(this, type, typeGuid);
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void createNameAttribute(ENamedElement element, Node targetNode, int index)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_2_1_init_blackB(element);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[element] = " + element + ".");
      }

      // addNameAttribute
      Object[] result2_black = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_2_2_addNameAttribute_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [addNameAttribute] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_2_2_addNameAttribute_greenBFBB(targetNode, index, element);
      // Attribute nameAttribute = (Attribute) result2_green[1];

      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClassifier getType(ETypedElement element)
   {// init
      Object[] result1_black = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_3_1_init_blackBF(element);
      if (result1_black != null)
      {
         EClassifier type = (EClassifier) result1_black[1];
         return Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_3_2_expressionFB(type);
      } else
      {

         // createVoid
         Object[] result3_green = Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_3_3_createVoid_greenF();
         if (result3_green == null)
         {
            throw new RuntimeException("Pattern matching in node [createVoid] failed.");
         }
         EDataType voidType = (EDataType) result3_green[0];
         return Ecore2MocaXMIConverterHelperImpl.pattern_Ecore2MocaXMIConverterHelper_3_4_expressionFB(voidType);
      }

   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException
   {
      switch (operationID)
      {
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_GUID_ATTRIBUTE__ENAMEDELEMENT_NODE_INT_STRING:
         createGuidAttribute((ENamedElement) arguments.get(0), (Node) arguments.get(1), (Integer) arguments.get(2), (String) arguments.get(3));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_TYPE_GUID_ATTRIBUTE__ECLASSIFIER_NODE_INT:
         createTypeGuidAttribute((EClassifier) arguments.get(0), (Node) arguments.get(1), (Integer) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_HELPER___CREATE_NAME_ATTRIBUTE__ENAMEDELEMENT_NODE_INT:
         createNameAttribute((ENamedElement) arguments.get(0), (Node) arguments.get(1), (Integer) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_HELPER___GET_TYPE__ETYPEDELEMENT:
         return getType((ETypedElement) arguments.get(0));
      }
      return super.eInvoke(operationID, arguments);
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_0_1_init_blackB(Ecore2MocaXMIConverterHelper _this)
   {
      return new Object[] { _this };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_0_2_createGuidNode_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_0_2_createGuidNode_greenBFBBBB(Node targetNode, int index,
         Ecore2MocaXMIConverterHelper _this, ENamedElement element, String path)
   {
      Attribute guid = MocaTreeFactory.eINSTANCE.createAttribute();
      String guid_name_prime = "guid";
      int guid_index_prime = Integer.valueOf(index);
      String _localVariable_0 = _this.getGuid(element, path);
      targetNode.getAttribute().add(guid);
      guid.setName(guid_name_prime);
      guid.setIndex(Integer.valueOf(guid_index_prime));
      String guid_value_prime = _localVariable_0;
      guid.setValue(guid_value_prime);
      return new Object[] { targetNode, guid, index, _this, element, path };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_1_1_init_blackB(Ecore2MocaXMIConverterHelper _this)
   {
      return new Object[] { _this };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_1_2_addTypeGuidAttribute_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_1_2_addTypeGuidAttribute_greenBFB(Node targetNode, int index)
   {
      Attribute typeGuid = MocaTreeFactory.eINSTANCE.createAttribute();
      String typeGuid_name_prime = "typeGuid";
      int typeGuid_index_prime = Integer.valueOf(index);
      targetNode.getAttribute().add(typeGuid);
      typeGuid.setName(typeGuid_name_prime);
      typeGuid.setIndex(Integer.valueOf(typeGuid_index_prime));
      return new Object[] { targetNode, typeGuid, index };
   }

   public static final void pattern_Ecore2MocaXMIConverterHelper_1_3_expressionBBB(Ecore2MocaXMIConverterHelper _this, EClassifier type, Attribute typeGuid)
   {
      _this.addSearchElement(type, typeGuid);

   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_2_1_init_blackB(ENamedElement element)
   {
      return new Object[] { element };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_2_2_addNameAttribute_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_2_2_addNameAttribute_greenBFBB(Node targetNode, int index, ENamedElement element)
   {
      Attribute nameAttribute = MocaTreeFactory.eINSTANCE.createAttribute();
      String nameAttribute_name_prime = "name";
      int nameAttribute_index_prime = Integer.valueOf(index);
      targetNode.getAttribute().add(nameAttribute);
      nameAttribute.setName(nameAttribute_name_prime);
      nameAttribute.setIndex(Integer.valueOf(nameAttribute_index_prime));
      String element_name = element.getName();
      String nameAttribute_value_prime = element_name;
      nameAttribute.setValue(nameAttribute_value_prime);
      return new Object[] { targetNode, nameAttribute, index, element };

   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_3_1_init_blackBF(ETypedElement element)
   {
      EClassifier type = element.getEType();
      if (type != null)
      {
         return new Object[] { element, type };
      }

      return null;
   }

   public static final EClassifier pattern_Ecore2MocaXMIConverterHelper_3_2_expressionFB(EClassifier type)
   {
      EClassifier _result = type;
      return _result;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverterHelper_3_3_createVoid_greenF()
   {
      EDataType voidType = EcoreFactory.eINSTANCE.createEDataType();
      String voidType_name_prime = "void";
      voidType.setName(voidType_name_prime);
      return new Object[] { voidType };
   }

   public static final EClassifier pattern_Ecore2MocaXMIConverterHelper_3_4_expressionFB(EDataType voidType)
   {
      EClassifier _result = voidType;
      return _result;
   }

   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} //Ecore2MocaXMIConverterHelperImpl
