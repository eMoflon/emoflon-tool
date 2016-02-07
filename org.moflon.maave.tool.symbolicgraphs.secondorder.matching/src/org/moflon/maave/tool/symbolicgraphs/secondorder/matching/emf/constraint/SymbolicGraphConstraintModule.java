/*
 * Democles, Declarative Model Query Framework for Monitoring Heterogeneous Embedded Systems
 * Copyright (C) 2010  Gergely Varro
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Contributors:
 * 		Gergely Varro <gervarro@cs.bme.hu> - initial API and implementation and/or initial documentation
 */
package org.moflon.maave.tool.symbolicgraphs.secondorder.matching.emf.constraint;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.democles.common.TypeModule;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.GraphEdgeVariable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.GraphNodeVariable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.LabelEdgeVariable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.LabelNodeVariable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.operations.GraphEdgeVariableType;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.operations.GraphElementVariableType;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.operations.GraphNodeVariableType;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.operations.LabelEdgeVariableType;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.operations.LabelNodeVariableType;

public class SymbolicGraphConstraintModule implements TypeModule {
	private final EPackage.Registry registry;
//	private final Map<EModelElement, EMFConstraint<?>> constraintTypeMapping =
//		new HashMap<EModelElement, EMFConstraint<?>>();
	private final Map<EClassifier, GraphNodeVariableType> graphNodeVariableTypeMapping =
		new HashMap<EClassifier, GraphNodeVariableType>();
	private final Map<EReference, GraphEdgeVariableType> graphEdgeVariableTypeMapping =
			new HashMap<EReference, GraphEdgeVariableType>();
	private Map<EAttribute, LabelEdgeVariableType> labelEdgeVariableTypeMapping=new HashMap<EAttribute, LabelEdgeVariableType>();
	private Map<EDataType, LabelNodeVariableType> labelNodeVariableTypeMapping=new HashMap<EDataType, LabelNodeVariableType>();
	
	public SymbolicGraphConstraintModule(Registry registry) {
		this.registry = registry;
	}
	
	public SymbolicGraphConstraintModule(ResourceSet resourceSet) {
		this(resourceSet.getPackageRegistry());
	}
	
	public boolean isAdapterForType(Object type) {
		return type instanceof Class<?> && getClass().isAssignableFrom((Class<?>) type);
	}
	
	public final String getName() {
		return "SymbolicGraphsConstraintTypeModule";
	}
	
//	public final EMFConstraint<?> getConstraintType(EModelElement eModelElement) {
////		EMFConstraint<?> constraintType = constraintTypeMapping.get(eModelElement);
////		if (constraintType == null) {
////			if (EcorePackage.eINSTANCE.getEAttribute().isInstance(eModelElement)) {
////				EAttribute eAttribute = (EAttribute) eModelElement;
////				constraintType = new Attribute(this, eAttribute);
////			} else if (EcorePackage.eINSTANCE.getEReference().isInstance(eModelElement)) {
////				EReference eReference = (EReference) eModelElement;
////				constraintType = new Reference(this, eReference);
////			} else if (EcorePackage.eINSTANCE.getEOperation().isInstance(eModelElement)) {
////				EOperation eReference = (EOperation) eModelElement;
////				constraintType = new Operation(this, eReference);
////			} else {
////				return null;
////			}
////			constraintTypeMapping.put(eModelElement, constraintType);
////		}
////		return constraintType;
//		return null;
//	}

	public final GraphElementVariableType getVariableType(GraphNodeVariable gn) {
		EClass typeGraphElement = gn.getType();
		GraphNodeVariableType graphNodeVariableType = graphNodeVariableTypeMapping.get(typeGraphElement);
		if (graphNodeVariableType == null) {
			graphNodeVariableType = new GraphNodeVariableType(typeGraphElement, this);
			graphNodeVariableTypeMapping.put(typeGraphElement, graphNodeVariableType);
		}
		return graphNodeVariableType;
	}
	public final GraphElementVariableType getVariableType(GraphEdgeVariable ge) {
		EReference typeGraphElement = ge.getType();
		GraphEdgeVariableType graphEdgeVariableType = graphEdgeVariableTypeMapping.get(typeGraphElement);
		if (graphEdgeVariableType == null) {
			graphEdgeVariableType = new GraphEdgeVariableType(typeGraphElement, this);
			graphEdgeVariableTypeMapping.put(typeGraphElement, graphEdgeVariableType);
		}
		return graphEdgeVariableType;
	}
	public final GraphElementVariableType getVariableType(LabelEdgeVariable le) {
		EAttribute typeGraphElement = le.getType();
		LabelEdgeVariableType labelEdgeVariableType = labelEdgeVariableTypeMapping.get(typeGraphElement);
		if (labelEdgeVariableType == null) {
			labelEdgeVariableType = new LabelEdgeVariableType(typeGraphElement, this);
			labelEdgeVariableTypeMapping.put(typeGraphElement, labelEdgeVariableType);
		}
		return labelEdgeVariableType;
	}
	public final GraphElementVariableType getVariableType(LabelNodeVariable ln) {
		EDataType typeGraphElement = ln.getType();
		LabelNodeVariableType labelNodeVariableType = labelNodeVariableTypeMapping.get(typeGraphElement);
		if (labelNodeVariableType == null) {
			labelNodeVariableType = new LabelNodeVariableType(typeGraphElement, this);
			labelNodeVariableTypeMapping.put(typeGraphElement, labelNodeVariableType);
		}
		return labelNodeVariableType;
	}
	
	public final Registry getRegistry() {
		return registry;
	}
}
