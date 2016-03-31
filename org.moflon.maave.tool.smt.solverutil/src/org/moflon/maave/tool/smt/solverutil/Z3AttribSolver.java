package org.moflon.maave.tool.smt.solverutil;



import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.impl.SymbolicGraphMorphismImpl;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

public class Z3AttribSolver implements IAttribSolver {
	
	

   private IFormulaTransformer formulaTransformer;
   

	public Z3AttribSolver()
	{
      super();
	   formulaTransformer=new FormulaToSMTLibTransformer();
   }
   public Status check(String smtStr) throws Z3Exception 
   {
      String smtStr2=new String(smtStr);
//      System.out.println(smtStr2);
      Context ctx= ContextFactory.getInstance().takeContext();
      BoolExpr eq =ContextFactory.parseSMTLibString(ctx, smtStr2);
      Solver s=ctx.mkSolver();
      s.add(eq);
      Status status=s.check();
//      ctx.dispose();
//      s.dispose();
      ContextFactory.getInstance().releaseContext(ctx);
      if (status==Status.UNKNOWN)
      {
         System.out.println("UNKNOWN + Reason: ");
         //System.out.println(smtStr);
      }
      return status;  
      
   }
	@Override
	public boolean checkImplication(SymbolicGraphMorphism morphism) {
	  
	   String smtStr=formulaTransformer.transformImplication(morphism.getCodom().getFormula(),morphism.getDom().getFormula(),((SymbolicGraphMorphismImpl)morphism).labelNodeMap);
	  
	      Status result;
         try
         {
            result = check(smtStr);
            return result==Status.UNSATISFIABLE;
         } catch (Z3Exception e)
         {
            System.out.println(smtStr);
            e.printStackTrace();
            throw new RuntimeException(e.fillInStackTrace());
            
         }
	     
	   
	  
	   
	}
   @Override
   public boolean hasNonEmptySemantic(SymbolicGraph symbGraph)
   {
     
      String smtStr=formulaTransformer.transformDisjunctionSat(symbGraph.getFormula());
      Status result;
      try
      {
         result = check(smtStr);
         return result==Status.SATISFIABLE;
      } catch (Z3Exception e)
      {
         
         
         System.out.println(smtStr);
         e.printStackTrace();
         throw new RuntimeException(e.fillInStackTrace());
         
      }
   }

   @Override
   public boolean hasEquivalentFormulas(SymbolicGraphMorphism morphism)
   {
     
      String smtStr=formulaTransformer.transformBiImplication(morphism.getCodom().getFormula(),morphism.getDom().getFormula(),((SymbolicGraphMorphismImpl)morphism).labelNodeMap);
      Status result;
      try
      {
         result = check(smtStr);
         return result==Status.UNSATISFIABLE;
      } catch (Z3Exception e)
      {
         System.out.println(smtStr);
         e.printStackTrace();
         throw new RuntimeException(e.fillInStackTrace());
         
      }
   }
   @Override
   
   public boolean isTrue(SymbolicGraph symbGraph)
   {
      
      String smtStr=formulaTransformer.transformDisjunctionUnsat(symbGraph.getFormula());
      
      Status result;
      try
      {
         result = check(smtStr);
         return result==Status.UNSATISFIABLE;
      } catch (Z3Exception e)
      {
         System.out.println(smtStr);
         e.printStackTrace();
         throw new RuntimeException(e.fillInStackTrace());
         
      }
   }
   
}
