package org.moflon.ide.metamodelevolution.core;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.core.mocatomoflon.Exporter;
import org.moflon.ide.core.properties.MocaTreeEAPropertiesReader;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHook;

public class MetamodelCoevolutionPostMetamodelBuilderHook implements PostMetamodelBuilderHook
{
   private static final Logger logger = Logger.getLogger(MetamodelBuilder.class);
   
   @Override
   public IStatus run(final IStatus mocaToMoflonStatus, final MocaTreeEAPropertiesReader mocaTreeReader, final Exporter exporter)
   {
      logger.debug("Performing post-build step for meta-model co-evolution support");
      return Status.OK_STATUS;
   }

}
