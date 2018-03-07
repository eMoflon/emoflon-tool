package org.moflon.ide.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.moflon.core.preferences.EMoflonPreferencesActivator;
import org.moflon.core.preferences.EMoflonPreferencesStorage;
import org.moflon.core.utilities.WorkspaceHelper;

public class EMoflonPreferenceInitializer extends AbstractPreferenceInitializer {
	private static final String KEY_VALIDATION_TIMEOUT = "org.moflon.ide.ui.preferences.ValidationTimeoutMillis";

	private static final String KEY_REACHABILITY_ENABLED = "org.moflon.ide.ui.preferences.ReachabilityEnabled";

	private static final String KEY_REACHABILITY_MAX_ADORNMENT_SIZE = "org.moflon.ide.ui.preferences.ReachabilityMaxAdornmentSize";

	/**
	 * Empty default constructor
	 */
	public EMoflonPreferenceInitializer() {
		// Needed for OSGi
	}

	@Override
	public void initializeDefaultPreferences() {
		getValidationTimeoutMillis();
	}

	/**
	 * Stores the default values to the {@link EMoflonPreferenceInitializer}
	 * singleton (as specified in {@link EMoflonPreferencesStorage}).
	 */
	public static void resetToDefaults() {
		setReachabilityEnabled(EMoflonPreferencesStorage.DEFAULT_REACHABILITIY_IS_ENABLED);
		setReachabilityMaxAdornmentSize(EMoflonPreferencesStorage.DEFAULT_REACHABILITY_MAX_ADORNMENT_SIZE);
		setValidationTimeoutMillis(EMoflonPreferencesStorage.DEFAULT_VALIDATION_TIMEOUT_MILLIS);
	}

	public static int getValidationTimeoutMillis() {
		return getPreferences().getInt(KEY_VALIDATION_TIMEOUT,
				EMoflonPreferencesStorage.DEFAULT_VALIDATION_TIMEOUT_MILLIS);
	}

	public static void setValidationTimeoutMillis(final int validationTimeout) {
		getPreferences().putInt(KEY_VALIDATION_TIMEOUT, validationTimeout);
		EMoflonPreferencesActivator.getDefault().getPreferencesStorage().setValidationTimeout(validationTimeout);
	}

	public static void setReachabilityEnabled(final boolean isEnabled) {
		getPreferences().putBoolean(KEY_REACHABILITY_ENABLED, isEnabled);
		EMoflonPreferencesActivator.getDefault().getPreferencesStorage().setReachabilityEnabled(isEnabled);
	}

	public static boolean getReachabilityEnabled() {
		return getPreferences().getBoolean(KEY_REACHABILITY_ENABLED,
				EMoflonPreferencesStorage.DEFAULT_REACHABILITIY_IS_ENABLED);
	}

	public static void setReachabilityMaxAdornmentSize(final int maxAdornmentSize) {
		getPreferences().putInt(KEY_REACHABILITY_MAX_ADORNMENT_SIZE, maxAdornmentSize);
		EMoflonPreferencesActivator.getDefault().getPreferencesStorage()
				.setReachabilityMaximumAdornmentSize(maxAdornmentSize);
	}

	public static int getReachabilityMaxAdornmentSize() {
		return getPreferences().getInt(KEY_REACHABILITY_MAX_ADORNMENT_SIZE,
				EMoflonPreferencesStorage.DEFAULT_REACHABILITY_MAX_ADORNMENT_SIZE);
	}

	private static IEclipsePreferences getPreferences() {
		return InstanceScope.INSTANCE.getNode(WorkspaceHelper.getPluginId(EMoflonPreferenceInitializer.class));
	}
}
