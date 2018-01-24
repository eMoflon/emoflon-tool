package org.moflon.ide.core.properties;

import java.util.function.Function;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

public class MetamodelProjectUtil
{
   private static final Logger logger = Logger.getLogger(MetamodelProjectUtil.class);


   /**
    * Returns the file handle of the MOCA tree of a metamodel project.
    *
    * The MOCA tree may not exist and has to be checked using {@link IFile#exists()}.
    *
    * @param metamodelProject
    * @return the file handle. Never null.
    */
   public static IFile getExportedMocaTree(final IProject metamodelProject)
   {
      Function<String, IFile> loadMocaTree = name -> metamodelProject.getFolder(MetamodelBuilder.TEMP_FOLDER).getFile(name + MetamodelBuilder.MOCA_XMI_FILE_EXTENSION);

      IFile mocaTreeFile = loadMocaTree.apply(metamodelProject.getName());

      if (!mocaTreeFile.exists())
         mocaTreeFile = loadMocaTree.apply(metamodelProject.getName().toUpperCase());

      if (!mocaTreeFile.exists())
         mocaTreeFile = loadMocaTree.apply(metamodelProject.getName().toLowerCase());

      return mocaTreeFile;
   }

   /**
    * Returns the file handle of the changes MOCA tree of a metamodel project.
    *
    * The MOCA tree may not exist and has to be checked using {@link IFile#exists()}.
    *
    * @param metamodelProject
    * @return the file handle. Never null.
    */
   public static IFile getChangesMocaTree(final IProject metamodelProject)
   {
      Function<String, IFile> loadMocaTree = name -> metamodelProject.getFolder(MetamodelBuilder.TEMP_FOLDER).getFile(name + ".changes" + MetamodelBuilder.MOCA_XMI_FILE_EXTENSION);

      IFile mocaTreeFile = loadMocaTree.apply(metamodelProject.getName());

      if (!mocaTreeFile.exists())
         mocaTreeFile = loadMocaTree.apply(metamodelProject.getName().toUpperCase());

      if (!mocaTreeFile.exists())
         mocaTreeFile = loadMocaTree.apply(metamodelProject.getName().toLowerCase());

      return mocaTreeFile;
   }

   public static void cleanTempFolder(final IProject project, final IProgressMonitor monitor) throws CoreException
   {
      final IFolder folder = project.getFolder(MetamodelBuilder.TEMP_FOLDER);
      if (folder.exists())
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Inspecting " + folder.getName(), folder.members().length);

         for (IResource resource : folder.members())
         {
            if (!resource.getName().startsWith(".") && resource.getType() != IResource.FOLDER
                  && resource.getName().endsWith(WorkspaceHelper.ECORE_FILE_EXTENSION))
               resource.delete(true, subMon.split(1));
            else
               subMon.worked(1);
         }
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
      return MetamodelBuilder.TEMP_FOLDER + WorkspaceHelper.PATH_SEPARATOR + nameOfFile + ending;
   }

   public static String pathToFileInModelFolder(final String nameOfFile, final String ending)
   {
      return WorkspaceHelper.MODEL_FOLDER + WorkspaceHelper.PATH_SEPARATOR + nameOfFile + ending;
   }
}
