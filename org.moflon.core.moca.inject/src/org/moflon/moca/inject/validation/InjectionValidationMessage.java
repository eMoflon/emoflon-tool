package org.moflon.moca.inject.validation;

import org.eclipse.core.runtime.IStatus;

public class InjectionValidationMessage
{
   private final String message;

   private final int severity;

   private String filename;

   /**
    * Creates a validation message
    * @param message the textual message
    * @param filename the name of the file that caused the problem
    * @param severity the severity, using the constants in {@link IStatus} (e.g., {@link IStatus#ERROR}
    */
   public InjectionValidationMessage(final String message, final String filename, final int severity)
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
    * 
    * Uses the same severity levels as {@link IStatus}
    */
   public int getSeverity()
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
