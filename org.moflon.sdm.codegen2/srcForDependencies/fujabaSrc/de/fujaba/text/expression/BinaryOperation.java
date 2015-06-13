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
import java.util.List;
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
public abstract class BinaryOperation extends TextNode
{
   /**
    * 
    * @param diagramItem
    */
   public BinaryOperation(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   /**
    * <pre>
    *           0..1     firstOperand     0..1 
    * UMLTextNode ------------------------- BinaryOperation
    *           firstOperand      
    * </pre>
    */
   private TextNode firstOperand;

   public boolean setFirstOperand(TextNode value)
   {
      boolean changed = false;

      if(this.firstOperand != value)
      {
         TextNode oldValue = this.firstOperand;
         this.firstOperand = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(0, value);

         changed = true;
      }
      return changed;
   }

   public TextNode getFirstOperand()
   {
      return this.firstOperand;
   }

   /**
    * <pre>
    *           0..n     operatorOperandPairs     0..1 
    * OperatorOperandPair ------------------------- BinaryOperation
    *           operatorOperandPair      binaryOperation
    * </pre>
    */
   private List<OperatorOperandPair> operatorOperandPairs;

   @SuppressWarnings("unchecked")
   public boolean addToOperatorOperandPairs(OperatorOperandPair value)
   {
      @SuppressWarnings("unused")
      boolean changed = false;

      if(value != null && !this.hasInOperatorOperandPairs(value))
      {
         if(this.operatorOperandPairs == null)
         {
            this.operatorOperandPairs = new FLinkedList();
         }
         changed = this.operatorOperandPairs.add(value);

         // sync children link
         this.addToChildren(value);
      }

      //      return changed;
      return addToChildren(value);
   }

   public boolean removeFromOperatorOperandPairs(OperatorOperandPair value)
   {
      boolean changed = false;

      if((this.operatorOperandPairs != null) && (value != null))
      {
         changed = this.operatorOperandPairs.remove(value);

         // sync children link
         this.removeFromChildren(value);
      }
      return changed;
   }

   public void removeAllFromOperatorOperandPairs()
   {
      if(this.operatorOperandPairs != null && this.operatorOperandPairs.size() > 0)
      {
         this.operatorOperandPairs.clear();

         // sync children link
         this.removeAllFromChildren();
         this.addToChildren(this.getFirstOperand());
      }
   }

   public boolean hasInOperatorOperandPairs(OperatorOperandPair value)
   {
      return((this.operatorOperandPairs != null) && (value != null) && this.operatorOperandPairs.contains(value));
   }

   @SuppressWarnings("unchecked")
   public ListIterator<OperatorOperandPair> iteratorOfOperatorOperandPairs()
   {
      return((this.operatorOperandPairs == null) ? FEmptyListIterator.<OperatorOperandPair> get()
            : this.operatorOperandPairs.listIterator());
   }

   public int sizeOfOperatorOperandPairs()
   {
      return((this.operatorOperandPairs == null) ? 0 : this.operatorOperandPairs.size());
   }

   public OperatorOperandPair getFirstOfOperatorOperandPairs()
   {
      if(operatorOperandPairs == null)
      {
         return null;
      }
      else
      {
         if(operatorOperandPairs.size() == 0)
         {
            return null;
         }
         return (OperatorOperandPair) ((FLinkedList) operatorOperandPairs).getFirst();
      }
   }

   public OperatorOperandPair getLastOfOperatorOperandPairs()
   {
      if(operatorOperandPairs == null)
      {
         return null;
      }
      else
      {
         if(operatorOperandPairs.size() == 0)
         {
            return null;
         }
         return (OperatorOperandPair) ((FLinkedList) operatorOperandPairs).getLast();
      }
   }

   @SuppressWarnings("cast")
   public OperatorOperandPair getFromOperatorOperandPairs(int index)
   {
      if(index >= 0 && index < sizeOfOperatorOperandPairs())
      {
         return (OperatorOperandPair) this.operatorOperandPairs.get(index);
      }
      else
      {
         throw new IllegalArgumentException("getOperatorOperandPairAt(" + index + ")");
      }
   }

   public int indexOfOperatorOperandPairs(OperatorOperandPair value)
   {
      return((this.operatorOperandPairs == null) ? -1 : this.operatorOperandPairs.indexOf(value));
   }

   public int indexOfOperatorOperandPairs(OperatorOperandPair value, int index)
   {
      return((this.operatorOperandPairs == null) ? -1 : ((FLinkedList) this.operatorOperandPairs).indexOf(value, index));
   }

   public int lastIndexOfOperatorOperandPairs(OperatorOperandPair value)
   {
      return((this.operatorOperandPairs == null) ? -1 : this.operatorOperandPairs.lastIndexOf(value));
   }

   public int lastIndexOfOperatorOperandPairs(OperatorOperandPair value, int index)
   {
      return((this.operatorOperandPairs == null) ? -1 : ((FLinkedList) this.operatorOperandPairs).lastIndexOf(value,
            index));
   }

   public boolean isBeforeOfOperatorOperandPairs(OperatorOperandPair leftObject, OperatorOperandPair rightObject)
   {
      if(operatorOperandPairs == null)
      {
         return false;
      }
      else
      {
         return ((FLinkedList) operatorOperandPairs).isBefore(leftObject, rightObject);
      }
   }

   public boolean isAfterOfOperatorOperandPairs(OperatorOperandPair leftObject, OperatorOperandPair rightObject)
   {
      if(operatorOperandPairs == null)
      {
         return false;
      }
      else
      {
         return ((FLinkedList) operatorOperandPairs).isAfter(leftObject, rightObject);
      }
   }

   public OperatorOperandPair getNextOfOperatorOperandPairs(OperatorOperandPair object)
   {
      if(operatorOperandPairs == null)
      {
         return null;
      }
      else
      {
         return (OperatorOperandPair) ((FLinkedList) operatorOperandPairs).getNextOf(object);
      }
   }

   public OperatorOperandPair getNextOfOperatorOperandPairs(OperatorOperandPair object, int index)
   {
      if(operatorOperandPairs == null)
      {
         return null;
      }
      else
      {
         return (OperatorOperandPair) ((FLinkedList) operatorOperandPairs).getNextOf(object, index);
      }
   }

   public OperatorOperandPair getPreviousOfOperatorOperandPairs(OperatorOperandPair object)
   {
      if(operatorOperandPairs == null)
      {
         return null;
      }
      else
      {
         return (OperatorOperandPair) ((FLinkedList) operatorOperandPairs).getPreviousOf(object);
      }
   }

   public OperatorOperandPair getPreviousOfOperatorOperandPairs(OperatorOperandPair object, int index)
   {
      if(operatorOperandPairs == null)
      {
         return null;
      }
      else
      {
         return (OperatorOperandPair) ((FLinkedList) operatorOperandPairs).getPreviousOf(object, index);
      }
   }

   public boolean addAfterOfOperatorOperandPairs(OperatorOperandPair refObject, OperatorOperandPair value)
   {
      boolean changed = false;
      if(operatorOperandPairs != null)
      {
         int index = operatorOperandPairs.indexOf(refObject);
         changed = addToOperatorOperandPairs(index + 1, value);

         // sync children link
         this.addAfterOfChildren(refObject, value);
      }
      return changed;
   }

   public boolean addBeforeOfOperatorOperandPairs(OperatorOperandPair refObject, OperatorOperandPair value)
   {
      boolean changed = false;
      if(operatorOperandPairs != null)
      {
         int index = operatorOperandPairs.indexOf(refObject);
         changed = addToOperatorOperandPairs(index, value);

         // sync children link
         this.addBeforeOfChildren(refObject, value);
      }
      return changed;
   }

   @SuppressWarnings("unchecked")
   public boolean addToOperatorOperandPairs(int index, OperatorOperandPair value)
   {
      boolean changed = false;

      if(value != null)
      {
         if(this.operatorOperandPairs == null)
         {
            this.operatorOperandPairs = new FLinkedList(); // or FTreeSet () or FLinkedList ()
         }
         int oldIndex = this.indexOfOperatorOperandPairs(value);
         if(oldIndex != index)
         {
            try
            {
               if(oldIndex > -1)
               {
                  operatorOperandPairs.remove(oldIndex);
               }
               operatorOperandPairs.add(index, value);

               // sync children link
               addToChildren(index + 1 < sizeOfChildren() ? index + 1 : index, value);

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

   @SuppressWarnings("cast")
   public boolean removeFromOperatorOperandPairs(int index)
   {
      boolean changed = false;

      if(this.operatorOperandPairs != null && (index >= 0 && index < this.operatorOperandPairs.size()))
      {
         OperatorOperandPair value = getFromOperatorOperandPairs(index);
         removeFromChildren(value);

         OperatorOperandPair tmpValue = (OperatorOperandPair) this.operatorOperandPairs.remove(index);

         // sync children link
         this.removeFromChildren(index);

         if(tmpValue != null)
         {
            changed = true;
         }
      }
      return changed;
   }

   @SuppressWarnings("cast")
   public boolean removeFromOperatorOperandPairs(int index, OperatorOperandPair value)
   {
      boolean changed = false;

      if((this.operatorOperandPairs != null) && (value != null)
            && (index >= 0 && index < this.operatorOperandPairs.size()))
      {
         OperatorOperandPair oldValue = (OperatorOperandPair) this.operatorOperandPairs.get(index);

         if(oldValue == value)
         {
            changed = this.removeFromOperatorOperandPairs(index);

            // sync children link
            this.removeFromChildren(index);
         }
      }
      return changed;
   }

   public ListIterator iteratorOfOperatorOperandPairs(OperatorOperandPair lowerBound)
   {
      ListIterator result = FEmptyListIterator.get();

      if(operatorOperandPairs != null && lowerBound != null)
      {
         int index = operatorOperandPairs.indexOf(lowerBound) + 1;
         result = operatorOperandPairs.listIterator(index);
      }
      else if(operatorOperandPairs != null && lowerBound == null)
      {
         result = operatorOperandPairs.listIterator(0);
      }
      return result;
   }

   public ListIterator iteratorOfOperatorOperandPairs(int index)
   {
      return((this.operatorOperandPairs == null) ? FEmptyListIterator.get() : this.operatorOperandPairs
            .listIterator(Math.max(0, Math.min(index, this.operatorOperandPairs.size()))));
   }

   @Override
   public void removeYou()
   {
      if(this.getFirstOperand() != null)
      {
         this.getFirstOperand().removeYou();
         this.setFirstOperand(null);
      }

      for(Iterator<OperatorOperandPair> iter = iteratorOfOperatorOperandPairs(); iter.hasNext();)
      {
         iter.next().removeYou();
      }
      this.removeAllFromOperatorOperandPairs();

      super.removeYou();
   }

   /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * 
    * Visitable implementation.
    * 
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

   public abstract void accept(NoArgVoidVisitor v);

   public abstract <R, A> R accept(ArgVisitor<R, A> v, A argu);

   public abstract <R> R accept(NoArgVisitor<R> v);

   public abstract <A> void accept(ArgVoidVisitor<A> v, A argu);

}
