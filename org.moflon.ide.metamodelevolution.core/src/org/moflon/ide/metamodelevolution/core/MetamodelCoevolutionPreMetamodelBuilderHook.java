package org.moflon.ide.metamodelevolution.core;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.ide.core.runtime.builders.hooks.PreMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PreMetamodelBuilderHookDTO;

public class MetamodelCoevolutionPreMetamodelBuilderHook implements PreMetamodelBuilderHook
{
   private static final Logger logger = Logger.getLogger(MetamodelCoevolutionPreMetamodelBuilderHook.class);

   @Override
   public IStatus run(final PreMetamodelBuilderHookDTO postMetamodelBuilderHookDTO)
   {
      logger.debug("Performing pre-build step for meta-model co-evolution support");

      return Status.OK_STATUS;
   }
}
