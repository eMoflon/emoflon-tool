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
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.common.UMLConnection;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;


/**
 * <h2>Associations</h2> <pre>
 *              0..1             N
 * UMLActivity -------------------- UMLTransition
 *              revEntry     entry
 * <p/>
 *              0..1             N
 * UMLActivity -------------------- UMLTransition
 *              revExit       exit
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLTransition extends UMLConnection
{
   public static final String GUARD_PROPERTY = "guard";
   public static final String REV_EXIT_PROPERTY = "revExit";
   public static final String REV_ENTRY_PROPERTY = "revEntry";


   /**
    * Constructor for class UMLTransition
    *
    * @param project
    * @param persistent
    */
   protected UMLTransition (FProject project, boolean persistent)
   {
      super (project, persistent);
   } // constructor


   /**
    * <pre>
    *              0..1             N
    * UMLActivity -------------------- UMLTransition
    *              revEntry     entry
    * </pre>
    */
   @Property(name=REV_ENTRY_PROPERTY, partner=UMLActivity.ENTRY_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.USAGE)
   private UMLActivity revEntry = null;


   /**
    * Sets the revEntry attribute of the UMLTransition object
    *
    * @param value  The new revEntry value
    * @return       No description provided
    */
   @Property(name=REV_ENTRY_PROPERTY)
   public boolean setRevEntry (UMLActivity value)
   {
      if ( (this.revEntry == null && value != null) ||
          (this.revEntry != null && !this.revEntry.equals (value)))
      {
         UMLActivity oldValue = this.revEntry;
         if (this.revEntry != null)
         {
            this.revEntry = null;
            oldValue.removeFromEntry (this);
         }
         this.revEntry = value;
         if (this.revEntry != null)
         {
            this.revEntry.addToEntry (this);
         }
         // side effects
         firePropertyChange (REV_ENTRY_PROPERTY, oldValue, value);

         return true;
      }

      return false;
   }


   /**
    * Get the revEntry attribute of the UMLTransition object
    *
    * @return   The revEntry value
    */
   @Property(name=REV_ENTRY_PROPERTY)
   public UMLActivity getRevEntry()
   {
      return this.revEntry;
   }


   /**
    * Sets the source attribute of the UMLTransition object
    *
    * @param source  The new source value
    * @return        No description provided
    */
   @NoProperty()
   public boolean setSource (UMLActivity source)
   {
      return setRevExit (source);
   }


   /**
    * Get the source attribute of the UMLTransition object
    *
    * @return   The source value
    */
   @NoProperty()
   public UMLActivity getSource()
   {
      return getRevExit();
   }


   /**
    * Sets the sourceConnector attribute of the UMLTransition object
    *
    * @param incr  The new sourceConnector value
    * @return      No description provided
    */
   @Override
   @NoProperty()
   public boolean setSourceConnector (UMLDiagramItem incr)
   {
      if (! (incr instanceof UMLActivity))
      {
         throw new IllegalArgumentException ("Argument is no UMLActivity");
      }
      return setRevExit ((UMLActivity) incr);
   }


   /**
    * Get the sourceConnector attribute of the UMLTransition object
    *
    * @return   The sourceConnector value
    */
   @Override
   @NoProperty()
   public UMLDiagramItem getSourceConnector()
   {
      return getRevExit();
   }


   /**
    * <pre>
    *              0..1             N
    * UMLActivity -------------------- UMLTransition
    *              revExit       exit
    * </pre>
    */
   @Property(name=REV_EXIT_PROPERTY, partner=UMLActivity.EXIT_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.USAGE)
   private UMLActivity revExit = null;


   /**
    * Sets the revExit attribute of the UMLTransition object
    *
    * @param value  The new revExit value
    * @return       No description provided
    */
   @Property(name=REV_EXIT_PROPERTY)
   public boolean setRevExit (UMLActivity value)
   {
      if ( (this.revExit == null && value != null) ||
          (this.revExit != null && !this.revExit.equals (value)))
      {
         UMLActivity oldValue = this.revExit;
         if (this.revExit != null)
         {
            this.revExit = null;
            oldValue.removeFromExit (this);
         }
         this.revExit = value;
         if (this.revExit != null)
         {
            this.revExit.addToExit (this);
         }

         // side effects
         firePropertyChange (REV_EXIT_PROPERTY, oldValue, value);

         return true;
      }

      return false;
   }


   /**
    * Get the revExit attribute of the UMLTransition object
    *
    * @return   The revExit value
    */
   @Property(name=REV_EXIT_PROPERTY)
   public UMLActivity getRevExit()
   {
      return this.revExit;
   }


   /**
    * Sets the target attribute of the UMLTransition object
    *
    * @param target  The new target value
    * @return        No description provided
    */
   @NoProperty()
   public boolean setTarget (UMLActivity target)
   {
      return setRevEntry (target);
   }


   /**
    * Get the target attribute of the UMLTransition object
    *
    * @return   The target value
    */
   @NoProperty()
   public UMLActivity getTarget()
   {
      return getRevEntry();
   }


   /**
    * Sets the targetConnector attribute of the UMLTransition object
    *
    * @param incr  The new targetConnector value
    * @return      No description provided
    */
   @Override
   @NoProperty()
   public boolean setTargetConnector (UMLDiagramItem incr)
   {
      if (! (incr instanceof UMLActivity))
      {
         throw new IllegalArgumentException ("Argument is no UMLActivity");
      }
      return setRevEntry ((UMLActivity) incr);
   }


   /**
    * Get the targetConnector attribute of the UMLTransition object
    *
    * @return   The targetConnector value
    */
   @Override
   @NoProperty()
   public UMLDiagramItem getTargetConnector()
   {
      return getRevEntry();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String event = null;


   /**
    * Get the value of event.
    *
    * @return   Value of event.
    */
   public String getEvent()
   {
      return event;
   } // getEvent


   /**
    * Patch getName() to return a hopefully sensible name for the transition in AOAD contract
    * analysis
    *
    * @return   Content of event (whatever this is).
    */
   @Override
   public String getName()
   {
      return event;
   }


    
   @Override
   public void setName(String newName)
   {
      setEvent(newName);
   }


   /**
    * Set the value of event.
    *
    * @param event  The new event value
    */
   public void setEvent (String event)
   {
      if ( (this.event == null && event != null) ||
          (this.event != null && !this.event.equals (event)))
      {
         String oldValue = this.event;
         this.event = event;
         firePropertyChange ("event", oldValue, this.event);
      }
   } // setEvent


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean hasEvent()
   {
      boolean result = false;

      if (event != null)
      {
         if (!event.equals (""))
         {
            result = true;
         }
      }

      return result;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String action = null;


   /**
    * Get the value of action.
    *
    * @return   Value of action.
    */
   public String getAction()
   {
      return action;
   } // getAction


   /**
    * Set the value of action.
    *
    * @param action  The new action value
    */
   public void setAction (String action)
   {
      if ( (this.action == null && action != null) ||
          (this.action != null && !this.action.equals (action)))
      {
         String oldValue = this.action;
         this.action = action;
         firePropertyChange ("action", oldValue, this.action);
      }
   } // setAction


   /**
    * Reverse UMLTransitionGuard revGuard.
    */
   @Property(name=GUARD_PROPERTY, partner=UMLTransitionGuard.REV_GUARD_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   private UMLTransitionGuard guard = null;


   /**
    * Get the guard attribute of the UMLTransition object
    *
    * @return   The guard value
    */
   public UMLTransitionGuard getGuard()
   {
      return guard;
   } // getGuard


   /**
    * Sets the guard attribute of the UMLTransition object
    *
    * @param guard  The new guard value
    */
   public void setGuard (UMLTransitionGuard guard)
   {

      if ( (this.guard == null && guard != null) ||
          (this.guard != null && !this.guard.equals (guard)))
      {
         // new partner
         UMLTransitionGuard oldGuard = this.guard;
         if (this.guard != null)
         {
            // revInform old partner
            this.guard = null;
            oldGuard.setRevGuard (null);
            oldGuard.removeYou();
         }
         this.guard = guard;
         if (this.guard != null)
         {
            // revInform new partner
            this.guard.setRevGuard (this);
         }
         firePropertyChange (GUARD_PROPERTY, oldGuard, this.guard);
      }
   } // setGuard

   @Property(name="guardExprText", derived=true)
   public String getGuardExprText()
   {
   	String result = this.getGuardExpr();
   	if (result == null)
   	{
   		result = "NONE";
   	}
   	return result;
   }
   
   public void setGuardExprText (String text)
   {
   	if ("NONE".equals(text))
   	{
   		text = null;
   	}
   	this.setGuardExpr(text);
   }

   @Property(name="guardExpr", derived=true)
   public String getGuardExpr ()
   {
	   if (this.getGuard() == null) return null;
	   
	   return this.getGuard().getBoolExpr();
   }
   
   public void setGuardExpr (String expr)
   {
	   if (this.getGuard() == null)
	   {
		   this.setGuard(this.getProject().getFromFactories(UMLTransitionGuard.class).create());
	   }
	   this.getGuard().setBoolExprString(expr);
   }
   
   
   
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private transient boolean isForwardEdge;


   /**
    * Sets the isForwardEdge attribute of the UMLTransition object
    *
    * @param isForwardEdge  The new isForwardEdge value
    */
   public void setIsForwardEdge (boolean isForwardEdge)
   {
      if (this.isForwardEdge != isForwardEdge)
      {
         boolean oldValue = this.isForwardEdge;
         this.isForwardEdge = isForwardEdge;
         firePropertyChange ("isForwardEdge", oldValue, isForwardEdge);
      }
   } // setIsForwardEdge


   /**
    * @return   TRUE - if Transition is a forward edge in the dfs tree FALSE - if Transition
    *         is a backward edge
    */
   public boolean getIsForwardEdge()
   {
      return isForwardEdge;
   } // getIsForwardEdge


   /**
    * Marker needed in Sequencer.explore()
    */
   private transient boolean isMarked = false;


   /**
    * Sets the isMarked attribute of the UMLTransition object
    *
    * @param isMarked  The new isMarked value
    */
   public void setIsMarked (boolean isMarked)
   {
      if (this.isMarked != isMarked)
      {
         boolean oldValue = this.isMarked;
         this.isMarked = isMarked;
         firePropertyChange ("isMarked", oldValue, isMarked);
      }
   } //setIsMarked


   /**
    * Get the isMarked attribute of the UMLTransition object
    *
    * @return   The isMarked value
    */
   public boolean getIsMarked()
   {
      return isMarked;
   } // getIsMarked


   /**
    * Same functionality as equals() there are maybe problems with jgl sets work if this method
    * is called equals()
    *
    * @param curTransition  No description provided
    * @return               No description provided
    */
   public boolean looksLike (UMLTransition curTransition)
   {
      final UMLTransitionGuard myGuard = this.getGuard();
      final UMLTransitionGuard curGuard = curTransition.getGuard();
      return  (myGuard == curGuard) ||  (myGuard != null && myGuard.looksLike (curGuard));
   } // looksLike


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      UMLIncrement incr = getGuard();
      if (incr != null)
      {
         setGuard (null);
         incr.removeYou();
      }

      setRevEntry (null);
      setRevExit (null);

      super.removeYou();
   } // removeYou

}

/*
 * $Log$
 * Revision 1.10  2006/09/16 20:35:42  zuendorf
 * just a quick save
 *
 * Revision 1.9  2006/08/22 22:51:34  zuendorf
 * Added FMM.create(proj, class) to shorten the creation of metamodel objects.
 * Linkeditor selects objects in correct order now.
 * Added some flags guiding the code generation for Story Patterns and objects.
 * Added some convinience to UMLTransition.
 *
 * Revision 1.8  2006/06/07 12:21:19  creckord
 * - UMLTransitionGuard can be null instead of UMLTransitionGuard.NONE
 * - old "repair assoc" code removed (access methods are not kept, so repairing them is no longer needed)
 * - loop bends for assocs should be removed when loop is removed (not triggered yet :( )
 *
 * Revision 1.7  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.6  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.5  2006/03/01 12:22:49  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */
