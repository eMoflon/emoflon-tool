package de.fhg.iao.matrixbrowser.context;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.fhg.iao.matrixbrowser.model.elements.Node;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SortingTreeSynthesizer extends DefaultTreeSynthesizer {

	/** ascending order */
	public static final int ASCENDING = 1;

	/** descending order */
	public static final int DESCENDING = 2;

	private int order = 0;

	static final Comparator<Object> ALPHANUMERIC_COMPERATOR = new Comparator<Object>() {

		@Override
      public int compare(final Object o1, final Object o2) {
			if ((o1 instanceof Node) & (o2 instanceof Node)) {
				return o1.toString().compareTo(o2.toString());
			} else {
				return 0;
			}
		}
	};

	static final Comparator<Object> REVERSE_ALPHANUMERIC_COMPERATOR = new Comparator<Object>() {

		@Override
      public int compare(final Object o1, final Object o2) {
			if ((o1 instanceof Node) & (o2 instanceof Node)) {
				return o2.toString().compareTo(o1.toString());
			} else {
				return 0;
			}
		}
	};

	/**
	 * @param aManager
	 */
	protected SortingTreeSynthesizer(final TreeManager aManager) {
		super(aManager);
	}

	protected SortingTreeSynthesizer() {
		super();
	}

	/**
	 * return name of synthesizer
	 */
	@Override
   public String getSynthesizerName() {
		switch (order) {
		case ASCENDING:
			return "Sort alphanumeric ascending";
		case DESCENDING:
			return "Sort alphanumeric descending";
		default:
			return "Sort alphanumeric ascending";
		}
	}

	/**
	 * returns all children sorted in specified order
	 */
	@Override
   public Collection<Node> getChildren(final Node aNode) {
		List<Node> children = (List<Node>) super.getChildren(aNode);
		switch (order) {
		case ASCENDING:
			Collections.sort(children, ALPHANUMERIC_COMPERATOR);
			break;
		case DESCENDING:
			Collections.sort(children, REVERSE_ALPHANUMERIC_COMPERATOR);
			break;
		default:
			Collections.sort(children, ALPHANUMERIC_COMPERATOR);
			break;
		}
		return children;
	}

	public void setOrder(final int myOrder) {
		this.order = myOrder;
	}
}