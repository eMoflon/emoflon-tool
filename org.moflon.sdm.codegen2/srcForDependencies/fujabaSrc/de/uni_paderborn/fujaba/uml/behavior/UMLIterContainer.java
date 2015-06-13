package de.uni_paderborn.fujaba.uml.behavior;

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FType;

public class UMLIterContainer extends UMLObject
{

   protected UMLIterContainer(FProject project, boolean persistent)
   {
      super(project, persistent);
   }

   /**
    * *
    * 
    * <pre>    *
    * 0..1     container     0..1    * IteratedCollection ------------------------&gt; FType    *           
    * 
    *    iteratedCollection               containedType    *
    * </pre>
    */
   public static final String PROPERTY_CONTAINED_TYPE = "containedType";

   @Property(name = PROPERTY_CONTAINED_TYPE, kind = ReferenceHandler.ReferenceKind.TO_ONE, adornment = ReferenceHandler.Adornment.NONE)
   private FType containedType;

   @Property(name = PROPERTY_CONTAINED_TYPE)
   public boolean setContainedType ( FType value )
   {
      boolean

      changed = false;
      if (this.containedType != value)
      {
         FType oldValue = this.containedType;
         this.containedType

         = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_CONTAINED_TYPE,
               oldValue, value);
         changed = true;

      }
      return changed;
   }

   @Property(name = PROPERTY_CONTAINED_TYPE)
   public FType getContainedType ()
   {
      return this.containedType;

   }
   /**

    * <pre>

    *           0..1     container     0..1

    * UMLIterContainer ------------------------- Iteration

    *           container               iteration

    * </pre>

    */

   public static final String PROPERTY_ITERATION = "iteration";



   @Property( name = PROPERTY_ITERATION, partner = UMLIteration.PROPERTY_CONTAINER, kind = ReferenceHandler.ReferenceKind.TO_ONE,

         adornment = ReferenceHandler.Adornment.NONE)

   private UMLIteration iteration;

   @Property( name = PROPERTY_ITERATION )

   public boolean setIteration (UMLIteration value)

   {

      boolean changed = false;



      if (this.iteration != value)

      {

         

         UMLIteration oldValue = this.iteration;

         UMLIterContainer source = this;

         if (this.iteration != null)

         {

            this.iteration = null;

            oldValue.setContainer (null);

         }

         this.iteration = value;



         if (value != null)

         {

            value.setContainer (this);

         }

            getPropertyChangeSupport().firePropertyChange(PROPERTY_ITERATION, oldValue, value);

         changed = true;

         

      }

      return changed;

   }



   @Property( name = PROPERTY_ITERATION )

   public UMLIteration getIteration ()

   {

      return this.iteration;

   }
   public void removeYou ()
   {
      this.setIteration(null);
      this.setContainedType(null);
   }

}
