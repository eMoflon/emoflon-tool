package org.moflon.democles.reachability.javabdd;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.compiler.CompilerPattern;

/**
 * Implementation of {@link ReachabilityAnalyzer} that always returns true.
 * 
 * @author Roland Kluge - Initial implementation
 *
 */
public class NullReachabilityAnalyzer implements ReachabilityAnalyzer {

	/**
	 * Always true.
	 */
	@Override
	public boolean analyzeReachability(final CompilerPattern pattern, final Adornment adornment) {
		return true;
	}

}
