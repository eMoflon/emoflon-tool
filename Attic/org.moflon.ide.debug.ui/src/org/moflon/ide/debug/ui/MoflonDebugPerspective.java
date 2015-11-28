package org.moflon.ide.debug.ui;

import org.eclipse.debug.internal.ui.DebugPerspectiveFactory;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.debug.internal.ui.preferences.IDebugPreferenceConstants;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.console.IConsoleConstants;

/**
 * 
 * The eMoflon Debug Perspective encompasses the normal Java Debug Perspective and adds typical elements for debugging.
 * 
 * @author Marco Ballhausen
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
@SuppressWarnings("restriction")
public class MoflonDebugPerspective extends DebugPerspectiveFactory// implements IPerspectiveFactory
{
   private IPageLayout layout;

   public static final String MOFLON_PERSPECTIVE_ID = "org.moflon.ide.debug.ui.MoflonDebugPerspective";

   @Override
   public void createInitialLayout(final IPageLayout layout)
   {
      // super.createInitialLayout(layout);
      this.layout = layout;
      layout.setEditorAreaVisible(false);
      debugView();
      addViews();
      // addActionSets();
      // addShortcuts();

      // Do not show console on simple text output
      IPreferenceStore prefs = DebugUIPlugin.getDefault().getPreferenceStore();
      // Not sure how this behaves
      // prefs.setDefault(IDebugPreferenceConstants.CONSOLE_OPEN_ON_OUT, false);
      // prefs.setDefault(IDebugPreferenceConstants.CONSOLE_OPEN_ON_ERR, false);

      prefs.setValue(IDebugPreferenceConstants.CONSOLE_OPEN_ON_OUT, false);
      // prefs.setValue(IDebugPreferenceConstants.CONSOLE_OPEN_ON_ERR, false);
   }

   /**
    * Creates and Layouts all default eclipse debug components in this perspective.
    */
   private void debugView()
   {
      // IFolderLayout consoleFolder = layout.createFolder(IInternalDebugUIConstants.ID_CONSOLE_FOLDER_VIEW,
      // IPageLayout.BOTTOM, (float) 0.75,
      // layout.getEditorArea());
      // consoleFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);
      // consoleFolder.addView(IPageLayout.ID_TASK_LIST);
      // consoleFolder.addPlaceholder(IPageLayout.ID_BOOKMARKS);
      // consoleFolder.addPlaceholder(IPageLayout.ID_PROP_SHEET);

      IFolderLayout navFolder = layout.createFolder(IInternalDebugUIConstants.ID_NAVIGATOR_FOLDER_VIEW, IPageLayout.LEFT, (float) 0.21, layout.getEditorArea());
      navFolder.addView(IDebugUIConstants.ID_DEBUG_VIEW);

      IFolderLayout properties = layout.createFolder("properties", IPageLayout.BOTTOM, (float) 0.4, IInternalDebugUIConstants.ID_NAVIGATOR_FOLDER_VIEW);
      properties.addView(IDebugUIConstants.ID_VARIABLE_VIEW);
      properties.addView(IDebugUIConstants.ID_BREAKPOINT_VIEW);
      properties.addPlaceholder(IDebugUIConstants.ID_EXPRESSION_VIEW);
      properties.addPlaceholder(IDebugUIConstants.ID_REGISTER_VIEW);
      properties.addPlaceholder(IPageLayout.ID_PROJECT_EXPLORER);

      IFolderLayout toolsFolder = layout.createFolder(IInternalDebugUIConstants.ID_NAVIGATOR_FOLDER_VIEW, IPageLayout.RIGHT, (float) 0.66,
            layout.getEditorArea());// IInternalDebugUIConstants.ID_NAVIGATOR_FOLDER_VIEW);
      // toolsFolder.addView(IDebugUIConstants.ID_VARIABLE_VIEW);
      // toolsFolder.addView(IDebugUIConstants.ID_BREAKPOINT_VIEW);
      // toolsFolder.addPlaceholder(IDebugUIConstants.ID_EXPRESSION_VIEW);
      // toolsFolder.addPlaceholder(IDebugUIConstants.ID_REGISTER_VIEW);
      toolsFolder.addView(IPageLayout.ID_PROP_SHEET);
      toolsFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);

      // IFolderLayout center = layout.createFolder(IInternalDebugUIConstants.ID_NAVIGATOR_FOLDER_VIEW,
      // IPageLayout.LEFT, (float) 0.66, layout.getEditorArea());// IInternalDebugUIConstants.ID_NAVIGATOR_FOLDER_VIEW);
      // center.addView(IConsoleConstants.ID_CONSOLE_VIEW);

      // IFolderLayout outlineFolder = layout.createFolder(IInternalDebugUIConstants.ID_OUTLINE_FOLDER_VIEW,
      // IPageLayout.RIGHT, (float) 0.75,
      // layout.getEditorArea());
      // outlineFolder.addView(IPageLayout.ID_OUTLINE);

      layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
      layout.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);

      setContentsOfShowViewMenu(layout);
   }

   /**
    * Adds eMoflon specific views to the perspective defined in {@link #debugView()}.
    */
   private void addViews()
   {
      // Note that each new Folder uses a percentage of the remaining EditorArea.

      // // Java package explorer
      // IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f, layout.getEditorArea());
      //
      // topLeft.addView(UIActivator.JAVA_PACKAGE_EXPLORER_ID);
      //
      // // Problem view and console
      // IFolderLayout bottom = layout.createFolder("bottomRight", IPageLayout.BOTTOM, 0.75f, layout.getEditorArea());

      // bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
      // bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);

      // GraphViewer and TripleView view
      IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.5f, layout.getEditorArea());

      topLeft.addView("org.moflon.ide.ui.admin.views.GraphView");// GraphView.ID);

      IFolderLayout topRight = layout.createFolder("topRight", IPageLayout.TOP, 0.5f, IInternalDebugUIConstants.ID_TOOLS_FOLDER_VIEW);

      topRight.addView("org.moflon.integrator.views.TripleView");// MatrixbrowserView.ID);

      // topRight.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
   }
   // private void addActionSets()
   // {
   // // MOFLON
   // layout.addActionSet(UIActivator.MOFLON_ACTION_SET_ID);
   //
   // // Java
   // layout.addActionSet(UIActivator.LAUNCH_ACTION_SET_ID);
   // layout.addActionSet(JavaUI.ID_ACTION_SET);
   // layout.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
   //
   // // Navigation
   // layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
   // }

   // private void addShortcuts()
   // {
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.JavaProjectWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewPackageCreationWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewClassCreationWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewInterfaceCreationWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewEnumCreationWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewAnnotationCreationWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewSourceFolderCreationWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewJavaWorkingSetWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
   // this.layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");
   // this.layout.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard");
   // this.layout.addNewWizardShortcut("org.eclipse.jdt.junit.wizards.NewTestCaseCreationWizard");
   //
   // this.layout.addPerspectiveShortcut("org.eclipse.jdt.ui.JavaPerspective");
   // this.layout.addPerspectiveShortcut("org.eclipse.debug.ui.DebugPerspective");
   // this.layout.addPerspectiveShortcut("org.eclipse.jdt.ui.JavaBrowsingPerspective");
   //
   // this.layout.addShowViewShortcut("org.eclipse.jdt.ui.PackageExplorer");
   // this.layout.addShowViewShortcut("org.eclipse.jdt.ui.TypeHierarchy");
   // this.layout.addShowViewShortcut("org.eclipse.jdt.ui.SourceView");
   // this.layout.addShowViewShortcut("org.eclipse.jdt.ui.JavadocView");
   // this.layout.addShowViewShortcut("org.eclipse.search.ui.views.SearchView");
   // this.layout.addShowViewShortcut("org.eclipse.ui.console.ConsoleView");
   // this.layout.addShowViewShortcut("org.eclipse.ui.views.ProblemView");
   // this.layout.addShowViewShortcut("org.eclipse.ui.views.ResourceNavigator");
   // this.layout.addShowViewShortcut("org.eclipse.ui.views.TaskList");
   // this.layout.addShowViewShortcut("org.eclipse.ui.views.ProgressView");
   // this.layout.addShowViewShortcut("org.eclipse.ui.navigator.ProjectExplorer");
   // this.layout.addShowViewShortcut("org.eclipse.ui.texteditor.TemplatesView");
   // this.layout.addShowViewShortcut("org.eclipse.ant.ui.views.AntView");
   // this.layout.addShowViewShortcut("org.eclipse.pde.runtime.LogView");
   // }
   //
   // public static void switchToMoflonPerspective(final IWorkbench workbench)
   // {
   // try
   // {
   // workbench.showPerspective(MOFLON_PERSPECTIVE_ID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
   // } catch (WorkbenchException e)
   // {
   // // Ignore
   // }
   // }

}
