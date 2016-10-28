package org.moflon.democles.reachability.javabdd;

import java.util.List;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.plan.ReachabilityAnalyzer;
import org.gervarro.democles.plan.WeightedOperation;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDDomain;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;

/**
 * This class implements a BDD-based reachability analysis, which serves for identifying reachable adornments of a pattern.
 * 
 * The algorithm is an adapted version of the algorithm described Sec. 3.5 of the paper "An algorithm for generating model-sensitive search plans for pattern matching on EMF models" {@link https://link.springer.com/article/10.1007%2Fs10270-013-0372-2}.
 * 
 * The original algorithm was designed for two types of adornments ({@link Adornment#BOUND} and {@link Adornment#FREE}).
 * 
 * Short explanations:
 * - Each operation has a pre-condition and a post-condition adornment
 * - Each pattern has a set of symbolic parameters. The size of the {@link Adornment} equals the number of symbolic parameters.
 * 
 * @param <U> the type of {@link WeightedOperation}s to analyze
 * @param <W> the weight type for U
 * 
 * @author Frederik Deckwerth
 * @author Erhan Leblebici
 * @author Roland Kluge
 */
public class BDDReachabilityAnalyzer<U extends OperationRuntime, W extends Comparable<W>> implements ReachabilityAnalyzer
{

   private final List<WeightedOperation<U, W>> operations;

   private final Adornment inputAdornment;

   private BDDFactory bddFactory;

   private BDDPairing fwdPairing;

   private BDDPairing revPairing;

   /*
    * 1st level of size 2: Distinguishes between pre- and post-variables (v and v')
    * 2nd level of size (O(|Adornment|): represents the list variables
    * 3rd level of size 2: Stores the (currently) 2 bits/variables per adornment entry 
    */
   private BDD[][][] bdd;

   private BDDDomain domain1;

   private BDDDomain domain2;

   boolean calculated = false;

   private BDD reachableStates;


   /**
    * Creates the BDD analyzer.
    * 
    * @param operations the available operations to build up a search plan for the pattern
    * @param inputAdornment the adornment of the pattern to be analyzed
    */
   public BDDReachabilityAnalyzer(List<WeightedOperation<U, W>> operations, Adornment inputAdornment)
   {
      this.operations = operations;
      this.inputAdornment = inputAdornment;
   }

   public void analyzeReachability()
   {
      final int cacheSize = 1000;
      final int numberOfBitsPerVariable = 2;
      final int variableCount = inputAdornment.size() * numberOfBitsPerVariable;
      final int numberOfNodes = (int) Math.max((Math.pow(variableCount, 3)) * 20, cacheSize);

      bddFactory = BDDFactory.init("java", numberOfNodes, cacheSize);
      bddFactory.setVarNum(variableCount * 2);
      bddFactory.setCacheRatio(1);
      fwdPairing = bddFactory.makePair();
      revPairing = bddFactory.makePair();
      domain1 = bddFactory.extDomain((long) Math.pow(2, variableCount));
      domain2 = bddFactory.extDomain((long) Math.pow(2, variableCount));
      bdd = new BDD[2][variableCount][2];
      bddFactory.setVarOrder(getVarOrder(variableCount));

      // Create a unique ID per variable
      int uniqueID = 0;
      for (int i = 0; i < 2; i++)
      {
         for (int j = 0; j < variableCount; j++)
         {
            for (int k = 0; k < 2; k++)
            {
               bdd[i][j][k] = bddFactory.ithVar(uniqueID++);
            }
         }
      }

      // Establishes a bidirectional mapping between pre- and post-variables (v and v')
      for (int j = 0; j < variableCount; j++)
      {
         fwdPairing.set(j, variableCount + j);
         revPairing.set(variableCount + j, j);
      }

      BDD transitionRelation = calculateTransitionRelation(operations);
      calculateReachableStates(transitionRelation);
      transitionRelation.free();
   }

