/*
 * Copyright (c) 2010-2012 Gergely Varro
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Gergely Varro - Initial API and implementation
 */
package org.moflon.eclipse.genmodel;

import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapter;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;
import org.moflon.moca.inject.InjectionManager;

public class GeneratorAdapterFactory extends GenModelGeneratorAdapterFactory {
	protected InjectionManager injectionManager;

	public Adapter createGenModelAdapter() {
		if (genModelGeneratorAdapter == null) {
			genModelGeneratorAdapter = new GenModelGeneratorAdapter(this) {
				public boolean canGenerateModel(Object object) {
					return false;
				}
			};
		}
		return genModelGeneratorAdapter;
	}

	public Adapter createGenPackageAdapter() {
		if (genPackageGeneratorAdapter == null) {
			genPackageGeneratorAdapter = new GenPackageGeneratorAdapter(this);
		}
		return genPackageGeneratorAdapter;
	}
	
	public final InjectionManager getInjectionManager() {
		return injectionManager;
	}
}
