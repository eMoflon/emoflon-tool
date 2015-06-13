package org.moflon.ide.core.properties;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.moflon.core.utilities.WorkspaceHelper;

public class MetamodelProjectUtil
{
   private static final Logger logger = Logger.getLogger(MetamodelProjectUtil.class);

   public static void cleanTempFolder(final IProject project, final IProgressMonitor monitor) throws CoreException
   {
      final IFolder folder = project.getFolder(WorkspaceHelper.TEMP_FOLDER);
      try
      {
         if (folder.exists())
         {
            monitor.beginTask("Inspecting " + folder.getName(), folder.members().length);

            for (IResource resource : folder.members())
            {
               if (!resource.getName().startsWith(".") && resource.getType() != IResource.FOLDER
                     && resource.getName().endsWith(WorkspaceHelper.ECORE_FILE_EXTENSION))
                  resource.delete(true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
               else
                  monitor.worked(1);
            }
         }
      } finally
      {
         monitor.done();
      }
   }

   /**
    * Retrieves a file consisting of the project name and the given ending.
    * 
    * The method also searches for the lowercased and uppercased version of the project name.
    */
   public static IFile getFileInTempFolder(final String ending, final IProject project)
   {
      String projectName = project.getName();

      IFile propertyFile = project.getFile(MetamodelProjectUtil.pathToFileInTempFolder(projectName, ending));

      if (!propertyFile.exists())
      {
         propertyFile = project.getFile(MetamodelProjectUtil.pathToFileInTempFolder(projectName.toUpperCase(), ending));
      }

      if (!propertyFile.exists())
      {
         propertyFile = project.getFile(MetamodelProjectUtil.pathToFileInTempFolder(projectName.toLowerCase(), ending));
      }

      if (!propertyFile.exists())
         logger.error("Can't find property file with expected name!");

      return propertyFile;
   }

   public static String pathToFileInTempFolder(final String nameOfFile, final String ending)
   {
      return WorkspaceHelper.TEMP_FOLDER + WorkspaceHelper.PATH_SEPARATOR + nameOfFile + ending;
   }

   public static String pathToFileInModelFolder(final String nameOfFile, final String ending)
   {
      return WorkspaceHelper.MODEL_FOLDER + WorkspaceHelper.PATH_SEPARATOR + nameOfFile + ending;
   }
}
