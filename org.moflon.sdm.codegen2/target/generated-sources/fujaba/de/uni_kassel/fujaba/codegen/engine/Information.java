/*
 * generated by Fujaba - CodeGen2
 */
package de.uni_kassel.fujaba.codegen.engine;
import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath


public class Information
{


   /**
    * <pre>
    *           0..1     additionalInfo     0..1
    * Information ------------------------- CodeWritingEngine
    *           information               engine
    * </pre>
    */
   public static final String PROPERTY_ENGINE = "engine";

   @Property( name = PROPERTY_ENGINE, partner = CodeWritingEngine.PROPERTY_INFORMATION, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   private CodeWritingEngine engine;

   @Property( name = PROPERTY_ENGINE )
   public boolean setEngine (CodeWritingEngine value)
   {
      boolean changed = false;

      if (this.engine != value)
      {
      
         CodeWritingEngine oldValue = this.engine;
         Information source = this;
         if (this.engine != null)
         {
            this.engine = null;
            oldValue.removeFromInformation (this);
         }
         this.engine = value;

         if (value != null)
         {
            value.addToInformation (this);
         }
         changed = true;
      
      }
      return changed;
   }

   @Property( name = PROPERTY_ENGINE )
   public Information withEngine (CodeWritingEngine value)
   {
      setEngine (value);
      return this;
   }

   public CodeWritingEngine getEngine ()
   {
      return this.engine;
   }

   public void reset ()
   {
   }

   public static final String PROPERTY_TYPE = "type";

   @Property( name = PROPERTY_TYPE, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE )
   private String type;

   @Property( name = PROPERTY_TYPE )
   public void setType (String value)
   {
      this.type = value;
   }

   public Information withType (String value)
   {
      setType (value);
      return this;
   }

   @Property( name = PROPERTY_TYPE )
   public String getType ()
   {
      return this.type;
   }

   public void removeYou()
   {
      this.setEngine (null);
   }
}


