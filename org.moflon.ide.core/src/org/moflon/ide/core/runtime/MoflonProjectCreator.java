package org.moflon.ide.core.runtime;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.gervarro.eclipse.workspace.autosetup.JavaProjectConfigurator;
import org.gervarro.eclipse.workspace.autosetup.PluginProjectConfigurator;
import org.gervarro.eclipse.workspace.autosetup.ProjectConfigurator;
import org.gervarro.eclipse.workspace.autosetup.WorkspaceAutoSetupModule;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.propertycontainer.SDMCodeGeneratorIds;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.natures.MoflonProjectConfigurator;
import org.moflon.util.plugins.BuildPropertiesFileBuilder;
import org.moflon.util.plugins.MetamodelProperties;
import org.moflon.util.plugins.manifest.ManifestFileUpdater;
import org.moflon.util.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;
import org.moflon.util.plugins.manifest.PluginManifestConstants;

public class MoflonProjectCreator extends WorkspaceTask implements ProjectConfigurator
{
   private static final Logger logger = Logger.getLogger(MoflonProjectCreator.class);

   private IProject project;

   private MetamodelProperties metamodelProperties;

   public MoflonProjectCreator(final IProject project, final MetamodelProperties projectProperties)
   {
      this.project = project;
      this.metamodelProperties = projectProperties;
   }

