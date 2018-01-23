package org.moflon.ide.core.runtime.builders.hooks;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.moflon.core.plugins.PluginProperties;
import org.moflon.ide.core.properties.MocaTreeEAPropertiesReader;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

/**
 * Base class for all DTOs that are used to pass data to hooks during an invocation of the {@link MetamodelBuilder}
 */
public abstract class MetamodelBuilderHookDTO
{
   public final IProject metamodelproject;
   public final MocaTreeEAPropertiesReader mocaTreeReader;

   public MetamodelBuilderHookDTO(MocaTreeEAPropertiesReader mocaTreeReader, IProject metamodelproject)
   {
      this.mocaTreeReader = mocaTreeReader;
      this.metamodelproject = metamodelproject;
   }

   /**
    * Returns the mapping of project name to project metadata from the MOCA tree of the specification
    */
   public final Map<String, PluginProperties> extractRepositoryProjectProperties() throws CoreException
   {
      return this.mocaTreeReader.getProperties(this.metamodelproject);
   }

}
