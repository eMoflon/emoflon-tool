package org.moflon.ide.core.runtime.natures;

import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.core.utilities.WorkspaceHelper;

public class RepositoryNature extends MoflonProjectConfigurator {

   private static final String NATURE_ID = "org.moflon.ide.core.runtime.natures.RepositoryNature";

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

   public static String getId()
   {
      return NATURE_ID;
   }
}
