package org.moflon.tutorial.sokobangamegui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.moflon.tie.TGGMain;
import org.moflon.core.utilities.eMoflonEMFUtil;

import SokobanLanguage.Board;
import SokobanLanguage.SokobanLanguageFactory;
import SokobanLanguage.SokobanLanguagePackage;
import SokobanLanguage.Floor;
import SokobanLanguage.Figure;

/**
 * This is the controller class which controls the board and view.
 * @author Matthias Senker (Comments by Lukas Hermanns)
 * Edited by Erika Burdon
 */
public class Controller {
	
	/* The controller class knows all objects, the view and the board */
	private View view;
	private Board board;


	private SokobanLanguageFactory factory;
	
	/**
	 * Main function or rather program entry point.
	 * @param args Specifies the program arguments (or rather parameters).
	 */
	public static void main(String[] args) {
		/* Create an instance of this class and create default empty board */
		Controller controller = new Controller();
		controller.switchBoard(BoardCreator.createEmptyBoard(8, 8));
	}
	
	public Controller() {
		factory = SokobanLanguageFactory.eINSTANCE;
		
		/* If you don't call this, loading of a model will throw an exception! */
		eMoflonEMFUtil.registerXMIFactoryAsDefault();
	}
	
	/**
	 * Store a reference to the new given board object and allocates a new view.
	 * @param board Specifies the new board object.
	 */
	public void switchBoard(Board board) {
		if (board != null) {
			/* Dispose new view before allocating a new one */
			if (view != null) {
				view.dispose();
			}
			
			/* Store reference to the new board and allocate a new view */
			this.board = board;
			view = new View(this, board);
		}
	}
	
	/**
	 * Creates a new board object
	 * @param width Specifies the width (the count of fields in the X direction)
	 * @param height Specifies the height (the count of fields in the Y direction)
	 */
	public void newBoard(int width, int height) {
		/* Create a new board with the factory and the given size */
		Board board = factory.createBoard();
		
		try {
			board.create(width, height);
			printBoard(board);
			switchBoard(board);
		} catch (UnsupportedOperationException e) {
			// Ignore internal exceptions
		}
	}
	
	/**
	 * Clears the board and updates the view.
	 */
	public void clearBoard() {
		try {
			board.clear();
			view.updateView();
		} catch (UnsupportedOperationException e) {
			/* Ignore internal exceptions */
		}
	}
	
	/**
	 * Saves the current eMoflon model.
	 * @param filePath Specifies the full XMI filename.
	 */
	public void saveModel(String filePath) {
		eMoflonEMFUtil.saveModel(board, filePath);
	}
	
	/**
	 * Loads the specified eModflon model.
	 * @param filePath Specifies the full XMI filename.
	 */
	public void loadModel(String filePath) {
		/* Load the specified model and switch to the new board */
		Board newBoard = (Board)eMoflonEMFUtil.loadModel(filePath);
		switchBoard(newBoard);
	}

	/**
	 * Save board model as textual .board file 
	 * @param saveToFileName
	 */
	public void saveText(String saveToFileName){
		/* Save current board to instances/tempBoard.xmi */
		String tempBoard = "instances" + File.separator + "tempBoard.xmi";
		eMoflonEMFUtil.saveModel(board, tempBoard);
		
		try {
			TGGMain helper = new TGGMain();
			
			/* transform board .xmi instance to tree .xmi instance */
			helper.performBackward(tempBoard);
			
			/* Get current directory reference */
			File file = new File(tempBoard);
			String parentDir = file.getParent();
			
			/* Get user's desired filename from JFrame dialog */
			Path tempPath = Paths.get(saveToFileName);
			String fileName = tempPath.getFileName().toString();
			
			/* Unparse tree into .sok instance */
			helper.performUnParse(parentDir, helper, fileName);
			
			System.out.println("Board instance successfully saved!");
			
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("Failed to save .sok instance");
		}
	}
	

	/**
	 * Load a user-selected file: parse .sok into .xmi tree,
	 * then translate into .xmi board instance.
	 * @param fileToLoad gives full file path of selection
	 */
	 	public void loadText(String fileToLoad) {
		try {
			/* instances/tempTree.xmi */
			String tempTree = "instances" + File.separator + "tempTree.xmi";
			TGGMain helper = new TGGMain();

			/* Parse user selected file, save to tempTree */
			helper.performParse(fileToLoad, tempTree);

			/* Transform tempTree */
			helper = new TGGMain();
			helper.performForward(tempTree);
		 
			/* Load transformed tempTree */
			Board newBoard = (Board)eMoflonEMFUtil.loadModel(tempTree + "_FWD.xmi");
			switchBoard(newBoard);
			
			// System.out.println(".sok instance successfully loaded!");
			
		} catch (IOException e){
			// e.printStackTrace();
			System.out.println("Failed to load .sok instance");
		}
	}
	
	
	
	
	/**
	 * Selects the given field
	 * @param field Specifies the field object which is to be selected.
	 */
	public void selectField(Floor floorSpace) {
		try {
			/* Select field object and update view */
			board.floorSelected(floorSpace);
			view.updateView();
		} catch (UnsupportedOperationException e) {
			// Ignore internal exceptions
		}
	}
	
	/**
	 * Gets a list of all figure class types.
	 * @return List of EClass containing all figure class types.
	 */
	public List<EClass> getFigureClasses() {
		/* Allocate output array list */
		List<EClass> result = new ArrayList<EClass>();
		
		/* Get template class type to compare with */
		EClass elementClass = SokobanLanguagePackage.eINSTANCE.getFigure();
		
		/* Iterate over all eMoflon classifiers to find the figure class types */
		for (EClassifier c : SokobanLanguagePackage.eINSTANCE.getEClassifiers()) {
			/* Check if the current classifier matches the template class type */
			if (c instanceof EClass /*&& !c.equals(elementClass)*/) {
				if (elementClass.isSuperTypeOf((EClass)c) && !((EClass)c).isAbstract()) {
					/* Add the current classifier to the output list */
					result.add((EClass)c);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Places the given figure to the given field and updates the view.
	 * @param field Specifies the field object where the figure is to be placed on.
	 * @param figure Specifies the figure object which is to be placed on the field.
	 */
	public void setFigure(Floor floor, Figure figure) {
		floor.setFigure(figure);
		view.updateView();
	}
	
	/**
	 * Prints the board as a kind of ASCII art to the console.
	 * @param board Specifies the board object which is to be printed.
	 */
	private void printBoard(Board board) {
		/* Check parameter validity */
		if (board == null) {
			return;
		}
		
		/* Print field array */
		System.out.println(Arrays.toString(board.getFloors().toArray()));
		
		/* Allocate temporary field array */
		int w = board.getWidth();
		int h = board.getHeight();
		
		Floor[][] fields = new Floor[h][w];
		
		/* Fill temporary field array with the board fields */
		for (Floor f : board.getFloors()) {
			fields[f.getRow()][f.getCol()] = f;
		}
		
		/* Print each row */
		for (int r = 0; r < h; r++) {
			/* Print each column */
			for (int c = 0; c < w; c++) {
				printFloor(fields[r][c].getBottom());
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints the given field object.
	 * @param field Specifies the field object which is to be printed.
	 */
	private void printFloor(Floor floor) {
		if (floor == null) {
			System.out.print("[-,-]");
		} else {
			System.out.print("[" + floor.getRow() + "," + floor.getCol() + "]");
		}
	}
	
	
	public Board getBoard() {
		return board;
	}


}
