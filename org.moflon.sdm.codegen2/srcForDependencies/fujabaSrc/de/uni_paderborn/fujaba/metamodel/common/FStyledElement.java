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



/**
 * Interface for specifying the style of the generated code in some way. E.g. attribute and role access,
 * creation and destruction of objects ...
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FStyledElement
{
   public final static String CODE_STYLE_PROPERTY = "codeStyle";


   /**
    * Query the code style for the element.
    *
    * @return   the code style (default = null)
    */
   FCodeStyle getCodeStyle();


   /**
    * Change the code style for the element.
    *
    * @param style  new style
    */
   void setCodeStyle (FCodeStyle style);


   /**
    * Query the style to use for this element. Recurses to appropriate parent elements if the style
    * was not set for this element itself.
    *
    * @return   the suggested code style
    */
   FCodeStyle getInheritedCodeStyle();
}

/*
 * $Log$
 * Revision 1.2  2007/03/21 14:19:24  cschneid
 * - map access style to code style on load
 * - show attrs in tree
 *
 */
