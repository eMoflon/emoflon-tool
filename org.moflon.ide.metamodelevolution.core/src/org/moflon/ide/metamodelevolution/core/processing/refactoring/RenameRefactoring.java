package org.moflon.ide.metamodelevolution.core.processing.refactoring;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.metamodelevolution.core.RenameChange;

public interface RenameRefactoring
{

	public static final String GEN_FOLDER ="/" + WorkspaceHelper.GEN_FOLDER;
	
	public static final String JAVA_EXTENSION = "." + WorkspaceHelper.JAVA_FILE_EXTENSION;
	
	public IStatus refactor(IProject project);
		
	public void refactor(IProject project, RenameChange renameChange);

}
