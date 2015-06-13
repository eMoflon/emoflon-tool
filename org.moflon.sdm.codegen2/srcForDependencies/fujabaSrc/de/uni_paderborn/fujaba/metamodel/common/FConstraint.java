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
package de.uni_paderborn.fujaba.metamodel.common;

import java.util.Iterator;

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;



/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FConstraint extends FDiagramItem
{

   // --- Property increments ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String INCREMENTS_PROPERTY = "increments";


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   @Property(name=INCREMENTS_PROPERTY)
   public abstract boolean hasInIncrements (FIncrement value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Property(name=INCREMENTS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY,
         partner=FIncrement.CONSTRAINTS_PROPERTY, adornment=ReferenceHandler.Adornment.PARENT)
   public abstract Iterator<? extends FIncrement> iteratorOfIncrements();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Property(name=INCREMENTS_PROPERTY)
   public abstract int sizeOfIncrements();


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   @Property(name=INCREMENTS_PROPERTY)
   public abstract boolean addToIncrements (FIncrement value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   @Property(name=INCREMENTS_PROPERTY)
   public abstract boolean removeFromIncrements (FIncrement value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property(name=INCREMENTS_PROPERTY)
   public abstract void removeAllFromIncrements();

   // --- Property revConstraint ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String REV_CONSTRAINT_PROPERTY = "revConstraint";


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   @Property(name=REV_CONSTRAINT_PROPERTY)
   public abstract boolean hasInRevConstraint (FDiagram value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Property(name=REV_CONSTRAINT_PROPERTY, partner=FModelDiagram.CONSTRAINTS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.PARENT)
   public abstract Iterator<? extends FDiagram> iteratorOfRevConstraint();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Property(name=REV_CONSTRAINT_PROPERTY)
   public abstract int sizeOfRevConstraint();


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   @Property(name=REV_CONSTRAINT_PROPERTY)
   public abstract boolean addToRevConstraint (FDiagram value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   @Property(name=REV_CONSTRAINT_PROPERTY)
   public abstract boolean removeFromRevConstraint (FDiagram value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property(name=REV_CONSTRAINT_PROPERTY)
   public abstract void removeAllFromRevConstraint();

   // --- Property text ---
   /**
    * Sets the text attribute of the FConstraint object
    *
    * @param text  The new text value
    */
   public abstract void setText (String text);

}

/*
 * $Log$
 * Revision 1.3  2006/06/21 10:35:54  koenigs
 * Renamed removeAllIncrements and removeAllRevConstraints to removeAllFromIncrements and removeAllFromRevConstraint since Fujaba would have generated them this way. [ak]
 *
 * Revision 1.2  2006/05/18 18:21:48  fklar
 * using java 1.5 generics:
 * * adjusted return value of method 'iteratorOfElement' so it returns a parameterized iterator
 *
 * Revision 1.1  2005/08/24 09:41:18  koenigs
 * Introduced new structure package for all Class Diagram related F-interfaces.
 * Introduced new common package for all remaining F-interfaces. [ak]
 *
 */
