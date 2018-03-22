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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.ui.AbstractCommandHandler;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintLibraryUtil;

public class CreateUserDefinedConstraintsLibraryHandler extends AbstractCommandHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		if (selection instanceof IStructuredSelection) {
			final ResourceSet resourceSet = eMoflonEMFUtil.createDefaultResourceSet();
			final Collection<IProject> projects = getProjectsFromSelection((IStructuredSelection) selection);
			if (!projects.isEmpty()) {
				final IProject project = projects.iterator().next();
				final URI uri = AttributeConstraintLibraryUtil.getURIOfUserDefinedLibrary(project);
				final Resource libraryResource = AttributeConstraintLibraryUtil
						.loadAttributeConstraintLibraryResource(uri, true, resourceSet);
				if (libraryResource.getContents().isEmpty()) {
					libraryResource.getContents().add(AttributeConstraintLibraryUtil.createEmptyLibrary(project));
					final Map<String, String> saveOptions = new HashMap<>();
					saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
							Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
					try {
						libraryResource.save(saveOptions);
					} catch (final IOException e) {
						throw new ExecutionException("Creating library failed.", e);
					}
				}
			}
		}
		return AbstractCommandHandler.DEFAULT_HANDLER_RESULT;
	}

}
