package org.moflon.maave.tool.smt.solverutil;



import java.util.HashMap;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.impl.SymbolicGraphMorphismImpl;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

public class Z3AttribSolver implements IAttribSolver {
	
	

	private Status check(Context ctx, BoolExpr f) throws Z3Exception
	{
		Solver s = ctx.mkSolver();
		s.add(f);
		Status status=s.check();
//		System.out.println("Z3AttribSolver/ceck/  Model:");
		if(status==Status.SATISFIABLE){
//			System.out.println("SATISFIABLE + Model: ");
			
//			System.out.println(s.getModel());
			
		}else if (status==Status.UNSATISFIABLE) {
		   
//			System.out.println("UNSATISFIABLE");
			
//			System.out.println(s.getUnsatCore());
		}else if (status==Status.UNKNOWN)
		   System.out.println("UNKNOWN + Reason: "+ s.getReasonUnknown());
		return status;
		
	}

	@Override
	public boolean checkImplication(SymbolicGraphMorphism morphism) {
	   SymbFormulaToSMTLibTransformer iTransformer= new SymbFormulaToSMTLibTransformer();
	   String smtStr=iTransformer.transformImplication(morphism.getCodom().getFormula(),morphism.getDom().getFormula(),((SymbolicGraphMorphismImpl)morphism).labelNodeMap);
	   Context ctx; 
	   BoolExpr eq;
	   try {
	      ctx = new Context();
//		System.out.println(GraphAndMorphismPrinter.print(morphism));
//       System.out.println("Z3AttribSolver/checkImplication:");
//       System.out.println(smtStr);
	      eq = ctx.parseSMTLIB2String(smtStr, null, null, null, null);
	      
	      Status result= check(ctx, eq);
	      
	      
	      return result==Status.UNSATISFIABLE;
	   } catch (Z3Exception e) {
	      System.out.println(smtStr);
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	   }
	   
	   return false;
	}
   @Override
   public boolean hasNonEmptySemantic(SymbolicGraph symbGraph)
   {
      SymbFormulaToSMTLibTransformer iTransformer= new SymbFormulaToSMTLibTransformer();
      String smtStr=iTransformer.transformDisjunctionSat(symbGraph.getFormula());
      Context ctx; 
      BoolExpr eq;
      try {
    	 HashMap<String, String> cfg = new HashMap<String, String>();
         ctx = new Context(cfg);
//         System.out.println("Z3AttribSolver/hasNonEmptySemantic:");
//         System.out.println(smtStr);
         eq = ctx.parseSMTLIB2String(smtStr, null, null, null, null);
         Status result= check(ctx, eq);
//         System.out.println(result);
         
         return result==Status.SATISFIABLE;
      } catch (Z3Exception e) {
         // TODO Auto-generated catch block
         System.out.println(smtStr);
         e.printStackTrace();
         
      }
      return false;
   }

   @Override
   public boolean hasEquivalentFormulas(SymbolicGraphMorphism morphism)
   {
      SymbFormulaToSMTLibTransformer iTransformer= new SymbFormulaToSMTLibTransformer();
      String smtStr=iTransformer.transformBiImplication(morphism.getCodom().getFormula(),morphism.getDom().getFormula(),((SymbolicGraphMorphismImpl)morphism).labelNodeMap);
//      System.out.println("Z3AttribSolver/hasEquivalentFormulas:");
//      System.out.println(smtStr);
      Context ctx; 
      BoolExpr eq;
      try {
         ctx = new Context();
         eq = ctx.parseSMTLIB2String(smtStr, null, null, null, null);
         Status result= check(ctx, eq);
         return result==Status.UNSATISFIABLE;
      } catch (Z3Exception e) {
         // TODO Auto-generated catch block
         System.out.println(smtStr);
         e.printStackTrace();
      }
      return false;
   }
   @Override
   
   public boolean isTrue(SymbolicGraph symbGraph)
   {
      SymbFormulaToSMTLibTransformer iTransformer= new SymbFormulaToSMTLibTransformer();
      String smtStr=iTransformer.transformDisjunctionUnsat(symbGraph.getFormula());
      
      Context ctx; 
      BoolExpr eq;
      try {
    	 HashMap<String, String> cfg = new HashMap<String, String>();
         ctx = new Context(cfg);
//         System.out.println("Z3AttribSolver/hasNonEmptySemantic:");
//         System.out.println(smtStr);
         eq = ctx.parseSMTLIB2String(smtStr, null, null, null, null);
         Status result= check(ctx, eq);
//         System.out.println(result);
         
         return result==Status.UNSATISFIABLE;
      } catch (Z3Exception e) {
         // TODO Auto-generated catch block
         System.out.println(smtStr);
         e.printStackTrace();
      }
      return false;
   }
}
