package org.moflon.ide.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.ui.console.MoflonConsole;
import org.moflon.ide.ui.decorators.MoflonProjectDecorator;
import org.osgi.framework.BundleContext;

/**
 * The Activator controls the plug-in life cycle and contains state and functionality that can be used throughout the
 * plugin. Constants used in various places in the plugin should also be defined in the Activator.
 * 
 */
public class UIActivator extends AbstractUIPlugin
{

   private static Logger logger = Logger.getLogger(UIActivator.class);

   // The shared instance
   private static UIActivator plugin;

   // IDs used in plugin (have to be synchronized with values in plugin.xml)

   public static final String JAVA_PACKAGE_EXPLORER_ID = "org.eclipse.jdt.ui.PackageExplorer";

   public static final String LAUNCH_ACTION_SET_ID = "org.eclipse.debug.ui.launchActionSet";

   public static final String MOFLON_ACTION_SET_ID = "org.moflon.ide.ui.actionSet";

   public static final String NEW_METAMODEL_WIZARD_ID = "org.moflon.ide.ui.admin.wizards.metamodel.NewMetamodelWizard";

   public static final String ADD_PARSER_AND_UNPARSER_WIZARD_ID = "org.moflon.ide.ui.admin.wizards.moca.AddParserAndUnparserWizard";

   public static final String NEW_TESTFRAMEWORK_WIZARD_ID = "org.moflon.ide.ui.admin.wizards.testframework.NewTestframeworkWizard";

   // Log4J file
   private static final String LOG4J_CONFIG_PROPERTIES = "log4jConfig.properties";

   // Default resources path
   private static final String RESOURCES_DEFAULT_FILES_PATH = "resources/defaultFiles/";

   private static String bundleId;

   // The config file used for logging in plugin
   private File configFile;

   public static String getModuleID()
   {
      if (bundleId == null)
         throw new NullPointerException();
      else
         return bundleId;
   }

   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      plugin = this;
      bundleId = context.getBundle().getSymbolicName();

      setUpLogging();

