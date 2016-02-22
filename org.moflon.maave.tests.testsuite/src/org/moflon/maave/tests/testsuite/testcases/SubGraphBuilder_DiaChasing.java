/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;


import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.CategoryUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

import org.moflon.maave.tests.testgen.diachase.DiaChaseTestGen;
import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;


/**
 * @author fdeckwerth
 *
 */
public class SubGraphBuilder_DiaChasing {


	CategoryUtil util=MatchingUtilsFactory.eINSTANCE.createCategoryUtil();
	
	
	@Test
	public  void test1(){
		System.out.println("Starting SubGraphBuilder_DiaChasing/Test1" );
	   DiaChaseTestGen testgen=DiachaseFactory.eINSTANCE.createDiaChaseTestGen();
      MorphismsSet morphisms=testgen.chasingTestSubGraphBuilder();
      for (SymbolicGraphMorphism morphism : morphisms.getMorphisms())
      {
         Assert.assertTrue(util.isValidEGraphMorphism(morphism));
         
      }
      
     
    
      

		
	}
	
}
