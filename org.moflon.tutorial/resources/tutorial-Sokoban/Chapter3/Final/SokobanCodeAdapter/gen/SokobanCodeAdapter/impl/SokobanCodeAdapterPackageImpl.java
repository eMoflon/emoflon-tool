/**
 */
package SokobanCodeAdapter.impl;

import MocaTree.MocaTreePackage;

import SokobanCodeAdapter.NodeToBoard;
import SokobanCodeAdapter.NodeToFloor;

import SokobanCodeAdapter.Rules.RulesPackage;

import SokobanCodeAdapter.Rules.impl.RulesPackageImpl;

import SokobanCodeAdapter.SokobanCodeAdapterFactory;
import SokobanCodeAdapter.SokobanCodeAdapterPackage;

import SokobanLanguage.SokobanLanguagePackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SokobanCodeAdapterPackageImpl extends EPackageImpl implements
		SokobanCodeAdapterPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeToBoardEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeToFloorEClass = null;

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
	 * @see SokobanCodeAdapter.SokobanCodeAdapterPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SokobanCodeAdapterPackageImpl() {
		super(eNS_URI, SokobanCodeAdapterFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SokobanCodeAdapterPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SokobanCodeAdapterPackage init() {
		if (isInited)
			return (SokobanCodeAdapterPackage) EPackage.Registry.INSTANCE
					.getEPackage(SokobanCodeAdapterPackage.eNS_URI);

		// Obtain or create and register package
		SokobanCodeAdapterPackageImpl theSokobanCodeAdapterPackage = (SokobanCodeAdapterPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof SokobanCodeAdapterPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new SokobanCodeAdapterPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		SokobanLanguagePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		RulesPackageImpl theRulesPackage = (RulesPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(RulesPackage.eNS_URI) instanceof RulesPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(RulesPackage.eNS_URI) : RulesPackage.eINSTANCE);

		// Create package meta-data objects
		theSokobanCodeAdapterPackage.createPackageContents();
		theRulesPackage.createPackageContents();

		// Initialize created meta-data
		theSokobanCodeAdapterPackage.initializePackageContents();
		theRulesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSokobanCodeAdapterPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SokobanCodeAdapterPackage.eNS_URI,
				theSokobanCodeAdapterPackage);
		return theSokobanCodeAdapterPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeToBoard() {
		return nodeToBoardEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeToBoard_Source() {
		return (EReference) nodeToBoardEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeToBoard_Target() {
		return (EReference) nodeToBoardEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeToFloor() {
		return nodeToFloorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeToFloor_Source() {
		return (EReference) nodeToFloorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeToFloor_Target() {
		return (EReference) nodeToFloorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SokobanCodeAdapterFactory getSokobanCodeAdapterFactory() {
		return (SokobanCodeAdapterFactory) getEFactoryInstance();
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
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		nodeToBoardEClass = createEClass(NODE_TO_BOARD);
		createEReference(nodeToBoardEClass, NODE_TO_BOARD__SOURCE);
		createEReference(nodeToBoardEClass, NODE_TO_BOARD__TARGET);

		nodeToFloorEClass = createEClass(NODE_TO_FLOOR);
		createEReference(nodeToFloorEClass, NODE_TO_FLOOR__SOURCE);
		createEReference(nodeToFloorEClass, NODE_TO_FLOOR__TARGET);
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
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		RulesPackage theRulesPackage = (RulesPackage) EPackage.Registry.INSTANCE
				.getEPackage(RulesPackage.eNS_URI);
		MocaTreePackage theMocaTreePackage = (MocaTreePackage) EPackage.Registry.INSTANCE
				.getEPackage(MocaTreePackage.eNS_URI);
		SokobanLanguagePackage theSokobanLanguagePackage = (SokobanLanguagePackage) EPackage.Registry.INSTANCE
				.getEPackage(SokobanLanguagePackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theRulesPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(nodeToBoardEClass, NodeToBoard.class, "NodeToBoard",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeToBoard_Source(), theMocaTreePackage.getNode(),
				null, "source", null, 0, 1, NodeToBoard.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeToBoard_Target(),
				theSokobanLanguagePackage.getBoard(), null, "target", null, 0,
				1, NodeToBoard.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeToFloorEClass, NodeToFloor.class, "NodeToFloor",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeToFloor_Source(), theMocaTreePackage.getNode(),
				null, "source", null, 0, 1, NodeToFloor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeToFloor_Target(),
				theSokobanLanguagePackage.getFloor(), null, "target", null, 0,
				1, NodeToFloor.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SokobanCodeAdapterPackageImpl
