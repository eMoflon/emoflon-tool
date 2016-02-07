package org.moflon.maave.tool.symbolicgraphs.secondorder.matching.operations;

import org.eclipse.emf.ecore.EModelElement;
import org.gervarro.democles.interpreter.InterpretableOperation;
import org.gervarro.democles.runtime.NativeOperation;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.emf.constraint.SymbolicGraphConstraintModule;

public abstract class GraphElementTypeCheckOperation extends NativeOperation implements InterpretableOperation { 



	protected final SymbolicGraphConstraintModule module;
	protected final EModelElement typeGraphElement;

	public GraphElementTypeCheckOperation(SymbolicGraphConstraintModule module, EModelElement typeGraphElement) {
		this.module=module;
		this.typeGraphElement=typeGraphElement;
	}
}