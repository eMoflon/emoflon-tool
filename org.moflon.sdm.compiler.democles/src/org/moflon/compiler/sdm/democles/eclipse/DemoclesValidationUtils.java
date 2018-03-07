package org.moflon.compiler.sdm.democles.eclipse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.UtilityClassNotInstantiableException;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;

/**
 * Useful utilities for the democles validation process
 * 
 * @author Roland Kluge - Original implementation
 */
public final class DemoclesValidationUtils {
	private static final Logger logger = Logger.getLogger(DemoclesValidationUtils.class);

	private DemoclesValidationUtils() {
		throw new UtilityClassNotInstantiableException();
	}

	/**
	 * This method persists the intermediate control flow structures that are
	 * produced during the Democles validation process
	 * 
	 * @param ePackage
	 */
	public static void saveAllIntermediateModels(final EPackage ePackage) {
		for (final EClass eClass : eMoflonEMFUtil.getEClasses(ePackage)) {

			for (final EOperation eOperation : eClass.getEOperations()) {
				final AdapterResource controlFlowResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
						DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);
				AdapterResource sdmFileResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
						DemoclesMethodBodyHandler.SDM_FILE_EXTENSION);
				AdapterResource dfsFileResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
						DemoclesMethodBodyHandler.DFS_FILE_EXTENSION);

				for (AdapterResource adapterResource : Arrays.asList(controlFlowResource, dfsFileResource,
						sdmFileResource)) {
					saveResource(adapterResource);
				}
			}

			AdapterResource bindingAndBlackResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass,
					DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION);
			AdapterResource redResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass,
					DemoclesMethodBodyHandler.RED_FILE_EXTENSION);
			AdapterResource greenResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass,
					DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION);
			AdapterResource bindingFileResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass,
					DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION);
			AdapterResource blackFileResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass,
					DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION);
			AdapterResource expressionResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eClass,
					DemoclesMethodBodyHandler.EXPRESSION_FILE_EXTENSION);

			for (AdapterResource adapterResource : Arrays.asList(bindingAndBlackResource, blackFileResource,
					redResource, greenResource, bindingFileResource, blackFileResource, expressionResource)) {
				saveResource(adapterResource);
			}
		}
	}

	/**
	 * Persists the given resource using its URL plus the suffix .xmi.
	 * 
	 * The resource will is not changed during this method.
	 * 
	 * @param adapterResource
	 *            the resource to save
	 */
	private static void saveResource(AdapterResource adapterResource) {
		if (adapterResource != null) {
			final URI oldUri = adapterResource.getURI();
			final URI inWorkspaceUri;
			if (oldUri.isPlatformPlugin()) {
				final URI deresolve = oldUri.deresolve(URI.createPlatformPluginURI("", false));
				inWorkspaceUri = deresolve.resolve(URI.createPlatformResourceURI("", false));
			} else {
				inWorkspaceUri = oldUri;
			}
			try {
				// Save with old URI to allow for navigation between models
				adapterResource.setURI(inWorkspaceUri);
				HashMap<Object, Object> saveOptions = new HashMap<>();
				saveOptions.put(Resource.OPTION_LINE_DELIMITER, WorkspaceHelper.DEFAULT_RESOURCE_LINE_DELIMITER);
				adapterResource.save(saveOptions);
				URIConverter.URI_MAP.put(oldUri, inWorkspaceUri);
			} catch (IOException e) {
				LogUtils.error(logger, e);
			} finally {
				adapterResource.setURI(oldUri);
			}
		}
	}

	/**
	 * Deletes all intermediate models of the control flow generation process, which
	 * are generated, e.g., by #saveAllIntermediateModels.
	 * 
	 * @param modelFolder
	 *            only files contained in this folder are scanned
	 */
	public static void clearAllIntermediateModels(final IFolder modelFolder) {
		try {
			modelFolder.accept(new IResourceVisitor() {
				@Override
				public boolean visit(final IResource resource) throws CoreException {
					for (final String extension : Arrays.asList(DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION,
							DemoclesMethodBodyHandler.SDM_FILE_EXTENSION, DemoclesMethodBodyHandler.DFS_FILE_EXTENSION,
							DemoclesMethodBodyHandler.RED_FILE_EXTENSION,
							DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION,
							DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION,
							DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION,
							DemoclesMethodBodyHandler.EXPRESSION_FILE_EXTENSION)) {
						if (resource.getName().endsWith("." + extension)) {
							resource.delete(true, new NullProgressMonitor());
						}
					}
					return true;
				}
			}, IResource.DEPTH_ONE, false);
		} catch (final CoreException e) {
			LogUtils.error(logger, "Problem while removing temporary files in %s. Reason: %s", modelFolder,
					e.getMessage());
		}

	}

}
