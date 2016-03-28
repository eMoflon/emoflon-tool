package org.moflon.maave.tool.smt.solverutil;

import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;

public class MyContext extends Context
{
   public MyContext() throws Z3Exception
   {
      super();
      // TODO Auto-generated constructor stub
   }

   public void myfinalize()
   {
      try
      {
         this.finalize();
      } catch (Throwable e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
