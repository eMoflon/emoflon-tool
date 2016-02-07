package org.moflon.maave.tool.symbolicgraphs.secondorder.matching.operations;

import org.eclipse.emf.ecore.EModelElement;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.runtime.InternalDataFrameProvider;
import org.gervarro.democles.runtime.RemappedDataFrame;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsPackage;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.emf.constraint.SymbolicGraphConstraintModule;

public class GraphNodeTypeCheckOperation extends GraphElementTypeCheckOperation {
	
	

	public GraphNodeTypeCheckOperation(SymbolicGraphConstraintModule module,
			EModelElement typeGraphElement) {
		super(module, typeGraphElement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InternalDataFrameProvider getDataFrame(RemappedDataFrame frame,
			Adornment adornment) {
		if (adornment.get(0) == Adornment.NOT_TYPECHECKED) {
			org.eclipse.emf.ecore.EObject object = (org.eclipse.emf.ecore.EObject) frame.getValue(0);
			if(SymbolicGraphsPackage.eINSTANCE.getGraphNode().isInstance(object)){
				GraphNode graphNode=(GraphNode)object;
				if (typeGraphElement.equals(graphNode.getType())) {
					return frame;
				}

			}
		} else {
			throw new RuntimeException("Adornment " + adornment.toString() + " is unknown.");
		}
		return null;

	}

}
