package org.moflon.deployment;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Parent class for components that deploy part of eMoflond
 */
public abstract class AbstractDeployer
{
   protected int warningCount;

   private final String deploymentPath;


   /**
    * Instantiates a deployer that uses the given deployment path as root directory for its deployment and that updates the versions of plugins and the feature using the given version number.
    * 
    * If the version number is null or empty, the current version is left unchanged.
    * 
    * @param deploymentPath
    */
   public AbstractDeployer(final String deploymentPath)
   {
      this.deploymentPath = deploymentPath;
      this.warningCount = 0;
   }

   //TODO@rkluge: Add return type "DeploymentStatusMessage"
   public abstract void deploy(IProgressMonitor monitor) throws CoreException;

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
}
