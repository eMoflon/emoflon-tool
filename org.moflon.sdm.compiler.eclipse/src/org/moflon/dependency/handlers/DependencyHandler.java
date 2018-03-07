package org.moflon.dependency.handlers;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.propertycontainer.PropertiesValue;
import org.moflon.emf.codegen.dependency.Dependency;

public interface DependencyHandler {
	public Collection<Dependency> getEcoreDependencies(IProject project);

	public Collection<Dependency> getGenModelDependencies(IProject project);

	public static Collection<Dependency> getDependencies(IProject project,
			Function<MoflonPropertiesContainer, Collection<? extends PropertiesValue>> extractDeps,
			Function<String, Dependency> createDependency) {
		MoflonPropertiesContainer properties = MoflonPropertiesContainerHelper.load(project, new NullProgressMonitor());
		return MoflonPropertiesContainerHelper.mapToValues(extractDeps.apply(properties)).stream().map(createDependency)
				.collect(Collectors.toSet());
	}
}
