package org.moflon.ide.core.runtime.builders;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.core.build.AbstractVisitorBuilder;
import org.moflon.core.build.CleanVisitor;
import org.moflon.core.plugins.manifest.ExportedPackagesInManifestUpdater;
import org.moflon.core.plugins.manifest.PluginXmlUpdater;
import org.moflon.core.preferences.EMoflonPreferencesActivator;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.ClasspathUtil;
import org.moflon.core.utilities.ErrorReporter;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.MoslTggConstants;
import org.moflon.ide.core.project.RepositoryProjectCreator;
import org.moflon.ide.core.runtime.natures.IntegrationNature;
import org.moflon.ide.core.runtime.natures.MetamodelNature;
import org.moflon.ide.core.runtime.natures.RepositoryNature;

public class RepositoryBuilder extends AbstractVisitorBuilder {
	public static final Logger logger = Logger.getLogger(RepositoryBuilder.class);

	public RepositoryBuilder() {
		super(new AntPatternCondition(new String[] { "model/*.ecore" }));
	}

	public static String getId() {
		return "org.moflon.ide.core.runtime.builders.RepositoryBuilder";
	}

	@Override
	public ISchedulingRule getRule(final int kind, final Map<String, String> args) {
		return getProject();
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		final SubMonitor subMon = SubMonitor.convert(monitor, "Cleaning " + getProject(), 4);

		final IProject project = getProject();

		deleteProblemMarkers();
		subMon.worked(1);

		cleanGeneratedCode(project);

		cleanModels(WorkspaceHelper.getModelFolder(project), subMon.split(1));
	}

	private void cleanGeneratedCode(final IProject project) throws CoreException {
		final CleanVisitor cleanVisitor = new CleanVisitor(getProject(), //
				new AntPatternCondition(new String[] { "gen/**", "debug/**" }), //
				new AntPatternCondition(new String[] { "gen/.keep*" }));
		// Remove generated code
		project.accept(cleanVisitor, IResource.DEPTH_INFINITE, IResource.NONE);
	}

	public void handleErrorsInEclipse(final IStatus status, final IFile ecoreFile) {
		final String reporterClass = "org.moflon.compiler.sdm.democles.eclipse.EclipseErrorReporter";
		final ErrorReporter eclipseErrorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(ecoreFile,
				reporterClass);
		if (eclipseErrorReporter != null) {
			eclipseErrorReporter.report(status);
		} else {
			logger.debug("Could not load error reporter '" + reporterClass + "'");
		}
	}

	public void handleErrorsInEA(final IStatus status) {
		final String reporterClass = "org.moflon.validation.EnterpriseArchitectValidationHelper";
		final ErrorReporter errorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(getProject(),
				reporterClass);
		if (errorReporter != null) {
			errorReporter.report(status);
		} else {
			logger.debug("Could not load error reporter '" + reporterClass + "'");
		}
	}

	@Override
	protected void processResource(final IResource ecoreResource, final int kind, Map<String, String> args,
			final IProgressMonitor monitor) {
		if (WorkspaceHelper.isEcoreFile(ecoreResource)) {
			final IFile ecoreFile = Platform.getAdapterManager().getAdapter(ecoreResource, IFile.class);
			try {
				final SubMonitor subMon = SubMonitor.convert(monitor,
						"Generating code for project " + getProject().getName(), 13);

				final IProject project = getProject();
				RepositoryProjectCreator projectCreator = new RepositoryProjectCreator(project, null, null);
				projectCreator.createFoldersIfNecessary(project, subMon.split(1));
				ClasspathUtil.makeSourceFolderIfNecessary(WorkspaceHelper.getGenFolder(getProject()));
				ClasspathUtil.makeSourceFolderIfNecessary(WorkspaceHelper.getInjectionFolder(getProject()));

				// Compute project dependencies
				final IBuildConfiguration[] referencedBuildConfigs = project
						.getReferencedBuildConfigs(project.getActiveBuildConfig().getName(), false);
				for (final IBuildConfiguration referencedConfig : referencedBuildConfigs) {
					addTriggerProject(referencedConfig.getProject());
				}

				deleteProblemMarkers();
				cleanGeneratedCode(getProject());

				// Build
				final ResourceSet resourceSet = eMoflonEMFUtil.createDefaultResourceSet();
				eMoflonEMFUtil.installCrossReferencers(resourceSet);
				subMon.worked(1);

				final MoflonCodeGenerator codeGenerationTask = new MoflonCodeGenerator(ecoreFile, resourceSet,
						EMoflonPreferencesActivator.getDefault().getPreferencesStorage());

				final IStatus status = codeGenerationTask.run(subMon.split(1));
				handleErrorsAndWarnings(status, ecoreFile);
				subMon.worked(3);

				final GenModel genModel = codeGenerationTask.getGenModel();
				if (genModel != null) {
					ExportedPackagesInManifestUpdater.updateExportedPackageInManifest(project, genModel);

					PluginXmlUpdater.updatePluginXml(project, genModel, subMon.split(1));
					ResourcesPlugin.getWorkspace().checkpoint(false);
				}

			} catch (final CoreException e) {
				final IStatus status = new Status(e.getStatus().getSeverity(), WorkspaceHelper.getPluginId(getClass()),
						e.getMessage(), e);
				handleErrorsInEclipse(status, ecoreFile);
			}
		}
	}

