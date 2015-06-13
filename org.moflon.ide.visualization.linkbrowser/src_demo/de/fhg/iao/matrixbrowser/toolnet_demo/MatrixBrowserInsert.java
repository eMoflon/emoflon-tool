package de.fhg.iao.matrixbrowser.toolnet_demo;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.RepaintManager;
import javax.swing.UIManager;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.RelationPane;
import de.fhg.iao.matrixbrowser.TransportPanel;
import de.fhg.iao.matrixbrowser.context.TreeManager;
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

/**
 * This file extends the <code>MatrixBrowserDemo</code> class. It has an
 * additional menu entry for adding a node while a model is loaded. This feature
 * may be used to simulate dynamically added nodes.
 * 
 * @author <a href=mailto:jan.roehrich@iao.fraunhofer.de>Jan Roehrich </a>
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 * @version $Revision: 1.1 $<br>
 *          28.02.2003 <br>
 */
public class MatrixBrowserInsert extends JFrame {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	JMenuBar jMenuBar1 = new JMenuBar();
	JMenu jMenuFile = new JMenu();
	JMenuItem jMenuFileExit = new JMenuItem();
	JMenu jMenuHelp = new JMenu();
	JMenuItem jMenuHelpAbout = new JMenuItem();
	BorderLayout borderLayout1 = new BorderLayout();
	MatrixBrowserPanel iAOBrowserPanel1 = null;
	JMenu jMenuEdit1 = new JMenu();
	JMenuItem jMenuEditEditableOn = new JMenuItem();
	JMenuItem jMenuEditEditableOff = new JMenuItem();
	JMenuItem jMenuEditInsert = new JMenuItem();
	JMenuItem jMenuFileLoadXML = new JMenuItem();
	JMenuItem jMenuFileSaveXML = new JMenuItem();
	JMenuItem jMenuFileNew = new JMenuItem();
	JMenuItem jMenuFilePrint = new JMenuItem();
	TransportPanel myTransportPanel = null;

	public MatrixBrowserInsert() {
		iAOBrowserPanel1 = new MatrixBrowserPanel();

		jMenuBar1.setBackground(Color.white);
		jMenuHelp.setBackground(Color.white);
		jMenuFile.setBackground(Color.white);
		jMenuEditEditableOn.setBackground(Color.white);
		jMenuEditEditableOff.setBackground(Color.white);
		jMenuEditInsert.setBackground(Color.white);
		jMenuFileLoadXML.setBackground(Color.white);
		jMenuFileSaveXML.setBackground(Color.white);
		jMenuFileNew.setBackground(Color.white);
		jMenuFileExit.setBackground(Color.white);
		jMenuHelpAbout.setBackground(Color.white);
		jMenuEdit1.setBackground(Color.white);

		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

		// enable dynamic layout (layout during resizing window)
		String version = System.getProperty("java.version");
		version = version.substring(version.indexOf(".") + 1);

		// float minorversion = Float.parseFloat(version);

		/*
		 * if ((minorversion > 4.0) && (this.getToolkit() != null) && (this
		 * .getToolkit() .getDesktopProperty("awt.dynamicLayoutSupported")
		 * .equals(Boolean.TRUE))) { this.getToolkit().setDynamicLayout(true); }
		 */
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		iAOBrowserPanel1.setBackground(Color.white);
		iAOBrowserPanel1.getVisibleRelationTypes().remove(
				RelationPane.IDENTITY_RELATION);

		myTransportPanel = new TransportPanel();

		JSplitPane matrixBrowserPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, myTransportPanel, iAOBrowserPanel1);
		matrixBrowserPane.setDividerLocation(150);
		contentPane.add(BorderLayout.CENTER, matrixBrowserPane);

		jMenuFile.setText("File");
		jMenuFileNew.setText("New File");
		jMenuFileLoadXML.setText("Load XML file ...");
		jMenuFileSaveXML.setText("Save XML file ...");
		jMenuFilePrint.setText("Print ...");
		jMenuFileExit.setText("Exit");
		jMenuHelp.setText("Help");
		jMenuHelpAbout.setText("Info");
		jMenuEdit1.setText("Edit");
		jMenuEditEditableOn.setText("Edit mode on");
		jMenuEditEditableOff.setText("Edit mode off");
		jMenuEditInsert.setText("Insert Node");

		jMenuFileExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jMenuFileExit_actionPerformed(e);
			}
		});

		jMenuEditInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MBModel model = iAOBrowserPanel1.getTreeManager().getModel();
				Node newNode = new MBModelNode("Hallo Welt");
				// get a tree spanning Relation Type
				Iterator<RelationType> iter = model
						.getTreeSpanningRelationTypes().iterator();
				RelationType relationType = iter.next();
				// Create a new relation connecting the Root Node and the Node
				// to be added
				Relation newRelation = new Relation(
						model.getRootNode().getID(), newNode.getID(),
						relationType, RelationClass.REAL,
						RelationDirection.DIRECTED);
				newNode.setNew();
				// Add the node and Relation to the model. This results in a
				// TreeModelChangedEvent.
				model.addNode(newNode);
				model.addRelation(newRelation);
			}
		});
		/*
		 * public void actionPerformed( ActionEvent e){ Node newnode = new
		 * Node("hello"); MBModelBuilder builder = new MBModelBuilder(); try{ /
		 * builder.addNode(newnode); builder.complete(); MBModel model =
		 * builder.getModel(); TreeManager treeManager = new TreeManager( model
		 * ); iAOBrowserPanel1.setTreeManager( treeManager );
		 * transportPanel.setTreeManager( treeManager );
		 */
		/*
		 * //create a new model based on the model before the node is added.
		 * RelationCachedModel model =
		 * iAOBrowserPanel1.getTreeManager().getModel(); //first copy relation
		 * types. Iterator iterator = model.getRelationTypes().iterator(); while
		 * (iterator.hasNext()){ RelationType reltype= (RelationType)
		 * iterator.next(); builder. } builder.setModel(model);
		 * builder.addNode(newnode); RelationType treeRel = new
		 * RelationType("User added tree relation"); builder.addRelationType(
		 * treeRel, true ); Relation newrelation = new Relation(
		 * model.getRootNode().getID(), newnode.getID(), treeRel,
		 * RelationClass.REAL, 0 ); builder.addRelation(newrelation);
		 * builder.complete(); MBModel newmodel = builder.getModel();
		 * 
		 * TreeManager treeManager = new TreeManager( newmodel );
		 * iAOBrowserPanel1.setTreeManager( treeManager );
		 * myTransportPanel.setTreeManager( treeManager );
		 * }catch(TransportException ex){ log.debug(ex); } } });
		 */

		jMenuHelpAbout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jMenuHelpAbout_actionPerformed(e);
			}
		});

		jMenuEditEditableOn
				.addActionListener(new java.awt.event.ActionListener() {

					public void actionPerformed(ActionEvent e) {
						jMenuEditEditableOn_actionPerformed(e);
					}
				});
		jMenuEditEditableOff
				.addActionListener(new java.awt.event.ActionListener() {

					public void actionPerformed(ActionEvent e) {
						jMenuEditEditableOff_actionPerformed(e);
					}
				});

		jMenuFileLoadXML.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jMenuFileLoadXML_actionPerformed(e);
			}
		});

		jMenuFileSaveXML.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jMenuFileSaveXML_actionPerformed(e);
			}
		});

		jMenuFilePrint.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jMenuFilePrint_actionPerformed(e);
			}
		});
		
		/*
		 * jMenuFileNew.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(ActionEvent e) {
		 * IAODefaultMatrixBrowserModel theModel = new
		 * IAODefaultMatrixBrowserModel(); theModel.getRootNode().add(new
		 * IAOTreeNode("default1")); iAOBrowserPanel1.setGraphModel(theModel); }
		 * });
		 */
		jMenuHelp.add(jMenuHelpAbout);
		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuEdit1);
		jMenuBar1.add(jMenuHelp);
		jMenuFile.add(jMenuFileNew);
		jMenuFile.add(jMenuFileLoadXML);
		jMenuFile.add(jMenuFileSaveXML);
		jMenuFile.add(jMenuFilePrint);
		jMenuFile.add(jMenuFileExit);
		jMenuEdit1.add(jMenuEditEditableOn);
		jMenuEdit1.add(jMenuEditEditableOff);
		jMenuEdit1.add(jMenuEditInsert);
		this.setJMenuBar(jMenuBar1);

		this.doLayout();
	}

	/** Aktion Datei | Beenden durchgef�hrt */
	public void jMenuFileExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/** Aktion Hilfe | Info durchgef�hrt */
	public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
		MatrixBrowserFrame_Infodialog dlg = new MatrixBrowserFrame_Infodialog(
				this);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		dlg.setLocation(((frmSize.width - dlgSize.width) / 2) + loc.x,
				((frmSize.height - dlgSize.height) / 2) + loc.y);
		dlg.setModal(true);
		dlg.setVisible(true);
	}

	/**
	 * @see java.awt.Window#processWindowEvent(java.awt.event.WindowEvent)
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);

		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			jMenuFileExit_actionPerformed(null);
		}
	}

	/**
	 * enables tree editing
	 */
	void jMenuEditEditableOn_actionPerformed(ActionEvent e) {
		iAOBrowserPanel1.setEditable(true);
	}

	/**
	 * disables tree editing
	 */
	void jMenuEditEditableOff_actionPerformed(ActionEvent e) {
		iAOBrowserPanel1.setEditable(false);
	}

	/**
	 * loads XML file
	 */
	void jMenuFileLoadXML_actionPerformed(ActionEvent e) {
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

				this.iAOBrowserPanel1.setTreeManager(aManager);

				// repaint is needed to display the loaded file correct
				this.repaint();
				this.myTransportPanel.setTreeManager(aManager);
			} catch (TransportException ex) {
				ex.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} else {
			// cancel was pressed
			// WindowEvent event = new WindowEvent(this,
			// WindowEvent.WINDOW_CLOSING);
			// this.processEvent(event);
		}
	}

	/**
	 * saves XML file
	 */
	void jMenuFileSaveXML_actionPerformed(ActionEvent e) {
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
						this.iAOBrowserPanel1.getTreeManager().getModel());
				director.startTransport();

				// writers are buffered -> force writing of data in buffer
				writer.flush();
				stream.close();
			} catch (Exception ex) {
				System.err.println(ex);
				ex.printStackTrace();
			}
		} else {
			// cancel was pressed
			// WindowEvent event = new WindowEvent(this,
			// WindowEvent.WINDOW_CLOSING);
			// this.processEvent(event);
		}
	}

	/**
	 * Prints the matrixbrowser as one horizontal page. See for example
	 * http://www.javaworld.com/javaworld/jw-10-2000/jw-1020-print.html for
	 * details about the java print api or google for java printing. Note that
	 * real printing should be run in a seperate thread beacouse the application
	 * hangs when printing.
	 */
	private void jMenuFilePrint_actionPerformed(ActionEvent e) {
		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat pageFormat = job.defaultPage();
		pageFormat.setOrientation(PageFormat.LANDSCAPE);

		if (job.printDialog()) {
			job.setPrintable(new Printable() {

				public int print(Graphics graphics, PageFormat pageFormat,
						int pageIndex) throws PrinterException {
					if (pageIndex != 0) {
						return NO_SUCH_PAGE;
					}

					Graphics2D g2 = (Graphics2D) graphics;
					g2.translate((int) pageFormat.getImageableX(),
							(int) pageFormat.getImageableY());

					double scaleY = pageFormat.getImageableHeight()
							/ iAOBrowserPanel1.getHeight();
					double scaleX = pageFormat.getImageableWidth()
							/ iAOBrowserPanel1.getWidth();

					if ((iAOBrowserPanel1.getWidth() * scaleY) <= pageFormat
							.getImageableWidth()) {
						// page fits in width, so use y scaling
						scaleX = scaleY;
					}

					g2.scale(scaleY, scaleY);

					// turn off doublebuffering to force new rendering on
					// printer graphics
					RepaintManager.currentManager(iAOBrowserPanel1)
							.setDoubleBufferingEnabled(false);

					// paint main panel on graphics
					iAOBrowserPanel1.paint(g2);
					g2.scale(1 / scaleX, 1 / scaleY);

					// drwa a black rectangle outside
					g2.setColor(Color.BLACK);
					g2.drawRect(0, 0, (int) Math.round(pageFormat
							.getImageableWidth()), (int) Math.round(pageFormat
							.getImageableHeight()));

					// turn on doublebuffering
					RepaintManager.currentManager(iAOBrowserPanel1)
							.setDoubleBufferingEnabled(true);

					return PAGE_EXISTS;
				}
			}, pageFormat);

			if (job.getPrintService() != null) {
				try {
					job.print();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "No printer available!!",
						"Print error", JOptionPane.ERROR_MESSAGE, null);
			}
		}
	}

	/** Main-Methode */
	public static void main(String[] args) {
		// File aFile = null;

		try {
			/*
			 * aFile = new File("resource/themepack.zip"); if (aFile.exists()) {
			 * Skin skin =
			 * SkinLookAndFeel.loadThemePack(aFile.getAbsolutePath());
			 * SkinLookAndFeel.setSkin(skin); SkinLookAndFeel lnf = new
			 * SkinLookAndFeel(); UIManager.setLookAndFeel(lnf);
			 * UIManager.getLookAndFeelDefaults().put( "ClassLoader",
			 * lnf.getClass().getClassLoader()); } else { log.debug( "Could not
			 * load MatrixBrowser themepack \"" + aFile.getAbsolutePath() + "\".
			 * Now using system default look&feel"); }
			 */
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			MatrixBrowserInsert frame = new MatrixBrowserInsert();

			// Frames �berpr�fen, die voreingestellte Gr��e haben
			// Frames packen, die nutzbare bevorzugte Gr��eninformationen
			// enthalten, z.B. aus ihrem Layout
			frame.pack();

			/*
			 * Iterator it = UIManager.getDefaults().keySet().iterator(); while
			 * (it.hasNext()) { Object key = it.next(); log.debug(key + " " +
			 * UIManager.getDefaults().get(key)); }
			 */

			// Das Fenster zentrieren
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
}