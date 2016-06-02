package org.moflon.moca.tie;

import org.eclipse.core.resources.IProject;

public class RunIntegrationGeneratorCC extends RunIntegrationGeneratorBatch
{
   public RunIntegrationGeneratorCC(IProject project)
   {
      super(project);
   }

   @Override
   protected String getTemplateName()
   {
      return "TGGMainCC";
   }

   @Override
   protected String getClassName()
   {
      return getRootOfClassName() + "CC";
   }
}
