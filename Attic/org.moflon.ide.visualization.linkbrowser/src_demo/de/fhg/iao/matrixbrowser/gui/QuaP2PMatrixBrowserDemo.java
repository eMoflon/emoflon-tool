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
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.matrixbrowser.model.elements.Node;
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
 * @version $Revision: 1.3 $
 */
public final class QuaP2PMatrixBrowserDemo {
	/**
	 * Constant defining the size of the constructed window divided by the
	 * overall screen size.
	 */
	private static final double SIZE_BY_SCRSZ = 0.8;

	/**
	 * Empty private contructor to impede contructor calls on utility class.
	 */
	private QuaP2PMatrixBrowserDemo() {
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
			// addListener(matrixBrowser);
//			modelcalls(matrixBrowser);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Some calls to the model showing the possibilities one has by using the
	 * interface.
	 * 
	 * @param model
	 *            the model to be treated by the calls.
	 */
	private static void modelcalls(final VisibleModel model) {
		RelationType treeRelationType = new RelationType("TreeRelationstyp");
		model.addTreeSpanningRelationType(treeRelationType);
		model.addNode(new StringID("Doors"));
		model.addRelation(treeRelationType, model.getRootNodeID(),
				new StringID("Doors"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("DoorsLink"));
		model.addNode(new StringID("EnterpriseArchitect"));
		model.addRelation(treeRelationType, model.getRootNodeID(),
				new StringID("EnterpriseArchitect"),
				RelationDirection.DIRECTED, RelationClass.REAL, new StringID(
						"EALink"));
		model.addNode(new StringID("Car"));
		model.addRelation(treeRelationType,
				new StringID("EnterpriseArchitect"), new StringID("Car"),
				RelationDirection.DIRECTED, RelationClass.REAL, new StringID(
						"EA-Car"));
		Node node = model.getNode(new StringID("Car"));

		node
				.setDescription("Developer: Patrick Mukherjee || State: Online || Project for the car Prototype.");
		List<ICommand> menu = new ArrayList<ICommand>();
		/*
		menu.add(new ExternalICommandImplementation("Contact Patrick Mukherjee"));
		menu.add(new ExternalICommandImplementation("Chat with all online Developers"));
		menu.add(new ExternalICommandImplementation("Check Links"));
		menu.add(new ExternalICommandImplementation("Check for new Version"));
		menu.add(new ExternalICommandImplementation("Edit Node Properties"));
		menu.add(new ExternalICommandImplementation("Add manual Link"));
		*/
		node.setContextMenu(menu);

		model.addNode(new StringID("carversion1"));
		/*
		 * model.addRelation(treeRelationType, new StringID("Car"), new
		 * StringID("carversion1"), RelationDirection.DIRECTED,
		 * RelationClass.REAL, new StringID("carversion1link"));
		 * model.getNode(new StringID("carversion1")).setName("Version 1");
		 */
		model.addNode(new StringID("carversion 1.1"));
		/*
		 * model.addRelation(treeRelationType, new StringID("Car"), new
		 * StringID("carversion 1.1"), RelationDirection.DIRECTED,
		 * RelationClass.REAL, new StringID("carversion2link"));
		 * model.getNode(new StringID("carversion2")).setName("Version 2");
		 */

		model.addNode(new StringID("Car - USE-Case"));
		// model.addRelation(treeRelationType, new StringID("carversion1"),
		// new StringID("Car - USE-Case"), RelationDirection.DIRECTED,
		// RelationClass.REAL,
		// new StringID("Car-USE-Case"));
		model.getNode(new StringID("Car - USE-Case")).setDescription(
				"Developer: Felix Klar, State: Offline ");
		//menu.add(new ExternalICommandImplementation("Send Mail to Felix Klar"));
		//menu.add(new ExternalICommandImplementation("Check Links"));
		//menu.add(new ExternalICommandImplementation("Check for new Version"));
		model.getNode(new StringID("Car - USE-Case")).setContextMenu(menu);
		model.addNode(new StringID("Cruise-Control"));
		model.addRelation(treeRelationType, new StringID("Car"), new StringID(
				"Cruise-Control"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("CarToCruiseControl"));

		model.addNode(new StringID("Cruise-Control-USE-Case"));
		model.addRelation(treeRelationType, new StringID("Cruise-Control"),
				new StringID("Cruise-Control-USE-Case"),
				RelationDirection.DIRECTED, RelationClass.REAL, new StringID(
						"CruiseControlMain to USE case"));

		model.setHorizontalRootNode(new StringID("Doors"));
		model.setVerticalRootNode(new StringID("EnterpriseArchitect"));

		model.addNode(new StringID("Car - Deployment"));
		model.addRelation(treeRelationType, new StringID("Car"), new StringID(
				"Car - Deployment"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("CarToDeployment"));

		model.addNode(new StringID("Lights"));
		model.addRelation(treeRelationType, new StringID("Car"), new StringID(
				"Lights"), RelationDirection.DIRECTED, RelationClass.REAL,
				new StringID("Car-lights"));

		model.addNode(new StringID("Lights - Class"));
		model.addRelation(treeRelationType, new StringID("Lights"),
				new StringID("Lights - Class"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("LightsToLightsClass"));

		model.addNode(new StringID("GPS"));
		model.addRelation(treeRelationType, new StringID("Car"), new StringID(
				"GPS"), RelationDirection.DIRECTED, RelationClass.REAL,
				new StringID("Car-GPS"));

		model.addNode(new StringID("GPS - Class"));
		model.addRelation(treeRelationType, new StringID("GPS"), new StringID(
				"GPS - Class"), RelationDirection.DIRECTED, RelationClass.REAL,
				new StringID("GPS-GPSClass"));

		model.addNode(new StringID("Lights-Control-USE-Case"));
		model.addRelation(treeRelationType, new StringID("Lights"),
				new StringID("Lights-Control-USE-Case"),
				RelationDirection.DIRECTED, RelationClass.REAL, new StringID(
						"Lights-to-lights-controlUseCase"));

		model.addNode(new StringID("GPS USE-Case"));
		model.addRelation(treeRelationType, new StringID("GPS"), new StringID(
				"GPS USE-Case"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("GPS-GPS USE-Case"));

		model.addNode(new StringID("D-Car"));
		model.addRelation(treeRelationType, new StringID("Doors"),
				new StringID("D-Car"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("DoorsProjectToCar"));

		model.addRelation(new RelationType("nochganzerlink"), new StringID(
				"carversion1"), new StringID("D-Car"),
				RelationDirection.DIRECTED, RelationClass.REAL, new StringID(
						"Versionganzerlink"));

		model.addRelation(new RelationType("kaputterlink"), new StringID(
				"carversion 1.1"), new StringID("D-Car"),
				RelationDirection.DIRECTED, RelationClass.REAL, new StringID(
						"Versionganzerlink"));
		model.addNode(new StringID("D-GPS"));
		model.getNode(new StringID("D-GPS")).setName("GPS");
		model.addRelation(treeRelationType, new StringID("D-Car"),
				new StringID("D-GPS"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("DoorsCar-DoorsGPS"));

		model.addNode(new StringID("GPS-Anforderungsliste"));
		model.addRelation(treeRelationType, new StringID("D-GPS"),
				new StringID("GPS-Anforderungsliste"),
				RelationDirection.DIRECTED, RelationClass.REAL, new StringID(
						"DoorsGpsZuAnforderungsliste"));

		// model.addRelation(new RelationType("Anforderungsliste-zu-USE-Case"),
		// new StringID("D-GPS"), new StringID("GPS"),
		// RelationDirection.UNDIRECTED, RelationClass.IDENTITY, new
		// StringID("GPS-Anforderungsliste zu GPS-Modell"));
		model.addRelation(new RelationType("Anforderungsliste-zu-USE-Case"),
				new StringID("GPS"), new StringID("GPS USE-Case"),
				RelationDirection.DIRECTED, RelationClass.REAL, new StringID(
						"GPS-Anforderungsliste-zu-USE-Case"));
		model.addRelation(new RelationType("Anforderungsliste-zu-USE-Case"),
				new StringID("GPS-Anforderungsliste"), new StringID(
						"GPS - Class"), RelationDirection.DIRECTED,
				RelationClass.INFERRED, new StringID(
						"GPS-Anforderungsliste-zu-Klassendiagramm"));

		model.addNode(new StringID("D-CruiseControl"));
		model.addRelation(treeRelationType, new StringID("D-Car"),
				new StringID("D-CruiseControl"), RelationDirection.DIRECTED,
				RelationClass.REAL,
				new StringID("DoorsCarToDoorsCruiseControl"));

		model.addNode(new StringID("Implementation"));
		model.addRelation(treeRelationType, model.getRootNodeID(),
				new StringID("Implementation"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("rootImplemenation"));
		model.addNode(new StringID("Karosserie"));
		model.addRelation(treeRelationType, new StringID("Implementation"),
				new StringID("Karosserie"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("ImplementationKarosserie"));
		model.addNode(new StringID("Cockpit"));
		model.addRelation(treeRelationType, new StringID("Implementation"),
				new StringID("Cockpit"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("ImplementaionCockpit"));
		model.addNode(new StringID("Software"));
		model.addRelation(treeRelationType, new StringID("Implementation"),
				new StringID("Software"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("ImplementationSoftware"));
		model.addNode(new StringID("Java"));
		model.addRelation(treeRelationType, new StringID("Software"),
				new StringID("Java"), RelationDirection.DIRECTED,
				RelationClass.REAL, new StringID("Software-Java"));

	}

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
	 * @version $Revision: 1.3 $
	 */
	public static class MyListener implements VisibleModelListener {

		private static final Logger log = Logger.getLogger(MyListener.class);

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
