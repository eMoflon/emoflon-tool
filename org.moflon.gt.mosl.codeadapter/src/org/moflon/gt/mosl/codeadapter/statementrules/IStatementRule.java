package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.Scope;

public interface IStatementRule
{
   /**
    * Returns true if this rule is able to handle the given type of statement
    * @param statement
    * @return
    */
   boolean canHandle(final Statement statement);

   /**
    * 
    * @param statement
    * @param scope
    * @param previosCFNode
    * @precondition {@link #canHandle(Statement)} must return true for statement 
    */
   void invoke(Statement statement, Scope scope, CFNode previosCFNode);
}
