package org.moflon.ide.metamodelevolution.core;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHookDTO;
import org.moflon.ide.metamodelevolution.core.changes.ChangesTreeCalculator;
import org.moflon.ide.metamodelevolution.core.changes.MetamodelChangeCalculator;
import org.moflon.ide.metamodelevolution.core.processing.JavaRefactorProcessor;
import org.moflon.ide.metamodelevolution.core.processing.MetamodelDeltaProcessor;

import MocaTree.Node;

public class MetamodelCoevolutionPostMetamodelBuilderHook implements PostMetamodelBuilderHook
{
   private static final Logger logger = Logger.getLogger(MetamodelCoevolutionPostMetamodelBuilderHook.class);

   @Override
   public IStatus run(final PostMetamodelBuilderHookDTO postMetamodelBuilderHookDTO)
   {
      logger.debug("Performing post-build step for meta-model co-evolution support");

      Node mocaTree = MetamodelCoevolutionHelper.getMocaTree(postMetamodelBuilderHookDTO.metamodelproject);
      Node changesTree = (Node) mocaTree.getChildren().get(0);
      IProject repositoryProject = MetamodelCoevolutionHelper.getRepositoryProject(changesTree, postMetamodelBuilderHookDTO);     
           
      if (repositoryProject != null)
      {
         MetamodelChangeCalculator changeCalculator = new ChangesTreeCalculator();
         ChangeSequence delta = changeCalculator.parseTree(mocaTree);
         
         if (delta.getEModelElementChange().size() > 0) // did we find any changes?
         {
             MetamodelDeltaProcessor processor = new JavaRefactorProcessor();
             processor.processDelta(repositoryProject, delta);
         }        
      }
     
      return Status.OK_STATUS;
   }
}
