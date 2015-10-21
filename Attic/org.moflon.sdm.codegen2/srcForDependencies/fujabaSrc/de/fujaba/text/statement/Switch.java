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
import de.fujaba.text.expression.Brackets;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class Switch extends StatementNode
{

   /**
    * 
    * @param parsedElement
    */
   public Switch(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   /**
    * 
    */
   private Brackets expression;

   public Brackets getExpression()
   {
      return expression;
   }

   /**
    * 
    * @param value
    * @return
    */
   public boolean setExpression(Brackets value)
   {
      boolean changed = false;

      if(this.expression != value)
      {
         final Brackets oldValue = this.expression;
         this.expression = value;

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
   private List<CaseOrDefaultStatement> statements;

   /**
    * 
    * @param value
    * @return
    */
   public boolean addToStatements(final CaseOrDefaultStatement value)
   {
      if(value == null)
      {
         return false;
      }

      boolean changed = this.addToChildren(value);
      if(!changed)
      {
         return false;
      }

      if(this.statements == null)
      {
         this.statements = new ArrayList<CaseOrDefaultStatement>();
      }

      this.statements.add(value);

      return changed;
   }

   /**
    * 
    * @return
    */
   public Iterator<CaseOrDefaultStatement> iteratorOfStatements()
   {
      if(this.statements == null)
      {
         this.statements = new ArrayList<CaseOrDefaultStatement>();
      }
      return this.statements.iterator();
   }

   /**
    * 
    * @param value
    * @return
    */
   public boolean removeFromStatements(CaseOrDefaultStatement value)
   {
      if(this.statements == null || !this.statements.contains(value))
      {
         return false;
      }

      boolean changed = this.removeFromChildren(value);
      if(changed)
      {
         this.statements.remove(value);
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

      if(this.expression != null)
      {
         this.expression.removeYou();
         this.expression = null;
      }

      if(this.statements != null)
      {
         for(final CaseOrDefaultStatement cods : this.statements)
         {
            cods.removeYou();
         }
         this.statements.clear();
         this.statements = null;
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
