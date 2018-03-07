package org.moflon.ide.core.properties;

import org.moflon.core.plugins.PluginProperties;
import org.moflon.core.utilities.UtilityClassNotInstantiableException;

/**
 * Helper methods for {@link PluginProperties} that are related to code
 * generation projects only
 *
 * @author Roland Kluge - Initial implementation
 */
public final class PluginPropertiesHelper {
	public static final String REPOSITORY_PROJECT = "repository";

	public static final String INTEGRATION_PROJECT = "integration";

	public static final String EXPORT_FLAG_KEY = "exportProject";

	public static final String HAS_ATTRIBUTE_CONSTRAINTS_KEY = "hasAttributeConstraints";

	public static final String VALIDATED_FLAG_KEY = "isValidated";

	private PluginPropertiesHelper() {
		throw new UtilityClassNotInstantiableException();
	}

	public static boolean isRepositoryProject(PluginProperties properties) {
		return PluginPropertiesHelper.REPOSITORY_PROJECT.equals(properties.getType());
	}

	public static boolean isIntegrationProject(PluginProperties properties) {
		return PluginPropertiesHelper.INTEGRATION_PROJECT.equals(properties.getType());
	}

	public static boolean isExported(PluginProperties properties) {
		return !"false".equals(properties.get(EXPORT_FLAG_KEY));
	}

	public static void setHasAttributeConstraints(final boolean hasAttributeConstraints, PluginProperties properties) {
		properties.put(HAS_ATTRIBUTE_CONSTRAINTS_KEY, Boolean.toString(hasAttributeConstraints));
	}

	public static boolean hasAttributeConstraints(PluginProperties properties) {
		return "true".equals(properties.get(HAS_ATTRIBUTE_CONSTRAINTS_KEY));
	}
}
