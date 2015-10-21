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


import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.beans.PropertyEditor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.plaf.ColorUIResource;

import org.apache.log4j.Logger;

import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.FeatureAccessModule;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.InvocationException;
import de.uni_kassel.features.QualifiedFieldHandler;
import de.uni_paderborn.fujaba.app.Version;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.asg.ASGTransient;
import de.uni_paderborn.fujaba.basic.BasicIncrement;
import de.uni_paderborn.fujaba.basic.FD;
import de.uni_paderborn.fujaba.basic.FujabaComparator;
import de.uni_paderborn.fujaba.gxl.GXLFilter;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.preferences.FujabaCorePreferenceKeys;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;
import de.uni_paderborn.fujaba.versioning.PathTranslator;
import de.uni_paderborn.lib.classloader.UPBClassLoader;
import de.upb.lib.plugins.PluginProperty;
import de.upb.tools.fca.FTreeSet;


/**
 * The ProjectWriter for .fpr files. In order to correctly save a project of the Fujaba stand-alone
 * version also use the FSAPreSavingHandler to initialize the FPRWriter.
 * 
 * @author christian.schneider@uni-kassel.de
 * @version $Revision$ $Date$
 * @created 17.05.2005, 11:50:50
 */
public class FPRWriter extends ProjectWriter
{

   final static transient Logger log = Logger.getLogger(FPRWriter.class);

   /**
    * StringBuffer that is target of all writing methods.
    */
   private transient StringBuffer data;

   /**
    * The current file version for Fujaba project files.
    */
   public final static transient int FILE_VERSION = 6;


   /**
    * Check if the currentField is a static, transient or read-only field.
    * If one of these conditions is true, the field is ignored.
    * 
    * @param currentField No description provided
    * @return No description provided
    */
   private boolean isIgnored(FieldHandler currentField)
   {
      // check transient first because NonResolvingFieldHandler throws exceptions when "isStatic" or "isReadOnly" is called
      boolean isTransient = ( currentField.isTransient() == null ? false : currentField.isTransient().booleanValue() );
      if (isTransient)
         return true;
      
      boolean isStatic = currentField.isStatic();
      boolean isReadOnly = currentField.isReadOnly();
      
      return isStatic || isReadOnly;
   }


