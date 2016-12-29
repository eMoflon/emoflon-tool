package org.moflon.ide.core.runtime.builders.hooks;

import org.eclipse.core.runtime.IStatus;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

public interface PostMetamodelBuilderHook
{
   String POST_BUILD_EXTENSION_ID = "org.moflon.extensionpoints.postmetamodelbuilderhook";

   /**
    * Performs an action after the finalization of the {@link MetamodelBuilder}.
    * 
    * @param data the data provided to this hook
    * 
    * @return the execution status of this hook
    */
   IStatus run(final PostMetamodelBuilderHookDTO data);
}
