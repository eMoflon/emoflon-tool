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
package de.uni_paderborn.fujaba.preferences;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.io.IOException;


/**
 * The interface for all preference stores used in Fujaba.
 *
 * @author Oleg Travkin
 */
public interface FujabaPreferenceStore
{
   /**
    * The default-default value for boolean preferences (<code>false</code>).
    */
   public static final boolean BOOLEAN_DEFAULT_DEFAULT = false;

   /**
    * The default-default value for double preferences (<code>0.0</code>).
    */
   public static final double DOUBLE_DEFAULT_DEFAULT = 0.0;

   /**
    * The default-default value for float preferences (<code>0.0f</code>).
    */
   public static final float FLOAT_DEFAULT_DEFAULT = 0.0f;

   /**
    * The default-default value for int preferences (<code>0</code>).
    */
   public static final int INT_DEFAULT_DEFAULT = 0;

   /**
    * The default-default value for long preferences (<code>0L</code>).
    */
   public static final long LONG_DEFAULT_DEFAULT = 0L;

   /**
    * The default-default value for String preferences (<code>""</code>).
    */
   public static final String STRING_DEFAULT_DEFAULT = ""; //$NON-NLS-1$

   /**
    * The string representation used for <code>true</code> (<code>"true"</code>).
    */
   public static final String TRUE = "true"; //$NON-NLS-1$

   /**
    * The string representation used for <code>false</code> (<code>"false"</code>).
    */
   public static final String FALSE = "false"; //$NON-NLS-1$

   /**
    * <p>
    * Adds a property change listener to this preference store.
    * </p>
    * <p>
    * <b>Note</b> The types of the oldValue and newValue of the
    * generated PropertyChangeEvent are determined by whether
    * or not the typed API in IPreferenceStore was called.
    * If values are changed via setValue(name,type) the 
    * values in the PropertyChangedEvent will be of that type.
    * If they are set using a non typed API (i.e. #setToDefault
    * or using the OSGI Preferences) the values will be unconverted
    * Strings.
    * </p>
    * <p>
    * A listener will be called in the same Thread
    * that it is invoked in. Any Thread dependant listeners (such as 
    * those who update an SWT widget) will need to update in the
    * correct Thread. In the case of an SWT update you can update
    * using Display#syncExec(Runnable) or Display#asyncExec(Runnable).
    * </p>
    * <p>  
    * Likewise any application that updates an IPreferenceStore 
    * from a Thread other than the UI Thread should be aware of
    * any listeners that require an update in the UI Thread. 
    * </p>
    *
    * @param listener a property change listener
    * @see java.beans.PropertyChangeEvent
    * @see #setToDefault(String)
    * @see #setValue(String, boolean)
    * @see #setValue(String, double)
    * @see #setValue(String, float)
    * @see #setValue(String, int)
    * @see #setValue(String, long)
    * @see #setValue(String, String)
    */
   public void addPropertyChangeListener(PropertyChangeListener listener);

   /**
    * Returns whether the named preference is known to this preference
    * store.
    *
    * @param name the name of the preference
    * @return <code>true</code> if either a current value or a default
    *  value is known for the named preference, and <code>false</code> otherwise
    */
   public boolean contains(String name);

   /**
    * Fires a property change event corresponding to a change to the
    * current value of the preference with the given name.
    * <p>
    * This method is provided on this interface to simplify the implementation 
    * of decorators. There is normally no need to call this method since
    * <code>setValue</code> and <code>setToDefault</code> report such
    * events in due course. Implementations should funnel all preference
    * changes through this method.
    * </p>
    *
    * @param name the name of the preference, to be used as the property
    *  in the event object
    * @param oldValue the old value
    * @param newValue the new value
    */
   public void firePropertyChangeEvent(String name, Object oldValue,
           Object newValue);

   /**
    * Returns the current value of the boolean-valued preference with the
    * given name.
    * Returns the default-default value (<code>false</code>) if there
    * is no preference with the given name, or if the current value 
    * cannot be treated as a boolean.
    *
    * @param name the name of the preference
    * @return the boolean-valued preference
    */
   public boolean getBoolean(String name);

