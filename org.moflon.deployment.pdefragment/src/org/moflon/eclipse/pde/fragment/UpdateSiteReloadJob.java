package org.moflon.eclipse.pde.fragment;

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.equinox.p2.operations.RepositoryTracker;
import org.eclipse.equinox.p2.ui.ProvisioningUI;

public class UpdateSiteReloadJob extends Job {
	private final ProvisioningUI ui;

	public UpdateSiteReloadJob(final ProvisioningUI ui) {
		super("Reloading update sites");
		this.ui = ui;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		final RepositoryTracker repoTracker = ui.getRepositoryTracker();
		final URI[] repositories = repoTracker.getKnownRepositories(ui.getSession());
		repoTracker.refreshRepositories(repositories, ui.getSession(), monitor);
		return Status.OK_STATUS;
	}
}
