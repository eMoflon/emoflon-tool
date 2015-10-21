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
import java.lang.ref.WeakReference;

import de.fujaba.preferences.ColorConverter;
import de.uni_paderborn.fujaba.metamodel.common.FProject;


/**
 * Facade for preferences stored in a Fujaba project file. This store does not ask any other
 * preference store to return a preference value.
 *
 * @author    Oleg Travkin
 * @author    last editor $Author$
 * @version   $Revision$ $Date$
 */
public class DefaultProjectPreferenceStore implements ProjectPreferenceStore
{

   /**
    * By doing it this way we don't need to remove the project from
    * AbstractPreferenceStore containment, because we can hold the
    * ProjectPreferenceStore objects in a WeakHashMap. The GarbageCollector
    * takes care of it.
    */
   private WeakReference<FProject> project;


   public DefaultProjectPreferenceStore (FProject project)
   {
      this.project = new WeakReference<FProject>(project);
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#contains(java.lang.String)
    */
   public boolean contains (String key)
   {
      return project.get() != null ? project.get().hasInProperties (key) : false;
   }

   public String getValue(String key)
   {
      return project.get() != null ? project.get().getFromProperties (key): null;
   }

   
   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setToDefault(java.lang.String)
    */
   public final void setToDefault(String key)
   {
      // Remove key from Properties
      putValue(key, null);
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setValue(java.lang.String, double)
    */
   public final void setValue(String key, double value)
   {
      putValue(key, Double.toString(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setValue(java.lang.String, float)
    */
   public final void setValue(String key, float value)
   {
      putValue(key, Float.toString(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setValue(java.lang.String, int)
    */
   public final void setValue(String key, int value)
   {
      putValue(key, Integer.toString(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setValue(java.lang.String, long)
    */
   public final void setValue(String key, long value)
   {
      putValue(key, Long.toString(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setValue(java.lang.String, java.lang.String)
    */
   public final void setValue(String key, String value)
   {
      putValue(key, value);
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setValue(java.lang.String, boolean)
    */
   public final void setValue(String key, boolean value)
   {
      putValue(key, Boolean.toString(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#putValue(java.lang.String, java.lang.String)
    */
   public void putValue (String key, String value)
   {
      if(project.get() == null)
      {
         return;
      }
      
      String oldValue = getString(key);
      if (((value == null) && (oldValue != null))
            || ((value != null) && (!value.equals(oldValue))))
      {
         if (value == null)
         {
            // Property changes are handled by ASGProject
            project.get().addToProperties(key, null);
         }
         else
         {
            // Property changes are handled by ASGProject
            project.get().addToProperties (key, value);
         }
      }
   }

   /**
    * @see de.uni_paderborn.fujaba.preferences.ProjectPreferenceStore#getProject()
    */
   public FProject getProject()
   {
      return project.get();
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#addPropertyChangeListener(java.beans.PropertyChangeListener)
    */
   public void addPropertyChangeListener(PropertyChangeListener listener)
   {
      // Property changes are handled by ASGProject
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#firePropertyChangeEvent(java.lang.String, java.lang.Object, java.lang.Object)
    */
   public void firePropertyChangeEvent(String name, Object oldValue, Object newValue)
   {
      // Property changes are handled by ASGProject
   }


   /**
    * Returns a project specific value if existing. 
    * Returns the stored value in Fujaba Core otherwise.
    * 
    *  @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getBoolean(java.lang.String)
    */
   public boolean getBoolean(String name)
   {
      return Boolean.valueOf(getString(name));
   }

   // FIXME: Move this method to another class (stand-alone module)
   /**
    * Returns a project specific value if existing. 
    * Returns the stored value in Fujaba Core otherwise.
    * 
    *  @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getColor(java.lang.String)
    */
   public Color getColor(String key)
   {
      return ColorConverter.parseString(getString(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getDefaultBoolean(java.lang.String)
    */
   public boolean getDefaultBoolean(String name)
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getDefaultBoolean(name);
   }

   // FIXME: Move this method to another class (stand-alone module)
   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getDefaultColor(java.lang.String)
    */
   public Color getDefaultColor(String key)
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getDefaultColor(key);
   }

   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getDefaultDouble(java.lang.String)
    */
   public double getDefaultDouble(String name)
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getDefaultDouble(name);
   }

   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getDefaultFloat(java.lang.String)
    */
   public float getDefaultFloat(String name)
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getDefaultFloat(name);
   }

   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getDefaultInt(java.lang.String)
    */
   public int getDefaultInt(String name)
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getDefaultInt(name);
   }

   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getDefaultLong(java.lang.String)
    */
   public long getDefaultLong(String name)
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getDefaultLong(name);
   }

   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getDefaultString(java.lang.String)
    */
   public String getDefaultString(String name)
   {
      return FujabaPreferencesManager.getFujabaCorePreferenceStore().getDefaultString(name);
   }

   /**
    * Returns a project specific value if existing. 
    * Returns the stored value in Fujaba Core otherwise.
    * 
    *  @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getDouble(java.lang.String)
    */
   public double getDouble(String name)
   {
      return Double.valueOf(getString(name));
   }


   /**
    * Returns a project specific value if existing. 
    * Returns the stored value in Fujaba Core otherwise.
    * 
    *  @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getFloat(java.lang.String)
    */
   public float getFloat(String name)
   {
      return Float.valueOf(getString(name));
   }


   /**
    * Returns a project specific value if existing. 
    * Returns the stored value in Fujaba Core otherwise.
    * 
    *  @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getInt(java.lang.String)
    */
   public int getInt(String name)
   {
      return Integer.valueOf(getString(name));
   }


   /**
    * Returns a project specific value if existing. 
    * Returns the stored value in Fujaba Core otherwise. 
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getLong(java.lang.String)
    */
   public long getLong(String name)
   {
      return Long.valueOf(getString(name));
   }


   /**
    * Returns a project specific value if existing. 
    * Returns the stored value in Fujaba Core otherwise. 
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#getString(java.lang.String)
    */
   public String getString(String name)
   {
      String stringResult = project.get().getFromProperties (name);
      if(stringResult != null)
      {
         return stringResult;
      }
      else
      {
         return FujabaPreferencesManager.getFujabaCorePreferenceStore().getString(name);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#isDefault(java.lang.String)
    */
   public boolean isDefault(String name)
   {
      String defaultString = FujabaPreferencesManager.getFujabaCorePreferenceStore().getDefaultString(name);
      String projectString = project.get().getFromProperties (name);
      
      if(defaultString == null && projectString == null)
      {
         //both null
         return true;
      }
      else if(defaultString != null)
      {
         return defaultString.equals(projectString);
      }
      else
      {
         return projectString.equals(defaultString);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#needsSaving()
    */
   public boolean needsSaving()
   {
      return false;
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#removePropertyChangeListener(java.beans.PropertyChangeListener)
    */
   public void removePropertyChangeListener(PropertyChangeListener listener)
   {
      // Property changes are handled by ASGProject
   }


   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#save()
    */
   public void save() throws IOException
   {
      //project's preferences get stored with the project
      //we do not take care of it here
   }


   /**
    * We do not set defaults for projects, as the fujaba
    * core preference store contains the defaults.
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setDefault(java.lang.String, double)
    */
   public void setDefault(String name, double value)
   {   
   }

   
   /**
    * We do not set defaults for projects, as the fujaba
    * core preference store contains the defaults.
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setDefault(java.lang.String, float)
    */
   public void setDefault(String name, float value)
   {   
   }


   /**
    * We do not set defaults for projects, as the fujaba
    * core preference store contains the defaults.
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setDefault(java.lang.String, int)
    */
   public void setDefault(String name, int value)
   {
   }


   /**
    * We do not set defaults for projects, as the fujaba
    * core preference store contains the defaults.
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setDefault(java.lang.String, long)
    */
   public void setDefault(String name, long value)
   {
   }

   
   /**
    * We do not set defaults for projects, as the fujaba
    * core preference store contains the defaults.
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setDefault(java.lang.String, java.lang.String)
    */
   public void setDefault(String name, String defaultObject)
   {
   }


   /**
    * We do not set defaults for projects, as the fujaba
    * core preference store contains the defaults.
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setDefault(java.lang.String, boolean)
    */
   public void setDefault(String name, boolean value)
   {      
   }


   /**
    * We do not set defaults for projects, as the fujaba
    * core preference store contains the defaults.
    * 
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setDefault(java.lang.String, java.awt.Color)
    */
   public void setDefault(String key, Color value)
   {
   }

   // FIXME: Move this method to another class (stand-alone module)
   /**
    * @see de.uni_paderborn.fujaba.preferences.FujabaPreferenceStore#setValue(java.lang.String, java.awt.Color)
    */
   public void setValue(String key, Color value)
   {
      putValue(key, ColorConverter.toString(value));
   }
}

/*
 * $Log$
 * Revision 1.8  2007/04/03 13:53:36  weisemoeller
 * Merged some changes from MOFLON_1_1_Maintenance-Branch into head
 *
 * Merged the changes that were checked into the maintenance branch with the following commit messages:
 *
 * - ask WorkspacePreferenceStore when evaluating variables (actually the outermost PreferenceStore which is asked for a value should be asked here, but I don't see a way to determine this at the moment, so this is a temporary solution only)
 *
 * - use the preferencestore that was initially asked for a property when resolving variables
 *
 * - appliance of variables to default strings with respect to unsaved changes in dialogs
 *
 * - Use ColumnRowLayout for LoggingPreferencesDialog
 *
 * - store project specific settings only if they differ from the default value
 *
 * Revision 1.7  2007/03/20 09:00:40  cschneid
 * - preferences report missing keys
 * - "user.dir" and "file.seperator" constants deprecated - they are no preferences
 *
 * Revision 1.6  2007/03/15 16:36:25  cschneid
 * - Generating code is possible again
 * - projects failing to load entirely are removed from workspace
 *
 * Revision 1.5  2007/03/13 13:40:24  weisemoeller
 * Restored deleted classes from last commit. Those classes have been marked as deprecated now and will probably be deleted in a few weeks (we can discuss the point of time for this on the developer days, I think.)
 *
 * The new classes have been moved back to de.fujaba for now.
 *
 * Moreover, the usage of some workspace settings has been replaced with the corresponding project specific settings.
 *
 * Revision 1.1  2007/03/13 10:46:11  weisemoeller
 * Replaced old dialogs with xml based ones.
 *
 * I have replaced the preferences dialogs in Fujaba (and MOFLON) with this commit. If you want to change or create dialogs, take a look at the file dialogs.xsd in the DTDs directory and the existing dialog.xml in the properties directory. Since the properties backend has been changed as well, I needed to modify a large number of classes to use the new backend. If you notice unusual behaviour and suppose it to be related to this, please let me (ingo,weisemoeller@es.tu-darmstadt.de) know.
 *
 * Revision 1.3  2007/01/24 17:54:05  rotschke
 * Enabled references to other properties using ${key} notation. It should even be possible to nest references like myProperty=prefix${innerPrefix${innerKey}}${otherKey}suffix [tr]. No the, algorithm cannot make coffee yet :-)
 *
 * Revision 1.2  2007/01/24 14:02:44  rotschke
 * Refactored preference stores for better control of the desired behaviour [tr].
 *
 * Revision 1.1  2007/01/24 12:13:53  rotschke
 * Added more preference stores [tr].
 *
 */
