package org.moflon.ide.core.runtime.natures;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.gervarro.eclipse.workspace.util.ProjectUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoflonBuilderUtilsTest
{
   private IProjectDescription projectDescription;

   private ICommand[] buildSpecification;

   @SuppressWarnings("restriction")
   @Before
   public void setUp()
   {
      projectDescription = new org.eclipse.core.internal.resources.ProjectDescription();
      buildSpecification = new ICommand[] {};
   }

   @Test
   public void testTwoBuildersOK() throws Exception
   {
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "A", projectDescription);
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "B", projectDescription);

      MoflonBuilderUtils.ensureBuilderOrder(buildSpecification, Arrays.asList("A", "B"));

      assertBuilderOrder(buildSpecification, Arrays.asList("A", "B"));
   }

   @Test
   public void testTwoBuildersChangeOrder() throws Exception
   {
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "A", projectDescription);
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "B", projectDescription);

      MoflonBuilderUtils.ensureBuilderOrder(buildSpecification, Arrays.asList("B", "A"));

      assertBuilderOrder(buildSpecification, Arrays.asList("B", "A"));
   }

   @Test
   public void testMissingBuilder() throws Exception
   {
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "A", projectDescription);
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "B", projectDescription);
      
      MoflonBuilderUtils.ensureBuilderOrder(buildSpecification, Arrays.asList("C", "A"));

      assertBuilderOrder(buildSpecification, Arrays.asList("A", "B"));
   }

   @Test
   public void testThreeBuilders() throws Exception
   {
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "A", projectDescription);
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "B", projectDescription);
      buildSpecification = MoflonBuilderUtils.appendIfMissing(buildSpecification, "C", projectDescription);

      MoflonBuilderUtils.ensureBuilderOrder(buildSpecification, Arrays.asList("C", "A"));
      
      assertBuilderOrder(buildSpecification, Arrays.asList("C", "A", "B"));
   }
   
   static void assertBuilderIsPresent(final ICommand[] buildSpecification, final String builderID)
   {
      Assert.assertTrue(ProjectUtil.indexOf(buildSpecification, builderID) >= 0);
   }

   static void assertBuilderIsMissing(final ICommand[] buildSpecification, final String builderID)
   {
      Assert.assertTrue(ProjectUtil.indexOf(buildSpecification, builderID) < 0);
   }

   static void assertBuilderOrder(final ICommand[] buildSpecification, final List<String> builderIDs)
   {
      for (int i = 0; i < buildSpecification.length; ++i)
      {
         Assert.assertEquals(i, ProjectUtil.indexOf(buildSpecification, builderIDs.get(i)));
      }
   }
}
