package org.moflon.gt.mosl.codeadapter.utils;

import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;

/**
 * This enumeration specifies the different types of Democles patterns that result from decomposing story patterns
 * 
 * This enumeration is a more strongly typed version of the *_FILE_EXTENSION constants in {@link DemoclesMethodBodyHandler} 
 * 
 * @author Sascha Zander
 * 
 * @see DemoclesMethodBodyHandler
 */
public enum PatternKind {
   BLACK, GREEN, RED, EXPRESSION, BINDING_AND_BLACK, BINDING;

   public String getSuffix()
   {
      switch (this)
      {
      case BINDING_AND_BLACK:
         return DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION;
      case BLACK:
         return DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION;
      case EXPRESSION:
         return DemoclesMethodBodyHandler.EXPRESSION_FILE_EXTENSION;
      case GREEN:
         return DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION;
      case RED:
         return DemoclesMethodBodyHandler.RED_FILE_EXTENSION;
      case BINDING:
         return DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION;
      default:
         return null;
      }
   }
   
   @Override
   public String toString()
   {
      return getSuffix();
   }
}