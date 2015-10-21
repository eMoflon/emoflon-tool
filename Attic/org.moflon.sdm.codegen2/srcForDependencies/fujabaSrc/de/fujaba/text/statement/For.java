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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.fujaba.text.FParsedElement;
import de.fujaba.text.TextNode;
import de.fujaba.text.expression.Declaration;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;
import de.uni_kassel.util.IteratorsIterator;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class For extends StatementNode
{

   /**
    * 
    * @param parsedElement
    */
   public For(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   /**
    * 
    */
   private Declaration declaration;

   /**
    * Sets this for loop's declaration field and
    * appends the given value to the beginning
    * of the list of children.
    * 
    * @param value
    */
   public boolean setDeclaration(final Declaration value)
   {
      boolean changed = false;

      if(this.declaration != value)
      {
         final Declaration oldValue = this.declaration;
         this.declaration = value;
         //         this.declaration.setContext(CONTEXT_CONDITION);

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(0, value);

         changed = true;
      }
      return changed;
   }

   public Declaration getDeclaration()
   {
      return this.declaration;
   }

   /**
    * 
    */
   private TextNode condition;

   /**
    * Sets this for loop's condition expression 
    * field and appends the given value to the list
    * of children, after the declaration node.
    * 
    * @param value
    */
   public boolean setConditionExpression(final TextNode value)
   {
      boolean changed = false;

      if(this.condition != value)
      {
         final TextNode oldValue = this.condition;
         this.condition = value;
         //         this.declaration.setContext(CONTEXT_CONDITION);

         // sync children link
         this.removeFromChildren(oldValue);
         if(this.declaration == null)
         {
            this.addToChildren(0, value);
         }
         else
         {
            this.addAfterOfChildren(this.declaration, value);
         }

         changed = true;
      }
      return changed;
   }

   public TextNode getConditionExpression()
   {
      return this.condition;
   }

   /**
    * 
    */
   private List<TextNode> incrementExpressions;

   /**
    * 
    * @param value
    * @return
    */
   public boolean addToIncrementExpressions(final TextNode value)
   {
      // sync children link
      boolean changed;
      if(this.loopBody == null)
      {
         changed = this.addToChildren(value);
      }
      else
      {
         changed = this.addBeforeOfChildren(this.loopBody, value);
      }

      if(changed)
      {
         if(this.incrementExpressions == null)
         {
            this.incrementExpressions = new ArrayList<TextNode>();
         }
         this.incrementExpressions.add(value);
      }
      return changed;
   }

   /**
    * Returns an Iterator of this For instance's increment
    * expressions. If the list is empty or null, an empty
    * iterator is returned.
    * 
    * @return
    */
   public Iterator<TextNode> iteratorOfIncrementExpressions()
   {
      if(this.incrementExpressions == null)
      {
         this.incrementExpressions = new ArrayList<TextNode>();
      }
      return this.incrementExpressions.iterator();
   }

   /**
    * 
    * @param value
    * @return
    */
   public boolean removeFromIncrementExpressions(final TextNode value)
   {
      // sync children link
      final boolean changed = this.removeFromChildren(value);

      if(changed)
      {
         if(this.incrementExpressions == null)
         {
            this.incrementExpressions = new ArrayList<TextNode>();
         }
         this.incrementExpressions.remove(value);
      }

      return changed;
   }

   /**
    * 
    * @param index
    * @return
    */
   public boolean removeFromIncrementExpressions(final Integer index)
   {
      // sync children link
      final boolean changed = this.removeFromChildren(index);

      if(changed)
      {
         if(this.incrementExpressions == null)
         {
            this.incrementExpressions = new ArrayList<TextNode>();
         }
         this.incrementExpressions.remove(index);
      }

      return changed;
   }

   /**
    * 
    * @param index
    * @return
    */
   public void removeAllFromIncrementExpression()
   {
      // sync children link
      for(Iterator<TextNode> iter = this.iteratorOfIncrementExpressions(); iter.hasNext();)
      {
         this.removeFromChildren(iter.next());
      }
      this.incrementExpressions.clear();
   }

   /**
    * 
    */
   private StatementNode loopBody;

   /**
    * Sets this for loop's loopBody field
    * and appends the given value to the end
    * of the list of children.
    * 
    * @param conditionNode
    */
   public boolean setLoopBody(final StatementNode value)
   {
      boolean changed = false;

      if(this.loopBody != value)
      {
         final StatementNode oldValue = this.loopBody;
         this.loopBody = value;
         //         this.declaration.setContext(CONTEXT_CONDITION);

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(value);

         changed = true;
      }
      return changed;
   }

   public StatementNode getLoopBody()
   {
      return this.loopBody;
   }

   /**
    * Just to ensure the correct order of elements when
    * iterating the cild elements.
    */
   @SuppressWarnings("unchecked")
   @Override
   public Iterator<TextNode> iteratorOfChildren()
   {
      IteratorsIterator<TextNode> iter = new IteratorsIterator<TextNode>();

      // 
      final ArrayList<TextNode> l = new ArrayList<TextNode>();
      if(declaration != null)
      {
         l.add(declaration);
      }
      if(condition != null)
      {
         l.add(condition);
      }
      iter.append(l.iterator());

      // 
      if(incrementExpressions != null)
      {
         iter.append(incrementExpressions.iterator());
      }

      // 
      final ArrayList<TextNode> l2 = new ArrayList<TextNode>();
      if(loopBody != null)
      {
         l.add(loopBody);
      }
      iter.append(l2.iterator());

      return iter;
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

      if(this.declaration != null)
      {
         this.declaration.removeYou();
         this.declaration = null;
      }

      if(this.condition != null)
      {
         this.condition.removeYou();
         this.condition = null;
      }

      if(this.incrementExpressions != null)
      {
         for(final TextNode tn : this.incrementExpressions)
         {
            tn.removeYou();
         }
         this.incrementExpressions.clear();
         this.incrementExpressions = null;
      }

      if(this.loopBody != null)
      {
         this.loopBody.removeYou();
         this.loopBody = null;
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
