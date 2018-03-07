package org.moflon.compiler.sdm.democles.eclipse;

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
import org.moflon.compiler.sdm.democles.DefaultValidatorConfig;
import org.moflon.compiler.sdm.democles.ScopeValidationConfigurator;
import org.moflon.core.preferences.EMoflonPreferencesStorage;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.emf.build.GenericMoflonProcess;

public class DemoclesValidationProcess extends GenericMoflonProcess {
	private final boolean shallSaveIntermediateModels;

	public DemoclesValidationProcess(final IFile ecoreFile, final ResourceSet resourceSet,
			final EMoflonPreferencesStorage preferencesStorage) {
		this(ecoreFile, resourceSet, false, preferencesStorage);
	}

	public DemoclesValidationProcess(IFile ecoreFile, ResourceSet resourceSet, boolean shallSaveIntermediateModels,
			EMoflonPreferencesStorage preferencesStorage) {
		super(ecoreFile, resourceSet, preferencesStorage);
		this.shallSaveIntermediateModels = shallSaveIntermediateModels;
	}

	@Override
	public String getTaskName() {
		return "Validating SDMs";
	}

	@Override
	public IStatus processResource(final IProgressMonitor monitor) {
		try {
			final SubMonitor subMon = SubMonitor.convert(monitor, "Validation task", 15);
			final Resource resource = getEcoreResource();
			final EPackage ePackage = (EPackage) resource.getContents().get(0);

			final String engineID = MoflonPropertiesContainerHelper.getMethodBodyHandler(getMoflonProperties());
			ScopeValidationConfigurator validatorConfig = (ScopeValidationConfigurator) Platform.getAdapterManager()
					.loadAdapter(this, engineID);

			if (validatorConfig == null) {
				validatorConfig = new DefaultValidatorConfig(getResourceSet(), this.getPreferencesStorage());
			}
			subMon.worked(5);

			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			final ITask validator = new DemoclesValidatorTask(validatorConfig.createScopeValidator(), ePackage);
			final IStatus validatorStatus = validator.run(subMon.split(10));
			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			if (!validatorStatus.isOK()) {
				return validatorStatus;
			}

			if (shallSaveIntermediateModels) {
				DemoclesValidationUtils.clearAllIntermediateModels(WorkspaceHelper.getModelFolder(this.getProject()));
				DemoclesValidationUtils.saveAllIntermediateModels(ePackage);
			}

			return Status.OK_STATUS;
		} catch (final Exception e) {
			return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), IStatus.ERROR, e.getMessage(), e);
		}
	}
}
