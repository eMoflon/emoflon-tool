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
import de.fujaba.text.expression.Declaration;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class Catch extends TextNode
{

   /**
    * 
    * @param parsedElement
    * @param catchPhrase
    * @param block
    */
   public Catch(FParsedElement parsedElement, Declaration catchPhrase, Block block)
   {
      super(parsedElement);

      if(catchPhrase == null)
      {
         throw new IllegalArgumentException("Parameter \"catchPhrase\" must not be null.");
      }
      this.setCatchPhrase(catchPhrase);

      if(block == null)
      {
         throw new IllegalArgumentException("Parameter \"block\" must not be null.");
      }
      this.setBlock(block);
   }

   /**
    * 
    */
   private Declaration catchPhrase;

   public Declaration getCatchPhrase()
   {
      return catchPhrase;
   }

   /**
    * Sets this catch statement's 'catchPhrase' field and
    * appends the given value to the beginning of the
    * list of children.
    * 
    * @param value
    * @return
    */
   public boolean setCatchPhrase(final Declaration value)
   {
      boolean changed = false;

      if(this.catchPhrase != value)
      {
         final TextNode oldValue = this.catchPhrase;
         this.catchPhrase = value;

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
   private Block block;

   public Block getBlock()
   {
      return block;
   }

   /**
    * Sets this catch statement's 'block' field and
    * appends the given value to the end of the
    * list of children.
    * 
    * @param value
    * @return
    */
   public boolean setBlock(Block value)
   {
      boolean changed = false;

      if(this.block != value)
      {
         final TextNode oldValue = this.block;
         this.block = value;

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

      if(this.catchPhrase != null)
      {
         this.catchPhrase.removeYou();
         this.catchPhrase = null;
      }

      if(this.block != null)
      {
         this.block.removeYou();
         this.block = null;
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
