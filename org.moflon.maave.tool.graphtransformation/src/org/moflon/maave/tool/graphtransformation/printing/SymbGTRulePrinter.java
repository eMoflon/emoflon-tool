package org.moflon.maave.tool.graphtransformation.printing;

import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.printing.GraphAndMorphismPrinter;


public class SymbGTRulePrinter
{
   public static String print(SymbGTRule rule){
      SymbolicGraphMorphism l=rule.getLeft();
      SymbolicGraphMorphism r=rule.getRight();
     
      
      return GraphAndMorphismPrinter.internalPrintSpan(l, r);
   
      
   }
}
