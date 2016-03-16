package org.moflon.maave.tool.smt.solverutil;


import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;



import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;


public class ContextFactory
{

   private static ContextFactory instance = null;
   private final int NUMEROFCONTEXTS=8;
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
   public Context takeContext()
   {
      try
      {
         return contexts.take();
      } catch (InterruptedException e)
      {
         
         throw new RuntimeException(e.getMessage());
         
      }
   }
   
   public void releaseContext(Context context)
   {
      try
      {
         contexts.put(context);
      } catch (InterruptedException e)
      {
         throw new RuntimeException(e.getMessage());
      }
   }
}
