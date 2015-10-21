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

import java.util.Iterator;

import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;


/*
 *  @author  $Author$
 *  @version $Revision$ $Date$
 */
/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class FRoleUtility
{

   public static boolean isToOneAccess (FRole role)
   {
      return  ( (role.getCard() != null) ?  (role.getCard().getUpperBound() == 1 && role.getPartnerRole().getQualifier() == null)
         : true);
   }

   /**
    * Check whether the given roleName is the realization of a role declared
    * in an interface implemented by the given class.
    * 
    * @param roleName The name of a role that should be looked up.
    * @param roleTarget A class thats parents should be looked up.
    * @return True if the roleName is declared in an interface implemented by the given class.
    */
   public static boolean isRealizationOfInterfaceRole (String roleName, FClass roleTarget)
   {
      if (roleName == null)
         return false;
      
      // look up parent classes of roleTarget
      // if the parent is an interface, check roles of its associations
      // if such a role has the same name, this role is its realization
      
      Iterator<? extends FClass> superClassesIter = roleTarget.iteratorOfSuperClasses();
      
      while (superClassesIter.hasNext())
      {
         FClass superClass = superClassesIter.next();
         
         if (FClassUtility.isInterface(superClass))
         {
            Iterator<? extends FRole> superClassRoleIter = superClass.iteratorOfRoles();
            while (superClassRoleIter.hasNext())
            {
               FRole possibleRoleDeclaration = superClassRoleIter.next().getPartnerRole();
               
               if (roleName.equals(possibleRoleDeclaration.getName()))
               {
                  return true;
               }
            }
         }
         
         if (isRealizationOfInterfaceRole(roleName, superClass))
            return true;
      }
      
      return false;
   }
}

/*
 * $Log$
 * Revision 1.1  2007/03/21 12:47:50  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 */
