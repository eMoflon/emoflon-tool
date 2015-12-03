package org.moflon.ide.metamodelevolution.core;

import org.eclipse.emf.ecore.EPackage;

public interface MetamodelComparator
{

   MetamodelDelta compare(EPackage oldMetaModel, EPackage newMetaModel);
}
