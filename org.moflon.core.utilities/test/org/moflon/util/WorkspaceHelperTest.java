package org.moflon.util;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.core.utilities.WorkspaceHelper;

@SuppressWarnings("restriction")
public class WorkspaceHelperTest
{

   @Test
   public void testGetFullyQualifiedClassName() throws Exception
   {
      final String expectedName = "org.moflon.ide.util.Helper";
      final String actualName = WorkspaceHelper.getFullyQualifiedClassName(new TestFile(new Path("MyProject/src/org/moflon/ide/util/Helper.java"), null));
      Assert.assertEquals(expectedName, actualName);
   }

   private class TestFile extends File
   {

      protected TestFile(final IPath path, final Workspace container)
      {
         super(path, container);
      }

   }
}
