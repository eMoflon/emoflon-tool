package org.moflon.ide.core.runtime.natures;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class MOSLGTNature extends MoflonProjectConfigurator
{
   @Override
   public ICommand[] updateBuildSpecs(final IProjectDescription description, final ICommand[] buildSpecs, final boolean shallAddBuilders) throws CoreException
   {

      /*
       * Interesting builders:
       * * WorkspaceHelper.XTEXT_BUILDER_ID
       * * CoreActivator.MOSL_GT_BUILDER_ID
       */
      ICommand[] newBuildSpecs = buildSpecs;

      if (shallAddBuilders)
      {
         newBuildSpecs = MoflonBuilderUtils.insertAtEndIfMissing(newBuildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID, description);
         newBuildSpecs = MoflonBuilderUtils.insertAtEndIfMissing(newBuildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID, description);

         {
            int javaBuilderPosition = ProjectUtil.indexOf(newBuildSpecs, WorkspaceHelper.JAVA_BUILDER_ID);
            int moflonBuilderPosition = ProjectUtil.indexOf(newBuildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID);
            if (javaBuilderPosition >= 0 && javaBuilderPosition < moflonBuilderPosition)
            {
               final ICommand moflonBuilder = newBuildSpecs[moflonBuilderPosition];
               System.arraycopy(newBuildSpecs, javaBuilderPosition, newBuildSpecs, javaBuilderPosition + 1, moflonBuilderPosition - javaBuilderPosition);
               moflonBuilderPosition = javaBuilderPosition++;
               newBuildSpecs[moflonBuilderPosition] = moflonBuilder;
            }
         }

         {
            int moslBuilderPosition = ProjectUtil.indexOf(newBuildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID);
            int xtextBuilderPosition = ProjectUtil.indexOf(newBuildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID);
            if (xtextBuilderPosition > moslBuilderPosition)
            {
               final ICommand xtextBuilderCommand = newBuildSpecs[xtextBuilderPosition];
               System.arraycopy(newBuildSpecs, moslBuilderPosition, newBuildSpecs, moslBuilderPosition + 1, xtextBuilderPosition - moslBuilderPosition);
               newBuildSpecs[moslBuilderPosition] = xtextBuilderCommand;
            }
         }
      } else
      {
         newBuildSpecs = MoflonBuilderUtils.removeBuilderID(newBuildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID);
         newBuildSpecs = MoflonBuilderUtils.removeBuilderID(newBuildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID);
      }
      return newBuildSpecs;
   }


   @Override
   public String[] updateNatureIDs(final String[] natureIDs, final boolean shallAddNature) throws CoreException
   {
      String[] newNatureIDs = natureIDs;
      if (shallAddNature)
      {
         newNatureIDs = MoflonNatureUtils.insertAtEndIfMissing(newNatureIDs, WorkspaceHelper.XTEXT_NATURE_ID);
         newNatureIDs = MoflonNatureUtils.insertAtEndIfMissing(newNatureIDs, getNatureId());
      } else
      {
         newNatureIDs = MoflonNatureUtils.removeNatureID(newNatureIDs, WorkspaceHelper.XTEXT_NATURE_ID);
         newNatureIDs = MoflonNatureUtils.removeNatureID(newNatureIDs, getNatureId());
      }
      return super.updateNatureIDs(newNatureIDs, shallAddNature);
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
