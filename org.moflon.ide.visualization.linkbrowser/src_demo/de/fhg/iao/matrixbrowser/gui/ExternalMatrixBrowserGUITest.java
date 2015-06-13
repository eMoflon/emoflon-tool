/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 * $Id: ExternalMatrixBrowserGUITest.java,v 1.3 
 * 2008/08/21 09:14:11 schotti Exp $
 * 
 * Created on Aug 12, 2008
 */
package de.fhg.iao.matrixbrowser.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.matrixbrowser.model.transport.ExternalModelMatrixBrowser;
import de.fhg.iao.matrixbrowser.model.transport.VisibleModel;
import de.fhg.iao.matrixbrowser.model.transport.VisibleModelListener;
import de.fhg.iao.swt.util.uniqueidentifier.StringID;

/**
 * A Demo Module for the ExternalMatrixBrowserGUI.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.2 $
 */
public final class ExternalMatrixBrowserGUITest {

	private static final Logger log = Logger
			.getLogger(ExternalMatrixBrowserGUITest.class);

	/**
	 * Constant defining the size of the constructed window divided by the
	 * overall screen size.
	 */
	private static final double SIZE_BY_SCRSZ = 0.8;

   private static RelationType COMPOSITE_RELATION_TYPE;

   private static StringID sourceRootID;

   private static StringID targetRootID;

	/**
	 * Empty private contructor to impede contructor calls on utility class.
	 */
	private ExternalMatrixBrowserGUITest() {
		// empty constructor, just to be sure that it will not be called.
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            String parameters with which the main method was called
	 */

	public static void main(final String[] args) {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// Construct a window where we put the MatrixBrowser in.
			JFrame frame = new JFrame();
			ExternalModelMatrixBrowser matrixBrowser = new ExternalModelMatrixBrowser();
			frame.add(matrixBrowser.getPanel());
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			screenSize.height = (int) Math.round(SIZE_BY_SCRSZ
					* screenSize.height);
			screenSize.width = (int) Math.round(SIZE_BY_SCRSZ
					* screenSize.width);
			frame.setSize(screenSize);
			frame.doLayout();
			frame.setVisible(true);
			addListener(matrixBrowser);
			funkmodelcalls(matrixBrowser);
			incrementalcalls(matrixBrowser);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static void incrementalcalls(VisibleModel model)
   {
      model.addNode(new StringID("newNode"));
      
      model.addRelation(COMPOSITE_RELATION_TYPE, sourceRootID, new StringID(
            "newNode"), RelationDirection.DIRECTED, RelationClass.REAL,
            new StringID("Verbindung horizontalRoot,newNode"));
      
      model.addNode(new StringID("nextNewNode"));
      model.addRelation(COMPOSITE_RELATION_TYPE, new StringID("newNode"), new StringID("nextNewNode"),
            RelationDirection.DIRECTED, RelationClass.REAL, new StringID("Verbindung horizontalRoot,nextNewNode"));
            
      model.removeNode(new StringID("nextNewNode"));
   }

   /**
	 * Some calls to the model showing the possibilities one has by using the
	 * interface.
	 * 
	 * @param model
	 *            the model to be treated by the calls.
	 */
	private static void modelcalls(final VisibleModel model) {
		model.addNode(new StringID("firstnode"));
		model.addNode(new StringID("secondnode"));
		model.addNode(new StringID("thirdnode"));
		RelationType treeRelationType = new RelationType("TreeRelationstyp");
		model.addTreeSpanningRelationType(treeRelationType);
		model.addRelation(treeRelationType, model.getRootNodeID(),
				new StringID("firstnode"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID(
						"Verbindung horizontalRoot,firstnode"));

		model.addRelation(treeRelationType, model.getRootNodeID(),
				new StringID("thirdnode"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("HorinotalRootRelation"));
		model.setVerticalRootNode(new StringID("firstnode"));
		model.setHorizontalRootNode(new StringID("thirdnode"));

		model.addRelation(treeRelationType, new StringID("firstnode"),
				new StringID("secondnode"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("Verbindung einszwei"));
		// model.setVerticalRootNode(new StringID("firstnode"));

		RelationType realRelationType = new RelationType("Real Relation");
		model.addRelationType(realRelationType);
		model.addRelation(realRelationType, new StringID("firstnode"),
				new StringID("thirdnode"), RelationDirection.UNDIRECTED,
				RelationClass.REAL, new StringID("Verbindung einsdrei"));
		model.addNode(new StringID("anothernode"));
		model.addRelation(realRelationType, new StringID("firstnode"),
				new StringID("anothernode"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("anotherrelation"));
		model.removeNode(new StringID("anothernode"));

//		Collection<ICommand> menu = new ArrayList<ICommand>();
//		menu.add("hello");
//		menu.add("goodbye");
//		menu.add("test1");
//		menu.add("test2");
//		model.getNode(new StringID("firstnode")).setContextMenu(menu);
	}

	private static void funkmodelcalls(final VisibleModel model) {
		COMPOSITE_RELATION_TYPE = new RelationType(
				"composite");
		final RelationType DEFAULT_RELATION_TYPE = new RelationType("default");
		sourceRootID = new StringID("Source model1");
		targetRootID = new StringID("Target model1");
		model.addNode(sourceRootID);
		model.addNode(targetRootID);

		// model.addRelationType(COMPOSITE_RELATION_TYPE);
		/* relation types */
		model.addTreeSpanningRelationType(COMPOSITE_RELATION_TYPE);
		model.addRelationType(DEFAULT_RELATION_TYPE);

		model.addRelation(COMPOSITE_RELATION_TYPE, model.getRootNodeID(),
				sourceRootID, RelationDirection.DIRECTED, RelationClass.REAL,
				new StringID("rootNode -> sourceRootNode"));
		model.addRelation(COMPOSITE_RELATION_TYPE, model.getRootNodeID(),
				targetRootID, RelationDirection.DIRECTED, RelationClass.REAL,
				new StringID("rootNode -> targetRootNode"));

		model.setVerticalRootNode(targetRootID);
		model.setHorizontalRootNode(sourceRootID);

		// Linkbrowser Tests
		model.addNode(new StringID("firstnode"));
		model.addNode(new StringID("secondnode"));
		model.addNode(new StringID("thirdnode"));

		model.addRelation(COMPOSITE_RELATION_TYPE, sourceRootID, new StringID(
				"firstnode"), RelationDirection.DIRECTED, RelationClass.REAL,
				new StringID("Verbindung horizontalRoot,firstnode"));
		model.addRelation(COMPOSITE_RELATION_TYPE, new StringID("firstnode"),
				new StringID("secondnode"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("Verbindung einszwei"));
		model.addRelation(COMPOSITE_RELATION_TYPE, targetRootID, new StringID(
				"thirdnode"), RelationDirection.DIRECTED, RelationClass.REAL,
				new StringID("HorinotalRootRelation"));

	}/*
	 * final RelationType COMPOSITE_RELATION_TYPE = new
	 * RelationType("composite"); final RelationType DEFAULT_RELATION_TYPE = new
	 * RelationType("default"); StringID sourceRootID = new
	 * StringID("Source model1"); StringID targetRootID = new
	 * StringID("Target model1"); model.addNode(sourceRootID);
	 * model.addNode(targetRootID);
	 * 
	 * model.addRelationType(COMPOSITE_RELATION_TYPE); / relation types
	 */

	/*
	 * model.addTreeSpanningRelationType(COMPOSITE_RELATION_TYPE);
	 * model.addRelationType(DEFAULT_RELATION_TYPE);
	 * 
	 * model.addRelation(COMPOSITE_RELATION_TYPE, model.getRootNodeID(),
	 * sourceRootID, RelationDirection.DIRECTED, RelationClass.REAL, new
	 * StringID("rootNode -> sourceRootNode"));
	 * model.addRelation(COMPOSITE_RELATION_TYPE, model.getRootNodeID(),
	 * targetRootID, RelationDirection.DIRECTED, RelationClass.REAL, new
	 * StringID("rootNode -> targetRootNode"));
	 * model.setHorizontalRootNode(sourceRootID);
	 * model.setVerticalRootNode(targetRootID);
	 * 
	 * }
	 */
	/**
	 * adds a listener to the given Model, reacting on all Events such as
	 * Visible Node events and visible Relation Events.
	 * 
	 * @param model
	 *            the model to register the listener on
	 */
	private static void addListener(final VisibleModel model) {
//		model.addVisibleModelListener(new MyListener());
	}

	/**
	 * a simple Listener class, receiving events and writing them out on the
	 * console.
	 * 
	 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
	 * @version $Revision: 1.2 $
	 */
	public static class MyListener implements VisibleModelListener {

		/**
		 * {@inheritDoc}
		 * 
		 * @see de.fhg.iao.matrixbrowser.model.transport.VisibleModelListener
		 *      #update(java.util.EventObject)
		 */
		public final void update(final EventObject e) {
			log.debug("External received an event: " + e.toString());
		}
	}
}
