package org.moflon.gt.ui.wizards;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.ide.natures.EMoflonGTNature;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.ide.ui.admin.wizards.metamodel.AbstractMoflonProjectInfoPage;
import org.moflon.ide.ui.admin.wizards.metamodel.AbstractMoflonWizard;
import org.moflon.util.plugins.MetamodelProperties;
import org.moflon.util.plugins.PluginProducerWorkspaceRunnable;

public class NewMOSLGTProjectWizard extends AbstractMoflonWizard
{
   private static final Logger logger = Logger.getLogger(NewMOSLGTProjectWizard.class);

   public static final String NEW_MOSLGT_PROJECT_WIZARD_ID = "org.moflon.gt.ui.wizard.NewMOSLGTProjectWizard";

   protected AbstractMoflonProjectInfoPage projectInfo;

   @Override
   public void addPages()
   {
      projectInfo = new NewMOSLGTProjectInfoPage();
      addPage(projectInfo);
   }

   @Override
   protected void doFinish(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Creating eMoflon project", 8);

         String projectName = projectInfo.getProjectName();

         MetamodelProperties properties = new MetamodelProperties();
         properties.setDefaultValues();
         properties.put(MetamodelProperties.PLUGIN_ID_KEY, projectName);
         properties.put(MetamodelProperties.NAME_KEY, projectName);

         IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
         createProject(subMon, project, properties);
         subMon.worked(3);

         generateDefaultFiles(subMon, project);
         subMon.worked(3);

         ResourcesPlugin.getWorkspace().run(new PluginProducerWorkspaceRunnable(project, properties), subMon.split(1));
         subMon.worked(2);
      } catch (Exception e)
      {
         LogUtils.error(logger, e);
      }
   }

   protected void generateDefaultFiles(final IProgressMonitor monitor, IProject project) throws CoreException
   {
      final String projectName = project.getName();
      final SubMonitor subMon = SubMonitor.convert(monitor, "Creating default files in " + projectName, 2);

      final String defaultEcoreFile = generateDefaultEPackageForProject(projectName);
      final String relativePathToEcoreFile = MoflonUtil.getDefaultPathToEcoreFileInProject(projectName);
      WorkspaceHelper.addFile(project, relativePathToEcoreFile, defaultEcoreFile, subMon.split(1));

      final String defaultMgtFile = generateDefaultMgtFileContentForProject(projectName);
      final String path = projectName.replaceAll(Pattern.quote("."), "/");
      final String relativePathToMgtFile = "src/" + path + "/" + MoflonUtil.lastCapitalizedSegmentOf(projectName) + "." + WorkspaceHelper.EMOFLON_GT_EXTENSION;
      WorkspaceHelper.addAllFoldersAndFile(project, new Path(relativePathToMgtFile), defaultMgtFile, subMon.split(1));
   }

   private String generateDefaultEPackageForProject(String projectName)
   {
      return "<?xml version=\"1.0\" encoding=\"ASCII\"?>" + nl() + //
            "<ecore:EPackage xmi:version=\"2.0\"" + nl() + "xmlns:xmi=\"http://www.omg.org/XMI\"" + nl() + //
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + nl() + //
            "xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\"" + nl() + //
            "name=\"" + MoflonUtil.lastSegmentOf(projectName) + "\"" + nl() + //
            "nsURI=\"" + MoflonUtil.getDefaultURIToEcoreFileInPlugin(projectName) + "\"" + nl() + //
            "nsPrefix=\"" + projectName + "\">" + nl() + //
            "</ecore:EPackage>";
   }

   private String generateDefaultMgtFileContentForProject(final String projectName)
   {
      return "import \"platform:/resource/" + projectName + "/model/" + MoflonUtil.lastCapitalizedSegmentOf(projectName) + ".ecore\"" + nl() + //
            nl() + // 
            "package " + projectName + nl();
            
   }

   private static String nl()
   {
      return "\n";
   }

   // The monitor is allowed to perform 1 tick
   protected void createProject(IProgressMonitor monitor, IProject project, MetamodelProperties metamodelProperties) throws CoreException
   {
      metamodelProperties.put(MetamodelProperties.TYPE_KEY, MetamodelProperties.MOSLGT_REPOSITORY_KEY);
      MoflonProjectCreator createMoflonProject = new MoflonProjectCreator(project, metamodelProperties, new EMoflonGTNature());
      ResourcesPlugin.getWorkspace().run(createMoflonProject, SubMonitor.convert(monitor).split(1));
   }
}
