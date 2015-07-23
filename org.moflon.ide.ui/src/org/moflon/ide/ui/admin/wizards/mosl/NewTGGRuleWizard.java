package org.moflon.ide.ui.admin.wizards.mosl;

import static org.moflon.ide.ui.admin.wizards.util.WizardUtil.loadStringTemplateGroup;
import static org.moflon.ide.ui.admin.wizards.util.WizardUtil.renderTemplate;

import java.util.HashMap;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.INewWizard;
import org.moflon.core.utilities.WorkspaceHelper;

public class NewTGGRuleWizard extends AbstractMOSLWizard implements INewWizard
{
   protected NewTGGRulePage tggRuleInfo;

   @Override
   public void addPages()
   {
      tggRuleInfo = new NewTGGRulePage();
      addPage(tggRuleInfo);
   }

   @Override
   protected void doFinish(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         String ruleName = tggRuleInfo.getRuleName();

         monitor.beginTask("Create TGG", 1);

         StringTemplateGroup stg = loadStringTemplateGroup("/resources/mosl/templates/rule.stg");

         Map<String, Object> attrs = new HashMap<String, Object>();
         attrs.put("name", ruleName);
         String content = renderTemplate(stg, "rule", attrs);

         String rulePath = resource.getProjectRelativePath() + "/" + ruleName + ".tgg";

         WorkspaceHelper.addFile(resource.getProject(), rulePath, content, monitor);

         IFile ruleFile = resource.getProject().getFile(rulePath);

         openEditor(ruleFile);
      } finally
      {
         monitor.done();
      }
   }
}
