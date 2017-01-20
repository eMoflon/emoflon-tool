package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.moflon.core.utilities.WorkspaceHelper;

public class RenameProjectRefactoring implements RenameRefactoring
{

   private final String currentValue;

   public RenameProjectRefactoring(String currentName)
   {
      this.currentValue = currentName;
   }
   
   @Override
   public IStatus refactor(IProject project)
   {
      return refactorProject(project);
   }
   
   private IStatus refactorProject(IProject project)
   {
      IJavaProject oldProject = JavaCore.create(project);

      if (!oldProject.exists())
         return new Status(IStatus.CANCEL, WorkspaceHelper.getPluginId(getClass()), "No Project for refactoring found");

      RefactoringContribution contribution = RefactoringCore.getRefactoringContribution(IJavaRefactorings.RENAME_JAVA_PROJECT);
      RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
      descriptor.setProject(null);
      descriptor.setUpdateReferences(true);
      descriptor.setNewName(currentValue);
      descriptor.setJavaElement(oldProject);

      RefactoringStatus status = new RefactoringStatus();
      try
      {
         Refactoring refactoring = descriptor.createRefactoring(status);

         IProgressMonitor monitor = new NullProgressMonitor();
         refactoring.checkInitialConditions(monitor);
         refactoring.checkFinalConditions(monitor);

         Change change = refactoring.createChange(monitor);
         change.perform(monitor);

      } catch (CoreException e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problem during refactoring", e);
      }
      return Status.OK_STATUS;
   }
}
