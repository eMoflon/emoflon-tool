package org.moflon.tutorial.sokobangamegui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.moflon.tutorial.sokobangamegui.View;

/**
 * Custom action listener for loading and saving '.sok' files
 * @author Erika Burdon, based on LoadSaveAction.java by Matthias Senker
 */

public class LoadSaveTextAction implements ActionListener {

	private JFileChooser fileChooser;
	private View view;
	private JComponent saveSource;
	private JComponent loadSource;
	
	public LoadSaveTextAction (View view, JComponent saveSource, JComponent loadSource) {
		/* Setup internal memory */
		this.view = view;
		this.loadSource = loadSource;
		this.saveSource = saveSource;
		
		/* Show file selector */
		fileChooser = new JFileChooser("instances/");
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileFilter filter = new FileNameExtensionFilter("levels", "sok");
		fileChooser.addChoosableFileFilter(filter);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(saveSource)) {
			if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
				/* Get full filename/filepath from file-selector*/
				String targetFilePath = fileChooser.getSelectedFile().toString();
				
				/* Check and adjust name */
				if (!targetFilePath.endsWith(".sok")) {
					targetFilePath = targetFilePath + ".sok";
				}

				view.getController().saveText(targetFilePath);
			}
		} else {
			if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
				/* Get full filename/filepath from file-selector*/
				String inputFilePath = fileChooser.getSelectedFile().toString();
				
				/* Check and adjust name */
				if (!inputFilePath.endsWith(".sok")) {
					inputFilePath = inputFilePath + ".sok";
				}
			
				view.getController().loadText(inputFilePath);
			 }
		}
		return;
		}
}
