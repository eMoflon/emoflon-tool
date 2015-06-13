package org.moflon.moca.dot.unparser;

import java.io.FileNotFoundException;
import java.net.URL;

import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.ide.core.CoreActivator;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import DotLanguage.DirectedGraph;
import Moca.unparser.impl.UnparserImpl;
import MocaTree.Node;

public class DotUnparserAdapter extends UnparserImpl
{

   @Override
   public boolean canUnparseFile(String fileName)
   {
      return fileName.endsWith(".dot");
   }

   @Override
   public String unparse(Node node)
   {
      DirectedGraph graph = (DirectedGraph) node;
      
      try
      {
        ST template = getStringTemplateGroup().getInstanceOf("DirectedGraph");
        template.add("directGraph", graph);
        return template.render();
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      
      return "Unable to unparse: " + node.getName();
   }

   protected STGroup getStringTemplateGroup() throws FileNotFoundException
   {
      URL templateFile = MoflonUtilitiesActivator.getPathRelToPlugIn("resources/templates/Dot.stg", CoreActivator.getModuleID());

      return new STGroupFile(templateFile, "UTF-8", '<', '>'); 
   }
}