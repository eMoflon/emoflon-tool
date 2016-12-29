package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.eclipse.workspace.autosetup.WorkspaceAutoSetupModule;
import org.gervarro.eclipse.workspace.util.ProjectUtil;

public abstract class MoflonProjectConfigurator extends ProjectConfiguratorNature {
	private final String natureID;
	private final String builderID;

	public MoflonProjectConfigurator() {
		this.natureID = getNatureId();
		this.builderID = getBuilderId();
	}

	abstract protected String getBuilderId();

   abstract protected String getNatureId();

	@Override
	public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException {
		if (added) {
			final int moflonNaturePosition = ProjectUtil.indexOf(natureIDs, natureID);
			if (moflonNaturePosition < 0) {
				final String[] oldNatureIDs = natureIDs;
				natureIDs = new String[oldNatureIDs.length + 1];
				System.arraycopy(oldNatureIDs, 0, natureIDs, 1, oldNatureIDs.length);
				natureIDs[0] = natureID;
			} else if (moflonNaturePosition > 0) {
				System.arraycopy(natureIDs, 0, natureIDs, 1, moflonNaturePosition);
				natureIDs[0] = natureID;
			}
		} else {
			int naturePosition = ProjectUtil.indexOf(natureIDs, natureID);
			if (naturePosition >= 0) {
				natureIDs = WorkspaceAutoSetupModule.remove(natureIDs, naturePosition);
			}
		}
		return natureIDs;
	}

	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description, ICommand[] buildSpecs, final boolean added) throws CoreException {
		if (added) {
			int javaBuilderPosition = ProjectUtil.indexOf(buildSpecs, "org.eclipse.jdt.core.javabuilder");
			int moflonBuilderPosition = ProjectUtil.indexOf(buildSpecs, builderID);
			if (moflonBuilderPosition < 0) {
				final ICommand moflonBuilder = description.newCommand();
				moflonBuilder.setBuilderName(builderID);
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				moflonBuilderPosition = buildSpecs.length - 1;
				buildSpecs[moflonBuilderPosition] = moflonBuilder;
			} 
			if (javaBuilderPosition >= 0 && javaBuilderPosition < moflonBuilderPosition) {
				final ICommand moflonBuilder = buildSpecs[moflonBuilderPosition];
				System.arraycopy(buildSpecs, javaBuilderPosition, buildSpecs, javaBuilderPosition+1, moflonBuilderPosition-javaBuilderPosition);
				moflonBuilderPosition = javaBuilderPosition++;
				buildSpecs[moflonBuilderPosition] = moflonBuilder;
			}
		} else {
			int moflonBuilderPosition = ProjectUtil.indexOf(buildSpecs, builderID);
			if (moflonBuilderPosition >= 0) {
				ICommand[] oldBuilderSpecs = buildSpecs;
				buildSpecs = new ICommand[oldBuilderSpecs.length - 1];
				if (moflonBuilderPosition > 0) {
					System.arraycopy(oldBuilderSpecs, 0, buildSpecs, 0, moflonBuilderPosition);
				}
				if (moflonBuilderPosition == buildSpecs.length) {
					System.arraycopy(oldBuilderSpecs, moflonBuilderPosition+1, buildSpecs, moflonBuilderPosition, buildSpecs.length-moflonBuilderPosition);
				}
			}
		}
		return buildSpecs;
	}
}