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
package de.fujaba.text;

import java.util.Iterator;
import java.util.ListIterator;

import de.fujaba.text.visitor.Visitable;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.asg.ASTNode;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.upb.tools.fca.FEmptyListIterator;
import de.upb.tools.fca.FLinkedList;

/**
 * Superclass to all syntax node classes used by the TextParser plugin.
 * 
 * <pre>
 *     0..n     referencedElement     0..1
 * TextNode ------------------------- FTextReference
 *      textUsages               referencedElement
 * </pre>
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public abstract class TextNode extends ASGElement implements Iterable<TextNode>, Visitable
{

   /**
    * Property name denoting the event that removeYou()
    * is just about to be executed for a TextNode instance.
    * If any tasks are to be executed before the members of
    * the node are reset, it is possible to register a listener
    * on this property.
    * 
    * Note: an according event is currently triggered only by Declaration.
    * @see de.fujaba.text.expression.Declaration
    */
   public static final String REMOVE_YOU_STARTED = "removeYouStarted";

   /**
    * A map that stores the text element parts classified as "special tokens"
    * by the parser, i.e. whitespace and comments.
    */
   private final SpecialTokenUtility specialTokenUtility = new SpecialTokenUtility();

   public SpecialTokenUtility getSpecialTokenUtility()
   {
      return this.specialTokenUtility;
   }

   /**
    * The begin line of the text fragment
    * represented by this node.
    */
   private int beginLine;

   public int getBeginLine()
   {
      return this.beginLine;
   }

   public void setBeginLine(final int beginLine)
   {
      this.beginLine = beginLine;
   }

   /**
    * The begin column of the text fragment
    * represented by this node.
    */
   private int beginColumn;

   public int getBeginColumn()
   {
      return this.beginColumn;
   }

   public void setBeginColumn(final int beginColumn)
   {
      this.beginColumn = beginColumn;
   }

   /**
    * The end line of the text fragment
    * represented by this node.
    */
   private int endLine;

   public int getEndLine()
   {
      return this.endLine;
   }

   public void setEndLine(final int endLine)
   {
      this.endLine = endLine;
   }

   /**
    * The begin column of the text fragment
    * represented by this node.
    */
   private int endColumn;

   public int getEndColumn()
   {
      return this.endColumn;
   }

   public void setEndColumn(final int endColumn)
   {
      this.endColumn = endColumn;
   }

   /**
    * Implementation of the Iterable Interface
    */
   public Iterator<TextNode> iterator()
   {
      return this.iteratorOfChildren();
   }

   @Override
   public FElement getParentElement()
   {
      return this.getParent();
   }

   /**
    * Constructor for TextNode.
    * 
    * @param parsedElement
    */
   protected TextNode(final FParsedElement parsedElement)
   {
      super(parsedElement.getProject(), false);
      this.parsedElement = parsedElement;
   }

   /**
    * <pre>
    *        0..n     referencedElement     0..1
    * UMLTextNode ------------------------- FTextReference
    *         textUsages               referencedElement
    * </pre>
    */
   private FTextReference referencedElement;

   public boolean setReferencedElement(final FTextReference value)
   {
      boolean changed = false;

      if(this.referencedElement != value)
      {
         final FTextReference oldValue = this.referencedElement;
         if(this.referencedElement != null)
         {
            this.referencedElement = null;
            oldValue.removeFromTextUsages(this);
         }
         this.referencedElement = value;

         if(value != null)
         {
            value.addToTextUsages(this);
         }
         changed = true;
      }
      return changed;
   }

   public FTextReference getReferencedElement()
   {
      return this.referencedElement;
   }

   /**
    * <pre>
    *           0..1     type     0..1 
    * FType ------------------------- UMLTextNode
    *           type      
    * </pre>
    */
   private FType type;

   public boolean setType(final FType value)
   {
      boolean changed = false;

      if(this.type != value)
      {
         this.type = value;
         changed = true;
      }
      return changed;
   }

   public FType getType()
   {
      return this.type;
   }

   /**
    * The model element that this node belongs to.
    */
   private final FParsedElement parsedElement;

   public FParsedElement getParsedElement()
   {
      return this.parsedElement;
   }

   /**
    * <pre>
    *           0..1     children     0..n 
    * UMLTextNode ------------------------- UMLTextNode
    *           parent      children
    * </pre>
    */
   private TextNode parent;

   public boolean setParent(final TextNode value)
   {
      boolean changed = false;

      if(this.parent != value)
      {
         final TextNode oldValue = this.parent;
         if(this.parent != null)
         {
            this.parent = null;
            oldValue.removeFromChildren(this);
         }
         this.parent = value;

         if(value != null)
         {
            value.addToChildren(this);
         }
         changed = true;
      }
      return changed;
   }

   public TextNode getParent()
   {
      return this.parent;
   }

   /**
    * Obtain the 'root' TextNode instance of this
    * TextNode's model tree.
    * 
    * @return
    */
   public TextNode getTopLevelParent()
   {
      final TextNode parent = this.getParent();
      if(parent != null)
      {
         return parent.getTopLevelParent();
      }
      else
      {
         return this;
      }
   }

   /**
    * <pre>
    *           0..n     children     0..1 
    * UMLTextNode ------------------------- UMLTextNode
    *           children      parent
    * </pre>
    */
   private FLinkedList children;

   public boolean addToChildren(final TextNode value)
   {
      boolean changed = false;

      if((value != null) && !this.hasInChildren(value))
      {
         if(this.children == null)
         {
            this.children = new FLinkedList();
         }
         changed = this.children.add(value);
         if(changed)
         {
            value.setParent(this);
         }
      }
      return changed;
   }

   public boolean removeFromChildren(final TextNode value)
   {
      boolean changed = false;

      if((this.children != null) && (value != null))
      {
         changed = this.children.remove(value);
         if(changed)
         {
            value.setParent(null);
         }
      }
      return changed;
   }

   public void removeAllFromChildren()
   {
      TextNode tmpValue;
      final Iterator iter = this.iteratorOfChildren();

      while(iter.hasNext())
      {
         tmpValue = (TextNode) iter.next();
         this.removeFromChildren(tmpValue);
      }
   }

   public boolean hasInChildren(final TextNode value)
   {
      return((this.children != null) && (value != null) && this.children.contains(value));
   }

   public Iterator<TextNode> iteratorOfChildren()
   {
      return((this.children == null) ? FEmptyListIterator.get() : this.children.listIterator());
   }

   public int sizeOfChildren()
   {
      return((this.children == null) ? 0 : this.children.size());
   }

   public TextNode getFirstOfChildren()
   {
      if(this.children == null)
      {
         return null;
      }
      else
      {
         if(this.children.size() == 0)
         {
            return null;
         }
         return (TextNode) this.children.getFirst();
      }
   }

   public TextNode getLastOfChildren()
   {
      if(this.children == null)
      {
         return null;
      }
      else
      {
         if(this.children.size() == 0)
         {
            return null;
         }
         return (TextNode) this.children.getLast();
      }
   }

   public TextNode getFromChildren(final int index)
   {
      if((index >= 0) && (index < this.sizeOfChildren()))
      {
         return (TextNode) this.children.get(index);
      }
      else
      {
         throw new IllegalArgumentException("getChildrenAt(" + index + ")");
      }
   }

   public int indexOfChildren(final TextNode value)
   {
      return((this.children == null) ? -1 : this.children.indexOf(value));
   }

   public int indexOfChildren(final TextNode value, final int index)
   {
      return((this.children == null) ? -1 : this.children.indexOf(value, index));
   }

   public int lastIndexOfChildren(final TextNode value)
   {
      return((this.children == null) ? -1 : this.children.lastIndexOf(value));
   }

   public int lastIndexOfChildren(final TextNode value, final int index)
   {
      return((this.children == null) ? -1 : this.children.lastIndexOf(value, index));
   }

   public boolean isBeforeOfChildren(final TextNode leftObject, final TextNode rightObject)
   {
      if(this.children == null)
      {
         return false;
      }
      else
      {
         return this.children.isBefore(leftObject, rightObject);
      }
   }

   public boolean isAfterOfChildren(final TextNode leftObject, final TextNode rightObject)
   {
      if(this.children == null)
      {
         return false;
      }
      else
      {
         return this.children.isAfter(leftObject, rightObject);
      }
   }

   public TextNode getNextOfChildren(final TextNode object)
   {
      if(this.children == null)
      {
         return null;
      }
      else
      {
         return (TextNode) this.children.getNextOf(object);
      }
   }

   public TextNode getNextOfChildren(final TextNode object, final int index)
   {
      if(this.children == null)
      {
         return null;
      }
      else
      {
         return (TextNode) this.children.getNextOf(object, index);
      }
   }

   public TextNode getPreviousOfChildren(final TextNode object)
   {
      if(this.children == null)
      {
         return null;
      }
      else
      {
         return (TextNode) this.children.getPreviousOf(object);
      }
   }

   public TextNode getPreviousOfChildren(final TextNode object, final int index)
   {
      if(this.children == null)
      {
         return null;
      }
      else
      {
         return (TextNode) this.children.getPreviousOf(object, index);
      }
   }

   public boolean addAfterOfChildren(final TextNode refObject, final TextNode value)
   {
      boolean changed = false;
      if(this.children != null)
      {
         final int index = this.children.indexOf(refObject);
         changed = this.addToChildren(index + 1, value);
      }
      return changed;
   }

   public boolean addBeforeOfChildren(final TextNode refObject, final TextNode value)
   {
      boolean changed = false;
      if(this.children != null)
      {
         final int index = this.children.indexOf(refObject);
         changed = this.addToChildren(index, value);
      }
      return changed;
   }

   public boolean addToChildren(final int index, final TextNode value)
   {
      boolean changed = false;

      if(value != null)
      {
         if(this.children == null)
         {
            this.children = new FLinkedList();
         }
         final int oldIndex = this.indexOfChildren(value);
         if(oldIndex != index)
         {
            try
            {
               if(oldIndex > -1)
               {
                  this.children.remove(oldIndex);
               }
               this.children.add(index, value);
               if(oldIndex < 0)
               {
                  value.setParent(this);
               }
               changed = true;
            }
            catch(final IndexOutOfBoundsException ex)
            {
               return false;
            }
         }
      }
      return changed;
   }

   public boolean setInChildren(final int index, final TextNode value)
   {
      boolean changed = false;

      if(value != null)
      {
         if(this.children == null)
         {
            this.children = new FLinkedList();
         }
         final int oldIndex = this.indexOfChildren(value);
         if(oldIndex != index)
         {
            try
            {
               final TextNode oldValue = (TextNode) this.children.set(index, value);
               if(oldIndex > -1)
               {
                  this.children.remove(oldIndex);
               }
               if(oldValue != value)
               {
                  if(oldValue != null)
                  {
                     oldValue.setParent(null);
                  }
                  if(oldIndex < 0)
                  {
                     value.setParent(this);
                  }
                  changed = true;
               }
            }
            catch(final IndexOutOfBoundsException ex)
            {
               return false;
            }
         }
      }
      return changed;
   }

   public boolean removeFromChildren(final int index)
   {
      boolean changed = false;

      if((this.children != null) && ((index >= 0) && (index < this.children.size())))
      {
         final TextNode tmpValue = (TextNode) this.children.remove(index);
         if(tmpValue != null)
         {
            tmpValue.setParent(null);
            changed = true;
         }
      }
      return changed;
   }

   public boolean removeFromChildren(final int index, final TextNode value)
   {
      boolean changed = false;

      if((this.children != null) && (value != null) && ((index >= 0) && (index < this.children.size())))
      {
         final TextNode oldValue = (TextNode) this.children.get(index);
         if(oldValue == value)
         {
            changed = this.removeFromChildren(index);
         }
      }
      return changed;
   }

   public Iterator iteratorOfChildren(final TextNode lowerBound)
   {
      ListIterator result = FEmptyListIterator.get();

      if((this.children != null) && (lowerBound != null))
      {
         final int index = this.children.indexOf(lowerBound) + 1;
         result = this.children.listIterator(index);
      }
      else if((this.children != null) && (lowerBound == null))
      {
         result = this.children.listIterator(0);
      }
      return result;
   }

   public Iterator iteratorOfChildren(final int index)
   {
      return((this.children == null) ? FEmptyListIterator.get() : this.children.listIterator(Math.max(0, Math.min(
            index, this.children.size()))));
   }

   @Override
   public void removeYou()
   {
      for(final Iterator iter = this.iteratorOfChildren(); iter.hasNext();)
      {
         final TextNode child = (TextNode) iter.next();
         child.removeYou();
      }

      this.setReferencedElement(null);
      this.setType(null);
      this.setParent(null);
      this.removeAllFromChildren();

      super.removeYou();
   }

}
