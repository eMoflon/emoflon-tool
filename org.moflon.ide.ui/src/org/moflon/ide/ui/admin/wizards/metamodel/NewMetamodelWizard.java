package org.moflon.ide.ui.admin.wizards.metamodel;

import static org.moflon.ide.ui.admin.wizards.util.WizardUtil.loadStringTemplateGroup;
import static org.moflon.ide.ui.admin.wizards.util.WizardUtil.renderTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.ui.UIActivator;

/**
 * The new metamodel wizard creates a new metamodel project with default directory structure and default files.
 * 
 */
public class NewMetamodelWizard extends Wizard
{
   // Page containing controls for taking user input
   private NewMetamodelProjectInfoPage projectInfo;

   private static Logger logger = Logger.getLogger(UIActivator.class);

   private static final String SPECIFICATION_WORKINGSET_NAME = "Specifications";

   public NewMetamodelWizard()
   {
      setNeedsProgressMonitor(true);
   }

   @Override
   public void addPages()
   {
      projectInfo = new NewMetamodelProjectInfoPage();
      addPage(projectInfo);
   }

   @Override
   public boolean performFinish()
   {
      IRunnableWithProgress op = new IRunnableWithProgress() {
         @Override
         public void run(final IProgressMonitor monitor) throws InvocationTargetException
         {
            try
            {
               doFinish(monitor);
            } catch (CoreException e)
            {
               throw new InvocationTargetException(e);
            } finally
            {
               monitor.done();
            }
         }
      };

      try
      {
         getContainer().run(true, false, op);
      } catch (InterruptedException e)
      {
         return false;
      } catch (InvocationTargetException e)
      {
         Throwable realException = e.getTargetException();
         MessageDialog.openError(getShell(), "Error", realException.getMessage());
         return false;
      }

      return true;
   }

   private void doFinish(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Creating metamodel project", 8);

         String projectName = projectInfo.getProjectName();
         IPath location = projectInfo.getProjectLocation();

         // Create project
         IProject newProjectHandle = WorkspaceHelper.createProject(projectName, UIActivator.getModuleID(), location , WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         // Add directory folders MOSL and imports file
         if (projectInfo.textual())
         {
            WorkspaceHelper.addFolder(newProjectHandle, "MOSL", WorkspaceHelper.createSubmonitorWith1Tick(monitor));

            // No demo selected: create working set folder and load empty constraints
            WorkspaceHelper.addFolder(newProjectHandle, "MOSL/MyWorkingSet", WorkspaceHelper.createSubmonitorWith1Tick(monitor));

            StringTemplateGroup stg = loadStringTemplateGroup("/resources/mosl/templates/imports.stg");

            Map<String, Object> attrs = new HashMap<String, Object>();
            String content = renderTemplate(stg, "imports", attrs);

            WorkspaceHelper.addFile(newProjectHandle, "MOSL/_MOSLConfiguration.mconf", content, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

            monitor.worked(1);
         } else /* EA/visual metamodel project */
         {
            // generate default files
            WorkspaceHelper.addFile(newProjectHandle, projectName + ".eap",
                  MoflonUtilitiesActivator.getPathRelToPlugIn("resources/defaultFiles/EAEMoflon.eap", UIActivator.getModuleID()), UIActivator.getModuleID(),
                  WorkspaceHelper.createSubmonitorWith1Tick(monitor));

            WorkspaceHelper.addFile(newProjectHandle, ".gitignore", ".temp", WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         }

         // Add Nature and Builders
         WorkspaceHelper.addNature(newProjectHandle, CoreActivator.METAMODEL_NATURE_ID, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         // Add Nature and Builders for MOSL
         if (projectInfo.textual())
            WorkspaceHelper.addNature(newProjectHandle, CoreActivator.MOSL_NATURE_ID, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         WorkspaceHelper.moveProjectToWorkingSet(newProjectHandle, SPECIFICATION_WORKINGSET_NAME);

         newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

      } catch (Exception e)
      {
         logger.error("Unable to add default EA project file: " + e);
         e.printStackTrace();
      } finally
      {
         monitor.done();
      }
   }
}
