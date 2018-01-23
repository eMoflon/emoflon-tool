package org.moflon.ide.core.project;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.core.build.MoflonProjectCreator;
import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.core.plugins.PluginProperties;
import org.moflon.core.utilities.WorkspaceHelper;

public class IntegrationProjectCreator extends MoflonProjectCreator
{

   private static final List<String> GITIGNORE_LINES = Arrays.asList(//
         "/bin", //
         "/gen/*", //
         "/model/*.ecore", "/model/*.genmodel", "/model/*.xmi", //
         "# The file AttrCondDefLibrary.tgg is not meant to be edited", //
         "/**/AttrCondDefLibrary.tgg", "!/**/.keep*");

   /**
    * Pass-through constructor to {@link MoflonProjectCreator}
    * @param project the project to create
    * @param projectProperties the metadata to use
    * @param projectConfigurator the project configurator
    */
   public IntegrationProjectCreator(final IProject project, final PluginProperties projectProperties, final MoflonProjectConfigurator projectConfigurator)
   {
      super(project, projectProperties, projectConfigurator);
   }

   @Override
   protected List<String> getGitignoreLines()
   {
      return GITIGNORE_LINES;
   }

   @Override
   protected String getNatureId() throws CoreException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected String getBuilderId() throws CoreException
   {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * Adds a default .gitignore file to the given repository project to prevent adding generated files to the repository
    *
    * @param project the project for which to generate the .gitignore file
    * @param monitor the progress monitor
    */
   public static void addGitignoreFileForIntegrationProject(final IProject project, final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Creating .gitignore file for " + project, 1);

      WorkspaceHelper.createGitignoreFileIfNotExists(project.getFile(WorkspaceHelper.GITIGNORE_FILENAME), //
            GITIGNORE_LINES, subMon.split(1));
   }

}
