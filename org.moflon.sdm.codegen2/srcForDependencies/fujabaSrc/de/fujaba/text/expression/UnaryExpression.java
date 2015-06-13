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
package de.fujaba.text.expression;

import de.fujaba.text.FParsedElement;
import de.fujaba.text.TextNode;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;

/**
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class UnaryExpression extends TextNode
{
   /**
    * <pre>
    *           0..1     prefix     0..1 
    * UnaryPrefix ------------------------- UnaryExpression
    *           prefix      
    * </pre>
    */
   private UnaryPrefix prefix;

   /**
    * 
    * @param diagramItem
    * @param prefix
    * @param expr
    */
   public UnaryExpression(FParsedElement parsedElement, UnaryPrefix prefix, TextNode expr)
   {
      super(parsedElement);

      if(expr == null)
      {
         throw new IllegalArgumentException("Parameter 'expr' must not be null.");
      }

      setPrefix(prefix);
      setExpression(expr);
   }

   /**
    * 
    * @param value
    * @return
    */
   private boolean setPrefix(UnaryPrefix value)
   {
      boolean changed = false;

      if(this.prefix != value)
      {
         UnaryPrefix oldValue = this.prefix;
         this.prefix = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(0, value);

         changed = true;
      }
      return changed;
   }

   /**
    * 
    * @return
    */
   public UnaryPrefix getPrefix()
   {
      return prefix;
   }

   /**
    * <pre>
    *           0..1     expression     0..1 
    * UMLTextNode ------------------------- UnaryExpression
    *           expression      unaryExpression
    * </pre>
    */
   private TextNode expression;

   public boolean setExpression(TextNode value)
   {
      boolean changed = false;

      if(this.expression != value)
      {
         TextNode oldValue = this.expression;
         this.expression = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(value);

         changed = true;
      }
      return changed;
   }

   public TextNode getExpression()
   {
      return this.expression;
   }

   /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * 
    * removeYou() implementation that fires an event to indicate that the
    * removal of this instance is about to begin.
    * 
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

   @Override
   public void removeYou()
   {
      this.getPropertyChangeSupport().firePropertyChange(TextNode.REMOVE_YOU_STARTED, this, this);

      if(this.getPrefix() != null)
      {
         this.getPrefix().removeYou();
         this.setPrefix(null);
      }

      if(this.getExpression() != null)
      {
         this.getExpression().removeYou();
         this.setExpression(null);
      }

      super.removeYou();
   }

   /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * 
    * Visitable implementation.
    * 
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

   public void accept(NoArgVoidVisitor v)
   {
      v.visit(this);
   }

   public <R, A> R accept(ArgVisitor<R, A> v, A argu)
   {
      return v.visit(this, argu);
   }

   public <R> R accept(NoArgVisitor<R> v)
   {
      return v.visit(this);
   }

   public <A> void accept(ArgVoidVisitor<A> v, A argu)
   {
      v.visit(this, argu);
   }

}
