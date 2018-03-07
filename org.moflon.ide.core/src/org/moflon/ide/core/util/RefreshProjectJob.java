package org.moflon.ide.core.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.core.utilities.WorkspaceHelper;

public class RefreshProjectJob extends Job {

	private final static String NAME = "Refreshing project (\"%1$s\") job";

	private IProject project;

	private RefreshProjectJob(final String name) {
		super(name);
	}

	public RefreshProjectJob(final IProject project) {
		this(String.format(NAME, project.getName()));
		this.project = project;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		final SubMonitor subMon = SubMonitor.convert(monitor, this.getName(), 1);
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, subMon.split(1));
		} catch (final CoreException e) {
			return e.getStatus();
		}
		return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), null);
	}
}