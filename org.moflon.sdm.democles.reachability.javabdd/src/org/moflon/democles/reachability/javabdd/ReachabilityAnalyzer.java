package org.moflon.democles.reachability.javabdd;

import org.gervarro.democles.common.Adornment;

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
   void analyzeReachability();
   
   /**
    * Returns whether the given adornment can in principle be fulfilled using the provided operations
    * 
    * @precondition Call {@link #analyzeReachability()} before.
    */
   @Override
   boolean isReachable(Adornment adornment);
}
