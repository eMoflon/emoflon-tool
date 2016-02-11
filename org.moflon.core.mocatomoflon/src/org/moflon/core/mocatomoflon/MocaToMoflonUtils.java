package org.moflon.core.mocatomoflon;

import MocaTree.Node;

public class MocaToMoflonUtils
{
   public static final String ECLASS_NODE_NAME = "EClass";
   

   public static boolean isEClassNode(final Node node) {
      return node.getName() != null && (node.getName().equals(ECLASS_NODE_NAME));
   }
}
