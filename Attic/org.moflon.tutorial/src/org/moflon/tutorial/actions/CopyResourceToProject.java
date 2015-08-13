package org.moflon.tutorial.actions;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
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

public class CopyResourceToProject extends Action implements ICheatSheetAction
{

   private static final Logger logger = Logger.getLogger(CopyResourceToProject.class);

   /**
    * copies the content of a resource folder into a project (or a subfolder inside a project) If the project does not
    * exist, it will be created.
    * 
    * @param params
    *           param1 is the resource folder, param2 is the path to the destination where the first part of that path
    *           is the name of the project. If param3 is specified and set to "nobuild" the destination project is not
    *           built after copying.
    */
   @Override
   public void run(final String[] params, final ICheatSheetManager manager)
   {
      try
      {

         // check if parameters are OK
         if (params[0] == null || params[1] == null)
         {
            System.err.println("Not enough parameters for CopyResourceToProject-Action!");
            return;
         }

         // determine target project (name is first part of param2)
         String projectName = params[1].split("/")[0];
         IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
         IProject targetProject = root.getProject(projectName);

         // if the target project is the eMoflon project, make sure it exists
         if (projectName.equals(Common.TUTORIAL_PROJECT_NAME))
         {
            if (!targetProject.exists())
            {
               new CreateMoflonProject().run(new String[9], manager);
            }

            // if the project still does not exist, something went wrong!
            if (!targetProject.exists())
            {
               logger.error("Cannot copy files because project '" + Common.TUTORIAL_PROJECT_NAME + "' does not exist and the attempt to create it failed!");
               return;
            }

            /*
             * Checking step for Chapter 3; we must confirm results of Chapter 1 (i.e., Target Language
             * 'SokobanLanguage' exists for TGG transformation) We do so by checking for a 'Server' class. Note: not
             * idea way to check, but we can't overwrite files from Chapter 1. That would be unexpected, and users may
             * have added changes/comments. Therefore, if 'Server' DNE, copy entire language from
             * resources/Chapter1/Final into workspace (path defined in common.java)
             */
            if (params[0].contains("Chapter3"))
            {
               IFile whitePawnFile = targetProject.getFolder("MOSL").getFolder(Common.TUTORIAL_WORKING_SET).getFolder(Common.GENERATED_PROJECT_NAME)
                     .getFile("Server.eclass");
               if (!whitePawnFile.exists())
               {
                  if (!MyWorkspaceHelper.copy(Common.CHAPTER1_FINAL_STEP_RESOURCES_LOCATION, Common.TUTORIAL_PROJECT_NAME + "/MOSL/"
                        + Common.TUTORIAL_WORKING_SET + "/" + Common.GENERATED_PROJECT_NAME))
                  {
                     logger.error("Failed to copy the files from the first chapter!");
                     System.err.println("Failed to copy the files from the first chapter!");
                     return;
                  }
               }
            }
         }

         // try to copy into the workspace. if that was successful, refresh the project and build it
         if (MyWorkspaceHelper.copy(params[0], params[1]))
         {
            try
            {
               targetProject.refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
               if (params.length < 3 || !"nobuild".equalsIgnoreCase(params[2]))
                  targetProject.build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
            } catch (CoreException | OperationCanceledException ex)
            {
               logger.error("Error while refreshing project");
               ex.printStackTrace();
               return;
            }
         } else
         {
            logger.error("Error while copying files!");
            System.err.println("Could not copy resource from " + params[0] + " into " + params[1] + " in the workspace.");
            return;
         }

      } catch (Exception e)
      {
         logger.error("An unexpected error occurred in action 'CopyResourceToProject':\n" + e.getMessage());
         e.printStackTrace();
      }
   }

}
