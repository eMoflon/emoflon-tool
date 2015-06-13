/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;
import java.util.Comparator;

import de.uni_kassel.fujaba.codegen.rules.UMLObjectRef;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class UMLObjectRefComparator implements Comparator
{


   public int compare (Object o1 , Object o2 )
   {
      boolean fujaba__Success = false;
      Object _TmpObject = null;
      UMLObjectRef objRef1 = null;
      UMLObjectRef objRef2 = null;
      int cost = 0;
      UMLObject obj2 = null;
      UMLObject obj1 = null;

      if ( o1 == o2 )
      {
         return 0;

      }
      if ( o1 == null )
      {
         if ( o2 == null )
         {
            return 0;

         }
         return -1;

      }
      if ( o2 == null )
      {
         return 1;

      }
      // story pattern storypatternwiththis
      try 
      {
         fujaba__Success = false; 

         _TmpObject = o1;

         // ensure correct type and really bound of object objRef1
         JavaSDM.ensure ( _TmpObject instanceof UMLObjectRef );
         objRef1 = (UMLObjectRef) _TmpObject;

         _TmpObject = o2;

         // ensure correct type and really bound of object objRef2
         JavaSDM.ensure ( _TmpObject instanceof UMLObjectRef );
         objRef2 = (UMLObjectRef) _TmpObject;

         // check isomorphic binding between objects objRef2 and objRef1
         JavaSDM.ensure ( !objRef2.equals (objRef1) );

         // collabStat call
         cost = (int) (objRef1.getMinSearchCost() - objRef2.getMinSearchCost());
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         throw new RuntimeException ("Objects not comparable");

      }
      if ( !( cost == 0 ) )
      {
         return cost;

      }
      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // check object objRef1 is really bound
         JavaSDM.ensure ( objRef1 != null );
         // check object objRef2 is really bound
         JavaSDM.ensure ( objRef2 != null );
         // check isomorphic binding between objects objRef2 and objRef1
         JavaSDM.ensure ( !objRef2.equals (objRef1) );

         // search to-one link ref from objRef1 to obj1
         obj1 = objRef1.getRef ();

         // check object obj1 is really bound
         JavaSDM.ensure ( obj1 != null );

         // search to-one link ref from objRef2 to obj2
         obj2 = objRef2.getRef ();

         // check object obj2 is really bound
         JavaSDM.ensure ( obj2 != null );

         // check isomorphic binding between objects obj2 and obj1
         JavaSDM.ensure ( !obj2.equals (obj1) );



         // collabStat call
         cost = JavaSDM.stringCompare (obj1.getObjectName(), obj2.getObjectName());
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( cost == 0 ) )
      {
         return cost;

      }
      System.err.println ("Objects have same names! Using hashcode to compare.");
      return objRef1.hashCode() - objRef2.hashCode();
   }

   public void removeYou()
   {
   }
}


