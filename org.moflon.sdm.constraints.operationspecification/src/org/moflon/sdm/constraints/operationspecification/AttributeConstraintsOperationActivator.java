package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.core.runtime.Plugin;

/**
 * Activator of this bundle.
 * 
 * @author Frederik Deckwerth - Initial implementation
 * @author Roland Kluge
 */
public class AttributeConstraintsOperationActivator extends Plugin {
	/**
	 * This is the delimiter used by developers of custom constraints
	 * 
	 * For instance, the {@link OperationSpecification} for the constraint 'max'
	 * with adornment FBB could look like <code>$a$ = Math.max($b$,$c$)</code>.
	 * Similarly, with adornment BBB <code>$a$ == Math.max($b$,$c$)</code>. (We
	 * assume that the constraint parameters are called a, b, c,... (as by default).
	 */
	public static final char INNER_ST_DELIMITER = '$';

	/**
	 * This is used to generate string templates from the user-specified operations
	 */
	public static final char OUTER_ST_DELIMITER = '#'; // '@' does not work.

}
