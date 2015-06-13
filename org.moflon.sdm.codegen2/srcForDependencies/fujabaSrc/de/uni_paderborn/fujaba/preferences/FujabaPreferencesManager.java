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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import de.fujaba.preferences.VectorConverter;
import de.uni_paderborn.fujaba.app.FolderSettings;
import de.uni_paderborn.fujaba.metamodel.common.FProject;

/**
 * Facade that provides access to the major functionality of the
 * Fujaba preference mechanism.
 *
 * @author fklar
 * @author Oleg Travkin
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
public class FujabaPreferencesManager implements FujabaCorePreferenceKeys
{
   public final static String FUJABA_CORE_PREFERENCES = "de.fujaba.core.preferences";
   
   /**
    * The file name of the properties file in which Fujaba remembers, which directory should be the 'propertyDir'.
    * @deprecated use {@link FolderSettings#PROPERTIES_PATH}.
    */
   public static final String PROPERTIES_PATH = FolderSettings.PROPERTIES_PATH;
   
   /**
    * PropertyName of the 'propertyDir' attribute.
    * Will be used in PropertyChangeEvents.
    * @deprecated use {@link FolderSettings#PROPERTY_DIR_PROPERTY}.
    */
   @Deprecated
   public final static String PROPERTY_DIR_PROPERTY = FolderSettings.PROPERTY_DIR_PROPERTY;
      
   private static HashMap<String, FujabaPreferenceStore> preferenceStoreMap; 
   private static WeakHashMap<FProject, ProjectPreferenceStore> projectStoreMap;
   
   static{
      preferenceStoreMap = new HashMap<String, FujabaPreferenceStore>();
      projectStoreMap = new WeakHashMap<FProject, ProjectPreferenceStore>();
   }
   
   
   /**
    * Overwrites store values if key is already in use.
    * 
    * @param preferenceStoreKey 
    * @param store
    * @see IPreferenceStoreKeys
    */
   public static void setPreferenceStore(String preferenceStoreKey, FujabaPreferenceStore store)
   {
      if(preferenceStoreKey == null)
      {
         throw new IllegalArgumentException("Argument preferenceStoreKey may not be null.");
      }
      
      preferenceStoreMap.put(preferenceStoreKey, store);
   }
   
   public static FujabaPreferenceStore getPreferenceStore(String preferenceStoreKey)
   {
      if(preferenceStoreKey != null)
      {
         return preferenceStoreMap.get(preferenceStoreKey);
      }
      return null;
   }
   
   private static ProjectPreferenceStoreBuilder projectPreferenceStoreBuilder = null;
   
   /**
    * Return the project preference store builder. If no builder is registered a default
    * implementation of a project preference store builder is initialized. Thus, the return
    * value is never <code>null</code>.
    * 
    * @return a project preference store builder
    */
   protected static ProjectPreferenceStoreBuilder getProjectPreferenceStoreBuilder()
   {
      if (projectPreferenceStoreBuilder == null)
      {
         projectPreferenceStoreBuilder = new DefaultProjectPreferenceStoreBuilder();
      }
      
      return projectPreferenceStoreBuilder;
   }
   
   /**
    * Sets the given builder. This builder is then used to create new project preference stores.
    * Use this method to adapt the project preference store implementation.
    * 
    * @param builder the builder to be used to create new project preference stores
    */
   public static void setProjectPreferenceStoreBuilder(ProjectPreferenceStoreBuilder builder)
   {
      if (builder == null)
      {
         throw new IllegalArgumentException("The ProjectPreferenceStoreBuilder must not be null.");
      }
      
      projectPreferenceStoreBuilder = builder;
   }
   
   /**
    * Creates a wrapper for the project to access the projects preferences like
    * normal preferences.
    * @param project
    * @return The preference store of the given project. Null if project is null.
    */
   public static ProjectPreferenceStore getProjectPreferenceStore(FProject project)
   {
      if(project != null)
      {
         if(projectStoreMap.containsKey(project))
         {
            return projectStoreMap.get(project); 
         }
         else
         {
            ProjectPreferenceStore newProjectPrefStore = getProjectPreferenceStoreBuilder().createProjectPreferenceStore(project);
            projectStoreMap.put(project, newProjectPrefStore);
            return newProjectPrefStore;
         }
      }
      return null;
   }
   
   
   /**
    * Removes the reference to ProjectPreferenceStore for the project. Nevertheless,
    * ProjectPreferenceStore are stored by a WeakReference and thus may be forgotten
    * to be removed. 
    * 
    * @param project The project is the key for its ProjectPreferenceStore
    */
   public static void deleteProjectPreferenceStore(FProject project)
   {
      if(projectStoreMap.containsKey(project))
      {
         projectStoreMap.remove(project);
      }
   }
   
   
   /**
    * Convinience method equivalent to calling  
    * <code>FujabaPreferencesManager.get().getPreferenceStore(FUJABA_CORE_PREFERENCES);</code>
    * @return FujabaPreferenceStore for fujaba core
    */
   public static FujabaPreferenceStore getFujabaCorePreferenceStore()
   {
      return getPreferenceStore(FUJABA_CORE_PREFERENCES);
   }

   
   /**
    * Helper method returns either a preference store corresponding to a project, equivalent
    * to calling <code>FujabaPreferencesManager.get().getProjectPreferenceStore(project);</code> if
    * project is not null. Returns fujaba core's preference store otherwise, equivalent to 
    * calling <code>FujabaPreferencesManager.get().getPreferenceStore(FUJABA_UI_PREFERENCES);</code>
    * @return FujabaPreferenceStore 
    */
   public static FujabaPreferenceStore getProjectOrFujabaCorePreferenceStore(FProject project)
   {
      if(project != null)
      {
         return getProjectPreferenceStore(project);
      }
      return getFujabaCorePreferenceStore();
   }
   
   public static boolean preferenceStoreKeyInUse(String key)
   {
      return preferenceStoreMap.containsKey(key);
   }
   
   
   ///-------------------------------------------------------------------
   // some migrated convenience methods from here on
   
   /**
    * Get the selectedCodeGenTargetNames attribute of the CodeGenPreferences object
    * 
    * @return The selectedCodeGenTargetNames value
    * @param project project to obtain targets for
    */
   public static Map<String, Boolean> getSelectedCodeGenTargetNames(FProject project)
   {
      Map<String, Boolean> selectedCodeGenTargetNames = new HashMap<String, Boolean>();
      FujabaPreferenceStore prefStore = getProjectOrFujabaCorePreferenceStore(project);
      Vector<String> targets = VectorConverter.parseString(prefStore.getString(FujabaCorePreferenceKeys.CODEGEN_TARGETS));
      for (String target : targets)
      {
         selectedCodeGenTargetNames.put(target, Boolean.TRUE);
      }
      FujabaPreferenceStore workspacePreferences = getFujabaCorePreferenceStore();
      String key = "de.fujaba.codegen.target.Java";
      if (targets.isEmpty() && workspacePreferences.getString(key) != null) // import old preferences
      {
         workspacePreferences.setToDefault(key);
         selectedCodeGenTargetNames.put("java", Boolean.TRUE);
         workspacePreferences.setValue (FujabaCorePreferenceKeys.CODEGEN_TARGETS, 
               VectorConverter.toString(new Vector<String>(selectedCodeGenTargetNames.keySet())));
         try
         {
            workspacePreferences.save();
         } catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      return selectedCodeGenTargetNames;
   }
   
   public static void setDebugMode (boolean value)
   {
      getFujabaCorePreferenceStore().setValue(FujabaCorePreferenceKeys.DEBUG_MODE, value);
   }


   /**
    * Get the debugMode attribute of the OptionsDebug object
    *
    * @return   The debugMode value
    */
   public static boolean isDebugMode()
   {
      return getFujabaCorePreferenceStore().getBoolean(FujabaCorePreferenceKeys.DEBUG_MODE);
   }

   /**
    * @deprecated use {@link FolderSettings#getExportFolderBasePath(FProject)}
    */
   public static String getExportFolderBasePath(FProject project)
   {
      return FolderSettings.getExportFolderBasePath(project);
   }

   /**
    * @return the folder where generated files are saved.
    * @param project project to obtain the export folder for
    * @deprecated use {@link FolderSettings} instead.
    */
   public static String getExportFolder(FProject project)
   {
      return FolderSettings.getExportFolder(project);
   }

   /**
    * @return the absolute path for codegeneration without project specific suffix.
    * @param project project to obtain the export folder for
    *
    * @deprecated use {@link FolderSettings} instead.
    */
   @Deprecated
   public static String getGlobalExportFolder(FProject project)
   {
      return FolderSettings.getGlobalExportFolder(project);
   }

   /**
    * Get the directory in which Fujaba properties are stored.
    *
    * <pre>
    * Note: the propertyDir ends with a file separator.
    * Note: if the propertyDir has not been set, a call to
    *       this function will initialize the propertyDir with
    *       the proposedPropertyDir.
    * </pre>
    *
    * @return   The directory in which properties are stored.
    * @see      #getProposedPropertyDir()
    * @deprecated use {@link FolderSettings} instead.
    */
   public static String getPropertyDir()
   {
      return FolderSettings.getPropertyDir();
   }


   /**
    * Set the directory in which Fujaba properties are stored.
    *
    * <pre>
    *
    * Note: do not change propertyDir during runtime, for now!
    * Properties won't be adjusted, if the propertyDir changes.
    * Use startup-parameter in class 'FujabaApp' to change the
    * propertyDir during startup.
    * </pre>
    *
    * @param value
    * @see          de.uni_paderborn.fujaba.app.FujabaApp#main(String[])
    * @deprecated use {@link FolderSettings} instead.
    */
   public static void setPropertyDir (String value)
   {
      FolderSettings.setPropertyDir(value);
   }


   /**
    * Get the default directory in which Fujaba properties should be stored. The directory-name is
    * constructed dependent on the version number of the fujaba application and is basically located
    * in the users home directory. Note: the propertyDir ends with a file separator.
    *
    * @return   The directory in which properties should be stored.
    * @deprecated use {@link FolderSettings} instead.
    */
   public static String getProposedPropertyDir()
   {
      return FolderSettings.getProposedPropertyDir();
   }

   /**
    * @deprecated use {@link FolderSettings} instead.
    */
   public static String getDefaultPropertyDir ()
   {
      return FolderSettings.getDefaultPropertyDir();
   }

   /**
    * @deprecated use {@link FolderSettings} instead.
    */
   public static String getSavedPropertyDir ()
   {
      return FolderSettings.getSavedPropertyDir();
   }
   
   /**
    * @deprecated use {@link FolderSettings} instead.
    */
   public static void savePropertyDir () throws IOException
   {
      FolderSettings.savePropertyDir();
   }
}
