package org.moflon.tgg.mosl.builder;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.ide.core.WorkspaceObservationLifecycleManager;
import org.osgi.framework.BundleContext;

public class MOSLTGGPlugin extends Plugin{
	private IWorkspace workspace;
	private MOSLTGGProjectMigrator migrator;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		workspace = ResourcesPlugin.getWorkspace();
		migrator = new MOSLTGGProjectMigrator();
		WorkspaceTask.execute(new WorkspaceObservationLifecycleManager(
				workspace, migrator, true), false);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		WorkspaceTask.execute(new WorkspaceObservationLifecycleManager(
				workspace, migrator, false), false);
		migrator = null;
		workspace = null;
		super.stop(context);
	}
}
