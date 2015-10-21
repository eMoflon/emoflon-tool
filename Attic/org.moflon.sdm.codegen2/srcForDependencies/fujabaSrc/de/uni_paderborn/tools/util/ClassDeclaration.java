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
 *      Copyright (C) 1997-2004 Fujaba Development Group
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
 * Contact adress:
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

import java.util.*;

import javax.swing.*;


/**
 * The class ClassDeclaration provides information about a String that declares a class. It
 * tries to parse the provided parameter, that has to be a fujaba-like declaration (see below).
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class ClassDeclaration
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String VALID_DECLARATION = "Valid declaration";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String EMPTY_DECLARATION = "Nothing defined";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String INVALID_CLASS_DECLARATION = "Invalid class declaration";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String CLASS_IS_KEYWORD = "The name of the class is a Java keyword";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String ILLEGAL_CONSTRUCTORS_DECLARATION = "Illegal declaration of constructors";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String NO_CONSTRUCTORS_ALLOWED = "Interfaces are not allowed to own constructors";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String UNPRECISE_CONSTRUCTORS_DECLARATION = "Constructor declaration unprecise; interpreted as its best";

   /**
    * The originally provided String.
    */
   private String original = "";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String classError = EMPTY_DECLARATION;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isAbstract = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isStatic = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isInterface = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isPersistent = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isReference = false;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name; //The name of the class.
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String packageName; //The package name

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private HashSet<Vector<String>> constructors;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int paramcount = 0;


   /**
    * Creates a new object of ClassDeclareation and parses the provided String.
    *
    * @param declaration             The String to be a class declaration.
    * @throws NullPointerException,  if <code>declaration</code> is null.
    */
   public ClassDeclaration (String declaration)
   {
      if (declaration == null)
      {
         throw new NullPointerException ("Parameter declaration is null");
      }

      original = declaration;
      constructors = new HashSet<Vector<String>>();

      name = "";
      packageName = "";

      parse();
   }


   /**
    * Tries to parse the provided String as a new class. Constructors can also be establieshed.
    * Here is a list of possible examples, how to declare a new class:
    * <tableborder=0>
    *
    *   <tr>
    *
    *     <td>
    *       <b>DECLARATION</b>
    *     </td>
    *
    *     <td>
    *       <b>LITERAL EXAMPLE</n>
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       class_name_only
    *     </td>
    *
    *     <td>
    *       Class
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       full_qualified_class_name
    *     </td>
    *
    *     <td>
    *       java.lang.Class
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) class_name
    *     </td>
    *
    *     <td>
    *       abstract Class
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       &nbsp;
    *     </td>
    *
    *     <td>
    *       abstract reference static Class
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) full_qualified_class_name
    *     </td>
    *
    *     <td>
    *       abstract java.lang.Class
    *     </td>
    *
    *   </tr>
    *
    * </table>
    * <p>
    *
    * Any of the ones above can be followed by parenthesis representing the parameters of the
    * (only then) created constructor(s). The parameters have to be divided by komma or semicolon,
    * otherwise they won't be recognized. You can provide different types only, or even a type
    * with a variable. <br>
    * When the class name is a Java keyword this declaration is invalid. Parameter types and
    * theri representing variables can be divided by white-space (then the declaration has
    * to be java-like, e. g. <code>String s, int i</code>), or it has to be divided by colon
    * (then the declaration has to be uml-like, e. g. <code>s:String, i:int</code>).
    */
   private void parse()
   {
      if (original.length() == 0)
      {
         return;
      } //this indicates that the original String is empty or already parsed.

      String s = original.trim();

      if (s.length() == 0)
      {
         classError = EMPTY_DECLARATION;

         return;
      }

      String param = "";
      StringTokenizer token = null;

      if (s.indexOf ("(") == -1)
      {
         token = new StringTokenizer (s);
      }
      else
      {
         token = new StringTokenizer (s.substring (0, s.indexOf ("(")));
         param = s.substring (s.indexOf ("("));
      }

      int counter = token.countTokens();

      while (token.hasMoreTokens())
      {
         String str = token.nextToken();

         if (counter == 1)
         {
            if (!TextUtil.isJavaSuitable (str))
            {

               if (str.lastIndexOf (".") != -1)
               {
                  packageName = str.substring (0, str.lastIndexOf ("."));
                  name = str.substring (str.lastIndexOf (".") + 1);

                  break;
               }
               else
               {
                  classError = INVALID_CLASS_DECLARATION;
                  clear();
                  return;
               }
            }
            else
               if (TextUtil.isJavaKeyword (str))
            {
               classError = CLASS_IS_KEYWORD;
               clear();
               return;
            }
            else
            {
               if (str.lastIndexOf (".") == -1)
               {
                  name = str;
               }
               else
               {
                  packageName = str.substring (0, str.lastIndexOf ("."));
                  name = str.substring (str.lastIndexOf (".") + 1);
               }

               break;
            }
         }
         else
            if (counter == 2)
         {
            if (str.equalsIgnoreCase ("interface") || str.equalsIgnoreCase ("i"))
            {
               isInterface = true;
               counter--;
               continue;
            }
            else
               if (str.equalsIgnoreCase ("new"))
            {
               counter--;
               continue;
            }
         }

         if (str.equalsIgnoreCase ("static") || str.equalsIgnoreCase ("s"))
         {
            isStatic = true;
         }
         else
            if (str.equalsIgnoreCase ("abstract") || str.equalsIgnoreCase ("a"))
         {
            isAbstract = true;
         }
         else
            if (str.equalsIgnoreCase ("persistent") || str.equalsIgnoreCase ("p"))
         {
            isPersistent = true;
         }
         else
            if (str.equalsIgnoreCase ("reference") || str.equalsIgnoreCase ("r"))
         {
            isReference = true;
         }
         else
         {
            classError = INVALID_CLASS_DECLARATION;
            clear();
            return;
         }

         counter--;
      }

      classError = VALID_DECLARATION;

      int b = parseParam (param);

      if (b == -1)
      {
         classError = ILLEGAL_CONSTRUCTORS_DECLARATION;
      }
      else
         if (b > 0)
      {
         classError = UNPRECISE_CONSTRUCTORS_DECLARATION;
      }
   }



   /**
    * Parses the parameters
    *
    * @param str  No description provided
    * @return     No description provided
    */
   private int parseParam (String str)
   {
      if (str == null || str.length() == 0)
      {
         return 0;
      }

      if (isInterface)
      {
         classError = NO_CONSTRUCTORS_ALLOWED;
         return 0;
      }

      int mistakes = 0;

      int on = -1;

      for (int i = 0; i < str.length(); i++)
      {
         switch (str.charAt (i))
         {
            case '(':
               if (on == -1)
               {
                  on = i + 1;
               }
               else
               {
                  on = i + 1;
                  mistakes++;
               }
               break;
            case ')':
               if (on != -1)
               {
                  if (!putToConstructors (str.substring (on, i)))
                  {
                     mistakes++;
                  }

                  on = -1;
               }
               else
               {
                  mistakes++;
               }
               break;
         }
      }

      return mistakes;
   }


   /**
    * Parses the provided String to be a constructor-definition.
    *
    * @param string  No description provided
    * @return        No description provided
    */
   private boolean putToConstructors (String string)
   {
      if (string == null || string.trim().length() == 0)
      {
         Vector<String> vector = new Vector<String>(); //empty constructor

         constructors.add (vector);

         return true;
      }

      StringTokenizer token = new StringTokenizer (string, ";,");

      if (token.countTokens() == 0)
      {
         return false;
      }

      Vector<String> vector = new Vector<String>();

      while (token.hasMoreTokens())
      {
         String str = token.nextToken().trim();

         if (str.indexOf (":") == -1)
         {
            if (str.indexOf (" ") == -1)
            {
               if (!TextUtil.isJavaSuitable (str))
               {
                  return false;
               }

               else
               {
                  String newvalue = "p" + paramcount;

                  while (vector.contains (newvalue))
                  {
                     paramcount++;
                     newvalue = "p" + paramcount;
                  }

                  vector.add (TextUtil.makeFujabaSuitable (str));
                  vector.add (newvalue);
               }
            }
            else
            {
               String param = str.substring (0, str.indexOf (" ")).trim();
               String value = str.substring (str.indexOf (" ")).trim();

               if (!TextUtil.isJavaSuitable (param) || !TextUtil.isJavaSuitable (value))
               {
                  return false;
               }
               else
               {
                  String newvalue = value;

                  while (vector.contains (newvalue))
                  {
                     paramcount++;
                     newvalue = value + paramcount;
                  }

                  vector.add (TextUtil.makeFujabaSuitable (param));
                  vector.add (newvalue);
               }
            }
         }
         else
         {
            String value = str.substring (0, str.indexOf (":")).trim();
            String param = str.substring (str.indexOf (":") + 1).trim();

            if (!TextUtil.isJavaSuitable (param) || !TextUtil.isJavaSuitable (value) || param.length() == 0)
            {
               return false;
            }
            else
            {
               String newvalue = value;

               if (newvalue.length() == 0)
               {
                  newvalue = "p" + paramcount;
               }

               while (vector.contains (newvalue))
               {
                  paramcount++;
                  newvalue = value + paramcount;
               }

               vector.add (TextUtil.makeFujabaSuitable (param));
               vector.add (newvalue);
            }
         }
      }

      constructors.add (vector);

      return true;
   }


   /**
    * @return   The constructors value
    */

   /**
    * Returns all defined constructors in a HashSet. Each entry of this set contains a vector,
    * each vector represents one constructor and contains its parameters. Each odd cell of
    * the vector contains therefor the type of the parameter, e. g. <code>String</code> or
    * <code>Integer</code>, each even cell represents the variable-bname to its predecessor
    *
    * @return   The constructors value
    */
   public HashSet<Vector<String>> getConstructors()
   {
      return constructors;
   }


   /**
    * Returns the name of this Declaration.
    *
    * @return   The name value
    */
   public String getName()
   {
      return name;
   }


   /**
    * Returns information about this declaration. These information contain:<br>
    *
    * <ul>
    *   <li> the name of the Declaration
    *   <li> the type of the Declaration
    *   <li> the visibility of this Declaration (if defined)
    *   <li> the constructors (if defined)
    * </ul>
    * The String is formatted to be shown best with a JOptionPane. Use showStatus() to do this.
    *
    * @return   The status value
    */
   public String getStatus()
   {
      StringBuffer buff = new StringBuffer();

      buff.append ("Name: ");

      if (this.name.length() == 0)
      {
         buff.append ("<<empty>>");
      }
      else
      {
         if (packageName.length() > 0)
         {
            buff.append (packageName).append (".");
         }

         buff.append (this.name);
      }

      buff.append ("\n\n");

      buff.append ("Type:\n");

      if (isEmpty())
      {
         buff.append ("EMPTY").append ("\n");
      }
      else
         if (isInterface())
      {
         buff.append ("Interface --").append (getClassError()).append ("\n");
      }
      else
      {
         buff.append ("Class -- ").append (getClassError()).append ("\n");
      }

      buff.append ("\n");

      if (isStatic)
      {
         buff.append ("static\n");
      }
      if (isAbstract)
      {
         buff.append ("abstract\n");
      }
      if (isPersistent)
      {
         buff.append ("persistent\n");
      }
      if (isReference)
      {
         buff.append ("reference\n");
      }

      buff.append ("\n\nConstructors:\n");
      boolean empty = true;

      Iterator iter = getConstructors().iterator();

      while (iter.hasNext())
      {
         empty = false;

         Vector vector = (Vector) iter.next();

         buff.append (name).append (" (");

         for (int i = 0; i < vector.size(); i += 2)
         {
            String key = (String) vector.get (i);
            String value = (String) vector.get (i + 1);

            buff.append (value).append (":").append (key);

            if (i < vector.size() - 2)
            {
               buff.append (", ");
            }
         }

         buff.append (")");
      }

      if (empty && isInterface())
      {
         buff.append ("<<not possible>>");
      }
      else
         if (empty)
      {
         buff.append ("<<none>>");
      }

      return buff.toString();
   }


   /**
    * Shows the status provided by getStatus() in a JOptionPane.
    */
   public void showStatus()
   {
      JOptionPane.showMessageDialog (null, getStatus());
   }


   /**
    * Returns the method error, that is an error occured while parsing. If no error occured
    * an appropriate message is returned.
    *
    * @return   The classError value
    */
   public String getClassError()
   {
      if (classError == EMPTY_DECLARATION)
      {
         return EMPTY_DECLARATION;
      }
      else
      {
         return classError;
      }
   }


   /**
    * Returns true if this Declaration is abstract, false otherwise.
    *
    * @return   The abstract value
    */
   public boolean isAbstract()
   {
      return isAbstract;
   }


   /**
    * Returns true if this Declaration is static, false otherwise.
    *
    * @return   The static value
    */
   public boolean isStatic()
   {
      return isStatic;
   }


   /**
    * Get the interface attribute of the ClassDeclaration object
    *
    * @return   The interface value
    */
   public boolean isInterface()
   {
      return isInterface;
   }


   /**
    * Get the persistent attribute of the ClassDeclaration object
    *
    * @return   The persistent value
    */
   public boolean isPersistent()
   {
      return isPersistent;
   }


   /**
    * Get the reference attribute of the ClassDeclaration object
    *
    * @return   The reference value
    */
   public boolean isReference()
   {
      return isReference;
   }


   /**
    * Returns true if this ClassDeclaration is empty.
    *
    * @return   The empty value
    */
   public boolean isEmpty()
   {
      return classError == EMPTY_DECLARATION;
   }


   /**
    * Returns true, if this declaration is valid, NOT considerung parameters or return type.
    * An empty declaration is not valid.
    *
    * @return   The valid value
    */
   public boolean isValid()
   {
      return classError != EMPTY_DECLARATION && classError != INVALID_CLASS_DECLARATION
         && classError != CLASS_IS_KEYWORD;
   }


   /**
    * Get the package attribute of the ClassDeclaration object
    *
    * @return   The package value
    */
   public String getPackage()
   {
      return packageName;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void clear()
   {
      isAbstract = false;
      isStatic = false;
      isInterface = false;
      isPersistent = true;
      isReference = true;
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
 * Revision 1.11  2004/10/20 17:50:34  schneider
 * Introduction of interfaces for class diagram classes
 *
 */
