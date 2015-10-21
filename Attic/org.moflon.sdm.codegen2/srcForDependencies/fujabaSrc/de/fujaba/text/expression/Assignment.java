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
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class Assignment extends TextNode
{
   /**
    * 
    * @param diagramItem
    * @param identifier
    * @param value
    */
   public Assignment(FParsedElement parsedElement, TextNode identifier, Operator operator, TextNode value)
   {
      super(parsedElement);

      if(identifier != null && (identifier instanceof Identifier || identifier instanceof Dereference))
      {
         setAssignTarget(identifier);
         setOperator(operator);
         setValue(value);
      }
      else
      {
         throw new IllegalArgumentException("Parameter 'identiifer' must be of type Identifier or Dereference.");
      }

      /*
       * Make sure the last element of the Dereference
       * chain is an identifier
       */
      if(identifier instanceof Dereference)
      {
         Dereference deref = (Dereference) identifier;

         while(deref.getChildElement() != null && deref.getChildElement() instanceof Dereference)
         {
            deref = (Dereference) deref.getChildElement();
         }

         // check consistency of last dereference in the chain 
         if(deref.getElement() == null)
         {
            throw new IllegalArgumentException("The given Dereference is invalid: element of last dereference is null.");
         }

         //
         if(deref.getChildElement() != null && !(deref.getChildElement() instanceof Identifier))
         {
            throw new IllegalArgumentException("Dereference has no identifier as last element ans can not be used "
                  + "for an assignment.");
         }
         else if(deref.getChildElement() == null && !(deref.getElement() instanceof Identifier))
         {
            throw new IllegalArgumentException("Dereference has no identifier as last element ans can not be used "
                  + "for an assignment.");
         }
      }
   }

   /**
    * <pre>
    *               identifier    0..1 
    * Assignment ---------------------> UMLTextNode
    *                       identifier
    * </pre>
    */
   private TextNode assignTarget;

   public TextNode getAssignTarget()
   {
      return assignTarget;
   }

   public boolean setAssignTarget(TextNode value)
   {
      boolean changed = false;

      if(this.assignTarget != value)
      {
         TextNode oldValue = this.assignTarget;
         this.assignTarget = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(0, value);

         changed = true;
      }
      return changed;
   }

   /**
    * 
    */
   private Operator operator;

   public Operator getOperator()
   {
      return operator;
   }

   public boolean setOperator(Operator value)
   {
      boolean changed = false;

      if(this.value != value)
      {
         TextNode oldValue = this.operator;
         this.operator = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addAfterOfChildren(this.getAssignTarget(), value);

         changed = true;
      }
      return changed;
   }

   /**
    * <pre>
    *                  value      0..1 
    * Assignment ---------------------> UMLTextNode
    *                            value
    * </pre>
    */
   private TextNode value;

   public TextNode getValue()
   {
      return value;
   }

   public boolean setValue(TextNode value)
   {
      boolean changed = false;

      if(this.value != value)
      {
         TextNode oldValue = this.value;
         this.value = value;

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

      if(this.getAssignTarget() != null)
      {
         this.getAssignTarget().removeYou();
         this.setAssignTarget(null);
      }

      if(this.getOperator() != null)
      {
         this.getOperator().removeYou();
         this.setOperator(null);
      }

      if(this.getValue() != null)
      {
         this.getValue().removeYou();
         this.setValue(null);
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
