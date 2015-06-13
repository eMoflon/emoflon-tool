package org.moflon.ide.ui.saveaction;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.jdt.ui.cleanup.ICleanUpFix;
import org.eclipse.text.edits.MultiTextEdit;
import org.moflon.ide.ui.admin.handlers.CreateInjectionHandler;

public class CreateInjectionFix implements ICleanUpFix
{

   private ICompilationUnit compilationUnit;

   public CreateInjectionFix(final ICompilationUnit compilationUnit)
   {
      this.compilationUnit = compilationUnit;
   }

   /**
    * This method schedules a new job for creating an injection from the stored compilation unit.
    */
   @Override
   public CompilationUnitChange createChange(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         CompilationUnitChange change = new CompilationUnitChange(CreateInjectionsSaveAction.DESCRIPTION, this.compilationUnit);
         change.setEdit(new MultiTextEdit());
         final WorkspaceJob job = new CreateInjectionJob("Creating injections job", compilationUnit);
         job.schedule();
         return change;
      } finally
      {
         if (monitor != null)
            monitor.done();
      }
   }

   private static final class CreateInjectionJob extends WorkspaceJob
   {
      private ICompilationUnit compilationUnit;

      /**
       * Creates a job for storing injections.
       * 
       * The job's scheduling rule will lock the compilation unit's enclosing project.
       */
      private CreateInjectionJob(final String name, final ICompilationUnit compilationUnit)
      {
         super(name);
         this.compilationUnit = compilationUnit;
         this.setRule(this.compilationUnit.getJavaProject().getProject());
      }

      @Override
      public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException
      {
         try
         {
            final CreateInjectionHandler handler = new CreateInjectionHandler();
            final IFile javaFile = (IFile) compilationUnit.getResource();

            if (javaFile != null)
               handler.extractInjectionNonInteractively(javaFile);

            return Status.OK_STATUS;
         } finally
         {
            monitor.done();
         }
      }
   }
}
