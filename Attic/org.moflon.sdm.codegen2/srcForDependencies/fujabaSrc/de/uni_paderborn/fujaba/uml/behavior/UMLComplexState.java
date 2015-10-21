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
import de.uni_paderborn.fujaba.basic.ObjectComparator;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.pcs.CollectionChangeEvent;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * <h2>Associations</h2> <pre>
 *                     n      contains      0..1
 * UMLActivityDiagram --------------------------- UMLComplexState
 *                     contains      revContains
 *
 *                  0..1   story      1
 * UMLComplexState --------------------- UMLStoryActivity
 *                  story      revStory
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLComplexState extends UMLActivity
{

   /**
    * Constructor for class UMLComplexState
    *
    * @param project
    * @param persistent
    */
   protected UMLComplexState (FProject project, boolean persistent)
   {
      super (project, persistent);

      this.deferredEvents = new FHashSet();
   } // UMLComplexState

   public final static String CONTAINS_PROPERTY = "contains";

   /**
    * <pre>
    *                     n      contains      0..1
    * UMLActivityDiagram --------------------------- UMLComplexState
    *                     contains      revContains
    * </pre>
    */
   @Property(name=CONTAINS_PROPERTY, partner=UMLStatechart.REV_CONTAINS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   private TreeSet contains = new TreeSet (new UMLActivityDiagramPriorityComparator());


   /**
    * Access method for an one to n association.
    *
    * @param elem  The object added.
    */
   public void addToContains (UMLStatechart elem)
   {
      if ( (elem != null) && !this.hasInContains (elem))
      {
         if (this.contains == null)
         {
            // Create a new container for the contains association.
            this.contains = new TreeSet (new UMLActivityDiagramPriorityComparator());
         }
         this.contains.add (elem);
         elem.setRevContains (this);
         firePropertyChange (CollectionChangeEvent.get (this, "contains", this.contains, null,
            elem, CollectionChangeEvent.ADDED));
      }
   } // addToContains


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param elem  No description provided
    * @return      No description provided
    */
   public boolean hasInContains (UMLStatechart elem)
   {
      if (this.contains == null)
      {
         return false;
      }
      else
      {
         return this.contains.contains (elem);
      }
   } // hasInContains


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfContains()
   {
      return  ( (this.contains == null)
         ? FEmptyIterator.get()
         : this.contains.iterator());
   } // iteratorOfContains


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param elem  No description provided
    */
   public void removeFromContains (UMLStatechart elem)
   {
      if (this.hasInContains (elem))
      {
         this.contains.remove (elem);
         elem.setRevContains (null);
         firePropertyChange (CollectionChangeEvent.get (this, "contains", this.contains, elem,
            null, CollectionChangeEvent.REMOVED));
      }
   } // removeFromContains


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfContains()
   {
      if (this.contains == null)
      {
         return 0;
      }
      else
      {
         return this.contains.size();
      }
   } // sizeOfContains


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromContains()
   {
      UMLStatechart tmpUMLActivityDiagram;
      Iterator iter = iteratorOfContains();

      while (iter.hasNext())
      {
         tmpUMLActivityDiagram = (UMLStatechart) iter.next();
         tmpUMLActivityDiagram.setRevContains (null);
         firePropertyChange (CollectionChangeEvent.get (this, "contains", this.contains, tmpUMLActivityDiagram,
            null, CollectionChangeEvent.REMOVED));
      }
   } // removeAllFromContains


   /**
    * Get the firstFromContains attribute of the UMLComplexState object
    *
    * @return   The firstFromContains value
    */
   public UMLStatechart getFirstFromContains()
   {
      return this.contains.isEmpty() ? null : (UMLStatechart) this.contains.first();
   } // getFirstFromContains


   /**
    * Get the lastFromContains attribute of the UMLComplexState object
    *
    * @return   The lastFromContains value
    */
   public UMLStatechart getLastFromContains()
   {
      return this.contains.isEmpty() ? null : (UMLStatechart) this.contains.last();
   } // getLastFromContains

   
   public static final String ACTIVITY_PROPERTY = "activity";
   
   /**
    * <pre>
    *                  0..1   story      1
    * UMLComplexState --------------------- UMLActivity
    *                  activity      revStory
    * </pre>
    */
   @Property(name=ACTIVITY_PROPERTY, partner=UMLActivity.REV_STORY_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   private UMLActivity activity;


   /**
    * Get the story attribute of the UMLComplexState object
    *
    * @return   The story value
    */
   public UMLActivity getActivity()
   {
      return activity;
   } // getStory


   /**
    * Sets the story attribute of the UMLComplexState object
    *
    * @param story  The new story value
    */
   public void setActivity (UMLActivity story)
   {
      if (this.activity != story)
      {
         // newPartner
         UMLActivity oldStory = this.activity;
         if (this.activity != null)
         {
            // inform old partner
            this.activity = null;

            oldStory.setRevStory (null);
         }
         this.activity = story;
         if (story != null)
         {
            // inform new partner
            story.setRevStory (this);
         }
         firePropertyChange (ACTIVITY_PROPERTY, oldStory, story);
      }
   } // setStory


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name;


   /**
    * Get the value of name.
    *
    * @return   Value of name.
    */
   @Override
   public String getName()
   {
      return name;
   } // getName


   /**
    * Set the value of name.
    *
    * @param v  Value to assign to name.
    */
   @Override
   public void setName (String v)
   {
      String oldValue = this.name;
      this.name = v;
      firePropertyChange ("name", oldValue, v);
   } // setName


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private transient TreeSet toDeclaredVariables;


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInDeclaredVariables (UMLObject value)
   {
      return  ( (this.toDeclaredVariables != null) &&  (value != null) &&
         this.toDeclaredVariables.contains (value));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfToDeclaredVariables()
   {
      return  ( (this.toDeclaredVariables == null)
         ? FEmptyIterator.get()
         : this.toDeclaredVariables.iterator());
   }


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    */
   public void addToDeclaredVariables (UMLObject value)
   {
      if (toDeclaredVariables == null)
      {
         toDeclaredVariables = new TreeSet (new ObjectComparator());
      }

      if (!hasInDeclaredVariables (value))
      {
         toDeclaredVariables.add (value);
         firePropertyChange (CollectionChangeEvent.get (this, "variables", toDeclaredVariables, null,
            value, CollectionChangeEvent.ADDED));
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromToDeclaredVariables()
   {
      if (toDeclaredVariables != null)
      {
         toDeclaredVariables.clear();
      }
   }


   /**
    * Get the text attribute of the UMLComplexState object
    *
    * @return   The text value
    */
   @Override
   public String getText()
   {
      return getName();
   } // getText


   /**
    * Sets the text attribute of the UMLComplexState object
    *
    * @param text  The new text value
    */
   public void setText (Set text)
   {
      this.setName (text.toString());
   } // setText


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String entryAction;


   /**
    * Get the value of entryAction.
    *
    * @return   Value of entryAction.
    */
   public String getEntryAction()
   {
      return entryAction;
   } // getEntryAction


   /**
    * Set the value of entryAction.
    *
    * @param v  Value to assign to entryAction.
    */
   public void setEntryAction (String v)
   {
      String oldValue = this.entryAction;
      this.entryAction = v;
      firePropertyChange ("entryAction", oldValue, v);
   } // setEntryAction


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String doAction;


   /**
    * Get the value of doAction.
    *
    * @return   Value of doAction.
    */
   public String getDoAction()
   {
      return doAction;
   } // getDoAction


   /**
    * Set the value of doAction.
    *
    * @param v  Value to assign to doAction.
    */
   public void setDoAction (String v)
   {
      String oldValue = this.doAction;
      this.doAction = v;
      firePropertyChange ("doAction", oldValue, v);
   } // setDoAction


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean hasDoAction()
   {
      boolean result = false;

      if (doAction != null)
      {
         if (!doAction.equals (""))
         {
            result = true;
         }
      }

      return result;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String exitAction;


   /**
    * Get the value of exitAction.
    *
    * @return   Value of exitAction.
    */
   public String getExitAction()
   {
      return exitAction;
   } // getExitAction


   /**
    * Set the value of exitAction.
    *
    * @param v  Value to assign to exitAction.
    */
   public void setExitAction (String v)
   {
      String oldValue = this.exitAction;
      this.exitAction = v;
      firePropertyChange ("exitAction", oldValue, v);
   } // setExitAction


   /**
    * <pre>
    *         0..n      deferredEvents       0..1
    * String <------------------------------------ UMLComplexState
    *         deferredEvents      uMLComplexState
    * </pre>
    */
   private FHashSet deferredEvents;


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfDeferredEvents()
   {
      /*
       *  id=id28993   # no Name
       */
      return  ( (this.deferredEvents == null)
         ? 0
         : this.deferredEvents.size());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromDeferredEvents (String value)
   {
      /*
       *  id=id28995   # no Name
       */
      boolean changed = false;
      if ( (this.deferredEvents != null) &&  (value != null))
      {
         changed = this.deferredEvents.remove (value);
         if (changed)
         {
            firePropertyChange (CollectionChangeEvent.get (this, "deferredEvents", this.deferredEvents, value,
               null, CollectionChangeEvent.REMOVED));
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromDeferredEvents()
   {
      /*
       *  id=id28996   # no Name
       */
      String tmpValue;
      Iterator iter = this.iteratorOfDeferredEvents();
      while (iter.hasNext())
      {
         tmpValue = (String) iter.next();
         this.removeFromDeferredEvents (tmpValue);
      }
      this.deferredEvents = null;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfDeferredEvents()
   {
      /*
       *  id=id28997   # no Name
       */
      return  ( (this.deferredEvents == null)
         ? FEmptyIterator.get()
         : this.deferredEvents.iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInDeferredEvents (String value)
   {
      /*
       *  id=id28998   # no Name
       */
      return  ( (this.deferredEvents != null) &&
          (value != null) &&
         this.deferredEvents.contains (value));
   }


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToDeferredEvents (String value)
   {
      /*
       *  id=id28999   # no Name
       */
      boolean changed = false;
      if (value != null)
      {
         if (this.deferredEvents == null)
         {
            this.deferredEvents = new FHashSet();
         }
         changed = this.deferredEvents.add (value);
         if (changed)
         {
            firePropertyChange (CollectionChangeEvent.get (this, "deferredEvents", this.deferredEvents, null,
               value, CollectionChangeEvent.ADDED));
         }
      }
      return changed;
   }


   /**
    * Get the deferredEvents attribute of the UMLComplexState object
    *
    * @return   The deferredEvents value
    */
   public FHashSet getDeferredEvents()
   {
      return deferredEvents;
   } // getDeferredEvents



   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Override
   public void removeYou()
   {
	   removeAllFromContains();
	   removeAllFromDeferredEvents();
	   removeAllFromToDeclaredVariables();
	   setActivity (null);     

		if (this.sizeOfDiagrams() == 1)
		{
			FElement parentElement = this.getParentElement();
			if (parentElement instanceof UMLStatechart)
			{
				UMLStatechart parent = (UMLStatechart) parentElement;
				parent.removeFromElements(this);
			}
		}
		
		super.removeYou();
   }

}

/*
 * $Log$
 * Revision 1.5  2006/10/13 18:37:33  fklar
 * + added constant 'CONTAINS_PROPERTY'
 *
 * Revision 1.4  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.3  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.2  2006/03/01 12:22:47  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */
