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
 * @author patrick.oppermann@cs.uni-kassel.de
 */
public enum IdentifierType
{

   /**
    * 
    */
   UNKNOWN,

   /**
    * Results in an unchanged code representation of the identifier,
    * with no prefixes or suffixes added by the template. Only the identifier's
    * image and its optional array access construct will be used as source code.
    */
   PLAIN,

   /*
    * Values set by the search handlers
    */

   /**
    * Represents a reading attribute access, so a
    * 'get' will be added in the generated code,
    * and the first letter of the attribute's name
    * will be capitalized.
    */
   ATTRIBUTE,

   /**
    * Represents a method parameter access.
    */
   PARAMETER,

   /**
    * Local variable access.
    */
   LOCALVAR,

   /**
    * The identifier's name is 'this'.
    */
   THIS,

   /**
    * The identifier's name is 'super'.
    */
   SUPER,

   /**
    * Represents a reading role access, so a
    * 'get' will be added in the generated code,
    * and the first letter of the attribute's name
    * will be capitalized.
    */
   ROLE,

   /**
    * The identifier references a class by its name.
    */
   CLASS,

   /**
    * Not used so far (see LOCALVAR).
    */
   OBJECT,

   /**
    * The identifier references a package by its name.
    */
   PACKAGE,

   /*
    * Base Types
    */

   /**
    * This identifier represents the base type <code>boolean</code>.
    */
   BOOLEAN,

   /**
    * This identifier represents the base type <code>byte</code>.
    * 
    */
   BYTE,

   /**
    * This identifier represents the base type <code>short</code>.
    * 
    */
   SHORT,

   /**
    * This identifier represents the base type <code>int</code>.
    * 
    */
   INT,

   /**
    * This identifier represents the base type <code>long</code>.
    * 
    */
   LONG,

   /**
    * This identifier represents the base type <code>float</code>.
    * 
    */
   FLOAT,

   /**
    * This identifier represents the base type <code>double</code>.
    * 
    */
   DOUBLE,

   /**
    * This identifier represents the base type <code>char</code>.
    * 
    */
   CHAR,

   /**
    * This identifier represents the base type <code>String</code>.
    * 
    */
   STRING,

   /*
    * Accessor method types
    */

   /**
    * Not used so far (see ATTRIBUTE and ROLE).
    */
   GETTER,

   /**
    * Identifier represents a setter method.
    */
   SETTER,

   /**
    * Identifier represents a boolean getter method (prefix 'is').
    */
   BOOL_GETTER,

   /**
    * Identifier represents an iterator method ('iteratorOf').
    */
   ITERATOR,

   /**
    * Identifier represents an addTo method.
    */
   ADDTO,

   /**
    * Identifier represents a removeFrom method.
    */
   REMOVE,

   /**
    * Identifier Represents a hasIn method.
    */
   HAS_IN,

   /**
    * Identifier represents a sizeOf method.
    */
   SIZE_OF;

}
