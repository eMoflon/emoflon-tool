/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;

import java.util.Comparator;

import de.uni_kassel.fujaba.codegen.rules.ASGElementTokenInterface;
import de.uni_kassel.fujaba.codegen.rules.Token;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.asg.ASGInformation;
import de.uni_paderborn.fujaba.asg.ASGUnparseInformation;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.upb.tools.sdm.JavaSDM;
import de.upb.tools.sdm.JavaSDMException; // requires Fujaba5/libs/RuntimeTools.jar in classpath


public class TokenComparator implements Comparator
{


   public int compare (Object o1 , Object o2 )
   {
      boolean fujaba__Success = false;
      String text1 = null;
      String text2 = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         text1 = this.getString (o1);
         // collabStat call
         text2 = this.getString (o2);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return JavaSDM.stringCompare (text1, text2);
   }

   public String getString (Object obj )
   {
      boolean fujaba__Success = false;
      Object _TmpObject = null;
      Token token = null;
      ASGElementTokenInterface asgToken = null;
      FElement elem = null;
      ASGElement asgElem = null;
      ASGInformation info = null;
      ASGUnparseInformation unparseInfo = null;

      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         _TmpObject = obj;

         // ensure correct type and really bound of object token
         JavaSDM.ensure ( _TmpObject instanceof Token );
         token = (Token) _TmpObject;

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         return null;

      }
      // story pattern successor
      try 
      {
         fujaba__Success = false; 

         _TmpObject = token;

         // ensure correct type and really bound of object asgToken
         JavaSDM.ensure ( _TmpObject instanceof ASGElementTokenInterface );
         asgToken = (ASGElementTokenInterface) _TmpObject;

         // search to-one link element from asgToken to elem
         elem = asgToken.getElement ();

         // check object elem is really bound
         JavaSDM.ensure ( elem != null );


         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         // story pattern successor
         try 
         {
            fujaba__Success = false; 

            // collabStat call
            System.err.println ("Can not compare " + obj + ".");
            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }

         return obj.toString();

      }
      // story pattern 
      try 
      {
         fujaba__Success = false; 

         _TmpObject = elem;

         // ensure correct type and really bound of object asgElem
         JavaSDM.ensure ( _TmpObject instanceof ASGElement );
         asgElem = (ASGElement) _TmpObject;

         // search to-one link aSGElement from asgElem to unparseInfo
         unparseInfo = asgElem.getFromUnparseInformations (asgElem);

         // check object unparseInfo is really bound
         JavaSDM.ensure ( unparseInfo != null );

         // check isomorphic binding between objects unparseInfo and asgElem
         JavaSDM.ensure ( !unparseInfo.equals (asgElem) );

         // search to-one link aSGInformation from unparseInfo to info
         info = unparseInfo.getFromASGInformation ("textOrder");

         // check object info is really bound
         JavaSDM.ensure ( info != null );

         // check isomorphic binding between objects info and asgElem
         JavaSDM.ensure ( !info.equals (asgElem) );

         // constraint info.getFromInformation("order") != null
         JavaSDM.ensure ( info.getFromInformation("order") != null );


         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         return elem.getName() + elem.toString();

      }
      return info.getFromInformation("order") + elem.getName() + elem.toString();
   }

   public void removeYou()
   {
   }
}


