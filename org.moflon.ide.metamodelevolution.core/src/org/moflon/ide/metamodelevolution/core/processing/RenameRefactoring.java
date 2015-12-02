package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IProject;
import org.moflon.ide.metamodelevolution.core.RenameChange;

public interface RenameRefactoring {

	public static final String GEN_FOLDER ="/gen"; //TODO@settl: Use "/" + WorkspaceHelper.GEN_FOLDER
	
	public static final String JAVA_EXTENSION = ".java"; //TODO@settl: use "." + JAVA_FILE_EXTENSION
	
	public void refactor(IProject project, RenameChange change);
}
