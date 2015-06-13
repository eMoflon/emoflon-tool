package org.moflon.tutorial.actions;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.moflon.tutorial.Common;
import org.moflon.tutorial.helper.MyWorkspaceHelper;

public class UpdateGUIProject extends Action implements ICheatSheetAction
{

   private static final Logger logger = Logger.getLogger(UpdateGUIProject.class);

   @Override
   public void run(final String[] params, final ICheatSheetManager manager)
   {
      try
      {
         IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
         IProject project = root.getProject(Common.GUI_PROJECT_NAME);

         // check if project exists already
         // if not create it with the CreateGUIProject-Action
         if (!project.exists())
         {
            new CreateGUIProject().run(new String[9], manager);

            if (!project.exists())
            {
               logger.error("GUI project does not exist and could not be created!");
               return;
            }
         }

         // configure the project
         // FIXME: Add necessary plugin dependencies?

         // copy new files into the project and refresh the project after that
         if (MyWorkspaceHelper.copy(Common.GUI_UPDATE_RESOURCES_LOCATION, Common.GUI_PROJECT_NAME))
         {
            try
            {
               project.refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
            } catch (CoreException | OperationCanceledException e)
            {
               logger.error("Failed to refresh GUI project!");
               e.printStackTrace();
               return;
            }
         } else
         {
            logger.error("Failed to copy files into GUI project!");
            System.err.println("Could not copy files from resources into GUI project");
            return;
         }

         // add the TGG (and parser) project as a dependency to the GUI
         IProject dependency = ResourcesPlugin.getWorkspace().getRoot().getProject(Common.GENERATED_TGG_PROJECT_NAME);
         if (dependency.exists())
         {
            try
            {
               // FIXME: Use plugin dependencies here!
               // WorkspaceHelper.setProjectOnBuildpath(JavaCore.create(project), JavaCore.create(dependency), monitor);
            } catch (Exception e)
            {
               logger.error("Could not add the project " + Common.GENERATED_TGG_PROJECT_NAME + " as dependency to GUI.");
               e.printStackTrace();
               return;
            }
         } else
         {
            logger.error("The project " + Common.GENERATED_TGG_PROJECT_NAME + " is missing! Did you build the tutorial project?");
            System.err.println("The TGG project does not exist and therefore no dependency can be added to the GUI.");
            return;
         }

      } catch (Exception e)
      {
         logger.error("An unexpected error occurred in action 'UpdateGUIProject':\n" + e.getMessage());
         e.printStackTrace();
      }

   }

}
