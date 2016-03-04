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
	
	



	private Status check(String smtStr) throws Z3Exception 
   {
	   Context ctx=ContextFactory.getInstance().takeContext();
	   BoolExpr eq = ctx.parseSMTLIB2String(smtStr, null, null, null, null);
      Solver s = ctx.mkSolver();
      s.add(eq);
      Status status=s.check();
      ContextFactory.getInstance().releaseContext(ctx);
      s.dispose();
      if(status==Status.SATISFIABLE){
//       System.out.println("SATISFIABLE + Model: ");
         
//       System.out.println(s.getModel());
         
      }else if (status==Status.UNSATISFIABLE) {
         
//       System.out.println("UNSATISFIABLE");
         
//       System.out.println(s.getUnsatCore());
      }else if (status==Status.UNKNOWN)
         System.out.println("UNKNOWN + Reason: "+ s.getReasonUnknown());
      return status;
      
   }
	@Override
	public boolean checkImplication(SymbolicGraphMorphism morphism) {
	   SymbFormulaToSMTLibTransformer iTransformer= new SymbFormulaToSMTLibTransformer();
	   String smtStr=iTransformer.transformImplication(morphism.getCodom().getFormula(),morphism.getDom().getFormula(),((SymbolicGraphMorphismImpl)morphism).labelNodeMap);
	  
	      Status result;
         try
         {
            result = check(smtStr);
            return result==Status.UNSATISFIABLE;
         } catch (Z3Exception e)
         {
            throw new RuntimeException(e.fillInStackTrace());
            
         }
	     
	   
	  
	   
	}
   @Override
   public boolean hasNonEmptySemantic(SymbolicGraph symbGraph)
   {
      SymbFormulaToSMTLibTransformer iTransformer= new SymbFormulaToSMTLibTransformer();
      String smtStr=iTransformer.transformDisjunctionSat(symbGraph.getFormula());
      Status result;
      try
      {
         result = check(smtStr);
         return result==Status.SATISFIABLE;
      } catch (Z3Exception e)
      {
         throw new RuntimeException(e.fillInStackTrace());
         
      }
   }

   @Override
   public boolean hasEquivalentFormulas(SymbolicGraphMorphism morphism)
   {
      SymbFormulaToSMTLibTransformer iTransformer= new SymbFormulaToSMTLibTransformer();
      String smtStr=iTransformer.transformBiImplication(morphism.getCodom().getFormula(),morphism.getDom().getFormula(),((SymbolicGraphMorphismImpl)morphism).labelNodeMap);
      Status result;
      try
      {
         result = check(smtStr);
         return result==Status.UNSATISFIABLE;
      } catch (Z3Exception e)
      {
         throw new RuntimeException(e.fillInStackTrace());
         
      }
   }
   @Override
   
   public boolean isTrue(SymbolicGraph symbGraph)
   {
      SymbFormulaToSMTLibTransformer iTransformer= new SymbFormulaToSMTLibTransformer();
      String smtStr=iTransformer.transformDisjunctionUnsat(symbGraph.getFormula());
      
      Status result;
      try
      {
         result = check(smtStr);
         return result==Status.UNSATISFIABLE;
      } catch (Z3Exception e)
      {
         throw new RuntimeException(e.fillInStackTrace());
         
      }
   }
}
