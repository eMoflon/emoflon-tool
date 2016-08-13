package org.moflon.compiler.sdm.democles.eclipse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.GenericMoflonProcess;
import org.moflon.compiler.sdm.democles.DefaultValidatorConfig;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.compiler.sdm.democles.ScopeValidationConfigurator;
import org.moflon.core.utilities.LogUtils;
import org.moflon.eclipse.job.IMonitoredJob;

public class DemoclesValidationProcess extends GenericMoflonProcess
{
   private static final Logger logger = Logger.getLogger(DemoclesValidationProcess.class);

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

         final IMonitoredJob validator = new DemoclesValidatorTask(validatorConfig.createScopeValidator(), ePackage);
         final IStatus validatorStatus = validator.run(subMon.split(10));
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (!validatorStatus.isOK())
         {
            return validatorStatus;
         }

         //TODO@rkluge: Move to separate class
         boolean outputIntermediateModels = false;
         if (outputIntermediateModels)
         {

            for (final EClass eClass : CodeGeneratorPlugin.getEClasses(ePackage))
            {

               for (final EOperation eOperation : eClass.getEOperations())
               {
                  final AdapterResource controlFlowResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
                        DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
                  AdapterResource sdmFileResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation, DemoclesMethodBodyHandler.SDM_FILE_EXTENSION);
                  AdapterResource dfsFileResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation, DemoclesMethodBodyHandler.DFS_FILE_EXTENSION);

                  for (AdapterResource adapterResource : Arrays.asList(controlFlowResource, dfsFileResource, sdmFileResource))
                  {
                     saveResource(adapterResource);
                  }
               }

               AdapterResource bindingAndBlackResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass,
                     DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION);
               AdapterResource redResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass, DemoclesMethodBodyHandler.RED_FILE_EXTENSION);
               AdapterResource greenResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass, DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION);
               AdapterResource bindingFileResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass, DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION);
               AdapterResource blackFileResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass, DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION);
               AdapterResource expressionResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass,
                     DemoclesMethodBodyHandler.EXPRESSION_FILE_EXTENSION);

               for (AdapterResource adapterResource : Arrays.asList(bindingAndBlackResource, blackFileResource, redResource, greenResource, bindingFileResource,
                     blackFileResource, expressionResource))
               {
                  saveResource(adapterResource);
               }
            }
         }

         return new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), "Validation succeeded");
      } catch (final Exception e)
      {
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR, e.getMessage(), e);
      }
   }

   private void saveResource(AdapterResource adapterResource)
   {
      if (adapterResource != null)
      {
         final URI oldUri = adapterResource.getURI();
         try
         {
            adapterResource.setURI(oldUri.appendFileExtension("xmi"));
            adapterResource.save(new HashMap<>());
            
            // Save with old URI to allow for navigation between models
            adapterResource.setURI(oldUri);
            adapterResource.save(new HashMap<>());
         } catch (IOException e)
         {
            LogUtils.error(logger, e);
         } finally
         {
            adapterResource.setURI(oldUri);
         }
      }
   }
}
