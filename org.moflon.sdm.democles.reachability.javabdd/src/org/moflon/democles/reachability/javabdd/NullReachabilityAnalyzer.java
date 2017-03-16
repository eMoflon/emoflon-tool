package org.moflon.democles.reachability.javabdd;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.compiler.CompilerPattern;

/**
 * Implementation of {@link ReachabilityAnalyzer} that always returns true.
 * 
 * @author Roland Kluge - Initial implementation
 *
 */
public class NullReachabilityAnalyzer implements ReachabilityAnalyzer
{

   @Override
   public boolean isReachable(Adornment adornment)
   {
      return true;
   }

   @Override
   public void analyzeReachability(final CompilerPattern pattern)
   {
      // nop
   }

}
