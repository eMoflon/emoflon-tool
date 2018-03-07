package org.moflon.core.ecore2mocaxmi.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EReference;

import MocaTree.Attribute;

public class GuidManager {

	private Map<ENamedElement, String> guids;
	private Map<EReference, EReference> opposites;
	private Map<Attribute, Set<ENamedElement>> searchMap;

	private static final String[] signalWords = { "EInt", "void", "EString", "EDouble", "EBoolean", "EFloat", "ELong" };
	private List<String> blackList;

	public GuidManager() {
		guids = new HashMap<>();
		searchMap = new HashMap<>();
		blackList = Arrays.asList(signalWords);
		opposites = new HashMap<>();
	}

	public void clear() {
		guids.clear();
		searchMap.clear();
		opposites.clear();
	}

	public void init(String projectName) {
		GuidGenerator.init(projectName);
	}

	private String getGuid(ENamedElement element) {
		return getGuid(element, null);
	}

	public String getGuid(ENamedElement element, String path) {
		String guid = null;
		if (element == null)
			throw new RuntimeException("The given element is Null");

		if (guids.containsKey(element))
			guid = guids.get(element);
		else if (element instanceof EReference) {
			guid = handleEReference(EReference.class.cast(element), path);
			guids.put(element, guid);
		} else {
			guid = GuidGenerator.generateGuid(element, path);
			guids.put(element, guid);
		}

		return guid;
	}

	public void addSearchElement(ENamedElement ecoreElement, Attribute searchAttribute) {
		Set<ENamedElement> models = null;
		if (searchMap.containsKey(searchAttribute)) {
			models = searchMap.get(searchAttribute);
			models.add(ecoreElement);
		} else {
			models = new HashSet<>();
			models.add(ecoreElement);
		}
		searchMap.put(searchAttribute, models);
	}

	public void resolve() {
		for (Attribute attribute : searchMap.keySet()) {
			Set<ENamedElement> models = searchMap.get(attribute);
			String seperator = "";
			if (models.size() > 1)
				seperator = " ";
			for (ENamedElement element : models) {
				String guid = null;
				if (element == null)
					guid = "";
				else {
					guid = getGuid(element);
					if (element instanceof ENamedElement) {
						if (blackList.contains(ENamedElement.class.cast(element).getName()))
							guid = "";
					}
					String oldGuid = attribute.getValue();
					if (oldGuid == null)
						oldGuid = "";
					attribute.setValue(oldGuid + seperator + guid);
				}
			}
		}
	}

	private String handleEReference(EReference eReference, String path) {
		EReference eOpposite = eReference.getEOpposite();
		String guid = null;
		if (opposites.containsKey(eOpposite)) {
			guid = getOppositeOfClientOrSupplier(guids.get(eOpposite));
			opposites.put(eReference, eOpposite);
		} else {
			if (eOpposite == null || !eOpposite.isContainment())
				guid = GuidGenerator.generateGuid(eReference, path) + "Supplier";
			else
				guid = GuidGenerator.generateGuid(eReference, path) + "Client";
			if (eOpposite != null)
				opposites.put(eReference, eOpposite);
		}

		return guid;
	}

	private String getOppositeOfClientOrSupplier(String guid) {
		if (guid.endsWith("Supplier"))
			return guid.replace("Supplier", "Client");
		else if (guid.endsWith("Client"))
			return guid.replace("Client", "Supplier");
		else
			return guid;
	}

}
