package org.moflon.ide.core;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.gervarro.eclipse.workspace.autosetup.JavaProjectConfigurator;
import org.gervarro.eclipse.workspace.autosetup.PluginProjectConfigurator;
import org.gervarro.eclipse.workspace.autosetup.ProjectConfigurator;
import org.gervarro.eclipse.workspace.util.ProjectStateObserver;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.ProjectNatureAndBuilderConfiguratorTask;
import org.moflon.ide.core.runtime.natures.AntlrNature;
import org.moflon.ide.core.runtime.natures.IntegrationNature;
import org.moflon.ide.core.runtime.natures.RepositoryNature;

//TODO@rkluge Disable later - only during transition to new build process
public class NatureMigrator extends ProjectStateObserver implements ProjectConfigurator {
	private static final String MOSL_TGG_NATURE_ID = "org.moflon.tgg.mosl.codeadapter.moslTGGNature";
	private static final String MOSL_TGG_BUILDER_ID = "org.moflon.tgg.mosl.codeadapter.mosltggbuilder";
	private static final String XTEXT_BUILDER_ID = "org.eclipse.xtext.ui.shared.xtextBuilder";

	
	protected void handleResourceChange(final IResource resource, final boolean added) {
		if (added && resource.getType() == IResource.PROJECT) {
			try {
				final ProjectNatureAndBuilderConfiguratorTask task =
						new ProjectNatureAndBuilderConfiguratorTask((IProject) resource, false);
				task.updateNatureIDs(this, added);
				task.updateBuildSpecs(this, added);
				WorkspaceTask.execute(task, false);
			} catch (CoreException e) {
				// Do nothing
			}
		}
	}

	@Override
	public String[] updateNatureIDs(String[] natureIDs, boolean added) throws CoreException {
		for (int i = 0; i < natureIDs.length; i++) {
			if (natureIDs[i].startsWith("org.moflon.ide.ui")) {
				natureIDs[i] = natureIDs[i].replaceFirst("^org[.]moflon[.]ide[.]ui", "org.moflon.ide.core");
			}
			if (WorkspaceHelper.INTEGRATION_NATURE_ID.equals(natureIDs[i])) {
				natureIDs = new IntegrationNature().updateNatureIDs(natureIDs, added);
			}
			if (WorkspaceHelper.REPOSITORY_NATURE_ID.equals(natureIDs[i])) {
				natureIDs = new RepositoryNature().updateNatureIDs(natureIDs, added);
			}
		}
		return natureIDs;
	}

