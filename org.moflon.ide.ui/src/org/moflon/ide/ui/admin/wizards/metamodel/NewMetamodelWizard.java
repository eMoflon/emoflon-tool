package org.moflon.ide.ui.admin.wizards.metamodel;

import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
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
         final IProject newProjectHandle = WorkspaceHelper.createProject(projectName, UIActivator.getModuleID(), location, subMon.newChild(1));

         // generate default files
         final URL pathToDefaultEapFile = MoflonUtilitiesActivator.getPathRelToPlugIn("resources/defaultFiles/EAEMoflon.eap", UIActivator.getModuleID());
         WorkspaceHelper.addFile(newProjectHandle, projectName + ".eap", pathToDefaultEapFile, UIActivator.getModuleID(), subMon.newChild(1));

         MoflonProjectCreator.addGitignoreFileForMetamodelProject(newProjectHandle, subMon.newChild(1));

         WorkspaceHelper.addNature(newProjectHandle, CoreActivator.METAMODEL_NATURE_ID, subMon.newChild(1));

         WorkspaceHelperUI.moveProjectToWorkingSet(newProjectHandle, SPECIFICATION_WORKINGSET_NAME);

         newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, subMon.newChild(1));

      } catch (Exception e)
      {
         LogUtils.error(logger, e, "Unable to add default EA project file.");
      }
   }

}
