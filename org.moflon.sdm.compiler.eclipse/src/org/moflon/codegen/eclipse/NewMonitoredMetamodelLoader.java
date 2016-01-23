package org.moflon.codegen.eclipse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.dependency.PackageRemappingDependency;
import org.moflon.eclipse.job.IMonitoredJob;
import org.moflon.eclipse.resource.SDMEnhancedEcoreResourceFactory;

import MoflonPropertyContainer.AdditionalDependencies;
import MoflonPropertyContainer.Dependencies;
import MoflonPropertyContainer.MoflonPropertiesContainer;

// This class is the new metamodel loading process that would handle all kinds of projects correctly
public final class NewMonitoredMetamodelLoader implements IMonitoredJob
{
   private static final String TASK_NAME = "Metamodel loading";

   private final ResourceSet resourceSet;

   private final IFile ecoreFile;

   private final MoflonPropertiesContainer moflonProperties;

   private Resource ecoreResource;

   public NewMonitoredMetamodelLoader(final ResourceSet resourceSet, final IFile ecoreFile, final MoflonPropertiesContainer moflonProperties)
   {
      this.resourceSet = resourceSet;
      this.resourceSet.setPackageRegistry(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
      this.resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistryImpl());

      this.resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new SDMEnhancedEcoreResourceFactory());
      this.resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());

      this.ecoreFile = ecoreFile;
      this.moflonProperties = moflonProperties;
   }

   @Override
   public final IStatus run(final IProgressMonitor monitor)
   {
      try
      {
         monitor.beginTask(TASK_NAME + " task", 50);
         IProject project = ecoreFile.getProject();
         monitor.subTask("Loading metamodel for project " + project.getName());
         monitor.worked(5);

         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Prepare plugin to resource URI mapping
         // PluginURIToResourceURIRemapper.createPluginToResourceMap(resourceSet);

         // Create (unloaded) resources for all possibly dependent metamodels in Moflon-specific workspace projects
         createResourcesForWorkspaceProjects(WorkspaceHelper.createSubMonitor(monitor, 10));
         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Create resources for the user-defined dependent metamodels
         final List<Resource> resourcesToLoad = createResourcesForUserDefinedMetamodels(WorkspaceHelper.createSubMonitor(monitor, 10));
         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Load the user-defined dependent metamodels
         final IStatus userDefinedMetamodelLoaderStatus = loadUserDefinedMetamodels(resourcesToLoad, WorkspaceHelper.createSubMonitor(monitor, 10));
         if (!userDefinedMetamodelLoaderStatus.isOK())
         {
            return userDefinedMetamodelLoaderStatus;
         }
         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Load the main metamodel
         URI projectURI = URI.createPlatformResourceURI(project.getName() + "/", true);
         URI ecoreURI = URI.createURI(ecoreFile.getProjectRelativePath().toString()).resolve(projectURI);
         ecoreResource = resourceSet.getResource(ecoreURI, true);

         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         IStatus status = CodeGeneratorPlugin.validateResourceSet(resourceSet, TASK_NAME, WorkspaceHelper.createSubMonitor(monitor, 5));

         return status;
      } finally
      {
         monitor.done();
      }
   }

   public final Resource getEcoreResource()
   {
      return ecoreResource;
   }

   @Override
   public final String getTaskName()
   {
      return TASK_NAME;
   }

   private final void createResourcesForWorkspaceProjects(final IProgressMonitor monitor)
   {
      try
      {
         final List<Dependencies> dependencies = moflonProperties.getDependencies();
         monitor.beginTask("Creating resources for required metamodels", dependencies.size());
         for (Dependencies metamodel : dependencies)
         {
            URI uri = URI.createURI(metamodel.getValue());
            resourceSet.createResource(uri);

            monitor.worked(1);
         }
      } finally
      {
         monitor.done();
      }
   }

   private final List<Resource> createResourcesForUserDefinedMetamodels(final IProgressMonitor monitor)
   {
      try
      {
         final List<Resource> resourcesToLoad = new LinkedList<Resource>();
         final List<AdditionalDependencies> additionalDependencies = moflonProperties.getAdditionalDependencies();
         monitor.beginTask("Creating resources for user-defined metamodels", additionalDependencies.size());
         for (AdditionalDependencies userDefinedMetamodel : additionalDependencies)
         {
            URI uri = URI.createURI(userDefinedMetamodel.getValue());
            PackageRemappingDependency dependency = new PackageRemappingDependency(uri, true, false);
            resourcesToLoad.add(dependency.getResource(resourceSet, false));
            monitor.worked(1);
         }
         return resourcesToLoad;
      } finally
      {
         monitor.done();
      }
   }

   private final IStatus loadUserDefinedMetamodels(final List<Resource> resourcesToLoad, final IProgressMonitor monitor)
   {
      try
      {
         final MultiStatus resourceLoadingStatus = new MultiStatus(CodeGeneratorPlugin.getModuleID(), IStatus.OK, "Resource loading status", null);
         monitor.beginTask("Loading user-defined metamodels", resourcesToLoad.size());
         for (Resource resource : resourcesToLoad)
         {
            try
            {
               resource.load(null);
            } catch (IOException e)
            {
               resourceLoadingStatus.add(new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR, e.getMessage(), e));
            }
            monitor.worked(1);
         }
         return resourceLoadingStatus;
      } finally
      {
         monitor.done();
      }
   }
}
