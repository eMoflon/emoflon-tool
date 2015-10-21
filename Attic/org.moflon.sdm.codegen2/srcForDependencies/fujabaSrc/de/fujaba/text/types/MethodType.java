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
 * 
 * @see IdentifierType
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public enum MethodType
{
   /**
    * Used for a normal method which is not an
    * accessor method.
    */
   NORMAL(""),

   /*
    * Accessor method types
    */

   /**
    * TODO better ATTRIBUTE and ROLE ?.
    */
   GETTER("get"),

   /**
    * The represented method is a setter method.
    */
   SETTER("set"),

   /**
    * The represented method is a boolean getter method (prefix 'is').
    */
   BOOL_GETTER("is"),

   /**
    * The represented method is an iterator method ('iteratorOf').
    */
   ITERATOR("iteratorOf"),

   /**
    * The represented method is an addTo method.
    */
   ADDTO("addTo"),

   /**
    * The represented method is a removeFrom method.
    */
   REMOVE("removeFrom"),

   /**
    * The represented method is a hasIn method.
    */
   HAS_IN("hasIn"),

   /**
    * The represented method is a sizeOf method.
    */
   SIZE_OF("sizeOf");

   /**
    * 
    * @param prefix
    */
   private MethodType(final String prefix)
   {
      this.prefix = prefix;
   }

   /**
    * The prefix of the represented accessor method.
    */
   private final String prefix;

   /**
    * 
    * @return
    */
   public String getPrefix()
   {
      return this.prefix;
   }

}
