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
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class ExceptionFlow extends Flow
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private FlowActivity flowActivity;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private Seq defaultSeq;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLTransitionGuard exceptionUMLTransitionGuard;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private Seq exceptionSeq;


   /**
    * Constructor for class Sel
    */
   public ExceptionFlow() { }


   /**
    * @param flowActivity            first flowActivity in the selection
    * @param defaultSeq                 Then part of the selection; may be any Seq
    * @param thenUMLTransitionGuard  Then transition guard of the selection
    * @param exceptionSeq                 Else part of the selection; may be any Seq or null
    * @param exceptionUMLTransitionGuard  Else transition guard of the selection
    */
   public ExceptionFlow (FlowActivity flowActivity, Seq thenSeq, Seq exceptionSeq, UMLTransitionGuard exceptionUMLTransitionGuard)
   {
      addToChildren (flowActivity);
      addToChildren (thenSeq);
      thenSeq.setContext ("default");
      addToChildren (exceptionSeq);
      exceptionSeq.setContext ("exception");
      this.flowActivity = flowActivity;
      this.defaultSeq = thenSeq;
      this.exceptionSeq = exceptionSeq;
      this.exceptionUMLTransitionGuard = exceptionUMLTransitionGuard;
   } // Sel


   /**
    * Get the defaultSeq attribute of the Sel object
    *
    * @return   The defaultSeq value
    */
   public Seq getDefaultSeq()
   {
      return defaultSeq;
   }


   /**
    * Get the exceptionUMLTransitionGuard attribute of the Sel object
    *
    * @return   The exceptionUMLTransitionGuard value
    */
   public UMLTransitionGuard getExceptionUMLTransitionGuard()
   {
      return exceptionUMLTransitionGuard;
   }


   /**
    * Get the exceptionSeq attribute of the Sel object
    *
    * @return   The exceptionSeq value
    */
   public Seq getExceptionSeq()
   {
      return exceptionSeq;
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
      if (exceptionSeq != null)
      {
         exceptionSeq.removeLastFlowActivity (flowActivity);
      }
      if (defaultSeq != null)
      {
         defaultSeq.removeLastFlowActivity (flowActivity);
      }
   } // removeLastFlowActivity

}

/*
 * $Log$
 * Revision 1.2  2006/03/24 12:55:25  l3_g5
 * applied patches from bayreuth
 *
 * Revision 1.3  2005/12/16 16:36:41  l3_g5
 * some bugfixing
 *
 */