   /**
    * used by writeAttributes to call stringBuffer functions
    * 
    * @param basicIncrement
    * @param fieldObject the attrib to be saved.
    * @param currentField the field description of the attrib.
    * @param setOfNeighbours to be saved incrs are inserted here and are saved later
    */
   private void callWriteToStringBuffer(BasicIncrement basicIncrement,
         Object fieldObject, FieldHandler currentField, FTreeSet setOfNeighbours)
   {
      String qualFieldName = FPRCommon.getQualifiedFieldName(currentField);
      // let's look, what has to be done.
      if (fieldObject instanceof Collection)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               (Collection) fieldObject, setOfNeighbours);
      }
      else if (fieldObject instanceof Iterator)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               (Iterator) fieldObject, setOfNeighbours);
      }
      else if (fieldObject instanceof BasicIncrement)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               (BasicIncrement) fieldObject, setOfNeighbours);
      }
      else if (fieldObject instanceof String)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               (String) fieldObject);
      }
      else if (fieldObject instanceof Boolean)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               ((Boolean) fieldObject).booleanValue());
      }
      else if (fieldObject instanceof Float)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               ((Float) fieldObject).floatValue());
      }
      else if (fieldObject instanceof Double)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               ((Double) fieldObject).doubleValue());
      }
      else if (fieldObject instanceof Short)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               ((Short) fieldObject).shortValue());
      }
      else if (fieldObject instanceof Byte)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               ((Byte) fieldObject).byteValue());
      }
      else if (fieldObject instanceof Integer)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               ((Integer) fieldObject).intValue());
      }
      else if (fieldObject instanceof Long)
      {
         writeToStringBuffer(basicIncrement, qualFieldName,
               ((Long) fieldObject).longValue());
      }
      else if (fieldObject instanceof Dimension)
      {
         writeToStringBuffer(basicIncrement, qualFieldName + "Width",
               ((Dimension) fieldObject).width);
         writeToStringBuffer(basicIncrement, qualFieldName + "Height",
               ((Dimension) fieldObject).height);
      }
      else if (fieldObject instanceof Insets)
      {
         writeToStringBuffer(basicIncrement, qualFieldName + "Top",
               ((Insets) fieldObject).top);
         writeToStringBuffer(basicIncrement, qualFieldName + "Bottom",
               ((Insets) fieldObject).bottom);
         writeToStringBuffer(basicIncrement, qualFieldName + "Left",
               ((Insets) fieldObject).left);
         writeToStringBuffer(basicIncrement, qualFieldName + "Right",
               ((Insets) fieldObject).right);
      }

      //
      // write instances of PropertyEditor
      //
      else if (fieldObject instanceof PropertyEditor)
      {
         writeToStringBuffer(basicIncrement, qualFieldName + "Value",
               ((PropertyEditor) fieldObject).getAsText());
         writeToStringBuffer(basicIncrement, qualFieldName + "Class",
               fieldObject.getClass().getName());
      }

      //
      // write ColorUIResource
      //
      else if (fieldObject instanceof ColorUIResource)
      {
         writeToStringBuffer(basicIncrement, qualFieldName + "Green",
               ((ColorUIResource) fieldObject).getGreen());
         writeToStringBuffer(basicIncrement, qualFieldName + "Red",
               ((ColorUIResource) fieldObject).getRed());
         writeToStringBuffer(basicIncrement, qualFieldName + "Blue",
               ((ColorUIResource) fieldObject).getBlue());
      }

      //
      // write java.lang.reflect.Method
      //
      else if (fieldObject instanceof Method)
      {
         throw new UnsupportedOperationException(
               "Storing java.reflect.Method is not supported any more.");
      }
      else
      {
         if ((fieldObject != null)
               && (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD)))
         {
            log
                  .debug("Attention! attributes of type "
                        + fieldObject.getClass()
                        + " will not be saved! In order to save them edit class de.uni_paderborn.fujaba.basic.BasicIncrement:");
            log
                  .debug("1. add by copy-paste an \"else if (fieldObject instanceof ...\" line to method \"private void callWriteToStringBuffer (Object fieldObject, Field currentField, FTreeSet setOfNeighbours)\"");
            log.debug("2. add by copy-paste a method \"writeToStringBuffer\"");
            log
                  .debug("3. add by copy-paste a method \"readFromStringTokenizer\"");
         }
      }
   }


   public void writeClassToStringBuffer(BasicIncrement basicIncrement,
         StringBuffer data, FTreeSet savedIncrements, FTreeSet setOfNeighbours)
   {
      if (savedIncrements.contains(basicIncrement)
            || basicIncrement instanceof ASGTransient)
      {
         // do not save this increment since
         // either the increment is already saved
         // or it implements the transient interface and should not be saved at all
         return;
      }


      if ((basicIncrement instanceof FElement)
            && !((FElement) basicIncrement).isPersistent())
      {
         // Do not save the increment if it is marked as non-persistent.
         return;
      }
      
      if (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD))
      {
         log.info("Adding: " + FD.toString(basicIncrement));
      }

      // do not dump this again
      savedIncrements.add(basicIncrement);

      if (!savedIncrements.contains(basicIncrement))
      {
         if (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD))
         {
            log.info("Tried to add " + basicIncrement.getID()
                  + " cannot get it afterwards. " + basicIncrement);
         }
      }

      // write object identification
      ClassLoader loader = basicIncrement.getClass().getClassLoader();
      String loaderID = ProjectLoader.getPersistencySupport()
            .getClassLoaderKey(loader);
      data = data.append("*;").append(basicIncrement.getID()).append(";")
            .append(basicIncrement.getClass().getName()).append(";").append(
                  loaderID);
      data.append("\n");


      // call the 'abstract' method to write all attributes
      writeAttributes(basicIncrement, data, setOfNeighbours);
   }

   // FIXME !!! Somehow remove dependency to GXLFilter or separate the GXL package from UI
   public void writeClassToStringBuffer(BasicIncrement basicIncrement,
         StringBuffer data, FTreeSet allIncrements, GXLFilter tmp)
         throws IOException
   {
      if (allIncrements.contains(basicIncrement)
            || basicIncrement instanceof ASGTransient)
      {
         // do not save thic increment since
         // either the increment is already saved
         // or it implements the transient interface and should not be saved at all
         return;
      }

      if (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD))
      {
         log.info("Adding: " + FD.toString(basicIncrement));
      }

      // do not dump this again
      allIncrements.add(basicIncrement);

      if (!allIncrements.contains(basicIncrement))
      {
         if (FD.isOn(FujabaCorePreferenceKeys.DEBUG_SAVE_LOAD))
         {
            log.info("Tried to add " + basicIncrement.getID()
                  + " cannot get it afterwards. " + basicIncrement);
         }
      }

      FTreeSet setOfNeighbours = new FTreeSet(FujabaComparator
            .getLessBasicIncr());

      if (tmp.verifyObject(basicIncrement))
      {
         // write object identification
         ClassLoader loader = basicIncrement.getClass().getClassLoader();
         String loaderID = ProjectLoader.getPersistencySupport()
               .getClassLoaderKey(loader);
         data.append("*;" + basicIncrement.getID() + ";"
               + basicIncrement.getClass().getName() + ";" + loaderID);
         data.append("\n");

         // call the 'abstract' method to write all attributes
         writeAttributes(basicIncrement, data, setOfNeighbours);

         Iterator iter = setOfNeighbours.iterator();

         while (iter.hasNext())
         {
            Object obj = iter.next();
            if ((obj instanceof FElement)
                  && (((FElement) obj).isPersistent() || FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                              FujabaCorePreferenceKeys.DEBUG_SAVE_GENERATED)))
            {
               BasicIncrement incr = (BasicIncrement) obj;
               writeClassToStringBuffer(incr, data, allIncrements, tmp);
            }
         }
      }
   }


   /**
    * Save double attribute.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         double value)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      this.data.append("~;").append(decl).append(";").append(
            Double.toString(value)).append("\n");
   }


   /**
    * Save int attribute.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         byte value)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      this.data.append("~;").append(decl).append(";").append(
            Byte.toString(value)).append("\n");
   }


   /**
    * Save int attribute.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         int value)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      this.data.append("~;").append(decl).append(";").append(
            Integer.toString(value)).append("\n");
   }


   /**
    * Save long attribute.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         long value)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      this.data.append("~;").append(decl).append(";").append(
            Long.toString(value)).append("\n");
   }


   /**
    * Save boolean attribute.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         boolean value)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      if (value)
      {
         writeToStringBuffer(basicIncrement, decl, "true");
      }
      else
      {
         writeToStringBuffer(basicIncrement, decl, "false");
      }
   }


   private String replaceEscapeSequences(String strg)
   {
      for (int pos = strg.indexOf('\r'); pos > -1; pos = strg.indexOf('\r'))
      {
         String substr = (pos > 0 ? strg.substring(0, pos) : "");
         substr = substr.replace('\n', '\1').replace(';', '\2');
         this.data.append(substr + '\1');
         if (strg.length() > pos + 1 && strg.charAt(pos + 1) == '\n')
         {
            pos++;
         }
         strg = strg.substring(pos + 1);
      }
      strg = strg.replace('\n', '\1').replace(';', '\2');

      return strg;
   }


   /**
    * Save String Attribute.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         String strg)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      if (strg != null)
      {
         this.data.append("~;").append(decl).append(";");
         strg = replaceEscapeSequences(strg); // trinet
         this.data.append(strg).append("\n");
      }
   }


   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         BasicIncrement incr, FTreeSet setOfNeighbours)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      if ((incr instanceof FElement)
            && (((FElement) incr).isPersistent() || FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                        FujabaCorePreferenceKeys.DEBUG_SAVE_GENERATED)))
      {
         this.data.append("~;").append(decl).append(";").append(incr.getID())
               .append("\n");
         setOfNeighbours.add(incr);
      }
   }


   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         BasicIncrement incr, String text, FTreeSet setOfNeighbours)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      if ((incr != null)
            && (text != null)
            && (((FElement) incr).isPersistent() || FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                        FujabaCorePreferenceKeys.DEBUG_SAVE_GENERATED)))
      {
         this.data.append("~;").append(decl).append(";").append(text).append(
               ";");
         this.data.append(incr.getID()).append("\n");
         setOfNeighbours.add(incr);
      }
   }


   /**
    * Write a Point to the fpr file
    * 
    * @param basicIncrement
    * @param decl field declaration
    * @param point point to write to file
    * @param text appended text
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         Point point, String text)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      if ((point != null)
            && (text != null)
            && (((FElement) basicIncrement).isPersistent() || FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                        FujabaCorePreferenceKeys.DEBUG_SAVE_GENERATED)))
      {
         this.data.append("~;").append(decl).append(";").append(text).append(
               ";");
         this.data.append("point").append(point.x).append(",").append(point.y)
               .append("\n");
      }
   }


   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         String value, String text)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      if ((value != null)
            && (text != null)
            && (((FElement) basicIncrement).isPersistent() || FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                        FujabaCorePreferenceKeys.DEBUG_SAVE_GENERATED)))
      {
         this.data.append("~;").append(decl).append(";").append(text).append(
               ";");
         this.data.append("string").append(replaceEscapeSequences(value))
               .append("\n");
      }
   }


   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         Long value, String text)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      if ((value != null)
            && (text != null)
            && (((FElement) basicIncrement).isPersistent() || FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                        FujabaCorePreferenceKeys.DEBUG_SAVE_GENERATED)))
      {
         this.data.append("~;").append(decl).append(";").append(text).append(
               ";");
         this.data.append("long").append(value.toString()).append("\n");
      }
   }


   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         Double value, String text)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      if ((value != null)
            && (text != null)
            && (((FElement) basicIncrement).isPersistent() || FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                        FujabaCorePreferenceKeys.DEBUG_SAVE_GENERATED)))
      {
         this.data.append("~;").append(decl).append(";").append(text).append(
               ";");
         this.data.append("double").append(value.toString()).append("\n");
      }
   }


   /**
    * Method handles saving of collection containers like Vector or HashSet from the Java Foundation
    * classes.
    * 
    * @param basicIncrement
    * @param decl the name of the attribute
    * @param collection the default value (only needed to use overload mechanism when calling the
    *           writeTo... methods)
    * @param setOfNeighbours incr referred by this incr and attrib are entered here.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         Collection collection, FTreeSet setOfNeighbours)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }
      if (collection != null)
      {
         writeToStringBuffer(basicIncrement, decl, collection.iterator(),
               setOfNeighbours);
      }
   }


   /**
    * Method handles saving of map containers like HashMap or Hashtable from the Java Foundation
    * classes.
    * 
    * @param basicIncrement
    * @param decl the name of the attribute
    * @param map the value which should be saved
    * @param setOfNeighbours incr referred by this incr and attrib are entered here.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         Map.Entry entry, FTreeSet setOfNeighbours)
   {
      // log.info(decl);
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }
      final Object incr = entry.getValue();
      final Object tmp = entry.getKey();
      final String key;

      if (tmp instanceof String || tmp == null)
      {
         key = (String) tmp;
      }
      else if (tmp instanceof BasicIncrement)
      {
         final BasicIncrement basic = (BasicIncrement) tmp;
         key = basic.getID();
      }
      else
      {
         // dunno
         log.error("can't write key of unsupported type '"
               + tmp.getClass().getName() + "' for increment '" + incr + "'");
         throw new RuntimeException("Error: cannot save unsupported key type "
               + tmp.getClass().getName());
      }

      if (incr instanceof BasicIncrement)
      {
         writeToStringBuffer(basicIncrement, decl, (BasicIncrement) incr, key,
               setOfNeighbours);
      }
      else if (incr instanceof Point)
      {
         writeToStringBuffer(basicIncrement, decl, (Point) incr, key);
      }
      else if (incr instanceof String)
      {
         writeToStringBuffer(basicIncrement, decl, (String) incr, key);
      }
      else if (incr instanceof Long)
      {
         writeToStringBuffer(basicIncrement, decl, (Long) incr, key);
      }
      else if (incr instanceof Double)
      {
         writeToStringBuffer(basicIncrement, decl, (Double) incr, key);
      }
      else
      {
         log.error("can't write element '" + incr + "' of unsupported type '"
               + incr.getClass().getName() + "'");
         throw new RuntimeException("Error: cannot save unsupported type "
               + incr.getClass().getName());
      }
   }


   /**
    * This method handles the iterator returned by several methods of the container classes in the
    * Java Foundation classes.
    * 
    * @param basicIncrement
    * @param decl the name of the container attribute the iterator belongs to.
    * @param iter the iterator.
    * @param setOfNeighbours incr referred by this incr and attrib are entered here.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         Iterator iter, FTreeSet setOfNeighbours)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }
      if (iter != null)
      {
         Object incr;
         while (iter.hasNext())
         {
            incr = iter.next();

            if (incr instanceof BasicIncrement)
            {
               writeToStringBuffer(basicIncrement, decl, (BasicIncrement) incr,
                     setOfNeighbours);
            }
            else if (incr instanceof Map.Entry)
            {
               writeToStringBuffer(basicIncrement, decl, (Map.Entry) incr,
                     setOfNeighbours);
            }
            else
            {
               writeToStringBuffer(basicIncrement, decl, incr.getClass()
                     .getName()
                     + ";" + incr.toString());
            }
         }
      }
   }


   private FeatureAccessModule reflect;


   /**
    * Save attributes of current class object, only those which are not marked static or transient.
    */
   private void writeAttributes(BasicIncrement basicIncrement,
         StringBuffer data, FTreeSet setOfNeighbours)
   {
      // todo: implement this in a visitor
      if (basicIncrement instanceof ASGElement)
      {
         final ASGElement asgElement = (ASGElement) basicIncrement;
         asgElement.removeObsoleteUnparseInformation();

         if (this.reflect == null)
         {
            log
                  .warn("FeatureAccessModule has not been initialized: I will use the one belonging to project '"
                        + asgElement.getProject() + "'");
            reflectFor(asgElement.getProject());
         }
      }
      // end visitor

      // init the string buffer
      this.data = data;

      // Use reflection mechanism to get attributes and method of current class
      ClassHandler javaClass;
      try
      {
         javaClass = this.reflect.getClassHandler(basicIncrement);
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException(
               "Can't retrieve ClassHandler for BasicIncrement of type '"
                     + basicIncrement.getClass().getName() + "': ", e);
      }

      // now store the fields into the string buffer
      Iterator<FieldHandler> elements = javaClass.iteratorOfFields();

      while (elements.hasNext())
      {
         try
         {
            FieldHandler currentField = elements.next();
            if (!isIgnored(currentField))
            {
               // let's write into the stringBuffer data.
               try
               {
                  Object value;
                  if (currentField instanceof QualifiedFieldHandler)
                  {
                     try
                     {
                        value = ((QualifiedFieldHandler) currentField)
                              .iteratorOfEntries(basicIncrement);
                     }
                     catch (UnsupportedOperationException uoe)
                     {
                        /*
                         * Internally qualified? FIXME: Is there a good way to decide if entry
                         * iterator is just unsupported or if this is an internally qualified field?
                         */
                        value = ((QualifiedFieldHandler) currentField)
                              .iterator(basicIncrement);
                     }
                  }
                  else
                  {
                     value = currentField.read(basicIncrement);
                  }
                  callWriteToStringBuffer(basicIncrement, value, currentField,
                        setOfNeighbours);
               }
               catch (UnsupportedOperationException e)
               {
                  log.warn("Reading field not supported: " + currentField);
               }
               catch (InvocationException e)
               {
                  log.warn("Reading field not supported: " + currentField);
               }
            }
         }
         catch (SecurityException accessExcept)
         {
            log.info("Security Exception at writing attributes in "
                  + basicIncrement.getClass().getName()
                  + ". Please check Security Manager.");
         }
         catch (NullPointerException nullExcept)
         {
            nullExcept.printStackTrace();
            // ignore these (null) attributes and skip to the next.
         }
      }

      if (basicIncrement instanceof FAttr)
      {
         final FAttr attr = (FAttr) basicIncrement;
         final FType attrType = attr.getAttrType();
         if (attrType instanceof BasicIncrement)
         {
            writeToStringBuffer(basicIncrement, FAttr.ATTR_TYPE_PROPERTY,
                  (BasicIncrement) attrType, setOfNeighbours);
         }
      }
   }


   /**
    * Save float attribute.
    */
   private void writeToStringBuffer(BasicIncrement basicIncrement, String decl,
         float value)
   {
      if (this.data == null)
      {
         throw new RuntimeException("Error: super method was not called in a "
               + "'writeAttributes' method\n in class "
               + basicIncrement.toString());
      }

      this.data.append("~;").append(decl).append(";").append(
            Float.toString(value)).append("\n");
   }


   /**
    * Creates a *.bak.fpr project backup if the given outputFile quite exists.
    * 
    * @param outputFile No description provided
    * @return true if backup was created successfully
    * @throws IOException
    */
   private static boolean createProjectBackup(File outputFile)
         throws IOException
   {
      // create a *.bak.fpr backup of the file
      if (outputFile.exists())
      {
         File newBackupFile = new File(outputFile.getCanonicalPath());
         String backupName = outputFile.getCanonicalPath();
         final int extensionIndex = backupName.lastIndexOf(".fpr");
         backupName = extensionIndex > 0 ? backupName.substring(0,
               extensionIndex)
               + ".bak.fpr" : backupName + ".bak.fpr";

         File oldBackupFile = new File(backupName);
         if (oldBackupFile.exists())
         {
            if (!(oldBackupFile.canWrite() && oldBackupFile.delete()))
            {
               throw new IOException(
                     "Not able to create a new project backup. \n"
                           + "Backup of project " + outputFile.getName() + "\n"
                           + "exists and is write protected!");
            }
         }
         
         copyFile(outputFile, new File(backupName));
         return true;
      }
      return true;
   }
   

   /** Copies file content from src to dst file.
    * @param src File
    * @param dst File
    * @throws IOException
    */
   private static void copyFile(File src, File dst) throws IOException
   {
      FileReader in = null;
      FileWriter out = null;
      try
      {
         in = new FileReader(src);
         out = new FileWriter(dst);
         int c;
         while ((c = in.read()) != -1)
         {
            out.write(c);
         }
      }
      catch (IOException e)
      {
      }
      if(in != null)
      {
         in.close();
      }
      if(out != null)
      {
         out.close();
      }
   }


   /**
    * @see ProjectWriter#save(de.uni_paderborn.fujaba.metamodel.common.FProject,java.io.File,ProgressHandler,boolean)
    */
   @Override
   protected synchronized void save(FProject project, File outputFile,
         ProgressHandler progress, boolean compressed) throws IOException
   {
      boolean backupFile = outputFile != null
            && FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                  FujabaCorePreferenceKeys.CREATE_PROJECT_BACKUP);
      save(project, outputFile, progress, compressed, backupFile, false);
   }


   /**
    * @see ProjectWriter#save(de.uni_paderborn.fujaba.metamodel.common.FProject,java.io.File,ProgressHandler,boolean)
    */
   @Override
   protected synchronized void saveAs(FProject project, File outputFile,
         ProgressHandler progress, boolean compressed) throws IOException
   {
      save(project, outputFile, progress, compressed, false, true);
   }


   private void save(FProject project, File outputFile,
         ProgressHandler progress, boolean compressed, boolean backupFile,
         boolean saveAs) throws IOException
   {
      reflectFor(project);

      if (backupFile)
      {
         // try creating a backup file
         createProjectBackup(outputFile);
      }

      Writer fw = null;
      try
      {
         StringBuffer outputData = writeProjectToBuffer(outputFile, project,
               progress);

         OutputStream out = getOutputStream(outputFile, compressed);
         // now write the StringBuffer to the file
         fw = new BufferedWriter(new OutputStreamWriter(out));
         fw.write(outputData.toString(), 0, outputData.length());
         fw.close();

         if (!saveAs)
         {
            project.setFile(outputFile);
         }
      }
      catch (Throwable e)
      {
         log.error(e.getMessage());
         e.printStackTrace();
         throw new RuntimeException("An error occured while writing file '"
               + outputFile.getName() + "': " + e.getMessage(), e);
      }
      finally
      {
         if(fw != null)
         {
            fw.close();
         }
         System.gc();
      }
   }


   private void reflectFor(FProject project)
   {
      FeatureAccessModule reflect = FProjectUtility
            .getFeatureAccessModule(project);
      this.reflect = reflect;
   }


   /**
    * Write the project data to a StringBuffer.
    * 
    * @param outputFile for outputting filename only, may be null
    * @param project what to print
    * @return buffer that received serialized data from project
    */
   private StringBuffer writeProjectToBuffer(File outputFile, FProject project,
         ProgressHandler progress)
   {
      int objectTablePos;
      int pluginTablePos;
      FTreeSet savedIncrements = new FTreeSet(FujabaComparator
            .getLessBasicIncr());
      StringBuffer outputData = new StringBuffer(131072);
      PluginProperty plugin;

      writeHeaderToFile(outputFile, outputData);
      StringBuffer dependencies = getOutputForProjectDependencies(project);
      outputData.append(dependencies);

      objectTablePos = outputData.length();
      pluginTablePos = outputData.length();

      outputData.append("# Object references\n");

      // write every class and their attributes to the StringBuffer
      FTreeSet setOfNeighbours = new FTreeSet(FujabaComparator
            .getLessBasicIncr());

      writeClassToStringBuffer((ASGElement) project, outputData,
            savedIncrements, setOfNeighbours);

      // write factory products that cannot be reached from project to the string buffer
      Iterator<FFactory<? extends FElement>> factoriesIter = project
            .iteratorOfFactories();
      while (factoriesIter.hasNext())
      {
         FFactory factory = factoriesIter.next();
         try
         {
            //the iterator fetches products from every factory
            //in the dependency hierarchy
            Iterator productsIter = factory.iteratorOfProducts();
            while (productsIter.hasNext())
            {
               BasicIncrement product = (BasicIncrement) productsIter.next();
               if (!(product instanceof FElement) || ((FElement)product).getProject() == project)
               {
                  writeClassToStringBuffer(product, outputData, savedIncrements, setOfNeighbours);
               }
            }
         }
         catch (UnsupportedOperationException e)
         {
            // factory does not support listing products - continue with next factory
         }
      }


      while (!setOfNeighbours.isEmpty())
      {
         Iterator iter = setOfNeighbours.iterator();
         while (iter.hasNext())
         {
            Object obj = iter.next();
            if ((obj instanceof FElement)
                  && (((FElement) obj).isPersistent() || FujabaPreferencesManager.getFujabaCorePreferenceStore().getBoolean(
                              FujabaCorePreferenceKeys.DEBUG_SAVE_GENERATED))
                              //make sure it is in the project to be saved
                              && ((FElement)obj).getProject() == project)
            {
               BasicIncrement incr = (BasicIncrement) obj;
               setOfNeighbours.remove(incr);
               writeClassToStringBuffer(incr, this.data, savedIncrements,
                     setOfNeighbours);
            }
            else
            {
               //prevent infinite loops and remove it
               setOfNeighbours.remove(obj);
            }
         }
      }

      // If everything is ok, insert the table [ID, objectType] at the
      // beginning of the StringBuffer
      StringBuffer objectTable = new StringBuffer(16384);
      Iterator iter = savedIncrements.iterator();
      BasicIncrement incr;

      objectTable.append("# HashTable of this File\n");
      objectTable.append("-;HashTableLength;");
      objectTable.append(Integer.toString(savedIncrements.size())).append("\n");

      StringBuffer pluginBuffer = new StringBuffer();
      pluginBuffer.append("# Used Fujaba core\n");
      // the fujaba core entry has the form: $;name;id;major;minor;revision
      pluginBuffer.append("$;The Fujaba kernel;"
            + UPBClassLoader.DEFAULT_CLASSLOADER + ";"
            + Version.get().getMajor() + ";" + Version.get().getMinor() + ";"
            + Version.get().getRevision() + "\n");

      pluginBuffer.append("# used plug-ins\n");
      // the inserted plug-ins have the structure
      // $;plugin-name;pluginID;MajorVersion;MinorVersion;buildnumber
      // $;My Plugin;de.upb.myplugin.MyPlugin;1;10;1020

      Map<String, PluginProperty> pluginHashtable = new Hashtable<String, PluginProperty>();

      ClassLoader theLoader; // responsible for fujaba core

      // String id = null;
      while (iter.hasNext())
      {
         incr = (BasicIncrement) iter.next();
         theLoader = incr.getClass().getClassLoader();

         if (ProjectLoader.getPersistencySupport().isPluginClassLoader(
               theLoader))
         {
            // a plug-in is responsible for this object
            String loaderID = ProjectLoader.getPersistencySupport()
                  .getClassLoaderKey(theLoader);
            plugin = ProjectLoader.getPersistencySupport().getPluginProperty(
                  loaderID);
            if (plugin == null)
            {
               System.err.println("***\nCan not extract plug-in: " + loaderID
                     + " from plug-in list.\n***");
               continue;
            }
            if (pluginHashtable.containsKey(plugin.getPluginID()))
            {
            }
            else
            {
               pluginHashtable.put(plugin.getPluginID(), plugin);
               pluginBuffer
                     .append("$;" + plugin.getName() + ";"
                           + plugin.getPluginID() + ";" + plugin.getMajor()
                           + ";" + plugin.getMinor() + ";"
                           + plugin.getBuildNumber() + "\n");
            }
            objectTable.append("+;").append(incr.getID()).append(";");
            objectTable.append(incr.getClass().getName()).append(";");
            objectTable.append(plugin.getPluginID());
         }
         else
         {
            objectTable.append("+;").append(incr.getID()).append(";");
            objectTable.append(incr.getClass().getName()).append(";");
            objectTable.append(UPBClassLoader.DEFAULT_CLASSLOADER);
         }
         if (incr instanceof FElement
               && ((FElement) incr).getFactoryKey() != null)
         {
            objectTable.append(";");
            objectTable.append(((FElement) incr).getFactoryKey());
         }
         objectTable.append("\n");
      }

      outputData.insert(pluginTablePos, pluginBuffer);

      outputData.insert(objectTablePos + pluginBuffer.length(), objectTable);
      return outputData;
   }


   /**
    * @param outputFile
    * @param outputData
    */
   private void writeHeaderToFile(File outputFile, StringBuffer outputData)
   {
      // write the header at the beginning of the file
      outputData.append("# Fujaba-Project-File (do not alter this file!!!)\n");
      if (outputFile != null)
      {
         outputData.append("# Filename: ").append(outputFile.getName()).append(
               "\n");
      }
      outputData.append("# Date    : ").append(
            new Date(System.currentTimeMillis()).toString()).append("\n");
      outputData.append("-;FileVersion;")
            .append(Integer.toString(FILE_VERSION)).append("\n");
   }
   
   private StringBuffer getOutputForProjectDependencies(FProject project)
   {
      Iterator<? extends FProject> i = project.iteratorOfRequires();
      StringBuffer dependencies = new StringBuffer();
      dependencies.append("# dependencies to other plugins\n");
      while(i.hasNext())
      {
         FProject required = i.next();
         dependencies.append("?;");
         dependencies.append(required.getID());
         dependencies.append(";");
         String filePath = required.getFile().getAbsolutePath();
         String basePath = project.getFile().getParentFile().toString();
         filePath = PathTranslator.translateToRelativePath(basePath, filePath);
         
         dependencies.append(filePath);
         dependencies.append(";");
         dependencies.append(required.getName());
         dependencies.append("\n");
      }
      return dependencies;
   }
}

