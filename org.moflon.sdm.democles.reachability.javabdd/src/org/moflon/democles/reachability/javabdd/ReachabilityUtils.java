package org.moflon.democles.reachability.javabdd;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.compiler.CompilerPattern;

public class ReachabilityUtils
{
   /**
    * Executes the given code with disabled stdout and stderr.
    * @param code
    */
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

   /**
    * Returns the set of operations to analyze for the given pattern
    * 
    * @param pattern
    * @return
    */
   public static List<GeneratorOperation> extractOperations(final CompilerPattern pattern)
   {
      return pattern.getBodies().get(0).getOperations();
   }

   /**
    * Returns whether the given operation is a 'check' operation.
    * 
    * Check operations have a precondition consisting only of {@link Adornment#BOUND}
    * 
    * @param operation the operation to analyze
    * @return whether the precondition adornment consists of {@link Adornment#BOUND} only
    */
   public static boolean isCheckOperation(final OperationRuntime operation)
   {
      return operation.getPrecondition().numberOfBound() == operation.getPrecondition().size();
   }


   private static void muteStdoutAndStderr()
   {
      final PrintStream mutedStream = new PrintStream(new OutputStream() {
         @Override
         public void write(int b) throws IOException
         { // nop
         }
      });
      System.setOut(mutedStream);
      System.setErr(mutedStream);
   }
}
