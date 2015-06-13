package org.moflon.ide.ui.admin.wizards.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.ide.ui.UIActivator;
import org.moflon.moca.BasicFormatRenderer;

public class WizardUtil
{
   public static String renderTemplate(StringTemplateGroup stg, String templateName, Map<String, Object> attributes) {
      StringTemplate st = stg.getInstanceOf(templateName);
      st.registerRenderer(String.class, new BasicFormatRenderer());
      st.setAttributes(attributes);
      return st.toString();
   }
   
   public static StringTemplateGroup loadStringTemplateGroup(String path) {
      try {
         InputStreamReader reader = new InputStreamReader(getTemplateFileURL(path).openStream());
         return new StringTemplateGroup(reader);
      } catch (IOException e) {
         System.err.println("unable to load template file: " + getTemplateFileURL(path));
      }
      return null;
   }
   
   public static URL getTemplateFileURL(String path) {
      return MoflonUtilitiesActivator.getPathRelToPlugIn(path, UIActivator.getModuleID());
   }

}
