package org.moflon.tgg.mosl.defaults;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.moflon.core.utilities.ExceptionUtil;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.ProblemMarkerUtil;

public class AttrCondDefLibraryProvider {

	private static final Logger logger = Logger.getLogger(AttrCondDefLibraryProvider.class);

	private static final String FILENAME = "AttrCondDefLibrary.tgg";

	private static final String SOURCE_FOLDER = "src";

	private static final String PACKAGE_PATH_TO_LIBRARY = "/org/moflon/tgg/mosl/csp/lib/";

	@Deprecated // Since 2018-08-10
	public static final String fileName = FILENAME;

	@Deprecated // Since 2018-08-10
	public static final String src = SOURCE_FOLDER;

	@Deprecated // Since 2018-08-10
	public static final String moslPath = PACKAGE_PATH_TO_LIBRARY;

	public static void syncAttrCondDefLibrary(final IProject project) throws CoreException {
		final IFile attrCondDefLibrary = findAttrCondDefLibrary(project.getFolder(SOURCE_FOLDER));
		if (attrCondDefLibrary == null) {
			createDefaultLibraryFile(project);
		} else {
			removeLibraryModificationProblemMarkers(attrCondDefLibrary);
			checkForModifiedLibrary(attrCondDefLibrary);
		}
	}

	/**
	 * Creates a file with the default attribute constraints library in the given project
	 * @param project the project
	 * @throws CoreException if the resource cannot be created
	 */
	private static void createDefaultLibraryFile(final IProject project) throws CoreException {
		final String defaultLib = DefaultFilesHelper.generateDefaultAttrCondDefLibrary();
		final String projectName = project.getProject().getName().replace('.', '/');
		final IPath pathToLib = new Path(SOURCE_FOLDER + "/" + projectName + PACKAGE_PATH_TO_LIBRARY + FILENAME);

		addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
	}

	/**
	 * Removes the resource marker that contains the warning about the modified library
	 * @param attrCondDefLibrary the file containing the library
	 * @throws CoreException
	 */
	private static void removeLibraryModificationProblemMarkers(final IFile attrCondDefLibrary) throws CoreException {
		final IMarker[] markers = attrCondDefLibrary.findMarkers(IMarker.PROBLEM, false, 1);
		for (final IMarker marker : markers) {
			if (getLibraryModificationWarningMessage().equals(marker.getAttribute(IMarker.MESSAGE))) {
				marker.delete();
			}
		}
	}

	/**
	 * Checks whether the attribute constraints library in the given file is equal to the default one
	 *
	 * If not, a warning is logged and a problem marker is created
	 *
	 * @param attrCondDefLibrary the library file
	 * @throws CoreException
	 */
	private static void checkForModifiedLibrary(final IFile attrCondDefLibrary) throws CoreException {
		try (final InputStream contents = attrCondDefLibrary.getContents();) {
			final String currentContent = normalizeWhitespaces(IOUtils.toString(contents));
			final String defaultContent = normalizeWhitespaces(DefaultFilesHelper.generateDefaultAttrCondDefLibrary());
			if (!currentContent.equals(defaultContent)) {

				final String message = getLibraryModificationWarningMessage();

				final IFile file = attrCondDefLibrary;
				final IResource markedResource = file.exists() ? file : file.getProject();
				ProblemMarkerUtil.createProblemMarker(markedResource, message, IMarker.SEVERITY_WARNING,
						markedResource.getProjectRelativePath().toString());

				LogUtils.warn(logger, "%s Location: %s", message, attrCondDefLibrary.getProjectRelativePath());
			}
		} catch (final IOException e) {
			throw new CoreException(ExceptionUtil.createDefaultErrorStatus(AttrCondDefLibraryProvider.class, e));
		}
	}

	/**
	 * Returns thew warning message for modified attribute constraint libraries
	 * @return the warning message
	 */
	private static String getLibraryModificationWarningMessage() {
		final String message = "The default attribute constraints library has been modified manually. " //
				+ "Please consider using user-defined constraints in the schema file instead.";
		return message;
	}

	/**
	 * Creates a normalized view of the given input string where all sequences of white spaces are collapsed into one whitespace
	 * @param inputString the input string
	 * @return the normalized string
	 */
	private static String normalizeWhitespaces(final String inputString) {
		return inputString.replaceAll("\\s+", " ");
	}

	/**
	 * Convenience method that returns true if
	 * {@link #findAttrCondDefLibrary(IContainer)} returns a non-null result for the
	 * given container
	 *
	 * @param container
	 *            the container in which the attribute constraints library shall be
	 *            searched for recursively
	 * @return true if the container contains an attribute constraints library
	 * @throws CoreException
	 *             if a resource access fails
	 */
	@Deprecated // Since 2018-08-10
	public static boolean containsAttrCondDefLibrary(final IContainer container) throws CoreException {
		return findAttrCondDefLibrary(container) != null;
	}

	/**
	 * Returns the attribute constraints library in the given container (if exists)
	 *
	 * @param container
	 *            the container in which the attribute constraints library shall be
	 *            searched for recursively
	 * @return the attribute constraints library
	 * @throws CoreException
	 *             if a resource access fails
	 */
	public static IFile findAttrCondDefLibrary(final IContainer container) throws CoreException {
		final IResource[] members = container.members();

		for (final IResource member : members) {
			if (member instanceof IFile) {
				if (member.getName().equals(fileName))
					return (IFile) member;
			} else if (member instanceof IContainer) {
				final IFile attrCondDefLibrary = findAttrCondDefLibrary((IContainer) member);
				if (attrCondDefLibrary != null)
					return attrCondDefLibrary;
			}
		}

		return null;
	}
}