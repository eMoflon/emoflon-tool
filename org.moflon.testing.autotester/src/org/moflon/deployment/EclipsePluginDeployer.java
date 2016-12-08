package org.moflon.deployment;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.autotest.AutoTestActivator;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class EclipsePluginDeployer
{
   private static final Logger logger = Logger.getLogger(EclipsePluginDeployer.class);

   protected int warningCount;

   private final String deploymentPath;

   private static final String SITE_BUILD_OPERATION = "org.eclipse.pde.internal.core.exports.SiteBuildOperation";

   private String updateSiteProjectName;

   public EclipsePluginDeployer(final String deploymentPath, final String updateSiteProject)
   {
      this.deploymentPath = deploymentPath;
      this.updateSiteProjectName = updateSiteProject;
   }

   public int getWarningCount()
   {
      return this.warningCount;
   }

   public String getDeploymentPath()
   {
      return this.deploymentPath;
   }

   /**
    * Creates or clears the given target folder.
    */
   protected static File clearFlatFolder(final String targetPath)
   {
      File target = new File(targetPath);
      if (!target.exists())
      {
         target.mkdirs();
         return target;
      }

      File[] files = target.listFiles();

      for (File file : files)
         file.delete();

      return target;
   }

   public void deploy(final IProgressMonitor monitor) throws CoreException
   {
      final String projectName = this.updateSiteProjectName;
      final IProject updateSiteProject = WorkspaceHelper.getProjectByName(projectName);
      SubMonitor subMon = SubMonitor.convert(monitor, "Deploying eMoflon update site", 60);
      try
      {
         logger.info("Deploying eMoflon update site...");

         if (!updateSiteProject.exists())
            throw new CoreException(
                  new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Update site project " + this.updateSiteProjectName + " is missing"));

         // 1. Delete all jars in project MoflonIdeUpdateSite and make copy
         // of site.xml
         clearUpdateSiteProject(updateSiteProject.getLocation().toString());
         updateSiteProject.getFile("site.xml.temp").create(getSiteXML(this.updateSiteProjectName).getContents(), true, subMon.split(1));

         // 2. Build MoflonIdeUpdateSite
         IProject source = build(this.updateSiteProjectName);
         subMon.worked(10);

         if (source == null)
            throw new CoreException(
                  new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Plugin '" + this.updateSiteProjectName + "' could not be built."));

         // 3. Delete contents of target
         File target = clearUpdateSite(this.getDeploymentPath());
         subMon.worked(10);

         // 4. Copy folder structure of MoflonIdeUpdateSite to target
         copyNewTarget(source, target);
         subMon.worked(10);
         final String projectName1 = this.updateSiteProjectName;

         // 5. Replace site.xml with copy from (II).2
         getSiteXML(this.updateSiteProjectName).setContents(WorkspaceHelper.getProjectByName(projectName1).getFile("site.xml.temp").getContents(), true, true,
               subMon.split(10));

         logger.info("Deploying eMoflon IDE Update Site done.");
      } catch (CoreException e)
      {
         logger.warn("Skipping creation of eMoflon update site. Reason: " + e.getMessage());
         ++this.warningCount;
      } finally
      {
         updateSiteProject.getFile("site.xml.temp").delete(true, subMon.split(1));
      }
   }

   public static IFile getSiteXmlFile(final IProject updateSiteProject)
   {
      return updateSiteProject.getFile("site.xml");
   }

   private static void copyNewTarget(final IResource source, final File target)
   {
      try
      {
         if (source instanceof IProject)
         {
            /*
             * Files and folders to be copied
             */
            IProject sourceProject = (IProject) source;
            IFolder features = sourceProject.getFolder("features");
            IFolder plugins = sourceProject.getFolder("plugins");
            File featuresFolder = createFeaturesFolderInTarget(target);

            for (IResource file : features.members())
            {
               copyAndReportMissingFile(file.getLocation().toFile(), new File(featuresFolder.getCanonicalPath().toString() + File.separator + file.getName()));
            }

            File pluginsFolder = createPluginsFolderInTarget(target);

            for (IResource file : plugins.members())
            {
               copyAndReportMissingFile(file.getLocation().toFile(), new File(pluginsFolder.getCanonicalPath().toString() + File.separator + file.getName()));
            }

            for (IFile file : Arrays.asList(getSiteXmlFile(sourceProject), sourceProject.getFile("index.html"), sourceProject.getFile("ea-ecore-addin.zip"),
                  sourceProject.getFile("changelog.txt"), sourceProject.getFile("associateSites.xml")))
            {
               copyAndReportMissingFile(file.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator + file.getName()));
            }

         } else if (source instanceof IFolder)
         {
            for (IResource file : ((IFolder) source).members())
            {
               copyAndReportMissingFile(file.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator + file.getName()));
            }
         } else if (source instanceof IFile)
         {
            copyAndReportMissingFile(source.getLocation().toFile(), new File(target.getCanonicalPath().toString() + File.separator + source.getName()));
         }
      } catch (CoreException | IOException e)
      {
         logger.error(MoflonUtil.displayExceptionAsString(e));
      }

   }

   private static void copyAndReportMissingFile(final File source, final File target)
   {
      try
      {
         FileUtils.copyFile(source, target);
      } catch (IOException e)
      {
         logger.warn("\tProblem while copying " + source.getAbsolutePath());
      }
   }

   public static File createFeaturesFolderInTarget(final File target)
   {
      File featuresFolder = new File(target + File.separator + "features");
      if (!featuresFolder.exists())
         featuresFolder.mkdir();
      while (!featuresFolder.exists())
      { // nop
      }
      return featuresFolder;
   }

   public static File createPluginsFolderInTarget(final File target)
   {
      File pluginsFolder = new File(target + File.separator + "plugins");
      if (!pluginsFolder.exists())
         pluginsFolder.mkdir();
      while (!pluginsFolder.exists())
      { // nop
      }
      return pluginsFolder;
   }

   private static IProject build(final String projectName) throws CoreException
   {
      IProject updateSiteProject = WorkspaceHelper.getProjectByName(projectName);
      Job job = (Job) Platform.getAdapterManager().loadAdapter(updateSiteProject, SITE_BUILD_OPERATION);
      if (job == null)
      {
         throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Cannot load adapter for " + SITE_BUILD_OPERATION));
      }
      job.schedule();
      try
      {
         job.join();
         if (job.getResult().isOK())
            return WorkspaceHelper.getProjectByName(projectName);
         else
            return null;
      } catch (InterruptedException e)
      {
         LogUtils.error(logger, e);
      }
      return null;
   }

   private static IFile getSiteXML(final String projectName)
   {
      return WorkspaceHelper.getProjectByName(projectName).getFile("site.xml");
   }

   private static File clearUpdateSite(final String targetPath)
   {
      File target = new File(targetPath);
      if (!target.exists())
      {
         target.mkdirs();
         return target;
      }

      File[] files = target.listFiles();
      for (File file : files)
      {
         if (file.isDirectory())
         {
            if (file.getName().equals("features") || file.getName().equals("plugins"))
            {
               try
               {
                  clearFlatFolder(file.getCanonicalPath().toString());
               } catch (IOException e)
               {
                  LogUtils.error(logger, e);
               }
            }
         } else
            file.delete();
      }

      return target;
   }

   private static File clearUpdateSiteProject(final String sourcePath)
   {
      File target = new File(sourcePath);
      File[] files = target.listFiles();

      for (File file : files)
      {
         if (file.isDirectory())
         {
            if (file.getName().equals("features") || file.getName().equals("plugins"))
            {
               try
               {
                  clearFlatFolder(file.getCanonicalPath().toString());
               } catch (IOException e)
               {
                  LogUtils.error(logger, e);
               }
            }
         } else if (!fileToBeRetained(file))
            file.delete();
      }

      return target;
   }

   private static boolean fileToBeRetained(final File file)
   {
      return file.getName().equals(".project") || file.getName().equals(".svn") || file.getName().equals("site.xml")
            || file.getName().equals("associateSites.xml") || file.getName().equals("index.html") || file.getName().equals("moflon.target")
            || file.getName().equals("ea-ecore-addin.zip") || file.getName().equals("changelog.txt") || file.getName().equals(".gitignore");
   }
}
