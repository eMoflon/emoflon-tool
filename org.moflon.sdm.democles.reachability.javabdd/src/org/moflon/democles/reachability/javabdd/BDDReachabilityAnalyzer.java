package org.moflon.democles.reachability.javabdd;

import java.util.List;

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
 * See also {@link LegacyBDDReachabilityAnalyzer} for an implementation of this algorithm.
 * 
 * Short explanations:
 * - Each operation has a pre-condition and a post-condition adornment
 * - Each pattern has a set of symbolic parameters. The size of the {@link Adornment} equals the number of symbolic parameters.
 * 
 * @author Roland Kluge - Reimplementation with support for {@link Adornment#NOT_TYPECHECKED} 
 */
public class BDDReachabilityAnalyzer implements ReachabilityAnalyzer
{
   private static final int INDEX_NOT_FOUND = -1;

   private static final int ADORNMENT_UNDEFINED = -1;

   private BDDFactory bddFactory;

   private BDDPairing fwdPairing;

   private BDDPairing revPairing;

   private BDD[][] bdd;

   private BDDDomain domain1;

   private BDDDomain domain2;

   private BDD reachableStates;

   @Override
   public void analyzeReachability(final CompilerPattern pattern, final Adornment inputAdornment)
   {
      final int cacheSize = 1000;
      final int v = inputAdornment.size();
      final int bddVariableCount = v * 2;
      int numberOfNodes = (int) Math.max((Math.pow(v, 3)) * 20, cacheSize);

      bddFactory = BDDFactory.init("java", numberOfNodes, cacheSize);
      bddFactory.setVarNum(bddVariableCount);
      bddFactory.setCacheRatio(1);
      fwdPairing = bddFactory.makePair();
      revPairing = bddFactory.makePair();
      domain1 = bddFactory.extDomain((long) Math.pow(2, v));
      domain2 = bddFactory.extDomain((long) Math.pow(2, v));
      bdd = new BDD[2][v];
      final int[] newVariableOrder = getVarOrder(v);
      ReachabilityUtils.executeWithMutedStderrAndStdout(() -> {bddFactory.setVarOrder(newVariableOrder);});
      
      for (int i = 0; i < 2; i++)
      {
         for (int j = 0; j < v; j++)
         {
            bdd[i][j] = bddFactory.ithVar(i * v + j);
         }
      }

      for (int j = 0; j < v; j++)
      {
         fwdPairing.set(j, v + j);
         revPairing.set(v + j, j);
      }
      BDD transitionRelation = calculateTransitionRelation(pattern);
      calculateReachableStates(transitionRelation);
      transitionRelation.free();
   }

   @Override
   public boolean isReachable(final Adornment adornment)
   {
      if (this.reachableStates == null)
         throw new IllegalStateException("Reachability analysis has not been executed, yet. Please invoke 'analyzeReachability' prior to this method.");

      return isReachable(adornment, reachableStates);
   }

   private BDD calculateTransitionRelation(final CompilerPattern pattern)
   {
      final BDD transitionRelation = bddFactory.zero(); // represents R_O

      for (final OperationRuntime operation : ReachabilityUtils.extractOperations(pattern))
      {
         if (operation != null) // This was here before: operation != null && (operation.getPrecondition().cardinality() != 0)
         {
            final BDD cube = bddFactory.one(); // Represents R_o

            final List<? extends Variable> symbolicParameters = pattern.getSymbolicParameters();
            for (int i = 0; i < symbolicParameters.size(); ++i)
            {
               int posInOperationParameters = INDEX_NOT_FOUND;
               for (int j = 0; j < operation.getParameters().size(); ++j)
               {
                  final VariableRuntime opVariable = operation.getParameters().get(j);
                  if (opVariable.getIndex() == i)
                  {
                     posInOperationParameters = j;
                     break;
                  }

               }
               final int preconditionAdornment = posInOperationParameters != INDEX_NOT_FOUND ? operation.getPrecondition().get(posInOperationParameters) : ADORNMENT_UNDEFINED;
               switch (preconditionAdornment)
               {
               case Adornment.FREE:
                  cube.andWith(bdd[0][i].id());
                  cube.andWith(bdd[1][i].not());
                  break;
               case Adornment.BOUND:
                  cube.andWith(bdd[0][i].not());
                  cube.andWith(bdd[1][i].not());
               case ADORNMENT_UNDEFINED:
               default:
                  cube.andWith(bdd[0][i].biimp(bdd[1][i]));
               }
            }
            transitionRelation.orWith(cube);
         }
      }
      return transitionRelation;
   }

   private boolean isReachable(final Adornment adornment, final BDD tree)
   {
      // We have arrived at '1', i.e., 'adornment' is reachable.
      if (tree.equals(bddFactory.one()))
      {
         return true;
      }
      if (tree.equals(bddFactory.zero()))
      {
         return false;
      }

      final BDD subtree;
      if (adornment.get(tree.var()) > Adornment.BOUND)
      {
         subtree = tree.high();
      } else
      {
         subtree = tree.low();
      }

      return isReachable(adornment, subtree);
   }

   private void calculateReachableStates(BDD transitionRelation)
   {
      BDD old = domain1.ithVar(0);
      BDD nu = old;
      do
      {
         old = nu;
         BDD z = (transitionRelation.and(old.replace(fwdPairing))).exist(bddFactory.makeSet(domain2.vars()));
         nu = old.or(z);
      } while (!old.equals(nu));
      reachableStates = nu;
   }

   private int[] getVarOrder(final int varNr)
   {
      final int[] varorder = new int[2 * varNr];
      for (int j = 0; j < varNr; j++)
      {
         varorder[2 * j] = j;
         varorder[2 * j + 1] = varNr + j;
      }
      return varorder;
   }
}

