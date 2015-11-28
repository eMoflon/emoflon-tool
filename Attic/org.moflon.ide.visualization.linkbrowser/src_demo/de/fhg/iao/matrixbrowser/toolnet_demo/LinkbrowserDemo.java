package de.fhg.iao.matrixbrowser.toolnet_demo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
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

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.TransportPanel;
import de.fhg.iao.matrixbrowser.context.TreeManager;
import de.fhg.iao.matrixbrowser.model.DefaultMBModel;
import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.matrixbrowser.model.transport.MBModelBuilder;

/**
 * A Standalone Demo Application of the LinkBrowser, bringing its own model data
 * containing also JComponents for Relations
 * 
 * @author hajjmoussa
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 */

public class LinkbrowserDemo extends JFrame implements ActionListener {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	JMenuBar menuBar;
	JMenu fileMenu, editMenu, historyMenu, helpMenu;
	JMenuItem openItem, saveItem, printItem, exitItem, editOnItem, editOffItem,
			editAddNodeItem, helpItem, infosItem;
	BorderLayout borderLayout1 = new BorderLayout();
	MatrixBrowserPanel browserPanel = null;
	TransportPanel transportPanel = null;

	public LinkbrowserDemo() {
		super("LinkBrowserDemo");
		browserPanel = new MatrixBrowserPanel();
		transportPanel = new TransportPanel();
		this.setLayout(borderLayout1);
		JSplitPane matrixBrowserPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, transportPanel, browserPanel);
		matrixBrowserPane.setDividerLocation(150);
		treeMaker();
		this.add(matrixBrowserPane);
		this.setJMenuBar(createMenu());

	}

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
		menuBar.add(historyMenu);
		menuBar.add(helpMenu);
		helpMenu.add(helpItem);
		helpMenu.add(infosItem);

		editAddNodeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MBModel model = browserPanel.getTreeManager().getModel();
				Node newNode = new MBModelNode("hello");
				Iterator<RelationType> iter = model
						.getTreeSpanningRelationTypes().iterator();
				RelationType relationType = iter.next();
				Relation newRelation = new Relation(
						model.getRootNode().getID(), newNode.getID(),
						relationType, RelationClass.REAL,
						RelationDirection.DIRECTED);
				model.addNode(newNode);
				model.addRelation(newRelation);
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitItemAction(e);
			}
		});

		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openItemAction(e);
			}
		});
		return (menuBar);
	}

	public void exitItemAction(ActionEvent e) {
		System.exit(0);
	}

	public void openItemAction(ActionEvent e) {
		LinkbrowserDemo lbd = new LinkbrowserDemo();
		JFileChooser chooser = new JFileChooser(new File(System
				.getProperty("user.dir")));
		chooser.showOpenDialog(lbd);

	}

	public void actionPerformed(ActionEvent e) {
		JFrame d = new JFrame();

		d.setSize(400, 400);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d.setVisible(true);

	}

	/** Main-Methode */
	public static void main(String[] args) {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			LinkbrowserDemo frame = new LinkbrowserDemo();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			screenSize.height = (int) Math.round(0.8 * screenSize.height);
			screenSize.width = (int) Math.round(0.8 * screenSize.width);
			frame.setSize(screenSize);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void treeMaker() {

		try {
			MBModelBuilder build = new MBModelBuilder(new DefaultMBModel());
			RelationType treeRel = new RelationType();
			RelationType linker1 = new RelationType("RelationTyp Name1");
			RelationType linker2 = new RelationType("RelationTyp Name2");
			RelationType linker3 = new RelationType();

			// Hier wird ein Collection mit ICommander als Elements
			// die die MenuItems eines ContextMenus f�r die Nodes darstellen.

			Collection<ICommand> node2ContextMenu = new Vector<ICommand>();

			Collection<ICommand> node1ContextMenu = new Vector<ICommand>();

			node1ContextMenu.add(new CMFunktion1());
			node1ContextMenu.add(new CMFunktion2());
			node1ContextMenu.add(new CMFunktion3());
			node1ContextMenu.add(new CMFunktion4());
			node1ContextMenu.add(new CMFunktion5());

			node2ContextMenu.add(new CMFunktion2());

			Collection<ICommand> relation1ContextMenu = new Vector<ICommand>();
			Collection<ICommand> relation2ContextMenu = new Vector<ICommand>();
			relation1ContextMenu.add(new CMFunktion5());
			relation1ContextMenu.add(new CMFunktion4());
			relation1ContextMenu.add(new CMFunktion3());
			relation1ContextMenu.add(new CMFunktion2());
			relation1ContextMenu.add(new CMFunktion1());

			relation2ContextMenu.add(new CMFunktion5());
			relation2ContextMenu.add(new CMFunktion4());
			relation2ContextMenu.add(new CMFunktion3());

			Collection<ICommand> node3ContextMenu = new Vector<ICommand>();
			//node3ContextMenu.add(new ExternalICommandImplementation("test1"));

			// Hier werden die Nodes erzeugt.
			Node mainProject = new MBModelNode("MainProject");
			Node matlabFiles = new MBModelNode("Matlab Files");

			Node togetherFiles = new MBModelNode("Together Files");

			Node m1 = new MBModelNode("M1", "Description M1");

			Node m10 = new MBModelNode("M1_0");
			Node m11 = new MBModelNode("M1_1");
			Node m110 = new MBModelNode("M1_1_0");
			Node m2 = new MBModelNode("M2", "Description M2");
			Node m20 = new MBModelNode("M2_0");
			Node m3 = new MBModelNode("M3");
			Node t1 = new MBModelNode("T1");
			Node t10 = new MBModelNode("T1_0", "Description T1_0");
			Node t2 = new MBModelNode("T2");
			Node t3 = new MBModelNode("T3");
			Node t30 = new MBModelNode("T3_0");
			Node t31 = new MBModelNode("T3_1", "Description T3_1");

			// Hier werden die ContextMenus f�r ein paar Nodes gesetzt.

			m3.setContextMenu(node1ContextMenu);

			m110.setContextMenu(node2ContextMenu);
			t2.setContextMenu(node2ContextMenu);
			m1.setContextMenu(node3ContextMenu);

			// Hier werden die Farben f�r ein paar Nodes gesetzt.

			m1.setColor(Color.MAGENTA);

			m110.setColor(Color.cyan);
			m20.setColor(Color.green);

			t10.setColor(Color.RED);
			t2.setColor(Color.ORANGE);
			t31.setColor(Color.PINK);
			t3.setColor(Color.yellow);

			// Hier werden die Nodes zu dem MBModelBuilder hinzugef�gt.
			build.setRootNodeID(mainProject.getID());
			build.addNode(mainProject);
			build.addNode(matlabFiles);
			build.addNode(togetherFiles);
			build.addNode(m1);
			build.addNode(m10);
			build.addNode(m11);
			build.addNode(m110);
			build.addNode(m2);
			build.addNode(m20);
			build.addNode(m3);
			build.addNode(t1);
			build.addNode(t10);
			build.addNode(t2);
			build.addNode(t3);
			build.addNode(t30);
			build.addNode(t31);

			build.addRelationType(treeRel, true);
			build.addRelationType(linker1, false);
			build.addRelationType(linker2, false);
			build.addRelationType(linker3, false);

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
					RelationClass.INFERRED, RelationDirection.DIRECTED);
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
			build.addRelation(mainMat);
			build.addRelation(matM1);
			build.addRelation(m1M10);
			build.addRelation(m1M11);
			build.addRelation(m11M110);
			build.addRelation(matM2);
			build.addRelation(m2M20);
			build.addRelation(matM3);
			build.addRelation(mainTog);
			build.addRelation(togT1);
			build.addRelation(t1T10);
			build.addRelation(togT2);
			build.addRelation(togT3);
			build.addRelation(t3T30);
			build.addRelation(t3T31);

			build.addRelation(m10T30); // Ab hier Links
			build.addRelation(m10T31);
			build.addRelation(m2T2);
			build.addRelation(m3T3);
			build.addRelation(m110T10);
			build.addRelation(m110T2);

			build.addRelation(m3T30);
			build.addRelation(m3T31);
			build.addRelation(m3T10);
			build.addRelation(m3T1);
			build.addRelation(m3T2);
			build.complete();

			MBModel model = build.getModel();

			TreeManager treeManager = new TreeManager(model);
			browserPanel.setTreeManager(treeManager);
			transportPanel.setTreeManager(treeManager);

		} catch (Exception e) {
		}
	}

}
