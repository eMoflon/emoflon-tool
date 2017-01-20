package org.moflon.ide.core.preferences;

/**
 * UI-independent preferences storage.
 */
public class EMoflonPreferencesStorage
{
   private static final EMoflonPreferencesStorage instance = new EMoflonPreferencesStorage();

   private int validationTimeout;

   public void setValidationTimeout(final int validationTimeout)
   {
      this.validationTimeout = validationTimeout;
   }

   public int getValidationTimeout()
   {
      return this.validationTimeout;
   }

   public static EMoflonPreferencesStorage getInstance()
   {
      return instance;
   }
}
