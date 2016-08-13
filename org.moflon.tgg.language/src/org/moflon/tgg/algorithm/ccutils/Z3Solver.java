package org.moflon.tgg.algorithm.ccutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.moflon.core.utilities.LogUtils;

public class Z3Solver extends AbstractSATSolver
{

   private static final Logger logger = Logger.getLogger(Z3Solver.class);

   int[] result;

   @Override
   public int[] solve(DimacFormat problem)
   {

      String[] params = new String[2];

      params[0] = "solvers\\z3-4.3.2-x64-win\\bin\\z3";
      params[1] = problem.getClausesAsFilePath();

      try
      {
         final Process p = Runtime.getRuntime().exec(params);
         Thread thread = new Thread() {
            public void run()
            {
               String line;
               BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

               try
               {
                  while ((line = input.readLine()) != null)
                  {
                     if (line.equals("sat"))
                     {
                        System.out.println("Satisfiable");
                     } else
                     {
                        result = generateResultArrayFromString(line);
                     }
                  }
                  input.close();
               } catch (IOException e)
               {
                  LogUtils.error(logger, e);
               }
            };
         };
         thread.start();
         int failedStatus = p.waitFor();
         thread.join();
         if (failedStatus != 0)
         {
            System.out.println("Process failed with status: " + failedStatus);
         }
      } catch (IOException e)
      {
         System.out.println(" procccess not read" + e);
      } catch (InterruptedException e)
      {
         System.out.println(" procccess interrupted" + e);
      }

      return result;
   }

   private int[] generateResultArrayFromString(String input)
   {

      String[] strings = input.split(" ");
      int[] result = new int[strings.length];

      for (int i = 0; i < strings.length; i++)
      {
         result[i] = Integer.parseInt(strings[i]);
      }
      return result;
   }

}
