package org.moflon.ide.core.runtime.natures;

import org.moflon.core.utilities.WorkspaceHelper;

public class IntegrationNature extends MoflonProjectConfigurator {
	
	public IntegrationNature() {
		super();
	}

   @Override
   protected String getBuilderId()
   {
      return WorkspaceHelper.INTEGRATION_BUILDER_ID;
   }

   @Override
   protected String getNatureId()
   {
      return WorkspaceHelper.INTEGRATION_NATURE_ID;
   }
	
}
