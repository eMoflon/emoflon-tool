package de.uni_paderborn.fujaba.metamodel.common;

import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath
import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath
import java.util.*;


public interface FTag extends FElement
{
   /**
    * <pre>
    *           0..*     tags     0..1
    * FTag ------------------------- FIncrement
    *           tags               revTags
    * </pre>
    */
   public static final String PROPERTY_REV_TAGS = "revTags";

   @Property( name = PROPERTY_REV_TAGS, partner = FIncrement.PROPERTY_TAGS, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   public boolean setRevTags (FIncrement value);

   @Property( name = PROPERTY_REV_TAGS )
   public FIncrement getRevTags ();

   /**
    * <pre>
    *           0..1     values     0..1
    * FTag ------------------------> String
    *           fTag               values
    * </pre>
    */
   public static final String PROPERTY_VALUES = "values";

   @Property( name = PROPERTY_VALUES, kind = ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE,
         adornment = ReferenceHandler.Adornment.NONE)
   public boolean removeFromValues (String value);

   @Property( name = PROPERTY_VALUES )
   public void removeAllFromValues ();

   @Property( name = PROPERTY_VALUES )
   public boolean hasInValues (String value);

   @Property( name = PROPERTY_VALUES )
   public Iterator iteratorOfValues ();

   @Property( name = PROPERTY_VALUES )
   public int sizeOfValues ();

   @Property( name = PROPERTY_VALUES )
   public boolean hasInValues (String key, String value);

   @Property( name = PROPERTY_VALUES )
   public boolean hasKeyInValues (String key);

   @Property( name = PROPERTY_VALUES )
   public Iterator keysOfValues ();

   @Property( name = PROPERTY_VALUES )
   public Iterator entriesOfValues ();

   @Property( name = PROPERTY_VALUES )
   public boolean addToValues (String key, String value);

   @Property( name = PROPERTY_VALUES )
   public boolean addToValues (Map.Entry entry);

   @Property( name = PROPERTY_VALUES )
   public boolean removeFromValues (String key, String value);

   @Property( name = PROPERTY_VALUES )
   public boolean removeKeyFromValues (String key);

   @Property( name = PROPERTY_VALUES )
   public String getFromValues (String key);
}