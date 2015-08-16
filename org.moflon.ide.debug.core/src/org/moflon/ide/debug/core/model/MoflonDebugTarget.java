package org.moflon.ide.debug.core.model;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jdt.internal.debug.core.breakpoints.JavaBreakpoint;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.debug.core.Activator;
import org.moflon.ide.debug.core.model.phases.addition.AdditionThread;
import org.moflon.ide.debug.core.model.phases.deletion.DeletionThread;
import org.moflon.ide.debug.core.model.phases.init.InitPhaseThread;
import org.moflon.ide.debug.core.model.phases.translation.TranslationThread;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;

import org.moflon.tgg.debug.language.AbstractPhase;
import org.moflon.tgg.debug.language.AdditionPhase;
import org.moflon.tgg.debug.language.DebugModel;
import org.moflon.tgg.debug.language.DeletionPhase;
import org.moflon.tgg.debug.language.InitializationPhase;
import org.moflon.tgg.debug.language.TranslationPhase;

@SuppressWarnings("restriction")
public class MoflonDebugTarget extends JDIDebugTarget // MoflonDebugElement implements IDebugTarget
{
   public static final String DEBUG_INIT_XMI = "debug.init.xmi";

   private DebugModel configuration;

   // private IThread[] threads = new IThread[0];

   // private MoflonThread thread;

   // private ILaunch launch;

   public MoflonDebugTarget(ILaunch launch, VirtualMachine vm)
   {
      super(launch, vm, null, true, true, null, true);

      // super(null);
      // this.launch = launch;
      // thread = new MoflonThread(this, "Moflon Debug Thread");
      // threads = new IThread[] { new InitPhaseThread(this), new MoflonThread(this,
      // "II Deletion Phase - Moflon Debug Thread"),
      // new MoflonThread(this, "III Addition Phase- Moflon Debug Thread"), new MoflonThread(this,
      // "IV Deletion by Addition Phase - Moflon Debug Thread"),
      // new MoflonThread(this, "V Translation Phase - Moflon Debug Thread"), new MoflonThread(this,
      // "VI Finalization Phase - Moflon Debug Thread") };
   }

   public JDIThread findThread(Class<? extends AbstractPhase> phase)
   {
      for (IThread t : getThreads())
      {
         MoflonThread mt = (MoflonThread) t;

         if (phase.isInstance(mt.getPhase()))
         {
            return mt;
         }
      }
      return null;
   }

   // @Override
   // public boolean canTerminate()
   // {
   // // TODO Auto-generated method stub
   // return true;
   // }
   //
   // @Override
   // public boolean isTerminated()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }

   // @Override
   // public void terminate() throws DebugException
   // {
   //
   // System.out.println("Terminate");
   // }

   public void deleteBreakpoints() throws CoreException
   {
     Iterator<IBreakpoint> iterator = getJavaDebugTarget().getBreakpoints().iterator();
      synchronized (iterator)
      {
         while (iterator.hasNext())
         {
            IBreakpoint delete = iterator.next();
            if (delete instanceof JavaBreakpoint)
            {
               JavaBreakpoint jbp = (JavaBreakpoint) delete;
               if (jbp.getBreakpointListeners().length > 0
                     && jbp.getBreakpointListeners()[0].equals(MoflonThread.ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER))
               {
                  iterator.remove();
                  delete.delete();
               }
            }
         }
      }
   }

   @Override
   protected void terminated()
   {
      try
      {
         deleteBreakpoints();
      } catch (CoreException e)
      {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
      super.terminated();
   }

   @Override
   public boolean canResume()
   {
      return super.canResume();
      // TODO Auto-generated method stub
      // return false;
   }

   @Override
   public boolean canSuspend()
   {
      return super.canSuspend();
      // return false;
   }

   @Override
   public boolean isSuspended()
   {
      return super.isSuspended();
      // return true;
   }

   @Override
   public void resume() throws DebugException
   {
      super.resume();
      // resumeQuiet();
      System.out.println("resume");
   }

   @Override
   public void suspend() throws DebugException
   {
      super.suspend();
      System.out.println("suspend");
   }

   @Override
   public void breakpointAdded(IBreakpoint breakpoint)
   {
      super.breakpointAdded(breakpoint);
      // TODO Auto-generated method stub
      // System.out.println();
   }

   @Override
   public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta)
   {
      // TODO Auto-generated method stub
      System.out.println();
      super.breakpointRemoved(breakpoint, delta);
   }

