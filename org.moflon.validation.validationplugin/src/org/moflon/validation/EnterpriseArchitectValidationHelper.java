package org.moflon.validation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.moflon.codegen.ErrorReporter;
import org.moflon.codegen.eclipse.ValidationStatus;
import org.moflon.core.utilities.EAInterfaceUriHelper;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.validation.info.ValidationChannel;

//import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import SDMLanguage.activities.Activity;
import SDMLanguage.activities.ActivityEdge;
import SDMLanguage.activities.StartNode;
import SDMLanguage.activities.StopNode;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.LinkVariable;
import SDMLanguage.patterns.ObjectVariable;

public final class EnterpriseArchitectValidationHelper implements ErrorReporter
{
   private IProject metamodelProject;

   private boolean attemptedConnection;

   private boolean establishedConnection;

   private final int DEFAULT_EA_PORT = 6423;

   private final Logger logger = Logger.getLogger(EnterpriseArchitectValidationHelper.class);

   private List<ValidationData> validationDataToSend;

   public EnterpriseArchitectValidationHelper(final IProject metamodelProject)
   {
      this.metamodelProject = metamodelProject;
      this.attemptedConnection = false;
      this.establishedConnection = false;
      this.validationDataToSend = new ArrayList<EnterpriseArchitectValidationHelper.ValidationData>();
   }

   /**
    * Resets the {@link #attemptedConnection} variable to its default false;
    */
   public void reset()
   {
      attemptedConnection = false;
   }

   /**
    * Returns whether EA is running.
    */
   public final boolean isEARunning()
   {
      if (!attemptedConnection)
      {
         attemptedConnection = true;
         establishedConnection = new ValidationChannel(DEFAULT_EA_PORT).checkConnection();
      }

      return establishedConnection;
   }

   public final void sendResultToEA(final ValidationReport validationReport, final IPath metamodelProjectPath, final int errorCounterOffset)
   {
      List<String> messages = new ArrayList<String>();
      messages.add("SETUP_VALIDATION_SESSION");
      messages.add("ValidationResult");
      int i = errorCounterOffset;
      for (ErrorMessage status : validationReport.getErrorMessages())
      {
         if (status.getSeverity().getValue() >= Severity.ERROR_VALUE)
         {
            String uriFragment = "";
            if (status.getLocation().get(0) != null)
            {
               EObject relatedObject = status.getLocation().get(0);
               uriFragment = computeURIString(relatedObject);
            }

            messages.add("Error" + i + ".message = " + status.getId());
            messages.add("Error" + i + ".path = " + uriFragment);
            i++;
         }
      }
      messages.add("TEARDOWN_VALIDATION_SESSION");

      sendMessagesToEA(messages);
   }

   public void sendAllValidationData()
   {

      List<String> messages = new ArrayList<String>();
      messages.add("SETUP_VALIDATION_SESSION");
      for (ValidationData dataToSend : validationDataToSend)
      {
         addErrorToMessages(dataToSend.getErrorMessage(), dataToSend.getCount(), messages);
      }
      messages.add("TEARDOWN_VALIDATION_SESSION");
      sendMessagesToEA(messages);

   }

   public final void sendErrorMessageToEA(final ErrorMessage errorMsg, final IPath metamodelProjectPath, final int errorCnt)
   {
      List<String> messages = new ArrayList<String>();
      messages.add("SETUP_VALIDATION_SESSION");
      addErrorToMessages(errorMsg, errorCnt, messages);
      messages.add("TEARDOWN_VALIDATION_SESSION");

      sendMessagesToEA(messages);
   }

   private void addErrorToMessages(final ErrorMessage errorMsg, final int errorCnt, final List<String> messages)
   {
      if (errorMsg.getSeverity().getValue() >= Severity.ERROR_VALUE)
      {
         String uriFragment = "";
         if (errorMsg.getLocation().get(0) != null)
         {
            EObject relatedObject = errorMsg.getLocation().get(0);
            uriFragment = computeURIString(relatedObject);
         }

         if (!uriFragment.equals(""))
         {
            messages.add("Error" + errorCnt + ".message = " + errorMsg.getId());
            messages.add("Error" + errorCnt + ".path = " + uriFragment);
         }
      }
   }
   
   public final void openRuleInEA(String packagename, String rule)
   {
      List<String> messages = new ArrayList<String>();
      messages.add("SETUP_HIGHLIGHT_OBJECT");
      messages.add(packagename + ":TGGSchemaPackage/Rules:RulePackage/" + rule + ":Rule/0:Diagram");
      messages.add("TEARDOWN_HIGHLIGHT_OBJECT");

      sendMessagesToEA(messages);
   }

   public final void openRulesDiagramInEA(String packagename)
   {
      List<String> messages = new ArrayList<String>();
      messages.add("SETUP_HIGHLIGHT_OBJECT");
      messages.add(packagename + ":TGGSchemaPackage/Rules:RulePackage/0:Diagram");
      messages.add("TEARDOWN_HIGHLIGHT_OBJECT");

      sendMessagesToEA(messages);
   }

   private void sendMessagesToEA(final List<String> messages)
   {
      try
      {
         int port = DEFAULT_EA_PORT;
         ValidationChannel eaChannel = new ValidationChannel(port);
         eaChannel.send(messages);
      } catch (UnknownHostException e)
      {
         logger.error("Unable to send messages to EA - do you have a running EA instance?");
      } catch (IOException e)
      {
         logger.error("No running EA instance to send validation result");
      }
   }

