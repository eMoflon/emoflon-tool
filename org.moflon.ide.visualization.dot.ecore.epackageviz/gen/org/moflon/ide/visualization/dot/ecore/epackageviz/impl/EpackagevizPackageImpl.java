/**
 */
package org.moflon.ide.visualization.dot.ecore.epackageviz.impl;

import java.io.IOException;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import org.moflon.ide.visualization.dot.ecore.epackageviz.EpackagevizFactory;
import org.moflon.ide.visualization.dot.ecore.epackageviz.EpackagevizPackage;

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.impl.RulesPackageImpl;

import org.moflon.ide.visualization.dot.language.LanguagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EpackagevizPackageImpl extends EPackageImpl implements EpackagevizPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected String packageFilename = "epackageviz.ecore";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pNodeToEClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classGraphToEPackageEClass = null;

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
	 * @see org.moflon.ide.visualization.dot.ecore.epackageviz.EpackagevizPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EpackagevizPackageImpl() {
		super(eNS_URI, EpackagevizFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EpackagevizPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @generated
	 */
	public static EpackagevizPackage init() {
		if (isInited)
			return (EpackagevizPackage) EPackage.Registry.INSTANCE.getEPackage(EpackagevizPackage.eNS_URI);

		// Obtain or create and register package
		EpackagevizPackageImpl theEpackagevizPackage = (EpackagevizPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof EpackagevizPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new EpackagevizPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		LanguagePackage.eINSTANCE.eClass();
		org.moflon.tgg.language.LanguagePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		RulesPackageImpl theRulesPackage = (RulesPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(RulesPackage.eNS_URI) instanceof RulesPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI) : RulesPackage.eINSTANCE);

		// Load packages
		theEpackagevizPackage.loadPackage();

		// Fix loaded packages
		theEpackagevizPackage.fixPackageContents();
		theRulesPackage.fixPackageContents();

		// Mark meta-data to indicate it can't be changed
		theEpackagevizPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EpackagevizPackage.eNS_URI, theEpackagevizPackage);
		return theEpackagevizPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPNodeToEClassifier() {
		if (pNodeToEClassifierEClass == null) {
			pNodeToEClassifierEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(EpackagevizPackage.eNS_URI)
					.getEClassifiers().get(0);
		}
		return pNodeToEClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPNodeToEClassifier_Source() {
		return (EReference) getPNodeToEClassifier().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPNodeToEClassifier_Target() {
		return (EReference) getPNodeToEClassifier().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassGraphToEPackage() {
		if (classGraphToEPackageEClass == null) {
			classGraphToEPackageEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(EpackagevizPackage.eNS_URI)
					.getEClassifiers().get(1);
		}
		return classGraphToEPackageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassGraphToEPackage_Source() {
		return (EReference) getClassGraphToEPackage().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassGraphToEPackage_Target() {
		return (EReference) getClassGraphToEPackage().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpackagevizFactory getEpackagevizFactory() {
		return (EpackagevizFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isLoaded = false;

	/**
	 * Laods the package and any sub-packages from their serialized form.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void loadPackage() {
		if (isLoaded)
			return;
		isLoaded = true;

		URL url = getClass().getResource(packageFilename);
		if (url == null) {
			throw new RuntimeException("Missing serialized package: " + packageFilename);
		}
		URI uri = URI.createURI(url.toString());
		Resource resource = new EcoreResourceFactoryImpl().createResource(uri);
		try {
			resource.load(null);
		} catch (IOException exception) {
			throw new WrappedException(exception);
		}
		initializeFromLoadedEPackage(this, (EPackage) resource.getContents().get(0));
		createResource(eNS_URI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isFixed = false;

	/**
	 * Fixes up the loaded package, to make it appear as if it had been programmatically built.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fixPackageContents() {
		if (isFixed)
			return;
		isFixed = true;
		fixEClassifiers();
	}

	/**
	 * Sets the instance class on the given classifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void fixInstanceClass(EClassifier eClassifier) {
		if (eClassifier.getInstanceClassName() == null) {
			eClassifier.setInstanceClassName(
					"org.moflon.ide.visualization.dot.ecore.epackageviz." + eClassifier.getName());
			setGeneratedClassName(eClassifier);
		}
	}

} //EpackagevizPackageImpl
