package org.moflon.gt.mosl.codeadapter.utils;

public enum PatternKind {
   BLACK, GREEN, RED, EXPRESSION, BINDING_AND_BLACK, BINDING;

   public String getSuffix()
   {
      switch (this)
      {
      case BINDING_AND_BLACK:
         return "bindingAndBlack";
      case BLACK:
         return "black";
      case EXPRESSION:
         return "expression";
      case GREEN:
         return "green";
      case RED:
         return "red";
      case BINDING:
         return "binding";
      default:
         return null;
      }
   }
}