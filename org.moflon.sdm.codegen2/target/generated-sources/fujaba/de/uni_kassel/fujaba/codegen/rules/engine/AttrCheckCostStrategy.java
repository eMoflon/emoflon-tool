/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;
import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.fujaba.codegen.rules.CheckAttrExprPairOperation;
import de.uni_kassel.fujaba.codegen.rules.Token;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class AttrCheckCostStrategy extends CostStrategy
{


   public static final String PROPERTY_ATT_R_CHEC_K_COST = "ATTR_CHECK_COST";

   @Property( name = PROPERTY_ATT_R_CHEC_K_COST, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE )
   public static final double ATTR_CHECK_COST = 500;


   public double getCost (Token operation )
   {

      return ATTR_CHECK_COST;
   }

   public boolean isResponsible (Token operation )
   {
      boolean fujaba__Success = false;
      Object _TmpObject = null;
      CheckAttrExprPairOperation check = null;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         _TmpObject = operation;

         // ensure correct type and really bound of object check
         JavaSDM.ensure ( _TmpObject instanceof CheckAttrExprPairOperation );
         check = (CheckAttrExprPairOperation) _TmpObject;

         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( !( fujaba__Success ) )
      {
         return false;

      }
      return true;
   }

}


