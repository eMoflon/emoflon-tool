package org.moflon.util;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.moflon.core.utilities.MoflonUtil;

public class MoflonUtilTest
{
   @Test
   public void testCorrectPathWithImportMappings() throws Exception
   {
      HashMap<String, String> importMappings = new HashMap<String, String>();
      importMappings.put("java.classifiers", "org.emftext.language.java.classifiers");
      importMappings.put("java.classifiers.foo", "longer.prefix");

      Assert.assertEquals("", MoflonUtil.transformPackageNameUsingImportMapping("", importMappings));
      Assert.assertEquals("notcontained", MoflonUtil.transformPackageNameUsingImportMapping("notcontained", importMappings));
      Assert.assertEquals("not.contained", MoflonUtil.transformPackageNameUsingImportMapping("not.contained", importMappings));
      Assert.assertEquals("java.partialmatch", MoflonUtil.transformPackageNameUsingImportMapping("java.partialmatch", importMappings));
      Assert.assertEquals("org.emftext.language.java.classifiers", MoflonUtil.transformPackageNameUsingImportMapping("java.classifiers", importMappings));

      //TODO@rkluge: Isn't this what we want?
      // Assert.assertEquals("org.emftext.language.java.classifiers.some-suffix",
      // MoflonUtil.transformPackageNameUsingImportMapping("java.classifiers.some-suffix", importMappings));

      // Matching the longest prefix is currently not supported. Instead, the first matching prefix is chosen
      // Assert.assertEquals("longer.prefix", MoflonUtil.transformPackageNameUsingImportMapping("java.classifiers.foo",
      // importMappings));
      Assert.assertEquals("org.emftext.language.java.classifiers", MoflonUtil.transformPackageNameUsingImportMapping("java.classifiers.foo", importMappings));
   }
}
