package org.moflon.ide.metamodelevolution.core.util;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.ide.metamodelevolution.core.MetamodelDelta;

public interface MetamodelComparator
{

   MetamodelDelta compare(EPackage oldMetaModel, EPackage newMetaModel);
}
