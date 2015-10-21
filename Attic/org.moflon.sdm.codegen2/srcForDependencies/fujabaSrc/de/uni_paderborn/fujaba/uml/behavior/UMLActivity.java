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
package de.uni_paderborn.fujaba.uml.behavior;

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashSet;

import java.util.Collection;
import java.util.Iterator;


/**
 * <h2>Associations</h2>
 * <p/>
 * <pre>
 *              0..1             N
 * UMLActivity -------------------- UMLTransition
 *              revEntry     entry
 * <p/>
 *              0..1             N
 * UMLActivity -------------------- UMLTransition
 *              revExit       exit
 * <p/>
 *              0..1                         0..1
 * UMLActivity ----------------------------------- FlowActivity
 *              + UMLActivity      + FlowActivity
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public abstract class UMLActivity extends UMLDiagramItem
{
   public static final String EXIT_PROPERTY = "exit";
   public static final String ENTRY_PROPERTY = "entry";

   /**
    * Constructor for class UMLActivity
    *
    * @param project
    * @param persistent
    */
   protected UMLActivity (FProject project, boolean persistent)
   {
      super (project, persistent);
   } // UMLActivity


   /**
    * Returns either the activity diagram in which this activity is contained or zero. But
    * then an exception is thrown outside this class.
    *
    * @return   the UMLActivityDiagram
    */
   public UMLActivityDiagram getActivityDiagram()
   {
      Iterator diagsIter = iteratorOfDiagrams();
      return  (diagsIter.hasNext()) ?  ((UMLActivityDiagram) diagsIter.next()) : null;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name;


   /**
    * Get the name attribute of the UMLDiagram object
    *
    * @return   The name value
    */
   @Override
   public String getName()
   {
      return name;
   }


   /**
    * Sets the name attribute of the UMLDiagram object
    *
    * @param name  The new name value
    */
   @Override
   public void setName (String name)
   {
      if ( (this.name == null && name != null) ||
          (this.name != null && !this.name.equals (name)))
      {
         String oldValue = this.name;
         this.name = name;
         firePropertyChange (NAME_PROPERTY, oldValue, name);
      }
   }


   /**
    * <pre>
    *              0..1             N
    * UMLActivity -------------------- UMLTransition
    *              revEntry     entry
    * </pre>
    */
   @Property(name=ENTRY_PROPERTY, partner=UMLTransition.REV_ENTRY_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   private FPropHashSet entry;


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToEntry (UMLTransition value)
   {
      boolean changed = false;

      if (value != null && !hasInEntry (value))
      {
         if (this.entry == null)
         {
            this.entry = new FPropHashSet (this, ENTRY_PROPERTY);
         }
         this.entry.add (value);
         value.setRevEntry (this);
         changed = true;
      }

      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInEntry (UMLTransition value)
   {
      return  ( (this.entry != null) &&
          (value != null) &&
         this.entry.contains (value));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfEntry()
   {
      return  ( (this.entry == null)
         ? FEmptyIterator.get()
         : this.entry.iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfEntry()
   {
      return  ( (this.entry == null)
         ? 0
         : this.entry.size());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromEntry (UMLTransition value)
   {
      boolean changed = false;

      if (value != null && hasInEntry (value))
      {
         this.entry.remove (value);
         value.setRevEntry (null);
         changed = true;
      }

      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromEntry()
   {
      UMLTransition tmpValue;
      Iterator iter = this.iteratorOfEntry();

      while (iter.hasNext())
      {
         tmpValue = (UMLTransition) iter.next();
         this.removeFromEntry (tmpValue);
      }
   }


   /**
    * <pre>
    *              0..1             N
    * UMLActivity -------------------- UMLTransition
    *              revExit       exit
    * </pre>
    */
   @Property(name=EXIT_PROPERTY, partner=UMLTransition.REV_EXIT_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   private FPropHashSet exit = null;


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToExit (UMLTransition value)
   {
      boolean changed = false;

      if (value != null && !hasInExit (value))
      {
         if (this.exit == null)
         {
            this.exit = new FPropHashSet (this, EXIT_PROPERTY);
         }
         this.exit.add (value);
         value.setRevExit (this);
         changed = true;
      }

      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInExit (UMLTransition value)
   {
      return  ( (this.exit != null) &&
          (value != null) &&
         this.exit.contains (value));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfExit()
   {
      return  ( (this.exit == null)
         ? FEmptyIterator.get()
         : this.exit.iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfExit()
   {
      return  ( (this.exit == null)
         ? 0
         : this.exit.size());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromExit (UMLTransition value)
   {
      boolean changed = false;

      if (value != null && hasInExit (value))
      {
         this.exit.remove (value);
         value.setRevExit (null);
         changed = true;
      }

      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromExit()
   {
      UMLTransition tmpValue;
      Iterator iter = this.iteratorOfExit();

      while (iter.hasNext())
      {
         tmpValue = (UMLTransition) iter.next();
         this.removeFromExit (tmpValue);
      }
   }

   // ######################################################################
   public static final String REV_STORY_PROPERTY = "revStory";

   /**
    * 1 story 0..1 UMLStoryActivity --------------------- UMLComplexState + +
    */
   @Property(name=REV_STORY_PROPERTY, partner=UMLComplexState.ACTIVITY_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   private UMLComplexState revStory; // reverse attribute story


   /**
    * Get the revStory attribute of the UMLStoryActivity object
    *
    * @return   The revStory value
    */
   public UMLComplexState getRevStory()
   {
      return revStory;
   } // getRevStory


   /**
    * Sets the revStory attribute of the UMLStoryActivity object
    *
    * @param revStory  The new revStory value
    */
   public void setRevStory (UMLComplexState revStory)
   {
      if (this.revStory != revStory)
      {
         UMLComplexState oldValue = this.revStory;
         if (this.revStory != null)
         {
            this.revStory = null;
            oldValue.setActivity (null);
         }
         this.revStory = revStory;
         if (revStory != null)
         {
            revStory.setActivity (this);
         }
         firePropertyChange (REV_STORY_PROPERTY, oldValue, revStory);
      }
   } // setRevStory


   @Override
   public FElement getParentElement()
   {
      UMLComplexState state = getRevStory();
      if (state != null)
      {
         // return state if embedded in statechart
         return state;
      }
      return super.getParentElement();
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      UMLTransition transition;

      Iterator iter = iteratorOfEntry();
      while (iter.hasNext())
      {
         transition = (UMLTransition) iter.next();
         removeFromEntry (transition);
         transition.removeYou();
      }

      iter = iteratorOfExit();
      while (iter.hasNext())
      {
         transition = (UMLTransition) iter.next();
         removeFromExit (transition);
         transition.removeYou();
      }

      setRevStory(null);
      
      super.removeYou();
   }

   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      if ( context != null && context.contains(this) )
      {
         return "activity";
      }
      else if (name != null && name.length() > 0)
      {
         return name;
      }
      else
      {
         throw new UnsupportedOperationException("activities can only be referenced as context or name attribute should be set");
      }
   }
}

/*
 * $Log$
 * Revision 1.6  2007/02/16 10:27:23  cschneid
 * tests fixed, several wrong/suprtfluous transient markers removed
 *
 * Revision 1.5  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.4  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.3  2006/03/01 12:22:47  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */
