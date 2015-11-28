/* Code developped by FHG*/
package de.fhg.iao.matrixbrowser.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.SimpleTransportPanel;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNodeEvent;
import de.fhg.iao.matrixbrowser.VisibleNodeEventListener;
import de.fhg.iao.matrixbrowser.context.TreeManager;
import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.RelationCachedModel;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.matrixbrowser.model.transport.MBModelBuilder;
import de.fhg.iao.matrixbrowser.model.transport.MBModelDirector;
import de.fhg.iao.matrixbrowser.model.transport.TransportException;
import de.fhg.iao.matrixbrowser.model.transport.XMLBuilder;
import de.fhg.iao.matrixbrowser.model.transport.XMLDirector;
import de.fhg.iao.matrixbrowser.widgets.AbstractTreeView;

/** 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.2 $
 */
public class LinkbrowserWindowStandalone extends JFrame implements
		ActionListener {

	private static final Logger log = Logger
			.getLogger(LinkbrowserWindowStandalone.class);

	/** Default <code>serialVersionUID</code> */
	private static final long serialVersionUID = 1L;
	/** Holds a Reference to the JFrame it is displayed in. */
	private JFrame frame;
	/** holds the menuBar. */
	private JMenuBar menuBar;
	/** holds the <code>fileMenu</code>. */
	private JMenu fileMenu;
	/** holds the<code>editMenu</code>. */
	private JMenu editMenu;
	/** holds the <code>historyMenu</code>. */
	private JMenu historyMenu;
	/** holds the <code>helpMenu</code> */
	private JMenu helpMenu;

	/** Comment for <code>openItem</code> */
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem printItem;
	private JMenuItem exitItem;
	private JMenuItem editOnItem;
	private JMenuItem editOffItem;
	private JMenuItem editAddNodeItem;
	private JMenuItem editDeleteNodeItem;
	private JMenuItem editAddRelationItem;
	private JMenuItem helpItem;
	private JMenuItem infosItem;
	private BorderLayout borderLayout1 = new BorderLayout();
	private MatrixBrowserPanel browserPanel = null;
	private SimpleTransportPanel transportPanel = null;

	// private boolean waitForMouseEvent = false; //schotti: not needed anymore

	/**
	 * Constructor for a Standalone LinkBrowser.
	 */
	public LinkbrowserWindowStandalone() {
		super("LinkBrowser"); // set JFrame Title
		browserPanel = new MatrixBrowserPanel();
		transportPanel = new SimpleTransportPanel();
		this.setLayout(borderLayout1);
		JSplitPane matrixBrowserPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, transportPanel, browserPanel);
		matrixBrowserPane.setDividerLocation(150);
		treeMaker();

		this.add(matrixBrowserPane);
		this.setJMenuBar(createMenu());
		this.validate();
		this.repaint();
	}

	/**
	 * @return
	 */
	public JMenuBar createMenu() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		historyMenu = new JMenu("History");
		helpMenu = new JMenu("Help");
		openItem = new JMenuItem("Open...");
		saveItem = new JMenuItem("Save");
		printItem = new JMenuItem("Print");
		exitItem = new JMenuItem("Exit");
		editOnItem = new JMenuItem("Edit ON");
		editOffItem = new JMenuItem("Edit OFF");
		editAddNodeItem = new JMenuItem("Add Node");
		editDeleteNodeItem = new JMenuItem("Delete Node");
		editAddRelationItem = new JMenuItem("Add Relation");
		helpItem = new JMenuItem("Help");
		helpItem.addActionListener(this);
		infosItem = new JMenuItem("Infos");
		infosItem.addActionListener(this);
		infosItem.setBackground(Color.red);

		menuBar.add(fileMenu);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(printItem);
		fileMenu.add(exitItem);
		menuBar.add(editMenu);
		editMenu.add(editOnItem);
		editMenu.add(editOffItem);
		editMenu.add(editAddNodeItem);
		editMenu.add(editDeleteNodeItem);
		editMenu.add(editAddRelationItem);
		menuBar.add(historyMenu);
		menuBar.add(helpMenu);
		helpMenu.add(helpItem);
		helpMenu.add(infosItem);

		editAddRelationItem.addActionListener(new ActionListener() {
			VisibleNode firstNode = null;
			VisibleNode secondNode = null;
			VisibleNodeEventListener myVisibleNodeEventListener = new VisibleNodeEventListener() {
				public void onNodeEdit(VisibleNodeEvent e) {}

				public void onNodeOver(VisibleNodeEvent e) {}

				public void onNodeClicked(VisibleNodeEvent e) {
					Relation relation;
					MBModel model = browserPanel.getTreeManager().getModel();
					Collection<RelationType> relationTypes = model
							.getRelationTypes();
					relationTypes.removeAll(model
							.getTreeSpanningRelationTypes());
					Iterator<RelationType> it = relationTypes.iterator();
					RelationType relType = it.next();
					// VisibleRelation visibleRelation;

					// AbstractTreeView aTreeView;
					// Choose the first one we find for now.

					// remove tree spanning relations

					if (e.getSource() instanceof AbstractTreeView
							&& e.getSource() != null) {
						if (firstNode == null) {
							firstNode = e.getNode();
						} else if (secondNode == null) {
							secondNode = e.getNode();
						} else {
							throw new InputMismatchException(
									"Up to now, you can only "
											+ "connect two nodes with one another.");
						}
					} else if (e.getSource() == null) {
						throw new InputMismatchException(
								"You have to select two nodes"
										+ " to connect via this relation");
					} else if (e.getSource() instanceof AbstractTreeView) {
						throw new InputMismatchException(
								"Please select the nodes to "
										+ "connect via this relation, one in vertical Tree View "
										+ "and one in Horizontal Tree View");
					} else {
						throw new InputMismatchException(
								"Something happened I had not " + "foreseen...");
					}
					if (secondNode != null && firstNode != null) {
						relation = new Relation(firstNode.getNestedNode()
								.getID(), secondNode.getNestedNode().getID(),
								relType, RelationClass.REAL,
								RelationDirection.DIRECTED);
						browserPanel
								.removeVisibleNodeEventListener(myVisibleNodeEventListener);
						model.addRelation(relation);
					}
				}

				public void onNodeMenu(VisibleNodeEvent e) {}
			};

			public void actionPerformed(final ActionEvent e) {
				MBModel model = browserPanel.getTreeManager().getModel();
				// final MouseListener[] mouselisteners =
				// browserPanel.getRelationPane().getMatrixBrowserPanel().getListeners(MouseListener.class);
				// final VisibleNodeEventListener[] visibleNodeEventListeners =
				// browserPanel.getRelationPane().getMatrixBrowserPanel().getListeners(VisibleNodeEventListener.class);
				// final VisibleRelationEventListener[]
				// visibleRelationEventListener =
				// browserPanel.getRelationPane().getMatrixBrowserPanel().getListeners(VisibleRelationEventListener.class);
				/*
				 * for (VisibleNodeEventListener listener :
				 * visibleNodeEventListeners) {
				 * browserPanel.getRelationPane().getMatrixBrowserPanel
				 * ().removeVisibleNodeEventListener(listener); }
				 * 
				 * for (MouseListener listener : mouselisteners){
				 * browserPanel.getRelationPane
				 * ().getMatrixBrowserPanel().removeMouseListener(listener); }
				 * 
				 * for (VisibleRelationEventListener listener :
				 * visibleRelationEventListener){
				 * browserPanel.getRelationPane().
				 * getMatrixBrowserPanel().removeVisibleRelationEventListener
				 * (listener); }
				 * 
				 * waitForEvent();
				 */
				browserPanel
						.addVisibleNodeEventListener(myVisibleNodeEventListener);
				// browserPanel.removeVisibleNodeEventListener(myVisibleNodeEventListener);
				/*
				 * for (VisibleNodeEventListener listener :
				 * visibleNodeEventListeners) {
				 * browserPanel.getRelationPane().getMatrixBrowserPanel
				 * ().addVisibleNodeEventListener(listener); } for
				 * (MouseListener listener : mouselisteners){
				 * browserPanel.getRelationPane
				 * ().getMatrixBrowserPanel().addMouseListener(listener); } for
				 * (VisibleRelationEventListener listener :
				 * visibleRelationEventListener){
				 * browserPanel.getRelationPane().
				 * getMatrixBrowserPanel().addVisibleRelationEventListener
				 * (listener); }
				 */
			}
		});

		editDeleteNodeItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				// The selected Node should be the one shown in the
				// NodeCloseUpView. This is ugly
				// and has to be changed, but this is just to show that it
				// works...
				MBModel model = browserPanel.getTreeManager().getModel();
				VisibleNode node = browserPanel.getNodeCloseUpView().getNode();
				for (Relation relation : node.getNestedNode().getMyRelations()) {
					model.removeRelation(relation);
				}
				model.removeNode(node.getNestedNode());
			}
		});

		editAddNodeItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				MBModel model = browserPanel.getTreeManager().getModel();
				Node newNode = new MBModelNode("hello");
				// Iterator<RelationType> iter
				// =model.getTreeSpanningRelationTypes().iterator();
				// RelationType relationType = iter.next();
				// Relation newRelation = new
				// Relation(model.getRootNode().getID(), newNode.getID(),
				// relationType, RelationClass.REAL,
				// RelationDirection.DIRECTED);
				model.addNode(newNode);
				// model.addRelation(newRelation);
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				exitItemAction(e);
			}
		});
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				openItemAction(e);
			}
		});
		editOnItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				browserPanel.setEditable(true);
			}
		});
		editOffItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				browserPanel.setEditable(false);
			}
		});
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				saveItemAction(e);
			}
		});

		return menuBar;
	}

	/**
	 * Method to call when exit was clicked in the Menu.
	 * 
	 * @param e
	 *            the event produced when exit was clicked
	 */
	public final void exitItemAction(final ActionEvent e) {
		System.exit(0);
	}

	/**
	 * Method to call when File->open was clicked.
	 * 
	 * @param e
	 *            the event produced when File->open is clicked.
	 */
	public final void openItemAction(final ActionEvent e) {
		JFileChooser chooser = new JFileChooser(new File(System
				.getProperty("user.dir")));
		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				// long millis = System.currentTimeMillis();
				File aFile = chooser.getSelectedFile();
				InputStream stream = new FileInputStream(aFile);
				InputStreamReader reader = new InputStreamReader(stream,
						"UTF-8");

				MBModelBuilder builder = new MBModelBuilder(
						new RelationCachedModel());

				// OldXMLDirector importer = new OldXMLDirector(builder,
				// reader);
				XMLDirector importer = new XMLDirector(builder, reader);
				importer.startTransport();

				MBModel aModel = builder.getModel();
				TreeManager aManager = new TreeManager(aModel);

				this.browserPanel.setTreeManager(aManager);

				// repaint is needed to display the loaded file correct
				this.repaint();
				// this.transportPanel.setTreeManager(aManager);
			} catch (TransportException ex) {
				ex.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} // else {
		// cancel was pressed
		// WindowEvent event = new WindowEvent(
		// this, WindowEvent.WINDOW_CLOSING);
		// this.processEvent(event);
		// }
	}

	/**
	 * Method to call when File->Save has been clicked.
	 * 
	 * @param e
	 *            The event produced when File->save is clicked.
	 */
	private void saveItemAction(final ActionEvent e) {
		JFileChooser chooser = new JFileChooser(new File(System
				.getProperty("user.dir")));
		int returnVal = chooser.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File aFile = chooser.getSelectedFile();
				OutputStream stream = new BufferedOutputStream(
						new FileOutputStream(aFile));
				OutputStreamWriter writer = new OutputStreamWriter(stream,
						"UTF-8");

				XMLBuilder builder = new XMLBuilder(writer);

				MBModelDirector director = new MBModelDirector(builder,
						this.browserPanel.getTreeManager().getModel());
				director.startTransport();

				// writers are buffered -> force writing of data in buffer
				writer.flush();
				stream.close();
			} catch (Exception ex) {
				System.err.println(ex);
				ex.printStackTrace();
			}
		} // else {
		// cancel was pressed
		// WindowEvent event = new WindowEvent(this,
		// WindowEvent.WINDOW_CLOSING);
		// this.processEvent(event);
		// }
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public final void actionPerformed(final ActionEvent e) {
		JFrame d = new JFrame();

		d.setSize(400, 400);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d.setVisible(true);

	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            String parameters with which the main method was called
	 */

	public static final void main(final String[] args) {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			LinkbrowserWindowStandalone frame = new LinkbrowserWindowStandalone();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			screenSize.height = (int) Math.round(0.8 * screenSize.height);
			screenSize.width = (int) Math.round(0.8 * screenSize.width);
			frame.setSize(screenSize);
			frame.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Builds up the demo tree.
	 */
	public final void treeMaker() {

		try {
			// MBModelBuilder build=new MBModelBuilder(new DefaultMBModel());
			RelationType treeRel = new RelationType();
			RelationType linker1 = new RelationType("RelationTyp Name1");
			RelationType linker2 = new RelationType("RelationTyp Name2");
			RelationType linker3 = new RelationType();

			// Hier wird ein Collection mit ICommander als Elements
			// die die MenuItems eines ContextMenus f�r die Nodes darstellen.

			Collection<ICommand> node2ContextMenu = new Vector<ICommand>();

			Collection<ICommand> node1ContextMenu = new Vector<ICommand>();

			node1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.ConsistencyCommand());
			node1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.GotoCommand());
			node1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.HighlightCommand());
			node1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.MultiplicityCommand());
			node1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.PreviewCommand());

			node2ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.PreviewCommand());

			Collection<ICommand> relation1ContextMenu = new Vector<ICommand>();
			Collection<ICommand> relation2ContextMenu = new Vector<ICommand>();
			relation1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.ConsistencyCommand());
			relation1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.GotoCommand());
			relation1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.HighlightCommand());
			relation1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.MultiplicityCommand());
			relation1ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.PreviewCommand());

			relation2ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.ConsistencyCommand());
			relation2ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.GotoCommand());
			relation2ContextMenu.add(new de.fhg.iao.matrixbrowser.toolnet_demo.HighlightCommand());

			// Hier werden die Nodes erzeugt.
			Node mainProject = new MBModelNode("MainProject");
			Node matlabFiles = new MBModelNode(
					"Matlab Files");
			Node togetherFiles = new MBModelNode(
					"Together Files");

			Node m1 = new MBModelNode("M1", "Description M1");

			Node m10 = new MBModelNode("M1_0");
			Node m11 = new MBModelNode("M1_1");
			Node m110 = new MBModelNode("M1_1_0");
			Node m2 = new MBModelNode("M2", "Description M2");
			Node m20 = new MBModelNode("M2_0");
			Node m3 = new MBModelNode("M3");
			Node t1 = new MBModelNode("T1");
			Node t10 = new MBModelNode("T1_0",
					"Description T1_0");
			Node t2 = new MBModelNode("T2");
			Node t3 = new MBModelNode("T3");
			Node t30 = new MBModelNode("T3_0");
			Node t31 = new MBModelNode("T3_1",
					"Description T3_1");

			// Hier werden die ContextMenus f�r ein paar Nodes gesetzt.

			m3.setContextMenu(node1ContextMenu);

			m110.setContextMenu(node2ContextMenu);
			t2.setContextMenu(node2ContextMenu);

			// Hier werden die Farben f�r ein paar Nodes gesetzt.

			m1.setNodeBGColor(Color.MAGENTA);

			m110.setNodeBGColor(Color.cyan);
			m20.setNodeBGColor(Color.green);

			t10.setNodeBGColor(Color.RED);
			t2.setNodeBGColor(Color.ORANGE);
			t31.setNodeBGColor(Color.PINK);
			t3.setNodeBGColor(Color.yellow);

