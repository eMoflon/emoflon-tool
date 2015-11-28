/*
 * Created on 16.06.2004
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNode;

/**
 * @author schaal
 * */
public class SortingTreeManipulator extends AbstractTreeManipulator {

	/** see superclass implementation */
	private String myName = "Sort alphanumerically";

	/** order type #1 */
	public static final int ASCENDING = 1;

	/** order type #2 */
	public static final int DESCENDING = 2;

	/** the current order type */
	private int myOrder = 0;

	/** the current comparator used by this class */
	private final Comparator<EncapsulatingVisibleNode> myCurrentComparator;

	/** ascending alphanumeric comparator */
	static final Comparator<EncapsulatingVisibleNode> ASCENDING_ALPHANUMERIC_COMPERATOR = new Comparator<EncapsulatingVisibleNode>() {

		@Override
		public int compare(final EncapsulatingVisibleNode o1,
				final EncapsulatingVisibleNode o2) {
			return o1.getNestedNode().toString()
					.compareTo(o2.getNestedNode().toString());
		}
	};

	/** descending alphanumeric comparator */
	static final Comparator<EncapsulatingVisibleNode> DESCENDING_ALPHANUMERIC_COMPERATOR = new Comparator<EncapsulatingVisibleNode>() {

		@Override
		public int compare(final EncapsulatingVisibleNode o1,
				final EncapsulatingVisibleNode o2) {
			return o2.getNestedNode().toString()
					.compareTo(o1.getNestedNode().toString());
		}
	};

	/**
	 * constructor.
	 * 
	 * @param aOrder
	 *            the order to use
	 */
	public SortingTreeManipulator(final int aOrder) {
		myOrder = aOrder;

		switch (myOrder) {
			case ASCENDING :
				myName += " ascending";
				myCurrentComparator = ASCENDING_ALPHANUMERIC_COMPERATOR;
				break;
			case DESCENDING :
				myName += " descending";
				myCurrentComparator = DESCENDING_ALPHANUMERIC_COMPERATOR;
				break;
			default :
				myName += " ascending";
				myOrder = ASCENDING;
				myCurrentComparator = ASCENDING_ALPHANUMERIC_COMPERATOR;
				break;
		}
	}

	/**
	 * returns the name of this TreeManipulator.
	 */
	@Override
	public String getName() {
		return myName;
	}

	/**
	 * default constructor uses ascending order.
	 */
	public SortingTreeManipulator() {
		this(ASCENDING);
	}

	/**
	 * see super implementation
	 */
	@Override
	public void manipulate() {
		if (getVisibleTree() == null)
			return;
		if (isEnabled())
			setVisibleTree(sort(getVisibleTree()));

	}

	/**
	 * sorts tree in myCurrentComparator order.
	 * 
	 * @param aNode
	 *            the root of the new sorted subtree
	 * @return new sorted subtree
	 */
	private VisibleNode sort(final VisibleNode aNode) {
		VisibleNode result = aNode.clone();
		result.getNestedNode().setID(aNode.getNestedNode().getID());
		Vector<VisibleNode> childs = aNode.getChildren();

		if (childs != null) {
			Collections.sort(childs, getCurrentComparator());
			Iterator<VisibleNode> iter = childs.iterator();

			while (iter.hasNext()) {
				result.add(sort(iter.next()));
			}
		}
		return result;

	}

	/**
	 * returns the current comparator.
	 * 
	 * @return myCurrentComparator
	 */
	private Comparator getCurrentComparator() {
		return myCurrentComparator;
	}
}