/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact address:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.uni_paderborn.fujaba.uml.common;


import java.util.Collection;
import java.util.Iterator;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FIncrement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.uml.structure.UMLClass;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashSet;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *                      +-----------+ 0..1        uMLStereotypes         0..1
 * UMLStereotypeManager | getText() |----------------------------------------- UMLStereotype
 *                      +-----------+ uMLStereotypeManager     uMLStereotypes
 *
 *              +-----------+ 0..n                   0..1
 * UMLIncrement | getText() |----------------------------- UMLStereotype
 *              +-----------+ increments     uMLStereotype
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLStereotype extends UMLIncrement implements FStereotype
{
   
   /**
    * This is a Flyweight object, do not use this constructor directly. Please use the
    * FStereotypeFactory for getting an instance of this class.
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory
    */
   protected UMLStereotype(FProject project, boolean persistent,
         String factoryKey)
   {
      super(project, persistent, factoryKey);
   }


   /**
    * The immutable text attribute. Once it is set, it can not be changed any more.
    */
   private String name;


   /**
    * Sets the immutable name attribute of the UMLStereotype object. Once it is set, it can not be
    * changed any more.
    * 
    * @param value The new name attribute
    */
   @Override
   public void setName(String value)
   {
      String oldValue = this.name;
      if (oldValue == null && value != null)
      {
         this.name = value;
         firePropertyChange(FElement.NAME_PROPERTY, oldValue, value);
      }
      else if (oldValue != null && !oldValue.equals(value))
      {
         if (!Versioning.get().isInOperationalization(this))
         {
            throw new IllegalStateException(
                  "The name attribute of UMLStereotype is immutable!"
                        + "\nOnce it is set, it can not be changed any more.");
         }
      }
   }


   /**
    * Get the immutable name attribute of the UMLStereotype object
    * 
    * @return The name value
    */
   @Override
   public String getName()
   {
      return name;
   }


   /**
    * <pre>
    *              +-----------+ 0..n                      1
    * UMLIncrement | getName() |----------------------------- UMLStereotype
    *              +-----------+ increments      stereotypes
    * </pre>
    */
   private FPropHashSet increments;


   /**
    * Mark the value with this stereotype
    * 
    * @param value The object that is marked
    * @return true if object was not already marked
    */
   public boolean addToIncrements(FIncrement value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.increments == null)
         {
            this.increments = new FPropHashSet(this, INCREMENTS_PROPERTY);
         }
         changed = this.increments.add(value);
         if (changed)
         {
            value.addToStereotypes(this);
         }
      }
      return changed;
   }


   /**
    * Check if increment has this stereotype.
    * 
    * @param value increment of interest
    * @return if value is marked with this stereotype
    */
   public boolean hasInIncrements(FIncrement value)
   {
      return ((this.increments != null) && (value != null) && this.increments
            .contains(value));
   }


   /**
    * List all increments that have this stereotype.
    * 
    * @return iterator through UMLIncrements
    */
   public Iterator iteratorOfIncrements()
   {
      return ((this.increments == null) ? FEmptyIterator.get()
            : this.increments.iterator());
   }


   /**
    * remove this stereotype from all increments.
    */
   public void removeAllFromIncrements()
   {
      UMLIncrement tmpValue;
      Iterator iter = this.iteratorOfIncrements();
      while (iter.hasNext())
      {
         tmpValue = (UMLIncrement) iter.next();
         this.removeFromIncrements(tmpValue);
      }
   }


   /**
    * Unmark the value.
    * 
    * @param value increment that will no longer be of this stereotype
    * @return true if increment had this stereotype
    */
   public boolean removeFromIncrements(FIncrement value)
   {
      boolean changed = false;
      if ((this.increments != null) && (value != null))
      {
         changed = this.increments.remove(value);
         if (changed)
         {
            value.removeFromStereotypes(this);
         }
      }
      return changed;
   }


   /**
    * number of increments of this stereotype.
    * 
    * @return count of increments
    */
   public int sizeOfIncrements()
   {
      return ((this.increments == null) ? 0 : this.increments.size());
   }


   /**
    * Access method for an one to n association. Just for loading old project files
    * 
    * @param value The object added.
    * @return No description provided
    * @deprecated (gets deleted in 5.1) use increments association instead
    */
   public boolean addToUMLClass(UMLClass value)
   {
      return addToIncrements(value);
   }


   /**
    * @return short string representation of current object
    */
   @Override
   public String toString()
   {
      return getName();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element;
    */
   @Override
   public FElement getParentElement()
   {
      // todo: this is a top level object - should it get a package?
      return getProject();
   }


   /**
    * delete this stereotype
    */
   @Override
   public void removeYou()
   {
      removeAllFromIncrements();
      super.removeYou();
   }

   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      return getName();
   }
}
