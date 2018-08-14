package org.moflon.compiler.sdm.democles.eclipse;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.codegen.eclipse.ValidationStatus;
import org.moflon.core.ui.errorhandling.MultiStatusAwareErrorReporter;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.moflon.util.eMoflonSDMUtil;

import SDMLanguage.activities.Activity;
import SDMLanguage.activities.ActivityEdge;
import SDMLanguage.activities.ActivityNode;
import SDMLanguage.activities.StopNode;
import SDMLanguage.calls.callExpressions.MethodCallExpression;
import SDMLanguage.expressions.LiteralExpression;

public class EclipseErrorReporter extends MultiStatusAwareErrorReporter
{
   public EclipseErrorReporter(final IFile ecoreFile)
   {
      super(ecoreFile);
   }

   /**
    * Reports a concrete status message (no {@link MultiStatus}.
    * If the given {@link IStatus} is a {@link ValidationStatus}, {@link #createMarker(String, ErrorMessage)} from this class is invoked.
    * Else, the method delegates to the super class.
    */
   @Override
   public void reportLeafStatus(final IStatus status) throws CoreException
   {
      if (status instanceof ValidationStatus)
      {
         final ErrorMessage errorMsg = ((ValidationStatus) status).getErrorMessage();
         createMarker(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID, errorMsg);
      } else
      {
         super.reportLeafStatus(status);
      }
   }

   /**
	 * Determines a human-readable description of the location of the given message
	 * @param message the {@link ErrorMessage} to format
	 * @return the formatted message
	 * @see ErrorMessage#getLocation()
	 */
	private String getLocationDescription(final ErrorMessage message) {
		final String result;
		final EList<EObject> locations = message.getLocation();
		if (!locations.isEmpty()) {
			final EObject location = locations.get(0);
			if (location instanceof StopNode) {
				final StopNode stopNode = ((StopNode) location);
				result = getReportableEOperationName(stopNode.getOwningActivity().getOwningOperation())
						+ " with StopNode ->[" + eMoflonSDMUtil.extractExpression(stopNode.getReturnValue(), "") + "]";
			} else if (location instanceof ActivityNode) {
			   final ActivityNode anode = ((ActivityNode) location);
				result = getReportableEOperationName(anode.getOwningActivity().getOwningOperation())
						+ " with Activity node (type '" + location.getClass().getSimpleName() + "', name '"
						+ anode.getName() + "')";
			} else if (location instanceof ActivityEdge) {
			   final ActivityEdge activityEdge = (ActivityEdge) location;
				result = "Activity edge from " + activityEdge.getSource().getName() + " to "
						+ activityEdge.getTarget().getName() + " in "
						+ eMoflonSDMUtil.getFQN(activityEdge.eContainer());
			} else if (location instanceof Activity) {
				result = "Activity of " + ((Activity) location).getOwningOperation().getName();
			} else if (location instanceof MethodCallExpression) {
				MethodCallExpression mce = (MethodCallExpression) location;
				result = eMoflonSDMUtil.extractMethodCall(mce, "") + " in " + eMoflonSDMUtil.getFQN(mce.eContainer());
			} else if (location instanceof LiteralExpression) {
				LiteralExpression le = (LiteralExpression) location;
				result = "Expression \"" + eMoflonSDMUtil.extractValueFromLiteralExpression(le) + "\" in "
						+ eMoflonSDMUtil.getFQN(le.eContainer());
			} else {
				result = "[" + eMoflonEMFUtil.getIdentifier(location) + "]" + " in "
						+ eMoflonSDMUtil.getFQN(location.eContainer());
			}
		} else {
         result = "";
      }
		return result;
	}

   private IMarker createMarker(final String markerId, final ErrorMessage message) throws CoreException
   {
      final IFile file = getFile();
      final IResource markedResource = file.exists() ? file : file.getProject();
      IMarker validationMarker = markedResource.createMarker(markerId);
      validationMarker.setAttribute(IMarker.MESSAGE, message.getId());
      validationMarker.setAttribute(IMarker.LOCATION, getLocationDescription(message));
      validationMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
      validationMarker.setAttribute(IMarker.SEVERITY, convertValidationResultSeverityToEclipseMarkerSeverity(message.getSeverity()));
      return validationMarker;
   }

   /**
    * Converts the given {@link Severity} to the corresponding value for {@link IMarker}
    * @param severity the {@link Severity}
    * @return the severity level for {@link IMarker}
    * @throws CoreException if the severity cannot be converted
    */
   private static final int convertValidationResultSeverityToEclipseMarkerSeverity(final Severity severity) throws CoreException
   {
      int value = severity.getValue();
      if (value >= Severity.ERROR_VALUE)
      {
         return IMarker.SEVERITY_ERROR;
      } else if (value >= Severity.WARNING_VALUE)
      {
         return IMarker.SEVERITY_WARNING;
      } else if (value >= Severity.INFO_VALUE)
      {
         return IMarker.SEVERITY_INFO;
      }

      final IStatus invalidSeverityConversion = new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(EclipseErrorReporter.class),
            "Cannot convert " + severity.getLiteral() + " severity to a marker");
      throw new CoreException(invalidSeverityConversion);
   }

   /**
    * Pretty-prints the {@link EOperation} name
    * @param eOperation the {@link EOperation}
    * @return the formatted name
    */
   private String getReportableEOperationName(final EOperation eOperation)
   {
      final StringBuilder builder = new StringBuilder();
      builder.append(eOperation.getEContainingClass().getName());
      builder.append(".");
      builder.append(eOperation.getName());
      builder.append("(");
      for (int i = 0; i < eOperation.getEParameters().size(); i++)
      {
         if (i > 0)
         {
            builder.append(", ");
         }
         builder.append(eOperation.getEParameters().get(i).getEType().getName());
      }
      builder.append(")");
      return builder.toString();
   }
}
