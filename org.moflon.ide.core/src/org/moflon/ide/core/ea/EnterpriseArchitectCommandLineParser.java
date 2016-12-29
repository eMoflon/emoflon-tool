package org.moflon.ide.core.ea;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.ide.core.ea.exceptions.EnterpriseArchitectCommandLineException;

public class EnterpriseArchitectCommandLineParser
{
   private static final Logger logger = Logger.getLogger(EnterpriseArchitectCommandLineParser.class);

   private IProgressMonitor originalMonitor;
   private SubMonitor internalMonitor;

   private final String ERROR = "ERROR:";

   private final String EXCEPTION = "EXCEPTION:";

   private final String DEBUG = "DEBUG:";

   private final String INFO = "INFO:";

   private final String SCALE = "SCALE:";

   private final String[] signalWords = { ERROR, EXCEPTION, DEBUG, INFO, SCALE };

   public void parse(String clMessages)
   {
      if (clMessages != null && !clMessages.isEmpty())
      {
         String[] lines = clMessages.split("\r\n");
         for (String line : lines)
         {
            parseLine(line);
         }
      }
   }

   public void setMonitor(IProgressMonitor mon)
   {
      originalMonitor = mon;
   }

   public void parseLine(String line)
   {
      String lastSignal = "";
      if (line != null && !line.isEmpty())
      {
         if (line.indexOf(":") != -1)
         {
            String signalWord = line.substring(0, line.indexOf(":") + 1);
            if (Arrays.asList(signalWords).contains(signalWord))
            {
               printAtLogger(signalWord, line.substring(line.indexOf(":") + 1));
               lastSignal = signalWord;
            } else
            {
               printAtLogger(lastSignal, line);
            }
         } else
         {
            printAtLogger(lastSignal, line);
         }
      }
   }

   private void printAtLogger(String signalWord, String restSequence)
   {
      switch (signalWord)
      {
      case ERROR:
         logger.error(restSequence);
         break;
      case EXCEPTION:
         logger.error("An Exception has been thrown", new EnterpriseArchitectCommandLineException(restSequence));
         break;
      case DEBUG:
         logger.debug(restSequence);
         break;
      case INFO:
         logger.info(restSequence);
         break;
      case SCALE:
         parseProgress(restSequence);
         break;
      default:
         break;
      }

   }

   private void parseProgress(final String line)
   {
      try
      {
         if (originalMonitor != null)
         {
            final String[] progressPartArray = line.split("[%/]");
            final String taskName = progressPartArray[0];
            final int current = Integer.parseInt(progressPartArray[progressPartArray.length - 2]);
            final int max = Integer.parseInt(progressPartArray[progressPartArray.length - 1]);

            if (current > 0)
            {
               internalMonitor.subTask(taskName);
            } else
            {
               internalMonitor = SubMonitor.convert(originalMonitor, taskName, max);
            }

            if (max > 0 && current < max)
            {
               internalMonitor.worked(1);
            }
         }
      } catch (final NumberFormatException | ArrayIndexOutOfBoundsException ex)
      {
         logger.error("Problem while parsing output line of EA: '" + line + "'.", ex);
      }
   }
}
