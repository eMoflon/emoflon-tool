package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.core.build.nature.ProjectConfiguratorNature;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

public class MetamodelNature extends ProjectConfiguratorNature {

	public static String getId() {
		return "org.moflon.ide.core.runtime.natures.MetamodelNature";
	}

	/**
	 * Returns whether the project is a a meta-model project, that is, if it
	 * contains a meta-model
	 *
	 * @param project
	 *            the project. May be null.
	 */
	public static boolean isMetamodelProject(final IProject project) throws CoreException {
		return project != null && project.hasNature(getId());
	}

	/**
	 * Same as {@link #isMetamodelProject(IProject)} but catches {@link Exception}s,
	 * returning false.
	 *
	 * @param project
	 * @return
	 */
	public static boolean isMetamodelProjectNoThrow(final IProject project) {
		try {
			return isMetamodelProject(project);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description, ICommand[] buildSpecs,
			final boolean added) throws CoreException {
		if (added) {
			int metamodelBuilderPosition = ProjectUtil.indexOf(buildSpecs, MetamodelBuilder.getId());
			if (metamodelBuilderPosition < 0) {
				final ICommand metamodelBuilder = description.newCommand();
				metamodelBuilder.setBuilderName(MetamodelBuilder.getId());
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				metamodelBuilderPosition = buildSpecs.length - 1;
				buildSpecs[metamodelBuilderPosition] = metamodelBuilder;
			}
		} else {
			int metamodelBuilderPosition = ProjectUtil.indexOf(buildSpecs, MetamodelBuilder.getId());
			if (metamodelBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, metamodelBuilderPosition);
			}
		}
		return buildSpecs;
	}

	@Override
	public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException {
		if (added) {
			if (ProjectUtil.indexOf(natureIDs, MetamodelNature.getId()) < 0) {
				natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
				natureIDs[natureIDs.length - 1] = MetamodelNature.getId();
			}
		} else {
			int metamodelNaturePosition = ProjectUtil.indexOf(natureIDs, MetamodelNature.getId());
			if (metamodelNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, metamodelNaturePosition);
			}
		}
		return natureIDs;
	}
}
