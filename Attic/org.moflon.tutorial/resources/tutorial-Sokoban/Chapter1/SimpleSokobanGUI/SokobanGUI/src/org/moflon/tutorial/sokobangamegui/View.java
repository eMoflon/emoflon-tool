package org.moflon.tutorial.sokobangamegui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.eclipse.emf.ecore.EClass;
import org.moflon.tutorial.sokobangamegui.actions.ClearBoardAction;
import org.moflon.tutorial.sokobangamegui.actions.FieldSelectedAction;
import org.moflon.tutorial.sokobangamegui.actions.LoadSaveAction;
import org.moflon.tutorial.sokobangamegui.actions.NewBoardAction;
import org.moflon.tutorial.sokobangamegui.addon.GUIAddOn;

import SokobanLanguage.Board;
import SokobanLanguage.SokobanLanguageFactory;
import SokobanLanguage.Floor;
import SokobanLanguage.Figure;

/**
 * Custom view class (inherited from JFrame).
 * This represents the whole window containing all the buttons and menu entries.
 * @author Matthias Senker (Comments by Lukas Hermanns)
 * Edited by Erika Burdon
 */
public class View extends JFrame {
	
	/* Board and controller references */
	private Board board;
	private Controller controller;
	
	/* JFrame main window and field buttons */
	private JFrame window;
	private FloorButton[][] buttons;
	
	/* Icon list (implemented as hash-map to quick access via string) */
	private Map<String, ImageIcon> icons;
	
	/**
	 * The view constructor
	 * @param controller Specifies the controller object. This must not be null!
	 * @param board Specifies the board object. This must not be null!
	 */
	public View(Controller controller, Board board) {
		/* Setup references */
		this.controller = controller;
		this.board = board;
		this.icons = new HashMap<>();
		
		/* Initialize all components and update the view for the first time */
		initializeComponents();
		updateView();
	}
	
	/**
	 * Initializes all components: creates menu entries, action listener, field buttons etc.
	 */
	private void initializeComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		/* Create the menu bar + components */
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newBoardItem = new JMenuItem("New Board...");
		JMenuItem clearBoardItem = new JMenuItem("Clear Board");
		JMenuItem loadItem = new JMenuItem("Load Model...");		
		JMenuItem saveItem = new JMenuItem("Save Model...");
		
		/* Create their action listeners */
		ActionListener loadSaveAction = new LoadSaveAction(this, saveItem, loadItem);

		newBoardItem.addActionListener(new NewBoardAction(this));
		clearBoardItem.addActionListener(new ClearBoardAction(this));
		loadItem.addActionListener(loadSaveAction);
		saveItem.addActionListener(loadSaveAction);
		
		/* Add items to menu */
		fileMenu.add(newBoardItem);
		fileMenu.add(clearBoardItem);
		fileMenu.addSeparator();
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		
		/* Add menu to GUI */
		menuBar.add(fileMenu);
		
		final JPopupMenu figureMenu = new JPopupMenu();

