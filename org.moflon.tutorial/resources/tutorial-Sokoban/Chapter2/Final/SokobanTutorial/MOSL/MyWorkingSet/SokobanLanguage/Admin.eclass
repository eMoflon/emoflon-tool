class Admin extends Figure {

	// Override
	moveTo(floor : Floor) : void { 
		if ! [pushDown] { 
			if ! [pushUp] { 
				if ! [pushLeft] { 
					if ! [pushRight] { 
						[moveFigure]
					}
				}
			}
		}
		return
	}
}