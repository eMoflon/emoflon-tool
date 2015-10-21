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
package de.fujaba.text;

import java.util.Iterator;

/**
 * FTextReference is implemented by all types whose instances can be
 * referenced from UML text elements, like FAttr, FMethod, FClass etc.
 * 
 * To keep the implementation in one place, the FTextReferenceUtility class
 * was added, so implementing classes only need have a field of it to which
 * they can forward the according operations.
 * 
 * <pre>
 *           0..1     referencedElement     0..n
 * FTextReference ------------------------- UMLTextNode
 *      referencedElement               textUsages
 * </pre>
 * 
 * Implementing helper class:
 * @see de.fujaba.text.FTextReferenceUtility
 * 
 * Implementing classes (using FTextReferenceUtility):
 * @see de.fujaba.text.nodes.Declaration
 * @see de.uni_paderborn.fujaba.uml.behavior.UMLCollabStat
 * @see de.uni_paderborn.fujaba.uml.behavior.UMLObject
 * @see de.uni_paderborn.fujaba.uml.structure.UMLAttr
 * @see de.uni_paderborn.fujaba.uml.structure.UMLBaseType
 * @see de.uni_paderborn.fujaba.uml.structure.UMLClass
 * @see de.uni_paderborn.fujaba.uml.structure.UMLMethod
 * @see de.uni_paderborn.fujaba.uml.structure.UMLPackage
 * @see de.uni_paderborn.fujaba.uml.structure.UMLParam
 * @see de.uni_paderborn.fujaba.uml.structure.UMLRole
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public interface FTextReference
{

   /**
    * Add the given UMLTextNode instance to this FTextReference' set of text usages.
    * 
    * @param node
    *           The node to be added
    * 
    * @return true if the set was changed
    */
   public boolean addToTextUsages(TextNode node);

   /**
    * Determines if this FTextReference instance has the given UMLTextNode in its set
    * of text usages.
    * 
    * @param node
    *           the UMLTextNode instance to be checked for existence in the set
    * 
    * @return true if this FTextReference contains the given UMLTextNode
    */
   public boolean hasInTextUsages(TextNode node);

   /**
    * @return an Iterator of this instance's set of text usages.
    */
   public Iterator<TextNode> iteratorOfTextUsages();

   /**
    * Remove all UMLTextNode instances from this FTextReference instance's set of text
    * usages.
    */
   public void removeAllFromTextUsages();

   /**
    * Remove the given UMLTextNode instance from this FTextReference instance's set of
    * text usages.
    * 
    * @param node
    *           the UMLTextNode instance to be removed
    * 
    * @return true if the given FTextReference's set was changed.
    */
   public boolean removeFromTextUsages(TextNode node);

   /**
    * @return the number of UMLTextNode instances in this instance's set of text usages.
    */
   public int sizeOfTextUsages();

}
