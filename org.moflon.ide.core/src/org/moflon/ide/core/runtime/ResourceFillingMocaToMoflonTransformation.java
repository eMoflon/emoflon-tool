package org.moflon.ide.core.runtime;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.UncheckedCoreException;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;
import org.moflon.util.plugins.MetamodelProperties;

import MocaTree.Node;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;

public class ResourceFillingMocaToMoflonTransformation extends BasicResourceFillingMocaToMoflonTransformation {
	private final Map<String, MetamodelProperties> propertiesMap;
	private final IProgressMonitor monitor;

	public ResourceFillingMocaToMoflonTransformation(final ResourceSet resourceSet,
			final MetamodelBuilder resourceSetProcessor,
			final IProject metamodelProject,
			final Map<String, MetamodelProperties> propertiesMap,
			final IProgressMonitor progressMonitor) {
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

   protected void handleMissingProject(final Node node, final IProject project) {
		final MetamodelProperties properties = propertiesMap.get(project.getName());
		final MoflonProjectCreator moflonProjectCreator =
				new MoflonProjectCreator(project, properties);
		try {
			WorkspaceTask.execute(moflonProjectCreator, false);
		} catch (CoreException e) {
			reportError(e);
		}
	}

	protected void handleClosedProject(final Node node, final IProject project) {
		try {
			project.open(new NullProgressMonitor());
		} catch (final CoreException e) {
			throw new UncheckedCoreException(e);
		}
	}

	protected void handleOpenProject(final Node node, final IProject project) {
		final MetamodelProperties properties = propertiesMap.get(project.getName());
		final MoflonPropertiesContainer moflonProps =
				createOrLoadMoflonProperties(project, properties.getMetamodelProjectName());
		final OpenProjectHandler openProjectHandler =
				new OpenProjectHandler(project, properties, moflonProps);
		try {
			WorkspaceTask.execute(openProjectHandler, false);
		} catch (final CoreException e) {
			reportError(e);
		}
	}

	private final MoflonPropertiesContainer createOrLoadMoflonProperties(
			final IProject project, final String metamodelProject) {
	   MoflonPropertiesContainerHelper.updateMoflonPropertiesToNewBasePackage(project);
		final IFile moflonProps = project.getFile(MoflonPropertiesContainerHelper.MOFLON_CONFIG_FILE);
		MoflonPropertiesContainerHelper.load(project, new NullProgressMonitor());
		if (moflonProps.exists()) {
			URI projectURI = URI.createPlatformResourceURI(project.getName() + "/", true);
			URI moflonPropertiesURI = URI.createURI(MoflonPropertiesContainerHelper.MOFLON_CONFIG_FILE).resolve(projectURI);
			Resource moflonPropertiesResource = set.getResource(moflonPropertiesURI, true);
			MoflonPropertiesContainer container = (MoflonPropertiesContainer) moflonPropertiesResource.getContents().get(0);
			container.updateMetamodelProjectName(metamodelProject);
			return container;
		} else {
			return MoflonPropertiesContainerHelper.createDefaultPropertiesContainer(project.getName(), metamodelProject);
		}
	}
}
