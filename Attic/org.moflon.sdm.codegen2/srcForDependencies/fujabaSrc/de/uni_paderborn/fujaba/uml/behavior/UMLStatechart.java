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
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.common.UMLDiagram;
import de.uni_paderborn.fujaba.uml.structure.UMLClass;

import java.util.Iterator;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLStatechart extends UMLActivityDiagram
{
   public static final String REV_CONTAINS_PROPERTY = "revContains";

   /**
    *Constructor for class UMLStatechart
    *
    * @param project     No description provided
    * @param persistent  No description provided
    */
   protected UMLStatechart (FProject project, boolean persistent)
   {
      super (project, persistent);
   }

   private UMLClass owner;
   
   public UMLClass getOwner()
   {
      return this.owner;
   }
   
   public void setOwner(UMLClass newOwner)
   {
      if (this.owner != newOwner)
      {
         // newPartner
         UMLClass oldOwner = this.owner;
         if (this.owner != null)
         {
            // inform old partner
            this.owner = null;

            oldOwner.setStatechart(null);
         }

         this.owner = newOwner;
         if (newOwner != null)
         {
            // inform new partner
            newOwner.setStatechart(this);
         }
         firePropertyChange("owner", oldOwner, newOwner);
      }
   }


   /**
    * Get the statechart attribute of the UMLStatechart object
    *
    * @return   The statechart value
    */
   @Override
   public final boolean isStatechart()
   {
      return true;
   }
   
   


   @Override
	public FCodeStyle getInheritedCodeStyle()
	{
      final FCodeStyle codeStyle = getCodeStyle();
      if (codeStyle!=null)
      {
         return codeStyle;
      }
      final FElement parentElement = getOwner();
      if (parentElement!=null)
      {
         return parentElement.getInheritedCodeStyle();
      }
      return null;
	}

	/**
    * Return the initial state of this statechart.
    *
    * @return   The initialState value
    */
   public UMLComplexState getInitialState()
   {
      UMLComplexState result = null;
      UMLStartActivity start = getStartActivity();
      if (start != null)
      {
         Iterator iter = start.iteratorOfExit();
         if (iter.hasNext())
         {
            result = (UMLComplexState) ((UMLTransition) iter.next()).getRevEntry();
         }
      }
      return result;
   }


   /**
    * getter for field spawnOwnThread
    *
    * @return   current value of field spawnOwnThread
    */
   public boolean isSpawningOwnThread()
   {
      return this.spawningOwnThread;
   }


   /**
    * store the value for field spawnOwnThread
    */
   private boolean spawningOwnThread = true;


   /**
    * setter for field spawnOwnThread
    *
    * @param value  new value
    */
   public void setSpawningOwnThread (final boolean value)
   {
      final boolean oldValue = this.spawningOwnThread;
      if (oldValue != value)
      {
         this.spawningOwnThread = value;
         firePropertyChange ("spawningOwnThread", oldValue, value);
      }
   }

   @Override
   public FElement getParentElement()
   {
      UMLComplexState superState = getRevContains();
      if ( superState != null ) return superState;
      return super.getParentElement();
   }

   /**
    * <pre>
    *                  0..1      Contains      n
    * UMLComplexState --------------------------- UMLActivityDiagram
    *                  revContains      contains
    * </pre>
    */
   @Property(name=REV_CONTAINS_PROPERTY, partner=UMLComplexState.CONTAINS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   private UMLComplexState revContains = null;


   /**
    * Sets the revContains attribute of the UMLActivityDiagram object
    *
    * @param elem  The new revContains value
    */
   public void setRevContains (UMLComplexState elem)
   {
      if ( (this.revContains == null && elem != null) ||
          (this.revContains != null && !this.revContains.equals (elem)))
      {
         UMLComplexState oldRevContains = this.revContains;
         // newPartner
         if (this.revContains != null)
         {
            // inform old partner
            this.revContains = null;
            oldRevContains.removeFromContains (this);
         }

         this.revContains = elem;
         if (elem != null)
         {
            // inform new partner
            elem.addToContains (this);
         }

         firePropertyChange (REV_CONTAINS_PROPERTY, oldRevContains, elem);
      }
   }


   /**
    * Get the revContains attribute of the UMLActivityDiagram object
    *
    * @return   The revContains value
    */
   public UMLComplexState getRevContains()
   {
      return this.revContains;
   } // getRevContains

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int priority;


   /**
    * Get the value of priority.
    *
    * @return   Value of priority.
    */
   public int getPriority()
   {
      return priority;
   } // getPriority


   /**
    * Set the value of priority.
    *
    * @param v  Value to assign to priority.
    */
   public void setPriority (int v)
   {
      if (this.priority != v)
      {
         int oldValue = this.priority;
         // ugly hack to let the UMLActivityDiagramPriorityComparator work
         UMLComplexState revContains = this.getRevContains();
         if (revContains != null)
         {
            revContains.removeFromContains (this);
            this.priority = v;
            revContains.addToContains (this);
         }
         this.priority = v;
         firePropertyChange ("priority", oldValue, v);
      }
   } // setPriority

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean belongsToState()
   {
      return this.belongsToMasterState() || this.belongsToSubState();
   } // belongsToState


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean belongsToMasterState()
   {
      // is this the master-state?
      UMLStartActivity startActivity = getStartActivity();
      if (startActivity != null)
      {
         if (startActivity.getRevStartOfStateChart() != null)
         {
            return true;
         }
      }

      return false;
   } // belongsToMasterState


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean belongsToSubState()
   {
      // is there a corresponding UMLComplexState?
      if (this.getRevContains() != null)
      {
         return true;
      }

      return false;
   } // belongsToSubState


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean belongsToSimpleState()
   {
      if (!this.belongsToState())
      {
         return false;
      }

      // the state is a simple one if the activity is empty
      return  (sizeOfElements() == 0);
   } // belongsToSimpleState


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean belongsToStateWithSubStates()
   {
      if (!this.belongsToState())
      {
         return false;
      }

      Iterator iter = iteratorOfElements();
      while (iter.hasNext())
      {
         if (iter.next() instanceof UMLComplexState)
         {
            return true;
         }
      }

      return false;
   } // belongsToStateWithSubStates


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public UMLStatechart findActivityDiagramOfMasterState()
   {
      UMLStartActivity startActivity = this.getStartActivity();
      if (startActivity != null)
      {
         if (this.belongsToMasterState())
         {
            // this one is the master
            return this;
         }
      }

      UMLStatechart activityDiag = null;
      UMLStatechart tmpDiag = null;

      UMLComplexState state = getRevContains();
      UMLDiagram diag = null;

      if (state != null)
      {
         diag = state.getFirstFromDiagrams();
      }

      if (diag != null &&
         diag instanceof UMLStatechart)
      {
         tmpDiag = (UMLStatechart) diag;
         activityDiag = tmpDiag.findActivityDiagramOfMasterState();
      }

      return activityDiag;
   } // findActivityDiagramOfMasterState

   
   @Override
   public void removeYou()
   {
      setRevContains (null);

      super.removeYou();
   } // removeYou
}

/*
 * $Log$
 * Revision 1.6  2006/12/01 13:02:11  l3_g5
 * improved support for non persistent object creation
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
 * Revision 1.3  2006/03/01 12:22:49  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */
