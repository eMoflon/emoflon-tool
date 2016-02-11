package org.moflon.ide.metamodelevolution.core.util;

import org.eclipse.emf.ecore.EPackage;

public interface MetamodelComparator
{

   MetamodelDelta compare(EPackage oldMetaModel, EPackage newMetaModel);
}
