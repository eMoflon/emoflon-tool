package org.moflon.gt.mosl.codeadapter;

import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraint;
import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraintFactory;

public class MOSLGTUtil
{
   private static MOSLGTUtil instance;

   private MOSLGTUtil()
   {

   }

   public static MOSLGTUtil getInstance()
   {
      if (instance == null)
         instance = new MOSLGTUtil();
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
