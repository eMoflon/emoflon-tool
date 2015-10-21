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



/**
 * A factory class for Java source code. It is easier to exchange the code later with a factory.
 * The return parameter is a String object with the specified code fragment in it. The indentString
 * contains the string which has to be inserted for every line and indent indicates how many
 * times.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class JavaFactory extends SourceCodeFactory
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param fClassName      No description provided
    * @param fRoleName       No description provided
    * @param fCard           No description provided
    * @param assocName       No description provided
    * @param constraint      No description provided
    * @param sClassName      No description provided
    * @param sRoleName       No description provided
    * @param sCard           No description provided
    * @param fQualifierName  No description provided
    * @param sQualifierName  No description provided
    * @param fIsRef          No description provided
    * @param sIsRef          No description provided
    * @param fIsAggr         No description provided
    * @param sIsAggr         No description provided
    * @return                No description provided
    */
   @Override
   public String assocCommentary (String fClassName, String fRoleName, String fCard, String assocName, String constraint, String sClassName, String sRoleName, String sCard, String fQualifierName, String sQualifierName, boolean fIsRef, boolean sIsRef, boolean fIsAggr, boolean sIsAggr)
   {
      StringBuffer result = new StringBuffer();

      try
      {
         if (constraint.length() > 0)
         {
            constraint = "{" + constraint + "}";
         }

         int fClassNameLen = fClassName.length();
         int fRoleNameLen = fRoleName.length();
         int fCardLen = fCard != null ? fCard.length() : 0;
         int aNameLen = assocName.length();
         int conLen = constraint.length();
         int sRoleNameLen = sRoleName.length();
         int sCardLen = sCard.length();
         int fQualLen = fQualifierName.length();
         int sQualLen = sQualifierName.length();

         // the number of blancs in front of the first cardinality
         int leftIndent = fClassNameLen + 1;

         int upperLineLen = 1 + fCardLen + 3 + aNameLen + 3 + sCardLen + 1;
         int lowerLineLen = 1 + fRoleNameLen + 3 + conLen + 3 + sRoleNameLen + 1;

         // compute the absolute length of the whole arrow
         int arrowLen = Math.max (upperLineLen, lowerLineLen);
         int arrowSub =  ( (fIsRef) ? 1 : 0) +  ( (sIsRef) ? 1 : 0);

         // compute the number of blancs to be inserted in the smaller line
         int moreSpace = Math.abs (upperLineLen - lowerLineLen);
         int moreLeftSpace = moreSpace / 2;
         int moreRightSpace = moreSpace - moreLeftSpace;

         result.append (newLine()).append ("/**\n");
         // insert <pre> tag
         result.append (newLine()).append (" * <pre>\n");
         result.append (newLine()).append (" * ").append (itChar (leftIndent, ' '));

         if (fQualLen > 0)
         {
            result.append (itChar (fQualLen + 4, '-'));
         }

         if (fIsAggr)
         {
            result.append (" /\\ ");
         }

         result.append (" ").append (fCard);

         if (arrowLen == upperLineLen)
         {
            result.append (itChar (3, ' ')).append (assocName).append (itChar (3, ' '));
         }
         else
         {
            result.append (itChar (3 + moreLeftSpace, ' ')).append (assocName).append (itChar (3 + moreRightSpace, ' '));
         }

         result.append (sCard).append (" ");

         if (sIsAggr)
         {
            result.append (" /\\ ");
         }

         if (sQualLen > 0)
         {
            result.append (itChar (sQualLen + 4, '-'));
         }

         result.append ("\n");
         result.append (newLine()).append (" * ").append (fClassName).append (" ");

         if (fQualLen > 0)
         {
            result.append ("| ").append (fQualifierName).append (" |");
         }

         if (fIsAggr)
         {
            result.append ("<  >");
         }

         if (fIsRef)
         {
            result.append ("<");
         }

         result.append (itChar (arrowLen - arrowSub, '-'));

         if (sIsRef)
         {
            result.append (">");
         }

         if (sIsAggr)
         {
            result.append ("<  >");
         }

         if (sQualLen > 0)
         {
            result.append ("| ").append (sQualifierName).append (" |");
         }

         result.append (" ").append (sClassName).append ("\n");
         result.append (newLine()).append (" * ").append (itChar (leftIndent, ' '));

         if (fQualLen > 0)
         {
            result.append (itChar (fQualLen + 4, '-'));
         }

         if (fIsAggr)
         {
            result.append (" \\/ ");
         }

         result.append (" ").append (fRoleName);

         if (arrowLen == lowerLineLen)
         {
            result.append (itChar (3, ' ')).append (constraint).append (itChar (3, ' '));
         }
         else
         {
            result.append (itChar (3 + moreLeftSpace, ' ')).append (constraint).append (itChar (3 + moreRightSpace, ' '));
         }

         result.append (sRoleName).append (" ");

         if (sIsAggr)
         {
            result.append (" \\/ ");
         }
         if (sQualLen > 0)
         {
            result.append (itChar (sQualLen + 4, '-'));
         }

         result.append ("\n");
         // insert </pre> tag
         result.append (newLine()).append (" * </pre>\n");
         result.append (newLine()).append (" */\n");
      }
      catch (RuntimeException e)
      {
         e.printStackTrace();
      }

      return result.toString();
   }


   /**
    * Get the assocAccessMethodLine attribute of the JavaFactory object
    *
    * @param type           No description provided
    * @param name           No description provided
    * @param upperBound     No description provided
    * @param qualifier      No description provided
    * @param selfQualified  No description provided
    * @return               The assocAccessMethodLine value
    */
   @Override
   public String getAssocAccessMethodLine (int type, String name,
                                           int upperBound, boolean qualifier, boolean selfQualified)
   {
      StringBuffer result = new StringBuffer();
      if (upperBound == 1)
      {
         result.append ("set").append (upFirstChar (name));
         if (type == ADD_METHOD)
         {
            if (qualifier)
            {
               result.append (" (key, this)");
            }
            else
            {
               result.append (" (this)");
            }
         }
         else
         {
            if (qualifier)
            {
               result.append (" (null, null)");
            }
            else
            {
               result.append (" (null)");
            }
         }
      }
      else
      {
         result.append (getAccessMethodName (name, type));

         if (qualifier)
         {
            result.append (" (key, ");
            if (selfQualified)
            {
               result.append ("partnerKey, ");
            }
            result.append ("this)");
         }
         else
         {
            result.append (" (");
            if (selfQualified)
            {
               result.append ("partnerKey, ");
            }
            result.append ("this)");
         }
      }
      return result.toString();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param roleName             No description provided
    * @param type                 No description provided
    * @param toOne                No description provided
    * @param isQualified          No description provided
    * @param isPartnerQualified   No description provided
    * @param isInternalQualified  No description provided
    * @param aggregation          No description provided
    * @return                     No description provided
    */
   @Override
   public String removeYouBody (String roleName, String type,
                                boolean toOne, boolean isQualified,
                                boolean isPartnerQualified, boolean isInternalQualified,
                                boolean aggregation)
   {
      StringBuffer result = new StringBuffer();
      String varName = "tmp" + upFirstChar (roleName);

      if (toOne)
      {
         result.append (newLine()).
            append (type).append (" ").
            append (varName).append (" = ").
            append (getAccessMethodName (roleName, GET_METHOD)).
            append (" ();\n");

         result.append (newLine()).append ("if (").append (varName).append (" != null)");
         result.append ("\n").append (newLine()).append ("{\n");

         incIndent();

         result.append (newLine()).
            append (getAccessMethodName (roleName, SET_METHOD));

         if (isPartnerQualified && !isInternalQualified)
         {
            result.append (" (null, null);\n");
         }
         else
         {
            result.append (" (null);\n");
         }

         if (aggregation)
         {
            result.append (newLine()).
               append (varName).
               append (".").
               append (getAccessMethodName ("", REMOVE_YOU_METHOD)).
               append (" ();\n");
         }

         decIndent();

         result.append (newLine().append ("}\n\n"));
      }
      else
      {
         result.append (newLine()).
            append (getAccessMethodName (roleName, REMOVE_ALL_FROM_METHOD)).
            append (" ();\n\n");
      }

      return result.toString();
   } // removeYouBody
}

/*
 * $Log$
 * Revision 1.272  2006/04/06 12:04:34  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.271  2005/01/27 15:44:43  cschneid
 * bugfixes, new libraries, SDMObjectDialog improved, add activity in transition
 *
 */
