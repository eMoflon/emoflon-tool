package org.moflon.ide.debug.core.breakpoints;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.DebugException;
import org.moflon.tgg.algorithm.synchronization.DebugBreakpoint.Phase;

import org.moflon.tgg.debug.language.AbstractPhase;
import org.moflon.tgg.debug.language.TranslationBreakpoint;

public class TranslationPhaseBreakpoint extends PhaseBreakpoint
{
   private String subphase;

   private TranslationBreakpoint tbp;

   public TranslationPhaseBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount, boolean add,
         Map<String, Object> attributes, Class<? extends AbstractPhase> phase, TranslationBreakpoint tbp) throws DebugException
   {
      super(resource, typeName, lineNumber, charStart, charEnd, hitCount, add, attributes, phase);
      subphase = tbp.getSubPhase();
      this.tbp = tbp;
   }

   public Phase getSubphase()
   {
      return Phase.valueOf(subphase);
   }

   public TranslationBreakpoint getBreakpoint()
   {
      return tbp;
   }

}
