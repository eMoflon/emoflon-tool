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
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.uml.common.UMLDiagram;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.sdm.Path;

import java.util.Iterator;


/**
 * <h2>Associations</h2>
 *
 * <pre>
 *                  0..1      contains      n
 * UMLComplexState --------------------------- UMLActivityDiagram
 *                  revContains      contains
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLActivityDiagram extends UMLDiagram
{
   /**
    * @param project
    * @param persistent
    */
   protected UMLActivityDiagram (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * Sets the name of the diagram.
    *
    * @param name  the new name
    */
   @Override
   public void setName (String name)
   {
      //method is here to match getName() method (feature abstract module requirement)
      super.setName (name);
   }


   /**
    * Get the name attribute of the UMLDiagram object
    *
    * @return   The name of the associated method if any, super.getName() else
    */
   @Override
   public String getName()
   {
      if (getStoryMethod() != null )
      {
         return getStoryMethod().getFullMethodName();
      }
      else
      {
         return super.getName();
      }
   }


   /**
    * Get the statechart attribute of the UMLActivityDiagram object
    *
    * @return   The statechart value
    */
   public boolean isStatechart()
   {
      UMLStartActivity start = getStartActivity();
      if (start != null)
      {
         return  (start.getSpecClass() != null && start.getSpec() == null);
      }
      return false;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String STORY_METHOD_PROPERTY = "storyMethod";

   /**
    * reverse UMLActivityStart revSpec
    */
   @Property(name=STORY_METHOD_PROPERTY, partner=FMethod.STORY_DIAG_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   private FMethod storyMethod;


   /**
    * Get the spec attribute of the UMLStartActivity object
    *
    * @return   The spec value
    */
   public FMethod getStoryMethod()
   {
      return storyMethod;
   } // getSpec


   /**
    * Sets the spec attribute of the UMLStartActivity object
    *
    * @param storyMethod  The new storyMethod value
    */
   public void setStoryMethod (FMethod storyMethod)
   {
      if ( (this.storyMethod == null && storyMethod != null) ||
          (this.storyMethod != null && !this.storyMethod.equals (storyMethod)))
      {
         // new partner
         FMethod oldStoryMethod = this.storyMethod;
         if (this.storyMethod != null)
         {
            // inform old partner
            this.storyMethod = null;
            oldStoryMethod.setStoryDiag (null);
         }
         this.storyMethod = storyMethod;
         if (storyMethod != null)
         {
            // inform new partner
            storyMethod.setStoryDiag (this);
         }
         firePropertyChange (STORY_METHOD_PROPERTY, oldStoryMethod, storyMethod);
      }
   } // setSpec

   public static final String PROPERTY_KIND = "kind";

   public static final int STORY_DIAG = 0;
   public static final int STORY_BOARD = 1;
   public static final int SCENARIO = 2;
   public static final int GRAPH = 3;

   private int kind = STORY_DIAG;

   public int getKind()
   {
      return kind;
   }


   public void setKind(int kind)
   {
      if (this.kind != kind)
      {
         int oldKind = this.kind;
         this.kind = kind;
         firePropertyChange(PROPERTY_KIND, oldKind, kind);
      }
       
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isStoryBoard = false;


   /**
    * Get the isStoryBoard attribute of the UMLActivityDiagram object
    *
    * @return   The isStoryBoard value
    */
   public boolean getIsStoryBoard()
   {
      return this.isStoryBoard;
   }

   /**
    * Property name for firing property change events of field isStoryBoard.
    */
   public static final String PROPERTY_IS_STORY_BOARD = "isStoryBoard";

   /**
    * setter for field isStoryBoard
    *
    * @param value new value
    */
   public void setIsStoryBoard(final boolean value)
   {
      final boolean oldValue = this.isStoryBoard;
      if (oldValue != value)
      {
         this.isStoryBoard = value;
         firePropertyChange(PROPERTY_IS_STORY_BOARD, oldValue, value);
      }
   }


   /**
    * Returns an iterator containing all objects in the diagram. If there are no UMLActivityStory's
    * in the diagram an empty iterator is returned.
    *
    * @return   iterator of all objects
    */
   public Iterator<UMLObject> iteratorOfObjects()
   {
      Iterator<UMLObject> tmpIter = FEmptyIterator.get();
      Iterator itemsIter = iteratorOfElements();
      while (itemsIter.hasNext())
      {
         ASGElement tmpItem = (ASGElement) itemsIter.next();

         if (tmpItem instanceof UMLStoryActivity)
         {
            UMLStoryPattern pattern =  ((UMLStoryActivity) tmpItem).getStoryPattern();
            if (pattern != null)
            {
               tmpIter = Path.iterUnion (tmpIter, pattern.iteratorOfObjects());
            }
         }

         if (tmpItem instanceof UMLComplexState)
         {
            UMLComplexState complexState = (UMLComplexState) tmpItem;
            UMLActivity activity = complexState.getActivity();
            if (activity != null && activity instanceof UMLStoryActivity)
            {
               UMLStoryPattern storyPattern = ((UMLStoryActivity) activity).getStoryPattern();

               if (storyPattern != null)
               {
                  tmpIter = Path.iterUnion (tmpIter, storyPattern.iteratorOfObjects());
               }
            }
            else
            {
               UMLActivityDiagram activityDiagram = complexState.getFirstFromContains();
               if (activityDiagram != null)
               {
                  tmpIter = Path.iterUnion (tmpIter, activityDiagram.iteratorOfObjects());
               }
            }
         }
      }

      return tmpIter;
   } // iteratorOfObjects

   /**
    * Returns the start activity of this diagram
    *
    * @return   The startActivity value
    */
   public UMLStartActivity getStartActivity()
   {
      UMLStartActivity theStartActivity = null;

      Iterator iter = iteratorOfElements();
      while (iter.hasNext() && theStartActivity == null)
      {
         ASGElement tmpItem = (ASGElement) iter.next();
         if (tmpItem instanceof UMLStartActivity)
         {
            theStartActivity = (UMLStartActivity) tmpItem;
         }
      }

      return theStartActivity;
   } // getStartActivity


   /**
    * Returns the full name like class::method
    *
    * @return   The fullName value
    */
   public String getFullName()
   {
      String text = null;
      FClass parent = null;
      FMethod method = null;
      UMLStartActivity activity = getStartActivity();

      if (activity != null)
      {
         method = activity.getSpec();
      }

      if (method != null)
      {
         parent = method.getParent();
      }

      if (parent != null &&
         parent.getName() != null &&
         parent.getName().length() > 0)
      {
         text = parent.getName();
      }
      else
      {
         text = "?";
      }
      return text + "::" + this;
   } // getFullName


   /**
    * Returns a correct name for the tree.
    *
    * @return   No description provided
    */
   @Override
   public String toString()
   {
      String text = null;
      FMethod method = null;
      UMLStartActivity activity = getStartActivity();

      if (activity != null)
      {
         method = activity.getSpec();
      }

      if (method != null &&
         method.getName() != null &&
         method.getName().length() > 0)
      {
         text = method.getName();
      }
      else
      {
         text = getName();
      }

      return text;
   } // toString


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      // A ActivityDiagram should aggregate all it's items, so
      // if the diagram is removed, remove also the items.
      Iterator iter = iteratorOfElements();
      while (iter.hasNext())
      {
         // but not nodes of the Java-ASG, they are removed by the method
         ASGElement item = (ASGElement) iter.next();
         item.removeYou();
      }

      setStoryMethod(null);

      super.removeYou();
   } // removeYou


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    *
    * @return   the logical parent of this element;
    */
   @Override
   public FElement getParentElement()
   {
      if (getStoryMethod() != null)
      {
         //is method spec
         return getStoryMethod();
      } else
      {
         UMLStartActivity startActivity = getStartActivity();
         if (startActivity != null)
         {
            UMLActivityDiagram activityDiagram = startActivity.getActivityDiagram();
            if (activityDiagram != null && activityDiagram.getStoryMethod() != null)
            {
               //is method spec
               return activityDiagram.getStoryMethod();
            } else if (startActivity.getRevStartOfStateChart() != null)
            {
               //is statechart
               return startActivity.getRevStartOfStateChart();
            }
         }
      }
      return super.getParentElement();
   }

}

/*
 * $Log: UMLActivityDiagram.java,v $
 * Revision 1.22  2007/04/24 09:05:50  cschneid
 * deprecation corrected, comment added; color convenience methods added to preferences; some fixes for uml object dialog; use new preferences in activity color updater; some fixed NPEs
 *
 * Revision 1.21  2007/02/16 10:27:23  cschneid
 * tests fixed, several wrong/suprtfluous transient markers removed
 *
 * Revision 1.20  2006/07/20 11:01:56  cschneid
 * deprecated getSpec etc., save as bug fixed
 *
 * Revision 1.19  2006/06/19 12:35:12  l3_g5
 * remove method link in removeYou
 *
 * Revision 1.18  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.17  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.16  2006/03/01 12:22:47  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */
