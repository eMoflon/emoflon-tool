package org.moflon.ide.debug;

import java.util.Collection;
import java.util.stream.Collectors;

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
   
   public static Collection<TripleMatch> convertInternalTripleMatchesToEMFTripleMatches(Collection<org.moflon.tgg.algorithm.datastructures.TripleMatch> c)
   {
      return c.stream().map(m -> convertInternalTripleMatchToEMFTripleMatch(m)).collect(Collectors.toList());
   }
}
