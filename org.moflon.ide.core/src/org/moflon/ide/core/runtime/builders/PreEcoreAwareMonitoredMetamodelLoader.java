package org.moflon.ide.core.runtime.builders;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.emf.build.MonitoredMetamodelLoader;
import org.moflon.ide.core.MoslTggConstants;

/**
 * This {@link MonitoredMetamodelLoader} also accepts projects that contain a pre.ecore file in the model folder
 * @author Roland Kluge - Initial implementation
 */
class PreEcoreAwareMonitoredMetamodelLoader extends MonitoredMetamodelLoader
{

   /**
    * Delegate to super constructor
    *
    * @see MonitoredMetamodelLoader#MonitoredMetamodelLoader(ResourceSet, IFile, MoflonPropertiesContainer)
    */
   public PreEcoreAwareMonitoredMetamodelLoader(ResourceSet resourceSet, IFile ecoreFile, MoflonPropertiesContainer moflonProperties)
   {
      super(resourceSet, ecoreFile, moflonProperties);
   }

   /**
    * Accepts (additionally to the super class) projects that contain a .pre.ecore file
    */
   @Override
   protected boolean isValidProject(IProject project)
   {
      return super.isValidProject(project) || MoslTggConstants.getDefaultPreEcoreFile(project).exists();
   }
}
