package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.moflon.core.utilities.WorkspaceHelper;

public class RenameClassRefactoring implements RenameRefactoring
{

   private boolean processInjections;

   private final String oldName;

   private final String newName;

   private final String packagePath;

   public RenameClassRefactoring(String oldName, String newName, String packagePath, boolean processInjections)
   {
      this.oldName = oldName;
      this.newName = newName;
      this.packagePath = packagePath;
      this.processInjections = processInjections;
   }

   @Override
   public IStatus refactor(IProject project)
   {
      return refactorClass(project);
   }

   private IStatus refactorClass(IProject project)
   {
      IFile file = project.getFile(new Path(GEN_FOLDER + WorkspaceHelper.formatPackagePath(packagePath) + oldName + JAVA_EXTENSION));
      ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);

      if (!cu.exists())
         return new Status(IStatus.CANCEL, WorkspaceHelper.getPluginId(getClass()), "No EClass for refactoring found");

      RefactoringContribution contribution = RefactoringCore.getRefactoringContribution(IJavaRefactorings.RENAME_COMPILATION_UNIT);
      RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
      descriptor.setUpdateReferences(true);
      descriptor.setProject(project.getName());
      descriptor.setNewName(newName);
      descriptor.setJavaElement(cu);

      RefactoringStatus status = new RefactoringStatus();
      try
      {
         Refactoring refactoring = descriptor.createRefactoring(status);

         IProgressMonitor monitor = new NullProgressMonitor();
         refactoring.checkInitialConditions(monitor);
         refactoring.checkFinalConditions(monitor);

         Change change = refactoring.createChange(monitor);
         change.perform(monitor);
         
         
         if (processInjections)
         {
            processInjections(project, file);
         }
         return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "EClass refactoring successful");
      } catch (CoreException e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problem during refactoring", e);
      }
   }

   /**
    * Updates the injections for the given original JavaFile.
    * 
    * The original injection file is first renamed to give version control a chance to record that this is NOT a
    * deletion of a versioned file and an addition of an unversioned file but rather a movement of a versioned file.
    */
   private void processInjections(IProject project, IFile previousJavaFile) throws CoreException
   {
      // Rename injection
      IFile previousInjectionFile = project.getFile(WorkspaceHelper.getPathToInjection(previousJavaFile));
      if (previousInjectionFile.exists())
      {
         final String newLastSegmentOfInjectionFile = previousInjectionFile.getProjectRelativePath().lastSegment().replace(oldName, newName);
         previousInjectionFile.move(Path.fromPortableString(newLastSegmentOfInjectionFile), true, new NullProgressMonitor());
      }
   }
}
