package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.moflon.ide.metamodelevolution.core.MetamodelCoevolutionPlugin;

public class RenamePackageRefactoring implements RenameRefactoring
{
	public final String oldPackageName;
	
	public final String newPackageName;
	
	public RenamePackageRefactoring(String oldPckgName, String newPckgName)
	{
		this.oldPackageName = oldPckgName;
		this.newPackageName = newPckgName;	
	}
	
   @Override
   public IStatus refactor(IProject project)
   {
      try
      {
         if(project.hasNature("org.eclipse.jdt.core.javanature")) {
            
            return refactorPackage(project);
         }
      } catch (CoreException e)
      {
         return new Status(IStatus.ERROR, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "Problem during RenamePackage Refactoring", e);
      }
      return Status.OK_STATUS;
   }
   
   private IStatus refactorPackage(IProject project)
   {
	  IJavaProject javaProject = JavaCore.create(project);
      IPackageFragment[] packages;
      
      RefactoringContribution contribution = RefactoringCore.getRefactoringContribution(IJavaRefactorings.RENAME_PACKAGE);
      RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution.createDescriptor();
      descriptor.setUpdateReferences(true);
      descriptor.setProject(project.getName());      
      descriptor.setNewName(newPackageName);   
      
      try
      {	
    	 boolean hasElement = false;
         packages = javaProject.getPackageFragments(); 
         for (IPackageFragment pckg : packages) {
            if (pckg.getKind() == IPackageFragmentRoot.JAVA_MODEL) { 
            	if(pckg.getElementName().equals(oldPackageName))
            	{
                	descriptor.setJavaElement(pckg);
                	hasElement = true;
                	break;
            	}

            }
         }
         if (!hasElement)
        	 return new Status(IStatus.CANCEL, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "No EPackage for refactoring found");
        
         RefactoringStatus status = new RefactoringStatus();
         Refactoring refactoring = descriptor.createRefactoring(status);

         IProgressMonitor monitor = new NullProgressMonitor();
         refactoring.checkInitialConditions(monitor);
         refactoring.checkFinalConditions(monitor);

         Change change = refactoring.createChange(monitor);
         change.perform(monitor);

			// TODO@fstallmeyer: Return an appropriate status and/or log if
            // status.severity() == IStatus.ERROR (RK)
                    
      } catch (Exception e)
      {
         return new Status(IStatus.ERROR, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "Problem during refactoring", e);
      }
      return Status.OK_STATUS;     
   }
}
