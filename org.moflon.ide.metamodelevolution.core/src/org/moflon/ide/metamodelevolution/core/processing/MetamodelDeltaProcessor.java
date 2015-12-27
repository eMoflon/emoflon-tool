package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IProject;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;

public interface MetamodelDeltaProcessor
{
   // TODO@settl Document all public API (rkluge)
	   public final static String E_CLASS ="EClass";
	   
	   public final static String E_PACKAGE ="EPackage";
	   
	   public final static String TL_PACKAGE ="TLPackage";
	   
	   // TODO@settl Document all public API (rkluge)
	   void processDelta(IProject project, ChangeSequence delta);
	   
}
