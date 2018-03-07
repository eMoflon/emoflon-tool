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
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class AdapterResource extends XMIResourceImpl implements Adapter.Internal {

	protected Notifier target = null;

	public AdapterResource(URI uri) {
		super(uri);
	}

	public boolean isAdapterForType(Object type) {
		return uri.fileExtension().equals(type);
	}

	public void notifyChanged(Notification msg) {
		// Do nothing.
	}

	public Notifier getTarget() {
		return target;
	}

	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}

	public void unsetTarget(Notifier oldTarget) {
		if (target == oldTarget) {
			setTarget(null);
		}
	}
}
