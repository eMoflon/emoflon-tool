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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

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
public class ArrayAccessorList extends TextNode
{

   /**
    * Default message for an <tt>IllegalstateException</tt>.
    */
   private static final String ISE_Message = "ArrayAccessorList must only contain nodes of type ArrayAccess.";

   /**
    * Default message for <tt>UnsupportedOperationException</tt> thrown by this class.
    */
   private static final String UOE_Message = "ArrayAccessorList must only contain nodes of type ArrayAccess.";

   /**
    * 
    * @param parsedElement
    */
   public ArrayAccessorList(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   public boolean addAfterOfArrayAccess(ArrayAccess refObject, ArrayAccess value)
   {
      return super.addAfterOfChildren(refObject, value);
   }

   public boolean addBeforeOfArrayAccess(ArrayAccess refObject, ArrayAccess value)
   {
      return super.addBeforeOfChildren(refObject, value);
   }

   public boolean addToArrayAccess(int index, ArrayAccess value)
   {
      return super.addToChildren(index, value);
   }

   public boolean addToArrayAccess(ArrayAccess value)
   {
      return super.addToChildren(value);
   }

   public ArrayAccess getFirstOfArrayAccess()
   {
      final TextNode result = super.getFirstOfChildren();

      if(result != null && !(result instanceof ArrayAccess))
      {
         throw new IllegalStateException(ISE_Message);
      }

      return (ArrayAccess) result;
   }

   public ArrayAccess getFromArrayAccess(int index)
   {
      final TextNode result = super.getFromChildren(index);

      if(result != null && !(result instanceof ArrayAccess))
      {
         throw new IllegalStateException(ISE_Message);
      }

      return (ArrayAccess) result;
   }

   public ArrayAccess getLastOfArrayAccess()
   {
      final TextNode result = super.getLastOfChildren();

      if(result != null && !(result instanceof ArrayAccess))
      {
         throw new IllegalStateException(ISE_Message);
      }

      return (ArrayAccess) result;
   }

   public ArrayAccess getNextOfArrayAccess(ArrayAccess object, int index)
   {
      final TextNode result = super.getNextOfChildren(object, index);

      if(result != null && !(result instanceof ArrayAccess))
      {
         throw new IllegalStateException(ISE_Message);
      }

      return (ArrayAccess) result;
   }

   public ArrayAccess getNextOfArrayAccess(ArrayAccess object)
   {
      final TextNode result = super.getNextOfChildren(object);

      if(result != null && !(result instanceof ArrayAccess))
      {
         throw new IllegalStateException(ISE_Message);
      }

      return (ArrayAccess) result;
   }

   public ArrayAccess getPreviousOfArrayAccess(ArrayAccess object, int index)
   {
      final TextNode result = super.getPreviousOfChildren(object, index);

      if(result != null && !(result instanceof ArrayAccess))
      {
         throw new IllegalStateException(ISE_Message);
      }

      return (ArrayAccess) result;
   }

   public ArrayAccess getPreviousOfArrayAccess(ArrayAccess object)
   {
      final TextNode result = super.getPreviousOfChildren(object);

      if(result != null && !(result instanceof ArrayAccess))
      {
         throw new IllegalStateException(ISE_Message);
      }

      return (ArrayAccess) result;
   }

   public boolean hasInArrayAccess(ArrayAccess value)
   {
      return super.hasInChildren(value);
   }

   public int indexOfArrayAccess(ArrayAccess value, int index)
   {
      return super.indexOfChildren(value, index);
   }

   public int indexOfArrayAccess(ArrayAccess value)
   {
      return super.indexOfChildren(value);
   }

   public boolean isAfterOfArrayAccess(ArrayAccess leftObject, ArrayAccess rightObject)
   {
      return super.isAfterOfChildren(leftObject, rightObject);
   }

   public boolean isBeforeOfArrayAccess(ArrayAccess leftObject, ArrayAccess rightObject)
   {
      return super.isBeforeOfChildren(leftObject, rightObject);
   }

   public ListIterator<ArrayAccess> iteratorOfArrayAccess()
   {
      final LinkedList<ArrayAccess> l = new LinkedList<ArrayAccess>();

      for(TextNode child : this)
      {
         if(child instanceof ArrayAccess)
         {
            l.add((ArrayAccess) child);
         }
         else
         {
            throw new IllegalStateException(ArrayAccessorList.ISE_Message);
         }
      }

      return l.listIterator();
   }

   public Iterator iteratorOfArrayAccess(int index)
   {
      return super.iteratorOfChildren(index);
   }

   public Iterator iteratorOfArrayAccess(ArrayAccess lowerBound)
   {
      return super.iteratorOfChildren(lowerBound);
   }

   public int lastIndexOfArrayAccess(ArrayAccess value, int index)
   {
      return super.lastIndexOfChildren(value, index);
   }

   public int lastIndexOfArrayAccess(ArrayAccess value)
   {
      return super.lastIndexOfChildren(value);
   }

   public void removeAllFromArrayAccess()
   {
      super.removeAllFromChildren();
   }

   public boolean removeFromArrayAccess(int index, ArrayAccess value)
   {
      return super.removeFromChildren(index, value);
   }

   public boolean removeFromArrayAccess(int index)
   {
      return super.removeFromChildren(index);
   }

   public boolean removeFromArrayAccess(ArrayAccess value)
   {
      return super.removeFromChildren(value);
   }

   public boolean setInArrayAccess(int index, ArrayAccess value)
   {
      return super.setInChildren(index, value);
   }

   public int sizeOfArrayAccess()
   {
      return super.sizeOfChildren();
   }

   /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * 
    * Overridden methods from TextNode.
    * 
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

   @Override
   public boolean addAfterOfChildren(TextNode refObject, TextNode value)
   {
      throw new UnsupportedOperationException(UOE_Message);
   }

   @Override
   public boolean addBeforeOfChildren(TextNode refObject, TextNode value)
   {
      throw new UnsupportedOperationException(UOE_Message);
   }

   @Override
   public boolean addToChildren(int index, TextNode value)
   {
      throw new UnsupportedOperationException(UOE_Message);
   }

   @Override
   public boolean addToChildren(TextNode value)
   {
      throw new UnsupportedOperationException(UOE_Message);
   }

   @Override
   public boolean setInChildren(int index, TextNode value)
   {
      throw new UnsupportedOperationException(UOE_Message);
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
