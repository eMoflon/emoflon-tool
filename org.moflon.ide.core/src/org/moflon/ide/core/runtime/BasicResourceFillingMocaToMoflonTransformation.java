package org.moflon.ide.core.runtime;

import java.util.LinkedList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.task.ITask;
import org.moflon.core.mocatomoflon.impl.ExporterImpl;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.UncheckedCoreException;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.properties.MocaTreeConstants;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;
import org.moflon.tgg.language.TripleGraphGrammar;

import MocaTree.Attribute;
import MocaTree.Node;

public class BasicResourceFillingMocaToMoflonTransformation extends ExporterImpl {

	private final IWorkspace workspace = ResourcesPlugin.getWorkspace();
	private final ResourceSet set;

	private final MetamodelBuilder metamodelBuilder;
	private final IProject metamodelProject;
	private final LinkedList<ITask> metamodelLoaderTasks = new LinkedList<>();
	private final LinkedList<ProjectDependencyAnalyzer> projectDependencyAnalyzerTasks = new LinkedList<>();

	public BasicResourceFillingMocaToMoflonTransformation(final ResourceSet set,
			final MetamodelBuilder metamodelBuilder, final IProject metamodelProject) {
		this.set = set;
		this.metamodelBuilder = metamodelBuilder;
		this.metamodelProject = metamodelProject;
	}

	public final LinkedList<ITask> getMetamodelLoaderTasks() {
		return metamodelLoaderTasks;
	}

	public final LinkedList<ProjectDependencyAnalyzer> getProjectDependencyAnalyzerTasks() {
		return projectDependencyAnalyzerTasks;
	}

	public final MetamodelBuilder getMetamodelBuilder() {
		return this.metamodelBuilder;
	}

	public final IProject getMetamodelProject() {
		return metamodelProject;
	}

	public ResourceSet getResourceSet() {
		return this.set;
	}

	public IWorkspace getWorkspace() {
		return workspace;
	}

	@Override
	public void handleOutermostPackage(final Node node, final EPackage outermostPackage) {
		final String projectName = getProjectName(node);
		final String exportAttribute = lookupAttribute(node, MocaTreeConstants.MOCA_TREE_ATTRIBUTE_EXPORT);
		if (isExported(exportAttribute)) {
			final String nodeName = node.getName();
			if (MocaTreeConstants.MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT.equals(nodeName)
					|| MocaTreeConstants.MOCA_TREE_ATTRIBUTE_INTEGRATION_PROJECT.equals(nodeName)) {
				// Handling (creating/opening) projects in Eclipse workspace
				final IProject workspaceProject = workspace.getRoot().getProject(projectName);
				if (!workspaceProject.exists()) {
					handleOrReportMissingProject(node, workspaceProject);
				}
				assert workspaceProject != null && workspaceProject.exists();
				if (!workspaceProject.isAccessible()) {
					handleOrReportClosedProject(node, workspaceProject);
				}
				assert workspaceProject.isAccessible();
				handleOpenProject(node, workspaceProject);
				metamodelLoaderTasks.add(new MetamodelLoader(metamodelBuilder, set, node, outermostPackage));
				projectDependencyAnalyzerTasks.add(new ProjectDependencyAnalyzer(metamodelBuilder, metamodelProject,
						workspaceProject, outermostPackage));
			} else {
				reportError("Project " + getProjectName(node) + " has unknown type " + node.getName());
			}
		} else {
			if (!MocaTreeConstants.MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT.equals(node.getName())) {
				reportError("Project " + getProjectName(node) + " must always be exported");
			}
			metamodelLoaderTasks.add(new MetamodelLoader(metamodelBuilder, set, node, outermostPackage));
		}
	}

	protected static final boolean isExported(final String exported) {
		return !"false".equals(exported);
	}

	@Override
	public void postProcessing() {
		super.postProcessing();
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		for (TripleGraphGrammar tgg : getTggexporter().getTripleGraphGrammar()) {
			final IProject workspaceProject = workspaceRoot.getProject(tgg.getName());
			if (!workspaceProject.exists()) {
				reportError("Project " + workspaceProject.getName() + " is missing");
			}
			if (!workspaceProject.isAccessible()) {
				reportError("Project " + workspaceProject.getName() + " is closed");
			}
			metamodelLoaderTasks.add(new TripleGraphGrammarLoader(set, tgg));
		}
	}

	protected String getProjectName(final Node node) {
		return lookupAttribute(node, MocaTreeConstants.MOFLON_TREE_ATTRIBUTE_NAME);
	}

	protected String getEcoreFileName(final Node node) {
		final String name = lookupAttribute(node, MocaTreeConstants.MOFLON_TREE_ATTRIBUTE_NAME);
		return MoflonUtil.lastCapitalizedSegmentOf(name);
	}

	protected static final String lookupAttribute(final Node node, final String attributeName) {
		for (final Attribute attribute : node.getAttribute()) {
			if (attributeName.equals(attribute.getName())) {
				return attribute.getValue();
			}
		}
		return null;
	}

	protected void handleMissingProject(final Node node, final IProject project) {
		// Do nothing
	}

	protected void handleClosedProject(final Node node, final IProject project) {
		// Do nothing
	}

	protected void handleOpenProject(final Node node, final IProject project) {
		// Do nothing
	}

	private final void handleOrReportMissingProject(final Node node, final IProject project) {
		handleMissingProject(node, project);
		if (!project.exists()) {
			reportError("Project %s is missing", getProjectName(node));
		}
	}

	private final void handleOrReportClosedProject(final Node node, final IProject project) {
		handleClosedProject(node, project);
		if (!project.isAccessible()) {
			reportError("Project %s is closed", project.getName());
		}
	}

	protected final void reportError(final String errorMessage, final Object... arguments) {
		throw new UncheckedCoreException(String.format(errorMessage, arguments),
				WorkspaceHelper.getPluginId(getClass()));
	}

	protected void reportError(final CoreException e) {
		throw new UncheckedCoreException(e);
	}
}
