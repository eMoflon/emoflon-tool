package org.moflon.ide.core.runtime;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.core.build.MoflonProjectCreator;
import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.core.plugins.PluginProperties;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.MoflonConventions;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.UncheckedCoreException;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.project.ProjectCreatorFactory;
import org.moflon.ide.core.project.RepositoryProjectCreator;
import org.moflon.ide.core.properties.MocaTreeEAPropertiesReader;
import org.moflon.ide.core.properties.PluginPropertiesHelper;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;
import org.moflon.ide.core.runtime.natures.IntegrationNature;
import org.moflon.ide.core.runtime.natures.RepositoryNature;

import MocaTree.Node;

public class ResourceFillingMocaToMoflonTransformation extends BasicResourceFillingMocaToMoflonTransformation {
	private final Map<String, PluginProperties> propertiesMap;

	private final IProgressMonitor monitor;

	public ResourceFillingMocaToMoflonTransformation(final ResourceSet resourceSet,
			final MetamodelBuilder resourceSetProcessor, final IProject metamodelProject,
			final Map<String, PluginProperties> propertiesMap, final IProgressMonitor progressMonitor) {
		super(resourceSet, resourceSetProcessor, metamodelProject);
		this.monitor = progressMonitor;
		this.propertiesMap = propertiesMap;
	}

	@Override
	public void handleOutermostPackage(final Node node, final EPackage outermostPackage) {
		super.handleOutermostPackage(node, outermostPackage);
		final String prefix = MoflonUtil.allSegmentsButLast(outermostPackage.getNsPrefix());
		if (prefix != null && prefix.length() > 0) {
			EcoreUtil.setAnnotation(outermostPackage, GenModelPackage.eNS_URI, "basePackage", prefix);
		}
		outermostPackage.setName(MoflonUtil.lastSegmentOf(outermostPackage.getName()));
		monitor.worked(1);
	}

	@Override
	protected void handleMissingProject(final Node node, final IProject project) {
		final PluginProperties properties = propertiesMap.get(project.getName());
		final MoflonProjectCreator moflonProjectCreator = ProjectCreatorFactory.getProjectCreator(project, properties,
				determineProjectConfigurator(properties));
		try {
			WorkspaceTask.executeInCurrentThread(moflonProjectCreator, IWorkspace.AVOID_UPDATE,
					new NullProgressMonitor());
		} catch (CoreException e) {
			reportError(e);
		}
	}

	@Override
	protected void handleClosedProject(final Node node, final IProject project) {
		try {
			project.open(new NullProgressMonitor());
		} catch (final CoreException e) {
			throw new UncheckedCoreException(e);
		}
	}

	@Override
	protected void handleOpenProject(final Node node, final IProject project) {
		final PluginProperties properties = propertiesMap.get(project.getName());
		final String expectedNatureId;
		if (PluginPropertiesHelper.isIntegrationProject(properties)) {
			expectedNatureId = IntegrationNature.getId();
		} else if (PluginPropertiesHelper.isRepositoryProject(properties)) {
			expectedNatureId = RepositoryNature.getId();
		} else {
			expectedNatureId = null;
		}
		try {
			if (expectedNatureId != null && !project.hasNature(expectedNatureId))
				throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()),
						"Missing nature of project " + project + ". Expected: " + expectedNatureId + "."));
		} catch (final CoreException e) {
			throw new UncheckedCoreException(e);
		}

		final MoflonPropertiesContainer moflonProps = createOrLoadMoflonProperties(project,
				properties.get(MocaTreeEAPropertiesReader.METAMODEL_PROJECT_NAME_KEY));
		final OpenProjectHandler openProjectHandler = new OpenProjectHandler(project, properties, moflonProps,
				determineProjectConfigurator(properties));
		try {
			WorkspaceTask.executeInCurrentThread(openProjectHandler, IWorkspace.AVOID_UPDATE,
					new NullProgressMonitor());
		} catch (final CoreException e) {
			reportError(e);
		}
	}

	private MoflonProjectConfigurator determineProjectConfigurator(final PluginProperties metamodelProperties) {
		final String projectType = metamodelProperties.getType();
		switch (projectType) {
		case PluginPropertiesHelper.INTEGRATION_PROJECT:
			return new IntegrationNature();
		case PluginPropertiesHelper.REPOSITORY_PROJECT:
			return new RepositoryNature();
		default:
			throw new IllegalArgumentException(String.format("Cannot handle project type %s", projectType));
		}
	}

	private final MoflonPropertiesContainer createOrLoadMoflonProperties(final IProject project,
			final String metamodelProject) {
		final IFile moflonProps = project.getFile(MoflonConventions.MOFLON_CONFIG_FILE);
		MoflonPropertiesContainerHelper.load(project, new NullProgressMonitor());
		if (moflonProps.exists()) {
			final URI projectURI = URI.createPlatformResourceURI(project.getName() + "/", true);
			final URI moflonPropertiesURI = URI.createURI(MoflonConventions.MOFLON_CONFIG_FILE).resolve(projectURI);
			final Resource moflonPropertiesResource = this.getResourceSet().getResource(moflonPropertiesURI, true);
			final MoflonPropertiesContainer container = (MoflonPropertiesContainer) moflonPropertiesResource
					.getContents().get(0);
			RepositoryProjectCreator.updateMetamodelProjectName(container, metamodelProject);
			return container;
		} else {
			throw new UncheckedCoreException(new CoreException(new Status(IStatus.ERROR,
					WorkspaceHelper.getPluginId(getClass()), "moflon.properties.xmi could not be created")));
		}
	}
}
