package org.moflon.ide.ui.admin.handlers;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.gervarro.eclipse.workspace.util.IWorkspaceTask;

final class RemoveDumpedValidationFilesJob implements IWorkspaceTask {
	final IFolder modelFolder;

	RemoveDumpedValidationFilesJob(final IFile ecoreFile) {
		modelFolder = (IFolder) ecoreFile.getParent();
	}

	@Override
	public void run(IProgressMonitor monitor) throws CoreException {
		modelFolder.accept(new IResourceVisitor() {

			@Override
			public boolean visit(IResource resource) throws CoreException {
				final String trimmedResourceName = resource.getName().replaceAll(".xmi", "");

				// Extensions as defined in DemoclesMethodBodyHandler
				final List<String> extensions = Arrays.asList("red", "green", "binding", "bindingAndBlack", "black",
						"expression", "cf", "dfs", "sdm");
				if (resource instanceof IFile
						&& extensions.stream().anyMatch(extension -> trimmedResourceName.endsWith(extension))) {
					resource.delete(true, new NullProgressMonitor());
				}

				// Do not recurse, only process the selected folder
				return false;
			}
		});
	}

	@Override
	public String getTaskName() {
		return "Remove old dump files";
	}

	@Override
	public ISchedulingRule getRule() {
		return modelFolder;
	}
}