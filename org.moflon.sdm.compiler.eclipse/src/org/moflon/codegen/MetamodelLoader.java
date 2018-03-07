package org.moflon.codegen;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.moflon.emf.codegen.dependency.Dependency;
import org.moflon.emf.codegen.dependency.PackageRemappingDependency;

public class MetamodelLoader {
	private final ResourceSet resourceSet;

	public MetamodelLoader(final ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
		this.resourceSet.setPackageRegistry(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
		this.resourceSet.setResourceFactoryRegistry(new ResourceFactoryRegistryImpl());
	}

	public Iterable<Dependency> getEcoreResourceDependencies() {
		return Collections.emptyList();
	}

	public void loadDependencies(final Iterable<Dependency> resourcesToLoad) {
		// Handle Ecore dependencies
		for (Dependency dependency : resourcesToLoad) {
			dependency.getResource(resourceSet, false);
		}
	}

	public final Resource loadResource(final Dependency resourceLoader, final boolean loadContent) {
		return resourceLoader.getResource(resourceSet, loadContent);
	}

	public Resource loadMetamodel(final URI ecoreURI) {
		// Load Ecore model (i.e., the metamodel)
		PackageRemappingDependency resourceHandler = new PackageRemappingDependency(ecoreURI, true, false);
		return loadResource(resourceHandler, false);
	}
}
