package org.moflon.codegen.eclipse;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.dependency.PackageRemappingDependency;

import MoflonPropertyContainer.MoflonPropertiesContainer;

public final class MonitoredMetamodelLoader extends AbstractMonitoredMetamodelLoader
{


   public MonitoredMetamodelLoader(final ResourceSet resourceSet, final IFile ecoreFile, final MoflonPropertiesContainer moflonProperties)
   {
      super(resourceSet, ecoreFile, moflonProperties);
   }

   

   protected final void createResourcesForWorkspaceProjects(final IProgressMonitor monitor)
   {
      try
      {
         final IProject[] workspaceProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
         monitor.beginTask("Loading workspace projects", workspaceProjects.length);
         for (IProject workspaceProject : workspaceProjects)
         {
            try
            {
               if (isAccessible(workspaceProject))
               {
                  final URI projectURI = CodeGeneratorPlugin.lookupProjectURI(workspaceProject);
                  final URI metamodelURI = CodeGeneratorPlugin.getDefaultProjectRelativeEcoreFileURI(workspaceProject).resolve(projectURI);
                  new PackageRemappingDependency(metamodelURI, false, false).getResource(resourceSet, false, true);
               }
            } catch (CoreException e)
            {
               // Do nothing
            }
            monitor.worked(1);
         }
      } finally
      {
         monitor.done();
      }
   }



@Override
protected boolean isAccessible(IProject project) throws CoreException {	
		return project.isAccessible() && (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) || project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID));
}

}
