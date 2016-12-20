package org.moflon.ide.core.runtime.builders;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.gervarro.eclipse.workspace.util.RelevantElementCollector;
import org.moflon.core.utilities.WorkspaceHelper;

public class MOSLGTBuilder extends AbstractVisitorBuilder
{
   public static final Logger logger = Logger.getLogger(MOSLGTBuilder.class);

   /**
    * Specification of files whose changes will trigger in invocation of this builder
    */
   private static final String[] PROJECT_INTERNAL_TRIGGERS = new String[] { "src/**/*.mgt"};
   
   public MOSLGTBuilder()
   {
      super(new AntPatternCondition(PROJECT_INTERNAL_TRIGGERS));
   }

   @Override
   protected AntPatternCondition getTriggerCondition(final IProject project)
   {
      //TODO@rkluge: Copied from MOSLTGGBuilder
      try {
         if (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) ||
               project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID)) {
            return new AntPatternCondition(new String[] { "gen/**" });
         }
      } catch (final CoreException e) {
         // Do nothing
      }
      return new AntPatternCondition(new String[0]);
   }
   
   @Override
   protected void postprocess(RelevantElementCollector buildVisitor, int originalKind, Map<String, String> args, IProgressMonitor monitor)
   {
      final int kind = correctBuildTrigger(originalKind);
      
      if (getCommand().isBuilding(kind)) {
         super.postprocess(buildVisitor, kind, args, monitor);
      }
   }

   @Override
   protected void processResource(final IResource resource, final int kind, final Map<String, String> args, final IProgressMonitor monitor)
   {
      //TODO@rkluge: Steps of this builder (wrap them into something similar to the 
      /*
       * 1. Read input Ecore file
       * 2. Read CF model from .mgt files
       * 3. Connect .mgt files to EOperations
       * 4. Trigger code generation
       * 
       * Gather parts from 
       * * MoflonCodeGenerator
       * * RepositoryBuilder
       * * MoslTGGBuilder
       */
   }

}