   @Override
   public void run(final IProgressMonitor monitor) throws CoreException
   {
      if (!project.exists())
      {
         final String projectName = metamodelProperties.getProjectName();
         final SubMonitor subMon = SubMonitor.convert(monitor, "Creating project " + projectName, 13);

         // (1) Create project
         final IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(projectName);
         project.create(description, IWorkspace.AVOID_UPDATE, subMon.newChild(1));
         project.open(IWorkspace.AVOID_UPDATE, subMon.newChild(1));

         // (2) Configure natures and builders (.project file)
         final JavaProjectConfigurator javaProjectConfigurator = new JavaProjectConfigurator();
         final MoflonProjectConfigurator moflonProjectConfigurator = new MoflonProjectConfigurator(
               MetamodelProperties.INTEGRATION_KEY.equals(metamodelProperties.getType()));
         final PluginProjectConfigurator pluginProjectConfigurator = new PluginProjectConfigurator();
         final ProjectNatureAndBuilderConfiguratorTask natureAndBuilderConfiguratorTask = new ProjectNatureAndBuilderConfiguratorTask(project, false);
         natureAndBuilderConfiguratorTask.updateNatureIDs(moflonProjectConfigurator, true);
         natureAndBuilderConfiguratorTask.updateNatureIDs(javaProjectConfigurator, true);
         natureAndBuilderConfiguratorTask.updateBuildSpecs(javaProjectConfigurator, true);
         natureAndBuilderConfiguratorTask.updateBuildSpecs(moflonProjectConfigurator, true);
         natureAndBuilderConfiguratorTask.updateNatureIDs(pluginProjectConfigurator, true);
         natureAndBuilderConfiguratorTask.updateBuildSpecs(pluginProjectConfigurator, true);
         WorkspaceTask.executeInCurrentThread(natureAndBuilderConfiguratorTask, IWorkspace.AVOID_UPDATE, subMon.newChild(1));

         // (3) Create folders and files in project
         createFoldersIfNecessary(project, subMon.newChild(4));
         addGitignoreFileForRepositoryProject(project, subMon.newChild(2));
         addGitKeepFiles(project, subMon.newChild(2));

         // (4) Create MANIFEST.MF file
         try
         {
            logger.debug("Adding MANIFEST.MF");
            new ManifestFileUpdater().processManifest(project, manifest -> {
               boolean changed = false;
               changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.MANIFEST_VERSION, "1.0", AttributeUpdatePolicy.KEEP);
               changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_MANIFEST_VERSION, "2", AttributeUpdatePolicy.KEEP);
               changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_NAME,
                     metamodelProperties.get(MetamodelProperties.NAME_KEY), AttributeUpdatePolicy.KEEP);
               changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_SYMBOLIC_NAME,
                     metamodelProperties.get(MetamodelProperties.PLUGIN_ID_KEY) + ";singleton:=true", AttributeUpdatePolicy.KEEP);
               changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION, "1.0", AttributeUpdatePolicy.KEEP);
               changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VENDOR, "TU Darmstadt", AttributeUpdatePolicy.KEEP);
               changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_ACTIVATION_POLICY, "lazy", AttributeUpdatePolicy.KEEP);
               changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_EXECUTION_ENVIRONMENT,
                     metamodelProperties.get(MetamodelProperties.JAVA_VERION), AttributeUpdatePolicy.KEEP);
               return changed;
            });
         } catch (IOException e)
         {
            LogUtils.error(logger, e);
         }

         // (5) Create build.properties file
         logger.debug("Adding build.properties");
         new BuildPropertiesFileBuilder().createBuildProperties(project, subMon.newChild(1));

         // (6) Configure Java settings (.classpath file)
         final IJavaProject javaProject = JavaCore.create(project);
         final IClasspathEntry srcFolderEntry = JavaCore.newSourceEntry(WorkspaceHelper.getSourceFolder(project).getFullPath());

         // Integration projects contain a lot of (useful?) boilerplate code in /gen, which requires to ignore warnings such as 'unused variable', 'unused import' etc. 
         final IClasspathAttribute[] genFolderClasspathAttributes = metamodelProperties.isIntegrationProject()
               ? new IClasspathAttribute[] { JavaCore.newClasspathAttribute("ignore_optional_problems", "true") } : new IClasspathAttribute[] {};
         final IClasspathEntry genFolderEntry = JavaCore.newSourceEntry(project.getFolder(WorkspaceHelper.GEN_FOLDER).getFullPath(), new IPath[0], new IPath[0],
               null, genFolderClasspathAttributes);
         final IClasspathEntry jreContainerEntry = JavaCore.newContainerEntry(new Path("org.eclipse.jdt.launching.JRE_CONTAINER"));
         final IClasspathEntry pdeContainerEntry = JavaCore.newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins"));
         javaProject.setRawClasspath(new IClasspathEntry[] { srcFolderEntry, genFolderEntry, jreContainerEntry, pdeContainerEntry },
               WorkspaceHelper.getBinFolder(project).getFullPath(), true, subMon.newChild(1));

         // (7) Create Moflon properties file (moflon.properties.xmi)
         MoflonPropertiesContainer moflonProperties = MoflonPropertiesContainerHelper.createDefaultPropertiesContainer(project.getName(),
               metamodelProperties.getMetamodelProjectName());
         moflonProperties.getSdmCodegeneratorHandlerId().setValue(getCodeGeneratorHandler(metamodelProperties));
         MoflonPropertiesContainerHelper.save(moflonProperties, subMon.newChild(1));
      }
   }

   private final SDMCodeGeneratorIds getCodeGeneratorHandler(final MetamodelProperties metamodelProperties)
   {
      switch (metamodelProperties.getType())
      {
      case MetamodelProperties.INTEGRATION_KEY:
         return SDMCodeGeneratorIds.DEMOCLES_REVERSE_NAVI;
      default:
         if (metamodelProperties.hasAttributeConstraints())
         {
            return SDMCodeGeneratorIds.DEMOCLES_ATTRIBUTES;
         }
         else {
            return SDMCodeGeneratorIds.DEMOCLES;
         }
      }
   }

   public static void createFoldersIfNecessary(final IProject project, final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Creating folders within project " + project, 9);

      WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getSourceFolder(project), subMon.newChild(1));
      WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getBinFolder(project), subMon.newChild(1));
      WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getGenFolder(project), subMon.newChild(1));
      WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getLibFolder(project), subMon.newChild(1));
      WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getModelFolder(project), subMon.newChild(1));
      WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getInstancesFolder(project), subMon.newChild(1));
      WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getInjectionFolder(project), subMon.newChild(1));
   }

   /**
    * Adds a default .gitignore file to the given repository project to prevent adding generated files to the repository
    * 
    * @param project the project for which to generate the .gitignore file
    * @param monitor the progress monitor
    */
   public static void addGitignoreFileForRepositoryProject(final IProject project, final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Creating .gitignore file for " + project, 1);

      WorkspaceHelper.createGitignoreFileIfNotExists(project.getFile(WorkspaceHelper.GITIGNORE_FILENAME), //
            Arrays.asList(//
                  "/bin", //
                  "/gen/*", //
                  "/model/*.ecore", "/model/*.genmodel", "/model/*.xmi", //
                  "# The file AttrCondDefLibrary.tgg is not meant to be edited", //
                  "/**/AttrCondDefLibrary.tgg", 
                  "!/**/.keep*"), subMon.newChild(1));
   }
   /**
    * Adds a default .gitignore file to the given metamodel project to prevent adding generated files to the repository
    * 
    * @param project the project for which to generate the .gitignore file
    * @param monitor the progress monitor
    */
   public static void addGitignoreFileForMetamodelProject(final IProject project, final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Creating .gitignore file for " + project, 1);
      
      WorkspaceHelper.createGitignoreFileIfNotExists(project.getFile(WorkspaceHelper.GITIGNORE_FILENAME), //
            Arrays.asList(//
                  "/.temp", //
                  "/*.ldb"), subMon.newChild(1));
   }

   /**
    * Adds dummy files to folders that are / may be empty after project initialization.
    * 
    * The dummy files are required because Git does not support versioning empty folders (unlike SVN).
    * 
    * @param project the project for which .keep files shall be produced
    * @param monitor the progress monitor
    */
   public static void addGitKeepFiles(final IProject project, final IProgressMonitor monitor)
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Creating .keep* files for Git within project " + project, 3);

      WorkspaceHelper.createKeepFile(WorkspaceHelper.getSourceFolder(project), subMon.newChild(1));
      WorkspaceHelper.createKeepFile(WorkspaceHelper.getGenFolder(project), subMon.newChild(1));
      WorkspaceHelper.createKeepFile(WorkspaceHelper.getModelFolder(project), subMon.newChild(1));
   }

   @Override
   public String getTaskName()
   {
      return "Creating Moflon project";
   }

   @Override
   public ISchedulingRule getRule()
   {
      return ResourcesPlugin.getWorkspace().getRoot();
   }

   public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException
   {
      final String natureID = MetamodelProperties.REPOSITORY_KEY.equals(metamodelProperties.getType()) ? WorkspaceHelper.REPOSITORY_NATURE_ID
            : WorkspaceHelper.INTEGRATION_NATURE_ID;
      if (added)
      {
         if (ProjectUtil.indexOf(natureIDs, natureID) < 0)
         {
            natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
            natureIDs[natureIDs.length - 1] = natureID;
         }
      } else
      {
         int naturePosition = ProjectUtil.indexOf(natureIDs, natureID);
         if (naturePosition >= 0)
         {
            natureIDs = WorkspaceAutoSetupModule.remove(natureIDs, naturePosition);
         }
      }
      return natureIDs;
   }

   public ICommand[] updateBuildSpecs(final IProjectDescription description, ICommand[] buildSpecs, final boolean added) throws CoreException
   {
      final String builderID = MetamodelProperties.REPOSITORY_KEY.equals(metamodelProperties.getType()) ? CoreActivator.REPOSITORY_BUILDER_ID
            : CoreActivator.INTEGRATION_BUILDER_ID;
      if (added)
      {
         int javaBuilderPosition = ProjectUtil.indexOf(buildSpecs, "org.eclipse.jdt.core.javabuilder");
         int moflonBuilderPosition = ProjectUtil.indexOf(buildSpecs, builderID);
         if (moflonBuilderPosition < 0)
         {
            final ICommand manifestBuilder = description.newCommand();
            manifestBuilder.setBuilderName(builderID);
            buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
            moflonBuilderPosition = buildSpecs.length - 1;
            buildSpecs[moflonBuilderPosition] = manifestBuilder;
         }
         if (javaBuilderPosition < moflonBuilderPosition)
         {
            final ICommand moflonBuilder = buildSpecs[moflonBuilderPosition];
            System.arraycopy(buildSpecs, javaBuilderPosition, buildSpecs, javaBuilderPosition + 1, moflonBuilderPosition - javaBuilderPosition);
            moflonBuilderPosition = javaBuilderPosition++;
            buildSpecs[moflonBuilderPosition] = moflonBuilder;
         }
      } else
      {
         int moflonBuilderPosition = ProjectUtil.indexOf(buildSpecs, builderID);
         if (moflonBuilderPosition >= 0)
         {
            ICommand[] oldBuilderSpecs = buildSpecs;
            buildSpecs = new ICommand[oldBuilderSpecs.length - 1];
            if (moflonBuilderPosition > 0)
            {
               System.arraycopy(oldBuilderSpecs, 0, buildSpecs, 0, moflonBuilderPosition);
            }
            if (moflonBuilderPosition == buildSpecs.length)
            {
               System.arraycopy(oldBuilderSpecs, moflonBuilderPosition + 1, buildSpecs, moflonBuilderPosition, buildSpecs.length - moflonBuilderPosition);
            }
         }
      }
      return buildSpecs;
   }
}
