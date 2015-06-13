class Board {

	// You are encouraged to review Part 2: Ecore of the eMoflon handbook for
	//	details on specifiying metamodels with MOSL. It includes EBNF
	//	descriptions of MOSL'S attribute, reference, and method syntax. It 
	//	also provides information on eMoflon's 'Graph Viewer' feature, which
	//	provides a visualization of our model as completed in this chapter!
	
	width : EInt
	height : EInt
	
	<>- floors(0..*) -> Floor
	
	- selectedFigure(0..1) -> Figure
}