package org.moflon.ide.ui.admin.wizards.mosl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;


public abstract class AbstractMOSLWizard extends Wizard implements INewWizard
{

   protected abstract void doFinish(IProgressMonitor monitor) throws CoreException;

   protected IResource resource = null;

   public AbstractMOSLWizard()
   {
      super();
      setNeedsProgressMonitor(true);
   }

   @Override
   public void init(final IWorkbench workbench, final IStructuredSelection selection)
   {
         Object obj = null;
         if (selection instanceof TreeSelection)
         {
            obj = ((TreeSelection) selection).getFirstElement();
         } else if (selection instanceof StructuredSelection)
         {
            obj = ((StructuredSelection) selection).getFirstElement();
         }
         // Get what the user selected in the workspace as a resource
         if (obj != null && obj instanceof IResource)
         {
            resource = (IResource) obj;
         } else
         {
            resource = null;
         }
   }

   @Override
   public boolean performFinish()
   {
      IRunnableWithProgress op = new IRunnableWithProgress() {
         @Override
         public void run(final IProgressMonitor monitor) throws InvocationTargetException
         {
            try
            {
               doFinish(monitor);
            } catch (CoreException e)
            {
               throw new InvocationTargetException(e);
            } finally
            {
               monitor.done();
            }
         }
      };
   
      try
      {
         getContainer().run(true, false, op);
      } catch (InterruptedException e)
      {
         return false;
      } catch (InvocationTargetException e)
      {
         Throwable realException = e.getTargetException();
         MessageDialog.openError(getShell(), "Error", realException.getMessage());
         return false;
      }
   
      return true;
   }

   protected void openEditor(final IFile ruleFile)
   {
      getShell().getDisplay().asyncExec(new Runnable() {
         @Override
         public void run()
         {
            IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            try
            {
               IDE.openEditor(workbenchPage, ruleFile);
            } catch (PartInitException e)
            {
               e.printStackTrace();
            }
         }
      });
   }

}