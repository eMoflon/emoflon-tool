package org.moflon.democles.reachability.javabdd;

import java.util.List;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.plan.ReachabilityAnalyzer;
import org.gervarro.democles.plan.WeightedOperation;

import net.sf.javabdd.*;

public class BDDReachabilityAnalyzer<U extends OperationRuntime, W extends Comparable<W>> implements ReachabilityAnalyzer {

	private BDDFactory bddFactory;
	BDDPairing fwdPairing;
	BDDPairing revPairing;
	BDD[][] bdd;
	BDDDomain domain1;
	BDDDomain domain2;
	boolean calculated = false;
	
	BDD reachableStates;

	public BDDReachabilityAnalyzer(List<WeightedOperation<U,W>> operations, Adornment inputAdornment){
		int cacheSize = 1000;
		int v = inputAdornment.size();
		int numberOfNodes = (int) Math.max((Math.pow(v, 3))*20, cacheSize);

		bddFactory = BDDFactory.init("java", numberOfNodes, cacheSize);
		bddFactory.setVarNum(v*2);
		bddFactory.setCacheRatio(1);
		fwdPairing = bddFactory.makePair();
		revPairing = bddFactory.makePair();
		domain1 = bddFactory.extDomain((long)Math.pow(2, v));
		domain2 = bddFactory.extDomain((long)Math.pow(2, v));
		bdd = new BDD[2][v];
		bddFactory.setVarOrder(getVarOrder(v));
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < v; j++) {
				bdd[i][j] = bddFactory.ithVar(i * v + j);
			}
		}
		
		for (int j = 0; j < v; j++){
			fwdPairing.set(j,v+j);
			revPairing.set(v+j, j);
		}
		BDD transitionRelation=calculateTransitionRelation(operations);
		calculateReachableStates(transitionRelation);
		transitionRelation.free();
	}
	
	public BDD calculateTransitionRelation(List<WeightedOperation<U,W>> operations) {
		// long time = System.currentTimeMillis();
		BDD transitionRelation = bddFactory.zero();
		
		for (WeightedOperation<U,W> operation : operations){
			if (operation != null && (operation.getOperation().getPrecondition().cardinality() != 0)){
				BDD cube = bddFactory.one();
				//TODO This process has to be updated
				Adornment precondition = operation.getOperation().getPrecondition();
				for (int i = 0; i < precondition.size(); i++) {
					if (Adornment.FREE == precondition.get(i)) {
						// Required to be free
						cube.andWith(bdd[0][i].id());
						cube.andWith(bdd[1][i].not());
					} else if(Adornment.BOUND == precondition.get(i)) {
						// Required to be bound
						cube.andWith(bdd[0][i].not());
						cube.andWith(bdd[1][i].not());
					} else {
						// Not defined
						cube.andWith(bdd[0][i].biimp(bdd[1][i]));
					}
				}
				transitionRelation.orWith(cube);
			}
		}
		// System.out.println("\nTransition Relation generated in: "+(System.currentTimeMillis()-time)+"ms");
		return transitionRelation;
	}
	
	private boolean isReachable(Adornment adornment, BDD r) {
		if (adornment.get(r.var()) > Adornment.BOUND) {
			r = r.high();
		} else {
			r = r.low();
		}
		if (r.equals(bddFactory.one())) {
			//System.out.println("State "+adornment.toString()+" is Reachable");
			return true;
		}
		if (r.equals(bddFactory.zero())) {
			//System.out.println("State "+adornment.toString()+" is NOT Reachable");
			return false;
		}
		return isReachable(adornment, r);
	}
	
	public final boolean isReachable(Adornment adornment) {
		return isReachable(adornment, reachableStates);
	}
	
	public void calculateReachableStates(BDD transitionRelation) {
		// long time = System.currentTimeMillis();
		BDD old = domain1.ithVar(0);
		BDD nu = old;
		do {
			old = nu;
			BDD z = (transitionRelation.and(old.replace(fwdPairing))).exist(bddFactory.makeSet(domain2.vars()));
			nu = old.or(z);
		} while (!old.equals(nu));
		reachableStates = nu;
		// long outtime = (System.currentTimeMillis()-time);
		//System.out.println("\nGenerate all Reachable States in: "+outtime+"ms Nodecount is:"+nu.nodeCount());
		//System.out.println("Reachable States:");
		nu.printSet();
	}
	
	private int[] getVarOrder(int varNr){
		int [] varorder = new int[2*varNr];
		for (int j = 0; j < varNr; j++) {
			varorder[2*j] = j;
			varorder[2*j+1] = varNr+j;
		}
		return varorder;
	}
}
