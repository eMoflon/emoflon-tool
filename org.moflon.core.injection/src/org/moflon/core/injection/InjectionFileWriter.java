package org.moflon.core.injection;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.log4j.Logger;
import org.moflon.core.utilities.LogUtils;

/**
 * This class is used to create injection files
 * 
 * @author Anne-Sophie Ettl
 * 
 */

public class InjectionFileWriter
{

   private static final Logger logger = Logger.getLogger(InjectionFileWriter.class);

   private InjectionFileContentModel content;
   
   private final String TEMPLATE_LOC = "platform:/plugin/org.moflon.core.injection/templates/InjectionFileTemplate.stg";
   
   public InjectionFileWriter(InjectionFileContentModel content) 
   {
      this.content = content;  
   }
  
   public String createFileContent() 
   {     
      StringTemplateGroup group;
      try
      {
         group = getStringTemplateGroup();
         
         StringTemplate classTemplate = group.getInstanceOf("class"); 
         classTemplate.setAttribute("className", content.getClassName());
         classTemplate.setAttribute("imports", content.getImports());
         classTemplate.setAttribute("members", content.getMembers());
         classTemplate.setAttribute("models", content.getMethods());
         
         return classTemplate.toString();

      } catch (FileNotFoundException e)
      {
         LogUtils.error(logger, e);
      }
      return null;
   }
   
   protected StringTemplateGroup getStringTemplateGroup() throws FileNotFoundException
   {   
      URL url;
      try {
          url = new URL(TEMPLATE_LOC);
          InputStream inputStream = url.openConnection().getInputStream();
          InputStreamReader reader = new InputStreamReader(inputStream);
          StringTemplateGroup stg = new StringTemplateGroup(reader, DefaultTemplateLexer.class); 
          return stg;
      } catch (Exception e) {
         LogUtils.error(logger, e);
      }     
      return null;
   }
}
