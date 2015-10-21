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
package de.uni_paderborn.fujaba.uml.common;


import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FCommentary;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FIncrement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.unparse.UMLUnparseGetter;

import java.util.StringTokenizer;


/**
 * Provides commentary nodes for the diags.
 * 
 * <h2>Associations</h2>
 * 
 * <pre>
 *                1                       1
 * UMLCommentary --------------------------- UMLIncrement
 *                comment        revComment
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLCommentary extends UMLIncrement implements FCommentary
{

   protected UMLCommentary(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * <pre>
    *                1                       1
    * UMLCommentary --------------------------- UMLIncrement
    *                comment        revComment
    * </pre>
    */
   @Property(name = REV_COMMENT_PROPERTY, accessFragment = AccessFragment.FIELD_STORAGE)
   private UMLIncrement revComment;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FCommentary#getRevComment()
    */
   public UMLIncrement getRevComment()
   {
      return revComment;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FCommentary#setRevComment(de.uni_paderborn.fujaba.metamodel.common.FIncrement)
    */
   public void setRevComment(FIncrement revComment)
   {
      if (this.revComment != revComment)
      {
         // newPartner
         UMLIncrement oldRevComment = this.revComment;
         if (this.revComment != null)
         {
            // inform old partner
            this.revComment = null;

            oldRevComment.setComment(null);
         }
         this.revComment = (UMLIncrement) revComment;
         if (revComment != null)
         {
            // inform new partner
            revComment.setComment(this);
         }
         firePropertyChange(REV_COMMENT_PROPERTY, oldRevComment, revComment);
      }
   }


   private String text = "";


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return text;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FCommentary#setText(java.lang.String)
    */
   public String setText(String text)
   {
      if (text != null && !this.text.equals(text))
      {
         String oldValue = this.text;
         this.text = text;
         firePropertyChange("text", oldValue, this.text);
      }

      return this.text;
   }


   /**
    * Get the text of this UMLCommentary in a Java-like multi-line-comment or single-line
    * representation.
    */
   public String getCommentedText()
   {
      String trimmedText = this.text.trim();

      // test if text already is a multi-line-comment
      if (trimmedText.startsWith("/*"))
      {
         // multi-line-comment is corrupt, so repair it
         if (!trimmedText.endsWith("*/"))
         {
            return (this.text + "*/\n");
         }
         // multi-line-comment is valid
         else
         {
            return this.text;
         }
      }
      // test if text already is a single-line-comment
      else if (trimmedText.startsWith("//"))
      {
         // if the text has additional lines
         // we have to make sure these start with "//" as well
         StringTokenizer tokenizer = new StringTokenizer(text, "\n", true);
         String token;
         String trimmedToken;
         StringBuffer textBuffer = new StringBuffer();

         while (tokenizer.hasMoreTokens())
         {
            token = tokenizer.nextToken();

            // is token a delimiter
            if (token.length() == 1 && token.startsWith("\n"))
            {
               textBuffer.append(token);
            }
            else
            {
               trimmedToken = token.trim();

               if (trimmedToken.startsWith("//"))
               {
                  textBuffer.append(token);
               }
               else
               {
                  textBuffer.append("// ");
                  textBuffer.append(token);
               }
            }
         }
         return textBuffer.toString();
      }
      // text is of unkown comment-format or not commented, so comment it
      else
      {
         return ("/*\n" + this.text + "*/\n");
      }
   }


   /**
    * Use this method to get an uncommented version of Java-style-commented text.
    * 
    * <pre>
    * Use this function, e.g. if you want to adjust the text of a UMLCommentary
    * but only have commented text, or don't know if the text you have
    * contains comment-symbols and you want them to be removed.
    *
    * Example code:
    *   String uncommentedText = UMLCommentary.getUncommentedText(myCommentedText);
    *   UMLCommentary theCommentary = ...;
    *   theCommentary.setText(uncommentedText);
    * </pre>
    * 
    * @param text A String containing commented Java-style text.
    * @return A String without comment-symbols.
    */
   public static String getUncommentedText(String text)
   {
      if (text == null || text.length() == 0)
      {
         return "";
      }

      // get text without leading and trailing (white)spaces
      String trimmedText = text.trim();

      // comment-types:
      // -1 unknown
      // 0 multi-line
      // 1 javadoc-style (multi-line)
      // 2 single-line
      int commentType = -1;

      // check what type of comment
      if (trimmedText.startsWith("/**"))
      {
         commentType = 1;
      }
      else if (trimmedText.startsWith("/*"))
      {
         commentType = 0;
      }
      else if (trimmedText.startsWith("//"))
      {
         commentType = 2;
      }
      else
      {
         commentType = -1;
      } // maybe uncommented text has been passed to this
      // method?

      switch (commentType)
      {
         case -1: // unknown comment-type
            break;
         case 0: // multi-line-comment
         case 1:
         { // java-doc-style-comment

            // text between start and end is the commented text
            int firstMultiIndex = -1;
            if (commentType == 0)
            {
               firstMultiIndex = text.indexOf("/*");
            }
            else
            {
               firstMultiIndex = text.indexOf("/**");
            }

            int textEndIndex = text.lastIndexOf("*/");

            String pureText;
            // correct multi-line-comment
            if (textEndIndex != -1)
            {
               pureText = text.substring(firstMultiIndex + 2, textEndIndex);
            }
            // corrupted multi-line-comment
            else
            {
               pureText = text.substring(firstMultiIndex + 2);
            }

            // trim each line, so (white)spaces will be removed
            if (commentType == 0)
            {
               return UMLCommentary.deleteInitialBlancs(pureText, false);
            }
            // additionally remove preceeding "*"s
            else
            {
               return UMLCommentary.deleteInitialBlancs(pureText, true);
            }
         }
         case 2:
         { // single-line-comment

            StringTokenizer tokenizer = new StringTokenizer(text, "\n", true);
            String token;
            String trimmedToken;
            StringBuffer textBuffer = new StringBuffer();

            // remove leading and trailing whitespace and the single-line-symbol
            while (tokenizer.hasMoreTokens())
            {
               token = tokenizer.nextToken();

               // is token a delimiter
               if (token.length() == 1 && token.startsWith("\n"))
               {
                  textBuffer.append(token);
               }
               else
               {
                  trimmedToken = token.trim();

                  if (trimmedToken.startsWith("//"))
                  {
                     textBuffer.append(trimmedToken.substring(2).trim());
                  }
                  else
                  {
                     textBuffer.append(trimmedToken);
                  }
               }
            }
            return textBuffer.toString();
         }
      }

      return text;
   }


   /**
    * Deletes leading blancs, from each line of the text and if specified preceeding asterisks as
    * well.
    * 
    * @param text A String.
    * @param removeAsterisk Should preceeding asteriks be removed as well?
    * @return The String without leading blancs.
    */
   public static String deleteInitialBlancs(String text, boolean removeAsterisk)
   {
      StringTokenizer tokenizer = new StringTokenizer(text, "\n", true);
      String token;
      String trimmedToken;
      StringBuffer textBuffer = new StringBuffer();

      // remove leading and trailing whitespace
      while (tokenizer.hasMoreTokens())
      {
         token = tokenizer.nextToken();

         // is token a delimiter
         if (token.length() == 1 && token.startsWith("\n"))
         {
            textBuffer.append(token);
         }
         else
         {
            trimmedToken = token.trim();

            // remove asterisk if specified
            if (removeAsterisk && trimmedToken.startsWith("*"))
            {
               textBuffer.append(trimmedToken.substring(1).trim());
            }
            else
            {
               textBuffer.append(trimmedToken);
            }
         }
      }
      return textBuffer.toString();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return getRevComment();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      setRevComment(null);

      super.removeYou();
   }

}

/*
 * $Log$
 * Revision 1.8  2006/11/10 13:48:28  fklar
 * + fixed spelling bug I invented some time ago: "shure" -> "sure" (as this is not a microphone)
 *
 * Revision 1.7  2006/05/30 08:36:53  lowende
 * Removed some transient flags, since they caused loss of information when saving FPR-files.
 *
 * Revision 1.6  2006/03/29 15:49:43  lowende
 * Changed creating/editing/hiding/showing of UMLCommentary.
 *
 */
