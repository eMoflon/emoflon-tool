package org.moflon.ide.core.runtime.builders;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.codegeneration.RepositoryCodeGenerator;
import org.moflon.properties.MoflonPropertiesContainerHelper;

import MoflonPropertyContainer.MoflonPropertiesContainer;

public class RepositoryBuilder extends AbstractBuilder
{

   protected boolean generateSDMs = true;
   public static final String BUILDER_ID = "org.moflon.ide.core.runtime.builders.RepositoryBuilder";

   @Override
   protected void cleanResource(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Cleaning " + getProject(), 4);

         final IProject project = getProject();

         // Remove all problem markers
         project.deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
         project.deleteMarkers(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID, false, IResource.DEPTH_INFINITE);
         project.deleteMarkers(WorkspaceHelper.INJECTION_PROBLEM_MARKER_ID, false, IResource.DEPTH_INFINITE);
         monitor.worked(1);

         // Remove generated code
         cleanFolderButKeepHiddenFiles(project.getFolder(WorkspaceHelper.GEN_FOLDER), WorkspaceHelper.createSubMonitor(monitor, 1));

         // Remove debug data
         cleanFolderButKeepHiddenFiles(project.getFolder(WorkspaceHelper.DEBUG_FOLDER), WorkspaceHelper.createSubMonitor(monitor, 1));

         // Remove generated model files
         cleanModels(project.getFolder(WorkspaceHelper.MODEL_FOLDER), WorkspaceHelper.createSubMonitor(monitor, 1));
      } finally
      {
         monitor.done();
      }
   }

   @Override
   protected boolean processResource(final IProgressMonitor monitor) throws CoreException
   {
      // Due to problems with dependencies, code generation is now no longer triggered but invoked explicitly
      CoreActivator.getDefault().setDirty(getProject(), true);

      return true;
   }

   @Override
   public boolean visit(final IResource resource) throws CoreException
   {
      // Make sure changes are from the right ecore file according to convention
      if (RepositoryCodeGenerator.isEcoreFileOfProject(resource, getProject()))
      {
         return processResource(WorkspaceHelper.createSubMonitor(this.getProgressMonitorForIncrementalChanges(), 100));
      }

      return false;
   }

   @Override
   public boolean visit(final IResourceDelta deltas) throws CoreException
   {
      // Get changes and call visit on all
      boolean buildSuccessful = false;
      final IResourceDelta[] changes = deltas.getAffectedChildren();
      for (final IResourceDelta delta : changes)
      {
         buildSuccessful = visit(delta.getResource());
         visit(delta);
      }
      return buildSuccessful;
   }

   protected void cleanFolderButKeepHiddenFiles(final IFolder folder, final IProgressMonitor monitor) throws CoreException
   {
      if (!folder.exists())
         return;

      try
      {
         monitor.beginTask("Inspecting " + folder.getName(), 2 * folder.members().length);

         for (final IResource resource : folder.members())
         {
            // keep SVN data
            if (!resource.getName().startsWith("."))
            {
               if (WorkspaceHelper.isFolder(resource))
                  cleanFolderButKeepHiddenFiles((IFolder) resource, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
               else
                  monitor.worked(1);

               resource.delete(true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            } else
            {
               monitor.worked(2);
            }
         }
      } finally
      {
         monitor.done();
      }
   }

   // Delete generated models within model folder
   private void cleanModels(final IFolder folder, final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Inspecting " + folder.getName(), folder.members().length);

         for (final IResource resource : folder.members())
         {
            // keep SVN data
            if (!resource.getName().startsWith("."))
            {
               // only delete generated models directly in folder 'model'
               if (!WorkspaceHelper.isFolder(resource))
               {
                  MoflonPropertiesContainer properties = MoflonPropertiesContainerHelper.load(getProject(), WorkspaceHelper.createSubmonitorWith1Tick(monitor));
                  if (properties.getReplaceGenModel().isBool() && resource.getName().endsWith(WorkspaceHelper.GEN_MODEL_EXT))
                     resource.delete(true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
                  else
                     monitor.worked(1);

                  if (WorkspaceHelper.isIntegrationProject(getProject()) && isAGeneratedFileInIntegrationProject(resource))
                     resource.delete(true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
                  else
                     monitor.worked(1);
               }
            } else
            {
               monitor.worked(1);
            }
         }
      } finally
      {
         monitor.done();
      }
   }

   private boolean isAGeneratedFileInIntegrationProject(final IResource resource)
   {
      return !(resource.getName().endsWith(WorkspaceHelper.PRE_ECORE_FILE_EXTENSION) || resource.getName().endsWith(WorkspaceHelper.PRE_TGG_FILE_EXTENSION));
   }

}
