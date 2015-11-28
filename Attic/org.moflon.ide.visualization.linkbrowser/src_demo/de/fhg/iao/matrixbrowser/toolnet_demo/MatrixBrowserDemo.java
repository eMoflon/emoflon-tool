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

import de.fhg.iao.matrixbrowser.HistoryMenu;
import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.RelationPane;
import de.fhg.iao.matrixbrowser.TransportPanel;
import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.context.TreeManager;
import de.fhg.iao.matrixbrowser.model.DefaultMBModel;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.transport.MBModelBuilder;
import de.fhg.iao.matrixbrowser.model.transport.MBModelDirector;
import de.fhg.iao.matrixbrowser.model.transport.TransportException;
import de.fhg.iao.matrixbrowser.model.transport.XMLBuilder;
import de.fhg.iao.matrixbrowser.model.transport.XMLDirector;

/**
 * Demo file how to start the MatrixBrowser and how to configure various widgets
 * (TreeViews/MatrixPanels) to change appearance <br>
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.1 $<br>
 *          28.02.2003 <br>
 */
public class MatrixBrowserDemo extends JFrame {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private static final int DIVIDER_LOCATION = 150;
	JPanel contentPane;

	JMenuBar jMenuBar1 = new JMenuBar();

	JMenu jMenuFile = new JMenu();

	JMenuItem jMenuFileExit = new JMenuItem();

	JMenu jMenuHelp = new JMenu();

	JMenuItem jMenuHelpHelp = new JMenuItem();

	JMenuItem jMenuHelpAbout = new JMenuItem();

	BorderLayout borderLayout1 = new BorderLayout();

	MatrixBrowserPanel iAOBrowserPanel1 = null;

	JMenu jMenuEdit1 = new JMenu();

	JMenuItem jMenuEditEditableOn = new JMenuItem();

	JMenuItem jMenuEditEditableOff = new JMenuItem();

	JMenuItem jMenuFileLoadXML = new JMenuItem();

	JMenuItem jMenuFileSaveXML = new JMenuItem();

	JMenuItem jMenuFileNew = new JMenuItem();

	JMenuItem jMenuFilePrint = new JMenuItem();

	JMenuItem jMenuFileLoadLocalization = new JMenuItem();

	TransportPanel myTransportPanel = null;

	HistoryMenu mHistoryMenu;

