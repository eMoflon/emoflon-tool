package org.moflon.ide.ui.admin.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.ea.EnterpriseArchitectHelper;
import org.moflon.ide.ui.UIActivator;

public class ExportAndBuildHandler extends AbstractCommandHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

		final Collection<IProject> projects = getMetamodelProjectsFromSelection(selection);

		try {
			WorkspaceJob job = new WorkspaceJob("eMoflon Export") {
				@Override
				public IStatus runInWorkspace(final IProgressMonitor pm) {
					try {
						Status status = new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);
						int numProjects = projects.size();
						String name = String.format("Exporting %d metamodel %s...", numProjects, numProjects == 1 ? "project" : "projects");
                  pm.beginTask(name, 2 * numProjects);
						for (final IProject project : projects) {
							pm.worked(1);
							try {
								EnterpriseArchitectHelper.delegateToEnterpriseArchitect(project,
										WorkspaceHelper.createSubmonitorWith1Tick(pm));
							} catch (IOException | InterruptedException e) {
								status = new Status(IStatus.ERROR, UIActivator.getModuleID(), e.getMessage());
							} finally {
								if (status.isOK()) {
									CoreActivator.getDefault().setDirty(project, true);
								}
							}
						}
						return status;
					} finally {
						pm.done();
					}

				}
			};
			job.setUser(true);
			job.schedule();
		} catch (Exception e) {
			throw new ExecutionException("Could not export metamodel to Eclipse", e);
		}

		return null;
	}

	private static Collection<IProject> getMetamodelProjectsFromSelection(final IStructuredSelection selection) {
		final List<IProject> projects = new ArrayList<>();
		if (selection instanceof StructuredSelection) {
			final StructuredSelection structuredSelection = (StructuredSelection) selection;
			for (final Iterator<?> selectionIterator = structuredSelection.iterator(); selectionIterator.hasNext();) {
				projects.addAll(getProjects(selectionIterator.next()));
			}
		} 
		
		return projects.stream().filter(p -> WorkspaceHelper.isMetamodelProjectNoThrow(p)).collect(Collectors.toList());
	}
}