   /**
    * Returns the default value for the boolean-valued preference
    * with the given name.
    * Returns the default-default value (<code>false</code>) if there
    * is no default preference with the given name, or if the default 
    * value cannot be treated as a boolean.
    *
    * @param name the name of the preference
    * @return the default value of the named preference
    */
   public boolean getDefaultBoolean(String name);

   /**
    * Returns the default value for the double-valued preference
    * with the given name.
    * Returns the default-default value (<code>0.0</code>) if there
    * is no default preference with the given name, or if the default 
    * value cannot be treated as a double.
    *
    * @param name the name of the preference
    * @return the default value of the named preference
    */
   public double getDefaultDouble(String name);

   /**
    * Returns the default value for the float-valued preference
    * with the given name.
    * Returns the default-default value (<code>0.0f</code>) if there
    * is no default preference with the given name, or if the default 
    * value cannot be treated as a float.
    *
    * @param name the name of the preference
    * @return the default value of the named preference
    */
   public float getDefaultFloat(String name);

   /**
    * Returns the default value for the integer-valued preference
    * with the given name.
    * Returns the default-default value (<code>0</code>) if there
    * is no default preference with the given name, or if the default 
    * value cannot be treated as an integer.
    *
    * @param name the name of the preference
    * @return the default value of the named preference
    */
   public int getDefaultInt(String name);

   /**
    * Returns the default value for the long-valued preference
    * with the given name.
    * Returns the default-default value (<code>0L</code>) if there
    * is no default preference with the given name, or if the default 
    * value cannot be treated as a long.
    *
    * @param name the name of the preference
    * @return the default value of the named preference
    */
   public long getDefaultLong(String name);

   /**
    * Returns the default value for the string-valued preference
    * with the given name.
    * Returns the default-default value (the empty string <code>""</code>) 
    * is no default preference with the given name, or if the default 
    * value cannot be treated as a string.
    *
    * @param name the name of the preference
    * @return the default value of the named preference
    */
   public String getDefaultString(String name);

   /**
    * Returns the current value of the double-valued preference with the
    * given name.
    * Returns the default-default value (<code>0.0</code>) if there
    * is no preference with the given name, or if the current value 
    * cannot be treated as a double.
    *
    * @param name the name of the preference
    * @return the double-valued preference
    */
   public double getDouble(String name);

   /**
    * Returns the current value of the float-valued preference with the
    * given name.
    * Returns the default-default value (<code>0.0f</code>) if there
    * is no preference with the given name, or if the current value 
    * cannot be treated as a float.
    *
    * @param name the name of the preference
    * @return the float-valued preference
    */
   public float getFloat(String name);

   /**
    * Returns the current value of the integer-valued preference with the
    * given name.
    * Returns the default-default value (<code>0</code>) if there
    * is no preference with the given name, or if the current value 
    * cannot be treated as an integter.
    *
    * @param name the name of the preference
    * @return the int-valued preference
    */
   public int getInt(String name);

   /**
    * Returns the current value of the long-valued preference with the
    * given name.
    * Returns the default-default value (<code>0L</code>) if there
    * is no preference with the given name, or if the current value 
    * cannot be treated as a long.
    *
    * @param name the name of the preference
    * @return the long-valued preference
    */
   public long getLong(String name);

   /**
    * Returns the current value of the string-valued preference with the
    * given name.
    * Returns the default-default value (the empty string <code>""</code>)
    * if there is no preference with the given name, or if the current value 
    * cannot be treated as a string.
    *
    * @param name the name of the preference
    * @return the string-valued preference
    */
   public String getString(String name);
   
   /**
    * Returns the current value of the string-valued preference with the
    * given name.
    * Returns the default-default value (the empty string <code>""</code>)
    * if there is no preference with the given name, or if the current value 
    * cannot be treated as a string.
    *
    * @param name the name of the preference
    * @return the string-valued preference
    */
//   public String getValue(String name);

   /**
    * Returns whether the current value of the preference with the given name
    * has the default value.
    *
    * @param name the name of the preference
    * @return <code>true</code> if the preference has a known default value
    * and its current value is the same, and <code>false</code> otherwise
    * (including the case where the preference is unknown to this store)
    */
   public boolean isDefault(String name);

