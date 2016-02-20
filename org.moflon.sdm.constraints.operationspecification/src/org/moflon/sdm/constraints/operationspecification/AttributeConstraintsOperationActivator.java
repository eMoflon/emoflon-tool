package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class AttributeConstraintsOperationActivator extends Plugin
{
   private static AttributeConstraintsOperationActivator plugin;

   private static String bundleId;

   // Singleton instance
   public static AttributeConstraintsOperationActivator getDefault()
   {
      return plugin;
   }

   public static final String getBundleId()
   {
      if (bundleId == null)
      {
         throw new NullPointerException();
      } else
      {
         return bundleId;
      }
   }

   /**
    * Executed during plugin startup.
    * 
    * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
    */
   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      plugin = this;
      bundleId = context.getBundle().getSymbolicName();
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      plugin = null;
      super.stop(context);
   }
}
