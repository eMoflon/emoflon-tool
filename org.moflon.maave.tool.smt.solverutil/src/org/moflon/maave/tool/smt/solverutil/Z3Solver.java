package org.moflon.maave.tool.smt.solverutil;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Global;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

public class Z3Solver
{

   public static Status check(String smtStr) throws Z3Exception 
   {
      String smtStr2=new String(smtStr);
      Context ctx= new Context();
      System.out.print("X");
      BoolExpr eq =ctx.parseSMTLIB2String(smtStr2, null, null, null, null);
      Solver s=ctx.mkSolver();
      s.add(eq);
      Status status=s.check();
      s.dispose();
      ctx.dispose();
      if (status==Status.UNKNOWN)
      {
         System.out.println("UNKNOWN + Reason: ");
         System.out.println(smtStr);
      }
      return status;  

   }

}
