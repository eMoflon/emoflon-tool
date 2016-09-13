package org.moflon.compiler.sdm.democles.eclipse;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.GenericMoflonProcess;
import org.moflon.compiler.sdm.democles.DefaultValidatorConfig;
import org.moflon.compiler.sdm.democles.ScopeValidationConfigurator;

public class DemoclesValidationProcess extends GenericMoflonProcess
{
   static final Logger logger = Logger.getLogger(DemoclesValidationProcess.class);
   private boolean shallSaveIntermediateModels = false;

   public DemoclesValidationProcess(final IFile ecoreFile, final ResourceSet resourceSet)
   {
      super(ecoreFile, resourceSet);
   }

   @Override
   public String getTaskName()
   {
      return "Validating SDMs";
   }

   @Override
   public IStatus processResource(final IProgressMonitor monitor)
   {
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Code generation task", 15);
         final Resource resource = getEcoreResource();
         final EPackage ePackage = (EPackage) resource.getContents().get(0);

         final String engineID = CodeGeneratorPlugin.getMethodBodyHandler(getMoflonProperties());
         ScopeValidationConfigurator validatorConfig = (ScopeValidationConfigurator) Platform.getAdapterManager().loadAdapter(this, engineID);

         if (validatorConfig == null)
         {
            validatorConfig = new DefaultValidatorConfig(getResourceSet());
         }
         subMon.worked(5);

         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         final ITask validator = new DemoclesValidatorTask(validatorConfig.createScopeValidator(), ePackage);
         final IStatus validatorStatus = validator.run(subMon.newChild(10));
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (!validatorStatus.isOK())
         {
            return validatorStatus;
         }

         if (shallSaveIntermediateModels)
         {
            DemoclesValidationUtils.saveAllIntermediateModels(ePackage);
         }

         return new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), "Validation succeeded");
      } catch (final Exception e)
      {
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR, e.getMessage(), e);
      }
   }
}
