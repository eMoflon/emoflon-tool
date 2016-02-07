/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;


import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPair;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;
import org.moflon.maave.tests.testgen.diachase.RBACTest;

/**
 * @author fdeckwerth
 *
 */
public class RbacMinimalContextTest {






   @Test
   public  void test1(){
	   System.out.println("Starting RbacMinimalContextTest/Test1" );
      RBACTest rbacTest=DiachaseFactory.eINSTANCE.createRBACTest();
      MorphismPairSet set=rbacTest.chasingAllEpis();

     Assert.assertTrue(set.getMorphismPairs().size()==2);
     for (MorphismPair mp : set.getMorphismPairs())
   {
      
     SymbolicGraphMorphism first1=mp.getFirst();
     SymbolicGraph minimalContext =first1.getCodom();
     Assert.assertTrue(minimalContext.getGraphNodes().size()==4);
     Assert.assertTrue(minimalContext.getGraphNodes().stream().anyMatch(x->x.getType().getName().equals("User")));
     Assert.assertTrue(minimalContext.getGraphNodes().stream().anyMatch(x->x.getType().getName().equals("SsdRelation")));
     Assert.assertTrue(minimalContext.getGraphNodes().stream().filter(x->x.getType().getName().equals("Role")).count()==2);
     
     Assert.assertTrue(minimalContext.getGraphEdges().size()==8);
     Assert.assertTrue(minimalContext.getGraphEdges().stream().filter(x->x.getType().getName().equals("roles")).count()==2);
     Assert.assertTrue(minimalContext.getGraphEdges().stream().filter(x->x.getType().getName().equals("users")).count()==2);
     Assert.assertTrue(minimalContext.getGraphEdges().stream().filter(x->x.getType().getName().equals("ssdRelation")).count()==2);
     Assert.assertTrue(minimalContext.getGraphEdges().stream().filter(x->x.getType().getName().equals("ssdExclusiveRoles")).count()==2);
   }
   }

      

}
