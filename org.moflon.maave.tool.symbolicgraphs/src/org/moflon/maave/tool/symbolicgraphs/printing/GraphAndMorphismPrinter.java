package org.moflon.maave.tool.symbolicgraphs.printing;


import java.util.LinkedList;


import java.util.List;

import org.apache.commons.lang3.StringUtils;


import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Conjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Constant;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Parameter;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

public class GraphAndMorphismPrinter
{
   
   public static String getDisjString(SymbolicGraph  graph){
      Disjunction disjunction=graph.getFormula();
      return disjunction!=null?disjunction.toString():"";
   }
   public static List<String> getConjString(Conjunction  conjunction){
      List<String>conStrList=new LinkedList<>();

     
      for (Predicate predicate : conjunction.getOf()) {
         StringBuilder sb=new StringBuilder();
         if(predicate.getSymbol()=="#T"){
            sb.append("(true  ");
         }else if(predicate.getSymbol()=="#F"){
            sb.append("(false  ");
         }else{
            sb.append(predicate.getSymbol() + "(");
         }
         for (Parameter parameter : predicate.getParameters()) {
            if (parameter instanceof Constant) {
               sb.append(((Constant) parameter).getInterpretation()
                     + ":" + parameter.getType().getName() + ", ");
            } else if (parameter instanceof LabelNode) {
               sb.append(print((LabelNode) parameter) + ", ");
            }
         }
         sb.deleteCharAt(sb.length() - 1);
         sb.deleteCharAt(sb.length() - 1);
         sb.append(")\n");
         conStrList.add(sb.toString());
      }

      
      return conStrList;
   }

   public static String print(Disjunction  dis){
      StringBuilder sb = new StringBuilder();
      for (Conjunction conjunction : dis.getOf()) {
         for (Predicate predicate : conjunction.getOf()) {
            if(predicate.getSymbol()=="#T"){
               sb.append("(true ");
            }else if(predicate.getSymbol()=="#F"){
               sb.append("(false");
            }else{
               sb.append(""+predicate.getSymbol() + "(");
            }
            for (Parameter parameter : predicate.getParameters()) {
               if (parameter instanceof Constant) {
                  sb.append(((Constant) parameter).getInterpretation()
                        + ":" + parameter.getType().getName() + ", ");
               } else if (parameter instanceof LabelNode) {
                  sb.append(((LabelNode) parameter).getLabel() + ":"
                        + parameter.getType().getName() + ", ");
               }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") \n");
         }
         sb.delete(sb.length() - 5, sb.length() - 1);
         sb.append(" OR \n");
      }
      sb.delete(sb.length() - 4, sb.length() - 1);
      return sb.toString();
   }

