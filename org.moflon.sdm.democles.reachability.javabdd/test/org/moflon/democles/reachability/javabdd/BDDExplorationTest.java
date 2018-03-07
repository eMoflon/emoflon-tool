package org.moflon.democles.reachability.javabdd;

import org.junit.Ignore;
import org.junit.Test;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;

public class BDDExplorationTest {
	@Ignore("Just for testing the undocumented toString behavior of JavaBDD")
	@Test
	public void testBiimp() {
		int numberOfNodes = (int) Math.max((Math.pow(2, 3)) * 20, getCachSize());
		BDDFactory bddFactory = BDDFactory.init("java", numberOfNodes, getCachSize());
		bddFactory.setVarNum(2);
		bddFactory.setCacheRatio(1);
		bddFactory.setVarOrder(new int[] { 0, 1 });
		BDD firstVariable = bddFactory.ithVar(0);
		BDD secondVariable = bddFactory.ithVar(1);
		BDD biimp = firstVariable.biimp(secondVariable);
		BDD imp = firstVariable.imp(secondVariable);
		BDD xor = firstVariable.xor(secondVariable);

		System.out.println("Each <..> fragment represents one possible path to a '1'.");
		System.out.println(
				"<0:1,1:1> means: from variable '0' take the 'true', then, from the variable '1' take the 'true' transition");
		System.out.println("v0:      " + firstVariable.toString());
		System.out.println("v1:      " + secondVariable.toString());
		System.out.println("v0<=>v1: " + biimp.toString());
		System.out.println("v0=>v1:  " + imp.toString());
		System.out.println("v0^v1:   " + xor.toString());
	}

	private int getCachSize() {
		return 1000;
	}
}
