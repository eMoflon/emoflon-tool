package org.moflon.ide.ui.admin.handlers;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.moca.inject.InjectionFile;

/**
 * UI handler that triggers the extraction/update of .inject files.
 */
public class CreateInjectionHandler extends AbstractCommandHandler
{
   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
      IFile javaFile = null;
      if (selection instanceof IStructuredSelection)
      {
         javaFile = extractFileFromStructuredSelection(selection);
      } else if (selection instanceof ITextSelection)
      {
         final IEditorPart editor = HandlerUtil.getActiveEditor(event);
         javaFile = (IFile) editor.getEditorInput().getAdapter(IFile.class);
      }

      if (javaFile != null)
      {
         extractInjectionInteractively(javaFile);
      }
      return null;
   }

   private IFile extractFileFromStructuredSelection(final ISelection selection)
   {
      IFile javaFile = null;
      final Object o = ((IStructuredSelection) selection).getFirstElement();
      if (o instanceof IFile)
      {
         javaFile = (IFile) o;
      } else if (o instanceof IJavaElement)
      {
         try
         {
            javaFile = getFileFromJavaElement((IJavaElement) o);
         } catch (final JavaModelException e)
         {
            logger.error("[Injection] Problem while reading Java file", e);
         }
      }
      return javaFile;
   }

   public void extractInjectionNonInteractively(final IFile javaFile)
   {
      this.extractInjection(javaFile, false);
   }

   public void extractInjectionInteractively(final IFile javaFile)
   {
      this.extractInjection(javaFile, true);
   }

   private void extractInjection(final IFile javaFile, final boolean runsInteractive)
   {
      try
      {
         // Stream needs to be re-opened for each check!
         if (isEmfUtilityClass(javaFile.getContents()) || isEmfUtilityInterface(javaFile.getContents()))
         {
            final String message = "It is not possible to create injections from EMF utility classes/interfaces.";
            if (runsInteractive)
            {
               MessageDialog.openWarning(Display.getDefault().getActiveShell(), "Injection error", message);
               logger.info(message + ". File: '" + javaFile.getFullPath() + "'.");
            } else
            {
               logger.debug(message + ". File: '" + javaFile.getFullPath() + "'.");
            }
         } else
         {
            final IPath fullInjectionPath = WorkspaceHelper.getPathToInjection(javaFile);
            final String fullyQualifiedClassname = WorkspaceHelper.getFullyQualifiedClassName(javaFile);

            // Determine contents of file
            final InputStream javaContentStream = javaFile.getContents();
            final String className = javaFile.getName().replace(".java", "");
            final InjectionFile injectionFile = new InjectionFile(javaContentStream, className);
            if (injectionFile.hasModelsOrImportsOrMembersCode())
            {
               final String injContent = injectionFile.getFileContent();

               // insert the contents
               final IProject project = javaFile.getProject();
               project.getFile(fullInjectionPath).delete(true, new NullProgressMonitor());
               logger.info(String.format("Created injection file for class %s (FQN='%s').", className, fullyQualifiedClassname));

               WorkspaceHelper.addAllFoldersAndFile(project, fullInjectionPath, injContent, new NullProgressMonitor());
            } else
            {
               logger.debug("Not creating injection file for  " + javaFile.getFullPath() + " because no model code was found.");
            }
         }
      } catch (final CoreException ex)
      {
         logger.error("Unable to create injection code for file " + javaFile + ". Reason: " + ex);
      }
   }

   private boolean isEmfUtilityClass(final InputStream javaContentStream)
   {
      final Pattern isEmfUtilClassPattern = Pattern
            .compile(".*public\\s+class\\s+[a-zA-Z1-9<>]+\\s+extends\\s+(AdapterFactoryImpl|Switch<T>|EPackageImpl|EFactoryImpl)[^a-zA-Z].*");
      final Scanner s = new Scanner(javaContentStream);
      String match = s.findWithinHorizon(isEmfUtilClassPattern, 0);
      s.close();
      return match != null;
   }

   private boolean isEmfUtilityInterface(final InputStream javaContentStream)
   {
      final Pattern isEmfUtilInterfacePattern = Pattern.compile(".*public\\s+interface\\s+[a-zA-Z1-9]+\\s+extends\\s+(EPackage|EFactory)[^a-zA-Z].*");
      final Scanner s = new Scanner(javaContentStream);
      String match = s.findWithinHorizon(isEmfUtilInterfacePattern, 0);
      s.close();
      return match != null;
   }

   private IFile getFileFromJavaElement(final IJavaElement javaElement) throws JavaModelException
   {
      IFile file = null;
      final IJavaElement unit = javaElement;
      final IResource resource = unit.getCorrespondingResource();
      if (resource instanceof IFile)
      {
         file = (IFile) resource;
      }
      return file;
   }
}
