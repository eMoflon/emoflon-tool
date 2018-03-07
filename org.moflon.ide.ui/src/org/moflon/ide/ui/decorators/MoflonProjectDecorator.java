package org.moflon.ide.ui.decorators;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;

public class MoflonProjectDecorator extends LabelProvider implements ILightweightLabelDecorator {
	public static final String DECORATOR_ID = "org.moflon.ide.ui.decorators.MoflonProjectDecorator";

	private static final String REQUIRES_REBUILD_PREFIX = "[outdated] ";

	private static final String MANUAL_REBUILD_REQUIRED_PREFIX = "[no-auto] ";

	private Set<IProject> metamodelProjectsRequiringRebuild = new HashSet<>();

	private Set<IProject> projectsWithoutAutobuild = new HashSet<>();

	/**
	 * Prefixes the project name of an eMoflon project with {@link IS_DIRTY_PREFIX}.
	 * 
	 * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object,
	 *      org.eclipse.jface.viewers.IDecoration)
	 */
	@Override
	public void decorate(final Object element, final IDecoration decoration) {
		if (metamodelProjectsRequiringRebuild.contains(element))
			decoration.addPrefix(REQUIRES_REBUILD_PREFIX);
		if (projectsWithoutAutobuild.contains(element))
			decoration.addPrefix(MANUAL_REBUILD_REQUIRED_PREFIX);
	}

	public void setMetamodelProjectRequiresRebuild(final IProject project, final boolean needsRebuild) {
		boolean changed = false;
		if (needsRebuild)
			changed |= metamodelProjectsRequiringRebuild.add(project);
		else
			changed |= metamodelProjectsRequiringRebuild.remove(project);

		if (changed)
			this.fireLabelProviderChanged(new LabelProviderChangedEvent(this, project));
	}

	public void setAutobuildEnabled(final IProject project, final boolean autobuildEnabled) {
		boolean changed = false;
		if (!autobuildEnabled)
			changed |= projectsWithoutAutobuild.add(project);
		else
			changed |= projectsWithoutAutobuild.remove(project);

		if (changed)
			this.fireLabelProviderChanged(new LabelProviderChangedEvent(this, project));
	}
}
