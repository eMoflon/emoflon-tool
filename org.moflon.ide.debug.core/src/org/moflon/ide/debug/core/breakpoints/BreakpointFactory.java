package org.moflon.ide.debug.core.breakpoints;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.internal.debug.ui.BreakpointUtils;

import DebugLanguage.AbstractPhase;
import DebugLanguage.Breakpoint;
import DebugLanguage.DeletionBreakpoint;
import DebugLanguage.DeletionPhase;
import DebugLanguage.TranslationBreakpoint;
import DebugLanguage.TranslationPhase;

@SuppressWarnings("restriction")
public class BreakpointFactory
{
   private static final String ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER = "org.moflon.ide.debug.core.MoflonBreakpointListener";

   public static PhaseBreakpoint createBreakpoint(IResource resource, Breakpoint bp, AbstractPhase phase) throws CoreException
   {
      if (phase instanceof TranslationPhase && bp instanceof TranslationBreakpoint)
      {
         return createTranslationBreakpoint((TranslationBreakpoint) bp, phase);
      } else if (phase instanceof DeletionPhase && bp instanceof DeletionBreakpoint)
      {
         return createDeletionBreakpoint((DeletionBreakpoint) bp, phase);
      } else if (phase instanceof AbstractPhase)
      {
         return createPhaseBreakpoint(bp, phase);
      } else
      {
         return null;
      }
   }

   private static PhaseBreakpoint createDeletionBreakpoint(DeletionBreakpoint bp, AbstractPhase phase) throws CoreException
   {
      Map<String, Object> attributes = new HashMap<String, Object>(4);
      BreakpointUtils.addRunToLineAttributes(attributes);

      PhaseBreakpoint pbp = new DeletionPhaseBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), bp.getClassname(), (int) bp.getLine(), -1, -1, 1, false,
            attributes, phase.getClass(), bp);
      pbp.addBreakpointListener(ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER);
      return pbp;
   }

   private static PhaseBreakpoint createTranslationBreakpoint(TranslationBreakpoint bp, AbstractPhase phase) throws CoreException
   {
      Map<String, Object> attributes = new HashMap<String, Object>(4);
      BreakpointUtils.addRunToLineAttributes(attributes);

      PhaseBreakpoint pbp = new TranslationPhaseBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), bp.getClassname(), (int) bp.getLine(), -1, -1, 1, false,
            attributes, phase.getClass(), bp);
      pbp.addBreakpointListener(ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER);
      return pbp;
   }

   private static PhaseBreakpoint createPhaseBreakpoint(Breakpoint bp, AbstractPhase phase) throws CoreException
   {
      Map<String, Object> attributes = new HashMap<String, Object>(4);
      BreakpointUtils.addRunToLineAttributes(attributes);

      PhaseBreakpoint pbp = new PhaseBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), bp.getClassname(), (int) bp.getLine(), -1, -1, 1, false, attributes,
            phase.getClass());
      pbp.addBreakpointListener(ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER);
      return pbp;
   }
}
