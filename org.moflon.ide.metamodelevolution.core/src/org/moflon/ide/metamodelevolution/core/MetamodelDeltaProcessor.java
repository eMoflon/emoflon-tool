package org.moflon.ide.metamodelevolution.core;

import org.eclipse.core.resources.IProject;

public interface MetamodelDeltaProcessor {

	void processDelta(IProject propject, MetamodelDelta delta);
}
