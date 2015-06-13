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
import org.moflon.ide.ui.admin.wizards.AbstractWizard;

public class NewPatternWizard extends AbstractWizard implements INewWizard
{

   protected NewPatternPage patternInfo;

   @Override
   public void addPages()
   {
      patternInfo = new NewPatternPage();
      addPage(patternInfo);
   }

   @Override
   protected void doFinish(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         String patternName = patternInfo.getPatternName();

         monitor.beginTask("Create pattern", 1);

         StringTemplateGroup stg = loadStringTemplateGroup("/resources/mosl/templates/pattern.stg");

         Map<String, Object> attrs = new HashMap<String, Object>();
         attrs.put("name", patternName);
         String content = renderTemplate(stg, "rule", attrs);

         String patternPath = resource.getProjectRelativePath() + "/" + patternName + ".pattern";

         WorkspaceHelper.addFile(resource.getProject(), patternPath, content, monitor);

         IFile patternFile = resource.getProject().getFile(patternPath);

         openEditor(patternFile);
      } finally
      {
         monitor.done();
      }
   }

}
