package org.moflon.ide.ui.runtime.natures;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.moflon.ide.core.CoreActivator;

public class MOSLNature implements IProjectNature
{
   protected IProject project;

   @Override
   public void configure() throws CoreException
   {
      appendCommand(CoreActivator.MOSL_BUILDER_ID);

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

   public void appendCommand(final String builderId)
   {

      ICommand command = null;

      try
      {
         IProjectDescription desc = project.getDescription();

         command = desc.newCommand();
         command.setBuilderName(builderId);

         ICommand[] commands = desc.getBuildSpec();

         ICommand[] newCommands = new ICommand[commands.length + 1];

         // Prepend our new command
         System.arraycopy(commands, 0, newCommands, 1, commands.length);

         newCommands[0] = command;
         desc.setBuildSpec(newCommands);

         // Reset augmented description
         project.setDescription(desc, null);

      } catch (CoreException e)
      {
         e.printStackTrace();
      }
   }

}
