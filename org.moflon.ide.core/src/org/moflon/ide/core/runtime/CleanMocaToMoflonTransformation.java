package org.moflon.ide.core.runtime;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.moflon.core.build.CleanVisitor;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.properties.MocaTreeConstants;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

import MocaTree.Node;

public class CleanMocaToMoflonTransformation extends BasicResourceFillingMocaToMoflonTransformation {

	public CleanMocaToMoflonTransformation(final ResourceSet set, final MetamodelBuilder metamodelBuilder,
			final IProject metamodelProject) {
		super(set, metamodelBuilder, metamodelProject);
	}

	@Override
	public void handleOutermostPackage(final Node node, final EPackage outermostPackage) {
		final String projectName = getProjectName(node);
		final String exportAttribute = lookupAttribute(node, MocaTreeConstants.MOCA_TREE_ATTRIBUTE_EXPORT);
		if (isExported(exportAttribute)) {
			final IProject workspaceProject = getWorkspace().getRoot().getProject(projectName);
			if (workspaceProject.exists() && workspaceProject.isAccessible()) {
				final String nodeName = node.getName();
				final IFolder modelFolder = workspaceProject.getFolder(WorkspaceHelper.MODEL_FOLDER);
				try {
					if (MocaTreeConstants.MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT.equals(nodeName)) {
						final IFile ecoreFile = modelFolder
								.getFile(getEcoreFileName(node) + WorkspaceHelper.ECORE_FILE_EXTENSION);
						ecoreFile.delete(true, null);
					} else if (MocaTreeConstants.MOCA_TREE_ATTRIBUTE_INTEGRATION_PROJECT.equals(nodeName)) {
						final String[] patterns = new String[] { "model/" + getEcoreFileName(node) + ".pre.*" };
						final CleanVisitor cleanVisitor = new CleanVisitor(workspaceProject,
								new AntPatternCondition(patterns));
						workspaceProject.accept(cleanVisitor, IResource.DEPTH_INFINITE, IResource.NONE);
					}
				} catch (final CoreException e) {
					reportError(e);
				}
			}
		}
	}

	@Override
	public void postProcessing() {
		// Do nothing
	}
}
