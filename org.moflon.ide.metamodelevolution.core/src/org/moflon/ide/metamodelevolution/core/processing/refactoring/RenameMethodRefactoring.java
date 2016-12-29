package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;

public class RenameMethodRefactoring implements RenameRefactoring
{
   private static final Logger logger = Logger.getLogger(RenameMethodRefactoring.class);

   private final String oldName;

   private final String newName;

   private final String packagePath;
   
   private final String[] parameters;
   
   private final String className;
   
   public RenameMethodRefactoring(String oldName, String newName, String packagePath, String[] parameters, String className)
   {
      this.oldName = oldName;
      this.newName = newName;
      this.packagePath = "/" + packagePath.replaceAll("\\.", "/") + "/";
      if (parameters == null)
      {
         this.parameters = new String[0];
      }
      else 
      {
         this.parameters = parameters;
      }
      this.className = className;
   }
   
   @Override
   public IStatus refactor(IProject project)
   {
      RefactoringContribution contribution = RefactoringCore.getRefactoringContribution(IJavaRefactorings.RENAME_METHOD);
      RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();

      descriptor.setUpdateReferences(true);
      descriptor.setProject(project.getName());
      descriptor.setNewName(newName);

      IFile file = project.getFile(new Path(GEN_FOLDER + packagePath + className) + JAVA_EXTENSION);
      ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);

      if (cu.exists())
      {
         IMethod method;
         try
         {
            method = findMethod(cu, oldName);
            if (method != null)
               descriptor.setJavaElement(method);
            else
               return new Status(IStatus.CANCEL, WorkspaceHelper.getPluginId(getClass()), "No matching method for refactoring found");
         } catch (JavaModelException e)
         {
            LogUtils.error(logger, e);
         }
      } else
         return new Status(IStatus.CANCEL, WorkspaceHelper.getPluginId(getClass()), "No CompilationUnit for refactoring found");;

      RefactoringStatus status = new RefactoringStatus();
      try
      {
         Refactoring refactoring = descriptor.createRefactoring(status);

         IProgressMonitor monitor = new NullProgressMonitor();
         refactoring.checkInitialConditions(monitor);
         refactoring.checkFinalConditions(monitor);

         Change change = refactoring.createChange(monitor);
         change.perform(monitor);
         return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "Method Refactoring successfull");
      }

      catch (CoreException e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problem during refactoring", e);
      }
   }

   private IMethod findMethod(ICompilationUnit cu, String methodName) throws JavaModelException
   {
      try
      {
         IMethod[] methods = cu.findPrimaryType().getMethods();
         for (IMethod method : methods)
         {
            if (method.getElementName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameters))
               return method;
         }
      } catch (JavaModelException e)
      {
         LogUtils.error(logger, e);
      }
      return null;
   }
}
