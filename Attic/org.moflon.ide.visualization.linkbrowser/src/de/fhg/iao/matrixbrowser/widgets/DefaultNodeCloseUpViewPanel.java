/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * DefaultNodeCloseUpViewPanel.java,v 1.15 2004/04/14 11:47:56 maxmonroe Exp $
 * Created on 18.03.02
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.NodeCloseUpView;
import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEvent;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEventListener;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNodeEvent;
import de.fhg.iao.matrixbrowser.VisibleNodeEventListener;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedEvent;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedListener;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.collection.DefaultStringComparator;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * The <code>DefaultNodeCloseUpViewPanel</code> depicts the directly neighboring
 * nodes of a slected node in a more detail manner.
 * <code>DefaultNodeCloseUpViewPanel</code> needs the following icon keys in the
 * <code>UIResourceRegistry</code>:<br>
 * NodeCloseUpView.NoDirectedRealRelation <br>
 * NodeCloseUpView.BiDirectedRealRelation <br>
 * NodeCloseUpView.BiDirectedInferedRelation <br>
 * NodeCloseUpView.RightDirectedRealRelation <br>
 * NodeCloseUpView.RightDirectedInferedRelation <br>
 * NodeCloseUpView.LeftDirectedRealRelation <br>
 * NodeCloseUpView.LeftDirectedInferedRelation <br>
 * The icon width, which is needed to paint the column depicting the relation
 * direction, is determined by the with of the
 * NodeCloseUpView.NoDirectedInferedRelation width. Therefore all icons shoud
 * have the same size. Other keys are: <br>
 * NodeCloseUpView.borderColor (needed) NodeCloseUpView.background (optional)
 * NodeCloseUpView.RelatedNodesTable.background (optional)
 * NodeCloseUpView.RelatedNodesTable.TableHeader.background (optional)
 * NodeCloseUpView.RelatedNodesTable.ShowInferedRelationsPanel.background
 * (optional) if the optional values are not given, the ui components default do
 * .setOpaque(false) Localization keys. <br>
 * NodeCloseUpView.ShowInferedRelationCheckBox.Label
 * NodeCloseUpView.RelationTable.RelatesWithString
 * NodeCloseUpView.RelationTable.RelatesToString
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.13 $
 * @see de.fhg.iao.matrixbrowser.UIResourceRegistry
 * @see javax.swing.JComponent#setOpaque(boolean)
 */
