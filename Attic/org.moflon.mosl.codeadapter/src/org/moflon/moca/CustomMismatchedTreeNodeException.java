package org.moflon.moca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.runtime.tree.TreeNodeStream;

public class CustomMismatchedTreeNodeException extends
		org.antlr.runtime.MismatchedTreeNodeException {
	private static final long serialVersionUID = -2296360319200051761L;

	public CustomMismatchedTreeNodeException(int expecting, TreeNodeStream input) {
		super(expecting, input);

		extractHierarchy();
	}
	
	@Override
	public String toString() {
		return super.toString() + "\n" + extractHierarchy();
	}

	private String extractHierarchy() {
		List<StackTraceElement> el = new ArrayList<StackTraceElement>(
				getStackTrace().length);
		for (StackTraceElement e : getStackTrace()) {
			if (e.getClassName().endsWith("TreeGrammar")) {
				el.add(e);
			}
		}

		Collections.reverse(el);
		StringBuilder ruleHierachy = new StringBuilder();
		for (int i = 0; i < el.size(); i++) {
			ruleHierachy.append(el.get(i).getMethodName());
			if (i < el.size() - 1) {
				ruleHierachy.append(" -> ");
			}
		}
		System.err.println(ruleHierachy);
		return ruleHierachy.toString();
	}

	@Override
	protected void extractInformationFromTreeNodeStream(IntStream arg0) {
		TreeNodeStream nodes = (TreeNodeStream) input;
		this.node = nodes.LT(1);
		TreeAdaptor adaptor = nodes.getTreeAdaptor();
		
		
		Object o = adaptor.getParent(this.node);
		System.err.println(o);
		for (int i = 0; i < adaptor.getChildCount(o); i++) {
			System.err.println(" - " + getChild(adaptor, o, i));
		}
		
		
		Token payload = adaptor.getToken(node);
		if (payload != null) {
			this.token = payload;
			try {
				if (payload.getLine() <= 0) {
					// imaginary node; no line/pos info; scan backwards
					int i = -1;
					Object priorNode = nodes.LT(i);
					while (priorNode != null) {
						Token priorPayload = adaptor.getToken(priorNode);
						if (priorPayload != null && priorPayload.getLine() > 0) {
							// we found the most recent real line / pos info
							this.line = priorPayload.getLine();
							this.charPositionInLine = priorPayload
									.getCharPositionInLine();
							this.approximateLineInfo = true;
							break;
						}
						--i;
						priorNode = nodes.LT(i);
					}
				} else { // node created from real token
					this.line = payload.getLine();
					this.charPositionInLine = payload.getCharPositionInLine();
				}
			} catch (Exception e) {
				this.line = -1;
				this.charPositionInLine = -1;
			}
		} else if (this.node instanceof Tree) {
			this.line = ((Tree) this.node).getLine();
			this.charPositionInLine = ((Tree) this.node)
					.getCharPositionInLine();
			if (this.node instanceof CommonTree) {
				this.token = ((CommonTree) this.node).token;
			}
		} else {
			int type = adaptor.getType(this.node);
			String text = adaptor.getText(this.node);
			this.token = new CommonToken(type, text);
		}
	}

	private Object getChild(TreeAdaptor adaptor, Object o, int i) {
		Object child = adaptor.getChild(o, i); 
		if ("ATTRIBUTE".equals(child.toString())) {
			Object name = adaptor.getChild(child, 0);
			Object value = adaptor.getChild(child, 1);
			return String.format("ATTRIBUTE[name=%s,value=%s]", name, value);
		}
		return child;
	}
}
