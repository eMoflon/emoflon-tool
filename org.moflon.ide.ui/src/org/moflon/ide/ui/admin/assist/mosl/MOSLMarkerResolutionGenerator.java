package org.moflon.ide.ui.admin.assist.mosl;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.moflon.mosl.utils.AbstractResolver;

import MOSLCodeAdapter.moslPlus.MoslCategory;

public class MOSLMarkerResolutionGenerator implements IMarkerResolutionGenerator
{
	private static final String SEMANTIC_ERROR = "SemanticError"; 
   @Override
   public IMarkerResolution[] getResolutions(IMarker marker)
   {
      try {
         if ((SEMANTIC_ERROR+":"+AbstractResolver.getStringOfCategory(MoslCategory.PATTERN_FILE)).equals(marker.getAttribute("errorType"))) {
            return new IMarkerResolution[] { new MissingPatternResolution() };
         }
      } catch (CoreException ce) {
         ce.printStackTrace();
      }
      return new IMarkerResolution[] {};
   }

}
