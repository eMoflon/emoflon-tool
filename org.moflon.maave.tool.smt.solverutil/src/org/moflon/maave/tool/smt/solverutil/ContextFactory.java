package org.moflon.maave.tool.smt.solverutil;


import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Z3Exception;


public class ContextFactory
{

   private static ContextFactory instance = null;
   private final int NUMEROFCONTEXTS=1;
   private LinkedBlockingQueue<Context> contexts=new LinkedBlockingQueue<Context>(NUMEROFCONTEXTS);
   protected ContextFactory() {
   }
 
  
   public static ContextFactory getInstance() {
      if (instance == null) {
          synchronized (ContextFactory.class) {
            if (instance == null) {
               instance = new ContextFactory();
               instance.init();
            }
         }
      }
      return instance;
   }


   private void init()
   {
      for (int i = 0; i < NUMEROFCONTEXTS;i++)
      {
         try
         {
            Context ctx = new Context();
            contexts.put(ctx);
         } catch (InterruptedException e)
         {
            throw new RuntimeException(e.getMessage());
         } catch (Z3Exception e)
         {
            throw new RuntimeException(e.getMessage());
         }
      }
   }
   public synchronized Context takeContext()
   {
      try
      {
         return contexts.take();
      } catch (InterruptedException e)
      {
         
         throw new RuntimeException(e.getMessage());
         
      }
   }
   public synchronized BoolExpr parseSMTLibString(Context ctx,String smtStr)
   {
      return   ctx.parseSMTLIB2String(smtStr, null, null, null, null);
   }
   public synchronized void releaseContext(Context context)
   {
      try
      {
         context.dispose();
        
         contexts.put(context);
      } catch (InterruptedException e)
      {
         throw new RuntimeException(e.getMessage());
      }
   }
}
