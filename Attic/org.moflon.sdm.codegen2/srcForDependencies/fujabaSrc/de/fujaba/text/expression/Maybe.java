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
import java.util.ListIterator;

import de.fujaba.text.FParsedElement;
import de.fujaba.text.TextNode;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;
import de.upb.tools.fca.FEmptyListIterator;
import de.upb.tools.fca.FLinkedList;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class Maybe extends TextNode
{
   /**
    * 
    * @param diagramItem
    */
   public Maybe(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   /**
    * 
    */
   private boolean oldStyle;

   public void useOldStyle(boolean value)
   {
      this.oldStyle = value;
   }

   public void useNewStyle(boolean value)
   {
      this.oldStyle = !value;
   }

   public boolean useOldStyle()
   {
      return this.oldStyle;
   }

   public boolean useNewStyle()
   {
      return !this.oldStyle;
   }

   /**
    * <pre>
    *           0..n     operands     0..1 
    * UMLTextNode ------------------------- Maybe
    *           operands      maybe
    * </pre>
    */
   private FLinkedList operands;

   public boolean addToOperands(TextNode value)
   {
      boolean changed = false;

      if(value != null && !hasInOperands(value))
      {
         if(operands == null)
         {
            operands = new FLinkedList();
         }
         changed = operands.add(value);

         // sync children link
         this.addToChildren(value);
      }
      return changed;
   }

   public boolean removeFromOperands(TextNode value)
   {
      boolean changed = false;

      if((operands != null) && (value != null))
      {
         changed = operands.remove(value);

         // sync children link
         this.removeFromChildren(value);
      }
      return changed;
   }

   public void removeAllFromOperands()
   {
      if(operands != null && operands.size() > 0)
      {
         operands.clear();

         // sync children link
         this.removeAllFromChildren();
      }
   }

   public boolean hasInOperands(TextNode value)
   {
      return((operands != null) && (value != null) && operands.contains(value));
   }

   @SuppressWarnings("unchecked")
   public ListIterator<TextNode> iteratorOfOperands()
   {
      return((operands == null) ? FEmptyListIterator.get() : operands.listIterator());
   }

   public int sizeOfOperands()
   {
      return((operands == null) ? 0 : operands.size());
   }

   public TextNode getFirstOfOperands()
   {
      if(operands == null)
      {
         return null;
      }
      else
      {
         if(operands.size() == 0)
         {
            return null;
         }
         return (TextNode) operands.getFirst();
      }
   }

   public TextNode getLastOfOperands()
   {
      if(operands == null)
      {
         return null;
      }
      else
      {
         if(operands.size() == 0)
         {
            return null;
         }
         return (TextNode) operands.getLast();
      }
   }

   public TextNode getFromOperands(int index)
   {
      if(index >= 0 && index < sizeOfOperands())
      {
         return (TextNode) operands.get(index);
      }
      else
      {
         throw new IllegalArgumentException("getOperandsAt(" + index + ")");
      }
   }

   public int indexOfOperands(TextNode value)
   {
      return((operands == null) ? -1 : operands.indexOf(value));
   }

   public int indexOfOperands(TextNode value, int index)
   {
      return((operands == null) ? -1 : operands.indexOf(value, index));
   }

   public int lastIndexOfOperands(TextNode value)
   {
      return((operands == null) ? -1 : operands.lastIndexOf(value));
   }

   public int lastIndexOfOperands(TextNode value, int index)
   {
      return((operands == null) ? -1 : operands.lastIndexOf(value, index));
   }

   public boolean isBeforeOfOperands(TextNode leftObject, TextNode rightObject)
   {
      if(operands == null)
      {
         return false;
      }
      else
      {
         return operands.isBefore(leftObject, rightObject);
      }
   }

   public boolean isAfterOfOperands(TextNode leftObject, TextNode rightObject)
   {
      if(operands == null)
      {
         return false;
      }
      else
      {
         return operands.isAfter(leftObject, rightObject);
      }
   }

   public TextNode getNextOfOperands(TextNode object)
   {
      if(operands == null)
      {
         return null;
      }
      else
      {
         return (TextNode) operands.getNextOf(object);
      }
   }

   public TextNode getNextOfOperands(TextNode object, int index)
   {
      if(operands == null)
      {
         return null;
      }
      else
      {
         return (TextNode) operands.getNextOf(object, index);
      }
   }

   public TextNode getPreviousOfOperands(TextNode object)
   {
      if(operands == null)
      {
         return null;
      }
      else
      {
         return (TextNode) operands.getPreviousOf(object);
      }
   }

   public TextNode getPreviousOfOperands(TextNode object, int index)
   {
      if(operands == null)
      {
         return null;
      }
      else
      {
         return (TextNode) operands.getPreviousOf(object, index);
      }
   }

   public boolean addAfterOfOperands(TextNode refObject, TextNode value)
   {
      boolean changed = false;
      if(operands != null)
      {
         int index = operands.indexOf(refObject);
         changed = addToOperands(index + 1, value);

         // sync children link
         this.addAfterOfChildren(refObject, value);
      }
      return changed;
   }

   public boolean addBeforeOfOperands(TextNode refObject, TextNode value)
   {
      boolean changed = false;
      if(operands != null)
      {
         int index = operands.indexOf(refObject);
         changed = addToOperands(index, value);

         // sync children link
         this.addBeforeOfChildren(refObject, value);
      }
      return changed;
   }

   public boolean addToOperands(int index, TextNode value)
   {
      boolean changed = false;

      if(value != null)
      {
         if(operands == null)
         {
            operands = new FLinkedList(); // or FTreeSet () or FLinkedList ()
         }
         int oldIndex = indexOfOperands(value);
         if(oldIndex != index)
         {
            try
            {
               if(oldIndex > -1)
               {
                  operands.remove(oldIndex);
               }
               operands.add(index, value);

               // sync children link
               this.addToChildren(index, value);

               changed = true;
            }
            catch(IndexOutOfBoundsException ex)
            {
               return false;
            }
         }
      }
      return changed;
   }

   public boolean setInOperands(int index, TextNode value)
   {
      boolean changed = false;

      if(value != null)
      {
         if(operands == null)
         {
            operands = new FLinkedList(); // or FTreeSet () or FLinkedList ()
         }
         int oldIndex = indexOfOperands(value);
         if(oldIndex != index)
         {
            try
            {
               TextNode oldValue = (TextNode) operands.set(index, value);

               // sync children link
               this.setInChildren(index, value);

               if(oldIndex > -1)
               {
                  operands.remove(oldIndex);
               }
               if(oldValue != value)
               {
                  changed = true;
               }
            }
            catch(IndexOutOfBoundsException ex)
            {
               return false;
            }
         }
      }
      return changed;
   }

   public boolean removeFromOperands(int index)
   {
      boolean changed = false;

      if(operands != null && (index >= 0 && index < operands.size()))
      {
         TextNode tmpValue = (TextNode) operands.remove(index);

         // sync children link
         this.removeFromChildren(index);

         if(tmpValue != null)
         {
            changed = true;
         }
      }
      return changed;
   }

   public boolean removeFromOperands(int index, TextNode value)
   {
      boolean changed = false;

      if((operands != null) && (value != null) && (index >= 0 && index < operands.size()))
      {
         TextNode oldValue = (TextNode) operands.get(index);

         // sync children link
         this.removeFromChildren(index, value);

         if(oldValue == value)
         {
            changed = removeFromOperands(index);
         }
      }
      return changed;
   }

   public ListIterator iteratorOfOperands(TextNode lowerBound)
   {
      ListIterator result = FEmptyListIterator.get();

      if(operands != null && lowerBound != null)
      {
         int index = operands.indexOf(lowerBound) + 1;
         result = operands.listIterator(index);
      }
      else if(operands != null && lowerBound == null)
      {
         result = operands.listIterator(0);
      }
      return result;
   }

   public ListIterator iteratorOfOperands(int index)
   {
      return((operands == null) ? FEmptyListIterator.get() : operands.listIterator(Math.max(0, Math.min(index, operands
            .size()))));
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

      for(Iterator<TextNode> iter = iteratorOfOperands(); iter.hasNext();)
      {
         iter.next().removeYou();
      }
      removeAllFromOperands();

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
