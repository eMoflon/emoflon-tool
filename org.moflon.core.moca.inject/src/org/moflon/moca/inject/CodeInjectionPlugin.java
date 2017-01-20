package org.moflon.moca.inject;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.osgi.framework.FrameworkUtil;

public final class CodeInjectionPlugin {
	
	public static final String getModuleID() {
		return FrameworkUtil.getBundle(CodeInjectionPlugin.class).getSymbolicName();
	}
	
	public static final String getInterfaceName(final GenClass genClass) {
		return genClass.getGenPackage().getInterfacePackageName() + "." + genClass.getInterfaceName();
	}

	public static final String getClassName(final GenClass genClass) {
		return genClass.getGenPackage().getClassPackageName() + "." + genClass.getClassName();
	}
}
