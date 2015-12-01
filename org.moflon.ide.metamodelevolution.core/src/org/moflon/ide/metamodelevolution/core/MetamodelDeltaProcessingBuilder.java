package org.moflon.ide.metamodelevolution.core;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.builders.AbstractBuilder;

public class MetamodelDeltaProcessingBuilder extends AbstractBuilder {

	// Is invoked for each modified/new/deleted resource in incremental model
	@Override
	public boolean visit(final IResource resource) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean visit(final IResourceDelta delta) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	// Is invoked if a FULL_BUILD is triggered
	@Override
	protected boolean processResource(final IProgressMonitor monitor) throws CoreException {
		//ResourcesPlugin.getWorkspace().getRoot().getProjects();
		
		Collection<IProject> allProjects =  WorkspaceHelper.getProjectsByNatureID("org.eclipse.jdt.core.javanature"); //TODO@settl : Use JavaCore.NATURE_ID
		for (IProject p : allProjects) {
			// get compiler errors of javaProject
		/*javaProject.get
			IType iType = javaProject.findType(classFullName);
			ICompilationUnit iUnit = iType.getCompilationUnit();*/

		}
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			
			@Override
			public void run(final IProgressMonitor monitor) throws CoreException {
				// TODO Auto-generated method stub
				
			}
		}, null, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());  //allgemein progressmonitore ignorieren, nullprogressmonitor verwenden
		return false;
	}

	// Is invoked if a CLEAN_BUILD is triggered
	@Override
	protected void cleanResource(final IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		
	}

}
