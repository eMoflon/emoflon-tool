package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceDescriptor;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class RenameGenmodelRefactoring implements RenameRefactoring
{

   private final String newName;

   public RenameGenmodelRefactoring(String newName)
   {
      this.newName = newName;
   }
   @Override
   public IStatus refactor(IProject project)
   {
      IResource genmodelResource = getGenmodelFile(project);
      
      if (!genmodelResource.exists())
         return new Status(IStatus.CANCEL, WorkspaceHelper.getPluginId(getClass()), "No Genmodel for refactoring found");
            
      RenameResourceDescriptor descriptor = new RenameResourceDescriptor();
      descriptor.setUpdateReferences(true);
      descriptor.setProject(project.getName());
      descriptor.setNewName(newName + ".genmodel");
      descriptor.setResourcePath(genmodelResource.getFullPath());

      RefactoringStatus status = new RefactoringStatus();
      try
      {
         Refactoring refactoring = descriptor.createRefactoring(status);

         IProgressMonitor monitor = new NullProgressMonitor();
         refactoring.checkInitialConditions(monitor);
         refactoring.checkFinalConditions(monitor);

         Change change = refactoring.createChange(monitor);
         change.perform(monitor);
         return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "Genmodel refactoring successful");
         
      } catch (CoreException e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problem during Genmodel refactoring", e);
      }
   }

   private IResource getGenmodelFile(final IProject p)
   {
      String genModelFileName = MoflonUtil.lastCapitalizedSegmentOf(p.getName());
      return p.getFolder(WorkspaceHelper.MODEL_FOLDER).getFile(genModelFileName + WorkspaceHelper.GEN_MODEL_EXT);
   }
}
