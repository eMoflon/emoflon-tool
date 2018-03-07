package org.moflon.testing.autotester;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.moflon.core.ui.MoflonPerspective;

/**
 * Triggers the autostart method of the test activator
 */
public class StartUp implements IStartup {

	private static final Logger logger = Logger.getLogger(StartUp.class);

	@Override
	public void earlyStartup() {
		final Job job = new Job("Moflon: Autostart ...") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				final SubMonitor subMon = SubMonitor.convert(monitor, "eMoflon Autostart", 100);
				runAutoSetup(subMon.split(100));
				return Status.OK_STATUS;

			}
		};
		job.schedule();
	}

	private void runAutoSetup(final IProgressMonitor monitor) {
		final IWorkbench workbench = PlatformUI.getWorkbench();

		workbench.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					if (workspaceContainsNoProjects()) {
						IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

						MoflonPerspective.switchToMoflonPerspective(workbench);

						final String workspaceName = root.getLocation().lastSegment();

						logger.debug("Autorunning workspace '" + workspaceName + "'");

						new EnterpriseArchitectAwareWorkspaceInstaller().installWorkspaceByName(workspaceName);

					}

				}
			}

		});
	}

	private boolean workspaceContainsNoProjects() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getProjects().length == 0;
	}
}
