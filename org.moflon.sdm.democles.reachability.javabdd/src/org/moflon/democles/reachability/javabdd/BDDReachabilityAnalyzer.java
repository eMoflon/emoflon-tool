package org.moflon.democles.reachability.javabdd;

import java.util.List;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.common.runtime.VariableRuntime;
import org.gervarro.democles.compiler.CompilerPattern;
import org.gervarro.democles.specification.Variable;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDDomain;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;

/**
 * This class implements a BDD-based reachability analysis, which serves for identifying reachable input adornments of a {@link CompilerPattern} invocation.
 * 
 * The algorithm is an adapted version of the algorithm described Section 3.5 of the paper "An algorithm for generating model-sensitive search plans for pattern matching on EMF models" {@link https://link.springer.com/article/10.1007%2Fs10270-013-0372-2}.
 * 
 * The original algorithm was designed for two types of adornments ({@link Adornment#BOUND} and {@link Adornment#FREE}).
 * See also {@link BDDReachabilityAnalyzer} for an implementation of this algorithm.
 * 
 * Short explanations:
 * - Each operation has a precondition and a postcondition adornment
 * - Each pattern has a set of symbolic parameters. The size of the {@link Adornment} equals the number of symbolic parameters.
 * 
 * @author Roland Kluge - Reimplementation of {@link LegacyBDDReachabilityAnalyzer} with support for {@link Adornment#NOT_TYPECHECKED} 
 */
public class BDDReachabilityAnalyzer implements ReachabilityAnalyzer
{
   private static final int ADORNMENT_UNDEFINED = -1;

   private static final int VARIABLE_NOT_FOUND = -1;

   private BDDFactory bddFactory;

   private BDDPairing fwdPairing;

   private BDDPairing revPairing;

   private BDD[][] bdd;

   private BDDDomain domain1;

   private BDDDomain domain2;

   private BDD reachableStates;

   private boolean reachabilityAnalysisPossible;

   @Override
   public void analyzeReachability(final CompilerPattern pattern)
   {
      this.reachabilityAnalysisPossible = true;
      //      this.reachabilityAnalysisPossible = !LegacyBDDReachabilityAnalyzer.hasOperationWithUncheckedAdornment(ReachabilityUtils.extractOperations(pattern));
      //      if (!this.reachabilityAnalysisPossible)
      //      {
      //         return;
      //      }
      
      final int cacheSize = 4000;
      final int v = pattern.getSymbolicParameters().size();
      final int numberOfBddVariables = v * 4;
      final int numberOfNodes = (int) Math.max((Math.pow(2 * v, 3)) * 20, cacheSize);

      bddFactory = BDDFactory.init("java", numberOfNodes, cacheSize);
      bddFactory.setVarNum(numberOfBddVariables);
      bddFactory.setCacheRatio(1);
      fwdPairing = bddFactory.makePair();
      revPairing = bddFactory.makePair();
      domain1 = bddFactory.extDomain((long) Math.pow(4, v));
      domain2 = bddFactory.extDomain((long) Math.pow(4, v ));
      bdd = new BDD[2][2 * v]; // v_p => v_p1, v_p2 | v'_p => v'_p1, v'_p2 reside next to each other

      ReachabilityUtils.executeWithMutedStderrAndStdout(() -> bddFactory.setVarOrder(getVarOrder(v)));

      // i = 0: pre-variables
      // i = 1: post-variables
      for (int i = 0; i < 2; i++)
      {
         for (int j = 0; j < 2 * v; j += 2)
         {
            bdd[i][j] = bddFactory.ithVar(i * 2 * v + j);
            bdd[i][j + 1] = bddFactory.ithVar(i * 2 * v + j + 1);
         }
      }

      for (int j = 0; j < 2 * v; ++j)
      {
         fwdPairing.set(j, 2 * v + j);
         revPairing.set(2 * v + j, j);
      }
      final BDD transitionRelation = calculateTransitionRelation(pattern);
      calculateReachableStates(transitionRelation);
      transitionRelation.free();
   }

   @Override
   public boolean isReachable(Adornment adornment)
   {
      if (!this.reachabilityAnalysisPossible)
         return true;
      
      if (reachableStates == null)
         throw new IllegalStateException("Reachability analysis has not been executed, yet. Please invoke 'analyzeReachability' prior to this method.");

      return isReachable(adornment, reachableStates);
   }

