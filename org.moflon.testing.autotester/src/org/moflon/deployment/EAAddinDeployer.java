package org.moflon.deployment;

import java.io.File;
import java.io.IOException;
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

   private static final String BUILD_TYPE_FOR_DEPLOYMENT = "Debug"; // Either Release or Debug

   private static final List<String> COMMAND_LINE_TOOL_FILENAMES = Arrays.asList("CommandLineParser.dll", "EAEcoreAddin.dll", "eMoflonCommandLine.exe",
         "Interop.EA.dll");

   private static final List<String> INSTALLER_FILENAMES = Arrays.asList("cab1.cab", "eMoflonAddinInstaller.msi", "eMoflonAddinInstaller.wixpdb");

   private static final Logger logger = Logger.getLogger(EAAddinDeployer.class);

   private static final String EA_INSTALLER_PROJECT_NAME = "eMoflonAddinInstaller";

   private static final String EA_COMMAND_LINE_PROJECT_NAME = "eMoflonCommandLine";

   private static final String EA_ADDIN_PROJECT_NAME = "MOFLON2EAEcoreAddin";

   private static final IFolder COMMAND_LINE_TOOL_FOLDER_SOURCE = WorkspaceHelper.getProjectByName(EA_ADDIN_PROJECT_NAME).getFolder(EA_COMMAND_LINE_PROJECT_NAME)
         .getFolder("bin").getFolder(BUILD_TYPE_FOR_DEPLOYMENT);

   private static final IFolder COMMAND_LINE_TOOL_FOLDER_TARGET = WorkspaceHelper.getProjectByName("MoflonIdeCore").getFolder("resources")
         .getFolder("commandLineEAExport");

   private static final IFile INSTALLER_ARCHIVE_TARGET = WorkspaceHelper.getProjectByName(EA_ADDIN_PROJECT_NAME).getFolder(EA_INSTALLER_PROJECT_NAME)
         .getFolder("bin").getFile("ea-ecore-addin.zip");

   private static final IFolder INSTALLER_FOLDER_SOURCE = WorkspaceHelper.getProjectByName(EA_ADDIN_PROJECT_NAME).getFolder(EA_INSTALLER_PROJECT_NAME)
         .getFolder("bin").getFolder(BUILD_TYPE_FOR_DEPLOYMENT);

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
         if (!areAllInstallerFilesPresent())
         {
            logger.warn("Some files of installer are missing. Expected location: '" + INSTALLER_FOLDER_SOURCE.getFullPath().toPortableString()
                  + "'. Expected files: " + INSTALLER_FILENAMES + ".");
            ++warningCount;
            areAllFilesPresent = false;
         }
         monitor.worked(1);
         
         if (!areAllCommandLineToolFilesPresent())
         {
            logger.warn("Some files in the command-line tool are missing. Source folder: " + COMMAND_LINE_TOOL_FOLDER_SOURCE.getFullPath().toPortableString()
                  + ". Expected files: '" + COMMAND_LINE_TOOL_FILENAMES + "'.");
            ++warningCount;
            areAllFilesPresent = false;
         }
         monitor.worked(1);

         if (areAllFilesPresent)
         {

            logger.info("Copying EA Addin...");

            copyCommandLineTool();
            monitor.worked(1);

            IFile zipArchiveToBeDeployed = createAddinArchive();
            monitor.worked(1);

            File target = clearFlatFolder(getDeploymentPath());
            monitor.worked(1);
            copyNewTarget(zipArchiveToBeDeployed, target);
            monitor.worked(1);

            if (zipArchiveToBeDeployed.exists())
               zipArchiveToBeDeployed.delete(true, WorkspaceHelper.createSubMonitor(monitor, 1));

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

   private static boolean areAllCommandLineToolFilesPresent()
   {
      final List<String> commandLineNames = COMMAND_LINE_TOOL_FILENAMES;
      for (final String fileName : commandLineNames)
      {
         final IFile source = COMMAND_LINE_TOOL_FOLDER_SOURCE.getFile(fileName);
         if (!source.exists())
            return false;
      }
      return true;
   }

   private static boolean areAllInstallerFilesPresent() throws CoreException
   {
      final List<String> filenames = INSTALLER_FILENAMES;
      for (final String fileName : filenames)
      {
         final IFile source = INSTALLER_FOLDER_SOURCE.getFile(fileName);
         if (!source.exists())
            return false;
      }
      return true;
   }

   private static void copyCommandLineTool() throws CoreException
   {
      final List<String> commandLineNames = COMMAND_LINE_TOOL_FILENAMES;
      for (final String fileName : commandLineNames)
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

   private static IFile createAddinArchive() throws CoreException
   {

      final IFolder debugFolder = INSTALLER_FOLDER_SOURCE;
      final IFile targetFile = INSTALLER_ARCHIVE_TARGET;

      ZipUtil.getZipArchive(debugFolder, targetFile);
      return targetFile;
   }

   private static void copyNewTarget(final IResource source, final File target)
   {
      try
      {
         if (source instanceof IProject)
         {
            IProject sourceProject = (IProject) source;
            IFolder features = sourceProject.getFolder("features");
            IFolder plugins = sourceProject.getFolder("plugins");
            IFile site = sourceProject.getFile("site.xml");
            IFile associatedSites = sourceProject.getFile("associateSites.xml");
            IFile indexHtml = sourceProject.getFile("index.html");

            File featuresFolder = new File(target + File.separator + "features");
            if (!featuresFolder.exists())
               featuresFolder.mkdir();
            while (!featuresFolder.exists())
               ;
            for (IResource file : features.members())
            {
               copyFile(file.getLocation().toFile(), new File(featuresFolder.getCanonicalPath().toString() + File.separator + file.getName()));
            }

            File pluginsFolder = new File(target + File.separator + "plugins");
            if (!pluginsFolder.exists())
               pluginsFolder.mkdir();
            while (!pluginsFolder.exists())
               ;
            for (IResource file : plugins.members())
            {
               copyFile(file.getLocation().toFile(), new File(pluginsFolder.getCanonicalPath().toString() + File.separator + file.getName()));
            }

            copyFile(site.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator + site.getName()));
            copyFile(associatedSites.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator + associatedSites.getName()));
            copyFile(indexHtml.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator + indexHtml.getName()));

         } else if (source instanceof IFolder)
         {
            for (IResource file : ((IFolder) source).members())
            {
               copyFile(file.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator + file.getName()));
            }
         } else if (source instanceof IFile)
         {
            copyFile(source.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator + source.getName()));
         }
      } catch (CoreException e)
      {
         e.printStackTrace();
      } catch (IOException e)
      {
         e.printStackTrace();
      }

   }
}
