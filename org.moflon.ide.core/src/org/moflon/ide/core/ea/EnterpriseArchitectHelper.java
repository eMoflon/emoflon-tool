package org.moflon.ide.core.ea;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.util.RefreshProjectJob;

public class EnterpriseArchitectHelper
{
   private static final Logger logger = Logger.getLogger(EnterpriseArchitectHelper.class);

   private static final String ERROR_MESSAGE_LATEST_VERSIONS = "Do you have the latest version of EA and of our eMoflon EA Addin?";

   private static final String ERROR_MESSAGE_PROBLEMS_EA_EXPORT = "I'm having problems executing the EA export for ";

   private static final String COMMAND_LINE_EA_EXPORT = "/resources/commandLineEAExport/eMoflonCommandLine.exe";

   public static void delegateToEnterpriseArchitect(final IProject project) throws IOException, InterruptedException
   {
      delegateToEnterpriseArchitect(project, new NullProgressMonitor());
   }

   public static void delegateToEnterpriseArchitect(final IProject project, final IProgressMonitor monitor) throws IOException, InterruptedException
   {
      try
      {
         monitor.beginTask("Exporting project " + project.getName(), 2);

         URL pathToExe = MoflonUtilitiesActivator.getPathRelToPlugIn(COMMAND_LINE_EA_EXPORT, CoreActivator.getModuleID());
         IFile eap = project.getFile(project.getName().concat(".eap"));
         Runtime rt = Runtime.getRuntime();
         String command = "\"" + new File(pathToExe.getPath()).getAbsolutePath() + "\"" + " -e --eap " + "\"" + eap.getLocation() + "\"";
         logger.debug("Executing '" + command + "'");

         Process pr = rt.exec(command);
         pr.waitFor();
         monitor.worked(1);
         
         InputStream inputStream = new BufferedInputStream(pr.getInputStream());
         StringBuilder stdout = new StringBuilder();
         byte[] buffer = new byte[1024];
         int readBytes = -1;
         do
         {
            readBytes = inputStream.read(buffer);
            if (readBytes > 0)
            {
               stdout.append(new String(buffer, 0, readBytes));
            }
         } while (readBytes > 0);
         logger.debug("Process output: " + stdout.toString());

         Job refreshProject = new RefreshProjectJob(project);
         refreshProject.schedule();
         monitor.worked(1);
      } finally
      {
         monitor.done();
      }
   }

   public static void exportEcoreFilesFromEAP(final IProject project)
   {
      try
      {
         delegateToEnterpriseArchitect(project);
      } catch (IOException | InterruptedException e)
      {
         logger.error(ERROR_MESSAGE_PROBLEMS_EA_EXPORT + project.getName());
         logger.info(ERROR_MESSAGE_LATEST_VERSIONS);
         logger.error(MoflonUtil.displayExceptionAsString(e));
      }
   }

}
