class Floor {
	row : EInt
	col : EInt
	
	// Four simple references to self
	- left(0..1) -> Floor
	- right(0..1) -> Floor
	- top(0..1) -> Floor
	- bottom(0..1) -> Floor
	
	// One container reference to store a single Figure
	<> - figure(0..1) -> Figure
	
	// A Floor SHOULD know the Board it belongs to
	- board(0..1) -> Board
}