package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.IPackagesViewPart;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.ui.AbstractCommandHandler;

/**
 * Handler that selects the corresponding meta-model for a repository project
 * (in the Package Explorer).
 */
public class GoToMetamodelProjectHandler extends AbstractCommandHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		IProject project = null;
		if (selection instanceof StructuredSelection) {
			final StructuredSelection structuredSelection = (StructuredSelection) selection;
			final Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof IJavaProject) {
				project = ((IJavaProject) firstElement).getProject();
			} else if (firstElement instanceof IResource) {
				project = ((IResource) firstElement).getProject();
			} else if (firstElement instanceof IJavaElement) {
				project = ((IJavaElement) firstElement).getJavaProject().getProject();
			}
		} else if (selection instanceof ITextSelection) {
			project = getEditedFile(event).getProject();
		}

		if (project != null && project.exists()) {
			final MoflonPropertiesContainer properties = MoflonPropertiesContainerHelper.load(project,
					new NullProgressMonitor());
			if (properties.getMetaModelProject() == null) {
				logger.error("Meta-model property of project " + project.getName() + " is not set.");
			} else {

				final String metaModelProjectName = properties.getMetaModelProject().getMetaModelProjectName();

				final IProject metaModelProject = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(metaModelProjectName);

				final IWorkbenchPart activePart = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage()
						.getActivePart();
				if (activePart != null && activePart instanceof IPackagesViewPart) {
					((IPackagesViewPart) activePart).selectAndReveal(metaModelProject);
				}
			}
		}

		return null;
	}

}
