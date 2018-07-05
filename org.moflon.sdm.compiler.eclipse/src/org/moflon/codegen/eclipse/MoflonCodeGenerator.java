package org.moflon.codegen.eclipse;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory.Descriptor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.MethodBodyHandler;
import org.moflon.core.preferences.EMoflonPreferencesStorage;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.emf.build.GenericMoflonProcess;
import org.moflon.emf.build.MonitoredGenModelBuilder;
import org.moflon.emf.codegen.CodeGenerator;
import org.moflon.emf.injection.build.CodeInjectorImpl;
import org.moflon.emf.injection.build.XTextInjectionExtractor;
import org.moflon.emf.injection.ide.CodeInjector;
import org.moflon.emf.injection.ide.InjectionExtractor;
import org.moflon.emf.injection.ide.InjectionManager;
import org.moflon.sdm.injection.CompilerInjectionExtractorImpl;

public class MoflonCodeGenerator extends GenericMoflonProcess {
	private static final Logger logger = Logger.getLogger(MoflonCodeGenerator.class);

	private InjectionManager injectionManager;

	private GenModel genModel;

	private MoflonCodeGeneratorPhase additionalCodeGenerationPhase;

	public MoflonCodeGenerator(final IFile ecoreFile, final ResourceSet resourceSet,
			final EMoflonPreferencesStorage preferencesStorage) {
		super(ecoreFile, resourceSet, preferencesStorage);
	}

	@Override
	public String getTaskName() {
		return "Generating code";
	}

	@Override
	public IStatus processResource(final IProgressMonitor monitor) {
		try {
			final int totalWork = 5 + 10 + 10 + 15 + 35 + 30 + 5;
			final MoflonPropertiesContainer moflonProperties = getMoflonProperties();
			final SubMonitor subMon = SubMonitor.convert(monitor,
					"Code generation task for " + moflonProperties.getProjectName(), totalWork);
			final String fullProjectName = getFullProjectName(moflonProperties);
			logger.info("Generating code for: " + fullProjectName);

			long toc = System.nanoTime();

			final Resource resource = getEcoreResource();
			final EPackage ePackage = (EPackage) resource.getContents().get(0);

			// (1) Instantiate code generation engine
			final String engineID = MoflonPropertiesContainerHelper.getMethodBodyHandler(getMoflonProperties());
			final MethodBodyHandler methodBodyHandler = (MethodBodyHandler) Platform.getAdapterManager()
					.loadAdapter(this, engineID);
			subMon.worked(5);
			if (methodBodyHandler == null) {
				return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
						"Unknown method body handler: " + engineID + ". Code generation aborted.");
			}
			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			// (2.1) Validate SDMs
			final ITask validator = methodBodyHandler.createValidator(ePackage);
			final StatusHolder validationStatusHolder = new StatusHolder();
			final WorkspaceJob validationJob = new WorkspaceJob(engineID) {
				@Override
				public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
					final SubMonitor subMon = SubMonitor.convert(monitor, "Validation job", 100);
					try {
						validationStatusHolder.status = validator.run(subMon.split(100));
					} catch (final Exception e) {
						validationStatusHolder.status = new Status(IStatus.ERROR,
								WorkspaceHelper.getPluginId(MoflonCodeGenerator.class),
								String.format("%s occurred during validation with message %s",
										e.getClass().getSimpleName(), e.getMessage()));
					}
					return validationStatusHolder.status;
				}
			};
			final JobGroup jobGroup = new JobGroup("Validation job group", 1, 1);
			validationJob.setJobGroup(jobGroup);
			validationJob.schedule();
			final int timeoutForValidationTaskInMillis = getPreferencesStorage().getValidationTimeout();
			jobGroup.join(timeoutForValidationTaskInMillis, subMon.split(10));

			if (validationJob.getResult() == null) {
				throw new OperationCanceledException(String.format(
						"Validation took longer than %ds. This could(!) mean that some of your patterns have no valid search plan. You may increase the timeout value using the eMoflon property page",
						(timeoutForValidationTaskInMillis / 1000)));
			}

			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			final IStatus validationStatus = validationStatusHolder.status;
			if (validationStatus.matches(IStatus.ERROR)) {
				return validationStatus;
			}

