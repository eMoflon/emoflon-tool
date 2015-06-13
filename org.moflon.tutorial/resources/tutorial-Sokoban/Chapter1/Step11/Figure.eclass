abstract class Figure {
	
	// A Figure MUST know what Floor it belongs to
	- floor(1..1) -> Floor

	moveTo(floor : Floor) : void 
}
