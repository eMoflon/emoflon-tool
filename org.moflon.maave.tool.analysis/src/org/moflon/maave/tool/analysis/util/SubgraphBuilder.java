package org.moflon.maave.tool.analysis.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;

public class SubgraphBuilder
{
   MorphismsSet morphisms = MatchingFactory.eINSTANCE.createMorphismsSet();

   SymbolicGraph inputGraph;

   public SubgraphBuilder(SymbolicGraph inputGraph)
   {
      this.inputGraph = inputGraph;
   }

   public MorphismsSet getAllSubgraphs()
   {
      BigInteger graphNodesVec = inputGraph.getGraphNodes().size() > 0
            ? new BigInteger(new String(new char[inputGraph.getGraphNodes().size()]).replace("\0", "1"), 2) : new BigInteger("0");
//      System.out.println(inputGraph.getGraphNodes().size());
      BigInteger graphEdgesVec = inputGraph.getGraphEdges().size() > 0
            ? new BigInteger(new String(new char[inputGraph.getGraphEdges().size()]).replace("\0", "1"), 2) : new BigInteger("0");

      graphNodesVec = graphNodesVec.add(BigInteger.ONE);
      graphEdgesVec = graphEdgesVec.add(BigInteger.ONE);

      BigInteger graphsEdgeVecSave = graphEdgesVec;

      List<GraphEdge> graphEdges = inputGraph.getGraphEdges();
      List<LabelNode> labelNodes = inputGraph.getLabelNodes();
      List<GraphNode> graphNodes = inputGraph.getGraphNodes();
      List<LabelEdge> labelEdges = inputGraph.getLabelEdges();

      addToElementToIndexMap(graphEdges);
      //         addToElementToIndexMap(labelNodes);
      addToElementToIndexMap(graphNodes);
      //         addToElementToIndexMap(labelEdges);

      List<SymbolicGraphMorphism> mList = new LinkedList<SymbolicGraphMorphism>();
      while (graphNodesVec.compareTo(BigInteger.ONE) > 0)
      {
         graphNodesVec = graphNodesVec.subtract(BigInteger.ONE);

         graphEdgesVec = graphsEdgeVecSave;
         ge: while (graphEdgesVec.compareTo(BigInteger.ZERO) > 0)
         {
            graphEdgesVec = graphEdgesVec.subtract(BigInteger.ONE);

            List<GraphEdge> subGraphGraphEdges = new LinkedList<GraphEdge>();
            for (int i = 0; i < graphEdges.size(); i++)
            {
               if (graphEdgesVec.testBit(graphEdges.size() - 1 - i))
               {
                  GraphEdge imageEdge = graphEdges.get(i);
                  //check if edge is dangeling
                  if (graphNodesVec.testBit(graphNodes.size() - 1 - elemToIndexMap.get(imageEdge.getSource()))
                        && graphNodesVec.testBit(graphNodes.size() - 1 - elemToIndexMap.get(imageEdge.getTarget())))
                  {
                     //remove wrong bidirectional EReffrences
                     EReference opposite=imageEdge.getType().getEOpposite();
                     if(opposite!=null)
                     {
                        GraphEdge oppositeEdge=graphEdges.stream().filter(e->
                           (e.getType()==opposite)&&(e.getSource()==imageEdge.getTarget())&&
                           (e.getTarget()==imageEdge.getSource())).findAny().get();
                        if(graphEdgesVec.testBit(graphEdges.size()-1-elemToIndexMap.get(oppositeEdge)))
                        {
                           subGraphGraphEdges.add(imageEdge);
                        }
                        else
                        {
                           continue ge;//discard if opposite is missing for bidirectional edge
                        }
                           
                     }
                     else
                     {
                     subGraphGraphEdges.add(imageEdge);
                     }
                  } else
                  {
                     continue ge;//discard morphism if edge is dangling
                  }

               }
            }

            SymbolicGraph subgraph = SymbolicGraphsFactory.eINSTANCE.createSymbolicGraph();
            SymbolicGraphMorphism monoMorphism = SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
            HashMap<GraphNode, GraphNode> reverseGraphNodeMap = new HashMap<GraphNode, GraphNode>(labelNodes.size() + 2, 1);
            for (int i = 0; i < graphNodes.size(); i++)
            {

               if (graphNodesVec.testBit(graphNodes.size() - 1 - i))
               {
                  GraphNode imageNode = graphNodes.get(i);
                  GraphNode preimageNode = EcoreUtil.copy(imageNode);
                  subgraph.getGraphNodes().add(preimageNode);
                  monoMorphism.addMapping(preimageNode, imageNode);
                  reverseGraphNodeMap.put(imageNode, preimageNode);

               }
            }

            //////////////////////
            for (GraphEdge imageEdge : subGraphGraphEdges)
            {
               GraphEdge preImageEdge = EcoreUtil.copy(imageEdge);
               subgraph.add(preImageEdge);
               preImageEdge.setSource(reverseGraphNodeMap.get(imageEdge.getSource()));
               preImageEdge.setTarget(reverseGraphNodeMap.get(imageEdge.getTarget()));
               monoMorphism.addMapping(preImageEdge, imageEdge);
            }

            for (LabelEdge imageEdge : labelEdges)
            {

               if (graphNodesVec.testBit(graphNodes.size() - 1 - elemToIndexMap.get(imageEdge.getSource())))
               {
                  LabelNode imageTargetNode = imageEdge.getTarget();

                  LabelEdge preImageLabelEdge = EcoreUtil.copy(imageEdge);
                  LabelNode preImageLabelNode = EcoreUtil.copy(imageTargetNode);
                  subgraph.getLabelEdges().add(preImageLabelEdge);
                  subgraph.getLabelNodes().add(preImageLabelNode);
                  preImageLabelEdge.setSource(reverseGraphNodeMap.get(imageEdge.getSource()));
                  preImageLabelEdge.setTarget(preImageLabelNode);
                  monoMorphism.addMapping(preImageLabelEdge, imageEdge);
                  monoMorphism.addMapping(preImageLabelNode, imageTargetNode);
               }
            }

            monoMorphism.setCodom(inputGraph);
            monoMorphism.setDom(subgraph);

            String graphNodesString = graphNodes.size() > 0
                  ? String.format("%" + Integer.toString(graphNodes.size()) + "s", graphNodesVec.toString(2)).replace(" ", "0") : "";

            String graphEdgesString = graphEdges.size() > 0
                  ? String.format("%" + Integer.toString(graphEdges.size()) + "s", graphEdgesVec.toString(2)).replace(" ", "0") : "";

            subgraph.setName(graphNodesString + "|" + graphEdgesString + "|");

            mList.add(monoMorphism);
//            System.out.print("SubgraphBuilder processed Subgraphs: ");
//            if (System.currentTimeMillis() - time > 1000)
//            {
//               System.out.print(mList.size()+";");
//               time = System.currentTimeMillis();
//            }
//            System.out.print(mList.size()+";");
//            System.out.println("");
         }
      }
      morphisms.getMorphisms().addAll(mList);
      return morphisms;

   }

   private long time = 0;

   private void addToElementToIndexMap(List<? extends Object> eList)
   {
      for (int i = 0; i < eList.size(); i++)
      {
         elemToIndexMap.put(eList.get(i), i);
      }

   }

   private HashMap<Object, Integer> elemToIndexMap = new HashMap<Object, Integer>();
}
