package org.moflon.ide.ui;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
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
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.DirtyProjectListener;
import org.moflon.ide.ui.decorators.MoflonDirtyProjectDecorator;
import org.osgi.framework.BundleContext;

/**
 * The Activator controls the plug-in life cycle and contains state and functionality that can be used throughout the
 * plugin. Constants used in various places in the plugin should also be defined in the Activator.
 * 
 */
public class UIActivator extends AbstractUIPlugin
{

   private static Logger logger = Logger.getLogger(UIActivator.class);

   /**
    * Use {@link UIActivator#getModuleID()}
    */
   @Deprecated 
   public static final String PLUGIN_ID = "org.moflon.ide.ui"; //$NON-NLS-1$

   // The shared instance
   private static UIActivator plugin;

   private static String bundleId;

   // IDs used in plugin (have to be synchronized with values in plugin.xml)

   public static final String JAVA_PACKAGE_EXPLORER_ID = "org.eclipse.jdt.ui.PackageExplorer";

   public static final String LAUNCH_ACTION_SET_ID = "org.eclipse.debug.ui.launchActionSet";

   public static final String MOFLON_ACTION_SET_ID = "org.moflon.ide.ui.actionSet";

   public static final String NEW_METAMODEL_WIZARD_ID = "org.moflon.ide.ui.admin.wizards.metamodel.NewMetamodelWizard";

   public static final String ADD_PARSER_AND_UNPARSER_WIZARD_ID = "org.moflon.ide.ui.admin.wizards.moca.AddParserAndUnparserWizard";

   public static final String NEW_TESTFRAMEWORK_WIZARD_ID = "org.moflon.ide.ui.admin.wizards.testframework.NewTestframeworkWizard";

   public static String getModuleID()
   {
      if (bundleId == null)
         throw new NullPointerException();
      else
         return bundleId;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
    */
   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      plugin = this;
      bundleId = context.getBundle().getSymbolicName();

      CoreActivator.getDefault().reconfigureLogging();
      CoreActivator.getDefault().registerDirtyProjectListener(new DirtyProjectListener() {

         @Override
         public void dirtyStateChanged(final IProject project, final boolean isDirty)
         {
            final MoflonDirtyProjectDecorator decorator = (MoflonDirtyProjectDecorator) PlatformUI.getWorkbench().getDecoratorManager()
                  .getBaseLabelProvider(MoflonDirtyProjectDecorator.DECORATOR_ID);
            if (decorator != null)
            {
               Display.getDefault().asyncExec(new Runnable() {
                  @Override
                  public void run()
                  {
                     decorator.projectStateChanged(project);
                  }
               });
            }
         }
      });
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
    */
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
         logger.error("Unable to open file: " + file.getAbsolutePath());
         e.printStackTrace();
      }
   }

   public void openConfigFileInEditor(final IWorkbenchWindow window)
   {
      openFileInEditor(window, CoreActivator.getDefault().getConfigFile());
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
      return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, pathToIcon);
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

}
