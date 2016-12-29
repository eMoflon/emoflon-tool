package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class MOSLGTNature extends MoflonProjectConfigurator {

	//TODO@rkluge: This code is really error-prone and could be simplified by using a topological sorting with minimal adjustment condition and initial positions
	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description,
			ICommand[] buildSpecs, final boolean added) throws CoreException {
	   
	   /*
	    * Interesting builders:
	    * * WorkspaceHelper.XTEXT_BUILDER_ID
	    * * CoreActivator.MOSL_GT_BUILDER_ID
	    */
	   
		if (added) {
			// Insert or move XText builder before IntegrationBuilder
			int xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID);
			if (xtextBuilderPosition < 0) {
				final ICommand xtextBuilder = description.newCommand();
				xtextBuilder.setBuilderName(WorkspaceHelper.XTEXT_BUILDER_ID);
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				xtextBuilderPosition = buildSpecs.length - 1;
				buildSpecs[xtextBuilderPosition] = xtextBuilder;
			}
			// Insert or move MOSL-GT builder before IntegrationBuilder and after Xtext builder
			int moslBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID);
			if (moslBuilderPosition < 0) {
				final ICommand moslTGGBuilder = description.newCommand();
				moslTGGBuilder.setBuilderName(WorkspaceHelper.MOSL_GT_BUILDER_ID);
				buildSpecs = Arrays.copyOf(buildSpecs, buildSpecs.length + 1);
				moslBuilderPosition = buildSpecs.length - 1;
				buildSpecs[moslBuilderPosition] = moslTGGBuilder;
			}
			int javaBuilderPosition = ProjectUtil.indexOf(buildSpecs, "org.eclipse.jdt.core.javabuilder");
			int moflonBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID);
			if (javaBuilderPosition >= 0 && javaBuilderPosition < moflonBuilderPosition) {
            final ICommand moflonBuilder = buildSpecs[moflonBuilderPosition];
            System.arraycopy(buildSpecs, javaBuilderPosition, buildSpecs, javaBuilderPosition+1, moflonBuilderPosition-javaBuilderPosition);
            moflonBuilderPosition = javaBuilderPosition++;
            buildSpecs[moflonBuilderPosition] = moflonBuilder;
         }
			
			moslBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID);
			xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID);
			if (xtextBuilderPosition > moslBuilderPosition) {
            final ICommand xtextBuilderCommand = buildSpecs[xtextBuilderPosition];
            System.arraycopy(buildSpecs, moslBuilderPosition, buildSpecs, moslBuilderPosition + 1,
                  xtextBuilderPosition - moslBuilderPosition);
            buildSpecs[moslBuilderPosition] = xtextBuilderCommand;
         }
		} else {
			int xtextBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID);
			if (xtextBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, xtextBuilderPosition);
			}
			int moslBuilderPosition = ProjectUtil.indexOf(buildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID);
			if (moslBuilderPosition >= 0) {
				buildSpecs = ProjectUtil.remove(buildSpecs, moslBuilderPosition);
			}
		}
		return buildSpecs;
	}

	@Override
	public String[] updateNatureIDs(String[] natureIDs, final boolean added) throws CoreException {
		if (added) {
			if (ProjectUtil.indexOf(natureIDs, WorkspaceHelper.XTEXT_NATURE_ID) < 0) {
				natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
				natureIDs[natureIDs.length - 1] = WorkspaceHelper.XTEXT_NATURE_ID;
			}
			if (ProjectUtil.indexOf(natureIDs, getNatureId()) < 0) {
				natureIDs = Arrays.copyOf(natureIDs, natureIDs.length + 1);
				natureIDs[natureIDs.length - 1] = getNatureId();
			}
		} else {
			int xtextNaturePosition = ProjectUtil.indexOf(natureIDs, WorkspaceHelper.XTEXT_NATURE_ID);
			if (xtextNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, xtextNaturePosition);
			}
			int moslTGGNaturePosition = ProjectUtil.indexOf(natureIDs, getNatureId());
			if (xtextNaturePosition >= 0) {
				natureIDs = ProjectUtil.remove(natureIDs, moslTGGNaturePosition);
			}
		}
		return super.updateNatureIDs(natureIDs, added);
	}

   @Override
   protected String getBuilderId()
   {
      return WorkspaceHelper.MOSL_GT_BUILDER_ID;
   }

   @Override
   protected String getNatureId()
   {
      return WorkspaceHelper.MOSL_GT_NATURE_ID;
   }
}
