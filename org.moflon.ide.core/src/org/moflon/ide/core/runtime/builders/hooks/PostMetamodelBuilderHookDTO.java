package org.moflon.ide.core.runtime.builders.hooks;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.moflon.core.mocatomoflon.Exporter;
import org.moflon.ide.core.properties.MocaTreeEAPropertiesReader;
import org.moflon.util.plugins.MetamodelProperties;

/**
 * Data transfer object for passing data to {@link PostMetamodelBuilderHook}s.
 */
public class PostMetamodelBuilderHookDTO
{
   public final IStatus mocaToMoflonStatus;

   public final MocaTreeEAPropertiesReader mocaTreeReader;

   public final Exporter exporter;

   public final IProject metamodelproject;

   public PostMetamodelBuilderHookDTO(final IStatus mocaToMoflonStatus, final MocaTreeEAPropertiesReader mocaTreeReader, final Exporter exporter,
         final IProject metamodelproject)
   {
      this.mocaToMoflonStatus = mocaToMoflonStatus;
      this.mocaTreeReader = mocaTreeReader;
      this.exporter = exporter;
      this.metamodelproject = metamodelproject;
   }
   
   /**
    * Returns the mapping of project name to project metadata from the MOCA tree of the specification
    */
   public Map<String, MetamodelProperties> extracRepositoryProjectProperties() throws CoreException {
      return this.mocaTreeReader.getProperties(this.metamodelproject);
   }
}
