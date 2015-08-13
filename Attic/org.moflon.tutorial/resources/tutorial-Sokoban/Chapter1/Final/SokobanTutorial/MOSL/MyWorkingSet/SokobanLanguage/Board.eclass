class Board {

	// You are encouraged to review Part 2: Ecore of the eMoflon handbook for
	//	details on creating models using MOSL, which includes EBNF
	//	descriptions of MOSL'S attribute, reference, and method syntax. It 
	//	also includes information on eMoflon's 'Graph Viewer' feature, which
	//	provides a visualization of our model as completed in these class
	//	files!
	
	width : EInt
	height : EInt
	
	<>- floors(0..*) -> Floor
	
	- selectedFigure(0..1) -> Figure
		
	clear() : void
	
	floorSelected(floor : Floor) : void
}