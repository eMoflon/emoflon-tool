package org.moflon.moca.tie;

import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;

public class RunIntegrationGeneratorBatch extends AbstractIntegratorGenerator
{
   public RunIntegrationGeneratorBatch(IProject project)
   {
      super(project);
   }

   @Override
   protected String getPackagePrefix()
   {
      return project.getName() + ".org.moflon.tie";
   }

   @Override
   protected String getTemplateName()
   {
      return "TGGMainBatch";
   }

   @Override
   protected String getPathToFileToBeGenerated()
   {
      return "/src/" + project.getName().replace(".", "/") + "/org/moflon/tie/" + getClassName() + ".java";
   }

   @Override
   protected String getClassName()
   {
      return getRootOfClassName() + "Trafo";
   }

   @Override
   protected URL getTemplateFileURL()
   {
      return MoflonUtilitiesActivator.getPathRelToPlugIn("/resources/templates/TGGMain.stg", WorkspaceHelper.getPluginId(getClass()));
   }

   @Override
   protected String getSupportedNature()
   {
      return WorkspaceHelper.REPOSITORY_NATURE_ID;
   }
}
