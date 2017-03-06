package org.moflon.democles.reachability.javabdd;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
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
 * This is the original BDD reachability analysis (modulo refactoring) 
 * 
 * Taken from  
 * gervarro.org/democles/trunk/org.moflon.democles.reachability.javabdd  
 * on 2016-09-03
 * 
 * Limitations:
 * The analysis is only application for patterns whose operations have {@link Adornment#FREE} or {@link Adornment#BOUND} as pre-/postconditions.
 * {@link Adornment#NOT_TYPECHECKED} is not supported.
 */
public class LegacyBDDReachabilityAnalyzer implements ReachabilityAnalyzer
{
   private static final Logger logger = Logger.getLogger(LegacyBDDReachabilityAnalyzer.class);

   private static final int INDEX_NOT_FOUND = -1;

   private static final int ADORNMENT_UNDEFINED = -1;

   private BDDFactory bddFactory;

   private BDDPairing fwdPairing;

   private BDDPairing revPairing;

   private BDD[][] bdd;

   private BDDDomain domain1;

   private BDDDomain domain2;

   private BDD reachableStates;

   private boolean reachabilityAnalysisPossible;

   @Override
   public void analyzeReachability(final CompilerPattern pattern, final Adornment inputAdornment)
   {
      final List<GeneratorOperation> operations = ReachabilityUtils.extractOperations(pattern);
      this.reachabilityAnalysisPossible = !hasOperationWithUncheckedAdornment(operations ); 
      if (!reachabilityAnalysisPossible)
      {
         final List<GeneratorOperation> operationsWithUncheckedAdornment = getOperationsWithUncheckedAdornment(operations);
         logger.debug(String.format("Cannot analyze reachability of pattern '%s' because it contains an operation with 'NOT_TYPECHECKED' adornment: '%s'", pattern.getName(), operationsWithUncheckedAdornment));
         return;
      }

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
      
      if (!reachabilityAnalysisPossible)
         return true;

      return isReachable(adornment, reachableStates);
   }
   
   private static <U  extends OperationRuntime> List<U> getOperationsWithUncheckedAdornment(final List<U> operations)
   {
      return operations.stream().filter(operation -> hasUncheckedAdornment(operation.getPrecondition())).collect(Collectors.toList());
   }

   private static <U extends OperationRuntime> boolean hasOperationWithUncheckedAdornment(final List<U> operations)
   {
      return operations.stream()
            .anyMatch(operation -> hasUncheckedAdornment(operation.getPrecondition()) || hasUncheckedAdornment(operation.getPostcondition()));
   }

   private static boolean hasUncheckedAdornment(Adornment adornment)
   {
      for (int i = 0; i < adornment.cardinality(); ++i)
      {
         if (adornment.get(i) == Adornment.NOT_TYPECHECKED)
            return true;
      }
      return false;
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
            // Original code by Fred:
            // Problem: The index i is relative to the precondition adornment of the operation, not to the adornment of the pattern
            //            for (int i = 0; i < precondition.size(); i++)
            //            {
            //               if (Adornment.FREE == precondition.get(i))
            //               {
            //                  // Required to be free
            //                  cube.andWith(bdd[0][i].id());
            //                  cube.andWith(bdd[1][i].not());
            //               } else if (Adornment.BOUND == precondition.get(i))
            //               {
            //                  // Required to be bound
            //                  cube.andWith(bdd[0][i].not());
            //                  cube.andWith(bdd[1][i].not());
            //               } else
            //               {
            //                  // Not defined
            //                  cube.andWith(bdd[0][i].biimp(bdd[1][i]));
            //               }
            //            }
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
