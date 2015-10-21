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
package de.uni_kassel.fujaba.codegen.sequencer;

import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class Rep extends Flow
{
   /**
    * The body of the repetition
    */
   private Seq bodySeq;

   /**
    * The transition which leaves the repetition top/buttom controlled
    */
   private UMLTransition leavingUMLTransition;

   /**
    * The transition which runs the repetition
    */
   private UMLTransition nextUMLTransition;

   /**
    * The type of transition
    */
   private boolean type;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static boolean HEAD_CONTROLLED = true;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static boolean FOOT_CONTROLLED = false;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean headedByActivity = false;


   /**
    * @return   Returns the headedByActivity.
    */
   public boolean isHeadedByActivity()
   {
      return headedByActivity;
   }


   /**
    * @param headedByActivity  The headedByActivity to set.
    */
   public void setHeadedByActivity (boolean headedByActivity)
   {
      this.headedByActivity = headedByActivity;
   }

   //To make Velocity happy
   public static int getGuardType (UMLTransition transition)
   {
      return UMLTransitionGuard.getGuardType (transition);
   }


   /**
    * Constructor for class Rep
    */
   public Rep() { }


   /**
    * @param bodySeq               The body of the repetition
    * @param nextUMLTransition     The transition which runs the repetition
    * @param leavingUMLTransition  The transition which runs the repetition
    * @param type                  The type of transition
    */
   public Rep (Seq bodySeq, UMLTransition nextUMLTransition, UMLTransition leavingUMLTransition, boolean type)
   {
      addToChildren (bodySeq);
      this.bodySeq = bodySeq;
      this.nextUMLTransition = nextUMLTransition;
      this.leavingUMLTransition = leavingUMLTransition;
      this.type = type;
   } // Rep


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param flowActivity  No description provided
    */
   public void removeLastFlowActivity (FlowActivity flowActivity)
   {
      if (bodySeq != null)
      {
         bodySeq.removeLastFlowActivity (flowActivity);
      }
   } // removeLastFlowActivity



   /**
    * Get the bodySeq attribute of the Rep object
    *
    * @return   The bodySeq value
    */
   public Seq getBodySeq()
   {
      return bodySeq;
   }


   /**
    * Get the type attribute of the Rep object
    *
    * @return   The type value
    */
   public boolean getType()
   {
      return type;
   }


   /**
    * Get the leavingUMLTransition attribute of the Rep object
    *
    * @return   The leavingUMLTransition value
    */
   public UMLTransition getLeavingUMLTransition()
   {
      return leavingUMLTransition;
   }


   /**
    * Get the nextUMLTransition attribute of the Rep object
    *
    * @return   The nextUMLTransition value
    */
   public UMLTransition getNextUMLTransition()
   {
      return nextUMLTransition;
   }


   /**
    * @return   short string representation of current object
    */
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append ("Rep[]");

      return result.toString();
   }

}

/*
 * $Log$
 * Revision 1.4  2006/06/07 12:25:52  creckord
 * - Support for null TransitionGuards
 * - various bug fixes
 *
 */
