package org.moflon.util.plugins;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.moflon.core.utilities.WorkspaceHelper;

public class FeatureUtilsTest
{

   private static final IFile MOFLON_IDE_FEATURE_XML = WorkspaceHelper.getProjectByName("MoflonIdeFeature").getFile("feature.xml");

   @Ignore("Not working without plugin context")
   @Test
   public void testExtractPluginIdsFromFeature() throws Exception
   {
      List<String> pluginIds = FeatureUtils.getAllPluginsInFeature(MOFLON_IDE_FEATURE_XML);
      Assert.assertTrue(pluginIds.contains("org.moflon.core.utilities"));
   }

   @Ignore("Not working without plugin context")
   @Test
   public void testUpdatePluginIdsInFeature() throws Exception
   {
      FeatureUtils.updateVersionsOfAllPluginsInFeature(MOFLON_IDE_FEATURE_XML, Arrays.asList("org.moflon.*"), "2.0.0.qualifier", new NullProgressMonitor());
   }

   @Ignore("Not working without plugin context")
   @Test
   public void testUpdateFeatureId() throws Exception
   {
      FeatureUtils.updateVersionOfFeature(MOFLON_IDE_FEATURE_XML, null, "2.0.1.qualifier", new NullProgressMonitor());
   }
}
