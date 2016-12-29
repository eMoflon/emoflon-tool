package org.moflon.ide.core.runtime.builders.hooks;

import org.eclipse.core.runtime.IStatus;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

public interface PreMetamodelBuilderHook
{
   String PRE_BUILD_EXTENSION_ID = "org.moflon.extensionpoints.premetamodelbuilderhook";

   /**
    * Performs an action prior to the 'MOCA-to-Ecore' export in the {@link MetamodelBuilder}.
    * 
    * @param data the data provided to this hook
    * 
    * @return the execution status of this hook
    */
   IStatus run(final PreMetamodelBuilderHookDTO data);
}
