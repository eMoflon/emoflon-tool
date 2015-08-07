package org.moflon.ide.ui.admin.wizards.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.ide.ui.UIActivator;
import org.moflon.moca.BasicFormatRenderer;

public class WizardUtil
{
   public static String renderTemplate(final StringTemplateGroup stg, final String templateName, final Map<String, Object> attributes) {
      StringTemplate st = stg.getInstanceOf(templateName);
      st.registerRenderer(String.class, new BasicFormatRenderer());
      st.setAttributes(attributes);
      return st.toString();
   }
   
   public static StringTemplateGroup loadStringTemplateGroup(final String path) {
      try {
         InputStreamReader reader = new InputStreamReader(getTemplateFileURL(path).openStream());
         return new StringTemplateGroup(reader);
      } catch (IOException e) {
         Logger.getLogger(WizardUtil.class).error("Unable to load template file: " + getTemplateFileURL(path));
      }
      return null;
   }
   
   public static URL getTemplateFileURL(final String path) {
      return MoflonUtilitiesActivator.getPathRelToPlugIn(path, UIActivator.getModuleID());
   }

}