//			// Hier werden die Nodes zu dem MBModelBuilder hinzugef�gt.
//			mainProject.add(matlabFiles);
//			// build.setRootNodeID(mainProject.getID());
//			// build.addNode(mainProject);
//			// build.addNode(matlabFiles);
//			// build.addNode(togetherFiles);
//			mainProject.add(togetherFiles);
//			// build.addNode(m1);
//			matlabFiles.add(m1);
//			// build.addNode(m10);
//			m1.add(m10);
//			// build.addNode(m11);
//			m1.add(m11);
//			m11.add(m110);
//			matlabFiles.add(m2);
//			m2.add(m20);
//			matlabFiles.add(m3);
//			togetherFiles.add(t1);
//			t1.add(t10);
//			togetherFiles.add(t2);
//			togetherFiles.add(t3);
//			t3.add(t30);
//			t3.add(t31);

			// build.addRelationType(treeRel, true);
			// build.addRelationType(linker1, false);
			// build.addRelationType(linker2, false);
			// build.addRelationType(linker3, false);

			// Hier werden die Relationen und Links erzeugt.

			Relation mainMat = new Relation(mainProject.getID(), matlabFiles
					.getID(), treeRel, RelationClass.REAL,
					RelationDirection.UNDIRECTED);

			Relation matM1 = new Relation(matlabFiles.getID(), m1.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation m1M10 = new Relation(m1.getID(), m10.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation m1M11 = new Relation(m1.getID(), m11.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation m11M110 = new Relation(m11.getID(), m110.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation matM2 = new Relation(matlabFiles.getID(), m2.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation m2M20 = new Relation(m2.getID(), m20.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation matM3 = new Relation(matlabFiles.getID(), m3.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);

			Relation mainTog = new Relation(mainProject.getID(), togetherFiles
					.getID(), treeRel, RelationClass.REAL,
					RelationDirection.UNDIRECTED);

			Relation togT1 = new Relation(togetherFiles.getID(), t1.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation t1T10 = new Relation(t1.getID(), t10.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation togT2 = new Relation(togetherFiles.getID(), t2.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation togT3 = new Relation(togetherFiles.getID(), t3.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation t3T30 = new Relation(t3.getID(), t30.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation t3T31 = new Relation(t3.getID(), t31.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);

			// Diese Relation sind Linker
			Relation m10T30 = new Relation(m10.getID(), t30.getID(), linker3,
					RelationClass.INFERRED, RelationDirection.DIRECTED,
					"Relation Description 1");
			Relation m10T31 = new Relation(m10.getID(), t31.getID(), linker2,
					RelationClass.INFERRED, RelationDirection.DIRECTED,
					"Relation_Description 1");
			Relation m2T2 = new Relation(m2.getID(), t2.getID(), linker1,
					RelationClass.REAL, RelationDirection.DIRECTED,
					"Relation Description 3");
			Relation m3T3 = new Relation(m3.getID(), t3.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m110T10 = new Relation(m110.getID(), t10.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m110T2 = new Relation(m110.getID(), t2.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);

			Relation m3T1 = new Relation(m3.getID(), t1.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m3T10 = new Relation(m3.getID(), t10.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m3T31 = new Relation(m3.getID(), t31.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m3T30 = new Relation(m3.getID(), t30.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m3T2 = new Relation(m3.getID(), t2.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			// Hier werden die JComponents f�r ein paar Linker gesetzt.
			JButton jbutton1 = new JButton();
			JTextField jTextField2 = new JTextField();
			JButton jbutton3 = new JButton();
			JTextField jTextField4 = new JTextField();
			JButton jbutton5 = new JButton();
			JButton jbutton6 = new JButton();

			jTextField2.setSize(20, 20);
			jbutton5.setSize(30, 30);
			jbutton1.setSize(50, 45);
			jTextField4.setSize(40, 30);
			jbutton3.setSize(30, 25);
			jbutton6.setSize(35, 40);

			m10T30.setJComponent(jbutton1);
			m10T31.setJComponent(jTextField2);
			m2T2.setJComponent(jbutton3);
			m3T3.setJComponent(jTextField4);
			m110T10.setJComponent(jbutton5);
			m110T2.setJComponent(jbutton6);
			// Hier werden die ContextMenus f�r ein paar Relation gesetzt.
			m10T30.setContextMenu(relation1ContextMenu);
			m2T2.setContextMenu(relation2ContextMenu);

			// Hier werden die Farbe f�r ein paar Relation gesetzt.

			m10T30.setColor(Color.RED);
			m2T2.setColor(Color.green);
			m110T10.setColor(Color.CYAN);
			m3T3.setColor(Color.MAGENTA);

			m3T1.setColor(Color.ORANGE);
			m3T30.setColor(Color.PINK);
			m3T31.setColor(Color.BLUE);
			m3T10.setColor(Color.GREEN);

			// Hier werden die Relationen und die Links zu dem MBModelBuilder
			// hinzugef�gt.
			// build.addRelation(mainMat);
			// build.addRelation(matM1);
			// build.addRelation(m1M10);
			// build.addRelation(m1M11);
			// build.addRelation(m11M110);
			// build.addRelation(matM2);
			// build.addRelation(m2M20);
			// build.addRelation(matM3);
			// build.addRelation(mainTog);
			// build.addRelation(togT1);
			// build.addRelation(t1T10);
			// build.addRelation(togT2);
			// build.addRelation(togT3);
			// build.addRelation(t3T30);
			// build.addRelation(t3T31);
			//		    
			// build.addRelation(m10T30); // Ab hier Links
			// build.addRelation(m10T31);
			// build.addRelation(m2T2);
			// build.addRelation(m3T3);
			// build.addRelation(m110T10);
			// build.addRelation(m110T2);
			//		    
			// build.addRelation(m3T30);
			// build.addRelation(m3T31);
			// build.addRelation(m3T10);
			// build.addRelation(m3T1);
			// build.addRelation(m3T2);
			// build.complete();

			// MBModel model = build.getModel();

			// TreeManager treeManager = new TreeManager(model);
			// browserPanel.setTreeManager(treeManager);
//			transportPanel.setTransportTree(new JTree(mainProject));
//			browserPanel.setTree(new JTree(mainProject));
			transportPanel.repaint();

		} catch (Exception e) {
			log.debug("exception in building the demo tree.");
			e.printStackTrace();
		}
	}

}
