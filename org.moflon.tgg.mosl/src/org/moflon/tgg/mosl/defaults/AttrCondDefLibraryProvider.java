package org.moflon.tgg.mosl.defaults;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.moflon.core.utilities.WorkspaceHelper;

public class AttrCondDefLibraryProvider
{

   public static void syncAttrCondDefLibrary(IProject project) throws CoreException
   {
      final String defaultLib = DefaultFilesHelper.generateDefaultAttrCondDefLibrary();
      final IPath pathToLib = new Path("src/org/moflon/tgg/mosl/csp/lib/AttrCondDefLibrary.tgg");
      try
      {

         IFile attrLibFile = project.getFile(pathToLib);
         if (attrLibFile.exists())
         {
            File file = new File(attrLibFile.getLocation().toString());
            String contents;

            contents = FileUtils.readFileToString(file);

            if (!contents.equals(defaultLib))
            {
               addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
            }
         } else
         {
            addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
         }
      } catch (IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(AttrCondDefLibraryProvider.class), "Problem why loading '" + pathToLib + "'. Reason: " + e.getMessage(), e));
      }
   }
}