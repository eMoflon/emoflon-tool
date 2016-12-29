package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.moflon.core.mocatomoflon.MocaToMoflonUtils;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.EModelElementChange;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;
import org.moflon.ide.metamodelevolution.core.processing.refactoring.RenameGenmodelRefactoring;
import org.moflon.ide.metamodelevolution.core.processing.refactoring.RenameProjectRefactoring;
import org.moflon.ide.metamodelevolution.core.processing.refactoring.RenameRefactoring;
import org.moflon.ide.metamodelevolution.core.processing.refactoring.SequenceRefactoring;

public class RenameProjectProcessor extends MetamodelDeltaProcessor_ImplBase
{

   public RenameProjectProcessor(GenModel genModel)
   {
      super(genModel);
   }

   @Override
   public IStatus processDelta(IProject project, ChangeSequence delta)
   {

      for (EModelElementChange change : delta.getEModelElementChange())
      {
         if (change instanceof RenameChangeImpl)
         {
            RenameChange renaming = (RenameChange) change;
            if (renaming.getElementType().equals(MocaToMoflonUtils.ROOTPACKAGE_NODE_NAME) && renaming.arePreviousAndCurrentValueDifferent())
            {
               IProject oldProject = getProject(((RenameChangeImpl) change).getPreviousValue());

               // rename .genmodel file
               RenameRefactoring genmodelProcessor = new RenameGenmodelRefactoring(getLastPackageComponent(renaming.getCurrentValue()));
               genmodelProcessor.refactor(oldProject);

               // delete old .ecore file
               IFile ecoreFile = oldProject.getFile(MoflonUtil.getDefaultPathToEcoreFileInProject(oldProject.getName()));
               try
               {
                  if (ecoreFile.exists())
                  {
                     ecoreFile.delete(false, new NullProgressMonitor());
                  }
               } catch (CoreException e)
               {
                  LogUtils.error(logger, e);
               }

               // rename project
               RenameRefactoring renameProjectRefactoring = new RenameProjectRefactoring(((RenameChangeImpl) change).getCurrentValue());
               renameProjectRefactoring.refactor(oldProject);
               
               // rename generated packages
               new SequenceRefactoring(processFields(renaming)).createPackageRefactorings(project, "Impl");
            }
         }
      }
      return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "Project refactoring done");
   }

   private IProject getProject(String projectName)
   {
      return WorkspaceHelper.getProjectByName(projectName);
   }

   private String getLastPackageComponent(String packageName)
   {
      return packageName.substring(packageName.lastIndexOf(".") + 1);
   }
   
   private RenameChange processFields(RenameChange change)
   {
      String previousValue = getLastPackageComponent(change.getPreviousValue());      
      change.setPreviousValue(previousValue);
      
      String currentValue = getLastPackageComponent(change.getCurrentValue());      
      change.setCurrentValue(currentValue);
      return change;      
   }
}