   /**
    * Returns whether the current values in this property store
    * require saving.
    *
    * @return <code>true</code> if at least one of values of 
    *  the preferences known to this store has changed and 
    *  requires saving, and <code>false</code> otherwise.
    */
   public boolean needsSaving();

   /**
    * Sets the current value of the preference with the given name to
    * the given string value without sending a property change.
    * <p>
    * This method does not fire a property change event and 
    * should only be used for setting internal preferences 
    * that are not meant to be processed by listeners.
    * Normal clients should instead call #setValue.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new current value of the preference
    */
   public void putValue(String name, String value);

   /**
    * Removes the given listener from this preference store.
    * Has no affect if the listener is not registered.
    *
    * @param listener a property change listener
    */
   public void removePropertyChangeListener(PropertyChangeListener listener);

   /**
    * Sets the default value for the double-valued preference with the
    * given name. 
    * <p>
    * Note that the current value of the preference is affected if
    * the preference's current value was its old default value, in which
    * case it changes to the new default value. If the preference's current
    * is different from its old default value, its current value is
    * unaffected. No property change events are reported by changing default
    * values.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new default value for the preference
    */
   public void setDefault(String name, double value);

   /**
    * Sets the default value for the float-valued preference with the
    * given name. 
    * <p>
    * Note that the current value of the preference is affected if
    * the preference's current value was its old default value, in which
    * case it changes to the new default value. If the preference's current
    * is different from its old default value, its current value is
    * unaffected. No property change events are reported by changing default
    * values.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new default value for the preference
    */
   public void setDefault(String name, float value);

   /**
    * Sets the default value for the integer-valued preference with the
    * given name. 
    * <p>
    * Note that the current value of the preference is affected if
    * the preference's current value was its old default value, in which
    * case it changes to the new default value. If the preference's current
    * is different from its old default value, its current value is
    * unaffected. No property change events are reported by changing default
    * values.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new default value for the preference
    */
   public void setDefault(String name, int value);

   /**
    * Sets the default value for the long-valued preference with the
    * given name. 
    * <p>
    * Note that the current value of the preference is affected if
    * the preference's current value was its old default value, in which
    * case it changes to the new default value. If the preference's current
    * is different from its old default value, its current value is
    * unaffected. No property change events are reported by changing default
    * values.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new default value for the preference
    */
   public void setDefault(String name, long value);

   /**
    * Sets the default value for the string-valued preference with the
    * given name. 
    * <p>
    * Note that the current value of the preference is affected if
    * the preference's current value was its old default value, in which
    * case it changes to the new default value. If the preference's current
    * is different from its old default value, its current value is
    * unaffected. No property change events are reported by changing default
    * values.
    * </p>
    *
    * @param name the name of the preference
    * @param defaultObject the new default value for the preference
    */
   public void setDefault(String name, String defaultObject);

   /**
    * Sets the default value for the boolean-valued preference with the
    * given name. 
    * <p>
    * Note that the current value of the preference is affected if
    * the preference's current value was its old default value, in which
    * case it changes to the new default value. If the preference's current
    * is different from its old default value, its current value is
    * unaffected. No property change events are reported by changing default
    * values.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new default value for the preference
    */
   public void setDefault(String name, boolean value);

   /**
    * Sets the current value of the preference with the given name back
    * to its default value.
    * <p>
    * Note that the preferred way of re-initializing a preference to the
    * appropriate default value is to call <code>setToDefault</code>.
    * This is implemented by removing the named value from the store, 
    * thereby exposing the default value.
    * </p>
    *
    * @param name the name of the preference
    */
   public void setToDefault(String name);

   /**
    * Sets the current value of the double-valued preference with the
    * given name.
    * <p>
    * A property change event is reported if the current value of the 
    * preference actually changes from its previous value. In the event
    * object, the property name is the name of the preference, and the
    * old and new values are wrapped as objects.
    * </p>
    * <p>
    * Note that the preferred way of re-initializing a preference to its
    * default value is to call <code>setToDefault</code>.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new current value of the preference
    */
   public void setValue(String name, double value);

