package org.moflon.maave.tool.symbolicgraphs.Datastructures;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsPackage;

public abstract class EGraphElementSwitch<T>
{
 public T doSwitch( EGraphElement element){
   switch (element.eClass().getClassifierID())
   {
  
   case SymbolicGraphsPackage.GRAPH_NODE:
   {
      GraphNode graphNode = (GraphNode) element;
      return caseGraphNode(graphNode);
      
   }
   case SymbolicGraphsPackage.GRAPH_EDGE:
   {
      GraphEdge graphEdge = (GraphEdge) element;
      return caseGraphEdge(graphEdge);
      
   }
   case SymbolicGraphsPackage.LABEL_EDGE:
   {
      LabelEdge labelEdge = (LabelEdge) element;
      return caseLabelEdge(labelEdge);
      
   }
   case SymbolicGraphsPackage.LABEL_NODE:
   {
      LabelNode labelNode = (LabelNode) element;
      return caseLabelNode(labelNode);
      
   }
   
   default:
      return defaultCase(element);
   }
   
}

public abstract T defaultCase(EGraphElement element);

public abstract T caseLabelNode(LabelNode labelNode);

public abstract T caseLabelEdge(LabelEdge labelEdge);

public abstract T caseGraphEdge(GraphEdge graphEdge);

public abstract T caseGraphNode(GraphNode graphNode);
}
