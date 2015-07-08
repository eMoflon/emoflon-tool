package org.moflon.autotest.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.autotest.AutoTestActivator;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.deployment.AbstractDeployer;
import org.moflon.deployment.EAAddinDeployer;
import org.moflon.deployment.EclipsePluginDeployer;
import org.moflon.deployment.HandbookDeployer;

public class DeploymentJob extends Job
{

   private static final Logger logger = Logger.getLogger(DeploymentJob.class);

   private final String deploymentRoot;

   /**
    * @param deploymentPath
    *           the root deployment path. Each sub-deployment controller will create separate subdirectories within it.
    */
   public DeploymentJob(final String deploymentPath)
   {
      super("Deploying eMoflon...");
      this.deploymentRoot = deploymentPath;
      setUser(true);
   }

   @Override
   protected IStatus run(final IProgressMonitor monitor)
   {

      final AbstractDeployer eclipsePluginDeployer = new EclipsePluginDeployer(deploymentRoot + "/update-site2", "MoflonIdeUpdateSite");
      final AbstractDeployer eaAddinDeployer = new EAAddinDeployer(deploymentRoot + "/ea-ecore-addin");
      final AbstractDeployer handbookDeployer = new HandbookDeployer(deploymentRoot + "/documents/release/handbook");
      try
      {
         monitor.beginTask("Deploying eMoflon", 300);
         logger.info("Deploying eMoflon [path: " + deploymentRoot + "]");

         ensureThatDirectoryIsWritable(new File(deploymentRoot));

         eaAddinDeployer.deploy(WorkspaceHelper.createSubMonitor(monitor, 100));
         //eclipsePluginDeployer.deploy(WorkspaceHelper.createSubMonitor(monitor, 100));
         //handbookDeployer.deploy(WorkspaceHelper.createSubMonitor(monitor, 100));

         return new Status(IStatus.OK, AutoTestActivator.getModuleID(), "Successfully deployed eMoflon ...");
         
      } catch (final CoreException e)
      {
         logger.error("I was unable to deploy eMoflon ...");
         logger.error("Are you sure you have access to all required folders (beta and release destinations have restricted access!)?");
         logger.error(MoflonUtil.displayExceptionAsString(e));

         return new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), IStatus.ERROR, e.getMessage(), e);
      } finally
      {
         final int warningCount = eclipsePluginDeployer.getWarningCount() + eaAddinDeployer.getWarningCount() + handbookDeployer.getWarningCount();

         if (warningCount == 0)
         {
            logger.info("Successfully deployed eMoflon.");
         } else
         {
            logger.warn("Deployed eMoflon with " + warningCount + " warnings.");
         }

         monitor.done();
      }

   }

   public void ensureThatDirectoryIsWritable(final File directory) throws CoreException
   {
      if (!directory.isDirectory())
         throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "The selected path is not a directory: '" + directory.getPath() + "'."));
      if (!isWritableDirectory(directory))
         throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "The selected direcotry is not writable: '" + directory.getPath()
               + "'."));
   }

   private boolean isWritableDirectory(final File folder)
   {
      final File testFile = new File(folder, "TemporaryTestFileForMoflonDeployment.txt");
      FileOutputStream stream = null;
      try
      {
         stream = new FileOutputStream(testFile);
         stream.flush();
         stream.close();
         testFile.setWritable(true);

      } catch (final IOException e)
      {
         return false;
      } finally
      {
         if (stream != null)
            IOUtils.closeQuietly(stream);
         testFile.delete();
      }

      return true;
   }
}