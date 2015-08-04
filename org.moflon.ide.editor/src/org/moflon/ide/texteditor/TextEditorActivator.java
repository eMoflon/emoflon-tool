package org.moflon.ide.texteditor;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TextEditorActivator extends AbstractUIPlugin
{

   // The plug-in ID
   @Deprecated // Use #getModuleID()
   public static final String PLUGIN_ID = "org.moflon.ide.editor"; //$NON-NLS-1$

   // The shared instance
   private static TextEditorActivator plugin;

   private static String bundleId;

   public static String getModuleID()
   {
      if (bundleId == null)
         throw new NullPointerException();
      else
         return bundleId;
   }

   /**
    * The constructor
    */
   public TextEditorActivator()
   {
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
    */
   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      plugin = this;
      bundleId = context.getBundle().getSymbolicName();
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
    */
   @Override
   public void stop(final BundleContext context) throws Exception
   {
      plugin = null;
      bundleId = null;
      super.stop(context);
   }

   /**
    * Returns the shared instance
    *
    * @return the shared instance
    */
   public static TextEditorActivator getDefault()
   {
      return plugin;
   }

}