public class DefaultNodeCloseUpViewPanel extends JPanel implements
		NodeCloseUpView, UIResourceRegistryEventListener {

	/** Comment for <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 1L;

	/** Icon for bi directed infered relations. */
	protected Icon myBiDirectedInferedRelationIcon = null;

	/** Icon for bi directed relations. */
	protected Icon myBiDirectedRelationIcon = null;

	/** Holds the node, of which to show details. */
	protected VisibleNode myDetailNode = null;

	/**
	 * The label in which the name of the node is shown, to which to depict
	 * details.
	 */
	protected JLabel myDetailNodeLabel = null;

	/** Icon for left directed inferred relations. */
	protected Icon myLeftDirectedInferedRelationIcon = null;

	/** Icon for left directed relations. */
	protected Icon myLeftDirectedRelationIcon = null;

	/** Holds a list of event listeners. */
	protected EventListenerList myListenerList = null;

	/** Reference to the main <code>MatrixBrowserPanel</code>. */
	protected MatrixBrowserPanel myMatrixBrowserPanel = null;

	/** Icon for no directed inferred relations. */
	protected Icon myNoDirectedInferedRelationIcon = null;

	/** Icon for no directed relations. */
	protected Icon myNoDirectedRelationIcon = null;

	/** <code>Map</code> mapping a <code>List</code> of <code>Relation</code>. */
	protected Map<RelationType, List<Relation>> myRelationsTypesToRelationMap = null;

	/** The table in which to depict related nodes with their relations. */
	protected JTable myRelationTypeTargetNodeTable = null;

	/** Icon for right directed relations. */
	protected Icon myRightDirectedRelationIcon = null;

	/** Icon for right directed inferred relations. */
	protected Icon myRigthDirectedInferedRelationIcon = null;

	/** Should i depict inferred relations ? */
	protected JCheckBox myShowInferedRelationsCheckBox = null;

	/** The JTextField where to input the node whose relations shall be shown. */
	protected JTextField showGraph = new JTextField("Show relations for a Node");
	// protected ShowGraphCombo showGraphCombo;

	/** The Number of Columns in this DefaultNodeCloseUpView. */
	private static final int COLUMNS = 3;

	/** The default Height of a Node Label. */
	private static final int NODE_LABEL_HEIGHT = 17;

	/**
	 * Constructs a <code>TreeView</code> given the main
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            reference to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>TreeView</code>
	 */
	public DefaultNodeCloseUpViewPanel(
			final MatrixBrowserPanel aMatrixBrowserPanel) {
		myMatrixBrowserPanel = aMatrixBrowserPanel;
		myListenerList = new EventListenerList();
		// showGraphCombo=new ShowGraphCombo(this);
		// showGraphCombo.addItemListener(showGraphCombo);
		// myMatrixBrowserPanel.addTreeModelChangedListener(showGraphCombo);
		// showGraphCombo.setToolTipText("NodeQuickFinder");
		showGraph.addActionListener(new ShowGraphEvent(this));
		showGraph.addCaretListener(new ShowGraphEvent(this));
		showGraph.setToolTipText("Node Quickfinder");
		initialize();
		UIResourceRegistry.getInstance().addUIResourceRegistryEventListener(
				this);
	}

	/**
	 * Initializes this DefaultNodeCloseUpView.
	 */
	private void initialize() {

		setBorder(new ResizeBorder(UIResourceRegistry.getInstance().getColor(
				UIResourceRegistry.getInstance().NODECLOSEUPVIEW_BORDERCOLOR)));

		if (UIResourceRegistry.getInstance().getColor(
				UIResourceRegistry.getInstance().NODECLOSEUPVIEW_BACKGROUND) != null) {
			setBackground(UIResourceRegistry
					.getInstance()
					.getColor(
							UIResourceRegistry.getInstance().NODECLOSEUPVIEW_BACKGROUND));

		} else {
			setOpaque(false);
		}

		setLayout(new BorderLayout());

		// init icons
		myNoDirectedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_NODIRECTED_REAL_RELATION);
		myNoDirectedInferedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_NODIRECTED_INFERED_RELATION);
		myBiDirectedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_BIDIRECTED_REAL_RELATION);
		myBiDirectedInferedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_BIDIRECTED_INFERED_RELATION);
		myRightDirectedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_RIGHTDIRECTED_REAL_RELATION);
		myRigthDirectedInferedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_RIGHTDIRECTED_INFERED_RELATION);
		myLeftDirectedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_LEFTDIRECTED_REAL_RELATION);
		myLeftDirectedInferedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_LEFTDIRECTED_INFERED_RELATION);

		// set up label for the selected node
		myDetailNodeLabel = new JLabel();
		myDetailNodeLabel.setPreferredSize(new Dimension(0, NODE_LABEL_HEIGHT));
		myDetailNodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		myDetailNodeLabel.setBorder(BorderFactory.createEtchedBorder());

		myRelationTypeTargetNodeTable = new JTable(
				new NodeCloseUpViewTableModel(null));
		myRelationTypeTargetNodeTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane();

		if (UIResourceRegistry
				.getInstance()
				.getColor(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_RELATEDNODESTABLE_BACKGROUND) != null) {
			scrollPane
					.getViewport()
					.setBackground(
							UIResourceRegistry
									.getInstance()
									.getColor(
											UIResourceRegistry.getInstance().NODECLOSEUPVIEW_RELATEDNODESTABLE_BACKGROUND));
		} else {
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
		}

		scrollPane.getViewport().add(myRelationTypeTargetNodeTable);

		// configure the myRelationTypeTargetNodeTable
		NodeCloseUpTableCellRender renderer = new NodeCloseUpTableCellRender();
		myRelationTypeTargetNodeTable.getTableHeader().setPreferredSize(
				new Dimension(0, NODE_LABEL_HEIGHT));

		if (UIResourceRegistry
				.getInstance()
				.getColor(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_RELATEDNODESTABLE_TABLEHEADER_BACKGROUND) != null) {
			myRelationTypeTargetNodeTable
					.getTableHeader()
					.setBackground(
							UIResourceRegistry
									.getInstance()
									.getColor(
											UIResourceRegistry.getInstance().NODECLOSEUPVIEW_RELATEDNODESTABLE_TABLEHEADER_BACKGROUND));
		}

		myRelationTypeTargetNodeTable.setAutoCreateColumnsFromModel(false);

		// set renderer
		for (int i = 0; i < COLUMNS; i++) {
			myRelationTypeTargetNodeTable.getColumnModel().getColumn(i)
					.setCellRenderer(renderer);
		}

		// set max width for the direction column
		myRelationTypeTargetNodeTable.getColumnModel().getColumn(1)
				.setMaxWidth(myNoDirectedRelationIcon.getIconWidth() + 2);
		myRelationTypeTargetNodeTable.setShowGrid(false);
		myRelationTypeTargetNodeTable.getSelectionModel()
				.addListSelectionListener(
						new CloseUpViewTableSelectionListener());

		// configure inferred relations checkbox
		myShowInferedRelationsCheckBox = new JCheckBox();
		myShowInferedRelationsCheckBox.setOpaque(false);

		JPanel checkBoxPanel = new JPanel(new BorderLayout());
		if (UIResourceRegistry
				.getInstance()
				.getColor(
						UIResourceRegistry.getInstance().NODECLOSEUPVIEW_RELATEDNODESTABLE_SHOWINFEREDRELATIONSPANEL_BACKGROUND) != null) {
			checkBoxPanel
					.setBackground(UIResourceRegistry
							.getInstance()
							.getColor(
									UIResourceRegistry.getInstance().NODECLOSEUPVIEW_RELATEDNODESTABLE_SHOWINFEREDRELATIONSPANEL_BACKGROUND));

			checkBoxPanel
					.add(
							new JLabel(
									UIResourceRegistry
											.getInstance()
											.getString(
													UIResourceRegistry
															.getInstance().NODECCLOSEUPVIEW_SHOWINFEREDRELATION_CHECKBOX_LABEL)), //$NON-NLS-1$
							BorderLayout.CENTER);

			checkBoxPanel
					.add(myShowInferedRelationsCheckBox, BorderLayout.EAST);
			// /////////////////////////////////////////////
			checkBoxPanel.add(showGraph, BorderLayout.SOUTH);
			// checkBoxPanel.add(showGraphCombo,BorderLayout.SOUTH);
			checkBoxPanel.setBorder(BorderFactory.createEtchedBorder());
			myShowInferedRelationsCheckBox
					.addActionListener(new ShowInferedRelationAction());

			add(myDetailNodeLabel, BorderLayout.NORTH);
			add(scrollPane, BorderLayout.CENTER);
			add(checkBoxPanel, BorderLayout.SOUTH);
		}
	}

	/**
	 * Implementation of the onLoad method of the
	 * UIResourceRegistryEventListener.
	 * 
	 * @param aEvent
	 *            the event that caused the UIResourceRegistryChange
	 */
	@Override
   public final void onUIResourceRegistryChanged(
			final UIResourceRegistryEvent aEvent) {
		removeAll();
		initialize();
		validate();
	}

	/**
	 * Adds a <code>VisibleNodeEventListener</code>, that will be notified if
	 * someone clicks on a node in the <code>NodeCloseUpView</code>s.
	 * 
	 * @param aVisibleNodeEventListener
	 *            the <code>VisibleNodeEventListener</code> to be notified of
	 *            <code>NodeEvent</code>s
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	@Override
   public final void addVisibleNodeEventListener(
			final VisibleNodeEventListener aVisibleNodeEventListener) {
		myListenerList.add(VisibleNodeEventListener.class,
				aVisibleNodeEventListener);
	}

	/**
	 * Creates a <code>TableModel</code> containing all relations and target
	 * <code>Node</code> s of the given <code>Node</code>.
	 * 
	 * @param aNode
	 *            the node to create the table model for
	 * @param aRelationTypeMap
	 *            the presorted relation/name mapping
	 * @param aRelationTypeComparator
	 *            a <code>Comparator</code> for sorting relation names
	 * @param aTargetNodeComaparator
	 *            a <code>Comparator</code> sorting <code>Node</code> names
	 * @return a table model for the visualization
	 * @see DefaultNodeCloseUpViewPanel#mapRelationTypesToRelations
	 */
	protected final TableModel createTableModelForNode(final Node aNode,
			final Map<RelationType, List<Relation>> aRelationTypeMap,
			final Comparator<RelationType> aRelationTypeComparator,
			final Comparator<Node> aTargetNodeComaparator) {
		// Vector target = new Vector();
		List<List<Object>> rows = new ArrayList<List<Object>>();

		// ok, first sort relation types with a given comparator
		List<RelationType> relationTypes = new ArrayList<RelationType>(
				aRelationTypeMap.keySet());

		try {
			Collections.sort(relationTypes, aRelationTypeComparator);
		} catch (Exception ec) {
			ec.printStackTrace();
			// Empty catch Block
		}

		Iterator<RelationType> typeIt = relationTypes.iterator();

		while (typeIt.hasNext()) {
			RelationType relationType = typeIt.next();

			// now collect list of relations
			List<Relation> relationList = aRelationTypeMap.get(relationType);
			Collections.sort(relationList, new TargetNodeComperator(aNode,
					aTargetNodeComaparator));

			boolean relationTypeSet = false;

			for (int i = 0; i < relationList.size(); i++) {

				Relation relation = relationList.get(i);

				// show inferred relations only if the user enabled it
				if ((myShowInferedRelationsCheckBox.isSelected())
						|| (relation.getRelationClass() != RelationClass.INFERRED)) {

					List<Object> row = new ArrayList<Object>();

					if (!relationTypeSet) {
						// first entry with relationType
						row.add(0, relationType.getName());
						relationTypeSet = true;
					} else {
						row.add(0, ""); //$NON-NLS-1$
					}

					RelationDirection direction = relation.getDirection();
					boolean swap = false;
					Node targetNode = null;

					if (aNode.getID().equals(relation.getSourceNodeID())) {
						// This node is Source -> use Target
						targetNode = getMatrixBrowserPanel().getTreeManager()
								.getModel().getNode(relation.getTargetNodeID());
					} else {
						// This node is Target -> use Source
						swap = true;
						targetNode = getMatrixBrowserPanel().getTreeManager()
								.getModel().getNode(relation.getSourceNodeID());
					}

					if (relation.getRelationClass() == RelationClass.REAL) {
						switch (direction) {
						case UNDIRECTED:
							row.add(1, myNoDirectedRelationIcon);

							break;

						case DIRECTED:

							if (swap) {
								row.add(1, myLeftDirectedRelationIcon);
							} else {
								row.add(1, myRightDirectedRelationIcon);
							}

							break;

						case BIDIRECTIONAL:
							row.add(1, myBiDirectedRelationIcon);

							break;

						default:
							// nothing else implemented.
						}
					} else {
						switch (direction) {
						case UNDIRECTED:
							row.add(1, myNoDirectedInferedRelationIcon);

							break;

						case DIRECTED:

							if (swap) {
								row.add(1, myLeftDirectedInferedRelationIcon);
							} else {
								row.add(1, myRigthDirectedInferedRelationIcon);
							}

							break;

						case BIDIRECTIONAL:
							row.add(1, myBiDirectedInferedRelationIcon);

							break;

						default:
							// nothing else implemented
						}
					}

					row.add(2, targetNode);
					rows.add(row);

				}
			}
		}

		return new NodeCloseUpViewTableModel(rows);
	}

	/**
	 * Fires a <code>VisibleNodeEvent</code> by calling 'onNodeSelected' at the
	 * listener. This should happen when a node was clicked in the
	 * <code>TreeView</code> s and therefore selected.
	 * 
	 * @param aVisibleNodeEvent
	 *            the <code>VisibleNodeEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	protected final void fireVisibleNodeEvent(
			final VisibleNodeEvent aVisibleNodeEvent) {
		Object[] listeners = myListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {
				((VisibleNodeEventListener) listeners[i + 1])
						.onNodeClicked(aVisibleNodeEvent);
			}
		}
	}

	/**
	 * @return the <code>MatrixBrowserPanel</code>, which created this
	 *         <code>IAONodeCloseUpView</code>
	 */
	public final MatrixBrowserPanel getMatrixBrowserPanel() {
		return myMatrixBrowserPanel;
	}

	/**
	 * Maps a <code>List</code> of <code>Relation</code> s containing the given
	 * <code>Node</code> either as source or target to the name of relations.
	 * 
	 * @param aNode
	 *            the source node of the relation
	 * @return a <code>Map</code> mapping a <code>List</code> of
	 *         <code>Relation</code> s containing <code>aNode</code> either as
	 *         source or target to the names of relations.
	 */
	protected final Map<RelationType, List<Relation>> mapRelationTypesToRelations(
			final Node aNode) {
		Map<RelationType, List<Relation>> relationMap = new HashMap<RelationType, List<Relation>>();

		// sort for relation types and target nodes
		Collection<Relation> relations = getMatrixBrowserPanel()
				.getTreeManager().getDefaultTreeSynthesizer().getRelations(
						aNode);

		Iterator<Relation> it = relations.iterator();

		while (it.hasNext()) {
			Relation relation = it.next();
			/*
			 * Node targetNode;
			 * 
			 * if (aNode.getID().equals(relation.getSourceNodeID())) { // This
			 * node is Source -> use Target targetNode =
			 * getMatrixBrowserPanel().getTreeManager().getModel()
			 * .getNode(relation.getTargetNodeID()); } else { // This node is
			 * Target -> use Source targetNode =
			 * getMatrixBrowserPanel().getTreeManager().getModel()
			 * .getNode(relation.getSourceNodeID()); }
			 */

			List<Relation> relationsOfType = relationMap.get(relation
					.getRelationType().getName());

			if (relationsOfType == null) {
				relationsOfType = new ArrayList<Relation>();
				relationMap.put(relation.getRelationType(), relationsOfType);
			}

			relationsOfType.add(relation);
		}

		return relationMap;
	}

	/**
	 * Removes a <code>VisibleNodeEventListener</code>.
	 * 
	 * @param aVisibleNodeEventListener
	 *            the <code>VisibleNodeEventListener</code> to be removed
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	@Override
   public final void removeVisibleNodeEventListener(
			final VisibleNodeEventListener aVisibleNodeEventListener) {
		myListenerList.remove(VisibleNodeEventListener.class,
				aVisibleNodeEventListener);
	}

	/**
	 * Sets the node to show details, which was clicked in an
	 * <code>TreeView</code>.
	 * 
	 * @param aNode
	 *            the clicked node in an <code>TreeView</code>
	 */
	@SuppressWarnings("unchecked")
   @Override
   public final void setNode(final VisibleNode aNode) {

		if (aNode != null) {
			myDetailNode = aNode;

			if (!(aNode instanceof VisibleNode)) {
				myDetailNodeLabel.setText(aNode.toString());
			} else {
				Node nestedNode = aNode.getNestedNode();
				myDetailNodeLabel.setText(nestedNode.getName());

				myRelationsTypesToRelationMap = mapRelationTypesToRelations(nestedNode);

				myRelationTypeTargetNodeTable.setModel(createTableModelForNode(
						nestedNode, myRelationsTypesToRelationMap,
						DefaultStringComparator.getStringComparator(),
						DefaultStringComparator.getStringComparator()));

			}

		}
	}

	/**
	 * Listener class for the table selections.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.13 $
	 */
	protected class CloseUpViewTableSelectionListener implements
			ListSelectionListener {

		/**
		 * Just fires a <code>VisibleNodeEvent</code>, when a row is selected.
		 * 
		 * @param e
		 *            the event causing this ValueChange
		 * @see javax.swing.event.ListSelectionListener#valueChanged
		 *      (javax.swing.event.ListSelectionEvent)
		 */
		@Override
      public final void valueChanged(final ListSelectionEvent e) {
			// Ignore extra messages.
			if (e.getValueIsAdjusting()) {
				return;
			}

			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			if (!(lsm.isSelectionEmpty())) {
				int selectedRow = lsm.getMinSelectionIndex();
				EncapsulatingVisibleNode node = new EncapsulatingVisibleNode(
						(Node) myRelationTypeTargetNodeTable.getModel()
								.getValueAt(selectedRow, 2));

				fireVisibleNodeEvent(new VisibleNodeEvent(this, myDetailNode,
						node));
			}
		}
	}

	/**
	 * Specialized <code>TableModel</code> for the
	 * <code>IAODefaultCloseUpViewPanel</code> s table.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.13 $
	 */
	protected class NodeCloseUpViewTableModel extends AbstractTableModel {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;
		/** Holds the current row. */
		private List<List<Object>> myRows = null;

		/**
		 * @param aRowList
		 */
		public NodeCloseUpViewTableModel(final List<List<Object>> aRowList) {
			super();

			if (aRowList == null) {
				myRows = Collections.emptyList();
			} else {
				myRows = aRowList;

			}
		}

		/**
		 * @return the column count.
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
      public final int getColumnCount() {
			return COLUMNS;
		}

		/**
		 * @return the row count
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
      public final int getRowCount() {
			return myRows.size();
		}

		/**
		 * gets the Value of the given row and column.
		 * 
		 * @param rowIndex
		 *            the row of which the Value shall be returned
		 * @param columnIndex
		 *            the column of which the Value shall be returned.
		 * @return the value at the specific row and column
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
      public final Object getValueAt(final int rowIndex, final int columnIndex) {
			return ((List<?>) myRows.get(rowIndex)).get(columnIndex);
		}

		/**
		 * Gets the name of the specified column.
		 * 
		 * @param column
		 *            the column for which to get the name
		 * @return the column names
		 * @see javax.swing.table.TableModel#getColumnName(int)
		 */
		@Override
      public final String getColumnName(final int column) {
			String result = null;

			switch (column) {
			case 0:
				result = UIResourceRegistry.getInstance().getString(
						"NodeCloseUpView.RelationTable.RelatesWithString"); //$NON-NLS-1$

				break;

			case 1:
				result = ""; //$NON-NLS-1$

				break;

			case 2:
				result = UIResourceRegistry.getInstance().getString(
						"NodeCloseUpView.RelationTable.RelatesToString"); //$NON-NLS-1$

				break;
			default:
				// not more than 3 columns implemented up to now.
			}

			return result;
		}
	}

	/**
	 * Helper <code>Comparator</code> for sorting target <code>Node</code> s
	 * encapsulated in a <code>List</code> of <code>Relation</code>s.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.13 $
	 */
	protected class TargetNodeComperator implements Comparator<Object> {

		protected Node mySourceNode = null;

		protected Comparator<Node> myHelperComparator = null;

		/**
		 * Constructs a <code>TargetNodeComperator</code> for sorting the target
		 * <code>Node</code> s in <code>Relation</code>s.
		 * 
		 * @param aSourceNode
		 *            the source node in the <code>Relation</code>
		 * @param aHelperComparator
		 *            the 'real' <code>Comparator</code> to which to compare
		 *            method is delegated.
		 */
		protected TargetNodeComperator(final Node aSourceNode,
				final Comparator<Node> aHelperComparator) {
			mySourceNode = aSourceNode;
			myHelperComparator = aHelperComparator;
		}

		/**
		 * A simple Comparator.
		 * 
		 * @param o1
		 *            one object to compare to the second
		 * @param o2
		 *            the second object (to be compared to the first)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
      public int compare(final Object o1, final Object o2) {
			Relation rel = (Relation) o1;
			Node node1 = null;

			if (mySourceNode.getID().equals(rel.getSourceNodeID())) {
				// This node is Source -> use Target
				node1 = getMatrixBrowserPanel().getTreeManager().getModel()
						.getNode(rel.getTargetNodeID());
			} else {
				// This node is Target -> use Source
				node1 = getMatrixBrowserPanel().getTreeManager().getModel()
						.getNode(rel.getSourceNodeID());
			}

			rel = (Relation) o2;

			Node node2 = null;

			if (mySourceNode.getID().equals(rel.getSourceNodeID())) {
				// This node is Source -> use Target
				node2 = getMatrixBrowserPanel().getTreeManager().getModel()
						.getNode(rel.getTargetNodeID());
			} else {
				// This node is Target -> use Source
				node2 = getMatrixBrowserPanel().getTreeManager().getModel()
						.getNode(rel.getSourceNodeID());
			}

			return myHelperComparator.compare(node1, node2);
		}
	}

	/**
	 * Cell renderer for the <code>JTable</code> showing related nodes.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.13 $
	 */
	protected class NodeCloseUpTableCellRender extends JLabel implements
			TableCellRenderer {

      private static final long serialVersionUID = -8201034334042784475L;

      /**
		 * @see javax.swing.table.TableCellRenderer
		 *      #getTableCellRendererComponent(javax.swing.JTable,
		 *      java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
      public final Component getTableCellRendererComponent(
				final JTable table, final Object value,
				final boolean isSelected, final boolean hasFocus,
				final int row, final int column) {
			int wortLaenge = 0;

			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}

			switch (column) {
			case 0:

				String str = (String) value;
				setText(str);
				setIcon(null);
				// try {
				if (str != null) {
					wortLaenge = str.length();
				} else {
					wortLaenge = 0;
				}

				// } catch (Exception e) {
				// e.printStackTrace();
				// }

				if (wortLaenge > 0) {

					setToolTipText(str);

				} else {
					setToolTipText(null);
				}

				break;

			case 1:

				// we got the relation direction column
				setText(null);
				setIcon((Icon) value);

				break;

			case 2:

				String nodeName = ((Node) value).getName();
				if (nodeName == null) {
					nodeName = ((Node) value).toString();
				}
				setText(nodeName);
				setIcon(null);
				setToolTipText(nodeName);

				break;
			default:
				// nothing implemented. Only have three columns.
				break;
			}

			return this;

		}

		public VisibleNode getNode() {
			return null;
		}
	}

	/**
	 * <code>ActionListener</code> for the check box toggeling the visualization
	 * of infered relations.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.13 $
	 */
	protected class ShowInferedRelationAction implements ActionListener {

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed
		 *      (java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
      @Override
      public final void actionPerformed(final ActionEvent e) {
			if (myRelationsTypesToRelationMap != null
					&& myDetailNode instanceof EncapsulatingVisibleNode) {
				myRelationTypeTargetNodeTable.setModel(createTableModelForNode(
						((EncapsulatingVisibleNode) myDetailNode)
								.getNestedNode(),
						myRelationsTypesToRelationMap, DefaultStringComparator
								.getStringComparator(), DefaultStringComparator
								.getStringComparator()));
			}
		}
	}

	/**
	 * Gets the currently shown VisibleNode.
	 * 
	 * @return the currently shown VisibleNode
	 * @see de.fhg.iao.matrixbrowser.NodeCloseUpView#getNode()
	 */
	@Override
   public final VisibleNode getNode() {
		return this.myDetailNode;
	}
}

// ///////////////////////////////////////Marwan

/**
 * Event produced when the NodeCloseUpView calls a new NodeQuickFinder.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.13 $
 */
class ShowGraphEvent implements ActionListener, CaretListener {
	/** holds a reference to the textField where the node to view is typed into. */
	private JTextField tf;
	/** The nodeCloseUpViewPanel where the Finder TextField is placed in. */
	private DefaultNodeCloseUpViewPanel panel;

	/** counter. */
	// int i = 0;
	/**
	 * Constructor for a ShowGraphEvent.
	 * 
	 * @param panel
	 *            the NodeCloseUpViewPanel from which the Event is generated.
	 * */
	public ShowGraphEvent(final DefaultNodeCloseUpViewPanel panel) {
		this.panel = panel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed
	 *      (java.awt.event.ActionEvent)
	 */
	@Override
   public void actionPerformed(final ActionEvent event) {
		boolean weiter = true;
		if (event.getSource().getClass() != JTextField.class) {
			return;
		}
		tf = (JTextField) event.getSource();
		Map<ID, Node> allNodes = panel.getMatrixBrowserPanel().getTreeManager()
				.getModel().getNodes();
		Collection<Node> col = allNodes.values();
		Iterator<Node> it = col.iterator();
		while (it.hasNext()) {
			Node knoten = it.next();
			if (knoten.getName().equalsIgnoreCase(tf.getText())) {
				weiter = false;
				// NodeQuickFinder f =
				new NodeQuickFinder(panel, knoten);
				tf.setText("Show relations for a Node");
			}
		}
		if (weiter) {
			// NodeQuickFinder f =
			new NodeQuickFinder(tf.getText());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.swing.event.CaretListener#caretUpdate
	 *      (javax.swing.event.CaretEvent)
	 */
	@Override
   public void caretUpdate(final CaretEvent event) {
	}

}

/**
 * A <code>JComboBox</code> for invocation of the NodeQuickFinder. Enhances the
 * MatrixBrowser CloseUpViewPanel with a Drop-Down menu to search and select a
 * node from the Model which shall afterwards be shown in the NodeQuickFinder.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.13 $
 */
class ShowGraphCombo extends JComboBox<Node> implements ItemListener,
		UIResourceRegistryEventListener, TreeModelChangedListener {
	/** Comment for <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 1L;
	/** reference to the DefaultNodeCloseUpView this ComboBox is placed in. */
	private DefaultNodeCloseUpViewPanel panel;
	/** Tells whether this ComboBox is initializing. */
	private boolean initializing;

	/**
	 * Constructor setting the Reference to the DefaultNodeCloseUpViewPanel.
	 * 
	 * @param panel
	 *            the DefaultNodeCloseUpViewPanel
	 */
	public ShowGraphCombo(final DefaultNodeCloseUpViewPanel panel) {
		this.panel = panel;
	}

	/**
	 * Initialization Routine for the <code>ShowGraphCombo</code> As soon as a
	 * Model is available, this method populates the Drop-Down Menu with all
	 * node names to choose from.
	 */
	public void initialize() {
		if (panel.getMatrixBrowserPanel().getTreeManager() != null) {
			initializing = true;
			Map<ID, Node> allNodes = panel.getMatrixBrowserPanel()
					.getTreeManager().getModel().getNodes();
			Iterator<Node> nodesIterator = allNodes.values().iterator();
			while (nodesIterator.hasNext()) {
				addItem(nodesIterator.next());
			}
			this.setEditable(true);
			initializing = false;
		}
	}

	/*
	 * public void actionPerformed(ActionEvent e){
	 * log.debug("Not reacting to actionPerformedEvent"+e);
	 * 
	 * if(e.getActionCommand()=="comboBoxChanged") return; //if
	 * (e.getSource().getClass() != ShowGraphCombo.class) return; Node
	 * newSelection = (Node)this.getSelectedItem(); new
	 * NodeQuickFinder(newSelection.getName()); }
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.UIResourceRegistryEventListener
	 *      #onUIResourceRegistryChanged
	 *      (de.fhg.iao.matrixbrowser.UIResourceRegistryEvent)
	 */
	@Override
   public void onUIResourceRegistryChanged(final UIResourceRegistryEvent aEvent) {
		initialize();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.context.TreeModelChangedListener
	 *      #onTreeModelChanged
	 *      (de.fhg.iao.matrixbrowser.context.TreeModelChangedEvent)
	 */
	@Override
   public void onTreeModelChanged(final TreeModelChangedEvent event) {
		initialize();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
   public void itemStateChanged(final ItemEvent e) {
		if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED
				&& !initializing) {
			new NodeQuickFinder(panel, (Node) this.getSelectedItem());
		}
	}
}
