package org.moflon.democles.reachability.javabdd;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.compiler.CompilerPattern;

/**
 * Parent interface for all reachability analyzers in eMoflon.
 * 
 * @author Roland Kluge - Initial implementation
 */
public interface ReachabilityAnalyzer extends org.gervarro.democles.plan.ReachabilityAnalyzer
{
   /**
    * This method performs the reachability analysis.
    */
   void analyzeReachability(final CompilerPattern compilerPattern);

   /**
    * Returns whether the given adornment can in principle be fulfilled using the provided operations
    * 
    * @precondition Call {@link #analyzeReachability()} before.
    */
   @Override
   boolean isReachable(final Adornment adornment);

}
