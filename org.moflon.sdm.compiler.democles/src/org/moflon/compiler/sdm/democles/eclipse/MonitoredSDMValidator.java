package org.moflon.compiler.sdm.democles.eclipse;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.task.ITask;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.core.preferences.EMoflonPreferencesStorage;
import org.moflon.core.propertycontainer.MetaModelProject;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.utilities.ErrorReporter;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;

public class MonitoredSDMValidator implements ITask {
	private static final Logger logger = Logger.getLogger(MonitoredSDMValidator.class);

	private static final String TASK_NAME = "SDM validation";

	private final IFile ecoreFile;

	private final EMoflonPreferencesStorage preferencesStorage;

	public MonitoredSDMValidator(final IFile ecoreFile, EMoflonPreferencesStorage preferencesStorage) {
		this.ecoreFile = ecoreFile;
		this.preferencesStorage = preferencesStorage;
	}

	@Override
	public final String getTaskName() {
		return TASK_NAME + " on project " + ecoreFile.getProject().getName();
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) {
		try {
			final SubMonitor subMon = SubMonitor.convert(monitor, TASK_NAME + " task", 100);
			logger.info("Validating Ecore file '" + ecoreFile.getProjectRelativePath() + "'.");

			// Delete markers
			ecoreFile.deleteMarkers(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID, true, IResource.DEPTH_INFINITE);
			final IProject project = ecoreFile.getProject();

			ResourceSet resourceSet = eMoflonEMFUtil.createDefaultResourceSet();
			DemoclesMethodBodyHandler.initResourceSetForDemocles(resourceSet);

			subMon.subTask("Validating SDMs for project " + project.getName());
			DemoclesValidationProcess validationProcess = createValidationProcess(resourceSet);
			IStatus validationStatus = validationProcess.run(subMon.split(50));

			if (!validationStatus.isOK()) {
				handleErrorsInEnterpriseArchitect(validationStatus, validationProcess.getMoflonProperties());
				subMon.worked(25);

				handleErrorsInEclipse(validationStatus);
				subMon.worked(25);
			}

			logger.info("Validation of Ecore file '" + ecoreFile.getProjectRelativePath() + "' complete.");

			return validationStatus.isOK()
					? new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), TASK_NAME + " succeeded")
					: validationStatus;
		} catch (RuntimeException e) {
			return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), IStatus.ERROR,
					"Internal exception occured (probably caused by a bug in the validation module): " + e.getMessage()
							+ " Please report the bug on " + WorkspaceHelper.ISSUE_TRACKER_URL,
					e);
		} catch (CoreException e) {
			return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), IStatus.ERROR, e.getMessage(), e);
		}
	}

	protected DemoclesValidationProcess createValidationProcess(ResourceSet resourceSet) {
		return new DemoclesValidationProcess(ecoreFile, resourceSet, this.getPreferencesStorage());
	}

	protected IFile getEcoreFile() {
		return ecoreFile;
	}

	public void handleErrorsInEnterpriseArchitect(final IStatus validationStatus,
			final MoflonPropertiesContainer moflonProperties) {
		final MetaModelProject metaModelProjectProperty = moflonProperties.getMetaModelProject();
		if (metaModelProjectProperty == null || metaModelProjectProperty.getMetaModelProjectName() == null) {
			logger.warn("Cannot send validation messages to EA because 'MetaModelProjectName' property is missing.");
		}

		IProject metamodelProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(metaModelProjectProperty.getMetaModelProjectName());
		ErrorReporter errorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(metamodelProject,
				"org.moflon.validation.EnterpriseArchitectValidationHelper");
		if (errorReporter != null) {
			errorReporter.report(validationStatus);
		}
	}

	public void handleErrorsInEclipse(final IStatus validationStatus) {
		ErrorReporter eclipseErrorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(ecoreFile,
				"org.moflon.compiler.sdm.democles.eclipse.EclipseErrorReporter");
		if (eclipseErrorReporter != null) {
			eclipseErrorReporter.report(validationStatus);
		}
	}

	protected EMoflonPreferencesStorage getPreferencesStorage() {
		return this.preferencesStorage;
	}

}
