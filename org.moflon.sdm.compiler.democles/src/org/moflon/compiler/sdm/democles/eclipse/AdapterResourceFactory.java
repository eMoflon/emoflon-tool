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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;

abstract public class AdapterResourceFactory<E extends Notifier> extends AdapterFactoryImpl
		implements Resource.Factory {
	private String extensionName;
	private EClassifier eClassifier;

	protected AdapterResourceFactory(String extensionName, EClassifier eClassifier) {
		this.extensionName = extensionName;
		this.eClassifier = eClassifier;
	}

	@SuppressWarnings("unchecked")
	protected Adapter createAdapter(Notifier target, Object type) {
		if (isFactoryForType(target, type)) {
			return createResource(getURI((E) target, (String) type));
		}
		return null;
	}

	abstract protected URI getURI(E target, String type);

	protected final boolean isFactoryForType(Notifier target, Object type) {
		return type instanceof String && isFactoryForType(type) && getEClassifier().isInstance(target);
	}

	protected final EClassifier getEClassifier() {
		return eClassifier;
	}

	public Adapter createAdapter(Notifier target) {
		return null;
	}

	public boolean isFactoryForType(Object object) {
		return extensionName.equals(object);
	}

	public AdapterResource createResource(URI uri) {
		return new AdapterResource(uri);
	}
}
