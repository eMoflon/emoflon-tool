package de.fhg.iao.matrixbrowser.toolnet_demo;
/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : $Id: MatrixBrowserFrame_Infodialog.java,v 1.1 2010-03-17 12:55:25 smueller Exp $
 * 
 * Created on 15.03.02
 */

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.fhg.iao.swt.util.ResourceLoader;

public class MatrixBrowserFrame_Infodialog extends JDialog implements
		ActionListener {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	JPanel panel1 = new JPanel();

	JPanel panel2 = new JPanel();

	JPanel insetsPanel1 = new JPanel();

	JPanel insetsPanel3 = new JPanel();

	JButton button1 = new JButton();

	JLabel label1 = new JLabel();

	JLabel label2 = new JLabel();

	JLabel label3 = new JLabel();

	BorderLayout borderLayout1 = new BorderLayout();

	BorderLayout borderLayout2 = new BorderLayout();

	GridLayout gridLayout1 = new GridLayout();

	ImageIcon iaoIcon = null;

	JPanel jPanel1 = new JPanel();

	BorderLayout borderLayout3 = new BorderLayout();

	JLabel jLabel1 = new JLabel();

	public MatrixBrowserFrame_Infodialog(Frame parent) {
		super(parent);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		pack();
	}

	/** Initialise components */
	private void jbInit() throws Exception {
		setBackground(Color.WHITE);
		iaoIcon = new ImageIcon(ResourceLoader.getResource("IAO.gif"));
		this.setTitle("Info");
		setResizable(false);
		panel1.setLayout(borderLayout1);
		panel1.setOpaque(false);
		panel1.setBackground(Color.WHITE);
		panel2.setLayout(borderLayout2);
		panel2.setBackground(Color.WHITE);
		panel2.setOpaque(false);
		gridLayout1.setRows(4);
		gridLayout1.setColumns(1);
		label1
				.setText("Written by Christoph Kunz, Jan Röhrich and many others");
		label2.setText("Matrix Browser Version 2.0");
		label3.setText("Copyright (c) 2001-2005 by Fraunhofer IAO");
		insetsPanel3.setLayout(gridLayout1);
		insetsPanel3.setOpaque(false);
		insetsPanel3.setBackground(Color.WHITE);
		insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
		button1.setText("OK");
		button1.setBackground(Color.WHITE);
		button1.addActionListener(this);
		jPanel1.setLayout(borderLayout3);
		jPanel1.setOpaque(false);
		jPanel1.setBackground(Color.WHITE);
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel1.setIcon(iaoIcon);
		this.getContentPane().add(panel1, null);
		insetsPanel3.add(label2, null);
		insetsPanel3.add(label3, null);
		insetsPanel3.add(label1, null);
		panel1.add(jPanel1, BorderLayout.CENTER);
		jPanel1.add(jLabel1, BorderLayout.SOUTH);
		panel2.add(insetsPanel3, BorderLayout.NORTH);
		insetsPanel1.add(button1, null);
		panel1.add(insetsPanel1, BorderLayout.SOUTH);
		panel1.add(panel2, BorderLayout.NORTH);
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
		if (e.getSource() == button1) {
			cancel();
		}
	}
}