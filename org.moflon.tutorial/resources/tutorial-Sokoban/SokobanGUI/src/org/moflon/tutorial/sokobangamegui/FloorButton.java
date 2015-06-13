package org.moflon.tutorial.sokobangamegui;

import javax.swing.JButton;

import SokobanLanguage.Floor;

/**
 * Custom button class (inherited from JButton).
 * Each button represents an own field.
 * @author Matthias Senker (Comments by Lukas Hermanns)
 * Edited by Erika Burdon
 */
public class FloorButton extends JButton {
	
	/* Reference to the field which is represented by this button */
	private Floor floor;
	
	/**
	 * @param field Sets the field object.
	 */
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	
	/**
	 * @return The field object.
	 */
	public Floor getFloor() {
		return floor;
	}
	
	/**
	 * @return The field row on the board.
	 */
	public int getRow() {
		return floor.getRow();
	}
	
	/**
	 * @return The field column on the board.
	 */
	public int getColumn() {
		return floor.getCol();
	}
}
