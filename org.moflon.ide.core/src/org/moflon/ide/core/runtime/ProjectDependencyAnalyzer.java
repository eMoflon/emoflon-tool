package org.moflon.ide.core.runtime;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl.URIMap;
import org.gervarro.eclipse.task.ITask;
import org.moflon.core.build.AbstractVisitorBuilder;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

public class ProjectDependencyAnalyzer implements ITask {
	private final AbstractVisitorBuilder builder;

	private final IProject metamodelProject;

	private final IProject moflonProject;

	private final EPackage metamodelRoot;

	private final Set<IProject> interestingProjects = new TreeSet<IProject>(MetamodelBuilder.DEFAULT_PROJECT_COMPARATOR);

	public ProjectDependencyAnalyzer(final AbstractVisitorBuilder builder, final IProject metamodelProject,
			final IProject moflonProject, final EPackage metamodelRoot) {
		this.builder = builder;
		this.metamodelProject = metamodelProject;
		this.moflonProject = moflonProject;
		this.metamodelRoot = metamodelRoot;
	}

	public static final void analyzeDependencies(final MultiStatus status, final TreeSet<IProject> projectReferences,
			final Resource resource) {
		final URIMap uriMap = (URIMap) resource.getResourceSet().getURIConverter().getURIMap();
		for (TreeIterator<EObject> j = resource.getAllContents(); j.hasNext();) {
			EObject eObject = j.next();
			if (eObject instanceof EDataType) {
				j.prune();
			}
			for (EObject eCrossReference : eObject.eCrossReferences()) {
				if (eCrossReference instanceof EClass) {
					final EPackage referencedEPackage = ((EClassifier) eCrossReference).getEPackage();
					if (referencedEPackage != null && resource != referencedEPackage.eResource()) {
						final URI uri = getResolvedPlatformResourceURI(uriMap, referencedEPackage.eResource().getURI());
						if (uri.isPlatformResource() && uri.segmentCount() >= 2) {
							final IProject project = eMoflonEMFUtil.getWorkspaceProject(uri);
							if (project != null) {
								projectReferences.add(project);
							} else {
								status.add(new Status(IStatus.ERROR,
										WorkspaceHelper.getPluginId(ProjectDependencyAnalyzer.class),
										"Project " + uri.segment(1) + " cannot be found in the workspace"));
							}
						}
					}
				}
			}
		}
	}

	public final void setInterestingProjects(final Collection<IProject> projects) {
		interestingProjects.addAll(projects);
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		final MultiStatus status = new MultiStatus(WorkspaceHelper.getPluginId(getClass()), 0,
				"Project dependency analysis failed", null);
		final TreeSet<IProject> projectReferences = new TreeSet<IProject>(MetamodelBuilder.DEFAULT_PROJECT_COMPARATOR);
		analyzeDependencies(status, projectReferences, metamodelRoot.eResource());

		for (IProject reference : projectReferences) {
			if (interestingProjects.contains(reference)) {
				builder.addTriggerProject(reference);
			}
		}
		if (!status.isOK()) {
			return status;
		}

		try {
			final LinkedList<IBuildConfiguration> buildConfigs = new LinkedList<IBuildConfiguration>();
			if (metamodelProject != moflonProject) {
				buildConfigs.add(metamodelProject.getBuildConfig(IBuildConfiguration.DEFAULT_CONFIG_NAME));
			}
			for (IProject project : projectReferences) {
				if (project.isAccessible()) {
					buildConfigs.add(project.getBuildConfig(IBuildConfiguration.DEFAULT_CONFIG_NAME));
				}
			}
			final IProjectDescription description = moflonProject.getDescription();
			IBuildConfiguration[] buildConfigArray = new IBuildConfiguration[buildConfigs.size()];
			buildConfigArray = buildConfigs.toArray(buildConfigArray);
			description.setBuildConfigReferences(IBuildConfiguration.DEFAULT_CONFIG_NAME, buildConfigArray);
			moflonProject.setDescription(description, monitor);
		} catch (CoreException e) {
			return new Status(IStatus.WARNING, WorkspaceHelper.getPluginId(getClass()),
					"Unable to set build configuration references for project " + moflonProject.getName(), e);
		}
		return Status.OK_STATUS;
	}

	@Override
	public String getTaskName() {
		return "Analyzing project dependency";
	}

	private static final URI getResolvedPlatformResourceURI(final URIMap uriMap, URI uri) {
		for (URI remappedURI = uriMap.getURI(uri); uri != remappedURI && !uri.isPlatformResource();) {
			uri = remappedURI;
			remappedURI = uriMap.getURI(uri);
		}
		return uri;
	}
}
