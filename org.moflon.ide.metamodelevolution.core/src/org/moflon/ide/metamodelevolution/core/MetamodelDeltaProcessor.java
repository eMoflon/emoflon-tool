package org.moflon.ide.metamodelevolution.core;

import org.eclipse.core.resources.IProject;

public interface MetamodelDeltaProcessor {
		
   //TODO@settl Document all public API (rkluge)
	void processDelta(IProject project, MetamodelDelta delta);
}
