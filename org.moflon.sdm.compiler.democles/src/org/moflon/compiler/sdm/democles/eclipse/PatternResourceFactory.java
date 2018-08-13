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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.moflon.core.utilities.EcoreUtils;

public final class PatternResourceFactory extends AdapterResourceFactory<EClass> {

	public PatternResourceFactory(String extensionName) {
		super(extensionName, EcorePackage.eINSTANCE.getEClass());
	}

	@Override
	protected final URI getURI(EClass eClass, String type) {
		return getClassURI(eClass).appendFileExtension(type);
	}

	private static final URI getClassURI(final EClass eClass) {
		return eClass.eResource().getURI().trimSegments(1).appendSegment(EcoreUtils.getFQN(eClass));
	}
}
