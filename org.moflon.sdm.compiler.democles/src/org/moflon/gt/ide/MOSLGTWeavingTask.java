package org.moflon.gt.ide;

import java.util.List;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.compiler.sdm.democles.eclipse.AdapterResource;

//TODO@rkluge
/*
 * Development notes:
 * * This class is inspired by the DemoclesValidationTask
 * * For the configured EPackage, we traverse all EClasses and their EOperations
 * * For each EOperation, we extract the control flow 
 */

/**
 * 
 * @author Roland Kluge - Initial implementation
 */
public class MOSLGTWeavingTask implements ITask
{
   private EPackage ePackage;

   private ResourceSet resourceSet;

   public MOSLGTWeavingTask(final EPackage ePackage)
   {
      this.ePackage = ePackage;
      this.resourceSet = ePackage.eResource().getResourceSet();
   }

   /**
    * Entry point of this {@link WorkspaceJob}
    */
   @Override
   public IStatus run(IProgressMonitor monitor)
   {
      // This status collects all information about the weaving process for 'ePackage'
      final MultiStatus weavingMultiStatus = new MultiStatus(CodeGeneratorPlugin.getModuleID(), 0, getTaskName() + " failed", null);
      final List<EClass> eClasses = CodeGeneratorPlugin.getEClasses(this.ePackage);
      final SubMonitor subMon = SubMonitor.convert(monitor, getTaskName() + " in " + ePackage.getName(), eClasses.size());
      for (final EClass eClass : eClasses)
      {
         IStatus cancelStatus = weaveEClass(eClass, weavingMultiStatus, subMon.split(1));
         if (cancelStatus.getSeverity() == Status.CANCEL)
            return cancelStatus;
      }

      return weavingMultiStatus.isOK() ? new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), getTaskName() + " succeeded") : weavingMultiStatus;
   }

   private IStatus weaveEClass(final EClass eClass, final MultiStatus weavingMultiStatus, final IProgressMonitor monitor)
   {
      final List<EOperation> eOperations = eClass.getEOperations();
      final SubMonitor subMon = SubMonitor.convert(monitor, "Validating operations in class " + eClass.getName(), eOperations.size());
      for (final EOperation eOperation : eOperations)
      {
         final IStatus cancelStatus = weaveEOperation(eOperation, weavingMultiStatus, subMon.split(1));
         if (cancelStatus.getSeverity() == IStatus.CANCEL)
            return cancelStatus;
      }
      return Status.OK_STATUS;
   }

   private IStatus weaveEOperation(final EOperation eOperation, final MultiStatus weavingMultiStatus, final SubMonitor split)
   {
      //TODO@rkluge: 
      /*
       * Steps:
       * 1. Create CF model (a la MOSLTGGConversionHelper) from MGT files in this project
       *    * We should load the MGT files only once -> Use EcoreUtil adapter mechanism
       *    * We have to ensure that the transformed patterns are stored in the correct way... This will be pretty tough, I guess.
       * 2. That's it.
       */

      if ("deleteNode".equals(eOperation.getName()))
      {
         // Code snippet from DemoclesValidationTask: There, each eOperation has its own CF model
         final AdapterResource controlFlowResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
               DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
         if (controlFlowResource.getResourceSet() == null)
         {
            resourceSet.getResources().add(controlFlowResource);
         }
      }
      return null;
   }

   @Override
   public String getTaskName()
   {
      return "MOSL-GT Weaving of control flow and pattern matching models";
   }

}
