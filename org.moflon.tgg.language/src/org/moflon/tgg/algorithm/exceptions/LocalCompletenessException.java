package org.moflon.tgg.algorithm.exceptions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("serial")
public abstract class LocalCompletenessException extends Exception {
	protected Collection<String> ruleNames;
	protected Collection<Map<String, EObject>> matches;

	public abstract String getMessage(String projectName);

	public String getMessage(String projectName, boolean verbose) {
		if (!verbose)
			return getMessage(projectName) + "\nI got stuck while trying to extend the following source matches: "
					+ ruleNames;
		else {
			StringBuffer detailedInfo = new StringBuffer();
			Iterator<String> itrNames = ruleNames.iterator();
			Iterator<Map<String, EObject>> itrMappings = matches.iterator();

			while (itrNames.hasNext() && itrMappings.hasNext()) {
				Map<String, EObject> mapping = itrMappings.next();
				detailedInfo.append("\n" + itrNames.next() + "\n-------------------\n");
				mapping.forEach((label, node) -> detailedInfo.append(label + " ---> " + "[" + node + "]" + "\n"));
			}

			return getMessage(projectName) + "\n\nI got stuck while trying to extend the following source matches: \n"
					+ detailedInfo;
		}
	}
}
