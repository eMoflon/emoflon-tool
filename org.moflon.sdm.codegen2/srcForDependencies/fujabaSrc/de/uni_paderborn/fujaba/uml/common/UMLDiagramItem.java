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


import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FDiagramItem;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.util.FDiagramItemUtility;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public abstract class UMLDiagramItem extends UMLIncrement implements
      FDiagramItem
{

   protected UMLDiagramItem(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   // ---- special methods for the diagrams role ---

   /**
    * Returns the first diagram in the set of diagrams.
    * 
    * @return The firstFromDiagrams value
    */
   @Override
   public UMLDiagram getFirstFromDiagrams()
   {
      return (UMLDiagram) FDiagramItemUtility.getFirstFromDiagrams(this);
   }


   /**
    * ensures that only UMLDiagrams can be added to the set of diagrams.
    * 
    * @param diagram The object added.
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#addToDiagrams(de.uni_paderborn.fujaba.metamodel.common.FDiagram)
    */
   @Override
   public void addToDiagrams(FDiagram diagram)
   {
      if (diagram instanceof UMLDiagram)
      {
         super.addToDiagrams(diagram);
      }
      else
      {
         throw new IllegalArgumentException(
               "Arguments have to be subtypes of "
               + UMLDiagram.class.getName());
      }
   }

   
   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return getName();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object). This
    * method allows to navigate in direction of the model root (project) from any element within a
    * project.
    * 
    * @return the logical parent of this element, may not return null unless this is the top level
    *         node (project) or is not contained in any parent yet
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      if (sizeOfDiagrams() == 1)
      {
         return getFirstFromDiagrams();
      }
      else
      {
         throw new UnsupportedOperationException(
               getClass()
                     + " does not support to query the parent element if present in multiple/no diagrams!");
      }
   }

}
