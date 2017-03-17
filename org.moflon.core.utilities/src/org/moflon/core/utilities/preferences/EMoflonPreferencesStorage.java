package org.moflon.core.utilities.preferences;

/**
 * UI-independent preferences storage.
 */
public class EMoflonPreferencesStorage
{

   private static final EMoflonPreferencesStorage instance = new EMoflonPreferencesStorage();

   public static final int REACHABILITY_MAX_ADORNMENT_SIZE_INFINITY = 0;
   
   public static final int DEFAULT_REACHABILITY_MAX_ADORNMENT_SIZE = REACHABILITY_MAX_ADORNMENT_SIZE_INFINITY;
   
   public static final int DEFAULT_VALIDATION_TIMEOUT_MILLIS = 30000;
   
   /**
    * Stores the configured validation timeout in milliseconds. 'null' if not set.
    */
   private int validationTimeout;

   /**
    * Stores the configured maximum adornment size. 'null' if not set 
    */
   private int maximumAdornmentSize;

   /**
    * Stores whether reachability analysis is active. 'null' if not set 
    */
   private boolean reachabilityEnabled;


   public static EMoflonPreferencesStorage getInstance()
   {
      return instance;
   }

   public void setValidationTimeout(final int validationTimeout)
   {
      this.validationTimeout = validationTimeout;
   }

   public int getValidationTimeout()
   {
      return this.validationTimeout;
   }
   
   public void setReachabilityMaximumAdornmentSize(final int maximumAdornmentSize)
   {
      this.maximumAdornmentSize = maximumAdornmentSize;
   }

   public int getMaximumAdornmentSize()
   {
      return this.maximumAdornmentSize;
   }
   
   public void setReachabilityEnabled(final boolean reachabilityEnabled)
   {
      this.reachabilityEnabled = reachabilityEnabled;
   }
   
   public boolean getReachabilityEnabled()
   {
      return reachabilityEnabled;
   }
}
