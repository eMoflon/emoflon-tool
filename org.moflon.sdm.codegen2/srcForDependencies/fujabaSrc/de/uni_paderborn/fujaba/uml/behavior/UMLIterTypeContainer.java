package de.uni_paderborn.fujaba.uml.behavior;

import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_kassel.features.ReferenceHandler;

public class UMLIterTypeContainer extends UMLIterContainer
{

   public UMLIterTypeContainer(FProject project, boolean persistent)
   {
      super(project, persistent);
   }

   public static final String PROPERTY_INCLUDE_SUBCLASSES = "includeSubclasses";

   @Property(name = PROPERTY_INCLUDE_SUBCLASSES, kind = ReferenceHandler.ReferenceKind.ATTRIBUTE)
   private boolean includeSubclasses;

   @Property(name = PROPERTY_INCLUDE_SUBCLASSES)
   public void setIncludeSubclasses ( boolean value )
   {
      if (this.includeSubclasses != value)
      {
         boolean oldValue = this.includeSubclasses;
         this.includeSubclasses = value;
         getPropertyChangeSupport().firePropertyChange(
               PROPERTY_INCLUDE_SUBCLASSES, oldValue, value);
      }
   }

   @Property(name = PROPERTY_INCLUDE_SUBCLASSES)
   public boolean isIncludeSubclasses ()
   {
      return this.includeSubclasses;
   }

}
