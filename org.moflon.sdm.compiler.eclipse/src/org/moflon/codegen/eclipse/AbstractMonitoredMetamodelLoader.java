package org.moflon.codegen.eclipse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.dependency.PackageRemappingDependency;
import org.moflon.eclipse.job.IMonitoredJob;

import MoflonPropertyContainer.AdditionalDependencies;
import MoflonPropertyContainer.MoflonPropertiesContainer;

public abstract class AbstractMonitoredMetamodelLoader implements IMonitoredJob
{
   private static final String TASK_NAME = "Metamodel loading";

   protected final ResourceSet resourceSet;

   protected final IFile ecoreFile;

   private final MoflonPropertiesContainer moflonProperties;

   private final UniqueEList<Resource> resources = new UniqueEList<Resource>();

   public AbstractMonitoredMetamodelLoader(final ResourceSet resourceSet, final IFile ecoreFile, final MoflonPropertiesContainer moflonProperties)
   {
      this.resourceSet = resourceSet;
      this.resourceSet.setPackageRegistry(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
      this.resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistryImpl());
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
         for (IProject workspaceProject : ResourcesPlugin.getWorkspace().getRoot().getProjects())
         {
            CodeGeneratorPlugin.createPluginToResourceMapping(resourceSet, workspaceProject);
         }

         // Create (unloaded) resources for all possibly dependent metamodels in Moflon-specific workspace projects
         createResourcesForWorkspaceProjects(WorkspaceHelper.createSubMonitor(monitor, 10));
         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Always load Ecore metamodel
         PackageRemappingDependency ecoreMetamodelDependency = new PackageRemappingDependency(
               URI.createURI("platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore"), true, true);
         ecoreMetamodelDependency.getResource(resourceSet, true);

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
         if (isAccessible(project))
         {
            final URI projectURI = CodeGeneratorPlugin.lookupProjectURI(project);
            final URI metamodelURI = URI.createURI(ecoreFile.getProjectRelativePath().toString()).resolve(projectURI);
            final Resource ecoreResource = new PackageRemappingDependency(metamodelURI, false, false).getResource(resourceSet, true, false);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            // Explore dependencies by resolving cross-references
            MultiStatus crossReferenceResolutionStatus = new MultiStatus(CodeGeneratorPlugin.getModuleID(), 0, "Cross reference resolution failed", null);
            resources.add(ecoreResource);
            for (int i = 0; i < resources.size(); i++)
            {
               final Resource resource = resources.get(i);
               for (final TreeIterator<EObject> j = resource.getAllContents(); j.hasNext();)
               {
                  final EObject eObject = j.next();
                  if (eObject instanceof EDataType)
                  {
                     j.prune();
                  }
                  for (final EObject eCrossReference : eObject.eCrossReferences())
                  {
                     if (eCrossReference instanceof EClass)
                     {
                        if (eCrossReference.eIsProxy())
                        {
                           final String proxyURI = eCrossReference instanceof InternalEObject ? ((InternalEObject) eCrossReference).eProxyURI().toString()
                                 + " " : "";
                           crossReferenceResolutionStatus.add(new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), "Unresolved cross reference "
                                 + proxyURI + "in " + EcoreUtil.getURI(eObject)));
                        } else
                        {
                           final EPackage referencedEPackage = ((EClass) eCrossReference).getEPackage();
                           if (resource != referencedEPackage.eResource())
                           {
                              resources.add(referencedEPackage.eResource());
                           }
                        }
                     }
                  }
               }
            }
            if (!crossReferenceResolutionStatus.isOK())
            {
               return crossReferenceResolutionStatus;
            }
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            // Remove unloaded resources from resource set
            final List<Resource> resources = resourceSet.getResources();
            for (int i = 0; i < resources.size(); i++)
            {
               final Resource resource = resources.get(i);
               if (!resource.isLoaded())
               {
                  resources.remove(i--);
               }
            }

            IStatus status = CodeGeneratorPlugin.validateResourceSet(resourceSet, TASK_NAME, WorkspaceHelper.createSubMonitor(monitor, 5));

            monitor.done();
            return status;
         } else
         {
            return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), "Project " + project.getName()
                  + "is either closed or does not have a recognized Moflon nature");
         }
      } catch (CoreException e)
      {
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), e.getMessage(), e);
      } finally
      {
         monitor.done();
      }
   }

   public final List<Resource> getResources()
   {
      return resources;
   }

   public final Resource getEcoreResource()
   {
      return resources.get(0);
   }

   @Override
   public final String getTaskName()
   {
      return TASK_NAME;
   }

   protected abstract void createResourcesForWorkspaceProjects(final IProgressMonitor monitor);
   
   protected abstract boolean isAccessible(IProject project) throws CoreException;

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
