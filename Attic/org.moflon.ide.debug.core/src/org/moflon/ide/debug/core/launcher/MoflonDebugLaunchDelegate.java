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
package org.moflon.ide.debug.core.launcher;

import java.io.IOException;
import java.net.ServerSocket;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.debug.core.Activator;
import org.moflon.ide.debug.core.breakpoints.BreakpointFactory;
import org.moflon.ide.debug.core.breakpoints.PhaseBreakpoint;
import org.moflon.ide.debug.core.model.MoflonDebugTarget;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import org.moflon.tgg.debug.language.AbstractPhase;
import org.moflon.tgg.debug.language.Breakpoint;
import org.moflon.tgg.debug.language.DebugModel;

/**
 * Launches eMoflon - {@link SynchronizationHelper} program on a TGG engine written in Java
 */
@SuppressWarnings("restriction")
public class MoflonDebugLaunchDelegate extends LaunchConfigurationDelegate
{
   protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

   protected static final String LOCALHOST = "127.0.0.1";

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.
    * eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch,
    * org.eclipse.core.runtime.IProgressMonitor)
    */
   public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException
   {
      String projectName = configuration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, EMPTY_STRING);
      String mainTypeName = configuration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, EMPTY_STRING);
      int aFreePort = findFreePort();

      ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
      ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
      final ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, projectName);

      // The projects configuration should have all necessary entries
      workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
      workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, mainTypeName);

      // In addition add "-verbose:class" for more detailed class loading related information
      workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, "-XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=dumponexit=true,defaultrecording=true,disk=true,maxage=2d,settings=profile,dumponexitpath=synchronizer.jfr,repository=jfr_temp_synchronizer -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=" + aFreePort);

      // If you like, create arguments for call to main
      // workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, "test");
      // workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, "-Xdebug");

      // DebugUITools.launch(workingCopy, ILaunchManager.RUN_MODE);
      workingCopy.launch(ILaunchManager.RUN_MODE, monitor);
      VirtualMachine vm = null;
      try
      {
         vm = JDIUtil.connecting(LOCALHOST, aFreePort);
      } catch (IOException | IllegalConnectorArgumentsException e)
      {
         e.printStackTrace();
      }

      // Initialize the source locator configured in plugin.xml with the working copy launch configuration
      ISourceLocator sourcelocator = launch.getSourceLocator();
      if (sourcelocator instanceof AbstractSourceLookupDirector)
      {
         AbstractSourceLookupDirector asld = (AbstractSourceLookupDirector) sourcelocator;
         asld.initializeDefaults(workingCopy);
      }
      MoflonDebugTarget target = new MoflonDebugTarget(launch, vm);
      launch.addDebugTarget(target);

      // Set Breakpoints
      String path = null;
      try
      {
         path = FileLocator.toFileURL(Platform.getBundle(Activator.getDefault().getPluginId()).getResource(MoflonDebugTarget.DEBUG_INIT_XMI)).getFile();
      } catch (IOException e)
      {
         e.printStackTrace();
      }

      // load debugger configuration
      DebugModel config = (DebugModel) eMoflonEMFUtil.loadModel(path);

      // register all breakpoints of all phases
      for (AbstractPhase phase : config.getPhases())
      {
         for (Breakpoint bp : phase.getBreakpoints())
         {
            try
            {
               DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(target);
               PhaseBreakpoint pbp = BreakpointFactory.createBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), bp, phase);
               DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(pbp);

            } catch (CoreException e)
            {
               e.printStackTrace();
            }
         }
      }

   }

   @Override
   public boolean buildForLaunch(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor) throws CoreException
   {
      // eMoflon programs do not require building.
      return false;
   }

   // TODO Implement Abort
   // /**
   // * Throws an exception with a new status containing the given message and optional exception.
   // *
   // * @param message
   // * error message
   // * @param e
   // * underlying exception
   // * @throws CoreException
   // */
   // private void abort(String message, Throwable e) throws CoreException
   // {
   // throw new CoreException(new Status(IStatus.ERROR,
   // DebugCorePlugin.getDefault().getDescriptor().getUniqueIdentifier(), 0, message, e));
   // }

   /**
    * Returns a free port number on localhost, or -1 if unable to find a free port.
    * 
    * @return a free port number on localhost, or -1 if unable to find a free port
    */
   public static int findFreePort()
   {
      ServerSocket socket = null;
      try
      {
         socket = new ServerSocket(0);
         return socket.getLocalPort();
      } catch (IOException e)
      {
      } finally
      {
         if (socket != null)
         {
            try
            {
               socket.close();
            } catch (IOException e)
            {
            }
         }
      }
      return -1;
   }
}
