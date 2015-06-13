package org.moflon.tutorial.sokobangamegui;

import SokobanLanguage.Board;
import SokobanLanguage.SokobanLanguageFactory;
import SokobanLanguage.Floor;

/**
 * The BoardCreator class has only a single, static function to
 * create an instance of the "Board" class.
 * @author Matthias Senker (Comments by Lukas Hermanns)
 * Edited by Erika Burdon
 */
public class BoardCreator {
	
	/**
	 * This method is needed to create an empty board even without the create function of Board,
	 * because that is only implemented in chapter 2.
	 * @param width Specifies the board width or rather the count of fields in the X direction.
	 * @param height Specifies the board height or rather the count of fields in the Y direction.
	 * @return New instance of the "Board" class containing an empty board with the given size.
	 */
	public static Board createEmptyBoard(int width, int height) {
		/* Setup tutorial factory and create board with given size */
		SokobanLanguageFactory factory = SokobanLanguageFactory.eINSTANCE;
		Board board = factory.createBoard();
		board.setWidth(width);
		board.setHeight(height);
		
		/* Allocate field array */
		Floor[][] floors = new Floor[height][width];
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				/* Create new field with tutorial factory */
				Floor f = factory.createFloor();
				
				/* Insert field into array */
				floors[row][col] = f;
				
				/* Setup field attributes */
				f.setBoard(board);
				f.setRow(row);
				f.setCol(col);
			}
		}
		
		/* Setup field connections to the surrounding fields */
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (row > 0)
					floors[row][col].setTop(floors[row - 1][col]);
				if (row < height - 1)
					floors[row][col].setBottom(floors[row + 1][col]);
				if (col > 0)
					floors[row][col].setLeft(floors[row][col - 1]);
				if (col < width - 1)
					floors[row][col].setRight(floors[row][col + 1]);
			}
		}
				
		return board;
	}
	

}
