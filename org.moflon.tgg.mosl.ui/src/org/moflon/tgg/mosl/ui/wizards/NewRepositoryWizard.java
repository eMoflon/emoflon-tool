package org.moflon.tgg.mosl.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.ide.ui.admin.wizards.metamodel.AbstractMoflonProjectInfoPage;
import org.moflon.ide.ui.admin.wizards.metamodel.AbstractMoflonWizard;
import org.moflon.tgg.mosl.ui.wizards.DefaultFilesHelper;
import org.moflon.util.plugins.MetamodelProperties;
import org.moflon.util.plugins.PluginProducerWorkspaceRunnable;

public class NewRepositoryWizard extends AbstractMoflonWizard {
	public static final String NEW_REPOSITORY_PROJECT_WIZARD_ID = "org.moflon.tgg.mosl.newRepositoryProject";
	
	protected AbstractMoflonProjectInfoPage projectInfo;

	@Override
	public void addPages() {
		projectInfo = new NewRepositoryProjectInfoPage();
		addPage(projectInfo);
	}

	@Override
	protected void doFinish(final IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask("Creating eMoflon project", 8);
			
			String projectName = projectInfo.getProjectName();
			
			IProject project = createProject(monitor, projectName);
			monitor.worked(3);
			
			generateDefaultFiles(monitor, project);
			monitor.worked(3);
			
			makeProjectPlugin(monitor, project);
			monitor.worked(2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			monitor.done();
		}
	}

	protected void makeProjectPlugin(IProgressMonitor monitor, IProject project) throws CoreException {
		MetamodelProperties properties = new MetamodelProperties();
		properties.setDefaultValues();
		properties.put(MetamodelProperties.PLUGIN_ID_KEY, project.getName());
		properties.put(MetamodelProperties.NAME_KEY, project.getName());
		ResourcesPlugin.getWorkspace().run(new PluginProducerWorkspaceRunnable(project, properties),
				WorkspaceHelper.createSubmonitorWith1Tick(monitor));
	}

	protected void generateDefaultFiles(final IProgressMonitor monitor, IProject project) throws CoreException {
		String defaultEcoreFile = DefaultFilesHelper.generateDefaultEPackageForProject(project.getName());
		WorkspaceHelper.addFile(project, MoflonUtil.getDefaultPathToEcoreFileInProject(project.getName()), defaultEcoreFile,
				WorkspaceHelper.createSubmonitorWith1Tick(monitor));
	}

	protected IProject createProject(IProgressMonitor monitor, String projectName) throws CoreException {
		MoflonProjectCreator createMoflonProject = new MoflonProjectCreator();
		createMoflonProject.setProjectName(projectName);
		createMoflonProject.setType(MetamodelProperties.REPOSITORY_KEY);
		ResourcesPlugin.getWorkspace().run(createMoflonProject, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}
}