	@Override
	public ICommand[] updateBuildSpecs(IProjectDescription description, ICommand[] buildSpecs, boolean added)
			throws CoreException {
		if (ProjectUtil.indexOf(buildSpecs, CoreActivator.ANTLR_BUILDER_ID) >= 0) {
			buildSpecs = new AntlrNature().updateBuildSpecs(description, buildSpecs, added);
		}
		final String[] natureIDs = description.getNatureIds();
		for (int i = 0; i < natureIDs.length; i++) {
			if (JavaCore.NATURE_ID.equals(natureIDs[i])) {
				buildSpecs = new JavaProjectConfigurator().updateBuildSpecs(description, buildSpecs, added);
			}
			if ("org.eclipse.pde.PluginNature".equals(natureIDs[i])) {
				buildSpecs = new PluginProjectConfigurator().updateBuildSpecs(description, buildSpecs, added);
			}
			if (WorkspaceHelper.INTEGRATION_NATURE_ID.equals(natureIDs[i]) || "org.moflon.ide.ui.runtime.natures.IntegrationNature".equals(natureIDs[i])) {
				buildSpecs = new IntegrationNature().updateBuildSpecs(description, buildSpecs, added);
			}
			if (WorkspaceHelper.REPOSITORY_NATURE_ID.equals(natureIDs[i]) || "org.moflon.ide.ui.runtime.natures.RepositoryNature".equals(natureIDs[i])) {
				buildSpecs = new RepositoryNature().updateBuildSpecs(description, buildSpecs, added);
			}
			if ("org.moflon.tgg.mosl".equals(description.getName())) {
				int javaBuilderPosition = ProjectUtil.indexOf(buildSpecs, JavaCore.BUILDER_ID);
				int xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, "org.eclipse.xtext.ui.shared.xtextBuilder");
				if (javaBuilderPosition >= 0 && xtextBuilderPosition >= 0 && javaBuilderPosition < xtextBuilderPosition) {
					final ICommand xtextBuilder = buildSpecs[xtextBuilderPosition];
					System.arraycopy(buildSpecs, javaBuilderPosition, buildSpecs, javaBuilderPosition+1, xtextBuilderPosition-javaBuilderPosition);
					xtextBuilderPosition = javaBuilderPosition++;
					buildSpecs[xtextBuilderPosition] = xtextBuilder;
				}
			}
			if (MOSL_TGG_NATURE_ID.equals(natureIDs[i])) {
				int integrationBuilderPosition = ProjectUtil.indexOf(buildSpecs, CoreActivator.INTEGRATION_BUILDER_ID);
				// Insert or move Xtext builder before IntegrationBuilder
				int xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, XTEXT_BUILDER_ID);
				if (xtextBuilderPosition < 0) {
					final ICommand xtextBuilder = description.newCommand();
					xtextBuilder.setBuilderName(XTEXT_BUILDER_ID);
					buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
					xtextBuilderPosition = buildSpecs.length - 1;
					buildSpecs[xtextBuilderPosition] = xtextBuilder;
				}
				if (integrationBuilderPosition < xtextBuilderPosition) {
					final ICommand xtextBuilder = buildSpecs[xtextBuilderPosition];
					System.arraycopy(buildSpecs, integrationBuilderPosition, buildSpecs, integrationBuilderPosition + 1,
							xtextBuilderPosition - integrationBuilderPosition);
					xtextBuilderPosition = integrationBuilderPosition++;
					buildSpecs[xtextBuilderPosition] = xtextBuilder;
				}
				// Insert or move MOSL-TGG builder before IntegrationBuilder and after Xtext builder
				int moslTGGBuilderPosition = ProjectUtil.indexOf(buildSpecs, MOSL_TGG_BUILDER_ID);
				if (moslTGGBuilderPosition < 0) {
					final ICommand moslTGGBuilder = description.newCommand();
					moslTGGBuilder.setBuilderName(MOSL_TGG_BUILDER_ID);
					buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
					moslTGGBuilderPosition = buildSpecs.length - 1;
					buildSpecs[moslTGGBuilderPosition] = moslTGGBuilder;
				}
				assert xtextBuilderPosition < integrationBuilderPosition;
				if (xtextBuilderPosition > moslTGGBuilderPosition) {
					final ICommand moslTGGBuilder = buildSpecs[moslTGGBuilderPosition];
					System.arraycopy(buildSpecs, moslTGGBuilderPosition + 1, buildSpecs, moslTGGBuilderPosition,
							xtextBuilderPosition - moslTGGBuilderPosition);
					moslTGGBuilderPosition = xtextBuilderPosition--;
					buildSpecs[moslTGGBuilderPosition] = moslTGGBuilder;
				}
				if (integrationBuilderPosition < moslTGGBuilderPosition) {
					final ICommand moslTGGBuilder = buildSpecs[moslTGGBuilderPosition];
					System.arraycopy(buildSpecs, integrationBuilderPosition, buildSpecs, integrationBuilderPosition + 1,
							moslTGGBuilderPosition - integrationBuilderPosition);
					moslTGGBuilderPosition = integrationBuilderPosition++;
					buildSpecs[moslTGGBuilderPosition] = moslTGGBuilder;
				}
			}

		}
		return buildSpecs;
	}
}
