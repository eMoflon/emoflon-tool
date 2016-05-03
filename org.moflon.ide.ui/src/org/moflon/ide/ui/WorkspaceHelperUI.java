package org.moflon.ide.ui;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.moflon.core.utilities.UtilityClassNotInstantiableException;

public class WorkspaceHelperUI {
   private static final Logger logger = Logger.getLogger(WorkspaceHelperUI.class);

	private WorkspaceHelperUI() {
		throw new UtilityClassNotInstantiableException();
	}

	@SuppressWarnings("restriction")
	public static void moveProjectToWorkingSet(final IProject project, final String workingSetName) {
		// Move project to appropriate working set
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
		IWorkingSet workingSet = workingSetManager.getWorkingSet(workingSetName);
		if (workingSet == null) {
			workingSet = workingSetManager.createWorkingSet(workingSetName, new IAdaptable[] { project });
			workingSet.setId(org.eclipse.jdt.internal.ui.workingsets.IWorkingSetIDs.JAVA);
			workingSetManager.addWorkingSet(workingSet);
		} else {
			// Add current contents of WorkingSet
			ArrayList<IAdaptable> newElements = new ArrayList<IAdaptable>();
			for (IAdaptable element : workingSet.getElements())
				newElements.add(element);

			// Add newly created project
			newElements.add(project);

			// Set updated contents
			IAdaptable[] newElementsArray = new IAdaptable[newElements.size()];
			workingSet.setElements(newElements.toArray(newElementsArray));
		}
	}

   /**
    * Open the default editor for a file in the current workbench page
    */
   public static void openDefaultEditorForFile(IFile file)
   {
      Display.getDefault().asyncExec(() -> {
         IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
         IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
         try
         {
            page.openEditor(new FileEditorInput(file), desc.getId());
         } catch (Exception e)
         {
            logger.error("Unable to open " + file);
            e.printStackTrace();
         }
      });
   }
}
