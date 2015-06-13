package org.moflon.autotest;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.ui.internal.ide.application.IDEApplication;

/**
 * Required to change the name of the application to org.moflon.testapplication so that subclipse does not block the
 * automated test process
 */
@SuppressWarnings("restriction")
public class AutotestApplication extends IDEApplication implements IApplication
{
   @Override
   public Object start(final IApplicationContext appContext) throws Exception
   {
      return super.start(appContext);
   }
}