	public MatrixBrowserDemo() {
		// First Customize UIManagers Look&Feel defaults
		setLAFDefaults();

		iAOBrowserPanel1 = new MatrixBrowserPanel();

		jMenuBar1.setBackground(Color.white);
		jMenuHelp.setBackground(Color.white);
		jMenuFile.setBackground(Color.white);
		jMenuEditEditableOn.setBackground(Color.white);
		jMenuEditEditableOff.setBackground(Color.white);
		jMenuFileLoadXML.setBackground(Color.white);
		jMenuFileSaveXML.setBackground(Color.white);
		jMenuFileNew.setBackground(Color.white);
		jMenuFileExit.setBackground(Color.white);
		jMenuHelpAbout.setBackground(Color.white);
		jMenuHelpHelp.setBackground(Color.white);
		jMenuEdit1.setBackground(Color.white);

		jMenuFileLoadLocalization.setBackground(Color.white);

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
		 * .equals(Boolean.TRUE))) { this.getToolkit().setDynamicLayout(true);
		 */
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		iAOBrowserPanel1.getVisibleRelationTypes().remove(
				RelationPane.IDENTITY_RELATION);

		myTransportPanel = new TransportPanel();

		JSplitPane matrixBrowserPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, myTransportPanel, iAOBrowserPanel1);
		matrixBrowserPane.setDividerLocation(DIVIDER_LOCATION);
		contentPane.add(BorderLayout.CENTER, matrixBrowserPane);

		jMenuFile.setText("File");
		jMenuFileNew.setText("New File");
		jMenuFileLoadXML.setText("Load XML file ...");
		jMenuFileSaveXML.setText("Save XML file ...");
		jMenuFilePrint.setText("Print ...");
		jMenuFileExit.setText("Exit");
		jMenuHelp.setText("Help");
		jMenuHelpAbout.setText("Info");
		jMenuHelpHelp.setText("Help");
		jMenuEdit1.setText("Edit");
		jMenuEditEditableOn.setText("Edit mode on");
		jMenuEditEditableOff.setText("Edit mode off");
		jMenuFileLoadLocalization.setText("Load language localization ...");

		jMenuFileExit.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				jMenuFileExit_actionPerformed(e);
			}
		});
		jMenuHelpAbout.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				jMenuHelpAbout_actionPerformed(e);
			}
		});

		jMenuHelpHelp.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				jMenuHelpHelp_actionPerformed(e);
			}
		});

		jMenuEditEditableOn
				.addActionListener(new java.awt.event.ActionListener() {

					public void actionPerformed(final ActionEvent e) {
						jMenuEditEditableOn_actionPerformed(e);
					}
				});
		jMenuEditEditableOff
				.addActionListener(new java.awt.event.ActionListener() {

					public void actionPerformed(final ActionEvent e) {
						jMenuEditEditableOff_actionPerformed(e);
					}
				});

		jMenuFileLoadXML.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				jMenuFileLoadXML_actionPerformed(e);
			}
		});

		jMenuFileSaveXML.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				jMenuFileSaveXML_actionPerformed(e);
			}
		});

		jMenuFilePrint.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				jMenuFilePrint_actionPerformed(e);
			}
		});

		jMenuFileLoadLocalization
				.addActionListener(new java.awt.event.ActionListener() {

					public void actionPerformed(final ActionEvent e) {
						jMenuFileLoadLocalization_actionPerformed(e);
					}
				});
		
		/*
		 * jMenuFileNew.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(ActionEvent e) {
		 * IAODefaultMatrixBrowserModel theModel = new
		 * IAODefaultMatrixBrowserModel(); theModel.getRootNode().add(new
		 * IAOTreeNode("default1")); iAOBrowserPanel1.setGraphModel(theModel); }
		 */
		jMenuHelp.add(jMenuHelpHelp);
		jMenuHelp.add(jMenuHelpAbout);
		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuEdit1);

		jMenuFile.add(jMenuFileNew);
		jMenuFile.add(jMenuFileLoadXML);
		jMenuFile.add(jMenuFileSaveXML);
		jMenuFile.add(jMenuFileLoadLocalization);
		jMenuFile.add(jMenuFilePrint);
		jMenuFile.add(jMenuFileExit);
		jMenuEdit1.add(jMenuEditEditableOn);
		jMenuEdit1.add(jMenuEditEditableOff);

		mHistoryMenu = new HistoryMenu("History", iAOBrowserPanel1);
		mHistoryMenu.setOpaque(false);

		jMenuBar1.add(mHistoryMenu);
		iAOBrowserPanel1.setHistoryMenu(mHistoryMenu);

		this.setJMenuBar(jMenuBar1);

		jMenuBar1.add(jMenuHelp);
		this.doLayout();
	}

	/**
	 * Custimize LaF�s default settings
	 */
	public void setLAFDefaults() {
	}

	/** Aktion Datei | Beenden durchgefuehrt */
	public void jMenuFileExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/** Aktion Hilfe | Info durchgefuehrt */
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

	/** Aktion Hilfe | Info durchgefuehrt */
	public void jMenuHelpHelp_actionPerformed(ActionEvent e) {
		MatrixBrowserFrame_Helpdialog dlg = new MatrixBrowserFrame_Helpdialog(
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
	 * Action File | loadLocalization
	 */
	public void jMenuFileLoadLocalization_actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser(new File(System
				.getProperty("user.dir")));
		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File aFile = chooser.getSelectedFile();

			UIResourceRegistry.getInstance().loadLocalization(
					aFile.getParentFile().getPath().concat(File.separator),
					aFile.getName());
			UIResourceRegistry.getInstance().loadIcons(
					"de/fhg/iao/matrixbrowser/resource/");
		}
		// UIResourceRegistry.getInstance().load(localization);
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
						new DefaultMBModel());

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
					// printer
					// graphics
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
			 * Now using system default look&feel");
			 */
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			MatrixBrowserDemo frame = new MatrixBrowserDemo();

			// Frames �berpr�fen, die voreingestellte Gr��e haben
			// Frames packen, die nutzbare bevorzugte Gr��eninformationen
			// enthalten, z.B. aus ihrem Layout
			frame.pack();

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