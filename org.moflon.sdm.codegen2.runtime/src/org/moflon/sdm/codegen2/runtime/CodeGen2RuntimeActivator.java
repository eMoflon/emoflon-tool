package org.moflon.sdm.codegen2.runtime;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.BundleContext;

public class CodeGen2RuntimeActivator extends Plugin
{
   private static final Logger logger = Logger.getLogger(CodeGen2RuntimeActivator.class);
   
   protected static final String PLUGIN_ID = "org.moflon.sdm.codegen2.runtime";

   @Override
   public void start(final BundleContext context) throws Exception
   {
      // Set up Fujaba Workspace
      Job fujabaWorkspaceSetupJob = new Job("Moflon: Setting up Fujaba Workspace") {
         @Override
         protected IStatus run(final IProgressMonitor monitor)
         {
            File fujabaWorkspace = getPathInStateLocation("FujabaWorkspace").toFile();

            if (!fujabaWorkspace.exists())
               fujabaWorkspace.mkdir();
            else
               try
               {
                  FileUtils.cleanDirectory(fujabaWorkspace);
               } catch (IOException e)
               {
                  logger.error("Unable to clean FujabaWorkspace!");
               }

            return new Status(IStatus.OK, CodeGen2RuntimeActivator.PLUGIN_ID, IStatus.OK, "", null);
         }
      };
      fujabaWorkspaceSetupJob.schedule();
   }

   /**
    * Used when the plugin has to store resources on the client machine and eclipse installation + current workspace.
    * This location reserved for the plugin is called the "state location" and is usually in
    * pathToWorkspace/.metadata/pluginName
    * 
    * @param filename
    *           Appended to the state location. This is the name of the resource to be saved.
    * @return path to location reserved for the plugin which can be used to store resources
    */
   public IPath getPathInStateLocation(final String filename)
   {
      return getStateLocation().append(filename);
   }

}
