package org.moflon.maave.tool.symbolicgraphs.printing;

import org.apache.commons.lang3.StringUtils;





import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivation;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivationPair;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.Span;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
public class DatastructuresPrinter
{



  

   public static String print(SymbGTRule rule){

      SymbolicGraphMorphism l=rule.getLeft();
      SymbolicGraphMorphism r=rule.getRight();
     

      return GraphAndMorphismPrinter.internalPrintSpan(l, r);

   }
    
   public static String print(DirectDerivation der){
      
      SymbolicGraphMorphism g=der.getSpan().getG();
      SymbolicGraphMorphism h=der.getSpan().getH();
      
      return GraphAndMorphismPrinter.internalPrintSpan(g, h);
   }
   public static String print(DirectDerivationPair derPair){

      Span span1=derPair.getDer1().getSpan();
      Span span2=derPair.getDer2().getSpan();
      if(span1.getG().getCodom()!=span2.getG().getCodom()){
         return "";
      }
      SymbolicGraph r1=span1.getH().getCodom();
      SymbolicGraph r2=span2.getH().getCodom();
      SymbolicGraph k1=span1.getH().getDom();
      SymbolicGraph k2=span2.getH().getDom();
      SymbolicGraph l12=span1.getG().getCodom();

      StringBuilder builder = new StringBuilder();
      builder.append("graphMapping: \n");
      builder.append(formatMapping(r1, k1, l12, k2, r2));
      builder.append("\n");
      builder.append("graphNodeMapping:" + "\n");
      for (GraphNode nl12 : l12.getGraphNodes())
      {
         GraphNode nk1= (GraphNode) k1.getAllElements().stream().filter(x->span1.getG().imageOf((EGraphElement) x)==nl12).findAny().get();
         GraphNode nk2= (GraphNode) k2.getAllElements().stream().filter(x->span2.getG().imageOf((EGraphElement) x)==nl12).findAny().get();
         GraphNode nr1=span1.getH().imageOf(nk1);
         GraphNode nr2=span2.getH().imageOf(nk2);
         builder.append(formatMapping(nr1, nk1, nl12, nk2, nr2));
         builder.append("\n");
      }

      builder.append("graphEdgesMapping:" + "\n");
      for (GraphEdge nl12 : l12.getGraphEdges())
      {
         GraphEdge nk1= (GraphEdge) k1.getAllElements().stream().filter(x->span1.getG().imageOf((EGraphElement) x)==nl12).findAny().get();
         GraphEdge nk2= (GraphEdge) k2.getAllElements().stream().filter(x->span2.getG().imageOf((EGraphElement) x)==nl12).findAny().get();
         GraphEdge nr1=span1.getH().imageOf(nk1);
         GraphEdge nr2=span2.getH().imageOf(nk2);
         builder.append(formatMapping(nr1, nk1, nl12, nk2, nr2));
         builder.append("\n");
      }
      //      for (GraphEdge e : morphism.getDom().getGraphEdges())
      //      {
      //         builder.append(formatMapping(e,morphism.imageOf(e)));
      //         builder.append("\n");
      //      }
      //      builder.append("labelNodeMapping:" + "\n");
      //      for (LabelNode n : morphism.getDom().getLabelNodes())
      //      {
      //         builder.append(formatMapping(n,morphism.imageOf(n)));
      //         builder.append("\n");
      //      }
      //      builder.append("labelEdgesMapping:" + "\n");
      //      for (LabelEdge e : morphism.getDom().getLabelEdges())
      //      {
      //         builder.append(formatMapping(e,morphism.imageOf(e)));
      //         builder.append("\n");
      //      }

      return builder.toString();
   }

   private static String formatMapping(SymbolicGraph r1, SymbolicGraph k1, SymbolicGraph l12, SymbolicGraph k2, SymbolicGraph r2)
   {
      String r1s = r1 == null || r1.getName() == null ? "" : r1.getName();
      String r2s = r2 == null || r2.getName() == null ? "" : r2.getName();
      String k1s = k1 == null || k1.getName() == null ? "" : k1.getName();
      String k2s = k2 == null || k2.getName() == null ? "" : k2.getName();
      String l12s = l12 == null || l12.getName() == null ? "" : l12.getName();
      return formatMapping(r1s, k1s, l12s, k2s, r2s);
   }

   private static String formatMapping(GraphNode r1, GraphNode k1, GraphNode l12, GraphNode k2, GraphNode r2)
   {
      String r1s = GraphAndMorphismPrinter.print(r1);
      String r2s = GraphAndMorphismPrinter.print(r2);
      String k1s =  GraphAndMorphismPrinter.print(k1);
      String k2s = GraphAndMorphismPrinter.print(k2);
      String l12s = GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, k1s, l12s, k2s, r2s);
   }

   private static String formatMapping(LabelNode r1, LabelNode k1, LabelNode l12, LabelNode k2, LabelNode r2)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);
      String r2s =  GraphAndMorphismPrinter.print(r2);
      String k1s =  GraphAndMorphismPrinter.print(k1);
      String k2s =  GraphAndMorphismPrinter.print(k2);
      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, k1s, l12s, k2s, r2s);
   }

   private static String formatMapping(GraphEdge r1, GraphEdge k1, GraphEdge l12, GraphEdge k2, GraphEdge r2)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);
      String r2s =  GraphAndMorphismPrinter.print(r2);
      String k1s =  GraphAndMorphismPrinter.print(k1);
      String k2s =  GraphAndMorphismPrinter.print(k2);
      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, k1s, l12s, k2s, r2s);
   }

   private static String formatMapping(LabelEdge r1, LabelEdge k1, LabelEdge l12, LabelEdge k2, LabelEdge r2)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);
      String r2s =  GraphAndMorphismPrinter.print(r2);
      String k1s =  GraphAndMorphismPrinter.print(k1);
      String k2s =  GraphAndMorphismPrinter.print(k2);
      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, k1s, l12s, k2s, r2s);
   }

   private static String formatMapping(String r1, String k1, String l12, String k2, String r2)
   {
      int l=20;
      return String.format("%-"+l+"s"+" <====== "+"%-"+l+"s"+" ======> "+"%-"+l+"s" +"<====== "+"%-"+l+"s" +" ======> "+"%-"+l+"s", StringUtils.leftPad(r1, l),StringUtils.center(k1, l), StringUtils.center(l12, l), StringUtils.center(k2, l), StringUtils.rightPad(r2, l));
   }

}
