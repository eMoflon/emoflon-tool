package org.moflon.gt.mosl.codeadapter.utils;

import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraint;
import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraintFactory;

public class ExpressionUtil
{
   private static ExpressionUtil instance;

   private ExpressionUtil()
   {

   }

   public static ExpressionUtil getInstance()
   {
      if (instance == null)
         instance = new ExpressionUtil();
      return instance;
   }
   
   public RelationalConstraint getConstraint(String opValue){
      switch(opValue){
      case "==": return RelationalConstraintFactory.eINSTANCE.createEqual();
      case ">": return RelationalConstraintFactory.eINSTANCE.createLarger();
      case ">=": return RelationalConstraintFactory.eINSTANCE.createLargerOrEqual();
      case "<": return RelationalConstraintFactory.eINSTANCE.createSmaller();
      case "<=": return RelationalConstraintFactory.eINSTANCE.createSmallerOrEqual();
      default: return null;
      }
   }
}
