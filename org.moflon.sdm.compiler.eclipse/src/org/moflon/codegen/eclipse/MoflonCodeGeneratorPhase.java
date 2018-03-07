package org.moflon.codegen.eclipse;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.resource.Resource;
import org.moflon.codegen.MethodBodyHandler;

public interface MoflonCodeGeneratorPhase {

	public IStatus run(IProject project, Resource rsource, MethodBodyHandler methodBodyHandler,
			IProgressMonitor monitor);

}
