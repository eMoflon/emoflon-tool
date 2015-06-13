package org.moflon.deployment;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.autotest.AutoTestActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.util.plugins.FeatureUtils;

public class EclipsePluginDeployer extends AbstractDeployer
{

   private static final String SITE_BUILD_OPERATION = "org.eclipse.pde.internal.core.exports.SiteBuildOperation";

   private static final Logger logger = Logger.getLogger(EclipsePluginDeployer.class);

   private String updateSiteProjectName;

   private List<String> ignoredPluginIdPatterns;

   public EclipsePluginDeployer(final String deploymentPath, final String updateSiteProject, final String versionNumber,
         final List<String> ignoredPluginIdPatterns)
   {
      super(deploymentPath, versionNumber);
      this.updateSiteProjectName = updateSiteProject;
      this.ignoredPluginIdPatterns = ignoredPluginIdPatterns;
   }

   @Override
   public void deploy(final IProgressMonitor monitor) throws CoreException
   {
      final String projectName = this.updateSiteProjectName;
      final IProject updateSiteProject = WorkspaceHelper.getProjectByName(projectName);
      try
      {
         monitor.beginTask("Deploying eMoflon update site", 80);
         logger.info("Deploying eMoflon update site...");

         if (!updateSiteProject.exists())
            throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Update site project " + this.updateSiteProjectName + " is missing"));


         if (this.getVersionNumber() != null && !this.getVersionNumber().isEmpty())
         {
            updateVersionPluginNumbers(WorkspaceHelper.createSubMonitor(monitor, 20), updateSiteProject);
         } else
         {
            monitor.worked(20);
         }

         // 1. Delete all jars in project MoflonIdeUpdateSite and make copy
         // of site.xml
         clearUpdateSiteProject(updateSiteProject.getLocation().toString());
         updateSiteProject.getFile("site.xml.temp").create(getSiteXML(this.updateSiteProjectName).getContents(), true,
               WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         // 2. Build MoflonIdeUpdateSite
         IProject source = build(this.updateSiteProjectName);
         monitor.worked(10);

         if (source == null)
            throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Plugin '" + this.updateSiteProjectName + "' could not be built."));

         // 3. Delete contents of target
         File target = clearUpdateSite(this.getDeploymentPath());
         monitor.worked(10);

         // 4. Copy folder structure of MoflonIdeUpdateSite to target
         copyNewTarget(source, target);
         monitor.worked(10);
         final String projectName1 = this.updateSiteProjectName;

         // 5. Replace site.xml with copy from (II).2
         getSiteXML(this.updateSiteProjectName).setContents(WorkspaceHelper.getProjectByName(projectName1).getFile("site.xml.temp").getContents(), true, true,
               WorkspaceHelper.createSubMonitor(monitor, 10));

         logger.info("Deploying eMoflon IDE Update Site done.");
      } catch (CoreException e)
      {
         logger.warn("Skipping creation of eMoflon update site. Reason: " + e.getMessage());
         ++this.warningCount;
      } finally
      {
         updateSiteProject.getFile("site.xml.temp").delete(true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         monitor.done();
      }
   }

   private void updateVersionPluginNumbers(final IProgressMonitor monitor, final IProject updateSiteProject) throws CoreException
   {
      try
      {
         final List<IProject> projects = getFeatureProjectsOfSite(updateSiteProject);
         monitor.beginTask("Update version numbers", projects.size() * 2);

         for (final IProject project : projects)
         {
            final IFile featureFile = project.getFile("feature.xml");

            FeatureUtils.updateVersionOfFeature(featureFile, getSiteXmlFile(updateSiteProject), getVersionNumber(), WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            FeatureUtils.updateVersionsOfAllPluginsInFeature(featureFile, ignoredPluginIdPatterns, this.getVersionNumber(),
                  WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         }
      } finally
      {
         monitor.done();
      }
   }

   public static List<IProject> getFeatureProjectsOfSite(final IProject updateSiteProject) throws CoreException
   {
      List<String> featureIds = FeatureUtils.getAllFeatureIdsInSite(getSiteXmlFile(updateSiteProject));
      List<IProject> projects = WorkspaceHelper.getAllProjectsInWorkspace().stream().filter(p -> {
         IFile file = p.getFile("feature.xml");
         if (file.exists())
            try
            {
               return featureIds.contains(FeatureUtils.getFeatureId(file));
            } catch (Exception e)
            { // ignore
            }
         return false;

      }).collect(Collectors.toList());
      return projects;
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
            IProject sourceProject = (IProject) source;
            IFolder features = sourceProject.getFolder("features");
            IFolder plugins = sourceProject.getFolder("plugins");
            IFile site = getSiteXmlFile(sourceProject);
            IFile associatedSites = sourceProject.getFile("associateSites.xml");
            IFile indexHtml = sourceProject.getFile("index.html");

            File featuresFolder = new File(target + File.separator + "features");
            if (!featuresFolder.exists())
               featuresFolder.mkdir();
            while (!featuresFolder.exists())
            { // nop
            }

            for (IResource file : features.members())
            {
               copyFile(file.getLocation().toFile(), new File(featuresFolder.getCanonicalPath().toString() + File.separator + file.getName()));
            }

            File pluginsFolder = new File(target + File.separator + "plugins");
            if (!pluginsFolder.exists())
               pluginsFolder.mkdir();
            while (!pluginsFolder.exists())
            { // nop
            }

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
         e.printStackTrace();
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
                  e.printStackTrace();
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
                  e.printStackTrace();
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
            || file.getName().equals("associateSites.xml") || file.getName().equals("index.html") || file.getName().equals("moflon.target");
   }
}
