/**
 */
package org.moflon.core.ecore2mocaxmi.impl;

import MocaTree.Attribute;
import MocaTree.Node;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.ENamedElement;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverterAdapter;
import org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage;
// <-- [user defined imports]
import org.moflon.core.ecore2mocaxmi.utils.GuidManager;
import org.moflon.core.ecore2mocaxmi.utils.PathProvider;
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ecore2 Moca XMI Converter Adapter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class Ecore2MocaXMIConverterAdapterImpl extends EObjectImpl implements Ecore2MocaXMIConverterAdapter
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected Ecore2MocaXMIConverterAdapterImpl()
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
      return Ecore2mocaxmiPackage.Literals.ECORE2_MOCA_XMI_CONVERTER_ADAPTER;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public String getGuid(ENamedElement ecoreElement, String path)
   {
      // [user code injected with eMoflon]
      return manager.getGuid(ecoreElement, path);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void addSearchElement(ENamedElement ecoreElement, Attribute searchAttribute)
   {
      // [user code injected with eMoflon]
      manager.addSearchElement(ecoreElement, searchAttribute);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void resolve()
   {
      // [user code injected with eMoflon]
      manager.resolve();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void init(String projectName, Node targetNode)
   {
      // [user code injected with eMoflon]
      manager.init(projectName);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public int getChildrenSize(Node node)
   {
      // [user code injected with eMoflon]
      return node.getChildren().size();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public String booleanToString(boolean eBoolean)
   {
      // [user code injected with eMoflon]
      return "" + eBoolean;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public String intToString(int eInt)
   {
      // [user code injected with eMoflon]
      return "" + eInt;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public boolean instanceofEEnum(EDataType eDataType)
   {
      // [user code injected with eMoflon]
      return eDataType instanceof EEnum;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EEnum castEEnum(EDataType eDataType)
   {
      // [user code injected with eMoflon]
      return EEnum.class.cast(eDataType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public String addToPath(String oldPath, ENamedElement element)
   {
      // [user code injected with eMoflon]
      return PathProvider.getNewPath(oldPath, element);
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
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_GUID__ENAMEDELEMENT_STRING:
         return getGuid((ENamedElement) arguments.get(0), (String) arguments.get(1));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_SEARCH_ELEMENT__ENAMEDELEMENT_ATTRIBUTE:
         addSearchElement((ENamedElement) arguments.get(0), (Attribute) arguments.get(1));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___RESOLVE:
         resolve();
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INIT__STRING_NODE:
         init((String) arguments.get(0), (Node) arguments.get(1));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___GET_CHILDREN_SIZE__NODE:
         return getChildrenSize((Node) arguments.get(0));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___BOOLEAN_TO_STRING__BOOLEAN:
         return booleanToString((Boolean) arguments.get(0));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INT_TO_STRING__INT:
         return intToString((Integer) arguments.get(0));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___INSTANCEOF_EENUM__EDATATYPE:
         return instanceofEEnum((EDataType) arguments.get(0));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___CAST_EENUM__EDATATYPE:
         return castEEnum((EDataType) arguments.get(0));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER_ADAPTER___ADD_TO_PATH__STRING_ENAMEDELEMENT:
         return addToPath((String) arguments.get(0), (ENamedElement) arguments.get(1));
      }
      return super.eInvoke(operationID, arguments);
   }

   // <-- [user code injected with eMoflon]
   private GuidManager manager = new GuidManager();
   // [user code injected with eMoflon] -->
} //Ecore2MocaXMIConverterAdapterImpl
