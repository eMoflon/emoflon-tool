package org.moflon.ide.core.runtime;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.eclipse.task.ITask;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.dependency.PackageRemappingDependency;
import org.moflon.eclipse.resource.SDMEnhancedEcoreResource;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

import MocaTree.Attribute;
import MocaTree.Node;

public class MetamodelLoader implements ITask {
	public static final int USER_DEFINED = CodeGeneratorPlugin.DEPENDENCY_TYPE_COUNT + 1;
	
	public static final String MOCA_TREE_ATTRIBUTE_INTEGRATION_PROJECT = "TGG";
	public static final String MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT = "EPackage";
	public static final String MOCA_TREE_ATTRIBUTE_NS_URI = "Moflon::NsUri";
	public static final String MOCA_TREE_ATTRIBUTE_EXPORT = "Moflon::Export";
	public static final String MOFLON_TREE_ATTRIBUTE_NAME = "Moflon::Name";
	private static final URI MOFLON_PROPERTIES_URI =
			URI.createURI(MoflonPropertiesContainerHelper.MOFLON_CONFIG_FILE);

	protected static final Logger MOCA_TO_MOFLON_TRANSFORMATION_LOGGER =
			Logger.getLogger(ResourceFillingMocaToMoflonTransformation.class);
	   
	protected final ResourceSet set;
	private final MetamodelBuilder builder;
	private final Node node;
	private final EPackage outermostPackage;
	private final URI defaultNamespaceURI;
	// private final IWorkspace workspace = ResourcesPlugin.getWorkspace();

	public MetamodelLoader(final MetamodelBuilder builder,
			final ResourceSet set, final Node node, final EPackage ePackage) {
		this.builder = builder;
		this.set = set;
		this.node = node;
		this.outermostPackage = ePackage;
		this.defaultNamespaceURI = getDefaultNamespace(node);
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		final String projectName = getProjectName(node);
		final URI namespaceURI = URI.createURI(lookupAttribute(node, MOCA_TREE_ATTRIBUTE_NS_URI));
		final String exportAttribute = lookupAttribute(node, MOCA_TREE_ATTRIBUTE_EXPORT);
		try {
			if (isExported(exportAttribute)) {
				if (isUserDefined(namespaceURI)) {
					MOCA_TO_MOFLON_TRANSFORMATION_LOGGER.warn("Namespace URIs are being ignored for exported projects");
				}

				final String nodeName = node.getName();
				if (MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT.equals(nodeName) ||
						MOCA_TREE_ATTRIBUTE_INTEGRATION_PROJECT.equals(nodeName)) {
					final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
					assert project.isAccessible();
					final URI projectURI = CodeGeneratorPlugin.lookupProjectURI(project);
					final URI metamodelURI =
							getProjectRelativeMetamodelURI(node).resolve(projectURI);
					
					CodeGeneratorPlugin.createPluginToResourceMapping(set, project);
					Resource resource =
							new PackageRemappingDependency(metamodelURI, false, false).getResource(set, false, true);
					resource.getContents().add(outermostPackage);
					setEPackageURI(outermostPackage);
				} else {
					return new Status(IStatus.ERROR, CoreActivator.getModuleID(),
							"Project " + projectName + " has unknown type " + node.getName());
				}
			} else {
				if (!MOCA_TREE_ATTRIBUTE_REPOSITORY_PROJECT.equals(node.getName())) {
					return new Status(IStatus.ERROR, CoreActivator.getModuleID(),
							"Project " + getProjectName(node) + " must always be exported");
				}

				// User-defined namespaceURI should point to the ecore file from which code was generated
				// E.g., platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore
				final int kind = getDependencyType(namespaceURI);
				if (kind == CodeGeneratorPlugin.DEPLOYED_PLUGIN) {
					try {
						// TODO gervarro: What shall we do with the set of interesting projects if the deployed plugin overrides a closed SDM project?
						// Override existing metamodel content (deployedResource) with the constructed metamodel (resource)
						final Resource deployedResource =
								new PackageRemappingDependency(namespaceURI, true, true).getResource(set, true, false);
						final EPackage ePackage = (EPackage) deployedResource.getContents().get(0);
						final Resource resource =
								new PackageRemappingDependency(namespaceURI, false, false).getResource(set, false, true);
						handleNewMetamodelContent(ePackage, resource);
					} catch (final WrappedException e) {
						return handleResourceLoadingException(e, namespaceURI);
					}
				} else if (kind == USER_DEFINED) {
					try {
						final Resource resource =
								new PackageRemappingDependency(namespaceURI, true, true).getResource(set, true, false);
						final EPackage ePackage = (EPackage) resource.getContents().get(0);
						// Replace existing metamodel content with the constructed metamodel in resource
						resource.unload();
						handleNewMetamodelContent(ePackage, resource);
					} catch (final WrappedException e) {
						return handleResourceLoadingException(e, namespaceURI);
					}
				} else if (kind == CodeGeneratorPlugin.WORKSPACE_PLUGIN_PROJECT || kind == CodeGeneratorPlugin.WORKSPACE_PROJECT) {
					final IProject project = CodeGeneratorPlugin.getWorkspaceProject(namespaceURI);
					if (project.isAccessible()) {
						CodeGeneratorPlugin.createPluginToResourceMapping(set, project);
						if (set.getURIConverter().exists(namespaceURI, null)) {
							try {
								// Open project with ecore file
								final Resource resource =
										new PackageRemappingDependency(namespaceURI, false, false).getResource(set, true, project.isAccessible());
								final EPackage ePackage = (EPackage) resource.getContents().get(0);
								// Replace existing metamodel content with the constructed metamodel in resource
								resource.unload();
								handleNewMetamodelContent(ePackage, resource);
								builder.addTriggerProject(project);
							} catch (final Exception e) {
								throw e;
							}
						} else {
							// Open project without ecore file
							builder.addTriggerProject(project);
							return new Status(IStatus.ERROR, CoreActivator.getModuleID(),
									"The project cannot be built until its prerequisite " + projectName + " is built. Cleaning and building all projects is recommended");
						}
					} else {
						return new Status(IStatus.ERROR, CoreActivator.getModuleID(),
								"Project " + projectName + " is closed and no deployed plugin can be found with URI " + namespaceURI);
					}
				} else {
					return new Status(IStatus.ERROR, CoreActivator.getModuleID(),
							"Project " + projectName + " has unknown type " + node.getName());
				}
			}
		} catch (CoreException e) {
			return new Status(IStatus.ERROR, CoreActivator.getModuleID(), e.getMessage(), e);
		}
		return Status.OK_STATUS;
	}

