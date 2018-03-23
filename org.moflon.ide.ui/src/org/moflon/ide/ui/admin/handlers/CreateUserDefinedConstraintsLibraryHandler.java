package org.moflon.ide.ui.admin.handlers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.ui.AbstractCommandHandler;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintLibraryUtil;

/**
 * This handler creates libraries for user-defined constraint libraries in each selected project
 *
 * @author Roland Kluge - Initial implementation
 */
public class CreateUserDefinedConstraintsLibraryHandler extends AbstractMoflonToolCommandHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Collection<IProject> projects = this.extractSelectedProjects(event);
		try {
			for (final IProject project : projects)
				createLibraryInProject(project);
		} catch (final IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
		return AbstractCommandHandler.DEFAULT_HANDLER_RESULT;
	}

	/**
	 * Creates the user-defined constraints library in the given project (if none
	 * exists)
	 *
	 * @param project
	 *            the project
	 * @throws IOException
	 *             if the library cannot be saved
	 */
	private void createLibraryInProject(final IProject project) throws IOException {
		final URI uri = AttributeConstraintLibraryUtil.getURIOfUserDefinedLibrary(project);
		final ResourceSet resourceSet = eMoflonEMFUtil.createDefaultResourceSet();
		final Resource libraryResource = AttributeConstraintLibraryUtil.loadAttributeConstraintLibraryResource(uri,
				true, resourceSet);
		if (libraryResource.getContents().isEmpty())
			fillAndSaveNewLibrary(project, libraryResource);
	}

	/**
	 * Creates an empty library, adds it the the {@link Resource} and saves it
	 * @param project the project containing the library
	 * @param libraryResource the resource of the library
	 * @throws IOException if saving fails
	 */
	private void fillAndSaveNewLibrary(final IProject project, final Resource libraryResource) throws IOException {
		libraryResource.getContents().add(AttributeConstraintLibraryUtil.createEmptyLibrary(project));
		final Map<String, String> saveOptions = new HashMap<>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		libraryResource.save(saveOptions);
	}
}
