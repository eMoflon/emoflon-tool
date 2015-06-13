package org.moflon.ide.ui.admin.assist.mosl;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.moflon.mosl.utils.exceptions.CanNotResolvePathException;

public class MOSLMarkerResolutionGenerator implements IMarkerResolutionGenerator
{

   @Override
   public IMarkerResolution[] getResolutions(IMarker marker)
   {
      try {
         if (CanNotResolvePathException.class.getSimpleName().equals(marker.getAttribute("errorType"))) {
            return new IMarkerResolution[] { new MissingPatternResolution() };
         }
      } catch (CoreException ce) {
         ce.printStackTrace();
      }
      return new IMarkerResolution[] {};
   }

}
