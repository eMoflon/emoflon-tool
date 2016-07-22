package org.moflon.ide.visualisation.dot.language;

import java.io.FileNotFoundException;
import java.net.URL;

import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.ide.visualization.dot.language.AbstractGraph;
import org.moflon.ide.visualization.dot.language.ClassGraph;
import org.moflon.ide.visualization.dot.language.DirectedGraph;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class DotUnparserAdapter
{

   public String unparse(AbstractGraph graph)
   {      
      try
      {
        ST template = null;
        if(graph instanceof DirectedGraph){
        	template=getStringTemplateGroup().getInstanceOf("DirectedGraph");
        	template.add("directGraph", graph);
        }
        if(graph instanceof ClassGraph){
        	template=getStringTemplateGroup().getInstanceOf("ClassGraph");
        	template.add("classGraph", graph);
        }
        return template.render();
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      
      return "Unable to unparse: " + graph.getName();
   }

   private STGroup getStringTemplateGroup() throws FileNotFoundException
   {
      URL templateFile = MoflonUtilitiesActivator.getPathRelToPlugIn("resources/templates/Dot.stg", DotVisualisationPlugin.getDefault().getPluginId());

      return new STGroupFile(templateFile, "UTF-8", '<', '>'); 
   }
}