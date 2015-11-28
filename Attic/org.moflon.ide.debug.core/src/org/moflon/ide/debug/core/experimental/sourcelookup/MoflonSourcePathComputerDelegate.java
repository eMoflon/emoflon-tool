/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Bjorn Freeman-Benson - initial API and implementation
 *******************************************************************************/
package org.moflon.ide.debug.core.experimental.sourcelookup;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.StandardSourcePathProvider;

/**
 * Computes the default source lookup path for a PDA launch configuration. The default source lookup path is the folder
 * or project containing the PDA program being launched. If the program is not specified, the workspace is searched by
 * default.
 */
public class MoflonSourcePathComputerDelegate implements ISourcePathComputerDelegate
{

   /*
    * (non-Javadoc)
    * 
    * @see
    * org.eclipse.debug.internal.core.sourcelookup.ISourcePathComputerDelegate#computeSourceContainers(org.eclipse.debug
    * .core.ILaunchConfiguration, org.eclipse.core.runtime.IProgressMonitor)
    */
   public ISourceContainer[] computeSourceContainers(ILaunchConfiguration configuration, IProgressMonitor monitor) throws CoreException
   {
      // IWorkspace workspace = ResourcesPlugin.getWorkspace();
      // workspace.getRoot().getProjects();
      // IProject project = workspace.getRoot().getProject("LearningBoxToDictionaryIntegration");
      // IJavaProject javaProject = JavaCore.create(project);
      // return new ISourceContainer[] { new JavaProjectSourceContainer(javaProject) };

      // String path = configuration.getAttribute(DebugCorePlugin.ATTR_PDA_PROGRAM, (String) null);
      // ISourceContainer sourceContainer = null;
      // if (path != null)
      // {
      // IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(path));
      // if (resource != null)
      // {
      // IContainer container = resource.getParent();
      // if (container.getType() == IResource.PROJECT)
      // {
      // sourceContainer = new ProjectSourceContainer((IProject) container, false);
      // } else if (container.getType() == IResource.FOLDER)
      // {
      // sourceContainer = new FolderSourceContainer(container, false);
      // }
      // }
      // }
      // if (sourceContainer == null)
      // {
      // sourceContainer = new WorkspaceSourceContainer();
      // }
      // // return new ISourceContainer[] { sourceContainer };
      //
      // // IRuntimeClasspathEntry[] entries = JavaRuntime.computeUnresolvedSourceLookupPath(configuration);
      // // IRuntimeClasspathEntry[] resolved = JavaRuntime.resolveSourceLookupPath(entries, configuration);
      // IRuntimeClasspathEntry[] entries = JavaRuntime.computeUnresolvedRuntimeClasspath(configuration);
      // IRuntimeClasspathEntry[] resolved = JavaRuntime.resolveSourceLookupPath(entries, configuration);
      // RuntimeClasspathProvider rcp = new RuntimeClasspathProvider(configuration);

      StandardSourcePathProvider sspp = new StandardSourcePathProvider();
      IRuntimeClasspathEntry[] entries = sspp.computeUnresolvedClasspath(configuration);
      entries = sspp.resolveClasspath(entries, configuration);
      return JavaRuntime.getSourceContainers(entries);
      // return JavaRuntime.getSourceContainers(resolved);
   }
}
