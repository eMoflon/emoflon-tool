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
package de.fujaba.text.types;

/**
 * Representation of a specific type of operator.
 * 
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public enum OperatorType
{
   /**
    * Taken if no operator type has been specified.
    */
   DEFAULT(""),

   /**
    * Representation of the <em>plus</em> operator.
    */
   PLUS("+"),

   /**
    * Representation of the <em>minus</em> operator.
    */
   MINUS("-"),

   /**
    * Representation of the <em>multiply</em> operator.
    */
   MULTIPLY("*"),

   /**
    * Representation of the <em>divide</em> operator.
    */
   DIVIDE("/"),

   /**
    * Representation of the <em>modulo</em> operator.
    */
   MODULO("%"),

   /**
    * Representation of the <em>increment</em> operator.
    */
   INCREMENT("++"),

   /**
    * Representation of the <em>decrement</em> operator.
    */
   DECREMENT("--"),

   /**
    * Representation of the <em>equality</em> operator.
    */
   /**  */
   EQUAL("=="),

   /**
    * Representation of the <em>unequal</em> operator.
    */
   /**  */
   UNEQUAL("!="),

   /**
    * Representation of the <em>greater than</em> operator.
    */
   GT(">"),

   /**
    * Representation of the <em>less than</em> operator.
    */
   LT("<"),

   /**
    * Representation of the <em>greater than or equal to</em> operator.
    */
   GE(">="),

   /**
    * Representation of the <em>instanceof</em> operator.
    */
   INSTANCEOF("instanceof"),

   /**
    * Representation of the <em>less than or equal to</em> operator.
    */
   LE("<="),

   /**
    * Representation of the <em>conditional and</em> operator.
    */
   CONDITIONAL_AND("&&"),

   /**
    * Representation of the <em>conditional or</em> operator.
    */
   CONDITIONAL_OR("||"),

   /**
    * Representation of the <em>inclusive and</em> operator.
    */
   INCLUSIVE_AND("&"),

   /**
    * Representation of the <em>inclusive or</em> operator.
    */
   INCLUSIVE_OR("|"),

   /**
    * Representation of the <em>exclusive or</em> operator.
    */
   EXCLUSIVE_OR("^"),

   /**
    * Representation of the <em>left bitshift</em> operator.
    */
   LSHIFT("<<"),

   /**
    * Representation of the <em>right bitshift</em> operator.
    */
   RSHIFT(">>"),

   /**
    * Representation of the <em>unsigned right bitshift</em> operator.
    */
   URSHIFT(">>>"),

   /**
    * Representation of the assignment operator.
    */
   ASSIGN("="),

   /**
    * Representation of the <em>add and assign</em> operator.
    */
   ASSIGN_ADD("+="),

   /**
    * Representation of the <em>subtract and assign</em> operator.
    * 
    */
   ASSIGN_SUB("-="),

   /**
    * Representation of the <em>divide and assign</em> operator.
    * 
    */
   ASSIGN_DIV("/="),

   /**
    * Representation of the <em>multiply and assign</em> operator.
    */
   ASSIGN_MUL("*="),

   /**
    * Representation of the <em>modulo and assign</em> operator.
    */
   ASSIGN_MOD("%=");

   /**
    * Create an OperatorType instance with the given image.
    * 
    * @param image The image of this operator.
    */
   private OperatorType(String image)
   {
      this.image = image;
   }

   /**
    * The image of this enum instance.
    */
   private final String image;

   /**
    * Obtain the image of this enum instance.
    * 
    * @return The image of this enum instance.
    */
   public String getImage()
   {
      return image;
   }

   /**
    * The String representation of this OperatorType is simply its image.
    */
   @Override
   public String toString()
   {
      return getImage();
   }
}