   public final String computeURIString(final EObject eObject)
   {
      if (eObject instanceof ObjectVariable)
      {
         return getObjectVariableString((ObjectVariable) eObject);
      } else if (eObject instanceof LinkVariable)
      {
         return getLinkVariableString((LinkVariable) eObject);
      } else if (eObject instanceof StoryNode)
      {
         return getStoryNodeString((StoryNode) eObject);
      } else if (eObject instanceof StopNode)
      {
         return getStopNodeString((StopNode) eObject);
      } else if (eObject instanceof StartNode)
      {
         return getStartNodeString((StartNode) eObject);
      } else if (eObject instanceof ActivityEdge)
      {
         return getActivityEdgeString((ActivityEdge) eObject);
      } else if (eObject instanceof Activity)
      {
         return getActivityString((Activity) eObject);
      } else if (eObject instanceof EOperation)
      {
         return EAInterfaceUriHelper.getEOperationString((EOperation) eObject);
      } else if (eObject instanceof EClass)
      {
         return EAInterfaceUriHelper.getEClassString((EClass) eObject);
      } else if (eObject instanceof EPackage)
      {
         return EAInterfaceUriHelper.getEPackageString((EPackage) eObject);
      }
      return "";
   }

   public final String getActivityString(final Activity activity)
   {
      final StringBuilder builder = new StringBuilder();
      builder.append(EAInterfaceUriHelper.getEOperationString(activity.getOwningOperation()));
      builder.append(EAInterfaceUriHelper.DELIM);
      builder.append(EAInterfaceUriHelper.getActivityString());
      return builder.toString();
   }

   public final String getActivityEdgeString(final ActivityEdge activityEdge)
   {
      final StringBuilder builder = new StringBuilder();
      builder.append(getActivityString(activityEdge.getOwningActivity()));
      builder.append(EAInterfaceUriHelper.DELIM);
      builder.append(EAInterfaceUriHelper.getActivityEdgeString(activityEdge.getSource().getName(), activityEdge.getTarget().getName()));
      return builder.toString();
   }

   public final String getStartNodeString(final StartNode startNode)
   {
      final StringBuilder builder = new StringBuilder();
      builder.append(getActivityString(startNode.getOwningActivity()));
      builder.append(EAInterfaceUriHelper.DELIM);
      builder.append(EAInterfaceUriHelper.getStartNodeString(startNode.getName()));
      return builder.toString();
   }

   public final String getStopNodeString(final StopNode stopNode)
   {
      final StringBuilder builder = new StringBuilder();
      builder.append(getActivityString(stopNode.getOwningActivity()));
      builder.append(EAInterfaceUriHelper.DELIM);
      builder.append(EAInterfaceUriHelper.getStopNodeString(stopNode.getName()));
      return builder.toString();
   }

   public final String getStoryNodeString(final StoryNode storyNode)
   {
      final StringBuilder builder = new StringBuilder();
      builder.append(getActivityString(storyNode.getOwningActivity()));
      builder.append(EAInterfaceUriHelper.DELIM);
      builder.append(EAInterfaceUriHelper.getStoryNodeString(storyNode.getName()));
      return builder.toString();
   }

   public final String getObjectVariableString(final ObjectVariable ov)
   {
      final StringBuilder builder = new StringBuilder();
      builder.append(getStoryNodeString(ov.getPattern().getStoryNode()));
      builder.append(EAInterfaceUriHelper.DELIM);
      builder.append(EAInterfaceUriHelper.getObjVarString(ov.getName()));
      return builder.toString();
   }

   public final String getLinkVariableString(final LinkVariable lv)
   {
      final StringBuilder builder = new StringBuilder();
      builder.append(getStoryNodeString(lv.getPattern().getStoryNode()));
      builder.append(EAInterfaceUriHelper.DELIM);
      builder.append(EAInterfaceUriHelper.getLinkVarString(lv.getTarget().getName(), lv.getSource().getName()));
      return builder.toString();
   }

   public class ValidationData
   {
      private ErrorMessage errorMessage;

      private IPath metamodelPath;

      private int count;

      public ValidationData(final ErrorMessage errorMessage, final IPath metamodelPath, final int count)
      {
         this.errorMessage = errorMessage;
         this.metamodelPath = metamodelPath;
         this.count = count;
      }

      public ErrorMessage getErrorMessage()
      {
         return errorMessage;
      }

      public void setErrorMessage(final ErrorMessage errorMessage)
      {
         this.errorMessage = errorMessage;
      }

      public IPath getMetamodelPath()
      {
         return metamodelPath;
      }

      public void setMetamodelPath(final IPath metamodelPath)
      {
         this.metamodelPath = metamodelPath;
      }

      public int getCount()
      {
         return count;
      }

      public void setCount(final int count)
      {
         this.count = count;
      }

   }

   public void gatherValidationData(final ValidationData validationData)
   {
      this.validationDataToSend.add(validationData);
   }

   @Override
   public void report(final IStatus status)
   {
      if (status == null)
         return;
      
      int errorCnt = 1;

      for (IStatus valStatus : status.getChildren())
      {

         if (!valStatus.isOK() && valStatus.getClass().equals(ValidationStatus.class))
         {
            ErrorMessage errorMsg = ((ValidationStatus) valStatus).getErrorMessage();
            gatherValidationData(new ValidationData(errorMsg, metamodelProject.getLocation(), errorCnt));
            errorCnt += 1;

         }

         if (valStatus.isMultiStatus())
         {
            report(valStatus);
         }
      }

      if (isEARunning())
      {
         logger.info("Sending messages to EA ...");
         try
         {
            sendAllValidationData();
         } catch (Exception e)
         {
            logger.error("Unable to communicate with EA: " + e.getMessage() + ", Please ensure that EA is running!");
         }
      }
   }

}
