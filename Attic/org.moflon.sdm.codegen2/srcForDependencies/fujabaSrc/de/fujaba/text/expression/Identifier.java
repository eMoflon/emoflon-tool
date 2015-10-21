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
import de.fujaba.text.FTextReference;
import de.fujaba.text.TextNode;
import de.fujaba.text.types.IdentifierType;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;
import de.uni_paderborn.fujaba.metamodel.common.FElement;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class Identifier extends ImageNode
{
   /**
    * 
    * @param parsedElement
    */
   public Identifier(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   /**
    * Returns the image of this identifier, but with its first letter
    * changed to uppercase. This can be used to form method names like 'getXxx()'
    * or 'setYyy(...)' from attribute names like 'xxx' and 'yyy'.
    * 
    * @return the image of this node, with the first letter capitalized.
    */
   public String getUpperCaseImage()
   {
      String image = null;
      try
      {
         image = getImage();
      }
      catch(IllegalStateException e)
      {
      }

      if(image != null)
      {
         return image.substring(0, 1).toUpperCase() + image.substring(1);
      }
      else
      {
         FTextReference ref = getReferencedElement();
         if(ref == null)
         {
            throw new IllegalStateException("Identifier has neither image nor referenced element.");
         }
         FElement fElem = null;
         if(ref instanceof FElement)
         {
            fElem = (FElement) ref;
         }
         else return "n.a.";

         image = fElem.getName();
         if(image == null)
         {
            throw new IllegalStateException("Referenced element of Identifier has no name.");
         }

         return image.substring(0, 1).toUpperCase() + image.substring(1);
      }
   }

   /**
    * 
    */
   private IdentifierType kind = IdentifierType.UNKNOWN;

   public void setKind(IdentifierType value)
   {
      this.kind = value;
   }

   public IdentifierType getKind()
   {
      return this.kind;
   }

   /**
    * 
    */
   private ArrayAccessorList arrayAccessors;

   public boolean setArrayAccessors(ArrayAccessorList value)
   {
      boolean changed = false;

      if(this.arrayAccessors != value)
      {
         ArrayAccessorList oldValue = this.arrayAccessors;
         this.arrayAccessors = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(0, value);

         changed = true;
      }
      return changed;
   }

   public ArrayAccessorList getArrayAccessors()
   {
      return this.arrayAccessors;
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

      if(this.getArrayAccessors() != null)
      {
         this.getArrayAccessors().removeYou();
         this.setArrayAccessors(null);
      }

      super.removeYou();
   }

   /**
    * Obtain the String image of this <tt>Identifier</tt> node.
    * 
    * If the image of this node is "this" or "super", then this image
    * String is returned in any case, even if there is a referenced element
    * (otherwise, an expression like <tt>super.foo()</tt> would be printed as
    * <tt>SuperClass.foo()</tt>).
    * 
    * Next, if this node has a referenced element, this element is cast to FElement and
    * asked for its name.
    * 
    * Otherwise, the value of the <tt>image</tt> field is returned.
    * 
    * @throws IllegalStateException if <tt>image</tt> field is null.
    * @return the String image of this <tt>Identifier</tt> node (or its referenced element).
    */
   public String obtainImage()
   {
      /*
       * Check for "this" and "super" and do
       * not return the referenced element's name.
       */
      if(getKind() == IdentifierType.THIS)
      {
         return this.getImage();
      }
      if(getKind() == IdentifierType.SUPER)
      {
         return this.getImage();
      }

      /*
       * Look for the referenced element's name.
       */
      final FTextReference ref = getReferencedElement();
      if(ref instanceof FElement)
      {
         final FElement fElem = (FElement) ref;
         return fElem.getName();
      }

      /*
       * An image node should never be without an image ...
       */
      if(getImage() == null)
      {
         throw new IllegalStateException("Identifier node has neither a named referenced element nor a String image.");
      }

      /*
       * Return image field value
       */
      return getImage();
   }

   @Override
   public String getName()
   {
      return obtainImage();
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
