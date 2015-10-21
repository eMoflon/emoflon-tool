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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringTokenizer;

import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;
import de.uni_paderborn.fujaba.preferences.FujabaCorePreferenceKeys;


/**
 * This class makes some debug functions available to the fujaba group. Every error message
 * could be turned on or off by changing the linked member variable.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class FujabaDebug
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static boolean SETATTRSTONULL = true;


   /**
    * Get the stackTrace attribute of the FujabaDebug class
    *
    * @param throwable  No description provided
    * @param begin      No description provided
    * @param end        No description provided
    * @return           The stackTrace value
    */
   public static String getStackTrace (Throwable throwable, int begin, int end)
   {
      StringBuffer buf = new StringBuffer();

      StringWriter strWriter = new StringWriter();
      throwable.printStackTrace (new PrintWriter (strWriter));
      StringTokenizer st = new StringTokenizer (strWriter.toString(), "\n");
      for (int i = 0; st.hasMoreTokens() &&  (i < 2 + begin); i++)
      {
         st.nextToken();
      }
      for (int i = 0;  (i < end + 2) && st.hasMoreTokens(); i++)
      {
         String tmp = st.nextToken();
         buf.append ("Stack(");
         buf.append (i);
         buf.append ("):");
         buf.append (tmp.trim());
         buf.append ("\n");
      }
      return new String (buf);
   }


   /**
    * Get the stackTrace attribute of the FujabaDebug class
    *
    * @param begin  No description provided
    * @param end    No description provided
    * @return       The stackTrace value
    */
   public static String getStackTrace (int begin, int end)
   {
      return getStackTrace(new Throwable(), begin, end);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param begin  No description provided
    * @param end    No description provided
    */
   public static void printStackTrace (int begin, int end)
   {
      System.out.println (FujabaDebug.getStackTrace (begin, end));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param depth  No description provided
    */
   public static void printStackTrace (int depth)
   {
      System.out.println (FujabaDebug.getStackTrace (0, depth));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static void printStackTrace()
   {
      System.out.println (FujabaDebug.getStackTrace (0, 100000));
   }


   /**
    * Debug messages which are important for all.
    */
   public static void println()
   {
      if (FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean (FujabaCorePreferenceKeys.DEBUG_DEFAULT))
      {
         FujabaDebug.println ("");
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   public static void println (String out)
   {
      FujabaDebug.println ((Object) out);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param debugValue  No description provided
    * @param out         No description provided
    */
   public static void println (String debugValue, String out)
   {
      FujabaDebug.println (debugValue, (Object) out);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   public static void println (Object out)
   {
      if (FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean (FujabaCorePreferenceKeys.DEBUG_DEFAULT))
      {
         _println (out);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param debugValue  No description provided
    * @param out         No description provided
    */
   public static void println (String debugValue, Object out)
   {
      if (FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(debugValue)
            && FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                  FujabaCorePreferenceKeys.DEBUG_MODE))
      {
         _println (out);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   protected static void _println (Object out)
   {
      if (out != null)
      {
         System.out.println (out.toString());
      }
      else
      {
         System.out.println();
      }
   }


   /**
    * Debug messages which are important for all.
    *
    * @param out  No description provided
    */
   public static void print (Object out)
   {
      if (FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean (FujabaCorePreferenceKeys.DEBUG_DEFAULT))
      {
         FujabaDebug.print (out);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param debugValue  No description provided
    * @param out         No description provided
    */
   public static void print (String debugValue, Object out)
   {
      if (FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean (debugValue) && FujabaPreferencesManager.isDebugMode())
      {
         //     if (out != null)
//           {
//              System.out.print (out.toString ());
//           }
         _print (out);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   protected static void _print (Object out)
   {
      if (out != null)
      {
         System.out.print (out.toString());
      }
      else
      {
         System.out.print ("");
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static void print()
   {
      if (FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean (FujabaCorePreferenceKeys.DEBUG_DEFAULT))
      {
         FujabaDebug.print ("");
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param debugValue  No description provided
    * @param out         No description provided
    */
   public static void print (String debugValue, String out)
   {
      FujabaDebug.print (debugValue, (Object) out);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   public static void print (String out)
   {
      FujabaDebug.print ((Object) out);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static void printRemoveYouStack()
   {
      String tmp;
      int first;
      int last;

      StringWriter strWriter = new StringWriter();
      new Throwable().printStackTrace (new PrintWriter (strWriter));
      StringTokenizer st = new StringTokenizer (strWriter.toString(), "\n");

      // skip the name of the exception and the caller method
      st.nextToken();
      st.nextToken();

      // get the name of current method
      tmp = st.nextToken();
      first = tmp.lastIndexOf (' ') + 1;
      last = tmp.indexOf ('(');
      String methodName = tmp.substring (first, last);

      // count calls of removeYou
      String lastMethod = "";
      int removeYou = 1;
      while (st.hasMoreTokens())
      {
         tmp = st.nextToken();
         first = tmp.lastIndexOf (' ') + 1;
         last = tmp.indexOf ('(');
         lastMethod = tmp.substring (first, last);
         if (lastMethod.indexOf ("removeYou") != -1)
         {
            removeYou++;
         }
         else
         {
            break;
         }
      }
      if (removeYou == 1)
      {
         System.out.println (lastMethod);
      }
      for (int i = 0; i < removeYou; i++)
      {
         System.out.print ("  ");
      }
      System.out.println (methodName);
   }
}

/*
 * $Log$
 * Revision 1.37  2007/03/13 13:40:35  weisemoeller
 * Restored deleted classes from last commit. Those classes have been marked as deprecated now and will probably be deleted in a few weeks (we can discuss the point of time for this on the developer days, I think.)
 *
 * The new classes have been moved back to de.fujaba for now.
 *
 * Moreover, the usage of some workspace settings has been replaced with the corresponding project specific settings.
 *
 */
