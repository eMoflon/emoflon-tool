package org.moflon.ide.ui;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.moflon.core.utilities.UtilityClassNotInstantiableException;

public class WorkspaceHelperUI {

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
}
