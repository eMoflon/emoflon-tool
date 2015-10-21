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
package de.uni_paderborn.fujaba.project;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import de.uni_kassel.features.FieldHandler;
import de.uni_paderborn.fujaba.basic.BasicIncrement;
import de.uni_paderborn.fujaba.basic.FujabaDebug;
import de.uni_paderborn.fujaba.preferences.FujabaCorePreferenceKeys;


/**
 * @author    christian.schneider@uni-kassel.de
 * @version   $Revision$ $Date$
 * @created   17.05.2005, 14:41:27
 */
public class FPRCommon
{
   /**
    * It is important that every Object has an unique identifier. But the method hashCode()
    * is not unique in every case. So here are our own methods to create an unique ID. This
    * implementation is not very fine but I hope it will be very fast and that's very important!
    */
   private static transient StringBuffer uniqueID = new StringBuffer ("id0");
   /**
    * buffer is stored to reduce memory footprint.
    */
   private static transient StringBuffer fieldNameStringBuffer = null;


   /**
    * Sets the uniqueId attribute of the BasicIncrement class
    *
    * @param newUniqueID  The new uniqueId value
    */
   protected static void setUniqueId (StringBuffer newUniqueID)
   {
      uniqueID = newUniqueID;
   }

   private static final Map<BasicIncrement, String> ids = new WeakHashMap<BasicIncrement, String>();
   private static final Map<String, WeakReference<BasicIncrement>> objects = new WeakHashMap<String, WeakReference<BasicIncrement>>();

   static synchronized void setID(BasicIncrement increment, String iD)
   {
      ids.put( increment, iD );
      objects.put(iD, new WeakReference<BasicIncrement>(increment));
   }

   public static synchronized String getID(BasicIncrement increment)
   {
      return ids.get(increment);
   }
   
   public static synchronized void removeID(BasicIncrement object, String id)
   {
      if (objects.containsKey(id))
      {         
         objects.remove(id);
      }
      if (ids.containsKey(object))
      {
         ids.remove(object);
      }
   }
   
   public static synchronized BasicIncrement getObject(String iD)
   {
      WeakReference<BasicIncrement> ref = objects.get(iD);
      if(ref != null)
      {
         return ref.get();
      }
      return null;
   }


   /**
    * Get a new unique (vm scope) ID.
    *
    * @param forIncrement increment which receives the new id (may be null)
    * @return   new ID
    * @throws IllegalArgumentException If an ID has already been provided for the given BasicIncrement
    * @throws RuntimeException if an internal error occurred during parsing of the new ID
    */
   public static synchronized String getUniqueID(BasicIncrement forIncrement) throws IllegalArgumentException, RuntimeException
   {
      if ( forIncrement != null && getID(forIncrement) != null )
      {
         throw new IllegalArgumentException("increment already has an ID");
      }
      boolean done = false;
      int pos = uniqueID.length() - 1;

      while (!done)
      {
         switch (uniqueID.charAt (pos))
         {
            case '0':
               uniqueID.setCharAt (pos, '1');
               done = true;
               break;
            case '1':
               uniqueID.setCharAt (pos, '2');
               done = true;
               break;
            case '2':
               uniqueID.setCharAt (pos, '3');
               done = true;
               break;
            case '3':
               uniqueID.setCharAt (pos, '4');
               done = true;
               break;
            case '4':
               uniqueID.setCharAt (pos, '5');
               done = true;
               break;
            case '5':
               uniqueID.setCharAt (pos, '6');
               done = true;
               break;
            case '6':
               uniqueID.setCharAt (pos, '7');
               done = true;
               break;
            case '7':
               uniqueID.setCharAt (pos, '8');
               done = true;
               break;
            case '8':
               uniqueID.setCharAt (pos, '9');
               done = true;
               break;
            case '9':
               uniqueID.setCharAt (pos, '0');
               pos--;
               break;
            case '.':
               uniqueID.insert (pos + 1, '1');
               done = true;
               break;
            default:
               throw new RuntimeException ("Unknown ID format:" + uniqueID);
         }
         if (pos < 2)
         {
            uniqueID.insert (2, '1');
            done = true;
         }
      }

      String iD = uniqueID.toString();
      if ( forIncrement != null ) setID(forIncrement, iD);
      return iD;
   }


   /**
    * Get the qualified field name of a reflection Field.
    *
    * @param field  field of interest
    * @return       the qualified field name (e.g. my.package.MyClass.myField)
    */
   static String getQualifiedFieldName (FieldHandler field)
   {
      if (fieldNameStringBuffer == null)
      {
         fieldNameStringBuffer = new StringBuffer();
      }
      else
      {
         fieldNameStringBuffer.delete (0, fieldNameStringBuffer.length());
      }
      fieldNameStringBuffer.append (field.getClassHandler().getName()).append ("::").append (field.getName());
      return fieldNameStringBuffer.toString();
   }


   /**
    * For backward compatibility: returns either the full qualified fieldname (declaringClass::fieldName)
    * if it is a key in the map or the normal fieldname otherwise.
    *
    * @param map          map containing full qualified field names as keys
    * @param field        field to be named
    * @param fieldSuffix  suffix appended to the full qualified field name
    * @return             the used field name
    */
   static String getCorrectFieldName (Map map, FieldHandler field, String fieldSuffix)
   {
      String fieldName = field.getName();
      String declaringClass = field.getClassHandler().getName();

      FujabaDebug.println (FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD, "fieldName: " + fieldName);
      FujabaDebug.println (FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD, "declaringClass: " + declaringClass);

      if (fieldNameStringBuffer == null)
      {
         fieldNameStringBuffer = new StringBuffer();
      }
      else
      {
         fieldNameStringBuffer.delete (0, fieldNameStringBuffer.length());
      }
      fieldNameStringBuffer.append (declaringClass).append ("::").append (fieldName).append (fieldSuffix);
      String fieldNameString = fieldNameStringBuffer.toString();

      if (map.containsKey (fieldNameString))
      {
         FujabaDebug.println (FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD, "map contains " + fieldNameStringBuffer.toString());
         return fieldNameString;
      }
      else
      {
         FujabaDebug.println (FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD, "!!! map does not contain " + fieldNameStringBuffer.toString());
         return fieldName;
      }
   }
}

/*
 * $Log$
 * Revision 1.6  2007/03/13 13:40:20  weisemoeller
 * Restored deleted classes from last commit. Those classes have been marked as deprecated now and will probably be deleted in a few weeks (we can discuss the point of time for this on the developer days, I think.)
 *
 * The new classes have been moved back to de.fujaba for now.
 *
 * Moreover, the usage of some workspace settings has been replaced with the corresponding project specific settings.
 *
 */
