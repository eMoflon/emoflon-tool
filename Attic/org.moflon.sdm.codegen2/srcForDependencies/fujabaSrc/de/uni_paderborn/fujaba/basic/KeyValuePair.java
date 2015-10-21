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
package de.uni_paderborn.fujaba.basic;

import de.uni_paderborn.fujaba.metamodel.common.FElement;

import java.util.Map;


/**
 * A record of a BasicIncrement and a String.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class KeyValuePair implements Map.Entry
{

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private Object value;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private Object key;


   /**
    * Constructor for class KeyValuePair
    *
    * @param key    No description provided
    * @param value  No description provided
    */
   public KeyValuePair (String key, Object value)
   {
      this.value = value;
      this.key = key;
   }


   /**
    * Constructor for class KeyValuePair
    *
    * @param key    No description provided
    * @param value  No description provided
    */
   public KeyValuePair (FElement key, FElement value)
   {
      this.value = value;
      this.key = key;
   }


   /**
    * Get the value attribute of the KeyValuePair object
    *
    * @return   The value value
    */
   public Object getValue()
   {
      return value;
   }


   /**
    * Get the key attribute of the KeyValuePair object
    *
    * @return   The key value
    */
   public Object getKey()
   {
      return key;
   }


   /**
    * Sets the value attribute of the KeyValuePair object
    *
    * @param value  The new value value
    * @return       No description provided
    */
   public Object setValue (Object value)
   {
      throw new UnsupportedOperationException();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Override
   public String toString()
   {
      return "KeyValuePair[key=" + getKey() + ",value=" + getValue() + "]";
   }

}

/*
 * $Log$
 * Revision 1.18  2006/04/06 12:04:34  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.17  2006/03/01 12:22:17  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */
