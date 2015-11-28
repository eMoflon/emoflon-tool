package de.fhg.iao.matrixbrowser.toolnet_demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Collection;
import java.util.Vector;

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
 * @author akoenigs
 */
public class ToolnetDemo extends JFrame {
	private static final Logger log = Logger.getLogger(ToolnetDemo.class);

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private final Color green = new Color(150, 255, 150);
	private final Color red = new Color(255, 150, 150);

	JFrame frame;

	JMenuBar menuBar;

	JMenu fileMenu, editMenu, historyMenu, helpMenu;

	JMenuItem exitItem;

	BorderLayout borderLayout1 = new BorderLayout();

	MatrixBrowserPanel browserPanel = null;

	TransportPanel transportPanel = null;

	public ToolnetDemo() {
		super("ToolnetDemo");
		browserPanel = new MatrixBrowserPanel();
		transportPanel = new TransportPanel();
		this.setLayout(borderLayout1);
		JSplitPane matrixBrowserPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, transportPanel, browserPanel);
		matrixBrowserPane.setDividerLocation(150);
		treeMaker();
		this.add(matrixBrowserPane);
	}

	/** Main-Methode */
	public static void main(String[] args) {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			ToolnetDemo frame = new ToolnetDemo();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			screenSize.height = (int) Math.round(0.8 * screenSize.height);
			screenSize.width = (int) Math.round(0.8 * screenSize.width);
			frame.setSize(screenSize);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void treeMaker() {

		try {
			MBModelBuilder build = new MBModelBuilder(new DefaultMBModel());

			// Knoten deklarieren.
			Node smrProject = new MBModelNode("SMR");
			Node doors = new MBModelNode("Doors", "Requirements");
			Node sya1 = new MBModelNode("SYA1", "Systemanforderung");
			Node sya1_1 = new MBModelNode("SYA1.1", "Systemanforderung");
			Node sya1_2 = new MBModelNode("SYA1.2", "Systemanforderung");
			Node sya2 = new MBModelNode("SYA2", "Systemanforderung");
			Node sya2_1 = new MBModelNode("SYA2.1", "Systemanforderung");
			Node sya2_2 = new MBModelNode("SYA2.2", "Systemanforderung");
			Node sya2_3 = new MBModelNode("SYA2.3", "Systemanforderung");
			Node cte = new MBModelNode("CTE", "Testf�lle");
			Node kf1 = new MBModelNode("Klassifikation1", "Klassifikation");
			Node kl1 = new MBModelNode("Klasse1", "Klasse");
			Node kf2 = new MBModelNode("Klassifikation2", "Klassifikation");
			Node kl2 = new MBModelNode("Klasse2", "Klasse");
			Node kl3 = new MBModelNode("Klasse3", "Klasse");

			// Relationstypen deklarieren
			RelationType treeRel = new RelationType();

			RelationType syaKlassifikationRelation = new RelationType(
					"SyaKlassifikation");
			RelationType syaKlasseRelation = new RelationType("SyaKlasse");

			// Relationen deklarieren
			Relation smrDoors = new Relation(smrProject.getID(), doors.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation doorsSya1 = new Relation(doors.getID(), sya1.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation sya1Sya1_1 = new Relation(sya1.getID(), sya1_1.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation sya1Sya1_2 = new Relation(sya1.getID(), sya1_2.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation doorsSya2 = new Relation(doors.getID(), sya2.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation sya2Sya2_1 = new Relation(sya2.getID(), sya2_1.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation sya2Sya2_2 = new Relation(sya2.getID(), sya2_2.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation sya2Sya2_3 = new Relation(sya2.getID(), sya2_3.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);

			Relation smrCte = new Relation(smrProject.getID(), cte.getID(),
					treeRel, RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation cteKf1 = new Relation(cte.getID(), kf1.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation kf1Kl1 = new Relation(kf1.getID(), kl1.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation cteKf2 = new Relation(cte.getID(), kf2.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation kf2Kl2 = new Relation(kf2.getID(), kl2.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);
			Relation kf2Kl3 = new Relation(kf2.getID(), kl3.getID(), treeRel,
					RelationClass.REAL, RelationDirection.UNDIRECTED);

			Relation sya1kf1 = new Relation(sya1.getID(), kf1.getID(),
					syaKlassifikationRelation, RelationClass.REAL,
					RelationDirection.DIRECTED);
			Relation sya1_1kl1 = new Relation(sya1_1.getID(), kl1.getID(),
					syaKlasseRelation, RelationClass.REAL,
					RelationDirection.DIRECTED);
			Relation sya2kf2 = new Relation(sya2.getID(), kf2.getID(),
					syaKlassifikationRelation, RelationClass.REAL,
					RelationDirection.DIRECTED);
			Relation sya2_2kl3 = new Relation(sya2_2.getID(), kl3.getID(),
					syaKlasseRelation, RelationClass.REAL,
					RelationDirection.DIRECTED);

			// Erstelle Kontextmenus
			Collection<ICommand> nodeContextMenu = new Vector<ICommand>();
			nodeContextMenu.add(new PreviewCommand());
			nodeContextMenu.add(new HighlightCommand());
			nodeContextMenu.add(new GotoCommand());

			Collection<ICommand> relationContextMenu = new Vector<ICommand>();
			relationContextMenu.add(new ConsistencyCommand());
			relationContextMenu.add(new MultiplicityCommand());
			relationContextMenu.add(new RepairCommand());

			// Setze Kontextmenu f�r die Knoten
			sya1.setContextMenu(nodeContextMenu);
			sya1_1.setContextMenu(nodeContextMenu);
			sya1_2.setContextMenu(nodeContextMenu);
			sya2.setContextMenu(nodeContextMenu);
			sya2_1.setContextMenu(nodeContextMenu);
			sya2_2.setContextMenu(nodeContextMenu);
			sya2_3.setContextMenu(nodeContextMenu);
			kf1.setContextMenu(nodeContextMenu);
			kl1.setContextMenu(nodeContextMenu);
			kf2.setContextMenu(nodeContextMenu);
			kl2.setContextMenu(nodeContextMenu);
			kl3.setContextMenu(nodeContextMenu);

			// Setze Farben f�r die Knoten
			sya1.setColor(green);
			sya1_1.setColor(green);
			sya1_2.setColor(red);
			sya2.setColor(green);
			sya2_1.setColor(red);
			sya2_2.setColor(green);
			sya2_3.setColor(red);
			kf1.setColor(green);
			kl1.setColor(green);
			kf2.setColor(green);
			kl2.setColor(red);
			kl3.setColor(green);

			// Setze Kontextmenus f�r die Relationen
			sya1kf1.setContextMenu(relationContextMenu);
			sya1_1kl1.setContextMenu(relationContextMenu);
			sya2kf2.setContextMenu(relationContextMenu);
			sya2_2kl3.setContextMenu(relationContextMenu);

			// Setze Farben f�r die Relationen
			sya1kf1.setColor(red);
			sya1_1kl1.setColor(green);
			sya2kf2.setColor(green);
			sya2_2kl3.setColor(red);

			// �bergebe die Knoten an den MBBuilder
			build.setRootNodeID(smrProject.getID());
			build.addNode(smrProject);
			build.addNode(doors);
			build.addNode(sya1);
			build.addNode(sya1_1);
			build.addNode(sya1_2);
			build.addNode(sya2);
			build.addNode(sya2_1);
			build.addNode(sya2_2);
			build.addNode(sya2_3);
			build.addNode(cte);
			build.addNode(kf1);
			build.addNode(kl1);
			build.addNode(kf2);
			build.addNode(kl2);
			build.addNode(kl3);

			// �bergebe die RelationTypes dem MBBuilder
			build.addRelationType(treeRel, true);
			build.addRelationType(syaKlassifikationRelation, false);
			build.addRelationType(syaKlasseRelation, false);

			// �bergebe die Relationen an den MBBuilder
			build.addRelation(smrDoors);
			build.addRelation(doorsSya1);
			build.addRelation(sya1Sya1_1);
			build.addRelation(sya1Sya1_2);
			build.addRelation(doorsSya2);
			build.addRelation(sya2Sya2_1);
			build.addRelation(sya2Sya2_2);
			build.addRelation(sya2Sya2_3);
			build.addRelation(smrCte);
			build.addRelation(cteKf1);
			build.addRelation(kf1Kl1);
			build.addRelation(cteKf2);
			build.addRelation(kf2Kl2);
			build.addRelation(kf2Kl3);

			build.addRelation(sya1kf1);
			build.addRelation(sya1_1kl1);
			build.addRelation(sya2kf2);
			build.addRelation(sya2_2kl3);

			build.complete();

			MBModel model = build.getModel();

			TreeManager treeManager = new TreeManager(model);
			browserPanel.setTreeManager(treeManager);
			transportPanel.setTreeManager(treeManager);
		} catch (Exception e) {
			log.debug("BAH");
			e.printStackTrace();
		}
	}

}
