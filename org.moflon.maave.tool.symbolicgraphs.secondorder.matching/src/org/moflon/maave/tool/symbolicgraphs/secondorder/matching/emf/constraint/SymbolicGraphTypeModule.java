/*
 * Democles, Declarative Model Query Framework for Monitoring Heterogeneous Embedded Systems
 * Copyright (C) 2010-2012  Gergely Varro
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

import org.eclipse.emf.ecore.util.Switch;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.VariableType;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.GraphEdgeVariable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.GraphNodeVariable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.LabelEdgeVariable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.LabelNodeVariable;
import org.moflon.maave.tool.symbolicgraphs.secondorder.constraints.util.ConstraintsSwitch;

public final class SymbolicGraphTypeModule extends org.gervarro.democles.specification.emf.TypeModule {
	
	public SymbolicGraphTypeModule(SymbolicGraphConstraintModule typeModule) {
		super(typeModule);
	}
	
	@Override
	protected final SymbolicGraphsVariableSwitch createVariableTypeSwitch() {
		return new SymbolicGraphsVariableSwitch();
	}

	@Override
	protected final Switch<ConstraintType> createConstraintTypeSwitch() {
		return  null;
	}
	
	private class SymbolicGraphsVariableSwitch extends ConstraintsSwitch<VariableType> {

		@Override
		public VariableType caseLabelNodeVariable(LabelNodeVariable object) {
			// TODO Auto-generated method stub
			return ((SymbolicGraphConstraintModule) typeModule).getVariableType(object);
		}

		@Override
		public VariableType caseGraphEdgeVariable(GraphEdgeVariable object) {
			// TODO Auto-generated method stub
			return ((SymbolicGraphConstraintModule) typeModule).getVariableType(object);
		}

		@Override
		public VariableType caseLabelEdgeVariable(LabelEdgeVariable object) {
			// TODO Auto-generated method stub
			return ((SymbolicGraphConstraintModule) typeModule).getVariableType(object);
		}

		@Override
		public VariableType caseGraphNodeVariable(GraphNodeVariable object) {
			// TODO Auto-generated method stub
			return ((SymbolicGraphConstraintModule) typeModule).getVariableType(object);
		}

		
		
		

		
	}
	
	

}
