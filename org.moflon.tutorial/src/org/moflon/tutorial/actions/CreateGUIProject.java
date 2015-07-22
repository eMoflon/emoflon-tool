package org.moflon.tutorial.actions;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.tutorial.Common;
import org.moflon.tutorial.helper.MyWorkspaceHelper;

public class CreateGUIProject extends Action implements ICheatSheetAction
{

   private static final String MAIN_CLASS = "org.moflon.tutorial.sokobangamegui.Controller";
   private static final Logger logger = Logger.getLogger(CreateGUIProject.class);

   /**
    * Creates the GUI project by doing the following steps: <list> <li>Create empty project (delete existing)</li> <li>
    * Setup project as Java-Project</li> <li>Add eMOFLON-dependencies (by adding MOFLON_CONTAINER to buildpath)</li> <li>
    * Add dependency to other project (the generated project containing our Model)</li> <li>Copy files from resource
    * folder</li> <li>Build project</li> <li>Setup Launch-Configuration, so that the project can be run</li> </list>
    * 
    * @param params
    *           param1 is resource folder to copy GUI from
    */
   @Override
   public void run(final String[] params, final ICheatSheetManager manager)
   {
      try
      {

         IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
         IProject project = root.getProject(Common.GUI_PROJECT_NAME);

         // check if project exists already
         if (project.exists())
         {
            logger.info("GUI project exists already. No files copied!");
            return;
         }

         // try to create an empty project
         try
         {
            project.create(new NullProgressMonitor());
            project.open(new NullProgressMonitor());
         } catch (CoreException | OperationCanceledException e)
         {
            logger.error("Failed to create empty GUI project!");
            e.printStackTrace();
            return;
         }

         // Configure the project
         WorkspaceHelper.setUpAsJavaProject(project, new NullProgressMonitor());

         // copy files into the project and refresh the project after that
         if (MyWorkspaceHelper.copy(Common.GUI_PROJECT_RESOURCES_LOCATION, Common.GUI_PROJECT_NAME))
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

         // add the generated project as a dependency to the GUI
         IProject dependency = ResourcesPlugin.getWorkspace().getRoot().getProject(Common.GENERATED_PROJECT_NAME);
         if (dependency.exists())
         {
            try
            {
               // FIXME: Use plugin dependencies!
               // WorkspaceHelper.setProjectOnBuildpath(JavaCore.create(project), JavaCore.create(dependency), monitor);
            } catch (Exception e)
            {
               logger.error("Could not add the project " + Common.GENERATED_PROJECT_NAME + " as dependency to GUI.");
               e.printStackTrace();
               return;
            }
         } else
         {
            logger.error("The project " + Common.GENERATED_PROJECT_NAME + " is missing! Did you build the tutorial project?");
            System.err.println("The project " + Common.GENERATED_PROJECT_NAME + " does not exist and therefore no dependency can be added to the GUI.");
            return;
         }

         // make sure the GUI can be easily run via the green play button
         setUpLaunchConfiguration(project);

         // add gui to tutorial working set
         MyWorkspaceHelper.addProjectToWorkingSet(project, Common.TUTORIAL_WORKING_SET);

      } catch (Exception e)
      {
         logger.error("An unexpected error occurred in action 'CreateGUIProject':\n" + e.getMessage());
         e.printStackTrace();
      }
   }

   /**
    * creates the launch configuration, so that the GUI can easily be started without having to select the Main-Class
    * Code copied from org.moflon.ide.ui.admin.actions.StartIntegrator (and slightly modified)
    * 
    * @param project
    */
   private void setUpLaunchConfiguration(final IProject project)
   {
      ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
      ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
      ILaunchConfiguration[] configurations;
      try
      {
         configurations = manager.getLaunchConfigurations(type);

         // This can be removed and adjusted to allow for e.g. the last five runs
         for (int i = 0; i < configurations.length; i++)
         {
            ILaunchConfiguration configuration = configurations[i];
            if (configuration.getName().equals("BoardGameGUI"))
            {
               configuration.delete();
               break;
            }
         }

         ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, "BoardGameGUI");
         ;

         // The projects classpath should have all necessary entries
         workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, project.getProject().getName());
         workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MAIN_CLASS);

         workingCopy.doSave();

      } catch (CoreException e)
      {
         System.err.println("ERROR while trying to set up launch configuration for GUI-Project");
         e.printStackTrace();
      }
   }

}