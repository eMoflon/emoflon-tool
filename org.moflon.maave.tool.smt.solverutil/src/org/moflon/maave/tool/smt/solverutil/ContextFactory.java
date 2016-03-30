package org.moflon.maave.tool.smt.solverutil;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;


public class ContextFactory
{

   private static ContextFactory instance = null;
   private final int NUMEROFCONTEXTS=5;
   private List<Context> contexts=new LinkedList<Context>();
   
   


   public ContextFactory()
   {
      super();
      // TODO Auto-generated constructor stub
   }


   public static ContextFactory getInstance() {
      if (instance == null) 
      {
         synchronized (ContextFactory.class) {
            if (instance == null) {
               instance = new ContextFactory();
               instance.init();
            }
         }
      }
      return instance;
   }


   private synchronized void init()
   {
      for (int i = 0; i < NUMEROFCONTEXTS;i++)
      {
       
            Context ctx;
            try
            {
               HashMap<String, String> cfg = new HashMap<String, String>();
               ctx = new Context(cfg);
               
               contexts.add(ctx);
            } catch (Z3Exception e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         
      }
   }
   public synchronized Context takeContext()
   {


      while (contexts.isEmpty()) 
      {
         try { wait(); }
         catch (InterruptedException e) { } 

      } 
      Context ctx = contexts.get(0); 
      return ctx;

   }
   public synchronized void releaseContext(Context context)
   {
      
      contexts.add(context);
      notify(); 
   }
   
   
   public synchronized BoolExpr parseSMTLibString(Context ctx,String smtStr) throws Z3Exception
   {
     
//      System.out.println("X");
         BoolExpr exp;
         exp = ctx.parseSMTLIB2String(smtStr, null, null, null, null);
         return   exp;
      
    
      
   }
}
