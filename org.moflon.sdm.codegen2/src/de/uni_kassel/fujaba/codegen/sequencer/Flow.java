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

import de.uni_kassel.fujaba.codegen.classdiag.ASGElementToken;
import de.uni_kassel.fujaba.codegen.rules.Token;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public abstract class Flow extends ASGElementToken
{
   @Override
   public boolean addToChildren(Token value)
   {
      if ((value != null) && (value.getParent() != null) && (value.getParent() != this))
      {
         System.err.println("Das darf niiiieeee passieren!!!!!!");
      }
      return super.addToChildren(value);
   }


   /**
    * Is used in the parsing algorithm. Removes exactly one activity in the flow, in Reps for
    * example it is not possible
    *
    * @param flowActivity  No description provided
    */
   public void removeLastFlowActivity (FlowActivity flowActivity)
   {
      // nothing to do in superclass
   }


   /**
    * @return   short string representation of current object
    */
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append ("Flow[]");

      return result.toString();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeYou()
   {
      super.removeYou();
   }
}

/*
 * $Log$
 * Revision 1.5  2005/12/20 12:32:03  l3_g5
 * corrected imports
 *
 * Revision 1.4  2005/12/16 16:36:41  l3_g5
 * some bugfixing
 *
 * Revision 1.3  2005/12/02 18:19:50  l3_g5
 * bootstrapping works!
 *
 * Revision 1.2  2005/08/09 16:30:45  cschneid
 * removed @Override
 *
 */
