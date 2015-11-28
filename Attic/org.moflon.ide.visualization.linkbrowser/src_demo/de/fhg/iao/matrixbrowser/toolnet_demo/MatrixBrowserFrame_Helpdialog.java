package de.fhg.iao.matrixbrowser.toolnet_demo;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import de.fhg.iao.swt.util.ResourceLoader;

public class MatrixBrowserFrame_Helpdialog extends JDialog implements
		ActionListener {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	public MatrixBrowserFrame_Helpdialog(Frame parent) {
		super(parent);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);

		JEditorPane textPane = new JEditorPane();
		textPane.setEditable(false);

		// Das Fenster zentrieren
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		size.height = (int) Math.round(0.4 * size.height);
		size.width = (int) Math.round(0.6 * size.width);
		setSize(size);

		try {
			setTitle("Help");
			getContentPane().setLayout(new BorderLayout());

			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			scrollPane.setViewportView(textPane);

			URL helpURL = ResourceLoader.getResource("HelpPage.html");

			textPane.setContentType("text/html");
			textPane.setPage(helpURL);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(this);
			getContentPane().add(okButton, BorderLayout.SOUTH);
		} catch (Exception e) {
			textPane.setText("HelpPage not supported<br>" + e.toString());
		}

		// pack();
	}

	/**
	 * �berschrieben, so dass eine Beendigung beim Schlie�en des Fensters
	 * m�glich ist.
	 */
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			cancel();
		}

		super.processWindowEvent(e);
	}

	/** Dialog schlie�en */
	void cancel() {
		dispose();
	}

	/** Dialog bei Schalter-Ereignis schlie�en */
	public void actionPerformed(ActionEvent e) {
		cancel();
	}
}