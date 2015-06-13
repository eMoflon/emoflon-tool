/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;
import de.uni_kassel.fujaba.codegen.rules.UMLLinkRef;
import de.uni_kassel.fujaba.codegen.rules.UMLObjectRef;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.upb.tools.sdm.JavaSDMException; // requires Fujaba5/libs/RuntimeTools.jar in classpath


public class KeyedOrderedToNCostStrategy extends OrderedToNCostStrategy
{


   protected boolean checkQualified (UMLLink link , UMLObject target )
   {
      boolean fujaba__Success = false;
      boolean result = false;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         result = super.checkQualified( link, target );
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return !result;
   }

   protected double getCreateCost (UMLLinkRef linkRef , UMLObjectRef from , double payload )
   {
      boolean fujaba__Success = false;
      double result = 0.0;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         result = super.getCreateCost( linkRef, from, payload );
         // collabStat call
         result = result + getSearchCost (payload);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return result;
   }

   protected int getUpperBound (UMLLink link , UMLObject target )
   {
      boolean fujaba__Success = false;
      int result = 0;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         result = this.getRange ( link, target ) + 1;
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return result;
   }

}


