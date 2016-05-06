package org.moflon.maave.tool.smt.solverutil;

public class TimeWriter
{
   public static long timeMorFinder=0;
   public static long timeSolving=0;
   
   public static void addTimeMorFinder(long value)
   {
      timeMorFinder=timeMorFinder+value;
   }
   public static void addTimeSolving(long value)
   {
      timeSolving=timeSolving+value;
   }
   
}
