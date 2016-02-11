package org.moflon.ide.metamodelevolution.core;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.ide.core.runtime.builders.hooks.PreMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PreMetamodelBuilderHookDTO;
import org.moflon.ide.metamodelevolution.core.changes.ChangesTreeCalculator;
import org.moflon.ide.metamodelevolution.core.changes.MetamodelChangeCalculator;
import org.moflon.ide.metamodelevolution.core.processing.MetamodelDeltaProcessor;
import org.moflon.ide.metamodelevolution.core.processing.RenameProjectProcessor;
import org.moflon.util.plugins.MetamodelProperties;

import MocaTree.Node;

public class MetamodelCoevolutionPreMetamodelBuilderHook implements PreMetamodelBuilderHook
{
   private static final Logger logger = Logger.getLogger(MetamodelCoevolutionPreMetamodelBuilderHook.class);

   @Override
   public IStatus run(final PreMetamodelBuilderHookDTO preMetamodelBuilderHookDTO)
   {
      try
      {
         logger.debug("Performing pre-build step for meta-model co-evolution support");

         Node changesTree = MetamodelCoevolutionHelper.getMocaTree(preMetamodelBuilderHookDTO.metamodelproject);

         // TODO@settl: Check whether tree is there - do nothing if not

         MetamodelChangeCalculator changeCalculator = new ChangesTreeCalculator();
         ChangeSequence delta = changeCalculator.parseTree(changesTree);


         if (delta.getEModelElementChange().size() > 0) // did we find any changes?
         {
            final Map<String, MetamodelProperties> projectPropertiesMap = preMetamodelBuilderHookDTO.extractRepositoryProjectProperties();
            
            for (final String projectName : projectPropertiesMap.keySet())
            {
               final MetamodelProperties properties = projectPropertiesMap.get(projectName);
               
               if (properties.isRepositoryProject())
               {
                  final IProject repositoryProject = properties.getProject();
                  MetamodelDeltaProcessor processor = new RenameProjectProcessor();
                  processor.processDelta(repositoryProject, delta);
               } else
               {
                  // Integration projects are currently not supported
               }
            }
         }

         return Status.OK_STATUS;
      } catch (final CoreException e)
      {
         return new Status(IStatus.ERROR, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "Problem why running pre builder hook", e);
      }

   }
}
