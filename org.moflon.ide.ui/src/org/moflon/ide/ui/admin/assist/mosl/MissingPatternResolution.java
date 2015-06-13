package org.moflon.ide.ui.admin.assist.mosl;

import static org.moflon.ide.ui.admin.wizards.util.WizardUtil.loadStringTemplateGroup;
import static org.moflon.ide.ui.admin.wizards.util.WizardUtil.renderTemplate;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.stringtemplate.StringTemplateGroup;
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

   @Override
   public String getLabel()
   {
      return "Create pattern file";
   }

   @Override
   public void run(final IMarker marker)
   {
      try {
         String message = marker.getAttribute(IMarker.MESSAGE).toString();
         
         Pattern p = Pattern.compile("Cannot find: '(([^\\.]*)\\.pattern)' for path '.*/([^/]*)/([^/]*)'");
         
         Matcher m = p.matcher(message);
         
         if (m.matches()) {
            String patternName = m.group(2);
            String className = m.group(3);
            String operationName = m.group(4);
            IResource resource = marker.getResource();
            IProject project = resource.getProject();
            IContainer parent = resource.getParent();
            String patternDir = parent.getProjectRelativePath().toString() + "/_patterns";
            String patternClassDir = patternDir + "/" + className;
            String patternOperationDir = patternClassDir + "/" + operationName; 
            String patternFileName = m.group(1);
            
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
         e.printStackTrace();
      }

   }

}
