package org.moflon.ide.core.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

public class MetamodelProjectUtil
{
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
      final IFile mocaTreeFile = metamodelProject.getFolder(MetamodelBuilder.TEMP_FOLDER)
            .getFile(metamodelProject.getName() + "." + MetamodelBuilder.MOCA_XMI_FILE_EXTENSION);

      return mocaTreeFile;
   }
}
