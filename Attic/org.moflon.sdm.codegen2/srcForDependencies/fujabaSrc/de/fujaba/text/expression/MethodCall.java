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
import de.fujaba.text.types.MethodType;
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
public class MethodCall extends TextNode
{
   /**
    * 
    * @param parsedElement 
    * @param name
    * @param arguments
    */
   public MethodCall(FParsedElement parsedElement, String name, Arguments arguments)
   {
      super(parsedElement);
      setName(name);
      setArguments(arguments);
      setKind(MethodType.NORMAL);
   }

   /**
    * 
    */
   private String name;

   @Override
   public void setName(String value)
   {
      this.name = value;
   }

   @Override
   public String getName()
   {
      String result = null;

      final String prefix = kind.getPrefix();
      final FTextReference refElem = getReferencedElement();

      if(refElem != null && refElem instanceof FElement)
      {
         final String tmp = ((FElement) refElem).getName();

         if(prefix != null && prefix.length() > 0)
         {
            result = prefix + tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
         }
         else
         {
            result = tmp;
         }
      }
      else
      {
         result = name;
      }

      return result;
   }

   /**
    * The kind of method that this node represents (some accessor or regular).
    */
   private MethodType kind;

   public MethodType getKind()
   {
      return this.kind;
   }

   public void setKind(MethodType value)
   {
      this.kind = value;
   }

   /**
    * <pre>
    *           0..1     arguments     0..1 
    * Arguments ------------------------- MethodCall
    *           arguments      methodCall
    * </pre>
    */
   private Arguments arguments;

   public boolean setArguments(Arguments value)
   {
      boolean changed = false;

      if(this.arguments != value)
      {
         Arguments oldValue = this.arguments;
         this.arguments = value;

         // sync children link
         this.removeFromChildren(oldValue);
         this.addToChildren(value);

         changed = true;
      }
      return changed;
   }

   public Arguments getArguments()
   {
      return this.arguments;
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

      if(this.arguments != null)
      {
         this.arguments.removeYou();
         this.arguments = null;
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
