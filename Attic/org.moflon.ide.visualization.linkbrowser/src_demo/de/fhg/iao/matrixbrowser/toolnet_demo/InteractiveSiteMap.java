package de.fhg.iao.matrixbrowser.toolnet_demo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.TransportPanel;
import de.fhg.iao.matrixbrowser.VisibleNodeEvent;
import de.fhg.iao.matrixbrowser.VisibleNodeEventListener;
import de.fhg.iao.matrixbrowser.context.TreeManager;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.transport.MBModelBuilder;
import de.fhg.iao.matrixbrowser.model.transport.XMLDirector;

/**
 * Demo file how to start the MatrixBrowser as an applet used to realize an
 * interactive site map.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.1 $<br>
 *          28.02.2003 <br>
 */
public class InteractiveSiteMap extends JApplet implements
		VisibleNodeEventListener {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	MatrixBrowserPanel mbPanel = null;

	public InteractiveSiteMap() {
	}

	/**
	 * The init() method is called by the Applet container (appletviewer,java
	 * plugin,...). In this method, we will set up the look and feel and tries
	 * to load a theme pack
	 */
	public void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			getContentPane().setLayout(new BorderLayout(3, 3)); // hgap, vgap

			URL file_url = new URL(getCodeBase(), "demo_Model.xml");

			InputStreamReader xmlReader = new InputStreamReader(
					new BufferedInputStream(file_url.openStream()));

			MBModelBuilder builder = new MBModelBuilder();
			XMLDirector importer = new XMLDirector(builder, xmlReader);
			importer.startTransport();

			MBModel model = builder.getModel();
			TreeManager manager = new TreeManager(model);

			mbPanel = new MatrixBrowserPanel(manager);
			installListeners();

			JSplitPane panel = new JSplitPane();
			panel.setDividerLocation(150);
			panel.setBackground(Color.white);

			TransportPanel transportPanel = new TransportPanel();
			transportPanel.setSize(new Dimension(200, 300));
			panel.setTopComponent(transportPanel);
			panel.setBottomComponent(mbPanel);
			transportPanel.setTreeManager(manager);
			getContentPane().add(panel, BorderLayout.CENTER);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method is called by the java command line and may also be used
	 * by Java Web Start
	 */
	public static void main(String[] args) throws Exception {
		try {
			File tempFile = new File("class//mbApplet.html");

			// File tempFile = new File("mbApplet.html");
			sun.applet.AppletViewer.parse(tempFile.toURI().toURL());
		} catch (Exception e) {
			System.err.println("Exception occurred while launching applet: "
					+ e);
			e.printStackTrace();
		}
	}

	// ab hier Versuch:
	public void onNodeSelected(VisibleNodeEvent e) {

		/*
		 * //mbPanel.onNodeSelectedEvent(e); Node treeNode = e.getNode();
		 * //log.debug("treeNode: "+ treeNode + " geklickt."); Collection
		 * resRefVec = treeNode.getResources(); //log.debug("Vector: "+
		 * resRefVec); URL href_url = null; AppletContext appcon =
		 * getAppletContext(); if (resRefVec != null) { href_url = (URL)
		 * resRefVec.iterator().next(); //log.debug("href_url: "+ href_url); }
		 * if (href_url != null) { appcon.showDocument(href_url,
		 * "drittesFenster"); }
		 */
	}

	public void onNodeClicked(VisibleNodeEvent e) {
	}

	public void onNodeOver(VisibleNodeEvent e) {
		// mbPanel.onNodeOverEvent(e);
	}

	public void onNodeEdit(VisibleNodeEvent e) {
		// mbPanel.onNodeEditEvent(e);
	}

	private void installListeners() {
		mbPanel.getHorizontalTreeView().addVisibleNodeEventListener(this);
		mbPanel.getVerticalTreeView().addVisibleNodeEventListener(this);
	}

	public void onNodeMenu(VisibleNodeEvent e) {
		// We don't react on that...

	}
}
// Ende der Klasse
