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
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.task.ITask;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.WorkspaceHelper;

public final class MonitoredGenModelBuilder implements ITask
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
      final SubMonitor subMon = SubMonitor.convert(monitor, TASK_NAME + " task", 100);
      IProject project = ecoreFile.getProject();
      subMon.subTask("Building or loading GenModel for project " + project.getName());
      subMon.worked(5);

      if (this.moflonProperties == null)
      {
         this.moflonProperties = MoflonPropertiesContainerHelper.load(project, subMon.newChild(5));

      }
      subMon.setWorkRemaining(90);

      if (subMon.isCanceled())
      {
         return Status.CANCEL_STATUS;
      }

      // Create EMFCodegen
      String basePackage = "";
      String modelFolder = WorkspaceHelper.GEN_FOLDER;
      String modelDirectory = project.getFolder(modelFolder).getFullPath().toString();

      MoflonGenModelBuilder genModelBuilder = new MoflonGenModelBuilder(resourceSet, resources, ecoreFile, basePackage, modelDirectory, moflonProperties);
      genModelBuilder.loadDefaultSettings();
      subMon.worked(10);
      if (subMon.isCanceled())
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
         // StatusManager.getManager().handle(errorStatus, StatusManager.SHOW | StatusManager.LOG);
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), e.getMessage(), e);
      }
      subMon.worked(30);
      if (subMon.isCanceled())
      {
         return Status.CANCEL_STATUS;
      }

      // Validate resource set
      IStatus resourceSetStatus = CodeGeneratorPlugin.validateResourceSet(resourceSet, "GenModel building", subMon.newChild(10));
      if (subMon.isCanceled())
      {
         return Status.CANCEL_STATUS;
      }
      if (!resourceSetStatus.isOK())
      {
         return resourceSetStatus;
      }

      // Validate GenModel
      IStatus genModelValidationStatus = genModel.validate();
      subMon.worked(30);
      if (subMon.isCanceled())
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
      subMon.worked(10);
      return new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), TASK_NAME + " succeeded");
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
