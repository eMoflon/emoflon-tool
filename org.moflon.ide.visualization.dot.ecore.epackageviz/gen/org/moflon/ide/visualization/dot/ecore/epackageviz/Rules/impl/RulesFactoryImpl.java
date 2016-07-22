/**
 */
package org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RulesFactoryImpl extends EFactoryImpl implements RulesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RulesFactory init() {
		try {
			RulesFactory theRulesFactory = (RulesFactory) EPackage.Registry.INSTANCE.getEFactory(RulesPackage.eNS_URI);
			if (theRulesFactory != null) {
				return theRulesFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RulesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case RulesPackage.INTERFACE_NODE_RULE:
			return createInterfaceNodeRule();
		case RulesPackage.PACKAGE_AXIOM:
			return createPackageAxiom();
		case RulesPackage.NODE_ABSTRACT_RULE:
			return createNodeAbstractRule();
		case RulesPackage.ENUM_NODE_RULE:
			return createEnumNodeRule();
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE:
			return createAbstractClassNodeRule();
		case RulesPackage.SIMPLE_CLASS_NODE_RULE:
			return createSimpleClassNodeRule();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InterfaceNodeRule createInterfaceNodeRule() {
		InterfaceNodeRuleImpl interfaceNodeRule = new InterfaceNodeRuleImpl();
		return interfaceNodeRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageAxiom createPackageAxiom() {
		PackageAxiomImpl packageAxiom = new PackageAxiomImpl();
		return packageAxiom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeAbstractRule createNodeAbstractRule() {
		NodeAbstractRuleImpl nodeAbstractRule = new NodeAbstractRuleImpl();
		return nodeAbstractRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumNodeRule createEnumNodeRule() {
		EnumNodeRuleImpl enumNodeRule = new EnumNodeRuleImpl();
		return enumNodeRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractClassNodeRule createAbstractClassNodeRule() {
		AbstractClassNodeRuleImpl abstractClassNodeRule = new AbstractClassNodeRuleImpl();
		return abstractClassNodeRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleClassNodeRule createSimpleClassNodeRule() {
		SimpleClassNodeRuleImpl simpleClassNodeRule = new SimpleClassNodeRuleImpl();
		return simpleClassNodeRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesPackage getRulesPackage() {
		return (RulesPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RulesPackage getPackage() {
		return RulesPackage.eINSTANCE;
	}

} //RulesFactoryImpl
