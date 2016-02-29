package org.moflon.ide.metamodelevolution.core;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.moflon.core.utilities.eMoflonEMFUtil;
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
         if (changesTree == null)
         {
            return new Status(IStatus.OK, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "No Changes detected");
         }

         MetamodelChangeCalculator changeCalculator = new ChangesTreeCalculator();
         ChangeSequence delta = changeCalculator.parseTree(changesTree);

         final Map<String, MetamodelProperties> projectPropertiesMap = preMetamodelBuilderHookDTO.extractRepositoryProjectProperties();

         if (!delta.getEModelElementChange().isEmpty())
         {
            for (EModelElementChange change : delta.getEModelElementChange())
            {
               final MetamodelProperties properties = projectPropertiesMap.get(change.getProjectName());
               if (properties != null && properties.isRepositoryProject())
               {
                  final IProject repositoryProject = properties.getProject();
                  final GenModel genModel = eMoflonEMFUtil.extractGenModelFromProject(repositoryProject);
                  final MetamodelDeltaProcessor processor = new RenameProjectProcessor(genModel);
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
         return new Status(IStatus.ERROR, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "Problem in PreMetamodelBuilderHook during refactoring", e);
      }

   }
}
