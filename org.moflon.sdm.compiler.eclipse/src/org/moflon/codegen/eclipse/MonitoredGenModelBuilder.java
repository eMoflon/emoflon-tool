package org.moflon.codegen.eclipse;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ui.statushandlers.StatusManager;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.eclipse.job.IMonitoredJob;
import org.moflon.properties.MoflonPropertiesContainerHelper;

import MoflonPropertyContainer.MoflonPropertiesContainer;

public final class MonitoredGenModelBuilder implements IMonitoredJob
{
   private static final String TASK_NAME = "GenModel building";

   private final ResourceSet resourceSet;

   private final IFile ecoreFile;

   private final List<Resource> resources;

   private final boolean saveGenModel;

   private MoflonPropertiesContainer moflonProperties;

   private GenModel genModel;

   public MonitoredGenModelBuilder(final ResourceSet resourceSet, final List<Resource> resources, final IFile ecoreFile, final boolean saveGenModel,
         final MoflonPropertiesContainer properties)
   {
      this.resourceSet = resourceSet;
      this.ecoreFile = ecoreFile;
      this.resources = resources;
      this.saveGenModel = saveGenModel;
      this.moflonProperties = properties;
   }

   @Override
   public final IStatus run(final IProgressMonitor monitor)
   {
      try
      {
         monitor.beginTask(TASK_NAME + " task", 100);
         IProject project = ecoreFile.getProject();
         monitor.subTask("Building or loading GenModel for project " + project.getName());
         monitor.worked(5);

         if (this.moflonProperties == null)
         {
            this.moflonProperties = MoflonPropertiesContainerHelper.load(project, WorkspaceHelper.createSubMonitor(monitor, 5));

         } else
         {
            monitor.worked(5);
         }

         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Create EMFCodegen
         String basePackage = "";
         String modelFolder = WorkspaceHelper.GEN_FOLDER;
         String modelDirectory = project.getFolder(modelFolder).getFullPath().toString();

         MoflonGenModelBuilder genModelBuilder = new MoflonGenModelBuilder(resourceSet, resources, ecoreFile, basePackage, modelDirectory, moflonProperties);
         genModelBuilder.loadDefaultSettings();
         monitor.worked(10);
         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         URI projectURI = URI.createPlatformResourceURI(project.getName() + "/", true);
         URI ecoreURI = URI.createURI(ecoreFile.getProjectRelativePath().toString()).resolve(projectURI);
         URI genModelURI = MoflonGenModelBuilder.calculateGenModelURI(ecoreURI);
         final boolean isNewGenModelConstructed = genModelBuilder.isNewGenModelRequired(genModelURI);
         try
         {
            this.genModel = genModelBuilder.buildGenModel(genModelURI);
         } catch (RuntimeException e)
         {
            IStatus errorStatus = new Status(IStatus.ERROR, "SDMCompiler", IStatus.ERROR, e.getMessage(), e);
            StatusManager.getManager().handle(errorStatus, StatusManager.SHOW | StatusManager.LOG);
            return errorStatus;
         }
         monitor.worked(30);
         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Validate resource set
         IStatus resourceSetStatus = CodeGeneratorPlugin.validateResourceSet(resourceSet, "GenModel building", WorkspaceHelper.createSubMonitor(monitor, 10));
         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (!resourceSetStatus.isOK())
         {
            return resourceSetStatus;
         }

         // Validate GenModel
         IStatus genModelValidationStatus = genModel.validate();
         monitor.worked(30);
         if (monitor.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (!genModelValidationStatus.isOK())
         {
            return genModelValidationStatus;
         }

         // Save GenModel
         if (saveGenModel)
         {
            try
            {
               Resource genModelResource = genModel.eResource();
               if (isNewGenModelConstructed)
               {
                  // Save to file (with no options)
                  genModelResource.save(Collections.EMPTY_MAP);
               } else
               {
                  // Save to file (if modified)
                  Map<String, Object> saveOptions = new HashMap<String, Object>();
                  saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
                  genModelResource.save(saveOptions);
               }
            } catch (IOException e)
            {
               return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR, e.getMessage(), e);
            }
         }
         monitor.worked(10);
         return new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), TASK_NAME + " succeeded");
      } finally
      {
         monitor.done();
      }
   }

   public final GenModel getGenModel()
   {
      return genModel;
   }

   @Override
   public final String getTaskName()
   {
      return TASK_NAME;
   }
}
