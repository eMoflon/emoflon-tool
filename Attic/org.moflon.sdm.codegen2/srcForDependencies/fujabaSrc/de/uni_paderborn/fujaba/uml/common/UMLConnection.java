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
package de.uni_paderborn.fujaba.uml.common;

import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_paderborn.fujaba.metamodel.common.FProject;


/**
 * UMLConnection is the base class for all DiagramItems that represent edges in Diagrams, such
 * as UMLAssoc, UMLLink ...
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public abstract class UMLConnection extends UMLDiagramItem
{

   /**
    * Constructor for class UMLConnection
    *
    * @param project
    * @param persistent
    */
   protected UMLConnection (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * access method for the source object of the edge
    *
    * @return   the source object of the edge
    */
   @NoProperty()
   public abstract UMLDiagramItem getSourceConnector();


   /**
    * access method for the source object of the edge
    *
    * @param item  The new sourceConnector value
    * @return      true if changed
    */
   @NoProperty()
   public abstract boolean setSourceConnector (UMLDiagramItem item);


   /**
    * access method for the target object of the edge
    *
    * @return   the target object of the edge
    */
   @NoProperty()
   public abstract UMLDiagramItem getTargetConnector();


   /**
    * access method for the target object of the edge
    *
    * @param item  The new targetConnector value
    * @return      true if changed
    */
   @NoProperty()
   public abstract boolean setTargetConnector (UMLDiagramItem item);



   /**
    * Gets the partner of the item<p>
    * <p/>
    * That is sourceConnector if item is targetConnector or targetConnector if the item is
    * sourceConnector<p>
    * <p/>
    * If item is none of these an Exception is thrown.
    *
    * @param item                                 No description provided
    * @return                                     the partner of the item
    * @throws java.lang.IllegalArgumentException  if item is not one of sourceConnector or
    *          targetConnector
    * @see                                        #getSourceConnector
    * @see                                        #getTargetConnector
    * @see                                        #setPartner
    */
   public UMLDiagramItem getPartner (UMLDiagramItem item)
   {
      if (item == getSourceConnector())
      {
         return getTargetConnector();
      }
      else if (item == getTargetConnector())
      {
         return getSourceConnector();
      }
      else
      {
         throw new IllegalArgumentException ("Argument must be equal to " +
            "sourceConnector or targetConnector");
      }
   }


   /**
    * Sets the partner of the item<p>
    * <p/>
    * That is sourceConnector if item is targetConnector or targetConnector if the item is
    * sourceConnector<p>
    * <p/>
    * If item is none of these an Exception is thrown.
    *
    * @param item                                 The new partner value
    * @param partner                              The new partner value
    * @return                                     true if the partner was changed
    * @throws java.lang.IllegalArgumentException  if item is not one of sourceConnector or
    *          targetConnector
    * @see                                        #getPartner
    */
   public boolean setPartner (UMLDiagramItem item, UMLDiagramItem partner)
   {
      if (item == getSourceConnector())
      {
         return setTargetConnector (partner);
      }
      else if (item == getTargetConnector())
      {
         return setSourceConnector (partner);
      }
      else
      {
         throw new IllegalArgumentException ("Argument must be equal to " +
            "sourceConnector or targetConnector");
      }
   }

}

/*
 * $Log$
 * Revision 1.6  2006/03/29 09:51:13  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.5  2006/03/01 12:22:51  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */
