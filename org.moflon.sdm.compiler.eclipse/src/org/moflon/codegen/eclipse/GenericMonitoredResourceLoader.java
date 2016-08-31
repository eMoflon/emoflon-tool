package org.moflon.codegen.eclipse;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.dependency.PackageRemappingDependency;
import org.moflon.eclipse.job.IMonitoredJob;

public class GenericMonitoredResourceLoader implements IMonitoredJob
{
   private static final String TASK_NAME = "Resource loading";

   protected final ResourceSet resourceSet;

   protected final IFile file;

   private Resource resource;

   private List<Resource> resources;

   public GenericMonitoredResourceLoader(final ResourceSet resourceSet, final IFile file)
   {
      this.resourceSet = resourceSet;
      this.file = file;
   }

   @Override
   public final IStatus run(final IProgressMonitor monitor)
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, TASK_NAME + " task", 20);
      final IProject project = file.getProject();
      subMon.subTask("Loading metamodel for project " + project.getName());

      // Preprocess resource set
      final IStatus preprocessingStatus = preprocessResourceSet(subMon.newChild(5));
      if (preprocessingStatus.matches(IStatus.ERROR | IStatus.CANCEL))
      {
         return preprocessingStatus;
      }

      if (isAccessible(project))
      {
         // Load the file
         URI projectURI = URI.createPlatformResourceURI(project.getName() + "/", true);
         URI uri = URI.createURI(file.getProjectRelativePath().toString()).resolve(projectURI);
         try {
             resource = resourceSet.getResource(uri, true);
         } catch (final WrappedException e) {
             return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR, e.getCause().getMessage(), e.getCause());
         }
         subMon.worked(5);
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Postprocess resource set
         final IStatus postprocessingStatus = postprocessResourceSet(subMon.newChild(5));
         if (postprocessingStatus.matches(IStatus.ERROR | IStatus.CANCEL))
         {
            return postprocessingStatus;
         }

         return CodeGeneratorPlugin.validateResourceSet(resourceSet, TASK_NAME, subMon.newChild(5));
      } else
      {
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), "Project " + project.getName() + " is not accessible");
      }
   }

   public final List<Resource> getResources()
   {
      return resources;
   }

   public final Resource getMainResource()
   {
      return resource;
   }

   @Override
   public String getTaskName()
   {
      return TASK_NAME;
   }

   protected IStatus preprocessResourceSet(final IProgressMonitor monitor)
   {
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Preprocessing resource set", 15);
         // Prepare plugin to resource URI mapping
         CodeGeneratorPlugin.createPluginToResourceMapping(resourceSet, subMon.newChild(5));
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Create (unloaded) resources for all possibly dependent metamodels in workspace projects
         createResourcesForWorkspaceProjects(subMon.newChild(10));
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         return new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), "Preprocessing succeeded");
      } catch (final CoreException e)
      {
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), e.getMessage(), e);
      }
   }

   protected IStatus postprocessResourceSet(final IProgressMonitor monitor)
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Postprocessing resource set", 5);
      // Resolve cross-references
      final CrossReferenceResolver crossReferenceResolver = new CrossReferenceResolver(resource);
      crossReferenceResolver.run(subMon.newChild(5));
      resources = crossReferenceResolver.getResources();

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
      return new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), "Postprocessing succeeded");
   }

   protected void createResourcesForWorkspaceProjects(final IProgressMonitor monitor)
   {
      final IProject[] workspaceProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      final SubMonitor subMon = SubMonitor.convert(monitor, "Loading workspace projects", workspaceProjects.length);
      for (final IProject workspaceProject : workspaceProjects)
      {
         if (isAccessible(workspaceProject))
         {
            final URI projectURI = CodeGeneratorPlugin.lookupProjectURI(workspaceProject);
            final URI metamodelURI = CodeGeneratorPlugin.getDefaultProjectRelativeEcoreFileURI(workspaceProject).resolve(projectURI);
            new PackageRemappingDependency(metamodelURI, false, false).getResource(resourceSet, false, true);
         }
         subMon.worked(1);
      }
   }

   protected boolean isAccessible(IProject project)
   {
      try
      {
         return project.isAccessible() && (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) || project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID));
      } catch (final CoreException e)
      {
         return false;
      }
   }
}
