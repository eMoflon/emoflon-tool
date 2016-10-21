package org.moflon.ide.core.git;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jgit.api.CleanCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;;

public class GitResetProjectHelper {
	private static final Logger logger = Logger.getLogger(GitResetProjectHelper.class);
	
	private static final String gitSuffix = "/.git";
	
	public static boolean resetAndCleanWorkspace(IProject project) {
		IPath path = project.getLocation().makeAbsolute();
		File gitFolder = null;
		do {
			if (path.isEmpty()) {
				logger.error("Could not find any Git repository");
				return false;
			}
			gitFolder = new File(path + gitSuffix);
			path = path.removeLastSegments(1);
		}
		while(!gitFolder.exists());
		
		Repository rep = null;
		try {
			rep = FileRepositoryBuilder.create(gitFolder);
		} catch (IOException e1) {
			logger.error("Could not find Git-Repository");
			return false;
		}
		
		Git git = new Git(rep);
		
		ResetCommand resetCmd = git.reset();
		resetCmd.setMode(ResetType.HARD);
		
		CleanCommand cleanCmd = git.clean();
		cleanCmd.setCleanDirectories(true);
		cleanCmd.setIgnore(false);
		
		try {
			logger.debug("Resetting workspace");
			resetCmd.call();
		} catch (Exception e) {
			logger.error("Failed to reset workspace");
			return false;
		} 
		
		try {
			logger.debug("Cleaning workspace");
			cleanCmd.call();
		} catch (Exception e) {
			logger.error("Failed to clean workspace");
			// true because reset was successful
			return true;
		}
		
		logger.debug("Resetting and cleaning successful");
		return true;
	}
}
