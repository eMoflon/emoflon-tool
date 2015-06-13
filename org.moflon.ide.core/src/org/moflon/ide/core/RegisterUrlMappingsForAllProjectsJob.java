package org.moflon.ide.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.core.utilities.WorkspaceHelper;

public final class RegisterUrlMappingsForAllProjectsJob extends Job
{
   public RegisterUrlMappingsForAllProjectsJob()
   {
      super("Moflon: Adding projects to registry");
   }

   @Override
   protected IStatus run(final IProgressMonitor monitor)
   {
      try
      {
         final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
         monitor.beginTask("Registering URL mappings", projects.length);
         for (IProject project : projects)
         {
            try
            {
               if (project.isOpen() && WorkspaceHelper.isMoflonProject(project))
                  CoreActivator.addMappingForProject(project);
            } catch (CoreException e)
            {
               e.printStackTrace();
               return new Status(IStatus.ERROR, CoreActivator.getModuleID(), IStatus.ERROR, "", null);
            }

            monitor.worked(1);

         }
      } finally
      {
         monitor.done();
      }
      return new Status(IStatus.OK, CoreActivator.getModuleID(), IStatus.OK, "", null);
   }
}