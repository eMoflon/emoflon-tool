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

import de.fujaba.text.FParsedElement;
import de.fujaba.text.TextNode;
import de.fujaba.text.expression.Brackets;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class IfElse extends StatementNode
{

   /**
    * 
    * @param parsedElement
    */
   public IfElse(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   /**
    * 
    */
   private Brackets conditionExpression;

   public Brackets getConditionExpression()
   {
      return conditionExpression;
   }

   /**
    * Sets this if-else statement's 'condition' field and
    * appends the given value to the beginning of the
    * list of children.
    * 
    * @param value
    * @return
    */
   public boolean setConditionExpression(Brackets value)
   {
      boolean changed = false;

      if(this.conditionExpression != value)
      {
         final Brackets oldValue = this.conditionExpression;
         this.conditionExpression = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(0, value);

         changed = true;
      }
      return changed;
   }

   /**
    * Sets this if-else statement's 'then' field and
    * appends the given value 'to the middle' of the
    * list of children, i.e. after the condition TextNode.
    */
   private TextNode then;

   public TextNode getThen()
   {
      return then;
   }

   public boolean setThen(TextNode value)
   {
      boolean changed = false;

      if(this.then != value)
      {
         final TextNode oldValue = this.then;
         this.then = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addAfterOfChildren(this.conditionExpression, value);

         changed = true;
      }
      return changed;
   }

   /**
    * 
    */
   private TextNode _else;

   public TextNode getElse()
   {
      return _else;
   }

   /**
    * Sets this if-else statement's 'else' field and
    * appends the given value to the end of the
    * list of children.
    * 
    * @param value
    * @return
    */
   public boolean setElse(TextNode value)
   {
      boolean changed = false;

      if(this._else != value)
      {
         final TextNode oldValue = this._else;
         this._else = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(value);

         changed = true;
      }
      return changed;
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

      if(this.conditionExpression != null)
      {
         this.conditionExpression.removeYou();
         this.conditionExpression = null;
      }

      if(this.then != null)
      {
         this.then.removeYou();
         this.then = null;
      }

      if(this._else != null)
      {
         this._else.removeYou();
         this._else = null;
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
