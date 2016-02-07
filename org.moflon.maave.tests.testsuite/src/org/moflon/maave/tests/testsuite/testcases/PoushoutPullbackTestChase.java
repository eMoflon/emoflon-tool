/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;


import org.junit.Assert;
import org.junit.Test;

import org.moflon.maave.tests.testgen.diachase.DiaChaseTestGen;
import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;

/**
 * @author fdeckwerth
 *
 */
public class PoushoutPullbackTestChase {

   



   @Test
   public  void test1(){
	   DiaChaseTestGen testgen=DiachaseFactory.eINSTANCE.createDiaChaseTestGen();
	   Assert.assertTrue(testgen.chasingTestPO_PB("testPatternA","testPatternB","testPatternC"));
	   Assert.assertTrue(testgen.chasingTestPO_PB("testPatternA2","testPatternB2","testPatternC2"));
      
    
     
   }

      

}