	/**
	 * This is externally triggered if new code is generated in other repository or
	 * integration projects or if an exported metamodel changes
	 */
	@Override
	protected final AntPatternCondition getTriggerCondition(final IProject project) {
		try {
			if (RepositoryNature.isRepositoryProject(project) || IntegrationNature.isIntegrationProject(project)) {
				return new AntPatternCondition(new String[] { "gen/**" });
			} else if (MetamodelNature.isMetamodelProject(project)) {
				return new AntPatternCondition(new String[] { ".temp/*.moca.xmi" });
			}
		} catch (final CoreException e) {
			// Do nothing
		}
		return new AntPatternCondition(new String[0]);
	}

	/**
	 * Handles errors and warning produced by the code generation task
	 *
	 * @param status
	 *            the {@link IStatus} that contains the errors and warnings
	 */
	protected void handleErrorsAndWarnings(final IStatus status, final IFile ecoreFile) throws CoreException {
		if (indicatesThatValidationCrashed(status)) {
			throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
					status.getMessage(), status.getException().getCause()));
		}
		if (status.matches(IStatus.ERROR)) {
			handleErrorsInEA(status);
			handleErrorsInEclipse(status, ecoreFile);
		}
		if (status.matches(IStatus.WARNING)) {
			handleInjectionWarningsAndErrors(status);
		}
	}

	// Delete generated models within model folder
	private void cleanModels(final IFolder folder, final IProgressMonitor monitor) throws CoreException {
		if (!folder.exists()) {
			return;
		}

		final SubMonitor subMon = SubMonitor.convert(monitor, "Inspecting " + folder.getName(),
				folder.members().length);
		for (final IResource resource : folder.members()) {
			// keep SVN data
			if (!resource.getName().startsWith(".")) {
				// only delete generated models directly in folder 'model'
				if (!WorkspaceHelper.isFolder(resource)) {
					MoflonPropertiesContainer properties = MoflonPropertiesContainerHelper.load(getProject(),
							subMon.split(1));
					if (properties.getReplaceGenModel().isBool()
							&& resource.getName().endsWith(WorkspaceHelper.GEN_MODEL_EXT)) {
						resource.delete(true, subMon.split(1));
					} else {
						monitor.worked(1);
					}

					if (IntegrationNature.isIntegrationProject(getProject())
							&& isAGeneratedFileInIntegrationProject(resource)) {
						resource.delete(true, subMon.split(1));
					} else {
						monitor.worked(1);
					}
				}
			} else {
				monitor.worked(1);
			}
		}
	}

	private boolean isAGeneratedFileInIntegrationProject(final IResource resource) {
		return !(resource.getName().endsWith(MoslTggConstants.PRE_ECORE_FILE_EXTENSION)
				|| resource.getName().endsWith(MoslTggConstants.PRE_TGG_FILE_EXTENSION));
	}

	private boolean indicatesThatValidationCrashed(IStatus status) {
		return status.getException() != null;
	}

	private void handleInjectionWarningsAndErrors(final IStatus status) {
		final String reporterClass = "org.moflon.emf.injection.validation.InjectionErrorReporter";
		final ErrorReporter errorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(getProject(),
				reporterClass);
		if (errorReporter != null) {
			errorReporter.report(status);
		} else {
			logger.debug("Could not load error reporter '" + reporterClass + "'");
		}
	}
}
