/**
 */
package org.moflon.ide.visualization.dot.sdm.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.moflon.ide.visualization.dot.sdm.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SdmFactoryImpl extends EFactoryImpl implements SdmFactory
{
   /**
    * Creates the default factory implementation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public static SdmFactory init()
   {
      try
      {
         SdmFactory theSdmFactory = (SdmFactory) EPackage.Registry.INSTANCE.getEFactory(SdmPackage.eNS_URI);
         if (theSdmFactory != null)
         {
            return theSdmFactory;
         }
      } catch (Exception exception)
      {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new SdmFactoryImpl();
   }

   /**
    * Creates an instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SdmFactoryImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EObject create(EClass eClass)
   {
      switch (eClass.getClassifierID())
      {
      case SdmPackage.NODE_TO_ACTIVITY_EDGE:
         return createNodeToActivityEdge();
      case SdmPackage.NODE_COMMAND_TO_ACTIVITY_NODE:
         return createNodeCommandToActivityNode();
      case SdmPackage.EDGE_COMMAND_TO_ACTIVITY_EDGE:
         return createEdgeCommandToActivityEdge();
      case SdmPackage.DIRECTED_GRAPH_TO_ACTIVITY:
         return createDirectedGraphToActivity();
      case SdmPackage.NODE_TO_ACTIVITY_NODE:
         return createNodeToActivityNode();
      case SdmPackage.NODE_COMMAND_TO_STOP_NODE:
         return createNodeCommandToStopNode();
      case SdmPackage.RECORD_TO_METHOD_CALL:
         return createRecordToMethodCall();
      default:
         throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NodeToActivityEdge createNodeToActivityEdge()
   {
      NodeToActivityEdgeImpl nodeToActivityEdge = new NodeToActivityEdgeImpl();
      return nodeToActivityEdge;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NodeCommandToActivityNode createNodeCommandToActivityNode()
   {
      NodeCommandToActivityNodeImpl nodeCommandToActivityNode = new NodeCommandToActivityNodeImpl();
      return nodeCommandToActivityNode;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EdgeCommandToActivityEdge createEdgeCommandToActivityEdge()
   {
      EdgeCommandToActivityEdgeImpl edgeCommandToActivityEdge = new EdgeCommandToActivityEdgeImpl();
      return edgeCommandToActivityEdge;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public DirectedGraphToActivity createDirectedGraphToActivity()
   {
      DirectedGraphToActivityImpl directedGraphToActivity = new DirectedGraphToActivityImpl();
      return directedGraphToActivity;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NodeToActivityNode createNodeToActivityNode()
   {
      NodeToActivityNodeImpl nodeToActivityNode = new NodeToActivityNodeImpl();
      return nodeToActivityNode;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NodeCommandToStopNode createNodeCommandToStopNode()
   {
      NodeCommandToStopNodeImpl nodeCommandToStopNode = new NodeCommandToStopNodeImpl();
      return nodeCommandToStopNode;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public RecordToMethodCall createRecordToMethodCall()
   {
      RecordToMethodCallImpl recordToMethodCall = new RecordToMethodCallImpl();
      return recordToMethodCall;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SdmPackage getSdmPackage()
   {
      return (SdmPackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @deprecated
    * @generated
    */
   @Deprecated
   public static SdmPackage getPackage()
   {
      return SdmPackage.eINSTANCE;
   }

} //SdmFactoryImpl
