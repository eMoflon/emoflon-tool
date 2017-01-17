package org.moflon.ide.core.injection;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.moca.inject.InjectionFile;

public class JavaFileInjectionExtractor {
	private static Logger logger = Logger.getLogger(JavaFileInjectionExtractor.class);

	public void extractInjectionNonInteractively(final IFile javaFile) {
		this.extractInjection(javaFile, false);
	}

	public void extractInjectionInteractively(final IFile javaFile) {
		this.extractInjection(javaFile, true);
	}

	public void extractInjection(final IFile javaFile, final boolean runsInteractive) {
		try {
			if (!javaFile.exists())
				return;

			// Stream needs to be re-opened for each check!
			if (isEmfUtilityClass(javaFile.getContents()) || isEmfUtilityInterface(javaFile.getContents())) {
				final String message = "It is not possible to create injections from EMF utility classes/interfaces.";
				if (runsInteractive) {
					logger.info(message + ". File: '" + javaFile.getFullPath() + "'.");
				} else {
					logger.debug(message + ". File: '" + javaFile.getFullPath() + "'.");
				}
			} else {
				final IPath fullInjectionPath = WorkspaceHelper.getPathToInjection(javaFile);
				final String fullyQualifiedClassname = WorkspaceHelper.getFullyQualifiedClassName(javaFile);

				// Determine contents of file
				final InputStream javaContentStream = javaFile.getContents();
				final String className = javaFile.getName().replace(".java", "");
				final InjectionFile injectionFile = new InjectionFile(javaContentStream, className);
				if (injectionFile.hasModelsOrImportsOrMembersCode()) {
					final String injContent = injectionFile.getFileContent();

					// insert the contents
					final IProject project = javaFile.getProject();
					project.getFile(fullInjectionPath).delete(true, new NullProgressMonitor());
					logger.info("Created injection file for '" + fullyQualifiedClassname + "'.");

					WorkspaceHelper.addAllFoldersAndFile(project, fullInjectionPath, injContent,
							new NullProgressMonitor());
				} else {
					logger.debug("Not creating injection file for  " + javaFile.getFullPath()
							+ " because no model code were found.");
				}
			}
		} catch (final CoreException ex) {
			logger.error("Unable to create injection code for " + javaFile + " due to " + ex);
		}
	}

	private boolean isEmfUtilityClass(final InputStream javaContentStream) {
		final Pattern isEmfUtilClassPattern = Pattern.compile(
				".*public\\s+class\\s+[a-zA-Z1-9<>]+\\s+extends\\s+(AdapterFactoryImpl|Switch<T>|EPackageImpl|EFactoryImpl)[^a-zA-Z].*");
		final Scanner s = new Scanner(javaContentStream);
		String match = s.findWithinHorizon(isEmfUtilClassPattern, 0);
		s.close();
		return match != null;
	}

	private boolean isEmfUtilityInterface(final InputStream javaContentStream) {
		final Pattern isEmfUtilInterfacePattern = Pattern
				.compile(".*public\\s+interface\\s+[a-zA-Z1-9]+\\s+extends\\s+(EPackage|EFactory)[^a-zA-Z].*");
		final Scanner s = new Scanner(javaContentStream);
		String match = s.findWithinHorizon(isEmfUtilInterfacePattern, 0);
		s.close();
		return match != null;
	}
}
