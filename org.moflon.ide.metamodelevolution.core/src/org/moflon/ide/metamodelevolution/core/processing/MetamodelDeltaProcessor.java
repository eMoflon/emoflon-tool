package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IProject;
import org.moflon.ide.metamodelevolution.core.MetamodelDelta;

public interface MetamodelDeltaProcessor
{

   // TODO@settl Document all public API (rkluge)
   void processDelta(IProject project, MetamodelDelta delta);
}
