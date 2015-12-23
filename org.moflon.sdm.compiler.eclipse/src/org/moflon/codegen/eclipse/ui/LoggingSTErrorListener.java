package org.moflon.codegen.eclipse.ui;

import org.apache.log4j.Logger;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.STMessage;

/**
 * An error listener to be used during the construction of {@link STGroup}s.
 * 
 * The error listener forwards error messages to a {@link Logger} instance that is configured by the constructor.
 */
public class LoggingSTErrorListener implements STErrorListener
{
   private Logger logger;

   public LoggingSTErrorListener(final Logger logger)
   {
      this.logger = logger;
   }

   @Override
   public void runTimeError(final STMessage msg)
   {
      logger.error("[ST runtime error] " + msg);
   }

   @Override
   public void internalError(final STMessage msg)
   {
      logger.error("[ST internal error] " + msg);
   }

   @Override
   public void compileTimeError(final STMessage msg)
   {
      logger.error("[ST compile time error] " + msg);
   }

   @Override
   public void IOError(final STMessage msg)
   {
      logger.error("[ST IO error] " + msg);
   }

}
