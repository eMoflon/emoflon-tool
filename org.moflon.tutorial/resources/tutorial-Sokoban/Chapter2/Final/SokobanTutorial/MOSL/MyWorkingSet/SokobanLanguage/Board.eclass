class Board {

	// You are encouraged to review Part 2: Ecore of the eMoflon handbook for
	//	details on specifiying metamodels with MOSL. It includes EBNF
	//	descriptions of MOSL'S attribute, reference, and method syntax. It 
	//	also provides information on eMoflon's 'Graph Viewer' feature, which
	//	provides a visualization of our model as completed in this chapter!

	width : EInt
	height : EInt
	
	<>-floors(0..*)-> Floor
	-selectedFigure(0..1)-> Figure
		
	//  This method is used by the GUI when invoked via "File / Clear Board"
	clear() : void { 
		forEach [bindFloor] { 
			if [removeFigure]
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
			[selectFigure]
		}
		return
	}
}