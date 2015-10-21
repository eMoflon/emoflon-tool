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
public interface FDiagram extends FElement, FModelRootNode
{

   // --- Property elements ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String ELEMENTS_PROPERTY = "elements";


   /**
    * @return   number of elements contained in this diagram
    */
   @Property(name=ELEMENTS_PROPERTY)
   public int sizeOfElements();


   /**
    * @param element  FElement of interest
    * @return         true when element is in elements attribute
    */
   @Property(name=ELEMENTS_PROPERTY)
   public boolean hasInElements (FElement element);


   /**
    * @return   iterator through elements (only FElement instances)
    */
   @Property(name=ELEMENTS_PROPERTY)
   public Iterator<? extends FElement> iteratorOfElements();


   /**
    * Access method for an one to n association.
    *
    * @param element  The object added.
    * @return         No description provided
    */
   @Property(name=ELEMENTS_PROPERTY, partner=FElement.DIAGRAMS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean addToElements (FElement element);


   /**
    * Access method for an one to n association.
    *
    * @param entry  The object added.
    */
   public void addToElements (java.util.Map.Entry entry);


   /**
    * remove an FElement from the elements attribute
    *
    * @param element  what to remove
    * @return         true when element was removed (had in elements)
    */
   @Property(name=ELEMENTS_PROPERTY)
   public boolean removeFromElements (FElement element);


   /**
    * clear elements attribute
    */
   @Property(name=ELEMENTS_PROPERTY)
   public void removeAllFromElements();

}

/*
 * $Log$
 * Revision 1.5  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.4  2006/05/17 17:35:11  fklar
 * using java 1.5 generics:
 * * adjusted return value of method 'iteratorOfElement' so it returns a parameterized iterator
 *
 * Revision 1.3  2005/11/02 22:48:14  rotschke
 * Removed obsolete import [tr].
 *
 */