   /**
    * Returns whether the given adornment can in principle be fulfilled using the provided operations
    */
   public final boolean isReachable(Adornment adornment)
   {
      if (reachableStates == null)
         throw new IllegalStateException("Need to perform reachability analysis first");

      return isReachable(adornment, reachableStates);
   }

   private BDD calculateTransitionRelation(List<WeightedOperation<U, W>> operations)
   {
      // long time = System.currentTimeMillis();
      BDD transitionRelation = bddFactory.zero();

      for (WeightedOperation<U, W> operation : operations)
      {
         if (operation != null && (operation.getOperation().getPrecondition().cardinality() != 0))
         {
            BDD cube = bddFactory.one();
            Adornment precondition = operation.getOperation().getPrecondition();
            for (int i = 0; i < precondition.size(); i++)
            {

               // Pre-variables
               BDD vp_1 = bdd[0][i][0];
               BDD vp_2 = bdd[0][i][1];

               // Post-variable
               BDD vp_1prime = bdd[1][i][0];
               BDD vp_2prime = bdd[1][i][1];

               // FREE corresponds to (True, True)
               if (Adornment.FREE == precondition.get(i))
               {
                  // Required to be free						
                  cube.andWith(vp_1.id());
                  cube.andWith(vp_2.id());
                  cube.andWith(vp_1prime.not());
                  cube.andWith(vp_2prime.not());

               } else
               // FREE corresponds to (False, False)
               if (Adornment.BOUND == precondition.get(i))
               {
                  // Required to be bound
                  cube.andWith(vp_1.not());
                  cube.andWith(vp_2.not());
                  cube.andWith(vp_1prime.not());
                  cube.andWith(vp_2prime.not());
               } else
               // FREE corresponds to (False, True)
               if (Adornment.NOT_TYPECHECKED == precondition.get(i))
               {
                  // Required to be not typechecked
                  cube.andWith(vp_1.not());
                  cube.andWith(vp_2.id());
                  cube.andWith(vp_1prime.not());
                  cube.andWith(vp_2prime.not());
               } else
               {
                  // Wildcard/don't care -> just 'copy' the truth values
                  cube.andWith(vp_1.biimp(vp_1prime));
                  cube.andWith(vp_2.biimp(vp_2prime));
               }
            }
            transitionRelation.orWith(cube);
         }
      }
      return transitionRelation;
   }

   private boolean isReachable(Adornment adornment, BDD r)
   {
      //TODO@rkluge: Adjust to new variable mapping
      if (adornment.get(r.var()) > Adornment.BOUND)
      {
         r = r.high();
      } else
      {
         r = r.low();
      }
      if (r.equals(bddFactory.one()))
      {
         //System.out.println("State "+adornment.toString()+" is Reachable");
         return true;
      }
      if (r.equals(bddFactory.zero()))
      {
         //System.out.println("State "+adornment.toString()+" is NOT Reachable");
         return false;
      }
      return isReachable(adornment, r);
   }

   public void calculateReachableStates(BDD transitionRelation)
   {
      // long time = System.currentTimeMillis();
      BDD old = domain1.ithVar(0);
      BDD nu = old;
      do
      {
         old = nu;
         BDD z = (transitionRelation.and(old.replace(fwdPairing))).exist(bddFactory.makeSet(domain2.vars()));
         nu = old.or(z);
      } while (!old.equals(nu));
      reachableStates = nu;
      // long outtime = (System.currentTimeMillis()-time);
      //System.out.println("\nGenerate all Reachable States in: "+outtime+"ms Nodecount is:"+nu.nodeCount());
      //System.out.println("Reachable States:");
      //nu.printSet();
   }

   private int[] getVarOrder(int varNr)
   {
      int[] varorder = new int[2 * varNr];
      for (int j = 0; j < varNr; j++)
      {
         varorder[2 * j] = j;
         varorder[2 * j + 1] = varNr + j;
      }
      return varorder;
   }
}
