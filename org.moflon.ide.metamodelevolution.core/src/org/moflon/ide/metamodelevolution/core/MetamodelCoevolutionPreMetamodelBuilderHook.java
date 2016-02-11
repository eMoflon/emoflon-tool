package org.moflon.ide.metamodelevolution.core;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.ide.core.runtime.builders.hooks.PreMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PreMetamodelBuilderHookDTO;
import org.moflon.ide.metamodelevolution.core.changes.ChangesTreeCalculator;
import org.moflon.ide.metamodelevolution.core.changes.MetamodelChangeCalculator;
import org.moflon.ide.metamodelevolution.core.processing.MetamodelDeltaProcessor;
import org.moflon.ide.metamodelevolution.core.processing.RenameProjectProcessor;

import MocaTree.Node;

public class MetamodelCoevolutionPreMetamodelBuilderHook implements PreMetamodelBuilderHook
{
   private static final Logger logger = Logger.getLogger(MetamodelCoevolutionPreMetamodelBuilderHook.class);

   @Override
   public IStatus run(final PreMetamodelBuilderHookDTO preMetamodelBuilderHookDTO)
   {
      logger.debug("Performing pre-build step for meta-model co-evolution support");

      Node mocaTree = MetamodelCoevolutionHelper.getMocaTree(preMetamodelBuilderHookDTO.metamodelproject);
      Node changesTree = (Node) mocaTree.getChildren().get(0);
      IProject repositoryProject = MetamodelCoevolutionHelper.getRepositoryProject(changesTree, preMetamodelBuilderHookDTO);  
      
      if (repositoryProject != null)
      {
         MetamodelChangeCalculator changeCalculator = new ChangesTreeCalculator();
         ChangeSequence delta = changeCalculator.parseTree(mocaTree);
         
         if (delta.getEModelElementChange().size() > 0) // did we find any changes?
         {
             MetamodelDeltaProcessor processor = new RenameProjectProcessor();
             processor.processDelta(repositoryProject, delta);
         }        
      }
      
      return Status.OK_STATUS;
   }
}
