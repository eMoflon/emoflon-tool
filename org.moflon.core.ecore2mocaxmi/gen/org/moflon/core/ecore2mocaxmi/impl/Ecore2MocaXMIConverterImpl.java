/**
 */
package org.moflon.core.ecore2mocaxmi.impl;

import MocaTree.Attribute;
import MocaTree.MocaTreeFactory;
import MocaTree.Node;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.moflon.core.ecore2mocaxmi.Ecore2MocaXMIConverter;
import org.moflon.core.ecore2mocaxmi.Ecore2mocaxmiPackage;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ecore2 Moca XMI Converter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class Ecore2MocaXMIConverterImpl extends Ecore2MocaXMIConverterHelperImpl implements Ecore2MocaXMIConverter
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected Ecore2MocaXMIConverterImpl()
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
      return Ecore2mocaxmiPackage.Literals.ECORE2_MOCA_XMI_CONVERTER;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Node convert(EPackage rootPackage, String workingSetName, boolean export)
   {
      // createBasicStructure
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_0_1_createBasicStructure_blackBB(this, rootPackage);
      if (result1_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [createBasicStructure] failed." + " Variables: " + "[this] = " + this + ", " + "[rootPackage] = " + rootPackage + ".");
      }
      Object[] result1_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_0_1_createBasicStructure_greenFF();
      Node rootNode = (Node) result1_green[0];
      Node exportedTree = (Node) result1_green[1];

      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_0_2_expressionBBB(this, rootPackage, exportedTree);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_0_3_expressionBBBBB(this, rootPackage, workingSetName, exportedTree, export);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_0_4_expressionB(this);
      return Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_0_5_expressionFB(rootNode);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Node transformSubPackage(EPackage ePackage, int index, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_1_init_blackBB(this, ePackage);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[ePackage] = " + ePackage + ".");
      }

      // createEPackageNode
      Object[] result2_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_2_createEPackageNode_greenFFFFFFBB(index, ePackage);
      if (result2_green == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [createEPackageNode] failed." + " Variables: " + "[index] = " + index + ", " + "[ePackage] = " + ePackage + ".");
      }
      Node ePackageNode = (Node) result2_green[0];
      // Attribute name = (Attribute) result2_green[1];
      // Attribute nsPrefix = (Attribute) result2_green[2];
      // Attribute uri = (Attribute) result2_green[3];
      Node classes = (Node) result2_green[4];
      Node packages = (Node) result2_green[5];
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_3_expressionBBBB(this, ePackage, ePackageNode, path);
      // ForEach allSubPackages
      for (Object[] result4_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_4_allSubPackages_blackBF(ePackage))
      {
         EPackage subEPackage = (EPackage) result4_black[1];

         // createSubpackageNode
         Object[] result5_bindingAndBlack = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_bindingAndBlackFBBBB(this,
               subEPackage, packages, path);
         if (result5_bindingAndBlack == null)
         {
            throw new RuntimeException("Pattern matching in node [createSubpackageNode] failed." + " Variables: " + "[this] = " + this + ", "
                  + "[subEPackage] = " + subEPackage + ", " + "[packages] = " + packages + ", " + "[path] = " + path + ".");
         }
         Node subEPackageNode = (Node) result5_bindingAndBlack[0];

         // addSubpackage
         Object[] result6_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_6_addSubpackage_blackBB(packages, subEPackageNode);
         if (result6_black == null)
         {
            throw new RuntimeException("Pattern matching in node [addSubpackage] failed." + " Variables: " + "[packages] = " + packages + ", "
                  + "[subEPackageNode] = " + subEPackageNode + ".");
         }
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_6_addSubpackage_greenBB(packages, subEPackageNode);

      }
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_7_expressionBBBB(this, ePackage, classes, path);
      return Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_1_8_expressionFB(ePackageNode);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformOutermostPackage(EPackage rootEPackage, String workingSetName, Node preConvertedNode, Node target, boolean export)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_2_1_init_blackBBBB(this, target, preConvertedNode, rootEPackage);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[target] = " + target + ", "
               + "[preConvertedNode] = " + preConvertedNode + ", " + "[rootEPackage] = " + rootEPackage + ".");
      }
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_2_1_init_greenBB(target, preConvertedNode);

      // addMissingAttributes
      Object[] result2_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_2_2_addMissingAttributes_blackB(preConvertedNode);
      if (result2_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [addMissingAttributes] failed." + " Variables: " + "[preConvertedNode] = " + preConvertedNode + ".");
      }
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_2_2_addMissingAttributes_greenBFFFFBBBB(preConvertedNode, rootEPackage, workingSetName, this,
            export);
            // Attribute pluginID = (Attribute) result2_green[1];
            // Attribute validated = (Attribute) result2_green[2];
            // Attribute workingSetAttr = (Attribute) result2_green[3];
            // Attribute exportAttr = (Attribute) result2_green[4];

      // addingMissingNodes
      Object[] result3_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_2_3_addingMissingNodes_blackB(preConvertedNode);
      if (result3_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [addingMissingNodes] failed." + " Variables: " + "[preConvertedNode] = " + preConvertedNode + ".");
      }
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_2_3_addingMissingNodes_greenBF(preConvertedNode);
      // Node dependencies = (Node) result3_green[1];

      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformEClassifiers(EPackage ePackage, Node targetNode, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_3_1_init_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ".");
      }
      // ForEach allClasses
      for (Object[] result2_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_3_2_allClasses_blackBF(ePackage))
      {
         EClass eClass = (EClass) result2_black[1];
         // 
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_3_3_expressionBBBB(this, eClass, targetNode, path);

      }
      // ForEach allEDataTypes
      for (Object[] result4_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_3_4_allEDataTypes_blackBF(ePackage))
      {
         EDataType eDataType = (EDataType) result4_black[1];
         // 
         if (Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_3_5_expressionFBB(this, eDataType))
         {
            // 
            Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_3_6_expressionBBBB(this, eDataType, targetNode, path);

         } else
         {
            // 
            Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_3_7_expressionBBBB(this, eDataType, targetNode, path);

         }

      }
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformEClass(EClass eClass, Node targetNode, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_1_init_blackBB(this, eClass);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[eClass] = " + eClass + ".");
      }

      // createAndAddClassProperties
      Object[] result2_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_2_createAndAddClassProperties_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [createAndAddClassProperties] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result2_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_2_createAndAddClassProperties_greenFBFFFFFFFBB(targetNode, this,
            eClass);
      Node eClassNode = (Node) result2_green[0];
      // Attribute isAbstract = (Attribute) result2_green[2];
      // Attribute isInterface = (Attribute) result2_green[3];
      // Attribute alias = (Attribute) result2_green[4];
      Attribute baseClasses = (Attribute) result2_green[5];
      Node attributes = (Node) result2_green[6];
      Node references = (Node) result2_green[7];
      Node operations = (Node) result2_green[8];

      // ForEach superTypes
      for (Object[] result3_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_3_superTypes_blackBF(eClass))
      {
         EClass superType = (EClass) result3_black[1];
         // 
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_4_expressionBBB(this, superType, baseClasses);

      }
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_5_expressionBBB(this, eClass, eClassNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_6_expressionBBBB(this, eClass, eClassNode, path);
      // ForEach everyAttribute
      for (Object[] result7_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_7_everyAttribute_blackBF(eClass))
      {
         EAttribute eAttribute = (EAttribute) result7_black[1];
         // 
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_8_expressionBBBBB(this, eAttribute, attributes, path, eClass);

      }
      // ForEach everyReference
      for (Object[] result9_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_9_everyReference_blackBF(eClass))
      {
         EReference eReference = (EReference) result9_black[1];
         // 
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_10_expressionBBBBB(this, eReference, references, path, eClass);

      }
      // ForEach everyOperation
      for (Object[] result11_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_11_everyOperation_blackBF(eClass))
      {
         EOperation eOperation = (EOperation) result11_black[1];
         // 
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_4_12_expressionBBBBB(this, eOperation, operations, path, eClass);

      }
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Node convert(EPackage rootPackage)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_5_1_init_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ".");
      }
      return Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_5_2_expressionFBB(this, rootPackage);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformEAttribute(EAttribute eAttribute, Node targetNode, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_6_1_init_blackBBF(this, eAttribute);
      if (result1_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[eAttribute] = " + eAttribute + ".");
      }
      EClassifier type = (EClassifier) result1_black[2];

      // createAttributes
      Object[] result2_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_6_2_createAttributes_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [createAttributes] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result2_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_6_2_createAttributes_greenBFFFFFFBBB(targetNode, type, this,
            eAttribute);
      Node eAttributeNode = (Node) result2_green[1];
      // Attribute typeAttribute = (Attribute) result2_green[2];
      // Attribute ordered = (Attribute) result2_green[3];
      // Attribute lowerBound = (Attribute) result2_green[4];
      // Attribute upperBound = (Attribute) result2_green[5];
      // Attribute isId = (Attribute) result2_green[6];

      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_6_3_expressionBBB(this, eAttribute, eAttributeNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_6_4_expressionBBBB(this, eAttribute, eAttributeNode, path);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_6_5_expressionBBB(this, type, eAttributeNode);
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformEReference(EReference eReference, Node targetNode, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_7_1_init_blackBBF(this, eReference);
      if (result1_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[eReference] = " + eReference + ".");
      }
      EClassifier referenceType = (EClassifier) result1_black[2];

      // 
      Object[] result2_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_7_2_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result2_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_7_2_greenBFFFFFBB(targetNode, this, eReference);
      Node eReferenceNode = (Node) result2_green[1];
      // Attribute lowerBound = (Attribute) result2_green[2];
      // Attribute upperBound = (Attribute) result2_green[3];
      // Attribute containment = (Attribute) result2_green[4];
      Attribute oppositeGuid = (Attribute) result2_green[5];

      // getOpposite
      Object[] result3_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_7_3_getOpposite_blackBF(eReference);
      if (result3_black != null)
      {
         EReference opposite = (EReference) result3_black[1];
         // 
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_7_4_expressionBBB(this, opposite, oppositeGuid);

      } else
      {
      }
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_7_5_expressionBBB(this, referenceType, eReferenceNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_7_6_expressionBBB(this, eReference, eReferenceNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_7_7_expressionBBBB(this, eReference, eReferenceNode, path);
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformOperation(EOperation eOperation, Node targetNode, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_1_init_blackBB(this, eOperation);
      if (result1_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[eOperation] = " + eOperation + ".");
      }

      // getType
      Object[] result2_bindingAndBlack = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_2_getType_bindingAndBlackFBB(this, eOperation);
      if (result2_bindingAndBlack == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [getType] failed." + " Variables: " + "[this] = " + this + ", " + "[eOperation] = " + eOperation + ".");
      }
      EClassifier type = (EClassifier) result2_bindingAndBlack[0];

      // createEOperationNode
      Object[] result3_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_3_createEOperationNode_blackB(targetNode);
      if (result3_black == null)
      {
         throw new RuntimeException("Pattern matching in node [createEOperationNode] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result3_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_3_createEOperationNode_greenBFFFB(targetNode, type);
      Node eOperationNode = (Node) result3_green[1];
      // Attribute returnType = (Attribute) result3_green[2];
      Node parameters = (Node) result3_green[3];

      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_4_expressionBBB(this, eOperation, eOperationNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_5_expressionBBBB(this, eOperation, eOperationNode, path);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_6_expressionBBB(this, type, eOperationNode);
      // ForEach everyParameter
      for (Object[] result7_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_7_everyParameter_blackBF(eOperation))
      {
         EParameter eParameter = (EParameter) result7_black[1];
         // 
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_8_8_expressionBBBBB(this, eParameter, parameters, path, eOperation);

      }
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformEParameter(EParameter eParameter, Node targetNode, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_9_1_init_blackBBF(this, eParameter);
      if (result1_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[eParameter] = " + eParameter + ".");
      }
      EClassifier type = (EClassifier) result1_black[2];

      // createEParameterNode
      Object[] result2_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_9_2_createEParameterNode_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [createEParameterNode] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result2_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_9_2_createEParameterNode_greenBFFB(targetNode, type);
      Node eParameterNode = (Node) result2_green[1];
      // Attribute typeName = (Attribute) result2_green[2];

      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_9_3_expressionBBB(this, eParameter, eParameterNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_9_4_expressionBBB(this, type, eParameterNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_9_5_expressionBBBB(this, eParameter, eParameterNode, path);
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformEEnum(EEnum eEnum, Node targetNode, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_10_1_init_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ".");
      }

      // createEEnumNode
      Object[] result2_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_10_2_createEEnumNode_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [createEEnumNode] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result2_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_10_2_createEEnumNode_greenBFF(targetNode);
      Node eEnumNode = (Node) result2_green[1];
      Node literals = (Node) result2_green[2];

      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_10_3_expressionBBB(this, eEnum, eEnumNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_10_4_expressionBBBB(this, eEnum, eEnumNode, path);
      // ForEach everyEEnumLiteral
      for (Object[] result5_black : Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_10_5_everyEEnumLiteral_blackBF(eEnum))
      {
         EEnumLiteral eEnumLiteral = (EEnumLiteral) result5_black[1];
         // 
         Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_10_6_expressionBBB(this, eEnumLiteral, literals);

      }
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformEDataType(EDataType eDataType, Node targetNode, String path)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_11_1_init_blackBB(this, eDataType);
      if (result1_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[eDataType] = " + eDataType + ".");
      }

      // createEDataTypeNode
      Object[] result2_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_11_2_createEDataTypeNode_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [createEDataTypeNode] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result2_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_11_2_createEDataTypeNode_greenBFFB(targetNode, eDataType);
      Node eDataTypeNode = (Node) result2_green[1];
      // Attribute instanceTypeName = (Attribute) result2_green[2];

      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_11_3_expressionBBB(this, eDataType, eDataTypeNode);
      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_11_4_expressionBBBB(this, eDataType, eDataTypeNode, path);
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void transformEEnumLiteral(EEnumLiteral eEnumLiteral, Node targetNode)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_12_1_init_blackBB(this, eEnumLiteral);
      if (result1_black == null)
      {
         throw new RuntimeException(
               "Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ", " + "[eEnumLiteral] = " + eEnumLiteral + ".");
      }

      // createEEnumLiteralNode
      Object[] result2_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_12_2_createEEnumLiteralNode_blackB(targetNode);
      if (result2_black == null)
      {
         throw new RuntimeException("Pattern matching in node [createEEnumLiteralNode] failed." + " Variables: " + "[targetNode] = " + targetNode + ".");
      }
      Object[] result2_green = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_12_2_createEEnumLiteralNode_greenBFFFBB(targetNode, eEnumLiteral,
            this);
      Node eEnumLiteralNode = (Node) result2_green[1];
      // Attribute literal = (Attribute) result2_green[2];
      // Attribute value = (Attribute) result2_green[3];

      // 
      Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_12_3_expressionBBB(this, eEnumLiteral, eEnumLiteralNode);
      return;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Node convert(EPackage rootPackage, String workingSetName)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_13_1_init_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ".");
      }
      return Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_13_2_expressionFBBB(this, rootPackage, workingSetName);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Node convert(EPackage rootPackage, boolean export)
   {
      // init
      Object[] result1_black = Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_14_1_init_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [init] failed." + " Variables: " + "[this] = " + this + ".");
      }
      return Ecore2MocaXMIConverterImpl.pattern_Ecore2MocaXMIConverter_14_2_expressionFBBB(this, rootPackage, export);
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
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_STRING_BOOLEAN:
         return convert((EPackage) arguments.get(0), (String) arguments.get(1), (Boolean) arguments.get(2));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_SUB_PACKAGE__EPACKAGE_INT_STRING:
         return transformSubPackage((EPackage) arguments.get(0), (Integer) arguments.get(1), (String) arguments.get(2));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_OUTERMOST_PACKAGE__EPACKAGE_STRING_NODE_NODE_BOOLEAN:
         transformOutermostPackage((EPackage) arguments.get(0), (String) arguments.get(1), (Node) arguments.get(2), (Node) arguments.get(3),
               (Boolean) arguments.get(4));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_ECLASSIFIERS__EPACKAGE_NODE_STRING:
         transformEClassifiers((EPackage) arguments.get(0), (Node) arguments.get(1), (String) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_ECLASS__ECLASS_NODE_STRING:
         transformEClass((EClass) arguments.get(0), (Node) arguments.get(1), (String) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE:
         return convert((EPackage) arguments.get(0));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EATTRIBUTE__EATTRIBUTE_NODE_STRING:
         transformEAttribute((EAttribute) arguments.get(0), (Node) arguments.get(1), (String) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EREFERENCE__EREFERENCE_NODE_STRING:
         transformEReference((EReference) arguments.get(0), (Node) arguments.get(1), (String) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_OPERATION__EOPERATION_NODE_STRING:
         transformOperation((EOperation) arguments.get(0), (Node) arguments.get(1), (String) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EPARAMETER__EPARAMETER_NODE_STRING:
         transformEParameter((EParameter) arguments.get(0), (Node) arguments.get(1), (String) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EENUM__EENUM_NODE_STRING:
         transformEEnum((EEnum) arguments.get(0), (Node) arguments.get(1), (String) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EDATA_TYPE__EDATATYPE_NODE_STRING:
         transformEDataType((EDataType) arguments.get(0), (Node) arguments.get(1), (String) arguments.get(2));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___TRANSFORM_EENUM_LITERAL__EENUMLITERAL_NODE:
         transformEEnumLiteral((EEnumLiteral) arguments.get(0), (Node) arguments.get(1));
         return null;
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_STRING:
         return convert((EPackage) arguments.get(0), (String) arguments.get(1));
      case Ecore2mocaxmiPackage.ECORE2_MOCA_XMI_CONVERTER___CONVERT__EPACKAGE_BOOLEAN:
         return convert((EPackage) arguments.get(0), (Boolean) arguments.get(1));
      }
      return super.eInvoke(operationID, arguments);
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_0_1_createBasicStructure_blackBB(Ecore2MocaXMIConverter _this, EPackage rootPackage)
   {
      return new Object[] { _this, rootPackage };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_0_1_createBasicStructure_greenFF()
   {
      Node rootNode = MocaTreeFactory.eINSTANCE.createNode();
      Node exportedTree = MocaTreeFactory.eINSTANCE.createNode();
      String exportedTree_name_prime = "exportedTree";
      exportedTree.setParentNode(rootNode);
      exportedTree.setName(exportedTree_name_prime);
      return new Object[] { rootNode, exportedTree };
   }

   public static final void pattern_Ecore2MocaXMIConverter_0_2_expressionBBB(Ecore2MocaXMIConverter _this, EPackage rootPackage, Node exportedTree)
   {
      String rootPackage_name = rootPackage.getName();
      _this.init(rootPackage_name, exportedTree);

   }

   public static final void pattern_Ecore2MocaXMIConverter_0_3_expressionBBBBB(Ecore2MocaXMIConverter _this, EPackage rootPackage, String workingSetName,
         Node exportedTree, boolean export)
   {
      int _localVariable_1 = _this.getChildrenSize(exportedTree);
      Node _localVariable_0 = _this.transformSubPackage(rootPackage, Integer.valueOf(_localVariable_1), "root");
      _this.transformOutermostPackage(rootPackage, workingSetName, _localVariable_0, exportedTree, Boolean.valueOf(export));

   }

   public static final void pattern_Ecore2MocaXMIConverter_0_4_expressionB(Ecore2MocaXMIConverter _this)
   {
      _this.resolve();

   }

   public static final Node pattern_Ecore2MocaXMIConverter_0_5_expressionFB(Node rootNode)
   {
      Node _result = rootNode;
      return _result;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_1_1_init_blackBB(Ecore2MocaXMIConverter _this, EPackage ePackage)
   {
      return new Object[] { _this, ePackage };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_1_2_createEPackageNode_greenFFFFFFBB(int index, EPackage ePackage)
   {
      Node ePackageNode = MocaTreeFactory.eINSTANCE.createNode();
      Attribute name = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute nsPrefix = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute uri = MocaTreeFactory.eINSTANCE.createAttribute();
      Node classes = MocaTreeFactory.eINSTANCE.createNode();
      Node packages = MocaTreeFactory.eINSTANCE.createNode();
      String ePackageNode_name_prime = "EPackage";
      int ePackageNode_index_prime = Integer.valueOf(index);
      String name_name_prime = "Moflon::Name";
      int name_index_prime = Integer.valueOf(1);
      String nsPrefix_name_prime = "Moflon::NsPrefix";
      int nsPrefix_index_prime = Integer.valueOf(2);
      String uri_name_prime = "Moflon::NsUri";
      int uri_index_prime = Integer.valueOf(3);
      String classes_name_prime = "classes";
      String packages_name_prime = "packages";
      ePackageNode.getAttribute().add(name);
      ePackageNode.getAttribute().add(nsPrefix);
      ePackageNode.getAttribute().add(uri);
      classes.setParentNode(ePackageNode);
      packages.setParentNode(ePackageNode);
      ePackageNode.setName(ePackageNode_name_prime);
      ePackageNode.setIndex(Integer.valueOf(ePackageNode_index_prime));
      name.setName(name_name_prime);
      name.setIndex(Integer.valueOf(name_index_prime));
      nsPrefix.setName(nsPrefix_name_prime);
      nsPrefix.setIndex(Integer.valueOf(nsPrefix_index_prime));
      uri.setName(uri_name_prime);
      uri.setIndex(Integer.valueOf(uri_index_prime));
      classes.setName(classes_name_prime);
      packages.setName(packages_name_prime);
      String ePackage_name = ePackage.getName();
      String name_value_prime = ePackage_name;
      name.setValue(name_value_prime);
      String ePackage_nsPrefix = ePackage.getNsPrefix();
      String nsPrefix_value_prime = ePackage_nsPrefix;
      nsPrefix.setValue(nsPrefix_value_prime);
      String ePackage_nsURI = ePackage.getNsURI();
      String uri_value_prime = ePackage_nsURI;
      uri.setValue(uri_value_prime);
      return new Object[] { ePackageNode, name, nsPrefix, uri, classes, packages, index, ePackage };

   }

   public static final void pattern_Ecore2MocaXMIConverter_1_3_expressionBBBB(Ecore2MocaXMIConverter _this, EPackage ePackage, Node ePackageNode, String path)
   {
      String _localVariable_0 = _this.addToPath(path, ePackage);
      _this.createGuidAttribute(ePackage, ePackageNode, Integer.valueOf(0), _localVariable_0);

   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_1_4_allSubPackages_blackBF(EPackage ePackage)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EPackage subEPackage : ePackage.getESubpackages())
      {
         if (!ePackage.equals(subEPackage))
         {
            _result.add(new Object[] { ePackage, subEPackage });
         }
      }
      return _result;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_bindingFBBBB(Ecore2MocaXMIConverter _this, EPackage subEPackage,
         Node packages, String path)
   {
      int _localVariable_1 = _this.getChildrenSize(packages);
      String _localVariable_2 = _this.addToPath(path, subEPackage);
      Node _localVariable_0 = _this.transformSubPackage(subEPackage, Integer.valueOf(_localVariable_1), _localVariable_2);
      Node subEPackageNode = _localVariable_0;
      if (subEPackageNode != null)
      {
         return new Object[] { subEPackageNode, _this, subEPackage, packages, path };
      }
      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_blackB(Node subEPackageNode)
   {
      return new Object[] { subEPackageNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_bindingAndBlackFBBBB(Ecore2MocaXMIConverter _this, EPackage subEPackage,
         Node packages, String path)
   {
      Object[] result_pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_binding = pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_bindingFBBBB(
            _this, subEPackage, packages, path);
      if (result_pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_binding != null)
      {
         Node subEPackageNode = (Node) result_pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_binding[0];

         Object[] result_pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_black = pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_blackB(
               subEPackageNode);
         if (result_pattern_Ecore2MocaXMIConverter_1_5_createSubpackageNode_black != null)
         {

            return new Object[] { subEPackageNode, _this, subEPackage, packages, path };
         }
      }
      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_1_6_addSubpackage_blackBB(Node packages, Node subEPackageNode)
   {
      if (!packages.equals(subEPackageNode))
      {
         return new Object[] { packages, subEPackageNode };
      }
      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_1_6_addSubpackage_greenBB(Node packages, Node subEPackageNode)
   {
      subEPackageNode.setParentNode(packages);
      return new Object[] { packages, subEPackageNode };
   }

   public static final void pattern_Ecore2MocaXMIConverter_1_7_expressionBBBB(Ecore2MocaXMIConverter _this, EPackage ePackage, Node classes, String path)
   {
      String _localVariable_0 = _this.addToPath(path, ePackage);
      _this.transformEClassifiers(ePackage, classes, _localVariable_0);

   }

   public static final Node pattern_Ecore2MocaXMIConverter_1_8_expressionFB(Node ePackageNode)
   {
      Node _result = ePackageNode;
      return _result;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_2_1_init_blackBBBB(Ecore2MocaXMIConverter _this, Node target, Node preConvertedNode,
         EPackage rootEPackage)
   {
      if (!preConvertedNode.equals(target))
      {
         return new Object[] { _this, target, preConvertedNode, rootEPackage };
      }
      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_2_1_init_greenBB(Node target, Node preConvertedNode)
   {
      preConvertedNode.setParentNode(target);
      return new Object[] { target, preConvertedNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_2_2_addMissingAttributes_blackB(Node preConvertedNode)
   {
      return new Object[] { preConvertedNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_2_2_addMissingAttributes_greenBFFFFBBBB(Node preConvertedNode, EPackage rootEPackage,
         String workingSetName, Ecore2MocaXMIConverter _this, boolean export)
   {
      Attribute pluginID = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute validated = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute workingSetAttr = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute exportAttr = MocaTreeFactory.eINSTANCE.createAttribute();
      String pluginID_name_prime = "Moflon::PluginID";
      String validated_name_prime = "Moflon::Validated";
      String validated_value_prime = "false";
      String workingSetAttr_name_prime = "Moflon::WorkingSet";
      String workingSetAttr_value_prime = workingSetName;
      String exportAttr_name_prime = "Moflon::Export";
      String _localVariable_0 = _this.booleanToString(Boolean.valueOf(export));
      preConvertedNode.getAttribute().add(pluginID);
      preConvertedNode.getAttribute().add(validated);
      preConvertedNode.getAttribute().add(workingSetAttr);
      preConvertedNode.getAttribute().add(exportAttr);
      pluginID.setName(pluginID_name_prime);
      validated.setName(validated_name_prime);
      validated.setValue(validated_value_prime);
      workingSetAttr.setName(workingSetAttr_name_prime);
      workingSetAttr.setValue(workingSetAttr_value_prime);
      exportAttr.setName(exportAttr_name_prime);
      String exportAttr_value_prime = _localVariable_0;
      exportAttr.setValue(exportAttr_value_prime);
      String rootEPackage_name = rootEPackage.getName();
      String pluginID_value_prime = rootEPackage_name;
      pluginID.setValue(pluginID_value_prime);
      return new Object[] { preConvertedNode, pluginID, validated, workingSetAttr, exportAttr, rootEPackage, workingSetName, _this, export };

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_2_3_addingMissingNodes_blackB(Node preConvertedNode)
   {
      return new Object[] { preConvertedNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_2_3_addingMissingNodes_greenBF(Node preConvertedNode)
   {
      Node dependencies = MocaTreeFactory.eINSTANCE.createNode();
      String dependencies_name_prime = "dependencies";
      dependencies.setParentNode(preConvertedNode);
      dependencies.setName(dependencies_name_prime);
      return new Object[] { preConvertedNode, dependencies };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_3_1_init_blackB(Ecore2MocaXMIConverter _this)
   {
      return new Object[] { _this };
   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_3_2_allClasses_blackBF(EPackage ePackage)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EClassifier tmpEClass : ePackage.getEClassifiers())
      {
         if (tmpEClass instanceof EClass)
         {
            EClass eClass = (EClass) tmpEClass;
            _result.add(new Object[] { ePackage, eClass });
         }
      }
      return _result;
   }

   public static final void pattern_Ecore2MocaXMIConverter_3_3_expressionBBBB(Ecore2MocaXMIConverter _this, EClass eClass, Node targetNode, String path)
   {
      _this.transformEClass(eClass, targetNode, path);

   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_3_4_allEDataTypes_blackBF(EPackage ePackage)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EClassifier tmpEDataType : ePackage.getEClassifiers())
      {
         if (tmpEDataType instanceof EDataType)
         {
            EDataType eDataType = (EDataType) tmpEDataType;
            _result.add(new Object[] { ePackage, eDataType });
         }
      }
      return _result;
   }

   public static final boolean pattern_Ecore2MocaXMIConverter_3_5_expressionFBB(Ecore2MocaXMIConverter _this, EDataType eDataType)
   {
      boolean _localVariable_0 = _this.instanceofEEnum(eDataType);
      boolean _result = Boolean.valueOf(_localVariable_0);
      return _result;
   }

   public static final void pattern_Ecore2MocaXMIConverter_3_6_expressionBBBB(Ecore2MocaXMIConverter _this, EDataType eDataType, Node targetNode, String path)
   {
      EEnum _localVariable_0 = _this.castEEnum(eDataType);
      _this.transformEEnum(_localVariable_0, targetNode, path);

   }

   public static final void pattern_Ecore2MocaXMIConverter_3_7_expressionBBBB(Ecore2MocaXMIConverter _this, EDataType eDataType, Node targetNode, String path)
   {
      _this.transformEDataType(eDataType, targetNode, path);

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_4_1_init_blackBB(Ecore2MocaXMIConverter _this, EClass eClass)
   {
      return new Object[] { _this, eClass };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_4_2_createAndAddClassProperties_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_4_2_createAndAddClassProperties_greenFBFFFFFFFBB(Node targetNode, Ecore2MocaXMIConverter _this,
         EClass eClass)
   {
      Node eClassNode = MocaTreeFactory.eINSTANCE.createNode();
      Attribute isAbstract = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute isInterface = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute alias = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute baseClasses = MocaTreeFactory.eINSTANCE.createAttribute();
      Node attributes = MocaTreeFactory.eINSTANCE.createNode();
      Node references = MocaTreeFactory.eINSTANCE.createNode();
      Node operations = MocaTreeFactory.eINSTANCE.createNode();
      String eClassNode_name_prime = "EClass";
      int _localVariable_0 = _this.getChildrenSize(targetNode);
      String isAbstract_name_prime = "isAbstract";
      int isAbstract_index_prime = Integer.valueOf(1);
      String isInterface_name_prime = "isInterface";
      int isInterface_index_prime = Integer.valueOf(2);
      String alias_name_prime = "alias";
      int alias_index_prime = Integer.valueOf(4);
      String alias_value_prime = "";
      String baseClasses_name_prime = "baseClasses";
      int baseClasses_index_prime = Integer.valueOf(5);
      String baseClasses_value_prime = "";
      String attributes_name_prime = "attributes";
      int attributes_index_prime = Integer.valueOf(1);
      String references_name_prime = "references";
      int references_index_prime = Integer.valueOf(0);
      String operations_name_prime = "operations";
      int operations_index_prime = Integer.valueOf(2);
      eClassNode.setParentNode(targetNode);
      eClassNode.getAttribute().add(isAbstract);
      eClassNode.getAttribute().add(isInterface);
      eClassNode.getAttribute().add(alias);
      eClassNode.getAttribute().add(baseClasses);
      attributes.setParentNode(eClassNode);
      references.setParentNode(eClassNode);
      operations.setParentNode(eClassNode);
      eClassNode.setName(eClassNode_name_prime);
      int eClassNode_index_prime = Integer.valueOf(_localVariable_0);
      isAbstract.setName(isAbstract_name_prime);
      isAbstract.setIndex(Integer.valueOf(isAbstract_index_prime));
      isInterface.setName(isInterface_name_prime);
      isInterface.setIndex(Integer.valueOf(isInterface_index_prime));
      alias.setName(alias_name_prime);
      alias.setIndex(Integer.valueOf(alias_index_prime));
      alias.setValue(alias_value_prime);
      baseClasses.setName(baseClasses_name_prime);
      baseClasses.setIndex(Integer.valueOf(baseClasses_index_prime));
      baseClasses.setValue(baseClasses_value_prime);
      attributes.setName(attributes_name_prime);
      attributes.setIndex(Integer.valueOf(attributes_index_prime));
      references.setName(references_name_prime);
      references.setIndex(Integer.valueOf(references_index_prime));
      operations.setName(operations_name_prime);
      operations.setIndex(Integer.valueOf(operations_index_prime));
      eClassNode.setIndex(Integer.valueOf(eClassNode_index_prime));
      boolean eClass_abstract = eClass.isAbstract();
      String _localVariable_1 = _this.booleanToString(Boolean.valueOf(eClass_abstract));
      String isAbstract_value_prime = _localVariable_1;
      isAbstract.setValue(isAbstract_value_prime);
      boolean eClass_interface = eClass.isInterface();
      String _localVariable_2 = _this.booleanToString(Boolean.valueOf(eClass_interface));
      String isInterface_value_prime = _localVariable_2;
      isInterface.setValue(isInterface_value_prime);
      return new Object[] { eClassNode, targetNode, isAbstract, isInterface, alias, baseClasses, attributes, references, operations, _this, eClass };

   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_4_3_superTypes_blackBF(EClass eClass)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EClass superType : eClass.getESuperTypes())
      {
         if (!eClass.equals(superType))
         {
            _result.add(new Object[] { eClass, superType });
         }
      }
      return _result;
   }

   public static final void pattern_Ecore2MocaXMIConverter_4_4_expressionBBB(Ecore2MocaXMIConverter _this, EClass superType, Attribute baseClasses)
   {
      _this.addSearchElement(superType, baseClasses);

   }

   public static final void pattern_Ecore2MocaXMIConverter_4_5_expressionBBB(Ecore2MocaXMIConverter _this, EClass eClass, Node eClassNode)
   {
      _this.createNameAttribute(eClass, eClassNode, Integer.valueOf(0));

   }

   public static final void pattern_Ecore2MocaXMIConverter_4_6_expressionBBBB(Ecore2MocaXMIConverter _this, EClass eClass, Node eClassNode, String path)
   {
      String _localVariable_0 = _this.addToPath(path, eClass);
      _this.createGuidAttribute(eClass, eClassNode, Integer.valueOf(3), _localVariable_0);

   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_4_7_everyAttribute_blackBF(EClass eClass)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EStructuralFeature tmpEAttribute : eClass.getEStructuralFeatures())
      {
         if (tmpEAttribute instanceof EAttribute)
         {
            EAttribute eAttribute = (EAttribute) tmpEAttribute;
            _result.add(new Object[] { eClass, eAttribute });
         }
      }
      return _result;
   }

   public static final void pattern_Ecore2MocaXMIConverter_4_8_expressionBBBBB(Ecore2MocaXMIConverter _this, EAttribute eAttribute, Node attributes,
         String path, EClass eClass)
   {
      String _localVariable_0 = _this.addToPath(path, eClass);
      _this.transformEAttribute(eAttribute, attributes, _localVariable_0);

   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_4_9_everyReference_blackBF(EClass eClass)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EStructuralFeature tmpEReference : eClass.getEStructuralFeatures())
      {
         if (tmpEReference instanceof EReference)
         {
            EReference eReference = (EReference) tmpEReference;
            _result.add(new Object[] { eClass, eReference });
         }
      }
      return _result;
   }

   public static final void pattern_Ecore2MocaXMIConverter_4_10_expressionBBBBB(Ecore2MocaXMIConverter _this, EReference eReference, Node references,
         String path, EClass eClass)
   {
      String _localVariable_0 = _this.addToPath(path, eClass);
      _this.transformEReference(eReference, references, _localVariable_0);

   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_4_11_everyOperation_blackBF(EClass eClass)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EOperation eOperation : eClass.getEOperations())
      {
         _result.add(new Object[] { eClass, eOperation });
      }
      return _result;
   }

   public static final void pattern_Ecore2MocaXMIConverter_4_12_expressionBBBBB(Ecore2MocaXMIConverter _this, EOperation eOperation, Node operations,
         String path, EClass eClass)
   {
      String _localVariable_0 = _this.addToPath(path, eClass);
      _this.transformOperation(eOperation, operations, _localVariable_0);

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_5_1_init_blackB(Ecore2MocaXMIConverter _this)
   {
      return new Object[] { _this };
   }

   public static final Node pattern_Ecore2MocaXMIConverter_5_2_expressionFBB(Ecore2MocaXMIConverter _this, EPackage rootPackage)
   {
      Node _localVariable_0 = _this.convert(rootPackage, "WorkingSet");
      Node _result = _localVariable_0;
      return _result;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_6_1_init_blackBBF(Ecore2MocaXMIConverter _this, EAttribute eAttribute)
   {
      EClassifier type = eAttribute.getEType();
      if (type != null)
      {
         return new Object[] { _this, eAttribute, type };
      }

      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_6_2_createAttributes_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_6_2_createAttributes_greenBFFFFFFBBB(Node targetNode, EClassifier type,
         Ecore2MocaXMIConverter _this, EAttribute eAttribute)
   {
      Node eAttributeNode = MocaTreeFactory.eINSTANCE.createNode();
      Attribute typeAttribute = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute ordered = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute lowerBound = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute upperBound = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute isId = MocaTreeFactory.eINSTANCE.createAttribute();
      String eAttributeNode_name_prime = "EAttribute";
      String typeAttribute_name_prime = "type";
      int typeAttribute_index_prime = Integer.valueOf(0);
      String ordered_name_prime = "ordered";
      int ordered_index_prime = Integer.valueOf(4);
      String lowerBound_name_prime = "lowerBound";
      int lowerBound_index_prime = Integer.valueOf(5);
      String upperBound_name_prime = "upperBound";
      int upperBound_index_prime = Integer.valueOf(6);
      String isId_name_prime = "isId";
      int isId_index_prime = Integer.valueOf(7);
      eAttributeNode.setParentNode(targetNode);
      eAttributeNode.getAttribute().add(typeAttribute);
      eAttributeNode.getAttribute().add(ordered);
      eAttributeNode.getAttribute().add(lowerBound);
      eAttributeNode.getAttribute().add(upperBound);
      eAttributeNode.getAttribute().add(isId);
      eAttributeNode.setName(eAttributeNode_name_prime);
      typeAttribute.setName(typeAttribute_name_prime);
      typeAttribute.setIndex(Integer.valueOf(typeAttribute_index_prime));
      ordered.setName(ordered_name_prime);
      ordered.setIndex(Integer.valueOf(ordered_index_prime));
      lowerBound.setName(lowerBound_name_prime);
      lowerBound.setIndex(Integer.valueOf(lowerBound_index_prime));
      upperBound.setName(upperBound_name_prime);
      upperBound.setIndex(Integer.valueOf(upperBound_index_prime));
      isId.setName(isId_name_prime);
      isId.setIndex(Integer.valueOf(isId_index_prime));
      String type_name = type.getName();
      String typeAttribute_value_prime = type_name;
      typeAttribute.setValue(typeAttribute_value_prime);
      boolean eAttribute_ordered = eAttribute.isOrdered();
      String _localVariable_0 = _this.booleanToString(Boolean.valueOf(eAttribute_ordered));
      String ordered_value_prime = _localVariable_0;
      ordered.setValue(ordered_value_prime);
      int eAttribute_lowerBound = eAttribute.getLowerBound();
      String _localVariable_1 = _this.intToString(Integer.valueOf(eAttribute_lowerBound));
      String lowerBound_value_prime = _localVariable_1;
      lowerBound.setValue(lowerBound_value_prime);
      int eAttribute_upperBound = eAttribute.getUpperBound();
      String _localVariable_2 = _this.intToString(Integer.valueOf(eAttribute_upperBound));
      String upperBound_value_prime = _localVariable_2;
      upperBound.setValue(upperBound_value_prime);
      boolean eAttribute_iD = eAttribute.isID();
      String _localVariable_3 = _this.booleanToString(Boolean.valueOf(eAttribute_iD));
      String isId_value_prime = _localVariable_3;
      isId.setValue(isId_value_prime);
      return new Object[] { targetNode, eAttributeNode, typeAttribute, ordered, lowerBound, upperBound, isId, type, _this, eAttribute };

   }

   public static final void pattern_Ecore2MocaXMIConverter_6_3_expressionBBB(Ecore2MocaXMIConverter _this, EAttribute eAttribute, Node eAttributeNode)
   {
      _this.createNameAttribute(eAttribute, eAttributeNode, Integer.valueOf(2));

   }

   public static final void pattern_Ecore2MocaXMIConverter_6_4_expressionBBBB(Ecore2MocaXMIConverter _this, EAttribute eAttribute, Node eAttributeNode,
         String path)
   {
      String _localVariable_0 = _this.addToPath(path, eAttribute);
      _this.createGuidAttribute(eAttribute, eAttributeNode, Integer.valueOf(3), _localVariable_0);

   }

   public static final void pattern_Ecore2MocaXMIConverter_6_5_expressionBBB(Ecore2MocaXMIConverter _this, EClassifier type, Node eAttributeNode)
   {
      _this.createTypeGuidAttribute(type, eAttributeNode, Integer.valueOf(1));

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_7_1_init_blackBBF(Ecore2MocaXMIConverter _this, EReference eReference)
   {
      EClassifier referenceType = eReference.getEType();
      if (referenceType != null)
      {
         return new Object[] { _this, eReference, referenceType };
      }

      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_7_2_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_7_2_greenBFFFFFBB(Node targetNode, Ecore2MocaXMIConverter _this, EReference eReference)
   {
      Node eReferenceNode = MocaTreeFactory.eINSTANCE.createNode();
      Attribute lowerBound = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute upperBound = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute containment = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute oppositeGuid = MocaTreeFactory.eINSTANCE.createAttribute();
      String eReferenceNode_name_prime = "EReference";
      String lowerBound_name_prime = "lowerBound";
      int lowerBound_index_prime = Integer.valueOf(3);
      String upperBound_name_prime = "upperBound";
      int upperBound_index_prime = Integer.valueOf(4);
      String containment_name_prime = "containment";
      int containment_index_prime = Integer.valueOf(5);
      String oppositeGuid_name_prime = "oppositeGuid";
      int oppositeGuid_index_prime = Integer.valueOf(6);
      eReferenceNode.setParentNode(targetNode);
      eReferenceNode.getAttribute().add(lowerBound);
      eReferenceNode.getAttribute().add(upperBound);
      eReferenceNode.getAttribute().add(containment);
      eReferenceNode.getAttribute().add(oppositeGuid);
      eReferenceNode.setName(eReferenceNode_name_prime);
      lowerBound.setName(lowerBound_name_prime);
      lowerBound.setIndex(Integer.valueOf(lowerBound_index_prime));
      upperBound.setName(upperBound_name_prime);
      upperBound.setIndex(Integer.valueOf(upperBound_index_prime));
      containment.setName(containment_name_prime);
      containment.setIndex(Integer.valueOf(containment_index_prime));
      oppositeGuid.setName(oppositeGuid_name_prime);
      oppositeGuid.setIndex(Integer.valueOf(oppositeGuid_index_prime));
      int eReference_lowerBound = eReference.getLowerBound();
      String _localVariable_0 = _this.intToString(Integer.valueOf(eReference_lowerBound));
      String lowerBound_value_prime = _localVariable_0;
      lowerBound.setValue(lowerBound_value_prime);
      int eReference_upperBound = eReference.getUpperBound();
      String _localVariable_1 = _this.intToString(Integer.valueOf(eReference_upperBound));
      String upperBound_value_prime = _localVariable_1;
      upperBound.setValue(upperBound_value_prime);
      boolean eReference_containment = eReference.isContainment();
      String _localVariable_2 = _this.booleanToString(Boolean.valueOf(eReference_containment));
      String containment_value_prime = _localVariable_2;
      containment.setValue(containment_value_prime);
      return new Object[] { targetNode, eReferenceNode, lowerBound, upperBound, containment, oppositeGuid, _this, eReference };

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_7_3_getOpposite_blackBF(EReference eReference)
   {
      EReference opposite = eReference.getEOpposite();
      if (opposite != null)
      {
         if (!eReference.equals(opposite))
         {
            return new Object[] { eReference, opposite };
         }
      }

      return null;
   }

   public static final void pattern_Ecore2MocaXMIConverter_7_4_expressionBBB(Ecore2MocaXMIConverter _this, EReference opposite, Attribute oppositeGuid)
   {
      _this.addSearchElement(opposite, oppositeGuid);

   }

   public static final void pattern_Ecore2MocaXMIConverter_7_5_expressionBBB(Ecore2MocaXMIConverter _this, EClassifier referenceType, Node eReferenceNode)
   {
      _this.createTypeGuidAttribute(referenceType, eReferenceNode, Integer.valueOf(0));

   }

   public static final void pattern_Ecore2MocaXMIConverter_7_6_expressionBBB(Ecore2MocaXMIConverter _this, EReference eReference, Node eReferenceNode)
   {
      _this.createNameAttribute(eReference, eReferenceNode, Integer.valueOf(1));

   }

   public static final void pattern_Ecore2MocaXMIConverter_7_7_expressionBBBB(Ecore2MocaXMIConverter _this, EReference eReference, Node eReferenceNode,
         String path)
   {
      String _localVariable_0 = _this.addToPath(path, eReference);
      _this.createGuidAttribute(eReference, eReferenceNode, Integer.valueOf(2), _localVariable_0);

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_8_1_init_blackBB(Ecore2MocaXMIConverter _this, EOperation eOperation)
   {
      return new Object[] { _this, eOperation };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_8_2_getType_bindingFBB(Ecore2MocaXMIConverter _this, EOperation eOperation)
   {
      EClassifier _localVariable_0 = _this.getType(eOperation);
      EClassifier type = _localVariable_0;
      if (type != null)
      {
         return new Object[] { type, _this, eOperation };
      }
      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_8_2_getType_blackB(EClassifier type)
   {
      return new Object[] { type };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_8_2_getType_bindingAndBlackFBB(Ecore2MocaXMIConverter _this, EOperation eOperation)
   {
      Object[] result_pattern_Ecore2MocaXMIConverter_8_2_getType_binding = pattern_Ecore2MocaXMIConverter_8_2_getType_bindingFBB(_this, eOperation);
      if (result_pattern_Ecore2MocaXMIConverter_8_2_getType_binding != null)
      {
         EClassifier type = (EClassifier) result_pattern_Ecore2MocaXMIConverter_8_2_getType_binding[0];

         Object[] result_pattern_Ecore2MocaXMIConverter_8_2_getType_black = pattern_Ecore2MocaXMIConverter_8_2_getType_blackB(type);
         if (result_pattern_Ecore2MocaXMIConverter_8_2_getType_black != null)
         {

            return new Object[] { type, _this, eOperation };
         }
      }
      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_8_3_createEOperationNode_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_8_3_createEOperationNode_greenBFFFB(Node targetNode, EClassifier type)
   {
      Node eOperationNode = MocaTreeFactory.eINSTANCE.createNode();
      Attribute returnType = MocaTreeFactory.eINSTANCE.createAttribute();
      Node parameters = MocaTreeFactory.eINSTANCE.createNode();
      String eOperationNode_name_prime = "EOperation";
      String returnType_name_prime = "returnType";
      int returnType_index_prime = Integer.valueOf(3);
      String parameters_name_prime = "parameters";
      int parameters_index_prime = Integer.valueOf(0);
      eOperationNode.setParentNode(targetNode);
      eOperationNode.getAttribute().add(returnType);
      parameters.setParentNode(eOperationNode);
      eOperationNode.setName(eOperationNode_name_prime);
      returnType.setName(returnType_name_prime);
      returnType.setIndex(Integer.valueOf(returnType_index_prime));
      parameters.setName(parameters_name_prime);
      parameters.setIndex(Integer.valueOf(parameters_index_prime));
      String type_name = type.getName();
      String returnType_value_prime = type_name;
      returnType.setValue(returnType_value_prime);
      return new Object[] { targetNode, eOperationNode, returnType, parameters, type };

   }

   public static final void pattern_Ecore2MocaXMIConverter_8_4_expressionBBB(Ecore2MocaXMIConverter _this, EOperation eOperation, Node eOperationNode)
   {
      _this.createNameAttribute(eOperation, eOperationNode, Integer.valueOf(0));

   }

   public static final void pattern_Ecore2MocaXMIConverter_8_5_expressionBBBB(Ecore2MocaXMIConverter _this, EOperation eOperation, Node eOperationNode,
         String path)
   {
      String _localVariable_0 = _this.addToPath(path, eOperation);
      _this.createGuidAttribute(eOperation, eOperationNode, Integer.valueOf(1), _localVariable_0);

   }

   public static final void pattern_Ecore2MocaXMIConverter_8_6_expressionBBB(Ecore2MocaXMIConverter _this, EClassifier type, Node eOperationNode)
   {
      _this.createTypeGuidAttribute(type, eOperationNode, Integer.valueOf(2));

   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_8_7_everyParameter_blackBF(EOperation eOperation)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EParameter eParameter : eOperation.getEParameters())
      {
         _result.add(new Object[] { eOperation, eParameter });
      }
      return _result;
   }

   public static final void pattern_Ecore2MocaXMIConverter_8_8_expressionBBBBB(Ecore2MocaXMIConverter _this, EParameter eParameter, Node parameters,
         String path, EOperation eOperation)
   {
      String _localVariable_0 = _this.addToPath(path, eOperation);
      _this.transformEParameter(eParameter, parameters, _localVariable_0);

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_9_1_init_blackBBF(Ecore2MocaXMIConverter _this, EParameter eParameter)
   {
      EClassifier type = eParameter.getEType();
      if (type != null)
      {
         return new Object[] { _this, eParameter, type };
      }

      return null;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_9_2_createEParameterNode_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_9_2_createEParameterNode_greenBFFB(Node targetNode, EClassifier type)
   {
      Node eParameterNode = MocaTreeFactory.eINSTANCE.createNode();
      Attribute typeName = MocaTreeFactory.eINSTANCE.createAttribute();
      String eParameterNode_name_prime = "EParameter";
      String typeName_name_prime = "type";
      int typeName_index_prime = Integer.valueOf(0);
      eParameterNode.setParentNode(targetNode);
      eParameterNode.getAttribute().add(typeName);
      eParameterNode.setName(eParameterNode_name_prime);
      typeName.setName(typeName_name_prime);
      typeName.setIndex(Integer.valueOf(typeName_index_prime));
      String type_name = type.getName();
      String typeName_value_prime = type_name;
      typeName.setValue(typeName_value_prime);
      return new Object[] { targetNode, eParameterNode, typeName, type };

   }

   public static final void pattern_Ecore2MocaXMIConverter_9_3_expressionBBB(Ecore2MocaXMIConverter _this, EParameter eParameter, Node eParameterNode)
   {
      _this.createNameAttribute(eParameter, eParameterNode, Integer.valueOf(1));

   }

   public static final void pattern_Ecore2MocaXMIConverter_9_4_expressionBBB(Ecore2MocaXMIConverter _this, EClassifier type, Node eParameterNode)
   {
      _this.createTypeGuidAttribute(type, eParameterNode, Integer.valueOf(2));

   }

   public static final void pattern_Ecore2MocaXMIConverter_9_5_expressionBBBB(Ecore2MocaXMIConverter _this, EParameter eParameter, Node eParameterNode,
         String path)
   {
      String _localVariable_0 = _this.addToPath(path, eParameter);
      _this.createGuidAttribute(eParameter, eParameterNode, Integer.valueOf(3), _localVariable_0);

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_10_1_init_blackB(Ecore2MocaXMIConverter _this)
   {
      return new Object[] { _this };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_10_2_createEEnumNode_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_10_2_createEEnumNode_greenBFF(Node targetNode)
   {
      Node eEnumNode = MocaTreeFactory.eINSTANCE.createNode();
      Node literals = MocaTreeFactory.eINSTANCE.createNode();
      String eEnumNode_name_prime = "EEnum";
      String literals_name_prime = "literals";
      eEnumNode.setParentNode(targetNode);
      literals.setParentNode(eEnumNode);
      eEnumNode.setName(eEnumNode_name_prime);
      literals.setName(literals_name_prime);
      return new Object[] { targetNode, eEnumNode, literals };
   }

   public static final void pattern_Ecore2MocaXMIConverter_10_3_expressionBBB(Ecore2MocaXMIConverter _this, EEnum eEnum, Node eEnumNode)
   {
      _this.createNameAttribute(eEnum, eEnumNode, Integer.valueOf(0));

   }

   public static final void pattern_Ecore2MocaXMIConverter_10_4_expressionBBBB(Ecore2MocaXMIConverter _this, EEnum eEnum, Node eEnumNode, String path)
   {
      String _localVariable_0 = _this.addToPath(path, eEnum);
      _this.createGuidAttribute(eEnum, eEnumNode, Integer.valueOf(1), _localVariable_0);

   }

   public static final Iterable<Object[]> pattern_Ecore2MocaXMIConverter_10_5_everyEEnumLiteral_blackBF(EEnum eEnum)
   {
      LinkedList<Object[]> _result = new LinkedList<Object[]>();
      for (EEnumLiteral eEnumLiteral : eEnum.getELiterals())
      {
         _result.add(new Object[] { eEnum, eEnumLiteral });
      }
      return _result;
   }

   public static final void pattern_Ecore2MocaXMIConverter_10_6_expressionBBB(Ecore2MocaXMIConverter _this, EEnumLiteral eEnumLiteral, Node literals)
   {
      _this.transformEEnumLiteral(eEnumLiteral, literals);

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_11_1_init_blackBB(Ecore2MocaXMIConverter _this, EDataType eDataType)
   {
      return new Object[] { _this, eDataType };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_11_2_createEDataTypeNode_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_11_2_createEDataTypeNode_greenBFFB(Node targetNode, EDataType eDataType)
   {
      Node eDataTypeNode = MocaTreeFactory.eINSTANCE.createNode();
      Attribute instanceTypeName = MocaTreeFactory.eINSTANCE.createAttribute();
      String eDataTypeNode_name_prime = "EDatatype";
      String instanceTypeName_name_prime = "instanceTypeName";
      int instanceTypeName_index_prime = Integer.valueOf(2);
      eDataTypeNode.setParentNode(targetNode);
      eDataTypeNode.getAttribute().add(instanceTypeName);
      eDataTypeNode.setName(eDataTypeNode_name_prime);
      instanceTypeName.setName(instanceTypeName_name_prime);
      instanceTypeName.setIndex(Integer.valueOf(instanceTypeName_index_prime));
      String eDataType_instanceTypeName = eDataType.getInstanceTypeName();
      String instanceTypeName_value_prime = eDataType_instanceTypeName;
      instanceTypeName.setValue(instanceTypeName_value_prime);
      return new Object[] { targetNode, eDataTypeNode, instanceTypeName, eDataType };

   }

   public static final void pattern_Ecore2MocaXMIConverter_11_3_expressionBBB(Ecore2MocaXMIConverter _this, EDataType eDataType, Node eDataTypeNode)
   {
      _this.createNameAttribute(eDataType, eDataTypeNode, Integer.valueOf(0));

   }

   public static final void pattern_Ecore2MocaXMIConverter_11_4_expressionBBBB(Ecore2MocaXMIConverter _this, EDataType eDataType, Node eDataTypeNode,
         String path)
   {
      String _localVariable_0 = _this.addToPath(path, eDataType);
      _this.createGuidAttribute(eDataType, eDataTypeNode, Integer.valueOf(1), _localVariable_0);

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_12_1_init_blackBB(Ecore2MocaXMIConverter _this, EEnumLiteral eEnumLiteral)
   {
      return new Object[] { _this, eEnumLiteral };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_12_2_createEEnumLiteralNode_blackB(Node targetNode)
   {
      return new Object[] { targetNode };
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_12_2_createEEnumLiteralNode_greenBFFFBB(Node targetNode, EEnumLiteral eEnumLiteral,
         Ecore2MocaXMIConverter _this)
   {
      Node eEnumLiteralNode = MocaTreeFactory.eINSTANCE.createNode();
      Attribute literal = MocaTreeFactory.eINSTANCE.createAttribute();
      Attribute value = MocaTreeFactory.eINSTANCE.createAttribute();
      String eEnumLiteralNode_name_prime = "EEnumLiteral";
      String literal_name_prime = "literal";
      int literal_index_prime = Integer.valueOf(2);
      String value_name_prime = "value";
      int value_index_prime = Integer.valueOf(1);
      eEnumLiteralNode.setParentNode(targetNode);
      eEnumLiteralNode.getAttribute().add(literal);
      eEnumLiteralNode.getAttribute().add(value);
      eEnumLiteralNode.setName(eEnumLiteralNode_name_prime);
      literal.setName(literal_name_prime);
      literal.setIndex(Integer.valueOf(literal_index_prime));
      value.setName(value_name_prime);
      value.setIndex(Integer.valueOf(value_index_prime));
      String eEnumLiteral_literal = eEnumLiteral.getLiteral();
      String literal_value_prime = eEnumLiteral_literal;
      literal.setValue(literal_value_prime);
      int eEnumLiteral_value = eEnumLiteral.getValue();
      String _localVariable_0 = _this.intToString(Integer.valueOf(eEnumLiteral_value));
      String value_value_prime = _localVariable_0;
      value.setValue(value_value_prime);
      return new Object[] { targetNode, eEnumLiteralNode, literal, value, eEnumLiteral, _this };

   }

   public static final void pattern_Ecore2MocaXMIConverter_12_3_expressionBBB(Ecore2MocaXMIConverter _this, EEnumLiteral eEnumLiteral, Node eEnumLiteralNode)
   {
      _this.createNameAttribute(eEnumLiteral, eEnumLiteralNode, Integer.valueOf(0));

   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_13_1_init_blackB(Ecore2MocaXMIConverter _this)
   {
      return new Object[] { _this };
   }

   public static final Node pattern_Ecore2MocaXMIConverter_13_2_expressionFBBB(Ecore2MocaXMIConverter _this, EPackage rootPackage, String workingSetName)
   {
      Node _localVariable_0 = _this.convert(rootPackage, workingSetName, false);
      Node _result = _localVariable_0;
      return _result;
   }

   public static final Object[] pattern_Ecore2MocaXMIConverter_14_1_init_blackB(Ecore2MocaXMIConverter _this)
   {
      return new Object[] { _this };
   }

   public static final Node pattern_Ecore2MocaXMIConverter_14_2_expressionFBBB(Ecore2MocaXMIConverter _this, EPackage rootPackage, boolean export)
   {
      Node _localVariable_0 = _this.convert(rootPackage, "WorkingSet", Boolean.valueOf(export));
      Node _result = _localVariable_0;
      return _result;
   }

   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} //Ecore2MocaXMIConverterImpl
