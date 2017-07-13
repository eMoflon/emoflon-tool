package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.moslgt.PatternStatement;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public class PatternStatementRule extends AbstractNextStatementRule<PatternStatement>
{

   @Override
   protected Class<PatternStatement> getStatementClass()
   {
      return PatternStatement.class;
   }

   @Override
   protected ValidationReport transformStatement(PatternStatement stmnt, Scope scope, CFNode previosCFNode, final TransformationConfiguration transformationConfiguration)
   {
      CFNode cfNode = this.updateCurrentNode(DemoclesFactory.eINSTANCE.createCFNode());
      cfNode.setScope(scope);
      return this.handlePattern(stmnt.getParameters(), stmnt.getPattern(), cfNode, scope, transformationConfiguration);
   }

}
