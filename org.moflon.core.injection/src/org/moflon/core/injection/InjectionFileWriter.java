package org.moflon.core.injection;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

/**
 * This class is used to create injection files
 * 
 * @author Anne-Sophie Ettl
 * 
 */

public class InjectionFileWriter
{
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
         e.printStackTrace();
      }
      return null;
   }
   
   /*public void createContent() 
   {     
      STGroup group;
      try
      {
         group = getSTGroup();
         
         ST classTemplate = group.getInstanceOf("class"); 
         classTemplate.add("imports", content.getImports());
         classTemplate.add("className", "Name");
         classTemplate.add("members", content.getMembers());
         classTemplate.add("models", content.getMethods());
         
         System.out.println(classTemplate.toString());

      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }*/
   
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
          e.printStackTrace();
      }     
      return null;
   }
   
   /*protected STGroup getSTGroup() throws FileNotFoundException
   {
      URL templateFile = MoflonUtilitiesActivator.getPathRelToPlugIn("templates/InjectionFileTemplate.stg", CoreActivator.getModuleID());
      return new STGroupFile(templateFile, "UTF-8", '<', '>'); 
   }*/
}
