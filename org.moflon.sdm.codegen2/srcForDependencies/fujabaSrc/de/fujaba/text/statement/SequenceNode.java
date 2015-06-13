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
package de.fujaba.text.statement;

import java.util.Iterator;
import java.util.LinkedList;

import de.fujaba.text.FParsedElement;
import de.fujaba.text.TextNode;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public abstract class SequenceNode extends TextNode
{

   /**
    * 
    * @param parsedElement
    */
   public SequenceNode(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   /**
    * 
    * @param value
    * @return 
    */
   public boolean addToElements(TextNode value)
   {
      return this.addToChildren(value);
   }

   /**
    * 
    * @return
    */
   public Iterator<TextNode> iteratorOfElements()
   {
      final LinkedList<TextNode> l = new LinkedList<TextNode>();

      Iterator<TextNode> iter = this.iteratorOfChildren();
      while(iter.hasNext())
      {
         l.add((TextNode) iter.next());
      }

      return l.iterator();
   }

}
