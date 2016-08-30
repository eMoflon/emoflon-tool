package org.moflon.ide.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.EMoflonPlugin;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.UncheckedCoreException;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.builders.IntegrationBuilder;
import org.osgi.framework.BundleContext;

/**
 * The Activator controls the plug-in life cycle and contains state and functionality that can be used throughout the
 * plugin. Constants used in various places in the plugin should also be defined in the Activator.
 * 
 * Core (non gui) functionality that can be useful for other Moflon eclipse plugins should be implemented here.
 */
public class CoreActivator extends EMoflonPlugin
{
   private static final Logger logger = Logger.getLogger(CoreActivator.class);

   // The plug-in ID

   // Nature and builder IDs
   /**
    * @deprecated Use JavaCore.NATURE_ID directly (since eMoflon 2.2.1)
    */
   @Deprecated
   public static final String JAVA_NATURE_ID = JavaCore.NATURE_ID;

   public static final String REPOSITORY_BUILDER_ID = "org.moflon.ide.core.runtime.builders.RepositoryBuilder";

   /**
    * @deprecated Use {@link WorkspaceHelper#METAMODEL_NATURE_ID} directly
    */
   @Deprecated
   public static final String METAMODEL_NATURE_ID = "org.moflon.ide.core.runtime.natures.MetamodelNature";

   public static final String METAMODEL_BUILDER_ID = "org.moflon.ide.core.runtime.builders.MetamodelBuilder";

   public static final String INTEGRATION_BUILDER_ID = "org.moflon.ide.core.runtime.builders.IntegrationBuilder";

   public static final String ANTLR_NATURE_ID = "org.moflon.ide.core.runtime.natures.AntlrNature";

   public static final String ANTLR_BUILDER_ID = "org.moflon.ide.core.runtime.builders.AntlrBuilder";

   public static final String JAVA_WORKING_SET_ID = "org.eclipse.jdt.ui.JavaWorkingSetPage";

   private NatureMigrator natureMigrator;

   public static CoreActivator getDefault()
   {
      CoreActivator plugin = getPlugin(CoreActivator.class);
      if (plugin == null)
         throw new IllegalStateException("Plugin has not yet been set!");
      return plugin;
   }

   public static final String getModuleID()
   {
      return getDefault().getPluginId();
   }

