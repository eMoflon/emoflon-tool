package org.moflon.ide.core;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.natures.MOSLGTNature;
import org.moflon.ide.core.runtime.natures.MoflonBuilderUtils;

public class MOSLGTNatureTest
{
   private MOSLGTNature nature;

   private IProjectDescription projectDescription;

   @SuppressWarnings("restriction")
   @Before
   public void setUp()
   {
      nature = new MOSLGTNature();
      projectDescription = new org.eclipse.core.internal.resources.ProjectDescription();
   }

   @Test
   public void testAddBuildSpecs() throws Exception
   {
      ICommand[] buildSpecification = {};
      buildSpecification = MoflonBuilderUtils.insertAtEndIfMissing(buildSpecification, WorkspaceHelper.JAVA_BUILDER_ID, projectDescription);
      buildSpecification = nature.updateBuildSpecs(projectDescription, buildSpecification, true);

      assertBuilderIsPresent(buildSpecification, WorkspaceHelper.XTEXT_BUILDER_ID);
      assertBuilderIsPresent(buildSpecification, WorkspaceHelper.MOSL_GT_BUILDER_ID);
      assertBuilderIsPresent(buildSpecification, WorkspaceHelper.JAVA_BUILDER_ID);

      assertBuilderOrder(buildSpecification, WorkspaceHelper.XTEXT_BUILDER_ID, WorkspaceHelper.MOSL_GT_BUILDER_ID);
      assertBuilderOrder(buildSpecification, WorkspaceHelper.MOSL_GT_BUILDER_ID, WorkspaceHelper.JAVA_BUILDER_ID);
   }

   @Test
   public void testRemoveBuildSpecs() throws Exception
   {
      ICommand[] buildSpecification = {};
      buildSpecification = MoflonBuilderUtils.insertAtEndIfMissing(buildSpecification, WorkspaceHelper.JAVA_BUILDER_ID, projectDescription);
      buildSpecification = nature.updateBuildSpecs(projectDescription, buildSpecification, true);
      
      // Now, remove the nature-specific builders
      buildSpecification = nature.updateBuildSpecs(projectDescription, buildSpecification, false);
      assertBuilderIsMissing(buildSpecification, WorkspaceHelper.XTEXT_BUILDER_ID);
      assertBuilderIsMissing(buildSpecification, WorkspaceHelper.MOSL_GT_BUILDER_ID);
   }

   private static void assertBuilderOrder(ICommand[] buildSpecification, final String firstBuilder, final String secondBuilder)
   {
      final int firstBuildersPosition = ProjectUtil.indexOf(buildSpecification, firstBuilder);
      final int secondBuildersPosition = ProjectUtil.indexOf(buildSpecification, secondBuilder);
      if (firstBuildersPosition >= 0 && secondBuildersPosition >= 0)
      {
         Assert.assertTrue(firstBuildersPosition < secondBuildersPosition);
      }
   }

   private static void assertBuilderIsPresent(final ICommand[] buildSpecification, final String builderID)
   {
      Assert.assertTrue(ProjectUtil.indexOf(buildSpecification, builderID) >= 0);
   }
   
   private static void assertBuilderIsMissing(final ICommand[] buildSpecification, final String builderID)
   {
      Assert.assertTrue(ProjectUtil.indexOf(buildSpecification, builderID) < 0);
   }

}
