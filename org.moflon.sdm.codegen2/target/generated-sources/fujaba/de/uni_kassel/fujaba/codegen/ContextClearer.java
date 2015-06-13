/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolManager;

import de.uni_kassel.fujaba.codegen.engine.CodeWritingEngine;
import de.uni_kassel.fujaba.codegen.engine.TemplateLoader;
import de.uni_kassel.fujaba.codegen.rules.Token;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class ContextClearer extends CodeWriter
{


   public String generateCode (Token operation )
   {
      boolean fujaba__Success = false;
      ToolManager manager = null;
      TemplateLoader templateEngine = null;
      CodeWritingEngine engine = null;
      Context context = null;

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // search to-one link generators from this to engine
         engine = this.getEngine ();

         // check object engine is really bound
         JavaSDM.ensure ( engine != null );

         // search to-one link template loader from engine to templateEngine
         templateEngine = engine.getTemplateLoader ();

         // check object templateEngine is really bound
         JavaSDM.ensure ( templateEngine != null );

         // search to-one link manager from templateEngine to manager
         manager = templateEngine.getManager ();

         // check object manager is really bound
         JavaSDM.ensure ( manager != null );




         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         context = manager.createContext();

         // check object context is really bound
         JavaSDM.ensure ( context != null );
         // check object templateEngine is really bound
         JavaSDM.ensure ( templateEngine != null );
         // create link context from templateEngine to context
         templateEngine.setContext (context);

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return null;
   }

}