   /**
    * Executed during plugin startup.
    * 
    * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
    */
   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);

      natureMigrator = new NatureMigrator();
      WorkspaceTask.execute(new WorkspaceObservationLifecycleManager(ResourcesPlugin.getWorkspace(), natureMigrator, true), false);
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      WorkspaceTask.execute(new WorkspaceObservationLifecycleManager(ResourcesPlugin.getWorkspace(), natureMigrator, false), false);
      super.stop(context);
   }

   /**
    * Used when the plugin has to store resources on the client machine and eclipse installation + current workspace.
    * This location reserved for the plugin is called the "state location" and is usually in
    * pathToWorkspace/.metadata/pluginName
    * 
    * @param filename
    *           Appended to the state location. This is the name of the resource to be saved.
    * @return path to location reserved for the plugin which can be used to store resources
    */
   public IPath getPathInStateLocation(final String filename)
   {
      return getStateLocation().append(filename);
   }

   /**
    * Adds a plugin-to-resource mapping to the global {@link URIConverter#URI_MAP}.
    * 
    * More precisely: If the given project is a plugin project, the following mapping is added to the map
    * 
    * platform:/plugin/[project-plugin-ID]/ to platform:/resource/[project-name]
    * 
    * @param project the project to be added
    */
   @Deprecated
   public static void addMappingForProject(final IProject project)
   {
      if (project.isAccessible())
      {
         final IPluginModelBase pluginModel = PluginRegistry.findModel(project);
         if (pluginModel != null)
         {
            String pluginID = project.getName();

            if (pluginModel.getBundleDescription() != null)
               pluginID = pluginModel.getBundleDescription().getSymbolicName();

            final URI pluginURI = URI.createPlatformPluginURI(pluginID + "/", true);
            final URI resourceURI = URI.createPlatformResourceURI(project.getName() + "/", true);

            URIConverter.URI_MAP.put(pluginURI, resourceURI);

            logger.debug("Add mapping for project " + project.getName() + ": " + pluginURI + " -> " + resourceURI);
         }
      }

   }

   public static void createProblemMarker(final IResource resource, final String message, final int severity, final String location)
   {
      try
      {
         IMarker marker = resource.createMarker(IMarker.PROBLEM);
         marker.setAttribute(IMarker.MESSAGE, message);
         marker.setAttribute(IMarker.SEVERITY, severity);
         marker.setAttribute(IMarker.LOCATION, location);
      } catch (CoreException e)
      {
         throw new UncheckedCoreException(e);
      }
   }

   public static final void deleteMarkers(final IResource resource, final String type, final boolean includeSubtypes, final int depth)
   {
      try
      {
         resource.deleteMarkers(type, includeSubtypes, depth);
      } catch (CoreException e)
      {
         throw new UncheckedCoreException(e);
      }
   }

   public static final int convertStatusSeverityToMarkerSeverity(final int severity) throws CoreException
   {
      switch (severity)
      {
      case IStatus.ERROR:
         return IMarker.SEVERITY_ERROR;
      case IStatus.WARNING:
         return IMarker.SEVERITY_WARNING;
      case IStatus.INFO:
         return IMarker.SEVERITY_INFO;
      default:
         break;
      }
      final IStatus invalidSeverityConversion = new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(),
            "Cannot convert severity " + severity + " to a marker");
      throw new CoreException(invalidSeverityConversion);
   }

   public static final IFile getEcoreFile(final IProject project)
   {
      final String ecoreFileName = MoflonUtil.getDefaultNameOfFileInProjectWithoutExtension(project.getName());
      return project.getFolder(WorkspaceHelper.MODEL_FOLDER).getFile(ecoreFileName + WorkspaceHelper.ECORE_FILE_EXTENSION);
   }

   public static final IProject[] getMetamodelProjects(final IProject[] projects)
   {
      final List<IProject> result = new ArrayList<IProject>(projects.length);
      for (final IProject project : projects)
      {
         if (project.isAccessible() && WorkspaceHelper.isMetamodelProjectNoThrow(project))
         {
            result.add(project);
         }
      }
      return result.toArray(new IProject[result.size()]);
   }

   public static final IProject[] getRepositoryAndIntegrationProjects(final IProject[] projects)
   {
      final List<IProject> result = new ArrayList<IProject>(projects.length);
      for (final IProject project : projects)
      {
         if (project.isAccessible() && WorkspaceHelper.isMoflonProjectNoThrow(project))
         {
            result.add(project);
         }
      }
      return result.toArray(new IProject[result.size()]);
   }

   public static final IProject[] getProjectsWithTextualSyntax(final IProject[] projects)
   {
      final List<IProject> result = getProjectsWithTextualSyntax(Arrays.asList(projects));
      return result.toArray(new IProject[result.size()]);
   }

   public static List<IProject> getProjectsWithTextualSyntax(final List<IProject> projects)
   {
      final List<IProject> result = new ArrayList<IProject>(projects.size());
      for (final IProject project : projects)
      {
         try
         {
            if (project.isAccessible() && project.hasNature(WorkspaceHelper.MOSL_TGG_NATURE))
            {
               result.add(project);
            }
         } catch (CoreException e)
         {
            // Do nothing: Skip erroneous projects
         }
      }
      return result;
   }

   public static final IProject[] getProjectsWithGraphicalSyntax(final IProject[] projects)
   {
      final List<IProject> result = new ArrayList<IProject>(projects.length);
      for (final IProject project : projects)
      {
         try
         {
            if (project.isAccessible() && !project.hasNature(WorkspaceHelper.MOSL_TGG_NATURE))
            {
               result.add(project);
            }
         } catch (CoreException e)
         {
            // Do nothing: Skip erroneous projects
         }
      }
      return result.toArray(new IProject[result.size()]);
   }

   public static final IBuildConfiguration[] getDefaultBuildConfigurations(final Collection<IProject> projects)
   {
      final List<IBuildConfiguration> result = new ArrayList<IBuildConfiguration>(projects.size());
      for (IProject project : projects)
      {
         try
         {
            result.add(project.getBuildConfig(IBuildConfiguration.DEFAULT_CONFIG_NAME));
         } catch (final CoreException e)
         {
            // Do nothing (i.e., ignore erroneous projects)
         }
      }
      return result.toArray(new IBuildConfiguration[result.size()]);
   }

   public static final IBuildConfiguration[] getDefaultBuildConfigurations(final IProject[] projects)
   {
      return getDefaultBuildConfigurations(Arrays.asList(projects));

   }

   public static final void checkCancellation(final IProgressMonitor monitor)
   {
      if (monitor != null && monitor.isCanceled())
      {
         throw new OperationCanceledException();
      }
   }

   public static String mapBuildKindToName(int buildType)
   {
      switch (buildType)
      {
      case IntegrationBuilder.AUTO_BUILD:
         return "auto";
      case IntegrationBuilder.FULL_BUILD:
         return "full";
      case IntegrationBuilder.CLEAN_BUILD:
         return "clean";
      case IntegrationBuilder.INCREMENTAL_BUILD:
         return "incremental";
      default:
         return "Unknown build type: " + buildType;
      }
   }

   //TODO@rkluge: Only during transition
   public static void removeOldStyleGitignoreAndKeepFiles(IProject project) throws CoreException
   {
      for (final IFile gitignoreFile : Arrays.asList(//
            WorkspaceHelper.getModelFolder(project).getFile(WorkspaceHelper.GITIGNORE_FILENAME),
            WorkspaceHelper.getGenFolder(project).getFile(WorkspaceHelper.GITIGNORE_FILENAME)))
      {
         if (gitignoreFile.exists())
            gitignoreFile.delete(true, new NullProgressMonitor());
      }
      for (final IFile oldKeepFiles : Arrays.asList(//
            WorkspaceHelper.getModelFolder(project).getFile(".keep"),
            WorkspaceHelper.getGenFolder(project).getFile(".keep"), //
            WorkspaceHelper.getSourceFolder(project).getFile(".keep")))
      {
         if (oldKeepFiles.exists())
            oldKeepFiles.delete(true, new NullProgressMonitor());
      }
   }
}
