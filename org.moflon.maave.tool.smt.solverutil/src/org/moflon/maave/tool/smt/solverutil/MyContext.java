package org.moflon.maave.tool.smt.solverutil;

import com.microsoft.z3.Context;

public class MyContext extends Context
{
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
