package org.moflon.compiler.sdm.democles.stringtemplate;

import java.util.Locale;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.stringtemplate.v4.AttributeRenderer;

public class EClassifierRenderer implements AttributeRenderer {
	private GenModel genModel;

	public EClassifierRenderer(GenModel genModel) {
		this.genModel = genModel;
	}

	@Override
	public String toString(Object o, String arg1, Locale arg2) {
		if (o instanceof EClass) {
			EClass eClass = (EClass) o;
			GenClass genClass = (GenClass) genModel.findGenClassifier(eClass);
			if (!genClass.isExternalInterface() && (!genModel.isSuppressInterfaces() || genClass.isInterface())) {
				StringBuilder sb = new StringBuilder();
				sb.append(genClass.getGenPackage().getInterfacePackageName());
				sb.append(".");
				sb.append(genClass.getInterfaceName());
				sb.append(genClass.getTypeParameters().trim());
				return sb.toString();
			} else if (!genClass.isInterface()) {
				StringBuilder sb = new StringBuilder();
				sb.append(genClass.getGenPackage().getClassPackageName());
				sb.append(".");
				sb.append(genClass.getClassName());
				sb.append(genClass.getTypeParameters().trim());
				return sb.toString();
			}
		} else if (o instanceof EEnum) {
			GenEnum genEnum = (GenEnum) genModel.findGenClassifier((EEnum) o);
			StringBuilder sb = new StringBuilder();
			sb.append(genEnum.getGenPackage().getInterfacePackageName());
			sb.append(".");
			sb.append(genEnum.getName());
			return sb.toString();

		} else if (o instanceof EDataType) {
			return ((EDataType) o).getInstanceClassName();
		}
		return null;
	}
}
