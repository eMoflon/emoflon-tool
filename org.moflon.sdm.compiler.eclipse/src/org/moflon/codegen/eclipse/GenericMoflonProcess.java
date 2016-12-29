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
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.task.ITask;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.WorkspaceHelper;

/**
 * This class defines a generic process for processing eMoflon projects.
 * 
 * @see #run(IProgressMonitor)
 */
public abstract class GenericMoflonProcess implements ITask
{
   private final IFile ecoreFile;

   private final ResourceSet resourceSet;

   private List<Resource> resources;

   private MoflonPropertiesContainer moflonProperties;

   public GenericMoflonProcess(final IFile ecoreFile, final ResourceSet resourceSet)
   {
      this.ecoreFile = ecoreFile;
      this.resourceSet = resourceSet;
   }

   /**
    * Loads moflon.properties.xmi and the project's meta-model from the specified Ecore file (see constructor).
    * 
    * The control flow then continues to {@link GenericMoflonProcess#processResource(IProgressMonitor)}.
    * 
    * @see #processResource(IProgressMonitor)
    */
   @Override
   public final IStatus run(final IProgressMonitor monitor)
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, getTaskName(), 10);
      
      if (!ecoreFile.exists())
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Ecore file does not exist. Expected location: " + ecoreFile);
      
      try
      {
         // (1) Loads moflon.properties file
         final IProject project = ecoreFile.getProject();
         final URI projectURI = URI.createPlatformResourceURI(project.getName() + "/", true);
         final URI moflonPropertiesURI = URI.createURI(MoflonPropertiesContainerHelper.MOFLON_CONFIG_FILE).resolve(projectURI);
         final Resource moflonPropertiesResource = CodeGeneratorPlugin.createDefaultResourceSet().getResource(moflonPropertiesURI, true);
         this.moflonProperties = (MoflonPropertiesContainer) moflonPropertiesResource.getContents().get(0);

         subMon.worked(1);
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
      } catch (WrappedException wrappedException)
      {
         final Exception exception = wrappedException.exception();
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), exception.getMessage(), exception);
      } catch (RuntimeException runtimeException)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), runtimeException.getMessage(), runtimeException);
      }

      // (2) Load metamodel
      final MonitoredMetamodelLoader metamodelLoader = new MonitoredMetamodelLoader(resourceSet, ecoreFile, moflonProperties);
      final IStatus metamodelLoaderStatus = metamodelLoader.run(subMon.split(2));
      if (subMon.isCanceled())
      {
         return Status.CANCEL_STATUS;
      }
      if (metamodelLoaderStatus.matches(IStatus.ERROR))
      {
         return metamodelLoaderStatus;
      }
      this.resources = metamodelLoader.getResources();

      // Delegate to the subclass
      return processResource(subMon.split(7));
   }

   abstract public IStatus processResource(final IProgressMonitor monitor);

   public final IFile getEcoreFile()
   {
      return ecoreFile;
   }

   public final ResourceSet getResourceSet()
   {
      return resourceSet;
   }

   /**
    * Returns the stored properties.
    * 
    * May be called only after executing {@link #run(IProgressMonitor)} and within
    * {@link #processResource(IProgressMonitor)}.
    */
   public final MoflonPropertiesContainer getMoflonProperties()
   {
      return moflonProperties;
   }

   public final Resource getEcoreResource()
   {
      return resources.get(0);
   }

   public final List<Resource> getAllResources()
   {
      return resources;
   }
}
