package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.moflon.core.utilities.UtilityClassNotInstantiableException;

public final class MoflonBuilderUtils
{
   public MoflonBuilderUtils()
   {
      throw new UtilityClassNotInstantiableException();
   }

   /**
    * Inserts a build specification with the given builderID into the given buildSpecification if the builder is not contained in the build specification yet.
    * @param buildSpecification the build specification
    * @param builderID the ID of the new builder
    * @param projectDescription the {@link IProjectDescription} that shall be used to construct the builder command
    * @return the updated build specification
    */
   public static ICommand[] insertAtEndIfMissing(final ICommand[] buildSpecification, final String builderID, final IProjectDescription projectDescription)
   {
      if (ProjectUtil.indexOf(buildSpecification, builderID) < 0)
      {
         final ICommand xtextBuilder = projectDescription.newCommand();
         xtextBuilder.setBuilderName(builderID);
         final ICommand[] newBuildSpecification = Arrays.copyOf(buildSpecification, buildSpecification.length + 1);
         newBuildSpecification[newBuildSpecification.length - 1] = xtextBuilder;
         return newBuildSpecification;
      } else
      {
         return buildSpecification;
      }
   }

   /**
    * Returns a list of builder configurations that results from removing the builder with the given ID 'builderID' from the given list of IDs 'inputBuildSpecs'. 
    * @param inputNatureIDs
    * @param id
    * @return the updated list of IDs
    */
   public static ICommand[] removeBuilderID(final ICommand[] inputBuildSpecs, final String builderID)
   {
      final int builderPosition = ProjectUtil.indexOf(inputBuildSpecs, builderID);
      if (builderPosition >= 0)
      {
         final Object[] newBuildSpecificationTmp = ProjectUtil.remove(inputBuildSpecs, builderPosition);
         final ICommand[] newBuildSpecification = Arrays.copyOf(inputBuildSpecs, newBuildSpecificationTmp.length);
         System.arraycopy(newBuildSpecificationTmp, 0, newBuildSpecification, 0, newBuildSpecificationTmp.length);
         return newBuildSpecification;
      } else
      {
         return inputBuildSpecs;
      }
   }
}
