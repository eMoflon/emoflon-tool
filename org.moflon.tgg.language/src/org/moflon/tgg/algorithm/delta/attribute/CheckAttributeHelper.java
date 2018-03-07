package org.moflon.tgg.algorithm.delta.attribute;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.moflon.tgg.runtime.TripleMatch;
import org.moflon.tgg.runtime.TripleMatchNodeMapping;

import SDMLanguage.expressions.ComparingOperator;

public class CheckAttributeHelper {

	private TripleMatch tripleMatch;

	public CheckAttributeHelper(TripleMatch tripleMatch) {
		this.tripleMatch = tripleMatch;
	}

	public boolean hasExpectedValue(String ovName, String attributeName, Object expectedValue, ComparingOperator comp) {
		// Ensure value is as expected
		switch (comp) {
		case EQUAL:
			return getValue(ovName, attributeName).equals(expectedValue);

		case UNEQUAL:
			return !getValue(ovName, attributeName).equals(expectedValue);

		case GREATER:
			return (Double) getValue(ovName, attributeName) > (Double) expectedValue;

		case GREATER_OR_EQUAL:
			return (Double) getValue(ovName, attributeName) >= (Double) expectedValue;

		case LESS:
			return (Double) getValue(ovName, attributeName) < (Double) expectedValue;

		case LESS_OR_EQUAL:
			return (Double) getValue(ovName, attributeName) <= (Double) expectedValue;

		default:
			return false;
		}
	}

	public Object getValue(String ovName, String attributeName) {
		// Retrieve correct node in match
		Optional<TripleMatchNodeMapping> mapping = getMappingForOV(ovName);

		if (!mapping.isPresent())
			return false;

		// Get relevant feature
		EObject node = mapping.get().getNode();
		EStructuralFeature feature = node.eClass().getEStructuralFeature(attributeName);

		// Return value
		return node.eGet(feature);
	}

	private Optional<TripleMatchNodeMapping> getMappingForOV(String ovName) {
		return tripleMatch.getNodeMappings().stream().filter(nm -> nm.getNodeName().equals(ovName)).findAny();
	}

	public void setValue(String ovName, String attributeName, Object value) {
		// Retrieve correct node in match
		Optional<TripleMatchNodeMapping> mapping = getMappingForOV(ovName);

		if (!mapping.isPresent())
			return;

		// Get relevant feature
		EObject node = mapping.get().getNode();
		EStructuralFeature feature = node.eClass().getEStructuralFeature(attributeName);

		// Set value
		node.eSet(feature, value);
	}
}