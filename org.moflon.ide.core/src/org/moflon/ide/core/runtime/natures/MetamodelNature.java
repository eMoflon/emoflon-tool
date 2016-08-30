package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;

public class MetamodelNature extends ProjectConfiguratorNature {

	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description,
			ICommand[] buildSpecs, final boolean added) throws CoreException {
		if (added) {
			int metamodelBuilderPosition = ProjectUtil.indexOf(buildSpecs, CoreActivator.METAMODEL_BUILDER_ID);
			if (metamodelBuilderPosition < 0) {
				final ICommand metamodelBuilder = description.newCommand();
				metamodelBuilder.setBuilderName(CoreActivator.METAMODEL_BUILDER_ID);
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				metamodelBuilderPosition = buildSpecs.length - 1;
				buildSpecs[metamodelBuilderPosition] = metamodelBuilder;
			} 
		} else {
			int metamodelBuilderPosition = ProjectUtil.indexOf(buildSpecs, CoreActivator.METAMODEL_BUILDER_ID);
			if (metamodelBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, metamodelBuilderPosition);
			}
		}
		return buildSpecs;
	}

	@Override
	public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException {
		if (added) {
			if (ProjectUtil.indexOf(natureIDs, WorkspaceHelper.METAMODEL_NATURE_ID) < 0) {
				natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
				natureIDs[natureIDs.length - 1] = WorkspaceHelper.METAMODEL_NATURE_ID;
			}
		} else {
			int metamodelNaturePosition = ProjectUtil.indexOf(natureIDs, WorkspaceHelper.METAMODEL_NATURE_ID);
			if (metamodelNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, metamodelNaturePosition);
			}
		}
		return natureIDs;
	}
}
