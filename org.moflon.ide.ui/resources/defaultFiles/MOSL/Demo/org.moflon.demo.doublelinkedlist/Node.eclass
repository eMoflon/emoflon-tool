class Node 
{
	label : EString
	
	// Set up simple references
	- container(0..1) -> List
	- next(1..1) -> Node
	- previous(1..1) -> Node

	// Destroys all references to this node and repairs those that remain
	deleteNode() : void 
	{
		[deleteNode] 
		return
	}
	
	// Sets 'next' reference of current node to 'newNode,' updating other nodes where required
	insertNodeAfter(newNode : Node) : void 
	{
		[insertNodeAfter]
		return	
	}

	// Sets 'previous' reference of current node to 'newNode' and updates any others
	insertNodeBefore(newNode: Node) : void 
	{
		[insertNodeBefore]
		return
	} 
} 
