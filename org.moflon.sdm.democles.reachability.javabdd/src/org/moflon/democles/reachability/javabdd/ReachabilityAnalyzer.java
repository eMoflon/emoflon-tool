package org.moflon.democles.reachability.javabdd;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.compiler.CompilerPattern;

/**
 * Parent interface for all reachability analyzers in eMoflon.
 * 
 * @author Roland Kluge - Initial implementation
 */
public interface ReachabilityAnalyzer {
	/**
	 * Returns whether the given adornment can in principle be fulfilled using the
	 * operations of the given {@link CompilerPattern}
	 * 
	 * @return whether a search plan for the given pattern exists
	 */
	boolean analyzeReachability(final CompilerPattern compilerPattern, final Adornment adornment);
}
