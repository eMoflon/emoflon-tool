package org.moflon.ide.ui.runtime.natures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.moflon.core.utilities.AntlrUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.ide.core.CoreActivator;

public class AntlrNature implements IProjectNature
{

   private IProject project;

   @Override
   public void configure() throws CoreException
   {
      // Get project description and add model builder
      IProjectDescription desc = project.getDescription();
      List<ICommand> originalCommands = Arrays.asList(desc.getBuildSpec());
      ICommand command = desc.newCommand();
      command.setBuilderName(CoreActivator.ANTLR_BUILDER_ID);

      List<ICommand> newCommands = new ArrayList<ICommand>(originalCommands.size() + 1);
      newCommands.add(command);
      newCommands.addAll(originalCommands);
      desc.setBuildSpec(newCommands.toArray(new ICommand[] {}));

      // Reset augmented description
      project.setDescription(desc, null);

      if (project instanceof IJavaProject)
      {
         IJavaProject javaProject = (IJavaProject) project;

         Collection<IClasspathEntry> classpathEntries = new HashSet<IClasspathEntry>();

         classpathEntries.addAll(Arrays.asList(javaProject.getRawClasspath()));
         IPath antlrPath = new Path(AntlrUtil.getAntrlPathUrl().toExternalForm().substring(5));
         classpathEntries.add(JavaCore.newLibraryEntry(antlrPath, null, null, null,
               new IClasspathAttribute[] { JavaCore.newClasspathAttribute("plugin_id", MoflonUtilitiesActivator.getDefault().getPluginId()) }, true));

         javaProject.setRawClasspath(classpathEntries.toArray(new IClasspathEntry[] {}), null);
      }
   }

   @Override
   public void deconfigure() throws CoreException
   {
   }

   @Override
   public IProject getProject()
   {
      return project;
   }

   @Override
   public void setProject(final IProject project)
   {
      this.project = project;
   }

}
