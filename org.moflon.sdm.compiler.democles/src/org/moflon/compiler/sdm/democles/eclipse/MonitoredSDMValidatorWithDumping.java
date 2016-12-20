package org.moflon.compiler.sdm.democles.eclipse;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class MonitoredSDMValidatorWithDumping extends MonitoredSDMValidator
{

   public MonitoredSDMValidatorWithDumping(IFile ecoreFile)
   {
      super(ecoreFile);
   }
   
   @Override
   protected DemoclesValidationProcess createValidationProcess(ResourceSet resourceSet)
   {
      return new DemoclesValidationProcess(getEcoreFile(), resourceSet, true);
   }

}
