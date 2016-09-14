package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.core.runtime.Plugin;

public class AttributeConstraintsOperationActivator extends Plugin
{
   // This is the delimiter used by developers of custom constraints
   public static final char INNER_ST_DELIMITER = '$';

   // This is used to generate string templates from the user-specified operations
   public static final char OUTER_ST_DELIMITER = '#'; // '@' does not work.

}
