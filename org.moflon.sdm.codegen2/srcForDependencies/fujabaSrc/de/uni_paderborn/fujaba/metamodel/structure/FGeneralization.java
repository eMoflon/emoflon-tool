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
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FDiagramItem;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FGeneralization extends FDiagramItem
{

   // --- Property subclass ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String SUBCLASS_PROPERTY = "subclass";


   /**
    * Get the subclass attribute of the FGeneralization object
    *
    * @return   The subclass value
    */
   @Property(name=SUBCLASS_PROPERTY)
   public abstract FClass getSubclass();


   /**
    * Sets the subclass attribute of the FGeneralization object
    *
    * @param subclass  The new subclass value
    * @return          No description provided
    */
   @Property(name=SUBCLASS_PROPERTY, partner=FClass.REV_SUBCLASS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   public abstract boolean setSubclass (FClass subclass);

   // --- Property superclass ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String SUPERCLASS_PROPERTY = "superclass";


   /**
    * Get the superclass attribute of the FGeneralization object
    *
    * @return   The superclass value
    */
   @Property(name=SUPERCLASS_PROPERTY)
   public abstract FClass getSuperclass();


   /**
    * Sets the superclass attribute of the FGeneralization object
    *
    * @param superclass  The new superclass value
    * @return            No description provided
    */
   @Property(name=SUPERCLASS_PROPERTY, partner=FClass.REV_SUPERCLASS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.USAGE)
   public abstract boolean setSuperclass (FClass superclass);
}

/*
 * $Log$
 * Revision 1.3  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.2  2006/01/06 15:45:26  rotschke
 * Removed FConnection interface, as it is UML-specific [tr].
 *
 */
