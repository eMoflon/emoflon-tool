class Admin extends Figure {

	// Override
	moveTo(floor : Floor) : void { 
		if ! [pushDown] { 
			if [moveFigure] {
					return
			}
		}
		return
	}
}