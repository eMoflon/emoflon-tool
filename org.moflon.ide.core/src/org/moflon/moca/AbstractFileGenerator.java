package org.moflon.moca;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.moflon.core.utilities.WorkspaceHelper;

public abstract class AbstractFileGenerator {

	protected String message = "";

	protected IProject project;

	public AbstractFileGenerator(final IProject project) {
		this.project = project;
	}

	public String doFinish() throws CoreException {
		prepareCodegen();
		generate();
		return message;
	}

	protected void generate() throws CoreException {

		Map<String, String> fileNamesToContents = extractFileNamesToContents();
		for (String fileName : fileNamesToContents.keySet()) {
			String content = fileNamesToContents.get(fileName);
			addFileAndCheckSuccess(fileName, content);
		}
	}

	protected void addFileAndCheckSuccess(final String fileName, final String content) throws CoreException {
		if (!addFile(fileName, content)) {
			message += fileName + " already exists! \n" + "Please don't forget to edit appropriately ... \n";
		}
	}

	protected boolean addFile(final String fileName, final String content) throws CoreException {
		IFile file = project.getFile(fileName);
		if (!file.exists()) {
			WorkspaceHelper.addFile(project, fileName, content, new NullProgressMonitor());
			return true;
		} else {
			return false;
		}
	}

	abstract protected void prepareCodegen();

	abstract protected Map<String, String> extractFileNamesToContents() throws CoreException;
}
