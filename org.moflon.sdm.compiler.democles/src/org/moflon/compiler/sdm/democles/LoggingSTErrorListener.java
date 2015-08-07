package org.moflon.compiler.sdm.democles;

import org.apache.log4j.Logger;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

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