   @Override
   public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta)
   {
      super.breakpointChanged(breakpoint, delta);
      // TODO Auto-generated method stub
      // System.out.println();
   }

   @Override
   public boolean canDisconnect()
   {
      return super.canDisconnect();
      // TODO Auto-generated method stub
   }

   @Override
   public void disconnect() throws DebugException
   {
      // TODO Auto-generated method stub
      super.disconnect();
   }

   @Override
   public boolean isDisconnected()
   {
      // TODO Auto-generated method stub
      // return false;
      return super.isDisconnected();
   }

   @Override
   public boolean supportsStorageRetrieval()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public IProcess getProcess()
   {
      // TODO Auto-generated method stub
      return super.getProcess();
   }

   // @Override
   // public IThread[] getThreads() // throws DebugException
   // {
   // synchronized (threads)
   // {
   // return threads;
   // }
   // }

   // @Override
   // public boolean hasThreads() // throws DebugException
   // {
   // System.out.println("hasThreads: " + (threads.length > 0));
   // return threads.length > 0;
   // }

   @Override
   public String getName() throws DebugException
   {
      return "Synchronization Algorithm - Moflon Debug Target";
   }

   @Override
   public boolean supportsBreakpoint(IBreakpoint breakpoint)
   {
      return super.supportsBreakpoint(breakpoint);
      // return false;
   }

   // @Override
   // public ILaunch getLaunch()
   // {
   // return launch;
   // }

   @Override
   public IDebugTarget getDebugTarget()
   {
      return this;
   }

   @Override
   protected void initializeState()
   {
      String path = null;
      try
      {
         path = FileLocator.toFileURL(Platform.getBundle(Activator.getDefault().getPluginId()).getResource(DEBUG_INIT_XMI)).getFile();
      } catch (IOException e)
      {
         e.printStackTrace();
      }
      configuration = (DebugModel) eMoflonEMFUtil.loadModel(path);

      // if (isResumeOnStartup())
      // {
      // try
      // {
      // suspend();
      // } catch (DebugException e)
      // {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      // }

      ThreadReference tr = getVM().allThreads().stream().filter(t -> t.name().equals("main")).findFirst().get();
      // fireCreationEvent();
      // fireCreationEvent();
      // createThread(tr);
      createThread(tr);
      // createThread(tr);
      // createThread(tr);
      // createThread(tr);
      super.initializeState();
   }

   int index = 0;

   @Override
   protected JDIThread newThread(ThreadReference reference)
   {
      if (index == 0 && reference.name().equals("main"))
      {
         index++;
         return new InitPhaseThread(this, configuration.getPhases().stream().filter(p -> p instanceof InitializationPhase).findFirst().get());
      }
      switch (index)
      {
      case 1:
         index++;
         return new DeletionThread(this, "II Deletion Phase", configuration.getPhases().stream().filter(p -> p instanceof DeletionPhase).findFirst().get());
      case 2:
         index++;
         return new AdditionThread(this, "III Addition Phase", configuration.getPhases().stream().filter(p -> p instanceof AdditionPhase).findFirst().get());
         // case 3:
         // index++;
         // return new MoflonThread(this, "IV Deletion by Addition Phase");
      case 3:
         index++;
         System.err.println("DER THREAD");
         return new TranslationThread(this, "IV Translation Phase", configuration.getPhases().stream().filter(p -> p instanceof TranslationPhase).findFirst()
               .get());
         // case 5:
         // index++;
         // return new MoflonThread(this, "VI Finalization Phase");
      default:
         return null;
      }
   }

}
