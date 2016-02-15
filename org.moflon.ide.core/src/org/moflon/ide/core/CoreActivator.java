package org.moflon.ide.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.moflon.core.utilities.EMoflonPlugin;
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

   public static final String METAMODEL_NATURE_ID = "org.moflon.ide.ui.runtime.natures.MetamodelNature";

   public static final String METAMODEL_BUILDER_ID = "org.moflon.ide.core.runtime.builders.MetamodelBuilder";

   public static final String INTEGRATION_BUILDER_ID = "org.moflon.ide.core.runtime.builders.IntegrationBuilder";

   public static final String ANTLR_NATURE_ID = "org.moflon.ide.ui.runtime.natures.AntlrNature";

   public static final String ANTLR_BUILDER_ID = "org.moflon.ide.core.runtime.builders.AntlrBuilder";

   public static final String JAVA_WORKING_SET_ID = "org.eclipse.jdt.ui.JavaWorkingSetPage";

   private Map<String, Boolean> isDirty = new HashMap<>();

   private List<DirtyProjectListener> dirtyProjectListeners;

   public static CoreActivator getDefault() {
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

      dirtyProjectListeners = new ArrayList<>();

      // Add mappings of all projects in Workspace for Ecore modelbrowser
      Job registrationJob = new RegisterUrlMappingsForAllProjectsJob();
      registrationJob.schedule();
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      dirtyProjectListeners = null;
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

   public boolean isDirty(final IProject project)
   {
      return project != null && isDirty(project.getName());
   }

   public boolean isDirty(final String projectName)
   {
      return isDirty.containsKey(projectName) ? isDirty.get(projectName) : false;
   }

   /**
    * Sets the projects dirty state.
    * 
    * A repository project is dirty if its corresponding metamodel project has been built recently so that new code
    * needs to be generated for this project. A metamodel project is dirty if its EAP file is newer than the generated
    * Moca tree (.temp/<project-name>.moca.xmi)
    * 
    * @param project
    * @param isDirty
    */
   public void setDirty(final IProject project, final boolean isDirty)
   {
      this.isDirty.put(project.getName(), isDirty);
      this.dirtyProjectListeners.forEach(l -> l.dirtyStateChanged(project, isDirty));
   }

   public void registerDirtyProjectListener(final DirtyProjectListener listener)
   {
      this.dirtyProjectListeners.add(listener);
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

}