   public static String printName(SymbolicGraph  e){
      if(e!=null){
         if(e.getName()!=null){
            return e.getName();
         }
      }
      return "";
   }
   public static String print(GraphEdge  e){
      if(e!=null){
         String source=print(e.getSource());
         String target=print(e.getTarget());
         //      return source+" -"+e.getType().getName()+"-> "+target;
         return source+" --> "+target+": "+e.getType().getName();
      }
      return "null";
   }
   public static String print(LabelEdge  e){
      if(e!=null){
         String source=print(e.getSource());
         String target=print(e.getTarget());
         //      return source+" -"+e.getType().getName()+"-> "+target;
         return source+" --> "+target;
      }
      return "null";
   }
   public static String print(GraphNode  n){
      if(n!=null){
         return n.getDebugId()==null?"":n.getDebugId()+":"+n.getType().getName();
      }
      return "null";
   }
   public static String print(LabelNode  n){
      if(n!=null)
      {
         return n.toString();
     
      }
      return "";

   }
   
   
   public static String print (SymbolicGraph graph){
      StringBuilder sb = new StringBuilder();
      sb.append(graph.getName() + "\n");
      sb.append("GraphNodes:\n");
      graph.getGraphNodes().forEach(
            x -> sb.append(print(x)+"\n"));
      sb.append("GraphEdges:\n");
      graph.getGraphEdges().forEach(
            x -> sb.append(print(x)+"\n"));
      sb.append("LabelNodes:\n");
      graph.getLabelNodes().forEach(
            x -> sb.append(print(x)+"\n"));
      sb.append("LabelEdges:\n");
      graph.getLabelEdges().forEach(
            x -> sb.append(print(x)+"\n"));
      sb.append("Formula:" + "\n");
      
      sb.append(GraphAndMorphismPrinter.getDisjString(graph)+"\n");
      return sb.toString();
      
   }
   public static String print(SymbolicGraphMorphism morphism){
      StringBuilder builder = new StringBuilder();
      builder.append("graphMapping: \n");
      builder.append(formatMapping(morphism.getDom(), morphism.getCodom()));
      builder.append("\n");
      builder.append("graphNodeMapping:" + "\n");
      for (GraphNode n : morphism.getDom().getGraphNodes())
      {
         builder.append(formatMapping(n,morphism.imageOf(n)));
         builder.append("\n");
      }
       morphism.getCodom().getGraphNodes().stream().filter(x->!morphism.isInImage(x)).forEach(x->{
          
          builder.append(formatMapping(print(x)));
          builder.append("\n");
   
       });;
      builder.append("graphEdgesMapping:" + "\n");
      for (GraphEdge e : morphism.getDom().getGraphEdges())
      {
         builder.append(formatMapping(e,morphism.imageOf(e)));
         builder.append("\n");
      }
      morphism.getCodom().getGraphEdges().stream().filter(x->!morphism.isInImage(x)).forEach(x->{
         
         builder.append(formatMapping(print(x)));
         builder.append("\n");
  
      });;
      builder.append("labelNodeMapping:" + "\n");
      for (LabelNode n : morphism.getDom().getLabelNodes())
      {
         builder.append(formatMapping(n,morphism.imageOf(n)));
         builder.append("\n");
      }
      morphism.getCodom().getLabelNodes().stream().filter(x->!morphism.isInImage(x)).forEach(x->{
         
         builder.append(formatMapping(print(x)));
         builder.append("\n");
  
      });;
      builder.append("labelEdgesMapping:" + "\n");
      for (LabelEdge e : morphism.getDom().getLabelEdges())
      {
         builder.append(formatMapping(e,morphism.imageOf(e)));
         builder.append("\n");
      }  
      morphism.getCodom().getLabelEdges().stream().filter(x->!morphism.isInImage(x)).forEach(x->{
         
         builder.append(formatMapping(print(x)));
         builder.append("\n");
  
      });;
      builder.append("Forumulas:" + "\n");
      builder.append(printName(morphism.getDom())+"\n");
      builder.append(GraphAndMorphismPrinter.getDisjString(morphism.getDom())+"\n");
      builder.append(printName(morphism.getCodom())+"\n");
      builder.append(GraphAndMorphismPrinter.getDisjString(morphism.getCodom())+"\n");
      return builder.toString();
   }



   private static String formatMapping(SymbolicGraph from, SymbolicGraph to){
      String toString=to==null||to.getName()==null?"":to.getName();
      String fromString=from==null||from.getName()==null?"":from.getName();
      return formatMapping(fromString, toString);
   }
   private static String formatMapping(GraphNode from, GraphNode to){
      String toString=print(to);
      String fromString=print(from);
      return formatMapping(fromString, toString);
   }
   private static String formatMapping(LabelNode from, LabelNode to){
      String toString=print(to);
      String fromString=print(from);
      return formatMapping(fromString, toString);
   }
   private static String formatMapping(GraphEdge from, GraphEdge to){
      String toString=print(to);
      String fromString=print(from);
      return formatMapping(fromString, toString);
   }
   private static String formatMapping(LabelEdge from, LabelEdge to){
      String toString=print(to);
      String fromString=print(from);
      return formatMapping(fromString, toString);
   }
   private static String formatMapping(String from, String to){
      int length=15;
      return String.format("%-20s ----> %20s", StringUtils.leftPad(from, length), StringUtils.rightPad(to, length));
   }
   
   private static String formatMapping(String to){
      int length=15;
      return String.format("%-20s       %20s", StringUtils.leftPad("", length), StringUtils.rightPad(to, length));
   }
   public static String formatMapping(SymbolicGraph r1, SymbolicGraph k1, SymbolicGraph l12)
   {
      String r1s = r1 == null || r1.getName() == null ? "" : r1.getName();
     
      String k1s = k1 == null || k1.getName() == null ? "" : k1.getName();
      
      String l12s = l12 == null || l12.getName() == null ? "" : l12.getName();
      return formatMapping(r1s, k1s, l12s);
   }

