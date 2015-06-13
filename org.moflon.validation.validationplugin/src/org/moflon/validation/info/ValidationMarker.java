package org.moflon.validation.info;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Tobias Raffel
 * @deprecated Not used anymore
 */
//TODO: remove
@Deprecated
public class ValidationMarker
{
   public final static String MARKER_TYPE = IMarker.PROBLEM;

   public static void removeMarkers(final IResource resource) throws CoreException
   { 
      resource.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
   }

   /**
    * Reports an error within a resource, specified by an violation object.
    * 
    * @param resource
    *           A resource where an error has been occured.
    * @param violation
    *           A Violation-object, providing information about the location and cause of the violation.
    */
   public static void reportError(final IResource resource, final Violation violation) throws IllegalArgumentException
   {
      if (resource == null || violation == null)
      {
         throw new IllegalArgumentException("Either resource, or violation is null");
      }
      
      try
      {
         IMarker validationMarker = resource.createMarker(MARKER_TYPE);
         validationMarker.setAttribute(IMarker.MESSAGE, violation.getMessage());
         validationMarker.setAttribute(IMarker.LOCATION, violation.getLocation());
         validationMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
         validationMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
      } catch (CoreException e)
      {
         e.printStackTrace();
      }
   }
}
