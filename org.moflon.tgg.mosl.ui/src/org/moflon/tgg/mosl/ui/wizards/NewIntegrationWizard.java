package org.moflon.tgg.mosl.ui.wizards;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;
import static org.moflon.core.utilities.WorkspaceHelper.addNature;
import static org.moflon.core.utilities.WorkspaceHelper.createSubmonitorWith1Tick;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.tgg.mosl.builder.MOSLTGGNature;
import org.moflon.tgg.mosl.scoping.AttrCondDefLibraryProvider;
import org.moflon.tgg.mosl.ui.wizards.DefaultFilesHelper;
import org.moflon.util.plugins.MetamodelProperties;

public class NewIntegrationWizard extends NewRepositoryWizard {

	public static final String NEW_INTEGRATION_PROJECT_WIZARD_ID = "org.moflon.tgg.mosl.newIntegrationProject";

	@Override
	public void addPages() {
		projectInfo = new NewIntegrationProjectInfoPage();
		addPage(projectInfo);
	}

	@Override
	protected IProject createProject(IProgressMonitor monitor, String projectName) throws CoreException {
		MoflonProjectCreator createMoflonProject = new MoflonProjectCreator();
		createMoflonProject.setProjectName(projectName);
		createMoflonProject.setType(MetamodelProperties.INTEGRATION_KEY);
		ResourcesPlugin.getWorkspace().run(createMoflonProject, createSubmonitorWith1Tick(monitor));
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		addNature(project, "org.eclipse.xtext.ui.shared.xtextNature", createSubmonitorWith1Tick(monitor));
		addNature(project, MOSLTGGNature.NATURE_ID, createSubmonitorWith1Tick(monitor));
		
		return project;
	}

	@Override
	protected void generateDefaultFiles(final IProgressMonitor monitor, IProject project) throws CoreException {
		String defaultSchema = DefaultFilesHelper.generateDefaultSchema(project.getName());
		addAllFoldersAndFile(project, new Path("src/org/moflon/tgg/mosl/Schema.tgg"), defaultSchema, createSubmonitorWith1Tick(monitor));
		
		String defaultRule = DefaultFilesHelper.generateDefaultRule(project.getName(), "Rule1");
		addAllFoldersAndFile(project, new Path("src/org/moflon/tgg/mosl/rules/Rule1.tgg"), defaultRule, createSubmonitorWith1Tick(monitor));
	
        try {
        	XtextResourceSet resourceSet = new XtextResourceSet();
			AttrCondDefLibraryProvider.syncAttrCondDefLibrary(resourceSet, project.getFullPath().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
