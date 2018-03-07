package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.core.build.nature.ProjectConfiguratorNature;
import org.moflon.ide.core.runtime.builders.AntlrBuilder;

public class AntlrNature extends ProjectConfiguratorNature {

	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description, ICommand[] buildSpecs,
			final boolean added) throws CoreException {
		if (added) {
			int javaBuilderPosition = ProjectUtil.indexOf(buildSpecs, JavaCore.BUILDER_ID);
			int antlrBuilderPosition = ProjectUtil.indexOf(buildSpecs, AntlrBuilder.getId());
			if (antlrBuilderPosition < 0) {
				final ICommand antlrBuilder = description.newCommand();
				antlrBuilder.setBuilderName(AntlrBuilder.getId());
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				antlrBuilderPosition = buildSpecs.length - 1;
				buildSpecs[antlrBuilderPosition] = antlrBuilder;
			}
			if (javaBuilderPosition < antlrBuilderPosition) {
				final ICommand antlrBuilder = buildSpecs[antlrBuilderPosition];
				System.arraycopy(buildSpecs, javaBuilderPosition, buildSpecs, javaBuilderPosition + 1,
						antlrBuilderPosition - javaBuilderPosition);
				antlrBuilderPosition = javaBuilderPosition++;
				buildSpecs[antlrBuilderPosition] = antlrBuilder;
			}
		} else {
			int antlrBuilderPosition = ProjectUtil.indexOf(buildSpecs, AntlrBuilder.getId());
			if (antlrBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, antlrBuilderPosition);
			}
		}
		return buildSpecs;
	}

	@Override
	public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException {
		if (added) {
			if (ProjectUtil.indexOf(natureIDs, AntlrNature.getId()) < 0) {
				natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
				natureIDs[natureIDs.length - 1] = AntlrNature.getId();
			}
		} else {
			int antlrNaturePosition = ProjectUtil.indexOf(natureIDs, AntlrNature.getId());
			if (antlrNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, antlrNaturePosition);
			}
		}
		return natureIDs;
	}

	public static String getId() {
		return "org.moflon.ide.core.runtime.natures.AntlrNature";
	}
}
