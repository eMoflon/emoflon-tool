package org.moflon.ide.core.runtime.natures;

import org.moflon.core.utilities.WorkspaceHelper;

public class RepositoryNature extends MoflonProjectConfigurator {
	
   @Override
   protected String getBuilderId()
   {
      return WorkspaceHelper.REPOSITORY_BUILDER_ID;
   }

   @Override
   protected String getNatureId()
   {
      return WorkspaceHelper.REPOSITORY_NATURE_ID;
   }
}
