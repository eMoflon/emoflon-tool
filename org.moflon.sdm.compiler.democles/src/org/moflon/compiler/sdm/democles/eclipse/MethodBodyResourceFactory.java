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
package org.moflon.compiler.sdm.democles.eclipse;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EcorePackage;
import org.moflon.codegen.CodeGenerator;

public class MethodBodyResourceFactory extends AdapterResourceFactory<EOperation> {

	public MethodBodyResourceFactory(String extensionName) {
		super(extensionName, EcorePackage.eINSTANCE.getEOperation());
	}

	@Override
	protected final URI getURI(EOperation eOperation, String type) {
		return CodeGenerator.getOperationURI(eOperation).appendFileExtension(type);
	}
}
