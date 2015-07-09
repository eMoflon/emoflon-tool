package org.moflon.deployment;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.moflon.core.utilities.WorkspaceHelper;

public class EAAddinDeployer extends AbstractDeployer
{


   private static final Logger logger = Logger.getLogger(EAAddinDeployer.class);

   private static final String EA_INSTALLER_PROJECT_NAME = "eMoflonAddinInstaller";

   private static final String EA_ARCHIVE = "ea-ecore-addin.zip";

   private static final String EA_ADDIN_PROJECT_NAME = "MOFLON2EAEcoreAddin";

   private static final IFile PATH_TO_INSTALLER_ARCHIVE = WorkspaceHelper.getProjectByName(EA_ADDIN_PROJECT_NAME).getFolder(EA_INSTALLER_PROJECT_NAME)
         .getFolder("bin").getFile(EA_ARCHIVE);

   public EAAddinDeployer(final String deploymentPath)
   {
      super(deploymentPath);
   }

   @Override
   public void deploy(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Deploying EA addin", 7);
         boolean areAllFilesPresent = true;
         if (!PATH_TO_INSTALLER_ARCHIVE.exists())
         {
            logger.warn("The installer archive is missing. Expected location: '" + PATH_TO_INSTALLER_ARCHIVE.getFullPath().toPortableString() + "'.");
            ++warningCount;
            areAllFilesPresent = false;
         }
         monitor.worked(1);

         if (areAllFilesPresent)
         {

            logger.info("Copying EA Addin...");

            File target = clearFlatFolder(getDeploymentPath());
            monitor.worked(3);
            try
            {
               FileUtils.copyFile(PATH_TO_INSTALLER_ARCHIVE.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator
                     + PATH_TO_INSTALLER_ARCHIVE.getName()));
            } catch (IOException e)
            {
               logger.error("Problem while copying addin installer archive. Reason: " + e.getMessage());
            }
            monitor.worked(1);

            logger.info("Copying EA Addin done.");
         } else
         {
            logger.warn("Skipping EA Addin because not all files are present.");
            monitor.worked(5);
         }
      } finally
      {
         monitor.done();
      }
   }
}
