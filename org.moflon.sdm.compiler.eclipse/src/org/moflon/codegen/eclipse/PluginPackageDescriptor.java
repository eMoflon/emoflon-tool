package org.moflon.codegen.eclipse;

import java.lang.reflect.Field;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;
import org.osgi.framework.Bundle;

public class PluginPackageDescriptor implements Descriptor {
	private final String pluginID;
	private final String className;

	public PluginPackageDescriptor(String pluginID, String className) {
		this.pluginID = pluginID;
		this.className = className;
	}

	@Override
	public EPackage getEPackage() {
		try {
			Bundle bundle = Platform.getBundle(pluginID);
			if (bundle != null) {
				Class<?> clazz = bundle.loadClass(className);
				Field eInstanceField = clazz.getField("eINSTANCE");
				Object result = eInstanceField.get(null);
				return (EPackage) result;
			} else {
				throw new ClassNotFoundException(
						className + " cannot be loaded because because bundle " + pluginID + " cannot be resolved");
			}
		} catch (ClassNotFoundException e) {
			// Do nothing
		} catch (SecurityException e) {
			// Do nothing
		} catch (IllegalArgumentException e) {
			// Do nothing
		} catch (IllegalAccessException e) {
			// Do nothing
		} catch (ClassCastException e) {
			// Do nothing
		} catch (NoSuchFieldException e) {
			// Do nothing
		}
		return null;
	}

	@Override
	public EFactory getEFactory() {
		return null;
	}
}
