package org.moflon.ide.debug.ui.launcher;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab;
import org.eclipse.jdt.internal.debug.ui.IJavaDebugHelpContextIds;
import org.eclipse.jdt.internal.debug.ui.launcher.DebugTypeSelectionDialog;
import org.eclipse.jdt.internal.debug.ui.launcher.LauncherMessages;
import org.eclipse.jdt.internal.debug.ui.launcher.MainMethodSearchEngine;
import org.eclipse.jdt.internal.debug.ui.launcher.SharedJavaMainTab;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.ISharedImages;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;

/**
 * Deeply inspired by {@link JavaMainTab}. As they recommend no to subclass this class is partly a copy.
 *
 * @author Marco Ballhausen
 * @author (last editor) $Author$
 * @version $Revision$ $Date$ 2015
 */
@SuppressWarnings("restriction")
public class MoflonMainTab extends SharedJavaMainTab
{

   /**
    * Defines the super class for a synchronizer.
    */
   private static final String SUPER_CLASS_NAME = SynchronizationHelper.class.getName();

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
    */
   public void createControl(Composite parent)
   {
      Composite comp = SWTFactory.createComposite(parent, parent.getFont(), 1, 1, GridData.FILL_BOTH);
      ((GridLayout) comp.getLayout()).verticalSpacing = 0;
      createProjectEditor(comp);
      createVerticalSpacer(comp, 1);
      createMainTypeEditor(comp, LauncherMessages.JavaMainTab_Main_cla_ss__4);
      setControl(comp);
      PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IJavaDebugHelpContextIds.LAUNCH_CONFIGURATION_DIALOG_MAIN_TAB);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getImage()
    */
   @Override
   public Image getImage()
   {
      return JavaUI.getSharedImages().getImage(ISharedImages.IMG_OBJS_CLASS);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
    */
   public String getName()
   {
      return LauncherMessages.JavaMainTab__Main_19;
   }

   /**
    * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getId()
    * 
    * @since 3.3
    */
   @Override
   public String getId()
   {
      return "org.moflon.ide.debug.ui.eMoflonMainTab"; //$NON-NLS-1$
   }

   /**
    * Show a dialog that lists all main types that are subclasses of
    */
   @Override
   protected void handleSearchButtonSelected()
   {
      IJavaProject javaProject = getJavaProject();

      IJavaSearchScope searchScope = null;
      try
      {
         IType superTypeType = javaProject.findType(SUPER_CLASS_NAME);
         if (superTypeType != null)
         {
            searchScope = SearchEngine.createHierarchyScope(superTypeType);
         }
      } catch (JavaModelException e)
      {
         e.printStackTrace();
      }
      MainMethodSearchEngine engine = new MainMethodSearchEngine();
      IType[] types = null;
      try
      {
         // TODO In future one might be interested in the boolean includeSubtypes parameter to be configured (now
         // default is true)
         types = engine.searchMainMethods(getLaunchConfigurationDialog(), searchScope, true);
      } catch (InvocationTargetException e)
      {
         setErrorMessage(e.getMessage());
         return;
      } catch (InterruptedException e)
      {
         setErrorMessage(e.getMessage());
         return;
      }
      DebugTypeSelectionDialog mmsd = new DebugTypeSelectionDialog(getShell(), types, LauncherMessages.JavaMainTab_Choose_Main_Type_11);
      if (mmsd.open() == Window.CANCEL)
      {
         return;
      }
      Object[] results = mmsd.getResult();
      IType type = (IType) results[0];
      if (type != null)
      {
         fMainText.setText(type.getFullyQualifiedName());
         fProjText.setText(type.getJavaProject().getElementName());
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.jdt.internal.debug.ui.launcher.AbstractJavaMainTab#initializeFrom(org.eclipse.debug.core.
    * ILaunchConfiguration)
    */
   @Override
   public void initializeFrom(ILaunchConfiguration config)
   {
      super.initializeFrom(config);
      updateMainTypeFromConfig(config);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
    */
   @Override
   public boolean isValid(ILaunchConfiguration config)
   {
      setErrorMessage(null);
      setMessage(null);
      String name = fProjText.getText().trim();
      if (name.length() > 0)
      {
         IWorkspace workspace = ResourcesPlugin.getWorkspace();
         IStatus status = workspace.validateName(name, IResource.PROJECT);
         if (status.isOK())
         {
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
            if (!project.exists())
            {
               setErrorMessage(NLS.bind(LauncherMessages.JavaMainTab_20, new String[] { name }));
               return false;
            }
            if (!project.isOpen())
            {
               setErrorMessage(NLS.bind(LauncherMessages.JavaMainTab_21, new String[] { name }));
               return false;
            }
         } else
         {
            setErrorMessage(NLS.bind(LauncherMessages.JavaMainTab_19, new String[] { status.getMessage() }));
            return false;
         }
      }
      name = fMainText.getText().trim();
      if (name.length() == 0)
      {
         setErrorMessage(LauncherMessages.JavaMainTab_Main_type_not_specified_16);
         return false;
      }
      return true;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
    */
   public void performApply(ILaunchConfigurationWorkingCopy config)
   {
      config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, fProjText.getText().trim());
      config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, fMainText.getText().trim());
      mapResources(config);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
    */
   public void setDefaults(ILaunchConfigurationWorkingCopy config)
   {
      IJavaElement javaElement = getContext();
      if (javaElement != null)
      {
         initializeJavaProject(javaElement, config);
      } else
      {
         config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, EMPTY_STRING);
      }
      initializeMainTypeAndName(javaElement, config);
   }

}
