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

import java.util.*;

import javax.swing.JOptionPane;

import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;


/**
 * The class MethodDeclaration provides information about a String that declares a method.
 * It tries to parse the provided parameter, that can be a UML-like or a Java-like declarationn
 * (or both). This declaration is not quite the style for instance demanded by the Java Language
 * Specification. But it should help to interpret the user's input as its best.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class MethodDeclaration
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
   public final static String INVALID_METHOD_DECLARATION = "Invalid method declaration";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String NO_METHOD_NAME_DECLARED = "No method name declared";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String METHOD_IS_KEYWORD = "The name of the method is a Java keyword";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String ILLEGAL_RETURN_TYPE_DECLARATION = "Illegal return type declaration";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String ILLEGAL_PARAMETER_DECLARATION = "Illegal parameter declaration";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String ILLEGAL_PARAMETER_AND_RETURN_TYPE_DECLARATION = "Illegal parameter and return type declaration";

   /**
    * The originally provided String.
    */
   private String original = "";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String methodError = EMPTY_DECLARATION;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isParsed = false;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isPublic = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isPackage = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isProtected = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isPrivate = false;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isFinal = false;
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
   private boolean isSynchronized = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isTransient = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isVolatile = false;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private boolean isNative = false;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name = ""; //The name of the method.
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String rType = ""; //The return type
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String[] param = new String[0]; //The parameters.
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String[] var = new String[0]; //The parameter-variables if any exists.


   /**
    * Creates a new object fo MethodDeclaration and parses the provided String.
    *
    * @param declaration             The String to be a method declaration.
    * @throws NullPointerException,  if <code>declaration</code> is null.
    */
   public MethodDeclaration (String declaration)
   {
      if (declaration == null)
      {
         throw new NullPointerException ("Parameter declaration is null");
      }

      original = declaration;

      parse();
   }


   /**
    * Calls parseJavaMethod() and parseUMLMethod() in this order.
    */
   private void parse()
   {
      parseJavaMethod();
      parseUMLMethod();
   }


   /**
    * Tries to parse the declaration as a Java-like expression. A Java-like expression consists
    * <i>NOT</i> of a grammatical correct expression as defined in the Java Specification Language.
    * The following expressions are to be considered as Java-like-correct (spaces between parenthesis
    * and other expressions are meaningless except square brackets indicating an array, which
    * have to follow right after the type with no spaces between):<p>
    *
    *
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
    *       method_name_only
    *     </td>
    *
    *     <td>
    *       doSomething
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis
    *     </td>
    *
    *     <td>
    *       doSomething ()
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis_with_parameter(s)
    *     </td>
    *
    *     <td>
    *       doSomething (int, String)
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis semicolon
    *     </td>
    *
    *     <td>
    *       doSomething ();
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis_with_parameter(s) semicolon
    *     </td>
    *
    *     <td>
    *       doSomething (int, String);
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) method_name
    *     </td>
    *
    *     <td>
    *       public (static ...) doSomething
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis semicolon
    *     </td>
    *
    *     <td>
    *       doSomething ();
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis_with_parameter(s) semicolon
    *     </td>
    *
    *     <td>
    *       doSomething (int, String);
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) method_name parenthesis semicolon
    *     </td>
    *
    *     <td>
    *       public (static ...) doSomething ();
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) method_name parenthesis_with_parameter(s) semicolon
    *     </td>
    *
    *     <td>
    *       public (static ...) doSomething (int, String);
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       return_type method_name
    *     </td>
    *
    *     <td>
    *       void doSomething
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       return_type method_name parenthesis
    *     </td>
    *
    *     <td>
    *       void doSomething ()
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       return_type method_name parenthesis_with_parameter(s)
    *     </td>
    *
    *     <td>
    *       void doSomething (int, String)
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       return_type method_name parenthesis semicolon
    *     </td>
    *
    *     <td>
    *       void doSomething ();
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       return_type method_name parenthesis_with_parameter(s) semicolon
    *     </td>
    *
    *     <td>
    *       void doSomething (int, String);
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) return_type method_name
    *     </td>
    *
    *     <td>
    *       public (static ...) void doSomething
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) return_type method_name parenthesis
    *     </td>
    *
    *     <td>
    *       public (static ...) void doSomething ()
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) return_type method_name parenthesis_with_parameter(s)
    *     </td>
    *
    *     <td>
    *       public (static ...) void doSomething (int, String)
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) return_type method_name parenthesis semicolon
    *     </td>
    *
    *     <td>
    *       public (static ...) void doSomething ();
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       signifier(s) return_type method_name parenthesis_with_parameter(s) semicolon
    *     </td>
    *
    *     <td>
    *       public (static ...) void doSomething (int, String);
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *
    *     </td>
    *
    *     <td>
    *
    *     </td>
    *
    *   </tr>
    *
    * </table>
    * <p>
    *
    * Parameters have to be divided by komma, otherwise they won't be recognized. You can provide
    * different types only, or even a type with a variable. <br>
    * When the method name is a Java keyword this declaration is invalid. <b>Note:</b> <br>
    * Since you cannot define a package visible method explicitly (when making no specifications
    * like in Java itself, the visibility will be ignored here), here it is allowed to write
    * the keyword 'package' as visibility type.
    */
   private void parseJavaMethod()
   {
      if (original.length() == 0)
      {
         return;
      } //this indicates that the original String is empty or already parsed.

      String s = original.trim();

      if (s.length() == 0)
      {
         methodError = EMPTY_DECLARATION;
         isParsed = true;

         return;
      }

      //type |= METHOD;

      Vector<String> vector = new Vector<String>(); //the parts of this String
      int mark = 0;
      int parOn = -1;
      int parOff = -1;
      int state = 0;

      for (int i = 0; i < s.length(); i++)
      {
         switch (state)
         {
            case 0: // no chars typed yet
               if (s.charAt (i) == '(' || s.charAt (i) == ')' || s.charAt (i) == ';')
               {
                  state = 6;
               }
               else
               {
                  state = 1;
               }
               break;
            case 1: // a word typed until this point
               if (s.charAt (i) == ' ')
               {
                  state = 2;
                  vector.add (s.substring (mark, i));
                  mark = i + 1;
               }
               else
                  if (s.charAt (i) == ';')
               {
                  state = 5;
                  vector.add (s.substring (mark, i));
               }
               else
                  if (s.charAt (i) == '(')
               {
                  state = 3;
                  parOn = i + 1;
                  vector.add (s.substring (mark, i));
               }
               else
                  if (s.charAt (i) == ')')
               {
                  state = 6;
               }

               break;
            case 2: // 1..n spaces after a word (case 1)
               if (s.charAt (i) == ')')
               {
                  state = 6;
               }
               else
                  if (s.charAt (i) == '(')
               {
                  state = 3;
                  parOn = i + 1;
               }
               else
                  if (s.charAt (i) == ';')
               {
                  state = 5;
               }
               else
                  if (s.charAt (i) != ' ')
               {
                  state = 1;
                  mark = i;
               }

               break;
            case 3: // left parenthesis
               if (s.charAt (i) == ')')
               {
                  state = 4;
                  parOff = i;
               }

               break;
            case 4: // right parenthesis
               if (s.charAt (i) == ';')
               {
                  state = 5;
               }
               else
               {
                  state = 6;
               }

               break;
            case 5: // this has to be all now
               state = 6;
               break;
            //case 6:	wrong syntax
         }

         if (state == 6)
         {
            methodError = INVALID_METHOD_DECLARATION;
            return;
         }
      }

      if (state == 3)
      {
         methodError = INVALID_METHOD_DECLARATION;
         return;
      }

      String method = "";
      String param = "";
      String rType = "";

      if (state == 1)
      {
         vector.add (s.substring (mark));
      }

      if ( (state == 4 || state == 5) && parOn >= 0)
      {
         param = s.substring (parOn, parOff);
      }

      if (vector.size() == 1)
      {
         method =  (vector.get (0)).trim();
         methodError = VALID_DECLARATION;
      }
      else
         if (vector.size() == 2)
      {
         method =  (vector.get (1)).trim();

         String str =  (vector.get (0)).trim();

         if (setSignifierMethod (str, false))
         {
            rType = "";
         }
         else
         {
            rType = str;
         }

         methodError = VALID_DECLARATION;
      }
      else
         if (vector.size() > 2)
      {
         for (int i = 0; i < vector.size() - 2; i++)
         {
            String str =  (vector.get (i)).trim();

            if (!setSignifierMethod (str, true))
            {
               return;
            }
         } //end: for

         method =  (vector.get (vector.size() - 1)).trim();
         rType =  (vector.get (vector.size() - 2)).trim();

         methodError = VALID_DECLARATION;

      } //end: else if (vector.size()>2)

      if (TextUtil.isJavaKeyword (method))
      {
         methodError = METHOD_IS_KEYWORD;
         //clear();

         return;
      }

      if (!TextUtil.isJavaIdentifier (method))
      {
         methodError = INVALID_METHOD_DECLARATION;
         //clear();


         return;
      }

      /*
       *  Parse parameters
       */
      if (param.trim().length() != 0)
      {
         parseJavaParam (param.trim());
      }

      /*
       *  Check if return-type is valid
       */
      String rTypeToCheck = rType;

      // remove array-identifier from java-suitable-check
      if (rType.endsWith ("[]"))
      {
         rTypeToCheck = rType.substring (0, rType.length() - 2);
      }

      // remove package-prefix from java-suitable-check
      if (rType.lastIndexOf (".") != -1)
      {
         int iIndex = rType.lastIndexOf (".");
         rTypeToCheck = rType.substring (iIndex + 1);
      }

      // is it a valid return-type?
      // must be java-suitable and (if it's an array) not empty!
      // an empty string as return-type means that it's a constructor
      // TODO: check if methodname equals classname in this case
      if (!TextUtil.isJavaSuitable (rTypeToCheck)
         ||  (rType.endsWith ("[]") && rTypeToCheck.length() == 0)
         )
      {
         if (methodError == ILLEGAL_PARAMETER_DECLARATION)
         {
            methodError = ILLEGAL_PARAMETER_AND_RETURN_TYPE_DECLARATION;
         }
         else
         {
            methodError = ILLEGAL_RETURN_TYPE_DECLARATION;
         }

         rType = "";
      }

      this.name = method;
      this.rType = rType;

      isParsed = true;
   }


   /**
    * Tries to parse the declaration as an UML-like expression. Since UML is graphically oriented,
    * this UML-like declaration referres to the text-presentation of methods. The following
    * expressions are to be considered as UML-like-correct (spaces between parenthesis and
    * other expressions, between visibility and method name or between colon and other expressions
    * are meaningless, except square brackets indicating an array, which have to follow right
    * after the type with no spaces between):<p>
    *
    *
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
    *       method_name_only
    *     </td>
    *
    *     <td>
    *       doSomething
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name return_type
    *     </td>
    *
    *     <td>
    *       doSomething : int
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       visibility method_name
    *     </td>
    *
    *     <td>
    *       + doSomething
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       visibility method_name return_type
    *     </td>
    *
    *     <td>
    *       + doSomething : int
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis
    *     </td>
    *
    *     <td>
    *       doSomething ()
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis return_type
    *     </td>
    *
    *     <td>
    *       doSomething () : int
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis parameter(s)
    *     </td>
    *
    *     <td>
    *       doSomething (int, String)
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       method_name parenthesis parameter(s) return_type
    *     </td>
    *
    *     <td>
    *       doSomething (int, String):int
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       visibility method_name parenthesis
    *     </td>
    *
    *     <td>
    *       + doSomething ()
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       visibility method_name parenthesisb return_type
    *     </td>
    *
    *     <td>
    *       + doSomething ():int
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       visibility method_name parenthesis parameter(s)
    *     </td>
    *
    *     <td>
    *       + doSomething (int, String)
    *     </td>
    *
    *   </tr>
    *
    *   <tr>
    *
    *     <td>
    *       visibility method_name parenthesis parameter(s) return_type
    *     </td>
    *
    *     <td>
    *       + doSomething (int, String):int
    *     </td>
    *
    *   </tr>
    *
    * </table>
    * Parameters have to be divided by komma or semicolon, otherwise they won't be recognized.
    * You can provide different types only, or even a type with a variable. <br>
    * When the method name is a Java keyword this declaration is invalid.
    */
   private void parseUMLMethod()
   {
      if (original.length() == 0 || isParsed)
      {
         return;
      } //this indicates that the original String is empty or already a method.

      String s = original.trim();

      if (s.length() == 0)
      {
         methodError = EMPTY_DECLARATION;

         return;
      }

      char vis = 0; //The visibility
      String method = ""; //Method name
      String param = ""; //Parameter
      String rType = ""; //Return type
      int mark = 0;

      /*
       *  First split the String into the right parts.
       */
      if (s.charAt (0) == '+' || s.charAt (0) == '-' || s.charAt (0) == '#' || s.charAt (0) == '~')
      {
         vis = s.charAt (0);
         mark = 1;
      }

      for (int i = 1; i < s.length(); i++)
      {
         if (s.charAt (i) == '(')
         {
            method = s.substring (mark, i).trim();
            mark = i + 1;
         }
         else
            if (s.charAt (i) == ')')
         {
            param = s.substring (mark, i).trim();
            rType = s.substring (i + 1, s.length()).trim();

            if (rType.startsWith (":"))
            {
               rType = s.substring (i + 2, s.length()).trim();
            }
            else
            {
               methodError = INVALID_METHOD_DECLARATION;

               return;
            }

            mark = -1;
            break;
         }
         if (s.charAt (i) == ':' && mark <= 1)
         {
            method = s.substring (mark, i);
            rType = s.substring (i + 1, s.length()).trim();
            mark = -2;
            break;
         }
      }

      /*
       *  If mark==0 then only a method name without anything else
       *  has been passed as argument.
       */
      if (mark == 0)
      {
         method = s;
      }
      /*
       *  If mark==1 then only a method name with visibility and without
       *  anything else has been passed as argument.
       */
      else
         if (mark == 1)
      {
         method = s.substring (1);
      }

      if (method.length() == 0)
      {
         methodError = NO_METHOD_NAME_DECLARED;

         return;
      }
      else
      {
         if (!TextUtil.isJavaIdentifier (method))
         {
            methodError = INVALID_METHOD_DECLARATION;

            return;
         }
      }
      /*
       *  Now testing, if the method name is a Java keyword.
       */
      if (TextUtil.isJavaKeyword (method))
      {
         methodError = METHOD_IS_KEYWORD;

         return;
      }

      /*
       *  Now the method name seems to be declared in a legal way.
       */
      methodError = VALID_DECLARATION;

      /*
       *  parse the parameters
       */
      if (param.length() != 0)
      {
         parseUMLParam (param);
      }

      if (rType.endsWith ("[]"))
      {
         if (rType.length() == 2 ||
            rType.length() > 2 && !TextUtil.isJavaSuitable (rType.substring (0, rType.length() - 2)))
         {
            if (methodError == ILLEGAL_PARAMETER_DECLARATION)
            {
               methodError = ILLEGAL_PARAMETER_AND_RETURN_TYPE_DECLARATION;
            }
            else
            {
               methodError = ILLEGAL_RETURN_TYPE_DECLARATION;
            }

            rType = "";
         }
      }
      else
         if (!TextUtil.isJavaSuitable (rType))
      {
         if (methodError == ILLEGAL_PARAMETER_DECLARATION)
         {
            methodError = ILLEGAL_PARAMETER_AND_RETURN_TYPE_DECLARATION;
         }
         else
         {
            methodError = ILLEGAL_RETURN_TYPE_DECLARATION;
         }

         rType = "";
      }

      if (vis == '~')
      {
         isPackage = true;
      }
      else
         if (vis == '#')
      {
         isProtected = true;
      }
      else
         if (vis == '+')
      {
         isPublic = true;
      }
      else
         if (vis == '-')
      {
         isPrivate = true;
      }

      this.rType = rType;
      this.name = method;

      isParsed = true;

   } //end: parseUMLMethod(String)



   /**
    * Parses the parameters of a UML-like-declaration.
    *
    * @param str  No description provided
    */
   private void parseUMLParam (String str)
   {
      StringTokenizer token = new StringTokenizer (str, ";,");

      String[] p = new String[token.countTokens()]; //parameters
      String[] v = new String[token.countTokens()]; //its varibales
      int counter = 0;

      while (token.hasMoreTokens())
      {
         String s = token.nextToken().trim();
         int put = -1;
         int state = 0;

         for (int i = 0; i < s.length(); i++)
         {
            switch (state)
            {
               case 0: // no chars typed yet
                  if (s.charAt (i) == ':')
                  {
                     methodError = ILLEGAL_PARAMETER_DECLARATION;
                     this.param = new String[0];
                     this.var = new String[0];
                     return;
                  }
                  else
                  {
                     state = 1;
                  }
                  break;
               case 1: // 1..n chars typed except ':' or <space>
                  if (s.charAt (i) == ' ')
                  {
                     state = 2;
                  }
                  else
                     if (s.charAt (i) == ':')
                  {
                     put = i;
                  }

                  break;
               case 2:
                  if (s.charAt (i) == ':')
                  {
                     put = i;
                  }
                  else
                     if (s.charAt (i) != ' ')
                  {
                     methodError = ILLEGAL_PARAMETER_DECLARATION;
                     this.param = new String[0];
                     this.var = new String[0];
                     return;
                  }
                  break;
            }

            if (put != -1)
            {
               break;
            }
         }
         if (put != -1)
         {
            v[counter] = s.substring (0, put).trim();
            p[counter] = s.substring (put + 1).trim();
         }
         else
         {
            p[counter] = s;
            v[counter] = "";
         }

         if (v[counter].length() != 0 && TextUtil.isJavaKeyword (v[counter]))
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }

         if (p[counter].equals ("void") || p[counter].equals ("void[]"))
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }

         if (p[counter].endsWith ("[]"))
         {
            if (p[counter].length() == 2 ||
               p[counter].length() > 2 && !TextUtil.isJavaSuitable (p[counter].substring (0, p[counter].length() - 2)))
            {
               methodError = ILLEGAL_PARAMETER_DECLARATION;
               this.param = new String[0];
               this.var = new String[0];
               return;
            }
         }
         else
            if (!TextUtil.isJavaSuitable (p[counter]))
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }

         if (p[counter].length() <= 0)
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }

         if (v[counter].length() > 0 && !TextUtil.isJavaIdentifier (v[counter]))
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }
         counter++;
      }

      this.param = p;
      this.var = v;
   }


   /**
    * Parses the parameters of a java-like-declaration
    *
    * @param str  No description provided
    */
   private void parseJavaParam (String str)
   {
      StringTokenizer token = new StringTokenizer (str, ";,");

      String[] p = new String[token.countTokens()];
      String[] v = new String[token.countTokens()];
      int counter = 0;

      while (token.hasMoreTokens())
      {
         StringTokenizer st = new StringTokenizer (token.nextToken());

         if (st.countTokens() > 2 || st.countTokens() <= 0)
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }
         else
            if (st.countTokens() == 2)
         {
            p[counter] = st.nextToken();
            v[counter] = st.nextToken();
         }
         else
            if (st.countTokens() == 1)
         {
            p[counter] = st.nextToken();
            v[counter] = "";
         }

         if (p[counter].equals ("void"))
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }

         if (TextUtil.isJavaKeyword (v[counter]))
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }

         if (p[counter].endsWith ("[]"))
         {
            if (p[counter].length() == 2 ||
               p[counter].length() > 2 && !TextUtil.isJavaSuitable (p[counter].substring (0, p[counter].length() - 2)))
            {
               methodError = ILLEGAL_PARAMETER_DECLARATION;
               this.param = new String[0];
               this.var = new String[0];
               return;
            }
         }
         else
            if (!TextUtil.isJavaSuitable (p[counter]))
         {
            // check if parameter contains a package-prefix
            int iIndex = p[counter].lastIndexOf (".");
            if (iIndex == -1)
            {
               methodError = ILLEGAL_PARAMETER_DECLARATION;
               this.param = new String[0];
               this.var = new String[0];
               return;
            }
            else
            {
               // parameter has a package-prefix so check parameter-type without it
               String paramType = p[counter].substring (iIndex + 1);

               if (!TextUtil.isJavaSuitable (paramType))
               {
                  methodError = ILLEGAL_PARAMETER_DECLARATION;
                  this.param = new String[0];
                  this.var = new String[0];
                  return;
               }
            }
         }

         if (p[counter].length() <= 0)
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }

         if (v[counter].length() > 0 && !TextUtil.isJavaIdentifier (v[counter]))
         {
            methodError = ILLEGAL_PARAMETER_DECLARATION;
            this.param = new String[0];
            this.var = new String[0];
            return;
         }

         counter++;
      }

      this.param = p;
      this.var = v;
   }


   /**
    * @return   The name value
    */

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
    * Constructs the full method-name in fujaba-style, so it can be used
    * for a call to UMLMethod#getFromMethods(String);
    *
    * <pre>
    * The resulting string looks like:
    *   name(type1,type2,type3,...)
    * where type is a name without preceeding packagename
    * e.g.
    *   parseJavaParam(String)
    * </pre>
    *
    * @return   The full method-name.
    */
   public String getFullName()
   {
      // construct full method-name
      String fullMethodName = name;

      fullMethodName += "(";
      String[] param = this.getParameters();
      for (int i = 0; i < param.length; i++)
      {
         fullMethodName += TextUtil.getNameWithoutPackage (param[i]);
         if (i != param.length - 1)
         {
            fullMethodName += ",";
         }
      }
      fullMethodName += ")";

      return fullMethodName;
   }


   /**
    * Returns the return type of this Declaration.
    *
    * <pre>
    * NOTE: If no return type is defined, this MAY be a
    * constructor-declaration. In this case, please check
    * <code>MethodDeclaration#getName()</code> against
    * the name of the class this method should be placed into.
    * If they equal this declaration is a constructor.
    * So you have to adjust the returned value to
    * <code>FBaseType.CONSTRUCTOR</code>.
    * </pre>
    *
    * @return   The returnType value
    */
   public String getReturnType()
   {
      return rType;
   }


   /**
    * Returns the parameters of this Declaration as an array. If there are no parameters, an
    * array with length 0 will be returned.
    *
    * @return   The parameters value
    */
   public String[] getParameters()
   {
      return param;
   }


   /**
    * Returns the variable names of the parameters, in the same order as the parameters. If
    * a parameter has no variable, an empty String is provided in this case. The returned array
    * is exact as long as the array containing the parameters.
    *
    * @return   The parameterVariables value
    */
   public String[] getParameterVariables()
   {
      return var;
   }


   /**
    * Returns information about this declaration. These information contain:<br>
    *
    * <ul>
    *   <li> the name of the Declaration
    *   <li> the type of the Declaration
    *   <li> the visibility of this Declaration (if defined)
    *   <li> the return type (if defined)
    *   <li> the parameters and their variables (if defined)
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
         buff.append (this.name);
      }

      buff.append ("\n\n");

      buff.append ("Type:\n");

      if (isEmpty())
      {
         buff.append ("EMPTY").append ("\n");
      }
      else
      {
         buff.append ("Method -- ").append (getMethodError()).append ("\n");
      }

      buff.append ("\n");

      if (isPublic)
      {
         buff.append ("public ");
      }
      if (isProtected)
      {
         buff.append ("protected ");
      }
      if (isPackage)
      {
         buff.append ("package ");
      }
      if (isPrivate)
      {
         buff.append ("private ");
      }
      if (isStatic)
      {
         buff.append ("static ");
      }
      if (isFinal)
      {
         buff.append ("final ");
      }
      if (isAbstract)
      {
         buff.append ("abstract ");
      }
      if (isNative)
      {
         buff.append ("native ");
      }
      if (isTransient)
      {
         buff.append ("transient ");
      }
      if (isVolatile)
      {
         buff.append ("volatile ");
      }
      if (isSynchronized)
      {
         buff.append ("synchronized ");
      }

      buff.append ("\nReturn type: ");
      if (getReturnType().length() == 0)
      {
         buff.append ("<<empty>>");
      }
      else
      {
         buff.append (getReturnType());
      }

      buff.append ("\n\nParameters:\n");
      if (param.length == 0)
      {
         buff.append ("<<empty>>");
      }
      else
      {
         for (int i = 0; i < param.length; i++)
         {
            if (var[i].length() != 0)
            {
               buff.append (var[i]).append (" : ").append (param[i]);
            }
            else
            {
               buff.append (param[i]);
            }
            if (i != param.length - 1)
            {
               buff.append (", ");
            }
         }
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
    * @return   The methodError value
    */
   public String getMethodError()
   {
      if (methodError == EMPTY_DECLARATION)
      {
         return "Not a method declaration";
      }
      else
      {
         return methodError;
      }
   }


   /**
    * Get the visibility attribute of the MethodDeclaration object
    *
    * @return   The visibility value
    */
   public int getVisibility()
   {
      if (isPublic)
      {
         return 1;
      }
      if (isPackage)
      {
         return 3;
      }
      if (isProtected)
      {
         return 2;
      }
      if (isPrivate)
      {
         return 0;
      }

      return 0;
   }


   /**
    * Returns true if this Declaration's visibility is public, false otherwise.
    *
    * @return   The public value
    */
   public boolean isPublic()
   {
      return isPublic;
   }


   /**
    * Returns true if this Declaration's visibility is package-wide, false otherwise.
    *
    * @return   The package value
    */
   public boolean isPackage()
   {
      return isPackage;
   }


   /**
    * Returns true if this Declaration's visibility is protected, false otherwise.
    *
    * @return   The protected value
    */
   public boolean isProtected()
   {
      return isProtected;
   }


   /**
    * Returns true if this Declaration's visibility is private, false otherwise.
    *
    * @return   The private value
    */
   public boolean isPrivate()
   {
      return isPrivate;
   }


   /**
    * Returns true if this Declaration is final, false otherwise.
    *
    * @return   The final value
    */
   public boolean isFinal()
   {
      return isFinal;
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
    * Returns true if this Declaration is synchronized, false otherwise.
    *
    * @return   The synchronized value
    */
   public boolean isSynchronized()
   {
      return isSynchronized;
   }


   /**
    * Returns true if this Declaration is transient, false otherwise.
    *
    * @return   The transient value
    */
   public boolean isTransient()
   {
      return isTransient;
   }


   /**
    * Returns true if this Declaration is volatile, false otherwise.
    *
    * @return   The volatile value
    */
   public boolean isVolatile()
   {
      return isVolatile;
   }


   /**
    * Returns true if this Declaration is native, false otherwise.
    *
    * @return   The native value
    */
   public boolean isNative()
   {
      return isNative;
   }


   /**
    * Returns true if this MethodDeclaration is empty.
    *
    * @return   The empty value
    */
   public boolean isEmpty()
   {
      return methodError == EMPTY_DECLARATION;
   }


   /**
    * Sets all the signifiers by examining the String str. For instance, if str is "public"
    * the variable isPublic will be set to true, but only if e. g. isPrivate is NOT set to
    * true. If a conflict occurs in that way, the boolean variable "adjustType" decides, if
    * the variable type will be set to type &= ~METHOD. Thus, if adjustType is true, the type
    * will be adjusted, otherwise no type changes will be done. Returns true if, and only if
    * one of the signifiers could be set without any ambiguity or error. It returns false if
    * an error has occured or nothing could be changed because the given String was not a signifier.
    *
    * @param str         The new signifierMethod value
    * @param adjustType  The new signifierMethod value
    * @return            No description provided
    */
   private boolean setSignifierMethod (String str, boolean adjustType)
   {
      if (str.equalsIgnoreCase ("public"))
      {
         isPublic = true && ! (isPrivate || isProtected || isPackage);

         if (!isPublic)
         {
            if (adjustType)
            {
               methodError = INVALID_METHOD_DECLARATION;
            }

            return false;
         }
      }
      else
         if (str.equalsIgnoreCase ("protected"))
      {
         isProtected = true && ! (isPublic || isPackage || isPrivate);

         if (!isProtected)
         {
            if (adjustType)
            {
               methodError = INVALID_METHOD_DECLARATION;
            }

            return false;
         }
      }
      else
         if (str.equalsIgnoreCase ("package"))
      {
         isPackage = true && ! (isPublic || isProtected || isPrivate);

         if (!isPackage)
         {
            if (adjustType)
            {
               methodError = INVALID_METHOD_DECLARATION;
            }

            return false;
         }
      }
      else
         if (str.equalsIgnoreCase ("private"))
      {
         isPrivate = true && ! (isPublic || isProtected || isPackage);

         if (!isPrivate)
         {
            if (adjustType)
            {
               methodError = INVALID_METHOD_DECLARATION;
            }

            return false;
         }
      }
      else
         if (str.equalsIgnoreCase ("static"))
      {
         isStatic = true;
      }
      else
         if (str.equalsIgnoreCase ("native"))
      {
         isNative = true;
      }
      else
         if (str.equalsIgnoreCase ("synchronized"))
      {
         isSynchronized = true;
      }
      else
         if (str.equalsIgnoreCase ("abstract"))
      {
         if (isFinal)
         {
            if (adjustType)
            {
               methodError = "Method is declared to be abstract AND final";
            }

            return false;
         }

         isAbstract = true;
      }
      else
         if (str.equalsIgnoreCase ("final"))
      {
         if (isAbstract)
         {
            if (adjustType)
            {
               methodError = "Method is declared to be abstract AND final";
            }

            return false;
         }

         isFinal = true;
      }
      else
      {
         if (adjustType)
         {
            methodError = INVALID_METHOD_DECLARATION;
         }

         return false;
      }

      return true;
   }


   /**
    * Returns true, if this declaration is valid, NOT considerung parameters or return type.
    * An empty declaration is not valid.
    *
    * @return   The valid value
    */
   public boolean isValid()
   {
      return methodError != EMPTY_DECLARATION && methodError != INVALID_METHOD_DECLARATION;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param method  No description provided
    * @return        No description provided
    */
   public static String userReadableSignature (FMethod method)
   {
      String signature = null;
      for (Iterator<? extends FParam> it = method.iteratorOfParam(); it.hasNext(); )
      {
         FParam param = it.next();
         String paramText = param.getName() + ": " + param.getParamType().getName();
         if (signature != null)
         {
            signature += ", " + paramText;
         }
         else
         {
            signature = paramText;
         }
      }
      if (signature == null)
      {
         signature = method.getName() + "(): ";
      }
      else
      {
         signature = method.getName() + "( " + signature + " ): ";
      }
      signature += method.getResultType().getName();
      return signature;
   }
}

/*
 * $Log$
 * Revision 1.24  2007/03/29 13:47:12  fklar
 * use F-interfaces instead of UML
 *
 * Revision 1.23  2006/03/29 09:51:08  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.22  2006/03/22 21:05:25  fklar
 * type safety: using java 1.5 generics
 *
 * Revision 1.21  2006/03/09 17:12:14  fklar
 * + changed all remaining 'UMLBaseTypes' to 'UMLBaseType'
 * + using UMLBaseType.class.getName() instead of "de.uni_paderborn.fujaba.uml.UMLBaseTypes" whereever possible,
 * + using "de.uni_paderborn.fujaba.uml.structure.UMLBaseType" instead of "de.uni_paderborn.fujaba.uml.UMLBaseTypes" otherwise
 *
 */
