package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.MoveDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class MoveAttributeRefactoring
{

   private static final Logger logger = Logger.getLogger(MoveAttributeRefactoring.class);
	
   public void refactor(IProject project, String oldClassName, String newClassName) throws JavaModelException
   {
      RefactoringContribution contribution = RefactoringCore.getRefactoringContribution(IJavaRefactorings.MOVE);
      MoveDescriptor descriptor = (MoveDescriptor) contribution.createDescriptor();
      descriptor.setUpdateReferences(true);
      descriptor.setUpdateQualifiedNames(true);
      descriptor.setProject(project.getName());

      IFile oldFile = project.getFile(new Path("/gen/org/emoflon/TopologyMetamodel/" + oldClassName + ".java"));
      ICompilationUnit oldClass = JavaCore.createCompilationUnitFrom(oldFile);

      if (oldClass.exists())
         descriptor.setMoveMembers(null);

      IFile newFile = project.getFile(new Path("/gen/org/emoflon/TopologyMetamodel/" + newClassName + ".java"));
      ICompilationUnit newClass = JavaCore.createCompilationUnitFrom(newFile);

      if (newClass.exists())
         descriptor.setDestination(newClass);

      RefactoringStatus status = new RefactoringStatus();
      try
      {
         Refactoring refactoring = descriptor.createRefactoring(status);

         IProgressMonitor monitor = new NullProgressMonitor();
         refactoring.checkInitialConditions(monitor);
         refactoring.checkFinalConditions(monitor);

         Change change = refactoring.createChange(monitor);
         change.perform(monitor);
      }

      catch (CoreException e)
      {
    	  logger.error("An Exception has been Thrown", e);
      }

   }
}