	@Override
	public String getTaskName() {
		return "Loading ecore model";
	}
	
	private final void handleNewMetamodelContent(final EPackage ePackage, final Resource resource) {
		if (resource instanceof SDMEnhancedEcoreResource) {
			((SDMEnhancedEcoreResource) resource).getDefaultSaveOptions().put(SDMEnhancedEcoreResource.READ_ONLY, true);
		}
		// Copy EPackage URIs from existing metamodel to the constructed metamodel
		copyEPackageURI(ePackage, outermostPackage);

		resource.getContents().add(outermostPackage);
	}
	
	private final boolean isExported(final String exported) {
		return !"false".equals(exported);
	}
	
	protected String getProjectName(final Node node) {
		return lookupAttribute(node, MOFLON_TREE_ATTRIBUTE_NAME);
	}

	protected String getEcoreFileName(final Node node) {
		final String name = lookupAttribute(node, MOFLON_TREE_ATTRIBUTE_NAME);
		return MoflonUtil.lastCapitalizedSegmentOf(name);
	}

	protected URI getProjectRelativeMetamodelURI(final Node node) {
		URI uri = URI.createURI("model/" + getEcoreFileName(node) + ".ecore");
		if (MOCA_TREE_ATTRIBUTE_INTEGRATION_PROJECT.equals(node.getName())) {
			uri = uri.trimFileExtension().appendFileExtension("pre.ecore");
		}
		return uri;
	}

	protected URI getDefaultNamespace(final Node node) {
		// TODO gervarro: This is a temporary solution as EA unfortunately generates platform:/plugin/ values as defaults
		// into the Moca tree, although the new build process relies on the knowledge whether a NsUri has been explicitly set in EA or not.
		//
		// If EA did not generate default URIs into the persisted Moca tree in the future, then
		// this method could be reverted (to the original code in the comment below) and used to determine default URI
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//	final IProject project =
		//			ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName(node));
		//	final URI projectURI = CodeGeneratorPlugin.lookupProjectURI(project);
		final URI projectURI = URI.createPlatformPluginURI(getProjectName(node) + "/", true);
		return getProjectRelativeMetamodelURI(node).resolve(projectURI);
	}

	private final boolean isUserDefined(URI namespaceURI) {
		return !defaultNamespaceURI.equals(namespaceURI);
	}
	
	private final int getDependencyType(URI namespaceURI) {
		if (isUserDefined(namespaceURI)) {
			return USER_DEFINED;
		}
		return CodeGeneratorPlugin.getDependencyType(namespaceURI);
	}
	
	protected static final String lookupAttribute(final Node node,
			final String attributeName) {
		for (final Attribute attribute : node.getAttribute()) {
			if (attributeName.equals(attribute.getName())) {
				return attribute.getValue();
			}
		}
		return null;
	}

	private final void setEPackageURI(final EPackage ePackage) {
		URI uri = EcoreUtil.getURI(ePackage);
		if (ePackage instanceof InternalEObject && ((InternalEObject) ePackage).eDirectResource() != null) {
			uri = uri.trimFragment();
		}
		ePackage.setNsURI(uri.toString());
		for (final EPackage subPackage : ePackage.getESubpackages()) {
			setEPackageURI(subPackage);
		}
	}

	private final void copyEPackageURI(final EPackage source,
			final EPackage target) {
		target.setNsURI(source.getNsURI());
		for (EPackage sourceSubpackage : source.getESubpackages()) {
			boolean matchFound = false;
			for (EPackage targetSubpackage : target.getESubpackages()) {
				if (sourceSubpackage.getName().equals(
						targetSubpackage.getName())) {
					copyEPackageURI(sourceSubpackage, targetSubpackage);
					matchFound = true;
					break;
				}
			}
			if (!matchFound) {
				MOCA_TO_MOFLON_TRANSFORMATION_LOGGER.warn("Unable to find a match for subpackage: " + sourceSubpackage + " in " + target);
			}
		}
	}
	
	private final IStatus handleResourceLoadingException(final WrappedException e, final URI namespaceURI) {
		final Throwable cause = e.getCause();
		if (cause instanceof CoreException) {
			throw e;
		} else {
			return new Status(IStatus.ERROR, CoreActivator.getModuleID(), 
					"Error while loading resource at " + namespaceURI.toString(), cause);
		}
	}
}
