/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.rules.engine;
import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.upb.tools.sdm.JavaSDM; // requires Fujaba5/libs/RuntimeTools.jar in classpath
import de.upb.tools.sdm.JavaSDMException;


public abstract class PayloadStrategy
{


   /**
    * <pre>
    *           0..n     payloadStrategies     0..1
    * PayloadStrategy ------------------------- ExecutionPlanEngine
    *           payloadStrategies               engine
    * </pre>
    */
   public static final String PROPERTY_ENGINE = "engine";

   @Property( name = PROPERTY_ENGINE, partner = ExecutionPlanEngine.PROPERTY_PAYLOAD_STRATEGIES, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   private ExecutionPlanEngine engine;

   @Property( name = PROPERTY_ENGINE )
   public boolean setEngine (ExecutionPlanEngine value)
   {
      boolean changed = false;

      if (this.engine != value)
      {
      
         ExecutionPlanEngine oldValue = this.engine;
         PayloadStrategy source = this;
         if (this.engine != null)
         {
            this.engine = null;
            oldValue.removeFromPayloadStrategies (this);
         }
         this.engine = value;

         if (value != null)
         {
            value.addToPayloadStrategies (this);
         }
         changed = true;
      
      }
      return changed;
   }

   @Property( name = PROPERTY_ENGINE )
   public PayloadStrategy withEngine (ExecutionPlanEngine value)
   {
      setEngine (value);
      return this;
   }

   public ExecutionPlanEngine getEngine ()
   {
      return this.engine;
   }

   public abstract double getPayload (UMLLink link , UMLObject source , UMLObject target );

   public double getPayload (UMLLink link , UMLObject source , UMLObject target , String key )
   {
      boolean fujaba__Success = false;

      // story pattern 
      try 
      {
         fujaba__Success = false; 

         // constraint key == null
         JavaSDM.ensure ( key == null );
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
            getEngine ().warning ("Payload for key unsupported by this PayloadStrategy: "+this.getClass (), null);
            fujaba__Success = true;
         }
         catch ( JavaSDMException fujaba__InternalException )
         {
            fujaba__Success = false;
         }


      }
      return getPayload (link, source, target);
   }

   public abstract boolean isResponsible (UMLLink link , UMLObject target );

   public void removeYou()
   {
      this.setEngine (null);
   }
}


