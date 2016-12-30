package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.moflon.core.utilities.WorkspaceHelper;

public class MOSLGTNature extends MoflonProjectConfigurator
{
   @Override
   public ICommand[] updateBuildSpecs(final IProjectDescription description, final ICommand[] buildSpecs, final boolean shallAddBuilders) throws CoreException
   {
      ICommand[] newBuildSpecs = buildSpecs;

      if (shallAddBuilders)
      {
         newBuildSpecs = MoflonBuilderUtils.appendIfMissing(newBuildSpecs, WorkspaceHelper.XTEXT_BUILDER_ID, description);
         newBuildSpecs = MoflonBuilderUtils.appendIfMissing(newBuildSpecs, WorkspaceHelper.MOSL_GT_BUILDER_ID, description);

         MoflonBuilderUtils.ensureBuilderOrder(newBuildSpecs, Arrays.asList(WorkspaceHelper.XTEXT_BUILDER_ID, WorkspaceHelper.MOSL_GT_BUILDER_ID, WorkspaceHelper.JAVA_BUILDER_ID));
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
         newNatureIDs = MoflonNatureUtils.appendIfMissing(newNatureIDs, WorkspaceHelper.XTEXT_NATURE_ID);
         newNatureIDs = MoflonNatureUtils.appendIfMissing(newNatureIDs, getNatureId());
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
