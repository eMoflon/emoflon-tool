package org.moflon.ide.debug.core.model.phases.translation;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.moflon.ide.debug.core.model.MoflonVariable;
import org.moflon.ide.debug.core.model.phases.init.RuleVariable.Mode;

import org.moflon.tgg.debug.language.DebugMatch;
import org.moflon.tgg.language.analysis.AnalysisFactory;
import org.moflon.tgg.language.analysis.Rule;

public class MatchValue extends MoflonDebugElement implements IValue
{

   public static final String CONTEXT = "Context";

   private DebugMatch match;

   private IVariable[] variables;

   public MatchValue(IDebugTarget target, DebugMatch match)
   {
      super(target);
      this.match = match;
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return null;
   }

   @Override
   public String getValueString() throws DebugException
   {
      return "Source Match";
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      if (variables == null)
      {
         variables = new IVariable[3];
         Rule rule = AnalysisFactory.eINSTANCE.createRule();
         rule.setRuleName(match.getRuleName());
         variables[0] = MoflonVariable.createVariable(rule, Mode.RULE, "rule");
         // variables[1] = MoflonVariable.createVariable(match.getToBeTranslatedNodes(), "toBeTranslatedNodes");
         // variables[2] = MoflonVariable.createVariable(match.getToBeTranslatedEdges(), "toBeTranslatedEdges");
         // variables[3] = MoflonVariable.createVariable(match.getContextNodes(), "ContextNodes");
         // variables[4] = MoflonVariable.createVariable(match.getContextEdges(), "ContextEdges");
         variables[1] = MoflonVariable.createVariable(match.getToBeTranslated(), TranslationStateStackFrame.TO_BE_TRANSLATED);
         variables[2] = MoflonVariable.createVariable(match.getContext(), CONTEXT);
         
      }
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return getVariables().length > 0;
   }

}
