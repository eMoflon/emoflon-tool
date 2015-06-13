package org.moflon.moca.inject.validation;


public class MissingEClassValidationMessage extends InjectionValidationMessage
{
   public MissingEClassValidationMessage(final String eclassName, final String filename)
   {
      super("EClass " + eclassName + " cannot be found in the GenModel.", filename, InjectionValidationSeverity.WARNING);
   }

}
