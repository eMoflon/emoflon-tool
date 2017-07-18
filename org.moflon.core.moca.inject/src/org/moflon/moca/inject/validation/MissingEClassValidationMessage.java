package org.moflon.moca.inject.validation;

import org.eclipse.core.runtime.IStatus;

public class MissingEClassValidationMessage extends InjectionValidationMessage
{
   public MissingEClassValidationMessage(final String eclassName, final String filename)
   {
      super("EClass " + eclassName + " cannot be found in the GenModel.", filename, IStatus.WARNING);
   }

}
