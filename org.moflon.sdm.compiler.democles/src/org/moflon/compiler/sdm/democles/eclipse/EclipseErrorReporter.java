package org.moflon.compiler.sdm.democles.eclipse;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.codegen.eclipse.ValidationStatus;
import org.moflon.core.utilities.ErrorReporter;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
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

public class EclipseErrorReporter implements ErrorReporter
{
   private static final Logger logger = Logger.getLogger(EclipseErrorReporter.class);

   private IFile file;

   public EclipseErrorReporter(final IFile ecoreFile)
   {
      this.file = ecoreFile;
   }

   @Override
   public void report(final IStatus status)
   {
      if (status != null && !status.matches(IStatus.OK))
      {
         if (status.isMultiStatus())
         {
            for (final IStatus childStatus : status.getChildren())
            {
               report(childStatus);
            }
         } else
         {
            try
            {
               if (status instanceof ValidationStatus)
               {
                  final ErrorMessage errorMsg = ((ValidationStatus) status).getErrorMessage();
                  createMarker(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID, errorMsg);
               } else
               {
                  final IMarker validationMarker = file.createMarker(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID);
                  validationMarker.setAttribute(IMarker.MESSAGE, status.getMessage());
                  // validationMarker.setAttribute(IMarker.LOCATION, getLocationDescription(message));
                  validationMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
                  validationMarker.setAttribute(IMarker.SEVERITY, convertStatusSeverityToEclipseMarkerSeverity(status.getSeverity()));
                  LogUtils.error(logger, status.getMessage());
               }
            } catch (final CoreException e)
            {
               LogUtils.error(logger, e, "Problem while reporting eMoflon errors in Eclipse: " + MoflonUtil.displayExceptionAsString(e));
            }
         }
      }
   }

   public static final int convertStatusSeverityToEclipseMarkerSeverity(final int value) throws CoreException
   {
      if (value >= IStatus.ERROR)
      {
         return IMarker.SEVERITY_ERROR;
      } else if (value >= IStatus.WARNING)
      {
         return IMarker.SEVERITY_WARNING;
      } else if (value >= IStatus.INFO)
      {
         return IMarker.SEVERITY_INFO;
      }
      final IStatus invalidSeverityConversion = new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(EclipseErrorReporter.class),
            "Cannot convert status severity value " + value + " to a marker");
      throw new CoreException(invalidSeverityConversion);
   }

   private String getLocationDescription(final ErrorMessage message)
   {
      String result = "";
      EList<EObject> locations = message.getLocation();
      if (!locations.isEmpty())
      {
         final EObject location = locations.get(0);
         if (location instanceof StopNode)
         {
            StopNode stopNode = ((StopNode) location);
            result = getReportableEOperationName(stopNode.getOwningActivity().getOwningOperation()) + " with StopNode ->["
                  + eMoflonSDMUtil.extractExpression(stopNode.getReturnValue(), "") + "]";
         } else if (location instanceof ActivityNode)
         {
            ActivityNode anode = ((ActivityNode) location);
            result = getReportableEOperationName(anode.getOwningActivity().getOwningOperation()) + " with Activity node (type '"
                  + location.getClass().getSimpleName() + "', name '" + anode.getName() + "')";
         } else if (location instanceof ActivityEdge)
         {
            ActivityEdge activityEdge = (ActivityEdge) location;
            result = "Activity edge from " + activityEdge.getSource().getName() + " to " + activityEdge.getTarget().getName() + " in "
                  + eMoflonSDMUtil.getFQN(activityEdge.eContainer());
         } else if (location instanceof Activity)
         {
            result = "Activity of " + ((Activity) location).getOwningOperation().getName();
         } else if (location instanceof MethodCallExpression)
         {
            MethodCallExpression mce = (MethodCallExpression) location;
            result = eMoflonSDMUtil.extractMethodCall(mce, "") + " in " + eMoflonSDMUtil.getFQN(mce.eContainer());
         } else if (location instanceof LiteralExpression)
         {
            LiteralExpression le = (LiteralExpression) location;
            result = "Expression \"" + eMoflonSDMUtil.extractValueFromLiteralExpression(le) + "\" in " + eMoflonSDMUtil.getFQN(le.eContainer());
         } else
         {
            result = "[" + eMoflonEMFUtil.getIdentifier(location) + "]" + " in " + eMoflonSDMUtil.getFQN(location.eContainer());
         }
      }
      return result;
   }

   private IMarker createMarker(final String markerId, final ErrorMessage message) throws CoreException
   {
      IMarker validationMarker = file.createMarker(markerId);
      validationMarker.setAttribute(IMarker.MESSAGE, message.getId());
      validationMarker.setAttribute(IMarker.LOCATION, getLocationDescription(message));
      validationMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
      validationMarker.setAttribute(IMarker.SEVERITY, convertValidationResultSeverityToEclipseMarkerSeverity(message.getSeverity()));
      return validationMarker;
   }

   public static final int convertValidationResultSeverityToEclipseMarkerSeverity(final Severity severity) throws CoreException
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
      IStatus invalidSeverityConversion = new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(EclipseErrorReporter.class),
            "Cannot convert " + severity.getLiteral() + " severity to a marker");
      throw new CoreException(invalidSeverityConversion);
   }

   private String getReportableEOperationName(final EOperation eOperation)
   {
      StringBuilder builder = new StringBuilder();
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
