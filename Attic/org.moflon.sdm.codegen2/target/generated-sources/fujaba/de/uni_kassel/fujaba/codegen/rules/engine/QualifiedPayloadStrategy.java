/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;
import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_paderborn.fujaba.metamodel.structure.FCardinality;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public class QualifiedPayloadStrategy extends DefaultPayloadStrategy
{


   public static final String PROPERTY_AV_G_KEYS = "AVG_KEYS";

   @Property( name = PROPERTY_AV_G_KEYS, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE )
   public static final double AVG_KEYS = 25;


   public static final String PROPERTY_AV_G_KE_Y_LOAD = "AVG_KEY_LOAD";

   @Property( name = PROPERTY_AV_G_KE_Y_LOAD, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE )
   public static final double AVG_KEY_LOAD = 10;


   public double getPayload (UMLLink link , UMLObject source , UMLObject target )
   {

      return AVG_KEYS * getPayload (link, source, target, null);
   }

   public double getPayload (UMLLink link , UMLObject source , UMLObject target , String key )
   {
      boolean fujaba__Success = false;
      FRole targetRole = null;
      FCardinality card = null;
      int lower = 0;
      double upper = 0.0;
      double payload = 0.0;

      // story pattern successor
      try 
      {
         fujaba__Success = false; 

         targetRole = link.getCorrespondingRole (target);

         // check object targetRole is really bound
         JavaSDM.ensure ( targetRole != null );
         // search to-one link card from targetRole to card
         card = targetRole.getCard ();

         // check object card is really bound
         JavaSDM.ensure ( card != null );


         // collabStat call
         lower = card.getLowerBound ();
         // collabStat call
         upper = card.getUpperBound ();
         // collabStat call
         if ( upper==Integer.MAX_VALUE )
         {
         upper = AVG_KEY_LOAD;
         }
         // collabStat call
         payload = 0.5 * (lower + upper);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      if ( fujaba__Success )
      {
         return payload;

      }
      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // collabStat call
         // payload = super.getPayload (link, source, target, key);
         fujaba__Success = true;
      }
      catch ( JavaSDMException fujaba__InternalException )
      {
         fujaba__Success = false;
      }

      return payload;
   }

   public boolean isResponsible (UMLLink link , UMLObject target )
   {
      boolean fujaba__Success = false;
      FRole targetRole = null;
      FQualifier qual = null;
      FRole sourceRole = null;

      // story pattern successor
      try 
      {
         fujaba__Success = false; 

         targetRole = link.getCorrespondingRole (target);

         // check object targetRole is really bound
         JavaSDM.ensure ( targetRole != null );
         // search to-one link partnerRole from targetRole to sourceRole
         sourceRole = targetRole.getPartnerRole ();

         // check object sourceRole is really bound
         JavaSDM.ensure ( sourceRole != null );

         // check isomorphic binding between objects targetRole and sourceRole
         JavaSDM.ensure ( !targetRole.equals (sourceRole) );

         // search to-one link qualifier from sourceRole to qual
         qual = sourceRole.getQualifier ();

         // check object qual is really bound
         JavaSDM.ensure ( qual != null );



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

