package org.moflon.maave.tool.symbolicgraphs.printing;


import java.util.LinkedList;


import java.util.List;

import org.apache.commons.lang3.StringUtils;


import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Conjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Constant;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Parameter;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;



public class GraphAndMorphismPrinter
{
   public static final String LEFT_AR="<======";
   public static final String RIGHT_AR="======>";
   public static final int BASIC_OFFSET=25;
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
         String source=printUntyped(e.getSource());
         String target=printUntyped(e.getTarget());
         //      return source+" -"+e.getType().getName()+"-> "+target;
         return source+" --"+e.getType().getName()+"--> "+target;
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
   public static String printUntyped(GraphNode  n){
      if(n!=null){
         return n.getDebugId()==null?"":n.getDebugId();
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
   
   public static String formatMapping(SymbolicGraph r1,String leftDelimiter, SymbolicGraph k1,String rightDelimiter, SymbolicGraph l12)
   {
      return  formatMapping( r1, leftDelimiter,  k1, rightDelimiter,  l12,BASIC_OFFSET);
   }
   
   public static String formatMapping(GraphNode r1,String leftDelimiter, GraphNode k1,String rightDelimiter, GraphNode l12)
   {
      return formatMapping( r1, leftDelimiter,  k1, rightDelimiter,  l12,BASIC_OFFSET);
   }
   
   public static String formatMapping(LabelNode r1,String leftDelimiter, LabelNode k1,String rightDelimiter, LabelNode l12)
   {
      return formatMapping( r1, leftDelimiter,  k1, rightDelimiter,  l12,BASIC_OFFSET);
   }
   
   public static String formatMapping(GraphEdge r1,String leftDelimiter, GraphEdge k1,String rightDelimiter, GraphEdge l12)
   {
      return formatMapping( r1, leftDelimiter,  k1, rightDelimiter,  l12,BASIC_OFFSET);
      
   }

   public static String formatMapping(LabelEdge r1,String leftDelimiter, LabelEdge k1, String rightDelimiter,LabelEdge l12)
   {
      return formatMapping( r1, leftDelimiter,  k1,  rightDelimiter, l12,BASIC_OFFSET);
   }
   public static String formatMapping(SymbolicGraph r1,String leftDelimiter, SymbolicGraph k1,String rightDelimiter, SymbolicGraph l12,int offset)
   {
      String r1s = r1 == null || r1.getName() == null ? "" : r1.getName();

      String k1s = k1 == null || k1.getName() == null ? "" : k1.getName();

      String l12s = l12 == null || l12.getName() == null ? "" : l12.getName();
      return formatMapping(r1s, leftDelimiter,k1s, rightDelimiter,l12s,offset);
   }

   public static String formatMapping(GraphNode r1,String leftDelimiter, GraphNode k1,String rightDelimiter, GraphNode l12,int offset)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);

      String k1s =  GraphAndMorphismPrinter.print(k1);

      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, leftDelimiter,k1s, rightDelimiter,l12s,offset);
   }

   public static String formatMapping(LabelNode r1,String leftDelimiter, LabelNode k1,String rightDelimiter, LabelNode l12,int offset)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);

      String k1s =  GraphAndMorphismPrinter.print(k1);

      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, leftDelimiter,k1s, rightDelimiter,l12s,offset);
   }

   public static String formatMapping(GraphEdge r1,String leftDelimiter, GraphEdge k1,String rightDelimiter, GraphEdge l12,int offset)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);

      String k1s =  GraphAndMorphismPrinter.print(k1);

      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s,leftDelimiter, k1s, rightDelimiter,l12s,offset);
   }

   public static String formatMapping(LabelEdge r1,String leftDelimiter, LabelEdge k1, String rightDelimiter,LabelEdge l12,int offset)
   {
      String r1s =  GraphAndMorphismPrinter.print(r1);

      String k1s =  GraphAndMorphismPrinter.print(k1);

      String l12s =  GraphAndMorphismPrinter.print(l12);
      return formatMapping(r1s, leftDelimiter,k1s,rightDelimiter, l12s,offset);
   }

   private static String formatMapping(String r1,String leftDelimiter, String k1,String rightDelimiter, String l12, int l)
   {

      return String.format("%-"+l+"s"+leftDelimiter+"%-"+l+"s"+rightDelimiter+"%-"+l+"s", StringUtils.leftPad(r1, l),StringUtils.center(k1, l),StringUtils.rightPad(l12, l));
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
      builder.append(formatMapping(graphL,LEFT_AR,graphK,RIGHT_AR,graphR));
      builder.append("\n");
      builder.append("graphNodeMapping:" + "\n");
      graphK.getGraphNodes().stream().forEach(x->{
         builder.append(formatMapping(l.imageOf(x),LEFT_AR, x,RIGHT_AR, r.imageOf(x)));
         builder.append("\n");
      });
      graphR.getGraphNodes().stream()
      .filter(x->!r.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping((GraphNode)null,LEFT_AR, (GraphNode)null,RIGHT_AR, x));
         builder.append("\n");
      });
      graphL.getGraphNodes().stream()
      .filter(x->!l.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping(x,LEFT_AR,(GraphNode)null,RIGHT_AR, (GraphNode)null));
         builder.append("\n");
      });


      builder.append("graphEdgesMapping:" + "\n");
      graphK.getGraphEdges().stream().forEach(x->{
         builder.append(formatMapping(l.imageOf(x),LEFT_AR, x,RIGHT_AR, r.imageOf(x)));
         builder.append("\n");
      });
      graphR.getGraphEdges().stream()
      .filter(x->!r.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping((GraphEdge)null,LEFT_AR, (GraphEdge)null,RIGHT_AR, x));
         builder.append("\n");
      });
      graphL.getGraphEdges().stream()
      .filter(x->!l.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping(x,LEFT_AR,(GraphEdge)null,RIGHT_AR, (GraphEdge)null));
         builder.append("\n");
      });


      builder.append("labelNodeMapping:" + "\n");
      graphK.getLabelNodes().stream().forEach(x->{
         builder.append(formatMapping(l.imageOf(x),LEFT_AR, x, RIGHT_AR,r.imageOf(x)));
         builder.append("\n");
      });
      graphR.getLabelNodes().stream()
      .filter(x->!r.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping((LabelNode)null,LEFT_AR,(LabelNode)null, RIGHT_AR, x));
         builder.append("\n");
      });
      graphL.getLabelNodes().stream()
      .filter(x->!l.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping(x,LEFT_AR,(LabelNode)null,RIGHT_AR, (LabelNode)null));
         builder.append("\n");
      });

      builder.append("labelEdgesMapping:" + "\n");
      graphK.getLabelEdges().stream().forEach(x->{
         builder.append(formatMapping(l.imageOf(x),LEFT_AR, x,RIGHT_AR, r.imageOf(x)));
         builder.append("\n");
      });
      graphR.getLabelEdges().stream()
      .filter(x->!r.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping((LabelEdge)null,LEFT_AR, (LabelEdge)null,RIGHT_AR, x));
         builder.append("\n");
      });
      graphL.getLabelEdges().stream()
      .filter(x->!l.isInImage(x))      
      .forEach(x->{
         builder.append(formatMapping(x,LEFT_AR,(LabelEdge)null, RIGHT_AR,(LabelEdge)null));
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
   public static String internalPrintPair(SymbolicGraphMorphism first, SymbolicGraphMorphism second)
   {
      SymbolicGraph graphL2=second.getDom();
      SymbolicGraph graphK=second.getCodom();
      SymbolicGraph graphL1=first.getDom();
      if(graphK==null||graphL2==null||graphL1==null){
         return "";
      }

      int offset=calculateOffset(graphK);



      StringBuilder builder = new StringBuilder();
      builder.append("graphMapping: \n");
      builder.append(formatMapping(graphL1,RIGHT_AR,graphK,LEFT_AR,graphL2,offset));
      builder.append("\n");
      builder.append("graphNodeMapping:" + "\n");
      graphK.getGraphNodes().stream().forEach(x->{
         builder.append(formatMapping((GraphNode)getPreimage(x, first),RIGHT_AR,x ,LEFT_AR, (GraphNode)getPreimage(x, second),offset));
         builder.append("\n");
      });
      builder.append("graphEdgesMapping:" + "\n");
      graphK.getGraphEdges().stream().forEach(x->{
         builder.append(formatMapping((GraphEdge)getPreimage(x, first),RIGHT_AR,x ,LEFT_AR, (GraphEdge)getPreimage(x, second),offset));
         builder.append("\n");
      });
      builder.append("labelNodeMapping:" + "\n");
      graphK.getLabelNodes().stream().forEach(x->{
         builder.append(formatMapping((LabelNode)getPreimage(x, first),RIGHT_AR,x ,LEFT_AR, (LabelNode)getPreimage(x, second),offset));
         builder.append("\n");
      });
      builder.append("labelEdgesMapping:" + "\n");
      graphK.getLabelEdges().stream().forEach(x->{
         builder.append(formatMapping((LabelEdge)getPreimage(x, first),RIGHT_AR,x ,LEFT_AR, (LabelEdge)getPreimage(x, second),offset));
         builder.append("\n");
      });
      return builder.toString();
   }

   private static EGraphElement getPreimage(EGraphElement image, SymbolicGraphMorphism morphisms)
   {
      return (EGraphElement) morphisms.getDom().getAllElements().stream().filter(elem->morphisms.imageOf((EGraphElement) elem)==image).findAny().orElse(null);
   }

   private static int calculateOffset(SymbolicGraph graph)
   {
      int maxOffset=0;
      for (LabelEdge le : graph.getLabelEdges())
      {
         int currentoffset=print(le).length();
         if(currentoffset>maxOffset)
         {
            maxOffset=currentoffset;
         }

      }
      return maxOffset>15?maxOffset+4:15;
   }
}
