package org.moflon.core.ecore2mocaxmi.utils;

import java.util.List;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;

public class PathProvider {

	public static String getNewPath(String oldPath, ENamedElement element) {
		StringBuilder sb = new StringBuilder();
		sb.append(oldPath).append('/').append(element.getName());
		if (element instanceof EOperation)
			getOperationPath(EOperation.class.cast(element).getEParameters(), sb);
		return sb.toString();
	}

	private static void getOperationPath(List<EParameter> parameters, StringBuilder sb) {
		sb.append('(');
		for (int index = 0; index < parameters.size(); index++) {
			sb.append(parameters.get(index).getName()).append(':').append(parameters.get(index).getEType().getName());
			if (index != parameters.size() - 1)
				sb.append(',');
		}
		sb.append(')');
	}

}
