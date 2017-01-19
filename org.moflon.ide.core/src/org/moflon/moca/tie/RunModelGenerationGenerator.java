package org.moflon.moca.tie;

import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;

public class RunModelGenerationGenerator extends RunIntegrationGeneratorBatch
{

   public RunModelGenerationGenerator(IProject project)
   {
      super(project);
   }

   @Override
   protected String getTemplateName()
   {
      return "TGGMainModelGen";
   }

   protected String getClassName()
   {
      return getRootOfClassName() + "ModelGen";
   }
}
