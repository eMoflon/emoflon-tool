package org.moflon.ide.ui.admin.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.eclipse.job.IMonitoredJob;
import org.moflon.eclipse.job.ProgressMonitoringJob;

public class ValidateHandler extends AbstractCommandHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		try {
			if (selection instanceof TreeSelection) {
				final TreeSelection treeSelection = (TreeSelection) selection;
				for (final Iterator<?> selectionIterator = treeSelection.iterator(); selectionIterator.hasNext();) {
					final Object element = selectionIterator.next();
					if (element instanceof IFile) {
						validateFile((IFile) element, new NullProgressMonitor());
					} else if (element instanceof IJavaProject) {
						validateProject(((IJavaProject) element).getProject(), new NullProgressMonitor());
					} else if (element instanceof IWorkingSet) {
						final List<IProject> projects = new ArrayList<>();
						final IWorkingSet workingSet = (IWorkingSet) element;
						for (final IAdaptable elementInWorkingSet : workingSet.getElements()) {
							if (elementInWorkingSet instanceof IProject) {
								projects.add((IProject) elementInWorkingSet);
							}
						}
						validateProjects(projects, new NullProgressMonitor());
					}
				}
			}
		} catch (CoreException e) {
			throw new ExecutionException(e.getMessage());
		}
		return null;
	}

	private void validateProjects(final Collection<IProject> projects, final IProgressMonitor monitor)
			throws CoreException {
		try {
			monitor.beginTask("Validating projects", projects.size());

			for (final IProject project : projects) {
				this.validateProject(project, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
			}
		} finally {
			monitor.done();
		}
	}

	private void validateProject(final IProject project, final IProgressMonitor monitor) throws CoreException {
		if (WorkspaceHelper.isMoflonProject(project)) {
			final IFile ecoreFile = WorkspaceHelper.getDefaultEcoreFile(project);
			validateFile(ecoreFile, monitor);
		}
	}

	private void validateFile(final IFile ecoreFile, final IProgressMonitor monitor) {
		try {
			monitor.beginTask("Validating " + ecoreFile.getName(), 1);

			final IMonitoredJob validationTask = (IMonitoredJob) Platform.getAdapterManager().loadAdapter(ecoreFile,
					"org.moflon.compiler.sdm.democles.eclipse.MonitoredSDMValidator");

			if (validationTask != null) {
				final ProgressMonitoringJob job = new ProgressMonitoringJob(CodeGeneratorPlugin.getModuleID(),
						validationTask);
				final boolean runInBackground = PlatformUI.getPreferenceStore().getBoolean("RUN_IN_BACKGROUND");
				if (!runInBackground) {
					// Run in foreground
					PlatformUI.getWorkbench().getProgressService().showInDialog(null, job);
				}
				job.schedule();
			}

			monitor.worked(1);
		} catch (final Exception e) {
			logger.error("Validation failed for reason: " + e.getMessage());
		} finally {
			monitor.done();
		}
	}
}
