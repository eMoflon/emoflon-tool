package org.moflon.gt.ui.handlers;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.task.ITask;
import org.moflon.compiler.sdm.democles.DefaultValidatorConfig;
import org.moflon.compiler.sdm.democles.ScopeValidationConfigurator;
import org.moflon.compiler.sdm.democles.eclipse.DemoclesValidatorTask;
import org.moflon.core.build.GenericMoflonProcess;
import org.moflon.core.preferences.EMoflonPreferencesActivator;
import org.moflon.core.preferences.EMoflonPreferencesStorage;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.ide.natures.EMoflonGTNature;
import org.moflon.ide.core.runtime.natures.RepositoryNature;

/**
 * This process conducts the conversion from an SDM-based project to a MOSL-GT-based project
 * @author Roland Kluge - Initial implementation
 *
 */
public class ConvertToMoslGtProcess extends GenericMoflonProcess
{
   private static final Logger logger = Logger.getLogger(ConvertToMoslGtProcess.class);

   public ConvertToMoslGtProcess(final IFile ecoreFile, final ResourceSet resourceSet, EMoflonPreferencesStorage preferencesStorage)
   {
      super(ecoreFile, resourceSet, preferencesStorage);
   }

   @Override
   public String getTaskName()
   {
      return "Convert " + getEcoreFile() + " to MOSL-GT.";
   }

   @Override
   public IStatus processResource(final IProgressMonitor monitor)
   {
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Convert to MOSL-GT", 15);
         final Resource resource = getEcoreResource();
         final EPackage ePackage = (EPackage) resource.getContents().get(0);

         final ScopeValidationConfigurator validatorConfig = createValidationConfiguration();
         subMon.worked(5);

         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // Build up control flow model
         final ITask validator = new DemoclesValidatorTask(validatorConfig.createScopeValidator(), ePackage);
         final IStatus validatorStatus = validator.run(subMon.split(10));
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (!validatorStatus.isOK())
         {
            return validatorStatus;
         }

         // Perform transformation
         final Resource mgtResource = createMoslGtResource();
         final IStatus transformationStatus = transformToMoslGt(mgtResource, getEcoreFile());
         if (transformationStatus.matches(IStatus.ERROR))
            return transformationStatus;

         mgtResource.save(null);

         // Transform project 'type' from Repository to MOSL-GT
         final IProjectDescription description = getProject().getDescription();
         final ICommand[] buildSpecs = description.getBuildSpec();
         final String[] natureIds = description.getNatureIds();
         final RepositoryNature repositoryNature = new RepositoryNature();
         final EMoflonGTNature moslGtNature = new EMoflonGTNature();
         repositoryNature.updateBuildSpecs(description, buildSpecs, false);
         repositoryNature.updateNatureIDs(natureIds, false);
         moslGtNature.updateBuildSpecs(description, buildSpecs, true);
         moslGtNature.updateNatureIDs(natureIds, true);

         return Status.OK_STATUS;
      } catch (final Exception e)
      {
         logger.debug(WorkspaceHelper.printStacktraceToString(e));
         return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), IStatus.ERROR, String.format(
               "%s occured during task '%s'. Message: '%s'. Stacktrace is logged with debug level.", e.getClass().getName(), getTaskName(), e.getMessage()), e);
      }
   }

   private ScopeValidationConfigurator createValidationConfiguration()
   {
      final String engineID = MoflonPropertiesContainerHelper.getMethodBodyHandler(getMoflonProperties());
      ScopeValidationConfigurator validatorConfig = (ScopeValidationConfigurator) Platform.getAdapterManager().loadAdapter(this, engineID);

      if (validatorConfig == null)
      {
         validatorConfig = new DefaultValidatorConfig(getResourceSet(), EMoflonPreferencesActivator.getDefault().getPreferencesStorage());
      }
      return validatorConfig;
   }

   /**
    * Creates the URI of the resource that
    *
    * Default URI:
    * platform://resource/[project name]/src/org/moflon/gt/[project name].mgt
    *
    * @return the resource that holds the resulting {@link GraphTransformationFile}
    */
   private Resource createMoslGtResource()
   {
      return getResourceSet()
            .createResource(URI.createPlatformResourceURI(String.format("%s/src/org/moflon/gt/%s.mgt", getProjectName(), getMgtBasename()), true));
   }

   /**
    * Returns the basename of the to-be-created MOSL-GT file
    */
   private String getMgtBasename()
   {
      final String[] splitProjectName = getProjectName().split(Pattern.quote("."));
      return splitProjectName[splitProjectName.length - 1];
   }

   /**
    * Determines the name of the current project
    * @return
    */
   private String getProjectName()
   {
      return getEcoreFile().getProject().getName();
   }

   /**
    * Extracts the {@link GraphTransformationFile} for the SDM specification of the current project
    * @param mgtResource the resource that will contain a consistent {@link GraphTransformationFile} if the operation is successful
    * @param ecoreFile the ECore file that serves as source of the transformation
    * @return the status of this operation
    */
   private IStatus transformToMoslGt(final Resource mgtResource, final IFile ecoreFile)
   {
      //TODO@rkluge: Implement me
      //      final GraphTransformationFile mgtFile = MoslgtFactory.eINSTANCE.createGraphTransformationFile();
      //      mgtResource.getContents().add(mgtFile);
      //      mgtFile.setName(getMgtBasename() + "." + WorkspaceHelper.EMOFLON_GT_EXTENSION);
      //      mgtFile.getEClasses().add(MoslgtFactory.eINSTANCE.createEClassDef());

      //      return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "SDM-to-MOSL-GT transformation not implemented yet.");
      return Status.OK_STATUS;
   }

}
