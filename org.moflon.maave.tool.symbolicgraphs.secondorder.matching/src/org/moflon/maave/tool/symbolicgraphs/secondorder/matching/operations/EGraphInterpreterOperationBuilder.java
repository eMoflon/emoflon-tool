package org.moflon.maave.tool.symbolicgraphs.secondorder.matching.operations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EReference;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.runtime.OperationBuilder;
import org.gervarro.democles.common.runtime.VariableRuntime;
import org.gervarro.democles.interpreter.InterpretableOperation;
import org.gervarro.democles.runtime.DelegatingAdornedOperation;
import org.gervarro.democles.runtime.NativeOperation;
import org.gervarro.democles.runtime.RemappingOperation;
import org.gervarro.democles.specification.impl.Constraint;
import org.gervarro.democles.specification.impl.Variable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.emf.constraint.SymbolicGraphConstraintModule;

public class EGraphInterpreterOperationBuilder implements	OperationBuilder<RemappingOperation,VariableRuntime>{
	private final Map<EModelElement, DelegatingAdornedOperation> graphNodeTypeCheckOperationMap =
			new HashMap<EModelElement, DelegatingAdornedOperation>();
	private final Map<EModelElement, DelegatingAdornedOperation> labelNodeTypeCheckOperationMap =
			new HashMap<EModelElement, DelegatingAdornedOperation>();
	private final Map<EModelElement, DelegatingAdornedOperation> labelEdgeTypeCheckOperationMap =
			new HashMap<EModelElement, DelegatingAdornedOperation>();
	private final Map<EModelElement, DelegatingAdornedOperation> graphEdgeTypeCheckOperationMap =
			new HashMap<EModelElement, DelegatingAdornedOperation>();
	private final SymbolicGraphConstraintModule module;



	public EGraphInterpreterOperationBuilder(SymbolicGraphConstraintModule module) {
		this.module=module;
		// TODO Auto-generated constructor stub
	}

	@Override
	public RemappingOperation getVariableOperation(Variable variable, VariableRuntime runtimeVariable) {
		DelegatingAdornedOperation adornedOperation=null;
		if (variable.getType() instanceof GraphNodeVariableType) {
			adornedOperation =getAdornedOperationForGraphNodeVariable((GraphNodeVariableType) variable.getType());
		}
		else if (variable.getType() instanceof GraphEdgeVariableType) {
			adornedOperation =getAdornedOperationForGraphEdgeVariable((GraphEdgeVariableType) variable.getType());
		}
		else if (variable.getType() instanceof LabelNodeVariableType) {
			adornedOperation =getAdornedOperationForLabelNodeVariable((LabelNodeVariableType) variable.getType());
		}
		else if (variable.getType() instanceof LabelEdgeVariableType) {
			adornedOperation =getAdornedOperationForLabelEdgeVariable((LabelEdgeVariableType) variable.getType());
		}
		if(adornedOperation!=null){
		RemappingOperation operation = new RemappingOperation();
		operation.setOrigin(variable);
		operation.setInput(adornedOperation);
		List<VariableRuntime> parameters = new LinkedList<VariableRuntime>();
		parameters.add(0, runtimeVariable);
		operation.setParameters(parameters);
		return operation;
		}
		return null;
		
	}
	private DelegatingAdornedOperation getAdornedOperationForLabelEdgeVariable(GraphElementVariableType variableType) {
		EModelElement typeGraphElement=variableType.getTypeGraphElement();
		DelegatingAdornedOperation adornedOperation = labelEdgeTypeCheckOperationMap.get(typeGraphElement);
		if (adornedOperation == null) {
			GraphElementTypeCheckOperation nativeOperation = new LabelEdgeTypeCheckOperation(module, typeGraphElement);
			adornedOperation = new DelegatingAdornedOperation(nativeOperation,
					Adornment.create(Adornment.NOT_TYPECHECKED),
					Adornment.create(Adornment.BOUND));
			nativeOperation.addEventListener(adornedOperation);
			labelEdgeTypeCheckOperationMap.put(typeGraphElement, adornedOperation);
		}
		return adornedOperation;
	}

	private DelegatingAdornedOperation getAdornedOperationForLabelNodeVariable(GraphElementVariableType variableType) {
		EModelElement typeGraphElement=variableType.getTypeGraphElement();
		DelegatingAdornedOperation adornedOperation = labelNodeTypeCheckOperationMap.get(typeGraphElement);
		if (adornedOperation == null) {
			GraphElementTypeCheckOperation nativeOperation = new LabelNodeTypeCheckOperation(module, typeGraphElement);
			adornedOperation = new DelegatingAdornedOperation(nativeOperation,
					Adornment.create(Adornment.NOT_TYPECHECKED),
					Adornment.create(Adornment.BOUND));
			nativeOperation.addEventListener(adornedOperation);
			labelNodeTypeCheckOperationMap.put(typeGraphElement, adornedOperation);
		}
		return adornedOperation;
	}

	private DelegatingAdornedOperation getAdornedOperationForGraphEdgeVariable(GraphElementVariableType variableType) {
		EModelElement typeGraphElement=variableType.getTypeGraphElement();
		DelegatingAdornedOperation adornedOperation = graphEdgeTypeCheckOperationMap.get(typeGraphElement);
		if (adornedOperation == null) {
			GraphElementTypeCheckOperation nativeOperation = new GraphEdgeTypeCheckOperation(module, typeGraphElement);
			adornedOperation = new DelegatingAdornedOperation(nativeOperation,
					Adornment.create(Adornment.NOT_TYPECHECKED),
					Adornment.create(Adornment.BOUND));
			nativeOperation.addEventListener(adornedOperation);
			graphEdgeTypeCheckOperationMap.put(typeGraphElement, adornedOperation);
		}
		return adornedOperation;
	}

	private DelegatingAdornedOperation getAdornedOperationForGraphNodeVariable(GraphElementVariableType variableType) {
		EModelElement typeGraphElement=variableType.getTypeGraphElement();
		DelegatingAdornedOperation adornedOperation = graphNodeTypeCheckOperationMap.get(typeGraphElement);
		if (adornedOperation == null) {
			GraphElementTypeCheckOperation nativeOperation = new GraphNodeTypeCheckOperation(module, typeGraphElement);
			adornedOperation = new DelegatingAdornedOperation(nativeOperation,
					Adornment.create(Adornment.NOT_TYPECHECKED),
					Adornment.create(Adornment.BOUND));
			nativeOperation.addEventListener(adornedOperation);
			graphNodeTypeCheckOperationMap.put(typeGraphElement, adornedOperation);
		}
		return adornedOperation;
	}

	@Override
	public List<RemappingOperation> getConstraintOperations(Constraint constraint, List<VariableRuntime> parameters) {
		// TODO Auto-generated method stub
		return null;
	}


}