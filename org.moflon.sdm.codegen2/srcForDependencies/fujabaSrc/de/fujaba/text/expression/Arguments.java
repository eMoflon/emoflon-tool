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
public class Arguments extends TextNode
{
   /**
    * 
    * @param diagramItem
    */
   public Arguments(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   /**
    * <pre>
    *           0..n     arguments     0..1 
    * UMLTextNode ------------------------- Arguments
    *           arguments      arguments
    * </pre>
    */
   private FLinkedList arguments;

   public boolean addToArguments(TextNode value)
   {
      boolean changed = false;

      if(value != null && !hasInArguments(value))
      {
         if(arguments == null)
         {
            arguments = new FLinkedList();
         }
         changed = arguments.add(value);

         // sync children link
         this.addToChildren(value);
      }
      return changed;
   }

   public boolean removeFromArguments(TextNode value)
   {
      boolean changed = false;

      if((arguments != null) && (value != null))
      {
         changed = arguments.remove(value);

         // sync children link
         this.removeFromChildren(value);
      }
      return changed;
   }

   public void removeAllFromArguments()
   {
      if(arguments != null && arguments.size() > 0)
      {
         arguments.clear();

         // sync children link
         this.removeAllFromChildren();
      }
   }

   public boolean hasInArguments(TextNode value)
   {
      return((arguments != null) && (value != null) && arguments.contains(value));
   }

   @SuppressWarnings("unchecked")
   public ListIterator<TextNode> iteratorOfArguments()
   {
      return((arguments == null) ? FEmptyListIterator.get() : arguments.listIterator());
   }

   public int sizeOfArguments()
   {
      return((arguments == null) ? 0 : arguments.size());
   }

   public TextNode getFirstOfArguments()
   {
      if(arguments == null)
      {
         return null;
      }
      else
      {
         if(arguments.size() == 0)
         {
            return null;
         }
         return (TextNode) arguments.getFirst();
      }
   }

   public TextNode getLastOfArguments()
   {
      if(arguments == null)
      {
         return null;
      }
      else
      {
         if(arguments.size() == 0)
         {
            return null;
         }
         return (TextNode) arguments.getLast();
      }
   }

   public TextNode getFromArguments(int index)
   {
      if(index >= 0 && index < sizeOfArguments())
      {
         return (TextNode) arguments.get(index);
      }
      else
      {
         throw new IllegalArgumentException("getArgumentsAt(" + index + ")");
      }
   }

   public int indexOfArguments(TextNode value)
   {
      return((arguments == null) ? -1 : arguments.indexOf(value));
   }

   public int indexOfArguments(TextNode value, int index)
   {
      return((arguments == null) ? -1 : arguments.indexOf(value, index));
   }

   public int lastIndexOfArguments(TextNode value)
   {
      return((arguments == null) ? -1 : arguments.lastIndexOf(value));
   }

   public int lastIndexOfArguments(TextNode value, int index)
   {
      return((arguments == null) ? -1 : arguments.lastIndexOf(value, index));
   }

   public boolean isBeforeOfArguments(TextNode leftObject, TextNode rightObject)
   {
      if(arguments == null)
      {
         return false;
      }
      else
      {
         return arguments.isBefore(leftObject, rightObject);
      }
   }

   public boolean isAfterOfArguments(TextNode leftObject, TextNode rightObject)
   {
      if(arguments == null)
      {
         return false;
      }
      else
      {
         return arguments.isAfter(leftObject, rightObject);
      }
   }

   public TextNode getNextOfArguments(TextNode object)
   {
      if(arguments == null)
      {
         return null;
      }
      else
      {
         return (TextNode) arguments.getNextOf(object);
      }
   }

   public TextNode getNextOfArguments(TextNode object, int index)
   {
      if(arguments == null)
      {
         return null;
      }
      else
      {
         return (TextNode) arguments.getNextOf(object, index);
      }
   }

   public TextNode getPreviousOfArguments(TextNode object)
   {
      if(arguments == null)
      {
         return null;
      }
      else
      {
         return (TextNode) arguments.getPreviousOf(object);
      }
   }

   public TextNode getPreviousOfArguments(TextNode object, int index)
   {
      if(arguments == null)
      {
         return null;
      }
      else
      {
         return (TextNode) arguments.getPreviousOf(object, index);
      }
   }

   public boolean addAfterOfArguments(TextNode refObject, TextNode value)
   {
      boolean changed = false;
      if(arguments != null)
      {
         int index = arguments.indexOf(refObject);
         changed = addToArguments(index + 1, value);

         // sync children link
         this.addAfterOfChildren(refObject, value);
      }
      return changed;
   }

   public boolean addBeforeOfArguments(TextNode refObject, TextNode value)
   {
      boolean changed = false;
      if(arguments != null)
      {
         int index = arguments.indexOf(refObject);
         changed = addToArguments(index, value);

         // sync children link
         this.addBeforeOfChildren(refObject, value);
      }
      return changed;
   }

   public boolean addToArguments(int index, TextNode value)
   {
      boolean changed = false;

      if(value != null)
      {
         if(arguments == null)
         {
            arguments = new FLinkedList(); // or FTreeSet () or FLinkedList ()
         }
         int oldIndex = indexOfArguments(value);
         if(oldIndex != index)
         {
            try
            {
               if(oldIndex > -1)
               {
                  arguments.remove(oldIndex);
                  removeFromChildren(oldIndex);
               }
               arguments.add(index, value);

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

   public boolean removeFromArguments(int index)
   {
      boolean changed = false;

      if(arguments != null && (index >= 0 && index < arguments.size()))
      {
         TextNode tmpValue = (TextNode) arguments.remove(index);

         // sync children link
         this.removeFromChildren(index);

         if(tmpValue != null)
         {
            changed = true;
         }
      }
      return changed;
   }

   public boolean removeFromArguments(int index, TextNode value)
   {
      boolean changed = false;

      if((arguments != null) && (value != null) && (index >= 0 && index < arguments.size()))
      {
         TextNode oldValue = (TextNode) arguments.get(index);

         // sync children link
         this.removeFromChildren(index, value);

         if(oldValue == value)
         {
            changed = removeFromArguments(index);
         }
      }
      return changed;
   }

   public ListIterator iteratorOfArguments(TextNode lowerBound)
   {
      ListIterator result = FEmptyListIterator.get();

      if(arguments != null && lowerBound != null)
      {
         int index = arguments.indexOf(lowerBound) + 1;
         result = arguments.listIterator(index);
      }
      else if(arguments != null && lowerBound == null)
      {
         result = arguments.listIterator(0);
      }
      return result;
   }

   public ListIterator iteratorOfArguments(int index)
   {
      return((arguments == null) ? FEmptyListIterator.get() : arguments.listIterator(Math.max(0, Math.min(index,
            arguments.size()))));
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

      for(Iterator<TextNode> iter = iteratorOfArguments(); iter.hasNext();)
      {
         iter.next().removeYou();
      }
      removeAllFromArguments();
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
