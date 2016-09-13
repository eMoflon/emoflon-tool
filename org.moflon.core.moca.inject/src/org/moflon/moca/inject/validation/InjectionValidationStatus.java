package org.moflon.moca.inject.validation;

import org.eclipse.core.runtime.IStatus;
import org.moflon.moca.inject.CodeInjectionPlugin;

/**
 * An {@link IStatus} that contains information about the validation of injections.
 *
 */
public class InjectionValidationStatus implements IStatus
{
   private final InjectionValidationMessage message;

   public InjectionValidationStatus(final InjectionValidationMessage injectionValidationMessage)
   {
      this.message = injectionValidationMessage;
   }

   @Override
   public IStatus[] getChildren()
   {
      return new IStatus[0];
   }

   @Override
   public int getCode()
   {
      return 0;
   }

   @Override
   public Throwable getException()
   {
      return null;
   }

   @Override
   public String getMessage()
   {
      return this.message.getMessage();
   }

   @Override
   public String getPlugin()
   {
      return CodeInjectionPlugin.getModuleID();
   }

   @Override
   public int getSeverity()
   {
      return maptoStatusSeverity(this.message.getSeverity());
   }

   @Override
   public boolean isMultiStatus()
   {
      return false;
   }

   @Override
   public boolean isOK()
   {
      return false;
   }

   @Override
   public boolean matches(final int severityMask)
   {
      return (getSeverity() & severityMask) != 0;
   }

   @Override
   public String toString()
   {
      return "Injection Status [m=" + this.message + "]";
   }

   public InjectionValidationMessage getInjectionMessage()
   {
      return this.message;
   }

   public static int maptoStatusSeverity(final InjectionValidationSeverity severity)
   {
      switch (severity)
      {
      case WARNING:
         return IStatus.WARNING;
      case ERROR:
         return IStatus.ERROR;
      default:
         return IStatus.OK;
      }
   }

}
