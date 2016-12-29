package org.moflon.gt.ide;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.compiler.sdm.democles.eclipse.AdapterResource;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

/**
 * This task weaves the control flow and pattern matching models stored in textual syntax with the plain metamodel
 * 
 * The task is configured with the top-level {@link EPackage} of the metamodel.
 * 
 * @author Roland Kluge - Initial implementation
 * 
 * @see #run(IProgressMonitor)
 */
public class MOSLGTWeavingTask implements ITask
{
   /**
    * The top-level {@link EPackage} of the ongoing build process
    */
   private EPackage ePackage;

   /**
    * The common {@link ResourceSet} of the ongoing build process
    */
   private ResourceSet resourceSet;

   /**
    * Preconfigures this task with the top-level {@link EPackage} of the metamodel to be processed
    * @param ePackage
    */
   public MOSLGTWeavingTask(final EPackage ePackage)
   {
      this.ePackage = ePackage;
      this.resourceSet = ePackage.eResource().getResourceSet();
   }

   /**
    * Entry point of this task
    * 
    * It iterates through all {@link EClass}es in the metamodel and invokes {@link #weaveEClass(EClass, MultiStatus, IProgressMonitor)} for each one.
    * 
    * @param monitor the progress monitor
    * @return the status of the entire task
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
         final IStatus cancelStatus = weaveEClass(eClass, weavingMultiStatus, subMon.split(1));
         if (cancelStatus.getSeverity() == Status.CANCEL)
            return cancelStatus;
      }

      return weavingMultiStatus.isOK() ? Status.OK_STATUS : weavingMultiStatus;
   }

   /**
    * Performs the weaving for all {@link EOperation}s in the given {@link EClass} eClass.
    * 
    * @param eClass the {@link EClass} to process
    * @param weavingMultiStatus the {@link MultiStatus} for storing all problems
    * @param monitor the progress monitor
    * @return the status used to indicate cancellation via {@link IStatus#CANCEL}
    */
   private IStatus weaveEClass(final EClass eClass, final MultiStatus weavingMultiStatus, final IProgressMonitor monitor)
   {
      final List<EOperation> eOperations = eClass.getEOperations();
      final SubMonitor subMon = SubMonitor.convert(monitor, "Weaving class " + eClass.getName(), eOperations.size());
      for (final EOperation eOperation : eOperations)
      {
         final IStatus cancelStatus = weaveEOperation(eOperation, weavingMultiStatus, subMon.split(1));
         if (cancelStatus.getSeverity() == IStatus.CANCEL)
            return cancelStatus;
      }
      return Status.OK_STATUS;
   }

   /**
    * Weaves a single {@link EOperation} eOperation
    * 
    * @param eOperation the {@link EOperation} to process
    * @param weavingMultiStatus the {@link MultiStatus} for storing all problems
    * @param monitor the progress monitor
    * @return the status used to indicate cancellation via {@link IStatus#CANCEL}
    */
   private IStatus weaveEOperation(final EOperation eOperation, final MultiStatus weavingMultiStatus, final IProgressMonitor monitor)
   {
      //TODO@rkluge: 
      /*
       * Steps:
       * 1. Create CF model (a la MOSLTGGConversionHelper) from MGT files in this project
       *    * We should load the MGT files only once -> Use EcoreUtil adapter mechanism
       *    * We have to ensure that the transformed patterns are stored in the correct way... This will be pretty tough, I guess.
       * 2. That's it.
       */
      if (monitor.isCanceled())
         return Status.CANCEL_STATUS;

      final SubMonitor subMon = SubMonitor.convert(monitor, "Weaving EOperation " + eOperation.getName(), 1);

      // Code snippet from DemoclesValidationTask: There, each eOperation has its own CF model
      final AdapterResource controlFlowResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
            DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
      if (controlFlowResource.getResourceSet() == null)
      {
         resourceSet.getResources().add(controlFlowResource);
      }

      final Scope scope = DemoclesFactory.eINSTANCE.createScope();
      final CFVariable thisObject = DemoclesFactory.eINSTANCE.createCFVariable();
      scope.getVariables().add(thisObject);
      thisObject.setName("this");
      thisObject.setType(eOperation.getEContainingClass());
      thisObject.setLocal(false);
      for (final EParameter eParameter : eOperation.getEParameters())
      {
         final CFVariable parameter = DemoclesFactory.eINSTANCE.createCFVariable();
         scope.getVariables().add(parameter);
         parameter.setName(eParameter.getName());
         parameter.setType(eParameter.getEType());
         parameter.setLocal(false);
      }
      subMon.worked(1);

      return Status.OK_STATUS;
   }

   @Override
   public String getTaskName()
   {
      return "MOSL-GT Weaving";
   }

}
