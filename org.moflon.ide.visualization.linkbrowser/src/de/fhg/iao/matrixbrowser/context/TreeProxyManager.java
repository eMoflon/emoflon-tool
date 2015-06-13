/*
 * Created on 04.06.2004
 */
package de.fhg.iao.matrixbrowser.context;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;

/**
 * This class handles the tree manipulators used for influencing the node
 * display of the tree
 */
public class TreeProxyManager extends TreeSynthesizerProxy {

	/** flag that indicates wether a change occured or not */
	// private boolean statusChanged = true;
	/** flag to see if TreeProxyManager is initialised */
	// private boolean isInitialized = false;
	/** menu that holds items representing the different SubTreeSynthesizers */
	private JMenu myContextMenu = null;

	/**
	 * the saved visibleTree; if no TreeModelChanged event occured, use this one
	 * instead of generating everything again in getVisibleTree()
	 */
	private VisibleNode mySavedVisibleTree = null;

	/** linked list of the manipulators used */
	private List<AbstractTreeManipulator> myManipulatorList = new LinkedList<AbstractTreeManipulator>();

	/** default tree synthesizer used for getting the original data */
	private DefaultTreeSynthesizer[] myDefaultTreeSynthesizer = null;

	private Node[] myRootNode = null;

	/**
	 * type of TreeProxyManager: is it used in horizontal or vertical treeview?
	 */
	// private int type = 0;
	public static String MENU_NAME = "Filter Settings";

	protected TreeProxyManager(TreeManager aManager) {
		setTreeManager(aManager);
		myDefaultTreeSynthesizer = new DefaultTreeSynthesizer[1];
		myDefaultTreeSynthesizer[0] = aManager.createDefaultTreeSynthesizer();

		// new Manipulator
		BuildCustomTreeManipulator myCustomManipulator = new BuildCustomTreeManipulator(
				this);
		myManipulatorList.add(myCustomManipulator);

		// new Manipulator
		SortingTreeManipulator myManipulator = new SortingTreeManipulator(
				SortingTreeManipulator.DESCENDING);
		myManipulatorList.add(myManipulator);

		// new Manipulator
		myManipulator = new SortingTreeManipulator(
				SortingTreeManipulator.ASCENDING);
		myManipulatorList.add(myManipulator);

		// new Manipulator
		AbstractTreeManipulator myHideManipulator = new HideTreeNodesManipulator(
				this);
		myManipulatorList.add(myHideManipulator);

	}

	/**
	 * returns the generated visible tree of the TreeProxyManager. This methods
	 * gets the visible tree of the first registered and enabled
	 * SubTreeSynthesizer and gives its result to the next registered and
	 * enabled SubTreeSynthesizer until, in the end, the full visible tree is
	 * generated.
	 */

	public VisibleNode getVisibleTree() {

		return getSavedVisibleTree();
	}

	public void onTreeModelChanged(TreeModelChangedEvent aE) {
	   Object source = aE.getSource();
	   if(source instanceof DefaultTreeSynthesizer){
	      DefaultTreeSynthesizer synthesizer = (DefaultTreeSynthesizer) source;
	      VisibleNode newVisibleNode = synthesizer.getVisibleSubTree(super.myVisibleTree.getNestedNode());
	      if(newVisibleNode != null){
	         super.myVisibleTree = newVisibleNode;
	         manipulateVisibleTree();
	      }
	   }
		fireTreeModelChangedEvent(aE);
	}

	/**
	 * @return Returns the savedVisibleTree.
	 */
	public VisibleNode getSavedVisibleTree() {
		return mySavedVisibleTree;
	}

	/**
	 * @param aSavedVisibleTree
	 *            The savedVisibleTree to set.
	 */
	public void setSavedVisibleTree(VisibleNode aSavedVisibleTree) {
		this.mySavedVisibleTree = aSavedVisibleTree;
	}

	/**
	 * sets the root node of the TreeProxyManager. this also sets the root nodes
	 * of all subtreesynthesizer. Refer to method documentation
	 */

	public void setRootNode(Node aRootNode) {
		super.setRootNode(aRootNode);
		// set the root node and get the original tree
		myRootNode = new Node[1];

		myRootNode[0] = aRootNode;
		setRootNode(myRootNode);
		manipulateVisibleTree();
	}

	/**
	 * sets one or more root nodes (important if more than one root node is
	 * drag'n dropped from the transport panel) as root node and build a new
	 * visible tree
	 * 
	 * @param aRootNode
	 *            the new root node(s)
	 */
	public void setRootNode(Node aRootNode[]) {
		myRootNode = new Node[aRootNode.length];
		VisibleNode tmpNode = new EncapsulatingVisibleNode(new MBModelNode(
				"tmp"));
		DefaultTreeSynthesizer []newDefaultTreeSynthesizer = new DefaultTreeSynthesizer[aRootNode.length];
		for (int i = 0; i < newDefaultTreeSynthesizer.length; i++) {
			if (aRootNode[i] == null) {
				break;
			}
			myRootNode[i] = aRootNode[i];
			if(myDefaultTreeSynthesizer[i] == null){
			   newDefaultTreeSynthesizer[i] = new DefaultTreeSynthesizer();
			}
			else{
			   newDefaultTreeSynthesizer[i] = myDefaultTreeSynthesizer[i];
			}
			newDefaultTreeSynthesizer[i].setTreeManager(getTreeManager());
			newDefaultTreeSynthesizer[i].setRootNode(aRootNode[i]);
			tmpNode.add(newDefaultTreeSynthesizer[i].getVisibleTree());
		}
		myDefaultTreeSynthesizer = newDefaultTreeSynthesizer;
		setSavedVisibleTree(tmpNode);

		Iterator<AbstractTreeManipulator> manipulatorIterator = myManipulatorList
				.iterator();
		// loop through all iterators and manipulate the tree
		while (manipulatorIterator.hasNext()) {
			AbstractTreeManipulator as = (AbstractTreeManipulator) manipulatorIterator
					.next();
			as.setVisibleTree(mySavedVisibleTree);
			as.manipulate();
			setSavedVisibleTree(as.getVisibleTree());
		}
	}

