package org.moflon.ide.core.runtime.builders.hooks;

import org.eclipse.core.resources.IProject;
import org.moflon.ide.core.properties.MocaTreeEAPropertiesReader;

/**
 * Data transfer object for passing data to {@link PreMetamodelBuilderHook}s.
 */
public class PreMetamodelBuilderHookDTO extends MetamodelBuilderHookDTO
{
   public PreMetamodelBuilderHookDTO(MocaTreeEAPropertiesReader mocaTreeReader, IProject metamodelproject)
   {
      super(mocaTreeReader, metamodelproject);
   }
}
