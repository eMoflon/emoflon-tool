package org.moflon.ide.metamodelevolution.core;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHookDTO;
import org.moflon.ide.metamodelevolution.core.changes.ChangesTreeCalculator;
import org.moflon.ide.metamodelevolution.core.changes.MetamodelChangeCalculator;
import org.moflon.ide.metamodelevolution.core.processing.JavaRefactorProcessor;
import org.moflon.ide.metamodelevolution.core.processing.MetamodelDeltaProcessor;
import org.moflon.util.plugins.MetamodelProperties;

import MocaTree.Node;

public class MetamodelCoevolutionPostMetamodelBuilderHook implements PostMetamodelBuilderHook
{
   private static final Logger logger = Logger.getLogger(MetamodelCoevolutionPostMetamodelBuilderHook.class);

   @Override
   public IStatus run(final PostMetamodelBuilderHookDTO postMetamodelBuilderHookDTO)
   {
      IStatus status = Status.OK_STATUS;
      try
      {
         logger.debug("Performing post-build step for meta-model co-evolution support");

         Node changesTree = MetamodelCoevolutionHelper.getMocaTree(postMetamodelBuilderHookDTO.metamodelproject);
         if (changesTree == null)
         {
            return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "No Changes detected");
         }

         MetamodelChangeCalculator changeCalculator = new ChangesTreeCalculator();
         ChangeSequence delta = changeCalculator.parseTree(changesTree);

         final Map<String, MetamodelProperties> projectPropertiesMap = postMetamodelBuilderHookDTO.extractRepositoryProjectProperties();

         if (delta.getEModelElementChange().size() > 0) // did we find any changes?
         {
            for (EModelElementChange change : delta.getEModelElementChange())
            {
               final MetamodelProperties properties = projectPropertiesMap.get(change.getProjectName());
               if (properties != null && properties.isRepositoryProject())
               {
                  final IProject repositoryProject = properties.getProject();
                  final GenModel genModel = eMoflonEMFUtil.extractGenModelFromProject(repositoryProject);
                  final MetamodelDeltaProcessor processor = new JavaRefactorProcessor(genModel);
                  status = processor.processDelta(repositoryProject, delta);
               } else
               {
                  // Integration projects are currently not supported
               }
            }            
            if (status.isOK())
            {
            // delete changes tree if changes have been successfully processed
               IFile changesTreeFile = WorkspaceHelper.getChangesMocaTree(postMetamodelBuilderHookDTO.metamodelproject);
               if (changesTreeFile.exists())
               {
                  changesTreeFile.delete(false, new NullProgressMonitor());
               }
            }
         }
      } catch (Exception e)
      {
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Problem in PostMetamodelBuilderHook during refactoring", e);
      }

      return Status.OK_STATUS;
   }
}
