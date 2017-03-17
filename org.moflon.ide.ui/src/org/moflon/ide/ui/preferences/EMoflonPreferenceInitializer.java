package org.moflon.ide.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.moflon.core.utilities.preferences.EMoflonPreferencesStorage;
import org.moflon.ide.ui.UIActivator;

public class EMoflonPreferenceInitializer extends AbstractPreferenceInitializer
{


   private static final String KEY_VALIDATION_TIMEOUT = "org.moflon.ide.ui.preferences.ValidationTimeoutMillis";
   
   private static final String KEY_REACHABILITY_ENABLED = "org.moflon.ide.ui.preferences.ReachabilityEnabled";
   
   private static final String KEY_REACHABILITY_MAX_ADORNMENT_SIZE = "org.moflon.ide.ui.preferences.ReachabilityMaxAdornmentSize";

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
      final IPreferenceStore store = EMoflonPreferenceInitializer.getPreferencesStore();
      store.setValue(KEY_VALIDATION_TIMEOUT, EMoflonPreferencesStorage.DEFAULT_REACHABILITY_MAX_ADORNMENT_SIZE);
      store.setValue(KEY_REACHABILITY_ENABLED, true);
      store.setValue(KEY_VALIDATION_TIMEOUT, EMoflonPreferencesStorage.DEFAULT_VALIDATION_TIMEOUT_MILLIS);
   }

   public static IPreferenceStore getPreferencesStore()
   {
      return UIActivator.getDefault().getPreferenceStore();
   }

   public static int getValidationTimeoutMillis()
   {
      return getPreferencesStore().getInt(KEY_VALIDATION_TIMEOUT);
   }

   public static void setValidationTimeoutMillis(final int validationTimeout)
   {
      getPreferencesStore().setValue(KEY_VALIDATION_TIMEOUT, validationTimeout);
   }

   public static void setReachabilityEnabled(final boolean isEnabled)
   {
      getPreferencesStore().setValue(KEY_REACHABILITY_ENABLED, isEnabled);
   }
   
   public static boolean getReachabilityEnabled()
   {
      return getPreferencesStore().getBoolean(KEY_REACHABILITY_ENABLED);
   }
   
   public static void setReachabilityMaxAdornmentSize(final int maxAdornmentSize)
   {
      getPreferencesStore().setValue(KEY_REACHABILITY_MAX_ADORNMENT_SIZE, maxAdornmentSize);
   }
   
   public static int getReachabilityMaxAdornmentSize()
   {
      return getPreferencesStore().getInt(KEY_REACHABILITY_MAX_ADORNMENT_SIZE);
   }
}
