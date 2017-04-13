package org.moflon.tgg.mosl.defaults;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.moflon.core.utilities.WorkspaceHelper;

public class AttrCondDefLibraryProvider {
	
	public static final String fileName = "AttrCondDefLibrary.tgg";
	
	public static final String src = "src/";
	
	public static final String moslPath = "/org/moflon/tgg/mosl/csp/lib/";

	public static void syncAttrCondDefLibrary(IProject project) throws CoreException {

		if(!containsAttrCondDefLibrary(project)){
			final String defaultLib = DefaultFilesHelper.generateDefaultAttrCondDefLibrary();
			final String projectName = project.getProject().getName().replace('.', '/');
			final IPath pathToLib = new Path(src + projectName + moslPath + fileName);

		    addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
		}
	}

	public static boolean containsAttrCondDefLibrary(IContainer container) throws CoreException {
		IResource[] members = container.members();

		for (IResource member : members) {
			if (member instanceof IFile) {
				if(member.getName().equals(fileName))
					return true;
			}
			else if (member instanceof IContainer){
				if(containsAttrCondDefLibrary((IContainer) member))
					return true;
			}
		}
		
		return false;
	}
}