/**
 */
package org.moflon.ide.visualization.dot.sdm.impl;

import MocaTree.MocaTreePackage;

import SDMLanguage.activities.ActivitiesPackage;

import SDMLanguage.calls.callExpressions.CallExpressionsPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.moflon.ide.visualization.dot.language.LanguagePackage;

import org.moflon.ide.visualization.dot.sdm.DirectedGraphToActivity;
import org.moflon.ide.visualization.dot.sdm.EdgeCommandToActivityEdge;
import org.moflon.ide.visualization.dot.sdm.NodeCommandToActivityNode;
import org.moflon.ide.visualization.dot.sdm.NodeCommandToStopNode;
import org.moflon.ide.visualization.dot.sdm.NodeToActivityEdge;
import org.moflon.ide.visualization.dot.sdm.NodeToActivityNode;
import org.moflon.ide.visualization.dot.sdm.RecordToMethodCall;

import org.moflon.ide.visualization.dot.sdm.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.sdm.Rules.impl.RulesPackageImpl;

import org.moflon.ide.visualization.dot.sdm.SdmFactory;
import org.moflon.ide.visualization.dot.sdm.SdmPackage;

import org.moflon.tgg.runtime.RuntimePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SdmPackageImpl extends EPackageImpl implements SdmPackage
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass nodeToActivityEdgeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass nodeCommandToActivityNodeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass edgeCommandToActivityEdgeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass directedGraphToActivityEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass nodeToActivityNodeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass nodeCommandToStopNodeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass recordToMethodCallEClass = null;

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
    * @see org.moflon.ide.visualization.dot.sdm.SdmPackage#eNS_URI
    * @see #init()
    * @generated
    */
   private SdmPackageImpl()
   {
      super(eNS_URI, SdmFactory.eINSTANCE);
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
    * <p>This method is used to initialize {@link SdmPackage#eINSTANCE} when that field is accessed.
    * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #eNS_URI
    * @see #createPackageContents()
    * @see #initializePackageContents()
    * @generated
    */
   public static SdmPackage init()
   {
      if (isInited)
         return (SdmPackage) EPackage.Registry.INSTANCE.getEPackage(SdmPackage.eNS_URI);

      // Obtain or create and register package
      SdmPackageImpl theSdmPackage = (SdmPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SdmPackageImpl
            ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SdmPackageImpl());

      isInited = true;

      // Initialize simple dependencies
      LanguagePackage.eINSTANCE.eClass();
      org.moflon.tgg.language.LanguagePackage.eINSTANCE.eClass();

      // Obtain or create and register interdependencies
      RulesPackageImpl theRulesPackage = (RulesPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI) instanceof RulesPackageImpl
            ? EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI) : RulesPackage.eINSTANCE);

      // Create package meta-data objects
      theSdmPackage.createPackageContents();
      theRulesPackage.createPackageContents();

      // Initialize created meta-data
      theSdmPackage.initializePackageContents();
      theRulesPackage.initializePackageContents();

      // Mark meta-data to indicate it can't be changed
      theSdmPackage.freeze();

      // Update the registry and return the package
      EPackage.Registry.INSTANCE.put(SdmPackage.eNS_URI, theSdmPackage);
      return theSdmPackage;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getNodeToActivityEdge()
   {
      return nodeToActivityEdgeEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getNodeToActivityEdge_Source()
   {
      return (EReference) nodeToActivityEdgeEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getNodeToActivityEdge_Target()
   {
      return (EReference) nodeToActivityEdgeEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getNodeCommandToActivityNode()
   {
      return nodeCommandToActivityNodeEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getNodeCommandToActivityNode_Source()
   {
      return (EReference) nodeCommandToActivityNodeEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getNodeCommandToActivityNode_Target()
   {
      return (EReference) nodeCommandToActivityNodeEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getEdgeCommandToActivityEdge()
   {
      return edgeCommandToActivityEdgeEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getEdgeCommandToActivityEdge_Source()
   {
      return (EReference) edgeCommandToActivityEdgeEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getEdgeCommandToActivityEdge_Target()
   {
      return (EReference) edgeCommandToActivityEdgeEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getDirectedGraphToActivity()
   {
      return directedGraphToActivityEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getDirectedGraphToActivity_Source()
   {
      return (EReference) directedGraphToActivityEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getDirectedGraphToActivity_Target()
   {
      return (EReference) directedGraphToActivityEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getNodeToActivityNode()
   {
      return nodeToActivityNodeEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getNodeToActivityNode_Source()
   {
      return (EReference) nodeToActivityNodeEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getNodeToActivityNode_Target()
   {
      return (EReference) nodeToActivityNodeEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getNodeCommandToStopNode()
   {
      return nodeCommandToStopNodeEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EClass getRecordToMethodCall()
   {
      return recordToMethodCallEClass;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getRecordToMethodCall_Source()
   {
      return (EReference) recordToMethodCallEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EReference getRecordToMethodCall_Target()
   {
      return (EReference) recordToMethodCallEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SdmFactory getSdmFactory()
   {
      return (SdmFactory) getEFactoryInstance();
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
      nodeToActivityEdgeEClass = createEClass(NODE_TO_ACTIVITY_EDGE);
      createEReference(nodeToActivityEdgeEClass, NODE_TO_ACTIVITY_EDGE__SOURCE);
      createEReference(nodeToActivityEdgeEClass, NODE_TO_ACTIVITY_EDGE__TARGET);

      nodeCommandToActivityNodeEClass = createEClass(NODE_COMMAND_TO_ACTIVITY_NODE);
      createEReference(nodeCommandToActivityNodeEClass, NODE_COMMAND_TO_ACTIVITY_NODE__SOURCE);
      createEReference(nodeCommandToActivityNodeEClass, NODE_COMMAND_TO_ACTIVITY_NODE__TARGET);

      edgeCommandToActivityEdgeEClass = createEClass(EDGE_COMMAND_TO_ACTIVITY_EDGE);
      createEReference(edgeCommandToActivityEdgeEClass, EDGE_COMMAND_TO_ACTIVITY_EDGE__SOURCE);
      createEReference(edgeCommandToActivityEdgeEClass, EDGE_COMMAND_TO_ACTIVITY_EDGE__TARGET);

      directedGraphToActivityEClass = createEClass(DIRECTED_GRAPH_TO_ACTIVITY);
      createEReference(directedGraphToActivityEClass, DIRECTED_GRAPH_TO_ACTIVITY__SOURCE);
      createEReference(directedGraphToActivityEClass, DIRECTED_GRAPH_TO_ACTIVITY__TARGET);

      nodeToActivityNodeEClass = createEClass(NODE_TO_ACTIVITY_NODE);
      createEReference(nodeToActivityNodeEClass, NODE_TO_ACTIVITY_NODE__SOURCE);
      createEReference(nodeToActivityNodeEClass, NODE_TO_ACTIVITY_NODE__TARGET);

      nodeCommandToStopNodeEClass = createEClass(NODE_COMMAND_TO_STOP_NODE);

      recordToMethodCallEClass = createEClass(RECORD_TO_METHOD_CALL);
      createEReference(recordToMethodCallEClass, RECORD_TO_METHOD_CALL__SOURCE);
      createEReference(recordToMethodCallEClass, RECORD_TO_METHOD_CALL__TARGET);
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
      RulesPackage theRulesPackage = (RulesPackage) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI);
      RuntimePackage theRuntimePackage = (RuntimePackage) EPackage.Registry.INSTANCE.getEPackage(RuntimePackage.eNS_URI);
      MocaTreePackage theMocaTreePackage = (MocaTreePackage) EPackage.Registry.INSTANCE.getEPackage(MocaTreePackage.eNS_URI);
      ActivitiesPackage theActivitiesPackage = (ActivitiesPackage) EPackage.Registry.INSTANCE.getEPackage(ActivitiesPackage.eNS_URI);
      LanguagePackage theLanguagePackage = (LanguagePackage) EPackage.Registry.INSTANCE.getEPackage(LanguagePackage.eNS_URI);
      CallExpressionsPackage theCallExpressionsPackage = (CallExpressionsPackage) EPackage.Registry.INSTANCE.getEPackage(CallExpressionsPackage.eNS_URI);

      // Add subpackages
      getESubpackages().add(theRulesPackage);

      // Create type parameters

      // Set bounds for type parameters

      // Add supertypes to classes
      nodeToActivityEdgeEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());
      nodeCommandToActivityNodeEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());
      edgeCommandToActivityEdgeEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());
      directedGraphToActivityEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());
      nodeToActivityNodeEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());
      nodeCommandToStopNodeEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());
      nodeCommandToStopNodeEClass.getESuperTypes().add(this.getNodeCommandToActivityNode());
      recordToMethodCallEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());

      // Initialize classes, features, and operations; add parameters
      initEClass(nodeToActivityEdgeEClass, NodeToActivityEdge.class, "NodeToActivityEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEReference(getNodeToActivityEdge_Source(), theMocaTreePackage.getNode(), null, "source", null, 1, 1, NodeToActivityEdge.class, !IS_TRANSIENT,
            !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getNodeToActivityEdge_Target(), theActivitiesPackage.getActivityEdge(), null, "target", null, 1, 1, NodeToActivityEdge.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(nodeCommandToActivityNodeEClass, NodeCommandToActivityNode.class, "NodeCommandToActivityNode", !IS_ABSTRACT, !IS_INTERFACE,
            IS_GENERATED_INSTANCE_CLASS);
      initEReference(getNodeCommandToActivityNode_Source(), theLanguagePackage.getNodeCommand(), null, "source", null, 1, 1, NodeCommandToActivityNode.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getNodeCommandToActivityNode_Target(), theActivitiesPackage.getActivityNode(), null, "target", null, 1, 1, NodeCommandToActivityNode.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(edgeCommandToActivityEdgeEClass, EdgeCommandToActivityEdge.class, "EdgeCommandToActivityEdge", !IS_ABSTRACT, !IS_INTERFACE,
            IS_GENERATED_INSTANCE_CLASS);
      initEReference(getEdgeCommandToActivityEdge_Source(), theLanguagePackage.getEdgeCommand(), null, "source", null, 1, 1, EdgeCommandToActivityEdge.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getEdgeCommandToActivityEdge_Target(), theActivitiesPackage.getActivityEdge(), null, "target", null, 1, 1, EdgeCommandToActivityEdge.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(directedGraphToActivityEClass, DirectedGraphToActivity.class, "DirectedGraphToActivity", !IS_ABSTRACT, !IS_INTERFACE,
            IS_GENERATED_INSTANCE_CLASS);
      initEReference(getDirectedGraphToActivity_Source(), theLanguagePackage.getDirectedGraph(), null, "source", null, 1, 1, DirectedGraphToActivity.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getDirectedGraphToActivity_Target(), theActivitiesPackage.getActivity(), null, "target", null, 1, 1, DirectedGraphToActivity.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(nodeToActivityNodeEClass, NodeToActivityNode.class, "NodeToActivityNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEReference(getNodeToActivityNode_Source(), theMocaTreePackage.getNode(), null, "source", null, 1, 1, NodeToActivityNode.class, !IS_TRANSIENT,
            !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getNodeToActivityNode_Target(), theActivitiesPackage.getActivityNode(), null, "target", null, 1, 1, NodeToActivityNode.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(nodeCommandToStopNodeEClass, NodeCommandToStopNode.class, "NodeCommandToStopNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

      initEClass(recordToMethodCallEClass, RecordToMethodCall.class, "RecordToMethodCall", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEReference(getRecordToMethodCall_Source(), theLanguagePackage.getRecordEntry(), null, "source", null, 1, 1, RecordToMethodCall.class, !IS_TRANSIENT,
            !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getRecordToMethodCall_Target(), theCallExpressionsPackage.getMethodCallExpression(), null, "target", null, 1, 1, RecordToMethodCall.class,
            !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      // Create resource
      createResource(eNS_URI);
   }

} //SdmPackageImpl
