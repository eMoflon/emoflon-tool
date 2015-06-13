package org.moflon.devtools.ui.handlers;

import org.apache.log4j.Logger;

/**
 * Super class for all command handlers.
 *
 */
public abstract class AbstractCommandHandler extends org.eclipse.core.commands.AbstractHandler
{

   protected Logger logger;

   public AbstractCommandHandler() {
      this.logger = Logger.getLogger(this.getClass());
   }
}
