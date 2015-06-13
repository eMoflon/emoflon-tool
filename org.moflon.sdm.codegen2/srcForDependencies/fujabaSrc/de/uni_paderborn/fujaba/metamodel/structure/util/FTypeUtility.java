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
package de.uni_paderborn.fujaba.metamodel.structure.util;


import de.uni_paderborn.fujaba.metamodel.structure.FArray;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FType;


/**
 * Utility class for dealing with instances of FType.
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class FTypeUtility
{
   /**
    * Get the full qualified name of the specified type.
    * 
    * @param type The type to be processed.
    * @return If successful the full qualified name of this type, null otherwise.
    */
   public static String getFullQualifiedName(FType type)
   {
      if (type instanceof FClass)
      {
         return ((FClass) type).getFullClassName();
      }
      else if (type instanceof FArray)
      {
         try
         {
            return FArrayUtility.getFullQualifiedName((FArray) type);
         }
         catch (Exception e)
         {
            return null;
         }
      }
      else if (type != null)
      {
         return type.getName();
      }

      return null;
   }

}

/*
 * $Log$
 * Revision 1.4  2006/03/29 15:49:43  lowende
 * Changed creating/editing/hiding/showing of UMLCommentary.
 *
 */