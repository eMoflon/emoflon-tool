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
 * An abstract factory class for source code. If you want to add an specific factory for example
 * Java source code, derive this class and fill the abstract method with life. The indentString
 * contains the string which has to be inserted for every line and indent indicates how many
 * times.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public abstract class SourceCodeFactory
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int ADD_METHOD = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int REMOVE_METHOD = ADD_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_METHOD = REMOVE_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int IS_METHOD = GET_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int SET_METHOD = IS_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int HAS_IN_METHOD = SET_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int ITERATOR_OF_METHOD = HAS_IN_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_FROM_METHOD = ITERATOR_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int REMOVE_YOU_METHOD = GET_FROM_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int SIZE_OF_METHOD = REMOVE_YOU_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int REMOVE_ALL_FROM_METHOD = SIZE_OF_METHOD + 1;

   // Special methods for qualified assocs.
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_VALUE_FROM_METHOD = REMOVE_ALL_FROM_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_KEY_FROM_METHOD = GET_VALUE_FROM_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int HAS_VALUE_IN_METHOD = GET_KEY_FROM_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int HAS_KEY_IN_METHOD = HAS_VALUE_IN_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int HAS_ENTRY_IN_METHOD = HAS_KEY_IN_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int KEYS_OF_METHOD = HAS_ENTRY_IN_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int REMOVE_KEY_FROM_METHOD = KEYS_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int REMOVE_VALUE_FROM_METHOD = REMOVE_KEY_FROM_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int REMOVE_ENTRY_FROM_METHOD = REMOVE_VALUE_FROM_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int ENTRIES_OF_METHOD = REMOVE_ENTRY_FROM_METHOD + 1;

   // Special method for ordered assoc
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_AT_METHOD = ENTRIES_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int INDEX_OF_METHOD = GET_AT_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int LAST_INDEX_OF_METHOD = INDEX_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int IS_BEFORE_OF_METHOD = LAST_INDEX_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int IS_AFTER_OF_METHOD = IS_BEFORE_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_FIRST_OF_METHOD = IS_AFTER_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_LAST_OF_METHOD = GET_FIRST_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_NEXT_OF_METHOD = GET_LAST_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_NEXT_INDEX_OF_METHOD = GET_NEXT_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_PREVIOUS_OF_METHOD = GET_NEXT_INDEX_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_PREVIOUS_INDEX_OF_METHOD = GET_PREVIOUS_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int ADD_BEFORE_OF_METHOD = GET_PREVIOUS_INDEX_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int ADD_AFTER_OF_METHOD = ADD_BEFORE_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int SET_IN_METHOD = ADD_AFTER_OF_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int GET_KEY_FOR_METHOD = SET_IN_METHOD + 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int KEY_CHANGED_IN_METHOD = GET_KEY_FOR_METHOD + 1;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static String indentString = "   ";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static SourceCodeFactory sourceCodeFactory;


   /**
    * Returns the currently selected source code factory. The default
    * factory is the {@link JavaFactory}.
    *
    * @return the currently selected source code factory.
    * 
    * @see {@link #setSourceCodeFactory(SourceCodeFactory)}
    */
   public static SourceCodeFactory get()
   {
      if (sourceCodeFactory == null)
      {
         sourceCodeFactory = new JavaFactory();
      }
      return sourceCodeFactory;
   }
   
   /**
    * Sets the source code factory to be used. If generating code for
    * a language different to Java you might have to set your language
    * specific factory here.
    * 
    * @param newFactory the new source code factory
    */
   public static void setSourceCodeFactory(SourceCodeFactory newFactory)
   {
      if (newFactory == null)
      {
         throw new IllegalArgumentException("The argument must not be null.");
      }
      
      sourceCodeFactory = newFactory;
   }

   /**
    * Get the indentString attribute of the SourceCodeFactory object
    *
    * @return   The indentString value
    */
   public String getIndentString()
   {
      return indentString;
   }


   /**
    * Sets the indentString attribute of the SourceCodeFactory object
    *
    * @param newStrg  The new indentString value
    */
   public void setIndentString (String newStrg)
   {
      if (newStrg != null)
      {
         indentString = newStrg;
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static int indent = 0;


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void incIndent()
   {
      indent++;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void decIndent()
   {
      indent--;
      if (indent < 0)
      {
         indent = 0;
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public StringBuffer newLine()
   {
      StringBuffer newBuf = new StringBuffer();

      for (int i = 0; i < indent; i++)
      {
         newBuf.append (indentString);
      }

      return newBuf;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param strg  No description provided
    * @return      No description provided
    */
   public static StringBuffer upFirstChar (String strg)
   {
      StringBuffer newBuf = new StringBuffer (strg);
      if (strg.length() > 0)
      {
         newBuf.setCharAt (0, Character.toUpperCase (newBuf.charAt (0)));
      } // if

      return newBuf;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param n  No description provided
    * @param c  No description provided
    * @return   No description provided
    */
   public StringBuffer itChar (int n, char c)
   {
      StringBuffer result = new StringBuffer (n);

      for (; n > 0; n--)
      {
         result.append (c);
      }

      return result;
   }


   /**
    * Get the accessMethodName attribute of the SourceCodeFactory object
    *
    * @param name  No description provided
    * @param type  No description provided
    * @return      The accessMethodName value
    */
   public String getAccessMethodName (String name, int type)
   {
      StringBuffer result = new StringBuffer();
      boolean oldNames = true; //TODO:Options

      switch (type)
      {
         case GET_METHOD:
            result.append ("get");
            break;
         case IS_METHOD:
            result.append ("is");
            break;
         case SET_METHOD:
            result.append ("set");
            break;
         case HAS_IN_METHOD:
            result.append ("hasIn");
            break;
         case ITERATOR_OF_METHOD:
            if (!"".equals (name))
            {
               result.append ("iteratorOf");
            }
            else
            {
               result.append ("iterator");
            }
            break;
         case REMOVE_YOU_METHOD:
            return "removeYou";
         case ADD_METHOD:
            if (!"".equals (name))
            {
               result.append ("addTo");
            }
            else
            {
               result.append ("add");
            }
            break;
         case REMOVE_METHOD:
            if (!"".equals (name))
            {
               result.append ("removeFrom");
            }
            else
            {
               result.append ("remove");
            }
            break;
         case GET_FROM_METHOD:
            result.append ("getFrom");
            break;
         case SIZE_OF_METHOD:
            result.append ("sizeOf");
            break;
         case REMOVE_ALL_FROM_METHOD:
            result.append ("removeAllFrom");
            break;
         // ordered, sorted assoc
         case INDEX_OF_METHOD:
            result.append ("indexOf");
            break;
         case LAST_INDEX_OF_METHOD:
            result.append ("lastIndexOf");
            break;
         case IS_BEFORE_OF_METHOD:
            result.append ("isBeforeOf");
            break;
         case IS_AFTER_OF_METHOD:
            result.append ("isAfterOf");
            break;
         case GET_FIRST_OF_METHOD:
            result.append ("getFirstOf");
            break;
         case GET_LAST_OF_METHOD:
            result.append ("getLastOf");
            break;
         case GET_NEXT_OF_METHOD:
            result.append ("getNextOf");
            break;
         case GET_NEXT_INDEX_OF_METHOD:
            if (oldNames)
            {
               result.append ("getNextIndexOf");
            }
            else
            {
               result.append ("getNextOf");
            }
            break;
         case GET_PREVIOUS_OF_METHOD:
            result.append ("getPreviousOf");
            break;
         case GET_PREVIOUS_INDEX_OF_METHOD:
            if (oldNames)
            {
               result.append ("getPreviousIndexOf");
            }
            else
            {
               result.append ("getPreviousOf");
            }
            break;
         case ADD_BEFORE_OF_METHOD:
            result.append ("addBeforeOf");
            break;
         case ADD_AFTER_OF_METHOD:
            result.append ("addAfterOf");
            break;
         // Qualified assoc methods.
         case GET_VALUE_FROM_METHOD:
            result.append ("getValueFrom");
            break;
         case GET_KEY_FROM_METHOD:
            result.append ("getKeyFrom");
            break;
         case HAS_VALUE_IN_METHOD:
            result.append ("hasValueIn");
            break;
         case HAS_KEY_IN_METHOD:
            result.append ("hasKeyIn");
            break;
         case HAS_ENTRY_IN_METHOD:
            result.append ("hasEntryIn");
            break;
         case KEYS_OF_METHOD:
            result.append ("keysOf");
            break;
         case REMOVE_VALUE_FROM_METHOD:
            result.append ("removeValueFrom");
            break;
         case REMOVE_KEY_FROM_METHOD:
            result.append ("removeKeyFrom");
            break;
         case REMOVE_ENTRY_FROM_METHOD:
            result.append ("removeEntryFrom");
            break;
         case ENTRIES_OF_METHOD:
            result.append ("entriesOf");
            break;
         case GET_AT_METHOD:
            if (oldNames)
            {
               result.append ("get").append (upFirstChar (name)).append ("At");
               return result.toString();
            }
            else
            {
               result.append ("getFrom");
            }
            break;
         case SET_IN_METHOD:
            result.append ("setIn");
            break;
         case GET_KEY_FOR_METHOD:
            result.append ("getKeyFor");
            break;
         case KEY_CHANGED_IN_METHOD:
            result.append ("keyChangedIn");
            break;
      }

      result.append (upFirstChar (name));

      return result.toString();
   }


   /**
    * Get the fullAccessMethodName attribute of the SourceCodeFactory object
    *
    * @param name           No description provided
    * @param fieldTypeName  No description provided
    * @param type           No description provided
    * @return               The fullAccessMethodName value
    */
   public String getFullAccessMethodName (String name, String fieldTypeName, int type)
   {
      StringBuffer result = new StringBuffer();

      switch (type)
      {
         case GET_METHOD:
            result.append ("get");
            result.append (upFirstChar (name)).append ("()");
            break;
         case IS_METHOD:
            result.append ("is");
            result.append (upFirstChar (name)).append ("()");
            break;
         case SET_METHOD:
            result.append ("set");
            result.append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
         case HAS_IN_METHOD:
            result.append ("hasIn").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
         case ITERATOR_OF_METHOD:
            if (!"".equals (name))
            {
               result.append ("iteratorOf").append (upFirstChar (name)).append ("()");
            }
            else
            {
               result.append ("iterator()");
            }
            break;
         case REMOVE_YOU_METHOD:
            result.append ("removeYou()");
            break;
         case ADD_METHOD:
            if (!"".equals (name))
            {
               result.append ("addTo").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            }
            else
            {
               result.append ("add(").append (fieldTypeName).append (")");
            }
            break;
         case REMOVE_METHOD:
            if (!"".equals (name))
            {
               result.append ("removeFrom").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            }
            else
            {
               result.append ("remove(").append (fieldTypeName).append (")");
            }
            break;
         case SIZE_OF_METHOD:
            result.append ("sizeOf").append (upFirstChar (name)).append ("()");
            break;
         case REMOVE_ALL_FROM_METHOD:
            result.append ("removeAllFrom").append (upFirstChar (name)).append ("()");
            break;
         // Special methods for qualified assocs.
         case GET_VALUE_FROM_METHOD:
            result.append ("getValueFrom").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
         case GET_KEY_FROM_METHOD:
            result.append ("getKeyFrom").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
         case HAS_VALUE_IN_METHOD:
            result.append ("hasValueIn").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
         case HAS_KEY_IN_METHOD:
            result.append ("hasKeyIn").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
         case HAS_ENTRY_IN_METHOD:
            result.append ("hasEntryIn").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
         case KEYS_OF_METHOD:
            result.append ("keysOf").append (upFirstChar (name)).append ("()");
            break;
         case REMOVE_VALUE_FROM_METHOD:
            result.append ("removeValueFrom").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
         case REMOVE_KEY_FROM_METHOD:
            result.append ("removeKeyFrom").append (upFirstChar (name)).append ("(").append (fieldTypeName).append (")");
            break;
      }

      return result.toString();
   }


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
   public abstract String assocCommentary (String fClassName, String fRoleName, String fCard, String assocName, String constraint, String sClassName, String sRoleName, String sCard, String fQualifierName, String sQualifierName, boolean fIsRef, boolean sIsRef, boolean fIsAggr, boolean sIsAggr);


   /**
    * Get the assocAccessMethodLine attribute of the SourceCodeFactory object
    *
    * @param type           No description provided
    * @param name           No description provided
    * @param upperBound     No description provided
    * @param qualifier      No description provided
    * @param selfQualified  No description provided
    * @return               The assocAccessMethodLine value
    */
   public abstract String getAssocAccessMethodLine (int type, String name, int upperBound, boolean qualifier, boolean selfQualified);


   /**
    * Get the assocAccessMethodLine attribute of the SourceCodeFactory object
    *
    * @param type           No description provided
    * @param name           No description provided
    * @param upperBound     No description provided
    * @param selfQualified  No description provided
    * @return               The assocAccessMethodLine value
    */
   public String getAssocAccessMethodLine (int type, String name, int upperBound, boolean selfQualified)
   {
      return getAssocAccessMethodLine (type, name, upperBound, false, selfQualified);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param roleName            No description provided
    * @param type                No description provided
    * @param toOne               No description provided
    * @param isQualified         No description provided
    * @param isPartnerQualified  No description provided
    * @param isUsingAttr         No description provided
    * @param aggregation         No description provided
    * @return                    No description provided
    */
   public abstract String removeYouBody (String roleName, String type,
                                         boolean toOne, boolean isQualified, boolean isPartnerQualified,
                                         boolean isUsingAttr, boolean aggregation);

}

/*
 * $Log$
 * Revision 1.105  2005/11/04 01:49:27  rotschke
 * - Removed dependencies to UML metamodel from codegen
 * - Made SourceCodeFactory a singleton.
 * - Removed unnecessary convenience methods from ProjectUtility.
 * [tr]
 *
 */
