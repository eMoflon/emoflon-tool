package org.moflon.ide.debug.core.breakpoints;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.DebugException;

import DebugLanguage.AbstractPhase;
import DebugLanguage.DeletionBreakpoint;

public class DeletionPhaseBreakpoint extends PhaseBreakpoint
{
   private DeletionBreakpoint dbp;

   public DeletionPhaseBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount, boolean add,
         Map<String, Object> attributes, Class<? extends AbstractPhase> phase, DeletionBreakpoint dbp) throws DebugException
   {
      super(resource, typeName, lineNumber, charStart, charEnd, hitCount, add, attributes, phase);
      this.dbp = dbp;
   }

   public DeletionBreakpoint getBreakpoint()
   {
      return dbp;
   }

}
