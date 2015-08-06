package org.moflon.util.plugins;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;

public class BuildPropertiesFileBuilder
{

   private static final String BUILD_PROPERTIES_NAME = "build.properties";

   public void createBuildProperties(final IProject currentProject, final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Creating build.properties", 2);

         IFile file = getBuildPropertiesFile(currentProject);
         if (file.exists())
         {
            // Do not touch existing build.properties
            monitor.worked(2);
         } else
         {
            Properties buildProperties = new Properties();
            buildProperties.put("bin.includes", "META-INF/,bin/,model/,injection/,plugin.xml");
            buildProperties.put("source..", "src/,gen/");
            buildProperties.put("output..", "bin/");

            monitor.worked(1);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            buildProperties.store(stream, "");

            if (!file.exists())
            {
               WorkspaceHelper.addFile(currentProject, BUILD_PROPERTIES_NAME, stream.toString(), WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            } else
            {
               file.setContents(new ByteArrayInputStream(stream.toByteArray()), true, true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            }
         }
      } catch (IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, MoflonUtilitiesActivator.getDefault().getPluginId(), "Error while creating build.properties: " + e.getMessage()));
      } finally
      {
         monitor.done();
      }
   }

   public IFile getBuildPropertiesFile(final IProject currentProject)
   {
      return currentProject.getFile(BUILD_PROPERTIES_NAME);
   }

}
