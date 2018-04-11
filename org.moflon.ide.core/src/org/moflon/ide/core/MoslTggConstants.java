package org.moflon.ide.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.moflon.core.utilities.MoflonConventions;

/**
 * Constants related to the MOSL TGG specification language
 *
 * @author Roland Kluge - Initial implementation
 *
 */
public class MoslTggConstants
{
   public static final String MOSL_TGG_EXTENSION = "tgg";

   public static final String TGG_FILE_EXTENSION = ".tgg.xmi";

   public static final String PRE_TGG_FILE_EXTENSION = ".pre.tgg.xmi";

   public static final String PRE_ECORE_FILE_EXTENSION = ".pre.ecore";

   /**
    * Returns the (expected) path of the pre.ecore file in an integration project
    *
    * @param projectName
    *            the project name
    * @return the default path to the Ecore model
    */
   public static String getDefaultPathToPreEcoreFileInProject(final String projectName)
   {
      return MoflonConventions.getDefaultPathToFileInProject(projectName, MoslTggConstants.PRE_ECORE_FILE_EXTENSION);
   }

   /**
    * Returns a handle to the default location of a pre.ecore file of an integration
    * project
    *
    * @param project
    *            the project of which to extract the ecore file
    */
   public static IFile getDefaultPreEcoreFile(final IProject project)
   {
      return project.getFile(new Path(getDefaultPathToPreEcoreFileInProject(project.getName())));
   }
}