	/**
	 * 
	 * @return the root node(s) of this TreeProxyManager
	 */
	public Node[] getRootNode() {
		return myRootNode;
	}

	/**
	 * gets original tree from DefaultTreeSynthesizer and then manipulates this
	 * tree by applying the TreeManipulators in myManipulatorList
	 */
	private void manipulateVisibleTree() {
		EncapsulatingVisibleNode tmpNode = new EncapsulatingVisibleNode(
				new MBModelNode("tmp"));
		setSavedVisibleTree(null);
		boolean hasDefaultVisibleTree = false;
		for (int i = 0; i < myDefaultTreeSynthesizer.length; i++) {
			if (myRootNode[i] == null) {
				// schotti: if there is no Tree rooted here, don't try to
				// manipulate it.
				break;
			}
			myDefaultTreeSynthesizer[i].setRootNode(myRootNode[i]);
			if (myDefaultTreeSynthesizer[i].getVisibleTree() != null) {
				hasDefaultVisibleTree = true;
				tmpNode.add(myDefaultTreeSynthesizer[i].getVisibleTree());
			}
		}
		if (hasDefaultVisibleTree)
			setSavedVisibleTree(tmpNode);

		// loop throught the list of registered manipulators
		// and manipulates original tree

		// the enabled status is used inside the tree manipulator
		// class in the method manipulate, not here !!
		Iterator<AbstractTreeManipulator> manipulatorIterator = myManipulatorList
				.iterator();
		while (manipulatorIterator.hasNext()) {
			AbstractTreeManipulator as = (AbstractTreeManipulator) manipulatorIterator
					.next();
			as.setVisibleTree(getSavedVisibleTree());
			as.manipulate();
			setSavedVisibleTree(as.getVisibleTree());
		}

		TreeModelChangedEvent e = new TreeModelChangedEvent(this);
		e.setModelChanged(true);
		fireTreeModelChangedEvent(e);
	}

	public JMenu createManagerContextMenu() {
		JMenu contextMenu = new JMenu(MENU_NAME);
		ButtonGroup group = new ButtonGroup();
		Iterator<AbstractTreeManipulator> iter = myManipulatorList.iterator();

		int i = 0;
		boolean once = true;

		while (iter.hasNext()) {

			AbstractTreeManipulator as = (AbstractTreeManipulator) iter.next();
			if (as instanceof SortingTreeManipulator) {
				JRadioButtonMenuItem radioItem = new JRadioButtonMenuItem(as
						.getName());
				group.add(radioItem);
				if (once)
					as.setEnabled(true);
				try {
					radioItem.setSelected(as.isEnabled());
					radioItem.setActionCommand(Integer.toString(i));
					radioItem.addItemListener(new MenuListener());
					contextMenu.add(radioItem);
				} catch (NullPointerException e) {
					log
							.debug("This dreadful exception that breaks my program: as is an instance of SortingTreeManipulator.");
					e.printStackTrace();
					return contextMenu;
				}
				once = false;
			} else {
				if (!(as instanceof BuildCustomTreeManipulator)) {
					JCheckBoxMenuItem item = new JCheckBoxMenuItem(as.getName());
					as.setEnabled(false);
					try {
						item.setSelected(as.isEnabled());
						item.setActionCommand(Integer.toString(i));
						item.addItemListener(new MenuListener());
						contextMenu.add(item);
					} catch (NullPointerException e) {
						log
								.debug("This dreadful exception that breaks my program: as is an instance of BuildCustomTreeManipulator.");
						e.printStackTrace();
						return null;
					}
				}
			}
			i++;
		}
		manipulateVisibleTree();
		return contextMenu;
	}

	/**
	 * sets a HashSet used for constructing a new tree
	 * 
	 * @param aHashSet
	 *            the HashSet containing the node ids
	 */
	public void setHashSet(HashSet aHashSet) {
		((BuildCustomTreeManipulator) myManipulatorList.get(0))
				.setEnabled(true);
		((BuildCustomTreeManipulator) myManipulatorList.get(0))
				.setHashSet(aHashSet);
		setRootNode(myRootNode);
		manipulateVisibleTree();
	}

	/**
	 * @author schaal Class used to handle PopupMenu Events of myContextMenu
	 *         sets the different TreeManipulators to enabled or disabled
	 */
	protected class MenuListener implements ItemListener {

		public void itemStateChanged(ItemEvent aItemEvent) {
			if (aItemEvent.getSource() instanceof JCheckBoxMenuItem) {
				JCheckBoxMenuItem myItem = (JCheckBoxMenuItem) aItemEvent
						.getSource();
				int i = Integer.parseInt(myItem.getActionCommand());
				((AbstractTreeManipulator) myManipulatorList.get(i))
						.setEnabled(myItem.getState());
			} else if (aItemEvent.getSource() instanceof JRadioButtonMenuItem) {
				JRadioButtonMenuItem myItem = (JRadioButtonMenuItem) aItemEvent
						.getSource();
				int i = Integer.parseInt(myItem.getActionCommand());
				((AbstractTreeManipulator) myManipulatorList.get(i))
						.setEnabled(myItem.isSelected());
			}
			manipulateVisibleTree();
		}
	}
}