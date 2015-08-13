package org.moflon.moca;

import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeAdaptor;

public class SafeCommonTreeNodeStream extends CommonTreeNodeStream {

	public SafeCommonTreeNodeStream(Object tree) {
		super(tree);
	}

	public SafeCommonTreeNodeStream(TreeAdaptor adaptor, Object tree) {
		super(adaptor, tree);
	}
	
	@Override
	protected Object LB(int k) {
		return null;
	}
}
