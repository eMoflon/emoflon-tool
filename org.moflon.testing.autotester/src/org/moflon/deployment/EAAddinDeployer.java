package org.moflon.deployment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.autotest.AutoTestActivator;
import org.moflon.core.utilities.WorkspaceHelper;

public class EAAddinDeployer extends AbstractDeployer
{

   private static final String EA_ARCHIVE = "ea-ecore-addin.zip";

   private static final String BUILD_TYPE_FOR_DEPLOYMENT = "Debug"; // Either Release or Debug

   private static final List<String> COMMAND_LINE_TOOL_FILENAMES = Arrays.asList("CommandLineParser.dll", "EAEcoreAddin.dll", "eMoflonCommandLine.exe",
         "Interop.EA.dll");

   private static final Logger logger = Logger.getLogger(EAAddinDeployer.class);

   private static final String EA_INSTALLER_PROJECT_NAME = "eMoflonAddinInstaller";

   private static final String EA_COMMAND_LINE_PROJECT_NAME = "eMoflonCommandLine";

   private static final String EA_ADDIN_PROJECT_NAME = "MOFLON2EAEcoreAddin";

   private static final IFolder COMMAND_LINE_TOOL_FOLDER_SOURCE = WorkspaceHelper.getProjectByName(EA_ADDIN_PROJECT_NAME)
         .getFolder(EA_COMMAND_LINE_PROJECT_NAME).getFolder("bin").getFolder(BUILD_TYPE_FOR_DEPLOYMENT);

   private static final IFolder COMMAND_LINE_TOOL_FOLDER_TARGET = WorkspaceHelper.getProjectByName("MoflonIdeCore").getFolder("resources")
         .getFolder("commandLineEAExport");

   private static final IFile INSTALLER_ARCHIVE_TARGET = WorkspaceHelper.getProjectByName(EA_ADDIN_PROJECT_NAME).getFolder(EA_INSTALLER_PROJECT_NAME)
         .getFolder("bin").getFile(EA_ARCHIVE);

   private static final IFile INSTALLER_ARCHIVE_SOURCE = WorkspaceHelper.getProjectByName(EA_ADDIN_PROJECT_NAME).getFolder(EA_INSTALLER_PROJECT_NAME)
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
         if (!INSTALLER_ARCHIVE_SOURCE.exists())
         {
            logger.warn("The installer archive is missing. Expected location: '" + INSTALLER_ARCHIVE_SOURCE.getFullPath().toPortableString() + "'.");
            ++warningCount;
            areAllFilesPresent = false;
         }
         monitor.worked(1);

         List<String> missingCmdLineFiles = areAllCommandLineToolFilesPresent();
         if (!missingCmdLineFiles.isEmpty())
         {
            logger.warn("Some files in the command-line tool are missing. Source folder: " + COMMAND_LINE_TOOL_FOLDER_SOURCE.getFullPath().toPortableString()
                  + ". Missing files: '" + missingCmdLineFiles + "'.");
            ++warningCount;
            areAllFilesPresent = false;
         }
         monitor.worked(1);

         if (areAllFilesPresent)
         {

            logger.info("Copying EA Addin...");

            copyCommandLineTool();
            monitor.worked(2);

            File target = clearFlatFolder(getDeploymentPath());
            monitor.worked(1);
            try
            {
               WorkspaceHelper.copyFile(INSTALLER_ARCHIVE_TARGET.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator
                     + INSTALLER_ARCHIVE_TARGET.getName()));
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

   private static List<String> areAllCommandLineToolFilesPresent()
   {
      final List<String> commandLineNames = COMMAND_LINE_TOOL_FILENAMES;
      final List<String> missingFiles = new ArrayList<>();
      for (final String fileName : commandLineNames)
      {
         final IFile source = COMMAND_LINE_TOOL_FOLDER_SOURCE.getFile(fileName);
         if (!source.exists())
            missingFiles.add(fileName);
      }
      return missingFiles;
   }

   private static void copyCommandLineTool() throws CoreException
   {
      final List<String> commandLineFileNames = COMMAND_LINE_TOOL_FILENAMES;
      for (final String fileName : commandLineFileNames)
      {
         final IFile source = COMMAND_LINE_TOOL_FOLDER_SOURCE.getFile(fileName);
         final IFile target = COMMAND_LINE_TOOL_FOLDER_TARGET.getFile(fileName);

         try
         {
            target.setContents(source.getContents(), true, true, null);
         } catch (CoreException e)
         {
            throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Failed to copy EA addin DLL. Reason: " + e.getMessage()));
         }
      }
   }
}
