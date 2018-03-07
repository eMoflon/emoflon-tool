package org.moflon.codegen.eclipse;

import java.lang.reflect.Field;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;

public final class PackageDescriptor implements Descriptor {
	private final String packagePluginID;
	private final String packageClassName;
	private final String factoryPluginID;
	private final String factoryClassName;

	public PackageDescriptor(final String packagePluginID, final String packageClassName, final String factoryPluginID,
			final String factoryClassName) {
		this.packagePluginID = packagePluginID;
		this.packageClassName = packageClassName;
		this.factoryPluginID = factoryPluginID;
		this.factoryClassName = factoryClassName;
	}

	@Override
	public EPackage getEPackage() {
		try {
			Class<?> clazz = CommonPlugin.loadClass(packagePluginID, packageClassName);
			Field eInstanceField = clazz.getField("eINSTANCE");
			Object result = eInstanceField.get(null);
			return (EPackage) result;
		} catch (ClassNotFoundException e) {
			throw new WrappedException(e);
		} catch (SecurityException e) {
			throw new WrappedException(e);
		} catch (IllegalArgumentException e) {
			throw new WrappedException(e);
		} catch (IllegalAccessException e) {
			throw new WrappedException(e);
		} catch (ClassCastException e) {
			throw new WrappedException(e);
		} catch (NoSuchFieldException e) {
			throw new WrappedException(e);
		}
	}

	@Override
	public EFactory getEFactory() {
		try {
			final Class<?> eFactoryClass = CommonPlugin.loadClass(factoryPluginID, factoryClassName);
			return (EFactory) eFactoryClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new WrappedException(e);
		} catch (InstantiationException e) {
			throw new WrappedException(e);
		} catch (IllegalAccessException e) {
			throw new WrappedException(e);
		}
	}
}
