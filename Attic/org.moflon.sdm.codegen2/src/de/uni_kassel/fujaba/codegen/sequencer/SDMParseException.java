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

import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.common.FElement;


/**
 * SDMParseException is an exception that will be thrown when an error occurs while parsing
 * the CFG This exception is catched in UMLActivityDiagram::generateJava()
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class SDMParseException extends RuntimeExceptionWithContext
{

   /**
    * Constructs a JavaSDMException with no detail message.
    */
   public SDMParseException()
   {
      super (null);
   } // SDMParseException


   /**
    * Constructs a SDMParseException with the specified detail message.
    *
    * @param s  the detail message.
    */
   public SDMParseException (String s)
   {
      super (s, null);
   } // SDMParseException


   /**
    * Constructs a SDMParseException with the specified detail message and context.
    *
    * @param s        the detail message.
    * @param context  No description provided
    */
   public SDMParseException (String s, FElement context)
   {
      super (s, context);
   } // SDMParseException
}

/*
 * $Log$
 * Revision 1.3  2006/01/10 13:31:33  l3_g5
 * CodeGen2 now works for Fujaba5; use CodeCen2_F4 branch for Fujaba4
 *
 */
