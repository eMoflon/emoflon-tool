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

public class NewMOSLConfiguration extends AbstractMOSLWizard implements INewWizard
{

   @Override
   public void addPages()
   {

   }

   @Override
   protected void doFinish(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
    	 IFile configFile = resource.getProject().getFile("MOSL/_MOSLConfiguration.mconf"); 
    	 
    	 if(!configFile.exists()){
	         monitor.beginTask("Create _MOSLConfiguration", 1);
	
	         StringTemplateGroup stg = loadStringTemplateGroup("/resources/mosl/templates/imports.stg");
	
	         Map<String, Object> attrs = new HashMap<String, Object>();
	         String content = renderTemplate(stg, "imports", attrs);
	         
	         
	         WorkspaceHelper.addFile(resource.getProject(), "MOSL/_MOSLConfiguration.mconf", content, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

    	 }

         openEditor(configFile);
      } finally
      {
         monitor.done();
      }
   }

}
