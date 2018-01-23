package org.moflon.ide.core.runtime.natures;

import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.core.utilities.WorkspaceHelper;

public class IntegrationNature extends MoflonProjectConfigurator {

	private static final String NATURE_ID = "org.moflon.ide.core.runtime.natures.IntegrationNature";

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

   public static String getId()
   {
      return NATURE_ID;
   }

}
