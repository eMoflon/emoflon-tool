package org.moflon.ide.core.runtime.natures;

import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;

public class RepositoryNature extends MoflonProjectConfigurator {
	
	public RepositoryNature() {
		super();
	}

   @Override
   protected String getBuilderId()
   {
      return CoreActivator.REPOSITORY_BUILDER_ID;
   }

   @Override
   protected String getNatureId()
   {
      return WorkspaceHelper.REPOSITORY_NATURE_ID;
   }
}
