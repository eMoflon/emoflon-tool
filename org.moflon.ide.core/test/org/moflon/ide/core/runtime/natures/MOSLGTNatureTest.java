package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.core.utilities.WorkspaceHelper;

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
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, WorkspaceHelper.JAVA_BUILDER_ID, projectDescription);
      buildSpecification = nature.updateBuildSpecs(projectDescription, buildSpecification, true);

      MoflonBuilderUtilsTest.assertBuilderIsPresent(buildSpecification, WorkspaceHelper.XTEXT_BUILDER_ID);
      MoflonBuilderUtilsTest.assertBuilderIsPresent(buildSpecification, WorkspaceHelper.MOSL_GT_BUILDER_ID);
      MoflonBuilderUtilsTest.assertBuilderIsPresent(buildSpecification, WorkspaceHelper.JAVA_BUILDER_ID);
      
      MoflonBuilderUtilsTest.assertBuilderOrder(buildSpecification, Arrays.asList(WorkspaceHelper.XTEXT_BUILDER_ID, WorkspaceHelper.MOSL_GT_BUILDER_ID, WorkspaceHelper.JAVA_BUILDER_ID));
   }

   @Test
   public void testRemoveBuildSpecs() throws Exception
   {
      ICommand[] buildSpecification = {};
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, WorkspaceHelper.JAVA_BUILDER_ID, projectDescription);
      buildSpecification = nature.updateBuildSpecs(projectDescription, buildSpecification, true);
      
      // Now, remove the nature-specific builders
      buildSpecification = nature.updateBuildSpecs(projectDescription, buildSpecification, false);
      MoflonBuilderUtilsTest.assertBuilderIsMissing(buildSpecification, WorkspaceHelper.XTEXT_BUILDER_ID);
      MoflonBuilderUtilsTest.assertBuilderIsMissing(buildSpecification, WorkspaceHelper.MOSL_GT_BUILDER_ID);
   }
   
   @Test
   public void testAddNatureIDs() throws Exception
   {
      String[] natureIDs = new String[]{WorkspaceHelper.MOSL_TGG_NATURE};
      natureIDs = nature.updateNatureIDs(natureIDs, true);
      
      assertNatureIDIsPresent(natureIDs, WorkspaceHelper.MOSL_GT_NATURE_ID);
      assertNatureIDIsPresent(natureIDs, WorkspaceHelper.XTEXT_NATURE_ID);
   }

   @Test
   public void testRemoveNatureIDs() throws Exception
   {
      String[] natureIDs = new String[] { WorkspaceHelper.MOSL_TGG_NATURE };
      natureIDs = nature.updateNatureIDs(natureIDs, true);
      
      // Now, remove the nature-specific nature IDs
      natureIDs = nature.updateNatureIDs(natureIDs, false);

      assertNatureIDIsMissing(natureIDs, WorkspaceHelper.MOSL_GT_NATURE_ID);
      assertNatureIDIsMissing(natureIDs, WorkspaceHelper.XTEXT_NATURE_ID);
   }

   private static void assertNatureIDIsPresent(final String[] natureIDs, final String natureID)
   {
      Assert.assertTrue(ProjectUtil.indexOf(natureIDs, natureID) >= 0);
   }
   
   private static void assertNatureIDIsMissing(final String[] natureIDs, final String natureID)
   {
      Assert.assertTrue(ProjectUtil.indexOf(natureIDs, natureID) < 0);
   }

}
