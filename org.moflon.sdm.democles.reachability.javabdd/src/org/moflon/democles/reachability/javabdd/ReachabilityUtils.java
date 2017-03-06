package org.moflon.democles.reachability.javabdd;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.compiler.CompilerPattern;

public class ReachabilityUtils
{

   private static void muteStdoutAndStderr()
   {
      PrintStream mutedStream = new PrintStream(new OutputStream() {
         @Override
         public void write(int b) throws IOException
         { // nop
         }
      });
      System.setOut(mutedStream);
      System.setErr(mutedStream);
   }

   public static void executeWithMutedStderrAndStdout(final Runnable code)
   {
      final PrintStream originalStdout = System.out;
      final PrintStream originalStderr = System.err;
      try
      {
         muteStdoutAndStderr();
         // The following code fragment tends to write to stdout and stderr
         code.run();
      } finally
      {
         System.setOut(originalStdout);
         System.setErr(originalStderr);
      }
   }

   public static List<GeneratorOperation> extractOperations(final CompilerPattern pattern)
   {
      return pattern.getBodies().get(0).getOperations();
   }

}
