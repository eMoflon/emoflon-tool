package org.moflon.tutorial.sokobangamegui.addon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.log4j.BasicConfigurator;
import org.moflon.moca.sok.parser.SokParserAdapter;
import org.moflon.moca.sok.unparser.SokUnparserAdapter;
import org.moflon.tie.TGGMain;
import org.moflon.tutorial.sokobangamegui.View;
import org.moflon.core.utilities.eMoflonEMFUtil;

import SokobanLanguage.Board;
import Moca.CodeAdapter;
import Moca.MocaFactory;

/**
 *  @author Matthias Senker (Comments by Lukas Hermanns)
 *  Edited by Erika Burdon
 */

public class ParserAddOn implements GUIAddOn, ActionListener {

	private JFileChooser fileChooser;
	private View view;
	
	private JMenuItem saveMenuItem;
	private JMenuItem loadMenuItem;

	private CodeAdapter codeAdapter;

	@Override
	public void install(View view) {
		this.view = view;
				
        /* Configure parser/unparser */
        codeAdapter = MocaFactory.eINSTANCE.createCodeAdapter();
        codeAdapter.getParser().add(new SokParserAdapter());
        codeAdapter.getUnparser().add(new SokUnparserAdapter() {
        	@Override
        	protected StringTemplateGroup getStringTemplateGroup()
        			throws FileNotFoundException {
        		return new StringTemplateGroup(new FileReader(new File("../SokobanLanguage/templates/Sok.stg")));
        	}
        });

        /* Setup bar menu*/
		JMenuBar menuBar = view.getJMenuBar();
		
		JMenu parserMenu = new JMenu("Parser");
		
		loadMenuItem = new JMenuItem("parse file...");
		loadMenuItem.addActionListener(this);
		saveMenuItem = new JMenuItem("unparse into file...");
		saveMenuItem.addActionListener(this);
		
		parserMenu.add(loadMenuItem);
		parserMenu.add(saveMenuItem);
		menuBar.add(parserMenu);
		
		 /* Setup file choosers */
		fileChooser = new JFileChooser("instances/");
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileFilter filter = new FileNameExtensionFilter("levels", "sok");
		fileChooser.addChoosableFileFilter(filter);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == saveMenuItem) {
			if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
					
				/* Get full filename from file-selector */
				File parentDirectory = fileChooser.getSelectedFile().getParentFile();
				String fileName = fileChooser.getSelectedFile().getName();
				
				/* Adjust filename for XMI file-extension */
				if (fileChooser.getFileFilter().getDescription().equals("levels")
						&& !fileName.endsWith(".sok"))
				{
					fileName += ".sok";
				}
		
				try {
					TGGMain tggHelper = new TGGMain();
					Board board = view.getController().getBoard();

					//transform board to tree
					eMoflonEMFUtil.addToResourceSet(tggHelper.getResourceSet(), board);
					tggHelper.setSrc(board);
					tggHelper.integrateForward();
					MocaTree.File file = (MocaTree.File)tggHelper.getTrg();
					
					//unparse tree into file
					file.setName(fileName);
					codeAdapter.unparseFile(file, parentDirectory);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(view, "An error occurred");
					ex.printStackTrace();
				}
			}
		} else { // if (e.getSource == loadMenuItem)
			if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
				/* Get full filename from file-selector */
				File selectedFile = fileChooser.getSelectedFile();

				try {
					MocaTree.File tree = (MocaTree.File)codeAdapter.parseFile(selectedFile, null);
					TGGMain tggHelper = new TGGMain();
					
					/* Parse file into tree */
					eMoflonEMFUtil.addToResourceSet(tggHelper.getResourceSet(), tree);
					
					/* Transform tree to board */
					tggHelper.setTrg(tree);
					tggHelper.integrateBackward();
					
					/* Show board */
					view.getController().switchBoard((Board)tggHelper.getSrc());
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(view, "An error occurred");
					ex.printStackTrace();
				}
				
				
			}
		}
	}

}


