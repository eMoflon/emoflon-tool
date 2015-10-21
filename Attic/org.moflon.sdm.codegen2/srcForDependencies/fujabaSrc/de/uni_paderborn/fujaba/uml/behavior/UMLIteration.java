package de.uni_paderborn.fujaba.uml.behavior;

import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_kassel.features.annotation.util.Property; // requires Fujaba5/libs/features.jar in classpath

import de.uni_kassel.features.ReferenceHandler; // requires Fujaba5/libs/features.jar in classpath

import de.upb.tools.pcs.PropertyChangeClient; // requires Fujaba5/libs/RuntimeTools.jar in classpath

public class UMLIteration extends UMLLink
{

   protected UMLIteration(FProject project, boolean persistent)
   {
      super(project, persistent);
   }

   /**
    * *
    * 
    * <pre>    *
    * 0..1     iteration     0..1    * UMLIteration ------------------------- UMLStoryActivity    *           
    * 
    *    iteration               revIteration    *
    * </pre>
    */
   public static final String PROPERTY_REV_ITERATION = "revIteration";
   
   @Property(name = PROPERTY_REV_ITERATION, partner = UMLStoryActivity.PROPERTY_ITERATION, kind = ReferenceHandler.ReferenceKind.TO_ONE, adornment = ReferenceHandler.Adornment.NONE)
   private UMLStoryActivity revIteration;

   @Property(name = PROPERTY_REV_ITERATION)
   public boolean setRevIteration ( UMLStoryActivity value )
   {
      boolean changed = false;
      if (this.revIteration != value)
      {

         UMLStoryActivity oldValue = this.revIteration;
         UMLIteration source = this;
         if (this.revIteration != null)
         {

            this.revIteration = null;
            oldValue.setIteration(null);
         }
         this.revIteration = value;
         if (value != null)
         {
            value.setIteration(this);
         }
         getPropertyChangeSupport().firePropertyChange(PROPERTY_REV_ITERATION, oldValue, value);
         changed = true;
      }
      return changed;
   }

   @Property(name = PROPERTY_REV_ITERATION)
   public UMLStoryActivity

   getRevIteration ()
   {
      return this.revIteration;
   }
   
   /**
    * *
    * 
    * <pre>    *
    * 0..1     container     0..1    * Iteration ------------------------- IteratedContainer    *           
    * 
    *    iteration               container    *
    * </pre>
    */
   public static final String PROPERTY_CONTAINER = "container";
   
   @Property(name = PROPERTY_CONTAINER, partner = UMLIterContainer.PROPERTY_ITERATION, kind = ReferenceHandler.ReferenceKind.TO_ONE, adornment = ReferenceHandler.Adornment.NONE)
   private UMLIterContainer container;

   @Property(name = PROPERTY_CONTAINER)
   public boolean setContainer ( UMLIterContainer value )
   {
      boolean changed = false;
      if (this.container != value)
      {
         UMLIterContainer oldValue = this.container;
         UMLIteration source = this;
         if (this.container != null)
         {
            this.container = null;

            oldValue.setIteration(null);
         }
         this.container = value;
         if (value != null)
         {
            value.setIteration(this);
         }
         getPropertyChangeSupport().firePropertyChange(PROPERTY_CONTAINER, oldValue, value);

         changed = true;
      }
      return changed;
   }

   @Property(name = PROPERTY_CONTAINER)
   public UMLIterContainer getContainer ()
   {
      return this.container;
   }

   /**
    * *
    * 
    * <pre>    *
    * 0..1     runVariable     0..1    * Iteration ------------------------- 
    * 
    *    IterationVariable    *           iteration               runVariable    *
    * </pre>
    */
   public static final String PROPERTY_RUN_VARIABLE = "runVariable";
   
   @Property(name = PROPERTY_RUN_VARIABLE, partner = UMLIterRunVariable.PROPERTY_ITERATION, kind = ReferenceHandler.ReferenceKind.TO_ONE, adornment = ReferenceHandler.Adornment.NONE)
   private UMLIterRunVariable runVariable;

   @Property(name = PROPERTY_RUN_VARIABLE)
   public boolean setRunVariable ( UMLIterRunVariable value )
   {
      boolean changed = false;
      if

      (this.runVariable != value)
      {
         UMLIterRunVariable oldValue = this.runVariable;
         UMLIteration source = this;
         if

         (this.runVariable != null)
         {
            this.runVariable = null;
            oldValue.setIteration(null);
         }

         this.runVariable = value;
         if (value != null)
         {
            value.setIteration(this);
         }

         getPropertyChangeSupport().firePropertyChange(PROPERTY_RUN_VARIABLE,
               oldValue, value);
         changed = true;
      }
      return

      changed;
   }

   @Property(name = PROPERTY_RUN_VARIABLE)
   public UMLIterRunVariable getRunVariable ()
   {
      return this.runVariable;
   }

   public void removeYou ()
   {
      this.setContainer(null);
      this.setRunVariable(null);
   }
   
   // TODO override source/target?
}
