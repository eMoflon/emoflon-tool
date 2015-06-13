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
import de.fujaba.text.types.LiteralType;
import de.fujaba.text.visitor.ArgVisitor;
import de.fujaba.text.visitor.ArgVoidVisitor;
import de.fujaba.text.visitor.NoArgVisitor;
import de.fujaba.text.visitor.NoArgVoidVisitor;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public class NumberLiteral extends Literal
{
   /**
    * 
    * @param project
    * @param image
    */
   public NumberLiteral(FParsedElement parsedElement, String image)
   {
      super(parsedElement, LiteralType.NUMBER, image);

      FFactory<FBaseType> baseTypeFactory = getProject().getFromFactories(FBaseType.class);

      try
      {
         if(image.contains(".") || image.contains("e") || image.contains("E"))
         {
            setValue(Double.valueOf(image));
            setType(baseTypeFactory.getFromProducts(FBaseType.DOUBLE));
         }
         else
         {
            long l = Long.valueOf(image);
            if(l > Integer.MAX_VALUE)
            {
               setValue(l);
               setType(baseTypeFactory.getFromProducts(FBaseType.LONG_INTEGER));
            }
            else
            {
               setValue(Integer.valueOf(image));
               setType(baseTypeFactory.getFromProducts(FBaseType.INTEGER));
            }
         }
      }
      catch(NumberFormatException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * 
    */
   private Number value;

   public void setValue(Number value)
   {
      this.value = value;
   }

   public Number getValue()
   {
      return this.value;
   }

   /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * 
    * Visitable implementation.
    * 
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

   @Override
   public void accept(NoArgVoidVisitor v)
   {
      v.visit(this);
   }

   @Override
   public <R, A> R accept(ArgVisitor<R, A> v, A argu)
   {
      return v.visit(this, argu);
   }

   @Override
   public <R> R accept(NoArgVisitor<R> v)
   {
      return v.visit(this);
   }

   @Override
   public <A> void accept(ArgVoidVisitor<A> v, A argu)
   {
      v.visit(this, argu);
   }

}
