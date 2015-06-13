package org.moflon.moca.inject.validation;

import org.eclipse.core.runtime.IStatus;

public class InjectionValidationMessage
{
   private final String message;

   private final InjectionValidationSeverity severity;

   private String filename;

   public InjectionValidationMessage(final String message, final String filename, final InjectionValidationSeverity severity)
   {
      this.message = message;
      this.filename = filename;
      this.severity = severity;
   }

   /**
    * Returns a descriptive message of the problem
    * @return
    */
   public String getMessage()
   {
      return message;
   }

   /**
    * Returns the severity of the problem
    * @return
    */
   public InjectionValidationSeverity getSeverity()
   {
      return severity;
   }
   
   /**
    * Returns the project-relative file name of the file that contains the problem.
    * @return
    */
   public String getLocation() {
      return this.filename;
   }

   @Override
   public String toString()
   {
      return "Injection Validation Message [m= " + this.getMessage() + ", severity=" + this.getSeverity() + "]";
   }

   public IStatus convertToStatus()
   {
      return new InjectionValidationStatus(this);
   }
}
