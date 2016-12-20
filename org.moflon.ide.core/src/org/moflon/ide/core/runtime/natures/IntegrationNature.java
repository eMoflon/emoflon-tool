package org.moflon.ide.core.runtime.natures;

import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;

public class IntegrationNature extends MoflonProjectConfigurator {
	
	public IntegrationNature() {
		super(true);
	}

   @Override
   protected String getBuilderId()
   {
      return CoreActivator.INTEGRATION_BUILDER_ID;
   }

   @Override
   protected String getNatureId()
   {
      return WorkspaceHelper.INTEGRATION_NATURE_ID;
   }
	
}
