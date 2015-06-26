source MocaTree
target SokobanLanguage

// You're encouraged to read Part IV: Triple Graph Grammars of the eMoflon handbook for thorough details on TGGs. It will explain the
//	creation of rules and each part of a Graph triple in greater detail than what we can offer here. It will also introduce an
//	integration feature that visualizes the transformation process for you, where you'll be able to view, step-by-step, each rule 
//	being matched and executed.

// These statements declare the correspondence types which will be used to create correspondence Links between elements in the listed 
//	classes to ensure they remain consistent with eachother. 
class NodeToBoard {
	source -> Node
	target -> Board
}

class NodeToFloor {
	source -> Node
	target -> Floor
}