			// (2.1) Perform additional code generation phase
			final IStatus weaverStatus;
			if (this.additionalCodeGenerationPhase != null) {
				weaverStatus = this.additionalCodeGenerationPhase.run(getProject(), resource, methodBodyHandler,
						subMon.split(10));

				if (subMon.isCanceled()) {
					return Status.CANCEL_STATUS;
				}

				if (weaverStatus.matches(IStatus.ERROR)) {
					return weaverStatus;
				}
			} else {
				weaverStatus = Status.OK_STATUS;
				subMon.worked(10);
			}

			// (3) Build or load GenModel
			final MonitoredGenModelBuilder genModelBuilderJob = new MonitoredGenModelBuilder(getResourceSet(),
					getAllResources(), getEcoreFile(), true, getMoflonProperties());
			final IStatus genModelBuilderStatus = genModelBuilderJob.run(subMon.split(15));
			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			if (genModelBuilderStatus.matches(IStatus.ERROR)) {
				return genModelBuilderStatus;
			}
			this.genModel = genModelBuilderJob.getGenModel();

			// (4) Load injections
			final IProject project = getEcoreFile().getProject();

			final IStatus injectionStatus = createInjections(project, genModel);
			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			if (injectionStatus.matches(IStatus.ERROR)) {
				return injectionStatus;
			}

			// (5) Process GenModel
			subMon.subTask("Processing SDMs for project " + project.getName());
			final ITask genModelProcessor = methodBodyHandler.createGenModelProcessor(this, resource);
			final IStatus genModelProcessorStatus = genModelProcessor.run(subMon.split(35));
			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			if (genModelProcessorStatus.matches(IStatus.ERROR)) {
				return genModelProcessorStatus;
			}

			// (6) Generate code
			subMon.subTask("Generating code for project " + project.getName());
			final Descriptor codeGenerationEngine = methodBodyHandler.createCodeGenerationEngine(this, resource);
			final CodeGenerator codeGenerator = new CodeGenerator(codeGenerationEngine);
			final IStatus codeGenerationStatus = codeGenerator.generateCode(genModel,
					new BasicMonitor.EclipseSubProgress(subMon, 30));
			if (subMon.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			if (codeGenerationStatus.matches(IStatus.ERROR)) {
				return codeGenerationStatus;
			}
			subMon.worked(5);

			long tic = System.nanoTime();

			logger.info(String.format(Locale.US, "Completed in %.3fs", (tic - toc) / 1e9));

			final boolean everythingOK = validationStatus.isOK() && injectionStatus.isOK() && weaverStatus.isOK();
			return everythingOK
					? new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "Code generation succeeded")
					: new MultiStatus(WorkspaceHelper.getPluginId(getClass()), validationStatus.getCode(),
							new IStatus[] { validationStatus, weaverStatus, injectionStatus },
							"Code generation warnings/errors", null);
		} catch (final Exception e) {
			return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), IStatus.ERROR,
					String.format("%s occurred during eMoflon code generation. Message: '%s'. Stacktrace:\n%s",
							e.getClass().getName(), e.getMessage(), WorkspaceHelper.printStacktraceToString(e)),
					e);
		}
	}

	public final GenModel getGenModel() {
		return genModel;
	}

	public final InjectionManager getInjectorManager() {
		return injectionManager;
	}

	protected String getFullProjectName(final MoflonPropertiesContainer moflonProperties) {
		return moflonProperties.getProjectName();
	}

	/**
	 * Loads the injections from the /injection folder
	 */
	private IStatus createInjections(final IProject project, final GenModel genModel) throws CoreException {
		IFolder injectionFolder = WorkspaceHelper.addFolder(project, WorkspaceHelper.INJECTION_FOLDER,
				new NullProgressMonitor());
		CodeInjector injector = new CodeInjectorImpl(project.getLocation().toOSString());

		InjectionExtractor injectionExtractor = new XTextInjectionExtractor(injectionFolder, genModel);
		CompilerInjectionExtractorImpl compilerInjectionExtractor = new CompilerInjectionExtractorImpl(project,
				genModel);

		injectionManager = new InjectionManager(injectionExtractor, compilerInjectionExtractor, injector);
		return injectionManager.extractInjections();
	}

	private static class StatusHolder {
		IStatus status;
	}

	public void setAdditionalCodeGenerationPhase(final MoflonCodeGeneratorPhase codeGenerationPhase) {
		this.additionalCodeGenerationPhase = codeGenerationPhase;
	}
}
