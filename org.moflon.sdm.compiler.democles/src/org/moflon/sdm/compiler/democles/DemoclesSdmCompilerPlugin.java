package org.moflon.sdm.compiler.democles;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.osgi.framework.BundleContext;

public class DemoclesSdmCompilerPlugin extends Plugin
{
   private static String bundleId;

   public static String getModuleID()
   {
      if (bundleId == null)
         throw new NullPointerException();
      else
         return bundleId;
   }

   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      bundleId = context.getBundle().getSymbolicName();

   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      super.stop(context);
   }

   public static final int convertStatusSeverityToEclipseMarkerSeverity(final int value) throws CoreException {
      if (value >= IStatus.ERROR) {
         return IMarker.SEVERITY_ERROR;
      } else if (value >= IStatus.WARNING) {
         return IMarker.SEVERITY_WARNING;
      } else if (value >= IStatus.INFO) {
         return IMarker.SEVERITY_INFO;
      }
      IStatus invalidSeverityConversion = new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), "Cannot convert status severity value " + value
            + " to a marker");
      throw new CoreException(invalidSeverityConversion);
   }
   
   public static final int convertValidationResultSeverityToEclipseMarkerSeverity(final Severity severity) throws CoreException {
	   int value = severity.getValue();
	   if (value >= Severity.ERROR_VALUE) {
		   return IMarker.SEVERITY_ERROR;
	   } else if (value >= Severity.WARNING_VALUE) {
		   return IMarker.SEVERITY_WARNING;
	   } else if (value >= Severity.INFO_VALUE) {
		   return IMarker.SEVERITY_INFO;
	   }
	   IStatus invalidSeverityConversion = new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), "Cannot convert " + severity.getLiteral()
	   + " severity to a marker");
	   throw new CoreException(invalidSeverityConversion);
   }
}
