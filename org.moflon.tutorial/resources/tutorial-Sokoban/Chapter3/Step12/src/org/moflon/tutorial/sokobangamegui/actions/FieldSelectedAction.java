package org.moflon.tutorial.sokobangamegui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.moflon.tutorial.sokobangamegui.View;

import SokobanLanguage.Floor;

/**
 * Custom action listener for field-selected action.
 * @author Matthias Senker (Comments by Lukas Hermanns)
 */
public class FieldSelectedAction implements ActionListener {

	private View view;
	private Floor floor;
	
	public FieldSelectedAction(View view, Floor floor) {
		this.view = view;
		this.floor = floor;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Select field with controller */
		view.getController().selectField(floor);
	}

}
