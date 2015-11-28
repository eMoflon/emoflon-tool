package de.fhg.iao.matrixbrowser.toolnet_demo;
/*
 * Created on 30.05.2005
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.TransportPanel;
import de.fhg.iao.matrixbrowser.context.TreeManager;
import de.fhg.iao.matrixbrowser.model.DefaultMBModel;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.matrixbrowser.model.transport.MBModelBuilder;
import de.fhg.iao.matrixbrowser.model.transport.TransportException;

/**
 * A Demo for the colored Browser, bringing its own model data to show.
 * 
 * @author Mr.Marwan
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 */
public class ColoredBrowserDemo extends JFrame {

	private static final Logger log = Logger
			.getLogger(ColoredBrowserDemo.class);

	/** Comment for <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 1L;

	/**
	 * Indicates how big the browser Window will be with respect to the screen
	 * size.
	 */
	private static final double SIZE_BY_SCRSZ = 0.8;
	/**
	 * The location of the divider between the <code>TransportPanel</code> and
	 * the <code>MatrixBrowserPanel</code>.
	 */
	private static final int DIVIDER_LOCATION = 150;

	/** This Browser's menu bar. */
	private JMenuBar menuBar;

	/** The File Menu. */
	private JMenu fileMenu;
	/** The Edit Menu. */
	private JMenu editMenu;
	/** The History Menu. */
	private JMenu historyMenu;
	/** The Help Menu. */
	private JMenu helpMenu;
	/** Menu Items. */
	private JMenuItem openItem, saveItem, printItem, exitItem, editOnItem;
	/** Further Menu Items. */
	private JMenuItem editOffItem, editAddNode, helpItem, infosItem;
	/** The Browser's BorderLayout. */
	private BorderLayout borderLayout1 = new BorderLayout();
	/** Reference to this browser's <code>BrowserPanel</code>. */
	private MatrixBrowserPanel browserPanel = null;

	/** This Browser's <code>TransportPanel</code>. */
	private TransportPanel transportPanel = null;

	/**
	 * Constructor for a ColoredBrowserDemo Application.
	 */
	public ColoredBrowserDemo() {
		super("ColoredBrowserDemo");
		browserPanel = new MatrixBrowserPanel();
		transportPanel = new TransportPanel();
		this.setLayout(borderLayout1);
		JSplitPane matrixBrowserPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, transportPanel, browserPanel);
		matrixBrowserPane.setDividerLocation(DIVIDER_LOCATION);
		treeMaker();
		this.add(matrixBrowserPane);
		this.setJMenuBar(createMenu());

	}

	/**
	 * Creator function of the Menu.
	 * 
	 * @return a MenuBar for the MatrixBrowser
	 */
	public final JMenuBar createMenu() {
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
		editAddNode = new JMenuItem("Add Node");
		helpItem = new JMenuItem("Help");
		infosItem = new JMenuItem("Infos");
		infosItem.setBackground(Color.red);

		menuBar.add(fileMenu);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(printItem);
		fileMenu.add(exitItem);
		menuBar.add(editMenu);
		editMenu.add(editOnItem);
		editMenu.add(editOffItem);
		editMenu.add(editAddNode);
		menuBar.add(historyMenu);
		menuBar.add(helpMenu);
		helpMenu.add(helpItem);
		helpMenu.add(infosItem);

		editAddNode.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Node newnode = new MBModelNode("hello");
            MBModel model = browserPanel.getTreeManager().getModel();
				MBModelBuilder builder = new MBModelBuilder(model);
				try {
					/*
					 * builder.addNode(newnode); builder.complete(); MBModel
					 * model = builder.getModel(); TreeManager treeManager = new
					 * TreeManager( model ); browserPanel.setTreeManager(
					 * treeManager ); transportPanel.setTreeManager( treeManager
					 * );
					 */
					builder.addNode(newnode);
					RelationType treeRel = new RelationType(
							"User added relation");
					builder.addRelationType(treeRel, true);
					Relation newrelation = new Relation(model.getRootNode()
							.getID(), newnode.getID(), treeRel,
							RelationClass.REAL, RelationDirection.UNDIRECTED);
					builder.addRelation(newrelation);
					builder.complete();
					MBModel newmodel = builder.getModel();

					TreeManager treeManager = new TreeManager(newmodel);
					browserPanel.setTreeManager(treeManager);
					transportPanel.setTreeManager(treeManager);
				} catch (TransportException ex) {
					log.debug(ex);
				}
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
		return (menuBar);
	}

	/**
	 * The Action for when File->Exit has been clicked.
	 * 
	 * @param e
	 *            the event triggering this action
	 */
	public final void exitItemAction(final ActionEvent e) {
		System.exit(0);
	}

	/**
	 * The Action for opening a File.
	 * 
	 * @param e
	 *            the event triggering this action
	 */
	public final void openItemAction(final ActionEvent e) {
		LinkbrowserDemo lbd = new LinkbrowserDemo();
		JFileChooser chooser = new JFileChooser(new File(System
				.getProperty("user.dir")));
		chooser.showOpenDialog(lbd);

	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            Arguments
	 */
	public static void main(final String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			ColoredBrowserDemo frame = new ColoredBrowserDemo();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			screenSize.height = (int) Math.round(SIZE_BY_SCRSZ
					* screenSize.height);
			screenSize.width = (int) Math.round(SIZE_BY_SCRSZ
					* screenSize.width);
			frame.setSize(screenSize);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Construct a simple demo tree and add it to the model.
	 */
	public final void treeMaker() {

		try {
			MBModelBuilder build = new MBModelBuilder(new DefaultMBModel());
			RelationType treeRel = new RelationType();
			RelationType linker1 = new RelationType("RelationTyp Name1");
			RelationType linker2 = new RelationType("RelationTyp Name2");
			RelationType linker3 = new RelationType();
			// create nodes
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
			// set colors for nodes
			m1.setColor(Color.MAGENTA);
			m110.setColor(Color.cyan);
			m20.setColor(Color.green);
			t10.setColor(Color.RED);
			t2.setColor(Color.ORANGE);
			t31.setColor(Color.PINK);
			t3.setColor(Color.yellow);
			// add nodes to MBModelBuilder
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
					RelationClass.REAL, RelationDirection.DIRECTED,
					"Relation Description 1");
			Relation m10T31 = new Relation(m10.getID(), t31.getID(), linker2,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m2T2 = new Relation(m2.getID(), t2.getID(), linker1,
					RelationClass.REAL, RelationDirection.DIRECTED,
					"Relation Description 3");
			Relation m3T3 = new Relation(m3.getID(), t3.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m110T10 = new Relation(m110.getID(), t10.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			Relation m110T2 = new Relation(m110.getID(), t2.getID(), linker3,
					RelationClass.REAL, RelationDirection.DIRECTED);
			// Hier werden die Farbe f�r ein paar Relation gesetzt.

			m10T30.setColor(Color.RED);
			m2T2.setColor(Color.green);
			m110T10.setColor(Color.CYAN);
			m3T3.setColor(Color.MAGENTA);

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
			build.complete();

			MBModel model = build.getModel();

			TreeManager treeManager = new TreeManager(model);
			browserPanel.setTreeManager(treeManager);
			transportPanel.setTreeManager(treeManager);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
