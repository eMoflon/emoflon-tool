package org.moflon.tutorial.actions;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.moflon.tutorial.Common;
import org.moflon.tutorial.helper.MyWorkspaceHelper;

public class CreateMoflonProject extends Action implements ICheatSheetAction
{

   private static final Logger logger = Logger.getLogger(CreateMoflonProject.class);

   /**
    * Creates the new eMoflon Project at the beginning of the tutorial. This will delete an existing Version of the
    * eMoflonTutorial as well as the projects generated during the tutorial.
    */
   @Override
   public void run(final String[] params, final ICheatSheetManager manager)
   {
      try
      {

         // get project object
         IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
         IProject moflonProject = root.getProject(Common.TUTORIAL_PROJECT_NAME);

         // check if project exists - if yes, return
         if (moflonProject.exists())
         {
            logger.info("Project '" + Common.TUTORIAL_PROJECT_NAME + "' already exists in the workspace.");
            return;
         }

         // create an empty new project
         try
         {
            moflonProject.create(new NullProgressMonitor());
            moflonProject.open(new NullProgressMonitor());
         } catch (CoreException | OperationCanceledException ex)
         {
            logger.error("Failed to create new project '" + Common.TUTORIAL_PROJECT_NAME + "'!");
            return;
         }

         // copy files into the project. Since the .project file is also copied, the nature of the project is also set!
         if (!MyWorkspaceHelper.copy(Common.INITIAL_TUTORIAL_PROJECT_RESOURCES_LOCATION, Common.TUTORIAL_PROJECT_NAME))
         {
            logger.error("Error while copying files into the new project!");
            return;
         }

         // after copying, the project must be updated, otherwise eclipse won't show the new files immediately.
         // of course we also build the project.
         try
         {
            moflonProject.refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
            moflonProject.build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
         } catch (CoreException | OperationCanceledException ex)
         {
            logger.error("Failed to refresh project '" + Common.TUTORIAL_PROJECT_NAME + "'!");
            return;
         }
      } catch (Exception e)
      {
         logger.error("An unexpected error occurred in action 'CreateMoflonProject':\n" + e.getMessage());
         e.printStackTrace();
      }
   }

}
