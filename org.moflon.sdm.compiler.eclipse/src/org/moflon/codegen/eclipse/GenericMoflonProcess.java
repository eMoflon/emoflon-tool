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
package org.moflon.codegen.eclipse;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.eclipse.job.IMonitoredJob;
import org.moflon.properties.MoflonPropertiesContainerHelper;

import MoflonPropertyContainer.MoflonPropertiesContainer;

abstract public class GenericMoflonProcess implements IMonitoredJob {
	private final IFile ecoreFile;
	private final ResourceSet resourceSet;
	private List<Resource> resources;
	private MoflonPropertiesContainer moflonProperties;

	public GenericMoflonProcess(final IFile ecoreFile, final ResourceSet resourceSet) {
		this.ecoreFile = ecoreFile;
		this.resourceSet = resourceSet;
	}

	@Override
	public final IStatus run(final IProgressMonitor monitor) {
		try {
		   monitor.beginTask(getTaskName(), 10);
			try {
				// (1) Loads moflon.properties file
				final IProject project = ecoreFile.getProject();
				final URI projectURI = URI.createPlatformResourceURI(project.getName() + "/", true);
				final URI moflonPropertiesURI = URI.createURI(MoflonPropertiesContainerHelper.MOFLON_CONFIG_FILE).resolve(projectURI);
				final Resource moflonPropertiesResource =
						CodeGeneratorPlugin.createDefaultResourceSet().getResource(moflonPropertiesURI, true);
				this.moflonProperties = (MoflonPropertiesContainer) moflonPropertiesResource.getContents().get(0);

				monitor.worked(1);
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
			} catch (WrappedException wrappedException) {
				final Exception exception = wrappedException.exception();
				return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), exception.getMessage(), exception);
			} catch (RuntimeException runtimeException) {
				return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), runtimeException.getMessage(), runtimeException);
			}

			// (2) Load metamodel
			final MonitoredMetamodelLoader metamodelLoader =
					new MonitoredMetamodelLoader(resourceSet, ecoreFile, moflonProperties);
			final IStatus metamodelLoaderStatus = metamodelLoader.run(WorkspaceHelper.createSubMonitor(monitor, 2));
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			if (metamodelLoaderStatus.matches(IStatus.ERROR)) {
				return metamodelLoaderStatus;
			}
			this.resources = metamodelLoader.getResources();

			return processResource(WorkspaceHelper.createSubMonitor(monitor, 7));
		} finally {
			monitor.done();
		}
	}
	
	abstract public IStatus processResource(final IProgressMonitor monitor);
	
	public final IFile getEcoreFile() {
		return ecoreFile;
	}
	
	public final ResourceSet getResourceSet() {
		return resourceSet;
	}
	
	public final MoflonPropertiesContainer getMoflonProperties() {
		return moflonProperties;
	}
	
	public final Resource getEcoreResource() {
		return resources.get(0);
	}
	
	public final List<Resource> getAllResources() {
		return resources;
	}
}
