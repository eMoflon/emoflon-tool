package org.moflon.tutorial.sokobangamegui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.moflon.tutorial.sokobangamegui.View;

/**
 * Custom action listener for load- and save-action.
 * @author Matthias Senker (Comments by Lukas Hermanns)
 * Edited by Erika Burdon
 */
public class LoadSaveAction implements ActionListener {
	private JFileChooser fileChooser;
	private View view;
	
	private JComponent saveSource;
	private JComponent loadSource;
	
	public LoadSaveAction(View view, JComponent saveSource, JComponent loadSource) {
		/* Setup internal memory */
		this.view = view;
		this.loadSource = loadSource;
		this.saveSource = saveSource;
		
		/* Show file selector */
		fileChooser = new JFileChooser("instances/");
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileFilter filter = new FileNameExtensionFilter("models", "xmi");
		fileChooser.addChoosableFileFilter(filter);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(saveSource)) {
			if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
				/* Get full filename from file-selector */
				String filePath = fileChooser.getSelectedFile().getPath();
				
				/* Adjust filename for XMI file-extension */
				if (fileChooser.getFileFilter().getDescription().equals("models")
						&& !filePath.endsWith(".xmi"))
				{
					filePath += ".xmi";
				}
				
				/* Write the model to file */
				view.getController().saveModel(filePath);
			}
		} else {
			if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
				/* Get full filename from file-selector */
				String filePath = fileChooser.getSelectedFile().getPath();
				
				/* Read the model from file */
				view.getController().loadModel(filePath);
			}
		}
	}
	
	
}
