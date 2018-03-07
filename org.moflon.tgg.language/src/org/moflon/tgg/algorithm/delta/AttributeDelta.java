package org.moflon.tgg.algorithm.delta;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.tgg.runtime.RuntimeFactory;

public class AttributeDelta {
	private Object oldValue;
	private Object newValue;
	private EAttribute affectedAttribute;
	private EObject affectedNode;

	public AttributeDelta(EAttribute affectedAttribute, Object oldValue, Object newValue, EObject affectedNode) {
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.affectedAttribute = affectedAttribute;
		this.affectedNode = affectedNode;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public EAttribute getAffectedAttribute() {
		return affectedAttribute;
	}

	public EObject getAffectedNode() {
		return affectedNode;
	}

	public org.moflon.tgg.runtime.AttributeDelta toEMF() {
		org.moflon.tgg.runtime.AttributeDelta attDelta = RuntimeFactory.eINSTANCE.createAttributeDelta();

		attDelta.setOldValue(getStringValue(oldValue));
		attDelta.setNewValue(getStringValue(newValue));
		attDelta.setAffectedAttribute(affectedAttribute);
		attDelta.setAffectedNode(affectedNode);

		return attDelta;
	}

	private String getStringValue(Object value) {
		return EcoreUtil.convertToString(affectedAttribute.getEAttributeType(), value);
	}

	public static AttributeDelta fromEMF(org.moflon.tgg.runtime.AttributeDelta attDeltaEMF) {
		return new AttributeDelta(attDeltaEMF.getAffectedAttribute(),
				extractTypeFromString(attDeltaEMF.getOldValue(), attDeltaEMF.getAffectedAttribute()),
				extractTypeFromString(attDeltaEMF.getNewValue(), attDeltaEMF.getAffectedAttribute()),
				attDeltaEMF.getAffectedNode());
	}

	private static Object extractTypeFromString(String value, EAttribute attribute) {
		return EcoreUtil.createFromString(attribute.getEAttributeType(), value);
	}
}
