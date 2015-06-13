class Board {
	
	// You are encouraged to review Part 2: Ecore of the eMoflon handbook for
	//	details on specifiying metamodels with MOSL. It includes EBNF
	//	descriptions of MOSL'S attribute, reference, and method syntax. It 
	//	also provides information on eMoflon's 'Graph Viewer' feature, which
	//	provides a visualization of our model as completed in this chapter!
	
	width : EInt
	height : EInt
	
	<> -> floors(0..*) : Floor
	
	-> selectedFigure(0..1) : Figure
	
	//  This method invoked each time the GUI begins to build you board and
	// 	 within an instance via "File / New Board..."
	//   A do/while control flow in implemented here because we know that no
	//	 matter what size is passed in, the SDM will have to create at least
	//	 one Floor instance, or else the resulting Board will not be a valid
	//	 model(i.e., the smallest possible Board dimensions is 1x1)
	create(w : EInt, h : EInt) : void { 
		do { 
			while [createFloor] { 
				[increaseWidth]
			} 
			[increaseHeight]
		} while [checkHeight]
		
		return
	}
	
	//  This method is used by the GUI when invoked via "File / Clear Board"
	clear() : void { 
		forEach [bindFloor] { 
			[removeFigure]
		}
		return
	}
	
	// Statement nodes are established between '<@ >' tokens and unlike patterns,
	//	cannot implement any structural changes to the model. They can however,
	//	also be used for conditional branching, providing a means of invoking
	//	methods and arbitrary Java code from SDMs.
	floorSelected(floor : Floor) : void { 
		if [checkSelected] { 
			<@alreadySelectedFigure.moveTo(floor)>
		} else { 
			if [selectFigure] { 
				return
			}
		}
		return
	}
}