   /**
    * Sets the current value of the float-valued preference with the
    * given name.
    * <p>
    * A property change event is reported if the current value of the 
    * preference actually changes from its previous value. In the event
    * object, the property name is the name of the preference, and the
    * old and new values are wrapped as objects.
    * </p>
    * <p>
    * Note that the preferred way of re-initializing a preference to its
    * default value is to call <code>setToDefault</code>.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new current value of the preference
    */
   public void setValue(String name, float value);

   /**
    * Sets the current value of the integer-valued preference with the
    * given name.
    * <p>
    * A property change event is reported if the current value of the 
    * preference actually changes from its previous value. In the event
    * object, the property name is the name of the preference, and the
    * old and new values are wrapped as objects.
    * </p>
    * <p>
    * Note that the preferred way of re-initializing a preference to its
    * default value is to call <code>setToDefault</code>.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new current value of the preference
    */
   public void setValue(String name, int value);

   /**
    * Sets the current value of the long-valued preference with the
    * given name.
    * <p>
    * A property change event is reported if the current value of the 
    * preference actually changes from its previous value. In the event
    * object, the property name is the name of the preference, and the
    * old and new values are wrapped as objects.
    * </p>
    * <p>
    * Note that the preferred way of re-initializing a preference to its
    * default value is to call <code>setToDefault</code>.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new current value of the preference
    */
   public void setValue(String name, long value);

   /**
    * Sets the current value of the string-valued preference with the
    * given name.
    * <p>
    * A property change event is reported if the current value of the 
    * preference actually changes from its previous value. In the event
    * object, the property name is the name of the preference, and the
    * old and new values are wrapped as objects.
    * </p>
    * <p>
    * Note that the preferred way of re-initializing a preference to its
    * default value is to call <code>setToDefault</code>.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new current value of the preference
    */
   public void setValue(String name, String value);

   /**
    * Sets the current value of the boolean-valued preference with the
    * given name.
    * <p>
    * A property change event is reported if the current value of the 
    * preference actually changes from its previous value. In the event
    * object, the property name is the name of the preference, and the
    * old and new values are wrapped as objects.
    * </p>
    * <p>
    * Note that the preferred way of re-initializing a preference to its
    * default value is to call <code>setToDefault</code>.
    * </p>
    *
    * @param name the name of the preference
    * @param value the new current value of the preference
    */
   public void setValue(String name, boolean value);
   
   /**
    * Saves the non-default-valued preferences known to this preference
    * store to the file from which they were originally loaded.
    *
    * @exception java.io.IOException if there is a problem saving this store
    */
   public void save() throws IOException;
   
//   //--------------------------------------------------------------------------
//   //additional
   public Color getColor(String key);
   
   public Color getDefaultColor(String key);
   
   public void setDefault(String key, Color value);
   
   public void setValue(String key, Color value);
//   public void setValue(String name, Color value);
//   public Properties getProperties();
//   //PreferenceProxy methods
//   public String getExportFolder(FProject project);
//   public String getExportFolderBasePath(FProject project);
//   public String getGlobalExportFolder(FProject project);
//   public String getJDKFolder();
//   public Map<String, Boolean> getSelectedCodeGenTargetNames(FProject project);
//   public ImageIcon getVisibilityIcon (String visibility);
//   public boolean isDebugMode();
//   public void setDebugMode (boolean value);
//   //URLPrefernceStore methods
//   public URL getUrl();
//   public void setUrl (URL url);
//   //WindowPrefernces methods USELESS IN ECLIPSE
//   public void deserializeBounds (Window window);
//   public void serializeLocation (Window window);
//   public boolean deserializeDivider (String key, JSplitPane splitPane);
//   public boolean deserializeLocation (Window window);
//   public boolean deserializeSize (Window window);
//   public void serializeBounds (Window window);
//   public void serializeDivider (String key, JSplitPane splitPane);
//   public void serializeSize (Window window);
//   //WorkspacePreferencesStore methods this one actually should be static
//   public boolean firstUse();
}
