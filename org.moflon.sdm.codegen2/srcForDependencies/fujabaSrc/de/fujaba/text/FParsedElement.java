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

import de.uni_paderborn.fujaba.metamodel.common.FDiagramItem;

/**
 * An FParsedElement instance is a UML diagram item that contains
 * a text expression and can be computed by the UMLTextParserPlugin.
 * 
 * Created: 07.09.2007
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public interface FParsedElement extends FDiagramItem
{

   /**
    * @return the name of the field that contains the
    *         UML text expression in this class.
    */
   public String getTextPropertyName();

   /**
    * @return the UML text expression of this object.
    */
   public String getParsedText();

   /**
    * Sets the UML text expression of this object.
    * @param value new value
    */
   public void setParsedText(String value);

   /**
    * @return the syntax tree of this object's UML text expression.
    *         Returns null if this object has not yet been computed
    *         by the UMLTextParserPlugin.
    */
   public TextNode getParsetree();

   /**
    * Set the parsetree that represents the syntax of this element's textual expression.
    * @param node new parse tree
    */
   public void setParsetree(TextNode node);

}
