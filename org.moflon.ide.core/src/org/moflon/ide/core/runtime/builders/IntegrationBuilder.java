package org.moflon.ide.core.runtime.builders;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;

public class IntegrationBuilder extends RepositoryBuilder
{
   public static final String BUILDER_ID = "org.moflon.ide.core.runtime.builders.IntegrationBuilder";

   @Override
   protected boolean processResource(final IProgressMonitor monitor) throws CoreException
   {
      if (!getPreEcoreFile(getProject()).exists() || !getPreTGGFile(getProject()).exists())
         return false;

      // Due to problems with dependencies, code generation is now no longer triggered but invoked explicitly
      CoreActivator.getDefault().setDirty(getProject(), true);

      monitor.done();

      return true;
   }

   @Override
   public boolean visit(final IResource resource) throws CoreException
   {
      // Make sure changes are from the right ecore file according to convention
      if (isPreEcoreFileOfProject(resource) || isPreTGGFileOfProject(resource))
      {
         final IProgressMonitor progressMonitorForIncrementalChanges = this.getProgressMonitorForIncrementalChanges();
         return processResource(WorkspaceHelper.createSubMonitor(progressMonitorForIncrementalChanges, 100));
      }

      return false;
   }

   private boolean isPreTGGFileOfProject(final IResource resource)
   {
      return resource.exists() && resource.getProjectRelativePath().toString().endsWith(WorkspaceHelper.PRE_TGG_FILE_EXTENSION);
   }

   private boolean isPreEcoreFileOfProject(final IResource resource)
   {
      return resource.exists() && resource.getProjectRelativePath().toString().endsWith(WorkspaceHelper.PRE_ECORE_FILE_EXTENSION);
   }

   public static IFile getPreTGGFile(final IProject project)
   {
      return project.getFile(MoflonUtil.getDefaultPathToFileInProject(project.getName(), WorkspaceHelper.PRE_TGG_FILE_EXTENSION));
   }

   public static IFile getPreEcoreFile(final IProject project)
   {
      return project.getFile(MoflonUtil.getDefaultPathToFileInProject(project.getName(), WorkspaceHelper.PRE_ECORE_FILE_EXTENSION));
   }

}
