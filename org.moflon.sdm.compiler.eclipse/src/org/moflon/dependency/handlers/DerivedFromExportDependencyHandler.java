package org.moflon.dependency.handlers;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.moflon.emf.codegen.dependency.Dependency;
import org.moflon.emf.codegen.dependency.PackageRemappingDependency;
import org.moflon.emf.codegen.dependency.SimpleDependency;

/**
 * A dependency handler which extracts "dependencies" from moflon.properties.xmi
 */
public class DerivedFromExportDependencyHandler implements DependencyHandler {

	public Collection<Dependency> getEcoreDependencies(IProject project) {
		return DependencyHandler.getDependencies(project, properties -> properties.getDependencies(),
				dep -> new PackageRemappingDependency(URI.createURI(dep), true, false));
	}

	public Collection<Dependency> getGenModelDependencies(IProject project) {
		return DependencyHandler.getDependencies(project, properties -> properties.getDependencies(),
				dep -> new SimpleDependency(URI.createURI(dep).trimFileExtension().appendFileExtension("genmodel")));
	}
}
