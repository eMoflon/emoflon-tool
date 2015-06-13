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
package de.fujaba.preferences;

import java.util.Vector;


public class VectorConverter
{

   // This String is required to convert Strings written by a previous version
   // of this class
   private static final String OLD_DELIM = "#:#next#:#";
   
   private static final char DELIM = ';';
   private static final char ESCAPE_CHAR = '\\';
   
   /**
    * convert a preference value given as String into a vector 
    * @param value the String representation of a preference value 
    * @return the vector of Strings represented by value
    */
   public static Vector<String> parseString (String value) {
      Vector<String> result = new Vector<String>();
      if (value != null)
      {
         if (value.endsWith(OLD_DELIM)) {
            return parseStringInOldFormat(value);
         } else {
            result = ConverterUtil.splitString(value, DELIM, ESCAPE_CHAR, true);
         }
      }
      return result;
   }

   /**
    * 
    * @deprecated method to parse Strings in the old format. 
    * Will be removed in Fujaba 5.2
    */
   private static Vector<String> parseStringInOldFormat (String value) {
      Vector<String> result = new Vector<String>();
      if (value != null)
      {
         int minIndex = 0;
         int currentIndex = value.indexOf(OLD_DELIM);
         while (currentIndex >= 0) {
            String next = value.substring(minIndex, currentIndex);
            if (next != null && ! next.equals("")) {
               result.add(next);
            }
            minIndex = currentIndex + OLD_DELIM.length();
            currentIndex = value.indexOf(OLD_DELIM, minIndex);
         }
      }
      return result;
   }

   /**
    * convert a vector into a String to store it in a PreferenceStore
    * @param vector the vector to be stored
    * @return a String representation, which can be used as a property 
    * value and stored by a PreferenceStore
    */
   public static String toString (Vector<String> vector)
   {
      if (vector == null)
         return null;
      return ConverterUtil.joinStrings(vector, DELIM, ESCAPE_CHAR,true);
   }
}
