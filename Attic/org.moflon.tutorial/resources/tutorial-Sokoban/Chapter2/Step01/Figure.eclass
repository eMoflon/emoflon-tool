abstract class Figure {
	
	// A Figure MUST know what Floor it belongs to
	-floor(1..1)-> Floor

	// Method signatures can only set the control flow; 
	//	No actions are implemented here
	moveTo(floor : Floor) : void { 
		if [moveFigure]
		return
	}
}
