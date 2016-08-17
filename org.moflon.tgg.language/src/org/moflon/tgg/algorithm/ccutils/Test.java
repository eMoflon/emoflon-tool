package org.moflon.tgg.algorithm.ccutils;

import org.gnu.glpk.GLPK;
public class Test {
  public static void main(String[] args) {
	  try {
	    } catch (UnsatisfiedLinkError e) {
	      System.err.println("Native code library failed to load.\n" + e);
	      System.exit(1);
	    }
	  System.out.println("here");
    System.out.println( GLPK.glp_version());
  }
}
