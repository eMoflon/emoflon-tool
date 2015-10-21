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

import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;
import de.uni_paderborn.fujaba.preferences.FujabaCorePreferenceKeys;


/**
 * This class just shortens the name FujabaDebug. In addition, it provides a boolean function
 * that may be used in an if statement: <pre>
 * if (FD.isOn (OD.DEBUG_LEVEL_F)) log.debug ("Hello world!");
 * </pre> This may replace old statements of the form: <pre>
 * FujabaDebug.println (OptionsDebug.DEBUG_LEVEL_F, "Hello world!");
 * </pre> The new statement does not perform the computation of the complex string output message,
 * in case the message is filtered. This could save a lot of runtime.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class FD extends FujabaDebug
{

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static boolean DEF_DEFAULT_PLUGIN_DEBUG = false;


   /**
    * Get the on attribute of the FD class
    *
    * @return   The on value
    */
   public static boolean isOn()
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean (FujabaCorePreferenceKeys.DEBUG_DEFAULT);
   }


   /**
    * Get the on attribute of the FD class
    *
    * @param debugValue  No description provided
    * @return            The on value
    */
   public static boolean isOn (String debugValue)
   {
      return  (FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean (debugValue));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   public static void println (String out)
   {
      _println (out);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   public static void println (Object out)
   {
      _println (out);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   public static void print (String out)
   {
      _print (out);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param out  No description provided
    */
   public static void print (Object out)
   {
      _print (out);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static boolean removeYouPrinted = false;


   /**
    * Get the removeYouPrinted attribute of the FD class
    *
    * @return   The removeYouPrinted value
    */
   public static boolean isRemoveYouPrinted()
   {
      return removeYouPrinted;
   }


   /**
    * Sets the removeYouPrinted attribute of the FD class
    *
    * @param value  The new removeYouPrinted value
    */
   public static void setRemoveYouPrinted (boolean value)
   {
      removeYouPrinted = value;
   }

   // ######################################################################

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param obj  No description provided
    * @return     No description provided
    */
   public static String toString (Object obj)
   {
      return  ( (obj == null)
         ? "null"
         : "Object[" + obj.hashCode() + "@@" + obj.getClass().getName() + "]");
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param incr  No description provided
    * @return      No description provided
    */
   public static String toString (BasicIncrement incr)
   {
      return  ( (incr == null)
         ? "null"
         : "BasicIncrement[" + incr.getID() + "@" + incr.getClass().getName() + "]");
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param type  No description provided
    * @return      No description provided
    */
   public static String toString (FType type)
   {
      // type is an interface so cast to the right one...
      if ( (type instanceof FClass))
      {
         return FD.toString ((FClass) type);
      }
      else if ( (type instanceof BasicIncrement))
      {
         return FD.toString ((BasicIncrement) type);
      }
      else if ( (type instanceof FBaseType))
      {
         return FD.toString ((FBaseType) type);
      }
      return FD.toString ((Object) type);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param incr  No description provided
    * @return      No description provided
    */
   public static String toString (FMethod incr)
   {
      return  ( (incr == null)
         ? "null"
         : "FMethod[" + incr.getID() + "@" + incr.getFullMethodName() + "]");
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param incr  No description provided
    * @return      No description provided
    */
   public static String toString (FClass incr)
   {
      return  ( (incr == null)
         ? "null"
         : "FClass[" + incr.getID() + "@" + incr.getFullClassName() + "]");
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param incr  No description provided
    * @return      No description provided
    */
   public static String toString (FBaseType incr)
   {
      return  ( (incr == null)
         ? "null"
         : "FBaseTypes[" + incr.getID() + "@" + incr.getName() + "]");
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param incr  No description provided
    * @return      No description provided
    */
   public static String toString (FParam incr)
   {
      return  ( (incr == null)
         ? "null"
         : "FParam[" + incr.getID() + "@" + incr.getName() + ":" + incr.getParamType() + "]");
   }

}

/*
 * $Log$
 * Revision 1.29  2007/03/13 13:40:35  weisemoeller
 * Restored deleted classes from last commit. Those classes have been marked as deprecated now and will probably be deleted in a few weeks (we can discuss the point of time for this on the developer days, I think.)
 *
 * The new classes have been moved back to de.fujaba for now.
 *
 * Moreover, the usage of some workspace settings has been replaced with the corresponding project specific settings.
 *
 */
