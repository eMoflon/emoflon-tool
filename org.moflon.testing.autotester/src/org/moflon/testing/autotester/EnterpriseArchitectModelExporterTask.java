package org.moflon.testing.autotester;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.core.utilities.ProgressMonitorUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.ea.EnterpriseArchitectHelper;
import org.moflon.ide.core.properties.MetamodelProjectUtil;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

public final class EnterpriseArchitectModelExporterTask extends WorkspaceTask {
	private final IProject[] metamodelProjects;

	EnterpriseArchitectModelExporterTask(final IProject[] metamodelProjects, final boolean checkProjects) {
		if (metamodelProjects == null) {
			throw new NullPointerException();
		}
		this.metamodelProjects = checkProjects ? MetamodelProjectUtil.getMetamodelProjects(metamodelProjects)
				: metamodelProjects;
	}

	@Override
	public String getTaskName() {
		return "Exporting metamodel from Enterprise Architect";
	}

	@Override
	public ISchedulingRule getRule() {
		return MultiRule.combine(metamodelProjects);
	}

	@Override
	public void run(final IProgressMonitor monitor) throws CoreException {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, metamodelProjects.length);
		try {
			for (final IProject project : metamodelProjects) {
				final SubMonitor loopMonitor = SubMonitor.convert(subMonitor.split(1),
						"Exporting from Enterprise Architect in project " + project.getName(), 11);
				if (shouldExport(project)) {
					EnterpriseArchitectHelper.exportEcoreFilesFromEAP(project, loopMonitor.split(10));
					ProgressMonitorUtil.checkCancellation(loopMonitor);
				}
				loopMonitor.setWorkRemaining(1);
				project.refreshLocal(IResource.DEPTH_INFINITE, loopMonitor.split(1));
			}
		} finally {
			SubMonitor.done(monitor);
		}
	}

	public final IProject[] getMetamodelProjects() {
		return metamodelProjects;
	}

	private final boolean shouldExport(final IProject metamodelProject) {
		final IFile eapFile = WorkspaceHelper.getEapFileFromMetamodelProject(metamodelProject);
		if (!eapFile.exists()) {
			return false;
		}

		final IFolder tempFolder = metamodelProject.getFolder(MetamodelBuilder.TEMP_FOLDER);
		if (tempFolder.exists()) {
			final IFile mocaTree = MetamodelProjectUtil.getExportedMocaTree(metamodelProject);
			if (tempFolder.exists() && mocaTree.exists()) {
				return mocaTree.getLocalTimeStamp() < eapFile.getLocalTimeStamp();
			}
		}
		return true;
	}
}
