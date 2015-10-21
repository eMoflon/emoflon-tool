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
package de.uni_paderborn.tools.util;

import java.util.HashSet;


/**
 * A class that contains some text-based utilities for fujaba.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public final class TextUtil
{
   /**
    * A HashSet containing all Java-Keywords.
    */
   private static HashSet<String> set;

   static
   {
      set = new HashSet<String>();

      set.add ("abstract");
      set.add ("boolean");
      set.add ("break");
      set.add ("byte");
      set.add ("case");
      set.add ("catch");
      set.add ("char");
      set.add ("class");
      set.add ("const");
      set.add ("continue");
      set.add ("default");
      set.add ("do");
      set.add ("double");
      set.add ("else");
      set.add ("extends");
      set.add ("final");
      set.add ("finally");
      set.add ("float");
      set.add ("for");
      set.add ("goto");
      set.add ("if");
      set.add ("implements");
      set.add ("import");
      set.add ("instanceof");
      set.add ("int");
      set.add ("interface");
      set.add ("long");
      set.add ("native");
      set.add ("new");
      set.add ("package");
      set.add ("private");
      set.add ("protected");
      set.add ("public");
      set.add ("return");
      set.add ("short");
      set.add ("static");
      set.add ("strictfp");
      set.add ("super");
      set.add ("switch");
      set.add ("synchronized");
      set.add ("this");
      set.add ("throw");
      set.add ("throws");
      set.add ("transient");
      set.add ("try");
      set.add ("void");
      set.add ("volatile");
      set.add ("while");
   }


   /**
    * Impossible instance ;o)
    */
   private TextUtil() { }


   /**
    * This method returns <code>true</code> if the provided String represents one of the Java
    * keywords as defined in the language specification. It returns <code>true</code> as well,
    * if the String represents a Java literal, which are "true", "false" and "null". If the
    * String itself <i>is</i> null, false is returned. When the String is compared, it has
    * to match cases. All Keywords and literals are lower-case-words.<br>
    * This method returns the same as <code>isJavaKeyword(str, true, true)</code>. <p>
    *
    * Note:<br>
    * Beginning spaces and spaces at the end will be ignored because of invoking the trim()-method
    * on this String.
    *
    * @param str  The String to be possibly a Java keyword.
    * @return     <code>true</code>, if the provided String represents a Java keyword or Java
    *      literal, <code>false</code> otherwise.
    */
   public static boolean isJavaKeyword (String str)
   {
      return isJavaKeyword (str, true, true);
   }


   /**
    * This method returns <code>true</code> if the provided String represents one of the Java
    * keywords as defined in the language specification. By setting the boolean value <code>literalsIncluded</code>
    * to true, it returns <code>true</code> as well, if the String represents a Java literal,
    * which are "true", "false" and "null". If the String itself <i>is</i> null, false is returned.
    * When the String is compared, it has to match cases. All Keywords and literals are lower-case-words.
    * <br>
    * This method returns the same as <code>isJavaKeyword(str, true, true)</code>. <p>
    *
    * Note:<br>
    * Beginning spaces and spaces at the end will be ignored because of invoking the trim()-method
    * on this String.
    *
    * @param str               The String to be possibly a Java keyword.
    * @param literalsIncluded  If <code>true</code>, the test will be extended to the Java
    *      literals <code>"false", "true" and "null"</code>.
    * @return                  <code>true</code>, if the provided String represents a Java
    *      keyword or, depending on the boolean parameter, represents a Java literal, <code>false</code>
    *      otherwise.
    */
   public static boolean isJavaKeyword (String str, boolean literalsIncluded)
   {
      return isJavaKeyword (str, literalsIncluded, true);
   }


   /**
    * This method returns <code>true</code> if the provided String represents one of the Java
    * keywords as defined in the language specification. By setting the boolean value <code>literalsIncluded</code>
    * to true, it returns <code>true</code> as well, if the String represents a Java literal,
    * which are "true", "false" and "null". If the String itself <i>is</i> null, false is returned.
    * When the String is compared, it has to match cases. All Keywords and literals are lower-case-words.
    * <br>
    * <p>
    *
    * @param str               The String to be possibly a Java keyword.
    * @param literalsIncluded  If <code>true</code>, the test will be extended to the Java
    *      literals <code>"false", "true" and "null"</code>.
    * @param trim              Indicates if the trim()-method should be invoked on the String
    *      before testing.
    * @return                  <code>true</code>, if the provided String represents a Java
    *      keyword or, depending on the boolean parameter, represents a Java literal, <code>false</code>
    *      otherwise.
    */
   public static boolean isJavaKeyword (String str, boolean literalsIncluded, boolean trim)
   {
      if (str == null)
      {
         return false;
      }

      str = str.trim();

      return set.contains (str)
         ||  (literalsIncluded &&
          (str.equals ("true")
         || str.equals ("false")
         || str.equals ("null")));
   }


   /**
    * Calls the Character.isJavaIdentifierPart(char) method for each char in string. Calls
    * also Character.isJavaIdentifierStart(char) for the first char. Returns true if the method
    * written above returns true for each char, false otherwise. If string is null, it returns
    * false. If string.length()==0 it returns true.
    *
    * @param string  No description provided
    * @return        The javaIdentifier value
    */
   public static boolean isJavaIdentifier (String string)
   {
      return isJavaIdentifier (string, true);
   }


   /**
    * Calls the Character.isJavaIdentifierPart(char) method for each char in string. Calls
    * also Character.isJavaIdentifierStart(char) for the first char. Returns true if the method
    * written above returns true for each char, false otherwise. If string is null, it returns
    * false. If string.length()==0 it returns true.
    *
    * @param string  No description provided
    * @return        The javaSuitable value
    */
   public static boolean isJavaSuitable (String string)
   {
      return isJavaIdentifier (string, true);
   }


   /**
    * Calls the Character.isJavaIdentifierPart(char) method for each char in string. Calls
    * also Character.isJavaIdentifierStart(char) for the first char if considerStartLetter
    * is true. Returns true if the method written above returns true for each char, false otherwise.
    * If string is null, it returns false. If string.length()==0 it returns true.
    *
    * @param string               No description provided
    * @param considerStartLetter  No description provided
    * @return                     The javaIdentifier value
    */
   public static boolean isJavaIdentifier (String string, boolean considerStartLetter)
   {
      if (string == null)
      {
         return false;
      }
      if (string.length() == 0)
      {
         return true;
      }

      int j;

      if (considerStartLetter)
      {
         if (!Character.isJavaIdentifierStart (string.charAt (0)))
         {
            return false;
         }

         j = 1;
      }
      else
      {
         j = 0;
      }

      for (int i = j; i < string.length(); i++)
      {
         if (!Character.isJavaIdentifierPart (string.charAt (i)))
         {
            return false;
         }
      }

      return true;
   }


   /**
    * Calls the Character.isJavaIdentifierPart(char) method for each char in string. Calls
    * also Character.isJavaIdentifierStart(char) for the first char if considerStartLetter
    * is true. Returns true if the method written above returns true for each char, false otherwise.
    * If string is null, it returns false. If string.length()==0 it returns true.
    *
    * @param string               No description provided
    * @param considerStartLetter  No description provided
    * @return                     The javaSuitable value
    */
   public static boolean isJavaSuitable (String string, boolean considerStartLetter)
   {
      return isJavaIdentifier (string, considerStartLetter);
   }


   /**
    * Turns a String, that represents a parameter or return type, to the right value so that
    * a comparison of this String with the list of parameters e. g. in a method declaration
    * will return true. This method turn the following String to to following modified Strings:
    * <p>
    *
    *
    * <tableborder=0>
    *
    *   <tr>
    *
    *     <td>
    *       <b>String</b>
    *     </td>
    *
    *     <td>
    *       <b>Modified String</b>
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       boolean
    *     </td>
    *
    *     <td>
    *       Boolean
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       boolean[]
    *     </td>
    *
    *     <td>
    *       BooleanArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       byte
    *     </td>
    *
    *     <td>
    *       Byte
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       byte[]
    *     </td>
    *
    *     <td>
    *       ByteArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       char
    *     </td>
    *
    *     <td>
    *       Character
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       char[]
    *     </td>
    *
    *     <td>
    *       CharacterArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       double
    *     </td>
    *
    *     <td>
    *       Double
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       double[]
    *     </td>
    *
    *     <td>
    *       DoubleArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       float
    *     </td>
    *
    *     <td>
    *       Float
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       float[]
    *     </td>
    *
    *     <td>
    *       FloatArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       int
    *     </td>
    *
    *     <td>
    *       Integer
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       int[]
    *     </td>
    *
    *     <td>
    *       IntegerArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       long
    *     </td>
    *
    *     <td>
    *       LongInteger
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       long[]
    *     </td>
    *
    *     <td>
    *       LongIntegerArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       short
    *     </td>
    *
    *     <td>
    *       ShortInteger
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       short[]
    *     </td>
    *
    *     <td>
    *       ShortIntegerArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       String[]
    *     </td>
    *
    *     <td>
    *       StringArray
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       void
    *     </td>
    *
    *     <td>
    *       Void
    *     </td>
    *
    *   </tr>
    *
    * </table>
    * <p>
    *
    * @param s  The String to be "translated".
    * @return   The "translated" String, or the String itself if nothing has be done.
    */
   public static String makeFujabaSuitable (String s)
   {
      String rType = s;

      if (rType.equals ("void"))
      {
         rType = "Void";
      }
      else
         if (rType.equals ("boolean"))
      {
         rType = "Boolean";
      }
      else
         if (rType.equals ("boolean[]"))
      {
         rType = "BooleanArray";
      }
      else
         if (rType.equals ("byte"))
      {
         rType = "Byte";
      }
      else
         if (rType.equals ("byte[]"))
      {
         rType = "ByteArray";
      }
      else
         if (rType.equals ("char"))
      {
         rType = "Character";
      }
      else
         if (rType.equals ("char[]"))
      {
         rType = "CharacterArray";
      }
      else
         if (rType.equals ("double"))
      {
         rType = "Double";
      }
      else
         if (rType.equals ("double[]"))
      {
         rType = "DoubleArray";
      }
      else
         if (rType.equals ("float"))
      {
         rType = "Float";
      }
      else
         if (rType.equals ("float[]"))
      {
         rType = "FloatArray";
      }
      else
         if (rType.equals ("int"))
      {
         rType = "Integer";
      }
      else
         if (rType.equals ("int[]"))
      {
         rType = "IntegerArray";
      }
      else
         if (rType.equals ("long"))
      {
         rType = "LongInteger";
      }
      else
         if (rType.equals ("long[]"))
      {
         rType = "LongIntegerArray";
      }
      else
         if (rType.equals ("short"))
      {
         rType = "ShortInteger";
      }
      else
         if (rType.equals ("short[]"))
      {
         rType = "ShortIntegerArray";
      }
      else
         if (rType.equals ("String[]"))
      {
         rType = "StringArray";
      }

      return rType;
   }


   /**
    * Get the nameWithoutPackage attribute of the TextUtil class
    *
    * @param className  No description provided
    * @return           The nameWithoutPackage value
    */
   public static String getNameWithoutPackage (String className)
   {
      if (className == null)
      {
         return "";
      }

      int iIndex = className.lastIndexOf (".");
      if (iIndex != -1)
      {
         return className.substring (iIndex + 1);
      }
      else
      {
         return className;
      }

   }


   /**
    * Get the packageWithoutName attribute of the TextUtil class
    *
    * @param className  No description provided
    * @return           The packageWithoutName value
    */
   public static String getPackageWithoutName (String className)
   {
      if (className == null)
      {
         return "";
      }

      int iIndex = className.lastIndexOf (".");
      if (iIndex != -1)
      {
         return className.substring (0, iIndex);
      }
      else
      {
         return "";
      }

   }

   /**
    * Shorten the given text so it will be at most as long as maxLength.
    * The text will be split into three parts. The middle part will be replaced
    * with three dots.
    * 
    * @param text A string.
    * @param maxLength The maximum length of the shortened string. Must be
    * greater or equal than 6. 
    * @return The shortened text if length of text is greater than maxLength.
    * The unmodified text otherwise.
    */
   public static String getShortenedString(String text, int maxLength)
   {
      if (text == null)
         return null;
      
      if (maxLength < 6)
         throw new IllegalArgumentException("maxLength must be >= 6");
      
      StringBuffer buffer = new StringBuffer();
      int textLength = text.length();
      int lengthFirstPart = maxLength / 2;
      int lengthSecondPart = (maxLength / 2) - 3;   // must be greater or equal than zero
      if (textLength > maxLength)
      {
         buffer.append(text.substring(0,lengthFirstPart));
         buffer.append("..."); // 3 chars
         buffer.append(text.substring(textLength-lengthSecondPart, textLength));
      }
      else
      {
         buffer.append(text);
      }
      return buffer.toString();
   }
}

/*
 * $Log$
 * Revision 1.13  2006/03/29 09:51:08  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.12  2006/03/22 21:05:25  fklar
 * type safety: using java 1.5 generics
 *
 * Revision 1.11  2005/03/16 11:01:41  fklar
 * introduced two methods, that will extract name (respectively packageName) from a full-qualified-classname
 *
 */
