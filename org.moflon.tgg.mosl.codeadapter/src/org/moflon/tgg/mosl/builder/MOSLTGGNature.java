package org.moflon.tgg.mosl.builder;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.core.build.nature.ProjectConfiguratorNature;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.builders.IntegrationBuilder;

public class MOSLTGGNature extends ProjectConfiguratorNature {

	public static String getId() {
		return "org.moflon.tgg.mosl.codeadapter.moslTGGNature";
	}

	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description, ICommand[] buildSpecs,
			final boolean added) throws CoreException {
		if (added) {
			int integrationBuilderPosition = ProjectUtil.indexOf(buildSpecs, IntegrationBuilder.getId());
			// Insert or move Xtext builder before IntegrationBuilder
			int xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID);
			if (xtextBuilderPosition < 0) {
				final ICommand xtextBuilder = description.newCommand();
				xtextBuilder.setBuilderName(WorkspaceHelper.XTEXT_BUILDER_ID);
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
			// Insert or move MOSL-TGG builder before IntegrationBuilder and after Xtext
			// builder
			int moslTGGBuilderPosition = ProjectUtil.indexOf(buildSpecs, MoslTGGBuilder.BUILDER_ID);
			if (moslTGGBuilderPosition < 0) {
				final ICommand moslTGGBuilder = description.newCommand();
				moslTGGBuilder.setBuilderName(MoslTGGBuilder.BUILDER_ID);
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
		} else {
			int xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID);
			if (xtextBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, xtextBuilderPosition);
			}
			int moslTGGBuilderPosition = ProjectUtil.indexOf(buildSpecs, MoslTGGBuilder.BUILDER_ID);
			if (moslTGGBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, moslTGGBuilderPosition);
			}
		}
		return buildSpecs;
	}

	@Override
	public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException {
		if (added) {
			if (!containsNatureID(natureIDs, WorkspaceHelper.XTEXT_NATURE_ID)) {
				natureIDs = insertAtEnd(natureIDs, WorkspaceHelper.XTEXT_NATURE_ID);
			}
			if (!containsNatureID(natureIDs, MOSLTGGNature.getId())) {
				natureIDs = insertAtEnd(natureIDs, MOSLTGGNature.getId());
			}
		} else {
			int xtextNaturePosition = ProjectUtil.indexOf(natureIDs, WorkspaceHelper.XTEXT_NATURE_ID);
			if (xtextNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, xtextNaturePosition);
			}
			int moslTGGNaturePosition = ProjectUtil.indexOf(natureIDs, MOSLTGGNature.getId());
			if (xtextNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, moslTGGNaturePosition);
			}
		}
		return natureIDs;
	}

}
