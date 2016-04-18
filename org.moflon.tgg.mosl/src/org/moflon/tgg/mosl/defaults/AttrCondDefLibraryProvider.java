package org.moflon.tgg.mosl.defaults;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;
import static org.moflon.core.utilities.WorkspaceHelper.createSubmonitorWith1Tick;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

public class AttrCondDefLibraryProvider {

	public static void syncAttrCondDefLibrary(IProject project) throws CoreException {
		String defaultLib = DefaultFilesHelper.generateDefaultAttrCondDefLibrary();

		IPath pathToLib = new Path("src/org/moflon/tgg/mosl/csp/lib/AttrCondDefLibrary.tgg");
		project.getFile(pathToLib).delete(true, new NullProgressMonitor());
		addAllFoldersAndFile(project, pathToLib, defaultLib, createSubmonitorWith1Tick(new NullProgressMonitor()));
	}
}