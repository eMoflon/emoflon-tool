package org.moflon.ide.debug.ui.actions;

import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.debug.ui.actions.OpenVariableConcreteTypeAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.moflon.ide.debug.core.model.phases.init.RuleVariable;
import org.moflon.ide.debug.core.model.phases.init.TripleMatchVariable;
import org.moflon.properties.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.validation.EnterpriseArchitectValidationHelper;

import MoflonPropertyContainer.MoflonPropertiesContainer;
import TGGRuntime.TripleMatch;

@SuppressWarnings("restriction")
public class JumpToEA extends OpenVariableConcreteTypeAction
{

   private static final String EAP = ".eap";

   @Override
   protected Object resolveSourceElement(Object e) throws CoreException
   {
      if (e instanceof TripleMatchVariable)
      {
         TripleMatchVariable tmv = (TripleMatchVariable) e;
         TripleMatch tm = (TripleMatch) tmv.getAdapter(TripleMatch.class);
         return tm.getRuleName();
      } else if (e instanceof RuleVariable)
      {
         return (RuleVariable) e;
      }
      return null;
   }

   @Override
   protected void openInEditor(Object sourceElement) throws CoreException
   {
      if (sourceElement instanceof String)
      {
         String ruleName = (String) sourceElement;
         openRule(ruleName, false);

      } else if (sourceElement instanceof RuleVariable)
      {
         RuleVariable rv = (RuleVariable) sourceElement;

         if (rv.getName().equals(RuleVariable.SOURCE_RULES) || rv.getName().equals(RuleVariable.TARGET_RULES))
         {
            String firstRuleName = rv.getValue().getVariables()[0].getName();
            openRule(firstRuleName, true);
         } else
         {
            openRule(rv.getName(), false);
         }
      }
   }

   /**
    * 
    * @param ruleName
    * @param showParentDiagramForRule
    *           - if true only the diagram containing all rules is displayed. If false the specific rule is displayed.
    * @throws CoreException
    */
   private void openRule(String ruleName, boolean showParentDiagramForRule) throws CoreException
   {
      // findTypeInWorkspace("RulesPackage", false)
      IType findTypeInWorkspace = findTypeInWorkspace(ruleName, false);
      String packageName = findTypeInWorkspace.getJavaProject().getElementName();

      // Get moflon.properties.xmi to determine EA project
      IPath path = findTypeInWorkspace.getJavaProject().getPath().append(MoflonPropertiesContainerHelper.MOFLON_CONFIG_FILE);
      String pathToMoflonProperties = ResourcesPlugin.getWorkspace().getRoot().findMember(path).getRawLocation().toFile().getAbsolutePath();

      // Load moflon.properties.xmi and extract path to metamodel project
      MoflonPropertiesContainer properties = (MoflonPropertiesContainer) eMoflonEMFUtil.loadModel(pathToMoflonProperties);
      String metamodelproject = properties.getMetaModelProject().getMetaModelProjectName();
      IPath metamodelprojectPath = ResourcesPlugin.getWorkspace().getRoot().getProject(metamodelproject).getRawLocation();

      // Determine .eap file location in EA project
      File[] eapFiles = metamodelprojectPath.toFile().listFiles(new FileFilter() {

         @Override
         public boolean accept(File pathname)
         {
            return pathname.getName().endsWith(EAP);
         }
      });

      EnterpriseArchitectValidationHelper helper = new EnterpriseArchitectValidationHelper(null);
      if (!helper.isEARunning())
      {
         // Start EA for first found eap file
         try
         {
            Desktop.getDesktop().open(eapFiles[0]);
            new ProgressMonitorDialog(Display.getCurrent().getActiveShell()).run(true, true, new EALaunchProgressMonitor(helper, true));
         } catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      if (showParentDiagramForRule)
      {
         helper.openRulesDiagramInEA(packageName);
      } else
      {
         helper.openRuleInEA(packageName, ruleName);
      }
   }
}
