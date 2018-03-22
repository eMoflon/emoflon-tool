package org.moflon.testing.autotester;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;
import org.gervarro.eclipse.workspace.util.WorkspaceTaskJob;
import org.moflon.core.build.BuildUtilities;
import org.moflon.core.build.ProjectBuilderTask;
import org.moflon.core.ui.autosetup.WorkspaceInstaller;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.properties.MetamodelProjectUtil;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;

public class EnterpriseArchitectAwareWorkspaceInstaller extends WorkspaceInstaller {
	public void installWorkspaceByName(final String workspaceName) {
		final List<String> path = EMoflonStandardWorkspaces.getPathToPsfFileForWorkspace(workspaceName);
		final String branchName = EMoflonStandardWorkspaces.extractCustomBranchName(workspaceName);
		if (!path.isEmpty()) {
			this.installPluginRelativePsfFiles(path, workspaceName, branchName);
		} else {
			logger.debug("Not a recognized workspace: " + workspaceName);
		}
	}

	@Override
	protected void enqueuePreprocessingJobs(final List<Job> jobs) {
		super.enqueuePreprocessingJobs(jobs);

		final IProject[] metamodelProjects = MetamodelProjectUtil
				.getMetamodelProjects(ResourcesPlugin.getWorkspace().getRoot().getProjects());
		if (metamodelProjects.length > 0) {
			final EnterpriseArchitectModelExporterTask eaModelExporter = new EnterpriseArchitectModelExporterTask(
					metamodelProjects, false);
			jobs.add(new WorkspaceTaskJob(eaModelExporter));
		}
		final IBuildConfiguration[] buildConfigurations = BuildUtilities
				.getDefaultBuildConfigurations(Arrays.asList(metamodelProjects));
		if (buildConfigurations.length > 0) {
			final ProjectBuilderTask metamodelBuilder = new ProjectBuilderTask(buildConfigurations);
			jobs.add(new WorkspaceTaskJob(metamodelBuilder));
		}
	}

	protected void installPluginRelativePsfFiles(final Collection<String> pluginRelativePsfFiles, final String label,
			final String branchName) {
		installPsfFiles(mapToAbsoluteFiles(pluginRelativePsfFiles), label, branchName);
	}

	@Override
	protected IProject[] getProjectsToBuild() {
		final IProject[] moflonProjects = getRepositoryAndIntegrationProjects(
				ResourcesPlugin.getWorkspace().getRoot().getProjects());
		final IProject[] graphicalMoflonProjects = getProjectsWithGraphicalSyntax(moflonProjects);
		return graphicalMoflonProjects;
	}

	private static String mapToAbsolutePath(final String pluginRelativePathToPSF) {
		final String pluginIdOfPsfFilesProject = getPluginIdOfPsfFilesProject();
		final IProject workspaceProject = WorkspaceHelper.getProjectByPluginId(pluginIdOfPsfFilesProject);
		if (workspaceProject != null) {
			logger.debug("Using PSF files from workspace project with plugin ID " + pluginIdOfPsfFilesProject + ".");
			final File fullPath = new File(workspaceProject.getLocation().toOSString(), pluginRelativePathToPSF);
			return fullPath.getAbsolutePath();
		} else {
			logger.debug("Using PSF files in installed plugin " + pluginIdOfPsfFilesProject + ".");
			final URL fullPathURL = WorkspaceHelper.getPathRelToPlugIn(pluginRelativePathToPSF,
					pluginIdOfPsfFilesProject);
			logger.debug("Retrieved URL: " + fullPathURL);
			final String absolutePathToPSF = new File(fullPathURL.getPath()).getAbsolutePath();
			return absolutePathToPSF;
		}
	}

	private static List<File> mapToAbsoluteFiles(final Collection<String> pluginRelativePsfFiles) {
		return pluginRelativePsfFiles.stream().filter(s -> s != null)
				.map(EnterpriseArchitectAwareWorkspaceInstaller::mapToAbsolutePath).map(s -> new File(s))
				.collect(Collectors.toList());
	}

	private static String getPluginIdOfPsfFilesProject() {
		return WorkspaceHelper.getPluginId(EMoflonStandardWorkspaces.class);
	}

	private static final IProject[] getRepositoryAndIntegrationProjects(final IProject[] projects) {
		final List<IProject> result = new ArrayList<IProject>(projects.length);
		for (final IProject project : projects) {
			if (project.isAccessible() && CoreActivator.isMoflonProjectNoThrow(project)) {
				result.add(project);
			}
		}
		return result.toArray(new IProject[result.size()]);
	}

	private static final IProject[] getProjectsWithGraphicalSyntax(final IProject[] projects) {
		final List<IProject> result = new ArrayList<IProject>(projects.length);
		for (final IProject project : projects) {
			try {
				// Hack to avoid dependency cycle
				if (project.isAccessible() && !project.hasNature("org.moflon.tgg.mosl.codeadapter.moslTGGNature")) {
					result.add(project);
				}
			} catch (CoreException e) {
				// Do nothing: Skip erroneous projects
			}
		}
		return result.toArray(new IProject[result.size()]);
	}
}
