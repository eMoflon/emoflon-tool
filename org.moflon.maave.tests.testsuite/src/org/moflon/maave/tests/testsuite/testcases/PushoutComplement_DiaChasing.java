/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

import org.moflon.maave.tests.testgen.diachase.DiaChaseTestGen;
import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;

/**
 * @author fdeckwerth
 *
 */
public class PushoutComplement_DiaChasing {


	
	
	
	@Test
	public  void test1(){
		System.out.println("Starting PushoutComplement_DiaChasing/Test1" );
	   DiaChaseTestGen testgen=DiachaseFactory.eINSTANCE.createDiaChaseTestGen();
      SymbolicGraph contexObject=testgen.chasingTestPushoutComplement();
      Assert.assertTrue("FailedAssert: 1",contexObject.getGraphNodes().size()==3);
      Assert.assertTrue("FailedAssert: 2",contexObject.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("n2")));
      Assert.assertTrue("FailedAssert: 3",contexObject.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("n3")));
      Assert.assertTrue("FailedAssert: 4",contexObject.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("q2")));
      
      Assert.assertTrue("FailedAssert: 5",contexObject.getGraphEdges().size()==4);
      Assert.assertTrue("FailedAssert: 6",contexObject.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("n2")&&x.getTarget().getDebugId().equals("q2")&&x.getType().getName().equals("qs")));
      Assert.assertTrue("FailedAssert: 7",contexObject.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("q2")&&x.getTarget().getDebugId().equals("n2")&&x.getType().getName().equals("ns")));
      Assert.assertTrue("FailedAssert: 8",contexObject.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("q2")&&x.getTarget().getDebugId().equals("n3")&&x.getType().getName().equals("ns")));
      Assert.assertTrue("FailedAssert: 9",contexObject.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("n3")&&x.getTarget().getDebugId().equals("q2")&&x.getType().getName().equals("qs")));
    
      TestRunner.saveTestResult(contexObject, "contextGraph");

		
	}
	
}