		JMenuItem noElementItem = new JMenuItem("none");
		noElementItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FloorButton fieldButton = (FloorButton)figureMenu.getInvoker();
				controller.setFigure(fieldButton.getFloor(), null);
			}
		});
		figureMenu.add(noElementItem);
		
		for (EClass elementClass : controller.getFigureClasses()) {
			JMenuItem elementClassItem = new JMenuItem(elementClass.getName());
			
			final EClass ec = elementClass;
			
			elementClassItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/* Setup new connection between field and figure */
					FloorButton fieldButton = (FloorButton)figureMenu.getInvoker();
					Figure newFigure = (Figure)SokobanLanguageFactory.eINSTANCE.create(ec);
					controller.setFigure(fieldButton.getFloor(), newFigure);
				}
			});
			
			figureMenu.add(elementClassItem);
		}
		
		/* Compute button size depending on the count of fields and window size */
		Dimension maxBoardSize = new Dimension(1000, 500);
		
		int width = board.getWidth();
		int height = board.getHeight();
		
		int buttonSize = Math.min(maxBoardSize.width / width, maxBoardSize.height / height);
		
		/* Create field buttons */
		buttons = new FloorButton[height][width];
		JPanel panelBoard = new JPanel(new GridLayout(height, width));
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				/* Create new field button */
				FloorButton button = new FloorButton();
				
				/* Initialize button with size and popup menu connection */
				button.setPreferredSize(new Dimension(buttonSize, buttonSize));
				button.setMargin(new Insets(0, 0, 0, 0));
				button.setComponentPopupMenu(figureMenu);
				
				/* Insert button into array */
				buttons[row][col] = button;
				panelBoard.add(button);
			}
		}
		
		/* Connect all fields with an action listener */
		for (Floor f : board.getFloors()) {
			buttons[f.getRow()][f.getCol()].setFloor(f);
			buttons[f.getRow()][f.getCol()].addActionListener(new FieldSelectedAction(this, f));
		}
		
		/* Finalize main window */
		setTitle("Sokoban GUI");
		setJMenuBar(menuBar);
		add(panelBoard);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				icons.clear();
				updateView();
			}
		});
		
		tryAddingParser();
		
		pack();
		setVisible(true);
	}
	
	/**
	 * if the ParserAddOn class exists, it will be loaded and the parser will be added to the menu.
	 * otherwise nothing will happen. this method could be easily extended, to load whatever AddOn 
	 * can be found in a certain folder.
	 */
	private void tryAddingParser() {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			Class<GUIAddOn> clazz = (Class<GUIAddOn>)classLoader.loadClass("org.moflon.tutorial.boardgamegui.addon.ParserAddOn");
			GUIAddOn parserAddon = clazz.newInstance();
			parserAddon.install(this);
		} catch (Exception e) {
			//oh well... no parser for us. (Should never occur?)
		}
	}

	/**
	 * Updates the view by setting up field text, icon, border, color etc.
	 */
	public void updateView() {
		/* Get selected figure from board */
		Figure selectedFigure = board.getSelectedFigure();
		Floor selectedField = null;
		
		if (selectedFigure != null) {
			selectedField = selectedFigure.getFloor();
		}
		
		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				/* Get field button at current row and column in array */
				FloorButton button = buttons[row][col];
				
				/* Get field from field-button */
				Floor f = button.getFloor();
				
				/* Temporary memory */
				String figureName = "";
				ImageIcon icon = null;
				
				/* Get figure icon */
				if (f.getFigure() != null) {
					figureName = f.getFigure().eClass().getName();
					icon = loadIcon(figureName);
				}
				
				/* Setup icon and text */
				if (icon != null) {
					button.setText("");
					button.setIcon(icon);
				} else {
					button.setIcon(null);
					button.setText(figureName);
				}
				
				/* Setup border and background color */
				if (f.equals(selectedField)) {
					button.setBorder(BorderFactory.createLineBorder(Color.RED));
					button.setBackground(Color.PINK);
				} else {
					button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
					button.setBackground(null);
				}
			}
		}
	}
	
	/**
	 * Loads the specified icon (resized to fit).
	 * @param name Specifies the name of the icon which is to be loaded.
	 * @return The new image icon object.
	 * @see ImageIcon
	 */
	private ImageIcon loadIcon(String name) {
		if (icons.containsKey(name)) {
			return icons.get(name);
		}
		
		ImageIcon icon = null;
		
		try {
			/* Read icon from file */
			Image img = ImageIO.read(new File("images/" + name + ".png"));
			
			/* Adjust icon size by scaling to button size */
			int buttonSize = Math.min(buttons[0][0].getWidth(), buttons[0][0].getHeight());
			Image scaled = img.getScaledInstance(buttonSize - 2, buttonSize - 2, Image.SCALE_SMOOTH);
			
			/* Allocate new image icon */
			icon = new ImageIcon(scaled);
		} catch (IOException e) {
			/* Ignore internal exceptions */
		}
		
		icons.put(name, icon);
		return icon;
	}
	
	/**
	 * @return The controller object.
	 */
	public Controller getController() {
		return controller;
	}
	
	
}





