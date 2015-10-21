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
package de.uni_paderborn.fujaba.basic;

import java.util.Comparator;

import de.uni_paderborn.fujaba.uml.behavior.UMLObject;


/**
 * The ObjectComparator compares two UMLObjects for the use in TreeSets etc.
 *
 * @author    $Author$
 * @version   $Revision$
 */

public class ObjectComparator implements Comparator
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param o1  No description provided
    * @param o2  No description provided
    * @return    No description provided
    */
   public int compare (Object o1, Object o2)
   {
      if (! (o1 instanceof UMLObject))
      {
         throw new ClassCastException ("o1 is not an UMLObject!");
      }
      if (! (o2 instanceof UMLObject))
      {
         throw new ClassCastException ("o2 is not an UMLObject!");
      }

      UMLObject obj1 = (UMLObject) o1;
      UMLObject obj2 = (UMLObject) o2;

      return obj1.getObjectName().compareTo (obj2.getObjectName());
   }

}

/*
 * $Log$
 * Revision 1.13  2005/08/24 09:19:27  koenigs
 * Introduced new structure package for all UML Class Diagram related classes.
 * Introduced new behavior package for all UML Activity Diagram related classes.
 * Introduced new common package for all remaining UML classes. [ak]
 *
 */
