
package org.moflon.maave.tool.smt.solverutil; 

import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;


public class PrettyPrinter {


	
	public static void printSpan(SymbGTRule rule){
		SymbolicGraphMorphism left=rule.getLeft();
		SymbolicGraphMorphism right=rule.getRight();
		SymbolicGraph K=rule.getLeft().getDom();
		
		for (GraphNode gn : K.getGraphNodes()) {
			System.out.println(left.imageOf(gn).getDebugId()+" <------- "+gn.getDebugId()+" ------> "+right.imageOf(gn).getDebugId());
		}
		for (GraphEdge ge : K.getGraphEdges()) {
			System.out.println(left.imageOf(ge).getDebugId()+" <------- "+ge.getDebugId()+" ------> "+right.imageOf(ge).getDebugId());
		}
		for (LabelNode ln: K.getLabelNodes()) {
			System.out.println(left.imageOf(ln).getLabel()+" <------- "+ln.getLabel()+" ------> "+right.imageOf(ln).getLabel());
		}
		for (LabelEdge le: K.getLabelEdges()) {
			System.out.println(left.imageOf(le).getDebugId()+" <------- "+le.getDebugId()+" ------> "+right.imageOf(le).getDebugId());
		}
	}

	public static void printMorphism(SymbolicGraphMorphism ix) {
		SymbolicGraphMorphism right=ix;
		SymbolicGraph K=right.getDom();
		
		for (GraphNode gn : K.getGraphNodes()) {
			System.out.println(gn.getDebugId()+" ------> "+right.imageOf(gn).getDebugId());
		}
		for (GraphEdge ge : K.getGraphEdges()) {
			System.out.println(ge.getDebugId()+" ------> "+right.imageOf(ge).getDebugId());
		}
		for (LabelNode ln: K.getLabelNodes()) {
			System.out.println(ln.getLabel()+" ------> "+ right.imageOf(ln).getLabel());
		}
		for (LabelEdge le: K.getLabelEdges()) {
			System.out.println(le.getDebugId()+" ------> "+right.imageOf(le).getDebugId());
		}
		
	}
	
}
