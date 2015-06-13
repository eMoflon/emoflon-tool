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

import de.fujaba.text.FParsedElement;
import de.fujaba.text.TextNode;
import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FType;

/**
 * 
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public abstract class NumericOperation extends BinaryOperation
{

   public NumericOperation(FParsedElement parsedElement)
   {
      super(parsedElement);
   }

   public static final int BYTE = 0;

   public static final int SHORT = 1;

   public static final int INTEGER = 2;

   public static final int LONG = 3;

   public static final int FLOAT = 4;

   public static final int DOUBLE = 5;

   public static final int CHARACTER = 6;

   public static final int STRING = 7;

   public static final int BOOLEAN = 8;

   public static final int CONSTRUCTOR = 9;

   public static final int SMALLEST = BYTE;

   public static final int BIGGEST = CONSTRUCTOR;

   /**
    * Auxiliary method to find out the resulting type of e.g. an additive or
    * multiplicative expression.<br>
    * <br>
    * An FNode with the additive expression 123l + 456.7f, for example, would be marked
    * as a float expression, because long values are a subset of float.<br>
    * <br>
    * Works also with String and char additions.
    * 
    * @param node
    * @return The 'biggest' base type of the given node's children.
    */
   public FType determineType()
   {
      FType result = null;

      FFactory<FBaseType> baseTypeFactory = getProject().getFromFactories(FBaseType.class);

      LinkedList<FType> baseTypes = new LinkedList<FType>();
      baseTypes.add(BYTE, baseTypeFactory.getFromProducts(FBaseType.BYTE));
      baseTypes.add(SHORT, baseTypeFactory.getFromProducts(FBaseType.SHORT_INTEGER));
      baseTypes.add(INTEGER, baseTypeFactory.getFromProducts(FBaseType.INTEGER));
      baseTypes.add(LONG, baseTypeFactory.getFromProducts(FBaseType.LONG_INTEGER));
      baseTypes.add(FLOAT, baseTypeFactory.getFromProducts(FBaseType.FLOAT));
      baseTypes.add(DOUBLE, baseTypeFactory.getFromProducts(FBaseType.DOUBLE));
      baseTypes.add(CHARACTER, baseTypeFactory.getFromProducts(FBaseType.CHARACTER));
      baseTypes.add(STRING, baseTypeFactory.getFromProducts(FBaseType.STRING));
      baseTypes.add(BOOLEAN, baseTypeFactory.getFromProducts(FBaseType.BOOLEAN));
      baseTypes.add(CONSTRUCTOR, baseTypeFactory.getFromProducts(FBaseType.CONSTRUCTOR));

      LinkedList<FType> numericBaseTypes = new LinkedList<FType>(baseTypes.subList(SMALLEST, STRING + 1));

      // iterate the numericBaseTypes list reverse
      int counter = STRING;
      FType type = null;
      while(counter > 0)
      {
         type = numericBaseTypes.get(counter);
         for(Iterator i = iteratorOfChildren(); i.hasNext();)
         {
            TextNode operand = (TextNode) i.next();

            // skip operators
            if(operand instanceof Operator)
               continue;

            if(operand.getType() == type)
            {
               result = type;
               break;
            }
         }
         counter--;
      }
      if(result == null)
      {
         throw new RuntimeExceptionWithContext("Determination of resulting type in arithmetic operation '" + toString()
               + "' failed.", getParsedElement());
      }

      return result;
   }

   @Override
   public void removeYou()
   {
      super.removeYou();
   }

}
