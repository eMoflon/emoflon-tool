package org.moflon.ide.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.moflon.ide.ui.UIActivator;

public class EMoflonPreferenceInitializer extends AbstractPreferenceInitializer
{

   public static final String UPDATE_SITE_PROJECT_KEY = "org.moflon.ide.ui.preferences.UpdateSiteProject";

   public static final String LOCAL_DEPLOYMENT_PATH = "org.moflon.ide.ui.preferences.LocalDeploymentPath";

   public EMoflonPreferenceInitializer()
   {
      // Needed for OSGi
   }

   @Override
   public void initializeDefaultPreferences()
   {
      restoreDefaults();
   }

   public static void restoreDefaults()
   {
      IPreferenceStore store = EMoflonPreferenceInitializer.getPreferencesStore();
      store.setValue(UPDATE_SITE_PROJECT_KEY, "org.moflon.deployment.updatesite");
   }

   static IPreferenceStore getPreferencesStore()
   {
      return UIActivator.getDefault().getPreferenceStore();
   }

   public static String getUpdateSiteProject()
   {
      return getPreferencesStore().getString(UPDATE_SITE_PROJECT_KEY);
   }

   public static void setUpdateSiteProject(final String updateSiteProject)
   {
      getPreferencesStore().setValue(UPDATE_SITE_PROJECT_KEY, updateSiteProject);
   }

   public static String getLocalDeploymentPath(final String defaultLocalDeployPath)
   {
      String result = getPreferencesStore().getString(LOCAL_DEPLOYMENT_PATH);
      if (result.isEmpty()) // Default behavior
      {
         return defaultLocalDeployPath;
      }
      else {
         return result;
      }
   }

   public static void setLocalDeploymentPath(final String path)
   {
      getPreferencesStore().setValue(LOCAL_DEPLOYMENT_PATH, path);
   }
}