      registerDecoratorListeners();
      registerListenerForMetaModelProjectRenaming();
   }

   /**
    * Registers a listener that identifies EAP projects that are outdated
    */
   private void registerDecoratorListeners()
   {
      ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

         @Override
         public void resourceChanged(final IResourceChangeEvent event)
         {
            try
            {
               event.getDelta().accept(new IResourceDeltaVisitor() {

                  @Override
                  public boolean visit(final IResourceDelta delta) throws CoreException
                  {
                     IResource resource = delta.getResource();
                     if (resource instanceof IProject)
                     {
                        final IProject project = (IProject) resource;
                        if (WorkspaceHelper.isMetamodelProjectNoThrow(project))
                        {
                           final IFile eapFile = WorkspaceHelper.getEapFileFromMetamodelProject(project);
                           final IFile xmiTree = WorkspaceHelper.getExportedMocaTree(project);
                           // EA writes to an EAP file immediately after exporting it.
                           // Without this timeout, 'needRebuild' would always be true.
                           final long outdatedXmiTreeToleranceInMillis = 5000;
                           final boolean needsRebuild = !xmiTree.exists()
                                 || (xmiTree.exists() && xmiTree.getLocalTimeStamp() + outdatedXmiTreeToleranceInMillis < eapFile.getLocalTimeStamp());

                           Display.getDefault().asyncExec(new Runnable() {
                              @Override
                              public void run()
                              {
                                 final MoflonProjectDecorator decorator = (MoflonProjectDecorator) PlatformUI.getWorkbench().getDecoratorManager()
                                       .getBaseLabelProvider(MoflonProjectDecorator.DECORATOR_ID);
                                 if (decorator != null) {
                                	 decorator.setMetamodelProjectRequiresRebuild(project, needsRebuild);
                                 }
                              }
                           });
                        }

                        return false;
                     } else
                     {
                        return true;
                     }
                  }
               });

               event.getDelta().accept(new IResourceDeltaVisitor() {

                  @Override
                  public boolean visit(IResourceDelta delta) throws CoreException
                  {
                     final IResource resource = delta.getResource();
                     final IProject project;
                     if (resource instanceof IProject)
                     {
                        project = (IProject) resource;
                     } else if (resource instanceof IJavaProject)
                     {
                        project = ((IJavaProject) resource).getProject();
                     } else
                     {
                        project = null;
                     }

                     if (project != null && project.isAccessible())
                     {
                        ICommand[] buildSpec = project.getDescription().getBuildSpec();
                        for (final ICommand builder : buildSpec)
                        {
                           if (WorkspaceHelper.REPOSITORY_BUILDER_ID.equals(builder.getBuilderName()) || 
                                 WorkspaceHelper.INTEGRATION_BUILDER_ID.equals(builder.getBuilderName()))
                           {
                              boolean autobuildEnabled = builder.isBuilding(IncrementalProjectBuilder.AUTO_BUILD);
                              Display.getDefault().asyncExec(new Runnable() {
                                 @Override
                                 public void run()
                                 {
                                    final MoflonProjectDecorator decorator = (MoflonProjectDecorator) PlatformUI.getWorkbench().getDecoratorManager()
                                          .getBaseLabelProvider(MoflonProjectDecorator.DECORATOR_ID);
                                    if (decorator != null) {
                                    	decorator.setAutobuildEnabled(project, autobuildEnabled);
                                    }
                                 }
                              });
                           }
                        }

                        return false;
                     }

                     return true;
                  }
               });
            } catch (CoreException e)
            {
               LogUtils.error(logger, e);
            }
         }
      }, IResourceChangeEvent.POST_CHANGE);
   }

   /**
    * Registers a {@link IResourceChangeListener} for detecting renamings of meta-model projects. According to our
    * convention, the name of the EAP file of a meta-model project equals the project name plus the suffix ".eap".
    */
   private void registerListenerForMetaModelProjectRenaming()
   {
      ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

         @Override
         public void resourceChanged(IResourceChangeEvent event)
         {

            IResourceDelta[] children = event.getDelta().getAffectedChildren();
            if (children.length == 2)
            {
               final IResource firstResource = children[0].getResource();
               final IResource secondResource = children[1].getResource();
               if (firstResource instanceof IProject && secondResource instanceof IProject)
               {
                  final IProject oldProject;
                  final IProject newProject;
                  if ((children[0].getFlags() & IResourceDelta.MOVED_TO) != 0)
                  {
                     oldProject = (IProject) firstResource;
                     newProject = (IProject) secondResource;
                  } else if ((children[1].getFlags() & IResourceDelta.MOVED_TO) != 0)
                  {
                     oldProject = (IProject) secondResource;
                     newProject = (IProject) firstResource;
                  } else
                  {
                     oldProject = null;
                     newProject = null;
                  }
                  if (oldProject != null && WorkspaceHelper.isMetamodelProjectNoThrow(newProject))
                  {
                     IFile eapFile = newProject.getFile(oldProject.getName() + ".eap");
                     if (eapFile.exists())
                     {
                        WorkspaceJob job = new WorkspaceJob("Renaming EAP file") {

                           @Override
                           public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException
                           {
                              try
                              {
                                 eapFile.move(new Path(newProject.getName() + ".eap"), true, null);
                                 return Status.OK_STATUS;
                              } catch (final CoreException e)
                              {
                                 return new Status(IStatus.ERROR, UIActivator.getModuleID(), "Failed to move EAP file " + eapFile, e);
                              }
                           }
                        };
                        job.schedule();

                     }
                  }
               }
            }
         }
      }, IResourceChangeEvent.POST_CHANGE);
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      plugin = null;
      bundleId = null;
      super.stop(context);
   }

   public static UIActivator getDefault()
   {
      return plugin;
   }

   public void openFileInEditor(final IWorkbenchWindow window, final File file)
   {
      IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
      try
      {
         IDE.openEditorOnFileStore(window.getActivePage(), fileStore);
      } catch (PartInitException e)
      {
         LogUtils.error(logger, e, "Unable to open file: " + file.getAbsolutePath());
      }
   }

   public void openConfigFileInEditor(final IWorkbenchWindow window)
   {
      openFileInEditor(window, getDefault().getConfigFile());
   }

   public static void openWizard(final String newModelWizardId, final IWorkbenchWindow window) throws CoreException
   {
      openWizard(newModelWizardId, window, null);
   }

   /**
    * Opens the specified wizard and initializes it with the given selection.
    * 
    * @param newModelWizardId
    * @param window
    * @param selection
    * @throws CoreException
    */
   public static void openWizard(final String newModelWizardId, final IWorkbenchWindow window, final IStructuredSelection selection) throws CoreException
   {
      // Search for wizard
      IWorkbenchWizard wizard = window.getWorkbench().getNewWizardRegistry().findWizard(newModelWizardId).createWizard();

      // Initialize and open dialogue
      wizard.init(window.getWorkbench(), selection);
      WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
      dialog.open();

   }

   public static ImageDescriptor getImage(final String pathToIcon)
   {
      return AbstractUIPlugin.imageDescriptorFromPlugin(getModuleID(), pathToIcon);
   }

   public static void showMessage(final String title, final String message)
   {
      Display.getDefault().asyncExec(new Runnable() {
         @Override
         public void run()
         {
            MessageDialog.openInformation(null, title, message);
         }
      });
   }

   public static void openError(final String title, final String message)
   {
      Display.getDefault().asyncExec(new Runnable() {
         @Override
         public void run()
         {
            MessageDialog.openError(null, title, message);
         }
      });
   }

   /**
    * Initialize log and configuration file. Configuration file is created with default contents if necessary. Log4J is
    * setup properly and configured with a console and logfile appender.
    */
   private void setUpLogging()
   {
      // Create configFile if necessary also in plugin storage space
      configFile = getPathInStateLocation(LOG4J_CONFIG_PROPERTIES).toFile();

      if (!configFile.exists())
      {
         try
         {
            // Copy default configuration to state location
            URL defaultConfigFile = MoflonUtilitiesActivator.getPathRelToPlugIn(RESOURCES_DEFAULT_FILES_PATH + LOG4J_CONFIG_PROPERTIES, getModuleID());

            FileUtils.copyURLToFile(defaultConfigFile, configFile);
         } catch (Exception e)
         {
            LogUtils.error(logger, e, "Unable to open default config file.");
         }
      }

      // Configure Log4J
      reconfigureLogging();
   }

   /**
    * Call to ensure that log4j is setup properly and configured. Can be called multiple times. Forces Plugin to be
    * loaded and started, ensuring that log file and config file exist.
    */
   public void reconfigureLogging()
   {
      try
      {
         configureLogging(configFile.toURI().toURL());
      } catch (MalformedURLException e)
      {
         LogUtils.error(logger, e, "URL to configFile is malformed: " + configFile);
      }
   }

   /**
    * Set up logging globally
    * 
    * @param configFile
    *           URL to log4j property configuration file
    */
   public static boolean configureLogging(final URL configFile)
   {
      try
      {
         Logger root = Logger.getRootLogger();
         String configurationStatus = "";
         if (configFile != null)
         {
            // Configure system using config
            PropertyConfigurator.configure(configFile);
            configurationStatus = "Log4j successfully configured using " + configFile;
         } else
         {
            configurationStatus = "Set up logging without config file!";
         }

         // Set format and scheme for output
         Logger.getRootLogger().addAppender(new MoflonConsole(configFile));

         // Indicate success
         root.info("Logging to eMoflon console. Configuration: " + configurationStatus);
         return true;
      } catch (Exception e)
      {
         LogUtils.error(logger, e);
         return false;
      }
   }

   /**
    * @return Logging configuration file in state location of client (usually
    *         $workspace/.metadata/.plugins/org.moflon.ide.core/log4jConfig.properties)
    */
   public File getConfigFile()
   {
      return configFile;
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
}
