package org.moflon.maave.tool.smt.solverutil;



import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.impl.SymbolicGraphMorphismImpl;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

public class Z3AttribSolver implements IAttribSolver {
	
	

   private IFormulaTransformer formulaTransformer;
   

	public Z3AttribSolver()
   {
      formulaTransformer=new FormulaToSMTLibTransformer();
   }
//   public Status check(String smtStr) throws Z3Exception 
//   {
//      String smtStr2=new String("(get-info :version)\n(set-option :timeout 80000)\n(set-option :verbose 0)\n(declare-datatypes () ((EX_STATE INIT REG_OPEN REG_CLOSED PERFORM CLOSED RECORDS)))\n(declare-const enrollment_cp!22 Int)\n(declare-const moduleOffer_cp!21 Int)\n(declare-const enrollment_cp_prime!14 Int)\n(declare-const rec_grade_prime!26 Int)\n(declare-const this_state!3 EX_STATE)\n(declare-const student_id!12 Int)\n(declare-const moduleOffer_cp!6 Int)\n(declare-const entry_studentId!24 Int)\n(declare-const entry_studentId!10 Int)\n(declare-const entry_grade!25 Int)\n(declare-const entry_grade!11 Int)\n(declare-const rec_grade_prime!13 Int)\n(declare-const this_state!18 EX_STATE)\n(define-fun geNAT ((x!0 Int)(x!1 Int)) Bool(> x!0 x!1))\n(define-fun addNat ((x!0 Int)(x!1 Int)(x!2 Int)) Bool(= x!0 (+ x!1 x!2)))\n(define-fun isLeqNAT ((x!0 Int)(x!1 Int)(x!2 Bool)) Bool(= (<= x!0 x!1) x!2))\n(define-fun iteNAT ((x!0 Bool)(x!1 Int)(x!2 Int)(x!3 Int))Bool(and  (=> x!0 (= x!1 x!2))  (=> (not x!0) (= x!1 x!3))))\n(define-fun eqNAT ((x!0 Int)(x!1 Int)) Bool(= x!0 x!1))\n(define-fun leqNAT ((x!0 Int)(x!1 Int)) Bool(<= x!0 x!1))\n(define-fun eqEX_STATE((x!1 EX_STATE)(x!2 EX_STATE)) Bool (=  x!1 x!2) )\n\n(assert  \n (not \n        (= \n          (exists((rec_grade_prime!!0 Int) (enrollment_cp_prime!!1 Int) (passed!!2 Bool) (newCP!!3 Int) (rec_grade_prime!!4 Int) (enrollment_cp_prime!!5 Int) (passed!!6 Bool) (newCP!!7 Int) (enrollment_cp_prime!!8 Int) (passed!!9 Bool) (newCP!!10 Int) (passed!!11 Bool) (newCP!!12 Int) ) (and (eqEX_STATE this_state!18 RECORDS) (eqNAT student_id!12 entry_studentId!24) (leqNAT entry_grade!25 5) (geNAT entry_grade!25 0) (eqNAT rec_grade_prime!!4 entry_grade!25) (isLeqNAT entry_grade!25 4 passed!!6) (iteNAT passed!!6 enrollment_cp_prime!!5 newCP!!7 enrollment_cp!22) (addNat newCP!!7 enrollment_cp!22 moduleOffer_cp!21) (eqEX_STATE this_state!3 RECORDS) (eqNAT student_id!12 entry_studentId!10) (leqNAT entry_grade!11 5) (geNAT entry_grade!11 0) (eqNAT rec_grade_prime!!0 entry_grade!11) (isLeqNAT entry_grade!11 4 passed!!2) (iteNAT passed!!2 enrollment_cp_prime!!1 newCP!!3 enrollment_cp!22) (addNat newCP!!3 enrollment_cp!22 moduleOffer_cp!6) (eqEX_STATE this_state!18 RECORDS) (eqNAT student_id!12 entry_studentId!24) (leqNAT entry_grade!25 5) (geNAT entry_grade!25 0) (eqNAT rec_grade_prime!26 entry_grade!25) (isLeqNAT entry_grade!25 4 passed!!11) (iteNAT passed!!11 enrollment_cp_prime!!8 newCP!!12 enrollment_cp!22) (addNat newCP!!12 enrollment_cp!22 moduleOffer_cp!21) (eqEX_STATE this_state!3 RECORDS) (eqNAT student_id!12 entry_studentId!10) (leqNAT entry_grade!11 5) (geNAT entry_grade!11 0) (eqNAT rec_grade_prime!13 entry_grade!11) (isLeqNAT entry_grade!11 4 passed!!9) (iteNAT passed!!9 enrollment_cp_prime!14 newCP!!10 enrollment_cp_prime!!8) (addNat newCP!!10 enrollment_cp_prime!!8 moduleOffer_cp!6) )  )             (exists((rec_grade_prime!!0 Int) (enrollment_cp_prime!!1 Int) (passed!!2 Bool) (newCP!!3 Int) (rec_grade_prime!!4 Int) (enrollment_cp_prime!!5 Int) (passed!!6 Bool) (newCP!!7 Int) (enrollment_cp_prime!!8 Int) (passed!!9 Bool) (newCP!!10 Int) (passed!!11 Bool) (newCP!!12 Int) ) (and (eqEX_STATE this_state!3 RECORDS) (eqNAT student_id!12 entry_studentId!10) (leqNAT entry_grade!11 5) (geNAT entry_grade!11 0) (eqNAT rec_grade_prime!!4 entry_grade!11) (isLeqNAT entry_grade!11 4 passed!!6) (iteNAT passed!!6 enrollment_cp_prime!!5 newCP!!7 enrollment_cp!22) (addNat newCP!!7 enrollment_cp!22 moduleOffer_cp!6) (eqEX_STATE this_state!18 RECORDS) (eqNAT student_id!12 entry_studentId!24) (leqNAT entry_grade!25 5) (geNAT entry_grade!25 0) (eqNAT rec_grade_prime!!0 entry_grade!25) (isLeqNAT entry_grade!25 4 passed!!2) (iteNAT passed!!2 enrollment_cp_prime!!1 newCP!!3 enrollment_cp!22) (addNat newCP!!3 enrollment_cp!22 moduleOffer_cp!21) (eqEX_STATE this_state!18 RECORDS) (eqNAT student_id!12 entry_studentId!24) (leqNAT entry_grade!25 5) (geNAT entry_grade!25 0) (eqNAT rec_grade_prime!26 entry_grade!25) (isLeqNAT entry_grade!25 4 passed!!11) (iteNAT passed!!11 enrollment_cp_prime!!8 newCP!!12 enrollment_cp!22) (addNat newCP!!12 enrollment_cp!22 moduleOffer_cp!21) (eqEX_STATE this_state!3 RECORDS) (eqNAT student_id!12 entry_studentId!10) (leqNAT entry_grade!11 5) (geNAT entry_grade!11 0) (eqNAT rec_grade_prime!13 entry_grade!11) (isLeqNAT entry_grade!11 4 passed!!9) (iteNAT passed!!9 enrollment_cp_prime!14 newCP!!10 enrollment_cp_prime!!8) (addNat newCP!!10 enrollment_cp_prime!!8 moduleOffer_cp!6) )  )          )\n   )\n)\n(check-sat-using smt)\n(get-info :reason-unknown)");
//      Context ctx= new Context();
//      BoolExpr eq =ctx.parseSMTLIB2String(smtStr, null, null, null, null);
//      Solver s=ctx.mkSolver();
//      s.add(eq);
//      Status status=s.check();
//      s.dispose();
//      ctx.dispose();
//      System.out.print("X");
//      return status;  
//      }else if (status==Status.UNSATISFIABLE) {
//         
////       System.out.println("UNSATISFIABLE");
//         
////       System.out.println(s.getUnsatCore());
//      }else if (status==Status.UNKNOWN)
//      {
//         System.out.println("UNKNOWN + Reason: ");
//         System.out.println(smtStr);
//      }
//      return status;
//      
//   }
	@Override
	public boolean checkImplication(SymbolicGraphMorphism morphism) {
	  
	   String smtStr=formulaTransformer.transformImplication(morphism.getCodom().getFormula(),morphism.getDom().getFormula(),((SymbolicGraphMorphismImpl)morphism).labelNodeMap);
	  
	      Status result;
         try
         {
            result = Z3Solver.check(smtStr);
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
         result = Z3Solver.check(smtStr);
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
         result = Z3Solver.check(smtStr);
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
         result = Z3Solver.check(smtStr);
         return result==Status.UNSATISFIABLE;
      } catch (Z3Exception e)
      {
         System.out.println(smtStr);
         e.printStackTrace();
         throw new RuntimeException(e.fillInStackTrace());
         
      }
   }
   
}
