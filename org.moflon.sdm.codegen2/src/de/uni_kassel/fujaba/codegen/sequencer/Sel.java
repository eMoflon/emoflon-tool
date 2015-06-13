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

import de.uni_paderborn.fujaba.basic.FujabaDebug;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class Sel extends Flow
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private FlowActivity flowActivity;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLTransition thenUMLTransition;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private Seq thenSeq;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLTransition elseUMLTransition;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private Seq elseSeq;


   /**
    * Constructor for class Sel
    */
   public Sel() { }


   /**
    * @param flowActivity            first flowActivity in the selection
    * @param thenSeq                 Then part of the selection; may be any Seq
    * @param elseSeq                 Else part of the selection; may be any Seq or null
    * @param thenUMLTransition
    * @param elseUMLTransition
    */
   public Sel (FlowActivity flowActivity, Seq thenSeq, UMLTransition thenUMLTransition, Seq elseSeq, UMLTransition elseUMLTransition)
   {
      addToChildren (flowActivity);
      addToChildren (thenSeq);
      thenSeq.setContext ("then");
      if (elseSeq != null)
      {
         addToChildren (elseSeq);
         elseSeq.setContext ("else");
      }
      this.flowActivity = flowActivity;
      this.thenSeq = thenSeq;
      this.thenUMLTransition = thenUMLTransition;
      this.elseSeq = elseSeq;
      this.elseUMLTransition = elseUMLTransition;
   } // Sel


   /**
    * Get the thenSeq attribute of the Sel object
    *
    * @return   The thenSeq value
    */
   public Seq getThenSeq()
   {
      return thenSeq;
   }


   /**
    * Get the thenUMLTransitionGuard attribute of the Sel object
    *
    * @return   The thenUMLTransitionGuard value
    */
   public UMLTransition getThenUMLTransition()
   {
      return thenUMLTransition;
   }


   /**
    * Get the elseUMLTransitionGuard attribute of the Sel object
    *
    * @return   The elseUMLTransitionGuard value
    */
   public UMLTransition getElseUMLTransition()
   {
      return elseUMLTransition;
   }

   //To make Velocity happy
   public static int getGuardType (UMLTransition transition)
   {
      return UMLTransitionGuard.getGuardType (transition);
   }


   /**
    * Get the elseSeq attribute of the Sel object
    *
    * @return   The elseSeq value
    */
   public Seq getElseSeq()
   {
      return elseSeq;
   }


   /**
    * Get the flowActivity attribute of the Sel object
    *
    * @return   The flowActivity value
    */
   public FlowActivity getFlowActivity()
   {
      return flowActivity;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param flowActivity  No description provided
    */
   public void removeLastFlowActivity (FlowActivity flowActivity)
   {
      System.out.println ("Error: removeLastFlowActivity called on SEL");
      FujabaDebug.printStackTrace (10);
      if (elseSeq != null)
      {
         elseSeq.removeLastFlowActivity (flowActivity);
      }
      if (thenSeq != null)
      {
         thenSeq.removeLastFlowActivity (flowActivity);
      }
   } // removeLastFlowActivity

}

/*
 * $Log$
 * Revision 1.5  2006/06/07 12:25:52  creckord
 * - Support for null TransitionGuards
 * - various bug fixes
 *
 */
