package org.moflon.gt.mosl.codeadapter.utils;

import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;

public enum PatternInvocationType {
   REGULAR, SINGLE_RESULT;


public PatternInvocation createPatternInvocation(){
   
   switch(this){
   case REGULAR:
      return DemoclesFactory.eINSTANCE.createRegularPatternInvocation();
   case SINGLE_RESULT:
      return DemoclesFactory.eINSTANCE.createSingleResultPatternInvocation();
   default:
      throw new AssertionError();   
   }
}
}