   public static String formatMapping(GraphNode r1, GraphNode k1, GraphNode l12)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);
      
      String k1s =  GraphAndMorphismPrinter.print(k1);
      
      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, k1s, l12s);
   }

   public static String formatMapping(LabelNode r1, LabelNode k1, LabelNode l12)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);
      
      String k1s =  GraphAndMorphismPrinter.print(k1);
      
      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, k1s, l12s);
   }

   public static String formatMapping(GraphEdge r1, GraphEdge k1, GraphEdge l12)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);
      
      String k1s =  GraphAndMorphismPrinter.print(k1);
     
      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, k1s, l12s);
   }

   public static String formatMapping(LabelEdge r1, LabelEdge k1, LabelEdge l12)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);
      
      String k1s =  GraphAndMorphismPrinter.print(k1);
  
      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, k1s, l12s);
   }

   private static String formatMapping(String r1, String k1, String l12)
   {
      int l=25;
      return String.format("%-"+l+"s"+" <====== "+"%-"+l+"s"+" ======> "+"%-"+l+"s", StringUtils.leftPad(r1, l),StringUtils.center(k1, l),StringUtils.rightPad(l12, l));
   }
   public static String internalPrintSpan(SymbolicGraphMorphism l, SymbolicGraphMorphism r)
   {
      SymbolicGraph graphR=r.getCodom();
      SymbolicGraph graphK=r.getDom();
      SymbolicGraph graphL=l.getCodom();
      if(graphK==null||graphR==null||graphL==null){
         return "";
      }
      StringBuilder builder = new StringBuilder();
      builder.append("graphMapping: \n");
      builder.append(formatMapping(graphL,graphK,graphR));
      builder.append("\n");
      builder.append("graphNodeMapping:" + "\n");
      graphK.getGraphNodes().stream().forEach(x->{
         builder.append(formatMapping(l.imageOf(x), x, r.imageOf(x)));
         builder.append("\n");
      });
      graphR.getGraphNodes().stream()
      .filter(x->!r.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping((GraphNode)null, (GraphNode)null, x));
         builder.append("\n");
      });
      graphL.getGraphNodes().stream()
      .filter(x->!l.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping(x,(GraphNode)null, (GraphNode)null));
         builder.append("\n");
      });
   
   
      builder.append("graphEdgesMapping:" + "\n");
      graphK.getGraphEdges().stream().forEach(x->{
         builder.append(formatMapping(l.imageOf(x), x, r.imageOf(x)));
         builder.append("\n");
      });
      graphR.getGraphEdges().stream()
      .filter(x->!r.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping((GraphEdge)null, (GraphEdge)null, x));
         builder.append("\n");
      });
      graphL.getGraphEdges().stream()
      .filter(x->!l.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping(x,(GraphEdge)null, (GraphEdge)null));
         builder.append("\n");
      });
      
      
      builder.append("labelNodeMapping:" + "\n");
      graphK.getLabelNodes().stream().forEach(x->{
         builder.append(formatMapping(l.imageOf(x), x, r.imageOf(x)));
         builder.append("\n");
      });
      graphR.getLabelNodes().stream()
      .filter(x->!r.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping((LabelNode)null, (LabelNode)null, x));
         builder.append("\n");
      });
      graphL.getLabelNodes().stream()
      .filter(x->!l.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping(x,(LabelNode)null, (LabelNode)null));
         builder.append("\n");
      });
      
      builder.append("labelEdgesMapping:" + "\n");
      graphK.getLabelEdges().stream().forEach(x->{
         builder.append(formatMapping(l.imageOf(x), x, r.imageOf(x)));
         builder.append("\n");
      });
      graphR.getLabelEdges().stream()
      .filter(x->!r.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping((LabelEdge)null, (LabelEdge)null, x));
         builder.append("\n");
      });
      graphL.getLabelEdges().stream()
      .filter(x->!l.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping(x,(LabelEdge)null, (LabelEdge)null));
         builder.append("\n");
      });
      
      builder.append("Forumulas:" + "\n");
      builder.append( l.getCodom().getName()+ "\n");
      builder.append(getDisjString(graphL)+"\n");
      builder.append(l.getDom().getName() + "\n");
      builder.append(getDisjString(graphK)+"\n");
      builder.append(r.getCodom().getName() + "\n");
      builder.append(getDisjString(graphR)+"\n");
   
   
   
      return builder.toString();
   }
}
