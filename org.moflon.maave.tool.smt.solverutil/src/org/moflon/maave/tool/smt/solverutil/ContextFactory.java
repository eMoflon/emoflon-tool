package org.moflon.maave.tool.smt.solverutil;


import java.util.HashMap;
import java.util.LinkedList;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;


public class ContextFactory
{

   private static ContextFactory instance = null;
   private int NUMEROFCONTEXTS=6;
   private LinkedList<Context> contexts=new LinkedList<Context>();

   


   public ContextFactory()
   {
      super();
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


   public static ContextFactory getInstance() {
      if (instance == null) 
      {
         synchronized (ContextFactory.class) {
            if (instance == null) {
               instance = new ContextFactory();
               
            }
         }
      }
      return instance;
   }


  
   public synchronized Context takeContext()
   {


      while (contexts.isEmpty()) 
      {
         try { wait(); }
         catch (InterruptedException e) { } 

      } 
      Context ctx = contexts.remove(0); 
      return ctx;

   }
   public synchronized void releaseContext(Context context)
   {
      
      contexts.add(context);
      notify(); 
   }
   
   
   public static synchronized BoolExpr parseSMTLibString(Context ctx,final String smtStr) throws Z3Exception
   {
     
//      System.out.println("X");
       
         BoolExpr exp;
         exp = ctx.parseSMTLIB2String(smtStr, null, null, null, null);
         return   exp;
      
    
      
   }
}
