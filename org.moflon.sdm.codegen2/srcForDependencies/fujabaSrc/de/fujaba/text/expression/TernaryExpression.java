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
 * UMLTextParser representation of a ternary if expression of the form 'a ? b : c'.
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class TernaryExpression extends TextNode
{

   /**
    * 
    * @param parsedElement
    * @param cond
    * @param then
    * @param _else
    */
   public TernaryExpression(FParsedElement parsedElement, TextNode cond, TextNode then, TextNode _else)
   {
      super(parsedElement);
      setCondition(cond);
      setThenExpr(then);
      setElseExpr(_else);
   }

   /**
    * <pre>
    *           0..1     condition     0..1 
    * UMLTextNode ------------------------- TernaryExpression
    *           condition      ternaryExpression
    * </pre>
    */
   private TextNode condition;

   public boolean setCondition(TextNode value)
   {
      boolean changed = false;

      if(this.condition != value)
      {
         TextNode oldValue = this.condition;
         this.condition = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(0, value);

         changed = true;
      }
      return changed;
   }

   public TextNode getCondition()
   {
      return this.condition;
   }

   /**
    * <pre>
    *           0..1     thenExpr     0..1 
    * UMLTextNode ------------------------- TernaryExpression
    *           thenExpr      ternaryExpression2
    * </pre>
    */
   private TextNode thenExpr;

   public boolean setThenExpr(TextNode value)
   {
      boolean changed = false;

      if(this.thenExpr != value)
      {
         TextNode oldValue = this.thenExpr;
         this.thenExpr = value;

         // sync children link
         this.removeFromChildren(oldValue);
         if(this.condition == null)
         {
            this.addToChildren(0, value);
         }
         else
         {
            this.addAfterOfChildren(this.condition, value);
         }

         changed = true;
      }
      return changed;
   }

   public TextNode getThenExpr()
   {
      return this.thenExpr;
   }

   /**
    * <pre>
    *           0..1     elseExpr     0..1 
    * UMLTextNode ------------------------- TernaryExpression
    *           elseExpr      ternaryExpression3
    * </pre>
    */
   private TextNode elseExpr;

   public boolean setElseExpr(TextNode value)
   {
      boolean changed = false;

      if(this.elseExpr != value)
      {
         TextNode oldValue = this.elseExpr;
         this.elseExpr = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(value);

         changed = true;
      }
      return changed;
   }

   public TextNode getElseExpr()
   {
      return this.elseExpr;
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

      if(this.getCondition() != null)
      {
         this.getCondition().removeYou();
         this.setCondition(null);
      }

      if(this.getThenExpr() != null)
      {
         this.getThenExpr().removeYou();
         this.setThenExpr(null);
      }

      if(this.getElseExpr() != null)
      {
         this.getElseExpr().removeYou();
         this.setElseExpr(null);
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
