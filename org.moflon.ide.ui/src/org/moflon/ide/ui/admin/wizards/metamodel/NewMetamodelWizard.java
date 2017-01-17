package org.moflon.ide.ui.admin.wizards.metamodel;

import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.ide.ui.UIActivator;
import org.moflon.ide.ui.WorkspaceHelperUI;

/**
 * The new metamodel wizard creates a new metamodel project with default directory structure and default files.
 * 
 */
public class NewMetamodelWizard extends AbstractMoflonWizard
{
   // Page containing controls for taking user input
   private NewMetamodelProjectInfoPage projectInfo;

   private static Logger logger = Logger.getLogger(NewMetamodelWizard.class);

   private static final String SPECIFICATION_WORKINGSET_NAME = "Specifications";

   @Override
   public void addPages()
   {
      projectInfo = new NewMetamodelProjectInfoPage();
      addPage(projectInfo);
   }

   @Override
   protected void doFinish(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Creating metamodel project", 8);

         final String projectName = projectInfo.getProjectName();
         final IPath location = projectInfo.getProjectLocation();

         // Create project
         final IProject newProjectHandle = createProject(projectName, UIActivator.getModuleID(), location, subMon.split(1));

         // generate default files
         final URL pathToDefaultEapFile = MoflonUtilitiesActivator.getPathRelToPlugIn("resources/defaultFiles/EAEMoflon.eap", UIActivator.getModuleID());
         WorkspaceHelper.addFile(newProjectHandle, projectName + ".eap", pathToDefaultEapFile, UIActivator.getModuleID(), subMon.split(1));

         MoflonProjectCreator.addGitignoreFileForMetamodelProject(newProjectHandle, subMon.split(1));

         WorkspaceHelper.addNature(newProjectHandle, WorkspaceHelper.METAMODEL_NATURE_ID, subMon.split(1));

         WorkspaceHelperUI.moveProjectToWorkingSet(newProjectHandle, SPECIFICATION_WORKINGSET_NAME);

         newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, subMon.split(1));

      } catch (Exception e)
      {
         LogUtils.error(logger, e, "Unable to add default EA project file.");
      }
   }

   /**
    * Creates a new project in current workspace
    * 
    * @param projectName
    *           name of the new project
    * @param monitor
    *           a progress monitor, or null if progress reporting is not desired
    * @param location
    *           the file system location where the project should be placed
    * @return handle to newly created project
    * @throws CoreException
    */
   private static IProject createProject(final String projectName, final String pluginId, final IPath location, final IProgressMonitor monitor)
         throws CoreException
   {
      SubMonitor subMon = SubMonitor.convert(monitor, "", 2);

      // Get project handle
      IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
      IProject newProject = root.getProject(projectName);

      // Use default location (in workspace)
      final IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(newProject.getName());
      description.setLocation(location);

      // Complain if project already exists
      if (newProject.exists())
      {
         throw new CoreException(new Status(IStatus.ERROR, pluginId, projectName + " exists already!"));
      }

      // Create project
      newProject.create(description, subMon.split(1));
      newProject.open(subMon.split(1));

      return newProject;
   }
}
