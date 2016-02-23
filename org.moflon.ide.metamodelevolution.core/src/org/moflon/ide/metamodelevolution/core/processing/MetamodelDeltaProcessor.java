package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;

public interface MetamodelDeltaProcessor
{
   /**
    * This method processes the changes found in the metamodel delta.
    * 
    * @param project
    * @param delta
    */
   public IStatus processDelta(IProject project, ChangeSequence delta);

}
