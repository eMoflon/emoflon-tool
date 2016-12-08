package org.moflon.democles.reachability.javabdd;

/**
 * Parent interface for all reachability analyzers in eMoflon.
 * 
 * @author Roland Kluge - Initial implementation
 */
public interface ReachabilityAnalyzer extends org.gervarro.democles.plan.ReachabilityAnalyzer
{
   void analyzeReachability();
}
