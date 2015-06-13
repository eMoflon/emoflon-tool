/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.classdiag.engine;

import java.util.Iterator;

import de.uni_kassel.fujaba.codegen.classdiag.ASGElementToken;
import de.uni_kassel.fujaba.codegen.engine.TokenCreationEngine;
import de.uni_kassel.fujaba.codegen.rules.Token;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLStartActivity;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class MethodEngine extends TokenCreationEngine
{


   public Token createToken (FElement element )
   {
      boolean fujaba__Success = false;
      Object _TmpObject = null;
      FMethod method = null;
      ASGElementToken token = null;
      Iterator fujaba__IterStartToActDiag = null;
      UMLActivityDiagram actDiag = null;
      UMLStartActivity start = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         _TmpObject = element;

         // ensure correct type and really bound of object method
         JavaSDM.ensure ( _TmpObject instanceof FMethod );
         method = (FMethod) _TmpObject;

         // create object token
         token = new ASGElementToken ( );

         // create link element from token to method
         token.setElement (method);

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      // story pattern Successor of Successor of Successor of storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         // check object method is really bound
         JavaSDM.ensure ( method != null );
         // search to-one link spec from method to start
         _TmpObject = method.getRevSpec ();

         // ensure correct type and really bound of object start
         JavaSDM.ensure ( _TmpObject instanceof UMLStartActivity );
         start = (UMLStartActivity) _TmpObject;


         // iterate to-many link elements from start to actDiag
         fujaba__Success = false;
         fujaba__IterStartToActDiag = start.iteratorOfDiagrams ();

         while ( !(fujaba__Success) && fujaba__IterStartToActDiag.hasNext () )
         {
            try
            {
               _TmpObject =  fujaba__IterStartToActDiag.next ();

               // ensure correct type and really bound of object actDiag
               JavaSDM.ensure ( _TmpObject instanceof UMLActivityDiagram );
               actDiag = (UMLActivityDiagram) _TmpObject;


               fujaba__Success = true;
            }
            catch ( JavaSDMException fujaba__InternalException )
            {
               fujaba__Success = false;
            }
         }
         JavaSDM.ensure (fujaba__Success);

         // collabStat call
         this.createToken (actDiag, token);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return token;
   }

   public boolean isResponsible (FElement element )
   {
      boolean fujaba__Success = false;
      Object _TmpObject = null;
      FMethod method = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         _TmpObject = element;

         // ensure correct type and really bound of object method
         JavaSDM.ensure ( _TmpObject instanceof FMethod );
         method = (FMethod) _TmpObject;

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( fujaba__Success )
      {
         return true;

      }
      return false;
   }

}


