package org.moflon.ide.metamodelevolution.core.processing;

import org.eclipse.core.resources.IProject;
import org.moflon.ide.metamodelevolution.core.RenameChange;

public interface RenameRefactoring {

	public static final String GEN_FOLDER ="/gen";
	
	public static final String JAVA_EXTENSION = ".java";
	
	public void refactor(IProject project, RenameChange change);
}
