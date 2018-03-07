package org.moflon.ide.core.runtime.builders;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

abstract class AbstractBuilder extends IncrementalProjectBuilder implements IResourceVisitor, IResourceDeltaVisitor {
	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		final SubMonitor subMon = SubMonitor.convert(monitor, getProgressBarMessage(), 2);
		cleanResource(subMon.split(1));

		getProject().deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
		monitor.worked(1);
	}

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		final SubMonitor subMon = SubMonitor.convert(monitor, getProgressBarMessage(), 4);

		getProject().deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
		subMon.worked(1);

		if (kind == FULL_BUILD) {
			// No changes -> perform full build
			processResource(subMon.split(3));
		}
		// We could return a list of projects for which the builder may want to
		// request resource deltas in the next invocation. E.g. referenced
		// projects. For now we just return null
		return null;
	}

	private String getProgressBarMessage() {
		return "Building " + getProject().getName();
	}

	abstract protected boolean processResource(IProgressMonitor monitor) throws CoreException;

	abstract protected void cleanResource(IProgressMonitor monitor) throws CoreException;

}
