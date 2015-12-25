package org.moflon.moca.tie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.moca.AbstractFileGenerator;
import org.moflon.moca.BasicFormatRenderer;

public abstract class AbstractIntegratorGenerator extends AbstractFileGenerator
{
   private static Logger logger = Logger.getLogger(AbstractFileGenerator.class);

   private StringTemplateGroup stg;

   public AbstractIntegratorGenerator(final IProject project)
   {
      super(project);
   }

   @Override
   protected void prepareCodegen()
   {
      List<String> packageFolders = Arrays.asList(getPackagePrefix().split("\\."));
      String currentFolder = "src/";
      for (String folder : packageFolders)
      {
         currentFolder += folder + WorkspaceHelper.PATH_SEPARATOR;
         createFolder(currentFolder);
      }
   }

   protected abstract String getPackagePrefix();

   @Override
   protected Map<String, String> extractFileNamesToContents() throws CoreException
   {
      loadStringTemplateGroup();

      Map<String, Object> attributes = extractTemplateParameters();

      String fileName = project.getProjectRelativePath() + getPathToFileToBeGenerated();

      Map<String, String> fileNameToContent = new HashMap<String, String>();
      try
      {
         fileNameToContent.put(fileName, renderTemplate(getTemplateName(), attributes));
      } catch (FileNotFoundException e)
      {
         MoflonUtil.throwCoreExceptionAsError(e.getMessage(), MoflonUtilitiesActivator.getDefault().getPluginId(), e);
      }

      return fileNameToContent;
   }

   protected abstract String getTemplateName();

   protected abstract String getPathToFileToBeGenerated();

   private void loadStringTemplateGroup()
   {
      try
      {
         InputStreamReader reader = new InputStreamReader(getTemplateFileURL().openStream());
         stg = new StringTemplateGroup(reader);
      } catch (IOException e)
      {
         logger.debug("unable to load template file: " + getTemplateFileURL());
      }
   }

   protected abstract URL getTemplateFileURL();

   private IFolder createFolder(final String path)
   {
      IFolder folder = project.getFolder(path);
      if (!folder.exists())
      {
         try
         {
            return WorkspaceHelper.addFolder(project, path, new NullProgressMonitor());
         } catch (CoreException e)
         {
            logger.error("error while creating folder: " + path, e);
            return null;
         }
      } else
      {
         return folder;
      }
   }

   private String renderTemplate(final String templateName, final Map<String, Object> attributes) throws FileNotFoundException
   {
      StringTemplate st = stg.getInstanceOf(templateName);
      st.registerRenderer(String.class, new BasicFormatRenderer());
      st.setAttributes(attributes);
      return st.toString();
   }

   protected Map<String, Object> extractTemplateParameters()
   {
      Map<String, Object> attributes = new HashMap<String, Object>();
      ArrayList<String> projects = new ArrayList<String>();
      projects.add(project.getName());

      for (IProject projectOnBuildPath : WorkspaceHelper.getProjectsOnBuildPathInReversedOrder(project))
      {
         try
         {
            if (projectOnBuildPath.hasNature(getSupportedNature()))
               projects.add(projectOnBuildPath.getName());
         } catch (CoreException e)
         {
            e.printStackTrace();
         }
      }

      attributes.put("projects", projects);
      attributes.put("corrPackage", getRootOfClassName() + "Package.eINSTANCE");
      attributes.put("className", getClassName());
      
      return attributes;
   }

   protected abstract String getSupportedNature();
   
   protected String getRootOfClassName(){
      return MoflonUtil.getDefaultNameOfFileInProjectWithoutExtension(project.getName());
   }
   
   protected abstract String getClassName();
}
