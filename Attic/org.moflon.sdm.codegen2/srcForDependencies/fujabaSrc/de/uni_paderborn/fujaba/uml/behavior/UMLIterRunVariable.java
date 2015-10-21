package de.uni_paderborn.fujaba.uml.behavior;

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FProject;

public class UMLIterRunVariable extends UMLObject
{

   protected UMLIterRunVariable(FProject project, boolean persistent)
   {
      super(project, persistent);

   }

   /**
    * *
    * 
    * <pre>    *
    * 0..1     runVariable     0..1    * IterationVariable ------------------------- Iteration    *           
    * 
    *    runVariable               iteration    *
    * </pre>
    */
   public static final String PROPERTY_ITERATION = "iteration";
   @Property(name =

   PROPERTY_ITERATION, partner = UMLIteration.PROPERTY_RUN_VARIABLE, kind = ReferenceHandler.ReferenceKind.TO_ONE, adornment =

   ReferenceHandler.Adornment.NONE)
   private UMLIteration iteration;

   @Property(name = PROPERTY_ITERATION)
   public boolean setIteration

   ( UMLIteration value )
   {
      boolean changed = false;
      if (this.iteration != value)
      {
         UMLIteration oldValue = this.iteration;
         UMLIterRunVariable source = this;
         if (this.iteration != null)
         {
            this.iteration = null;

            oldValue.setRunVariable(null);
         }
         this.iteration = value;
         if (value != null)
         {

            value.setRunVariable(this);
         }
         getPropertyChangeSupport().firePropertyChange(PROPERTY_ITERATION,
               oldValue, value);

         changed = true;
      }
      return changed;
   }

   @Property(name = PROPERTY_ITERATION)
   public UMLIteration getIteration ()
   {

      return this.iteration;
   }

   public void removeYou ()
   {
      this.setIteration(null);
   }
}
