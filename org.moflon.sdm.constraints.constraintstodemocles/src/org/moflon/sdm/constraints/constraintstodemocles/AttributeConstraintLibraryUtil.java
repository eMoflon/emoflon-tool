package org.moflon.sdm.constraints.constraintstodemocles;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintsOperationActivator;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationFactory;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;

/**
 * Utility methods for working with attribute constraint libraries
 *
 * @author Roland Kluge - Initial implementation
 *
 */
public class AttributeConstraintLibraryUtil {

	/**
	 * Tries to load the attribute constraint library from the given URI
	 * @param uri the {@link URI} from which to load
	 * @param createIfNotExists true if the library shall be created if missing
	 * @param resourceSet the {@link ResourceSet} to load the library into
	 * @return the loaded library. May be null
	 */
	public static Resource loadAttributeConstraintLibraryResource(final URI uri, final boolean createIfNotExists,
			final ResourceSet resourceSet) {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(OperationspecificationPackage.eNS_URI,
				OperationspecificationPackage.eINSTANCE);
		Resource resource = null;
		if (resourceSet.getURIConverter().exists(uri, null)) {
			resource = resourceSet.getResource(uri, true);
		} else if (createIfNotExists) {
			resource = resourceSet.createResource(uri);
		}
		return resource;

	}

	/**
	 * Returns the URI of the attribute library contained in eMoflon
	 * @return the URI of the built-in library
	 */
	public static URI getURIOfBuiltInLibrary() {

		return URI.createPlatformPluginURI("/" + WorkspaceHelper.getPluginId(AttributeConstraintsOperationActivator.class)
						+ "/lib/buildInConstraintsLibrary/BuildInAttributeVariableConstraintLibrary.xmi", true);
	}

	/**
	 * Returns the URI of the attribute library provided by the user in the given project
	 * @return the URI of the user-defined library
	 */
	public static URI getURIOfUserDefinedLibrary(final IProject project) {

		final String projectName = project.getName();
		return URI.createPlatformResourceURI(
				"/" + projectName + "/lib/" + projectName + "AttributeConstraintsLib.xmi", true);
	}

	public static AttributeConstraintLibrary createEmptyLibrary(final IProject project) {
		final AttributeConstraintLibrary library = OperationspecificationFactory.eINSTANCE.createAttributeConstraintLibrary();
		library.setPrefix(project.getName() + "AttributeConstraintsLib");
		return library;
	}

}
