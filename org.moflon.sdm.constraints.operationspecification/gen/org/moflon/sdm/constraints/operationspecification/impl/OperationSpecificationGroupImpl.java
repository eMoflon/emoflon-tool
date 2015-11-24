/**
 */
package org.moflon.sdm.constraints.operationspecification.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;

import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;
// <-- [user defined imports]
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;
import org.stringtemplate.v4.compiler.CompiledST;
import org.moflon.sdm.constraints.operationspecification.ParamIdentifier;
import java.util.List;
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Specification Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl#getConstraintSpecifications <em>Constraint Specifications</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl#getOperationSpecifications <em>Operation Specifications</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl#getParameterIDs <em>Parameter IDs</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl#getOperationIdentifier <em>Operation Identifier</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl#isTemplateGroupGenerated <em>Template Group Generated</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl#getTemplateGroupString <em>Template Group String</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperationSpecificationGroupImpl extends EObjectImpl implements OperationSpecificationGroup {
	/**
	 * The cached value of the '{@link #getConstraintSpecifications() <em>Constraint Specifications</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintSpecifications()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstraintSpecification> constraintSpecifications;

	/**
	 * The cached value of the '{@link #getOperationSpecifications() <em>Operation Specifications</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationSpecifications()
	 * @generated
	 * @ordered
	 */
	protected EList<OperationSpecification> operationSpecifications;

	/**
	 * The cached value of the '{@link #getParameterIDs() <em>Parameter IDs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterIDs()
	 * @generated
	 * @ordered
	 */
	protected EList<ParamIdentifier> parameterIDs;

	/**
	 * The default value of the '{@link #getOperationIdentifier() <em>Operation Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final String OPERATION_IDENTIFIER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOperationIdentifier() <em>Operation Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationIdentifier()
	 * @generated
	 * @ordered
	 */
	protected String operationIdentifier = OPERATION_IDENTIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #isTemplateGroupGenerated() <em>Template Group Generated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTemplateGroupGenerated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TEMPLATE_GROUP_GENERATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTemplateGroupGenerated() <em>Template Group Generated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTemplateGroupGenerated()
	 * @generated
	 * @ordered
	 */
	protected boolean templateGroupGenerated = TEMPLATE_GROUP_GENERATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getTemplateGroupString() <em>Template Group String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateGroupString()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMPLATE_GROUP_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemplateGroupString() <em>Template Group String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateGroupString()
	 * @generated
	 * @ordered
	 */
	protected String templateGroupString = TEMPLATE_GROUP_STRING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationSpecificationGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationspecificationPackage.Literals.OPERATION_SPECIFICATION_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintLibrary getAttributeConstraintLibrary() {
		if (eContainerFeatureID() != OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY)
			return null;
		return (AttributeConstraintLibrary) eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConstraintLibrary(
			AttributeConstraintLibrary newAttributeConstraintLibrary, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newAttributeConstraintLibrary,
				OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeConstraintLibrary(AttributeConstraintLibrary newAttributeConstraintLibrary) {
		if (newAttributeConstraintLibrary != eInternalContainer()
				|| (eContainerFeatureID() != OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY
						&& newAttributeConstraintLibrary != null)) {
			if (EcoreUtil.isAncestor(this, newAttributeConstraintLibrary))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAttributeConstraintLibrary != null)
				msgs = ((InternalEObject) newAttributeConstraintLibrary).eInverseAdd(this,
						OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS,
						AttributeConstraintLibrary.class, msgs);
			msgs = basicSetAttributeConstraintLibrary(newAttributeConstraintLibrary, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY,
					newAttributeConstraintLibrary, newAttributeConstraintLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConstraintSpecification> getConstraintSpecifications() {
		if (constraintSpecifications == null) {
			constraintSpecifications = new EObjectWithInverseResolvingEList<ConstraintSpecification>(
					ConstraintSpecification.class, this,
					OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS,
					OperationspecificationPackage.CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP);
		}
		return constraintSpecifications;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OperationSpecification> getOperationSpecifications() {
		if (operationSpecifications == null) {
			operationSpecifications = new EObjectContainmentEList<OperationSpecification>(OperationSpecification.class,
					this, OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS);
		}
		return operationSpecifications;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParamIdentifier> getParameterIDs() {
		if (parameterIDs == null) {
			parameterIDs = new EObjectContainmentWithInverseEList<ParamIdentifier>(ParamIdentifier.class, this,
					OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS,
					OperationspecificationPackage.PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP);
		}
		return parameterIDs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOperationIdentifier() {
		return operationIdentifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperationIdentifier(String newOperationIdentifier) {
		String oldOperationIdentifier = operationIdentifier;
		operationIdentifier = newOperationIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_IDENTIFIER,
					oldOperationIdentifier, operationIdentifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTemplateGroupGenerated() {
		return templateGroupGenerated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplateGroupGenerated(boolean newTemplateGroupGenerated) {
		boolean oldTemplateGroupGenerated = templateGroupGenerated;
		templateGroupGenerated = newTemplateGroupGenerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_GENERATED,
					oldTemplateGroupGenerated, templateGroupGenerated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTemplateGroupString() {
		return templateGroupString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplateGroupString(String newTemplateGroupString) {
		String oldTemplateGroupString = templateGroupString;
		templateGroupString = newTemplateGroupString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_STRING,
					oldTemplateGroupString, templateGroupString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorMessage gernerateTemplate() {
		// [user code injected with eMoflon]
		final char del = '$';
		String finalGroupString = "group BuildInConstraints;";
		List<ParamIdentifier> parameterIdlist = this.getParameterIDs();
		for (OperationSpecification opSpec : this.getOperationSpecifications()) {
			String userOpTemplateString = opSpec.getSpecification();
			String declarationTemplateString = "";

			for (int i = 0; i < parameterIdlist.size(); i++) {
				String identifier = "$" + parameterIdlist.get(i).getIdentifier() + "$";
				userOpTemplateString = userOpTemplateString.replace(identifier, "§p" + i + "()§§Param()§");
				if (opSpec.getAdornmentString().charAt(i) == 'F') {
					declarationTemplateString = declarationTemplateString + "§p" + i + "()§§Decl()§;\n";
				}

			}

			STGroup stg = new STGroup('$', '$');
			stg.loadGroupFile("/", TEMPLATE_LOC);
			ST decl;
			if (opSpec.isAlwaysSuccessful()) {
				decl = stg.getInstanceOf("/alwaysSuccessful");
			} else {
				decl = stg.getInstanceOf("/notAlwaysSuccessful");
			}

			decl.add("templateName", this.getOperationIdentifier() + opSpec.getAdornmentString());
			decl.add("bT", "::=<<");
			decl.add("eT", ">>");

			ST declST = new ST(declarationTemplateString, '§', '§');
			stg.rawDefineTemplate("/userDeclaration", declST.impl, null);

			ST operationST = new ST(userOpTemplateString, '§', '§');
			stg.rawDefineTemplate("/userOperation", operationST.impl, null);
			finalGroupString = finalGroupString + "\n" + decl.render();
		}
		this.setTemplateGroupString(finalGroupString);
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetAttributeConstraintLibrary((AttributeConstraintLibrary) otherEnd, msgs);
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getConstraintSpecifications())
					.basicAdd(otherEnd, msgs);
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getParameterIDs()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY:
			return basicSetAttributeConstraintLibrary(null, msgs);
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS:
			return ((InternalEList<?>) getConstraintSpecifications()).basicRemove(otherEnd, msgs);
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS:
			return ((InternalEList<?>) getOperationSpecifications()).basicRemove(otherEnd, msgs);
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS:
			return ((InternalEList<?>) getParameterIDs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY:
			return eInternalContainer().eInverseRemove(this,
					OperationspecificationPackage.ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS,
					AttributeConstraintLibrary.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY:
			return getAttributeConstraintLibrary();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS:
			return getConstraintSpecifications();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS:
			return getOperationSpecifications();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS:
			return getParameterIDs();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_IDENTIFIER:
			return getOperationIdentifier();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_GENERATED:
			return isTemplateGroupGenerated();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_STRING:
			return getTemplateGroupString();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY:
			setAttributeConstraintLibrary((AttributeConstraintLibrary) newValue);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS:
			getConstraintSpecifications().clear();
			getConstraintSpecifications().addAll((Collection<? extends ConstraintSpecification>) newValue);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS:
			getOperationSpecifications().clear();
			getOperationSpecifications().addAll((Collection<? extends OperationSpecification>) newValue);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS:
			getParameterIDs().clear();
			getParameterIDs().addAll((Collection<? extends ParamIdentifier>) newValue);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_IDENTIFIER:
			setOperationIdentifier((String) newValue);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_GENERATED:
			setTemplateGroupGenerated((Boolean) newValue);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_STRING:
			setTemplateGroupString((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY:
			setAttributeConstraintLibrary((AttributeConstraintLibrary) null);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS:
			getConstraintSpecifications().clear();
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS:
			getOperationSpecifications().clear();
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS:
			getParameterIDs().clear();
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_IDENTIFIER:
			setOperationIdentifier(OPERATION_IDENTIFIER_EDEFAULT);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_GENERATED:
			setTemplateGroupGenerated(TEMPLATE_GROUP_GENERATED_EDEFAULT);
			return;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_STRING:
			setTemplateGroupString(TEMPLATE_GROUP_STRING_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY:
			return getAttributeConstraintLibrary() != null;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS:
			return constraintSpecifications != null && !constraintSpecifications.isEmpty();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS:
			return operationSpecifications != null && !operationSpecifications.isEmpty();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS:
			return parameterIDs != null && !parameterIDs.isEmpty();
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__OPERATION_IDENTIFIER:
			return OPERATION_IDENTIFIER_EDEFAULT == null ? operationIdentifier != null
					: !OPERATION_IDENTIFIER_EDEFAULT.equals(operationIdentifier);
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_GENERATED:
			return templateGroupGenerated != TEMPLATE_GROUP_GENERATED_EDEFAULT;
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_STRING:
			return TEMPLATE_GROUP_STRING_EDEFAULT == null ? templateGroupString != null
					: !TEMPLATE_GROUP_STRING_EDEFAULT.equals(templateGroupString);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case OperationspecificationPackage.OPERATION_SPECIFICATION_GROUP___GERNERATE_TEMPLATE:
			return gernerateTemplate();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (operationIdentifier: ");
		result.append(operationIdentifier);
		result.append(", templateGroupGenerated: ");
		result.append(templateGroupGenerated);
		result.append(", templateGroupString: ");
		result.append(templateGroupString);
		result.append(')');
		return result.toString();
	}

	// <-- [user code injected with eMoflon]
	private final static String TEMPLATE_LOC = "platform:/plugin/DemoclesAttributeConstraintSpecification/lib/stringtemplates/basicStringtemplateDef.stg";
	//private final static String TEMPLATE_LOC="file:/F:/Workspaces/Eclipse/2015_01_22_DevWs_ComplexAttributeValidation/DemoclesAttributeConstraintSpecification/lib/stringtemplates/basicStringtemplateDef.stg";
	// [user code injected with eMoflon] -->
} //OperationSpecificationGroupImpl