   private BDD calculateTransitionRelation(final CompilerPattern pattern)
   {
      final List<GeneratorOperation> operations = ReachabilityUtils.extractOperations(pattern);
      final BDD transitionRelation = bddFactory.zero(); // represents R_O

      for (final OperationRuntime operation : operations)
      {
         if (operation != null && !ReachabilityUtils.isCheckOperation(operation))
         {
            final BDD cube = bddFactory.one(); // Represents R_o
            final List<? extends Variable> symbolicParameters = pattern.getSymbolicParameters();
            for (int i = 0; i < symbolicParameters.size(); ++i)
            {
               final int posInOperationParameters = findVariableWithIndex(operation, i);
               
               final int preconditionAdornment = posInOperationParameters != VARIABLE_NOT_FOUND //
                     ? operation.getPrecondition().get(posInOperationParameters) //
                     : ADORNMENT_UNDEFINED;
              

               // Handle precondition
               switch (preconditionAdornment)
               {
               case Adornment.FREE:
                  cube.andWith(bdd[0][2 * i].id());
                  cube.andWith(bdd[0][2 * i + 1].id());
                  break;
               case Adornment.BOUND:
                  cube.andWith(bdd[0][2 * i].not());
                  cube.andWith(bdd[0][2 * i + 1].not());
                  break;
               case Adornment.NOT_TYPECHECKED:
                  cube.andWith(bdd[0][2 * i].not());
                  cube.andWith(bdd[0][2 * i + 1].id());
                  break;
               case ADORNMENT_UNDEFINED:
                  cube.andWith(bdd[0][2 * i].biimp(bdd[1][2*i]));
                  cube.andWith(bdd[0][2 * i + 1].biimp(bdd[1][2*i + 1]));
                  break;
               }

               final int postconditionAdornment = posInOperationParameters != VARIABLE_NOT_FOUND //
                     ? operation.getPostcondition().get(posInOperationParameters) //
                     : ADORNMENT_UNDEFINED;
               // Handle postcondition
               switch (postconditionAdornment)
               {
               case Adornment.FREE:
                  cube.andWith(bdd[1][2 * i].id());
                  cube.andWith(bdd[1][2 * i + 1].id());
                  break;
               case Adornment.BOUND:
                  cube.andWith(bdd[1][2 * i].not());
                  cube.andWith(bdd[1][2 * i + 1].not());
                  break;
               case Adornment.NOT_TYPECHECKED:
                  cube.andWith(bdd[1][2 * i].not());
                  cube.andWith(bdd[1][2 * i + 1].id());
                  break;
               case ADORNMENT_UNDEFINED:
                  // nop because previous switch-case has cared about this
                  break;
               default:
               }
            }
            transitionRelation.orWith(cube);
         }
      }
      return transitionRelation;
   }

   /**
    * Returns the first variable in the parameters of this operation for which {@link VariableRuntime#getIndex()} equals 'needle'
    * 
    * @param operation
    * @param needle
    * @return the position in the 
    */
   private static int findVariableWithIndex(final OperationRuntime operation, int needle)
   {
      int posInOperationParameters = VARIABLE_NOT_FOUND;
      for (int j = 0; j < operation.getParameters().size(); ++j)
      {
         final VariableRuntime opVariable = operation.getParameters().get(j);
         if (opVariable.getIndex() == needle)
         {
            posInOperationParameters = j;
            break;
         }
      }
      return posInOperationParameters;
   }

   private boolean isReachable(final Adornment adornment, final BDD r)
   {
      // We have arrived at '1', i.e., 'adornment' is reachable.
      if (r.equals(bddFactory.one()))
      {
         return true;
      }
      if (r.equals(bddFactory.zero()))
      {
         return false;
      }

      final BDD subtree;
      final int positionInAdornment = r.var() / 2;
      switch(adornment.get(positionInAdornment))
      {
      case Adornment.FREE:
         subtree = r.high().high();
         break;
      case Adornment.BOUND:
         subtree = r.low().low();
         break;
      case Adornment.NOT_TYPECHECKED:
         subtree = r.low().high();
         break;
      default:
         throw new IllegalArgumentException(String.format("Unsupported adornment specification i=%d in %s", positionInAdornment, adornment));
      }

      return isReachable(adornment, subtree);
   }

   private void calculateReachableStates(final BDD transitionRelation)
   {
      BDD old = domain1.ithVar(0); // Target adornment: everything is 0 -> 
      BDD nu = old;
      do
      {
         old = nu;
         BDD z = (transitionRelation.and(old.replace(fwdPairing))).exist(bddFactory.makeSet(domain2.vars()));
         nu = old.or(z);
      } while (!old.equals(nu));
      reachableStates = nu;
   }

   private int[] getVarOrder(int adornmentSize)
   {
      //TODO@rkluge: was new int[2*adornmentSize]
      int[] varorder = new int[4 * adornmentSize];
      for (int j = 0; j < adornmentSize; ++j)
      {
         varorder[4 * j] = 2 * j; // pre-variable 1
         varorder[4 * j + 1] = 2 * j + 1; // pre-variable 2
         varorder[4 * j + 2] = 2 * adornmentSize + 2 * j; //post-variable 1
         varorder[4 * j + 3] = 2 * adornmentSize + 2 * j + 1; // post-variable 2
      }
      return varorder;
   }
}
