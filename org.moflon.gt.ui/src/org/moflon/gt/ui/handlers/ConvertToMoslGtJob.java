package org.moflon.gt.ui.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.ide.builders.EMoflonGTBuilder;
import org.moflon.ide.core.CoreActivator;

/**
 * This job triggers the conversion of an SDM-based project to a MOSL-GT-based project
 * 
 * @author Roland Kluge - Initial implementation
 */
public class ConvertToMoslGtJob extends WorkspaceJob
{

   private IProject project;

   public ConvertToMoslGtJob(final IProject project)
   {
      super("Converting " + project + " to MOSL-GT");
      this.project = project;
   }

   @Override
   public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException
   {
      final IFile ecoreFile = findEcoreFile();
      if (null == ecoreFile)
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Unable to find Ecore file in " + getProject());

      final ResourceSet resourceSet = initializeResourceSet();

      final ConvertToMoslGtProcess conversionProcess = new ConvertToMoslGtProcess(ecoreFile, resourceSet, CoreActivator.getDefault().getPreferencesStorage());
      final IStatus processStatus = conversionProcess.run(monitor);
      if (processStatus.matches(IStatus.ERROR))
         return processStatus;
      else
         return Status.OK_STATUS;
   }

   private IFile findEcoreFile()
   {
      return WorkspaceHelper.getDefaultEcoreFile(getProject());
   }

   private ResourceSet initializeResourceSet()
   {
      return EMoflonGTBuilder.initializeResourceSet();
   }

   private IProject getProject()
   {
      return this.project;
   }

}
