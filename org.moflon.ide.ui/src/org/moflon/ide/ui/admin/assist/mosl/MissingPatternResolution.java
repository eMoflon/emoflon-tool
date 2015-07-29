package org.moflon.ide.ui.admin.assist.mosl;

import static org.moflon.ide.ui.admin.wizards.util.WizardUtil.loadStringTemplateGroup;
import static org.moflon.ide.ui.admin.wizards.util.WizardUtil.renderTemplate;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

class MissingPatternResolution implements IMarkerResolution
{
   private final Logger log= Logger.getLogger(this.getClass());
	
   @Override
   public String getLabel()
   {
      return "Create pattern file";
   }

   @Override
   public void run(final IMarker marker)
   {
      try {
    	  
    	 String path = marker.getAttribute("referencePath").toString();    	 
    	 
    	 String[] pathParts = path.split("/");
    	 
    	 if(pathParts.length >= 3){
    	 
         String message = marker.getAttribute(IMarker.MESSAGE).toString();

            String patternFileName = message.replace("For the name ", "");
            patternFileName = patternFileName.substring(0, patternFileName.indexOf(" cannot"));
            String patternName = patternFileName.replace(".pattern", "");
            String operationName = pathParts[pathParts.length-1];
            String className = pathParts[pathParts.length-2];         
            IResource resource = marker.getResource();
            IProject project = resource.getProject();
            IContainer parent = resource.getParent();
            String patternDir = parent.getProjectRelativePath().toString() + "/_patterns";
            String patternClassDir = patternDir + "/" + className;
            String patternOperationDir = patternClassDir + "/" + operationName; 
            
            
            IFolder patternFolder = project.getFolder(patternDir);
            if (!patternFolder.exists()) {
               patternFolder.create(true, true, null);
            }
            patternFolder = project.getFolder(patternClassDir);
            if (!patternFolder.exists()) {
               patternFolder.create(true, true, null);
            }
            patternFolder = project.getFolder(patternOperationDir);
            if (!patternFolder.exists()) {
               patternFolder.create(true, true, null);
            }
            
            StringTemplateGroup stg = loadStringTemplateGroup("/resources/mosl/templates/pattern.stg");
            
            Map<String, Object> attrs = new HashMap<String, Object>();
            attrs.put("name", patternName);
            String contents = renderTemplate(stg, "pattern", attrs);
            
            IFile patternFile = project.getFile(patternOperationDir + "/" + patternFileName);
            ByteArrayInputStream source = new ByteArrayInputStream(contents.getBytes());
            patternFile.create(source, true, null);
            
            IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), patternFile);

    	 }
      } catch (Exception e) {
    	 log.debug(e.getMessage(), e);
         e.printStackTrace();
      }

   }

}
