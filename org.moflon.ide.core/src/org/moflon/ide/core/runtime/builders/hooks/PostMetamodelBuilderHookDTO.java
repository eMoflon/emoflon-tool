package org.moflon.ide.core.runtime.builders.hooks;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.moflon.core.mocatomoflon.Exporter;
import org.moflon.ide.core.properties.MocaTreeEAPropertiesReader;

/**
 * Data transfer object for passing data to {@link PostMetamodelBuilderHook}s.
 */
public class PostMetamodelBuilderHookDTO extends MetamodelBuilderHookDTO
{
   public final IStatus mocaToMoflonStatus;
   public final Exporter exporter;

   public PostMetamodelBuilderHookDTO(final IStatus mocaToMoflonStatus, final MocaTreeEAPropertiesReader mocaTreeReader, final Exporter exporter,
         final IProject metamodelproject)
   {
      super(mocaTreeReader, metamodelproject);
      this.mocaToMoflonStatus = mocaToMoflonStatus;
      this.exporter = exporter;
   }
}
