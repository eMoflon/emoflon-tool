package org.moflon.ide.metamodelevolution.core.processing;

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
import org.moflon.ide.metamodelevolution.core.RenameChange;

public class RenameProjectRefactoring implements RenameRefactoring {

	@Override
	public void refactor(IProject project, RenameChange change) {
		refactorProject(project, change);
	}
	
	private void refactorProject(IProject project, RenameChange renaming) 
	{
			try {
				project.delete(true, new NullProgressMonitor());
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
			
		  IProject oldProject = WorkspaceHelper.getProjectByName(renaming.getPreviousValue());
		  IJavaProject oldJavaProject = JavaCore.create(oldProject);
		  
	      if (!oldJavaProject.exists())
		         return;
	      
	      RefactoringContribution contribution = RefactoringCore.getRefactoringContribution(IJavaRefactorings.RENAME_JAVA_PROJECT);
	      RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
	      descriptor.setUpdateReferences(true);
	      descriptor.setNewName("test");
	      descriptor.setJavaElement(oldJavaProject);
	      
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
	         // TODO@settl: Return an appropriate status and/or log if
	         // status.severity() == IStatus.ERROR (RK)
	         e.printStackTrace();
	         new Status(IStatus.ERROR, "", "Problem during refactoring", e);
	      }
	   }
}
