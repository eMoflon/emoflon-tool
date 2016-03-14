package org.moflon.sdm.constraints.operationspecification;

import org.moflon.core.utilities.EMoflonPlugin;

public class AttributeConstraintsOperationActivator extends EMoflonPlugin
{
   // This is the delimiter used by developers of custom constraints
   public static final char INNER_ST_DELIMITER = '$';

   // This is used to generate string templates from the user-specified operations
   public static final char OUTER_ST_DELIMITER = '#'; // '@' does not work.

   public static AttributeConstraintsOperationActivator getDefault() {
      AttributeConstraintsOperationActivator plugin = getPlugin(AttributeConstraintsOperationActivator.class);
      if (plugin == null)
         throw new IllegalStateException("Plugin has not yet been set!");
      return plugin;
   }

   public static final String getBundleId()
   {
      return getDefault().getPluginId();
   }

}
