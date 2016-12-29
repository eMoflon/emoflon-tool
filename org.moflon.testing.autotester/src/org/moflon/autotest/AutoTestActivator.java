package org.moflon.autotest;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetPlatformService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.moflon.autotest.core.WorkspaceInstaller;
import org.moflon.ide.ui.admin.MoflonPerspective;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class AutoTestActivator extends AbstractUIPlugin
{

   private static final Logger logger = Logger.getLogger(AutoTestActivator.class);

   // The shared instance
   private static AutoTestActivator plugin;

   private ITargetPlatformService targetPlatformService;

   private ITargetDefinition moflonTargetDefinition;

   private ProvisioningUI ui;

   private static String bundleId;

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
      bundleId = context.getBundle().getSymbolicName();
      plugin = this;
      plugin.targetPlatformService = (ITargetPlatformService) acquireService(context, ITargetPlatformService.class.getName());
   }

   public void autoStart(final IProgressMonitor monitor)
   {
      final IWorkbench workbench = PlatformUI.getWorkbench();

      workbench.getDisplay().asyncExec(new Runnable() {
         @Override
         public void run()
         {
            final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
            if (window != null)
            {
               if (workspaceContainsNoProjects())
               {
                  IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

                  MoflonPerspective.switchToMoflonPerspective(workbench);

                  final String workspaceName = root.getLocation().lastSegment();

                  logger.debug("Autorunning workspace '" + workspaceName + "'");

                  new WorkspaceInstaller().installWorkspaceByName(workspaceName);

               }

            }
         }

      });
   }

   private boolean workspaceContainsNoProjects()
   {
      final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
      return root.getProjects().length == 0;
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      plugin = null;
      super.stop(context);
   }

   /**
    * Returns the shared instance
    * 
    * @return the shared instance
    */
   public static AutoTestActivator getDefault()
   {
      return plugin;
   }

   public final ITargetPlatformService getTargetPlatformService()
   {
      return targetPlatformService;
   }

   public final ITargetDefinition getMoflonTargetDefinition()
   {
      if (moflonTargetDefinition == null)
      {
         final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("MoflonIdeUpdateSite");
         if (project.isAccessible())
         {
            final IFile moflonTargetDefinitionFile = project.getFile("moflon.target");
            if (moflonTargetDefinitionFile.isAccessible())
            {
               try
               {
                  moflonTargetDefinition = targetPlatformService.getTarget(moflonTargetDefinitionFile).getTargetDefinition();
               } catch (CoreException e)
               {
                  // Do nothing
               }
            }
         }

      }
      return moflonTargetDefinition;
   }

   public final ProvisioningUI getProvisioningUI()
   {
      if (ui == null)
      {
         final ITargetDefinition definition = getMoflonTargetDefinition();
         if (definition != null)
         {
            ui = (ProvisioningUI) Platform.getAdapterManager().loadAdapter(definition, "org.eclipse.equinox.p2.ui.ProvisioningUI");
         }
      }
      return ui;
   }

   public static final Object acquireService(final BundleContext context, final String serviceName)
   {
      ServiceReference<?> reference = context.getServiceReference(serviceName);
      if (reference == null)
         return null;
      Object service = context.getService(reference);
      if (service != null)
      {
         context.ungetService(reference);
      }
      return service;
   }

   /**
    * Returns an image descriptor for the image file at the given plug-in relative path
    * 
    * @param path
    *           the path
    * @return the image descriptor
    */
   public static ImageDescriptor getImageDescriptor(final String path)
   {
      return imageDescriptorFromPlugin(getModuleID(), path);
   }
}