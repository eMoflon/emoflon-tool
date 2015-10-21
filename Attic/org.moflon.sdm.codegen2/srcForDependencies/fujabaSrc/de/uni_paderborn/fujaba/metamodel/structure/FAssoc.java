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
package de.uni_paderborn.fujaba.metamodel.structure;


import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FDiagramItem;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FAssoc extends FDiagramItem
{

   /**
    * direction = {LeftRight, RightLeft}
    */
   public final static int LEFTRIGHT = 10;

   /**
    * direction = {LeftRight, RightLeft}
    */
   public final static int RIGHTLEFT = 11;

   // --- Property direction ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String DIRECTION_PROPERTY = "direction";


   /**
    * Get the direction attribute of the FAssoc object
    *
    * @return   The direction value
    */
   @Property(name=DIRECTION_PROPERTY)
   public int getDirection();


   /**
    * Sets the direction attribute of the FAssoc object
    *
    * @param direction  The new direction value
    */
   @Property(name=DIRECTION_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setDirection (int direction);

   // --- Property leftRole ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String LEFT_ROLE_PROPERTY = "leftRole";


   /**
    * Get the leftRole attribute of the FAssoc object
    *
    * @return   The leftRole value
    */
   @Property(name=LEFT_ROLE_PROPERTY)
   public FRole getLeftRole();


   /**
    * Sets the leftRole attribute of the FAssoc object
    *
    * @param leftRole  The new leftRole value
    * @return          No description provided
    */
   @Property(name=LEFT_ROLE_PROPERTY, partner=FRole.REV_LEFT_ROLE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean setLeftRole (FRole leftRole);

   // --- Property rightRole
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String RIGHT_ROLE_PROPERTY = "rightRole";


   /**
    * Get the rightRole attribute of the FAssoc object
    *
    * @return   The rightRole value
    */
   @Property(name=RIGHT_ROLE_PROPERTY)
   public FRole getRightRole();


   /**
    * Sets the rightRole attribute of the FAssoc object
    *
    * @param rightRole  The new rightRole value
    * @return           No description provided
    */
   @Property(name=RIGHT_ROLE_PROPERTY, partner=FRole.REV_RIGHT_ROLE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean setRightRole (FRole rightRole);


   // --- Property stereotype ---
   public final static String STEREOTYPE_PROPERTY = "stereotype";

   String INSTANCES_PROPERTY = "instances";

   @Property( name = INSTANCES_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = UMLLink.INSTANCE_OF_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.ADDER )
   public void addToInstances (UMLLink link);


   @Property( name = INSTANCES_PROPERTY, accessFragment = AccessFragment.REMOVER )
   public void removeFromInstances (UMLLink link);


   /**
    * Get the sortedComparator attribute of the FAssoc object
    *
    * @return   The sortedComparator value
    */
   @Property(name=FRole.SORTED_COMPARATOR_PROPERTY)
   public String getSortedComparator();


   /**
    * Sets the sortedComparator attribute of the FAssoc object
    *
    * @param text  The new sortedComparator value
    */
   @Property(name=FRole.SORTED_COMPARATOR_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setSortedComparator (String text);

}

/*
 * $Log$
 * Revision 1.10  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.9  2006/04/25 11:58:24  cschneid
 * added deprecation expiration note, work on versioning
 *
 */