/*
 * $Log$ Revision 1.34 2007/03/13 13:40:20 weisemoeller Restored deleted classes from last commit.
 * Those classes have been marked as deprecated now and will probably be deleted in a few weeks (we
 * can discuss the point of time for this on the developer days, I think.)
 * 
 * The new classes have been moved back to de.fujaba for now.
 * 
 * Moreover, the usage of some workspace settings has been replaced with the corresponding project
 * specific settings.
 * 
 * Revision 1.33 2007/03/13 10:46:18 weisemoeller Replaced old dialogs with xml based ones.
 * 
 * I have replaced the preferences dialogs in Fujaba (and MOFLON) with this commit. If you want to
 * change or create dialogs, take a look at the file dialogs.xsd in the DTDs directory and the
 * existing dialog.xml in the properties directory. Since the properties backend has been changed as
 * well, I needed to modify a large number of classes to use the new backend. If you notice unusual
 * behaviour and suppose it to be related to this, please let me
 * (ingo,weisemoeller@es.tu-darmstadt.de) know.
 * 
 * Revision 1.32 2007/03/12 16:08:36 mtt synchronized interface save-methods to avoid parallel
 * saving of project files using the same writer with a shared data stringbuffer
 * 
 * Revision 1.31 2007/02/27 14:47:44 lowende Introduced a "save as" feature into ProjectWriter to
 * fix a bug in Reclipse. Revision 1.30 2006/05/31 14:22:16 cschneid save to same file works
 * (including test case), some more generics
 * 
 * Revision 1.29 2006/05/29 18:53:07 fklar moved call to 'FProject#setSaved(true)' to class
 * 'ProjectWriter'
 * 
 * Revision 1.28 2006/05/10 14:56:55 cschneid umlAbstract etc throw exception again, specified
 * transient property for those fields, methodBody field made 'plain'
 * 
 * Revision 1.27 2006/05/08 17:02:08 creckord Fix for saving qualified fields (like
 * ASGInformation.information) (hopefully correct now)
 * 
 * Revision 1.25 2006/05/08 15:29:58 creckord Fix for saving qualified fields (like
 * ASGInformation.information)
 * 
 * Revision 1.24 2006/05/04 15:34:37 lowende Saving of FPR files fixed. Revision 1.23 2006/05/03
 * 13:43:46 lowende Exception catched when saving FPR-files. Revision 1.22 2006/04/10 14:25:28
 * cschneid catch UnsupportedOperationException
 * 
 * Revision 1.21 2006/03/15 22:13:18 lowende Some compile warnings removed.
 */
