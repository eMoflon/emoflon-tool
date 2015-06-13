package org.moflon.ide.debug;

import org.moflon.tgg.algorithm.datastructures.SynchronizationProtocol;

import TGGRuntime.TripleMatch;

public class DebugSynchronizationProtocolHelper extends SynchronizationProtocol
{

   public static TripleMatch convertInternalTripleMatchToEMFTripleMatch(org.moflon.tgg.algorithm.datastructures.TripleMatch m)
   {
      return m == null ? null : new DebugSynchronizationProtocolHelper().toEMF(m);
   }

   public static org.moflon.tgg.algorithm.datastructures.TripleMatch convertEMFTripleMatchToInternalTripleMatch(TripleMatch m)
   {
      return m == null ? null : new DebugSynchronizationProtocolHelper().fromEMF(m);
   }
}
